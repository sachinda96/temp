/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

//import ExceptionService.DataException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
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
import javax.ws.rs.core.PathSegment;
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.catalogue.Location;
import soul.circulation.MMember;
import soul.circulation.service.MMemberFacadeREST;
import soul.catalogue.service.LocationFacadeREST;
import soul.catalogue.service.MBkstatusFacadeREST;
import soul.circulation.Bookbackmemberissueinfo;
import soul.circulation.Bookbankmeminfo;
import soul.circulation.TBookbankissuereturn;
import soul.circulation.TBookbankissuereturnPK;
import soul.circulation_master.MType;
import soul.response.StringprocessData;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;
import soul.util.function.DateNTimeChange;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.tbookbankissuereturn")
public class TBookbankissuereturnFacadeREST extends AbstractFacade<TBookbankissuereturn> {
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private MMemberFacadeREST mMemberFacadeREST;
    @EJB
    private MBkstatusFacadeREST mBkStatusFacadeRest;
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    ConvertStringIntoJson stringintojson;
    ConvertStringIntoXml stringintoxml;
    Date todaydate = new Date();
//    Location location;
//    Location location1;
//    String loc;
//    String output;
//    String bookStatus;
//    String member;
//    TBookbankissuereturn localMember;
//    TBookbankissuereturn bbissuereturn;
//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private TBookbankissuereturnPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;memCd=memCdValue;accNo=accNoValue;issueDt=issueDtValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.circulation.TBookbankissuereturnPK key = new soul.circulation.TBookbankissuereturnPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> memCd = map.get("memCd");
        if (memCd != null && !memCd.isEmpty()) {
            key.setMemCd(memCd.get(0));
        }
        java.util.List<String> accNo = map.get("accNo");
        if (accNo != null && !accNo.isEmpty()) {
            key.setAccNo(accNo.get(0));
        }
        java.util.List<String> issueDt = map.get("issueDt");
        if (issueDt != null && !issueDt.isEmpty()) {
            try {
                key.setIssueDt(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(issueDt.get(0)));
            } catch (ParseException ex) {
                Logger.getLogger(TBookbankissuereturnFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }

    public TBookbankissuereturnFacadeREST() {
        super(TBookbankissuereturn.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TBookbankissuereturn entity) {
        super.create(entity);
    }
    
    @GET
    @Path("getmeminfo/{memcd}")
    @Produces({"application/xml", "application/json"})
    public List<Bookbankmeminfo> getmemberdetail(@PathParam("memcd") String memcd){
        MMember m;
     
        List<Bookbankmeminfo> libbmeminfo=null;
        m = mMemberFacadeREST.find(memcd);
        if(m!=null){
            Query query;
            String q="select m_member.mem_cd as memcd,concat(m_member.mem_firstnm,' ',m_member.mem_lstnm) as memname,m_member.mem_status as memstatus,m_ctgry.ctgry_desc as memctgry,m_fcltydept.Fclty_dept_dscr as memdept,m_type.MemberType as memtype,m_type.MaximumBooks as maxbookallow,m_type.MaximumMoney as maxamtallow,m_type.EndDate as duedt,COUNT(t_bookbankissuereturn.mem_cd) as totalitemissue from m_member,m_type,m_fcltydept,m_ctgry,t_bookbankissuereturn where m_member.mem_ctgry=m_ctgry.ctgry_cd and m_member.mem_dept=m_fcltydept.Fclty_dept_cd and m_fcltydept.Fld_tag='D' and m_member.mem_type=m_type.MemberType and m_member.mem_cd=t_bookbankissuereturn.mem_cd and t_bookbankissuereturn.status='BB' and m_member.mem_cd='"+m.getMemCd()+"'";
            query = getEntityManager().createNativeQuery(q,Bookbankmeminfo.class);
            libbmeminfo = query.getResultList();
            
        }else{
            libbmeminfo = new ArrayList<>();
        }
        return libbmeminfo;
    }
    
    @GET
    @Path("getmemberissueinfo/{memcd}")
    @Produces({"application/xml", "application/json"})
    public List<Bookbackmemberissueinfo> getmemberissuedetail(@PathParam("memcd") String memcd){
        MMember m;
     
        List<Bookbackmemberissueinfo> libbmeminfo=null;
        m = mMemberFacadeREST.find(memcd);
        if(m!=null){
            Query query;
            String q="select DISTINCT b1.FValue as Bookname,b2.FValue as author,t.acc_no as accno,t.issue_dt as issuedt,t.due_dt as duedt,t.user_cd as usercd,t.mem_cd as memcd,l.Price as price from biblidetails b left JOIN biblidetails b1 on b.RecID=b1.RecID and b1.Tag='245' and b1.SbFld='a' left join  biblidetails b2 on b1.RecID=b2.RecID and (b2.Tag='100' or b2.Tag='700') and b2.SbFld='a' left join location l on l.RecID=b.RecID INNER join t_bookbankissuereturn t on l.p852=t.acc_no where t.mem_cd='"+m.getMemCd()+"' and t.status='BB'";
            query = getEntityManager().createNativeQuery(q,Bookbackmemberissueinfo.class);
            libbmeminfo = query.getResultList();
            
        }else{
            libbmeminfo = new ArrayList<>();
        }
        return libbmeminfo;
    }

        //added manually
    @POST
    @Path("bookbankissue")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData issue(String bbissuedata)
    {   
        StringprocessData spd=new StringprocessData();
        String output="";
        String notprocess="";
        Bookbankmeminfo bookbankmeminfo;
        List<Bookbankmeminfo> limembbinfo = null;
        List<Location> liloc=null;
        TBookbankissuereturn tbbissue;
        DateNTimeChange dt = new DateNTimeChange();
        TBookbankissuereturnPK tbbpk;
        MMember m;
        Location ln=null;
        String memcode="";
        String bookinfo="";
        String memduedt="";
        int issueitemamt=0;
        int totalmemissueamt=0;
        
         String datatype = bbissuedata.substring(0,1);
         
        if(datatype.equals("{")){
            stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(bbissuedata);
            memcode = jsonobj.getString("memcd");
            bookinfo = jsonobj.getString("accnoprice");
            memduedt = jsonobj.getString("duedt");
            issueitemamt = Integer.valueOf(jsonobj.getString("issueamt"));
            totalmemissueamt = Integer.valueOf(jsonobj.getString("totalamt"));
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(bbissuedata);
                issueitemamt = Integer.valueOf(stringintoxml.getdatafromxmltag(doc,"issueamt"));
                totalmemissueamt = Integer.valueOf(stringintoxml.getdatafromxmltag(doc,"totalamt"));
                memcode = stringintoxml.getdatafromxmltag(doc,"memcd");
                bookinfo = stringintoxml.getdatafromxmltag(doc,"accnoprice");
                memduedt = stringintoxml.getdatafromxmltag(doc,"duedt");   
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }
        
        
        int notptransaction=0;
        String booklist[] = bookinfo.split(",");
        m = mMemberFacadeREST.find(memcode);
        int memskip=0;
        if(m.getMemStatus().equals("S")){
            memskip=1;
        }
       
        if(m!=null && memskip==0){
            limembbinfo = getmemberdetail(m.getMemCd());
            if(limembbinfo.size()>0){
                bookbankmeminfo = limembbinfo.get(0);
                Double maxamt = Double.valueOf(bookbankmeminfo.getMaxamtallow());
                Double maxbook = Double.valueOf(bookbankmeminfo.getMaxbookallow());
                int totalitemissue = Integer.valueOf(bookbankmeminfo.getTotalitemissue());
                
                if(maxamt==0 && maxbook==0){
                     notprocess += "Member not able to do this transaction !.";
                     notptransaction=1;
                }
                if(totalmemissueamt>maxamt){
                       notprocess += "Member has crossed total price limit !.";
                       notptransaction=1;
                }
                if(totalitemissue>maxbook){
                        notprocess += " Member has crossed total book limit !.";
                        notptransaction=1;
                } 
                if(maxamt>0 && maxbook>0){
                   if(notptransaction==0){
                       Double remainamt = maxamt-totalmemissueamt;
                       Double remainbook = maxbook-totalitemissue;
                       Double bookcount=0.0;
                       for(int i=0;i<booklist.length;i++){
                           String accnoNprice[] = booklist[i].split("-");
                           String accno = accnoNprice[0];
                           int bookprice = Integer.valueOf(accnoNprice[1]);
                           int skip=0;
                           liloc=null;
                           liloc = locationFacadeREST.getByAcc(accno);
                           if(liloc.size()>0){
                               ln = liloc.get(0);
                               if(ln.getStatus().equals("AV") && ln.getIssueRestricted().equals("N")){
                                   skip=0;
                               }else{
                                   notprocess += accno+",";
                                   skip=1;
                               }
                           }else{
                                notprocess += accno+",";
                                skip=1;
                           }
                           if(remainamt<bookprice){
                               notprocess += accno+" Member has crossed total price limit,";
                           }
                           if(remainbook<=bookcount){
                               notprocess += accno+" Member has crossed total book limit,";
                           }
                           
                           if(remainamt>bookprice && remainbook>bookcount && skip==0){
                               remainamt = remainamt-bookprice;
                               bookcount++;
                               tbbpk = new TBookbankissuereturnPK();
                               tbbpk.setAccNo(accno);
                               tbbpk.setIssueDt(todaydate);
                               tbbpk.setMemCd(m.getMemCd());
                               
                               tbbissue = new TBookbankissuereturn();
                               tbbissue.setTBookbankissuereturnPK(tbbpk);
                               tbbissue.setAccprice(BigDecimal.valueOf(Double.valueOf(String.valueOf(bookprice))));
                               tbbissue.setDueDt(dt.getDateNTimechange(memduedt));
                               tbbissue.setUserCd("Superuser");
                               tbbissue.setStatus("BB");
                               
                               create(tbbissue);
                               
                               ln.setStatus("BB");
                               locationFacadeREST.edit(ln);
                               
                               output+= accno+",";
                               
                           }
                       }
                   }
                }
            }else{
                notprocess += "Member Type is not exists !.";
            }
        }else{
            if(memskip==1){
                notprocess += "Member is suspended !.";
            }else{
                notprocess += "Member is not exists !.";
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
    
        
    @POST
    @Path("return")
    @Consumes({"application/xml", "application/json"})
    public StringprocessData returnIssue(String issueData)
    {    
        StringprocessData spd = new StringprocessData();
        String output="";
        String notprocess="";
        MMember m;
        Location ln;
        List<Location> liloc=null;
        Bookbackmemberissueinfo bbmemissue;
        List<Bookbackmemberissueinfo> libbmemissue =null;
        TBookbankissuereturnPK bookbankissuereturnPK;
        TBookbankissuereturn bookbankissuereturn;
        
        String datatype=issueData.substring(0,1);
        String memcode="";
        String accno="";
        String accnos[]={};
        if(datatype.equals("{")){
           stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(issueData);
             memcode = jsonobj.getString("memcd");
             accno = jsonobj.getString("accno");
             accnos = accno.split(",");
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(issueData);
                memcode = stringintoxml.getdatafromxmltag(doc,"memcd");
                accno = stringintoxml.getdatafromxmltag(doc,"accno");
                accnos = accno.split(",");
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }    
        
        m = mMemberFacadeREST.find(memcode);
        int memskip=0;
        if(m.getMemStatus().equals("S")){
            memskip=1;
        }
        if(m!=null && memskip==0){
          
            libbmemissue = getmemberissuedetail(m.getMemCd());
           
            for(int i=0;i<accnos.length;i++){
                liloc= null;
                liloc= locationFacadeREST.getByAcc(accnos[i]);
                int op=0;
                if(liloc.size()>0){
                    ln=null;
                    ln= liloc.get(0);
                    if(ln.getStatus().equals("BB")){
                        for(int j=0;j<libbmemissue.size();j++){
                            bbmemissue = null;
                            bbmemissue=libbmemissue.get(j);
                            if(bbmemissue.getAccno().equals(accnos[i]) && m.getMemCd().equals(bbmemissue.getMemcd())){
                                bookbankissuereturnPK = new TBookbankissuereturnPK();
                                bookbankissuereturnPK.setAccNo(accnos[i]);
                                bookbankissuereturnPK.setMemCd(m.getMemCd());
                                bookbankissuereturnPK.setIssueDt(bbmemissue.getIssuedt());
                                
                                bookbankissuereturn=null;
                                bookbankissuereturn=super.find(bookbankissuereturnPK);
                                bookbankissuereturn.setStatus("AV");
                                bookbankissuereturn.setReturnDt(todaydate);
                                bookbankissuereturn.setReturnUserCd("Superuser");
                                
                                edit(bookbankissuereturn);
                                
                                ln.setStatus("AV");
                                locationFacadeREST.edit(ln);
                                
                                op=1;
                            }
                        }
                    }
                }
                
                if(op==1){
                    output+=accnos[i]+",";
                }else{
                     notprocess+=accnos[i]+",";
                }
                
            }
        }else{
            if(memskip==1){
                notprocess="Member is suspended !.";
            }else{
                notprocess="Member not exists !.";
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
    
   
    
    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TBookbankissuereturn entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.circulation.TBookbankissuereturnPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public TBookbankissuereturn find(@PathParam("id") PathSegment id) {
        soul.circulation.TBookbankissuereturnPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TBookbankissuereturn> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TBookbankissuereturn> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<TBookbankissuereturn> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch (query) {
            case "findByMemCd":
                valueList.add(valueString[0]);
                break;
            case "findByMemCdAndIssuedandAccNo":
                 valueList.add(valueString[0]);
                 valueList.add(valueString[1]);
                break;
            default:
                 System.out.println("valueString[0]: "+valueString[0]);
                valueList.add(valueString[0]);
              //  valueList.add(valueString[1]);
               
               // System.out.println("valueString[1]: "+valueString[1]);
                //used for findByMemCdAndIssued 
                break;
        }
        return super.findBy("TBookbankissuereturn."+query, valueList);
    }
    
    @GET
    @Path("findByMemCdAndAccNoAndIssueDt/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public TBookbankissuereturn findByMemCdAndAccNoAndIssueDt(@PathParam("namedQuery") String query, @PathParam("values") String values){
         String[] valueString = values.split(",");
        System.out.println("valueString[0] "+valueString[0]+"  "+valueString[1]+"  "+valueString[2]);
         List<Object> valueList = new ArrayList<>();
             valueList.add(valueString[0].trim());
             valueList.add(valueString[1].trim()); 
           
             SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             SimpleDateFormat dateFormat1 =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
             Date t ;
             java.sql.Timestamp sq ;
              try
              {
                    t = dateFormat.parse(dateFormat.format(dateFormat1.parse(valueString[2])));
                    System.out.println(dateFormat.parse(dateFormat.format(dateFormat1.parse(valueString[2].trim()))));
                    sq = new java.sql.Timestamp(t.getTime());
             
                  valueList.add(sq);
              }
              catch (ParseException ex) 
              {
                  Logger.getLogger(TBookbankissuereturnFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
              }
              System.out.println(valueList);
            return super.findBy("TBookbankissuereturn."+query, valueList).get(0);
    }
    
    
    //This method will show the history of items issued by member and issued status of member in dates between
    //Member/item issue return hostory
    @GET
    @Path("BookBankIssueReturn/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> IssueReturnHistory(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
                
            case "byIssueDateBetween":
                //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                q = "SELECT t_bookbankissuereturn.mem_cd, t_bookbankissuereturn.acc_no, t_bookbankissuereturn.issue_dt, t_bookbankissuereturn.accprice,\n" 
                        + " t_bookbankissuereturn.due_dt, Biblidetails.FValue,   m_member.mem_firstnm, m_member.mem_lstnm, m_member.mem_type from t_bookbankissuereturn\n" 
                        + " left outer join m_member on t_bookbankissuereturn.mem_cd = m_member.mem_cd \n"
                        + " join location on t_bookbankissuereturn.acc_no = location.p852\n" 
                        + " join Biblidetails on location.RecID = Biblidetails.RecID\n"
                        + " Where t_bookbankissuereturn.issue_dt between '" + valueString[0] + "' and '" + valueString[1] + "' and biblidetails.Tag = '245' and biblidetails.SbFld = 'a' ";
               break;
            
               
            case "byReturnDateBetween":
                q = "SELECT t_bookbankissuereturn.mem_cd, t_bookbankissuereturn.acc_no, t_bookbankissuereturn.issue_dt, t_bookbankissuereturn.accprice,\n" 
                        + " t_bookbankissuereturn.due_dt, Biblidetails.FValue,   m_member.mem_firstnm, m_member.mem_lstnm, m_member.mem_type from t_bookbankissuereturn\n" 
                        + " left outer join m_member on t_bookbankissuereturn.mem_cd = m_member.mem_cd \n"
                        + " join location on t_bookbankissuereturn.acc_no = location.p852\n" 
                        + " join Biblidetails on location.RecID = Biblidetails.RecID\n"
                        + " Where t_bookbankissuereturn.return_dt between '" + valueString[0] + "' and '" + valueString[1] + "' and biblidetails.Tag = '245' and biblidetails.SbFld = 'a' ";
               break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
}
