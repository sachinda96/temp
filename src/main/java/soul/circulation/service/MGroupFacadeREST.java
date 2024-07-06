/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
import javax.ws.rs.core.PathSegment;
import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.circulation.GroupmemberData;
import soul.circulation.MGroup;
import soul.circulation.MGroupPK;
import soul.circulation.MMember;
import soul.circulation.TGroupIssue;
import soul.response.StringData;
import soul.util.function.ConvertStringIntoJson;
import soul.response.StringprocessData;
import soul.util.function.ConvertStringIntoXml;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.mgroup")
public class MGroupFacadeREST extends AbstractFacade<MGroup> {
    @EJB
    private MMemberFacadeREST mMemberFacadeREST;
    @EJB
    private TGroupIssueFacadeREST groupIssueFacadeREST;
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    ConvertStringIntoJson stringintojson;
    ConvertStringIntoXml stringintoxml;
   // List<MGroup> mgroupCount = new ArrayList<>();
  //  List<MGroup> mgroupL = new ArrayList<>();
  //  List<MGroup> mgroupList = new ArrayList<>();
  //  String output = null;
  //  MGroup mgroup ,mgroup2;
  //  MGroup addMember;
 //   MMember member;
  //  MGroupPK mgrouppk;
 //   int srno;

    private MGroupPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;groupID=groupIDValue;memCd=memCdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.circulation.MGroupPK key = new soul.circulation.MGroupPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> groupID = map.get("groupID");
        if (groupID != null && !groupID.isEmpty()) {
            key.setGroupID(new java.lang.Integer(groupID.get(0)));
        }
        java.util.List<String> memCd = map.get("memCd");
        if (memCd != null && !memCd.isEmpty()) {
            key.setMemCd(memCd.get(0));
        }
        return key;
    }

    public MGroupFacadeREST() {
        super(MGroup.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(MGroup entity) {
        super.create(entity);
    }
    
    @POST
   // @Override
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public MGroup createAndGet(MGroup entity) {
       return super.createAndGet(entity);
    }
    
    @GET
    @Path("bygroupname/{groupname}")
    @Produces({"application/xml", "application/json"})
    public List<GroupmemberData> groupmemdata(@PathParam("groupname") String groupname){
        List<GroupmemberData> ligroupdata = null;
        Query q;
        String sql="SELECT m_group.GroupID as groupid,m_group.GroupName as groupname,m_group.SrNo as srno,m_group.mem_cd as memcd,concat(m_member.mem_firstnm,' ',m_member.mem_lstnm) as memname FROM m_group,m_member where m_group.mem_cd=m_member.mem_cd and m_group.GroupName='"+groupname+"'";
        q = em.createNativeQuery(sql, GroupmemberData.class);
        ligroupdata = q.getResultList();
        if(!(ligroupdata.size()>0)){
            ligroupdata = new ArrayList();
        }
        return ligroupdata;
    }
    
    @GET
    @Path("groupname")
    @Produces({"application/xml", "application/json"})
    public List<MGroup> groupname(){
        List<MGroup> ligroupdata = null;
        Query q;
        String sql="SELECT * FROM m_group where SrNo='1' and status='A'";
        q = em.createNativeQuery(sql, MGroup.class);
        ligroupdata = q.getResultList();
        if(!(ligroupdata.size()>0)){
            ligroupdata = new ArrayList();
        }
        return ligroupdata;
    }
    
    @GET
    @Path("checkgroupmember")
    @Produces({"application/xml", "application/json"})
    public List<MGroup> checkgroupmember(String memcd){
        List<MGroup> ligroupdata = null;
        Query q;
        String sql="SELECT * FROM m_group where status='A' and m_group.mem_cd='"+memcd+"'";
        q = em.createNativeQuery(sql, MGroup.class);
        ligroupdata = q.getResultList();
        if(!(ligroupdata.size()>0)){
            ligroupdata = new ArrayList();
        }
        return ligroupdata;
    }
    
    @GET
    @Path("getduedt/{groupname}")
    @Produces({"application/xml", "application/json"})
    public List<StringData> getduedtbygroupname(@PathParam("groupname") String groupnm){
         List<StringData> liduedt = new ArrayList();;
         StringData sd = new StringData();
         List<Object> liobj=null;
         Query q;
         String sql="SELECT m_ctgry.ctgry_end_dt FROM m_ctgry where m_ctgry.ctgry_cd in(select m_member.mem_ctgry from m_member where m_member.mem_cd in(select m_group.mem_cd from m_group where m_group.GroupName='"+groupnm+"' and m_group.SrNo='1' and m_group.status='A'))";
         q = em.createNativeQuery(sql);
         liobj = q.getResultList();
         if(liobj.size()>0){
             Object ob = liobj.get(0);
             sd.setMessage(ob.toString());
             liduedt.add(sd);
         }else{
             liduedt = new ArrayList();
         }
         return liduedt;
    }
    
    @POST
    @Path("addGroup")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData addGroup(String groupdata) {
        
        StringprocessData spd = new StringprocessData();
        String output="";
        String notprocess="";
        String datatype = groupdata.substring(0,1);
        String groupname="";
        String members="";
        
        if(datatype.equals("{")){
           stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(groupdata);
             groupname = jsonobj.getString("groupname");
             members = jsonobj.getString("memcd");
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(groupdata);
                groupname = stringintoxml.getdatafromxmltag(doc,"groupname");
                members = stringintoxml.getdatafromxmltag(doc,"memcd");
                
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }
        
        int skip=0;
        String member[] = members.split(",");
        int srno=1;
        List<MGroup> liallgroupfor = findAll();
        List<MGroup> limemgroups = findBy("findByGroupName",groupname);
        List<MGroup> limemgroupsfind=null;
        int groupidflg=0;
        if(liallgroupfor.isEmpty()){
            groupidflg=1;
        }
        if(limemgroups.size()>0){
            skip=1;
        }
        String memcrgty="";
        int groupid=0;
        int addmem=0;
        
        List<MGroup> mgli=null;
      
        if(skip==0){
            for(int i=0;i<member.length;i++){
                MMember m = mMemberFacadeREST.find(member[i]);
                int skipformemhasgroup=0;
               // mgli=findBy("findByMemCd",m.getMemCd());
               mgli = checkgroupmember(m.getMemCd());
                if(mgli.size()>0){
                    skipformemhasgroup=1;
                    if(i==0){
                        addmem=1;
                    }
                }
                if(m.getMemStatus().equals("S")){
                    skipformemhasgroup=2;
                }
                if(m!=null && skipformemhasgroup==0){
                    
                    if(i==0 || addmem==1){
                        MGroupPK mgpk = new MGroupPK();
                        mgpk.setMemCd(m.getMemCd());
                        if(groupidflg==1){
                            mgpk.setGroupID(1);
                            groupidflg=0;
                        }
                        
                        MGroup mg = new MGroup();
                        mg.setGroupName(groupname);
                        mg.setMMember(m);
                        mg.setMGroupPK(mgpk);
                        mg.setStatus("A");
                        mg.setSrNo(srno);
                        srno++;
                    
                        create(mg);
                        limemgroupsfind = findBy("findByGroupName",groupname);
                        if(limemgroupsfind.size()>0){
                            groupid = limemgroupsfind.get(0).getMGroupPK().getGroupID();
                        }
                       
                        memcrgty=m.getMemCtgry().getCtgryCd();
                      
                        output+=member[i]+",";
                        addmem=0;
                    }else{
                        if(memcrgty.equals(m.getMemCtgry().getCtgryCd())){
                            MGroupPK mgpk = new MGroupPK();
                            mgpk.setGroupID(groupid);
                            mgpk.setMemCd(m.getMemCd());      
                            
                            MGroup mg = new MGroup();
                            mg.setGroupName(groupname);
                            mg.setMMember(m);
                            mg.setMGroupPK(mgpk);
                            mg.setStatus("A");
                            mg.setSrNo(srno);
                            srno++;
                        
                            create(mg);
                            output+=member[i]+",";
                        }else{
                             notprocess+=member[i]+" Category not match,";
                        }
                    }
                }else{
                    if(skipformemhasgroup==1){
                        notprocess+=member[i]+" already eixsts in group ,";
                    }else if(skipformemhasgroup==2){
                        notprocess+=member[i]+" is suspended ,";
                    }else{
                        notprocess+=member[i]+" not eixsts ,";
                    }
                    
                }
                
            }
        }else{
            notprocess="Group already available !.";
        }
        String op;
        String nonop;
        if(output.length()>1){
            op = output.substring(0,output.length()-1);
        }else{
            op=output;
        }
        if(notprocess.length()>1){
            nonop = notprocess.substring(0,notprocess.length()-1);
        }else{
            nonop=notprocess;
        }
        
        spd.setProcessdata(op);
        spd.setNonprocessdata(nonop);
        return spd;
    }
    
    @DELETE
    @Path("deleteGroup/{id}")
    @Produces({"application/xml", "application/json"})
    public StringprocessData deleteGroup(@PathParam("id") String groupId)
    {   
        StringprocessData spd = new StringprocessData();
        String notprocess="";
        String output="";
        List<MGroup> ligroup = null;
        List<TGroupIssue> litissuegroup =null;
        List<TGroupIssue> ligrouptransaction =null;
        
        ligroup = findBy("findByGroupID", groupId);
        if(ligroup.size()>0){
            litissuegroup = groupIssueFacadeREST.checknumberofissuebygroup(groupId);
            if(litissuegroup.size()>0){
                notprocess="Group has "+litissuegroup.size()+" book issue !.";
            }else{
                ligrouptransaction=groupIssueFacadeREST.findBy("findByGroupID", groupId);
                if(ligrouptransaction.size()>0){
                    Query q;
                    String sql="update m_group set status='S' where GroupID='"+groupId+"'";
                    q = em.createNativeQuery(sql);
                    int stattusofsql = q.executeUpdate();
                    System.out.println("stattusofsql : "+stattusofsql);
                    output = "Group Deleted ..";
                }else{
                    removeByid("RemoveByGroupID", groupId);
                    output = "Group Deleted ..";
                }
                
            }
        }else{
            notprocess="Group not exists !.";
        }
        String op;
        String nonop;
        if(output.length()>1){
            op = output.substring(0,output.length()-1);
        }else{
            op=output;
        }
        if(notprocess.length()>1){
            nonop = notprocess.substring(0,notprocess.length()-1);
        }else{
            nonop=notprocess;
        }
        
        spd.setProcessdata(op);
        spd.setNonprocessdata(nonop);
        return spd;                                
    }
    
    
    @POST
    @Path("addMember")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData addMember(String addmemdataingroup)
    {
        StringprocessData spd = new StringprocessData();
        String notprocess="";
        String output="";
        Query q;
        String groupname="";
        String memcds ="";
        String memcode[]={};
        
        String datatype = addmemdataingroup.substring(0,1);
        
         if(datatype.equals("{")){
            stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(addmemdataingroup);
            groupname = jsonobj.getString("groupname");
            memcds = jsonobj.getString("memcd");
            memcode=memcds.split(",");
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(addmemdataingroup);
                groupname = stringintoxml.getdatafromxmltag(doc,"groupname");
                memcds = stringintoxml.getdatafromxmltag(doc,"memcd");
                memcode=memcds.split(",");
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }
        
      
        String sql="SELECT * FROM m_group where GroupName='"+groupname+"' and SrNo in(select max(SrNo) from m_group where GroupName='"+groupname+"')";
        q = em.createNativeQuery(sql, MGroup.class);
        List<MGroup> limemgroups = q.getResultList();
        List<MGroup> mgli=null;
        int skip=0;
        int groupid=0;
        int srno=1;
         String memcrgty="";
        if(!(limemgroups.size()>0)){
            skip=1;
        }else{
            groupid = limemgroups.get(0).getMGroupPK().getGroupID();
            srno = limemgroups.get(0).getSrNo();
            srno++;
            MMember m1 = mMemberFacadeREST.find(limemgroups.get(0).getMGroupPK().getMemCd());
            if(m1!=null && m1.getMemStatus().equals("A")){
                memcrgty=m1.getMemCtgry().getCtgryCd();
            }else{
                skip=1;
            }
            
        }
       
        if(skip==0){
            for(int i=0;i<memcode.length;i++){
                MMember m = mMemberFacadeREST.find(memcode[i]);
                int skipformemhasgroup=0;
               // mgli=findBy("findByMemCd",m.getMemCd());
               mgli = checkgroupmember(m.getMemCd());
                if(mgli.size()>0){
                    skipformemhasgroup=1;
                }
                if(m.getMemStatus().equals("S")){
                    skipformemhasgroup=2;
                }
                if(m!=null && skipformemhasgroup==0){
                     
                    if(memcrgty.equals(m.getMemCtgry().getCtgryCd())){
                            MGroupPK mgpk = new MGroupPK();
                            mgpk.setGroupID(groupid);
                            mgpk.setMemCd(m.getMemCd());      
                            
                            MGroup mg = new MGroup();
                            mg.setGroupName(groupname);
                            mg.setMMember(m);
                            mg.setMGroupPK(mgpk);
                            mg.setStatus("A");
                            mg.setSrNo(srno);
                            srno++;
                        
                            create(mg);
                            output+=memcode[i]+",";
                        
                    }else{
                             notprocess+=memcode[i]+" Category not match,";
                        }
                    
                }else{
                    if(skipformemhasgroup==1){
                        notprocess+=memcode[i]+" already eixsts in group ,";
                    }else if(skipformemhasgroup==2){
                        notprocess+=memcode[i]+" is suspended ,";
                    }else{
                        notprocess+=memcode[i]+" not eixsts ,";
                    }
                    
                }
            }    
        }else{
             notprocess="Group not available !.";
        }

        String op;
        String nonop;
        if(output.length()>1){
            op = output.substring(0,output.length()-1);
        }else{
            op=output;
        }
        if(notprocess.length()>1){
            nonop = notprocess.substring(0,notprocess.length()-1);
        }else{
            nonop=notprocess;
        }
        
        spd.setProcessdata(op);
        spd.setNonprocessdata(nonop);
        return spd;                         
    }
          
    @POST
    @Path("delMember")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData deleteMemberingroup(String delmemedata)
    {
        StringprocessData spd = new StringprocessData();
        String notprocess="";
        String output="";
        String groupname="";
        String memcds="";
        String memcode[]={};
        
        String datatype = delmemedata.substring(0,1);
        if(datatype.equals("{")){
             stringintojson = new ConvertStringIntoJson();
             JSONObject jsonobj = stringintojson.convertTOJson(delmemedata);
             groupname = jsonobj.getString("groupname");
             memcds = jsonobj.getString("memcd");
             memcode=memcds.split(",");
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(delmemedata);
                groupname = stringintoxml.getdatafromxmltag(doc,"groupname");
                memcds = stringintoxml.getdatafromxmltag(doc,"memcd");
                memcode=memcds.split(",");
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }
        
        List<MGroup> limg = null;
        List<TGroupIssue> liissuedetails = null;
        List<TGroupIssue> ligettransaction = null;
        int skip=0;
        int groupid=0;
        int skipprocess=0;
        int memcount=0;
        int grouptransaction=0;
        int notremoverfirst=0;
        int deletegroup=0;
        
        Query q;
        String sql="select * from m_group where m_group.GroupName='"+groupname+"' and status='A'";
        q = em.createNativeQuery(sql, MGroup.class);
        limg = q.getResultList();
        if(!(limg.size()>0)){
            skip=1;
        }else{
            groupid = limg.get(0).getMGroupPK().getGroupID();
            liissuedetails = groupIssueFacadeREST.checknumberofissuebygroup(String.valueOf(groupid));
            ligettransaction = groupIssueFacadeREST.findBy("findByGroupID", String.valueOf(groupid));
            memcount = limg.size();
            if(liissuedetails.size()>0){
                skipprocess=1;
            }
            if(ligettransaction.size()>0){
                grouptransaction=1;
            }
        }
        
        
        if(skip==0 && skipprocess==0){
                String remainmember;
                List<Integer> count=new ArrayList();
                  
                    for(int i=0;i<memcode.length;i++){
                        for(int j=0;j<limg.size();j++){
                            String memcd =  limg.get(j).getMGroupPK().getMemCd();
                            if(memcd.equals(memcode[i])){
                                count.add(i);
                            }
                        }
                    }
                if(grouptransaction==1){
                    if(memcount==count.size()){
                        System.out.println("Old member List : "+ memcode.length);
                        notremoverfirst=1;
                        remainmember = memcode[count.get(0)];
                        memcode = ArrayUtils.remove(memcode, count.get(0));
                        System.out.println("New member List : "+ memcode.length);
                    }
                }//else{
//                    if(memcount==count.size()){
//                        deletegroup=1;
//                    }
//                }
                
                for(int i=0;i<memcode.length;i++){
                    int pass=0;
                    for(int j=0;j<limg.size();j++){
                        String memcd =  limg.get(j).getMGroupPK().getMemCd();
                        if(memcd.equals(memcode[i])){
                            String delmemsql = "delete from m_group where GroupName='"+groupname+"' and mem_cd='"+memcode[i]+"'";
                            Query q2 = em.createNativeQuery(delmemsql);
                            q2.executeUpdate();
                            pass=1;
                            output +=memcode[i]+" member delete,";
                        }
                    }
                    if(pass==0){
                        notprocess+=memcode[i]+" member not belong to this group,";
                    }
                }
                //update remain member srno
                List<MGroup> limg2=null;
                Query q2;
                String sql2="select * from m_group where m_group.GroupName='"+groupname+"'  ORDER BY `m_group`.`SrNo` ASC";
                q2 = em.createNativeQuery(sql2, MGroup.class);
                limg2 = q2.getResultList();
                if(limg.size()>0){
                    int srno=1;
                    for(int k=0;k<limg2.size();k++){
                        MGroup mg = limg2.get(k);
                        mg.setSrNo(srno);
                        srno++;
                        edit(mg);
                    }
                }
                
                if(notremoverfirst==1){
                    Query q1;
                    String sql1="update m_group set status='S' where GroupID='"+String.valueOf(groupid)+"'";
                    q1 = em.createNativeQuery(sql1);
                    int stattusofsql = q1.executeUpdate();
                    System.out.println("stattusofsql : "+stattusofsql);
                    output = "Group Deleted ..";
                }
//                if(deletegroup==1){
//                    removeByid("RemoveByGroupID", String.valueOf(groupid));
//                    output = "Group Deleted ..";
//                }
                   
        }else{
            if(skipprocess==1){
                notprocess="Group has issue book !.";
            }else{
                notprocess="Group not available !.";
            }
            
        }
                
                
        String op;
        String nonop;
        if(output.length()>1){
            op = output.substring(0,output.length()-1);
        }else{
            op=output;
        }
        if(notprocess.length()>1){
            nonop = notprocess.substring(0,notprocess.length()-1);
        }else{
            nonop=notprocess;
        }
        
        spd.setProcessdata(op);
        spd.setNonprocessdata(nonop);
        return spd;                          
    }
    
//    public boolean checkMember(String memCd) {
//        String output;
//        int count = 0;
//        try{
//            member = mMemberFacadeREST.find(memCd);
//        count = member.toString().length();
//        }catch(NullPointerException e){
//            return false;
//        }
//        
//        System.out.println("Count:"+count);
//        if(count > 0)
//            return true;
//        else       
//            return false;
//    }
    
//    public boolean checkMemberinGroup(String memCd) {
//        mgroupCount = findBy("countByMemcd",memCd);
//        int count = mgroupCount.size();       
//        System.out.println("Count:"+count);
//        if(count > 0)
//            return false;
//        else       
//            return true;
//    }
//        //Added Manually to generate groupId
//    public static int getUniqueEmpId() {
//        groupID = groupID + 1;
//        return groupID;
//    }
    
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
    
    
    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MGroup entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.circulation.MGroupPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public MGroup find(@PathParam("id") PathSegment id) {
        soul.circulation.MGroupPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MGroup> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MGroup> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<MGroup> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findByGroupName":         valueList.add(valueString[0]);    
                                            break;
            case "findByMemCd":         valueList.add(valueString[0]);    
                                            break;                                
            case "findByGroupIDandMemcd":         valueList.add(Integer.parseInt(valueString[0]));
                                                    valueList.add(valueString[1]);
                                            break;
            case "findByGroupID":           valueList.add(Integer.parseInt(valueString[0]));    
                                            break;
            case "RemoveByGroupID":         valueList.add(Integer.parseInt(valueString[0]));    
                                            break;
            case "findWithMaxSrNo":         valueList.add(Integer.parseInt(valueString[0]));
                                            System.out.println("findWithMaxSrNo:"+valueString[0]);
                                            break;
            case "getMaxSrNo":              valueList.add(Integer.parseInt(valueString[0]));
                                            System.out.println("getMaxSrNo:"+valueString[0]);
                                            break;
            default:    valueList.add(valueString[0]);
                //used for findByGroupName, findWithMaxSrNo, RemoveByMemCd, 
                        break;
        }
        return super.findBy("MGroup."+query, valueList);
    }
    
    @DELETE
    @Path("removeBy/{namedQuery}/{values}")
    public void removeByid(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "RemoveByGroupID":         valueList.add(Integer.parseInt(valueString[0]));    
                                            break;
            default:    valueList.add(valueString[0]);
                //used for RemoveByMemCd, 
                        break;
        }
        super.removeBy("MGroup."+query, valueList);
    }
    
    
    //Will delete entire group with same id 
   
   
    
    //Added manually
    //Thsi method will return the groups 
    @GET
    @Path("AllGroups")
    @Produces({"application/xml", "application/json"})
    public List<MGroup> findAllGroups(){
        return super.findAll();
    }
    
    //Added manually
    @GET
    @Path("GroupDetails/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<MGroup> GroupDetailsByName(@PathParam("searchParameter") String param){
        List<MGroup> group  = findBy("findByGroupName", param);
        return group;
    }
    
    @GET
    @Path("findAllByGroupName")
    @Produces({"application/xml", "application/json"})
    public List<MGroup> findAllByGroupName(){
         String valueString  = null;
         List<Object> valueList = new ArrayList<>();
       //  valueList.add(valueString);
         return super.findBy("MGroup.findAllByGroupName" , valueList);
    }
    
    
    /*try {
            mgroupList = findBy("findByGroupIDandMemcd", groupId + "," + mcode);
            mgroup = mgroupList.get(0);
            System.out.println("D: "+mgroup);
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Member is already part of the group";
        }*/
}
