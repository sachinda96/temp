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
import javax.ws.rs.QueryParam;
//import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.catalogue.Location;
import soul.catalogue.service.LocationFacadeREST;
import soul.circulation.MMember;
import soul.circulation.TLost;
import soul.circulation.TLostPK;
import soul.circulation.TMemdue;
import soul.circulation.TMemfine;
import soul.circulation.TMemrcpt;
import soul.response.StringprocessData;
import soul.circulation.OverdueData;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.tmemdue")
public class TMemdueFacadeREST extends AbstractFacade<TMemdue> {
    @EJB
    private TMemrcptFacadeREST tMemrcptFacadeREST;
    @EJB
    private TMemfineFacadeREST tMemfineFacadeREST;
    @EJB
    private TLostFacadeREST tLostFacadeREST;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private MMemberFacadeREST mMemberFacadeREST;
        
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    ConvertStringIntoJson stringintojson;
    ConvertStringIntoXml stringintoxml;
    Date TodayDate = new Date();
//    String output = "";
//    TMemdue memDue;
//    TMemrcpt memRcpt;
//    TMemdue due;
//    TMemfine fine;
//    Location location;
//    TMemrcpt receipt;
//    TLost lost;
//    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//    SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    public TMemdueFacadeREST() {
        super(TMemdue.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TMemdue entity) {
        super.create(entity);
    }
    
    //Added Manually
    @POST
    @Override
    @Path("createAndGet")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public TMemdue createAndGet(TMemdue entity) {
        return super.createAndGet(entity);
    }
    
    
    
 
    
    
//    //added manually
//    
//    @POST
//    @Path("makePayment")
//    @Consumes({"application/x-www-form-urlencoded"})
//    @Produces({"application/xml", "application/json", "text/plain"})
//    public String makePayment(@FormParam("receiptAmount") String receipt_Amount, @FormParam("slipNos") String slip_Nos,
//            @FormParam("receiptDate") String receipt_Date,
//            @Pattern(regexp = "^([a-zA-Z0-9]+([ ][a-zA-Z0-9])*)+{12}+$", message = "{memCd.pattern}") @FormParam("memCd") String memCd) {
//        System.out.println("receipt_Amount " + receipt_Amount);
//        BigDecimal receiptAmount = new BigDecimal(receipt_Amount);
//        String[] slipNos = slip_Nos.split(",");
//        Date receiptDate = null;
//        String userCode = "super user";     //Need to be changed to dynamic
//        try {
//            receiptDate = new SimpleDateFormat("yyyy-MM-dd").parse(receipt_Date);
//        } catch (ParseException ex) {
//          //  Logger.getLogger(MemberDue.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        BigDecimal newDueAmt;
//
//        for (int i = 0; i < slipNos.length; i++) {
//            try {
//                memDue = find(Integer.parseInt(slipNos[i]));
//                if (receiptAmount.compareTo(memDue.getDueAmt()) < 0) //if (receiptAmt < memDueAmt)
//                {
//                    memRcpt = new TMemrcpt();
//                    memRcpt.setFineDesc(memDue.getTMemfine().getFineDesc());
//                    memRcpt.setMemCd(memCd);
//                    memRcpt.setRcptAmt(receiptAmount);
//                    memRcpt.setRcptDt(receiptDate);
//                    System.out.println("memDue.getSlipDt() " + memDue.getSlipDt());
//                    System.out.println("memDue.getTMemfine() " + memDue.getTMemfine());
//                    memRcpt.setSlipDt(memDue.getSlipDt());
//                    memRcpt.setSlipNo(memDue.getTMemfine());
//                    memRcpt.setUserCd(userCode);
//
//                    memRcpt = tMemrcptFacadeREST.createAndGet(memRcpt);
//
//                    newDueAmt = memDue.getDueAmt().subtract(receiptAmount);
//                    memDue.setDueAmt(newDueAmt);
//                    memDue.setLstRcptDt(receiptDate);
//                    memDue.setLstRcptNo(Integer.toString(memRcpt.getRcptNo()));
//                    edit(memDue);
//
//                } else {
//                    //else subtrat dueAmoutn from current receipt amount and delete memdue entry
//                    receiptAmount = receiptAmount.subtract(memDue.getDueAmt());
//
//                    memRcpt = new TMemrcpt();
//                    memRcpt.setFineDesc(memDue.getTMemfine().getFineDesc());
//                    memRcpt.setMemCd(memCd);
//                    memRcpt.setRcptAmt(memDue.getDueAmt());
//                    memRcpt.setRcptDt(receiptDate);
//                    memRcpt.setSlipDt(memDue.getSlipDt());
//                    memRcpt.setSlipNo(memDue.getTMemfine());
//                    memRcpt.setUserCd(userCode);
//
//                    memRcpt = tMemrcptFacadeREST.createAndGet(memRcpt);
//
//                    remove(Integer.parseInt(slipNos[i]));
//                }
//            } catch (NullPointerException e) {
//                return "Invalid Slip details";
//            }
//
//            //if receipt amount is less that current due amt subtract it from due amount and update memDue and exit
//        }
//        output = "Due amount paid.";
//        return output;
//
//    }
//  
    @GET
    @Path("getmemdueData/{memecode}")
    public List<OverdueData> getmemdueData(@PathParam("memecode") String memcd){
         List<OverdueData> lioverdue=null;
        MMember m = null;
        m = mMemberFacadeREST.find(memcd);
        if(m!=null){
            String q="SELECT t_memdue.slip_no as slipNo, t_memdue.slip_dt as slipDate, t_memdue.lst_rcpt_no as lastRecpNo,t_memdue.lst_rcpt_dt as lastRecpDate, t_memfine.fine_desc as reason, t_memfine.accn_no as accno, t_memdue.due_amt as dueamt,t_memfine.fine_amt as totalfine,CONCAT(m_member.mem_firstnm,' ',m_member.mem_lstnm) as memname FROM t_memfine, t_memdue,m_member where t_memdue.mem_cd=m_member.mem_cd and t_memfine.slip_no = t_memdue.slip_no and t_memdue.mem_cd = '"+m.getMemCd()+"' order by t_memdue.slip_dt";
        Query query;
         query = getEntityManager().createNativeQuery(q,OverdueData.class);
         lioverdue = query.getResultList();
        }else{
            lioverdue = new ArrayList();
        }
        
        return lioverdue;
    }
    
    @POST
    @Path("makePayment")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData makePayment(String paymentdata) {
        
        StringprocessData spd;
        String output="";
        OverdueData ord;
        String notprocess="";   
        TMemfine tmfine;
        TMemdue tmdue;
        TMemrcpt tmrcpt;
        MMember m;
        Location ln;
        List<Location> liloc=null;
        List<OverdueData> liord=null;
        
        String datatype = paymentdata.substring(0,1);
        String memcd="";
        String slipnos="";
        String paydetails="";
        
        if(datatype.equals("{")){
            stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(paymentdata);
             memcd = jsonobj.getString("memcd");   
             slipnos = jsonobj.getString("slipnos");
             paydetails = jsonobj.getString("paydetails");
             
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(paymentdata);
                memcd = stringintoxml.getdatafromxmltag(doc,"memcd");
                slipnos = stringintoxml.getdatafromxmltag(doc,"slipnos");
                paydetails = stringintoxml.getdatafromxmltag(doc,"paydetails");
                
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }
        
        
        String slipno[]=slipnos.split(",");
        m = mMemberFacadeREST.find(memcd);
        String paydetail[]=paydetails.split(",");
        String totalfine = paydetail[0];
        String recpamt = paydetail[1];
        
        Double fineamt = Double.valueOf(totalfine);
        Double recptamt = Double.valueOf(recpamt);
        Double diffrenceamt = fineamt - recptamt;
        
        String paymod = paydetail[2];
        String transactionid = paydetail[3];
        String bankname = paydetail[4];
        String transactionref;
        
        if(paymod.equals("CASH")){
            transactionref = "CASH";
            bankname=" ";
        }else{
            transactionref = paymod+" : "+transactionid;
        }
        
        if(m!=null){
            liord=getmemdueData(m.getMemCd());
            
            if(diffrenceamt==0){
                for(int j=0;j<slipno.length;j++){
                    String memslipno = slipno[j];
                    for(int i=0;i<liord.size();i++){
                        ord = liord.get(i);
                         if(memslipno.equals(ord.getSlipno())){
                             tmdue = find(Integer.valueOf(memslipno));
                             Double bookdueamt = ord.getRcptamt().doubleValue();
                             
                             tmfine = tMemfineFacadeREST.find(tmdue.getSlipNo());
                                        
                                tmrcpt = new TMemrcpt();
                                tmrcpt.setSlipNo(tmfine);
                                tmrcpt.setSlipDt(TodayDate);
                                tmrcpt.setFineDesc(tmfine.getFineDesc());
                                tmrcpt.setRcptDt(TodayDate);
                                tmrcpt.setUserCd("Superuser");
                                tmrcpt.setRcptAmt(BigDecimal.valueOf(bookdueamt));
                                tmrcpt.setBankbr(bankname);
                                tmrcpt.setChqDrftNo(transactionref);
                                tmrcpt.setMemCd(m.getMemCd());
                                
                                tMemrcptFacadeREST.create(tmrcpt);
                                
                                remove(tmdue.getSlipNo());
                                
                                output+=tmdue.getSlipNo()+",";
                         }
                    }
                }
            }else{
                int singlerecord=0;
                Double payamt= recptamt;
                if(slipno.length==1){
                    singlerecord=1;
                }
                               
                for(int j=0;j<slipno.length;j++){
                    String memslipno = slipno[j];
                    
                    for(int k=0;k<liord.size();k++){
                        ord = liord.get(k);
                        if(memslipno.equals(ord.getSlipno())){
                            tmdue = find(Integer.valueOf(memslipno));
                                                     
                            Double bookdueamt = ord.getRcptamt().doubleValue();
                            
                                if(bookdueamt>payamt){ 
                                    if(payamt!=0){
                                      
                                        Double recpamtpay = bookdueamt - payamt;
                                        tmfine = tMemfineFacadeREST.find(tmdue.getSlipNo());
                                        
                                        tmrcpt = new TMemrcpt();
                                        tmrcpt.setSlipNo(tmfine);
                                        tmrcpt.setSlipDt(TodayDate);
                                        tmrcpt.setFineDesc(tmfine.getFineDesc());
                                        tmrcpt.setRcptDt(TodayDate);
                                        tmrcpt.setUserCd("Superuser");
                                        tmrcpt.setRcptAmt(BigDecimal.valueOf(payamt));
                                        tmrcpt.setBankbr(bankname);
                                        tmrcpt.setChqDrftNo(transactionref);
                                        tmrcpt.setMemCd(m.getMemCd());
                                
                                        tmrcpt = tMemrcptFacadeREST.createAndGet(tmrcpt);
                                
                                        tmdue.setDueAmt(BigDecimal.valueOf(recpamtpay));
                                        tmdue.setLstRcptNo(String.valueOf(tmrcpt.getRcptNo()));
                                        tmdue.setLstRcptDt(tmrcpt.getRcptDt());
                                        edit(tmdue);
                                        
                                        payamt=0.0;
                                        if(singlerecord==1){
                                            output="due of slip no."+tmfine.getSlipNo().toString()+" is updated "+recpamtpay.toString()+"..";
                                        }
                                    }else{
                                        notprocess+=tmdue.getSlipNo()+",";
                                    }
                                }else{
                                    if(payamt>=bookdueamt){
                                        tmfine = tMemfineFacadeREST.find(tmdue.getSlipNo());
                                        
                                        tmrcpt = new TMemrcpt();
                                        tmrcpt.setSlipNo(tmfine);
                                        tmrcpt.setSlipDt(TodayDate);
                                        tmrcpt.setFineDesc(tmfine.getFineDesc());
                                        tmrcpt.setRcptDt(TodayDate);
                                        tmrcpt.setUserCd("Superuser");
                                        tmrcpt.setRcptAmt(BigDecimal.valueOf(bookdueamt));
                                        tmrcpt.setBankbr(bankname);
                                        tmrcpt.setChqDrftNo(transactionref);
                                        tmrcpt.setMemCd(m.getMemCd());
                                
                                        tMemrcptFacadeREST.create(tmrcpt);
                                
                                        remove(tmdue.getSlipNo());
                                    
                                        payamt = payamt-bookdueamt;
                                        output+=tmdue.getSlipNo()+",";
                                    }
                                }
                        }
                    }
                }        
            
             }
            
        }else{
            notprocess=memcd+" is not valid..";
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

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TMemdue entity) {
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
    public TMemdue find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TMemdue> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TMemdue> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml","application/json"})
    public List<TMemdue> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
            default:    valueList.add(valueString[0]);
            //used for findByMemCd 
        }
        return super.findBy("TMemdue."+query, valueList);
    }
    
    @GET
    @Path("sum/by/{namedQuery}/{values}")
    @Produces("text/plain")
    public String sumBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
            default:    valueList.add(valueString[0]);
            //used for findDueSumByMemCd, findByMemCd
        }
        return String.valueOf(super.sumBy("TMemdue."+query, valueList));
    }
    
    @GET
    @Path("byDate/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<TMemdue> byDt(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");       
        List<Object> valueList = new ArrayList<>();         
        switch(query)
        {
            case "findBySlipDtBtwn": SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                                            try {
                                                valueList.add(dateFormat1.parse(valueString[0]));
                                                valueList.add(dateFormat1.parse(valueString[1]));
                                                        
                                            } catch (ParseException ex) {
                                                Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            break;
            default:    valueList.add(valueString[0]);
                        break;
        }
        //System.out.println("TMemrcpt."+query + valueList);
        return super.findBy("TMemdue."+query, valueList);
    }
    
    //used in transaction menu to pay due amount.
//    @POST
//    @Path("payDue")
//    @Consumes({"application/xml", "application/json"})
//    @Produces({"text/plain"})
//    public String payDue(@FormParam("dueIds") String due_Ids ,@FormParam("amountPaying") String dueAmount,
//    @FormParam("receiptDate") String receiptDate , @FormParam("bankName") String bankName,
//    @FormParam("chequeDraft") String chequeDraftNo , @FormParam("reason") String reason)
//    {
//        String[] dueIds = due_Ids.split(",");
//        //pr       String[] amountPaying = request.getParameter("amountPaying").split(",");
//        String[] amountPaying = dueAmount.split(" ");
//       // String receiptDate = request.getParameter("receiptDate");
//        System.out.println("due amt "+amountPaying.length+"  "+dueIds.length+"  "+receiptDate);
//        BigDecimal diff, paying, diffSum  = BigDecimal.ZERO;
//        due = find(Integer.parseInt(dueIds[0]));
//        System.out.println("DUECL "+due.getSlipNo());
//        for(int i=0; i<dueIds.length; i++)
//        {   System.out.println("due amt........ "+amountPaying[i]+"  "+dueIds[i]+"  "+receiptDate);
//            due = find(Integer.parseInt(dueIds[i]));
//            paying = new BigDecimal(amountPaying[i]);
//            System.out.println("paying "+paying);
//            //Make entry in TMemRcpt
//            receipt = new TMemrcpt();
//            receipt.setFineDesc(reason);
//            receipt.setMemCd(due.getMMember().getMemCd());
//            receipt.setRcptAmt(paying);
//            try {
//                receipt.setRcptDt(format.parse(receiptDate));
//            } catch (ParseException ex) {
//                Logger.getLogger(TMemdueFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            receipt.setSlipDt(due.getSlipDt());
//            receipt.setSlipNo(due.getTMemfine());
//            receipt.setUserCd("superuser");     //Make it dynamic
//            receipt.setBankbr(bankName);
//            receipt.setChqDrftNo(chequeDraftNo);
//
//            receipt = tMemrcptFacadeREST.createAndGet(receipt);
//            //Delete due entry if amount is fully paid and Update if partially paid
//            System.out.println("due.getDueAmt() "+due.getDueAmt()+" PAYING  "+paying);
//            //diff = due.getDueAmt().subtract(paying);
//            diff = due.getDueAmt().subtract(paying);
//            
//            System.out.println("diff "+diff);
//            if(diff.compareTo(BigDecimal.ZERO) == 0)
//            {
//                remove(Integer.parseInt(dueIds[i]));
//            }
//            else
//            {
//                due.setDueAmt(diff);
//                System.out.println("RecptDate "+receipt.getRcptDt());
//                due.setLstRcptDt(new java.sql.Date(receipt.getRcptDt().getTime()));
//                due.setLstRcptNo(receipt.getRcptNo().toString());
//                edit(due);
//
//                diffSum = diffSum.add(diff);
//            }
//        }
//        if(diffSum.compareTo(BigDecimal.ZERO) == 0)
//        {
//            output = "Amount received. No due left for "+due.getMMember().getMemFirstnm()+" "+due.getMMember().getMemLstnm()+".";
//        }        
//        else
//        {
//            output = "Amount received. But still Rs."+diffSum+" due on "+due.getMMember().getMemFirstnm()+" "+due.getMMember().getMemLstnm()+".";
//        }
//        return output;
//    }
//    vijay test
    @POST
    @Path("payDue")
    @Consumes({"application/xml", "application/json"})
    @Produces({"text/plain"})
    public String payDue(String dueData)
    {
//        JSONObject jsonObject = new JSONObject(dueData);
//        System.out.println(" Json : "+dueData+" jsonObject : "+jsonObject.getString("dueIds"));
//        
//        String due_Ids = jsonObject.getString("dueIds").toString();
//        String dueAmount=jsonObject.getString("amountPaying").toString();
//        String receiptDate=jsonObject.getString("receiptDate").toString();
//        String bankName=jsonObject.getString("bankName").toString();
//        String chequeDraftNo=jsonObject.getString("chequeDraftNo").toString();
//        String reason=jsonObject.getString("reason").toString();
//        
//        System.out.println("dueIds----"+due_Ids);
//        System.out.println("amountPaying----"+dueAmount);
//        System.out.println("receiptDate----"+receiptDate);
//        System.out.println("bankName----"+bankName);
//        System.out.println("chequeDraftNo----"+chequeDraftNo);
//        System.out.println("reason----"+reason);
//        
//         String[] dueIds = due_Ids.split(",");
//         String[] amountPaying = dueAmount.split(",");
//         
//        System.out.println("due amt "+amountPaying.length+"  "+dueIds.length+"  "+receiptDate);
//        BigDecimal diff, paying, diffSum  = BigDecimal.ZERO;
//        due = find(Integer.parseInt(dueIds[0]));
//        System.out.println("DUECL "+due.getSlipNo());
//        for(int i=0; i<dueIds.length; i++)
//        {   System.out.println("due amt........ "+amountPaying[i]+"  "+dueIds[i]+"  "+receiptDate);
//            due = find(Integer.parseInt(dueIds[i]));
//            paying = new BigDecimal(amountPaying[i]);
//            System.out.println("paying "+paying);
//            //Make entry in TMemRcpt
//            receipt = new TMemrcpt();
//            receipt.setFineDesc(reason);
//            receipt.setMemCd(due.getMMember().getMemCd());
//            receipt.setRcptAmt(paying);
//            try {
//                receipt.setRcptDt(format.parse(receiptDate));
//            } catch (ParseException ex) {
//                Logger.getLogger(TMemdueFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            receipt.setSlipDt(due.getSlipDt());
//            receipt.setSlipNo(due.getTMemfine());
//            receipt.setUserCd("superuser");     //Make it dynamic
//            receipt.setBankbr(bankName);
//            receipt.setChqDrftNo(chequeDraftNo);
//
//            receipt = tMemrcptFacadeREST.createAndGet(receipt);
//            //Delete due entry if amount is fully paid and Update if partially paid
//            System.out.println("due.getDueAmt() "+due.getDueAmt()+" PAYING  "+paying);
//            //diff = due.getDueAmt().subtract(paying);
//            diff = due.getDueAmt().subtract(paying);
//            
//            System.out.println("diff "+diff);
//            if(diff.compareTo(BigDecimal.ZERO) == 0)
//            {
//                remove(Integer.parseInt(dueIds[i]));
//            }
//            else
//            {
//                due.setDueAmt(diff);
//                System.out.println("RecptDate "+receipt.getRcptDt());
//                due.setLstRcptDt(new java.sql.Date(receipt.getRcptDt().getTime()));
//                due.setLstRcptNo(receipt.getRcptNo().toString());
//                edit(due);
//
//                diffSum = diffSum.add(diff);
//            }
//        }
//        if(diffSum.compareTo(BigDecimal.ZERO) == 0)
//        {
//            output = "Amount received. No due left for "+due.getMMember().getMemFirstnm()+" "+due.getMMember().getMemLstnm()+".";
//        }        
//        else
//        {
//            output = "Amount received. But still Rs."+diffSum+" due on "+due.getMMember().getMemFirstnm()+" "+due.getMMember().getMemLstnm()+".";
//        }
       return "";
    }
                                          
     @GET
     @Path("retrieveAllDueForMemCd/{memCd}")
     @Produces("application/xml")
     //public List<TMemdue> retrieveAllDueForMemCd(@QueryParam("memCd") String memCd)
     public List<TMemdue> retrieveAllDueForMemCd(@PathParam("memCd") String memCd)        
     {
        memCd = memCd.equals("") ? "null" : memCd;
        return findBy("findByMemCd", memCd);       
     }
     
     @POST
     @Path("recoverAmount")
     public String recoverAmount(@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{accNo.pattern}") @FormParam("accNo") String accNo, 
             @FormParam("recoveryAmount") String recovery_Amount,
      @FormParam("receiptDate") String receiptDate,@FormParam("amountPaying") String amount_Paying,
      @Pattern(regexp = "^([a-zA-Z0-9]+([ ][a-zA-Z0-9])*)+{12}+$", message = "{memCd.pattern}") @FormParam("memberId") String memCd,
      @FormParam("reportingDate") String reportingDate,
      @FormParam("reason") String reason,@FormParam("recptDate") String recptDate)
     {
//            lost = new TLost();
//            TMemfine memfine = new TMemfine();
//            BigDecimal recoveryAmount = new BigDecimal(recovery_Amount);
//            BigDecimal amountPaying = new BigDecimal(amount_Paying);
//            memfine.setAccnNo(accNo);
//            memfine.setMemCd(memCd);
//            memfine.setFineDesc(reason);
//            try {
//                memfine.setSlipDt(format.parse(recptDate));
//            } catch (ParseException ex) {
//                Logger.getLogger(TMemdueFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            memfine.setFineAmt(recoveryAmount);
//            memfine.setUserCd("super user");        //Make dynamic as logged in user
//
//            memfine = tMemfineFacadeREST.createAndGet(memfine);
//
//            TMemrcpt memrcpt = new TMemrcpt();
//            memrcpt.setFineDesc(memfine.getFineDesc());
//            memrcpt.setMemCd(memCd);
//            memrcpt.setRcptAmt(amountPaying);
//            memrcpt.setRcptDt(memfine.getSlipDt());
//            memrcpt.setSlipDt(memfine.getSlipDt());
//            memrcpt.setSlipNo(memfine);
//            memrcpt.setUserCd("super user");        //Make dynamic as logged in user
//
//            memrcpt = tMemrcptFacadeREST.createAndGet(memrcpt);
//            TLostPK lostPk = new TLostPK();
//            lostPk.setAccNo(accNo);
//            lostPk.setMemCd(memCd);
//            try {
//                lostPk.setRepDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(reportingDate));
//            } catch (ParseException ex) {
//                Logger.getLogger(TLostFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//      
//            lost = tLostFacadeREST.find(lostPk);
//             lost.setAmountRecover("Y");
//
//            try{
//                lost = tLostFacadeREST.find(lostPk);
//                lost.setAmountRecover("Y");
//            } catch (NullPointerException e) {
//                return "Something is wrong with records";
//            }
//            
//            
//
//            tLostFacadeREST.edit(lost);
//            
//            try{
//               location = locationFacadeREST.findBy("findByP852", accNo).get(0); 
//           }catch(ArrayIndexOutOfBoundsException e){
//               output = "Invalid Accession No.";
//               return output;               
//           }
//            location.setStatus("AV");
//
//            if(amountPaying.compareTo(recoveryAmount) == -1)
//            {
//                TMemdue memdue = new TMemdue();
//                memdue.setDueAmt(recoveryAmount.subtract(amountPaying));
//                memdue.setLstRcptDt(memrcpt.getRcptDt());
//                memdue.setLstRcptNo(Integer.toString(memrcpt.getRcptNo()));
//                memdue.setSlipDt(memfine.getSlipDt());
//                memdue.setSlipNo(memfine.getSlipNo());
//                memdue.setMMember(mMemberFacadeREST.find(memCd));
//                memdue.setTMemfine(memfine);
//
//                memdue = createAndGet(memdue);
//
//                location.setStatus("AM");
//                output = "Partial amount recovered for lost book.";
//            }
//            else
//            {
//                output = "Amount Recovered for lost book.";
//            }
//            locationFacadeREST.edit(location);
            return "";
       }
     
    //Over due item list
    //This method will return the list of items that passez the due date 
     
    @GET
    @Path("OverdueItemList/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> OverdueItemList(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
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

            case "byDueDate":
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                result.add(dateFormat.parse(valueString[0]));
                                //result.add(dateFormat.parse(valueString[1]));
                            } catch (ParseException ex) {
                                Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                            }
                q = "select * from m_member a join t_issue b on a.mem_cd = b.mem_cd join"
                        + "( SELECT        Location.RecID, Location.p852, Biblidetails.FValue, Location.f852, Location.k852"
                        + " FROM            Location INNER JOIN"
                        + "   Biblidetails ON Location.RecID = Biblidetails.RecID"
                        + "  WHERE  (Location.p852 IS NOT NULL) AND (Biblidetails.Tag = '245'\n"
                        + ") AND (Biblidetails.SbFld = 'a'\n"
                        + ") ) c"
                        + " on b.acc_no = c.p852 "
                        + " WHERE b.due_dt < '" + Paramvalue + "'";
                //query = getEntityManager().createNativeQuery(q);
                //result = (List<Object>) query.getResultList();
                break;

            case "byTitle":
                q = "select t_issue.mem_cd, concat(m_member.mem_firstnm, m_member.mem_lstnm), m_member.mem_prmntphone, t_issue.acc_no, "
                        + "biblidetails.FValue, t_issue.iss_dt, t_issue.due_dt from t_issue join m_member on t_issue.mem_cd = m_member.mem_cd\n"
                        + "right outer join location on location.p852 = t_issue.acc_no\n"
                        + "join biblidetails on location.RecID = biblidetails.RecID where t_issue.mem_cd = m_member.mem_cd "
                        + "AND location.p852 = t_issue.acc_no AND biblidetails.Tag = '245' and biblidetails.SbFld = 'a' and "
                        + "biblidetails.FValue like '" + Paramvalue + "'";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
    
    
    //Over due item list
    //This method will return the list of members whose due is pending
     
    @GET
    @Path("MembersPendingDue/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> PendingDue(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
            case "byMemberCode":
                q = "select  t_memdue.mem_cd, t_memdue.slip_no, t_memfine.fine_desc, t_memdue.slip_dt, t_memfine.accn_no, t_memdue.due_amt, Biblidetails.FValue,\n" 
                        + "m_member.mem_tag, m_member.mem_firstnm, m_member.mem_midnm, m_member.mem_lstnm from t_memdue\n" 
                        + "join m_member on t_memdue.mem_cd = m_member.mem_cd\n" 
                        + "join t_memfine on m_member.mem_cd = t_memfine.mem_cd\n" 
                        + "join Location on t_memfine.accn_no = Location.p852\n" 
                        + "join Biblidetails on Location.RecID = Biblidetails.RecID\n" 
                        + "where t_memdue.due_amt > 0  and t_memfine.mem_cd like '" + Paramvalue + "' AND biblidetails.Tag = '245' and biblidetails.SbFld = 'a' ";
                break;
                
            case "bySlipDateBetween":
                q = "select  t_memdue.mem_cd, t_memdue.slip_no, t_memfine.fine_desc, t_memdue.slip_dt, t_memfine.accn_no, t_memdue.due_amt, Biblidetails.FValue,\n" 
                        + "m_member.mem_tag, m_member.mem_firstnm, m_member.mem_midnm, m_member.mem_lstnm from t_memdue\n" 
                        + "join m_member on t_memdue.mem_cd = m_member.mem_cd\n" 
                        + "join t_memfine on m_member.mem_cd = t_memfine.mem_cd\n" 
                        + "join Location on t_memfine.accn_no = Location.p852\n" 
                        + "join Biblidetails on Location.RecID = Biblidetails.RecID\n" 
                        + "where t_memdue.due_amt > 0  and (t_memfine.slip_dt BETWEEN '" + valueString[0] + "' AND '" + valueString[1] + "') AND biblidetails.Tag = '245' and biblidetails.SbFld = 'a' ";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
     
}
