/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl.service;

import com.sun.xml.ws.security.opt.impl.crypto.SSBData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
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
import javax.ws.rs.core.Form;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import soul.circulation.MMember;
import soul.serialControl.SMst;
import soul.serialControl.SPayment;
import soul.serialControl.SRequest;
import soul.serialControl.SSchedule;
import soul.serialControl.SSubInv;
import soul.serialControl.SSubInvdetail;
import soul.serialControl.SSubscription;
import soul.serial_master.SInvoice;
import soul.serial_master.SMode;
import soul.serial_master.service.SInvoiceFacadeREST;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.serialcontrol.spayment")
public class SPaymentFacadeREST extends AbstractFacade<SPayment> {

    @EJB
    private SSubInvFacadeREST sSubInvFacadeREST;
    @EJB
    private SInvoiceFacadeREST sInvoiceFacadeREST;
    @EJB
    private SSubInvdetailFacadeREST sSubInvdetailFacadeREST;
    @EJB
    private SMstFacadeREST sMstFacadeREST;

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    StringReader reader;
    JsonReader jsonReader;
    JsonObject jsonObject;

    public SPaymentFacadeREST() {
        super(SPayment.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(SPayment entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(SPayment entity) {
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
    public SPayment find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SPayment> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SPayment> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
    @GET
    @Path("viewers/view/rcpt/data")
    public String countAll() {
        SSchedule s = new SSchedule();
        Query query = getEntityManager().createNativeQuery(s.getRcpts());
        int result = query.executeUpdate();
        return "";
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

    @POST
    @Path("savePayment")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain", "application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String savePayment(Form form, @FormParam("rows") List<String> row,
            @Pattern(regexp = "^[a-zA-Z ]*$", message = "{string.pattern}") @FormParam("paymentBy") String paymentBy, 
            @Pattern(regexp = "^[a-zA-Z ]*$", message = "{string.pattern}") @FormParam("bankName") String bankName,
            @Pattern(regexp = "^[a-zA-Z ]*$", message = "{string.pattern}") @FormParam("branchName") String branchName, 
            @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "{string.pattern}") @FormParam("chequeDdNo") String payModeNo,
            @FormParam("paymentDate") String paymentDate, @FormParam("totAmount") BigDecimal totAmount) {
        // 1. Update SSubInv
        // 2. Update SMst Entry
        // 3. Insert into s_payment
        //**********************************************
        System.out.println("rows  "+row);
        System.out.println("row..... " + row.size());
        String[] rows = new String[row.size()];
        rows = row.toArray(rows);

        SSubInv inv;
        String status;
        SMst mst;
        String output = null;
        BigDecimal payingAmount, paidAmount, remainingAmount, netAmount;
        System.out.println("rows:" + rows);
        System.out.println("1:" + rows.length);
        for (int i = 0; i < rows.length; i++) {
            reader = new StringReader(rows[i]);
            jsonReader = Json.createReader(reader);
            jsonObject = jsonReader.readObject();
            System.out.println("rows:" + jsonObject);
            // 1. Update SSubInv
            inv = sSubInvFacadeREST.find(Integer.parseInt(jsonObject.getString("invoiceId")));

            netAmount = new BigDecimal(jsonObject.getString("netAmount"));
            remainingAmount = new BigDecimal(jsonObject.getString("remainingAmount"));
            payingAmount = new BigDecimal(jsonObject.getString("amountPayable"));
            paidAmount = inv.getSInvAmount();
            System.out.println("Before: " + remainingAmount + " , " + paidAmount + " , " + payingAmount);

            paidAmount = paidAmount.add(payingAmount);
            remainingAmount = remainingAmount.subtract(payingAmount);
            System.out.println("After: " + remainingAmount + " , " + paidAmount + " , " + payingAmount);
            if (remainingAmount.compareTo(BigDecimal.ZERO) > 0) {
                status = "I";
            } else {
                status = "P";
            }

            int res = netAmount.compareTo(paidAmount);
            if (res == -1) {
                inv.setSInvRemain(BigDecimal.ZERO);
                inv.setSInvAmount(netAmount);
            } else {
                inv.setSInvRemain(remainingAmount);
                inv.setSInvAmount(paidAmount);
            }

            inv.setSInvStatus(status);

            sSubInvFacadeREST.edit(inv);

            // 2. Update SMst Entry
            List<SSubInvdetail> invdetailList;
            SSubInvdetail invDetail;
            List<SMst> mstList;
            SSubscription subscription;
            // GenericType<List<SSubInvdetail>> invDetailGenericType = new GenericType<List<SSubInvdetail>>(){};
            // GenericType<List<SMst>> sMstGenericType = new GenericType<List<SMst>>(){};
            invdetailList = sSubInvdetailFacadeREST.findBy("findBySInvRecid", jsonObject.getString("invoiceId"));
            System.out.println(invdetailList);
            // invDetailList to get orderNo
            for (int k = 0; k < invdetailList.size(); k++) {
                // for each mst entry with given orderNo and invoiceNo update is done
                System.out.println("aaaaaaaaaaaa   " + invdetailList.get(k));
                invDetail = invdetailList.get(k);
                subscription = invDetail.getSSubscription();
                mstList = sMstFacadeREST.findBy("findBySOrderNoANDSInvNo", invDetail.getSSubInvdetailPK().getSInvOrderNo() + "," + invDetail.getSSubInvdetailPK().getSInvNo());
                for (int j = 0; j < mstList.size(); j++) {
                    mst = mstList.get(j);
                    if (subscription.getSType().equals("F")) {
                        mst.setSMstStatus(status);
                    } else {
                        mst.setSMstStatus("O");
                    }

                    sMstFacadeREST.edit(mst);
                }
            }

            // 3. Insert into s_payment
            SPayment payment = new SPayment();

            payment.setSPayInvid(inv);
            System.out.println("paymentDate ..............  "+paymentDate);
            try {
                payment.setSPayDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paymentDate));
            } catch (ParseException ex) {
                Logger.getLogger(SPaymentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            totAmount = payingAmount;
            payment.setSPayAmt(totAmount);
            payment.setSPayMode(paymentBy);
            if (!payment.getSPayMode().equals("cash")) {  //if payment metod is other than cash
                payment.setSPayBank(bankName);
                payment.setSPayBranch(branchName);
                payment.setSPayModeno(payModeNo);
            }
            create(payment);
            output = "Payment Done.";
        }
        return output;
    }

//    public SPayment getSPayment(SSubInv inv, BigDecimal amount){
//        SPayment payment = new SPayment();
//
//        payment.setSPayInvid(inv);
//        try {
//            payment.setSPayDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paymentDate));
//        } catch (ParseException ex) {
//            Logger.getLogger(SPaymentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        payment.setSPayAmt(totAmount);
//        payment.setSPayMode(paymentBy);
//        if(!payment.getSPayMode().equals("cash")){  //if payment metod is other than cash
//            payment.setSPayBank(bankName);
//            payment.setSPayBranch(branchName);
//            payment.setSPayModeno(payModeNo);
//        }
//
//        return payment;
//    }
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<Object> retrieveAll() {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;
        q = "SELECT DISTINCT s_pay_invid as invoiceID FROM s_payment";
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();

        return result;
    }

    @GET
    @Path("retrieveAllP")
    @Produces({"application/xml", "application/json"})
    public List<SPayment> retrieveAllP() {
        List<SPayment> getAll = findBy("retrieveAllP", "null");
        return getAll;
    }

    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<SPayment> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        //System.out.println("dates : "+values);
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        List<String> status = new ArrayList<>();

        switch (query) {
            case "retrieveAllP":    //No Parameter to set
                return super.findBy("SPayment." + query);

        }
        return super.findBy("SPayment." + query, valueList);
    }

    public void jsontoxml() throws JAXBException {
        JAXBContext contextObj = JAXBContext.newInstance(SPayment.class);

        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        Result writer = null;
        marshallerObj.marshal(em, writer);
    }

    public final class XStreamTranslator {

        private XStream xstream = null;

        private XStreamTranslator() {
            xstream = new XStream();
            xstream.ignoreUnknownElements();
        }

        /**
         * Convert a any given Object to a XML String
         *
         * @param object
         * @return
         */
        public String toXMLString(Object object) {
            return xstream.toXML(object);
        }
    }

    // Payment Report
    //This methods return the details about payment for report generation    
    @GET
    @Path("paymentReport/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> paymentReport(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {

            case "budgetWise":
                q = "SELECT distinct s_payment.s_pay_no, s_payment.s_pay_dt, s_payment.s_pay_invid, s_payment.s_pay_amt, s_payment.s_pay_bank, \n"
                        + "s_payment.s_pay_branch,s_payment.s_pay_mode, s_payment.s_pay_remark, s_payment.s_pay_modeno, s_sub_invdetail.s_inv_order_no, \n"
                        + "s_sub_inv.s_inv_dt,s_subscription.s_order_dt \n"
                        + "FROM s_payment, s_sub_invdetail, s_sub_inv, s_subscription,s_mst \n"
                        + "WHERE s_payment.s_pay_invid = s_sub_invdetail.s_inv_recid AND s_sub_invdetail.s_inv_no = s_sub_inv.s_inv_no AND \n"
                        + "s_sub_invdetail.s_inv_order_no = s_subscription.s_order_no and s_mst.s_order_no= s_subscription.s_order_no and s_mst.s_buget_cd = '" + Paramvalue + "'";
                break;

            case "supplierWise":
                //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                q = "   SELECT distinct s_payment.s_pay_no, s_payment.s_pay_dt, s_payment.s_pay_invid, s_payment.s_pay_amt, s_payment.s_pay_bank, \n"
                        + "   s_payment.s_pay_branch,s_payment.s_pay_mode, s_payment.s_pay_remark, s_payment.s_pay_modeno, s_sub_invdetail.s_inv_order_no, \n"
                        + "   s_sub_inv.s_inv_dt,s_subscription.s_order_dt \n"
                        + "   FROM s_payment, s_sub_invdetail, s_sub_inv, s_subscription \n"
                        + "   WHERE s_payment.s_pay_invid = s_sub_invdetail.s_inv_recid AND s_sub_invdetail.s_inv_no = s_sub_inv.s_inv_no AND \n"
                        + "   s_sub_invdetail.s_inv_order_no = s_subscription.s_order_no and s_subscription.s_sup_cd in (select a_s_cd from s_supplier_detail where a_s_name Like '" + Paramvalue + "%')";
                break;

            case "dateBetween":
                //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                q = "SELECT distinct s_payment.s_pay_no, s_payment.s_pay_dt, s_payment.s_pay_invid, s_payment.s_pay_amt, s_payment.s_pay_bank,\n"
                        + " s_payment.s_pay_branch,s_payment.s_pay_mode, s_payment.s_pay_remark, s_payment.s_pay_modeno, s_sub_invdetail.s_inv_order_no,\n"
                        + "  s_sub_inv.s_inv_dt,s_subscription.s_order_dt \n"
                        + "  FROM s_payment, s_sub_invdetail, s_sub_inv, s_subscription \n"
                        + "  WHERE s_payment.s_pay_invid = s_sub_invdetail.s_inv_recid AND s_sub_invdetail.s_inv_no = s_sub_inv.s_inv_no AND\n"
                        + "   s_sub_invdetail.s_inv_order_no = s_subscription.s_order_no and (s_pay_dt>='" + valueString[0] + "' and s_pay_dt <= '" + valueString[1] + "')";
                break;
        }
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }

    //Basic setup of service is done
    //check for inserting inv id in s_payment, as it is reffered to id in s_sub_inv table
    //define some relations or use existing find by
    @POST
    @Path("bindingPayment")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain", "application/xml", "application/json"})
    public String bindingPayment(@FormParam("invId") Integer invId, 
            @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{string.pattern}") @FormParam("binderCode") String binderCode,
            @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{string.pattern}") @FormParam("rcptNo") String rcptNo, 
            @FormParam("rcptDate") String rcptDate,
            @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{string.pattern}") @FormParam("forwardNo") String forwardNo, 
            @FormParam("forwardDate") String forwardDate, 
            @Pattern(regexp = "^[a-zA-Z ]*$", message = "{string.pattern}") @FormParam("forwardMode") String forwardMode,
            @FormParam("chqDrftDate") String chqDrftDate, 
            @FormParam("forwardRemark") String forwardRemark,
            @FormParam("payingAmount") BigDecimal payingAmount, 
            @FormParam("paymentDate") String paymentDate,
            @Pattern(regexp = "^[a-zA-Z ]*$", message = "{string.pattern}") @FormParam("paymentBy") String paymentBy, 
            @Pattern(regexp = "^[a-zA-Z ]*$", message = "{string.pattern}") @FormParam("bankName") String bankName, 
            @FormParam("bankCharge") BigDecimal bankCharge,
            @Pattern(regexp = "^[a-zA-Z ]*$", message = "{string.pattern}") @FormParam("bankBranch") String bankBranch, 
            @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "{string.pattern}") @FormParam("paymentModeNo") String paymentModeNo,
            @FormParam("ackNo") String ackNo, @FormParam("ackDate") String ackDate, @FormParam("ackRemark") String ackRemark) {
        
        String regexInvoice = Integer.toString(invId);
        for(int i = 0; i < regexInvoice.length(); i++){
            if(!regexInvoice.matches("^[0-9]*$")){
                return "Something is wrong with invoice id!";
            }
        }
        
        String output = null;
        SPayment payment = new SPayment();
        SSubInv subinv = new SSubInv();
        SInvoice sinvoice = sInvoiceFacadeREST.find(invId);
        System.out.println("Invoice details: " + sinvoice);
        
        BigDecimal totalAmount = sinvoice.getSInvNetamt();
        List<SPayment> payNo = findAll();
        
        List<Integer> s_pay = new ArrayList<Integer>();
        for(int i=0; i<payNo.size(); i++){
            s_pay.add(payNo.get(i).getSPayNo());
        }
        Integer s_pay_no = Collections.max(s_pay) + 1;
        

        
        
        if (payingAmount.compareTo(totalAmount) == 0 && sinvoice.getSInvType() == "I") {
            
            subinv.setSInvId(invId);
            subinv.setSInvNo(sinvoice.getSInvNo());
            subinv.setSInvParty(binderCode);
            subinv.setSInvDt(sinvoice.getSInvDt());
            try {
                subinv.setSInvRcptDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paymentDate));
            } catch (ParseException ex) {
                Logger.getLogger(SPaymentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            subinv.setSInvStatus("P");
            subinv.setSInvNetAmt(totalAmount);
            subinv.setSInvAmount(payingAmount);
            subinv.setSInvRemain(totalAmount.subtract(payingAmount));
            sSubInvFacadeREST.create(subinv);
            
            payment.setSPayInvid(subinv);
            try {
                payment.setSPayDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paymentDate));
            } catch (ParseException ex) {
                Logger.getLogger(SPaymentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            payment.setSPayAmt(payingAmount);
            payment.setSPayMode(paymentBy);
            if (!payment.getSPayMode().equals("cash")) {  //if payment metod is other than cash
                payment.setSPayBank(bankName);
                payment.setSPayBranch(bankBranch);
                payment.setSPayModeno(paymentModeNo);
            }
            try {
                payment.setSPayForwardDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(forwardDate));
            } catch (ParseException ex) {
                Logger.getLogger(SPaymentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            payment.setSPayForwardMode(forwardNo);
            payment.setSPayForwardNo(forwardNo);
            payment.setSPayRemark(paymentBy);
            
            payment.setSPayAckNo(ackNo);
            try {
                payment.setSPayAckDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ackDate));
            } catch (ParseException ex) {
                Logger.getLogger(SPaymentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            payment.setSPayAckRemark(ackRemark);
            payment.setSPayBankCharge(bankCharge);
            int count = super.count();
            
            create(payment);
            sinvoice.setSInvType("P");
            sInvoiceFacadeREST.edit(sinvoice);
            
            if (count == super.count()) {
                output = "Something went wrong, failed to make payment";
            } else {
                output = "Payment of " + sinvoice.getSInvNo() + " done. Amount"+payingAmount;
            }
        } else if (payingAmount.compareTo(totalAmount) == -1) {
            output = "Partial payment is not allowed, kindly try again and pay full amount mentioned in invoice.";
        } else if (payingAmount.compareTo(totalAmount) == 1) {
            output = "Paying amount is greater than invoice amount";
        } else {
            output = "Payment of " + sinvoice.getSInvNo() + " is already done.";
        }

        return output;
    }
}
