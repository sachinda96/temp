/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.management.MBeanException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import soul.catalogue.Location;
import soul.catalogue.service.LocationFacadeREST;
import soul.circulation.TBookTransfer;
import soul.circulation.TStockvarification;
import soul.catalogue.MBkstatus;
import soul.catalogue.service.MBkstatusFacadeREST;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;


/**
 *
 * @author admin
 */
@Stateless
@Path("soul.circulation.tstockvarification")
public class TStockvarificationFacadeREST extends AbstractFacade<TStockvarification> {

    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private MBkstatusFacadeREST mBkStatusFacadeRest;
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    private String[] accNos;
    //private String[] originalLocations;
    List<Location> locationList = new ArrayList<>();
    List<MBkstatus> statusDescription = new ArrayList<>();
    List<TStockvarification> stockVerification = new ArrayList<>();
    Location location;
    String bookStatus;

    public TStockvarificationFacadeREST() {
        super(TStockvarification.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TStockvarification entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, TStockvarification entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public TStockvarification find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TStockvarification> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TStockvarification> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @POST
    @Path("stockVerification")
    @Consumes({"application/xml", "application/json"})
    @Produces("text/plain")
    public String stockVerification(String stockdata) { 
        
        String accessionNos="";
       // String originalLocation="";
        String foundLocation="";
        String verifiedBy="";
        
        String datatype = stockdata.substring(0,1);
        if(datatype.equals("{")){
           ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(stockdata);
             accessionNos = jsonobj.getString("accessionNos");
             //originalLocation=jsonobj.getString("originalLocation");
             foundLocation = jsonobj.getString("foundLocation");
             verifiedBy= jsonobj.getString("verifiedBy");
                     
        }else if(datatype.equals("<")){
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(stockdata);
                accessionNos = stringintoxml.getdatafromxmltag(doc,"accessionNos");
               // originalLocation=stringintoxml.getdatafromxmltag(doc,"originalLocation");
                foundLocation=stringintoxml.getdatafromxmltag(doc,"foundLocation");
                verifiedBy=stringintoxml.getdatafromxmltag(doc,"verifiedBy");
                        
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }

        accNos = accessionNos.split(",");
       // originalLocations = originalLocation.split(",");
        System.out.println("accNos " + accNos + "   s" + accNos.length);
       // System.out.println("originalLocations " + originalLocations + "   s" + originalLocations.length);
        String output;
        String verified = "";
        String notVerified = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        List<Location> liloc = null;
        
        System.out.println(Arrays.toString(accNos));
        String statusCode;
        for (int i = 0; i < accNos.length; i++) {
            if (checkStock(accNos[i]) == "true") {
                liloc = locationFacadeREST.getByAcc(accNos[i]);
                String originallocation="NaN";
                if(liloc.get(0).getC852().isEmpty()){
                    originallocation="NaN";
                }else{
                    originallocation= liloc.get(0).getC852();
                }
                TStockvarification stockvarification = new TStockvarification();
                stockvarification.setFoundlocation(foundLocation);
                stockvarification.setOriginallocation(originallocation);
                stockvarification.setFound(verifiedBy);
                stockvarification.setFounddt(date);

                System.out.println(dateFormat.format(date));
                stockvarification.setFounddt(date);
                System.out.println(Arrays.toString(accNos));
                System.out.println(accNos[i]);
                stockvarification.setAccNo(accNos[i]);
                System.out.println("..........size.... " + locationFacadeREST.findBy("findByP852", accNos[i].trim()).size());

                try {
                    statusCode = locationFacadeREST.getByAcc(accNos[i].trim()).get(0).getStatus();
                } catch (ArrayIndexOutOfBoundsException e) {
                    return "Invalid accession no.";
                }
                System.out.println("status  " + statusCode);
                bookStatus = mBkStatusFacadeRest.find(statusCode).getStatusDscr();
                System.out.println(bookStatus);
                stockvarification.setStatus(bookStatus);
                create(stockvarification);
                verified = verified + " " + accNos[i] + ",";
            } else {
//<<<<<<< .mine
//                notVerified =  notVerified+" "+accNo+",";
//=======
                notVerified = notVerified + " " + accNos[i] + ",";
//>>>>>>> .r33
            }
        }
        output = "Stock Verified for following accNo: " + verified + " \nStock  already Verified for following accNo: " + notVerified;
        return output;
    }

    @DELETE
    @Path("clearStock")
    @Produces("text/plain")
    public String clearStock() {
        String output;
        List<TStockvarification> stock = new ArrayList<>();
        stock = findAll();
        for (TStockvarification stocks : stock) {
            removeAll(stocks);
        }
        return "Stock Verificcation cleared.";
    }

    public void removeAll(TStockvarification stock) {
        remove(stock);
    }

    public String checkStock(String accNo) {
        List<TStockvarification> list = new ArrayList<>();
        List<String> arrayList = new ArrayList<>();
        arrayList.add(accNo);
        list = super.findBy("TStockvarification.findByAccNo", arrayList);
        System.out.println("list.size()  " + list.size());
        if (list.size() > 0) {
            return "false";
        } else {
            return "true";
        }
    }
//<<<<<<< .mine
//    
//    @POST
//    @Path("importAccession")
//    @Consumes({"multipart/form-data"})
//    @Produces("text/plain")
//    public Response importAccession(@FormDataParam("fileLocation") InputStream fileLocation) {
//        Response.ResponseBuilder responseBuilder = Response.status(200);
//        String output = null;
//        try {
//            Workbook wb = WorkbookFactory.create(fileLocation);
//            Sheet sheet = wb.getSheetAt(0); 
//            ArrayList<String> list_accNos = new ArrayList<>();
//            Row row;
//            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//                row = sheet.getRow(i);
//                if (row.getCell(0) == null || row.getCell(0).toString().length() == 0 || row.getCell(0).toString().contentEquals("null")) {
//                        output = "Nothing to import!!!";
//                        responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
//                    } else {
//                        list_accNos.add(row.getCell(0).toString());
//                        responseBuilder.type(MediaType.APPLICATION_XML).entity(list_accNos);
//                    }
//            }
//        } catch (IOException ioe) {
//            System.out.println(ioe);
//        } catch (Exception ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return responseBuilder.build();
//    }
//}
//=======
//>>>>>>> .r33

//    @POST
//    @Path("importAccNo")
//    @Consumes({"multipart/form-data"})
//    @Produces({"application/xml", "application/json"})
//    public JsonObject importAccNo(
//            @FormDataParam("file") File file) throws FileNotFoundException, IOException {
//
//        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
//        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
//        BufferedReader br = null;
//        String line = "";
//        String cvsSplitBy = ",";
//
//        try {
//
//            br = new BufferedReader(new FileReader(file));
//            while ((line = br.readLine()) != null) {
//                String[] accNo = line.split(cvsSplitBy);
//               arrayBuilder.add(accNo[0]);
//                 
//            }
// jsonBuilder.add("accNo", arrayBuilder);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//        return jsonBuilder.build();
//    }
    
        @POST
    @Path("importAccNo")
    @Consumes({"multipart/form-data"})
    @Produces({"application/xml", "application/json"})
    public JsonObject importAccNo(
            @FormDataParam("file") File file) throws FileNotFoundException, IOException {

        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                String[] accNo = line.split(cvsSplitBy);
               arrayBuilder.add(accNo[0]);
                 
            }
 jsonBuilder.add("accNo", arrayBuilder);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return jsonBuilder.build();
    }
}
