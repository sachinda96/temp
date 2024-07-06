/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue.service;

//import ExceptionService.DataException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import soul.catalogue.Biblidetails;
import soul.catalogue.BiblidetailsPK;
import org.ini4j.Wini;
import com.google.gson.Gson;
//import com.sun.jersey.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.FormDataParam;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.Query;
import javax.servlet.ServletException;
import org.apache.commons.lang3.StringUtils;
import soul.catalogue.Location;
import java.io.File;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.Collections;
import javax.servlet.ServletContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import soul.general_master.service.LibmaterialsFacadeREST;
import soul.serialControl.SMst;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.biblidetails")
public class BiblidetailsFacadeREST extends AbstractFacade<Biblidetails> {

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private BiblifilesFacadeREST biblifilesFacadeREST;
    @EJB
    private AuthoritybiblidetailsFacadeREST authoritybiblidetailsFacadeREST;
    @EJB
    private LibmaterialsFacadeREST libmaterialsFacadeREST;
    SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
    // SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");

    @PUT
    @Path("viewers/view/fines/data")
    public void countAll() {
        Biblidetails s = new Biblidetails();
        Query query = getEntityManager().createNativeQuery(s.getFine());
        int result = query.executeUpdate();
    }
    
    private BiblidetailsPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;recID=recIDValue;tagSrNo=tagSrNoValue;tag=tagValue;sbFldSrNo=sbFldSrNoValue;sbFld=sbFldValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.catalogue.BiblidetailsPK key = new soul.catalogue.BiblidetailsPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> recID = map.get("recID");
        if (recID != null && !recID.isEmpty()) {
            key.setRecID(new java.lang.Integer(recID.get(0)));
        }
        java.util.List<String> tagSrNo = map.get("tagSrNo");
        if (tagSrNo != null && !tagSrNo.isEmpty()) {
            key.setTagSrNo(new java.lang.Integer(tagSrNo.get(0)));
        }
        java.util.List<String> tag = map.get("tag");
        if (tag != null && !tag.isEmpty()) {
            key.setTag(tag.get(0));
        }
        java.util.List<String> sbFldSrNo = map.get("sbFldSrNo");
        if (sbFldSrNo != null && !sbFldSrNo.isEmpty()) {
            key.setSbFldSrNo(new java.lang.Integer(sbFldSrNo.get(0)));
        }
        java.util.List<String> sbFld = map.get("sbFld");
        if (sbFld != null && !sbFld.isEmpty()) {
            key.setSbFld(sbFld.get(0));
        }
        return key;
    }

    public BiblidetailsFacadeREST() {
        super(Biblidetails.class);
    }

    @POST
    @Path("addTemplateDataEntry")
    @Consumes({ "multipart/form-data"})
     public Response addTemplateDataEntry(@FormDataParam("tagSrNo") String tagSrNo, @FormDataParam("tagArray") String tagArray,
            @FormDataParam("sbfldSrNo") String sbfldSrNo, @FormDataParam("sbfldArray") String sbfldArray, @FormDataParam("hasIndArray") String hasIndArray,
            @FormDataParam("fval") String fval, @FormDataParam("copyNo") String copyNo, @FormDataParam("LocInd1Val") String LocInd1Val,
            @FormDataParam("LocInd2Val") String LocInd2Val, @FormDataParam("accNo") String accNo,
            @FormDataParam("collection") String collection, @FormDataParam("codedLocation") String codedLocation,
            @FormDataParam("department") String department, @FormDataParam("supplier") String supplier,
            @FormDataParam("material") String material, @FormDataParam("classNo") String classNo,
            @FormDataParam("location") String locationdata, @FormDataParam("budget") String budget,
            @FormDataParam("invoiceNo") String invoiceNo, @FormDataParam("statusType") String statusType,
            @FormDataParam("bookNo") String bookNo, @FormDataParam("shelvingLocation") String shelvingLocation,
            @FormDataParam("currency") String currency, @FormDataParam("currencyRate") String currencyRate,
            @FormDataParam("invoiceDate") String invoiceDate, @FormDataParam("dateOfAcquisition") String dateOfAcquisition,
            @FormDataParam("issueRestricted") String issueRestricted, @FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("filename") String filename) {
        try {   // * save Biblidetails*     
            System.out.println("tagSrNo  .... "+tagSrNo);
            Biblidetails b = create(tagSrNo, tagArray, sbfldSrNo, sbfldArray, hasIndArray, fval); // *save bibliDetails*
            int recId = b.getBiblidetailsPK().getRecID();
            // *save location* 
            try {
                locationFacadeREST.createLocation(String.valueOf(recId), copyNo, LocInd1Val, LocInd2Val, accNo, collection, codedLocation, department,
                        supplier, material, classNo, locationdata, budget, invoiceNo, statusType,
                        bookNo, shelvingLocation, currency, currencyRate, invoiceDate, dateOfAcquisition, issueRestricted);
            } catch (ParseException ex) {
                Logger.getLogger(BiblidetailsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            // *save file*
            System.out.println("filename :" + filename + "  uploadedInputStream  " + uploadedInputStream);
            if (filename != null) {
                biblifilesFacadeREST.uploadFile(recId, uploadedInputStream, filename);
            }
        } catch (Exception e) {
            return Response.status(500).entity("error").build();

        }
        return Response.status(200).entity("saved successfully").build();
    }

    @PUT
    @Path("updateTemplateDataEntry")
    @Consumes({ "multipart/form-data"})
    // @Produces("text/plain")
    public Response updateTemplateDataEntry(@FormDataParam("currentRecId") String currentRecId, @FormDataParam("tagSrNo") String tagSrNo, @FormDataParam("tagArray") String tagArray,
            @FormDataParam("sbfldSrNo") String sbfldSrNo, @FormDataParam("sbfldArray") String sbfldArray, @FormDataParam("hasIndArray") String hasIndArray,
            @FormDataParam("fval") String fval, @FormDataParam("copyNo") String copyNo, @FormDataParam("LocInd1Val") String LocInd1Val,
            @FormDataParam("LocInd2Val") String LocInd2Val, @FormDataParam("accNo") String accNo,
            @FormDataParam("collection") String collection, @FormDataParam("codedLocation") String codedLocation,
            @FormDataParam("department") String department, @FormDataParam("supplier") String supplier,
            @FormDataParam("material") String material, @FormDataParam("classNo") String classNo,
            @FormDataParam("location") String locationdata, @FormDataParam("budget") String budget,
            @FormDataParam("invoiceNo") String invoiceNo, @FormDataParam("statusType") String statusType,
            @FormDataParam("bookNo") String bookNo, @FormDataParam("shelvingLocation") String shelvingLocation,
            @FormDataParam("currency") String currency, @FormDataParam("currencyRate") String currencyRate,
            @FormDataParam("invoiceDate") String invoiceDate, @FormDataParam("dateOfAcquisition") String dateOfAcquisition,
            @FormDataParam("issueRestricted") String issueRestricted, @FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("filename") String filename) {
        try {    // * update Biblidetails*  
            update(currentRecId, tagSrNo, tagArray, sbfldSrNo, sbfldArray, hasIndArray, fval); // *save bibliDetails*
            // *update location* 
            
                locationFacadeREST.update(currentRecId, copyNo, LocInd1Val, LocInd2Val, accNo, collection, codedLocation, department,
                        supplier, material, classNo, locationdata, budget, invoiceNo, statusType,
                        bookNo, shelvingLocation, currency, currencyRate, invoiceDate, dateOfAcquisition, issueRestricted);
            
            // *update file*  
            if (filename != null) {
                biblifilesFacadeREST.updateFile(uploadedInputStream, filename, currentRecId);
            }
        } catch (Exception e) {
            return Response.status(500).entity("Data could not be updated successfully").build();
        }
        return Response.status(200).entity("updated successfully").build();
    }

    @POST
    @Path("add")
    @Consumes({"application/xml", "application/json"})
    public Biblidetails create(@FormParam("tagSrNo") String tagSrNo, @FormParam("tagArray") String tagArray,
            @FormParam("sbfldSrNo") String sbfldSrNo, @FormParam("sbfldArray") String sbfldArray, @FormParam("hasIndArray") String hasIndArray,
            @FormParam("fval") String fval) {

        String output = null;
        // splitting each parameter
        String tagSrNoData[] = tagSrNo.split("~", -1);
        String tagArrayData[] = tagArray.split("~", -1);
        String sbfldSrNoData[] = sbfldSrNo.split("~", -1);
        String sbfldArrayData[] = sbfldArray.split("~", -1);
        String hasIndArrayData[] = hasIndArray.split("~", -1);
        String fvalData[] = fval.split("~", -1);
        // to get next id
        List<Biblidetails> countRecId = findBy("findByMaxRecID", "NULL");
        int nextRecId = (countRecId.get(0).getBiblidetailsPK().getRecID() + 1);
        // nextRecId++;
        System.out.println("Previous-RecId  " + countRecId.get(0).getBiblidetailsPK().getRecID());
        System.out.println("Next Recid : "+nextRecId);
        Biblidetails newBiblidetails = new Biblidetails();
        
        List<String> tag = new ArrayList();  
         int f=0;
        //check tag or subfield is repetable..
        for (int i = 0; i < tagSrNoData.length; i++) {
            
            tag.add(tagArrayData[i]);
        }
        
        System.out.println("List of tag : "+tag);
        
       
        for (int i = 0; i < tagSrNoData.length; i++) {
            Biblidetails biblidetails = new Biblidetails();
            BiblidetailsPK biblidetailsPK = new BiblidetailsPK();
            if (!fvalData[i].isEmpty()) {
                biblidetailsPK.setRecID(nextRecId);
                biblidetailsPK.setTagSrNo(Integer.parseInt(tagSrNoData[i]));
                biblidetailsPK.setTag(tagArrayData[i]);
                biblidetailsPK.setSbFldSrNo(Integer.parseInt(sbfldSrNoData[i]));
                biblidetailsPK.setSbFld(sbfldArrayData[i]);
                if (!hasIndArrayData[i].contentEquals("no")) {
                    biblidetails.setInd(hasIndArrayData[i]);
                }
                //   biblidetails.setInd(hasIndArrayData[i]);
                biblidetails.setFValue(fvalData[i]);
                 System.out.println("biblidetailsPK : "+biblidetailsPK.getTag());
                biblidetails.setBiblidetailsPK(biblidetailsPK);
                int count = Integer.parseInt(countREST());
                newBiblidetails = super.createAndGet(biblidetails);
                if (Integer.parseInt(countREST()) != count) {

                    output = "success";
                } else {
                    removeBy("deleteByRecID", String.valueOf((count + 1)));
                    output = "Data not saved";
                }
            }
        }
        return newBiblidetails;
    }

    @PUT
    @Path("update")
    @Consumes({"application/xml", "application/json"})
    public void update(@FormParam("currentRecId") String currentRecId, @FormParam("tagSrNo") String tagSrNo, @FormParam("tagArray") String tagArray,
            @FormParam("sbfldSrNo") String sbfldSrNo, @FormParam("sbfldArray") String sbfldArray, @FormParam("hasIndArray") String hasIndArray,
            @FormParam("fval") String fval) {

try{
        int currentRecIdInt = Integer.parseInt(currentRecId);
        System.out.println("currentRecId  " + currentRecId);
        System.out.println("editdata............" + tagSrNo + "....." + tagArray + "..." + sbfldSrNo + ".." + sbfldArray + ".." + hasIndArray + ".." + fval);
        //request.getRequestDispatcher("country.jsp").forward(request, response);
        //response.sendRedirect("country.jsp");
        //   out.println(output);
        String edittagSrNoData[] = tagSrNo.split("~", -1);
        String edittagArrayData[] = tagArray.split("~", -1);
        String editsbfldSrNoData[] = sbfldSrNo.split("~", -1);
        String editsbfldArrayData[] = sbfldArray.split("~", -1);
        String edithasIndArrayData[] = hasIndArray.split("~", -1);
        String editfvalData[] = fval.split("~", -1);
        // int RecId = biblidetailsClient.findByQuery_XML(Integer.class, "findMaxRecId", "null");
        System.out.println("edittagSrNoData " + edittagSrNoData.length);
        System.out.println("edithasIndArrayData " + edithasIndArrayData.length);
        System.out.println("edittagArrayData " + edittagArrayData.length);
        System.out.println("editsbfldSrNoData " + editsbfldSrNoData.length);
        System.out.println("editsbfldArrayData " + editsbfldArrayData.length);
        System.out.println("editfvalData " + editfvalData.length);
        //  biblidetailsClient.remove(request.getParameter("currentRecId"));
        // biblidetailsClient.removeBy("deleteByRecID", currentRecId); 
        // for(int i=2;i<edittagSrNoData.length;i++) //omit i=0&1 because it has no subfield (ie - leader & fxfield)
        // {
//                    if(!editfvalData[i].isEmpty())
//                    {   if(editsbfldArrayData[i].isEmpty())
//                        {
//                            editsbfldArrayData[i] = "";
//                        }
        // removeBy("deleteByRecID&tag&sbfld", currentRecId+","+edittagArrayData[i]+","+editsbfldArrayData[i]);    
        // removeBy("deleteByRecID&tag&sbfld", ); 
        // }
        //    }
        for (int i = 0; i < edittagSrNoData.length; i++) {
            Biblidetails biblidetails = new Biblidetails();
            BiblidetailsPK biblidetailsPK = new BiblidetailsPK();
            if (!editfvalData[i].isEmpty()) {

                //   biblidetailsClient.removeBy("deleteByRecID&tag&sbfld", currentRecId+","+edittagArrayData[i]+","+editsbfldArrayData[i]); 
                biblidetailsPK.setRecID(currentRecIdInt);
                biblidetailsPK.setTagSrNo(Integer.parseInt(edittagSrNoData[i]));
                biblidetailsPK.setTag(edittagArrayData[i]);
                biblidetailsPK.setSbFldSrNo(Integer.parseInt(editsbfldSrNoData[i]));
                biblidetailsPK.setSbFld(editsbfldArrayData[i]);
                if (!edithasIndArrayData[i].contentEquals("no")) {
                    biblidetails.setInd(edithasIndArrayData[i]);
                }
                //   biblidetails.setInd(hasIndArrayData[i]);
                System.out.println("data...........update.. " + i + " " + currentRecIdInt + " " + edittagSrNoData[i] + " " + edittagArrayData[i] + " " + editsbfldSrNoData[i] + "  " + editsbfldArrayData[i] + " " + editsbfldSrNoData[i] + " " + editfvalData[i]);
                biblidetails.setFValue(editfvalData[i]);
                biblidetails.setBiblidetailsPK(biblidetailsPK);
                super.edit(biblidetails);
                // biblidetailsClient.create_XML(objectFactory.createBiblidetails(biblidetails));
            }

        }
}
catch(Exception e)
{
  //  throw new DataException("error");
}
     
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.catalogue.BiblidetailsPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Biblidetails find(@PathParam("id") PathSegment id) {
        soul.catalogue.BiblidetailsPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Biblidetails> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Biblidetails> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("count/by/{namedQuery}/{values}")
    @Produces("text/plain")
    public String countBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        String output = null;
        switch (query) {   // used in controljs to check whether a recorId exists or not
            case "countRecId":
                try {
                    System.out.println("len......... " + valueString[0].length());
                    System.out.println("len......... " + valueString[0].trim().length());
                    valueList.add(Integer.parseInt(valueString[0].trim()));
                    String count = String.valueOf(super.countBy("Biblidetails." + query, valueList));

                    if (count.contentEquals("0")) {
                        output = "no";
                    } else {
                        output = "yes";
                    }
                } catch (Exception e) {
                    System.out.println("caught..............");
                    output = "no";
                }
                break;
            default:
                valueList.add(valueString[0]);
            //used for countRecId
        }
        return output;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Added manually
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<Biblidetails> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        switch (query) {
            case "findByMaxRecID":  //used by AccessioningServlet
                return super.findBy("Biblidetails." + query);
            case "findByRecIDMaxSbFldSrNoAndTag887":
                valueList.add(Integer.parseInt(valueString[0]));
                break;
            case "findByRecIDAndTag887":
                valueList.add(Integer.parseInt(valueString[0]));
                break;
            case "findByRecIDAndTag563":
                valueList.add(Integer.parseInt(valueString[0]));
                break;
            case "findByRecID":
                valueList.add(Integer.parseInt(valueString[0]));
                break;
            case "findBetweenStartAndEndRecId":
                valueList.add(Integer.parseInt(valueString[0]));
                valueList.add(Integer.parseInt(valueString[1]));
                break;
            case "findByf852AndDateOfAcq":
                try {
                    Date date1 = dateFormat.parse(valueString[1]);
                    Date date2 = dateFormat.parse(valueString[2]);
                    valueList.add(valueString[0]);
                    valueList.add(new java.sql.Date(date1.getTime()));
                    valueList.add(new java.sql.Date(date2.getTime()));
                } catch (ParseException ex) {
                    Logger.getLogger(BiblidetailsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "findRecIdToSetLocationByTitle":
                valueList.add("%" + valueString[0] + "%");
                break;
            case "findByListOfRecId":  // used in add acc no. to imported data
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < valueString.length; i++) {
                    list.add(Integer.parseInt(valueString[i]));
                }
                valueList.add(list);
                break;
            default:
                valueList.add(valueString[0]);
                break;
            //used for findByTitle

        }
        return super.findBy("Biblidetails." + query, valueList);
    }

    @GET
    @Path("getRecordIdForEdit/{namedQuery}/{values}")
    @Produces({"text/plain"})
    public String getRecordIdForEdit(@PathParam("namedQuery") String searchQuery, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String q = "";
        List<String> result = new ArrayList<>();
        Query query;
        switch (searchQuery) {
            case "editRecIdByRange":
                q = "Select distinct recid from biblidetails where recid BETWEEN " + Integer.parseInt(valueString[0]) + "  AND " + Integer.parseInt(valueString[1]);
                break;

            case "editRecIdByAcc":
                q = "Select distinct recid from location where p852 = '" + values + "'";
                break;

            case "editRecIdByUserName":
                q = "Select distinct recid from location where UserName = '" + values + "'";
                break;
            case "editRecIdByAccDate":
                try {
                    Date date1 = dateFormat.parse(valueString[0]);
                    Date date2 = dateFormat.parse(valueString[1]);
                    q = "Select distinct recid from location where DateofAcq between  '" + new java.sql.Date(date1.getTime()) + "' and '" + new java.sql.Date(date2.getTime()) + "'";
                    System.out.println("qqqq   " + q);
                } catch (ParseException ex) {
                    Logger.getLogger(BiblidetailsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
        }

        query = getEntityManager().createNativeQuery(q);
        result = (List<String>) query.getResultList();
        System.out.println(" result   " + result);
        return StringUtils.join(result, ',');
    }

    @DELETE
    @Path("removeBy/{namedQuery}/{values}")
    public void removeBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch (query) {
            case "deleteByRecID":
                valueList.add(Integer.parseInt(valueString[0]));
                break;
//            case "deleteByPk":  valueList.add(Integer.parseInt(valueString[0]))  ; 
//                                valueList.add(valueString[1]);
//                                valueList.add(valueString[2]);
//                                valueList.add(Integer.parseInt(valueString[3]))  ; 
//                                valueList.add(Integer.parseInt(valueString[4]))  ; 
//                break;

            case "deleteByRecID&tag&sbfld":
                valueList.add(Integer.parseInt(valueString[0]));
                valueList.add(valueString[1]);
                valueList.add(valueString[2]);
                break;
            default:
                valueList.add(valueString[0]);
                //used for RemoveByMemCd, 
                break;
        }
        super.removeBy("Biblidetails." + query, valueList);
    }

    @DELETE
    @Path("deleteByPk/{currentRecId}/{tagSrNo}/{tagArray}/{sbfldSrNo}/{sbfldArray}")
    public void deleteByPk(@PathParam("currentRecId") String recId, @PathParam("tagArray") String tagArray,
            @PathParam("sbfldArray") String sbfldArray, @PathParam("tagSrNo") String tagSrNo, @PathParam("sbfldSrNo") String sbfldSrNo) {

        List<Object> valueList = new ArrayList<>();
        String tagArrayData[] = tagArray.split("~", -1);
        String sbfldArrayData[] = sbfldArray.split("~", -1);
        String tagSrNoArrayData[] = tagSrNo.split("~", -1);
        String sbfldSrNoArrayData[] = sbfldSrNo.split("~", -1);

        for (int i = 0; i < tagArrayData.length; i++) 
        {
            Biblidetails b = new Biblidetails();
            BiblidetailsPK bPk =  new BiblidetailsPK();            
            bPk.setRecID(Integer.parseInt(recId));
            bPk.setTagSrNo(Integer.parseInt(tagSrNoArrayData[i]));
            bPk.setTag(tagArrayData[i]);
            bPk.setSbFldSrNo(Integer.parseInt(sbfldSrNoArrayData[i]));
            bPk.setSbFld(sbfldArrayData[i]);
            b.setBiblidetailsPK(bPk);
            super.remove(b);
          }


        // super.removeBy("Biblidetails.deleteByRecID&tag&sbfld", valueList);
    }

    @DELETE
    @Path("deleteByRecID&tag&sbfld/{currentRecId}/{tagArray}/{sbfldArray}")
//    public void deleteByRecID_tag_sbfld(@QueryParam("currentRecId") String recId, @QueryParam("tagArray") String tagArray,
//            @QueryParam("sbfldArray") String sbfldArray) {
    public void deleteByRecID_tag_sbfld(@PathParam("currentRecId") String recId, @PathParam("tagArray") String tagArray,
            @PathParam("sbfldArray") String sbfldArray) {    

        List<Object> valueList = new ArrayList<>();
        String tagArrayData[] = tagArray.split("~", -1);

        String sbfldArrayData[] = sbfldArray.split("~", -1);

        for (int i = 2; i < tagArrayData.length; i++) //omit i=0&1 because it has no subfield (ie - leader & fxfield)
        {

            valueList.add(Integer.parseInt(recId));
            valueList.add(tagArrayData[i]);
            valueList.add(sbfldArrayData[i]);
            super.removeBy("Biblidetails.deleteByRecID&tag&sbfld", valueList);
            valueList.clear();

        }


        // super.removeBy("Biblidetails.deleteByRecID&tag&sbfld", valueList);
    }
    
    @Context 
    private ServletContext servletContext;
    @GET
    @Path("retrieveIndValues/{header}/{headerVal}")
    @Produces("application/json")
//    public String retrieveIndValues(@QueryParam("header") String header, @QueryParam("headerVal") String headerVal)
//            throws ServletException, IOException {
    public String retrieveIndValues(@PathParam("header") String header, @PathParam("headerVal") String headerVal)
            throws ServletException, IOException {    
        String   indFilePath = servletContext.getRealPath("/WEB-INF");
       
       String jsonData = null;
        File f = new File(indFilePath+"/IndicatorFiles/MARCIndValue.ini");
        Wini ini = new Wini(f);
         System.out.println("header : " + header + "   " + headerVal);
        String entry = ini.get(header, headerVal);
        Map<StringBuffer, StringBuffer> entryMap = new HashMap<StringBuffer, StringBuffer>();
         if (entry != null) {
            String entrySplit[] = entry.split("~");

            for (int i = 2; i < entrySplit.length; i++) {
                // entryList.add(entrySplit[i]);
                String strNum = entrySplit[i];

                StringBuffer num = new StringBuffer(strNum.length() / 2);
                StringBuffer str = new StringBuffer(strNum.length() / 2);
                boolean isDigit = false;
                char ch;
                int begin = 0;
                ch = strNum.charAt(0);

                {
                    num.append(ch);
                }

                str.append(entrySplit[i], 1, entrySplit[i].length());


                entryMap.put(num, str);
            }

            System.out.println("entryMap :.............. " + entryMap);
            jsonData = new Gson().toJson(entryMap);
 
        } else {
            jsonData = "empty";
        }
        return jsonData;
    }

//    @GET
//    @Path("exportRecordsData")
//     public void exportRecordsData(@QueryParam("startRecNo") int startRecNo, @QueryParam("endRecNo") int endRecNo )
//    {
//        List<Object> valueList = new ArrayList<>();
//        valueList.add(startRecNo);
//        valueList.add(endRecNo);
//        List<Biblidetails> biblidetails = null;
//        biblidetails = super.findBy("Biblidetails.findBetweenStartAndEndRecId", valueList);
//        String exportData = null;
//        for(int i=0;i<biblidetails.size();i++)
//        {
//          // exportData =  biblidetails.get(0)
//        }
//    }
//    @GET
//    @Path("checkRecIdExists/{recId}")
//    @Produces("text/plain")
//    public String checkRecIdExists(@PathParam("recId") String recId) 
//    {
//        List<Object> valueList = new ArrayList<>();
//        String output = null;
//        valueList.add(Integer.parseInt(recId));
//        String count = String.valueOf(locationFacadeREST.countBy("countRecId", valueList));
//        if(count.contentEquals("0"))
//              {
//                output= "no";
//              }
//              else
//              {
//                output= "yes";
//              }
//        return output;
//    }
    @DELETE
    @Path("deleteBibliographies/{RecNoFrom}/{RecNoTo}")
    @Produces("text/plain")
    public String deleteBibliographies(@PathParam("RecNoFrom") int RecNoFrom, @PathParam("RecNoTo") int RecNoTo) {
        System.out.println("RecNoFrom......  " + RecNoFrom + "   " + RecNoTo);
        List<Object> valueList = new ArrayList<>();
        valueList.add(RecNoFrom);
        valueList.add(RecNoTo);
        //  List<Biblidetails> biblidetails = null;
        // if(checkRecIdExists())
        removeBy("Biblidetails.deleteBetweenStartAndEndRecId", valueList);
        locationFacadeREST.removeBy("Location.deleteBetweenStartAndEndRecId", valueList);
        biblifilesFacadeREST.removeBy("Biblifiles.deleteBetweenStartAndEndRecId", valueList);
        return "deleted";
    }

    // @GET
    //  @Path("getBibdetailsforGlobalSearch/by/tagAndSubfield/{tag}/{subfield}/searchWord/{searchWord}")
    //  @Produces({"application/xml", "application/json"})
    public List<Biblidetails> getBibdetailsforGlobalSearch(@PathParam("tag") String tag, @PathParam("subfield") String subfield, @PathParam("searchWord") String searchWord) {
        List<Object> valueList = new ArrayList<>();
        List<Biblidetails> getBiblidetails = null;
        List<Biblidetails> getBiblidetailsWithSearchWord = new ArrayList<>();
        valueList.add(tag);
        valueList.add(subfield);
        valueList.add("%" + searchWord + "%");
        getBiblidetails = super.findBy("Biblidetails.findFvalueByTagAndSubfieldAndSearchWord", valueList);
        for (Biblidetails b : getBiblidetails) {
            // System.out.println("b.getFValue() "+b.getFValue());
            if (b.getFValue().matches(".*\\b" + searchWord + "\\b.*")) {
                getBiblidetailsWithSearchWord.add(b);
            }

        }
        return getBiblidetailsWithSearchWord;
    }

//    @PUT
//    @Path("replaceWord/by/tagAndSubfield/{tag}/{subfield}/oldWord/{oldWord}/newWord/{newWord}")
//    @Produces("text/plain")
//    public String replaceWord(@PathParam("tag") String tag,@PathParam("subfield") String subfield,
//    @PathParam("oldWord") String oldWord,@PathParam("newWord") String newWord)
//    {
////        List<Object> valueList = new ArrayList<>();
////        List<Biblidetails>  getBiblidetails = null;
////        List<Biblidetails>  getBiblidetailsWithSearchWord = new ArrayList<>();
////        valueList.add(tag);
////        valueList.add(subfield);
////        valueList.add("%"+oldWord+"%");
////        getBiblidetails = super.findBy("Biblidetails.findFvalueByTagAndSubfieldAndSearchWord", valueList);
////        for(Biblidetails b :getBiblidetails)
////        {
////            System.out.println("b.getFValue() "+b.getFValue());
////            if(b.getFValue().matches(".*\\b"+oldWord+"\\b.*"))
////            {
////                System.out.println("trueeeeeeeeeeeeeeeee");
////                getBiblidetailsWithSearchWord.add(b);
////            }
////            
////        }
//        List<Biblidetails>  getBiblidetailsWithSearchWord = new ArrayList<>();
//        getBiblidetailsWithSearchWord = getBibdetailsforGlobalSearch(tag,subfield,oldWord);
//        for(Biblidetails b : getBiblidetailsWithSearchWord)
//        {
//            b.setFValue(b.getFValue().replace(oldWord, newWord));
//            super.edit(b);
//        }
//      
//        return "word replaced successfully";
//    }
    @PUT
    @Path("replaceWord")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String replaceWord(@FormParam("oldWord") String oldWord, @FormParam("newWord") String newWord, @FormParam("recIDs") String recIDs) {

        List<Biblidetails> getBiblidetailsWithSearchWord = new ArrayList<>();
        String q = "Select * from biblidetails b  where b.RecID IN (" + recIDs + ")";
        Query query = getEntityManager().createNativeQuery(q, Biblidetails.class);
        getBiblidetailsWithSearchWord = (List<Biblidetails>) query.getResultList();
        for (Biblidetails b : getBiblidetailsWithSearchWord) {
            b.setFValue(b.getFValue().replace(oldWord, newWord));
            super.edit(b);
        }

        return "word replaced successfully";
    }

    @GET
    @Path("exportToMarc/{startingRecordNo}/{endingRecordNo}/{isTag852Required}/{isCentralized}/{libraryLocationCode}/{dateOfAcq1}/{dateOfAcq2}")
    @Produces("application/text")
    public Response exportToMarc(@PathParam("fileLocation") String fileLocation, @PathParam("startingRecordNo") String startingRecordNo,
            @PathParam("endingRecordNo") String endingRecordNo, @PathParam("isTag852Required") String tag852, @PathParam("isCentralized") String isCentralized,
            @PathParam("libraryLocationCode") String libraryLocationCode, @PathParam("dateOfAcq1") String dateOfAcq1, @PathParam("dateOfAcq2") String dateOfAcq2) {
        String output = "";
        System.out.println("caled..........");
        List<Object> valueList = new ArrayList<>();

        List<Biblidetails> biblidetailsAll = new ArrayList<>();

        Map<Integer, List<Biblidetails>> biblidetailsMap = new HashMap<>();

        if (isCentralized.equalsIgnoreCase("yes") || isCentralized.equalsIgnoreCase("true") || isCentralized.equalsIgnoreCase("on")) {
            biblidetailsAll = findBy("findByf852AndDateOfAcq", libraryLocationCode + "," + dateOfAcq1 + "," + dateOfAcq2);
        } else {
            valueList.add(Integer.parseInt(startingRecordNo));
            valueList.add(Integer.parseInt(endingRecordNo));
            biblidetailsAll = findBy("findBetweenStartAndEndRecId", startingRecordNo + "," + endingRecordNo);
        }

        // splitting biblidetails by recid and data and creating a map with recId and its data pair
        //1) creating a set  of recorId to eliminate duplicates
        int recIdRows = 0;
        Set<Integer> recIdset = new TreeSet<>();

        for (int i = 0; i < biblidetailsAll.size(); i++) {
            recIdset.add(biblidetailsAll.get(i).getBiblidetailsPK().getRecID());
        }
        //2) converting the set to array
        Integer recIdsetToArray[] = recIdset.toArray(new Integer[recIdset.size()]);

        //3) creating a map to store recid and its corresponding data in a list
        for (int i = 0; i < recIdsetToArray.length; i++) {
            List<Biblidetails> biblidetailsList = new ArrayList<>();

            for (Biblidetails b : biblidetailsAll) {
                if (b.getBiblidetailsPK().getRecID() == recIdsetToArray[i]) {
                    //  biblidetailsMap.put(recIdsetToArray[i],biblidetailsAll);
                    biblidetailsList.add(b);
                }
            }
            biblidetailsMap.put(recIdsetToArray[i], biblidetailsList);
        }

        StringBuilder exportData = new StringBuilder("");

        //4) looping through the map and getting data for each recid 
        for (int a = 0; a < biblidetailsMap.size(); a++) {
            Biblidetails b = new Biblidetails();
            List<Biblidetails> biblidetails = new ArrayList<>();
            biblidetails = biblidetailsMap.get(recIdsetToArray[a]);

            List<String> recIdList = new ArrayList<>();
            List<String> tagList = new ArrayList<>();
            List<String> tagSrNoList = new ArrayList<>();
            List<String> sbfldList = new ArrayList<>();
            List<String> sbfldSrNoList = new ArrayList<>();
            List<String> fvalList = new ArrayList<>();
            List<String> fvallength = new ArrayList<>();
            List<String> indicatorList = new ArrayList<>();

            char uS = 31;  //unitSeperator
            char rS = 30;   //rowSeperator
            char gS = 29;  //groupSeperator

            for (int i = 0; i < biblidetails.size(); i++) {
                recIdList.add(String.valueOf(biblidetails.get(i).getBiblidetailsPK().getRecID()));

                tagList.add(biblidetails.get(i).getBiblidetailsPK().getTag());

                tagSrNoList.add(String.valueOf(biblidetails.get(i).getBiblidetailsPK().getTagSrNo()));

                sbfldList.add(biblidetails.get(i).getBiblidetailsPK().getSbFld());

                sbfldSrNoList.add(String.valueOf(biblidetails.get(i).getBiblidetailsPK().getSbFldSrNo()));

                fvalList.add(biblidetails.get(i).getFValue());

                indicatorList.add(biblidetails.get(i).getInd());
            }
            Query query = getEntityManager().createNativeQuery(b.getFine());
            int result = query.executeUpdate();
            StringBuilder recordData = new StringBuilder("");
            // recordData for tag 001
            recordData = recordData.append(rS).append(recIdList.get(0));
            // recordData for tag 008
            recordData = recordData.append(rS).append(fvalList.get(1));
            // record data for remaining tags
            String indicator = "";
            String previousTagSrno = "";
            String nextTagSrno = "";

            String previousTag = "";
            String nextTag = "";
            for (int r = 2; r < fvalList.size(); r++) {
                previousTagSrno = tagSrNoList.get(r - 1);
                nextTagSrno = tagSrNoList.get(r);

                previousTag = tagList.get(r - 1);
                nextTag = tagList.get(r);

                System.out.println("....." + previousTagSrno + " " + nextTagSrno + " " + previousTag + "  " + nextTag);
                // if previous tag  & Srno equals nxt tag & srno then add unitSeparator(uS) else add rowSeparator (rS)
                if ((previousTagSrno.equals(nextTagSrno)) && (previousTag.equals(nextTag))) {
                    recordData = recordData.append(uS).append(sbfldList.get(r)).append(fvalList.get(r));
                } else {

                    indicator = (indicatorList.get(r) == null || indicatorList.get(r).contentEquals("")) ? "  " : indicatorList.get(r);
                    recordData = recordData.append(rS).append(indicator).append(uS).append(sbfldList.get(r)).append(fvalList.get(r));
                }
            }

            // for tag 852

            if (tag852.equalsIgnoreCase("yes") || tag852.equalsIgnoreCase("true") || tag852.equalsIgnoreCase("on")) {
                List<Location> locationAll = new ArrayList<>();
                if (isCentralized.equalsIgnoreCase("yes") || isCentralized.equalsIgnoreCase("true")) {
                    locationAll = locationFacadeREST.findBy("findByf852AndDateOfAcq", libraryLocationCode + "," + dateOfAcq1 + "," + dateOfAcq2);
                } else {
                    System.out.println(".........  " + startingRecordNo + "," + endingRecordNo);
                    locationAll = locationFacadeREST.findBy("findBetweenStartAndEndRecId", startingRecordNo + "," + endingRecordNo);
                }

                Map<Integer, List<Location>> locationMap = new HashMap<>();
                for (int i = 0; i < recIdsetToArray.length; i++) {
                    List<Location> locationList = new ArrayList<>();

                    for (Location l : locationAll) {
                        if (l.getLocationPK().getRecID() == recIdsetToArray[i]) {
                            //  biblidetailsMap.put(recIdsetToArray[i],biblidetailsAll);
                            locationList.add(l);
                        }
                    }
                    locationMap.put(recIdsetToArray[i], locationList);
                }

                List<Location> locations = new ArrayList<>();
                locations = locationMap.get(recIdsetToArray[a]);

                List<String> t852List = new ArrayList<>();
                List<String> p852List = new ArrayList<>();
                List<String> a852List = new ArrayList<>();
                List<String> b852List = new ArrayList<>();
                List<String> k852List = new ArrayList<>();
                List<String> f852List = new ArrayList<>();
                List<String> c852List = new ArrayList<>();
                List<String> m852List = new ArrayList<>();
                List<String> locIndicatorList = new ArrayList<>();

                for (int i = 0; i < locations.size(); i++) {
                    t852List.add(String.valueOf(locations.get(i).getT852()));
                    p852List.add(String.valueOf(locations.get(i).getLocationPK().getP852()));
                    a852List.add(locations.get(i).getA852() == null ? "" : String.valueOf(locations.get(i).getA852()));
                    b852List.add(locations.get(i).getB852() == null ? "" : String.valueOf(locations.get(i).getB852()));
                    k852List.add(locations.get(i).getK852() == null ? "" : String.valueOf(locations.get(i).getK852()));
                    f852List.add(locations.get(i).getF852() == null ? "" : String.valueOf(locations.get(i).getF852()));
                    c852List.add(locations.get(i).getC852() == null ? "" : String.valueOf(locations.get(i).getC852()));
                    m852List.add(locations.get(i).getM852() == null ? "" : String.valueOf(locations.get(i).getM852()));
                    locIndicatorList.add(locations.get(i).getInd() == null ? "  " : String.valueOf(locations.get(i).getInd()));
                }

                for (int l = 0; l < locations.size(); l++) {
                    recordData = recordData.append(rS).append(locIndicatorList.get(l)).append(uS).append("t" + t852List.get(l)).append(uS).append("p" + p852List.get(l)).append(uS).append("a" + a852List.get(l)).append(uS).append("b" + b852List.get(l)).append(uS);
                    recordData.append("k" + k852List.get(l)).append(uS).append("f" + f852List.get(l)).append(uS).append("c" + c852List.get(l)).append(uS).append("m" + m852List.get(l));

                    tagSrNoList.add(String.valueOf(l + 1));  // add tag 852 in taglist
                    tagList.add("852");  // add tag 852 in taglist
                }
            }
            //  split record data and store in array to get length and position..
            String recordDataArray[] = splitStringByChar(recordData, rS);

            // creating directory data  
            StringBuilder directoryData = new StringBuilder("");
            // for tag 000  char : 0 - 23   // represents leader
            // StringBuilder getLeader = new StringBuilder(fvalList.get(0));
            //  getLeader.append(" ", 0, 0); 
            directoryData = directoryData.append(fvalList.get(0));
            // for tag 001     // represents record Id
            String recIdlength = String.valueOf((recIdList.get(0).length() + 1));
            directoryData = directoryData.append("001").append(setZeroPrefix(recIdlength, 4)).append("00000");

            // for remaining tags
            String fvalPosition = setZeroPrefix(recIdlength, 5);
            int count = 1;
            previousTagSrno = "";
            nextTagSrno = "";

            previousTag = "";
            nextTag = "";

            for (int j = 1; j < tagList.size(); j++) {

                previousTagSrno = tagSrNoList.get(j - 1);
                nextTagSrno = tagSrNoList.get(j);

                previousTag = tagList.get(j - 1);
                nextTag = tagList.get(j);

                if (!((previousTagSrno.equals(nextTagSrno)) && (previousTag.equals(nextTag)))) {
                    count = count + 1;
                    String fvalength = String.valueOf(recordDataArray[count].length() + 1);
                    directoryData = directoryData.append(tagList.get(j)).append(setZeroPrefix(fvalength, 4)).append(fvalPosition);
                    int nextfval = (recordDataArray[count].length() + 1) + Integer.parseInt(fvalPosition);
                    System.out.println("nextfval " + nextfval);
                    fvalPosition = setZeroPrefix(String.valueOf(nextfval), 5);
                    System.out.println("fvalPosition  " + fvalPosition);
                }
            }

            // replacing dynamic values in leader
            int exportDataLength = directoryData.length() + recordData.length() + 2;
            directoryData.replace(0, 5, setZeroPrefix(String.valueOf(exportDataLength), 5));  // for char 0-4
            directoryData.replace(7, 8, "m");  // for char 7
            directoryData.replace(9, 10, "a");  // for char 9
            int directoryDataLength = directoryData.length() + 1;
            directoryData.replace(12, 17, setZeroPrefix(String.valueOf(directoryDataLength), 5));  // for char 12-16
            directoryData.replace(17, 18, "u");  // for char 17
            directoryData.replace(18, 19, "a");  // for char 18
            // directoryData.insert(19, " "); // FOR CHAR 19
            // for char 20 - 23  
//        directoryData.insert(20, "4"); // FOR CHAR 20
//        directoryData.insert(21, "5"); // FOR CHAR 21
//        directoryData.insert(22, "0"); // FOR CHAR 22
//        directoryData.insert(23, "0"); // FOR CHAR 23
//        

            exportData = exportData.append(directoryData.append(recordData).append(rS).append(gS));
        }
        System.out.println("exportData  " + exportData);




        FileWriter fr = null;
        BufferedWriter br = null;
        File file1 = new File("c:\\marcExportData.txt");
        //    ResponseBuilder response = null;
        try {

            fr = new FileWriter(file1);
            br = new BufferedWriter(fr);
            //  for(String data : dataWithNewLine){
            br.write(exportData.toString());
            //   output = "Data exported successfully";



        } catch (Exception e) {
           // throw new DataException("File not found.......");
        } finally {
            try {
                br.close();
                fr.close();
            } catch (Exception e) {
                System.out.println("eeeeeeeeeeeeeeeeeeeeee  " + e.getMessage() + "    " + e.getClass());
                output = "File not found..";
              //  throw new DataException("File not found..");
                //e.printStackTrace();
            }
        }
        ResponseBuilder response = Response.ok((Object) file1);
        response.header("Content-Disposition", "attachment; filename=marcExportData.txt");
        file1.deleteOnExit();
        return response.build();
    }

    public static String[] splitStringByChar(StringBuilder strToSplit, char delimiter) {
        ArrayList<String> arr = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strToSplit.length(); i++) {
            char at = strToSplit.charAt(i);
            if (at == delimiter) {
                arr.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append(at);
            }
        }
        arr.add(sb.toString());
        return arr.toArray(new String[0]);
    }

    public int inArray(String data, Object dataArray[]) {
        int count = dataArray.length;
        for (int i = 0; i < count; i++) {//alert(haystack[i]);
            if (dataArray[i].equals(data)) {
                return i;
            }
        }
        return -1;
    }

    public String setZeroPrefix(String flen, int totalcount) {
        int totallength = totalcount;
        String new_flen = "";
        int diff = totallength - flen.length();
        for (int i = 1; i <= diff; i++) {
            new_flen = new_flen + "0";
        }
        new_flen = new_flen + flen;
        // int fvalue = Integer.parseInt(new_flen);
        return new_flen;
    }

    // import from marc to database
    @POST
    @Path("importFromMarc/{type}/{considerTag852}")
    @Consumes({"application/xml", "application/json", "multipart/form-data", "application/x-www-form-urlencoded"})
    //@Produces("text/plain")
    //  public String importFromMarc(@FormDataParam("fileLocation") File fileLocation, @PathParam("type") String type) throws FileNotFoundException {
    public String importFromMarc(@FormDataParam("fileLocation") File fileLocation, @PathParam("type") String type, @PathParam("considerTag852") String considerTag852) throws FileNotFoundException {

        BufferedReader br = new BufferedReader(new FileReader(fileLocation));
        BufferedReader br1 = new BufferedReader(new FileReader(fileLocation));
        LineNumberReader lnr = new LineNumberReader(br);
        String s = "";
        try {
            System.out.println("fileLocation  .. " + fileLocation);
            String contentLine = "";
            int linenumber = 0;

            while (lnr.readLine() != null) {
                linenumber++;
            }
            System.out.println("Total number of lines : " + linenumber);
            for (int i = 1; i < linenumber; i++) {
                if (i > 4) {
                    contentLine = contentLine + br1.readLine();
                } else {
                    br1.readLine();
                }
            }
            // File file = fileLocation;
            //  FileReader fr = new FileReader(fileLocation);
            // String s = "";
            s = contentLine;
            String output = "";
            char uS = 31;  //unitSeperator
            char rS = 30;   //rowSeperator
            char gS = 29;  //groupSeperator

            //     try {
//            int data = fr.read();
//            while (data != -1) {
//                //do something with data...
//                char c = (char) data;
//                s = s + c;
//                data = fr.read();
//            }


            System.out.println("ssss11  " + s);
            String s_Array[] = s.split("" + gS + "");
            System.out.println("s_Array[] ........... " + s_Array.length);
            for (int m = 0; m < s_Array.length; m++) // looping for multiple records
            {
                StringBuilder leader = new StringBuilder("");
                StringBuilder directoryData = new StringBuilder("");
                StringBuilder recordData = new StringBuilder("");
                /*  filedata = leader + directorydata+ recorddata*/
                leader = leader.append(s_Array[m].substring(0, 24));  // char 0-23 belongs to leader
                System.out.println("leader " + leader);

                int directoryDataLength = Integer.parseInt(s_Array[m].substring(12, 17)); // char 12-16 shows base address of recorddata

                directoryData = directoryData.append(s_Array[m].substring(24, directoryDataLength - 1));  // (base address - 1 ) gives length of directory data
                System.out.println("directoryData " + directoryData);
                int totallength = Integer.parseInt(s_Array[m].substring(0, 5)); // char 0-4 shows total length
                recordData = recordData.append(s_Array[m].substring(directoryDataLength, (totallength - 2)));

                // each group of 12 chars in directory data shows tas,row length , row address
                int intialTagCount = 0;  // char 0-2 in directory data shows tag
                int intialRowLengthCount = 3;  // char 3-6 in directory data shows row length
                int intialRowAddressCount = 7; // char 7-11 in directory data shows row address
                int finalFvalAddressCount = 12;

                List<String> tagList = new ArrayList<>();
                // List<String> rowLengthList = new ArrayList<>();
                // List<String> rowAddressList = new ArrayList<>();

                for (int i = 0; i < directoryData.length() / 12; i++) {
                    tagList.add(directoryData.substring(intialTagCount, intialRowLengthCount));
                    // rowLengthList.add(directoryData.substring(intialRowLengthCount, intialRowAddressCount));
                    // rowAddressList.add(directoryData.substring(intialRowAddressCount, finalFvalAddressCount));
                    intialTagCount = intialTagCount + 12;
                    intialRowLengthCount = intialRowLengthCount + 12;
                    intialRowAddressCount = intialRowAddressCount + 12;
                    finalFvalAddressCount = finalFvalAddressCount + 12;
                }

                String rowsArray[] = splitStringByChar(recordData, rS); // splitting record data by rS to get rows
                System.out.println("rowsArray  " + rowsArray);
                for (int j = 0; j < rowsArray.length; j++) {
                    System.out.println("rowsArray " + rowsArray[j]);
                }
                //Biblidetails / Authority
                List<Integer> tagSrNoList = new ArrayList<>();  // to store tagSrNo
                List<String> finalTagList = new ArrayList<>();  // to store tag
                List<String> ind = new ArrayList<>();           // to store indicator
                List<String> sbfld = new ArrayList<>();            // to store subfield
                List<Integer> sbfldSrNoList = new ArrayList<>(); // to store sbFldSrNo
                List<String> fval = new ArrayList<>();          // to store fvalue

                List<String> TgList_sbfldSrNo_sbfld = new ArrayList<>();

                //location
                List<String> locInd = new ArrayList<>();
                List<String> locInd1 = new ArrayList<>();
                List<String> t852 = new ArrayList<>();
                List<String> p852 = new ArrayList<>();
                List<String> a852 = new ArrayList<>();
                List<String> b852 = new ArrayList<>();
                List<String> k852 = new ArrayList<>();
                List<String> f852 = new ArrayList<>();
                List<String> c852 = new ArrayList<>();
                List<String> m852 = new ArrayList<>();
                List<String> issueRestricted = new ArrayList<>();
                List<String> price = new ArrayList<>();
                List<String> status = new ArrayList<>();
                List<String> dateOfAcq = new ArrayList<>();
                List<String> supplierList = new ArrayList<>();
                List<String> locNulldata = new ArrayList<>();
                String supplier = null;

                // for tag 000 i.e leader
                finalTagList.add("000");
                ind.add("");
                sbfldSrNoList.add(0);
                sbfld.add("");
                fval.add(leader.toString());

                int getLocationTagIndex = tagList.indexOf("852");  // get index for tag 852 
                for (int j = 0; j < rowsArray.length; j++) {
                    String unitArray[] = rowsArray[j].split("" + uS + "");  // split row wise data by unit seperator

                    if (unitArray.length == 1) // for rows without any unit separator
                    {
                        finalTagList.add(tagList.get(j));
                        ind.add("");
                        sbfldSrNoList.add(0);
                        sbfld.add("");
                        fval.add(unitArray[0]);
                    }

                    int counterSbFld = 1;
                    String previousSbFld = "";
                    String currentSbFld = "";

                    for (int k = 1; k < unitArray.length; k++) {

                        if (getLocationTagIndex != -1) {  // if getLocationTagIndex = -1 then tag 852 does not exits
                            if (j < getLocationTagIndex) {
                                finalTagList.add(tagList.get(j));
                                if (k == 1) {
                                    ind.add((unitArray[0]));
                                } else {
                                    ind.add("");
                                }
                                currentSbFld = unitArray[k].substring(0, 1);
                                if (currentSbFld.equals(previousSbFld)) { // if( previous and current sbFld are equal ,increment the counter
                                    counterSbFld = counterSbFld + 1;
                                }

                                sbfldSrNoList.add(counterSbFld);
                                sbfld.add(unitArray[k].substring(0, 1));

                                /*--to set supplier tag in location*/
                                if (tagList.get(j).contentEquals("260") && unitArray[k].substring(0, 1).contentEquals("b")) {
                                    supplier = unitArray[k].substring(1, unitArray[k].length());
                                }
                                /*---------------------------------*/
                                fval.add(unitArray[k].substring(1, unitArray[k].length()));

                            } else {
                                String sbVal = unitArray[k].substring(0, 1);
                                String lVal = unitArray[k].substring(1, unitArray[k].length());
                                switch (sbVal) {
                                    case "t":
                                        t852.add(lVal);
                                        locInd.add(unitArray[0]);
                                        locInd1.add("");
                                        supplierList.add(supplier);
                                        issueRestricted.add("N");
                                        price.add("0");
                                        status.add("AV");
                                        dateOfAcq.add(formatter1.format(formatter.parse(new Date().toString())));
                                        locNulldata.add("");
                                        break;
                                    case "p":
                                        p852.add(lVal);
                                        break;
                                    case "a":
                                        a852.add(lVal);
                                        break;
                                    case "b":
                                        b852.add(lVal);
                                        break;
                                    case "k":
                                        k852.add(lVal);
                                        break;
                                    case "f":
                                        f852.add(lVal);
                                        break;
                                    case "c":
                                        c852.add(lVal);
                                        break;
                                    case "m":
                                        m852.add(lVal);
                                        break;

                                }
                            }
                        } else {
                            System.out.println("tagList " + tagList.get(j) + "  " + j);
                            finalTagList.add(tagList.get(j));

                            if (k == 1) {
                                ind.add((unitArray[0]));
                            } else {
                                ind.add("");
                            }
                            currentSbFld = unitArray[k].substring(0, 1);

                            if (currentSbFld.equals(previousSbFld)) { // if( previous and current sbFld are equal ,increment the counter
                                counterSbFld = counterSbFld + 1;
                            }

                            sbfldSrNoList.add(counterSbFld);
                            sbfld.add(unitArray[k].substring(0, 1));
                            previousSbFld = unitArray[k].substring(0, 1);
                            fval.add(unitArray[k].substring(1, unitArray[k].length()));
                        }
                    }
                }

                // for setting tag sr no..
                int counter = 1;
                tagSrNoList.add(1);  // setting  SrNo for first occurence of first  tag  as 1


                for (int i = 0; i < finalTagList.size() - 1; i++) {
                    TgList_sbfldSrNo_sbfld.add(finalTagList.get(i) + "_" + sbfldSrNoList.get(i) + "_" + sbfld.get(i));
                    // System.out.println("tag   "+finalTagList.get(i)+" .......  "+finalTagList.get(i+1));
                    // System.out.println("sbfld sr  "+sbfldSrNoList.get(i)+" .......  "+sbfldSrNoList.get(i+1));
                    // System.out.println("sbfld   "+sbfld.get(i)+" .......  "+sbfld.get(i+1));
                    // if (finalTagList.get(i).equals(finalTagList.get(i + 1)) && sbfld.get(i).equals(sbfld.get(i + 1))
                    if (TgList_sbfldSrNo_sbfld.contains(finalTagList.get(i + 1) + "_" + sbfldSrNoList.get(i + 1) + "_" + sbfld.get(i + 1))) //&& sbfldSrNoList.get(i) == sbfldSrNoList.get(i + 1)) 
                    {
                        counter = counter + 1;
                        tagSrNoList.add(counter);
                    } else {
                        counter = 1;
                        tagSrNoList.add(1);
                    }
                }

                System.out.println("tagSrNoList " + tagSrNoList);
                String listString = StringUtils.join(tagSrNoList, '~');
                System.out.println("listString " + listString);
                System.out.println("tag " + finalTagList);
                String finalTagListStr = StringUtils.join(finalTagList, '~');
                System.out.println("finalTagListStr " + finalTagListStr);
                System.out.println("ind " + ind);
                System.out.println("sbSr " + sbfldSrNoList);
                System.out.println("sb " + sbfld);
                System.out.println("fval " + fval);
                System.out.println("locInd " + locInd);
                System.out.println("t852 " + t852);
                System.out.println("p852 " + p852);
                System.out.println("a852 " + a852);
                System.out.println("b852 " + b852);
                System.out.println("k852 " + k852);
                System.out.println("f852 " + f852);
                System.out.println("c852 " + c852);
                System.out.println("m852 " + m852);
                System.out.println("supplierList " + supplierList);

                System.out.println("tagSrNoList " + tagSrNoList.size());
                System.out.println("tag " + finalTagList.size());
                System.out.println("ind " + ind.size());
                System.out.println("sbSr " + sbfldSrNoList.size());
                System.out.println("sb " + sbfld.size());
                System.out.println("fval " + fval.size());
                System.out.println("locInd " + locInd.size());
                System.out.println("t852 " + t852.size());
                System.out.println("p852 " + p852.size());
                System.out.println("a852 " + a852.size());
                System.out.println("b852 " + b852.size());
                System.out.println("k852 " + k852.size());
                System.out.println("f852 " + f852.size());
                System.out.println("c852 " + c852.size());
                System.out.println("m852 " + m852.size());

                //   fr.close();
                //  }
                Biblidetails b = null;
                if (type.equalsIgnoreCase("bibliographic")) {
                    /* to save biblidtetails  */
                    b = create(StringUtils.join(tagSrNoList, '~'), StringUtils.join(finalTagList, '~'), StringUtils.join(sbfldSrNoList, '~'), StringUtils.join(sbfld, '~'), StringUtils.join(ind, '~'), StringUtils.join(fval, '~'));
                }
                if (type.equalsIgnoreCase("authority")) {
                    /* to save authority*/
                    authoritybiblidetailsFacadeREST.create(StringUtils.join(tagSrNoList, '~'), StringUtils.join(finalTagList, '~'), StringUtils.join(sbfldSrNoList, '~'), StringUtils.join(sbfld, '~'), StringUtils.join(ind, '~'), StringUtils.join(fval, '~'));
                }

                /* to save location  */
                if (getLocationTagIndex != -1 && (considerTag852.equalsIgnoreCase("true") || considerTag852.equalsIgnoreCase("yes") || considerTag852.equalsIgnoreCase("on"))) {
                    locationFacadeREST.createLocation(String.valueOf(b.getBiblidetailsPK().getRecID()), StringUtils.join(t852, ','), StringUtils.join(locInd, ','), StringUtils.join(locInd1, ','), StringUtils.join(p852, ','), StringUtils.join(b852, ','), StringUtils.join(f852, ','), StringUtils.join(locNulldata, ','),
                            StringUtils.join(supplierList, ','), StringUtils.join(locNulldata, ','), StringUtils.join(k852, ','), StringUtils.join(a852, ','), StringUtils.join(locNulldata, ','), StringUtils.join(locNulldata, ','), StringUtils.join(status, ','),
                            StringUtils.join(m852, ','), StringUtils.join(c852, ','), StringUtils.join(locNulldata, ','), StringUtils.join(price, ','), StringUtils.join(locNulldata, ','), StringUtils.join(dateOfAcq, ','), StringUtils.join(issueRestricted, ','));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            try {
//                //     br.close();
//               // fr.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            System.out.println("ssss1222...  " + s);
            return s;
        }
    }

    /*import from marcXML*/
    public static final String UTF8_BOM = "\uFEFF";
    private final int[] BYTE_ORDER_MARK = {239, 187, 191};

    @POST
    @Path("importFromMarcXml/{isTag852Required}")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded", "multipart/form-data"})
    public String importFromMarcXml(@FormDataParam("fileLocation") File fileLocation, @PathParam("isTag852Required") String isTag852Required) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(fileLocation));
        BufferedReader br1 = new BufferedReader(new FileReader(fileLocation));
        LineNumberReader lnr = new LineNumberReader(br);
        try {
            System.out.println("fileLocation  .. " + fileLocation);
            String contentLine = "";
            int linenumber = 0;

            while (lnr.readLine() != null) {
                linenumber++;
            }
            System.out.println("Total number of lines : " + linenumber);
            for (int i = 1; i < linenumber; i++) {
                if (i > 4) {
                    contentLine = contentLine + br1.readLine();
                } else {
                    br1.readLine();
                }
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource();
            // System.out.println("contentLine111 .......... "+contentLine); 
            contentLine = contentLine.replaceAll("[^\\p{ASCII}]", "");
            //  System.out.println("After removing non ASCII chars:");

            //  System.out.println("contentLine 222.......... "+contentLine);  
            is.setCharacterStream(new StringReader(contentLine));
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("marc:record");
            System.out.println("----------------------------");

            //Biblidetails 
            List<String> tagSrNo = new ArrayList<>();  // to store tagSrNo
            List<String> tag = new ArrayList<>();  // to store tag
            List<String> ind = new ArrayList<>();           // to store indicator
            List<String> sbfld = new ArrayList<>();            // to store subfield
            List<String> sbfldSrNo = new ArrayList<>(); // to store sbFldSrNo
            List<String> fval = new ArrayList<>();          // to store fvalue
            //location
            List<String> locInd1 = new ArrayList<>();
            List<String> locInd2 = new ArrayList<>();
            List<String> t852 = new ArrayList<>();
            List<String> p852 = new ArrayList<>();
            List<String> a852 = new ArrayList<>();
            List<String> b852 = new ArrayList<>();
            List<String> k852 = new ArrayList<>();
            List<String> f852 = new ArrayList<>();
            List<String> c852 = new ArrayList<>();
            List<String> m852 = new ArrayList<>();
            List<String> status = new ArrayList<>();
            List<String> currency = new ArrayList<>();
            List<String> issueRestricted = new ArrayList<>();
            List<String> dateOfAcq = new ArrayList<>();
            List<String> material = new ArrayList<>();
            List<String> locNulldata = new ArrayList<>();


            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                /*--------------- for leader ----------------*/
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    tagSrNo.add("1");
                    tag.add("000");
                    ind.add("");
                    sbfldSrNo.add("0");
                    sbfld.add("");
                    fval.add(eElement.getElementsByTagName("marc:leader").item(0).getTextContent());

                    System.out.println("tag leader.. " + tag);
                    System.out.println("tagSrNo leader.. " + tagSrNo);
                    System.out.println("ind leader.. " + ind);
                    System.out.println("sbfldSrNo leader.. " + sbfldSrNo);
                    System.out.println("sbfld leader.. " + sbfld);
                    System.out.println("fval leader.. " + fval);
                    /*-------------------------------------------*/

                    /*---------------  for control field ----------------*/
                    NodeList nControlFieldList = eElement.getElementsByTagName("marc:controlfield");
                    for (int cf = 0; cf < nControlFieldList.getLength(); cf++) {
                        tagSrNo.add("1");
                        tag.add(nControlFieldList.item(cf).getAttributes().getNamedItem("tag").getTextContent());
                        ind.add("");
                        sbfldSrNo.add("0");
                        sbfld.add("");
                        fval.add(eElement.getElementsByTagName("marc:controlfield").item(cf).getTextContent());


                    }
                    System.out.println("tag controlfield.. " + tag);
                    System.out.println("tagSrNo controlfield.. " + tagSrNo);
                    System.out.println("ind controlfield.. " + ind);
                    System.out.println("sbfldSrNo controlfield.. " + sbfldSrNo);
                    System.out.println("sbfld controlfield.. " + sbfld);
                    System.out.println("fval controlfield.. " + fval);

                    /*-------------------------------------------*/

                    /*---------------  for data field ----------------*/
                    NodeList nDataFieldList = eElement.getElementsByTagName("marc:datafield");

                    int tagSrNoCounter = 1;
                    String previousTag = "0";
                    String currentTag = "0";
                    for (int df = 0; df < nDataFieldList.getLength(); df++) {
                        Node node1 = nDataFieldList.item(df);
                        if (node1.getNodeType() == node1.ELEMENT_NODE) {

                            Element datafield = (Element) node1;
                            NodeList nSubFieldList = datafield.getElementsByTagName("marc:subfield");

                            String tagval = nDataFieldList.item(df).getAttributes().getNamedItem("tag").getTextContent();
                            String ind1 = nDataFieldList.item(df).getAttributes().getNamedItem("ind1").getTextContent();
                            String ind2 = nDataFieldList.item(df).getAttributes().getNamedItem("ind2").getTextContent();
                            currentTag = tagval;
                            if (currentTag.contentEquals(previousTag)) {
                                tagSrNoCounter++;
                            } else {
                                tagSrNoCounter = 1;
                            }
                            previousTag = tagval;

                            List<String> tagSrNo_tag_sbfld = new ArrayList<>();  // used to set sbfld SrNo

                            for (int sf = 0; sf < nSubFieldList.getLength(); sf++) {

                                if (!tagval.equals("852")) {

                                    tagSrNo.add(String.valueOf(tagSrNoCounter));
                                    String sbfldval = nSubFieldList.item(sf).getAttributes().getNamedItem("code").getTextContent();
                                    Node node2 = nSubFieldList.item(sf);
                                    tagSrNo_tag_sbfld.add(tagSrNoCounter + "_" + tagval + "_" + sbfldval);

                                    int sbfldOccurrences = Collections.frequency(tagSrNo_tag_sbfld, tagSrNoCounter + "_" + tagval + "_" + sbfldval);

                                    sbfldSrNo.add(String.valueOf(sbfldOccurrences));
                                    sbfld.add(sbfldval);
                                    tag.add(tagval);

                                    ind.add(ind1 + ind2);
                                    fval.add(nSubFieldList.item(sf).getTextContent());
                                } else {
                                    String sbfldval = nSubFieldList.item(sf).getAttributes().getNamedItem("code").getTextContent();
                                    switch (sbfldval) {
                                        case "t":
                                            t852.add(nSubFieldList.item(sf).getTextContent());
                                            locInd1.add(ind1);
                                            locInd2.add(ind2);
                                            dateOfAcq.add(formatter1.format(formatter.parse(new Date().toString())));
                                            locNulldata.add("");
                                            status.add("AV");
                                            issueRestricted.add("N");
                                            currency.add("INR");
                                            break;
                                        case "3":
                                            material.add(nSubFieldList.item(sf).getTextContent());
                                            break;
                                        case "p":
                                            p852.add(nSubFieldList.item(sf).getTextContent());
                                            break;
                                        case "a":
                                            a852.add(nSubFieldList.item(sf).getTextContent());
                                            break;
                                        case "b":
                                            b852.add(nSubFieldList.item(sf).getTextContent());
                                            break;
                                        case "k":
                                            k852.add(nSubFieldList.item(sf).getTextContent());
                                            break;
                                        case "f":
                                            f852.add(nSubFieldList.item(sf).getTextContent());
                                            break;
                                        case "c":
                                            c852.add(nSubFieldList.item(sf).getTextContent());
                                            break;
                                        case "m":
                                            m852.add(nSubFieldList.item(sf).getTextContent());
                                            break;
                                    }
                                }
                            }

                        }
                    }
                    /*-------------------------------------------*/
                }
            }

            /* to save biblidtetails  */
            Biblidetails b = create(StringUtils.join(tagSrNo, '~'), StringUtils.join(tag, '~'), StringUtils.join(sbfldSrNo, '~'), StringUtils.join(sbfld, '~'), StringUtils.join(ind, '~'), StringUtils.join(fval, '~'));

            /* to save location  */
            if (isTag852Required.equalsIgnoreCase("yes") || isTag852Required.equalsIgnoreCase("true") || isTag852Required.equalsIgnoreCase("on")) {
                locationFacadeREST.createLocation(String.valueOf(b.getBiblidetailsPK().getRecID()), StringUtils.join(t852, ','), StringUtils.join(locInd1, ','), StringUtils.join(locInd2, ','), StringUtils.join(p852, ','), StringUtils.join(b852, ','), StringUtils.join(f852, ','), StringUtils.join(locNulldata, ','),
                        StringUtils.join(locNulldata, ','), StringUtils.join(material, ','), StringUtils.join(k852, ','), StringUtils.join(a852, ','), StringUtils.join(locNulldata, ','), StringUtils.join(locNulldata, ','), StringUtils.join(status, ','),
                        StringUtils.join(m852, ','), StringUtils.join(c852, ','), StringUtils.join(currency, ','), StringUtils.join(locNulldata, ','), StringUtils.join(locNulldata, ','), StringUtils.join(dateOfAcq, ','), StringUtils.join(issueRestricted, ','));
            }
            System.out.println("tag data.. " + tag);
            System.out.println("tagSrNo data.. " + tagSrNo);
            System.out.println("ind data.. " + ind);
            System.out.println("sbfldSrNo data.. " + sbfldSrNo);
            System.out.println("sbfld data.. " + sbfld);
            System.out.println("fval data.. " + fval);
            System.out.println("locInd " + locInd1);
            System.out.println("locInd " + locInd2);
            System.out.println("t852 " + t852);
            System.out.println("p852 " + p852);
            System.out.println("a852 " + a852);
            System.out.println("b852 " + b852);
            System.out.println("k852 " + k852);
            System.out.println("f852 " + f852);
            System.out.println("c852 " + c852);
            System.out.println("m852 " + m852);
            System.out.println("material  " + material);
            System.out.println("-------------------- : ");
            System.out.println("tag data.. " + tag.size());
            System.out.println("tagSrNo data.. " + tagSrNo.size());
            System.out.println("ind data.. " + ind.size());
            System.out.println("sbfldSrNo data.. " + sbfldSrNo.size());
            System.out.println("sbfld data.. " + sbfld.size());
            System.out.println("fval data.. " + fval.size());
        } catch (Exception e) {
            e.printStackTrace();
            return "Data not Imported";
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                br1.close();
                lnr.close();

            } catch (IOException ioe) {
                System.out.println("Error in closing the BufferedReader");
            }
        }
        return "Data Imported Successfully";
    }

    
    @POST
    @Path("importSRUMarcXml")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded", "multipart/form-data"})
    public String importFromSRUtoSOULMarcXml(@FormDataParam("filexmldata") String xmldata) throws FileNotFoundException {
//        BufferedReader br = new BufferedReader(new FileReader(fileLocation));
//        BufferedReader br1 = new BufferedReader(new FileReader(fileLocation));
//        LineNumberReader lnr = new LineNumberReader(br);
        try {
            System.out.println("XML DATA  .. " + xmldata);
//            System.out.println("fileLocation  .. " + fileLocation);
//            String contentLine = "";
//            int linenumber = 0;
//
//            while (lnr.readLine() != null) {
//                linenumber++;
//            }
//            System.out.println("Total number of lines : " + linenumber);
//            for (int i = 1; i < linenumber; i++) {
//                if (i > 4) {
//                    contentLine = contentLine + br1.readLine();
//                } else {
//                    br1.readLine();
//                }
//            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            InputSource is = new InputSource();
//            // System.out.println("contentLine111 .......... "+contentLine); 
//            contentLine = contentLine.replaceAll("[^\\p{ASCII}]", "");
//            //  System.out.println("After removing non ASCII chars:");
//
//            //  System.out.println("contentLine 222.......... "+contentLine);  
//            is.setCharacterStream(new StringReader(contentLine));
            Document doc = dBuilder.parse(new InputSource(new StringReader(xmldata)));
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("record");
            System.out.println("----------------------------");
             Map<String,String> repratTaglist = new HashMap<>();
              Map<String,String> tagnsubflist = new HashMap<>();
                Map<String,String> tagsbfncountlist = new HashMap<>();
                
            //main record loop one time only..
            for (int temp = 0; temp < nList.getLength(); temp++) {
                
                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;
                NodeList nDataFieldList = eElement.getElementsByTagName("datafield");
                
                repratTaglist.clear();
               List<String> Taglist = new ArrayList<>();
               
                // loop for tag in list
                for (int df = 0; df < nDataFieldList.getLength(); df++) {
                    String tagval = nDataFieldList.item(df).getAttributes().getNamedItem("tag").getTextContent();
                    Taglist.add(tagval);
                }
                System.out.println("TAg list "+ Taglist);
                //check tag count more then 1.
                for(int tl=0;tl<Taglist.size();tl++){
                    String maintag = Taglist.get(tl);
                    int count = Collections.frequency(Taglist, maintag);
                    if(count>1){
                        if(!repratTaglist.containsKey(maintag)){
                            System.out.println("Tag : "+ maintag+"  Count : "+count);
                            repratTaglist.put(maintag, String.valueOf(count));
                        }
                    }    
                }
                
                List<String> subfieldlist = new ArrayList<>();
                 List<String> codelist = new ArrayList<>();
                tagnsubflist.clear();
               tagsbfncountlist.clear();
                // loop for tag & sub field
                for (int df = 0; df < nDataFieldList.getLength(); df++) {
                    
                    String tagval = nDataFieldList.item(df).getAttributes().getNamedItem("tag").getTextContent();
                    System.out.println("Current tag : " + tagval);
                    Node node1 = nDataFieldList.item(df);
                    Element datafield = (Element) node1;
                    NodeList nSubFieldList = datafield.getElementsByTagName("subfield");
                    
                    subfieldlist.clear();
                    // store all sub field of current tag in list
                    for (int sf = 0; sf < nSubFieldList.getLength(); sf++) {
                        String sbfldval = nSubFieldList.item(sf).getAttributes().getNamedItem("code").getTextContent();
                        subfieldlist.add(sbfldval);
                    }
                    
                   codelist.clear();
                    for(int sbfl=0;sbfl<subfieldlist.size();sbfl++){
                        String code = subfieldlist.get(sbfl);
                        int codecount = Collections.frequency(subfieldlist, code);
                        if(codecount>1){
                            
                            // check tag is repetable 
                            if(repratTaglist.containsKey(tagval)){
                                 System.out.println("Repetable Tag and code------------- ");
                           
                                String checktag = tagval+code;
                                if(!codelist.contains(code)){
                                    if(tagnsubflist.containsKey(checktag)){
                                        String oldcount = tagsbfncountlist.get(checktag);
                                        int fcount = codecount+Integer.parseInt(oldcount);
                                        tagsbfncountlist.replace(checktag, String.valueOf(fcount));
                                        System.out.println("Tagsubfield n Count list "+ tagsbfncountlist);
                                    }else{
                                        codelist.add(code);
                                        tagnsubflist.put(checktag,code);
                                        tagsbfncountlist.put(checktag,String.valueOf(codecount));
                                        System.out.println("Tag n subfield list "+ tagnsubflist);
                                        System.out.println("Tagsubfield n Count list "+ tagsbfncountlist);
                                    }
                                }
                                
                              // tag not repetable  
                            }else{
                                String ftag = tagval+code;
                                if(!tagnsubflist.containsKey(ftag)){
                                    tagnsubflist.put(ftag,code);
                                    tagsbfncountlist.put(ftag,String.valueOf(codecount));
                                    System.out.println("Tag n subfield list "+ tagnsubflist);
                                    System.out.println("Tagsubfield n Count list "+ tagsbfncountlist);
                                }
                            }
                            
                        }
                    }
                    
                }
            
            }

            //Biblidetails 
            List<String> tagSrNo = new ArrayList<>();  // to store tagSrNo
            List<String> tag = new ArrayList<>();  // to store tag
            List<String> ind = new ArrayList<>();           // to store indicator
            List<String> sbfld = new ArrayList<>();            // to store subfield
            List<String> sbfldSrNo = new ArrayList<>(); // to store sbFldSrNo
            List<String> fval = new ArrayList<>();          // to store fvalue
            //location
            List<String> locInd1 = new ArrayList<>();
            List<String> locInd2 = new ArrayList<>();
            List<String> t852 = new ArrayList<>();
            List<String> p852 = new ArrayList<>();
            List<String> a852 = new ArrayList<>();
            List<String> b852 = new ArrayList<>();
            List<String> k852 = new ArrayList<>();
            List<String> f852 = new ArrayList<>();
            List<String> c852 = new ArrayList<>();
            List<String> m852 = new ArrayList<>();
            List<String> status = new ArrayList<>();
            List<String> currency = new ArrayList<>();
            List<String> issueRestricted = new ArrayList<>();
            List<String> dateOfAcq = new ArrayList<>();
            List<String> material = new ArrayList<>();
            List<String> locNulldata = new ArrayList<>();

             Map<String,String> tgcount = new HashMap<>();
             Map<String,String> tagSrNo_tag_sbfld = new HashMap<>();  // used to set sbfld SrNo
             
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                /*--------------- for leader ----------------*/
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    tagSrNo.add("1");
                    tag.add("000");
                    ind.add("");
                    sbfldSrNo.add("0");
                    sbfld.add("");
                    fval.add(eElement.getElementsByTagName("leader").item(0).getTextContent());

                    System.out.println("tag leader.. " + tag);
                    System.out.println("tagSrNo leader.. " + tagSrNo);
                    System.out.println("ind leader.. " + ind);
                    System.out.println("sbfldSrNo leader.. " + sbfldSrNo);
                    System.out.println("sbfld leader.. " + sbfld);
                    System.out.println("fval leader.. " + fval);
                    /*-------------------------------------------*/

                    /*---------------  for control field ----------------*/
                    NodeList nControlFieldList = eElement.getElementsByTagName("controlfield");
                    for (int cf = 0; cf < nControlFieldList.getLength(); cf++) {
                        tagSrNo.add("1");
                        tag.add(nControlFieldList.item(cf).getAttributes().getNamedItem("tag").getTextContent());
                        ind.add("");
                        sbfldSrNo.add("0");
                        sbfld.add("");
                        fval.add(eElement.getElementsByTagName("controlfield").item(cf).getTextContent());


                    }
                    System.out.println("tag controlfield.. " + tag);
                    System.out.println("tagSrNo controlfield.. " + tagSrNo);
                    System.out.println("ind controlfield.. " + ind);
                    System.out.println("sbfldSrNo controlfield.. " + sbfldSrNo);
                    System.out.println("sbfld controlfield.. " + sbfld);
                    System.out.println("fval controlfield.. " + fval);

                    /*-------------------------------------------*/

                    /*---------------  for data field ----------------*/
                    NodeList nDataFieldList = eElement.getElementsByTagName("datafield");

                    int tagSrNoCounter = 1;
                    String previousTag = "0";
                    String currentTag = "0";
                    int repttag = 0;
                   
                    for (int df = 0; df < nDataFieldList.getLength(); df++) {
                        Node node1 = nDataFieldList.item(df);
                        if (node1.getNodeType() == node1.ELEMENT_NODE) {

                            Element datafield = (Element) node1;
                            NodeList nSubFieldList = datafield.getElementsByTagName("subfield");

                            String tagval = nDataFieldList.item(df).getAttributes().getNamedItem("tag").getTextContent();
                            String ind1 = nDataFieldList.item(df).getAttributes().getNamedItem("ind1").getTextContent();
                            String ind2 = nDataFieldList.item(df).getAttributes().getNamedItem("ind2").getTextContent();
//                            currentTag = tagval;
//                            if (currentTag.contentEquals(previousTag)) {
//                                tagSrNoCounter++;
//                            } else {
//                                tagSrNoCounter = 1;
//                            }
//                            previousTag = tagval;
                              System.out.println("TAg : "+tagval );
                             if(repratTaglist.containsKey(tagval)){
                                 if(tgcount.containsKey(tagval)){
                                     String oldvalue = tgcount.get(tagval);
                                      System.out.println("TAg : "+tagval +" oldcount : "+oldvalue);
                                     int oldv = Integer.parseInt(oldvalue);
                                     int c =oldv+1;
                                     tgcount.replace(tagval,String.valueOf(c));
                                     System.out.println("TAg : "+tagval +" Rcount : "+c);
                                 }else{
                                      tgcount.put(tagval, "1");
                                      System.out.println("TAg : "+tagval +" Dcount : 1");
                                 }
                               repttag=1;
                             }else{
                                 repttag=0;
                                  tagSrNoCounter = 1;  
                             }
                              

                            
                            tagSrNo_tag_sbfld.clear();
                            // subfield loop    
                            for (int sf = 0; sf < nSubFieldList.getLength(); sf++) {

                                if (!tagval.equals("852")) {
                                     if(repttag==0){
                                         tagSrNo.add(String.valueOf(tagSrNoCounter));
                                     }else{
                                         tagSrNo.add(tgcount.get(tagval));
                                     }   
                                   // tagSrNo.add(String.valueOf(tagSrNoCounter));
                                    String sbfldval = nSubFieldList.item(sf).getAttributes().getNamedItem("code").getTextContent();
                                    //Node node2 = nSubFieldList.item(sf);
                                  //  tagSrNo_tag_sbfld.add(tagSrNoCounter + "_" + tagval + "_" + sbfldval);

                                   // int sbfldOccurrences = Collections.frequency(tagSrNo_tag_sbfld, tagSrNoCounter + "_" + tagval + "_" + sbfldval);
                                   String checktagcode = tagval+sbfldval;
                                   // check repetable of code...
                                   if(tagnsubflist.containsKey(checktagcode)){
                                       if(tagSrNo_tag_sbfld.containsKey(checktagcode)){
                                            String oldvalue = tagSrNo_tag_sbfld.get(checktagcode);
                                            int oldv = Integer.parseInt(oldvalue);
                                            int c =oldv+1;
                                            tagSrNo_tag_sbfld.replace(checktagcode,String.valueOf(c));
                                       }else{
                                            tagSrNo_tag_sbfld.put(checktagcode,"1");
                                       }
                                    }else{
                                        tagSrNo_tag_sbfld.put(checktagcode,"1"); 
                                   }
                                    sbfldSrNo.add(tagSrNo_tag_sbfld.get(checktagcode));
                                    sbfld.add(sbfldval);
                                    tag.add(tagval);

                                    ind.add(ind1 + ind2);
                                    fval.add(nSubFieldList.item(sf).getTextContent());
                                } else {
//                                    String sbfldval = nSubFieldList.item(sf).getAttributes().getNamedItem("code").getTextContent();
//                                    switch (sbfldval) {
//                                        case "t":
//                                            t852.add(nSubFieldList.item(sf).getTextContent());
//                                            locInd1.add(ind1);
//                                            locInd2.add(ind2);
//                                            dateOfAcq.add(formatter1.format(formatter.parse(new Date().toString())));
//                                            locNulldata.add("");
//                                            status.add("AV");
//                                            issueRestricted.add("N");
//                                            currency.add("INR");
//                                            break;
//                                        case "3":
//                                            material.add(nSubFieldList.item(sf).getTextContent());
//                                            break;
//                                        case "p":
//                                            p852.add(nSubFieldList.item(sf).getTextContent());
//                                            break;
//                                        case "a":
//                                            a852.add(nSubFieldList.item(sf).getTextContent());
//                                            break;
//                                        case "b":
//                                            b852.add(nSubFieldList.item(sf).getTextContent());
//                                            break;
//                                        case "k":
//                                            k852.add(nSubFieldList.item(sf).getTextContent());
//                                            break;
//                                        case "f":
//                                            f852.add(nSubFieldList.item(sf).getTextContent());
//                                            break;
//                                        case "c":
//                                            c852.add(nSubFieldList.item(sf).getTextContent());
//                                            break;
//                                        case "m":
//                                            m852.add(nSubFieldList.item(sf).getTextContent());
//                                            break;
//                                    }
                                }
                            }

                        }
                    }
                    /*-------------------------------------------*/
                }
            }

            /* to save biblidtetails  */
            Biblidetails b = create(StringUtils.join(tagSrNo, '~'), StringUtils.join(tag, '~'), StringUtils.join(sbfldSrNo, '~'), StringUtils.join(sbfld, '~'), StringUtils.join(ind, '~'), StringUtils.join(fval, '~'));

            /* to save location  */
//            if (isTag852Required.equalsIgnoreCase("yes") || isTag852Required.equalsIgnoreCase("true") || isTag852Required.equalsIgnoreCase("on")) {
//                locationFacadeREST.createLocation(String.valueOf(b.getBiblidetailsPK().getRecID()), StringUtils.join(t852, ','), StringUtils.join(locInd1, ','), StringUtils.join(locInd2, ','), StringUtils.join(p852, ','), StringUtils.join(b852, ','), StringUtils.join(f852, ','), StringUtils.join(locNulldata, ','),
//                        StringUtils.join(locNulldata, ','), StringUtils.join(material, ','), StringUtils.join(k852, ','), StringUtils.join(a852, ','), StringUtils.join(locNulldata, ','), StringUtils.join(locNulldata, ','), StringUtils.join(status, ','),
//                        StringUtils.join(m852, ','), StringUtils.join(c852, ','), StringUtils.join(currency, ','), StringUtils.join(locNulldata, ','), StringUtils.join(locNulldata, ','), StringUtils.join(dateOfAcq, ','), StringUtils.join(issueRestricted, ','));
//            }
            System.out.println("tag data.. " + tag);
            System.out.println("tagSrNo data.. " + tagSrNo);
            System.out.println("ind data.. " + ind);
            System.out.println("sbfldSrNo data.. " + sbfldSrNo);
            System.out.println("sbfld data.. " + sbfld);
            System.out.println("fval data.. " + fval);
            System.out.println("locInd " + locInd1);
            System.out.println("locInd " + locInd2);
            System.out.println("t852 " + t852);
            System.out.println("p852 " + p852);
            System.out.println("a852 " + a852);
            System.out.println("b852 " + b852);
            System.out.println("k852 " + k852);
            System.out.println("f852 " + f852);
            System.out.println("c852 " + c852);
            System.out.println("m852 " + m852);
            System.out.println("material  " + material);
            System.out.println("-------------------- : ");
            System.out.println("tag data.. " + tag.size());
            System.out.println("tagSrNo data.. " + tagSrNo.size());
            System.out.println("ind data.. " + ind.size());
            System.out.println("sbfldSrNo data.. " + sbfldSrNo.size());
            System.out.println("sbfld data.. " + sbfld.size());
            System.out.println("fval data.. " + fval.size());
        } catch (Exception e) {
            e.printStackTrace();
            return "Data not Imported";
        } finally {
//            try {
//                if (br != null) {
//                    br.close();
//                }
//                br1.close();
//                lnr.close();
//
//            } catch (IOException ioe) {
//                System.out.println("Error in closing the BufferedReader");
//            }
        }
        return "Data Imported Successfully";
    }
    
    private static String removeUTF8BOM(String s) {
        if (s.startsWith(UTF8_BOM)) {
            System.out.println(".....calledddddddddddddddddddddd");
            s = s.substring(1);
        }
        return s;
    }

    /*CRITERIA VS SQL  TESTING..*/
//     @GET
//    @Path("caseName/{caseName}")
//    @Produces({"application/xml", "application/json"})
//    public List<Object> findAllByCase(@PathParam("caseName") String caseName) 
//    {
//        List<Object> results = null;
//        Query query;
//        String q="";
//       switch(caseName)
//               {
//                   case "Simple":
//                    q = "Select recid ,tag,SbFld,TagSrNo,SbFldSrNo   from biblidetails ";
//                    query = getEntityManager().createNativeQuery(q);
//                    long a = System.currentTimeMillis();
//                    results = (List<Object>) query.getResultList();
//                    long b = System.currentTimeMillis();
//                       System.out.println("simple........ "+(b-a));
//                    break;
//                   
//                   case "criteria":
//                    CriteriaBuilder cb = em.getCriteriaBuilder();
//                    CriteriaQuery<Object> q1 = cb.createQuery(Object.class);
//                    Root<Biblidetails> s1 = q1.from(Biblidetails.class);
//                   // Root<Location> s2 = q1.from(Location.class);
//                      q1.multiselect( s1.get("biblidetailsPK").get("recID"),s1.get("biblidetailsPK").get("tag"),
//                              s1.get("biblidetailsPK").get("tagSrNo"),s1.get("biblidetailsPK").get("sbFldSrNo"),
//                              s1.get("biblidetailsPK").get("sbFld"));
//                 //   Join<BiblidetailsPK,Location> j1 = s2.jo
////                       Expression<Integer> e1 = s1.get("biblidetailsPK").get("recID");
////                      // Expression e2 = s1.get("recID");
////                    
////                       Predicate p1 = cb.lessThan(e1, 100);
////                         Expression<Integer> e3 = s2.get("locationPK").get("recID");
////                     //  Expression e4 = s2.get("recID");
////                       Predicate p2 = cb.lessThan(e3, 100);
////                    q1.where(p1,p2);
//                    long c = System.currentTimeMillis();
//                    results = em.createQuery(q1).getResultList();
//                    long d = System.currentTimeMillis();
//                    System.out.println("criteria........ "+(d-c));
//                       break;
//                   
//               }
//         return results;
//    }
    /* used in add accession to imported data */
    @GET
    @Path("getRecIdToSetLocation/{title}")
    @Produces("text/plain")
    public String setLocationToImportedData(@PathParam("title") String title) {
        List<Biblidetails> biblidetailslist = findBy("findRecIdToSetLocationByTitle", title);
        List<Integer> recIdList = new ArrayList<>();
        for (int i = 0; i < biblidetailslist.size(); i++) {
            recIdList.add(biblidetailslist.get(i).getBiblidetailsPK().getRecID());
        }
        String recIdListAsString = StringUtils.join(recIdList, ',');
        return recIdListAsString;
    }

    @GET
    @Path("getTitleAndAuthorByRecIds/{recIds}")
    @Produces({"application/xml", "application/json", "text/plain"})
    public Object getTitleAndAuthorByRecIds(@PathParam("recIds") String recIds) {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;
        q = "select distinct a.recID , b.FValue , c.FValue from  (select RecID  from Biblidetails  where RecID in (" + recIds + "))a "
                + " LEFT OUTER JOIN"
                + " (SELECT RecID,FValue FROM  Biblidetails where  (Tag = '245') AND (SbFld = 'a'))b"
                + " ON  a.recID = b.recID"
                + " LEFT OUTER JOIN"
                + " (SELECT RecID ,FValue FROM  Biblidetails  where (Tag IN (100, 600, 700, 800)) AND (SbFld = 'a'))c"
                + " on b.recID = c.recID ";
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }

    /*------------------------------------------------------*/
//     @POST
//     @Path("setLocationToImportedData/{recId}")
//     public void setLocationToImportedData(@PathParam("title") String title)
//     {
//         int recId
//     }
    //Used in circulation -> Search By Title sub module     
    /**
     *
     * @param title
     * @return
     */
    @GET
    @Path("searchByTitle/{title}")
    @Produces({"application/xml", "application/json", "text/plain"})
    public Object searchByTitle(@PathParam("title") String title) {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;
        q = "select distinct location.p852 as 'Accession No',fvalue as 'Book Title' ,m.mem_cd as 'Member Code',mem_firstnm as Forename,"
                + "mem_lstnm as Surname,fclty_dept_dscr as Department ,\n"
                + "branch_name as Branch,t.iss_dt as 'Issue Date',t.due_dt as 'Due Date',mem_prmntadd1 as Address,"
                + "mem_prmntcity as City,mem_prmntpin as PIN ,mem_prmntphone as Phone from location,\n"
                + "biblidetails,m_member m,t_issue t,m_fcltydept mf,m_branchmaster mb where m.mem_cd=t.mem_cd and mf.fclty_dept_cd=m.mem_dept and"
                + " mb.branch_cd=m.mem_degree and biblidetails.tag IN (245) \n"
                + "and SBfLD = 'a' and location.recid=biblidetails.recid and t.acc_no=location.p852 and fvalue like '%" + title + "%' and status='IS'";
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }

    @GET
    @Path("searchByTitleOnILL/{title}")
    @Produces({"application/xml", "application/json", "text/plain"})
    public Object searchByTitleOnILL(@PathParam("title") String title) {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;
        q = "select location.p852 as 'Accession No',fvalue as 'Book Title',lm.lib_cd as 'Library Code',lib_nm as 'Request Date',\n"
                + "lib_add1 as Address,lib_city as City,lib_pin as PIN,lib_phone as Phone, rqst_dt as 'Request Date',send_dt as 'Send Date',\n"
                + "rqst_ref as 'Request Refrence' from ill_libmst lm,ill_extissue ie,location,biblidetails where lm.lib_cd=ie.lib_cd and \n"
                + "biblidetails.tag IN (245) and SBfLD = 'a' and location.recid = biblidetails.recid And ie.acc_no = location.p852 and "
                + "fvalue like '%" + title + "%' and status='IL' ";
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
}
