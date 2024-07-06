/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
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
import javax.ws.rs.core.PathSegment;
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.catalogue.Location;
import soul.catalogue.service.LocationFacadeREST;
import soul.circulation.MMember;
import soul.circulation.TOtherissue;
import soul.circulation.TOtherissuePK;
import soul.circulation_master.MCtgryColllection;
import soul.circulation_master.service.MCtgryColllectionFacadeREST;
import soul.response.StringprocessData;
import soul.system_setting.AccessionCtype;
import soul.system_setting.service.AccessionCtypeFacadeREST;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;
import soul.util.function.DateNTimeChange;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.totherissue")
public class TOtherissueFacadeREST extends AbstractFacade<TOtherissue> {

    @EJB
    private MMemberFacadeREST mMemberFacadeREST;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private MCtgryColllectionFacadeREST mCtgryColllectionFacadeREST;
    @EJB
    private AccessionCtypeFacadeREST accessionCtypeFacadeREST;

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    Date Todaydate = new Date();
    DateNTimeChange dt = new DateNTimeChange();
//    Location location;
//    MMember member;
//    String output;

    private TOtherissuePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;memCd=memCdValue;accNo=accNoValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.circulation.TOtherissuePK key = new soul.circulation.TOtherissuePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> memCd = map.get("memCd");
        if (memCd != null && !memCd.isEmpty()) {
            key.setMemCd(memCd.get(0));
        }
        java.util.List<String> accNo = map.get("accNo");
        if (accNo != null && !accNo.isEmpty()) {
            key.setAccNo(accNo.get(0));
        }
        return key;
    }

    public TOtherissueFacadeREST() {
        super(TOtherissue.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TOtherissue entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TOtherissue entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.circulation.TOtherissuePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @DELETE
    @Path("{memcd}/{accno}")
    public void removebyMemcdNAccno(@PathParam("memcd") String memcd, @PathParam("accno") String accno) {
        TOtherissuePK tk = new TOtherissuePK();
        tk.setAccNo(accno);
        tk.setMemCd(memcd);
        super.remove(super.find(tk));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public TOtherissue find(@PathParam("id") PathSegment id) {
        soul.circulation.TOtherissuePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TOtherissue> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TOtherissue> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<TOtherissue> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();

        switch (query) {
            case "findByIssueReson":
                valueList.add(valueString[0]);
                break;
            //used for findByAccNo,findByMemCd
            default:
                valueList.add(valueString[0]);
                break;
        }
        return super.findBy("TOtherissue." + query, valueList);
    }

    @GET
    @Path("allIssues")
    @Produces({"application/xml", "application/json"})
    public List<TOtherissue> allIssues() {
        List<TOtherissue> issues = findAll();
        return issues;
    }

    @POST
    @Path("issueOnPremise")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData issueOnPremise(String issuedata) {

        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String datatype = issuedata.substring(0, 1);
        String memcd = "";
        String title = "";
        String remark = "";
        String duedt = "";
        String accno = "";
        String operation = "";

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(issuedata);
            memcd = jsonobj.getString("memcd");
            accno = jsonobj.getString("accno");
            title = jsonobj.getString("title");
            remark = jsonobj.getString("remark");
            operation = jsonobj.getString("operation");
            duedt = jsonobj.getString("duedt");
        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(issuedata);
                memcd = stringintoxml.getdatafromxmltag(doc, "memcd");
                accno = stringintoxml.getdatafromxmltag(doc, "accno");
                title = stringintoxml.getdatafromxmltag(doc, "title");
                remark = stringintoxml.getdatafromxmltag(doc, "remark");
                operation = stringintoxml.getdatafromxmltag(doc, "operation");
                duedt = stringintoxml.getdatafromxmltag(doc, "duedt");
                
            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }

        String accnos[] = accno.split(",");
        MMember m = null;
        m = mMemberFacadeREST.find(memcd);
        List<MCtgryColllection> licollectiontype = null;
        Map<String, String> mapcatcodeNcollection = new HashMap<>();
        Map<String, String> mapcaollectionNpermision = new HashMap<>();
        Map<String, Integer> mapcollectionNmaxallowed = new HashMap<>();
        Map<String, Date> mapcollectionNtime = new HashMap<>();
        if (m != null) {
            if (m.getMemStatus().equals("A")) {
                licollectiontype = mCtgryColllectionFacadeREST.findBy("findByCtgryCd", m.getMemCtgry().getCtgryCd());

                if (licollectiontype.size() > 0) {

                    for (int i = 0; i < licollectiontype.size(); i++) {
                        mapcatcodeNcollection.put(licollectiontype.get(i).getMCtgry().getCtgryCd(), licollectiontype.get(i).getMCtgryColllectionPK().getCtCd());
                        mapcaollectionNpermision.put(licollectiontype.get(i).getMCtgryColllectionPK().getCtCd(), licollectiontype.get(i).getOPIssueAllow() + "," + licollectiontype.get(i).getONIssueAllow());
                        mapcollectionNmaxallowed.put(licollectiontype.get(i).getMCtgryColllectionPK().getCtCd(), licollectiontype.get(i).getMaxReserveColletype());
                        mapcollectionNtime.put(licollectiontype.get(i).getMCtgryColllectionPK().getCtCd(), licollectiontype.get(i).getOPTime());
                    }

                    System.err.println("mapcatcodeNcollection : " + mapcatcodeNcollection);
                    System.err.println("mapcaollectionNpermision : " + mapcaollectionNpermision);
                    System.err.println("mapcollectionNmaxallowed : " + mapcollectionNmaxallowed);
                    System.err.println("mapcollectionNtime : " + mapcollectionNtime);

                    List<TOtherissue> liotherissue = null;
                    liotherissue = findBy("findByMemCd", m.getMemCd());
                    int totalbookissuebymember = 0;
                    if (liotherissue.size() > 0) {
                        totalbookissuebymember = liotherissue.size();
                    }

                    for (int j = 0; j < accnos.length; j++) {
                        List<Location> liloc = null;
                        liloc = locationFacadeREST.getByAcc(accnos[j]);
                        if (liloc.size() > 0) {
                            Location ln = null;
                            ln = liloc.get(0);
                            if (ln.getStatus().equals("AV")) {
                                try {
                                    if (ln.getB852().isEmpty() == false) {
                                        List<AccessionCtype> liaccnotype = null;
                                        liaccnotype = accessionCtypeFacadeREST.findBy("findByCollectionType", ln.getB852());
                                        if (liaccnotype.size() > 0) {
                                            if (mapcaollectionNpermision.containsKey(liaccnotype.get(0).getCid())) {
                                                String permision[] = mapcaollectionNpermision.get(liaccnotype.get(0).getCid()).split(",");
                                                int process = 0;
                                                if (permision[0].equals("N") && permision[1].equals("N")) {
                                                    process = 2;
                                                } else {
                                                    if (operation.equals("OP")) {
                                                        if (permision[0].equals("Y")) {
                                                            process = 1;
                                                        }
                                                    }
                                                    if (operation.equals("ON")) {
                                                        if (permision[1].equals("Y")) {
                                                            process = 1;
                                                        }
                                                    }
                                                }

                                                if (totalbookissuebymember > mapcollectionNmaxallowed.get(liaccnotype.get(0).getCid())) {
                                                    process = 3;
                                                }

                                                if (process == 1) {
                                                    int issue=0;
                                                    for (int k = 0; k < liotherissue.size(); k++) {
                                                        if (liotherissue.get(k).getTOtherissuePK().getAccNo().equals(accnos[j])) {
                                                            issue = 1;
                                                        }
                                                    }
                                                    
                                                    if(issue==0){
                                                        TOtherissuePK totherpk = new TOtherissuePK();
                                                        totherpk.setAccNo(accnos[j]);
                                                        totherpk.setMemCd(m.getMemCd());
                                                        
                                                        TOtherissue toissue = new TOtherissue();
                                                        toissue.setTOtherissuePK(totherpk);
                                                        toissue.setIssueRemarks(remark);
                                                        toissue.setIssueDate(Todaydate);
                                                        if(operation.equals("OP")){
                                                            ln.setStatus("OP");
                                                            toissue.setIssueReson("On primises issue");
                                                            toissue.setDueDate(dt.getTimechange(duedt));
                                                        }else{
                                                            ln.setStatus("ON");
                                                            toissue.setIssueReson("Over night issue");
                                                            toissue.setDueDate(dt.getDateNTimechange(duedt));
                                                        }
                                                        toissue.setMMember(m);
                                                        
                                                        create(toissue);
                                                        
                                                        locationFacadeREST.edit(ln);
                                                        totalbookissuebymember++;
                                                        output +=accnos[j] +" Isssue successfuly,";
                                                        

                                                    }else{
                                                        notprocess += accnos[j] + " Book is already issue by this member,";
                                                    }
                                                } else {
                                                    if (process == 3) {
                                                        notprocess += accnos[j] + " Member excced the privileges,";
                                                    } else {
                                                        notprocess += accnos[j] + " Member not have enough collection type permision to issue this item,";
                                                    }

                                                }

                                            } else {
                                                notprocess += accnos[j] + " Member not have enough collection type to issue this item,";
                                            }
                                        } else {
                                            // j=accnos.length; 
                                            notprocess += accnos[j] + " Accession collection type data not found,";
                                        }
                                    } else {
                                        notprocess += accnos[j] + " Book not have collection type plese enter,";
                                    }
                                } catch (NullPointerException e) {
                                    notprocess += accnos[j] + " Book not have collection type plese enter,";
                                }

                            } else {
                                notprocess += accnos[j] + " Book satus is not available,";
                            }
                        } else {
                            notprocess += accnos[j] + " Book not found,";
                        }
                    }

                } else {
                    notprocess = "Member not have collection type privilege please add privilege to issue item..";
                }

            } else {
                notprocess = "Member is suspended..";
            }

        } else {
            notprocess = "Member not available..";
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

    public String checkOnOpPrivilege(MMember member, Location location, String issueType) {
        String allowed = "Allowed";
        //  GenericType<List<AccessionCtype>> genericType = new GenericType<List<AccessionCtype>>(){};
        List<AccessionCtype> accessionCtypeList = accessionCtypeFacadeREST.findBy("findByCollectionType", location.getB852());
        AccessionCtype accessionCtype = new AccessionCtype();
        if (!accessionCtypeList.isEmpty()) {
            accessionCtype = accessionCtypeList.get(0);
        } else {
            allowed = "This book is not allowed for OverNight/Premise Issue.";
            return allowed;
        }
        MCtgryColllection mCtgryColllection = mCtgryColllectionFacadeREST.findByCtgryCdAndCtCd(member.getMemCtgry().getCtgryCd(), accessionCtype.getCid()).get(0);

        if (mCtgryColllection == null) {
            allowed = "Member does not have privilege for \"" + accessionCtype.getCollectionType() + "\" collection type.";
        } else if ("ON".equals(issueType)) {
            if (Character.toChars(mCtgryColllection.getONIssueAllow())[0] == 'Y') {
                allowed = "Allowed";
            } else {
                allowed = "Member does not have privilege for OverNight Issue for \"" + accessionCtype.getCollectionType() + "\" collection type.";
            }
        } else if ("OP".equals(issueType)) {
            if (Character.toChars(mCtgryColllection.getOPIssueAllow())[0] == 'Y') {
                allowed = "Allowed";
            } else {
                allowed = "Member does not have privilege for OnPremises Issue for \"" + accessionCtype.getCollectionType() + "\" collection type.";
            }
        }

        if (allowed.equals("Allowed")) {
            List<TOtherissue> tOtherissuesList = findBy("findByMemCd", member.getMemCd());
            if (tOtherissuesList.size() == mCtgryColllection.getMaxReserveColletype()) {
                allowed = "Max limit reached for this member for OnPremises/OverNight Issue.";
            }
        }

        return allowed;
    }

    @PUT
    @Path("returnOnPremise")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String returnOnPremise(@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{accNo.pattern}") @FormParam("accNo") String accNo) {
        String output = "";
//        List<TOtherissue> otherissueList = findBy("findByAccNo", accNo.trim());
//        if (otherissueList.isEmpty()) {
//            output = "Book issue information not found in otehrissue details.";
//        } else {
//            TOtherissue otherissue = otherissueList.get(0);
//            int counts = Integer.parseInt(countREST());
//            System.out.println(counts);
//            String memCd = otherissue.getMMember().getMemCd();
//            removeByMemcdAndAccNo(memCd, accNo);
//            if (counts == Integer.parseInt(countREST())) {
//                output = "Something went wrong, Book is not returned.";
//            } else {
//                location = locationFacadeREST.findBy("findByP852", accNo.trim()).get(0);
//                location.setStatus("AV");
//                locationFacadeREST.edit(location);
//                output = "Book returned";
//            }
//        }
        return output;
    }

    public void removeByMemcdAndAccNo(String memCd, String accNo) {
        //String[] valueString = values.split(",");
        //  TOtherissue  tOtherissue = new TOtherissue();
        //  TOtherissuePK  tOtherissuePK = new TOtherissuePK();
        //  tOtherissuePK.setAccNo(accNo);
        //  tOtherissuePK.setMemCd(memCd);
        List<Object> valueList = new ArrayList<>();

        valueList.add(memCd);
        valueList.add(accNo);
        super.removeBy("TOtherissue.removeByMemcdAndAccNo", valueList);
    }

    //OtherIssueDetails
    //This method will return the list of issued item which are issued as On premises issue or Over night issue
    @GET
    @Path("OtherIssueDetails/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> OtherIssueDetails(@PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramvalue) {
            case "AllIssues":
                q = "SELECT DISTINCT m_member.mem_cd, m_member.mem_firstnm, m_member.mem_midnm, m_member.mem_lstnm, m_member.mem_tag, \n"
                        + "t_otherIssue.acc_no, Biblidetails.FValue, t_otherIssue.Issue_Date, t_otherIssue.due_Date, m_member.mem_prmntadd1, m_member.mem_prmntadd2,  \n"
                        + "m_member.mem_prmntcity, m_member.mem_prmntpin, m_member.mem_prmntphone, m_member.mem_email  \n"
                        + "FROM t_otherissue join m_member on m_member.mem_cd = t_otherissue.mem_cd \n"
                        + "	join location on t_otherissue.acc_no = location.p852\n"
                        + "    join biblidetails on location.RecID = biblidetails.RecID\n"
                        + "    where (Biblidetails.Tag = '245') AND (Biblidetails.SbFld IN ('a','b')) AND (Issue_Reson='ON' or Issue_Reson='OP')";
                break;

            case "OverNightIssues":
                q = "SELECT DISTINCT m_member.mem_cd, m_member.mem_firstnm, m_member.mem_midnm, m_member.mem_lstnm, m_member.mem_tag, \n"
                        + " t_otherIssue.acc_no, Biblidetails.FValue, t_otherIssue.Issue_Date, t_otherIssue.due_Date, m_member.mem_prmntadd1, m_member.mem_prmntadd2,  \n"
                        + " m_member.mem_prmntcity, m_member.mem_prmntpin, m_member.mem_prmntphone, m_member.mem_email  \n"
                        + " FROM t_otherissue join m_member on m_member.mem_cd = t_otherissue.mem_cd \n"
                        + " join location on t_otherissue.acc_no = location.p852\n"
                        + " join biblidetails on location.RecID = biblidetails.RecID\n"
                        + " where (Biblidetails.Tag = '245') AND (Biblidetails.SbFld IN ('a','b')) AND (Issue_Reson='ON') ";
                break;

            case "OnPremisesIssues":
                q = "SELECT DISTINCT m_member.mem_cd, m_member.mem_firstnm, m_member.mem_midnm, m_member.mem_lstnm, m_member.mem_tag, \n"
                        + "t_otherIssue.acc_no, Biblidetails.FValue, t_otherIssue.Issue_Date, t_otherIssue.due_Date, m_member.mem_prmntadd1, m_member.mem_prmntadd2,  \n"
                        + "m_member.mem_prmntcity, m_member.mem_prmntpin, m_member.mem_prmntphone, m_member.mem_email  \n"
                        + "FROM t_otherissue join m_member on m_member.mem_cd = t_otherissue.mem_cd \n"
                        + "	join location on t_otherissue.acc_no = location.p852\n"
                        + "    join biblidetails on location.RecID = biblidetails.RecID\n"
                        + "    where (Biblidetails.Tag = '245') AND (Biblidetails.SbFld IN ('a','b')) AND (Issue_Reson='OP')";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
}
