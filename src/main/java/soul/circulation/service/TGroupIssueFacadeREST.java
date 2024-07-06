/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import soul.catalogue.Location;
import soul.catalogue.service.LocationFacadeREST;
import soul.circulation.MGroup;
import soul.circulation.MMember;
import soul.circulation.TGroupIssue;
import soul.circulation.TGroupIssuePK;
import soul.circulation.TMemdue;
import soul.circulation.TMemfine;
import soul.response.StringprocessData;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;
import soul.util.function.DateNTimeChange;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.tgroupissue")
public class TGroupIssueFacadeREST extends AbstractFacade<TGroupIssue> {

    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private MGroupFacadeREST mGroupFacadeREST;
    @EJB
    private MMemberFacadeREST memberFacadeREST;
    @EJB
    private TMemdueFacadeREST tMemdueFacadeREST;
    @EJB
    private TMemfineFacadeREST tMemfineFacadeREST;
    @EJB
    private TIssueFacadeREST tIssueFacadeREST;
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    ConvertStringIntoJson stringintojson;
    ConvertStringIntoXml stringintoxml;
    Date todayDate = new Date();
    DateNTimeChange dt;
//    String output;
//    Location location;
//    TGroupIssue returnbook = null;
//    private String[] accNos;
//    List<Location> locationList;
//    List<TGroupIssue> returnList;

    private TGroupIssuePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;groupID=groupIDValue;accNo=accNoValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.circulation.TGroupIssuePK key = new soul.circulation.TGroupIssuePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> groupID = map.get("groupID");
        if (groupID != null && !groupID.isEmpty()) {
            key.setGroupID(new java.lang.Integer(groupID.get(0)));
        }
        java.util.List<String> accNo = map.get("accNo");
        if (accNo != null && !accNo.isEmpty()) {
            key.setAccNo(accNo.get(0));
        }
        java.util.List<String> issDt = map.get("iss_dt");
        if (issDt != null && !issDt.isEmpty()) {
            key.setIssDt(new java.util.Date(issDt.get(0)));
        }
        return key;
    }

    public TGroupIssueFacadeREST() {
        super(TGroupIssue.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TGroupIssue entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TGroupIssue entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.circulation.TGroupIssuePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public TGroupIssue find(@PathParam("id") PathSegment id) {
        soul.circulation.TGroupIssuePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TGroupIssue> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("numberofissuebygroup/{groupid}")
    @Produces({"application/xml", "application/json"})
    public List<TGroupIssue> checknumberofissuebygroup(@PathParam("groupid") String groupid) {
        
        List<TGroupIssue> litgissue=null;
        Query q;
        String sql="SELECT * FROM `t_group_issue` where status='GR' and GroupID='"+groupid+"'";
        q = em.createNativeQuery(sql, TGroupIssue.class);
        litgissue = q.getResultList();
        if(!(litgissue.size()>0)){
            litgissue = new ArrayList();
        }
        
        return litgissue;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TGroupIssue> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    @DELETE
    @Path("removeBy/{namedQuery}/{values}")
    public void removeBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch (query) {
            case "removeByAccNo":
                valueList.add(valueString[0]);
                break;
            default:
                valueList.add(valueString[0]);
                break;
        }
        super.removeBy("TGroupIssue." + query, valueList);
    }

    
    @POST
    @Path("Issue")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData Issue(String issuesata) {
        System.err.println("issuesata : "+ issuesata);
        StringprocessData spd = new StringprocessData();
        String output="";
        String notprocess="";
        Location ln;
        List<Location> liloc;
        List<MGroup> limemgroup=null;
        int groupid=0;
        String accnos="";
        String duedt="";
        String accno[]={};
        String datatype = issuesata.substring(0,1);
        
        if(datatype.equals("{")){
            stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(issuesata);
            groupid = Integer.valueOf(jsonobj.getString("groupid"));
             accnos = jsonobj.getString("accno");
             duedt = jsonobj.getString("duedt");
             accno = accnos.split(",");
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(issuesata);
                groupid = Integer.valueOf(stringintoxml.getdatafromxmltag(doc,"groupid"));
                accnos = stringintoxml.getdatafromxmltag(doc,"accno");
                duedt = stringintoxml.getdatafromxmltag(doc,"duedt");
                accno = accnos.split(",");
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }
        
        int skip=0;
        dt = new DateNTimeChange();

        limemgroup = mGroupFacadeREST.findBy("findByGroupID", String.valueOf(groupid));
        
        if(!(limemgroup.size()>0)){
            skip=1;
        }
        
        if(skip==0){
            for(int i=0;i<accno.length;i++){
                liloc=null;
                liloc= locationFacadeREST.getByAcc(accno[i]);
                if(liloc.size()>0){
                    ln=null;
                    ln = liloc.get(0);
                    if(ln.getStatus().equals("AV") && ln.getIssueRestricted().equals("N")){
                        TGroupIssuePK groupIssuePK = new TGroupIssuePK();
                        groupIssuePK.setAccNo(accno[i]);
                        groupIssuePK.setGroupID(groupid);
                        groupIssuePK.setIssDt(todayDate);
                        
                        TGroupIssue groupIssue = new TGroupIssue();
                        groupIssue.setBookStatus("GR");
                        groupIssue.setDueDt(dt.getDateNTimechange(duedt));
                        groupIssue.setUsercd("Useruser");
                        groupIssue.setTGroupIssuePK(groupIssuePK);
                        
                        create(groupIssue);
                        
                        ln.setStatus("GR");
                        locationFacadeREST.edit(ln);
                        
                        output += accno[i]+" Book issue.,";
                        
                    }else{
                        notprocess+=accno[i]+" Book not available.,";
                    }
                }else{
                    notprocess="Book not exists..";
                }
            }
        }else{
             notprocess="Group not available..";
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
    @Path("Return")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData Return(String bookdata) {
        StringprocessData spd = new StringprocessData();
        String output="";
        String notprocess="";
        String groupid="";
        String accno ="";
        String datatype = bookdata.substring(0,1);
        
        if(datatype.equals("{")){
            stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(bookdata);
             groupid = jsonobj.getString("groupid");
             accno = jsonobj.getString("accno");
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(bookdata);
                groupid = stringintoxml.getdatafromxmltag(doc,"groupid");
                accno = stringintoxml.getdatafromxmltag(doc,"accno");
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }
        int skip=0;
        String accnos[] = accno.split(",");
        String sqli ="select * from m_group g where g.GroupID='"+groupid+"' and g.SrNo='1'";
        Query q1 = em.createNativeQuery(sqli, MGroup.class);
        List<MGroup> limgroup = q1.getResultList();
        String memcode = "";
        String groupstatus="";
        String memcatcode="";
        List<TGroupIssue> litgroupissue =null;
        if(limgroup.size()>0){
            memcode=limgroup.get(0).getMMember().getMemCd();
            groupstatus = limgroup.get(0).getStatus();
            memcatcode = limgroup.get(0).getMMember().getMemCtgry().getCtgryCd();
            if(groupstatus.equals("A")){
                skip=0;
                String sql="select * from t_group_issue t where t.status='GR' and t.GroupID='"+groupid+"'";
                Query q = em.createNativeQuery(sql, TGroupIssue.class);
                litgroupissue= q.getResultList();
                if(!(litgroupissue.size()>0)){
                    skip=2;
                }
            }else{
                skip=1;
            }
        }
        if(skip==0){
            for(int i=0;i<accnos.length;i++){
                List<Location> liloc=null;
                liloc=locationFacadeREST.getByAcc(accnos[i]);
                if(liloc.size()>0){
                    Location ln = null;
                    ln = liloc.get(0);
                    if(ln.getStatus().equals("GR")){
                        int matchaccno=0;
                        for(int j=0;j<litgroupissue.size();j++){
                            if(litgroupissue.get(j).getTGroupIssuePK().getAccNo().equals(accnos[i])){
                                matchaccno=1;
                                Date duedt = litgroupissue.get(j).getDueDt();
                                if(duedt.before(todayDate)){
                                    BigDecimal fineamt = tIssueFacadeREST.getdueofbook(memcatcode, ln.getMaterial().getCode(),duedt);
                                    if(fineamt.compareTo(BigDecimal.valueOf(Double.valueOf("0.00")))==1){
                                        // paylater and retun book
                                        //due enter in fine table
                                        TMemfine fine = new TMemfine();
                                        fine.setAccnNo(accnos[i]);
                                        fine.setFineAmt(fineamt);
                                        fine.setMemCd(memcode);
                                        fine.setIssDt(litgroupissue.get(j).getTGroupIssuePK().getIssDt());
                                        fine.setRetDt(duedt);
                                        fine.setSlipDt(todayDate);
                                        fine.setUserCd("Superuser");
                                        fine.setFineDesc("Group issue OverDue");
                                        
                                        fine = tMemfineFacadeREST.createAndGet(fine);
                                        
                                        // due enter in memdue table
                                        TMemdue due = new TMemdue();
                                        due.setDueAmt(fine.getFineAmt());
                                        due.setMMember(limgroup.get(0).getMMember());
                                        due.setTMemfine(fine);
                                        due.setSlipDt(fine.getSlipDt());
                                        due.setSlipNo(fine.getSlipNo());
                                        tMemdueFacadeREST.create(due);
                                        
                                        ln.setStatus("AV");
                                        locationFacadeREST.edit(ln);
                                        
                                        TGroupIssue tissuegroup = litgroupissue.get(j);
                                        tissuegroup.setBookStatus("AV");
                                        tissuegroup.setReturnDt(todayDate);
                                        
                                        edit(tissuegroup);
                                        
                                        output += accnos[i]+"-"+fineamt.toString()+"-"+fine.getSlipNo()+" book return to library,";
                                    } else {
                                        // return book
                                        ln.setStatus("AV");
                                        locationFacadeREST.edit(ln);

                                        TGroupIssue tissuegroup = litgroupissue.get(j);
                                        tissuegroup.setBookStatus("AV");
                                        tissuegroup.setReturnDt(todayDate);

                                        edit(tissuegroup);

                                        output += accnos[i] + " book return to library,";
                                    }
                                }else{
                                    // return book
                                    ln.setStatus("AV");
                                    locationFacadeREST.edit(ln);

                                    TGroupIssue tissuegroup = litgroupissue.get(j);
                                    tissuegroup.setBookStatus("AV");
                                    tissuegroup.setReturnDt(todayDate);

                                    edit(tissuegroup);

                                    output += accnos[i]+" book return to library,";
                                }
                            }
//                            else{
//                                notprocess+= accnos[i]+" Book is not belong to this group.,";
//                            }
                        }
                        if(matchaccno==0){
                             notprocess+= accnos[i]+" Book is not belong to this group.,";
                        }
                    }else{
                        notprocess+= accnos[i]+" status is not Group issue.,";
                    }
                }else{
                    notprocess+= accnos[i]+" is not available.,";
                }
            }
            
        }else{
            if(skip==2){
                notprocess="Group has no issue item..";
            }else if(skip==1){
                notprocess="Group is not available..";
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

  
    public List<TGroupIssue> findByGroupIdAndAccNo(String group, String accNo) {
        List<Object> valueList = new ArrayList<>();

        valueList.add(Integer.parseInt(group));
        valueList.add(accNo);
        return super.findBy("TGroupIssue.findByGroupIdAndAccNo", valueList);
    }

    //Added Manually
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<TGroupIssue> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        //List<String> inString = new ArrayList<>();
        List<String> inString = new ArrayList<>();

        switch (query) {
            case "findByGroupIdAndAccNo":       //inString.addAll(Arrays.asList(values.split(",")));
                //valueList.add(inString);
                valueList.add(Integer.parseInt(valueString[0]));
                valueList.add(valueString[1]);
                break;
            case "findByGroupID":
                    valueList.add(Integer.parseInt(valueString[0]));
                    break;
            default:
                valueList.add(valueString[0]);
                break;

        }
        System.out.println("DTGroupIssue." + query + valueList);
        return super.findBy("TGroupIssue." + query, valueList);
    }

  
}
