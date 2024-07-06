/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue.service;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJValueFormatter;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
//import ar.com.fdvs.dj.test.ReportExporter;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import static java.lang.System.out;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import soul.catalogue.Biblidetails;
import soul.catalogue.BiblidetailsLocation;
import soul.catalogue.Location;
import soul.catalogue.Rpttemplate;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.mail.Message;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
//import mondrian.xmla.DataSourcesConfig.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
//import org.glassfish.jersey.media.multipart.BodyPart;
import org.json.JSONException;
//import reporting.GenerateJasper;

import javax.activation.DataSource;
import javax.print.PrintException;
import net.sf.jasperreports.components.barbecue.StandardBarbecueComponent;
import net.sf.jasperreports.engine.JRFrame;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.base.JRBaseBoxPen;
import net.sf.jasperreports.engine.base.JRBaseLineBox;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignLine;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignRectangle;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.type.FillEnum;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.type.LineStyleEnum;
import net.sf.jasperreports.engine.type.VerticalAlignEnum;
import soul.system_setting.MLablespects;
import soul.system_setting.service.MLablespectsFacadeREST;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.biblidetailslocation")
public class BiblidetailsLocationFacadeREST extends AbstractFacade<BiblidetailsLocation> {

    @EJB
    private MBkstatusFacadeREST mBkstatusFacadeREST;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private RpttemplateFacadeREST rpttemplateFacadeREST;
    @EJB
    private ReporttemplatemainFacadeREST reporttemplatemainFacadeREST;
    @EJB
    private BiblidetailsFacadeREST biblidetailsFacadeREST;
    @EJB
    private MLablespectsFacadeREST mLablespectsFacadeREST;

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    Biblidetails bibliObj = new Biblidetails();
    List<Location> locationList;
    String output;
    // private static String jsondataReport;
//    private JsonArray jArray;
//
//    public JsonArray getjArray() {
//        return jArray;
//    }
//
//    public void setjArray(JsonArray jArray) {
//        System.out.println("jArray jArray .. "+jArray);
//        this.jArray = jArray;
//    }

    public BiblidetailsLocationFacadeREST() {
        super(BiblidetailsLocation.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(BiblidetailsLocation entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(BiblidetailsLocation entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public BiblidetailsLocation find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<BiblidetailsLocation> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<BiblidetailsLocation> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Added manually
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<BiblidetailsLocation> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch (query) {
            case "findByLibCdAndStatus":
                valueList.add(valueString[0]); //used in borrowing book to get list of  unreceived/return request

                valueList.add(valueString[1]);
                break;
            case "findByStatus":
                valueList.add(valueString[0]);
                break;
            case "findByP852AndStatus":
                valueList.add(valueString[0]);
                break;
            case "findByTitleLike":       //Used by FindTitle of Accesssioning Process
                valueList.add(valueString[0] + "%");
                break;
            case "findByListOfRecId":
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < valueString.length; i++) {
                    list.add(Integer.parseInt(valueString[i]));
                }
                valueList.add(list);
                break;
            case "findByListOfAccNo":
                List<String> listP852 = new ArrayList<>();
                for (int i = 0; i < valueString.length; i++) {
                    listP852.add(valueString[i]);
                }
                valueList.add(listP852);
                break;
            default:
                valueList.add(valueString[0]);
                break;
            //used for findByP852
        }
        return super.findBy("BiblidetailsLocation." + query, valueList);
    }
    Location location;
    String[] accessionNos;

    @GET
    @Path("findByAccession/{accessionNo}")
    @Produces({"application/xml", "application/json"})
   // public Object findByAccession(@QueryParam("accessionNo") String accNo) {
    public Object findByAccession(@PathParam("accessionNo") String accNo) {    
        List<Object> valueList = new ArrayList<>();
        List<BiblidetailsLocation> b = new ArrayList<>();

        //String accNo = request.getParameter("accessionNo");
        if ("".equals(accNo)) {
            accNo = "null";
        }
        locationList = locationFacadeREST.findBy("findByP852", accNo);
        System.out.println("locationList " + locationList.size() + "  " + locationList.isEmpty());
        if (!locationList.isEmpty()) {
            location = locationList.get(0);
            if (!"AV".equals(location.getStatus()) && !"GR".equals(location.getStatus())) {
                output = "Item is currently " + mBkstatusFacadeREST.find(location.getStatus()).getStatusDscr() + ".";
                return output;
            } else if ("Y".equals(location.getIssueRestricted())) {
                output = "Item is Restricted for Issue....";
                return output;
            } else {

                b = findBy("findByP852", accNo);
                return b;

                // return (List<String>) b;
            }
        } else {
            output = "Invalid Accession No....";
            return output;
        }
        //System.out.println("output "+output);
        //  return output;
        //return (T)((String)(output));
    }

    @GET // used in damage 
    @Path("bookdetail/{accno}")
    @Produces({"application/xml", "application/json", "test/plain"})
    //public Response bookdetail(@QueryParam("accno") String accessionNo) {
    public Response bookdetail(@PathParam("accno") String accessionNo) {    
        Response.ResponseBuilder responseBuilder = Response.status(200);

        locationList = locationFacadeREST.findBy("findByP852", accessionNo);
        if (locationList.isEmpty()) {
            output = "Book not found with '" + accessionNo + "' Accession No. Please enter valid Accession No.";
            responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
        } else {
            location = locationList.get(0);
            if (location.getStatus().equals("AV")) {
                BiblidetailsLocation biblidetailsLocation = find(accessionNo);
                responseBuilder.type(MediaType.APPLICATION_XML).entity(biblidetailsLocation);
            } else {
                output = "Book is currently in '" + mBkstatusFacadeREST.find(location.getStatus()).getStatusDscr() + "' state and can not be marked as Damaged..";
                responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
            }
        }
        return responseBuilder.build();
    }

    @GET
    @Path("GetStockDetails/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<BiblidetailsLocation> GetStockDetailsByAccessionNo(@PathParam("searchParameter") String param) {
        return findBy("findByP852", param);
    }

    @GET
    @Path("GetStockDetailsByAccNoList/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<BiblidetailsLocation> GetStockDetailsByAccessionNos(@PathParam("searchParameter") String param) {
        return findBy("findByListOfAccNo", param);
    }

    @GET // used in missing 
    @Path("bookdetail_missing/{accno}")
    @Produces({"application/xml", "application/json", "test/plain"})
   // public Response bookdetail_missing(@QueryParam("accno") String accessionNo) {
    public Response bookdetail_missing(@PathParam("accno") String accessionNo) {    
        Response.ResponseBuilder responseBuilder = Response.status(200);
        locationList = locationFacadeREST.findBy("findByP852", accessionNo);
        if (locationList.isEmpty()) {
            output = "Book not found with '" + accessionNo + "' Accession No. Please enter valid Accession No.";
            responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
        } else {
            location = locationList.get(0);
            if (locationList.get(0).getStatus().equals("MI")) {
                output = "Book with Accession No. '" + accessionNo + "' is already reported missing.";
                responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
            } else {
                BiblidetailsLocation biblidetailsLocation = find(accessionNo);
                responseBuilder.type(MediaType.APPLICATION_XML).entity(biblidetailsLocation);
            }
        }
        return responseBuilder.build();
    }

    @GET // used in book withdrawal  
    @Path("retrieveAllWithdrawnBooks")
    @Produces({"application/xml", "application/json", "test/plain"})
    public List<BiblidetailsLocation> retrieveAll() {
        List<BiblidetailsLocation> list = null;
        list = findBy("findByStatus", "WI");
        //get all withdrawn books and requres no parameter
        return list;
    }

    @GET // used in book withdrawal  
    @Path("bookdetail_withdraw/{acc}")
    @Produces({"application/xml", "application/json", "test/plain"})
   // public Response bookdetail_withdraw(@QueryParam("acc") String accessionNo) {
    public Response bookdetail_withdraw(@PathParam("acc") String accessionNo) {    
        Response.ResponseBuilder responseBuilder = Response.status(200);
        locationList = locationFacadeREST.findBy("findByP852", accessionNo);
        if (locationList.isEmpty()) {
            output = "Book not found with '" + accessionNo + "' Accession No. Please enter valid Accession No.";
            responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
        } else {
            location = locationList.get(0);
            if (location.getStatus().equals("AV")) {
                BiblidetailsLocation biblidetailsLocation = find(accessionNo);
                responseBuilder.type(MediaType.APPLICATION_XML).entity(biblidetailsLocation);
            } else {
                output = "Book is currently in '" + mBkstatusFacadeREST.find(location.getStatus()).getStatusDscr() + "' state and can not be withdrawn.";
                responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
            }
        }
        return responseBuilder.build();
    }

    //used in title in process
//    @GET
//    @Path("getTitleInProcess/{paramName}/{paramValue}")
//    public List<BiblidetailsLocation> getTitleInProcess(@PathParam("paramName") String paramName,@PathParam("paramValue") String paramValue) throws ParseException
//    {
//         String valueString[] = paramValue.split(",");
//        List<Object> valueList = new ArrayList<>();
//        List<BiblidetailsLocation> getBibdetailsList = null;
//        switch(paramName)
//        {
//            case "date":
//                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[0]));
//                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[1]));
//                getBibdetailsList = super.findBy("BiblidetailsLocation.findByDateOfAcqBetweenAndStatusIP", valueList );
//                break;
//            case "title":
//                valueList.add(valueString[0]+'%');
//                getBibdetailsList = super.findBy("BiblidetailsLocation.findByTitleLikeAndStatusIP", valueList );
//                break;
//            case "accession":
//                valueList.add(valueString[0]);
//                getBibdetailsList = super.findBy("BiblidetailsLocation.findByP852AndStatusIP", valueList );
//                break;
//          default:
//               getBibdetailsList = super.findBy("BiblidetailsLocation.findByStatusIP");
//                break;
//     }
//        return getBibdetailsList;
//    }
//    //used in Export to marc
//    @GET
//    @Path("getBibliRecordsByRange/{RecNoFrom}/{RecNoTo}")
//     public List<BiblidetailsLocation> getBibliRecordsByRange(@PathParam("RecNoFrom") int RecNoFrom, @PathParam("RecNoTo") int RecNoTo )
//    {
//        System.out.println("RecNoFrom......  "+RecNoFrom+"   "+RecNoTo);
//        List<BiblidetailsLocation> getDetails = null;
//        List<Object> valueList = new ArrayList<>();
//        valueList.add(RecNoFrom);
//        valueList.add(RecNoTo);
//      //  List<Biblidetails> biblidetails = null;
//        getDetails = findBy("BiblidetailsLocation.findByRecIdRange", valueList);
//        return  getDetails;
//    }
    //display biblidetails for global Search
    @GET
    @Path("getBibdetailsforGlobalSearch/by/tagAndSubfield/{tag}/{subfield}/searchWord/{searchWord}")
    @Produces({"application/xml", "application/json"})
    public List<Object> getBibdetailsforGlobalSearch(@PathParam("tag") String tag, @PathParam("subfield") String subfield, @PathParam("searchWord") String searchWord) {
        List<Biblidetails> getBiblidetails = biblidetailsFacadeREST.getBibdetailsforGlobalSearch(tag, subfield, searchWord);
        List<String> recIdList = new ArrayList<>();
        for (Biblidetails b : getBiblidetails) {
            recIdList.add(String.valueOf(b.getBiblidetailsPK().getRecID()));
        }
        String recIdListString = StringUtils.join(recIdList, ',');
        recIdListString = recIdListString.replace(",", "','");
        recIdListString = "'" + recIdListString + "'";
        //     String q = "Select * from biblidetails_location b  where b.RecID IN ("+recIdListString+")";GlobalSearchclass
        String q = "Select l.p852,b.RecID ,b.title, b.Status ,b.status_dscr ,b.author, l.k852,l.a852 from biblidetails_location b join location l on b.RecID = l.RecID where b.RecID IN (" + recIdListString + ")";
        List<Object> result;
        Query query = getEntityManager().createNativeQuery(q, "GlobalSearchMapping");
        result = (List<Object>) query.getResultList();
        return result;
    }

    // @GET
    // @Path("getReportJsonData")
    public JsonArray getReportJsonData(JsonObject jobj) throws ParseException {
       // JsonObjectBuilder objectBuilderFinal = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilderFinal = Json.createArrayBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        // JsonObject jobj =   getBibliRecordsall("department","3","18","''","''");

        List<String> titleList = new ArrayList<>();
        for (String key : jobj.keySet()) {
            titleList.add(key);
        }
        System.out.println("titleList  "+titleList);
        List<String> Id = new ArrayList<>();
        for (String key1 : jobj.getJsonObject(titleList.get(0)).keySet()) {
            Id.add(key1);
               System.out.println("val..  "+jobj.getJsonObject(titleList.get(0)).get(key1));
        }
        
        System.out.println("Id.size() "+Id.size());
         System.out.println("titleList.size() "+titleList.size());
        
//JsonArray jjj = null;
        for (int i = 0; i < Id.size(); i++) {
JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            for (int j = 0; j < titleList.size(); j++) {
                
                StringBuilder sb = new StringBuilder(jobj.getJsonObject(titleList.get(j)).get(Id.get(i)).toString());
                System.out.println("sb.. "+sb);
                sb.deleteCharAt(0);
                sb.deleteCharAt(sb.lastIndexOf("]"));
                System.out.println("sb.toString() "+j+"  "+sb.toString());
                objectBuilder.add(titleList.get(j), sb.toString().replaceAll("\"", " "));
            }
            //JsonObject jo = objectBuilder.build();
           // System.out.println("jooo  "+i+"  "+jo);
           arrayBuilder.add(objectBuilder.build());
        }
//    for (String key1: jobj.getJsonObject(key).keySet())
//    {
//         objectBuilderFinal.add()
//         System.out.println(key1);
//    }
//JsonArray jarr = arrayBuilder.build();
//System.out.println("jjj......... "+jjj);
        return arrayBuilder.build();
    }

    // used in basic report in catalogue
    // get by paramvalue , material and date
    @GET
    @Path("getBibliRecordsforReport/byParamAndTemplateId/{paramName}/{paramValue}/{paramValueDesc}/{reportTemplateId}/{dates}/{materialCd}")
    public JsonArray getBibliRecordsall(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue, @PathParam("materialCd") String materialCd, @PathParam("dates") String dates, List<String> sbrptNameList)
            throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException {
        JsonObjectBuilder objectBuilderFinal = Json.createObjectBuilder();
        JsonObjectBuilder objectBuilderFinal1 = Json.createObjectBuilder();
        JsonObjectBuilder objectBuilderFinal2 = Json.createObjectBuilder();
        // JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        String paramQuery = "";
        String paramQueryAdd = "";
        String[] date = dates.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String paramValueSplit[] = paramValue.split(",");

        if ((!materialCd.contentEquals("''")) && dates.contentEquals("''")) {
            paramQueryAdd = "and l.Material = '" + materialCd + "'";
        }
        if (materialCd.contentEquals("''") && (!dates.contentEquals("''"))) {
            Date date1 = dateFormat.parse(date[0]);
            Date date2 = dateFormat.parse(date[1]);
            paramQueryAdd = "and l.dateOfAcq BETWEEN '" + new java.sql.Date(date1.getTime()) + "' AND '" + new java.sql.Date(date2.getTime()) + "'";
        }

        if ((!materialCd.equals("''")) && (!dates.equals("''"))) {
            Date date1 = dateFormat.parse(date[0]);
            Date date2 = dateFormat.parse(date[1]);
            paramQueryAdd = "and l.Material = '" + materialCd + " 'and l.dateOfAcq BETWEEN '" + new java.sql.Date(date1.getTime()) + "' AND '" + new java.sql.Date(date2.getTime()) + "'";
        }

        switch (paramName) {
            case "subject":
                paramQuery = "(SELECT c.RecID from Biblidetails c join Location l  on c.RecID = l.RecID\n"
                        + "where c.Tag LIKE '6%' and c.SbFld = 'a' and c.FValue LIKE '" + paramValueSplit[0] + "%' " + paramQueryAdd + ")";
                break;
            case "language":
                paramQuery = "(SELECT c.RecID from Biblidetails c join Location l  on c.RecID = l.RecID\n"
                        + "where c.Tag = '008' and  c.FValue LIKE '%" + paramValueSplit[0] + "%' " + paramQueryAdd + ")";
                break;
//               case "author":
//                    paramQuery = "(select RecID from Location where department = '"+paramValue+"' "+paramQueryAdd+")";
//                    break;
            case "department":
                paramQuery = "(select RecID from Location l where l.department = '" + paramValueSplit[0] + "' " + paramQueryAdd + ")";
                break;
            case "classNo":
                paramQuery = "(select RecID from Location l where LTRIM(l.k852) LIKE '" + paramValueSplit[0] + "%' " + paramQueryAdd + " )";
                break;
            case "title":
                paramQuery = "( Select distinct b.RecID from Biblidetails b LEFT OUTER JOIN "
                        + "(Select * from Biblidetails  where Tag = 245 and SbFld = 'a')a ON a.RecID = b.RecID "
                        + " LEFT OUTER JOIN  Location l On a.RecID = l.RecID "
                        + "where LTRIM(a.FValue) LIKE '" + paramValueSplit[0] + "%' " + paramQueryAdd + " )";
                break;
            case "collectionType":
                paramQuery = "( select distinct l.RecID from Location l where LTRIM(l.b852) LIKE '" + paramValueSplit[0] + "%' " + paramQueryAdd + " )";
                break;
            case "shelvingLocation":
                paramQuery = "(select distinct l.RecID from Location l where LTRIM(l.c852) LIKE '" + paramValueSplit[0] + "%' " + paramQueryAdd + " )";
                break;
            case "location":
                paramQuery = "(select distinct l.RecID from Location  l where LTRIM(l.a852) LIKE '" + paramValueSplit[0] + "%' " + paramQueryAdd + " )";
                break;
            case "accessionNo":
                paramQuery = "(select distinct l.RecID from Location  l where l.p852 =  '" + paramValueSplit[0] + "' " + paramQueryAdd + " )";
                break;
            case "accessionRange":
                List<String> accList = new ArrayList<>();
                int fromVal = Integer.parseInt(paramValueSplit[1]);
                int toVal = Integer.parseInt(paramValueSplit[2]);

                for (int i = fromVal; i <= toVal; i++) {
                    accList.add("'" + paramValueSplit[0] + i + "'");
                }
                String accArray = StringUtils.join(accList, ',');

                paramQuery = "(select distinct l.RecID from Location l where l.p852   in  (" + accArray + ") " + paramQueryAdd + " )";
                break;
            case "recIdRange":
                paramQuery = "(select distinct l.RecID from Location l where l.RecID BETWEEN  '" + paramValueSplit[0] + "' AND  '" + paramValueSplit[1] + "'   )";
                break;
            case "username":
                paramQuery = "( select distinct l.RecID from Location l where LTRIM(l.UserName) LIKE  '" + paramValueSplit[0] + "'  " + paramQueryAdd + " )";
                break;
            case "codedLocation":
                paramQuery = "(select distinct b.RecId from BibliDetails b, Location l Where b.RecId=l.RecId "
                        + " and l.f852 = '" + paramValueSplit[0] + "' " + paramQueryAdd + ")";
                break;
        }

        //System.out.println("paramQuery "+paramQuery);
//        List<Rpttemplate> rpttemplateList = null;
//        List<String> sbrptNameList = new ArrayList<>(); // to store column names 
//        rpttemplateList = rpttemplateFacadeREST.getRptByTempId(reportTemplateId); // to get list by template id
//        System.out.println("rpttemplateList........." + rpttemplateList.size());
//        // get all sbrptnames...
//        for (int i = 0; i < rpttemplateList.size(); i++) {
//            System.out.println("iiiiiiiiiiii  "+i);
//            sbrptNameList.add(rpttemplateList.get(i).getRpttemplatePK().getRptSbrptName());
//        }
        System.out.println("sbrptNameList......  " + sbrptNameList);
        for (int m = 0; m < sbrptNameList.size(); m++) {
            System.out.println("mmmmmmmmmmm  " + m);
            objectBuilderFinal.add(sbrptNameList.get(m), getReportByFieldName(sbrptNameList.get(m), paramQuery));
            // to set height,width,top,left
            // objectBuilderFinal1.add(sbrptNameList.get(m)+"_height",Integer.valueOf(rpttemplateList.get(m).getRptHeight()));
            //  objectBuilderFinal1.add(sbrptNameList.get(m)+"_width",Integer.valueOf(rpttemplateList.get(m).getRptWidth()));
            //  objectBuilderFinal1.add(sbrptNameList.get(m)+"_top",Integer.valueOf(rpttemplateList.get(m).getRptTop()));
            // objectBuilderFinal1.add(sbrptNameList.get(m)+"_left",Integer.valueOf(rpttemplateList.get(m).getRptLeft()));

        }

        // to set template height , width
        //  objectBuilderFinal2.add("PageHeight",Integer.valueOf(reporttemplatemainFacadeREST.find(Integer.parseInt(reportTemplateId)).getHeight()));
        //  objectBuilderFinal2.add("PageWidth", Integer.valueOf(reporttemplatemainFacadeREST.find(Integer.parseInt(reportTemplateId)).getWidth()));
        JsonObject jobj = objectBuilderFinal.build();
        //  JsonObject jobj1 = objectBuilderFinal1.build();
        //  JsonObject jobj2 = objectBuilderFinal2.build();

        System.out.println("objectBuilderFinal ..... " + jobj);
        //  System.out.println("objectBuilderFinal1 ..... "+jobj1);
        //   System.out.println("objectBuilderFinal2 ..... "+jobj2);
        JsonArray jarray = getReportJsonData(jobj);
        // setjArray(jarray);

        return jarray;
    }

    @GET
    @Path("getColumnNames/{tempId}")
    public String getColumnnNames(@PathParam("tempId") String tempId) {
        List<Rpttemplate> rpttemplateList = null;
        List<String> sbrptNameList = new ArrayList<>(); // to store column names 
        rpttemplateList = rpttemplateFacadeREST.getRptByTempId(tempId); // to get list by template id
        System.out.println("rpttemplateList........." + rpttemplateList.size());
        // get all sbrptnames...
        for (int i = 0; i < rpttemplateList.size(); i++) {
            System.out.println("iiiiiiiiiiii  " + i);
            sbrptNameList.add(rpttemplateList.get(i).getRpttemplatePK().getRptSbrptName());
        }

        return StringUtils.join(sbrptNameList, ',');
    }

    // to get column height , width , top, left from template
    @GET
    @Path("getColumnPropertiesFromTemplate/{tempId}")
    public String getColumnPropertiesFromTemplate(@PathParam("tempId") String tempId) {
        String[] colNames = getColumnnNames(tempId).split(",");
        List<Rpttemplate> rpttemplateList = null;
        rpttemplateList = rpttemplateFacadeREST.getRptByTempId(tempId);
        JsonObjectBuilder objectBuilderFinal = Json.createObjectBuilder();
        for (int m = 0; m < colNames.length; m++) {
            objectBuilderFinal.add(colNames[m] + "_height", Integer.valueOf(rpttemplateList.get(m).getRptHeight()));
            objectBuilderFinal.add(colNames[m] + "_width", Integer.valueOf(rpttemplateList.get(m).getRptWidth()));
            objectBuilderFinal.add(colNames[m] + "_top", Integer.valueOf(rpttemplateList.get(m).getRptTop()));
            objectBuilderFinal.add(colNames[m] + "_left", Integer.valueOf(rpttemplateList.get(m).getRptLeft()));
        }
        JsonObject jobj = objectBuilderFinal.build();
        return jobj.toString();
    }

    // to get page height and width from template
    @GET
    @Path("getPagePropertiesFromTemplate/{tempId}")
    public String getPagePropertiesFromTemplate(@PathParam("tempId") String tempId) {
        JsonObjectBuilder objectBuilderFinal = Json.createObjectBuilder();
        objectBuilderFinal.add("PageHeight", Float.parseFloat(reporttemplatemainFacadeREST.find(Integer.parseInt(tempId)).getHeight()));
        objectBuilderFinal.add("PageWidth", Float.parseFloat(reporttemplatemainFacadeREST.find(Integer.parseInt(tempId)).getWidth()));
        JsonObject jobj = objectBuilderFinal.build();
        return jobj.toString();
    }

    // used in advanced reports
    @GET
    @Path("advancedReport/{tag1}/{sbfld1}/{query1}/{op1}/{tag2}/{sbfld2}/{query2}/{op2}/{tag3}/{sbfld3}/{query3}/{reportTemplateId}")
    public String advancedReport(@PathParam("tag1") String tag1, @PathParam("sbfld1") String sbfld1,
            @PathParam("query1") String query1, @PathParam("op1") String op1, @PathParam("tag2") String tag2,
            @PathParam("sbfld2") String sbfld2, @PathParam("query2") String query2,
            @PathParam("tag3") String tag3,
            @PathParam("sbfld3") String sbfld3, @PathParam("query3") String query3,
            @PathParam("op2") String op2, @PathParam("reportTemplateId") String reportTemplateId) throws ParseException, UnsupportedEncodingException {
        System.out.println("calllllllllllllledd");
        query1 = URLDecoder.decode(query1, "UTF-8");
        System.out.println("query1 " + query1);
        JsonObjectBuilder objectBuilderFinal = Json.createObjectBuilder();
        String paramQuery = "(";

        // for query1
        //String data1[] = tag_sbfld_query1.split(",");
        //String tag1 = tag1;
        //String sbfld1 = data1[1];
        String queryVal1 = query1;
        paramQuery = paramQuery + "Select RecID from Biblidetails "
                + "where tag = '" + tag1 + "' and SbFld = '" + sbfld1 + "' and FValue LIKE '%" + queryVal1 + "%' ";
        // for query2
        String operator1 = op1;
        if (!operator1.contentEquals("select")) {
            // String data2[] = tag_sbfld_query2.split(",");
            // String tag2 = data2[0];
            //  String sbfld2 = data2[1];
            query2 = URLDecoder.decode(query2, "UTF-8");
            String queryVal2 = query2;

            if (operator1.equalsIgnoreCase("not")) {
                paramQuery = paramQuery + "  AND RecID IN   (Select RecID from Biblidetails "
                        + "where tag = '" + tag2 + "' and SbFld = '" + sbfld2 + "' and FValue NOT LIKE '%" + queryVal2 + "%' )";
            }
            if (operator1.equalsIgnoreCase("and")) {
                paramQuery = paramQuery + "  AND RecID IN   (Select RecID from Biblidetails "
                        + "where tag = '" + tag2 + "' and SbFld = '" + sbfld2 + "' and FValue LIKE '%" + queryVal2 + "%' )";
            }
            if (operator1.equalsIgnoreCase("or")) {
                paramQuery = paramQuery + " UNION   Select RecID from Biblidetails "
                        + "where tag = '" + tag2 + "' and SbFld = '" + sbfld2 + "' and FValue LIKE '%" + queryVal2 + "%' ";
            }

        }
        // for query3
        String operator2 = op2;
        if (!operator2.contentEquals("select")) {
            //   String data3[] = tag_sbfld_query3.split(",");
            //   String tag3 = data3[0];
            //   String sbfld3 = data3[1];
            query3 = URLDecoder.decode(query3, "UTF-8");
            String queryVal3 = query3;

            if (operator2.equalsIgnoreCase("not")) {
                paramQuery = paramQuery + "  AND RecID IN   (Select RecID from Biblidetails "
                        + "where tag = '" + tag3 + "' and SbFld = '" + sbfld3 + "' and FValue NOT LIKE '%" + queryVal3 + "%') ";
            }
            if (operator2.equalsIgnoreCase("and")) {
                paramQuery = paramQuery + "  AND RecID IN   (Select RecID from Biblidetails "
                        + "where tag = '" + tag3 + "' and SbFld = '" + sbfld3 + "' and FValue LIKE '%" + queryVal3 + "%' )";
            }
            if (operator2.equalsIgnoreCase("or")) {
                paramQuery = paramQuery + " UNION   Select RecID from Biblidetails "
                        + "where tag = '" + tag3 + "' and SbFld = '" + sbfld3 + "' and FValue LIKE '%" + queryVal3 + "%' ";
            }

        }

        paramQuery = paramQuery + " )";
        System.out.println("paramQuery  " + paramQuery);

        List<Rpttemplate> rpttemplateList = null;
        List<String> sbrptNameList = new ArrayList<>();
        rpttemplateList = rpttemplateFacadeREST.getRptByTempId(reportTemplateId);
        System.out.println("rpttemplateList........." + rpttemplateList.size());
        // get all sbrptnames...

        for (int i = 0; i < rpttemplateList.size(); i++) {
            sbrptNameList.add(rpttemplateList.get(i).getRpttemplatePK().getRptSbrptName());
        }
        System.out.println("paramQuery " + paramQuery);
        sbrptNameList.remove("totalItems");
        for (int m = 0; m < sbrptNameList.size(); m++) {
             JsonObject job = getReportByFieldName(sbrptNameList.get(m), paramQuery).build();
            objectBuilderFinal.add(sbrptNameList.get(m), job);
           // objectBuilderFinal.add(sbrptNameList.get(m), getReportByFieldName(sbrptNameList.get(m), paramQuery));
        }
        JsonObject jobj = objectBuilderFinal.build();
        String jsonString = getReportJsonData(jobj).toString();
        try {
            getJasperPrint(jsonString, reportTemplateId, "Advanced Report");
        } catch (JRException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonString;
    }

    // used in spine label  final version
    @GET
    @Path("getBibliRecordsforLabels/{paramName}/{paramValue}/{checkboxParam}/{dates}/{materialCd}/{labelId}/{header}/{subtitle}")
    public String getBibliRecordsforLabels(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue,
            // @PathParam("accNo") String accNo , @PathParam("classNo") String classNo,
            @PathParam("checkboxParam") String checkboxParam, @PathParam("materialCd") String materialCd,
            @PathParam("dates") String dates, @PathParam("labelId") String labelId, @PathParam("header") String header,
            @PathParam("subtitle") String subtitle) throws ParseException, FileNotFoundException {
        JsonObjectBuilder objectBuilderFinal = Json.createObjectBuilder();
        String paramQuery = "";
        String paramQueryAdd = "";
        String[] date = dates.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String paramValueSplit[] = paramValue.split(",");

        if ((!materialCd.contentEquals("''")) && dates.contentEquals("''")) {
            paramQueryAdd = "and l.Material = '" + materialCd + "'";
        }
        if (materialCd.contentEquals("''") && (!dates.contentEquals("''"))) {
            Date date1 = dateFormat.parse(date[0]);
            Date date2 = dateFormat.parse(date[1]);
            paramQueryAdd = "and l.dateOfAcq BETWEEN '" + new java.sql.Date(date1.getTime()) + "' AND '" + new java.sql.Date(date2.getTime()) + "'";
        }

        if ((!materialCd.equals("''")) && (!dates.equals("''"))) {
            Date date1 = dateFormat.parse(date[0]);
            Date date2 = dateFormat.parse(date[1]);
            paramQueryAdd = "and l.Material = '" + materialCd + " 'and l.dateOfAcq BETWEEN '" + new java.sql.Date(date1.getTime()) + "' AND '" + new java.sql.Date(date2.getTime()) + "'";
        }

        String checkboxParamSplit[] = checkboxParam.split(",");
        List<String> columnList = new ArrayList<>();
        for (int c = 0; c < checkboxParamSplit.length; c++) {
            columnList.add(checkboxParamSplit[c]);
        }
        switch (paramName) {
            case "subject":
                paramQuery = "(SELECT c.RecID from Biblidetails c join Location l  on c.RecID = l.RecID\n"
                        + "where c.Tag LIKE '6%' and c.SbFld = 'a' and c.FValue LIKE '" + paramValueSplit[0] + "%' " + paramQueryAdd + ")";
                break;
            case "language":
                paramQuery = "(SELECT c.RecID from Biblidetails c join Location l  on c.RecID = l.RecID\n"
                        + "where c.Tag = '008' and  c.FValue LIKE '%" + paramValueSplit[0] + "%' " + paramQueryAdd + ")";
                break;
//               case "author":
//                    paramQuery = "(select RecID from Location where department = '"+paramValue+"' "+paramQueryAdd+")";
//                    break;
            case "department":
                paramQuery = "(select RecID from Location l where l.department = '" + paramValueSplit[0] + "' " + paramQueryAdd + ")";
                break;
            case "classNo":
                paramQuery = "(select RecID from Location l where LTRIM(l.k852) LIKE '" + paramValueSplit[0] + "%'  " + paramQueryAdd + ")";
                break;
            case "title":
                paramQuery = "( Select distinct b.RecID from Biblidetails b LEFT OUTER JOIN "
                        + "(Select * from Biblidetails  where Tag = 245 and SbFld = 'a')a ON a.RecID = b.RecID "
                        + " LEFT OUTER JOIN  Location l On a.RecID = l.RecID "
                        + "where LTRIM(a.FValue) LIKE '" + paramValueSplit[0] + "%'  " + paramQueryAdd + ")";
                break;
            case "collectionType":
                paramQuery = "( select distinct l.RecID from Location l where LTRIM(l.b852) LIKE '" + paramValueSplit[0] + "%'  " + paramQueryAdd + ")";
                break;
            case "shelvingLocation":
                paramQuery = "(select distinct l.RecID from Location l where LTRIM(l.c852) LIKE '" + paramValueSplit[0] + "%'  " + paramQueryAdd + ")";
                break;
            case "location":
                paramQuery = "(select distinct l.RecID from Location  l where LTRIM(l.a852) LIKE '" + paramValueSplit[0] + "%' " + paramQueryAdd + " )";
                break;
            case "accessionNos":
                System.out.println("paramValueSplit  " + paramValueSplit[0]);
                String getParamValues = "";
                for (int p = 0; p < paramValueSplit.length; p++) {
                    getParamValues = getParamValues + "'" + paramValueSplit[p] + "'";
                    if (p < (paramValueSplit.length - 1)) {
                        getParamValues = getParamValues + ",";
                    }
                }
                System.out.println("getParamValues " + getParamValues);
                paramQuery = "(select distinct l.RecID from Location  l where l.p852 in  (" + getParamValues + ")   " + paramQueryAdd + ")";
                break;
            case "accessionNoPrefix":
                paramQuery = "(select distinct l.RecID from Location  l where l.p852 LIKE  '" + paramValueSplit[0] + "%'  " + paramQueryAdd + ")";
                break;
            case "accessionRange":

                List<String> accList = new ArrayList<>();
                int fromVal = Integer.parseInt(paramValueSplit[1]);
                int toVal = Integer.parseInt(paramValueSplit[2]);

                for (int i = fromVal; i <= toVal; i++) {
                    accList.add("'" + paramValueSplit[0] + i + "'");
                }
                String accArray = StringUtils.join(accList, ',');
                System.out.println("accArray .....  " + accArray);
                paramQuery = "(select distinct l.RecID from Location l where l.p852   in  (" + accArray + ")  " + paramQueryAdd + ")";
                break;
            case "recIdRange":
                paramQuery = "(select distinct l.RecID from Location l where l.RecID BETWEEN  '" + paramValueSplit[0] + "' AND  '" + paramValueSplit[1] + "'   " + paramQueryAdd + ")";
                break;
            case "username":
                paramQuery = "( select distinct l.RecID from Location l where LTRIM(l.UserName) LIKE  '" + paramValueSplit[0] + "'  " + paramQueryAdd + " )";
                break;
            case "codedLocation":
                paramQuery = "(select distinct b.RecId from BibliDetails b, Location l Where b.RecId=l.RecId "
                        + " and l.f852 = '" + paramValueSplit[0] + "' " + paramQueryAdd + ")";
                break;
        }
        System.out.println("columnList ... " + columnList);
        columnList.remove("drawLine");
        columnList.remove("header");
        if (columnList.isEmpty()) {
            columnList.add("accessionNo");
        }
        System.out.println("columnList ... rem " + columnList);
        for (int m = 0; m < columnList.size(); m++) {
            JsonObject job = getLabelByFieldName(columnList.get(m), paramQuery).build();
            objectBuilderFinal.add(columnList.get(m), job);
           // objectBuilderFinal.add(columnList.get(m), getLabelByFieldName(columnList.get(m), paramQuery));

        }
        //  objectBuilderFinal.add("header", header);
        JsonObject jobj = objectBuilderFinal.build();
        System.out.println("jobj..  " + jobj);
        String jsonString = getReportJsonData(jobj).toString();
        try {
            getJasperPrintLabels(jsonString, checkboxParam, labelId, header, subtitle, "spine");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonString;

    }

    // used in bookCard    final version
    @GET
    @Path("getBibliRecordsforBookcard/{paramName}/{paramValue}/{checkboxParam}/{materialCd}/{dates}/{labelId}/{header}/{subtitle}")
    public String getBibliRecordsforBookcard(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue,
            // @PathParam("accNo") String accNo , @PathParam("classNo") String classNo,
            @PathParam("checkboxParam") String checkboxParam, @PathParam("materialCd") String materialCd,
            @PathParam("dates") String dates, @PathParam("labelId") String labelId, @PathParam("header") String header,
            @PathParam("subtitle") String subtitle) throws ParseException, FileNotFoundException {
        JsonObjectBuilder objectBuilderFinal = Json.createObjectBuilder();
        String paramQuery = "";
        String paramValueSplit[] = paramValue.split(",");
        String checkboxParamSplit[] = checkboxParam.split(",");

        String paramQueryAdd = "";
        String[] date = dates.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if ((!materialCd.contentEquals("''")) && dates.contentEquals("''")) {
            paramQueryAdd = "and l.Material = '" + materialCd + "'";
        }
        if (materialCd.contentEquals("''") && (!dates.contentEquals("''"))) {
            Date date1 = dateFormat.parse(date[0]);
            Date date2 = dateFormat.parse(date[1]);
            paramQueryAdd = "and l.dateOfAcq BETWEEN '" + new java.sql.Date(date1.getTime()) + "' AND '" + new java.sql.Date(date2.getTime()) + "'";
        }

        if ((!materialCd.equals("''")) && (!dates.equals("''"))) {
            Date date1 = dateFormat.parse(date[0]);
            Date date2 = dateFormat.parse(date[1]);
            paramQueryAdd = "and l.Material = '" + materialCd + " 'and l.dateOfAcq BETWEEN '" + new java.sql.Date(date1.getTime()) + "' AND '" + new java.sql.Date(date2.getTime()) + "'";
        }

        List<String> columnList = new ArrayList<>();
        for (int c = 0; c < checkboxParamSplit.length; c++) {
            columnList.add(checkboxParamSplit[c]);
        }
        switch (paramName) {
            case "subject":
                paramQuery = "(SELECT c.RecID from Biblidetails c join Location l  on c.RecID = l.RecID\n"
                        + "where c.Tag LIKE '6%' and c.SbFld = 'a' and c.FValue LIKE '" + paramValueSplit[0] + "%' " + paramQueryAdd + " )";
                break;
            case "language":
                paramQuery = "(SELECT c.RecID from Biblidetails c join Location l  on c.RecID = l.RecID\n"
                        + "where c.Tag = '008' and  c.FValue LIKE '%" + paramValueSplit[0] + "%' " + paramQueryAdd + " )";
                break;
//               case "author":
//                    paramQuery = "(select RecID from Location where department = '"+paramValue+"' "+paramQueryAdd+")";
//                    break;
//             case "department":
//                  paramQuery = "(select RecID from Location l where l.department = '"+paramValueSplit[0]+"' )";
//                  break;
            case "classNo":
                paramQuery = "(select RecID from Location l where LTRIM(l.k852) LIKE '" + paramValueSplit[0] + "%'  " + paramQueryAdd + " )";
                break;
            case "title":
                paramQuery = "( Select distinct b.RecID from Biblidetails b LEFT OUTER JOIN "
                        + "(Select * from Biblidetails  where Tag = 245 and SbFld = 'a')a ON a.RecID = b.RecID "
                        + " LEFT OUTER JOIN  Location l On a.RecID = l.RecID "
                        + "where LTRIM(a.FValue) LIKE '" + paramValueSplit[0] + "%'  " + paramQueryAdd + ")";
                break;
            case "collectionType":
                paramQuery = "( select distinct l.RecID from Location l where LTRIM(l.b852) LIKE '" + paramValueSplit[0] + "%'  " + paramQueryAdd + " )";
                break;
            case "shelvingLocation":
                paramQuery = "(select distinct l.RecID from Location l where LTRIM(l.c852) LIKE '" + paramValueSplit[0] + "%'  " + paramQueryAdd + " )";
                break;
            case "location":
                paramQuery = "(select distinct l.RecID from Location  l where LTRIM(l.a852) LIKE '" + paramValueSplit[0] + "%'  " + paramQueryAdd + " )";
                break;
            case "accessionNos":
                System.out.println("paramValueSplit  " + paramValueSplit[0]);
                String getParamValues = "";
                for (int p = 0; p < paramValueSplit.length; p++) {
                    getParamValues = getParamValues + "'" + paramValueSplit[p] + "'";
                    if (p < (paramValueSplit.length - 1)) {
                        getParamValues = getParamValues + ",";
                    }
                }
                System.out.println("getParamValues " + getParamValues);
                paramQuery = "(select distinct l.RecID from Location  l where l.p852 in  (" + getParamValues + ") " + paramQueryAdd + "  )";
                break;
            case "accessionNoPrefix":
                paramQuery = "(select distinct l.RecID from Location  l where l.p852 LIKE  '" + paramValueSplit[0] + "%' " + paramQueryAdd + "  )";
                break;
            case "accessionRange":
                List<String> accList = new ArrayList<>();
                int fromVal = Integer.parseInt(paramValueSplit[1]);
                int toVal = Integer.parseInt(paramValueSplit[2]);

                for (int i = fromVal; i <= toVal; i++) {
                    accList.add(paramValueSplit[0] + i);
                }
                String accArray = StringUtils.join(accList, ',');

                paramQuery = "(select distinct l.RecID from Location l where l.p852   in  (" + accArray + ")  " + paramQueryAdd + " )";
                break;
            case "recIdRange":
                paramQuery = "(select distinct l.RecID from Location l where l.RecID BETWEEN  '" + paramValueSplit[0] + "' AND  '" + paramValueSplit[1] + "'  " + paramQueryAdd + "  )";
                break;
            case "username":
                paramQuery = "( select distinct l.RecID from Location l where LTRIM(l.UserName) LIKE  '" + paramValueSplit[0] + "'  " + paramQueryAdd + "  )";
                break;
        }

//        for (int m = 0; m < checkboxParamSplit.length; m++) {
//
//            objectBuilderFinal.add(checkboxParamSplit[m], getLabelByFieldName(checkboxParamSplit[m], paramQuery));
//
//        }
//        objectBuilderFinal.add("header", header);
//        String jsonString = 
//         getJasperPrintLabels(jsonString, checkboxParam, labelId, header, subtitle, "spine");
//        return objectBuilderFinal.build();
        System.out.println("columnList ... " + columnList);
        columnList.remove("drawLine");
        columnList.remove("header");
        if (columnList.isEmpty()) {
            columnList.add("accessionNo");
        }
        System.out.println("columnList ... rem " + columnList);
        for (int m = 0; m < columnList.size(); m++) {

            objectBuilderFinal.add(columnList.get(m), getLabelByFieldName(columnList.get(m), paramQuery));

        }
        //  objectBuilderFinal.add("header", header);
        JsonObject jobj = objectBuilderFinal.build();
        System.out.println("jobj..  " + jobj);
        String jsonString = getReportJsonData(jobj).toString();
        try {
            getJasperPrintLabels(jsonString, checkboxParam, labelId, header, subtitle, "bookcard");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonString;
    }

    // used in catalogue card
    @GET
    @Path("getBibliRecordsforCataloguecard/{paramName}/{paramValue}/{dates}")
    public JsonObject getBibliRecordsforCataloguecard(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue,
            // @PathParam("accNo") String accNo , @PathParam("classNo") String classNo,
            // @PathParam("callNo") String callNo , @PathParam("collectionType") String collectionType,
            //@PathParam("bookNo") String bookNo ,
            @PathParam("dates") String dates) throws ParseException {
        JsonObjectBuilder objectBuilderFinal = Json.createObjectBuilder();
        String paramQuery = "";
        String paramValueSplit[] = paramValue.split(",");
        String checkboxParam = "classNo,bookNo,author,title,publisher,accessionNo,physicalDescription,isbn,price,subject";
        String checkboxParamSplit[] = checkboxParam.split(",");

        String paramQueryAdd = "";
        String[] date = dates.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if ((!dates.contentEquals("''"))) {
            Date date1 = dateFormat.parse(date[0]);
            Date date2 = dateFormat.parse(date[1]);
            paramQueryAdd = "and l.dateOfAcq BETWEEN '" + new java.sql.Date(date1.getTime()) + "' AND '" + new java.sql.Date(date2.getTime()) + "'";
        }

        switch (paramName) {

            case "accessionNos":
                System.out.println("paramValueSplit  " + paramValueSplit[0]);
                String getParamValues = "";
                for (int p = 0; p < paramValueSplit.length; p++) {
                    getParamValues = getParamValues + "'" + paramValueSplit[p] + "'";
                    if (p < (paramValueSplit.length - 1)) {
                        getParamValues = getParamValues + ",";
                    }
                }
                System.out.println("getParamValues " + getParamValues);
                paramQuery = "(select distinct l.RecID from Location  l where l.p852 in  (" + getParamValues + ") " + paramQueryAdd + "  )";
                break;
            case "accessionNo":
                paramQuery = "(select distinct l.RecID from Location  l where l.p852 =  '" + paramValueSplit[0] + "' " + paramQueryAdd + "  )";
                break;
            case "recIdRange":
                paramQuery = "(select distinct l.RecID from Location l where l.RecID BETWEEN  '" + paramValueSplit[0] + "' AND  '" + paramValueSplit[1] + "'  " + paramQueryAdd + "  )";
                break;
            case "username":
                paramQuery = "( select distinct l.RecID from Location l where LTRIM(l.UserName) LIKE  '" + paramValueSplit[0] + "'  " + paramQueryAdd + "  )";
                break;
        }

        for (int m = 0; m < checkboxParamSplit.length; m++) {

            objectBuilderFinal.add(checkboxParamSplit[m], getReportByFieldName(checkboxParamSplit[m], paramQuery));

        }
        return objectBuilderFinal.build();
    }

    public JsonObjectBuilder getReportByFieldName(String SbrptName, String paramQuery) {
        
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilderFinal = Json.createArrayBuilder();
        Map<Integer, List<String>> fMap = new HashMap<>();
        Query query;
        String q = "";

        System.out.println("SbrptName... " + SbrptName);
        if (SbrptName.equalsIgnoreCase("recId")) {
            q = "Select distinct a.RecID ,CAST(a.RecID  as CHAR(20))  from Biblidetails a where a.RecID in "
                    + paramQuery + " ORDER BY a.RecID ";
        }

        if (SbrptName.equalsIgnoreCase("accessionNo")) {
            q = "Select distinct b.RecID ,a.p852  from Biblidetails b"
                    + " LEFT OUTER JOIN  Location a ON b.RecID = a.recID where b.RecID in "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("title")) {
            q = "Select distinct  a.RecID , b.FValue  from Biblidetails a LEFT outer JOIN "
                    + " (Select * from biblidetails where Tag=245 and SbFld in ('a','b','c')) b on a.RecID = b.recID where a.RecID in "
                    + paramQuery + " ORDER BY a.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("classNo")) {
//            q = "Select distinct a.RecID as 'recId' ,a.k852 ,a.p852  from Location a where a.RecID in "
//                    + paramQuery + " ORDER BY a.RecID ";
            q = "Select distinct b.RecID ,a.k852 ,a.p852  from Biblidetails b"
                    + "  LEFT OUTER JOIN  Location a ON b.RecID = a.recID where b.RecID in "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("bookNo")) {
            q = "Select distinct b.RecID ,a.m852 ,a.p852  from Biblidetails b"
                    + " LEFT OUTER JOIN Location a ON b.RecID = a.recID where b.RecID in "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("author")) {
            q = "Select distinct b.RecID, a.FValue\n"
                    + " from Biblidetails b LEFT OUTER JOIN\n"
                    + " (Select * from Biblidetails  where Tag in('100','110','111','130') and SbFld = 'a')a ON a.RecID = b.RecID \n"
                    + " where b.RecID IN "
                    + paramQuery + " ORDER BY a.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("subject")) {
            q = "Select distinct b.RecID, a.FValue\n"
                    + " from Biblidetails b LEFT OUTER JOIN\n"
                    + " (Select * from Biblidetails  where Tag LIKE '6%' and SbFld = 'a')a ON a.RecID = b.RecID \n"
                    + " where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("serialNo")) {
            q = "SELECT   RecID  ,CAST((@count:=@count+1) as CHAR(10)) as FValue from "
                    + paramQuery + " as s , (SELECT @count:=0) AS t ORDER BY RecID ";
        }

        if (SbrptName.equalsIgnoreCase("publisher")) {
//            q = "   Select * from (Select a.RecID , concat(a.FValue , b.FValue) from "
//                    + "(Select * from Biblidetails where Tag = '260' and SbFld = 'a')a "
//                    + "LEFT outer JOIN (Select * from Biblidetails where Tag = '260' and SbFld = 'b')b "
//                    + "on a.RecID = b.RecID where b.RecID IN "
//                    + paramQuery 
//                    + " UNION "
//                    + " Select a.RecID , concat(a.FValue , b.FValue) from "
//                    + "(Select * from Biblidetails where Tag = '260' and SbFld = 'a')a "
//                    + "RIGHT outer JOIN (Select * from Biblidetails where Tag = '260' and SbFld = 'b')b "
//                    + "on a.RecID = b.RecID where b.RecID IN "
//                    + paramQuery 
//                    + " ) i ORDER BY RecID ";

// q = "  Select fvalue from biblidetails where sbfld in ('a','b') And tag=260 "
//           + " where RecID IN "          
//         + paramQuery + " ORDER BY RecID ";
//  q = "Select distinct b.RecID, a.FValue  from Biblidetails b LEFT OUTER JOIN  "
//                    + "(Select * from( Select * from Biblidetails where Tag = '260' and SbFld = 'a')c " 
//                    + " LEFT outer JOIN (Select * from Biblidetails where Tag = '260' and SbFld = 'b')d " 
//                   + " on c.RecID = d.RecID))a ON a.RecID = b.RecID "
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//  q = "Select distinct b.RecID, a.FValue  from Biblidetails b LEFT OUTER JOIN \n "
//                    + " (Select * from Biblidetails sbfld in ('a','b') And tag=260 " 
//                    + " )a ON a.RecID = b.RecID "
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//     q = "Select  distinct Recid from  biblidetails where sbfld in ('a','b') and  tag=260 "
//      +"  b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
            q = " Select distinct c.RecID , concat(a.FValue , b.FValue) from "
                    + "(Select * from Biblidetails)c LEFT outer JOIN"
                    + " (Select * from Biblidetails where Tag = '260' and SbFld = 'a') a   on c.RecID = a.RecID "
                    + " LEFT outer JOIN (Select * from Biblidetails where Tag = '260' and SbFld = 'b') b "
                    + " on  a.RecID = b.RecID  where c.RecID IN "
                    + paramQuery + " ORDER BY c.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("budget")) {
            q = "Select distinct b.RecID ,a.Budget , a.p852  from Biblidetails b"
                    + " LEFT OUTER JOIN  Location a ON b.RecID = a.RecID \n"
                    + " where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("callNumber")) {
            q = "Select distinct b.RecID , CAST((concat(a.k852,',',a.m852)) as CHAR(20)) , a.p852  from "
                    + " Biblidetails b LEFT OUTER JOIN  Location  a \n"
                    + " ON b.RecID = a.RecID where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("edition")) {
            q = "Select distinct b.RecID, a.FValue  from (Select * from Biblidetails  where Tag ='250' and SbFld = 'a')a  \n"
                    + "  RIGHT OUTER JOIN Biblidetails b ON a.RecID = b.RecID "
                    + " where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("DateofAcquisition")) {
            q = "Select distinct b.RecID , DATE_FORMAT(a.DateofAcq,'%d-%m-%Y') ,a.p852  from  Biblidetails b \n"
                    + "  LEFT OUTER JOIN Location a  ON b.RecID = a.RecID "
                    + " where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("CollectionType")) {
            q = "Select distinct b.RecID ,l.b852 , l.p852 from  Biblidetails b \n"
                    + "  LEFT OUTER JOIN Location l  ON b.RecID = l.RecID "
                    + " where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
//            if(SbrptName.equalsIgnoreCase("CollectionType"))
//           {
//               q = "Select distinct a.RecID  ,a.b852 ,a.p852 from  Location  a \n" +
//                   " where a.RecID IN "
//                   + paramQuery+" ORDER BY a.RecID ";
//            }
        if (SbrptName.equalsIgnoreCase("invoiceDate")) {
            q = "Select distinct b.RecID  ,DATE_FORMAT(a.InvoiceDate,'%d-%m-%Y') ,a.p852 from "
                    + " Biblidetails b LEFT OUTER JOIN Location  a  ON b.RecID = a.RecID \n"
                    + " where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("invoiceNo")) {
            q = "Select distinct b.RecID  ,a.InvoiceNo ,a.p852 from  Biblidetails b  "
                    + " LEFT OUTER JOIN Location  a ON b.RecID = a.RecID\n"
                    + " where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("isbn")) {
            q = "Select distinct b.RecID, a.FValue  from Biblidetails b LEFT OUTER JOIN \n"
                    + "(Select * from Biblidetails  where Tag in('020','021') and SbFld = 'a')a ON a.RecID = b.RecID "
                    + " where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("keyword")) {
            /*needs to be verified*/ q = "Select distinct b.RecID, a.FValue  from Biblidetails b LEFT OUTER JOIN \n "
                    + "(Select * from Biblidetails  where Tag ='653' and SbFld = 'a')a ON a.RecID = b.RecID "
                    + " where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("location")) {
            q = "Select distinct b.RecID  ,a.a852  ,a.p852 from  Biblidetails b LEFT OUTER JOIN Location  a \n"
                    + " ON b.RecID = a.RecID where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("noOfCopies")) {
            q = "Select distinct b.RecID  ,CAST((MAX(a.t852)) as CHAR(3)),a.p852  from "
                    + " Biblidetails b LEFT OUTER JOIN Location  a \n"
                    + " ON b.RecID = a.RecID where b.RecID IN "
                    + paramQuery + " group by b.RecID ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("note")) {
            q = "Select distinct b.RecID, a.FValue  from Biblidetails b LEFT OUTER JOIN "
                    + "(Select * from Biblidetails  where Tag in(500,501,502,504,505,510,525,546) "
                    + " and SbFld = 'a')a ON a.RecID = b.RecID  "
                    + " where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("physicalDescription")) {
            q = "Select distinct b.RecID, a.FValue  from Biblidetails b LEFT OUTER JOIN \n "
                    + "(Select * from Biblidetails  where Tag = 300 and "
                    + " SbFld in ('a','b','c'))a ON a.RecID = b.RecID "
                    + " where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("price")) {
            q = "Select distinct b.RecID  , CAST((a.Price) as CHAR(10)),a.p852 from "
                    + " Biblidetails b LEFT OUTER JOIN   Location  a \n"
                    + " ON b.RecID = a.RecID  where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("shelvingLocation")) {
            q = "Select distinct b.RecID , a.c852 , a.p852  from Biblidetails b LEFT OUTER JOIN  Location  a \n"
                    + " ON b.RecID = a.RecID  where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("supplier")) {
            q = "Select distinct b.RecID , a.c852 , a.p852  from  Biblidetails b "
                    + " LEFT OUTER JOIN Location  a  \n"
                    + " ON b.RecID = a.RecID where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("year")) {
            q = "Select distinct b.RecID, a.FValue  from Biblidetails b LEFT OUTER JOIN \n "
                    + " (Select * from Biblidetails  where Tag = 260 and "
                    + " SbFld = 'c')a ON a.RecID = b.RecID "
                    + " where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("accessionNoBarcode")) // used in spineLabels in reports
        {
            q = "Select distinct b.RecID ,a.p852 as barcode from Biblidetails b"
                    + " LEFT OUTER JOIN  Location a ON b.RecID = a.recID where b.RecID in "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        if (SbrptName.equalsIgnoreCase("issNoVolNo")) {
            q = "Select distinct b.RecID, a.FValue  from Biblidetails b LEFT OUTER JOIN \n "
                    + " (Select * from Biblidetails  where Tag = 887 and "
                    + " SbFld = 'a')a ON a.RecID = b.RecID "
                    + " where b.RecID IN "
                    + paramQuery + " ORDER BY b.RecID ";
        }
        System.out.println("q........... " + q);
        query = getEntityManager().createNativeQuery(q);
        List<Object[]> l = query.getResultList();// get query result  
        System.out.println("l........... " + l.size());

        ArrayList<Integer> getRecId = new ArrayList<>(l.size());
        for (Object[] row : l) {
            getRecId.add((Integer) row[0]); // get RecId from query result
        }

        ArrayList<String> getFval = new ArrayList<>(l.size());
        for (Object[] row : l) {
            getFval.add((String) row[1]); // get fvalue from query result
        }

        Set<Integer> s = new LinkedHashSet<>();
        s.addAll(getRecId); // store recId in a set to eliminate duplicates

        ArrayList<Integer> list = new ArrayList<>();
        list.addAll(s); // convert the set to list again

        // store recId and fvalue in objectBuilder 
        System.out.println("list.size() .. " + list.size());

        try {

            for (int i = 0; i < list.size(); i++) {
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                ArrayList<Integer> a = indexOfAll(list.get(i), getRecId);
                for (int j = 0; j < a.size(); j++)// looping is used to store single recId  with multiple fvalues
                {
                    String fval = getFval.get(a.get(j));

                    if (fval == null || fval.isEmpty() || fval.equals("")) {
                        //  fval = "null";
                        continue;
                    }

                    System.out.println("recId : " + list.get(i) + " " + fval);

                    System.out.println("fval " + fval + " recid .. " + list.get(i));

                    arrayBuilder.add(fval); //will store multiple fvalues for a single recId
                     System.out.println("arrayBuilder  "+arrayBuilder);
                    
                }
                JsonArray jab = arrayBuilder.build();
                //System.out.println("jab ....  "+jab);
                objectBuilder.add(String.valueOf(list.get(i)), jab);//  add array builder to objectBuilder
            }
           
        } catch (Exception e) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, e);

        }
      //  System.out.println("objectBuilder .. build.. " + objectBuilder.build().toString());
        return objectBuilder;  // store all objectbuilder
    }

    public JsonObjectBuilder getLabelByFieldName(String SbrptName, String paramQuery) {
       // JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        Map<Integer, List<String>> fMap = new HashMap<>();
        Query query;
        String q = "";

        if (SbrptName.equalsIgnoreCase("accessionNo")) {
            q = "Select distinct a.p852 ,a.p852  from Biblidetails b"
                    + " LEFT OUTER JOIN  Location a ON b.RecID = a.recID where b.RecID in "
                    + paramQuery + " ORDER BY a.p852 ";
        }
        if (SbrptName.equalsIgnoreCase("title")) {
            q = "Select distinct  a.p852 , b.FValue  from Biblidetails c  LEFT OUTER JOIN (Select * from biblidetails where Tag=245 and SbFld in ('a','b','c')) b "
                    + "   ON b.RecID = c.RecID  LEFT OUTER JOIN  Location a"
                    + " ON c.RecID = a.recID where c.RecID in "
                    + paramQuery + " ORDER BY a.p852 ";
        }
        if (SbrptName.equalsIgnoreCase("classNo")) {
//            q = "Select distinct a.RecID as 'recId' ,a.k852 ,a.p852  from Location a where a.RecID in "
//                    + paramQuery + " ORDER BY a.RecID ";
            q = "Select distinct a.p852 ,a.k852   from Biblidetails b"
                    + "  LEFT OUTER JOIN  Location a ON b.RecID = a.recID where b.RecID in "
                    + paramQuery + " ORDER BY a.p852 ";
        }
        if (SbrptName.equalsIgnoreCase("bookNo")) {
            q = "Select distinct a.p852 ,a.m852   from Biblidetails b"
                    + " LEFT OUTER JOIN Location a ON b.RecID = a.recID where b.RecID in "
                    + paramQuery + " ORDER BY a.p852 ";
        }
        if (SbrptName.equalsIgnoreCase("author")) {
            q = "Select distinct a.p852, b.FValue "
                    + " from Biblidetails c  LEFT OUTER JOIN (Select * from Biblidetails  where Tag in('100','110','111','130') and SbFld = 'a') b "
                    + "   ON b.RecID = c.RecID  LEFT OUTER JOIN  Location a"
                    + " ON c.RecID = a.recID where c.RecID in "
                    + paramQuery + " ORDER BY a.p852 ";
        }
//        if (SbrptName.equalsIgnoreCase("subject")) {
//            q = "Select distinct b.RecID, a.FValue\n"
//                    + " from Biblidetails b LEFT OUTER JOIN\n"
//                    + " (Select * from Biblidetails  where Tag LIKE '6%' and SbFld = 'a')a ON a.RecID = b.RecID \n"
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
//        if (SbrptName.equalsIgnoreCase("serialNo")) {
//            q = "SELECT   RecID  ,CAST((@count:=@count+1) as CHAR(10)) as FValue from "
//                    + paramQuery + " as s , (SELECT @count:=0) AS t ORDER BY RecID ";
//        }
//
//        if (SbrptName.equalsIgnoreCase("publisher")) {
//            q = "   Select * from (Select a.RecID , concat(a.FValue , b.FValue) from "
//                    + "(Select * from Biblidetails where Tag = '260' and SbFld = 'a')a "
//                    + "LEFT outer JOIN (Select * from Biblidetails where Tag = '260' and SbFld = 'b')b "
//                    + "on a.RecID = b.RecID where b.RecID IN "
//                    + paramQuery 
//                    + " UNION "
//                    + " Select a.RecID , concat(a.FValue , b.FValue) from "
//                    + "(Select * from Biblidetails where Tag = '260' and SbFld = 'a')a "
//                    + "RIGHT outer JOIN (Select * from Biblidetails where Tag = '260' and SbFld = 'b')b "
//                    + "on a.RecID = b.RecID where b.RecID IN "
//                    + paramQuery 
//                    + " ) i ORDER BY RecID ";

// q = "  Select fvalue from biblidetails where sbfld in ('a','b') And tag=260 "
//           + " where RecID IN "          
//         + paramQuery + " ORDER BY RecID ";
//  q = "Select distinct b.RecID, a.FValue  from Biblidetails b LEFT OUTER JOIN  "
//                    + "(Select * from( Select * from Biblidetails where Tag = '260' and SbFld = 'a')c " 
//                    + " LEFT outer JOIN (Select * from Biblidetails where Tag = '260' and SbFld = 'b')d " 
//                   + " on c.RecID = d.RecID))a ON a.RecID = b.RecID "
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//  q = "Select distinct b.RecID, a.FValue  from Biblidetails b LEFT OUTER JOIN \n "
//                    + " (Select * from Biblidetails sbfld in ('a','b') And tag=260 " 
//                    + " )a ON a.RecID = b.RecID "
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//     q = "Select  distinct Recid from  biblidetails where sbfld in ('a','b') and  tag=260 "
//      +"  b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//            q = " Select distinct c.RecID , concat(a.FValue , b.FValue) from "
//          +  "(Select * from Biblidetails)c LEFT outer JOIN" 
//     + " (Select * from Biblidetails where Tag = '260' and SbFld = 'a') a   on c.RecID = a.RecID "
//     + " LEFT outer JOIN (Select * from Biblidetails where Tag = '260' and SbFld = 'b') b "
//     + " on  a.RecID = b.RecID  where c.RecID IN " 
//     + paramQuery + " ORDER BY c.RecID ";
//        }
//        if (SbrptName.equalsIgnoreCase("budget")) {
//            q = "Select distinct b.RecID ,a.Budget , a.p852  from Biblidetails b"
//                    + " LEFT OUTER JOIN  Location a ON b.RecID = a.RecID \n"
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
        if (SbrptName.equalsIgnoreCase("callNumber")) {
            q = "Select distinct a.p852 , CAST((concat(a.k852,',',a.m852)) as CHAR(20))  from "
                    + " Biblidetails b LEFT OUTER JOIN  Location  a \n"
                    + " ON b.RecID = a.RecID where b.RecID IN "
                    + paramQuery + " ORDER BY a.p852 ";
        }
//        if (SbrptName.equalsIgnoreCase("edition")) {
//            q = "Select distinct b.RecID, a.FValue  from (Select * from Biblidetails  where Tag ='250' and SbFld = 'a')a  \n"
//                    + "  RIGHT OUTER JOIN Biblidetails b ON a.RecID = b.RecID "
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
//        if (SbrptName.equalsIgnoreCase("DateofAcquisition")) {
//            q = "Select distinct b.RecID , DATE_FORMAT(a.DateofAcq,'%d-%m-%Y') ,a.p852  from  Biblidetails b \n"
//                     + "  LEFT OUTER JOIN Location a  ON b.RecID = a.RecID "
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
        if (SbrptName.equalsIgnoreCase("CollectionType")) {
            q = "Select distinct l.p852 ,l.b852 from  Biblidetails b \n"
                    + "  LEFT OUTER JOIN Location l  ON b.RecID = l.RecID "
                    + " where b.RecID IN "
                    + paramQuery + " ORDER BY l.p852 ";
        }
//            if(SbrptName.equalsIgnoreCase("CollectionType"))
//           {
//               q = "Select distinct a.RecID  ,a.b852 ,a.p852 from  Location  a \n" +
//                   " where a.RecID IN "
//                   + paramQuery+" ORDER BY a.RecID ";
//            }
//        if (SbrptName.equalsIgnoreCase("invoiceDate")) {
//            q = "Select distinct b.RecID  ,DATE_FORMAT(a.InvoiceDate,'%d-%m-%Y') ,a.p852 from "
//                    + " Biblidetails b LEFT OUTER JOIN Location  a  ON b.RecID = a.RecID \n"
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
//        if (SbrptName.equalsIgnoreCase("invoiceNo")) {
//            q = "Select distinct b.RecID  ,a.InvoiceNo ,a.p852 from  Biblidetails b  "
//                    + " LEFT OUTER JOIN Location  a ON b.RecID = a.RecID\n"
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
//        if (SbrptName.equalsIgnoreCase("isbn")) {
//            q = "Select distinct b.RecID, a.FValue  from Biblidetails b LEFT OUTER JOIN \n"
//                    + "(Select * from Biblidetails  where Tag in('020','021') and SbFld = 'a')a ON a.RecID = b.RecID "
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
//        if (SbrptName.equalsIgnoreCase("keyword")) {
//            /*needs to be verified*/ q = "Select distinct b.RecID, a.FValue  from Biblidetails b LEFT OUTER JOIN \n "
//                    + "(Select * from Biblidetails  where Tag ='653' and SbFld = 'a')a ON a.RecID = b.RecID "
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
//        if (SbrptName.equalsIgnoreCase("location")) {
//            q = "Select distinct b.RecID  ,a.a852  ,a.p852 from  Biblidetails b LEFT OUTER JOIN Location  a \n"
//                    + " ON b.RecID = a.RecID where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
//        if (SbrptName.equalsIgnoreCase("noOfCopies")) {
//            q = "Select distinct b.RecID  ,CAST((MAX(a.t852)) as CHAR(3)),a.p852  from "
//                    + " Biblidetails b LEFT OUTER JOIN Location  a \n"
//                    + " ON b.RecID = a.RecID where b.RecID IN "
//                    + paramQuery + " group by b.RecID ORDER BY b.RecID ";
//        }
//        if (SbrptName.equalsIgnoreCase("note")) {
//            q = "Select distinct b.RecID, a.FValue  from Biblidetails b LEFT OUTER JOIN "
//                    + "(Select * from Biblidetails  where Tag in(500,501,502,504,505,510,525,546) "
//                    + " and SbFld = 'a')a ON a.RecID = b.RecID  "
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
//        if (SbrptName.equalsIgnoreCase("physicalDescription")) {
//            q = "Select distinct b.RecID, a.FValue  from Biblidetails b LEFT OUTER JOIN \n "
//                    + "(Select * from Biblidetails  where Tag = 300 and "
//                    + " SbFld in ('a','b','c'))a ON a.RecID = b.RecID "
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
//        if (SbrptName.equalsIgnoreCase("price")) {
//            q = "Select distinct b.RecID  , CAST((a.Price) as CHAR(10)),a.p852 from "
//                    + " Biblidetails b LEFT OUTER JOIN   Location  a \n"
//                    + " ON b.RecID = a.RecID  where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
//        if (SbrptName.equalsIgnoreCase("shelvingLocation")) {
//            q = "Select distinct b.RecID , a.c852 , a.p852  from Biblidetails b LEFT OUTER JOIN  Location  a \n"
//                    + " ON b.RecID = a.RecID  where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
//        if (SbrptName.equalsIgnoreCase("supplier")) {
//            q = "Select distinct b.RecID , a.c852 , a.p852  from  Biblidetails b "
//                    + " LEFT OUTER JOIN Location  a  \n"
//                    + " ON b.RecID = a.RecID where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
//        if (SbrptName.equalsIgnoreCase("year")) {
//            q = "Select distinct b.RecID, a.FValue  from Biblidetails b RIGHT OUTER JOIN \n "
//
//                    + " (Select * from Biblidetails  where Tag = 260 and "
//                    + " SbFld = 'c')a ON a.RecID = b.RecID "
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
        if (SbrptName.equalsIgnoreCase("accessionNoBarcode")) // used in spineLabels in reports
        {
            q = "Select distinct a.p852 ,a.p852 as barcode from Biblidetails b"
                    + " LEFT OUTER JOIN  Location a ON b.RecID = a.recID where b.RecID in "
                    + paramQuery + " ORDER BY a.p852 ";
        }
//         if (SbrptName.equalsIgnoreCase("issNoVolNo")) 
//        {
//              q = "Select distinct b.RecID, a.FValue  from Biblidetails b LEFT OUTER JOIN \n "
//                    + " (Select * from Biblidetails  where Tag = 887 and "
//                    + " SbFld = 'a')a ON a.RecID = b.RecID "
//                    + " where b.RecID IN "
//                    + paramQuery + " ORDER BY b.RecID ";
//        }
        System.out.println("q........... " + q);
        query = getEntityManager().createNativeQuery(q);
        List<Object[]> l = query.getResultList();// get query result  
        System.out.println("l........... " + l.size());

        ArrayList<String> getId = new ArrayList<>(l.size());
        for (Object[] row : l) {
            getId.add((String) row[0]); // get RecId from query result
        }
        System.out.println("getId  " + getId);
        ArrayList<String> getFval = new ArrayList<>(l.size());
        for (Object[] row : l) {

            getFval.add((String) row[1]); // get fvalue from query result
        }
        System.out.println("getFval  " + getFval);

        Set<String> s = new LinkedHashSet<>();
        s.addAll(getId); // store recId in a set to eliminate duplicates

        ArrayList<String> list = new ArrayList<>();
        list.addAll(s); // convert the set to list again

        // store recId and fvalue in objectBuilder 
        System.out.println("list.size() .. " + list.size());

        try {

            for (int i = 0; i < list.size(); i++) {
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                ArrayList<Integer> a = indexOfAll(list.get(i), getId);
                for (int j = 0; j < a.size(); j++)// looping is used to store single recId  with multiple fvalues
                {
                    String fval = getFval.get(a.get(j));

                    if (fval == null || fval.isEmpty() || fval.equals("")) {
                        //  fval = "null";
                        continue;
                    }

                    System.out.println("Id : " + list.get(i) + " " + fval);

                    System.out.println("fval " + fval + " id .. " + list.get(i));

                    arrayBuilder.add(fval); //will store multiple fvalues for a single recId
                }
                JsonArray jab = arrayBuilder.build();
                //System.out.println("jab ....  "+jab);
                objectBuilder.add(String.valueOf(list.get(i)), jab);//  add array builder to objectBuilder
            }
        } catch (Exception e) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, e);

        }
        System.out.println("objectBuilder.. " + objectBuilder.toString());
        return objectBuilder;  // store all objectbuilder
    }

    static ArrayList<Integer> indexOfAll(Object obj, ArrayList list) {
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            if (obj.equals(list.get(i))) {
                indexList.add(i);
            }
        }
        return indexList;
    }

//    @GET
//    @Path("case/{caseName}")
//    @Produces({"application/xml", "application/json"})
//    public List<Object> findAllByCase(@PathParam("caseName") String caseName) 
//    {
//        List<Object> results = null;
//        Query query;
//        String q="";
//       switch(caseName)
//               {
//                   case "Simple":
//                    q = "Select * from biblidetails_location WHERE RecId < 500";
//                    query = getEntityManager().createNativeQuery(q);
//                    results = (List<Object>) query.getResultList();
//                    break;
//                   
//                   case "criteria":
//                    CriteriaBuilder cb = em.getCriteriaBuilder();
//                    CriteriaQuery<Object> q1 = cb.createQuery(Object.class);
//                    Root<BiblidetailsLocation> s1 = q1.from(BiblidetailsLocation.class);
//                    q1.select(s1);
//                       Expression<Integer> e1 = s1.get("recID");
//                     Predicate p1 = cb.lessThan(e1, 200);
//                    q1.where(p1);
//                    
//                    results = em.createQuery(q1).getResultList();
//                   
//               }
//         return results;
//    }
    @GET
    @Path("CurrentAwareness/{subject}/{author}/{dates}/{languageCd}/{template}")
    public JsonObject CurrentAwareness(@PathParam("subject") String subject, @PathParam("author") String author,
            @PathParam("dates") String dates, @PathParam("languageCd") String languageCd, @PathParam("template") String templateId) throws ParseException {
        JsonObjectBuilder objectBuilderFinal = Json.createObjectBuilder();
        // JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        String paramQuery = "";
        String paramQueryAdd = "";
        String[] date = dates.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if ((subject.contentEquals("''"))) {
            subject = "";
        }

        if ((author.contentEquals("''"))) {
            author = "";
        }

        if ((!dates.contentEquals("''"))) {
            Date date1 = dateFormat.parse(date[0]);
            Date date2 = dateFormat.parse(date[1]);
            paramQueryAdd = " and l.dateOfAcq BETWEEN '" + new java.sql.Date(date1.getTime()) + "' AND '" + new java.sql.Date(date2.getTime()) + "'";
        }

        paramQuery = " (Select distinct b1.RecID from Biblidetails b1 join Location l1 on b1.RecID = l1.RecID where"
                + " b1.RecID in (Select c1.RecID from (Select distinct b2.RecID from Biblidetails b2 where "
                + "b2.Tag LIKE '6%' and b2.SbFld = 'a' and b2.FValue like '%" + subject + "%')c1 INNER JOIN "
                + "(Select distinct b3.RecID from Biblidetails b3 where b3.Tag in('100','600','700','800') "
                + "and b3.SbFld = 'a' and b3.FValue like N'" + author + "%')d1 on c1.RecID = d1.RecID ) "
                + "and b1.Tag='008' and b1.Fvalue like '%" + languageCd + "%'   " + paramQueryAdd + " )";

        // System.out.println("paramQuery----------- "+paramQuery);
        List<Rpttemplate> rpttemplateList = null;
        List<String> sbrptNameList = new ArrayList<>();
        rpttemplateList = rpttemplateFacadeREST.getRptByTempId(templateId);

        // get all sbrptnames...
        for (int i = 0; i < rpttemplateList.size(); i++) {
            sbrptNameList.add(rpttemplateList.get(i).getRpttemplatePK().getRptSbrptName());
        }

        for (int m = 0; m < sbrptNameList.size(); m++) {
            objectBuilderFinal.add(sbrptNameList.get(m), getReportByFieldName(sbrptNameList.get(m), paramQuery));
        }
        return objectBuilderFinal.build();
    }

    @GET
    @Path("getBookDetailsToReset/{accNo}")
    @Produces({"application/xml", "application/json", "text/plain"})
    public Response getBookDetailsToReset(@PathParam("accNo") String accNo) {
        List<BiblidetailsLocation> bookDetails = null;
        String output = "";
        Response.ResponseBuilder responseBuilder = Response.status(200);
        bookDetails = findBy("findByP852AndStatus", accNo);
        System.out.println(bookDetails);
        if (bookDetails.size() == 0) {
            output = "Only issued or On-hold items are allowed";
            responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
        } else {
            responseBuilder.type(MediaType.APPLICATION_XML).entity(bookDetails.get(0));
        }

        return responseBuilder.build();
    }

    protected JasperReport jr;

    // generate basic reports
//    @Context
//    @GET
//    @Path("generateDynamicBasicReports/{paramName}/{paramValue}/{paramValueDesc}/{reportTemplateId}/{materialCd}/{dates}")
//    public JasperPrint generateDynamicBasicReports(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue,@PathParam("paramValueDesc") String paramValueDesc, @PathParam("reportTemplateId") String reportTemplateId, @PathParam("materialCd") String materialCd, @PathParam("dates") String dates) throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException 
//        {
//           System.out.println("paramName  "+paramName); 
//        List<Rpttemplate> rpttemplateList = null;
//              List<String> sbrptNameList = new ArrayList<>(); // to store column names 
//        rpttemplateList = rpttemplateFacadeREST.getRptByTempId(reportTemplateId); // to get list by template id
//        System.out.println("rpttemplateList........." + rpttemplateList.size());
//        // get all sbrptnames...
//        for (int i = 0; i < rpttemplateList.size(); i++) {
//             sbrptNameList.add(rpttemplateList.get(i).getRpttemplatePK().getRptSbrptName());
//        }
//        
//          List<String> titleList = new ArrayList<>();
//          titleList.addAll(sbrptNameList);
//         titleList.remove("totalItems");
//          
//         JsonArray jarray = getBibliRecordsall(paramName, paramValue,  materialCd, dates ,titleList);
//        // JsonArray jarray = getjArray();
//            System.out.println("jarray.......... "+jarray );
//        // JsonObject colNames = getColumnnNames(sbrptNameList);
//            JsonObject colProperties = getColumnPropertiesFromTemplate(sbrptNameList, rpttemplateList);
//            JsonObject  pageProperties = getPagePropertiesFromTemplate(reportTemplateId);
//            
//        String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
//        System.out.println("indFilePath  " + filePath);
//        // builder object
//        DynamicReportBuilder drb = new DynamicReportBuilder();
//        
//        // detail style - applicable to all rows
//        Style detailStyle = new Style();
//        detailStyle.setFont(Font.COURIER_NEW_BIG);
//        detailStyle.setTransparency(Transparency.OPAQUE);
//      
//        // header style
//        Style headerStyle = new Style();
//        headerStyle.setBackgroundColor(new Color(51, 153, 255));
//        headerStyle.setBorder(Border.PEN_1_POINT());
//        headerStyle.setTextColor(Color.white);
//        headerStyle.setBorderColor(Color.black);
//       //headerStyle.setHorizontalAlign(HorizontalAlign.);
//        headerStyle.setTransparency(Transparency.OPAQUE);
//
//        // set title style - applicable to column names
//        Style titleStyle = Style.createBlankStyle("titleStyle");
//        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
//        titleStyle.setFont(Font.ARIAL_BIG_BOLD);
//        titleStyle.setTransparency(Transparency.OPAQUE);
//        /**
//         * "subtitleStyleParent" is meant to be used as a parent style, while
//         * "subtitleStyle" is the child.
//         */
//        Style subtitleStyleParent = Style.createBlankStyle("subtitleParent");
//        subtitleStyleParent.setBackgroundColor(Color.GREEN);
//        subtitleStyleParent.setTransparency(Transparency.OPAQUE);
//
//        Style subtitleStyle = Style.createBlankStyle("subtitleStyle");
//        subtitleStyle.setFont(Font.ARIAL_BIG_BOLD);
//        subtitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
//   
//         drb.setTitle("REPORT") //defines the title of the report
//                .setSubtitle("Library collection having "+paramValueDesc)
//                .setDetailHeight(20) //defines the height for each record of the report
//                .setMargins(30, 20, 30, 15) //define the margin space for each side (top, bottom, left and right)
//                .setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
//               
//                 .setColumnsPerPage(1);
//                
//                
////FastReportBuilder drb = new FastReportBuilder();
//        DynamicReport dr = null;
//       
//        AbstractColumn[] s = new AbstractColumn[titleList.size()];
//        String titleListVal = null;
//        for (int i = 0; i < titleList.size(); i++) {
//            titleListVal = titleList.get(i);
//  
//            s[i] = ColumnBuilder.getNew() //creates a new instance of a ColumnBuilder
//                    .setColumnProperty(titleListVal, String.class.getName()) //defines the field of the data source that this column will show, also its type
//                    .setTitle(titleListVal) //the title for the column
//                    .setWidth(Integer.parseInt(colProperties.get(titleListVal + "_width").toString())) //the width of the column
//                    //   .setStyle(colStyle)
//                    .build();
//
//            s[i].setPosX(Integer.parseInt(colProperties.get(titleListVal + "_left").toString()));
//            s[i].setPosY(Integer.parseInt(colProperties.get(titleListVal + "_top").toString()));
//            drb.addColumn(s[i]);
//        }
//        try {
//               /**
//             * add some more options to the report (through the builder)
//             */
////drb.addBarcodeColumn("Bar-Code", "recId", Long.class.getName(), BarcodeTypes.USD3, true, false,null, 100, true, ImageScaleMode.FILL, null);
//            drb.setUseFullPageWidth(true);  //we tell the report to use the full width of the page. this rezises
//            //the columns width proportionally to meat the page width.
//		
//            Style oddRowBackgroundStyle = new Style();
//            oddRowBackgroundStyle.setBackgroundColor(new Color(244, 249, 202));
//            drb.setOddRowBackgroundStyle(oddRowBackgroundStyle);
//            drb.setPrintBackgroundOnOddRows(true);
//            	Style atStyle2 = new StyleBuilder(true).setFont(new Font(12, Font._FONT_ARIAL, false, true, false)).setTextColor(Color.BLUE).build();
//  	
//             drb.addAutoText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()),AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT,100,atStyle2);
//            drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_CENTER,100,40,atStyle2);
////            Page page = new Page();
////            page.Page_A4_Landscape();
////            page.setOrientationPortrait(true);
//  //drb.addGlobalFooterVariable(s[0], "Tota");
//
//  if(sbrptNameList.contains("totalItems"))
//  {
//  drb.addGlobalFooterVariable(s[0], DJCalculation.COUNT ,detailStyle, new DJValueFormatter() {
//				
//				public String getClassName() {
//					return String.class.getName();
//				}
//				
//				public Object evaluate(Object value, Map fields, Map variables, Map parameters) {
//					
//					return "Total Items :"+value;
//				}
//			});
//  }
//            dr = drb.build(); //Finally build the report!  
//           
//          
//           
////drb.setReportLocale(new Locale("en","IN"));
//        } catch (ColumnBuilderException ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jarray.toString().getBytes());
//
//        JsonDataSource ds = new JsonDataSource(jsonDataStream);
//        JasperPrint jp = null;
//        try {
//           
//            jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
//            
//            jp.setPageWidth((int) (96 * Double.parseDouble(pageProperties.get("PageWidth").toString())));
//            jp.setPageHeight((int) (96 *  Double.parseDouble(pageProperties.get("PageHeight").toString())));
//
//        } catch (JRException ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
////        String xmlString = null;
////        try {
////            xmlString = JasperExportManager.exportReportToXml(jp);
////        } catch (JRException ex) {
////            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
////        }
//         
//        
//        JasperExportManager.exportReportToPdfFile(jp, System.getProperty("user.home") + "/Desktop/reports/memberT.pdf");
//   
//      //  JasperExportManager.exportReportToHtmlFile(jp, filePath + "/mem.html");
//  // p JasperExportManager.exportReportToXmlFile(jp, filePath+ "/mem.jrxml", true);
//
//       // DynamicJasperHelper.generateJRXML(this.jr, "UTF-8", dir + "/" + fileName + ".jrxml");
//      //  ReportExporter.exportReportPlainXls(jp, System.getProperty("user.home") + "/Desktop/reports/mem15.xls");
////DynamicJasperHelper.generateJasperReport(dr,  new ClassicLayoutManager(), pageProperties, filePath)
////p DynamicJasperHelper.generateJRXML(dr, new ClassicLayoutManager(), new HashMap(), "UTF-8", System.getProperty("user.home") + "/Desktop/reports/memData.jrxml");
////String ht = null;
////        FileReader fr = new FileReader(filePath + "/mem.html");
////        BufferedReader br = new BufferedReader(fr);
////        StringBuilder content = new StringBuilder(1024);
////
////        try {
////            while ((ht = br.readLine()) != null) {
////                content.append(ht);
////            }
////        } catch (IOException ex) {
////            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
////        } finally {
////            try {
////                br.close();
////                fr.close();
////            } catch (IOException ex) {
////                Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
////            }
////
////        }
//        //   System.out.println("ht...  "+content.toString());
//        //File file = new File(filePath+"/mem.html"); 
//        //file.delete();
//            System.out.println("jjjpppppppppppp  "+jp);
//        return jp;
//        }
//    @GET
//    @Path("generateReportPdf/{paramName}/{paramValue}/{paramValueDesc}/{reportTemplateId}/{dates}/{materialCd}")
//    @Produces("application/pdf")
//    public Response generateReportPdf(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue,@PathParam("paramValueDesc") String paramValueDesc, @PathParam("reportTemplateId") String reportTemplateId, @PathParam("materialCd") String materialCd, @PathParam("dates") String dates) throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException 
//    {
//     List<Rpttemplate> rpttemplateList = null;
//              List<String> sbrptNameList = new ArrayList<>(); // to store column names 
//        rpttemplateList = rpttemplateFacadeREST.getRptByTempId(reportTemplateId); // to get list by template id
//        System.out.println("rpttemplateList........." + rpttemplateList.size());
//        // get all sbrptnames...
//        for (int i = 0; i < rpttemplateList.size(); i++) {
//            System.out.println("iiiiiiiiiiii  "+i);
//            sbrptNameList.add(rpttemplateList.get(i).getRpttemplatePK().getRptSbrptName());
//        }
//        
//         List<String> titleList = new ArrayList<>();
//          titleList.addAll(sbrptNameList);
//         titleList.remove("totalItems");   
//         
//            JsonArray jarray = getBibliRecordsall(paramName, paramValue,  materialCd, dates ,titleList);
//          // JsonArray jarray = getjArray();
//        //  JsonObject colNames = getColumnnNames(sbrptNameList);
//            JsonObject colProperties = getColumnPropertiesFromTemplate(titleList, rpttemplateList);
//            JsonObject  pageProperties = getPagePropertiesFromTemplate(reportTemplateId);
//            
//        String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
//        System.out.println("indFilePath  " + filePath);
//        // builder object
//        DynamicReportBuilder drb = new DynamicReportBuilder();
//        
//        // detail style - applicable to all rows
//        Style detailStyle = new Style();
//        detailStyle.setFont(Font.COURIER_NEW_BIG);
//        detailStyle.setTransparency(Transparency.OPAQUE);
//      
//        // header style
//        Style headerStyle = new Style();
//        headerStyle.setBackgroundColor(new Color(51, 153, 255));
//        headerStyle.setBorder(Border.PEN_1_POINT());
//        headerStyle.setTextColor(Color.white);
//        headerStyle.setBorderColor(Color.black);
//       //headerStyle.setHorizontalAlign(HorizontalAlign.);
//        headerStyle.setTransparency(Transparency.OPAQUE);
//
//        // set title style - applicable to column names
//        Style titleStyle = Style.createBlankStyle("titleStyle");
//        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
//        titleStyle.setFont(Font.ARIAL_BIG_BOLD);
//        titleStyle.setTransparency(Transparency.OPAQUE);
//        /**
//         * "subtitleStyleParent" is meant to be used as a parent style, while
//         * "subtitleStyle" is the child.
//         */
//        Style subtitleStyleParent = Style.createBlankStyle("subtitleParent");
//        subtitleStyleParent.setBackgroundColor(Color.GREEN);
//        subtitleStyleParent.setTransparency(Transparency.OPAQUE);
//
//        Style subtitleStyle = Style.createBlankStyle("subtitleStyle");
//        subtitleStyle.setFont(Font.ARIAL_BIG_BOLD);
//        subtitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
//   
//         drb.setTitle("REPORT") //defines the title of the report
//                .setSubtitle("Library collection having "+paramValueDesc)
//                .setDetailHeight(20) //defines the height for each record of the report
//                .setMargins(30, 20, 30, 15) //define the margin space for each side (top, bottom, left and right)
//                .setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
//                .setColumnsPerPage(1);
//                
//                
////FastReportBuilder drb = new FastReportBuilder();
//        DynamicReport dr = null;
//      
//        AbstractColumn[] s = new AbstractColumn[titleList.size()];
//        String titleListVal = null;
//        for (int i = 0; i < titleList.size(); i++) {
//            titleListVal = titleList.get(i);
//  
//            s[i] = ColumnBuilder.getNew() //creates a new instance of a ColumnBuilder
//                    .setColumnProperty(titleListVal, String.class.getName()) //defines the field of the data source that this column will show, also its type
//                    .setTitle(titleListVal) //the title for the column
//                    .setWidth(Integer.parseInt(colProperties.get(titleListVal + "_width").toString())) //the width of the column
//                    //   .setStyle(colStyle)
//                    .build();
//
//            s[i].setPosX(Integer.parseInt(colProperties.get(titleListVal + "_left").toString()));
//            s[i].setPosY(Integer.parseInt(colProperties.get(titleListVal + "_top").toString()));
//            drb.addColumn(s[i]);
//        }
//        try {
//               /**
//             * add some more options to the report (through the builder)
//             */
////drb.addBarcodeColumn("Bar-Code", "recId", Long.class.getName(), BarcodeTypes.USD3, true, false,null, 100, true, ImageScaleMode.FILL, null);
//            drb.setUseFullPageWidth(true);  //we tell the report to use the full width of the page. this rezises
//            //the columns width proportionally to meat the page width.
//
//            Style oddRowBackgroundStyle = new Style();
//            oddRowBackgroundStyle.setBackgroundColor(new Color(244, 249, 202));
//            drb.setOddRowBackgroundStyle(oddRowBackgroundStyle);
//            drb.setPrintBackgroundOnOddRows(false);
//           // drb.setWhenNoData("no data", detailStyle);
//                       drb.addAutoText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()),AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT);
//            drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_CENTER);
////            Page page = new Page();
////            page.Page_A4_Landscape();
////            page.setOrientationPortrait(true);
// 
//
//  if(sbrptNameList.contains("totalItems"))
//  {
//  drb.addGlobalFooterVariable(s[0], DJCalculation.COUNT ,detailStyle, new DJValueFormatter() {
//				
//				public String getClassName() {
//					return String.class.getName();
//				}
//				
//				public Object evaluate(Object value, Map fields, Map variables, Map parameters) {
//					
//					return "Total Items :"+value;
//				}
//			});
//  }
//
//            dr = drb.build(); //Finally build the report!      
//
//        } catch (ColumnBuilderException ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jarray.toString().getBytes());
//
//        JsonDataSource ds = new JsonDataSource(jsonDataStream);
//        JasperPrint jp = null;
//        try {
//            jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
//             jp.setPageWidth((int) (96 * Double.parseDouble(pageProperties.get("PageWidth").toString())));
//            jp.setPageHeight((int) (96 *  Double.parseDouble(pageProperties.get("PageHeight").toString())));
//
//        } catch (JRException ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
////        String xmlString = null;
////        try {
////            xmlString = JasperExportManager.exportReportToXml(jp);
////        } catch (JRException ex) {
////            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
////        }
//      
//        JasperExportManager.exportReportToPdfFile(jp, filePath + "/report.pdf");
//   
//      //  File file = new File(System.getProperty("java.io.tmpdir") + "/memReport.pdf");
//       //  JasperExportManager.exportReportToPdfFile(jp, System.getProperty("user.home") + "/Desktop/reports/mem13.pdf");
//
//      FileInputStream fileInputStream = new FileInputStream(filePath+"/report.pdf");        javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
//  //  responseBuilder.type("application/pdf");
//    responseBuilder.header("Content-Disposition","filename=report.pdf");
//            //File file = new File(filePath+"/mem.html"); 
//        //file.delete();
//       return responseBuilder.build();
//        }
//      @GET
//    @Path("generateReportExcel/{paramName}/{paramValue}/{paramValueDesc}/{reportTemplateId}/{dates}/{materialCd}")
//    @Produces("application/vnd.ms-excel")
//    public Response generateReportExcel(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue,@PathParam("paramValueDesc") String paramValueDesc, @PathParam("reportTemplateId") String reportTemplateId, @PathParam("materialCd") String materialCd, @PathParam("dates") String dates) throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException 
//    {
//     List<Rpttemplate> rpttemplateList = null;
//              List<String> sbrptNameList = new ArrayList<>(); // to store column names 
//        rpttemplateList = rpttemplateFacadeREST.getRptByTempId(reportTemplateId); // to get list by template id
//        System.out.println("rpttemplateList........." + rpttemplateList.size());
//        // get all sbrptnames...
//        for (int i = 0; i < rpttemplateList.size(); i++) {
//            System.out.println("iiiiiiiiiiii  "+i);
//            sbrptNameList.add(rpttemplateList.get(i).getRpttemplatePK().getRptSbrptName());
//        }
//        
//         List<String> titleList = new ArrayList<>();
//          titleList.addAll(sbrptNameList);
//         titleList.remove("totalItems");
//         
//           JsonArray jarray = getBibliRecordsall(paramName, paramValue,  materialCd, dates ,titleList);
//          // JsonArray jarray = getjArray();
//        //  JsonObject colNames = getColumnnNames(sbrptNameList);
//            JsonObject colProperties = getColumnPropertiesFromTemplate(titleList, rpttemplateList);
//            JsonObject  pageProperties = getPagePropertiesFromTemplate(reportTemplateId);
//            
//        String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
//        System.out.println("indFilePath  " + filePath);
//        // builder object
//        DynamicReportBuilder drb = new DynamicReportBuilder();
//        
//        // detail style - applicable to all rows
//        Style detailStyle = new Style();
//        detailStyle.setFont(Font.COURIER_NEW_BIG);
//      //  detailStyle.setTransparency(Transparency.OPAQUE);
//      
//        // header style
//        Style headerStyle = new Style();
//        headerStyle.setBackgroundColor(new Color(51, 153, 255));
//        headerStyle.setBorder(Border.PEN_1_POINT());
//        headerStyle.setTextColor(Color.white);
//        headerStyle.setBorderColor(Color.black);
//       //headerStyle.setHorizontalAlign(HorizontalAlign.);
//        headerStyle.setTransparency(Transparency.OPAQUE);
//
//        // set title style - applicable to column names
//        Style titleStyle = Style.createBlankStyle("titleStyle");
//        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
//        titleStyle.setFont(Font.ARIAL_BIG_BOLD);
//        titleStyle.setTransparency(Transparency.OPAQUE);
//        /**
//         * "subtitleStyleParent" is meant to be used as a parent style, while
//         * "subtitleStyle" is the child.
//         */
//        Style subtitleStyleParent = Style.createBlankStyle("subtitleParent");
//        subtitleStyleParent.setBackgroundColor(Color.GREEN);
//        subtitleStyleParent.setTransparency(Transparency.OPAQUE);
//
//        Style subtitleStyle = Style.createBlankStyle("subtitleStyle");
//        subtitleStyle.setFont(Font.ARIAL_BIG_BOLD);
//        subtitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
//   
//         drb.setTitle("REPORT") //defines the title of the report
//                .setSubtitle("Library collection having "+paramValueDesc)
//                .setDetailHeight(20) //defines the height for each record of the report
//                .setMargins(0, 0, 0, 0) //define the margin space for each side (top, bottom, left and right)
//                .setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
//                .setColumnsPerPage(1);
//                
//                
////FastReportBuilder drb = new FastReportBuilder();
//        DynamicReport dr = null;
//           
//        AbstractColumn[] s = new AbstractColumn[titleList.size()];
//        String titleListVal = null;
//        for (int i = 0; i < titleList.size(); i++) {
//            titleListVal = titleList.get(i);
//            titleList.remove("totalItems");
//            s[i] = ColumnBuilder.getNew() //creates a new instance of a ColumnBuilder
//                    .setColumnProperty(titleListVal, String.class.getName()) //defines the field of the data source that this column will show, also its type
//                    .setTitle(titleListVal) //the title for the column
//                    .setWidth(Integer.parseInt(colProperties.get(titleListVal + "_width").toString())) //the width of the column
//                    //   .setStyle(colStyle)
//                     .build();
//
//            s[i].setPosX(Integer.parseInt(colProperties.get(titleListVal + "_left").toString()));
//            s[i].setPosY(Integer.parseInt(colProperties.get(titleListVal + "_top").toString()));
//            drb.addColumn(s[i]);
//        }
//        try {
//               /**
//             * add some more options to the report (through the builder)
//             */
////drb.addBarcodeColumn("Bar-Code", "recId", Long.class.getName(), BarcodeTypes.USD3, true, false,null, 100, true, ImageScaleMode.FILL, null);
//            drb.setUseFullPageWidth(true);  //we tell the report to use the full width of the page. this rezises
//            //the columns width proportionally to meat the page width.
//
//            Style oddRowBackgroundStyle = new Style();
//            oddRowBackgroundStyle.setBackgroundColor(new Color(244, 249, 202));
//            drb.setOddRowBackgroundStyle(oddRowBackgroundStyle);
//            drb.setPrintBackgroundOnOddRows(false);
//            drb.setWhenNoData("no data", detailStyle);
//            drb.setReportName("report");
//           // Page page = new Page();
//           // page.Page_A4_Landscape();
//           // page.setOrientationPortrait(true);
//
//            if(sbrptNameList.contains("totalItems"))
//  {
//  drb.addGlobalFooterVariable(s[0], DJCalculation.COUNT ,detailStyle, new DJValueFormatter() {
//				
//				public String getClassName() {
//					return String.class.getName();
//				}
//				
//				public Object evaluate(Object value, Map fields, Map variables, Map parameters) {
//					
//					return "Total Items :"+value;
//				}
//			});
//  }
//            dr = drb.build(); //Finally build the report!      
//
//        } catch (ColumnBuilderException ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jarray.toString().getBytes());
//
//        JsonDataSource ds = new JsonDataSource(jsonDataStream);
//        JasperPrint jp = null;
//        try {
//            jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
//           jp.setPageWidth((int) (96 * Double.parseDouble(pageProperties.get("PageWidth").toString())));
//            jp.setPageHeight((int) (96 *  Double.parseDouble(pageProperties.get("PageHeight").toString())));
//
//        } catch (JRException ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
////        String xmlString = null;
////        try {
////            xmlString = JasperExportManager.exportReportToXml(jp);
////        } catch (JRException ex) {
////            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
////        }
//      
//      //  JasperExportManager.exportReportToPdfFile(jp, filePath + "/memReport.pdf");
//   
//      //  File file = new File(System.getProperty("java.io.tmpdir") + "/memReport.pdf");
//       //  JasperExportManager.exportReportToPdfFile(jp, System.getProperty("user.home") + "/Desktop/reports/mem13.pdf");
//       ReportExporter.exportReportPlainXls(jp, filePath+"/report.xls");
//
//      FileInputStream fileInputStream = new FileInputStream(filePath+"/report.xls");        javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
//  //  responseBuilder.type("application/pdf");
//    responseBuilder.header("Content-Disposition","attachment;filename=report.xls");
//            //File file = new File(filePath+"/mem.html"); 
//        //file.delete();
//       return responseBuilder.build();
//        }
//    
//    //   @GET
//    // @Path("generateDynamicColreports")
//
//    public String generateDynamicColreports(JsonArray jarray, JsonObject jobj, JsonObject jobj1, JsonObject jobj2) throws JRException, ParseException, JSONException, FileNotFoundException, ClassNotFoundException {
//        String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
//        System.out.println("indFilePath  " + filePath);
//        DynamicReportBuilder drb = new DynamicReportBuilder();
//        Style detailStyle = new Style();
//        detailStyle.setFont(Font.COURIER_NEW_BIG);
//        //detailStyle.setBorder(Border.PEN_1_POINT());
//        detailStyle.setTransparency(Transparency.OPAQUE);
//// detailStyle.setTextColor(Color.yellow);
//
//        Style headerStyle = new Style();
//
//        headerStyle.setBackgroundColor(new Color(51, 153, 255));
//        headerStyle.setBorder(Border.PEN_1_POINT());
//        headerStyle.setTextColor(Color.white);
//        headerStyle.setBorderColor(Color.black);
////headerStyle.setHorizontalAlign(HorizontalAlign.);
//        headerStyle.setTransparency(Transparency.OPAQUE);
//
//        /**
//         * "titleStyle" exists in the template .jrxml file The title should be
//         * seen in a big font size, violet foreground and light green background
//         */
//        Style titleStyle = Style.createBlankStyle("titleStyle");
////titleStyle.setBackgroundColor(Color.BLUE);
//        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
//        titleStyle.setFont(Font.ARIAL_BIG);
//        titleStyle.setTransparency(Transparency.OPAQUE);
//        /**
//         * "subtitleStyleParent" is meant to be used as a parent style, while
//         * "subtitleStyle" is the child.
//         */
//        Style subtitleStyleParent = Style.createBlankStyle("subtitleParent");
//        subtitleStyleParent.setBackgroundColor(Color.GREEN);
//        subtitleStyleParent.setTransparency(Transparency.OPAQUE);
//
//        Style subtitleStyle = Style.createBlankStyle("subtitleStyle");
//        subtitleStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
//        subtitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
//
//        Style amountStyle = new Style();
//        amountStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
//
//        drb.setTitle("REPORT") //defines the title of the report
//                .setSubtitle("Library collection having ")
//                .setDetailHeight(20) //defines the height for each record of the report
//                .setMargins(30, 20, 30, 15) //define the margin space for each side (top, bottom, left and right)
//                .setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
//                .setColumnsPerPage(1);
//
////FastReportBuilder drb = new FastReportBuilder();
//        DynamicReport dr = null;
////JsonObject jobj = biblidetailsLocationFacadeREST.getBibliRecordsall("department","3","18","''","''");
//        List<String> titleList = new ArrayList<>();
//        for (String key : jobj.keySet()) {
//            titleList.add(key);
//
//        }
//
//        AbstractColumn[] s = new AbstractColumn[titleList.size()];
//        String titleListVal = null;
//        for (int i = 0; i < titleList.size(); i++) {
//            titleListVal = titleList.get(i);
//            Style colStyle = new Style();
//            // colStyle.createBlankStyle("colStyle");
//            // colStyle.setPaddingLeft(Integer.parseInt(jobj1.get(titleListVal+"_left").toString()));
//            //   colStyle.setBackgroundColor(Color.PINK);
//
//            s[i] = ColumnBuilder.getNew() //creates a new instance of a ColumnBuilder
//                    .setColumnProperty(titleListVal, String.class.getName()) //defines the field of the data source that this column will show, also its type
//                    .setTitle(titleListVal) //the title for the column
//                    .setWidth(Integer.parseInt(jobj1.get(titleListVal + "_width").toString())) //the width of the column
//                    //   .setStyle(colStyle)
//                    .build();
//
//            s[i].setPosX(Integer.parseInt(jobj1.get(titleListVal + "_left").toString()));
//            s[i].setPosY(Integer.parseInt(jobj1.get(titleListVal + "_top").toString()));
//            drb.addColumn(s[i]);
//        }
//        try {
//            // for(int ij=0;ij<titleList.size();ij++)
//            //   {       
//            // s[ij].setPosX(133);
//
//            //  }
//            /**
//             * add some more options to the report (through the builder)
//             */
////drb.addBarcodeColumn("Bar-Code", "recId", Long.class.getName(), BarcodeTypes.USD3, true, false,null, 100, true, ImageScaleMode.FILL, null);
//            drb.setUseFullPageWidth(true);  //we tell the report to use the full width of the page. this rezises
//            //the columns width proportionally to meat the page width.
//
//            Style oddRowBackgroundStyle = new Style();
//            oddRowBackgroundStyle.setBackgroundColor(new Color(244, 249, 202));
//            drb.setOddRowBackgroundStyle(oddRowBackgroundStyle);
//            drb.setPrintBackgroundOnOddRows(true);
//            drb.setWhenNoData("no data", detailStyle);
//            Page page = new Page();
//            page.Page_A4_Landscape();
//            page.setOrientationPortrait(true);
//
//            dr = drb.build(); //Finally build the report!      
//
//        } catch (ColumnBuilderException ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jarray.toString().getBytes());
//
//        JsonDataSource ds = new JsonDataSource(jsonDataStream);
//        JasperPrint jp = null;
//        try {
//            jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
//            jp.setPageWidth(96 * Integer.parseInt(jobj2.get("PageWidth").toString()));
//            jp.setPageHeight(96 * Integer.parseInt(jobj2.get("PageHeight").toString()));
//
//        } catch (JRException ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        String xmlString = null;
//        try {
//            xmlString = JasperExportManager.exportReportToXml(jp);
//        } catch (JRException ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //JasperExportManager.exportReportToHtmlFile(jp, xmlString);
//        JasperExportManager.exportReportToPdfFile(jp, System.getProperty("user.home") + "/Desktop/reports/mem13.pdf");
//        // String reportDest = "F:/SRA29012018/SoulRestApp/web/jsp/catalogue/reportFile/mem156.html";
//        // String htmlDest = System.getProperty("user.home") +"/Desktop/mem.html";
//        // String reportDest1 = "F:/SRA29012018/SoulRestApp/web/reportFile/mem156.html";
////java.io.tmpdir
//        //  JasperExportManager.exportReportToHtmlFile(jp, System.getProperty("user.home") +"/Desktop/reports/mem156.html");
//
//        JasperExportManager.exportReportToHtmlFile(jp, filePath + "/mem.html");
//        JasperExportManager.exportReportToXmlFile(jp, System.getProperty("user.home") + "/Desktop/mem.xml", true);
//        //   JasperExportManager.exportReportToHtmlFile(jp, reportDest1);
//
//        ReportExporter.exportReportPlainXls(jp, System.getProperty("user.home") + "/Desktop/reports/mem15.xls");
//
//        String ht = null;
//        FileReader fr = new FileReader(filePath + "/mem.html");
//        BufferedReader br = new BufferedReader(fr);
//        StringBuilder content = new StringBuilder(1024);
//
//        try {
//            while ((ht = br.readLine()) != null) {
//                content.append(ht);
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                br.close();
//                fr.close();
//            } catch (IOException ex) {
//                Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
//        //   System.out.println("ht...  "+content.toString());
//        //File file = new File(filePath+"/mem.html"); 
//        //file.delete();
//        return content.toString();
//        // JasperViewer.viewReport(jp);    //finally display the report report
//
//// @GET
////@Path("/pdf")
////@Produces("application/pdf")
////public javax.ws.rs.core.Response getPdf() throws Exception
////{
////    File file = new File("E:\\tmp\\test.pdf");
////    FileInputStream fileInputStream = new FileInputStream(file);
////    javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
////    responseBuilder.type("application/pdf");
////    responseBuilder.header("Content-Disposition", "filename=test.pdf");
////    return responseBuilder.build();
////}
//    }
//    
//     @GET
//@Path("pdf")
//@Produces("application/pdf")
//public javax.ws.rs.core.Response getPdf() throws Exception
//{
//    File file = new File("D:\\67.pdf");
//    FileInputStream fileInputStream = new FileInputStream(file);
//    javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
//   responseBuilder.header("Content-Disposition","attachment; filename=new-android-book.pdf");
//   return responseBuilder.build();
//}
    @GET
    @Path("basicReport/{paramName}/{paramValue}/{paramValueDesc}/{reportTemplateId}/{dates}/{materialCd}")
    public String basicReport(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue, @PathParam("reportTemplateId") String reportTemplateId,
            @PathParam("paramValueDesc") String paramValueDesc, @PathParam("materialCd") String materialCd, @PathParam("dates") String dates)
            throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException {
        JsonObjectBuilder objectBuilderFinal = Json.createObjectBuilder();
        // JsonObjectBuilder objectBuilderFinal1 = Json.createObjectBuilder();
        // JsonObjectBuilder objectBuilderFinal2 = Json.createObjectBuilder();
        // JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        String paramQuery = "";
        String paramQueryAdd = "";
        String[] date = dates.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String paramValueSplit[] = paramValue.split(",");

        if ((!materialCd.contentEquals("''")) && dates.contentEquals("''")) {
            paramQueryAdd = "and l.Material = '" + materialCd + "'";
        }
        if (materialCd.contentEquals("''") && (!dates.contentEquals("''"))) {
            Date date1 = dateFormat.parse(date[0]);
            Date date2 = dateFormat.parse(date[1]);
            paramQueryAdd = "and l.dateOfAcq BETWEEN '" + new java.sql.Date(date1.getTime()) + "' AND '" + new java.sql.Date(date2.getTime()) + "'";
        }

        if ((!materialCd.equals("''")) && (!dates.equals("''"))) {
            Date date1 = dateFormat.parse(date[0]);
            Date date2 = dateFormat.parse(date[1]);
            paramQueryAdd = "and l.Material = '" + materialCd + " 'and l.dateOfAcq BETWEEN '" + new java.sql.Date(date1.getTime()) + "' AND '" + new java.sql.Date(date2.getTime()) + "'";
        }

        switch (paramName) {
            case "subject":
                paramQuery = "(SELECT c.RecID from Biblidetails c join Location l  on c.RecID = l.RecID\n"
                        + "where c.Tag LIKE '6%' and c.SbFld = 'a' and c.FValue LIKE '" + paramValueSplit[0] + "%' " + paramQueryAdd + ")";
                break;
            case "language":
                paramQuery = "(SELECT c.RecID from Biblidetails c join Location l  on c.RecID = l.RecID\n"
                        + "where c.Tag = '008' and  c.FValue LIKE '%" + paramValueSplit[0] + "%' " + paramQueryAdd + ")";
                break;
//               case "author":
//                    paramQuery = "(select RecID from Location where department = '"+paramValue+"' "+paramQueryAdd+")";
//                    break;
            case "department":
                paramQuery = "(select RecID from Location l where l.department = '" + paramValueSplit[0] + "' " + paramQueryAdd + ")";
                break;
            case "classNo":
                paramQuery = "(select RecID from Location l where LTRIM(l.k852) LIKE '" + paramValueSplit[0] + "%' " + paramQueryAdd + " )";
                break;
            case "title":
                paramQuery = "( Select distinct b.RecID from Biblidetails b LEFT OUTER JOIN "
                        + "(Select * from Biblidetails  where Tag = 245 and SbFld = 'a')a ON a.RecID = b.RecID "
                        + " LEFT OUTER JOIN  Location l On a.RecID = l.RecID "
                        + "where LTRIM(a.FValue) LIKE '" + paramValueSplit[0] + "%' " + paramQueryAdd + " )";
                break;
            case "collectionType":
                paramQuery = "( select distinct l.RecID from Location l where LTRIM(l.b852) LIKE '" + paramValueSplit[0] + "%' " + paramQueryAdd + " )";
                break;
            case "shelvingLocation":
                paramQuery = "(select distinct l.RecID from Location l where LTRIM(l.c852) LIKE '" + paramValueSplit[0] + "%' " + paramQueryAdd + " )";
                break;
            case "location":
                paramQuery = "(select distinct l.RecID from Location  l where LTRIM(l.a852) LIKE '" + paramValueSplit[0] + "%' " + paramQueryAdd + " )";
                break;
            case "accessionNo":
                paramQuery = "(select distinct l.RecID from Location  l where l.p852 =  '" + paramValueSplit[0] + "' " + paramQueryAdd + " )";
                break;
            case "accessionNos":
                System.out.println("paramValueSplit  " + paramValueSplit[0]);
                String getParamValues = "";
                for (int p = 0; p < paramValueSplit.length; p++) {
                    getParamValues = getParamValues + "'" + paramValueSplit[p] + "'";
                    if (p < (paramValueSplit.length - 1)) {
                        getParamValues = getParamValues + ",";
                    }
                }
                System.out.println("getParamValues " + getParamValues);
                paramQuery = "(select distinct l.RecID from Location  l where l.p852 in  (" + getParamValues + ")   " + paramQueryAdd + ")";
                break;
            case "accessionRange":
                List<String> accList = new ArrayList<>();
                int fromVal = Integer.parseInt(paramValueSplit[1]);
                int toVal = Integer.parseInt(paramValueSplit[2]);

                for (int i = fromVal; i <= toVal; i++) {
                    accList.add("'" + paramValueSplit[0] + i + "'");
                }
                String accArray = StringUtils.join(accList, ',');

                paramQuery = "(select distinct l.RecID from Location l where l.p852   in  (" + accArray + ") " + paramQueryAdd + " )";
                break;
            case "recIdRange":
                paramQuery = "(select distinct l.RecID from Location l where l.RecID BETWEEN  '" + paramValueSplit[0] + "' AND  '" + paramValueSplit[1] + "'   )";
                break;
            case "username":
                paramQuery = "( select distinct l.RecID from Location l where LTRIM(l.UserName) LIKE  '" + paramValueSplit[0] + "'  " + paramQueryAdd + " )";
                break;
            case "codedLocation":
                paramQuery = "(select distinct b.RecId from BibliDetails b, Location l Where b.RecId=l.RecId "
                        + " and l.f852 = '" + paramValueSplit[0] + "' " + paramQueryAdd + ")";
                break;
        }

        System.out.println("paramQuery ............." + paramQuery);
        System.out.println("reportTemplateId .........." + reportTemplateId);
        List<Rpttemplate> rpttemplateList = null;
        List<String> sbrptNameList = new ArrayList<>(); // to store column names 
        rpttemplateList = rpttemplateFacadeREST.getRptByTempId(reportTemplateId); // to get list by template id
        System.out.println("rpttemplateList........." + rpttemplateList.size());
        // get all sbrptnames...
        for (int i = 0; i < rpttemplateList.size(); i++) {
            System.out.println("iiiiiiiiiiii  " + i);
            sbrptNameList.add(rpttemplateList.get(i).getRpttemplatePK().getRptSbrptName());
        }
        sbrptNameList.remove("totalItems");
        System.out.println("sbrptNameList......  " + sbrptNameList);
        for (int m = 0; m < sbrptNameList.size(); m++) {
            System.out.println("mmmmmmmmmmm  " + m);
            JsonObject job = getReportByFieldName(sbrptNameList.get(m), paramQuery).build();
            objectBuilderFinal.add(sbrptNameList.get(m), job);
            System.out.println("sbrptNameList.get(m) "+sbrptNameList.get(m)+ "   "+job.toString());
            // to set height,width,top,left
            // objectBuilderFinal1.add(sbrptNameList.get(m)+"_height",Integer.valueOf(rpttemplateList.get(m).getRptHeight()));
            //  objectBuilderFinal1.add(sbrptNameList.get(m)+"_width",Integer.valueOf(rpttemplateList.get(m).getRptWidth()));
            //  objectBuilderFinal1.add(sbrptNameList.get(m)+"_top",Integer.valueOf(rpttemplateList.get(m).getRptTop()));
            // objectBuilderFinal1.add(sbrptNameList.get(m)+"_left",Integer.valueOf(rpttemplateList.get(m).getRptLeft()));

        }

        // to set template height , width
        //  objectBuilderFinal2.add("PageHeight",Integer.valueOf(reporttemplatemainFacadeREST.find(Integer.parseInt(reportTemplateId)).getHeight()));
        //  objectBuilderFinal2.add("PageWidth", Integer.valueOf(reporttemplatemainFacadeREST.find(Integer.parseInt(reportTemplateId)).getWidth()));
        JsonObject jobj = objectBuilderFinal.build();
        //  JsonObject jobj1 = objectBuilderFinal1.build();
        //  JsonObject jobj2 = objectBuilderFinal2.build();

        System.out.println("objectBuilderFinal ..... " + jobj);
        //  System.out.println("objectBuilderFinal1 ..... "+jobj1);
        //   System.out.println("objectBuilderFinal2 ..... "+jobj2);
        //  JsonArray jarray =   getReportJsonData(jobj);
        // setjArray(jarray);
        // String jsondataReport =  getReportJsonData(jobj).toString();
        // servletContext.setAttribute("jsondataReport", jsondataReport);
        String jsonString = getReportJsonData(jobj).toString();
        System.out.println("jsonString ..... " + jsonString);
        try {
            getJasperPrint(jsonString, reportTemplateId, paramValueDesc);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonString;
    }

//    @GET
//    @Path("printService")
//  public void printService() throws JRException
//    {
//         long start = System.currentTimeMillis();
//    PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
//    printRequestAttributeSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
//
//    PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
//  //  printServiceAttributeSet.add(new PrinterName("Epson Stylus 820 ESC/P 2", null));
//    //printServiceAttributeSet.add(new PrinterName("hp LaserJet 1320 PCL 6", null));
//    printServiceAttributeSet.add(new PrinterName("Microsoft Print to PDF", null));
//    
//     GenerateJasper generateJasper = new GenerateJasper();
//     JasperPrint jasperPrint = null;
//    try {
//       
//        jasperPrint = generateJasper.getJsonData("classNo", "000", "classNo_as_000", "18", "", "");
//
//    } catch (Exception e) {
//        System.out.println("eroorrr  " + e);
//    }
//
//     JRPrintServiceExporter exporterp = new JRPrintServiceExporter();
//	exporterp.setExporterInput(new SimpleExporterInput(jasperPrint));
//	SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
//	configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
//	configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
//	configuration.setDisplayPageDialog(false);
//	configuration.setDisplayPrintDialog(true);
//	exporterp.setConfiguration(configuration);
//	exporterp.exportReport();
//    }
//    
    @Context
    private ServletContext servletContext;

    @GET
    @Path("generateReportPdf/{paramName}/{paramValue}/{paramValueDesc}/{reportTemplateId}/{dates}/{materialCd}")
    @Produces("application/pdf")
    public Response generateReportPdf(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue, @PathParam("paramValueDesc") String paramValueDesc, @PathParam("reportTemplateId") String reportTemplateId,
            @PathParam("materialCd") String materialCd, @PathParam("dates") String dates)
            throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException {
        JasperPrint jp = null;
        System.out.println("11 " + paramName);
        System.out.println("11 " + paramValue);
        System.out.println("11 " + paramValueDesc);
        System.out.println("11 " + reportTemplateId);
        System.out.println("11 " + materialCd);
        System.out.println("11 " + dates);

        String jsondata, columnNames, columnPropertiesString, pagePropertiesString;

        jsondata = basicReport(paramName, paramValue, reportTemplateId, paramValueDesc, materialCd, dates);
        columnNames = getColumnnNames(reportTemplateId);
        columnPropertiesString = getColumnPropertiesFromTemplate(reportTemplateId);
        pagePropertiesString = getPagePropertiesFromTemplate(reportTemplateId);

        JsonReader readerColProp = Json.createReader(new StringReader(columnPropertiesString));
        JsonStructure jsonstColProp = readerColProp.read();
        JsonObject columnProperties = (JsonObject) jsonstColProp;

        JsonReader readerPageProp = Json.createReader(new StringReader(pagePropertiesString));
        JsonStructure jsonstPageProp = readerPageProp.read();
        JsonObject pageProperties = (JsonObject) jsonstPageProp;

        List<String> sbrptNameList = new ArrayList<String>(); // store columnNames as List
        String[] columnNameArray = columnNames.split(",");
        for (int i = 0; i < columnNameArray.length; i++) {
            sbrptNameList.add(columnNameArray[i]);
        }

        List<String> titleList = new ArrayList<String>();  // create list for column titles
        titleList.addAll(sbrptNameList);
        titleList.remove("totalItems"); // to be shown at the end of report

        // creating template and setting properties
        DynamicReportBuilder drb = new DynamicReportBuilder();

        // detail style - applicable to all rows
        Style detailStyle = new Style();
        detailStyle.setFont(Font.COURIER_NEW_BIG);
        detailStyle.setTransparency(Transparency.OPAQUE);

        // header style
        Style headerStyle = new Style();
        headerStyle.setBackgroundColor(new Color(0, 0, 0));
        headerStyle.setBorder(Border.PEN_1_POINT());
        headerStyle.setTextColor(Color.white);
        headerStyle.setBorderColor(Color.black);
        headerStyle.setFont(Font.TIMES_NEW_ROMAN_MEDIUM_BOLD);
        //headerStyle.setHorizontalAlign(HorizontalAlign.);
        headerStyle.setTransparency(Transparency.OPAQUE);

        // set title style - applicable to column names
        Style titleStyle = Style.createBlankStyle("titleStyle");
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(Font.ARIAL_BIG_BOLD);
        titleStyle.setTransparency(Transparency.OPAQUE);
        /**
         * "subtitleStyleParent" is meant to be used as a parent style, while
         * "subtitleStyle" is the child.
         */
        Style subtitleStyleParent = Style.createBlankStyle("subtitleParent");
        subtitleStyleParent.setBackgroundColor(Color.GREEN);
        subtitleStyleParent.setTransparency(Transparency.OPAQUE);

        Style subtitleStyle = Style.createBlankStyle("subtitleStyle");
        subtitleStyle.setFont(Font.ARIAL_BIG_BOLD);
        subtitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);

        Style colStyle = Style.createBlankStyle("colStyle");
        //  colStyle.setBorderBottom(Border.PEN_2_POINT());
        //    colStyle.setBorderTop(Border.PEN_2_POINT());
        //   colStyle.setBorderColor(Color.BLACK);
        Font font = new Font();
        font.setFontSize(12);
        font.setBold(true);
        colStyle.setFont(font);
        colStyle.setVerticalAlign(VerticalAlign.TOP);
        colStyle.setStretchWithOverflow(true);
        // colStyle.setPadding(1);

        drb.setTitle("REPORT") //defines the title of the report
                .setSubtitle("Library collection having " + URLDecoder.decode(paramValueDesc, "UTF-8"))
                .setDetailHeight(20) //defines the height for each record of the report
                .setMargins(30, 20, 30, 15) //define the margin space for each side (top, bottom, left and right)
                .setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
                .setColumnsPerPage(1);

        //FastReportBuilder drb = new FastReportBuilder();
        DynamicReport dr = null;

        AbstractColumn[] s = new AbstractColumn[titleList.size()];
        String titleListVal = null;
        for (int i = 0; i < titleList.size(); i++) {
            titleListVal = titleList.get(i);

            s[i] = ColumnBuilder.getNew() //creates a new instance of a ColumnBuilder
                    .setColumnProperty(titleListVal, String.class.getName()) //defines the field of the data source that this column will show, also its type
                    .setTitle(titleListVal.toUpperCase()) //the title for the column
                    .setWidth(Integer.parseInt(columnProperties.get(titleListVal + "_width").toString())) //the width of the column
                    .setStyle(colStyle)
                    .build();

            s[i].setPosX(Integer.parseInt(columnProperties.get(titleListVal + "_left").toString()));
            s[i].setPosY(Integer.parseInt(columnProperties.get(titleListVal + "_top").toString()));
            drb.addColumn(s[i]);
        }
        try {
            /**
             * add some more options to the report (through the builder)
             */
//drb.addBarcodeColumn("Bar-Code", "recId", Long.class.getName(), BarcodeTypes.USD3, true, false,null, 100, true, ImageScaleMode.FILL, null);
            drb.setUseFullPageWidth(true);  //we tell the report to use the full width of the page. this rezises
            //the columns width proportionally to meat the page width.

            Style oddRowBackgroundStyle = new Style();
            oddRowBackgroundStyle.setBackgroundColor(new Color(222, 222, 222));
            drb.setOddRowBackgroundStyle(oddRowBackgroundStyle);
            drb.setPrintBackgroundOnOddRows(true);
            Style atStyle2 = new StyleBuilder(true).setFont(new Font(12, Font._FONT_ARIAL, false, true, false)).setTextColor(Color.BLACK).build();

            drb.addAutoText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT, 100, atStyle2);
            drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_CENTER, 100, 40, atStyle2);
//            Page page = new Page();
//            page.Page_A4_Landscape();
//            page.setOrientationPortrait(true);
            //drb.addGlobalFooterVariable(s[0], "Tota");

            if (sbrptNameList.contains("totalItems")) {
                drb.addGlobalFooterVariable(s[0], DJCalculation.COUNT, detailStyle, new DJValueFormatter() {

                    public String getClassName() {
                        return String.class.getName();
                    }

                    public Object evaluate(Object value, Map fields, Map variables, Map parameters) {

                        return "Total Items :" + value;
                    }
                });
            }
            dr = drb.build(); //Finally build the report!  

//drb.setReportLocale(new Locale("en","IN"));
        } catch (ColumnBuilderException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }

        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jsondata.getBytes());

        JsonDataSource ds = null;
        try {
            ds = new JsonDataSource(jsonDataStream);
        } catch (JRException ex) {
          //  Logger.getLogger(GenerateJasper.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);

            jp.setPageWidth((int) (96 * Double.parseDouble(pageProperties.get("PageWidth").toString())));
            jp.setPageHeight((int) (96 * Double.parseDouble(pageProperties.get("PageHeight").toString())));

        } catch (JRException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("jp...... " + jp.getPages().size());
        String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
        System.out.println("filePath  .. " + filePath);
        JasperExportManager.exportReportToPdfFile(jp, filePath + "/report.pdf");

        FileInputStream fileInputStream = new FileInputStream(filePath + "/report.pdf");
        javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
        //  responseBuilder.type("application/pdf");
        responseBuilder.header("Content-Disposition", "attachment; filename=report.pdf");
        //File file = new File(filePath+"/mem.html"); 
        //file.delete();
        return responseBuilder.build();
    }

//       @GET
//   @Path("generateReportExcel/{paramName}/{paramValue}/{paramValueDesc}/{reportTemplateId}/{dates}/{materialCd}")
//    @Produces("application/vnd.ms-excel")
//    public Response generateReportExcel(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue,@PathParam("paramValueDesc") String paramValueDesc, @PathParam("reportTemplateId") String reportTemplateId, @PathParam("materialCd") String materialCd, @PathParam("dates") String dates) throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException 
    public JasperPrint generateJasperForPdf(String jsondata, String columnNames, String columnPropertiesString,
            String pagePropertiesString, String reportTitle, String reportSubtitle) throws UnsupportedEncodingException {
        JasperPrint jp = null;
        JsonReader readerColProp = Json.createReader(new StringReader(columnPropertiesString));
        JsonStructure jsonstColProp = readerColProp.read();
        JsonObject columnProperties = (JsonObject) jsonstColProp;

        JsonReader readerPageProp = Json.createReader(new StringReader(pagePropertiesString));
        JsonStructure jsonstPageProp = readerPageProp.read();
        JsonObject pageProperties = (JsonObject) jsonstPageProp;

        List<String> sbrptNameList = new ArrayList<String>(); // store columnNames as List
        String[] columnNameArray = columnNames.split(",");
        for (int i = 0; i < columnNameArray.length; i++) {
            sbrptNameList.add(columnNameArray[i]);
        }

        List<String> titleList = new ArrayList<String>();  // create list for column titles
        titleList.addAll(sbrptNameList);
        titleList.remove("totalItems"); // to be shown at the end of report

        // creating template and setting properties
        DynamicReportBuilder drb = new DynamicReportBuilder();

        // detail style - applicable to all rows
        Style detailStyle = new Style();
        detailStyle.setFont(Font.COURIER_NEW_BIG);
        detailStyle.setTransparency(Transparency.OPAQUE);

        // header style
        Style headerStyle = new Style();
        headerStyle.setBackgroundColor(new Color(0, 0, 0));
        headerStyle.setBorder(Border.PEN_1_POINT());
        headerStyle.setTextColor(Color.white);
        headerStyle.setBorderColor(Color.black);
        headerStyle.setFont(Font.TIMES_NEW_ROMAN_MEDIUM_BOLD);
        //headerStyle.setHorizontalAlign(HorizontalAlign.);
        headerStyle.setTransparency(Transparency.OPAQUE);

        // set title style - applicable to column names
        Style titleStyle = Style.createBlankStyle("titleStyle");
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(Font.ARIAL_BIG_BOLD);
        titleStyle.setTransparency(Transparency.OPAQUE);
        /**
         * "subtitleStyleParent" is meant to be used as a parent style, while
         * "subtitleStyle" is the child.
         */
        Style subtitleStyleParent = Style.createBlankStyle("subtitleParent");
        subtitleStyleParent.setBackgroundColor(Color.GREEN);
        subtitleStyleParent.setTransparency(Transparency.OPAQUE);

        Style subtitleStyle = Style.createBlankStyle("subtitleStyle");
        subtitleStyle.setFont(Font.ARIAL_BIG_BOLD);
        subtitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);

        Style colStyle = Style.createBlankStyle("colStyle");
        //  colStyle.setBorderBottom(Border.PEN_2_POINT());
        //    colStyle.setBorderTop(Border.PEN_2_POINT());
        //   colStyle.setBorderColor(Color.BLACK);
        Font font = new Font();
        font.setFontSize(9);
        font.setBold(true);
        font.setFontName("Arial Unicode MS");
        colStyle.setFont(font);
        colStyle.setVerticalAlign(VerticalAlign.TOP);
        colStyle.setStretchWithOverflow(true);
        // colStyle.setPadding(1);

        drb.setTitle(reportTitle) //defines the title of the report
                .setSubtitle(reportSubtitle)
                .setDetailHeight(20) //defines the height for each record of the report
                .setMargins(96, 96, 96, 96) //define the margin space for each side (top, bottom, left and right)
                .setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
                .setColumnsPerPage(1);

        //FastReportBuilder drb = new FastReportBuilder();
        DynamicReport dr = null;

        AbstractColumn[] s = new AbstractColumn[titleList.size()];
        String titleListVal = null;
        for (int i = 0; i < titleList.size(); i++) {
            titleListVal = titleList.get(i);

            s[i] = ColumnBuilder.getNew() //creates a new instance of a ColumnBuilder
                    .setColumnProperty(titleListVal, String.class.getName()) //defines the field of the data source that this column will show, also its type
                    .setTitle(titleListVal.toUpperCase()) //the title for the column
                    .setWidth(Integer.parseInt(columnProperties.get(titleListVal + "_width").toString())) //the width of the column
                    .setStyle(colStyle)
                    .build();

            s[i].setPosX(Integer.parseInt(columnProperties.get(titleListVal + "_left").toString()));
            s[i].setPosY(Integer.parseInt(columnProperties.get(titleListVal + "_top").toString()));
            drb.addColumn(s[i]);
        }
        try {
            /**
             * add some more options to the report (through the builder)
             */
//drb.addBarcodeColumn("Bar-Code", "recId", Long.class.getName(), BarcodeTypes.USD3, true, false,null, 100, true, ImageScaleMode.FILL, null);
            drb.setUseFullPageWidth(true);  //we tell the report to use the full width of the page. this rezises
            //the columns width proportionally to meat the page width.

            Style oddRowBackgroundStyle = new Style();
            oddRowBackgroundStyle.setBackgroundColor(new Color(222, 222, 222));
            drb.setOddRowBackgroundStyle(oddRowBackgroundStyle);
            drb.setPrintBackgroundOnOddRows(true);
            Style atStyle2 = new StyleBuilder(true).setFont(new Font(12, Font._FONT_ARIAL, false, true, false)).setTextColor(Color.BLACK).build();

            drb.addAutoText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT, 100, atStyle2);
            drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_CENTER, 100, 40, atStyle2);
//            Page page = new Page();
//            page.Page_A4_Landscape();
//            page.setOrientationPortrait(true);
            //drb.addGlobalFooterVariable(s[0], "Tota");

            if (sbrptNameList.contains("totalItems")) {
                drb.addGlobalFooterVariable(s[0], DJCalculation.COUNT, detailStyle, new DJValueFormatter() {

                    public String getClassName() {
                        return String.class.getName();
                    }

                    public Object evaluate(Object value, Map fields, Map variables, Map parameters) {

                        return "Total Items :" + value;
                    }
                });
            }
            dr = drb.build(); //Finally build the report!  

//drb.setReportLocale(new Locale("en","IN"));
        } catch (ColumnBuilderException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }

        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jsondata.getBytes("UTF-8"));

        JsonDataSource ds = null;
        try {
            ds = new JsonDataSource(jsonDataStream);
        } catch (JRException ex) {
          //  Logger.getLogger(GenerateJasper.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);

            jp.setPageWidth((int) (96 * Double.parseDouble(pageProperties.get("PageWidth").toString())));
            jp.setPageHeight((int) (96 * Double.parseDouble(pageProperties.get("PageHeight").toString())));

        } catch (JRException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jp;
    }

    public JasperPrint generateJasperForExcel(String jsondata, String columnNames, String columnPropertiesString,
            String pagePropertiesString, String reportTitle, String reportSubtitle) {
        JasperPrint jp = null;
        JsonReader readerColProp = Json.createReader(new StringReader(columnPropertiesString));
        JsonStructure jsonstColProp = readerColProp.read();
        JsonObject columnProperties = (JsonObject) jsonstColProp;

        JsonReader readerPageProp = Json.createReader(new StringReader(pagePropertiesString));
        JsonStructure jsonstPageProp = readerPageProp.read();
        JsonObject pageProperties = (JsonObject) jsonstPageProp;

        List<String> sbrptNameList = new ArrayList<String>(); // store columnNames as List
        String[] columnNameArray = columnNames.split(",");
        for (int i = 0; i < columnNameArray.length; i++) {
            sbrptNameList.add(columnNameArray[i]);
        }

        List<String> titleList = new ArrayList<String>();  // create list for column titles
        titleList.addAll(sbrptNameList);
        titleList.remove("totalItems"); // to be shown at the end of report

        // creating template and setting properties
        DynamicReportBuilder drb = new DynamicReportBuilder();

        // detail style - applicable to all rows
        Style detailStyle = new Style();
        detailStyle.setFont(Font.COURIER_NEW_BIG);
        detailStyle.setTransparency(Transparency.OPAQUE);

        // header style
        Style headerStyle = new Style();
        headerStyle.setBackgroundColor(new Color(0, 0, 0));
        headerStyle.setBorder(Border.PEN_1_POINT());
        headerStyle.setTextColor(Color.white);
        headerStyle.setBorderColor(Color.black);
        headerStyle.setFont(Font.TIMES_NEW_ROMAN_MEDIUM_BOLD);
        //headerStyle.setHorizontalAlign(HorizontalAlign.);
        headerStyle.setTransparency(Transparency.OPAQUE);

        // set title style - applicable to column names
        Style titleStyle = Style.createBlankStyle("titleStyle");
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(Font.ARIAL_BIG_BOLD);
        titleStyle.setTransparency(Transparency.OPAQUE);
        /**
         * "subtitleStyleParent" is meant to be used as a parent style, while
         * "subtitleStyle" is the child.
         */
        Style subtitleStyleParent = Style.createBlankStyle("subtitleParent");
        subtitleStyleParent.setBackgroundColor(Color.GREEN);
        subtitleStyleParent.setTransparency(Transparency.OPAQUE);

        Style subtitleStyle = Style.createBlankStyle("subtitleStyle");
        subtitleStyle.setFont(Font.ARIAL_BIG_BOLD);
        subtitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);

        Style colStyle = Style.createBlankStyle("colStyle");
        //  colStyle.setBorderBottom(Border.PEN_2_POINT());
        //    colStyle.setBorderTop(Border.PEN_2_POINT());
        //   colStyle.setBorderColor(Color.BLACK);
        Font font = new Font();
        font.setFontSize(9);
        font.setBold(true);
        colStyle.setFont(font);
        colStyle.setStretchWithOverflow(true);
        colStyle.setVerticalAlign(VerticalAlign.TOP);
        // colStyle.setPadding(1);

        drb.setTitle(reportTitle) //defines the title of the report
                // .setSubtitle("Library collection having "+URLDecoder.decode(paramValueDesc, "UTF-8"))
                .setSubtitle(reportSubtitle)
                .setDetailHeight(20) //defines the height for each record of the report
                .setMargins(96, 96, 96, 96) //define the margin space for each side (top, bottom, left and right)
                .setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
                .setColumnsPerPage(1);

        //FastReportBuilder drb = new FastReportBuilder();
        DynamicReport dr = null;

        AbstractColumn[] s = new AbstractColumn[titleList.size()];
        String titleListVal = null;
        for (int i = 0; i < titleList.size(); i++) {
            titleListVal = titleList.get(i);

            s[i] = ColumnBuilder.getNew() //creates a new instance of a ColumnBuilder
                    .setColumnProperty(titleListVal, String.class.getName()) //defines the field of the data source that this column will show, also its type
                    .setTitle(titleListVal.toUpperCase()) //the title for the column
                    .setWidth(Integer.parseInt(columnProperties.get(titleListVal + "_width").toString())) //the width of the column
                    .setStyle(colStyle)
                    .build();

            s[i].setPosX(Integer.parseInt(columnProperties.get(titleListVal + "_left").toString()));
            s[i].setPosY(Integer.parseInt(columnProperties.get(titleListVal + "_top").toString()));
            drb.addColumn(s[i]);
        }
        try {
            /**
             * add some more options to the report (through the builder)
             */
//drb.addBarcodeColumn("Bar-Code", "recId", Long.class.getName(), BarcodeTypes.USD3, true, false,null, 100, true, ImageScaleMode.FILL, null);
            drb.setIgnorePagination(true); //for Excel, we may dont want pagination, just a plain list
            drb.setMargins(0, 0, 0, 0);
            drb.setUseFullPageWidth(true);  //we tell the report to use the full width of the page. this rezises
            //the columns width proportionally to meat the page width.

            Style oddRowBackgroundStyle = new Style();
            oddRowBackgroundStyle.setBackgroundColor(Color.LIGHT_GRAY);
            drb.setOddRowBackgroundStyle(oddRowBackgroundStyle);
            drb.setPrintBackgroundOnOddRows(true);
            //Style atStyle2 = new StyleBuilder(true).setFont(new Font(12, Font._FONT_ARIAL, false, true, false)).setTextColor(Color.BLUE).build();

            //    drb.addAutoText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()),AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT,100,atStyle2);
            //   drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_CENTER,100,40,atStyle2);
//            Page page = new Page();
//            page.Page_A4_Landscape();
//            page.setOrientationPortrait(true);
            //drb.addGlobalFooterVariable(s[0], "Tota");
            if (sbrptNameList.contains("totalItems")) {
                drb.addGlobalFooterVariable(s[0], DJCalculation.COUNT, detailStyle, new DJValueFormatter() {

                    public String getClassName() {
                        return String.class.getName();
                    }

                    public Object evaluate(Object value, Map fields, Map variables, Map parameters) {

                        return "Total Items :" + value;
                    }
                });
            }
            dr = drb.build(); //Finally build the report!  

//drb.setReportLocale(new Locale("en","IN"));
        } catch (ColumnBuilderException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }

        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jsondata.getBytes());

        JsonDataSource ds = null;
        try {
            ds = new JsonDataSource(jsonDataStream);
        } catch (JRException ex) {
           // Logger.getLogger(GenerateJasper.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);

            jp.setPageWidth((int) (96 * Double.parseDouble(pageProperties.get("PageWidth").toString())));
            jp.setPageHeight((int) (96 * Double.parseDouble(pageProperties.get("PageHeight").toString())));

        } catch (JRException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jp;
    }

//    @GET
//   @Path("generateBasicReportExcel/{paramName}/{paramValue}/{paramValueDesc}/{reportTemplateId}/{dates}/{materialCd}")
//        @Produces("application/vnd.ms-excel")
//    public Response generateBasicReportExcel(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue,@PathParam("paramValueDesc") String paramValueDesc, 
//            @PathParam("reportTemplateId") String reportTemplateId, @PathParam("materialCd") String materialCd, @PathParam("dates") String dates) throws ParseException, JRException, JSONException, FileNotFoundException, UnsupportedEncodingException, ClassNotFoundException  
//     @GET
//   @Path("generateBasicReportPdf/{paramName}/{paramValue}/{paramValueDesc}/{reportTemplateId}/{dates}/{materialCd}")
//        @Produces("application/pdf")
//    public Response generateBasicReportPdf(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue,@PathParam("paramValueDesc") String paramValueDesc, 
//            @PathParam("reportTemplateId") String reportTemplateId, @PathParam("materialCd") String materialCd, @PathParam("dates") String dates) throws ParseException, JRException, 
//            JSONException, FileNotFoundException, UnsupportedEncodingException, ClassNotFoundException  
//     @GET
//   @Path("generateAdvancedReportPdf/{tag1}/{sbfld1}/{query1}/{op1}/{tag2}/{sbfld2}/{query2}/{op2}/{tag3}/{sbfld3}/{query3}/{reportTemplateId}")
//   @Produces("application/pdf")
//     public Response generateAdvancedReportPdf(@PathParam("tag1") String tag1, @PathParam("sbfld1") String sbfld1,
//           @PathParam("query1") String query1,@PathParam("op1") String op1,@PathParam("tag2") String tag2,
//            @PathParam("sbfld2") String sbfld2, @PathParam("query2") String query2,
//            @PathParam("tag3") String tag3,
//            @PathParam("sbfld3") String sbfld3, @PathParam("query3") String query3,
//            @PathParam("op2") String op2, @PathParam("reportTemplateId") String reportTemplateId)   
//            throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException 
//     public JasperPrint generateJasperForLabels(String paramName, String paramValue, String checkboxparam , String header,String label,
//             String material,  String dates) throws JRException, ParseException, FileNotFoundException, UnsupportedEncodingException
//     {
//         
//         System.out.println("material .. "+material);
//          System.out.println("dates .. "+dates);
//         
//         MLablespects mlabel = new MLablespects();
//         System.out.println("label ..."+label);
//         mlabel = mLablespectsFacadeREST.find(label);
//         JasperDesign jasDes = new JasperDesign();
//         
//          int noOfRows = mlabel.getNoOfRows();
//            String checkboxparamArray[] = checkboxparam.split(",");
//             List<String> columnList = new ArrayList<>();
//             for(int c=0;c<checkboxparamArray.length;c++)
//                 {
//                     columnList.add(checkboxparamArray[c]);
//                 }
//            columnList.remove("drawLine");
//            columnList.remove("header");
//            
//            int  totalCols =  checkboxparamArray.length;
//            
//            jasDes.setName("Spine Labels");
//            int pageWidth = (int)(mlabel.getPageWidth() * 96);
//            int pageHeight = (int) (mlabel.getPageHeight() * 96);
//            int topMargin = (int) (mlabel.getTopMargin() * 96);
//            int bottomMargin = (int) (mlabel.getBottomMargin() * 96);
//            int rightMargin = (int) (mlabel.getRightMargin() * 96);
//            int leftMargin = (int) (mlabel.getLeftMargin() * 96);
//            int titleBandWidth = 40;
//            int noOfColumns = mlabel.getNoOfColumns();
//            System.out.println("bottom..."+bottomMargin);
//            System.out.println("colspace  .. before.."+mlabel.getColumnSpace());
//            int colSpace = (int) (mlabel.getColumnSpace() * 96);
//              System.out.println("colspace  .. after.."+colSpace);
//               int rowSpace = (int) (mlabel.getRowSpace()* 96);
//              System.out.println("rowspace  .. after.."+rowSpace);
//            int columnWidth = (pageWidth - leftMargin-rightMargin- (colSpace*(noOfColumns)))/noOfColumns;
//            
//            jasDes.setPageWidth(pageWidth);
//            jasDes.setPageHeight(pageHeight);
//            jasDes.setLeftMargin(leftMargin);
//            jasDes.setRightMargin(rightMargin);
//            jasDes.setTopMargin(topMargin);
//            jasDes.setBottomMargin(bottomMargin);
//            jasDes.setColumnWidth(columnWidth);
//            jasDes.setColumnCount(noOfColumns);
//            jasDes.setColumnSpacing(colSpace);
//            
//            
//           
//             
//int totalRowsPerPage = noOfRows * totalCols;
//         System.out.println("totalRowsPerPage "+totalRowsPerPage);
//         System.out.println("pageHeight "+pageHeight);
//         System.out.println("topMargin "+topMargin);
//         System.out.println("bottomMargin "+bottomMargin);
//         System.out.println("titleBandWidth "+titleBandWidth);
//         System.out.println("rowSpace "+rowSpace);
//         System.out.println("rowSpace*(noOfRows) "+rowSpace*(noOfRows));
//         
//  int rowHeight =    (pageHeight - topMargin-bottomMargin-titleBandWidth-(rowSpace*(noOfRows)))/totalRowsPerPage; 
//         System.out.println("rowHeight  .. after.."+rowHeight);
//   int detailHeight =  (rowHeight * totalCols) + rowSpace;
//    
//         System.out.println("totalRowsPerPage : "+totalRowsPerPage);
//           System.out.println("rowHeight : "+rowHeight);
//             System.out.println("detailHeight : "+detailHeight);
//   
//        JRDesignStyle mystyle = new JRDesignStyle();
//            mystyle.setName("mystyle");
////            mystyle.setDefault(true);
////            mystyle.setFontName("DejaVu Sans");
////            //  mystyle.setPdfFontName("DejaVu Sans");
////         // mystyle.setFontSize(14f);
////           mystyle.setPdfFontName("Helvetica");
////            mystyle.setPdfEncoding("CP1252");
////             mystyle.setPdfEmbedded(Boolean.TRUE);
// //mystyle.setDefault(true);
//              mystyle.setFontName("Arial Unicode MS");
//          //   mystyle.setPdfFontName("Arial Unicode MS");
//       //  mystyle.setFontSize(20f);
//       //   mystyle.setPdfFontName("F:\\Soul Routine Backup\\FONT TFF\\arial-unicode-ms\\arialuni(1).ttf");
//      //      mystyle.setPdfFontName("Arial Unicode MS");
//        //   mystyle.setPdfFontName("NewFontFamily");
//          //  mystyle.setPdfEncoding("UTF-8");
//        //  mystyle.setPdfEncoding("Identity-H");
//        //    mystyle.setPdfEmbedded(Boolean.TRUE);
//    //     mystyle.setPdfEncoding("CP1252");
//            jasDes.addStyle(mystyle);
//         System.out.println("mystyle .... "+mystyle.getPdfFontName());
//        //it will add n number of field.
//        
//        for(int i=0; i<columnList.size();i++){     
//            
//            JRDesignField field = new JRDesignField();
//                 field.setName(columnList.get(i));
//                 field.setValueClass(String.class);
//                 jasDes.addField(field);
//        
//        } 
//        
//        JRDesignBand titleBand = new JRDesignBand();
//        titleBand.setHeight(titleBandWidth);
//        
//        JRDesignStaticText titleText = new JRDesignStaticText();
//         titleText.setText("SPINE LABELS");
//       // titleText.setX(0);
//      //  titleText.setY(10);
//        titleText.setWidth(pageWidth);
//        titleText.setHeight(30);
//        titleText.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
//        titleText.setVerticalAlignment(VerticalAlignEnum.MIDDLE);
//        titleText.setFontSize(22f);
//    
//        titleBand.addElement(titleText);
//        jasDes.setTitle(titleBand);
//        
//        JRDesignBand detailBand = new JRDesignBand();
//        detailBand.setHeight(detailHeight);
//        
//        
//     
//        
//        
//        //it will add the data field into report dyanamically
//        
//        int y2 = 0;
//        for(int i=0;i<totalCols;i++){
//        
//       
//        
//        if(checkboxparamArray[i].contentEquals("accessionNoBarcode"))  // is barcode
//            {
//                StandardBarbecueComponent bbcomp = new StandardBarbecueComponent();
//                bbcomp.setType("Code128");
//                bbcomp.setDrawText(false);
//                bbcomp.setChecksumRequired(true);
//                System.out.println("columnWidth .. "+columnWidth);
//                 System.out.println("rowHeight .. "+rowHeight);
//                bbcomp.setBarHeight(200);
//                
//                bbcomp.setBarWidth(10);
//                bbcomp.setEvaluationTimeValue(EvaluationTimeEnum.NOW);
//                bbcomp.setCodeExpression(new JRDesignExpression("$F{"+checkboxparamArray[i]+"}"));
//                
//                JRDesignComponentElement barcode = new JRDesignComponentElement();
//                barcode.setComponent(bbcomp);
//                barcode.setComponentKey(new ComponentKey("http://jasperreports.sourceforge.net/jasperreports/components", "jr", "barbecue"));
//                barcode.setX(0);
//                barcode.setY(y2);
//                barcode.setWidth(columnWidth);
//                barcode.setHeight(rowHeight); 
//                
//                System.out.println("bbcomp  "+bbcomp.getBarWidth());
//                detailBand.addElement(barcode);
//                
//                y2+=rowHeight;
//            } 
//        
//        else if(checkboxparamArray[i].contentEquals("drawLine"))// is line
//            {
//                JRDesignLine line = new JRDesignLine();
//                line.setX(0);
//                line.setY(y2);
//                line.setWidth(columnWidth);
//                 line.setFill(FillEnum.SOLID);
//                detailBand.addElement(line);
//                line.setHeight(1);
//                y2+=rowHeight;
//            }
//            
//            else if(checkboxparamArray[i].contentEquals("header")) // is header
//                      {         
//                        JRDesignTextField tf = new JRDesignTextField();
//       // tf1.setBlankWhenNull(true);
//        tf.setX(0);
//        tf.setY(y2);
//        tf.setWidth(columnWidth);
//        tf.setHeight(rowHeight);
//        tf.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
//        tf.setStyle(mystyle);  
//        //tf.setStretchWithOverflow(true);
//      //  tf.getLineBox().getPen().setLineWidth(1);
//        String headerVal = "\"" + header + "\"";
//        tf.setExpression(new JRDesignExpression(headerVal));
//        detailBand.addElement(tf);
//        
//        y2+=rowHeight;
//                       }
//                    else{
//                    JRDesignTextField tf = new JRDesignTextField();
//                    tf.setX(0);
//        tf.setY(y2);
//        tf.setWidth(columnWidth);
//        tf.setHeight(rowHeight);
//        tf.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
//        tf.setStyle(mystyle); 
//     //  tf.getLineBox().getPen().setLineWidth(1);
////        JRLineBox box = tf.getLineBox();
////        box.getPen().setLineWidth(10F);
//    //    tf.setStretchWithOverflow(true);
//        tf.setExpression(new JRDesignExpression("$F{"+checkboxparamArray[i]+"}"));
//        detailBand.addElement(tf);
//        
//        y2+=rowHeight;
//                    }          
//            
//        }
//        
//      
//        ((JRDesignSection) jasDes.getDetailSection()).addBand(detailBand);
//         
//        // convert jsonArray data into input Stream
//         System.out.println("checkboxparam.ss. "+checkboxparam);
//        String jsonData  = getBibliRecordsforLabels(paramName, paramValue, checkboxparam,  material,dates);
//        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jsonData.getBytes("UTF-8"));
//        // convert into json data source
//        JsonDataSource ds = new JsonDataSource(jsonDataStream);
//        // compile the report
//        JasperReport report = JasperCompileManager.compileReport(jasDes);
//       
//        // print all json data into report
//        JasperPrint jp = JasperFillManager.fillReport(report,null,ds); 
//        
//       return jp;
//     }
//    @GET
//   @Path("generateLabelPdf/{paramName}/{paramValue}/{checkboxparam}/{header}/{label}/{dates}/{materialCd}")
//    @Produces("application/pdf")
//    public Response generateLabelPdf(@PathParam("paramName") String paramName,@PathParam("paramValue") String paramValue,
//           @PathParam("checkboxparam") String checkboxparam,@PathParam("header") String header, 
//           @PathParam("label") String label ,@PathParam("materialCd") String materialCd, @PathParam("dates") String dates) throws ParseException, JRException, 
//            JSONException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException, IOException 
    @GET
    @Path("generateReportExcel/{datasource}/{reportTemplateId}")
    @Produces("application/vnd.ms-excel")
    public Response generateReportExcel(@PathParam("datasource") String datasource, @PathParam("reportTemplateId") String reportTemplateId) throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException {
        JasperPrint jp = null;
        String jsondata, columnNames, columnPropertiesString, pagePropertiesString;

        // jsondata  = basicReport(paramName, paramValue,reportTemplateId, materialCd, dates);
        //jsondata = (String) servletContext.getAttribute("jsondataReport");
        jsondata = datasource;
        columnNames = getColumnnNames(reportTemplateId);
        columnPropertiesString = getColumnPropertiesFromTemplate(reportTemplateId);
        pagePropertiesString = getPagePropertiesFromTemplate(reportTemplateId);

        JsonReader readerColProp = Json.createReader(new StringReader(columnPropertiesString));
        JsonStructure jsonstColProp = readerColProp.read();
        JsonObject columnProperties = (JsonObject) jsonstColProp;

        JsonReader readerPageProp = Json.createReader(new StringReader(pagePropertiesString));
        JsonStructure jsonstPageProp = readerPageProp.read();
        JsonObject pageProperties = (JsonObject) jsonstPageProp;

        List<String> sbrptNameList = new ArrayList<String>(); // store columnNames as List
        String[] columnNameArray = columnNames.split(",");
        for (int i = 0; i < columnNameArray.length; i++) {
            sbrptNameList.add(columnNameArray[i]);
        }

        List<String> titleList = new ArrayList<String>();  // create list for column titles
        titleList.addAll(sbrptNameList);
        titleList.remove("totalItems"); // to be shown at the end of report

        // creating template and setting properties
        DynamicReportBuilder drb = new DynamicReportBuilder();

        // detail style - applicable to all rows
        Style detailStyle = new Style();
        detailStyle.setFont(Font.COURIER_NEW_BIG);
        detailStyle.setTransparency(Transparency.OPAQUE);

        // header style
        Style headerStyle = new Style();
        headerStyle.setBackgroundColor(new Color(0, 0, 0));
        headerStyle.setBorder(Border.PEN_1_POINT());
        headerStyle.setTextColor(Color.white);
        headerStyle.setBorderColor(Color.black);
        headerStyle.setFont(Font.TIMES_NEW_ROMAN_MEDIUM_BOLD);
        //headerStyle.setHorizontalAlign(HorizontalAlign.);
        headerStyle.setTransparency(Transparency.OPAQUE);

        // set title style - applicable to column names
        Style titleStyle = Style.createBlankStyle("titleStyle");
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(Font.ARIAL_BIG_BOLD);
        titleStyle.setTransparency(Transparency.OPAQUE);
        /**
         * "subtitleStyleParent" is meant to be used as a parent style, while
         * "subtitleStyle" is the child.
         */
        Style subtitleStyleParent = Style.createBlankStyle("subtitleParent");
        subtitleStyleParent.setBackgroundColor(Color.GREEN);
        subtitleStyleParent.setTransparency(Transparency.OPAQUE);

        Style subtitleStyle = Style.createBlankStyle("subtitleStyle");
        subtitleStyle.setFont(Font.ARIAL_BIG_BOLD);
        subtitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);

        Style colStyle = Style.createBlankStyle("colStyle");
        //  colStyle.setBorderBottom(Border.PEN_2_POINT());
        //    colStyle.setBorderTop(Border.PEN_2_POINT());
        //   colStyle.setBorderColor(Color.BLACK);
        Font font = new Font();
        font.setFontSize(9);
        font.setBold(true);
        colStyle.setFont(font);
        colStyle.setStretchWithOverflow(true);
        colStyle.setVerticalAlign(VerticalAlign.TOP);
        // colStyle.setPadding(1);

        drb.setTitle("REPORT") //defines the title of the report
                //  .setSubtitle("Library collection having "+URLDecoder.decode(paramValueDesc, "UTF-8"))
                .setDetailHeight(20) //defines the height for each record of the report
                .setMargins(30, 20, 30, 15) //define the margin space for each side (top, bottom, left and right)
                .setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
                .setColumnsPerPage(1);

        //FastReportBuilder drb = new FastReportBuilder();
        DynamicReport dr = null;

        AbstractColumn[] s = new AbstractColumn[titleList.size()];
        String titleListVal = null;
        for (int i = 0; i < titleList.size(); i++) {
            titleListVal = titleList.get(i);

            s[i] = ColumnBuilder.getNew() //creates a new instance of a ColumnBuilder
                    .setColumnProperty(titleListVal, String.class.getName()) //defines the field of the data source that this column will show, also its type
                    .setTitle(titleListVal.toUpperCase()) //the title for the column
                    .setWidth(Integer.parseInt(columnProperties.get(titleListVal + "_width").toString())) //the width of the column
                    .setStyle(colStyle)
                    .build();

            s[i].setPosX(Integer.parseInt(columnProperties.get(titleListVal + "_left").toString()));
            s[i].setPosY(Integer.parseInt(columnProperties.get(titleListVal + "_top").toString()));
            drb.addColumn(s[i]);
        }
        try {
            /**
             * add some more options to the report (through the builder)
             */
//drb.addBarcodeColumn("Bar-Code", "recId", Long.class.getName(), BarcodeTypes.USD3, true, false,null, 100, true, ImageScaleMode.FILL, null);
            drb.setIgnorePagination(true); //for Excel, we may dont want pagination, just a plain list
            drb.setMargins(0, 0, 0, 0);
            drb.setUseFullPageWidth(true);  //we tell the report to use the full width of the page. this rezises
            //the columns width proportionally to meat the page width.

            Style oddRowBackgroundStyle = new Style();
            oddRowBackgroundStyle.setBackgroundColor(Color.LIGHT_GRAY);
            drb.setOddRowBackgroundStyle(oddRowBackgroundStyle);
            drb.setPrintBackgroundOnOddRows(true);
            //Style atStyle2 = new StyleBuilder(true).setFont(new Font(12, Font._FONT_ARIAL, false, true, false)).setTextColor(Color.BLUE).build();

            //    drb.addAutoText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()),AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT,100,atStyle2);
            //   drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_CENTER,100,40,atStyle2);
//            Page page = new Page();
//            page.Page_A4_Landscape();
//            page.setOrientationPortrait(true);
            //drb.addGlobalFooterVariable(s[0], "Tota");
            if (sbrptNameList.contains("totalItems")) {
                drb.addGlobalFooterVariable(s[0], DJCalculation.COUNT, detailStyle, new DJValueFormatter() {

                    public String getClassName() {
                        return String.class.getName();
                    }

                    public Object evaluate(Object value, Map fields, Map variables, Map parameters) {

                        return "Total Items :" + value;
                    }
                });
            }
            dr = drb.build(); //Finally build the report!  

//drb.setReportLocale(new Locale("en","IN"));
        } catch (ColumnBuilderException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }

        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jsondata.getBytes());

        JsonDataSource ds = null;
        try {
            ds = new JsonDataSource(jsonDataStream);
        } catch (JRException ex) {
          ///  Logger.getLogger(GenerateJasper.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);

            jp.setPageWidth((int) (96 * Double.parseDouble(pageProperties.get("PageWidth").toString())));
            jp.setPageHeight((int) (96 * Double.parseDouble(pageProperties.get("PageHeight").toString())));

        } catch (JRException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("jp...... " + jp.getPages().size());
        String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
        System.out.println("filePath  .. " + filePath);
        //ReportExporter.exportReportPlainXls(jp, filePath + "/report.xls");

        FileInputStream fileInputStream = new FileInputStream(filePath + "/report.xls");
        javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
        //  responseBuilder.type("application/pdf");
        responseBuilder.header("Content-Disposition", "attachment; filename=report.xls");
        //File file = new File(filePath+"/mem.html"); 
        //file.delete();
        return responseBuilder.build();
    }

    @GET
    @Path("sendMail")
    public String sendMail() throws AddressException, MessagingException {

        String ToEmail = "priyam@inflibnet.ac.in";
        // out.print(ToEmail);
        final String user = "priyam@inflibnet.ac.in";//change accordingly  
        final String password = "";//change accordingly  

        // private static final String UPLOAD_DIRECTORY = "upload";
        final int THRESHOLD_SIZE = 1024 * 1024 * 3;  // 3MB
        final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
        final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
        File file;
        final String UPLOAD_DIRECTORY = "D:/uploads";
        ServletFileUpload uploader = null;
        String date_to_string = null;
        String pdfFile = "D:/report.pdf";
        String filename = "report.pdf";
        //  JasperExportManager.exportReportToPdfFile(jp, pdfFile);
        Properties props = new Properties();
        // props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.EnableSSL.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        //out.println(" properties set successfully....");
        Session session1 = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        user, password);// Specify the Username and the PassWord
            }
        });
        Message message = new MimeMessage(session1);
        message.setFrom(new InternetAddress(user));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(ToEmail));

        message.setSubject("Soul generated Report");
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Hello User,"
                + "This the catalogue basic Report");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(pdfFile);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);
        Transport.send(message);
        out.print("you Email Sent Successfully!...");

        // response.setHeader("Content-Disposition", "dasda");
        //response.setContentType("text/plain");
        return "success";

    }

    @GET
    @Path("generateReportPrint/{paramName}/{paramValue}/{paramValueDesc}/{reportTemplateId}/{dates}/{materialCd}")
    // @Produces("application/vnd.ms-excel")
    public Response generateReportPrint(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue, @PathParam("paramValueDesc") String paramValueDesc, @PathParam("reportTemplateId") String reportTemplateId, @PathParam("materialCd") String materialCd, @PathParam("dates") String dates) throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException, PrintException {
        JasperPrint jp = null;
        //    System.setProperty("java.awt.headless", "false");
        System.out.println("Headless:" + java.awt.GraphicsEnvironment.isHeadless());
        System.out.println("11 " + paramName);
        System.out.println("11 " + paramValue);
        System.out.println("11 " + paramValueDesc);
        System.out.println("11 " + reportTemplateId);
        System.out.println("11 " + materialCd);
        System.out.println("11 " + dates);

        String jsondata, columnNames, columnPropertiesString, pagePropertiesString;

        jsondata = basicReport(paramName, paramValue, reportTemplateId, paramValueDesc, materialCd, dates);
        columnNames = getColumnnNames(reportTemplateId);
        columnPropertiesString = getColumnPropertiesFromTemplate(reportTemplateId);
        pagePropertiesString = getPagePropertiesFromTemplate(reportTemplateId);

        JsonReader readerColProp = Json.createReader(new StringReader(columnPropertiesString));
        JsonStructure jsonstColProp = readerColProp.read();
        JsonObject columnProperties = (JsonObject) jsonstColProp;

        JsonReader readerPageProp = Json.createReader(new StringReader(pagePropertiesString));
        JsonStructure jsonstPageProp = readerPageProp.read();
        JsonObject pageProperties = (JsonObject) jsonstPageProp;

        List<String> sbrptNameList = new ArrayList<String>(); // store columnNames as List
        String[] columnNameArray = columnNames.split(",");
        for (int i = 0; i < columnNameArray.length; i++) {
            sbrptNameList.add(columnNameArray[i]);
        }

        List<String> titleList = new ArrayList<String>();  // create list for column titles
        titleList.addAll(sbrptNameList);
        titleList.remove("totalItems"); // to be shown at the end of report

        // creating template and setting properties
        DynamicReportBuilder drb = new DynamicReportBuilder();

        // detail style - applicable to all rows
        Style detailStyle = new Style();
        detailStyle.setFont(Font.COURIER_NEW_BIG);
        detailStyle.setTransparency(Transparency.OPAQUE);

        // header style
        Style headerStyle = new Style();
        headerStyle.setBackgroundColor(new Color(0, 0, 0));
        headerStyle.setBorder(Border.PEN_1_POINT());
        headerStyle.setTextColor(Color.white);
        headerStyle.setBorderColor(Color.black);
        headerStyle.setFont(Font.TIMES_NEW_ROMAN_MEDIUM_BOLD);
        //headerStyle.setHorizontalAlign(HorizontalAlign.);
        headerStyle.setTransparency(Transparency.OPAQUE);

        // set title style - applicable to column names
        Style titleStyle = Style.createBlankStyle("titleStyle");
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(Font.ARIAL_BIG_BOLD);
        titleStyle.setTransparency(Transparency.OPAQUE);
        /**
         * "subtitleStyleParent" is meant to be used as a parent style, while
         * "subtitleStyle" is the child.
         */
        Style subtitleStyleParent = Style.createBlankStyle("subtitleParent");
        subtitleStyleParent.setBackgroundColor(Color.GREEN);
        subtitleStyleParent.setTransparency(Transparency.OPAQUE);

        Style subtitleStyle = Style.createBlankStyle("subtitleStyle");
        subtitleStyle.setFont(Font.ARIAL_BIG_BOLD);
        subtitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);

        Style colStyle = Style.createBlankStyle("colStyle");
        //  colStyle.setBorderBottom(Border.PEN_2_POINT());
        //    colStyle.setBorderTop(Border.PEN_2_POINT());
        //   colStyle.setBorderColor(Color.BLACK);
        Font font = new Font();
        font.setFontSize(9);
        font.setBold(true);
        colStyle.setFont(font);
        colStyle.setStretchWithOverflow(true);
        colStyle.setVerticalAlign(VerticalAlign.TOP);
        // colStyle.setPadding(1);

        drb.setTitle("REPORT") //defines the title of the report
                .setSubtitle("Library collection having " + URLDecoder.decode(paramValueDesc, "UTF-8"))
                .setDetailHeight(20) //defines the height for each record of the report
                .setMargins(30, 20, 30, 15) //define the margin space for each side (top, bottom, left and right)
                .setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
                .setColumnsPerPage(1);

        //FastReportBuilder drb = new FastReportBuilder();
        DynamicReport dr = null;

        AbstractColumn[] s = new AbstractColumn[titleList.size()];
        String titleListVal = null;
        for (int i = 0; i < titleList.size(); i++) {
            titleListVal = titleList.get(i);

            s[i] = ColumnBuilder.getNew() //creates a new instance of a ColumnBuilder
                    .setColumnProperty(titleListVal, String.class.getName()) //defines the field of the data source that this column will show, also its type
                    .setTitle(titleListVal.toUpperCase()) //the title for the column
                    .setWidth(Integer.parseInt(columnProperties.get(titleListVal + "_width").toString())) //the width of the column
                    .setStyle(colStyle)
                    .build();

            s[i].setPosX(Integer.parseInt(columnProperties.get(titleListVal + "_left").toString()));
            s[i].setPosY(Integer.parseInt(columnProperties.get(titleListVal + "_top").toString()));
            drb.addColumn(s[i]);
        }
        try {
            /**
             * add some more options to the report (through the builder)
             */
//drb.addBarcodeColumn("Bar-Code", "recId", Long.class.getName(), BarcodeTypes.USD3, true, false,null, 100, true, ImageScaleMode.FILL, null);
            drb.setIgnorePagination(true); //for Excel, we may dont want pagination, just a plain list
            drb.setMargins(0, 0, 0, 0);
            drb.setUseFullPageWidth(true);  //we tell the report to use the full width of the page. this rezises
            //the columns width proportionally to meat the page width.

            Style oddRowBackgroundStyle = new Style();
            oddRowBackgroundStyle.setBackgroundColor(Color.LIGHT_GRAY);
            drb.setOddRowBackgroundStyle(oddRowBackgroundStyle);
            drb.setPrintBackgroundOnOddRows(true);
            //Style atStyle2 = new StyleBuilder(true).setFont(new Font(12, Font._FONT_ARIAL, false, true, false)).setTextColor(Color.BLUE).build();

            //    drb.addAutoText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()),AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT,100,atStyle2);
            //   drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_CENTER,100,40,atStyle2);
//            Page page = new Page();
//            page.Page_A4_Landscape();
//            page.setOrientationPortrait(true);
            //drb.addGlobalFooterVariable(s[0], "Tota");
            if (sbrptNameList.contains("totalItems")) {
                drb.addGlobalFooterVariable(s[0], DJCalculation.COUNT, detailStyle, new DJValueFormatter() {

                    public String getClassName() {
                        return String.class.getName();
                    }

                    public Object evaluate(Object value, Map fields, Map variables, Map parameters) {

                        return "Total Items :" + value;
                    }
                });
            }
            dr = drb.build(); //Finally build the report!  

//drb.setReportLocale(new Locale("en","IN"));
        } catch (ColumnBuilderException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }

        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jsondata.getBytes());

        JsonDataSource ds = null;
        try {
            ds = new JsonDataSource(jsonDataStream);
        } catch (JRException ex) {
          //  Logger.getLogger(GenerateJasper.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);

            jp.setPageWidth((int) (96 * Double.parseDouble(pageProperties.get("PageWidth").toString())));
            jp.setPageHeight((int) (96 * Double.parseDouble(pageProperties.get("PageHeight").toString())));

        } catch (JRException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("jp...... " + jp.getPages().size());
        String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
        System.out.println("filePath  .. " + filePath);
        //     JasperExportManager.exportReportToPdfFile(jp,  "D:/rrrrrrrr.pdf");

//     
////      FileInputStream fileInputStream = new FileInputStream(filePath+"/report.xls");     
////  //  responseBuilder.type("application/pdf");
//// responseBuilder.header("Content-Disposition","attachment; filename=report.xls");
////            //File file = new File(filePath+"/mem.html"); 
////        //file.delete();
////       return responseBuilder.build();
//PrintService ps=PrintServiceLookup.lookupDefaultPrintService();
//    //  DocPrintJob job=ps.createPrintJob();
//        PrinterJob pj = PrinterJob.getPrinterJob();
////      job.addPrintJobListener(new PrintJobAdapter() {
////      public void printDataTransferCompleted(PrintJobEvent event){
////         System.out.println("data transfer complete");
////      }
////      public void printJobNoMoreEvents(PrintJobEvent event){
////            System.out.println("received no more events");
////         }
////      });
////     // FileInputStream fis=new FileInputStream("D:/reporting.pdf");
////    
//      Doc doc=new SimpleDoc(JasperExportManager.exportReportToPdf(jp), DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
//      // Doc doc=new SimpleDoc(fis, DocFlavor.INPUT_STREAM.JPEG, null);
//      PrintRequestAttributeSet attrib=new HashPrintRequestAttributeSet();
//      attrib.add(new Copies(1));
//      //job.print(doc, attrib);
//      //javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) job);
//     // responseBuilder.type("application/pdf");
//    if(!pj.printDialog(attrib))
//    {
//        return;
//    }
//JasperPrintManager.printReport(jp, false);
        //String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
        JasperExportManager.exportReportToPdfFile(jp, filePath + "/report.pdf");
        FileInputStream fileInputStream = new FileInputStream(filePath + "/report.pdf");
        javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
        //  responseBuilder.type("application/pdf");
        responseBuilder.header("Content-Disposition", "filename=report.pdf");
        //File file = new File(filePath+"/mem.html"); 
        //file.delete();
        return responseBuilder.build();

    }

//      @GET 
//    @Path("getJasperPrintLabels/{labelId}/{header}/{paramName}/{paramValue}/{checkboxParam}/{dates}/{materialCd}")
//   // @Produces({"application/xml", "application/json", "test/plain"})
//     public void getJasperPrintLabels(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue,
//              @PathParam("checkboxParam") String checkboxparam,@PathParam("materialCd") String materialCd, 
//                    @PathParam("dates") String dates,@PathParam("labelId") String labelId,
//                    @PathParam("header") String header) throws UnsupportedEncodingException, JRException, ParseException {
//     @GET
//    @Path("getJasperPrint/{subtitle}/{paramName}/{paramValue}/{reportTemplateId}/{dates}/{materialCd}")
//    public void getJasperPrint(@PathParam("subtitle") String subtitle,@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue,
//            @PathParam("reportTemplateId") String reportTemplateId, @PathParam("materialCd") String materialCd, @PathParam("dates") String dates )
//            throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException {
//        // JasperPrint jp = null;
    // @GET
    // @Path("getJasperPrint/{reportType}/{paramName}/{paramValue}/{reportTemplateId}/{dates}/{materialCd}")
    // for basic, advanced report -- generating jasper file --  --- final version -----
    public void getJasperPrint(String jsondata, String reportTemplateId, String subtitle)
            throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException {

        // String jsondata = basicReport(paramName, paramValue, reportTemplateId, materialCd, dates);
        // jsondata  = webResource1.accept("text/plain").get(ClientResponse.class).getEntity(String.class);
        String columnNames = getColumnnNames(reportTemplateId);
        System.err.println("columnNames .. " + columnNames);
        String columnPropertiesString = getColumnPropertiesFromTemplate(reportTemplateId);
        String pagePropertiesString = getPagePropertiesFromTemplate(reportTemplateId);

        JsonReader readerColProp = Json.createReader(new StringReader(columnPropertiesString));
        JsonStructure jsonstColProp = readerColProp.read();
        JsonObject columnProperties = (JsonObject) jsonstColProp;

        JsonReader readerPageProp = Json.createReader(new StringReader(pagePropertiesString));
        JsonStructure jsonstPageProp = readerPageProp.read();
        JsonObject pageProperties = (JsonObject) jsonstPageProp;

        List<String> sbrptNameList = new ArrayList<String>(); // store columnNames as List
        String[] columnNameArray = columnNames.split(",");
        for (int i = 0; i < columnNameArray.length; i++) {
            sbrptNameList.add(columnNameArray[i]);
        }

        List<String> titleList = new ArrayList<String>();  // create list for column titles
        titleList.addAll(sbrptNameList);
        titleList.remove("totalItems"); // to be shown at the end of report

        // creating template and setting properties
        DynamicReportBuilder drb = new DynamicReportBuilder();

        // detail style - applicable to all rows
        Style detailStyle = new Style();
        detailStyle.setFont(Font.COURIER_NEW_BIG);
        detailStyle.setTransparency(Transparency.OPAQUE);

        // header style
        Style headerStyle = new Style();
        headerStyle.setBackgroundColor(new Color(0, 0, 0));
        headerStyle.setBorder(Border.PEN_1_POINT());
        headerStyle.setTextColor(Color.white);
        headerStyle.setBorderColor(Color.black);
        headerStyle.setFont(Font.TIMES_NEW_ROMAN_MEDIUM_BOLD);
        //headerStyle.setHorizontalAlign(HorizontalAlign.);
        headerStyle.setTransparency(Transparency.OPAQUE);

        // set title style - applicable to column names
        Style titleStyle = Style.createBlankStyle("titleStyle");
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(Font.ARIAL_BIG_BOLD);
        titleStyle.setTransparency(Transparency.OPAQUE);
        /**
         * "subtitleStyleParent" is meant to be used as a parent style, while
         * "subtitleStyle" is the child.
         */
        Style subtitleStyleParent = Style.createBlankStyle("subtitleParent");
        subtitleStyleParent.setBackgroundColor(Color.GREEN);
        subtitleStyleParent.setTransparency(Transparency.OPAQUE);

        Style subtitleStyle = Style.createBlankStyle("subtitleStyle");
        subtitleStyle.setFont(Font.ARIAL_BIG_BOLD);
        subtitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);

        Style colStyle = Style.createBlankStyle("colStyle");
        //  colStyle.setBorderBottom(Border.PEN_2_POINT());
        //    colStyle.setBorderTop(Border.PEN_2_POINT());
        //   colStyle.setBorderColor(Color.BLACK);
        Font font = new Font();
        font.setFontSize(12);
        font.setBold(true);
        colStyle.setFont(font);
        colStyle.setVerticalAlign(VerticalAlign.TOP);
        // colStyle.setPadding(1);

        drb.setTitle("REPORT") //defines the title of the report
                .setSubtitle(URLDecoder.decode(subtitle, "UTF-8"))
                .setDetailHeight(20) //defines the height for each record of the report
                .setMargins(30, 20, 30, 15) //define the margin space for each side (top, bottom, left and right)
                .setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
                .setColumnsPerPage(1);

        //FastReportBuilder drb = new FastReportBuilder();
        DynamicReport dr = null;

        AbstractColumn[] s = new AbstractColumn[titleList.size()];
        String titleListVal = null;
        for (int i = 0; i < titleList.size(); i++) {
            titleListVal = titleList.get(i);

            s[i] = ColumnBuilder.getNew() //creates a new instance of a ColumnBuilder
                    .setColumnProperty(titleListVal, String.class.getName()) //defines the field of the data source that this column will show, also its type
                    .setTitle(titleListVal.toUpperCase()) //the title for the column
                    .setWidth(Integer.parseInt(columnProperties.get(titleListVal + "_width").toString())) //the width of the column
                    .setStyle(colStyle)
                    .build();

            s[i].setPosX(Integer.parseInt(columnProperties.get(titleListVal + "_left").toString()));
            s[i].setPosY(Integer.parseInt(columnProperties.get(titleListVal + "_top").toString()));
            drb.addColumn(s[i]);
        }
        Map<String, Object> parameters = new HashMap<String, Object>();
        try {
            /**
             * add some more options to the report (through the builder)
             */
//drb.addBarcodeColumn("Bar-Code", "recId", Long.class.getName(), BarcodeTypes.USD3, true, false,null, 100, true, ImageScaleMode.FILL, null);
            drb.setUseFullPageWidth(true);  //we tell the report to use the full width of the page. this rezises
            //the columns width proportionally to meat the page width.

            Style oddRowBackgroundStyle = new Style();

            Page page = new Page();
            page.setHeight((int) (96 * Double.parseDouble(pageProperties.get("PageHeight").toString())));
            page.setWidth((int) (96 * Double.parseDouble(pageProperties.get("PageWidth").toString())));
            drb.setPageSizeAndOrientation(page);

            oddRowBackgroundStyle.setBackgroundColor(new Color(222, 222, 222));
            drb.setOddRowBackgroundStyle(oddRowBackgroundStyle);
            drb.setPrintBackgroundOnOddRows(true);

            Style atStyle2 = new StyleBuilder(true).setFont(new Font(12, Font._FONT_ARIAL, false, true, false)).setTextColor(Color.BLACK).build();

            drb.addAutoText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT, 100, atStyle2);
            drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_CENTER, 100, 40, atStyle2);
//            Page page = new Page();
//            page.Page_A4_Landscape();
//            page.setOrientationPortrait(true);
            //drb.addGlobalFooterVariable(s[0], "Tota");
            System.out.println("..........sbrptNameList.............  " + sbrptNameList);
            if (sbrptNameList.contains("totalItems")) {
                System.out.println(".....totalItems");
                drb.addGlobalFooterVariable(s[0], DJCalculation.COUNT, detailStyle, new DJValueFormatter() {

                    public String getClassName() {
                        return String.class.getName();
                    }

                    public Object evaluate(Object value, Map fields, Map variables, Map parameters) {

                        return "Total Items :" + value;
                    }
                });
            }

            dr = drb.build(); //Finally build the report!  
            //  System.out.println("bundle "+dr.getResourceBundle() + "   "+dr.getReportLocale()); 
            ResourceBundle messages = ResourceBundle.getBundle("dj-messages_en", dr.getReportLocale());

            parameters.put(JRDesignParameter.REPORT_FORMAT_FACTORY, messages);

//drb.setReportLocale(new Locale("en","IN"));
        } catch (ColumnBuilderException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("jsondata ....  "+jsondata);
        //System.out.println("jsonbytes  ....  "+jsondata.getBytes("UTF-8"));
//        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jsondata.getBytes("UTF-8"));
//
//        JsonDataSource ds = null;
//        try {
//           
//            ds = new JsonDataSource(jsonDataStream);
//        } catch (JRException ex) {
//            Logger.getLogger(GenerateJasper.class.getName()).log(Level.SEVERE, null, ex);
//        }
        String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
        try {

            DynamicJasperHelper.generateJRXML(dr, new ClassicLayoutManager(), parameters, "UTF-8", filePath + "/rpt.jrxml");
            //  DynamicJasperHelper.
            //JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
            //    JasperExportManager.exportReportToHtmlFile(jp,filePath+"/report.html");
            //   jp.setPageWidth((int) (96 * Double.parseDouble(pageProperties.get("PageWidth").toString())));
            //    jp.setPageHeight((int) (96 *  Double.parseDouble(pageProperties.get("PageHeight").toString())));

            //JasperCompileManager.compileReportToFile(jasDes, filePath+"/rpt.jasper");
            JasperCompileManager.compileReportToFile(filePath + "/rpt.jrxml", filePath + "/rpt.jasper");
            File f = new File(filePath + "/dataSource.txt");
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(f), StandardCharsets.UTF_8));
            //  FileWriter fileWriter = null;
            try {
                //fileWriter = new FileWriter(f);
                //inherited method from java.io.OutputStreamWriter 
                //fileWriter.write(jsondata);
                out.write(jsondata);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.flush();
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (JRException ex) {
            Logger.getLogger(BiblidetailsLocationFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // for spine label & bookcard -- generating jasper file -- --- final version -----
    @GET
    @Path("getJasperPrintLabels/{labelId}/{header}/{paramName}/{paramValue}/{checkboxParam}/{dates}/{materialCd}")
    // @Produces({"application/xml", "application/json", "test/plain"})
    public void getJasperPrintLabels(@PathParam("jsonData") String jsonData, @PathParam("checkboxParam") String checkboxparam,
            @PathParam("labelId") String labelId, @PathParam("header") String header, @PathParam("subtitle") String subtitle,
            @PathParam("labelType") String labelType)
            throws UnsupportedEncodingException, JRException, ParseException, FileNotFoundException {
        MLablespects mlabel = mLablespectsFacadeREST.find(labelId);
        JasperDesign jasDes = new JasperDesign();
        int noOfRows = mlabel.getNoOfRows();
        String checkboxparamArray[] = checkboxparam.split(",");
        List<String> columnList = new ArrayList<>();
        for (int c = 0; c < checkboxparamArray.length; c++) {
            columnList.add(checkboxparamArray[c]);
        }
        columnList.remove("drawLine");
        columnList.remove("header");

        int totalCols = checkboxparamArray.length;

        jasDes.setName("Spine Labels");
        
        
        int pageWidth = (int) (mlabel.getPageWidth() * 96);
        int pageHeight = (int) (mlabel.getPageHeight() * 96);
        int topMargin = (int) (mlabel.getTopMargin() * 96);
        int bottomMargin = (int) (mlabel.getBottomMargin() * 96);
        int rightMargin = (int) (mlabel.getRightMargin() * 96);
        int leftMargin = (int) (mlabel.getLeftMargin() * 96);
        int titleBandWidth = 0;
        int noOfColumns = mlabel.getNoOfColumns();
        int colSpace = (int) (mlabel.getColumnSpace() * 96);
        int rowSpace = (int) (mlabel.getRowSpace() * 96);
        int columnWidth = (pageWidth - leftMargin - rightMargin - (colSpace * (noOfColumns))) / noOfColumns;
 //int columnWidth = 142;
        jasDes.setPageWidth(pageWidth);
        jasDes.setPageHeight(pageHeight);
        jasDes.setLeftMargin(leftMargin);
        jasDes.setRightMargin(rightMargin);
        jasDes.setTopMargin(topMargin);
        jasDes.setBottomMargin(bottomMargin);
        jasDes.setColumnWidth(columnWidth);
        jasDes.setColumnCount(noOfColumns);
        jasDes.setColumnSpacing(colSpace);

        int totalRowsPerPage = noOfRows * totalCols;
        System.out.println("totalRowsPerPage " + totalRowsPerPage);
        System.out.println("pageHeight " + pageHeight);
        System.out.println("topMargin " + topMargin);
        System.out.println("bottomMargin " + bottomMargin);
        System.out.println("titleBandWidth " + titleBandWidth);
        System.out.println("rowSpace " + rowSpace);
        System.out.println("rowSpace*(noOfRows) " + rowSpace * (noOfRows));

        int rowHeight = (pageHeight - topMargin - bottomMargin - titleBandWidth - (rowSpace * (noOfRows))) / totalRowsPerPage;
     //   int rowHeight = 15;
        System.out.println("rowHeight  .. after.." + rowHeight);
        int detailHeight = (rowHeight * totalCols) + (rowSpace);
       // int detailHeight = 79;
        System.out.println("totalRowsPerPage : " + totalRowsPerPage);
        System.out.println("rowHeight : " + rowHeight);
        System.out.println("detailHeight : " + detailHeight);

        JRDesignStyle mystyle = new JRDesignStyle();
        mystyle.setName("mystyle");
        //    mystyle.setDefault(true);
        mystyle.setFontName("Arial Unicode MS");
        jasDes.addStyle(mystyle);

        //it will add n number of field.
        for (int i = 0; i < columnList.size(); i++) {

            JRDesignField field = new JRDesignField();
            field.setName(columnList.get(i));
            field.setValueClass(String.class);
            jasDes.addField(field);
        }

      //  JRDesignBand titleBand = new JRDesignBand();
       // titleBand.setHeight(titleBandWidth);

    //    JRDesignStaticText titleText = new JRDesignStaticText();
       // titleText.setText("SPINE LABELS");
        // titleText.setX(0);
        //  titleText.setY(10);
//        titleText.setWidth(pageWidth);
//        titleText.setHeight(30);
//        titleText.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
//        titleText.setVerticalAlignment(VerticalAlignEnum.MIDDLE);
//        titleText.setFontSize(22f);

       // titleBand.addElement(titleText);
      //  jasDes.setTitle(titleBand);

      
      
        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(detailHeight);

        //it will add the data field into report dyanamically
        int y2 = 0;
        for (int i = 0; i < totalCols; i++) {

            if (checkboxparamArray[i].contentEquals("accessionNoBarcode")) // is barcode
            {
                StandardBarbecueComponent bbcomp = new StandardBarbecueComponent();
                bbcomp.setType("Code128");
                bbcomp.setDrawText(false);
                bbcomp.setChecksumRequired(true);
                System.out.println("columnWidth .. " + columnWidth);
                System.out.println("rowHeight .. " + rowHeight);
                bbcomp.setBarHeight(rowHeight);

              //  bbcomp.setBarWidth(10);
                bbcomp.setEvaluationTimeValue(EvaluationTimeEnum.NOW);
                String barcodeVal = "$F{" + checkboxparamArray[i] + "}";
                JRDesignExpression exp = new JRDesignExpression("$F{" + checkboxparamArray[i] + "}");
                // System.out.println("barcodeVal .. "+exp.getChunks()[0]);
                if (!barcodeVal.isEmpty()) {
                    bbcomp.setCodeExpression(new JRDesignExpression("$F{" + checkboxparamArray[i] + "}"));
                }

                JRDesignComponentElement barcode = new JRDesignComponentElement();
                barcode.setComponent(bbcomp);
                barcode.setComponentKey(new ComponentKey("http://jasperreports.sourceforge.net/jasperreports/components", "jr", "barbecue"));
                barcode.setX(0);
                barcode.setY(y2);
                barcode.setWidth(columnWidth);
                barcode.setHeight(rowHeight);

                System.out.println("bbcomp  " + bbcomp.getBarWidth());
                detailBand.addElement(barcode);

                y2 += rowHeight;
            } else if (checkboxparamArray[i].contentEquals("drawLine"))// is line
            {
                JRDesignLine line = new JRDesignLine();
                line.setX(0);
                line.setY(y2);
                line.setWidth(columnWidth);
                line.setFill(FillEnum.SOLID);
                line.setHeight(1);
                detailBand.addElement(line);

                y2 += rowHeight;
            } else if (checkboxparamArray[i].contentEquals("header")) // is header
            {
                JRDesignTextField tf = new JRDesignTextField();
                // tf1.setBlankWhenNull(true);
                tf.setX(0);
                tf.setY(y2);
                tf.setWidth(columnWidth);
                tf.setHeight(rowHeight);
                tf.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
                tf.setStyle(mystyle);
                tf.setStretchWithOverflow(true);
                //  tf.getLineBox().getPen().setLineWidth(1);
                String headerVal = "\"" + header + "\"";
                tf.setExpression(new JRDesignExpression(headerVal));
                detailBand.addElement(tf);

                y2 += rowHeight;
            } else {
              
      //  JRDesignRectangle 
 
     
                JRDesignTextField tf = new JRDesignTextField();
                tf.setX(0);
                tf.setY(y2);
                tf.setWidth(columnWidth);
                tf.setHeight(rowHeight);
                tf.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
                tf.setStyle(mystyle);
                tf.setStretchWithOverflow(true);
                tf.setBackcolor(Color.yellow);
//                JRLineBox box = tf.getLineBox();
//                float bw = .5f;
//                Color c =  Color.RED;
//                box.getLeftPen().setLineWidth(bw);
//		box.getLeftPen().setLineColor(c);
//		box.getRightPen().setLineWidth(bw);
//		box.getRightPen().setLineColor(c);
//		box.getBottomPen().setLineWidth(bw);
//		box.getBottomPen().setLineColor(c);
//		box.getTopPen().setLineWidth(bw);
//		box.getTopPen().setLineColor(c);
                if (labelType.contentEquals("spine")) {
                    tf.setExpression(new JRDesignExpression("$F{" + checkboxparamArray[i] + "}"));
                }
                if (labelType.contentEquals("bookcard")) {
                    tf.setExpression(new JRDesignExpression(" \"" + checkboxparamArray[i] + " : \" + $F{" + checkboxparamArray[i] + "}"));
                }
                // detailBand.addElement(jl);
                detailBand.addElement(tf);
                // detailBand.addElement(titleText1);
                y2 += rowHeight;
            }
        }

        
        
        ((JRDesignSection) jasDes.getDetailSection()).addBand(detailBand);

        // print all json data into report
        String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
        JasperCompileManager.compileReportToFile(jasDes, filePath + "/rpt.jasper");
        File f = new File(filePath + "/dataSource.txt");
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(f), StandardCharsets.UTF_8));
        //  FileWriter fileWriter = null;
        try {
            //fileWriter = new FileWriter(f);
            //inherited method from java.io.OutputStreamWriter 
            //fileWriter.write(jsondata);
            out.write(jsonData);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    @GET
//    @Path("generateBasicReportExcel/{paramValueDesc}/{reportTemplateId}")
//    @Produces("application/vnd.ms-excel")
//    public Response generateBasicReportExcel(@PathParam("paramValueDesc") String paramValueDesc,
//            @PathParam("reportTemplateId") String reportTemplateId)
        @GET
   @Path("generateBasicReportExcel/{paramName}/{paramValue}/{paramValueDesc}/{reportTemplateId}/{dates}/{materialCd}")
        @Produces("application/vnd.ms-excel")
    public Response generateBasicReportExcel(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue,@PathParam("paramValueDesc") String paramValueDesc, 
            @PathParam("reportTemplateId") String reportTemplateId, @PathParam("materialCd") String materialCd, 
            @PathParam("dates") String dates) throws ParseException, JRException, JSONException, FileNotFoundException,
            UnsupportedEncodingException, ClassNotFoundException  {
         String columnNames, columnPropertiesString, pagePropertiesString;
        String jsondata  = basicReport(paramName, paramValue, reportTemplateId,paramValueDesc, materialCd, dates);
        String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
//        File file = new File(filePath + "/dataSource.txt");
//        //  FileReader fr = new FileReader(path + "/dataSource.txt");
//        BufferedReader fr = new BufferedReader(
//                new InputStreamReader(
//                        new FileInputStream(file), StandardCharsets.UTF_8));
//        int i;
//        String jsondata = "";
//        while ((i = fr.read()) != -1) {
//            jsondata = jsondata + (char) i;
//        }
//        fr.close();
        columnNames = getColumnnNames(reportTemplateId);
        columnPropertiesString = getColumnPropertiesFromTemplate(reportTemplateId);
        pagePropertiesString = getPagePropertiesFromTemplate(reportTemplateId);
        JasperPrint jp = generateJasperForExcel(jsondata, columnNames, columnPropertiesString, pagePropertiesString, "BASIC REPORT",  URLDecoder.decode(paramValueDesc, "UTF-8"));

     //   ReportExporter.exportReportPlainXls(jp, filePath + "/report.xls");
        FileInputStream fileInputStream = new FileInputStream(filePath + "/report.xls");
        javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
        responseBuilder.header("Content-Disposition", "attachment; filename=report.xls");
        return responseBuilder.build();
    }

//    @GET
//    @Path("generateAdvancedReportExcel/{reportTemplateId}")
//    @Produces("application/vnd.ms-excel")
//    public Response generateAdvancedReportExcel(@PathParam("reportTemplateId") String reportTemplateId)
//            throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException,
//            UnsupportedEncodingException, IOException {
     @GET
   @Path("generateAdvancedReportExcel/{tag1}/{sbfld1}/{query1}/{op1}/{tag2}/{sbfld2}/{query2}/{op2}/{tag3}/{sbfld3}/{query3}/{reportTemplateId}")
   @Produces("application/pdf")
     public Response generateAdvancedReportExcel(@PathParam("tag1") String tag1, @PathParam("sbfld1") String sbfld1,
           @PathParam("query1") String query1,@PathParam("op1") String op1,@PathParam("tag2") String tag2,
            @PathParam("sbfld2") String sbfld2, @PathParam("query2") String query2,
            @PathParam("tag3") String tag3,
            @PathParam("sbfld3") String sbfld3, @PathParam("query3") String query3,
            @PathParam("op2") String op2, @PathParam("reportTemplateId") String reportTemplateId)   
            throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException {

        String columnNames, columnPropertiesString, pagePropertiesString;
        // jsondata = advancedReport(tag1, sbfld1, query1, op1, tag2, sbfld2, query2, tag3, sbfld3, query3, op2, reportTemplateId);
       String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
//        File file = new File(filePath + "/dataSource.txt");
//        //  FileReader fr = new FileReader(path + "/dataSource.txt");
//        BufferedReader fr = new BufferedReader(
//                new InputStreamReader(
//                        new FileInputStream(file), StandardCharsets.UTF_8));
//        int i;
//        String jsondata = "";
//        while ((i = fr.read()) != -1) {
//            jsondata = jsondata + (char) i;
//        }
//        fr.close();
        String jsondata = advancedReport(tag1, sbfld1, query1, op1, tag2, sbfld2, query2, tag3, sbfld3, query3, op2, reportTemplateId);
        columnNames = getColumnnNames(reportTemplateId);
        columnPropertiesString = getColumnPropertiesFromTemplate(reportTemplateId);
        pagePropertiesString = getPagePropertiesFromTemplate(reportTemplateId);
        JasperPrint jp = generateJasperForExcel(jsondata, columnNames, columnPropertiesString, pagePropertiesString, "ADVANCED REPORT", "");
        // String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
       // ReportExporter.exportReportPlainXls(jp, filePath + "/report.xls");
        FileInputStream fileInputStream = new FileInputStream(filePath + "/report.xls");
        javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
        responseBuilder.header("Content-Disposition", "attachment; filename=report.xls");
        return responseBuilder.build();
    }

//    @GET
//    @Path("generateBasicReportPdf/{paramValueDesc}/{reportTemplateId}")
//    @Produces("application/pdf")
//    public Response generateBasicReportPdf(@PathParam("paramValueDesc") String paramValueDesc, @PathParam("reportTemplateId") String reportTemplateId) throws ParseException, JRException,
//            JSONException, FileNotFoundException, UnsupportedEncodingException, ClassNotFoundException, IOException {
     @GET
   @Path("generateBasicReportPdf/{paramName}/{paramValue}/{paramValueDesc}/{reportTemplateId}/{dates}/{materialCd}")
        @Produces("application/pdf")
    public Response generateBasicReportPdf(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue,@PathParam("paramValueDesc") String paramValueDesc, 
            @PathParam("reportTemplateId") String reportTemplateId, @PathParam("materialCd") String materialCd, @PathParam("dates") String dates) throws ParseException, JRException, 
            JSONException, FileNotFoundException, UnsupportedEncodingException, ClassNotFoundException  {
        String columnNames, columnPropertiesString, pagePropertiesString;
         // jsondata  = basicReport(paramName, paramValue, reportTemplateId, paramValueDesc, materialCd, dates);

        String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
//        File file = new File(filePath + "/dataSource.txt");
//        //  FileReader fr = new FileReader(path + "/dataSource.txt");
//        BufferedReader fr = new BufferedReader(
//                new InputStreamReader(
//                        new FileInputStream(file), StandardCharsets.UTF_8));
//        int i;
//        String jsondata = "";
//        while ((i = fr.read()) != -1) {
//            jsondata = jsondata + (char) i;
//        }
//        fr.close();
        String jsondata = basicReport(paramName, paramValue, reportTemplateId, paramValueDesc, materialCd, dates);
        columnNames = getColumnnNames(reportTemplateId);
        columnPropertiesString = getColumnPropertiesFromTemplate(reportTemplateId);
        pagePropertiesString = getPagePropertiesFromTemplate(reportTemplateId);
        JasperPrint jp = generateJasperForPdf(jsondata, columnNames, columnPropertiesString, pagePropertiesString, "BASIC REPORT",  URLDecoder.decode(paramValueDesc, "UTF-8"));

        JasperExportManager.exportReportToPdfFile(jp, filePath + "/report.pdf");
        FileInputStream fileInputStream = new FileInputStream(filePath + "/report.pdf");
        javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
        //  responseBuilder.type("application/pdf");
        responseBuilder.header("Content-Disposition", "attachment; filename=report.pdf");
        //File file = new File(filePath+"/mem.html"); 
        //file.delete();
        return responseBuilder.build();
    }

    ////////////--- advancedreport pdf final version  ---////////////////
//    @GET
//    @Path("generateAdvancedReportPdf/{reportTemplateId}")
//    @Produces("application/pdf")
//    public Response generateAdvancedReportPdf(@PathParam("reportTemplateId") String reportTemplateId)
//            throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException, IOException {
     
     @GET
   @Path("generateAdvancedReportPdf/{tag1}/{sbfld1}/{query1}/{op1}/{tag2}/{sbfld2}/{query2}/{op2}/{tag3}/{sbfld3}/{query3}/{reportTemplateId}")
   @Produces("application/pdf")
     public Response generateAdvancedReportPdf(@PathParam("tag1") String tag1, @PathParam("sbfld1") String sbfld1,
           @PathParam("query1") String query1,@PathParam("op1") String op1,@PathParam("tag2") String tag2,
            @PathParam("sbfld2") String sbfld2, @PathParam("query2") String query2,
            @PathParam("tag3") String tag3,
            @PathParam("sbfld3") String sbfld3, @PathParam("query3") String query3,
            @PathParam("op2") String op2, @PathParam("reportTemplateId") String reportTemplateId)   
            throws ParseException, JRException, JSONException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException {
    String columnNames, columnPropertiesString, pagePropertiesString;
        String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
//        File file = new File(filePath + "/dataSource.txt");
//        //  FileReader fr = new FileReader(path + "/dataSource.txt");
//        BufferedReader fr = new BufferedReader(
//                new InputStreamReader(
//                        new FileInputStream(file), StandardCharsets.UTF_8));
//        int i;
//        String jsondata = "";
//        while ((i = fr.read()) != -1) {
//            jsondata = jsondata + (char) i;
//        }
//        fr.close();
        String jsondata = advancedReport(tag1, sbfld1, query1, op1, tag2, sbfld2, query2, tag3, sbfld3, query3, op2, reportTemplateId);
        columnNames = getColumnnNames(reportTemplateId);
        columnPropertiesString = getColumnPropertiesFromTemplate(reportTemplateId);
        pagePropertiesString = getPagePropertiesFromTemplate(reportTemplateId);
        JasperPrint jp = generateJasperForPdf(jsondata, columnNames, columnPropertiesString, pagePropertiesString, "ADVANCED REPORT", "");

        JasperExportManager.exportReportToPdfFile(jp, filePath + "/report.pdf");

        FileInputStream fileInputStream = new FileInputStream(filePath + "/report.pdf");
        javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
        //  responseBuilder.type("application/pdf");
        responseBuilder.header("Content-Disposition", "attachment; filename=report.pdf");
        //File file = new File(filePath+"/mem.html"); 
        //file.delete();
        return responseBuilder.build();
    }

    ///////////// spine & book card pdf generation -- final version /////////////
//    @GET
//    @Path("generateLabelPdf")
//    @Produces("application/pdf")
//    public Response generateLabelPdf() throws ParseException, JRException,
//            JSONException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException, IOException {
        // JasperPrint jp = generateJasperForLabels(paramName, paramValue, checkboxparam, header, label, materialCd, dates);
        @GET
   @Path("generateLabelPdf/{paramName}/{paramValue}/{checkboxparam}/{header}/{label}/{dates}/{materialCd}")
    @Produces("application/pdf")
    public Response generateLabelPdf(@PathParam("paramName") String paramName,@PathParam("paramValue") String paramValue,
           @PathParam("checkboxparam") String checkboxparam,@PathParam("header") String header, 
           @PathParam("label") String label ,@PathParam("materialCd") String materialCd, @PathParam("dates") String dates) throws ParseException, JRException, 
            JSONException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException, IOException{
               JasperPrint jp = null;
        //  String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
        String path = servletContext.getRealPath("/jsp/catalogue/reports");
        //FileReader fr=new FileReader(path+"/dataSource.txt");    
//        File file = new File(path + "/dataSource.txt");
//        //  FileReader fr = new FileReader(path + "/dataSource.txt");
//        BufferedReader fr = new BufferedReader(
//                new InputStreamReader(
//                        new FileInputStream(file), StandardCharsets.UTF_8));
//        int i;
//        String jdata = "";
//        while ((i = fr.read()) != -1) {
//            jdata = jdata + (char) i;
//        }
//        fr.close();
        String jdata =  getBibliRecordsforLabels(paramName, paramValue, checkboxparam, materialCd, dates, label, header, label);
        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jdata.getBytes("UTF-8"));
        JsonDataSource ds = new JsonDataSource(jsonDataStream);

        try {

            jp = JasperFillManager.fillReport(path + "/rpt.jasper", null, ds);

        } catch (Exception e) {
            System.out.println("error  " + e);
        }
        JasperExportManager.exportReportToPdfFile(jp, path + "/label.pdf");
        //  JasperExportManager.

        FileInputStream fileInputStream = new FileInputStream(path + "/label.pdf");
        javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
        //  responseBuilder.type("application/pdf");
        responseBuilder.header("Content-Disposition", "attachment; filename=label.pdf");
        //File file = new File(filePath+"/mem.html"); 
        //file.delete();
        return responseBuilder.build();
    }

    ///////////// spine & book card excel generation -- final version /////////////
//    @GET
//    @Path("generateLabelExcel")
//    @Produces("application/vnd.ms-excel")
//    public Response generateLabelExcel() throws ParseException, JRException,
//            JSONException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException, IOException {
      @GET
   @Path("generateLabelExcel/{paramName}/{paramValue}/{checkboxparam}/{header}/{label}/{dates}/{materialCd}")
    @Produces("application/pdf")
    public Response generateLabelExcel(@PathParam("paramName") String paramName,@PathParam("paramValue") String paramValue,
           @PathParam("checkboxparam") String checkboxparam,@PathParam("header") String header, 
           @PathParam("label") String label ,@PathParam("materialCd") String materialCd, @PathParam("dates") String dates) throws ParseException, JRException, 
            JSONException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException, IOException{
        JasperPrint jp = null;
          String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
//        String filePath = servletContext.getRealPath("/jsp/catalogue/reports");
//        File file = new File(filePath + "/dataSource.txt");
//        //  FileReader fr = new FileReader(path + "/dataSource.txt");
//        BufferedReader fr = new BufferedReader(
//                new InputStreamReader(
//                        new FileInputStream(file), StandardCharsets.UTF_8));
//        int i;
//        String jsondata = "";
//        while ((i = fr.read()) != -1) {
//            jsondata = jsondata + (char) i;
//        }
//        fr.close();
        String jsondata = getBibliRecordsforLabels(paramName, paramValue, checkboxparam, materialCd, dates, label, header, label);
        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jsondata.getBytes("UTF-8"));
        JsonDataSource ds = new JsonDataSource(jsonDataStream);

        try {

            jp = JasperFillManager.fillReport(filePath + "/rpt.jasper", null, ds);

        } catch (Exception e) {
            System.out.println("error  " + e);
        }
       // ReportExporter.exportReportPlainXls(jp, filePath + "/label.xls");
        //  JasperExportManager.

        FileInputStream fileInputStream = new FileInputStream(filePath + "/label.xls");
        javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
        //  responseBuilder.type("application/pdf");
        responseBuilder.header("Content-Disposition", "attachment; filename=label.xls");
        //File file = new File(filePath+"/mem.html"); 
        //file.delete();
        return responseBuilder.build();
    }

}
