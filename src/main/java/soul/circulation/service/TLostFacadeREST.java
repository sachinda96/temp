/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.math.BigDecimal;
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
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.catalogue.Location;
import soul.catalogue.service.LocationFacadeREST;
import soul.circulation.MMember;
import soul.circulation.TIssue;
import soul.circulation.TLost;
import soul.circulation.TLostPK;
import soul.circulation.TMemdue;
import soul.circulation.TMemfine;
import soul.circulation.TMemrcpt;
import soul.circulation.TReplace;
import soul.circulation.TReserve;
import soul.response.StringData;
import soul.response.StringprocessData;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.tlost")
public class TLostFacadeREST extends AbstractFacade<TLost> {
    @EJB
    private TIssueFacadeREST tIssueFacadeREST;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private TReplaceFacadeREST tReplaceFacadeREST;
    @EJB
    private TReserveFacadeREST treserveFacadeREST;
    @EJB
    private MMemberFacadeREST mMemberFacadeREST;
     @EJB
    private TMemdueFacadeREST tMemdueFacadeREST;
      @EJB
    private TMemfineFacadeREST tMemfineFacadeREST;
       @EJB
    private TMemrcptFacadeREST tMemrcptFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    Date todaydtae = new Date();
    ConvertStringIntoJson stringintojson;
    ConvertStringIntoXml stringintoxml;
  //  String output;
  //  TLost lost;
  //  TIssue issue;
  //  StringData sd;
  
    private TLostPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;memCd=memCdValue;accNo=accNoValue;repDt=repDtValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.circulation.TLostPK key = new soul.circulation.TLostPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> memCd = map.get("memCd");
        if (memCd != null && !memCd.isEmpty()) {
            key.setMemCd(memCd.get(0));
        }
        java.util.List<String> accNo = map.get("accNo");
        if (accNo != null && !accNo.isEmpty()) {
            key.setAccNo(accNo.get(0));
        }
        java.util.List<String> repDt = map.get("repDt");
        if (repDt != null && !repDt.isEmpty()) {
            try {
                key.setRepDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(repDt.get(0)));
            } catch (ParseException ex) {
                Logger.getLogger(TLostFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }

    public TLostFacadeREST() {
        super(TLost.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TLost entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TLost entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.circulation.TLostPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public TLost find(@PathParam("id") PathSegment id) {
        soul.circulation.TLostPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TLost> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TLost> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
 
    @GET
    @Path("byDate/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<TLost> byquery(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");       
        List<Object> valueList = new ArrayList<>();         
        switch(query)
        {
            case "findByRepDtBtwn": SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                                            try {
                                                valueList.add(dateFormat1.parse(valueString[0]));
                                                valueList.add(dateFormat1.parse(valueString[1]));
                                                        
                                            } catch (ParseException ex) {
                                                Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            break;
           case "findByAccNo":
                                     valueList.add(valueString[0]);
                                        break;
                                                 
            default:    valueList.add(valueString[0]);
                        break;
        }
        //System.out.println("TMemrcpt."+query + valueList);
        return super.findBy("TLost."+query, valueList);
    }
    
    @GET
    @Path("by/{namedQuery}")
    @Produces({"application/xml", "application/json"})
    public List<TLost> findBy(@PathParam("namedQuery") String query)
    {
     //   String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
            case "findAllNotProcessed": //No parameter to send
                                        break;
            default:    valueList.add("");
            //used 
        }
        return super.findBy("TLost."+query, valueList);
    }  
    
    //List of Documents
    //Returns all the details from t_lost table
    @GET
    @Path("ListOfDocumetns/{Query}")
    @Produces({"application/xml", "application/json"})
    public List<TLost> ListOfDocumetns(@PathParam("Query") String query)
    {
        List<TLost> lost  = findBy("findAllNotProcessed");
        return lost;
    }  
    
    @POST
    @Path("lost")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData lost(String lostdata)
    {   
        StringprocessData spd;
        String output="";
        String notprocess="";
        TIssue issue;
        List<TIssue> tissueli=null;
        Location location=null;
        List<Location> liloc=null;
        List<TReserve> trs;
        String accNo="";
        String datatype=lostdata.substring(0,1);
        
        if(datatype.equals("{")){
            stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(lostdata);
           
             accNo = jsonobj.getString("accNo");
             
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(lostdata);
                
                accNo = stringintoxml.getdatafromxmltag(doc,"accNo");
               
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }
        int pass=0;
        String[] accno = accNo.split(",");
        int recid;
        for(int i=0;i<accno.length;i++){
            liloc=null;
            liloc = locationFacadeREST.findBy("findByP852", accno[i]);
            if(liloc.size()>0){
                location =null;
                location = liloc.get(0);
            }else{
                location =null;
            }
            
            
            if(location!=null){
                if(location.getStatus().equals("IS") || location.getStatus().equals("IR")){
                    tissueli=tIssueFacadeREST.findBy("findByAccNo", accno[i]);
                    if(tissueli.size()>0){
                        issue=null;
                        issue = tissueli.get(0);
                    }else{
                        issue=null;
                    }
                   // issue = tIssueFacadeREST.findBy("findByAccNo", accno[i]).get(0);
                    recid = location.getLocationPK().getRecID();
                    if(issue!=null){
                        String memCd = issue.getMMember().getMemCd();
                        TLost lost = new TLost();
                        TLostPK lostPK = new TLostPK();
                        lostPK.setAccNo(accno[i]);
                        lostPK.setMemCd(memCd);
                        lostPK.setRepDt(new Date());
                        lost.setTLostPK(lostPK);

                        lost.setAmountRecover("N");
                        lost.setReplaceBook("N");
                        lost.setReason("LO");
                        lost.setRepResponsible("super user");   //Change To Dynamic

                        //1. Entry in lost
                        create(lost);

                        //2. Delete issue entry
                        tIssueFacadeREST.removeByMemcdAndAccNo(memCd,accNo);
                        pass=1;
                    }
                    trs = treserveFacadeREST.findBy("findByRecordNo", String.valueOf(recid));
                    if(trs.size()>0){
                        treserveFacadeREST.removebyRecid(recid);
                    }
                    
                    if(pass==1){
                        location.setStatus("LO");
                        locationFacadeREST.edit(location);
                        output += accno[i]+" Lost Successfully,";
                    }
                    
                    
                }else{
                    notprocess += accno[i]+" book status is not issue or Reserve,";
                }
            }
            else{
                notprocess += accno[i]+" book not available,";
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
        spd = new StringprocessData();
        spd.setProcessdata(op);
        spd.setNonprocessdata(nonop);
    
       return spd;
    }
    
    @POST
    @Path("replaceBook")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData replaceBook(String replacebookData) {
        String output="";
        String notprocess="";
        StringprocessData spd;
        List<TLost> lilost = null;
        List<Location> liloc =null;
        Location ln;
        TLost lost;
        MMember m;
        
        String oldaccno="";
        String memcd="";
        String title = "";
        String author = "";
        String callno = "";
        //String status = bookdata[3];
        String edition = "";
        String reason = "";
        //String bookdetails="";
        String datatype= replacebookData.substring(0,1);
        if(datatype.equals("{")){
            stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(replacebookData);
             oldaccno = jsonobj.getString("oldaccno");
             memcd = jsonobj.getString("memcd");
            // bookdetails = jsonobj.getString("bookdetails");
             title=jsonobj.getString("title");
             author=jsonobj.getString("author");
             callno=jsonobj.getString("callno");
             edition=jsonobj.getString("edition");
             reason=jsonobj.getString("reason");
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(replacebookData);
                oldaccno = stringintoxml.getdatafromxmltag(doc,"oldaccno");
                memcd = stringintoxml.getdatafromxmltag(doc,"memcd");
               // bookdetails = stringintoxml.getdatafromxmltag(doc,"bookdetails");
               title=stringintoxml.getdatafromxmltag(doc,"title");
               author=stringintoxml.getdatafromxmltag(doc,"author");
               callno=stringintoxml.getdatafromxmltag(doc,"callno");
               edition=stringintoxml.getdatafromxmltag(doc,"edition");
               reason=stringintoxml.getdatafromxmltag(doc,"reason");
               
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }
        
        //String[] bookdata = bookdetails.split(",");
        
        
        m = mMemberFacadeREST.find(memcd);
        liloc = locationFacadeREST.findBy("findByP852", oldaccno);
        if(liloc.size()>0 && m!=null){
            ln = liloc.get(0);
            if(ln.getStatus().equals("LO")){
                lilost = byquery("findByAccNo",oldaccno);
                if(lilost.size()>0){
                    int flg = 0;
                    for(int i=0;i<lilost.size();i++){
                        lost = lilost.get(i);
                        if(lost.getAmountRecover().equals("N") && lost.getReplaceBook().equals("N") && lost.getTLostPK().getMemCd().equals(m.getMemCd())){
                          //  int recid = ln.getLocationPK().getRecID();
                             TReplace replace = new TReplace();
                             replace.setOldAccno(oldaccno);
                             replace.setMemCd(m.getMemCd());
                             replace.setTitle(title);
                             replace.setAuthor(author);
                             replace.setCallNo(callno);
                             replace.setEdition(edition);
                             replace.setReason(reason);
                             
                             tReplaceFacadeREST.create(replace);
                             
                             lost.setReplaceBook("Y");
                             edit(lost);
                             
                             ln.setStatus("AV");
                             locationFacadeREST.edit(ln);
                             
                             flg=1;
                             
                             output = oldaccno+" replaceBook.";
                        }
                    }
                    if(flg==0){
                         notprocess=oldaccno+" not replaceBook.";
                    }
                }else{
                    notprocess=oldaccno+" book not available.";
                }
            }else{
                notprocess=oldaccno+" book status is not lost.";
            }
        }else{
            notprocess=oldaccno+" Somthing is worng in book or member.";
        }
        
        spd = new StringprocessData();
        spd.setProcessdata(output);
        spd.setNonprocessdata(notprocess);
        return spd;
    }
   
    
    @POST
    @Path("amountrecover")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData amountrecover(String amountrecoverdata){
        StringprocessData spd;
        String output="";
        String notprocess="";
        MMember m;
        Location ln;
        TMemfine tfine;
        TMemdue tmdue;
        TMemrcpt tmrcpt;
        TLost lost;
        List<TLost> lilost=null;
        List<Location> liloc=null;
//        List<TMemfine> litfine=null;
//        List<TMemdue> litmemdue=null;
//        List<TMemrcpt> litmrcpt=null;
        String datatype = amountrecoverdata.substring(0,1);
        String accno="";
        String memcode="";
        String slipdata="";
        
        if(datatype.equals("{")){
            stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(amountrecoverdata);
             accno = jsonobj.getString("accno");   
             memcode = jsonobj.getString("memcode");
             slipdata = jsonobj.getString("slipdata");
             
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(amountrecoverdata);
                accno = stringintoxml.getdatafromxmltag(doc,"accno");
                memcode = stringintoxml.getdatafromxmltag(doc,"memcode");
                slipdata = stringintoxml.getdatafromxmltag(doc,"slipdata");
              
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }

        String slipdatas[] = slipdata.split(",");    
        m = mMemberFacadeREST.find(memcode);
        liloc = locationFacadeREST.findBy("findByP852", accno);
        Double  finamt = Double.valueOf(slipdatas[0]);
        Double  recpamt = Double.valueOf(slipdatas[1]);
        Double dueamt = finamt - recpamt;
        String paymod = slipdatas[2];
        String transactionid = slipdatas[3];
        String bankname = slipdatas[4];
        String transactionref;
        
        if(paymod.equals("CASH")){
            transactionref = "CASH";
            bankname=" ";
        }else{
            transactionref = paymod+" : "+transactionid;
        }
        
        
        if(liloc.size()>0 && m!=null){
            ln = liloc.get(0);
            if(ln.getStatus().equals("LO")){
                lilost = byquery("findByAccNo",accno);
                if(lilost.size()>0){
                    int flg = 0;
                    for(int i=0;i<lilost.size();i++){
                        lost = lilost.get(i);
                        if(lost.getAmountRecover().equals("N") && lost.getReplaceBook().equals("N") && lost.getTLostPK().getMemCd().equals(m.getMemCd())){
                            tfine = new TMemfine();
                            tfine.setAccnNo(accno);
                            tfine.setMemCd(m.getMemCd());
                            tfine.setFineDesc("LOST");
                            tfine.setSlipDt(todaydtae);
                            tfine.setUserCd("Superuser");
                            tfine.setFineAmt(BigDecimal.valueOf(finamt));
                            
                            tfine = tMemfineFacadeREST.createAndGet(tfine);
                            int slipno = tfine.getSlipNo();
                            
                            tmrcpt =new TMemrcpt();
                            tmrcpt.setSlipNo(tfine);
                            tmrcpt.setFineDesc("LOST");
                            tmrcpt.setSlipDt(todaydtae);
                            tmrcpt.setRcptDt(todaydtae);
                            tmrcpt.setMemCd(m.getMemCd());
                            tmrcpt.setRcptAmt(BigDecimal.valueOf(recpamt));
                            tmrcpt.setChqDrftNo(transactionref);
                            tmrcpt.setBankbr(bankname);
                            tmrcpt.setUserCd("SuperUser");
                            
                            tmrcpt = tMemrcptFacadeREST.createAndGet(tmrcpt);
                            int rcptno = tmrcpt.getRcptNo();
                            
                            if(dueamt>=1){
                                tmdue = new TMemdue();
                                tmdue.setSlipNo(slipno);
                                tmdue.setSlipDt(todaydtae);
                                tmdue.setMMember(m);
                                tmdue.setLstRcptNo(String.valueOf(rcptno));
                                tmdue.setLstRcptDt(todaydtae);
                                tmdue.setDueAmt(BigDecimal.valueOf(dueamt));
                                
                                tMemdueFacadeREST.create(tmdue);
                            }
                            
                            lost.setAmountRecover("Y");
                            edit(lost);
                            
                            ln.setStatus("AM");
                            locationFacadeREST.edit(ln);
                            flg=1;
                            output=accno+" Amount recover.";
                        }
                    }
                    if(flg==0){
                         notprocess=accno+" Amount not recover.";
                    }
                  }  
            }else{
                notprocess=accno+" book status is not lost.";
            }
            
        }else{
            notprocess=accno+" Somthing is worng in book or member.";
        }
        
        spd = new StringprocessData();
        spd.setProcessdata(output);
        spd.setNonprocessdata(notprocess);
        return spd;
    }
    //Replacing Documents (Replace Book) and also for Item information (for amount recovery)
    //Get member and item details by accession no
    @GET
    @Path("ItemInformation/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> ItemInformation(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
            case "byAccessionNo":
                q = "select distinct t_lost.mem_cd, location.k852, location.price, m_bkstatus.status_dscr, location.p852,concat((select FValue from biblidetails where RecID = location.RecID AND "
                        + "tag in ('210','222','240','242','243','245','246','247') and SbFld in ('a','b')),'-') AS Title, \n" 
                        + "concat((select FValue from biblidetails where RecID = location.RecID AND tag in ('100','110','111','130') AND sbfld = 'a'), '-') AS 'Main Author'\n" 
                        + "from t_lost join Location on Location.p852 = t_lost.acc_no\n"
                        + "join m_bkstatus on t_lost.reason = m_bkstatus.bk_issue_stat\n" 
                        + "join Biblidetails on Location.RecID = Biblidetails.RecID where location.p852 = '" + Paramvalue + "' ";
                        //must be changed to dynamic afterwards
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
  
}
