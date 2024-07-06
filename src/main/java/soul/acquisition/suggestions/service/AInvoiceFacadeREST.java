/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.acquisition.suggestions.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
import javax.ws.rs.core.Form;
import soul.acquisition.suggestions.AInvoice;
import soul.acquisition.suggestions.AOrder;
import soul.acquisition.suggestions.AOrderPK;
import soul.acquisition.suggestions.AOrdermaster;
import soul.acquisition.suggestions.APayment;
import soul.acquisition.suggestions.ARequest;
import soul.circulation.MLibinfo;
import soul.general_master.ABudget;
import soul.general_master.ASupplierDetail;
import soul.general_master.Autogenerate;
import soul.general_master.LetterFormats;
import soul.general_master.Reportimage;
import soul.general_master.service.ABudgetFacadeREST;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.acquisition.suggestions.ainvoice")
public class AInvoiceFacadeREST extends AbstractFacade<AInvoice> {
    @EJB 
    private AOrdermasterFacadeREST aOrdermasterFacadeREST;
    @EJB
    private ABudgetFacadeREST aBudgetFacadeREST;
    @EJB
    private AOrderFacadeREST aOrderFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    AInvoice invoice = new AInvoice();
    int count;
    String output;
    List<AInvoice> getAll;
        
    public AInvoiceFacadeREST() {
        super(AInvoice.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(AInvoice entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(AInvoice entity) {
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
    public AInvoice find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<AInvoice> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("by/{namedQuery}/{attrValues}")
    @Produces({"application/xml", "application/json"})
    public List<AInvoice> findBy(@PathParam("namedQuery") String query, @PathParam("attrValues") String attrValues) {
        String[] valueString = attrValues.split("\\|");
        List<String> inString = new ArrayList<>();
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findByNULLAPStatus": return  super.findBy("AInvoice."+query);
                                           
            case "findByAOApaymentAOStatusRefStatusNull":   inString.addAll(Arrays.asList(valueString[0].split(",")));
                                                            valueList.add(inString);
                                                            valueList.add(valueString[1]);
                                                            break;
//            case "findByAOApaymentAOStatusRefStatusNullForRefund":   inString.addAll(Arrays.asList(valueString[0].split(",")));
//                                                      valueList.add(inString);
//                                                      valueList.add(valueString[1]);
//                                                      break;
            case "findByAOApaymentAOStatusRefStatusNullANDOrderNo":   inString.addAll(Arrays.asList(valueString[0].split(",")));
                                                            valueList.add(inString);
                                                            valueList.add(valueString[1]);
                                                            valueList.add(Integer.parseInt(valueString[2]));
                                                            break;
            case "findByAIPoNoANDRegular":      valueList.add(Integer.parseInt(valueString[0]));
                                                break;
        }
        return super.findBy("AInvoice."+query, valueList);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<AInvoice> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    //manually added
    
        
    @GET
    @Path("retrieveAll/{accept}/{form}")
   // public List<AInvoice> retrieveAll(@QueryParam("accept") String accept,@QueryParam("form") String form)
    public List<AInvoice> retrieveAll(@PathParam("accept") String accept,@PathParam("form") String form)        
  {
        if(accept.equals("XML"))
       {
           switch(form)
           {
               case "PaymentProcess":  getAll = findBy("findByNULLAPStatus", "NULL");
                                       break;
               case "AcquisitionRefund":   getAll = findBy("findByAOApaymentAOStatusRefStatusNull", "S,S1,L|D");
            //   case "AcquisitionRefund":   getAll = findBy("findByAOApaymentAOStatusRefStatusNullForRefund", "S,S1,L|D");
                                       
                                       break;
           }

       }
        return getAll;
        
  
        
//       else
//       {
//           genericType = new GenericType<List<AInvoice>>(){};
//           restResponse = invoiceClient.findBy(Response.class, "findByNULLAPStatus", "NULL");
//           invoiceList = (List<AInvoice>) restResponse.readEntity(genericType);
//           request.setAttribute("invoiceList", invoiceList);
//       }
    }
           AOrder order = new AOrder();
        ARequest req;

      
    @POST
    @Path("createInvoice")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createInvoice(Form form,@FormParam("invoiceDate") String invoiceDate, @FormParam("referenceNo") String referenceNo,
    @FormParam("totalAmount") String totalAmount, @FormParam("orderNo") String orderNo,
    @FormParam("miscCharges") String miscCharges, @FormParam("budget") String budget,
    @FormParam("pType") String pType, @FormParam("overall_Discount") String overall_Discount,
    @FormParam("itemNos") String itemNos)
    {    //getInvoice//
         try {
            Date date = new SimpleDateFormat("YYYY-MM-dd").parse(invoiceDate);
            invoice.setAIDt(new java.sql.Date(new java.util.Date().getTime()));
            invoice.setAIProcDt(new java.sql.Date(new java.util.Date().getTime()));
        } catch (ParseException ex) {
            Logger.getLogger(AInvoiceFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        invoice.setAINo(referenceNo);
        invoice.setAIRefNo(referenceNo);
        invoice.setAITotal(new BigDecimal(totalAmount));
        invoice.setAIMisc(new BigDecimal(miscCharges));
        invoice.setAINetamount(invoice.getAITotal().subtract(invoice.getAIMisc()));
        invoice.setAIPoNo(aOrdermasterFacadeREST.find(Integer.parseInt(orderNo)));
        if(!budget.equals("select"))
            invoice.setAIMiscBudget(aBudgetFacadeREST.find(Integer.parseInt(budget)));
        //getInvoice - end //
        count = Integer.parseInt(countREST());
        create(invoice);
        if(Integer.parseInt(countREST()) != count)
        {
            updateBud(form,invoice,pType, overall_Discount, itemNos, orderNo );
            output = "Invoice Generated.";
        }
        else
        {
            output = "Someting went wrong, Invoice is not generated.";
        }
        return output;
     }
    
    public void updateBud(Form form,AInvoice invoice,String pType, String overall_Discount, String itemNos, String orderNo )
    {
        String AOPayment = pType.equals("regular") ? "L" : "S";
        
        //MiscCharges Budget Update
        ABudget budget = invoice.getAIMiscBudget();
        if(budget != null)
        {
            budget.setABExpAmt(budget.getABExpAmt().add(invoice.getAIMisc()));
            //System.out.println("After adding misc price :"+budget.getABPoraisedAmt());
            aBudgetFacadeREST.edit(budget);
            
            //done
        }
        
        BigDecimal rate, price, totalPrice;
        int orderedCopies, receivedCopies, copies;
        Double overallDiscount = null;
        if(overall_Discount != null)
        {
             overallDiscount = new Double(overall_Discount)/100;
        }
        //All Requests and Budget update
        int reqCount = Integer.parseInt(itemNos);
        for(int i=0; i<reqCount; i++)
        {
            order = new AOrder();
            req = new ARequest();
            String[] order_No = form.asMap().getFirst("orderNo"+(i+1)).split(";");
            String aRNo= order_No[1].split("=")[1];
            String aOPoNo = order_No[2].split("=")[1];
            System.out.println("aRNo_  "+aRNo);
            // String aOPoNo = form.asMap().get("itemNo1").get(1);
            System.out.println("aOPoNo_  "+aOPoNo);
            AOrderPK aorderPk = new AOrderPK();
            aorderPk.setARNo(Integer.parseInt(aRNo));
            aorderPk.setAOPoNo(Integer.parseInt(aOPoNo));
            order = aOrderFacadeREST.find(aorderPk);
            
            req = order.getARequest();
            
            rate = req.getARConvRate();
            price = req.getARPrice();
            orderedCopies = order.getAOOrderedCopy();
            receivedCopies = order.getAOTotalreceivedCopy();
            
            if(pType.equals("regular"))
                copies = receivedCopies;
            else
                copies = orderedCopies - receivedCopies;
            
            if(copies != orderedCopies)
                AOPayment = (AOPayment+"1").substring(0, 2);
            
            totalPrice = rate.multiply(price).multiply(new BigDecimal(copies));
            
            budget = req.getARBudgetCd();
            budget.setABPoraisedAmt(budget.getABPoraisedAmt().subtract(totalPrice));
            //System.out.println("After Subtractin Old Price:"+budget.getABPoraisedAmt());
            //old amount subtracted
            
            totalPrice = new BigDecimal(form.asMap().getFirst("netPrice"+(i+1)));  //
            if(overall_Discount != null)
            {
                totalPrice = totalPrice.subtract(totalPrice.multiply(new BigDecimal(overallDiscount))); 
            }
            budget.setABExpAmt(budget.getABExpAmt().add(totalPrice));
            //System.out.println("After adding new Price:"+budget.getABPoraisedAmt());
            //new overall Discounted price added to budget
            
            //No update in Request Table
            //No update in Order Table
            
            aBudgetFacadeREST.edit(budget);
        }
        //update of OrderMaster entry
        //Set AOPayment : null to L(Received), S(Ordered), L1(Partial Received-Regular), S1(Partial Received-Advance)
        if(order.getAOrdermaster().getAOStatus().equals("D"))
            AOPayment = (AOPayment+"1").substring(0, 2);
        
        AOrdermaster orderMaster = invoice.getAIPoNo();
        //System.out.println("AOAPayment: "+AOPayment);
        if(orderMaster.getAOApayment() != null)         //Means it is L1 or S1 but not null
            orderMaster.setAOApayment(AOPayment.substring(0,1));    //In this case set It to normal L or S
        else
            orderMaster.setAOApayment(AOPayment);       //If AOApayment is null we need to set it to first stage status ie L1 or S1
        aOrdermasterFacadeREST.edit(orderMaster);
        //orderMaster updated done
    }
    
    
    @GET
    @Path("getInvoiceForPaymentProcess")
    public List<AInvoice> getInvoiceForPaymentProcess()
    {     
        List getInvoice = null;
        return super.findBy("AInvoice.findByNULLAPStatus");
    }
    
    @GET
    @Path("getInvoiceForRefundProcess")
    public List<AInvoice> getInvoiceForRefundProcess()
    {     
        List getInvoice = null;
       // return findBy("findByAOApaymentAOStatusRefStatusNullForRefund", "S,S1,L|D");
        return findBy("findByAOApaymentAOStatusRefStatusNull", "S,S1,L|D");
    }
    
    @GET
    @Path("getInvoiceForRefundProcess/by/orderNo/{orderNo}")
    public List<AInvoice> getInvoiceForRefundProcess(@PathParam("orderNo") String orderNo)
    {     
        List getInvoice = null;
        List<Object> valueList = new ArrayList<>();
        valueList.add(orderNo);
        return findBy("findByAOApaymentAOStatusRefStatusNullANDOrderNo", "S,S1,L|D|"+orderNo);
    }
    
    //for report generation for invoice register
    @GET
    @Path("searchInvoice/{paramName}/{paramValue}")
    @Produces({"application/xml", "application/json"})
    public List<AInvoice> getInvoiceListByParam(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) throws ParseException 
    {
        String[] valueString = paramValue.split(",");
        List<Object> valueList = new ArrayList<>();
        List<AInvoice> getInvoiceList = null;
        switch(paramName){
            case "supplier":
                 valueList.add(valueString[0]);
                 getInvoiceList = super.findBy("AInvoice.findBySuppCd", valueList );
                break;
            case "date":
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[0]));
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[1]));
                getInvoiceList = super.findBy("AInvoice.findByDateBetween", valueList );
                break;
            case "payment":
                if(valueString[0].equalsIgnoreCase("due"))
                {
                   getInvoiceList = super.findBy("AInvoice.findByPaymentStatusNULL") ;
                }
                if(valueString[0].equalsIgnoreCase("done"))
                {
                    getInvoiceList = super.findBy("AInvoice.findByPaymentStatusNOTNULL");
                }
               break;
         }
        return getInvoiceList;
    }
    
     //for report generation for payment register
    @GET
    @Path("searchInvoicePayment/{paramName}/{paramValue}")
    @Produces({"application/xml", "application/json"})
    public List<AInvoice> getPaymentListByParam(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) throws ParseException 
    {
        String[] valueString = paramValue.split(",");
        List<Object> valueList = new ArrayList<>();
        List<AInvoice> getInvoicePaymentList = null;
        switch(paramName){
            case "supplier":
                 valueList.add(valueString[0]);
                 getInvoicePaymentList = super.findBy("AInvoice.findBySuppCdAndPaymentNotNull", valueList );
                break;
            case "date":
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[0]));
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[1]));
                getInvoicePaymentList = super.findBy("AInvoice.findByPayCheckDateBetween", valueList );
                break;
            case "budget":
                valueList.add(Integer.parseInt(valueString[0]));
                getInvoicePaymentList = super.findBy("AInvoice.findByBudgetAndPaymentNotNull" ,valueList) ;
               break;
         }
        return getInvoicePaymentList;
    }
    
       //for report generation for refund report
    @GET
    @Path("getRefundReportdetails/{dates}")
    @Produces({"application/xml", "application/json"})
    public List<AInvoice> getRefundReportdetails(@PathParam("dates") String dates) throws ParseException 
    {
        String[] valueString = dates.split(",");
        List<Object> valueList = new ArrayList<>();
        List<AInvoice> getRefundList = null;
        valueList.add(new java.sql.Date(new SimpleDateFormat("YYYY-MM-dd").parse(valueString[0]).getTime()));
        valueList.add(new java.sql.Date(new SimpleDateFormat("YYYY-MM-dd").parse(valueString[1]).getTime()));
        getRefundList = super.findBy("AInvoice.findRefundByDate" ,valueList) ;
        return getRefundList;
    }
    
    // used in forwarding to account
    @GET
    @Path("getDataForForwardToAccount/{paramName}/{paramValue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> getDataForForwardToAccount(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) throws ParseException {
        String q="";
        Query query;
        String valuestring[] = paramValue.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        switch(paramName)
        {
            case "invoiceNo": q =  "SELECT  AutoGenerate.Prefix, letter_formats.letter_format, letter_formats.subject, ReportImage.ImageData, "+ 
                                   " m_libinfo.Lib_Nm, m_libinfo.Addr1, m_libinfo.Addr2,   m_libinfo.City, m_libinfo.PIN, m_libinfo.email, a_invoice.a_i_no, "+
                                   " a_invoice.a_i_ref_no, a_invoice.a_i_dt, a_invoice.a_i_total, a_supplier_detail.a_s_name,  a_supplier_detail.a_s_city,"+
                                   " m_libinfo.Uni_Nm, AutoGenerate.LastSeed FROM a_invoice, a_payment, a_supplier_detail , ReportImage, AutoGenerate, letter_formats, "+
                                   " m_libinfo,a_ordermaster   WHERE a_invoice.a_i_po_no = a_ordermaster.a_o_po_no and a_ordermaster.a_o_sup_cd = a_supplier_detail.a_s_cd  and "+
                                   " letter_name='Fwd_letter_acq' and ReportImage.ID=1 and AutoGenerate.Name='Acquisition Forward to Account Reference No' and a_payment.a_p_pay_id=a_invoice.a_p_pay_id  "+
                                   " and  a_invoice.a_i_no = '"+paramValue+"' "; 
                                    break;
            case "supplierCd":  q = "SELECT  AutoGenerate.Prefix, letter_formats.letter_format, letter_formats.subject, ReportImage.ImageData, "+ 
                                   " m_libinfo.Lib_Nm, m_libinfo.Addr1, m_libinfo.Addr2,   m_libinfo.City, m_libinfo.PIN, m_libinfo.email, a_invoice.a_i_no, "+
                                   " a_invoice.a_i_ref_no, a_invoice.a_i_dt, a_invoice.a_i_total, a_supplier_detail.a_s_name,  a_supplier_detail.a_s_city,"+
                                   " m_libinfo.Uni_Nm, AutoGenerate.LastSeed FROM a_invoice, a_payment, a_supplier_detail , ReportImage, AutoGenerate, letter_formats, "+
                                   " m_libinfo,a_ordermaster   WHERE a_invoice.a_i_po_no = a_ordermaster.a_o_po_no and a_ordermaster.a_o_sup_cd = a_supplier_detail.a_s_cd  and "+
                                   " letter_name='Fwd_letter_acq' and ReportImage.ID=1 and AutoGenerate.Name='Acquisition Forward to Account Reference No' and a_payment.a_p_pay_id=a_invoice.a_p_pay_id  "+
                                   " and  a_supplier_detail.a_s_cd = '"+paramValue+"' "; 
                                    break;
            case "paymentDate":   
                                Date date1 = new java.sql.Date(dateFormat.parse(valuestring[0]).getTime());
                                Date date2 = new java.sql.Date(dateFormat.parse(valuestring[1]).getTime());
                                q = "SELECT  AutoGenerate.Prefix, letter_formats.letter_format, letter_formats.subject, ReportImage.ImageData, "+ 
                                   " m_libinfo.Lib_Nm, m_libinfo.Addr1, m_libinfo.Addr2,   m_libinfo.City, m_libinfo.PIN, m_libinfo.email, a_invoice.a_i_no, "+
                                   " a_invoice.a_i_ref_no, a_invoice.a_i_dt, a_invoice.a_i_total, a_supplier_detail.a_s_name,  a_supplier_detail.a_s_city,"+
                                   " m_libinfo.Uni_Nm, AutoGenerate.LastSeed FROM a_invoice, a_payment, a_supplier_detail , ReportImage, AutoGenerate, letter_formats, "+
                                   " m_libinfo,a_ordermaster   WHERE a_invoice.a_i_po_no = a_ordermaster.a_o_po_no and a_ordermaster.a_o_sup_cd = a_supplier_detail.a_s_cd  and "+
                                   " letter_name='Fwd_letter_acq' and ReportImage.ID=1 and AutoGenerate.Name='Acquisition Forward to Account Reference No' and a_payment.a_p_pay_id=a_invoice.a_p_pay_id  "+
                                   " and  (a_p_cheque_dt>'"+date1+"' or a_p_cheque_dt<'"+date2+"') "; 
                                    break;
        }
        System.out.println("query  "+q);
        List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        
        return result;
    }
    
     // used in forwarding to account
//    @GET
//    @Path("getDataForForwardToAccountCriteria/{paramName}/{paramValue}")
//    @Produces({"application/xml", "application/json"})
//    public List<Object> getDataForForwardToAccountCriteria(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) throws ParseException {
//        String q="";
//        List<Object> results = null;
//        String valuestring[] = paramValue.split(",");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        
//      //  String q1 = "";
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Object> q1 = cb.createQuery(Object.class);
//        
//        Root<Autogenerate> r1 = q1.from(Autogenerate.class);
//        Root<LetterFormats> r2 = q1.from(LetterFormats.class);
//        Root<Reportimage> r3 = q1.from(Reportimage.class);
//        Root<MLibinfo> r4 = q1.from(MLibinfo.class);
//        Root<AInvoice> r5 = q1.from(AInvoice.class);
//       // Root<AOrdermaster> r6 = q1.from(AOrdermaster.class);
//        
//        Join<AOrdermaster,AInvoice> j1 = r5.join("aIPoNo");
//        Join<AOrdermaster, ASupplierDetail> j2 = j1.join("aOSupCd");
//        Join<APayment,AInvoice> j3 =  r5.join("aPPayId");
//        
//        q1.multiselect(r1.get("prefix"), r2.get("letterFormat"), r2.get("subject"), r3.get("imageData"),
//                r4.get("libNm"), r4.get("addr1"), r4.get("addr2"), r4.get("city"), r4.get("pin"), r4.get("email"),
//                r5.get("aINo"), r5.get("aIRefNo"), r5.get("aIDt"), r5.get("aITotal"), j2.get("aSName"), j2.get("aSCity"),
//                r4.get("uniNm"), r1.get("lastSeed"));
//
//         Predicate p1 = cb.equal(r2.get("letterName"), "Fwd_letter_acq");
//         Predicate p2 = cb.and(cb.equal(r3.get("id"), "1"));
//         Predicate p3 = cb.and(cb.equal(r1.get("name"), "Acquisition Forward to Account Reference No"));
//         Predicate p4 = null;
//         Predicate p5 = null;
//          switch(paramName)
//        {
//              case "invoiceNo":
//                  p4 = cb.and(cb.equal(r5.get("aINo"), valuestring[0]));
//                  break;
//              case "supplierCd":
//                  p4 = cb.and(cb.equal(j2.get("aSCd"), valuestring[0]));
//                  break;
//              case "paymentDate":
//                  Date date1 = new java.sql.Date(dateFormat.parse(valuestring[0]).getTime());
//                  Date date2 = new java.sql.Date(dateFormat.parse(valuestring[1]).getTime());
//                  Expression<Date> e1 = r5.get("aPChequeDt");
//                  p4 = cb.and(cb.greaterThan(e1, date1));
//                  p5 = cb.or(cb.lessThan(e1, date2));
//                  break;
//         }
//         
//        q1.where(p1, p2, p3 , p4, p5);
//        results = em.createQuery(q1).getResultList();
//        return results;
//    }
    
}
