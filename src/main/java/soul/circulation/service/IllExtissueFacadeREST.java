/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

//import ExceptionService.DataException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.catalogue.Location;
import soul.catalogue.service.BiblidetailsLocationFacadeREST;
import soul.catalogue.service.LocationFacadeREST;
import soul.catalogue.service.MBkstatusFacadeREST;
import soul.circulation.IllExtissue;
import soul.circulation.IllExtissueBiblocation;
import soul.circulation.IllExtissueload;
//import soul.circulation.client.IllLending;
import soul.general_master.IllLibrestrict;
import soul.general_master.IllLibrestrictPK;
import soul.general_master.service.IllLibmstFacadeREST;
import soul.general_master.service.IllLibrestrictFacadeREST;
import soul.response.StringprocessData;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;
import soul.util.function.DateNTimeChange;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.illextissue")
public class IllExtissueFacadeREST extends AbstractFacade<IllExtissue> {

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    DateNTimeChange dt = new DateNTimeChange();
//    @EJB
//    private IllExtissuedmpFacadeREST illExtissuedmpFacadeREST;
    @EJB
    private IllExtissueBiblocationFacadeREST illExtissueBiblocationFacadeREST;
    @EJB
    private IllLibmstFacadeREST illLibmstFacadeREST;
    @EJB
    private BiblidetailsLocationFacadeREST biblidetailsLocationFacadeREST;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private MBkstatusFacadeREST mBkstatusFacadeREST;
    @EJB
    private IllLibrestrictFacadeREST illLibrestrictFacadeREST;

    public IllExtissueFacadeREST() {
        super(IllExtissue.class);
    }

    public String checkPrivilege(Location location, String libCd) {
        String allowed = "Allowed";
        // String statusDesc = mBkstatusFacadeREST.find(location.getStatus()).getStatusDscr();
        String statusDesc = mBkstatusFacadeREST.find(location.getStatus()).getStatusDscr();
        System.out.println(mBkstatusFacadeREST.find(location.getStatus()));
        System.out.println(location);
        if (location.getStatus().equals("AV")) {
            if (location.getIssueRestricted().equals("N")) {
                String id = " ;libCd=" + libCd + ";libMediaCd=" + location.getMaterial().getCode();
                //   String id = " ;libCd="+libCd+";libMediaCd="+location.getMaterial().getCode();
                //      IllLibrestrict libPrivilege = illLibrestrictFacadeREST.findByIdAndMeadiaCd("findByIdAndMeadiaCd", libCd+","+location.getMaterial().getCode());
                IllLibrestrictPK illLibrestrictPK = new IllLibrestrictPK();
                illLibrestrictPK.setLibCd(libCd);
                illLibrestrictPK.setLibMediaCd(location.getMaterial().getCode());
                IllLibrestrict libPrivilege = illLibrestrictFacadeREST.find(illLibrestrictPK);

                if (libPrivilege != null) {
                    List<IllExtissueBiblocation> extIssueBibLocList = new ArrayList<>();
                    // GenericType<List<IllExtissueBiblocation>> genericType = new GenericType<List<IllExtissueBiblocation>>(){};
                    extIssueBibLocList = illExtissueBiblocationFacadeREST.findBy("findByLibCdAndMaterial", libCd + "," + location.getMaterial().getCode());

                    if (extIssueBibLocList.size() < libPrivilege.getMxAllowed()) {
                        allowed = "Allowed";
                    } else {
                        allowed = "Max. quota for '" + location.getMaterial().getDescription() + "' for this libray reached and further issue is not allowed.";
                    }
                } else {
                    allowed = "Item(s) is not allowed for Issue.";
                }
            } else {
                allowed = "Item is issue restricted and can not be issued.";
            }
        } else {
            allowed = "Item is currently in '" + statusDesc + "' state and can not be issued.";
        }

        return allowed;
    }

//   String output="";
//   IllExtissue extIssue;
//   Location location;
//   String privilegeCheck;
//   int count;
    //FOr issuing book under ILL Lending Book
    //Entry in ill_extissue table
    @POST
    @Path("Issue")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData create(String illissuedata) {
        StringprocessData spd = new StringprocessData();
        System.out.println("called..........");
        String privilegeCheck;
        IllExtissue extIssue;

        List<Location> liloc;
        String output = "";
        String notprocess = "";

        String accNo = "";
        String title = "";
        String librarycd = "";
        String requestDate = "";
        String sendDate = "";
        String reference = "";

        String datatype = illissuedata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(illissuedata);
            accNo = jsonobj.getString("accNo");
            title = jsonobj.getString("title");
            librarycd = jsonobj.getString("librarycd");
            requestDate = jsonobj.getString("requestDate");
            sendDate = jsonobj.getString("sendDate");
            reference = jsonobj.getString("reference");

        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(illissuedata);
                accNo = stringintoxml.getdatafromxmltag(doc, "accNo");
                title = stringintoxml.getdatafromxmltag(doc, "title");
                librarycd = stringintoxml.getdatafromxmltag(doc, "librarycd");
                requestDate = stringintoxml.getdatafromxmltag(doc, "requestDate");
                sendDate = stringintoxml.getdatafromxmltag(doc, "sendDate");
                reference = stringintoxml.getdatafromxmltag(doc, "reference");
            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }

//        try{
//            l = locationFacadeREST.findBy("findByP852", accNo);
//            location = locationFacadeREST.findBy("findByP852", accNo).get(0);
//        }catch(ArrayIndexOutOfBoundsException e){
//            return "Invalid accession no.";
//        }
        int skip = 0;
        liloc = locationFacadeREST.findBy("findByP852", accNo);
        List<IllExtissue> liillextissue = findBy("findByAccNo", accNo);
        if (liillextissue.size() > 0) {
            skip = 1;
        }
        if (skip == 0) {
            if (liloc.size() > 0) {
                Location ln = liloc.get(0);

                privilegeCheck = checkPrivilege(ln, librarycd);
                if (privilegeCheck.equals("Allowed")) {
                    if (ln.getStatus().equals("AV") && ln.getIssueRestricted().equals("N")) {

                        extIssue = new IllExtissue();
                        extIssue.setAccNo(accNo);
                        extIssue.setTitle(title);
                        extIssue.setLibCd(illLibmstFacadeREST.find(librarycd));

                        extIssue.setRqstDt(dt.getDateNTimechange(requestDate));
                        extIssue.setSendDt(dt.getDateNTimechange(sendDate));
                        extIssue.setSendUCd("super user");
                        extIssue.setRqstRef(reference);

                        //char status = 'I';
                        extIssue.setLendBookStatus("I");
                        extIssue = createAndGet(extIssue);

                        ln.setStatus("IL");
                        locationFacadeREST.edit(ln);

                        output = "Item "+ln.getLocationPK().getP852()+" issued to " + extIssue.getLibCd().getLibNm() + " library.";

                    } else {
                        output = "Book is currently in \'" + ln.getStatus() + "\' status and issue Restricted is " + ln.getIssueRestricted() + "..";
                    }
                } else {
                    output = privilegeCheck + ".";
                }

            } else {
                notprocess = "Invalid accession no..";
            }
        } else {
            notprocess = "Book already in ILL..";
        }

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
        // super.create(entity);
    }

    @PUT
    @Path("UpdateIssue")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData updtaeissuedata(String updateissuedata) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String accNo = "";
        String issueid = "";
        String librarycd = "";
        String requestDate = "";
        String sendDate = "";
        String reference = "";
        String datatype = updateissuedata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(updateissuedata);
            accNo = jsonobj.getString("accNo");
            issueid = jsonobj.getString("issueid");
            librarycd = jsonobj.getString("librarycd");
            requestDate = jsonobj.getString("requestDate");
            sendDate = jsonobj.getString("sendDate");
            reference = jsonobj.getString("reference");

        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(updateissuedata);
                accNo = stringintoxml.getdatafromxmltag(doc, "accNo");
                issueid = stringintoxml.getdatafromxmltag(doc, "issueid");
                librarycd = stringintoxml.getdatafromxmltag(doc, "librarycd");
                requestDate = stringintoxml.getdatafromxmltag(doc, "requestDate");
                sendDate = stringintoxml.getdatafromxmltag(doc, "sendDate");
                reference = stringintoxml.getdatafromxmltag(doc, "reference");
            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }

        List<IllExtissue> liissuefound = findBy("findByIssueId", issueid);
        if (liissuefound.size() > 0) {
            if (librarycd.equals(liissuefound.get(0).getLibCd()) && accNo.equals(liissuefound.get(0).getAccNo())) {
                IllExtissue ill = null;
                ill = liissuefound.get(0);
                ill.setRqstRef(reference);
                ill.setRqstDt(dt.getDateNTimechange(requestDate));
                ill.setSendDt(dt.getDateNTimechange(sendDate));
                edit(ill);
                output = "Issued item " + ill.getAccNo() + " update..";
            } else {
                notprocess = "No data Match..";
            }

        } else {
            notprocess = "No data Found..";
        }

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

    @GET
    @Path("Issuedata/{libcode}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<IllExtissueload> getdata(@PathParam("libcode") String libcode) {
        List<IllExtissueload> extissueload = null;
        String s = "select acc_no as 'Accno',issue_id as issueid,rqst_dt as 'Request_Date',rqst_ref as 'Referance',send_dt as 'Send_Date' from ill_extissue where lib_cd='" + libcode + "' and lend_book_status='I'";
        Query q = em.createNativeQuery(s, IllExtissueload.class);
        extissueload = q.getResultList();
        if (!(extissueload.size() > 0)) {
            extissueload = new ArrayList<>();
        }
        return extissueload;
    }

    @GET
    @Path("Issuedata")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<IllExtissueload> getdata() {
        List<IllExtissueload> extissueload = null;
        String s = "select acc_no as 'Accno',issue_id as issueid,lib_nm as 'Lib_Name',rqst_dt as 'Request_Date',rqst_ref as 'Referance',send_dt as 'Send_Date' from ill_extissue ie,ill_libmst im where im.lib_cd=ie.lib_cd and lend_book_status='I'";
        Query q = em.createNativeQuery(s, IllExtissueload.class);
        extissueload = q.getResultList();
        if (!(extissueload.size() > 0)) {
            extissueload = new ArrayList<>();
        }
        return extissueload;
    }

    @POST
    @Path("createAndGet")
    @Override
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public IllExtissue createAndGet(IllExtissue entity) {
        return super.createAndGet(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(IllExtissue entity) {
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
    public IllExtissue find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<IllExtissue> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<IllExtissue> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<IllExtissue> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();

        switch (query) {

            case "findByLendBookStatus":
                valueList.add(valueString[0].charAt(0));
                break;
            case "findByLibCd":
                valueList.add(valueString[0]);
                break;
            default:
                valueList.add(valueString[0]);
                break;
        }
        return super.findBy("IllExtissue." + query, valueList);
    }

    @DELETE
    @Path("removeIssue/{issueId}")
    @Produces({"application/xml", "application/json"})
    public StringprocessData removeIssue(@PathParam("issueId") String issueId) {
        StringprocessData spd = new StringprocessData();
        List<IllExtissue> extIssue = null;
        List<Location> liloc = null;
        Location location = null;
        String output = "";
        String notprocess = "";
        String ids[] = issueId.split(",");
        int count = Integer.parseInt(countREST());
        for (int i = 0; i < ids.length; i++) {
            extIssue = findBy("findByIssueId", ids[i]);
            if (extIssue.size() > 0) {
                liloc = locationFacadeREST.findBy("findByP852", extIssue.get(0).getAccNo());
                if (liloc.size() > 0) {
                    location = liloc.get(0);
                    if (location.getStatus().equals("IL")) {
                        remove(Integer.parseInt(issueId));
                        if (Integer.parseInt(countREST()) == count) {
                            notprocess += location.getLocationPK().getP852()+"issue item is not deleted,";
                        } else {
                            location.setStatus("AV");
                            locationFacadeREST.edit(location);
                            output += location.getLocationPK().getP852() + "Item is received,";
                        }
                    } else {
                        notprocess += location.getLocationPK().getP852()+" Book status is not ILL Issue,";
                    }
                } else {
                    notprocess += ids[i]+" No book found,";
                }

            } else {
                notprocess += ids[i]+" Invalid data,";
            }
        }
        

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

    //Reminder to library
    //THis method will get the details of the members and issued item by the member to send the reminder to suppy books
    @GET
    @Path("ReminderLetter/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> remtoLiReminderLetterforNonSupplyofBooks(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
            case "libCd":
                q = "SELECT ill_extissue.lib_cd, ill_libmst.lib_nm, ill_extissue.acc_no, ill_extissue.rqst_dt, ill_extissue.send_dt, "
                        + "ill_extissue.recv_dt, ill_extissue.rqst_ref,  Biblidetails.FValue,ill_libmst.lib_add1, ill_libmst.lib_add2, "
                        + "ill_libmst.lib_city, ill_libmst.lib_pin, ill_libmst.librarian_nm, ill_libmst.lib_email, ill_libmst.lib_phone,  "
                        + "letter_formats.letter_format, letter_formats.subject, letter_formats.letter_fullname "
                        + "FROM Location, Biblidetails, "
                        + "ill_libmst, ill_extissue, letter_formats "
                        + "where Location.RecID = Biblidetails.RecID  and  "
                        + "ill_libmst.lib_cd = ill_extissue.lib_cd and ill_extissue.acc_no=location.p852 and  "
                        + "Biblidetails.tag='245' and Biblidetails.sbfld ='a'  and letter_formats.letter_name = 'remin_lett' and "
                        + "lend_book_status='I' and ill_extissue.lib_cd = '" + Paramvalue + "' ";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }

}
