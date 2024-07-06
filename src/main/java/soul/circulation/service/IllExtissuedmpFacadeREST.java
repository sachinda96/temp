/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.catalogue.Location;
import soul.catalogue.service.LocationFacadeREST;
import soul.circulation.IllExtissue;
import soul.circulation.IllExtissuedmp;
import soul.response.StringprocessData;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.illextissuedmp")
public class IllExtissuedmpFacadeREST extends AbstractFacade<IllExtissuedmp> {

    @EJB
    private IllExtissueFacadeREST illExtissueFacadeREST;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    // String output;
    public IllExtissuedmpFacadeREST() {
        super(IllExtissuedmp.class);
    }

    @POST
    @Path("Return")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData create(String issueIds_data) {

        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String accno = "";
        String datatype = issueIds_data.substring(0, 1);
        IllExtissue extIssue;

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(issueIds_data);
            accno = jsonobj.getString("accnos");

        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(issueIds_data);
                accno = stringintoxml.getdatafromxmltag(doc, "accnos");

            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }

        String[] accnos = accno.split(",");
        IllExtissuedmp issueDmp;
        //int idCount = 0;
        for (int i = 0; i < accnos.length; i++) {
            Location ln;
            List<Location> liloc = null;
            String acnno = null;
            extIssue = null;
            List<IllExtissue> liillextissue = null;
            liillextissue = illExtissueFacadeREST.findBy("findByAccNo", accnos[i]);
            if(liillextissue.size()>0){
                extIssue=liillextissue.get(0);
            }
           // extIssue = illExtissueFacadeREST.find(Integer.parseInt(issueIds[i]));
            //int count = Integer.parseInt(countREST());
            if (extIssue != null) {
                issueDmp = new IllExtissuedmp();
                issueDmp.setAccNo(extIssue.getAccNo());
                Integer issueid = extIssue.getIssueId();   
                acnno = extIssue.getAccNo();
                liloc = locationFacadeREST.getByAcc(acnno);
                if (liloc.size() > 0) {
                    ln = liloc.get(0);
                    if (ln.getStatus().equals("IL")) {
                        issueDmp.setLibCd(extIssue.getLibCd());
                        issueDmp.setRecvDt(new Date());
                        issueDmp.setRecvUCd("super user");      //make dynamic
                        //System.out.println("extIssue.getRqstDt() " + extIssue.getRqstDt().getTime());
                        issueDmp.setRqstDt(extIssue.getRqstDt());
                        issueDmp.setRqstRef(extIssue.getRqstRef());
                        issueDmp.setSendDt(extIssue.getSendDt());
                        issueDmp.setSendUCd(extIssue.getSendUCd());
                        issueDmp.setTitle(extIssue.getTitle());

                        issueDmp = createAndGet(issueDmp);
                        //  idCount++;

                        illExtissueFacadeREST.remove(issueid);
                        
                        ln.setStatus("AV");
                        locationFacadeREST.edit(ln);

                        output += extIssue.getAccNo() + " items received..";
                    }else{
                         notprocess += acnno + " status is not IIL ISSUE..";
                    }
                }

            } else {
                notprocess += accnos[i] + " not valid..";
            }
        }
        //output = idCount+" items received.";

        String op;
        String nonop;
        if (output.length() > 1) {
            op = output.substring(0, output.length() - 1);
        } else {
            op = output;
        }
        if (notprocess.length() > 1) {
            nonop = notprocess.substring(0, notprocess.length() - 1);
        } else {
            nonop = notprocess;
        }

        spd.setProcessdata(op);
        spd.setNonprocessdata(nonop);
        return spd;
    }

    @POST
    @Path("createAndGet")
    @Override
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public IllExtissuedmp createAndGet(IllExtissuedmp entity) {
        return super.createAndGet(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(IllExtissuedmp entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public IllExtissuedmp find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<IllExtissuedmp> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<IllExtissuedmp> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<IllExtissuedmp> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //  member.setMemEffctupto(new java.sql.Date(MemEffctuptoDate.getTime()));
        String startTime = "T00:00:00+05:30";
        String endTime = "T23:59:59+05:30";
        switch (query) {

            case "findByRqstDtBetween":
                try {
                    Date date1 = dateFormat.parse(valueString[0]);
                    Date date2 = dateFormat.parse(valueString[1]);
                    valueList.add(new java.sql.Date(date1.getTime()));
                    valueList.add(new java.sql.Date(date2.getTime()));
                } catch (ParseException ex) {
                    Logger.getLogger(IllExtissuedmpFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                valueList.add(valueString[0]);
                break;
        }
        return super.findBy("IllExtissuedmp." + query, valueList);
    }

    @GET
    @Path("allReturnedBetween/{fromDate}/{toDate}")
    @Produces({"application/xml", "application/json"})
    // public List<IllExtissuedmp> allReturnedBetween(@QueryParam("fromDate") String fromDate,@QueryParam("toDate") String toDate)
    public List<IllExtissuedmp> allReturnedBetween(@PathParam("fromDate") String fromDate, @PathParam("toDate") String toDate) {
        // String fromDate = request.getParameter("fromDate");
        // String toDate = request.getParameter("toDate");
        return findBy("findByRqstDtBetween", fromDate + "," + toDate);
    }

    //List of lended books on Inter library loan
    //THis method will get the details of the items by the which are  lended on Inter library loan
    @GET
    @Path("LendedBooksList/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> listOfLendedBooks(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
            case "byRequestDateBetween":
                q = " SELECT ill_extissuedmp.lib_cd, ill_libmst.lib_nm, Biblidetails.FValue, ill_extissuedmp.acc_no, ill_extissuedmp.rqst_dt, ill_extissuedmp.send_dt  \n"
                        + " FROM ill_libmst, ill_extissuedmp, Biblidetails, Location \n"
                        + " WHERE(ill_libmst.lib_cd = ill_extissuedmp.lib_cd)  AND ill_extissuedmp.acc_no = Location.p852 \n"
                        + " AND Biblidetails.RecID = Location.RecID  and Biblidetails.tag ='245' \n"
                        + " and Biblidetails.sbfld in ('a') and ill_extissuedmp.rqst_dt between '" + valueString[0] + "' AND '" + valueString[1] + "' ";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
}
