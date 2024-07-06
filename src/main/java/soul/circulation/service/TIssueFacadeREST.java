/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
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
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.catalogue.Location;
import soul.catalogue.service.LocationFacadeREST;
import soul.circulation.CtgryMediaIssueReserve;
import soul.circulation.MMember;
import soul.circulation.Memissueinfo;
import soul.circulation.TIssue;
import soul.circulation.TIssuePK;
import soul.circulation.TMemdue;
import soul.circulation.TMemfine;
import soul.circulation.TReceive;
import soul.circulation.TReceivePK;
import soul.circulation.TReserve;
import soul.circulation.Tissuedetails;
import soul.circulation_master.MCalender;
import soul.circulation_master.MCtgry;
import soul.circulation_master.MCtgryMedia;
import soul.circulation_master.MCtgryMediaPK;
import soul.circulation_master.MWeekoffday;
import soul.circulation_master.service.MCalenderFacadeREST;
import soul.circulation_master.service.MCtgryFacadeREST;
import soul.circulation_master.service.MCtgryMediaFacadeREST;
import soul.circulation_master.service.MWeekoffdayFacadeREST;
import soul.response.StringprocessData;
import soul.user_setting.Userdetail;
import soul.user_setting.service.UserdetailFacadeREST;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;
import soul.util.function.DateNTimeChange;

/**
 *
 * @author dashrath
 */
@Stateless
@Path("soul.circulation.tissue")
public class TIssueFacadeREST extends AbstractFacade<TIssue> {

    @EJB
    private MMemberFacadeREST mMemberFacadeREST;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private TReserveFacadeREST tReserveFacadeREST;
    @EJB
    private TReceiveFacadeREST tReceiveFacadeREST;
    @EJB
    private MWeekoffdayFacadeREST mWeekoffdayFacadeREST;
    @EJB
    private MCalenderFacadeREST mCalenderFacadeREST;
    @EJB
    private MCtgryFacadeREST mCtgryFacadeREST;
    @EJB
    private MCtgryMediaFacadeREST mCtgryMediaFacadeREST;
    @EJB
    private UserdetailFacadeREST userdetailFacadeREST;
    @EJB
    private TMemfineFacadeREST tMemfineFacadeREST;
    @EJB
    private TMemdueFacadeREST tMemdueFacadeREST;
   

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    DateNTimeChange dt = new DateNTimeChange();
    Date Todaydate = new Date();
//    String output;
//    MMember member;
//    String[] accNos;
//    Location location;
//    TIssue issue;
//    CtgryMediaIssueReserve privilege;
//    TReceive receive;
//    TReserve reserve;
//    List<Location> locationList;
//    List<TReserve> reserveList;
//    //  Map<String, String> accNoSlipNo = new HashMap<>();
//    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//    SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd");

    private TIssuePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;memCd=memCdValue;accNo=accNoValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.circulation.TIssuePK key = new soul.circulation.TIssuePK();
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

    public TIssueFacadeREST() {
        super(TIssue.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TIssue entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TIssue entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.circulation.TIssuePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @DELETE
    @Path("{memcode}/{accno}")
    public void removebymemcodeNaccno(@PathParam("memcode") String memcd, @PathParam("accno") String accno) {
        TIssuePK tk = new TIssuePK();
        tk.setAccNo(accno);
        tk.setMemCd(memcd);
        super.remove(super.find(tk));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public TIssue find(@PathParam("id") PathSegment id) {
        soul.circulation.TIssuePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TIssue> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TIssue> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

        switch (query) {
            default:
                valueList.add(valueString[0]);
            //used for CountByMemCd
        }
        return String.valueOf(super.countBy("TIssue." + query, valueList));
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Added mannually
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<TIssue> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();

        switch (query) {
            default:
                valueList.add(valueString[0]);
            //used for findByAccNo, memcd, title
        }
        return super.findBy("TIssue." + query, valueList);
    }


    @GET
    @Path("memissueinfo/{memcd}")
    public List<Tissuedetails> getissuedetails(@PathParam("memcd") String memcd) {
        MMember m;

        List<Tissuedetails> libbmeminfo = null;
        m = mMemberFacadeREST.find(memcd);
        if (m != null) {
            Query query;
            String q = "select DISTINCT b1.FValue as Bookname,b2.FValue as author,t.acc_no as accno,t.iss_dt as issuedt,t.due_dt as duedt,t.user_cd as usercd from biblidetails b left JOIN biblidetails b1 on b.RecID=b1.RecID and b1.Tag='245' and b1.SbFld='a' left join  biblidetails b2 on b1.RecID=b2.RecID and (b2.Tag='100' or b2.Tag='700') and b2.SbFld='a' left join location l on l.RecID=b.RecID INNER join t_issue t on l.p852=t.acc_no where t.mem_cd='" + m.getMemCd() + "'";
            query = getEntityManager().createNativeQuery(q, Tissuedetails.class);
            libbmeminfo = query.getResultList();

        } else {
            libbmeminfo = new ArrayList<>();
        }
        return libbmeminfo;
    }
    
    @GET
    @Path("memissueinfobyaccno/{accno}")
    public List<Memissueinfo> getissuedetailsbyaccno(@PathParam("accno") String accno) {
        TIssue t;
        List<TIssue> litisuue;
        List<Memissueinfo> limemissueinfo = null;
        litisuue = findBy("findByAccNo", accno);
        if(litisuue.size()>0){
            t=litisuue.get(0);
        }else{
            t=null;
        }
     
        if (t != null) {
            Query query;
            String q = "select m_member.mem_cd as memcd,m_member.mem_id as memid,concat(m_member.mem_firstnm,' ',m_member.mem_lstnm) as memname,m_member.mem_status as Status,f1.Fclty_dept_dscr as dept,f2.Fclty_dept_dscr as inst,m_ctgry.ctgry_desc as crgty,m_branchmaster.Branch_name as branch,t_issue.iss_dt as issuedt,t_issue.due_dt as duedt from m_member,m_fcltydept f1,m_fcltydept f2,m_ctgry,m_branchmaster,t_issue where m_member.mem_dept=f1.Fclty_dept_cd and f1.Fld_tag='D' and m_member.mem_ctgry=m_ctgry.ctgry_cd and m_member.mem_inst=f2.Fclty_dept_cd and f2.Fld_tag='I' and m_branchmaster.branch_cd=m_member.mem_degree and m_member.mem_cd=t_issue.mem_cd and t_issue.acc_no='"+accno+"'";
            query = getEntityManager().createNativeQuery(q, Memissueinfo.class);
            limemissueinfo = query.getResultList();

        } else {
            limemissueinfo = new ArrayList<>();
        }
        return limemissueinfo;
    }


    // used in transaction menu to issue an item
    @POST
    @Path("issue")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData issuebook(String issuedata) {

        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        MMember member = null;
        String memCd = "";
        String duedt = "";
        String accnowithduedt = "";

        String datatype = issuedata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(issuedata);
            memCd = jsonobj.getString("memcd");
            accnowithduedt = jsonobj.getString("accnonduedt");

        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(issuedata);
                memCd = stringintoxml.getdatafromxmltag(doc, "memcd");
                accnowithduedt = stringintoxml.getdatafromxmltag(doc, "accnonduedt");

            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }
        String accnoNduedt[] = accnowithduedt.split(",");
        member = mMemberFacadeREST.find(memCd);
        List<Location> liloc = null;
        Location ln = null;
        Userdetail userdetail = null;
        List<MCtgryMedia> limemctmedia = null;
        List<TReserve> reserveListofmemeber =null;
        List<TReserve> reserveListofbook =null;
        int skip = 0;
        Map<String, Integer> catmediamapwithcodeNmaxallowed = new HashMap<>();
        Map<String, Integer> issuemeadiacodeNcount = new HashMap<>();
        Map<String, Integer> materialcodenduration = new HashMap<>();

        if (member != null && member.getMemStatus().equals("A")) {
            if (member.getNoDueTag().equals("N")) {

                int maxbookallow = member.getMemCtgry().getMaxBookAllow(); // get category max book allowed
                userdetail = userdetailFacadeREST.findSingleResultBy("findByUName", "superuser");
                List<TIssue> litissue = findBy("findByMemCd", String.valueOf(member.getMemCd())); // find number of book issue by member
                int totalissuememberhas = 0;
                if (litissue.size() > 0) {
                    totalissuememberhas = litissue.size();
                }
                if (totalissuememberhas > maxbookallow) {
                    skip = 2;
                }
                // check all privilage of member and calulate max material book allow and max material issue by member

                if (skip == 0) {

                    // 1. check  max material book allow.
                    limemctmedia = mCtgryMediaFacadeREST.findBy("findByCtgryCd", member.getMemCtgry().getCtgryCd());

                    if (limemctmedia.size() > 0) {
                        for (int i = 0; i < limemctmedia.size(); i++) {
                            catmediamapwithcodeNmaxallowed.put(limemctmedia.get(i).getMCtgryMediaPK().getMediaCd(), limemctmedia.get(i).getMaxAllowed());
                            materialcodenduration.put(limemctmedia.get(i).getMCtgryMediaPK().getMediaCd(), limemctmedia.get(i).getIssPeriod());
                        }
                        System.out.println("catmediamapwithcodeNmaxallowed :" + catmediamapwithcodeNmaxallowed);
                        System.out.println("materialcodenduration :" + materialcodenduration);
                    } else {
                        skip = 1;
                    }

                    //2. check number of material issue by memeber
                    if (skip == 0 && totalissuememberhas != 0) {
                        for (int i = 0; i < litissue.size(); i++) {

                            List<Location> lilocformaterial = null;
                            lilocformaterial = locationFacadeREST.getByAcc(litissue.get(i).getTIssuePK().getAccNo());

                            if (lilocformaterial.size() > 0) {

                                Location lnformaterials = null;
                                lnformaterials = lilocformaterial.get(0);

                                if (issuemeadiacodeNcount.isEmpty()) {
                                    issuemeadiacodeNcount.put(lnformaterials.getMaterial().getCode(), 1);
                                } else {
                                    if (issuemeadiacodeNcount.containsKey(lnformaterials.getMaterial().getCode())) {
                                        int previouscount = issuemeadiacodeNcount.get(lnformaterials.getMaterial().getCode());
                                        previouscount++;
                                        issuemeadiacodeNcount.replace(lnformaterials.getMaterial().getCode(), previouscount);
                                    } else {
                                        issuemeadiacodeNcount.put(lnformaterials.getMaterial().getCode(), 1);
                                    }
                                }
                            }
                        }

                        System.err.println("issuemeadiacodeNcount :" + issuemeadiacodeNcount);
                    }
                }

                if (userdetail != null && skip == 0) {
                    for (int i = 0; i < accnoNduedt.length; i++) {
                        String bookdetails[] = accnoNduedt[i].split("/");
                        liloc = null;
                        liloc = locationFacadeREST.getByAcc(bookdetails[0]);
                        if (liloc.size() > 0) {
                            ln = null;
                            ln = liloc.get(0);
                            if (ln.getStatus().equals("AV") || ln.getStatus().equals("AR")) {

                                if (catmediamapwithcodeNmaxallowed.containsKey(ln.getMaterial().getCode())) {
                                    
                                    int maxallowformaterialcode = catmediamapwithcodeNmaxallowed.get(ln.getMaterial().getCode());
                                    int issuecountmateialcode =0;
                                    if(issuemeadiacodeNcount.containsKey(ln.getMaterial().getCode())){
                                       issuecountmateialcode= issuemeadiacodeNcount.get(ln.getMaterial().getCode());
                                    }
                                    
                                    
                                    if (!(issuecountmateialcode >= maxallowformaterialcode)) {
                                        List<MWeekoffday> mweekOffList = null;
                                        // week of day
                                        mweekOffList = mWeekoffdayFacadeREST.findAll();
                                        int dayofweek = Integer.valueOf(mweekOffList.get(0).getWeekOffDay());
                                        System.err.println("weekofday :" + dayofweek);
                                        // find duration of book allow
                                        //int durationofallow = materialcodenduration.get(ln.getMaterial().getCode());

                                        // check due_dt  
                                        Calendar cl = Calendar.getInstance();
                                        cl.setTime(dt.getDateNTimechange(bookdetails[1]));

                                        //cl.add(Calendar.DATE,durationofallow);
                                        List<MCalender> holidayList = null;
                                        boolean duedtfind = true;

                                        do {
                                            if (cl.get(Calendar.DAY_OF_WEEK) == dayofweek) {
                                                System.err.println("Day of date :" + cl.get(Calendar.DAY_OF_WEEK));
                                                cl.add(5, 1);
                                                cl.set(Calendar.HOUR_OF_DAY, 0);
                                                cl.set(Calendar.MINUTE, 0);
                                                cl.set(Calendar.SECOND, 0);
                                            }

                                            holidayList = null;
                                            //now check holidays

                                            System.err.println("Date of Issue :" + cl.getTime());
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                                            holidayList = mCalenderFacadeREST.findBy("findByFestivalDt", dateFormat.format(cl.getTime()));

                                            if (holidayList.size() > 0) {
                                                cl.add(5, 1);
                                            } else {
                                                if (cl.get(Calendar.DAY_OF_WEEK) == dayofweek) {
                                                    cl.add(5, 1);
                                                } else {
                                                    duedtfind = false;
                                                }
                                            }

                                        } while (duedtfind);
                                        
                                        cl.set(Calendar.HOUR_OF_DAY,Calendar.HOUR_OF_DAY);
                                        cl.set(Calendar.MINUTE,Calendar.MINUTE);
                                        cl.set(Calendar.MINUTE,Calendar.MINUTE);
                                                
                                        Date duedateforissue = cl.getTime();
                                        System.err.println("final of Issue :" + cl.getTime());
                                        //System.err.println("final 2 of Issue :" + issuedate);
                                        int issueprocess=0;
                                        if (ln.getStatus().equals("AV") || ln.getStatus().equals("AR")) {
                                            int status=0;    
                                            if(ln.getStatus().equals("AR")){
                                                reserveListofmemeber = tReserveFacadeREST.findBy("findByMemCd",member.getMemCd());
                                                if(reserveListofmemeber.size()>0){
                                                    reserveListofbook = tReserveFacadeREST.findBy("findByRecordNo",String.valueOf(ln.getLocationPK().getRecID()));
                                                    if(reserveListofbook.size()>0){
                                                        for(int j=0;j<reserveListofbook.size();j++){
                                                            TReserve tr =null;
                                                            tr = reserveListofbook.get(j);
                                                            if(tr.getSrNo()==1){
                                                                if(tr.getMMember().getMemCd().equals(member.getMemCd())){
                                                                    
                                                                    Calendar c2 = Calendar.getInstance();
                                                                    c2.setTime(Todaydate);
                                                                    c2.set(Calendar.HOUR_OF_DAY,0);
                                                                    c2.set(Calendar.MINUTE,0);
                                                                    c2.set(Calendar.MINUTE,0);
                                                                    Date dt1 = c2.getTime();
                                                                    System.out.println("DAte check :"+dt1.before(tr.getHoldDt()) +" : "+tr.getHoldDt());
                                                                    System.out.println("DAte check :"+dt1.equals(tr.getHoldDt())+" : "+tr.getHoldDt());
                                                                    if(dt1.before(tr.getHoldDt()) || dt1.equals(tr.getHoldDt())){
                                      
                                                                        tReserveFacadeREST.removebyMemcdNRecid(member.getMemCd(),ln.getLocationPK().getRecID());
                                                                        List<TReserve> litrve=null;
                                                                        String sql="SELECT * FROM t_reserve where record_no='"+ln.getLocationPK().getRecID()+"'"+"ORDER BY t_reserve.sr_no ASC";
                                                                        Query q = em.createNativeQuery(sql,TReserve.class);
                                                                        litrve = q.getResultList();
                                                                        
                                                                        if(litrve.size()>0){
                                                                            int srno=1;
                                                                            for(int k=0;k<litrve.size();k++){
                                                                                String updatesql="update t_reserve set sr_no='"+srno+"' where record_no='"+ln.getLocationPK().getRecID()+"' and mem_cd='"+litrve.get(k).getMMember().getMemCd()+"'";
                                                                                Query update = em.createNativeQuery(updatesql);
                                                                                update.executeUpdate();
                                                                                srno++;
                                                                            }
                                                                            status=1;
                                                                        }
                                                                        
                                                                    }else{
                                                                        // add privileges or 
                                                                        issueprocess=4;
                                                                    }
                                                                }else{
                                                                    issueprocess=3;
                                                                }
                                                            }
                                                        }
                                                    }else{
                                                        issueprocess=2;
                                                    }
                                                }else{ 
                                                    issueprocess=1;
                                                }
                                            }
                                            
                                            if (issueprocess == 0) {
                                                TIssue issue = new TIssue();
                                                TIssuePK issuePK = new TIssuePK();
                                                issuePK.setAccNo(bookdetails[0]);
                                                issuePK.setMemCd(member.getMemCd());
                                                issue.setTIssuePK(issuePK);
                                                issue.setDueDt(duedateforissue);
                                                issue.setIssDt(Todaydate);
                                                issue.setUserCd("useruser");
                                                issue.setMMember(member);

                                                create(issue);
                                                
                                                if(status==1){
                                                    ln.setStatus("IR");
                                                }else{
                                                    ln.setStatus("IS");
                                                }
                                                
                                                locationFacadeREST.edit(ln);

                                                if (issuemeadiacodeNcount.containsKey(ln.getMaterial().getCode())) {
                                                    int previouscount = issuemeadiacodeNcount.get(ln.getMaterial().getCode());
                                                    previouscount++;
                                                    issuemeadiacodeNcount.replace(ln.getMaterial().getCode(), previouscount);
                                                } else {
                                                    issuemeadiacodeNcount.put(ln.getMaterial().getCode(), 1);
                                                }

                                                output += bookdetails[0] + " Book is issued,";
                                            } else {
                                                if(issueprocess==1){
                                                    notprocess += bookdetails[0] + " Member has no reservation on this book,";
                                                }else if(issueprocess==2){
                                                    notprocess += bookdetails[0] + " This Book has no reservation you can reset the book status,";
                                                }else if(issueprocess==3){
                                                    notprocess += bookdetails[0] + " Book is not issue because member has less priority,";
                                                }else if(issueprocess==4){
                                                    notprocess += bookdetails[0] + " Reservation is expire,";
                                                }else{
                                                    notprocess += bookdetails[0] + " Book is not issue,";
                                                }
                                                
                                            }

                                        } 
                                    } else {
                                        notprocess += bookdetails[0] + " category material privileges exceed,";
                                    }
                                } else {
                                    notprocess += bookdetails[0] + " This material is not allowed to issue due to not have the privileges,";
                                }
                            } else {
                                notprocess += bookdetails[0] + " Book status is not Availavle or ON Hold,";
                            }
                        } else {
                            notprocess += bookdetails[0] + " Book details not found,";
                        }
                    }
                } else {
                    if (skip == 1) {
                        notprocess = "Member category not have privilleges to issue books..";
                    } else if (skip == 2) {
                        notprocess = "Member has issue maximum book..";
                    } else {
                        notprocess = "Library user not found..";
                    }

                }
            }
        } else {
            if (member != null) {
                if (member.getMemStatus().equals("S")) {
                    notprocess = "Member status is not active..";
                } else if (member.getNoDueTag().equals("Y")) {
                    notprocess = "Member issue no due certificate..";
                }
            } else {
                notprocess = "Member not found..";
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


    @POST
    @Path("return")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData returnbook(String accnosdata) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String accno="";
        String datatype = accnosdata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(accnosdata);
            accno = jsonobj.getString("accno");
          
        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(accnosdata);
                accno = stringintoxml.getdatafromxmltag(doc, "accno");
          
            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }
        
        String[] accnos = accno.split(",");
        
        for(int i=0;i<accnos.length;i++){
            List<Location> liloc=null;
                liloc=locationFacadeREST.getByAcc(accnos[i]);
                if(liloc.size()>0){
                    Location ln=null;
                    ln = liloc.get(0);
                    if(ln.getStatus().equals("IS") || ln.getStatus().equals("IR")) {
                        List<TIssue>litissue=null;
                        litissue=findBy("findByAccNo", accnos[i]);
                        if(litissue.size()>0){
                            MMember m =null;
                            m= mMemberFacadeREST.find(litissue.get(0).getTIssuePK().getMemCd());
                            if(m!=null){
                               int dueprocess=0;
                               BigDecimal fineamt;
                               if(litissue.get(0).getDueDt().before(Todaydate)){
                                   //fineamt =BigDecimal.valueOf(Double.valueOf("0.00"));
                                    fineamt = getdueofbook(m.getMemCtgry().getCtgryCd(),ln.getMaterial().getCode(),litissue.get(0).getDueDt());
                                    if(fineamt.compareTo(BigDecimal.valueOf(Double.valueOf("0.00")))==1){
                                        TMemfine fine = new TMemfine();
                                        fine.setAccnNo(accnos[i]);
                                        fine.setFineAmt(fineamt);
                                        fine.setMemCd(m.getMemCd());
                                        fine.setIssDt(litissue.get(0).getIssDt());
                                        fine.setRetDt(litissue.get(0).getDueDt());
                                        fine.setSlipDt(Todaydate);
                                        fine.setUserCd("Superuser");
                                        fine.setFineDesc("issue OverDue");
                                        
                                        fine = tMemfineFacadeREST.createAndGet(fine);
                                        
                                        // due enter in memdue table
                                        TMemdue due = new TMemdue();
                                        due.setDueAmt(fine.getFineAmt());
                                        due.setMMember(m);
                                        due.setTMemfine(fine);
                                        due.setSlipDt(fine.getSlipDt());
                                        due.setSlipNo(fine.getSlipNo());
                                        tMemdueFacadeREST.create(due);
                                        
                                        TReceivePK trpk = new TReceivePK();
                                        trpk.setAccnNo(accnos[i]);
                                        trpk.setIssDt(litissue.get(0).getIssDt());
                                        trpk.setMemCd(m.getMemCd());
                                        
                                        TReceive tr= new TReceive();
                                        tr.setTReceivePK(trpk);
                                        tr.setSlipNo(String.valueOf(fine.getSlipNo()));
                                        tr.setRecvDt(Todaydate);
                                        tr.setRecvUserCd("superuser");
                                        tr.setIssUserCd(litissue.get(0).getUserCd());
                                        tReceiveFacadeREST.create(tr);
                                        
                                        removeByMemcdAndAccNo(m.getMemCd(),accnos[i]);
                                        
                                        output += accnos[i]+" Book is retrn to library -"+fineamt.toString()+"-"+fine.getSlipNo()+",";
                                    }else{
                                        // retrun book
                                        TReceivePK trpk = new TReceivePK();
                                        trpk.setAccnNo(accnos[i]);
                                        trpk.setIssDt(litissue.get(0).getIssDt());
                                        trpk.setMemCd(m.getMemCd());
                                        
                                        TReceive tr= new TReceive();
                                        tr.setTReceivePK(trpk);
                                        tr.setSlipNo("NaN");
                                        tr.setRecvDt(Todaydate);
                                        tr.setRecvUserCd("superuser");
                                        tr.setIssUserCd(litissue.get(0).getUserCd());
                                        tReceiveFacadeREST.create(tr);
                                        
                                        removeByMemcdAndAccNo(m.getMemCd(),accnos[i]);
                                        
                                        output += accnos[i]+" Book is retrn to library,";
                                        
                                    }
                               }else{
                                   // return book
                                        TReceivePK trpk = new TReceivePK();
                                        trpk.setAccnNo(accnos[i]);
                                        trpk.setIssDt(litissue.get(0).getIssDt());
                                        trpk.setMemCd(m.getMemCd());
                                        
                                        TReceive tr= new TReceive();
                                        tr.setTReceivePK(trpk);
                                        tr.setSlipNo("NaN");
                                        tr.setRecvDt(Todaydate);
                                        tr.setRecvUserCd("superuser");
                                        tr.setIssUserCd(litissue.get(0).getUserCd());
                                        tReceiveFacadeREST.create(tr);
                                        
                                        removeByMemcdAndAccNo(m.getMemCd(),accnos[i]);
                                        
                                        output += accnos[i]+" Book is retrn to library,";
                               }
                               
                              
                               if(ln.getStatus().equals("IR")){
                                    List<TReserve> lirevs=null;
                                  lirevs= tReserveFacadeREST.findBy("findByRecordNo", String.valueOf(ln.getLocationPK().getRecID()));
                                  if(lirevs.size()>0){
                                      ln.setStatus("AR");
                                  }else{
                                      ln.setStatus("AV");
                                  }
                               }else{
                                  ln.setStatus("AV");
                               }
                               
                               locationFacadeREST.edit(ln);
                               
                            }else{
                                notprocess+=accnos[i]+" Member data not found,";
                            }
                        }else{
                            notprocess+=accnos[i]+" Book not found,";
                        }
                    }else{
                        notprocess+=accnos[i]+" Book status is not issue,";
                    }
                }else{
                    notprocess+=accnos[i]+" Book not available,";
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

    @PUT
    @Path("renew")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData renew(String renewaldata) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String accno="";
        String datatype = renewaldata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(renewaldata);
            accno = jsonobj.getString("accno");
          
        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(renewaldata);
                accno = stringintoxml.getdatafromxmltag(doc, "accno");
          
            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }
        
        String[] accnos = accno.split(",");
        Map<String,Integer> materialcodenduration = new HashMap<>();
        for(int i=0;i<accnos.length;i++){
            List<Location> liloc =null;
            liloc = locationFacadeREST.getByAcc(accnos[i]);
            if(liloc.size()>0){
                Location ln=null;
                ln=liloc.get(0);
                if (ln.getStatus().equals("IS")) {
                    List<TIssue> litissue = null;
                    litissue = findBy("findByAccNo", accnos[i]);
                    if (litissue.size() > 0) {
                        TIssue ti = null;
                        ti = litissue.get(0);
                        if (ti.getDueDt().after(Todaydate) || ti.getDueDt().equals(Todaydate)) {
                            List<MCtgryMedia> limemctmedia = null;
                            limemctmedia = mCtgryMediaFacadeREST.findBy("findByCtgryCd", ti.getMMember().getMemCtgry().getCtgryCd());
                            if (limemctmedia.size() > 0) {
                                for (int j = 0; j < limemctmedia.size(); j++) {
                                    materialcodenduration.put(limemctmedia.get(j).getMCtgryMediaPK().getMediaCd(), limemctmedia.get(j).getIssPeriod());
                                }
                                System.out.println("materialcodenduration :" + materialcodenduration);
                                List<MWeekoffday> mweekOffList = null;
                                // week of day
                                mweekOffList = mWeekoffdayFacadeREST.findAll();
                                int dayofweek = Integer.valueOf(mweekOffList.get(0).getWeekOffDay());
                                int durationofallow = materialcodenduration.get(ln.getMaterial().getCode());

                                // check due_dt  
                                Calendar cl = Calendar.getInstance();
                                cl.add(Calendar.DATE,durationofallow);
                                List<MCalender> holidayList = null;
                                boolean duedtfind = true;

                                do {
                                    if (cl.get(Calendar.DAY_OF_WEEK) == dayofweek) {
                                        System.err.println("Day of date :" + cl.get(Calendar.DAY_OF_WEEK));
                                        cl.add(5, 1);
                                        cl.set(Calendar.HOUR_OF_DAY, 0);
                                        cl.set(Calendar.MINUTE, 0);
                                        cl.set(Calendar.SECOND, 0);
                                    }

                                    holidayList = null;
                                    //now check holidays

                                    System.err.println("Date of Issue :" + cl.getTime());
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                                    holidayList = mCalenderFacadeREST.findBy("findByFestivalDt", dateFormat.format(cl.getTime()));

                                    if (holidayList.size() > 0) {
                                        cl.add(5, 1);
                                    } else {
                                        if (cl.get(Calendar.DAY_OF_WEEK) == dayofweek) {
                                            cl.add(5, 1);
                                        } else {
                                            duedtfind = false;
                                        }
                                    }

                                } while (duedtfind);

                                cl.set(Calendar.HOUR_OF_DAY, Calendar.HOUR_OF_DAY);
                                cl.set(Calendar.MINUTE, Calendar.MINUTE);
                                cl.set(Calendar.MINUTE, Calendar.MINUTE);

//                                Date duedateforrenewal = cl.getTime();
                                System.err.println("final of renewal :" + cl.getTime());
                                
                                System.err.println("old issue dt :" + ti.getIssDt());
                                System.err.println("old due dt :" + ti.getDueDt());
                                
                                ti.setIssDt(Todaydate);
                                ti.setDueDt(cl.getTime());
                                
                                edit(ti);
                                
                                output+=accnos[i] +" is renew for member : "+ti.getMMember().getMemCd();

                            } else {
                                notprocess += accnos[i] + " Member has no privileges,";
                            }
                        } else {
                            notprocess += accnos[i] + " book due date is over,";
                        }
                    } else {
                        notprocess += accnos[i] + " book not found,";
                    }
                } else {
                    notprocess += accnos[i] + " book status is not issue,";
                }
            }else{
                notprocess+=accnos[i]+" book not available,";
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
   

//   @DELETE
//   @Path("removeByMemcdAndAccNo")
    public void removeByMemcdAndAccNo(String memCd, String accNo) {
        //String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();

        valueList.add(memCd);
        valueList.add(accNo);

        super.removeBy("TIssue.removeByMemcdAndAccNo", valueList);
    }

//    void returnIssue(String acc_Nos, Map<String, String> accNoSlipNo) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    /**
     *
     * @param query
     * @param values
     * @return
     */
    @GET
    @Path("ReminderMail/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<TIssue> GetIssuedItemDetailsforReminderToMember(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch (query) {
            case "findByDueDtBetween":
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    valueList.add(dateFormat.parse(valueString[0]));
                    valueList.add(dateFormat.parse(valueString[1]));

                } catch (ParseException ex) {
                    Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "findByIssDtBetween":
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    valueList.add(dateFormat1.parse(valueString[0]));
                    valueList.add(dateFormat1.parse(valueString[1]));

                } catch (ParseException ex) {
                    Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                valueList.add(valueString[0]);
                break;
        }
        System.out.println("TIssue." + query + valueList);
        return super.findBy("TIssue." + query, valueList);
    }

    @GET
    @Path("IssuedItemsDetails/{searchParameter}/{param}")
    @Produces({"application/xml", "application/json"})
    public List<TIssue> GetIssuedItemDetails(@PathParam("searchParameter") String code, @PathParam("param") String param) {
        switch (code) {
            case "memCd":
                code = "findByMemCd";
                break;
            case "titleLike":
                code = "findByTitleLike";
                break;
        }
        List<TIssue> cd = findBy(code, param);
        return cd;
    }

   

    public Date getDueDate(String accNos, String memCd) {
        MMember member = mMemberFacadeREST.find(memCd);
        Location location;
        MCtgryMedia privilege;
        //   GenericType<List<Location>> genericType = new GenericType<List<Location>>(){};
        Map<String, Integer> matTypeIssDays = new HashMap<>();
        //  GenericType<List<CtgryMediaIssueReserve>> privilegeGenericType = new GenericType<List<CtgryMediaIssueReserve>>(){};
        //JsonObjectBuilder object = Json.createObjectBuilder();
        Calendar dueDate;
        String libMatCode;
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<MWeekoffday> mweekOffList = new ArrayList<>();
        List<String> weekOffList = new ArrayList<>();
        Date dt1=null;
        mweekOffList = mWeekoffdayFacadeREST.findAll();
        for (MWeekoffday w : mweekOffList) {
            weekOffList.add(w.getWeekOffDay());
        }

        //for (int i = 0; i < accNos.length; i++) {
            location = locationFacadeREST.findBy("findByP852", accNos).get(0);
            libMatCode = location.getMaterial().getCode();

            if (!matTypeIssDays.containsKey(libMatCode)) {
                privilege = mCtgryMediaFacadeREST.findBy("findByCtgryCdAndMediaCd", member.getMemCtgry().getCtgryCd() + "," + location.getMaterial().getCode()).get(0);
                matTypeIssDays.put(libMatCode, privilege.getIssPeriod());
            }
            
            dueDate = Calendar.getInstance();
            if(matTypeIssDays.size()>0){
                dueDate.setTime(new Date());
                dueDate.add(Calendar.DATE, matTypeIssDays.get(libMatCode));
                dueDate = getWorkingDayDate(dueDate, weekOffList);     
            }
            
               //Check Week of Day and gets next working day date
            dt1 = dueDate.getTime();
            //object.add(accNos[i], dateFormat.format(dueDate.getTime()).toString());
        //}

        return dt1;
    }

    public Calendar getWorkingDayDate(Calendar dueDate, List weekOffList) {
        dueDate = setTimeZero(dueDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Boolean workingDay = false;
        List<MCalender> holidayList;
        //Calender.SUNDAY = 1, MONDAY=2, TUESDAY=3, WEDNESDAY=4, THURSDAY=5, FRIDAY=6, SATURDAY=7
        while (!workingDay) {
            workingDay = true;
            if (weekOffList.contains(Integer.toString(dueDate.get(Calendar.DAY_OF_WEEK))))//Check for WeekOffday
            {
                dueDate.add(Calendar.DATE, 1);
                workingDay = false;
            }
            if (workingDay) //Check of Holiday
            {
                holidayList = mCalenderFacadeREST.findBy("findByFestivalDt", dateFormat.format(dueDate.getTime()));
                if (!holidayList.isEmpty()) {
                    dueDate.add(Calendar.DATE, 1);
                    workingDay = false;
                }
            }
        }
        return dueDate;
    }

    public Calendar setTimeZero(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        return date;
    }

    public BigDecimal getdueofbook(String categorycode, String materialcode, Date duedt) {
        BigDecimal dueCharge = BigDecimal.ZERO;
        MCtgryMediaPK mCtgryMedia_pk = new MCtgryMediaPK();
        mCtgryMedia_pk.setCtgryCd(categorycode);
        mCtgryMedia_pk.setMediaCd(materialcode);
        MCtgryMedia mCtgryMedia = mCtgryMediaFacadeREST.find(mCtgryMedia_pk);
        //3. Calulation of Due charge
        long dateDiff = new Date().getTime() - duedt.getTime();
        long days = dateDiff / (24 * 60 * 60 * 1000);

        long phase1period = mCtgryMedia.getFinePhase1Prd();
        long phase2period = mCtgryMedia.getFinePhase2Prd();
        BigDecimal phase1charge = mCtgryMedia.getFinePhase1Charge();
        BigDecimal phase2charge = mCtgryMedia.getFinePhase2Charge();

        BigDecimal defaultCharge = mCtgryMedia.getFineCharges();

        System.out.println("P1P: " + phase1period + "   P2P: " + phase2period);
        System.out.println("P1C: " + phase1charge + "   P2C: " + phase2charge);
        System.out.println("DC: " + defaultCharge);

        dueCharge = phase1charge.multiply(new BigDecimal(Math.min(phase1period, Math.max(0, days))));
        dueCharge = dueCharge.add(phase2charge.multiply(new BigDecimal(Math.min(phase2period, Math.max(0, days - phase1period)))));
        dueCharge = dueCharge.add(defaultCharge.multiply(new BigDecimal(Math.max(0, days - phase1period - phase2period))));
        return dueCharge;
    }

    //item issue return hostory 
    //Returns the history of issue and return over a specified period by accession no as matching parameter for items
    //member code as matching parameter for members 
    @GET
    @Path("IssueReturnHistory/byDateBetween/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> IssueReturnHistory(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dateFormat2.parse(valueString[0]));
        System.out.println(dateFormat2.parse(valueString[1]));
        System.out.println(valueString[0]);
        System.out.println(valueString[1]);
        switch (Paramname) {

            case "ForMembers":
                //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                q = "select  count(t_receive.mem_cd) as Issue_Count, m_member.mem_firstnm, m_member.mem_lstnm, m_member.mem_id,\n"
                        + "m_member.mem_cd, m_ctgry.ctgry_desc, m_fcltydept.Fclty_dept_dscr, m_fcltydept.Fclty_dept_dscr, m_branchmaster.Branch_name from t_issue \n"
                        + "right outer join t_receive on t_issue.mem_cd = t_receive.mem_cd and t_issue.acc_no = t_receive.accn_no \n"
                        + "join m_member on t_receive.mem_cd = m_member.mem_cd\n"
                        + "join m_ctgry on m_member.mem_ctgry = m_ctgry.ctgry_cd\n"
                        + "join m_fcltydept on m_member.mem_inst = m_fcltydept.Fclty_dept_cd\n"
                        + "join m_fcltydept m2 on m_member.mem_dept = m2.Fclty_dept_cd\n"
                        + "join m_branchmaster on m_member.mem_degree = m_branchmaster.branch_cd\n"
                        + "where t_receive.iss_dt BETWEEN '" + valueString[0] + "' AND '" + valueString[1] + "' OR t_issue.iss_dt BETWEEN '" + valueString[0] + "' AND '" + valueString[1] + "' group by t_receive.mem_cd";
                break;

            case "ForItems":
                q = "select count(t_receive.accn_no), t_receive.accn_no, location.k852, concat((select FValue from biblidetails where RecID = location.RecID AND tag in ('210','222','240','242','243','245','246','247') and SbFld in ('a','b')), '-') AS Title,\n"
                        + "concat((select FValue from biblidetails where RecID = location.RecID AND tag in ('100','110','111','130') AND sbfld = 'a'), '-') AS 'Main Author' from t_issue \n"
                        + "right outer join t_receive on t_issue.acc_no = t_receive.accn_no \n"
                        + "join location on t_receive.accn_no = location.p852\n"
                        + "join biblidetails on location.RecID = biblidetails.RecID\n"
                        + "where t_receive.iss_dt between '" + valueString[0] + "' AND '" + valueString[1] + "' OR t_issue.iss_dt BETWEEN '" + valueString[0] + "' AND '" + valueString[1] + "' group by t_receive.accn_no, location.k852, location.RecID";
                System.out.println(dateFormat2.parse(valueString[0]));
                System.out.println(dateFormat2.parse(valueString[0]));
                System.out.println(q);
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }

    //Item issued over a period
    //This methods return the details about issued items and the members who issued the items
    @GET
    @Path("ItemIssuedOverAPeriod/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> ItemIssuedOverAPeriod(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {

            case "byAccessionNo":
                q = "select a.mem_cd, (c.mem_firstnm+c.mem_lstnm), d.ctgry_desc, a.acc_no, e.k852, b.FValue, a.iss_dt, "
                        + " a.due_dt from  (select mem_cd,acc_no,iss_dt,due_dt  from t_issue union select mem_cd,accn_no,iss_dt,recv_dt  from t_receive) a \n"
                        + " join    (SELECT Location.RecID, Location.p852, Biblidetails.FValue, Location.f852 mem_cd\n"
                        + " FROM    Location INNER JOIN Biblidetails ON Location.RecID = Biblidetails.RecID \n"
                        + " WHERE   (Location.p852 IS NOT NULL) AND (Biblidetails.Tag = '245') AND (Biblidetails.SbFld = 'a')) b on  a.acc_no = b.p852\n"
                        + " join    m_member c  on a.mem_cd = c.mem_cd\n"
                        + " join    m_ctgry d on c.mem_ctgry = d.ctgry_cd\n"
                        + " join    location e on a.acc_no = e.p852\n"
                        + " join    t_issue f on f.acc_no = a.acc_no \n"
                        + " where e.p852 = '" + Paramvalue + "'";
                break;

            case "byMemberCode":
                q = "select * from m_member a join t_issue b on a.mem_cd = b.mem_cd join"
                        + "( SELECT        Location.RecID, Location.p852, Biblidetails.FValue, Location.f852, Location.k852"
                        + " FROM            Location INNER JOIN"
                        + "   Biblidetails ON Location.RecID = Biblidetails.RecID"
                        + "  WHERE  (Location.p852 IS NOT NULL) AND (Biblidetails.Tag = '245'\n"
                        + ") AND (Biblidetails.SbFld = 'a'\n"
                        + ") ) c"
                        + " on b.acc_no = c.p852 "
                        + " WHERE a.mem_cd = '" + Paramvalue + "'";
                break;

            case "byUserCode":
                q = "select a.mem_cd, (c.mem_firstnm+c.mem_lstnm), d.ctgry_desc, a.acc_no, e.k852, b.FValue, a.iss_dt, "
                        + "a.due_dt from  (select mem_cd,acc_no,iss_dt,due_dt  from t_issue union select mem_cd,accn_no,iss_dt,recv_dt  from t_receive) a \n"
                        + " join	(SELECT Location.RecID, Location.p852, Biblidetails.FValue, Location.f852 mem_cd\n"
                        + " FROM	Location INNER JOIN Biblidetails ON Location.RecID = Biblidetails.RecID \n"
                        + " WHERE	(Location.p852 IS NOT NULL) AND (Biblidetails.Tag = '245') AND (Biblidetails.SbFld = 'a')) b on  a.acc_no = b.p852\n"
                        + " join	m_member c  on a.mem_cd = c.mem_cd\n"
                        + " join	m_ctgry d on c.mem_ctgry = d.ctgry_cd\n"
                        + " join	location e on a.acc_no = e.p852\n"
                        + " join	t_issue f on f.acc_no = a.acc_no\n"
                        + " WHERE f.user_cd = '" + Paramvalue + "' ";
                break;

            case "byIssueDateBetween":
                //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                q = "select  count(t_receive.mem_cd) as Issue_Count, m_member.mem_firstnm, m_member.mem_lstnm, m_member.mem_id,\n"
                        + "m_member.mem_cd, m_ctgry.ctgry_desc, m_fcltydept.Fclty_dept_dscr, m_fcltydept.Fclty_dept_dscr, m_branchmaster.Branch_name from t_issue \n"
                        + "right outer join t_receive on t_issue.mem_cd = t_receive.mem_cd and t_issue.acc_no = t_receive.accn_no \n"
                        + "join m_member on t_receive.mem_cd = m_member.mem_cd\n"
                        + "join m_ctgry on m_member.mem_ctgry = m_ctgry.ctgry_cd\n"
                        + "join m_fcltydept on m_member.mem_inst = m_fcltydept.Fclty_dept_cd\n"
                        + "join m_fcltydept m2 on m_member.mem_dept = m2.Fclty_dept_cd\n"
                        + "join m_branchmaster on m_member.mem_degree = m_branchmaster.branch_cd\n"
                        + "where t_receive.iss_dt BETWEEN '" + valueString[0] + "' AND '" + valueString[1] + "' OR t_issue.iss_dt BETWEEN '" + valueString[0] + "' AND '" + valueString[1] + "' group by t_receive.mem_cd";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }

//    @POST
//    @Path("issue/{memcode}/{accNo}/{dueDate}")
//    @Consumes({"application/xml", "application/json"})
//    @Produces("text/plain")
//    public String issueBook(@PathParam("memcode") String memCd, @PathParam("accNo") String acc_Nos, @PathParam("dueDate") String due_Dates) {
//        {
//            //1. Check if Current issue if equal to max allowed.
//            //2. Check if IssueRestricted.
//            //3. If status of book is OnHold, it is checked if it is being issued to correct member for whome it is reserved or not
//            //4. Make new entry in t_issue
//            //5. Update status of item in location
//            if (mMemberFacadeREST.CheckMemberAvalibility(memCd)) {
//                member = mMemberFacadeREST.find(memCd);
//            } else {
//                return "Invalid Member Code";
//            }
//            String[] dueDates = due_Dates.split(",");
//            String[] accNos = acc_Nos.split(",");
//            Integer issueCount = 0;
//
//            List<String> restrictedAccNo = new ArrayList<>();
//            List<String> maxLimitAccNo = new ArrayList<>();
//            List<String> notAvailableAccNo = new ArrayList<>();
//
//            System.out.println(accNos[0]);
//            if (accNos.length == 0) {
//                return "invalid member code";
//            }
//            for (int i = 0; i < accNos.length; i++) {
//                try {
//                    location = locationFacadeREST.findBy("findByP852", accNos[i]).get(0);
//                } catch (ArrayIndexOutOfBoundsException e) {
//                    output = "Invalid Accession No.";
//                    return output;
//                }
//
//                MCtgry mCtgry = mCtgryFacadeREST.find(member.getMemCtgry().getCtgryCd());
//                String isscount = countBy("countByMemCd", memCd);
//
//                System.out.println("isscount--" + isscount);
//                //1. Conditaion for Checking if Current Issued count is less than max allowed or not
//                if (isscount == null || (mCtgry.getMaxBookAllow() - Integer.parseInt(isscount) > 0)) {
//                    //2. Condition to check if Item is issue restricted
//                    if (!"Y".equals(location.getIssueRestricted())) {
//                        issue = new TIssue();
//                        // java.sql.Date sqlDate = java.sql.Date.valueOf( new Date().getTime() );
//                        issue.setIssDt(new java.sql.Date(new Date().getTime()));
//
//                        try {
//                            issue.setDueDt(new java.sql.Date(parseFormat.parse(dueDates[i]).getTime()));
//                        } catch (ParseException ex) {
//                            Logger.getLogger(TIssueFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        issue.setMMember(member);
//                        issue.setUserCd("super user");   //Need to be changed
//                        TIssuePK issuePK = new TIssuePK();
//                        issuePK.setAccNo(accNos[i]);
//                        issuePK.setMemCd(memCd);
//                        issue.setTIssuePK(issuePK);
//                        System.out.println("location.getStatus() " + location.getStatus());
//                        if (location.getStatus().equals("AV") || location.getStatus().equals("AR")) {
//                            //3. Check if OnHold 
//                            if (location.getStatus().equals("AR")) {
//                                reserve = tReserveFacadeREST.findBy("findByRecNoAndMinSrNo", Integer.toString(location.getLocationPK().getRecID())).get(0);
//                                //If it is not being issued to correct member then contine with next item in list( Dont issue this)
//                                if (!reserve.getMMember().getMemCd().equals(member.getMemCd())) {
//                                    continue;   //if current issue requesting member is not equal to max priority member for whom book is reserved
//                                    //then continue
//                                }
//
//                                //If correct member then remove entry from reserve table and contine with issue
//                                //String s = new String();
//                                //     UriBuilder uriBuilder ;
//                                //    uriBuilder.path(";memCd="+member.getMemCd()+";recordNo="+location.getLocationPK().getRecID());
//                                tReserveFacadeREST.removeByMemcdAndRecordNo(member.getMemCd(), location.getLocationPK().getRecID());
//                            }
//                            //5.Updation of location
//                            location.setStatus("IS");
//                            locationFacadeREST.edit(location);
//
//                            //4.Entry in issue
//                            create(issue);
//                            issueCount++;
//                        } else {
//                            notAvailableAccNo.add(accNos[i]);
//                        }
//                    } else {
//                        restrictedAccNo.add(accNos[i]);
//                    }
//                } else {
//                    maxLimitAccNo.add(accNos[i]);
//                }
//            }
//
//            /////////////////////For sending appropriate message to client side////////////////////////
//            String restrict = "";
//            String maxLimit = "";
//
//            output = issueCount + " Items issued.";
//
//            if (restrictedAccNo.size() > 0) {
//                for (int i = 0; i < restrictedAccNo.size() - 1; i++) {
//                    restrict = restrict + "," + restrictedAccNo.get(i);
//                }
//                if (restrictedAccNo.size() > 1) {
//                    restrict = restrict.substring(1) + " and " + restrictedAccNo.get(restrictedAccNo.size() - 1);
//                } else {
//                    restrict = restrictedAccNo.get(0);
//                }
//
//                if (maxLimitAccNo.size() > 0) {
//                    for (int i = 0; i < maxLimitAccNo.size() - 1; i++) {
//                        maxLimit = maxLimit + "," + maxLimitAccNo.get(i);
//                    }
//                    if (maxLimitAccNo.size() > 1) {
//                        maxLimit = maxLimit.substring(1) + " and " + maxLimitAccNo.get(maxLimitAccNo.size() - 1);
//                    } else {
//                        maxLimit = maxLimitAccNo.get(0);
//                    }
//                    output = output
//                            + (accNos.length - restrictedAccNo.size() - maxLimitAccNo.size()) + " Items are Issued."
//                            + "Items will Accession No. " + restrict + " are Restricted for Issue."
//                            + "Items with Accession No." + maxLimit + " are not issued because Max Issue Limit excedded.";
//                } else {
//                    output = output
//                            + (accNos.length - restrictedAccNo.size() - maxLimitAccNo.size()) + " Items are Issued."
//                            + "Items will Accession No. " + restrict + " are Restricted for Issue./n";
//                }
//            } else if (maxLimitAccNo.size() > 0) {
//                for (int i = 0; i < maxLimitAccNo.size() - 1; i++) {
//                    maxLimit = maxLimit + "," + maxLimitAccNo.get(i);
//                }
//                if (maxLimitAccNo.size() > 1) {
//                    maxLimit = maxLimit.substring(1) + " and " + maxLimitAccNo.get(maxLimitAccNo.size() - 1);
//                } else {
//                    maxLimit = maxLimitAccNo.get(0);
//                }
//                output = (accNos.length - restrictedAccNo.size() - maxLimitAccNo.size()) + " Items are Issued." + "\n"
//                        + "Items with Accession No." + maxLimit + " are not issued because Max Issue Limit excedded.";
//            }
//
//            // label1:
//            if (notAvailableAccNo.size() > 0) {
//                output = output + "\n"
//                        + "Following books are not available for issue: ";
//                for (int i = 0; i < notAvailableAccNo.size(); i++) {
//                    output = output + notAvailableAccNo.get(i) + ",";
//                }
//                output = output.substring(0, output.length() - 1);
//            }
//            return output;
//        }
//
//    }
//     //Reminder Mail
//    @GET
//    @Path("getdetails/reminder/{memberInstitute}/{memberDepartment}/{memberDegree}")
//    @Produces({"application/xml", "application/json"})
//    public List<TIssue> findByParams(@PathParam("memberInstitute") String memberInstitute, 
//                                     @PathParam("memberDepartment") String memberDepartment,
//                                     @PathParam("memberDegree") String memberDegree) 
//    {
//     
//        List<String> valueList = new ArrayList<>();
//        valueList.add(memberInstitute);
//        valueList.add(memberDepartment);
//        valueList.add(memberDegree);
//        
//        List<TIssue> list = super.findBy( "getdetails", valueList);
//        return list;
//       
//    }
}
