/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
//import static javafx.util.Duration.millis;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.PathSegment;
import soul.serialControl.SMst;
import soul.catalogue.Biblidetails;
import soul.catalogue.BiblidetailsPK;
import soul.catalogue.service.BiblidetailsFacadeREST;
import soul.serialControl.SSchedule;
import soul.serialControl.SSchedulePK;
import soul.serial_master.SFrequency;
import soul.serial_master.service.SFrequencyFacadeREST;
import soul.serialControl.SLooseissuemapping;
import soul.serialControl.service.SLooseissuemappingFacadeREST;
import soul.catalogue.Location;
import soul.catalogue.service.LocationFacadeREST;
import soul.catalogue.LocationPK;
import soul.serial_master.SSupplierDetail;
import soul.serial_master.service.SSupplierDetailFacadeREST;
import soul.serialControl.SRequest;
import soul.serialControl.service.SRequestFacadeREST;
import soul.general_master.Libmaterials;
import soul.serialControl.SSubInv;
import soul.serialControl.service.SSubInvFacadeREST;
import soul.general_master.MFcltydept;
import soul.general_master.service.MFcltydeptFacadeREST;
import soul.general_master.ABudget;
import soul.general_master.BHeads;
import soul.general_master.service.ABudgetFacadeREST;
import soul.general_master.service.BHeadsFacadeREST;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.serialcontrol.sschedule")
public class SScheduleFacadeREST extends AbstractFacade<SSchedule> {

    @EJB
    private SMstFacadeREST sMstFacadeREST;
    @EJB
    private MFcltydeptFacadeREST mFcltydeptFacadeREST;
    @EJB
    private ABudgetFacadeREST aBudgetFacadeREST;
    @EJB
    private BHeadsFacadeREST bHeadsFacadeREST;
    @EJB
    private SSubInvFacadeREST sSubInvFacadeREST;
    @EJB
    private SRequestFacadeREST sRequestFacadeREST;
    @EJB
    private SSupplierDetailFacadeREST sSupplierDetailFacadeREST;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private SLooseissuemappingFacadeREST sLooseissuemappingFacadeREST;
    @EJB
    private BiblidetailsFacadeREST biblidetailsFacadeREST;
    @EJB
    private SFrequencyFacadeREST sFrequencyFacadeREST;
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    StringReader reader;
    JsonReader jsonReader;
    JsonObject jsonObject;
    SSchedule sSchedule;
    SMst sMst;
    String output = null;
    private String[] recIds;
    private String[] srNos;
    private String[] issues;
    private String[] volumes;
    

    private SSchedulePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;sSSrno=sSSrnoValue;sSRecid=sSRecidValue;sSVol=sSVolValue;sSIss=sSIssValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.serialControl.SSchedulePK key = new soul.serialControl.SSchedulePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> sSSrno = map.get("sSSrno");
        if (sSSrno != null && !sSSrno.isEmpty()) {
            key.setSSSrno(new java.lang.Integer(sSSrno.get(0)));
        }
        java.util.List<String> sSRecid = map.get("sSRecid");
        if (sSRecid != null && !sSRecid.isEmpty()) {
            key.setSSRecid(sSRecid.get(0));
        }
        java.util.List<String> sSVol = map.get("sSVol");
        if (sSVol != null && !sSVol.isEmpty()) {
            key.setSSVol(sSVol.get(0));
        }
        java.util.List<String> sSIss = map.get("sSIss");
        if (sSIss != null && !sSIss.isEmpty()) {
            key.setSSIss(sSIss.get(0));
        }
        return key;
    }

    public SScheduleFacadeREST() {
        super(SSchedule.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(SSchedule entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(SSchedule entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.serialControl.SSchedulePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public SSchedule find(@PathParam("id") PathSegment id) {
        soul.serialControl.SSchedulePK key = getPrimaryKey(id);
        return super.find(key);
    }
    
    @PUT
    @Path("viewers/view/rcpts/data")
    public void countAll() {
        SSchedule s = new SSchedule();
        Query query = getEntityManager().createNativeQuery(s.getRcpts());
        int result = query.executeUpdate();
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    //Added Manually
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();

        switch (query) {
            case "findBySSRecidAndGtEqSrNo":
                valueList.add(valueString[0]);
                valueList.add(Integer.parseInt(valueString[1]));
                break;

            case "findBySSSrno":
                valueList.add(Integer.parseInt(valueString[0]));
                break;
            case "findBySSBindNo":
                valueList.add(valueString[0]);
                break;
            case "findBySSRecidANDStatus":
                valueList.add(valueString[0]);
                valueList.add(valueString[1]);
                break;
            case "listofAllNonReceivedIssues":
                return super.findBy("SSchedule." + query, new SimpleDateFormat("yyyy-MM-dd"));

            case "findBySSStatusANDLtExpDt":
                valueList.add(valueString[0]);
                try {
                    valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[1]));
                } catch (ParseException ex) {
                    valueList.add(new Date());
                    Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "getForRegenerate":
                System.out.println("V");
                valueList.add(valueString[0]);
                try {
                    valueList.add(new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy").parse(valueString[1]));
                    //valueList.add(new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy").parse(valueString[2]));
                } catch (ParseException ex) {
                    //valueList.add(new Date());
                    Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "existornot":
                System.out.println("V");
                valueList.add(valueString[0]);
                try {
                    valueList.add(new SimpleDateFormat("YYYY-MM-DD").parse(valueString[1]));
                    valueList.add(new SimpleDateFormat("YYYY-MM-DD").parse(valueString[2]));
                } catch (ParseException ex) {
                    //valueList.add(new Date());
                    Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "findSetByRecId":
                valueList.add(valueString[0]);
                try {
                    valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[1]));
                } catch (ParseException ex) {
                    valueList.add(new Date());
                    Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listofAllNonReceivedIssuesByExpectedDateUpTo":
                try {
                    valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[0]));
                } catch (ParseException ex) {
                    Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "findByForCheckIn":            //No Parameters to set
                break;

            //Check-In Report starts here
            case "checkInReportByAnyStatus":
                break;
            case "checkInReportofExpectedIssues":
                break;
            case "checkInReportofReceivedIssues":
                break;
            case "checkInReportofNonReceivedIssues":
                break;
            case "checkInReportofBindedIssues":
                break;

            case "checkInReportOfAnyStatusByTitle":
                valueList.add(valueString[0] + "%");
                break;
            case "checkInReportOfExpectedIssuesByTitle":
                valueList.add(valueString[0] + "%");
                break;
            case "checkInReportOfReceivedIssuesByTitle":
                valueList.add(valueString[0] + "%");
                break;
            case "checkInReportOfNonReceivedIssuesByTitle":
                valueList.add(valueString[0] + "%");
                break;
            case "checkInReportOfBindedIssuesByTitle":
                valueList.add(valueString[0] + "%");
                break;

            //CHeck-In report ends here
            case "getScheduleToUpdate":
                valueList.add(valueString[0]);
                break;
            case "listofTitlesCheckInDetails":            //No Parameters to set
                break;
            default:
                valueList.add(valueString[0]);
                System.out.println("D");
                break;
            //findByMaxSSSrno by ScheduleGeneration

        }
        return super.findBy("SSchedule." + query, valueList);
    }

    @DELETE
    @Path("removeBy/{namedQuery}/{values}")
    public void removeBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch (query) {
            case "deleteSchedule":
                valueList.add(valueString[0]);
                try {
                    valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[1]));
                } catch (ParseException ex) {
                    valueList.add(new Date(Long.valueOf("978287400000")));
                    Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "deleteCheckIn":
                valueList.add(valueString[0]);
                valueList.add(Integer.parseInt(valueString[1]));
                valueList.add(valueString[2]);
                valueList.add(valueString[3]);
                break;
                
            case "deleteAllExpected":
                valueList.add(valueString[0]);
                valueList.add(valueString[1]);
                break;
               
            case "deleteAllExpectedUpto":
                valueList.add(valueString[0]);
                valueList.add(valueString[1]);
                valueList.add(valueString[2]);
                break;
            case "deleteExisting":
                valueList.add(valueString[0]);
                 {
                    try {
                        valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[1]));
                        valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[2]));
                    } catch (ParseException ex) {
                        Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                break;
                

            case "removeReceived":
                valueList.add(valueString[0]);
                valueList.add(Integer.parseInt(valueString[1]));
                valueList.add(valueString[2]);
                valueList.add(valueString[3]);
                break;

            case "listofItemsforCheckIn":
                valueList.add(valueString[0]);

                break;
            default:
                valueList.add(valueString[0]);
                break;
        }
        super.removeBy("SSchedule." + query, valueList);
    }

    @GET
    @Path("checkScheduleExistsOrNot/{recid}/{date}")
    @Produces("text/plain")
    public String checkScheduleExistsOrNot(@PathParam("recid") String recid,@PathParam("date") String date)
    {
 
        String output = "";
        String[] valueString = date.split(",");
        List<SSchedule> result = new ArrayList<>();

        result = findBy("existornot",recid+","+valueString[0]+","+valueString[1]);
        System.out.println("result:"+result.size());
        System.out.println("rsult"+result.get(0));
        if(result.size()==1){
            output="yes";
        } else {
            output = "no";
        }

        return output;
    }
    
   
    
    @DELETE
    @Path("deleteExistingSchedule/{recid}/{date}")
    @Produces({"text/plain"})
    public void deleteExistingSchedule(@PathParam("recid") String recid,@PathParam("date") String date,@PathParam("deleteExisting") String deleteExisting) {
        String q = "";
        String output = "";
        String[] valueString = date.split(",");
        removeBy("deleteExisting", recid + "," + valueString[0] + "," + valueString[1]);
      }
    

 
    //will display the tiles to be processes for check in into dropdown
    @GET
    @Path("listofTitlesCheckInDetails")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> listofTitlesCheckInDetails() {
        List<SSchedule> getAll = null;
        getAll = findBy("listofTitlesCheckInDetails", "null");
        return getAll;
    }
    
    @GET
    @Path("getSetDetailsBySetNo/{setNo}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> getSetDetailsBySetNo(@PathParam("setNo") String setNo) {
        List<SSchedule> getAll = null;
        getAll = findBy("findBySSBindNo", setNo);
        String recId = getAll.get(0).getSSchedulePK().getSSRecid();
        return getAll;
    }

    @GET
    @Path("listofItemsforCheckIn/{recId}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> listofTitlesCheckInDetails(@PathParam("recId") String recId) {
        List<SSchedule> getAll = null;
        getAll = findBy("listofItemsforCheckIn", recId);
        return getAll;
    }

    @GET
    @Path("retrieveAll/{accept}/{form}")
    @Produces({"application/xml", "application/json"})
    //public List<SSchedule> retrieveAll(@QueryParam("accept") String accept, @QueryParam("form") String form) {
    public List<SSchedule> retrieveAll(@PathParam("accept") String accept, @PathParam("form") String form) {    
        List<SSchedule> getAll = null;
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (accept.equals("XML")) {
            if (form.equals("NonReceived")) {
                getAll = findBy("findBySSStatusANDLtExpDt", "Expected," + date);
            }
        }
        return getAll;
    }

    @GET
    @Path("retrieveAllGeneratedSchedule/{recId}/{srNoForDisplay}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> retrieveAllGeneratedSchedule(@PathParam("recId") String recId, @PathParam("srNoForDisplay") String displaySrNo) {
        List<SSchedule> getAll = null;

        if ("null".equals(displaySrNo)) {
            getAll = findBy("findBySSRecidANDStatus", recId + "," + "Expected");
        } else {
            getAll = findBy("findBySSRecidAndGtEqSrNo", recId + "," + displaySrNo);
        }
        return getAll;
    }

//    @POST
//    @Path("convertIntoNonReceived")
//    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
//    @Produces("text/plain")
//    public String convertIntoNonReceived(@FormParam("rows") List<String> row)
//    {
//        String[] rows = new String[row.size()];
//        rows = row.toArray(rows);
//        for(int i=0; i<rows.length; i++){
//            reader = new StringReader(rows[i]);
//            jsonReader = Json.createReader(reader);
//            jsonObject = jsonReader.readObject();
//            SSchedulePK sSchedulePK = new SSchedulePK();
//            sSchedulePK.setSSIss(jsonObject.getString("issue"));
//            sSchedulePK.setSSRecid(jsonObject.getString("recId"));
//            sSchedulePK.setSSSrno(Integer.parseInt(jsonObject.getString("srNo")));
//            sSchedulePK.setSSVol(jsonObject.getString("volume"));
//            sSchedule = find(sSchedulePK);
//           // sSchedule = find(" ;sSSrno="+jsonObject.getString("srNo")+";sSRecid="+jsonObject.getString("recId")+";sSVol="+jsonObject.getString("volume")+";sSIss="+);
//            sSchedule.setSSStatus("Non-Received");
//            edit(sSchedule);
//        }
//       return "Issues Converted into Non-Received.";
//    }
    //to update status to non received in Non-Receive Process
    @PUT
    @Path("convertInToNonReceived")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String convertInToNonReceived(@FormParam("recordIds") String recordIds, @FormParam("srNo") String srNo,
            @FormParam("volume") String volume, @FormParam("issue") String issue) {
        recIds = recordIds.split(",");
        srNos = srNo.split(",");
        volumes = volume.split(",");
        issues = issue.split(",");

        for (int i = 0; i < recIds.length; i++) {
            SSchedulePK s = new SSchedulePK();
            s.setSSIss(issues[i]);
            s.setSSRecid(recIds[i]);
            s.setSSSrno(Integer.parseInt(srNos[i]));
            s.setSSVol(volumes[i]);

            sSchedule = find(s);
            //  sSchedule.setSSchedulePK(s);
            sSchedule.setSSStatus("Non-Received");
            edit(sSchedule);
        }
        return "Issues Converted into Non-Received.";
    }

    //list of non-received orders, in non-received process all orders
    @GET
    @Path("listofAllNonReceivedIssues")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> listofAllNonReceivedIssues() {
        String q = "";
        List<SSchedule> result = new ArrayList<>();
        Query query;
        q = "select s_title,s_s_recid,s_s_vol,s_s_iss,s_s_srno,s_s_pub_dt,s_s_exp_dt,s_s_remark from s_schedule,s_mst where s_s_status like 'E%' and s_s_exp_dt<current_Date() and s_recid=s_s_recid";

        query = getEntityManager().createNativeQuery(q, SSchedule.class);
        result = (List<SSchedule>) query.getResultList();
        return result;
    }

    //list of non-received orders, in non-received process by title
    @GET
    @Path("listofAllNonReceivedIssuesByTitle/{title}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> listofAllNonReceivedIssues(@PathParam("title") String title) {
        String q = "";
        List<SSchedule> result = new ArrayList<>();
        Query query;

        List<SMst> getAll = null;
        getAll = sMstFacadeREST.findBy("findBySTitle", title);
        System.out.println("getaLL" + getAll);

        StringJoiner joiner = new StringJoiner("','");
        for (SMst get : getAll) {
            joiner.add(get.getSRecid());
        }

        System.out.println("'" + joiner + "'");

        q = "select s_title,s_s_recid,s_s_vol,s_s_iss,s_s_srno,s_s_pub_dt,s_s_exp_dt,s_s_remark from s_schedule,s_mst where s_s_status like 'E%' and s_s_exp_dt<current_Date() and s_recid IN ('" + joiner + "')";
        query = getEntityManager().createNativeQuery(q, SSchedule.class);
        result = (List<SSchedule>) query.getResultList();
        return result;
    }

    //list of non-received orders, in non-received process by date
    @GET
    @Path("listofAllNonReceivedIssuesByExpectedDateUpTo/{date}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> listofAllNonReceivedIssuesByExpectedDateUpTo(@PathParam("date") String date) {
        String q = "";
        List<SSchedule> result = new ArrayList<>();
        Query query;
        Date dt = null;
        try {
            dt = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }

        q = "select s_title,s_s_recid,s_s_vol,s_s_iss,s_s_srno,s_s_pub_dt,s_s_exp_dt,s_s_remark from s_schedule,s_mst where s_s_status like 'E%' and s_s_exp_dt < '" + date + "' and s_recid = s_s_recid";
        System.out.println("date: " + date);
        query = getEntityManager().createNativeQuery(q, SSchedule.class);
        result = (List<SSchedule>) query.getResultList();
        return result;
    }

    //list of orders, for the process of removing received issues
    @GET
    @Path("retrieveAllforRemovingReceived")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> retrieveAllforRemovingReceived() {
        String q = "";
        List<SSchedule> result = new ArrayList<>();
        Query query;
        q = "select s_title,s_s_recid,s_s_vol,s_s_iss,s_s_srno,s_s_pub_dt,s_s_exp_dt,s_s_rcpt_dt,s_s_remark \n"
                + "from s_schedule,s_mst where s_s_status like 'Received%' and s_s_rcpt_dt < current_Date() and s_recid=s_s_recid";

        query = getEntityManager().createNativeQuery(q, SSchedule.class);
        result = (List<SSchedule>) query.getResultList();
        return result;
    }

//    //commercail bindinig 
//    @GET
//    @Path("retrieveAllforRemovingReceived")
//    @Produces({"application/xml", "application/json"})
//    public List<SSchedule> retrieveAllforRemovingReceived() {
//        String q = "";
//        List<SSchedule> result = new ArrayList<>();
//        Query query;
//        q = "select s_title,s_s_recid,s_s_vol,s_s_iss,s_s_srno,s_s_pub_dt,s_s_exp_dt,s_s_rcpt_dt,s_s_remark \n" +
//                "from s_schedule,s_mst where s_s_status like 'Received%' and s_s_rcpt_dt < '2018-03-16' and s_recid=s_s_recid";
//
//        query = getEntityManager().createNativeQuery(q, SSchedule.class);
//        result = (List<SSchedule>) query.getResultList();
//        return result;
//    }
    //list of orders, for the process of removing received issues by title
    @GET
    @Path("retrieveAllforRemovingReceivedByTitle/{title}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> retrieveAllforRemovingReceivedByTitle(@PathParam("title") String title) {
        String q = "";
        List<SSchedule> result = new ArrayList<>();
        Query query;

        List<SMst> getAll = null;
        getAll = sMstFacadeREST.findBy("findBySTitle", title);
        System.out.println("getaLL" + getAll);

        StringJoiner joiner = new StringJoiner("','");
        for (SMst get : getAll) {
            joiner.add(get.getSRecid());
        }

        System.out.println("'" + joiner + "'");

        q = "select s_title,s_s_recid,s_s_vol,s_s_iss,s_s_srno,s_s_pub_dt,s_s_exp_dt,s_s_remark from s_schedule,s_mst where s_s_status like 'Received%' and s_s_exp_dt < current_Date() and s_recid IN ('" + joiner + "')";
        query = getEntityManager().createNativeQuery(q, SSchedule.class);
        result = (List<SSchedule>) query.getResultList();
        return result;
    }

    //list of orders, for the process of removing received issues by inbetween expected date
    @GET
    @Path("retrieveAllforRemovingReceivedByDateBetween/{date}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> retrieveAllforRemovingReceivedByDateBetween(@PathParam("date") String date) {
        String q = "";
        List<SSchedule> result = new ArrayList<>();
        Query query;
        String[] valueString = date.split(",");

        q = "select s_title,s_s_recid,s_s_vol,s_s_iss,s_s_srno,s_s_pub_dt,s_s_exp_dt,s_s_rcpt_dt,s_s_remark \n"
                + " from s_schedule,s_mst where s_s_status like 'Received%' and s_s_recid \n"
                + " IN (select s_s_recid from s_schedule where ( s_s_rcpt_dt BETWEEN '" + valueString[0] + "' AND '" + valueString[1] + "')) and s_s_recid = s_recid";

        query = getEntityManager().createNativeQuery(q, SSchedule.class);
        result = (List<SSchedule>) query.getResultList();
        return result;
    }

    //Services for Reminder letter in Check-In Tab
    //Dropdowns
    //all Suppliers in Dropdown
    @GET
    @Path("allSuppliers")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> allSuppliers() {
        String q = "";
        List<SSchedule> result = new ArrayList<>();
        Query query;
        q = "select a_s_name from s_supplier_detail where a_s_cd in (select s_sup_cd from s_subscription where s_pubsup='Y') group by a_s_cd";

        query = getEntityManager().createNativeQuery(q, SSchedule.class);
        result = (List<SSchedule>) query.getResultList();
        return result;
    }

    //list of orders, for the process of removing received issues
    //all Publishers in Dropdown
    @GET
    @Path("allPublishers")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> allPublishers() {
        String q = "";
        List<SSchedule> result = new ArrayList<>();
        Query query;
        q = "select a_s_name from s_supplier_detail where a_s_cd in (select distinct s_pub_cd from s_mst) group by a_s_cd";

        query = getEntityManager().createNativeQuery(q, SSchedule.class);
        result = (List<SSchedule>) query.getResultList();
        return result;
    }

    // check-In Report --Starts Here
    //This methods return the details about orders for check-In report generation
    @GET
    @Path("checkInReportByAnyStatus")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> checkInReportByAnyStatus() {
        List<SSchedule> getAll = null;
        getAll = findBy("checkInReportByAnyStatus", "null");
        return getAll;
    }

    @GET
    @Path("checkInReportofExpectedIssues")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> checkInReportofExpectedIssues() {
        List<SSchedule> getAll = null;
        getAll = findBy("checkInReportofExpectedIssues", "null");
        return getAll;
    }

    @GET
    @Path("checkInReportofReceivedIssues")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> checkInReportofReceivedIssues() {
        List<SSchedule> getAll = null;
        getAll = findBy("checkInReportofReceivedIssues", "null");
        return getAll;
    }

    @GET
    @Path("checkInReportofNonReceivedIssues")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> checkInReportofNonReceivedIssues() {
        List<SSchedule> getAll = null;
        getAll = findBy("checkInReportofNonReceivedIssues", "null");
        return getAll;
    }

    @GET
    @Path("checkInReportofBindedIssues")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> checkInReportofBindedIssues() {
        List<SSchedule> getAll = null;
        getAll = findBy("checkInReportofBindedIssues", "null");
        return getAll;
    }

    @GET
    @Path("checkInReportOfAnyStatusByTitle/{title}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> checkInReportOfAnyStatusByTitle(@PathParam("title") String title) {
        List<SSchedule> getAll = null;
        getAll = findBy("checkInReportOfAnyStatusByTitle", title);
        return getAll;
    }

    @GET
    @Path("checkInReportOfExpectedIssuesByTitle/{title}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> checkInReportOfExpectedIssuesByTitle(@PathParam("title") String title) {
        List<SSchedule> getAll = null;
        getAll = findBy("checkInReportOfExpectedIssuesByTitle", title);
        return getAll;
    }

    @GET
    @Path("checkInReportOfReceivedIssuesByTitle/{title}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> checkInReportOfReceivedIssuesByTitle(@PathParam("title") String title) {
        List<SSchedule> getAll = null;
        getAll = findBy("checkInReportOfReceivedIssuesByTitle", title);
        return getAll;
    }

    @GET
    @Path("checkInReportOfNonReceivedIssuesByTitle/{title}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> checkInReportOfNonReceivedIssuesByTitle(@PathParam("title") String title) {
        List<SSchedule> getAll = null;
        getAll = findBy("checkInReportOfNonReceivedIssuesByTitle", title);
        return getAll;
    }

    @GET
    @Path("checkInReportOfBindedIssuesByTitle/{title}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> checkInReportOfBindedIssuesByTitle(@PathParam("title") String title) {
        List<SSchedule> getAll = null;
        getAll = findBy("checkInReportOfBindedIssuesByTitle", title);
        return getAll;
    }

    @POST
    @Path("addAdditionalIssues")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String addAdditionalIssues(@Pattern(regexp = "^[A-Za-z0-9]*$", message = "{recId.pattern}") @FormParam("recId") String recId,
            @FormParam("volume") String volume, @FormParam("issue") String issue,
            @Pattern(regexp = "^[0-9]*$", message = "{srNo.pattern}") @FormParam("srNo") int srNo, 
            @Pattern(regexp = "^[A-Za-z]*$", message = "{status.pattern}") @FormParam("status") String status,
            @FormParam("receiptDate") String receiptDate, @FormParam("expectedDate") String expectedDate,
            @FormParam("remarks") String remarks, @FormParam("publicationDate") String publicationDate) {
        String output;

        SSchedule sSchedule = new SSchedule();
        SSchedulePK sSchedulePK = new SSchedulePK();
        try {
            sMstFacadeREST.find(recId);
        } catch (NullPointerException d) {
            return "Invalid record id.";
        }
        sSchedulePK.setSSRecid(recId);
        sSchedulePK.setSSIss(issue);
        sSchedulePK.setSSSrno(srNo);
        sSchedulePK.setSSVol(volume);
        sSchedule.setSSchedulePK(sSchedulePK);

        sSchedule.setSSRemark(remarks);
        sSchedule.setSSStatus(status);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            sSchedule.setSSRcptDt(format.parse(receiptDate));
            sSchedule.setSSExpDt(format.parse(expectedDate));
            sSchedule.setSSPubDt(format.parse(publicationDate));
        } catch (ParseException ex) {
            sSchedule.setSSRcptDt(new Date());
            sSchedule.setSSExpDt(new Date());
            sSchedule.setSSPubDt(new Date());
            Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        int count = Integer.parseInt(countREST());
        create(sSchedule);
        if (count == Integer.parseInt(countREST())) {
            output = "Someting went wrong, failed to add issue.";
        } else {
            output = "Issue added successfully.";
        }
        return output;
    }

    //service to update reminder counter
    @GET
    @Path("updateRemainderCounter/{recordIds}")
    @Produces({"application/xml", "application/json"})
    public String updateRemainderCounter(@PathParam("recordIds") String recordIds) {

        recIds = recordIds.split(",");
        List<SSchedule> schedule;
        for (String recId : recIds) {
            try {
                schedule = findBy("getForCounterUpdate", recId);
            } catch (NullPointerException d) {
                return "Invalid record Id. Or something is worng with serial status";
            }

            System.out.println("schedule:        " + schedule);
            for (int i = 0; i < schedule.size(); i++) {
                String currentCount = schedule.get(i).getSSReminder();
                System.out.println("currentCount: " + currentCount);
                //System.out.println("currentCountContent: " + currentCount.contentEquals(null));
                //System.out.println("currentCount.length(): " + currentCount.length());
                if (currentCount == null) {
                    if (schedule.get(i).getSSStatus().contentEquals("Expected")) {
                        schedule.get(i).setSSReminder("1");
                        System.out.println("D");
                    } else {
                        System.out.println("V");
                        schedule.get(i).setSSReminder("null");
                    }
                } else {
                    int newCount = Integer.parseInt(currentCount);
                    String newestcount = Integer.toString(++newCount);
                    schedule.get(i).setSSReminder(newestcount);
                    System.out.println("oldCOunt:" + newCount);
                    System.out.println("newCOunt:" + newestcount);
                }
                edit(schedule.get(i));
            }
        }
        return "Remainder Counter updated.";
    }

    //list of titles, for the process of preperation of sets
    @GET
    @Path("retrieveAllforPreparationOfSets")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> retrieveAllforPreparationOfSets() {
        String q = "";
        List<SSchedule> result = new ArrayList<>();
        Query query;

        q = "select * from s_schedule,s_mst where  s_schedule.s_s_status like 'R%' and s_s_rcpt_dt <= current_Date() group by s_s_recid";
        query = getEntityManager().createNativeQuery(q, SSchedule.class);
        result = (List<SSchedule>) query.getResultList();
        return result;
    }
    
    @GET
    @Path("orderDetailsForInhouseBinding/{recId}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> orderDetailsForInhouseBinding(@PathParam("recId") String recId) {
        String q = "";
        List<SSchedule> result = new ArrayList<>();
        Query query;

        q = "select s_s_bind_no,s_s_recid,s_s_vol,s_s_iss,s_s_srno,s_s_pub_dt,s_s_exp_dt,s_s_status,s_s_rcpt_dt,s_s_remark from s_schedule where s_s_recid = '" + recId + "' and s_s_status='Received'";
        query = getEntityManager().createNativeQuery(q, SSchedule.class);
        result = (List<SSchedule>) query.getResultList();
        return result;
    }
    
    //list of titles, for the process of preperation of sets - inhouse binding
    @GET
    @Path("retrieveAllforPreparationOfSetsIB")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> retrieveAllforPreparationOfSetsIB() {
        String q = "";
        List<SSchedule> result = new ArrayList<>();
        Query query;

        q = "select distinct * from s_mst,s_schedule where s_recid=s_s_recid and s_schedule.s_s_status like'R%' order by s_title";
          query = getEntityManager().createNativeQuery(q, SSchedule.class);
        result = (List<SSchedule>) query.getResultList();
        return result;
    }

    @GET
    @Path("findSetByRecId/{recId}/{date}")
    @Produces({"application/xml", "application/json"})
    public List<SSchedule> findSetByRecId(@PathParam("recId") String recId, @PathParam("date") String date) {
        List<SSchedule> getAll = null;
        getAll = findBy("findSetByRecId", recId + "," + date);
        return getAll;
    }

    @DELETE
    @Path("deleteSchedule/{recId}/{pubDate}")
    @Produces("text/plain")
    public String deleteSchedule(@Pattern(regexp = "^[A-Za-z0-9]*$", message = "{recId.pattern}") @PathParam("recId") String recId, 
            @PathParam("pubDate") String pubDate) {
        removeBy("deleteSchedule", recId + "," + pubDate);
        try {
            sMst = sMstFacadeREST.find(recId);
        } catch (NullPointerException e) {
            return "Invalid record id...";
        }
        List<SSchedule> schedule = findBy("findBySSRecid", recId);
        //System.out.println("Schedule size: "+schedule.size());

        //updatings schedule even if there are records for a record id in s_Schedule
//        if(schedule.size() == 0){
//            sMst.setSMstStatus("I");
//        }
//        sMstFacadeREST.edit(sMst);
        output = "Schedules Deleted";
        return output;
    }

    /**
     *
     * @author dashrath
     * @param endVolume
     */
    //service to generate new schedule
    @POST
    @Path("generateNewSchedule")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain"})
    public String generateNewSchedule(@FormParam("recId") String recId,
            @FormParam("volNoFrom") Integer startVolume, @FormParam("issNoFrom") Integer startIssue,
            @FormParam("volNoTo") Integer endVolume, @FormParam("issNoTo") Integer endIssue, @FormParam("noOfCopies") Integer noOfCopies,
            @FormParam("issPerVol") int issPerVol,
            @FormParam("volPrifix") String volPrefix, @FormParam("issPrifix") String issPrefix,
            @FormParam("status") String status, @FormParam("rcptDate") String rcptDate,
            @FormParam("remark") String remark, @FormParam("pricePerIss") String pricePerIss,
            @FormParam("bindNo") String bindNo, @FormParam("reminder") String reminder,
            @FormParam("deleteIssue") String deleteIssue ,@FormParam("upto") String upto,
            @FormParam("flag") String flag, @FormParam("leadTime") int leadTime, @FormParam("regenerate") String regenerate,
            @FormParam("fullVolCheck") String firstVolINF, @FormParam("issFirstVol") int issPerFV, @FormParam("issNoFromVol") int startIssueNfFV,
            @FormParam("repeatIssCheck") String repeatINoWEVNo, @FormParam("masterChangeCheck") String ReflectCInToMstDb) {
        //repeatINoWEVNo -> Repeat issue no with each volume no
        //               -> acceps parameters as (yes/no) and in client side checkbox will represent this parameter
        //firstVolINF -> if starting in between issues, and ssome starting issues are skipped
        //               -> acceps parameters as (yes/no) and in client side checkbox will represent this parameter
        //ReflectCInToMstDb -> reflect changes into master database
        //               -> acceps parameters as (yes/no) and in client side checkbox will represent this parameter
        //regenerate -> for regenerating the schedule and update existing schedule to old
        //               -> acceps parameters as (yes/no) and in client side prompt box will represent this parameter

        //Comments which are added as plain text in this function are actually for checking the status of service
        SMst smst;
        SSchedule newsSchedule = null;
        try {
            smst = sMstFacadeREST.find(recId);
        } catch (NullPointerException d) {
            return "Invalid record id.";
        }
//        System.out.println("Start Volume: "+smst.getSStVol()+"\tStart Issue: "+smst.getSStIss());
//        System.out.println("End Volume: "+smst.getSEnVol()+"\tEnd Issue: "+smst.getSEnIss());

        //Local variable
        //int startIssueNfFV = issPerFV + 1;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String frequency = smst.getSFreqCd();
        Date pubDate = smst.getSStDt();
        String newVolume = null;
        String newIssue = null;
        int sTemp = 1;
        int mainCounter = 1;
        int totalVolumes = endVolume - startVolume + 1;
        int totalIssues = endIssue - startIssue + 1;
        System.out.println("totalVolumes: " + totalVolumes);
        System.out.println("totalIssues: " + totalIssues);
        int srNo = getMaxSrNo();
        System.out.println("Max srno: " + srNo);
//        if(Integer.parseInt(issPerVol) != endIssue)
//            return "Data is not proper";
        try {
            String sIssue = Integer.toString(startIssue);
            String eIssue = Integer.toString(endIssue);
            String sVolume = Integer.toString(startVolume);
            String eVolume = Integer.toString(endVolume);
            String noOfCp = Integer.toString(noOfCopies);
        } catch (NullPointerException e) {
            return "NECESSARY DATA IS UNAVILABLE LIKE VOLUME FROM OR VOLUME TO OR ISSUE FROM OR ISSUE TO OR ISSUE/VOLUME OR NO OF COPIES";
        }

        int kCount = 0;
        int k2 = 0;
        Date sDate = smst.getSStDt();
        Date eDate = smst.getSEnDt();
        List<SSchedule> getAll = null;
        getAll = findBy("getForRegenerate", recId + "," + sDate);
        System.out.println("getAll of schedule: " + getAll);
        if (totalIssues / totalVolumes != issPerVol) {
            return "ERROR occured :::: Data is not proper!!!";
        }
//        if(firstVolINF.contentEquals("yes")){
//            startIssue = startIssueNfFV;
//            endVolume = endVolume + 1;
//            sTemp = startIssueNfFV;
//        }
        if (noOfCopies > smst.getSReqCopy()) {
            output = "no of copy can not be greater then requested copies";
        } else if (getAll.size() >= 0 && regenerate.contentEquals("yes")) {
            if (deleteIssue.contentEquals("deleteSelected")) {
                deleteExpectedIssues(smst.getSTitle());
            }   
             if (deleteIssue.contentEquals("deleteUpto")) {
                deleteExpectedIssuesUpto(smst.getSTitle(),upto);
            }   
            for (int i = 1; i <= noOfCopies; i++) {
                if (firstVolINF.contentEquals("on")) {
                    endVolume = endVolume + 1;
                }
                for (int k = startVolume; k <= endVolume; k++) {
                    int counter = 0;

                    for (int j = sTemp; j <= endIssue; j++) {
                        if (startIssueNfFV == j && endVolume == k) {
                            break;
                        }
                        SSchedule sSchedule = new SSchedule();
                        SSchedulePK sSchedulePK = new SSchedulePK();
                        //entry in s_schedule
                        int counter2 = 0;
                        if (repeatINoWEVNo.contentEquals("on")) {
                            if (j > issPerVol) {
                                j = startIssue;
                                sTemp = j;
                            }

                            // System.out.println(j+" "+sTemp);
                        }
                        if (j >= issPerVol && counter2 == 0) {
                            sTemp = j;
                            counter2++;
                        }
                        System.out.println(" DASHRATH1: " + firstVolINF);
                        if (firstVolINF.contentEquals("on")) {
                            System.out.println(" DASHRATH ");
                            if (k == startVolume && j <= issPerFV) {

                            } else {

                                System.out.println(volPrefix + k + " - " + issPrefix + j);
                                sSchedulePK.setSSVol(volPrefix + k);
                                sSchedulePK.setSSIss(issPrefix + j);
                                if (kCount == 0 && k == endVolume) {
                                    //System.out.println("-------------"+k+"------------------");
                                    k2 = k;
                                    kCount++;
                                }
                                if ((k2 == endVolume && j == endIssue) || (k2 == endVolume && j == issPerVol)) {
                                    //System.out.println("-------------"+k2+"------------------");
                                    k2 = k2 + 1;
                                    int j3 = 0;
                                    for (int j2 = 1; j2 <= issPerFV; j2++) {
                                        if (!repeatINoWEVNo.contentEquals("on")) {
                                            j3 = j + j2;
                                        } else if (repeatINoWEVNo.contentEquals("on")) {
                                            j3 = j2;
                                        }
                                        System.out.println(volPrefix + k2 + " - " + issPrefix + j3);
                                        sSchedulePK.setSSVol(volPrefix + k2);
                                        sSchedulePK.setSSIss(issPrefix + j3);
                                    }
                                }
                            }
                        }
                        srNo = srNo + 1;
                        sSchedulePK.setSSSrno(srNo);
//                    newVolume = volPrefix + startVolume;
//                    newIssue = issPrefix + j;
                        sSchedulePK.setSSVol(volPrefix + k);
                        sSchedulePK.setSSIss(issPrefix + j);
                        //System.out.println(volPrefix + k + " - " + issPrefix + j);
                        counter++;
//                        if (counter == issPerVol) {
//                            sTemp++;
//                            break;
//                        }
                        sSchedulePK.setSSRecid(recId);
                        sSchedule.setSSStatus(status);
                        sSchedule.setSSchedulePK(sSchedulePK);

                        //Date pubDate = null;
                        SFrequency freq = sFrequencyFacadeREST.find(frequency);
                        int noOdDays = freq.getSFDd();
                        int noOdMonths = freq.getSFMm();
                        int noOdYears = freq.getSFYy();
                        Date expDate = null;
                        Date lPubDate = null;

                        try {
                            if ((mainCounter == 1 && j == 1) || (j == startIssueNfFV && mainCounter == 2)) {
                                System.out.println("1: " + mainCounter + " " + j + " " + startIssueNfFV);
//                                Date newPubDate = pubDate;
                                System.out.println("pubDate:" + new java.sql.Date(pubDate.getTime()));
                                sSchedule.setSSPubDt(new java.sql.Date(pubDate.getTime()));
                                System.out.println("pubDate:" + new java.sql.Date(pubDate.getTime()));
                                mainCounter = 2;
                                System.out.println("1: " + mainCounter + " " + j + " " + startIssueNfFV);
                            } else {
                                System.out.println("2 n more: " + mainCounter + " " + j + " " + startIssueNfFV);
                                lPubDate = getLastPubDate();
                                Date newPubDate;
                                if (freq.getSFDmy().contentEquals("D")) {
                                    newPubDate = addDays(lPubDate, noOdDays);
                                    sSchedule.setSSPubDt(newPubDate);
                                } else if (freq.getSFDmy().contentEquals("M")) {
                                    if (freq.getSFCd().contentEquals("s")) {
                                        newPubDate = addDays(lPubDate, noOdDays);
                                        sSchedule.setSSPubDt(newPubDate);
                                    } else {
                                        newPubDate = addMonths(lPubDate, noOdMonths);
                                        sSchedule.setSSPubDt(newPubDate);
                                    }
                                } else if (freq.getSFDmy().contentEquals("Y")) {
                                    newPubDate = addYears(lPubDate, noOdYears);
                                    sSchedule.setSSPubDt(newPubDate);
                                }
                            }
                            System.out.println("expDate:" + sSchedule.getSSPubDt() + "    " + sSchedule.getSSchedulePK().getSSSrno());
                            expDate = addDays(sSchedule.getSSPubDt(), leadTime);
                            //increase publication date every month or by frequency for publication date for each issue per volume
                            sSchedule.setSSExpDt(expDate);  //add no of days(Lead time) to the publication date for each issue expected date
                            sSchedule.setSSRcptDt(format.parse(rcptDate));
                        } catch (ParseException ex) {
                            Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        sSchedule.setSSPriceperissue(BigDecimal.ZERO);
                        //will break the loop when subscription period crosses the date
                        if (k == startVolume && j <= issPerFV) {
                            System.out.println(" DASHRATH10: " + firstVolINF);
                        } else {
                            System.out.println("main: " + startVolume + " " + j + " " + k + " " + endVolume);
                            if ((startIssueNfFV == j && endVolume == k) || (j < startIssueNfFV && startVolume == k)) {
                                System.out.println("within if.......  k= " + k + "   j=" + j);
                                break;
                            } else {
                                System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssss   " + k + " " + j);
                              newsSchedule =  createAndGet(sSchedule);
                            }

                            if (counter == issPerVol) {
                                System.out.println("ssssssssssssss111111111111111111111111");
                                sTemp++;
                                break;
                            }
                        }
                    }
                }
            }
          //  output = "Schedule generated";
            output = ""+newsSchedule.getSSchedulePK().getSSSrno();
            for (int k = 0; k < getAll.size(); k++) {
                //if flag in databse is 0 that means that schedule is old and the one which are empty is newly generated
                getAll.get(k).setSSFlag(0);
                edit(getAll.get(k));
            }
        } else {
            System.out.println(" DASHRATH1: " + firstVolINF);
            for (int i = 1; i <= noOfCopies; i++) {
                try {
                    if (firstVolINF.contentEquals("on")) {
                        endVolume = endVolume + 1;
                    } else if (!firstVolINF.contentEquals("on") || firstVolINF.isEmpty()) {
                        firstVolINF = "no";
                        endVolume = endVolume;
                    }
                } catch (NullPointerException e) {
                    firstVolINF = "no";
                    endVolume = endVolume;
                }

                for (int k = startVolume; k <= endVolume; k++) {

                    int counter = 0;
                    System.out.println(" DASHRATH2: " + firstVolINF);
                    System.out.println("i................... " + i + "   sTemp " + sTemp + "   " + endIssue + "  endIssue");
                    for (int j = sTemp; j <= endIssue; j++) {

                        System.out.println("i " + i + "   sTemp " + sTemp + "  j" + j + "  " + endIssue + "  endIssue");
                        System.out.println(" DASHRATH3: " + firstVolINF);
                        SSchedule sSchedule = new SSchedule();
                        SSchedulePK sSchedulePK = new SSchedulePK();
                        //entry in s_schedule
                        int counter2 = 0;
                        System.out.println(" DASHRATH4: " + firstVolINF);
                        if (repeatINoWEVNo.contentEquals("on")) {
                            System.out.println(" DASHRATH5: " + firstVolINF);
                            if (j > issPerVol) {
                                System.out.println(" DASHRATH6: " + firstVolINF);
                                j = startIssue;
                                sTemp = j;
                            }
                            // System.out.println(j+" "+sTemp);
                        }
                        System.out.println(" DASHRATH7: " + firstVolINF);
                        if (j >= issPerVol && counter2 == 0) {
                            sTemp = j;
                            counter2++;
                        }
                        System.out.println(" DASHRATH8: " + firstVolINF);
                        if (firstVolINF.contentEquals("on")) {
                            System.out.println(" DASHRATH9: " + k + " " + startVolume + " " + j + " " + issPerFV);
                            if (k == startVolume && j <= issPerFV) {
                                System.out.println(" DASHRATH10: " + firstVolINF);
                            } else {
                                System.out.println(" DASHRATH11: " + firstVolINF);
                                System.out.println(volPrefix + k + " DASH " + issPrefix + j);
                                sSchedulePK.setSSVol(volPrefix + k);
                                sSchedulePK.setSSIss(issPrefix + j);
                                System.out.println("iff..1..condition ..... kCount = " + kCount + "   k = " + k + "  endVolume = " + endVolume);
                                if (kCount == 0 && k > endVolume) {
                                    //System.out.println("-------------"+k+"------------------");
                                    System.out.println("ifff....11111");
                                    k2 = k;
                                    kCount++;
                                }
                                System.out.println("iff..2...condition...  k2 = " + k2 + "  endVolume =  " + endVolume + "  j = " + j + "  issPerVol = " + issPerVol);
                                if ((k2 == endVolume && j == endIssue) || (k2 == endVolume && j == issPerVol)) {
                                    //System.out.println("-------------"+k2+"------------------");
                                    System.out.println("ifff....222222222222");
                                    k2 = k2 + 1;
                                    int j3 = 0;
                                    for (int j2 = 1; j2 <= issPerFV; j2++) {
                                        if (!repeatINoWEVNo.contentEquals("on")) {
                                            j3 = j + j2;
                                            System.out.println(" DASHRATH12: " + firstVolINF);
                                        } else if (repeatINoWEVNo.contentEquals("on")) {
                                            j3 = j2;
                                            System.out.println(" DASHRATH13: " + firstVolINF);
                                        }
                                        System.out.println(volPrefix + k2 + " Dash " + issPrefix + j3);
                                        sSchedulePK.setSSVol(volPrefix + k2);
                                        sSchedulePK.setSSIss(issPrefix + j3);
                                        System.out.println(" DASHRATH14: " + firstVolINF);
                                    }
                                }
                            }
                        }
                        System.out.println("hhh111111111");
                        srNo = srNo + 1;
                        sSchedulePK.setSSSrno(srNo);
//                    newVolume = volPrefix + startVolume;
//                    newIssue = issPrefix + j;
                        sSchedulePK.setSSVol(volPrefix + k);
                        sSchedulePK.setSSIss(issPrefix + j);
                        // System.out.println(volPrefix + k + " - " + issPrefix + j);
                        counter++;

                        sSchedulePK.setSSRecid(recId);
                        sSchedule.setSSStatus(status);
                        sSchedule.setSSchedulePK(sSchedulePK);

                        //Date pubDate = null;
                        SFrequency freq = sFrequencyFacadeREST.find(frequency);
                        int noOdDays = freq.getSFDd();
                        int noOdMonths = freq.getSFMm();
                        int noOdYears = freq.getSFYy();
                        Date expDate = null;
                        Date lPubDate = null;
                        try {

                            if ((mainCounter == 1 && j == 1) || (j == startIssueNfFV && mainCounter == 2)) {
                                System.out.println("1: " + mainCounter + " " + j + " " + startIssueNfFV);
                                Date newPubDate = pubDate;
                                sSchedule.setSSPubDt(pubDate);
                                mainCounter++;
                                System.out.println("pubDate:" + pubDate);

                            } else {
                                System.out.println("2 n more: " + mainCounter + " " + j + " " + startIssueNfFV);
                                System.out.println("hhhhhhh3333333333");
                                lPubDate = getLastPubDate();
                                Date newPubDate;
                                if (freq.getSFDmy().contentEquals("D")) {
                                    System.out.println("hhhhhh4444444444");
                                    newPubDate = addDays(lPubDate, noOdDays);
                                    sSchedule.setSSPubDt(newPubDate);
                                } else if (freq.getSFDmy().contentEquals("M")) {
                                    System.out.println("hhhh55555555555");
                                    if (freq.getSFCd().contentEquals("s")) {
                                        System.out.println("hhhhhhhhhhh66666666");
                                        newPubDate = addDays(lPubDate, noOdDays);
                                        sSchedule.setSSPubDt(newPubDate);
                                    } else {
                                        System.out.println("hhhhh7777777777");
                                        newPubDate = addMonths(lPubDate, noOdMonths);
                                        sSchedule.setSSPubDt(newPubDate);
                                    }
                                } else if (freq.getSFDmy().contentEquals("Y")) {
                                    System.out.println("hhhhh888888888");
                                    newPubDate = addYears(lPubDate, noOdYears);
                                    sSchedule.setSSPubDt(newPubDate);
                                }
                            }
                            expDate = addDays(sSchedule.getSSPubDt(), leadTime);
                            //increase publication date every month or by frequency for publication date for each issue per volume
                            sSchedule.setSSExpDt(expDate);  //add no of days(Lead time) to the publication date for each issue expected date
                            sSchedule.setSSRcptDt(format.parse(rcptDate));
                        } catch (ParseException ex) {
                            Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        sSchedule.setSSPriceperissue(BigDecimal.ZERO);
                        System.out.println("hhh9999999999999");
                        //will break the loop when subscription period crosses the date
                        if (sSchedule.getSSPubDt().after(smst.getSEnDt())) {
                            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                            break;
                        } else {

                            if (k == startVolume && j <= issPerFV) {
                                System.out.println(" DASHRATH10: " + firstVolINF);
                            } else {
                                if (startIssueNfFV == j && endVolume == k) {
                                    break;
                                } else {
                                    System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssss   " + k + " " + j);
                                    create(sSchedule);
                                }

                                if (counter == issPerVol) {
                                    System.out.println("ssssssssssssss111111111111111111111111");
                                    sTemp++;
                                    break;
                                }
                            }
                        }
                    }
                    output = "Schedule generated";
                }

                //repeatINoWEVNo, ReflectCInToMstDb
                if (!repeatINoWEVNo.contentEquals("on") && !ReflectCInToMstDb.contentEquals("on")) {
                    smst.setSMstStatus("S");
                    sMstFacadeREST.edit(smst);
                } else if (!repeatINoWEVNo.contentEquals("on") && ReflectCInToMstDb.contentEquals("on")) {
                    smst.setSStVol(Integer.toString(startVolume));
                    smst.setSEnVol(Integer.toString(endVolume));
                    smst.setSStIss(Integer.toString(startIssue));
                    smst.setSEnIss(Integer.toString(endIssue));
                    smst.setSLeadtime(leadTime);
                    smst.setSStDt(pubDate);
                    smst.setSIssPerVol(issPerVol);
                    smst.setSMstStatus("S");
                    sMstFacadeREST.edit(smst);
                } else if (repeatINoWEVNo.contentEquals("on") && !ReflectCInToMstDb.contentEquals("on")) {
                    smst.setSMstStatus("S");
                    sMstFacadeREST.edit(smst);
                } else if (repeatINoWEVNo.contentEquals("on") && ReflectCInToMstDb.contentEquals("on")) {
                    smst.setSStVol(Integer.toString(startVolume));
                    smst.setSEnVol(Integer.toString(endVolume));
                    smst.setSStIss(Integer.toString(startIssue));
                    smst.setSEnIss(Integer.toString(endIssue));
                    smst.setSLeadtime(leadTime);
                    smst.setSStDt(pubDate);
                    smst.setSIssPerVol(issPerVol);
                    smst.setSMstStatus("S");
                    sMstFacadeREST.edit(smst);
                }
            }
            //return "Schedule generated.";
        }
        return output;
    }
    
    

    public int getMaxSrNo() {
        List<SSchedule> getAll = new ArrayList<>();
        getAll = findAll();
        int max = Integer.MIN_VALUE;
        if (getAll.size() == 0) {
            max = 0;
        } else {
            for (int i = 0; i < getAll.size(); i++) {
                if (getAll.get(i).getSSchedulePK().getSSSrno() > max) {
                    max = getAll.get(i).getSSchedulePK().getSSSrno();
                }
            }
        }
        return max;
    }

    public static Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static Date addMonths(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.MONTH, days);
        return cal.getTime();
    }

    public static Date addYears(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.YEAR, days);
        return cal.getTime();
    }

    public Date getLastPubDate() {
        GregorianCalendar cal = new GregorianCalendar();
        String srNo = Integer.toString(getMaxSrNo());
        List<SSchedule> pub = findBy("findBySSSrno", srNo);
        Date date = pub.get(0).getSSPubDt();
        System.out.println("Pub date: " + date);
        return date;
    }
    
    @POST
    @Path("deleteExpectedIssues")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain"})
    public String deleteExpectedIssues(@FormParam("title") String title){
        List<SSchedule> result = new ArrayList<>();
        Query query;
        String q;
        List<SMst> smst;
        List<SSchedule> schedule;
        try {
            smst = sMstFacadeREST.findBy("findBySTitle1", title);
        } catch (NullPointerException d) {
            return "Invalid record id.";
        }

        String recID = smst.get(0).getSRecid();
        schedule = findBy("findAllExpected", recID);
        for (int i = 0; i < schedule.size(); i++) {
            String recId = schedule.get(i).getSSchedulePK().getSSRecid();
            removeBy("deleteAllExpected", recId + "," + "Expected");
        }

        return "Deleted...";
    }


    @POST
    @Path("deleteExpectedIssuesUpto")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain"})
    public String deleteExpectedIssuesUpto(@FormParam("title") String title, @FormParam("date") String date){
        List<SSchedule> result = new ArrayList<>();
        Query query;
        String q;
        List<SMst> smst;
        List<SSchedule> schedule;
        try {
            smst = sMstFacadeREST.findBy("findBySTitle1", title);
        } catch (NullPointerException d) {
            return "Invalid record id.";
        }

        String recID = smst.get(0).getSRecid();
        schedule = findBy("findAllExpectedUpto", recID+","+date);
        for (int i = 0; i < schedule.size(); i++) {
            String recId = schedule.get(i).getSSchedulePK().getSSRecid();
            Date expDate = schedule.get(i).getSSExpDt();
            removeBy("deleteAllExpectedUpto", recId + "," + expDate + "," + "Expected");
        }

        return "Deleted...";
    }    
    
    @POST
    @Path("saveSchedule")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain"})
    public String saveSchedule(@FormParam("recId") String recId, @FormParam("pubDate") String pubDate, @FormParam("expDate") String expDate) {

        List<SSchedule> result = new ArrayList<>();
        Query query;
        String q;
        SMst smst;
        try {
            smst = sMstFacadeREST.find(recId);
        } catch (NullPointerException d) {
            return "Invalid record id.";
        }
        q = "select * from s_schedule where s_s_recid = '" + recId + "' and s_S_flag IS NULL;";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        query = getEntityManager().createNativeQuery(q, SSchedule.class);
        result = (List<SSchedule>) query.getResultList();
        System.out.println("result: " + result.size());
        String expDate_Split[] = expDate.split(",");
        String pubDate_Split[] = pubDate.split(",");
        for (int i = 0; i < pubDate_Split.length; i++) {
            try {
                result.get(i).setSSPubDt(dateFormat.parse(pubDate));
                result.get(i).setSSExpDt(dateFormat.parse(expDate));
            } catch (ParseException ex) {
                Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            edit(result.get(i));
        }
        return "Updation of schedule for title: \'" + smst.getSTitle() + "\' is done.";
    }
    
    @POST
    @Path("checkIn")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain"})
    public String checkIn(@FormParam("recId") String recId, @FormParam("srNo") String srNo,
            @FormParam("issue") String issue, @FormParam("volume") String volume,
            @FormParam("pubDate") String pubDate, @FormParam("expDate") String expDate,
            @FormParam("status") String status, @FormParam("rcptDate") String rcptDate,
            @FormParam("accessionNo") String accessionNo, @FormParam("allowIssRet") String allowIssRet,
            @FormParam("remarks") String remarks) {

        String srNo_Split[] = srNo.split(",");
        String issue_Split[] = issue.split(",");
        String volume_Split[] = volume.split(",");
        String status_Split[] = status.split(",");
        String rcptDate_Split[] = rcptDate.split(",");
        String expDate_Split[] = expDate.split(",");
        String pubDate_Split[] = pubDate.split(",");
        String accessionNo_Split[] = accessionNo.split(",");
        String allowIssRet_Split[] = allowIssRet.split(",");
        String remarks_Split[] = remarks.split(",", -1);
        
        System.out.println("remarks...  "+remarks_Split.length);
        System.out.println("status_Split...  "+status_Split.length);
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SMst smst;
        List<Biblidetails> bd;
        List<BiblidetailsPK> dbPK;
        try {
            smst = sMstFacadeREST.find(recId);
        } catch (NullPointerException d) {
            return "Invalid record id.";
        }

        for (int i = 0; i < srNo_Split.length; i++) {
            SSchedule ss = new SSchedule();
            SSchedulePK ssPK = new SSchedulePK();
            ssPK.setSSRecid(recId);
            ssPK.setSSSrno(Integer.parseInt(srNo_Split[i]));
            ssPK.setSSVol(volume_Split[i]);
            ssPK.setSSIss(issue_Split[i]);
            ss.setSSchedulePK(ssPK);

            try {
                ss.setSSPubDt(format.parse(pubDate_Split[i]));
            } catch (ParseException ex) {
                Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ss.setSSExpDt(format.parse(expDate_Split[i]));
            } catch (ParseException ex) {
                Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (i < rcptDate_Split.length) {
                    ss.setSSRcptDt(format.parse(rcptDate_Split[i]));
                }

            } catch (ParseException ex) {
                Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            ss.setSSRemark(remarks_Split[i]);
            ss.setSSStatus(status_Split[i]);
            //edit(ss);

            String cat_rec_id = smst.getSCatRecid();
            bd = biblidetailsFacadeREST.findBy("findByRecIDAndTag887", cat_rec_id);
            if (i < rcptDate_Split.length) {

                Biblidetails bd1 = new Biblidetails();
                BiblidetailsPK bdPk = new BiblidetailsPK();
                System.out.println("bd.size(): " + bd.size());
                int sbFld = 0;
                if (bd.size() == 0) {
                    sbFld = 1;
                } else {
                    sbFld = bd.get(bd.size() - 1).getBiblidetailsPK().getSbFldSrNo() + 1;
                }
                bdPk.setRecID(Integer.parseInt(cat_rec_id));
                bdPk.setSbFld("a");
                bdPk.setSbFldSrNo(sbFld);
                bdPk.setTag("887");
                bdPk.setTagSrNo(1);
                String fValue = "<srno>" + srNo_Split[i] + "</srno><volno>" + volume_Split[i] + "</volno><issno>" + issue_Split[i] + "</issno><dateofreceipt>" + rcptDate_Split[i] + "</dateofreceipt>";
                bd1.setBiblidetailsPK(bdPk);
                bd1.setFValue(fValue);
                System.out.println("rcptDate_Split[i]: " + rcptDate_Split[i].length() + ":     :" + rcptDate_Split[i]);
                if (ss.getSSStatus().contentEquals("Received") && rcptDate_Split[i].length() > 0) {
                    biblidetailsFacadeREST.create(bd1);
                }
                edit(ss);
            } else {
                edit(ss);
            }

            if (i < accessionNo_Split.length) {
                SLooseissuemapping sLooseissuemapping = new SLooseissuemapping();
                Location loc = new Location();
                LocationPK locpk = new LocationPK();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                sLooseissuemapping.setIssue(issue_Split[i]);
                sLooseissuemapping.setSAccessionno(accessionNo_Split[i]);
                sLooseissuemapping.setVolume(volume_Split[i]);
                sLooseissuemapping.setSTitle(smst.getSTitle());
                sLooseissuemapping.setSRecid(recId);
                sLooseissuemapping.setSCatRecid(Integer.parseInt(cat_rec_id));
                sLooseissuemapping.setAllowIssue(allowIssRet_Split[i]);

                locpk.setP852(accessionNo_Split[i]);
                locpk.setRecID(Integer.parseInt(cat_rec_id));
                loc.setLocationPK(locpk);
                //loc.setA852(srNo);
                //loc.setM852(srNo);
                //loc.setC852(srNo);
                //loc.setInd(srNo);
                loc.setB852("General"); //change to dynamic
                loc.setF852("00000"); //change to dynamic
                try {
                    loc.setK852(smst.getSClassCd());
                } catch (NullPointerException e) {
                    loc.setK852(null);
                }

                ABudget bud = aBudgetFacadeREST.find(Integer.parseInt(smst.getSBugetCd()));
                System.out.println("abcdefg:   " + bud);
                System.out.println("abcdefg:   " + bud.getBudgetHead());

                BHeads bhead = bHeadsFacadeREST.find(bud.getBudgetHead().getBudgetCode());
                String invoiceNo = null;
                try {
                    invoiceNo = smst.getSInvNo();
                    if (!invoiceNo.isEmpty()) {
                        List<SSubInv> invDet = sSubInvFacadeREST.findBy("findBySInvNo", invoiceNo);
                        Date invDate = invDet.get(0).getSInvDt();
                        loc.setInvoiceDate(invDate);
                    } else {
                        loc.setInvoiceDate(null);
                    }
                } catch (NullPointerException d) {
                    loc.setInvoiceDate(null);
                }

                MFcltydept deptDet = mFcltydeptFacadeREST.find(Integer.parseInt(smst.getSDeptCd()));
                List<SRequest> sR = sRequestFacadeREST.findBy("findBySRTitle", smst.getSTitle());
                Libmaterials mc = sR.get(0).getSRPhycd();
                List<SSupplierDetail> supplier = sSupplierDetailFacadeREST.findBy("findByASCd", smst.getSSupCd());
                String supplierName = supplier.get(0).getASName();

                loc.setDepartment(deptDet);
                System.out.println("abcdefg:   " + bhead);
                System.out.println("abcdefg:   " + bhead.getShortDesc());
                loc.setBudget(bhead.getShortDesc());
                loc.setCurrency(smst.getSCurrency());
                loc.setDateofAcq(date);

                //loc.setInvoiceNo(volume);
                loc.setIssueRestricted("N");
                loc.setLastOperatedDT(date);
                loc.setMaterial(mc);
                loc.setSupplier(supplierName);
                loc.setStatus("AV");
                if (ss.getSSStatus().contentEquals("Received") && allowIssRet_Split[i].contentEquals("Yes")) {
                    sLooseissuemappingFacadeREST.create(sLooseissuemapping);
                    locationFacadeREST.create(loc);
                }
            }
        }
        return "Check-In Done...";
    }

    @POST
    @Path("updateSchedule")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain"})
    public String updateSchedule(@FormParam("recId") String recId, @FormParam("pubDate") String pubDate, 
            @FormParam("expDate") String expDate) {

        List<SSchedule> result = new ArrayList<>();
        Query query;
        String q;
        SMst smst;
        try {
            smst = sMstFacadeREST.find(recId);
        } catch (NullPointerException d) {
            return "Invalid record id.";
        }
        q = "select * from s_schedule where s_s_recid = '" + recId + "' and s_S_flag IS NULL;";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        query = getEntityManager().createNativeQuery(q, SSchedule.class);
        result = (List<SSchedule>) query.getResultList();
        System.out.println("result: " + result.size());
        String expDate_Split[] = expDate.split(",");
        String pubDate_Split[] = pubDate.split(",");
        for (int i = 0; i < pubDate_Split.length; i++) {
            try {
                result.get(i).setSSPubDt(dateFormat.parse(pubDate));
                result.get(i).setSSExpDt(dateFormat.parse(expDate));
            } catch (ParseException ex) {
                Logger.getLogger(SScheduleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            edit(result.get(i));
        }
        return "Updation of schedule for title: \'" + smst.getSTitle() + "\' is done.";
    }

    @POST
    @Path("deleteCheckIn")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain"})
    public String deleteCheckIn(@FormParam("recId") String recId, @FormParam("createdBinding") String createdBinding) {
        List<SSchedule> delRec = listofTitlesCheckInDetails(recId);
        if (createdBinding.contentEquals("yes")) {
            for (int i = 0; i < delRec.size(); i++) {
                int srNo = delRec.get(i).getSSchedulePK().getSSSrno();
                String iss = delRec.get(i).getSSchedulePK().getSSIss();
                String vol = delRec.get(i).getSSchedulePK().getSSVol();
                removeBy("deleteCheckIn", recId + "," + srNo + "," + iss + "," + vol);
            }
        }
        return "Deleted";
    }

    @DELETE
    @Path("removeReceived")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain"})
    public String removeReceived() {
        List<SSchedule> delRec = null;
        String q;
        Query query;
        q = "select s_title,s_s_recid,s_s_vol,s_s_iss,s_s_srno,s_s_pub_dt,s_s_exp_dt,s_s_rcpt_dt,s_s_remark from s_schedule,s_mst where s_s_status like 'Received%' and s_s_rcpt_dt<'8/31/2018' and s_recid=s_s_recid";
        query = getEntityManager().createNativeQuery(q, SSchedule.class);
        delRec = (List<SSchedule>) query.getResultList();
        for (int i = 0; i < delRec.size(); i++) {
            int srNo = delRec.get(i).getSSchedulePK().getSSSrno();
            String iss = delRec.get(i).getSSchedulePK().getSSIss();
            String vol = delRec.get(i).getSSchedulePK().getSSVol();
            removeBy("removeReceived", delRec.get(i).getSSchedulePK().getSSRecid() + "," + srNo + "," + iss + "," + vol);
        }
        return "Deleted";
    }

    @DELETE
    @Path("removeReceived/{dateBetween}")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain"})
    public String removeReceived(@PathParam("date") String date) {
        List<SSchedule> delRec = retrieveAllforRemovingReceivedByDateBetween(date);
        for (int i = 0; i < delRec.size(); i++) {
            int srNo = delRec.get(i).getSSchedulePK().getSSSrno();
            String iss = delRec.get(i).getSSchedulePK().getSSIss();
            String vol = delRec.get(i).getSSchedulePK().getSSVol();
            removeBy("removeReceived", delRec.get(i).getSSchedulePK().getSSRecid() + "," + srNo + "," + iss + "," + vol);
        }
        return "Deleted";
    }

    @DELETE
    @Path("removeReceivedByTitle/{title}")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain"})
    public String removeReceivedByTitle(@PathParam("title") String title) {
        List<SSchedule> delRec = retrieveAllforRemovingReceivedByTitle(title);
        String seperator = ";";
        String newLine = "\n";
        String userHomeFolder = System.getProperty("user.home");
        System.out.println("userHomeFolde: "+userHomeFolder);
            for (int i = 0; i < delRec.size(); i++) {
                int srNo = delRec.get(i).getSSchedulePK().getSSSrno();
                String iss = delRec.get(i).getSSchedulePK().getSSIss();
                String vol = delRec.get(i).getSSchedulePK().getSSVol();
                removeBy("removeReceived", delRec.get(i).getSSchedulePK().getSSRecid() + "," + srNo + "," + iss + "," + vol);
            }
        return "Deleted";
    }
}
