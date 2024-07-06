/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
import javax.xml.registry.infomodel.User;
import soul.circulation.MMember;
import soul.circulation.TMemlog;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.tmemlog")
public class TMemlogFacadeREST extends AbstractFacade<TMemlog> {

    @EJB
    private MMemberFacadeREST mMemberFacadeREST;
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output;
    String[] memCodes;
    TMemlog memLog;
    List<TMemlog> members = new ArrayList();
    MMember member;
    List<TMemlog> memLogList = new ArrayList();

    public TMemlogFacadeREST() {
        super(TMemlog.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TMemlog entity) {
        super.create(entity);
    }

    @POST
    @Path("createAndGet")
    @Override
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public TMemlog createAndGet(TMemlog entity) {
        return super.createAndGet(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TMemlog entity) {
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
    public TMemlog find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TMemlog> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TMemlog> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    //This method will return the list of members who logged in between specied date/time
    @GET
    @Path("findByLoginDateBetween/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<TMemlog> findByLoginDateBetween(@PathParam("searchParameter") String param) {
        List<TMemlog> group = findBy("findByLoginDateBetween", param);
        return group;
    }

    //This method will return the member logged in details by specific member code
    @GET
    @Path("MemberLogByMemberCode/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<TMemlog> MemberLogByMemberCode(@PathParam("searchParameter") String param) {
        List<TMemlog> group = findBy("findByMemCd", param);
        return group;
    }

    @GET
    @Path("CurrentLoggedInMembers")
    @Produces({"application/xml", "application/json"})
    public List<TMemlog> CurrentLoggedInMembers() throws ParseException {
        String q = "";
        List<TMemlog> result = findBy("findByLogouttimeISNULL", "null");
        return result;
    }

    //Added Manually
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<TMemlog> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        switch (query) {

            case "findByLoginDateBetween":
                try {
                    valueList.add(dateFormat.parse(valueString[0]));
                    valueList.add(dateFormat.parse(valueString[1]));
                } catch (ParseException ex) {
                    Logger.getLogger(TMemlogFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "findByLogouttimeISNULL":
                break;
            case "findByLogoutTimeAndMemCd":
                valueList.add(valueString[0]);
                break;
            default:
                valueList.add(valueString[0]);
            //used for findByMemCdAndLogouttimeNULL, findByMemCd
        }
        return super.findBy("TMemlog." + query, valueList);
    }

    @GET
    @Path("getLog/{parameter}/{fromDate}/{toDate}/{memCd}")
    @Produces({"application/xml", "application/json"})
   // public List<TMemlog> getLog(@PathParam("parameter") String parameter, @QueryParam("fromDate") String fromDate,
    //        @QueryParam("toDate") String toDate, @QueryParam("memCd") String memCd) {
    public List<TMemlog> getLog(@PathParam("parameter") String parameter, @PathParam("fromDate") String fromDate,
            @PathParam("toDate") String toDate, @PathParam("memCd") String memCd) {    
        List<TMemlog> list = null;
        switch (parameter) {
            case "logInDate":
                list = findBy("findByLogintimeBetween", fromDate + "," + toDate);
                break;
            case "currentLoggedIn":
                list = findBy("findByLogouttimeISNULL", "NULL");
                break;
            case "memCd":
                list = findBy("findByMemCd", memCd);
                break;
        }
        return list;
    }

    @GET
    @Path("MostFrequentMembers/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> MostFrequentlyLoggedInMembers(@PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;

        //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        q = "SELECT  t_memlog.mem_cd, concat_ws(' ',m_member.mem_firstnm, m_member.mem_lstnm) as mem_name,\n"
                + "TIMESTAMPDIFF(MINUTE,Login_time,Logout_time) as All_minute \n"
                + "FROM m_member, t_memlog \n"
                + "where m_member.mem_cd = t_memlog.mem_cd and Logout_time IS NOT NULL and \n"
                + "t_memlog.Login_time between '" + valueString[0] + "' AND '" + valueString[1] + "' \n"
                + "group by t_memlog.mem_cd,m_member.mem_firstnm,m_member.mem_lstnm order by All_minute DESC";
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
    
    @GET
    @Path("MemberLogByMemberId/{id}")
    @Produces({"application/xml", "application/json"})
    public List<Object> MemberLogByMemberId(@PathParam("id") String id) throws ParseException {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        q = "SELECT t_memlog.LogID, t_memlog.mem_cd, t_memlog.Login_time, t_memlog.Logout_time, "
                + "m_member.mem_firstnm +' '+ m_member.mem_lstnm as mem_name  FROM m_member ,"
                + "t_memlog where m_member.mem_cd = t_memlog.mem_cd and m_member.mem_id like '" + id + "%' ";
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }

    @POST
    @Path("Login/{memCd}")
    @Produces({"text/plain"})
    public String login(@PathParam("memCd") String memCd) {
        memLog = new TMemlog();
        List<TMemlog> memberLog = new ArrayList<TMemlog>();
        member = mMemberFacadeREST.find(memCd);
        String status = member.getMemStatus();
        memberLog = findBy("findByLogoutTimeAndMemCd",memCd);
        System.out.println("memberLog: "+memberLog);
        String logId = null;
        if (member == null) {
            output = "Member does not exist for this member code";
        } else if(memberLog.size() >= 1) {
            output = "Member already logged In.";
        } else {
            if(status.contentEquals("A")){
                memLog.setMemCd(memCd);
                memLog.setLogintime(new java.sql.Date(new Date().getTime()));
                logId = generateLogId(memCd, new java.sql.Date(new Date().getTime()));
                memLog.setLogID(logId);
                memLog = createAndGet(memLog);

                output = "Member Logged In.";
            }else{
                output = "Member is supended, cannot login.";
            }
        }
        return output;
    }

    @PUT
    @Path("LogoutAllMembers")
    @Consumes({"application/xml", "application/json"})
    @Produces({"text/plain", "application/xml", "application/json"})
    public String LogoutAllMembers() {
        members = TMemlogFacadeREST.super.findAll();
        for (TMemlog ml : members) {
            if (ml.getLogouttime() == null) //System.out.println("A");
            {
                System.out.println("D");
                ml.setLogouttime(new java.sql.Date(new Date().getTime()));
                edit(ml);
                output = "All logged-in members Logged out.";
            } else {
                output = "No Currently logged-in member";
            }
        }
        return output;
    }

    @PUT
    @Path("LogoutSingleMember/{memCd}")
    @Consumes({"application/xml", "application/json"})
    @Produces({"text/plain"})
    public String LogoutSingleMember(@PathParam("memCd") String memCd) {
        member = mMemberFacadeREST.find(memCd);
        memLogList = findBy("findByMemCdAndLogouttimeNULL", memCd);

        if (member == null) {
            output = "Member does not exist for this member code";
        } else if(memLogList.size() == 0){
            output = "Member is not logged in. Login and try again.";
        } else {
            for (TMemlog ml : memLogList) {
                ml.setLogouttime(new Date());
                edit(ml);
            }
            output = "Member Logged Out.";
        }
        return output;
    }
    
    public String generateLogId(String memCd, Date date){
        String logId = "";
        String finalLogId = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        members = TMemlogFacadeREST.super.findAll();
        String sDate = date.toString();
        System.out.println(members.size());
        System.out.println("B: "+sDate);
        sDate = sDate.replaceAll("-", "");
        System.out.println("A: "+sDate);
        int maxId = 0;
        logId = logId + sDate;
        ArrayList<String> ids = new ArrayList<>();
        for(int i = 0; i<members.size(); i++){
            String lDate = dateFormat.format(members.get(i).getLogintime());
            lDate = lDate.replace("-", "");
            if(lDate.contentEquals(sDate)){
                System.out.println("id:"+members.get(i).getLogID());
                ids.add(members.get(i).getLogID().toString());
            }
            System.out.println("lDate: "+lDate);
        }
        ArrayList<String> SintIds = new ArrayList<>();
        ArrayList<Integer> intIds = new ArrayList<>();
        if(ids.size() == 0){
            finalLogId = sDate + "0000";
        } else {
            for(int i=0 ; i<ids.size(); i++){
                SintIds.add(members.get(i).getLogID().toString().substring(8, ids.get(i).length()));
            }
            for(int i=0; i<SintIds.size(); i++){
                intIds.add(Integer.parseInt(SintIds.get(i)));
            }
            maxId = Collections.max(intIds);
            
            int length = Integer.toString(maxId).length();
            if(length == 1){
                finalLogId = sDate + ("000" + (maxId+1));
            } else if(length == 2){
                finalLogId = sDate + ("00" + (maxId+1));
            } else if(length == 3){
                finalLogId = sDate + ("0" + (maxId+1));
            }
        }
        System.out.println("ids: "+ids);
        System.out.println("Sids: "+SintIds);
        System.out.println("Iids: "+intIds);
        System.out.println("maxId: "+maxId);
        return finalLogId;
    }
}
