/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl.service;

import java.io.StringReader;
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
import javax.ws.rs.core.PathSegment;
import soul.general_master.ABudget;
import soul.general_master.ACurrency;
import soul.general_master.service.ABudgetFacadeREST;
import soul.serialControl.SMst;
import soul.serialControl.SSubInv;
import soul.serialControl.SSubInvdetail;
import soul.serialControl.SSubInvdetailPK;
import soul.general_master.service.ACurrencyFacadeREST;
import soul.general_master.service.AutogenerateFacadeREST;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.serialcontrol.ssubinvdetail")
public class SSubInvdetailFacadeREST extends AbstractFacade<SSubInvdetail> {

    @EJB
    private SSubInvFacadeREST sSubInvFacadeREST;
    @EJB
    private SMstFacadeREST sMstFacadeREST;
    @EJB
    private ABudgetFacadeREST aBudgetFacadeREST;
    @EJB
    private ACurrencyFacadeREST aCurrencyFacadeREST;
    @EJB
    private AutogenerateFacadeREST autogenerateFacadeREST;

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    StringReader reader;
    JsonReader jsonReader;
    JsonObject jsonObject;
    private String[] recdIds;

    private SSubInvdetailPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;sInvNo=sInvNoValue;sInvRecid=sInvRecidValue;sInvOrderNo=sInvOrderNoValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.serialControl.SSubInvdetailPK key = new soul.serialControl.SSubInvdetailPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> sInvNo = map.get("sInvNo");
        if (sInvNo != null && !sInvNo.isEmpty()) {
            key.setSInvNo(sInvNo.get(0));
        }
        java.util.List<String> sInvRecid = map.get("sInvRecid");
        if (sInvRecid != null && !sInvRecid.isEmpty()) {
            key.setSInvRecid(new java.lang.Integer(sInvRecid.get(0)));
        }
        java.util.List<String> sInvOrderNo = map.get("sInvOrderNo");
        if (sInvOrderNo != null && !sInvOrderNo.isEmpty()) {
            key.setSInvOrderNo(sInvOrderNo.get(0));
        }
        return key;
    }

    public SSubInvdetailFacadeREST() {
        super(SSubInvdetail.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(SSubInvdetail entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(SSubInvdetail entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.serialControl.SSubInvdetailPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public SSubInvdetail find(@PathParam("id") PathSegment id) {
        soul.serialControl.SSubInvdetailPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SSubInvdetail> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SSubInvdetail> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<SSubInvdetail> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();

        switch (query) {
            case "findBySInvRecid":
                valueList.add(Integer.parseInt(valueString[0]));
                break;

            default:
                valueList.add(valueString[0]);
                break;
            //

        }
        return super.findBy("SSubInvdetail." + query, valueList);
    }

    //Refund process
    //This methods return the details about individual orders for which payment has done and to be processed for refund
    @GET
    @Path("getOrderDetailsforRefund/{orderNo}")
    @Produces({"application/xml", "application/json"})
    public List<SSubInvdetail> getOrderDetailsforRefund(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{orderNo.pattern}") @PathParam("orderNo") String orderNo) {
        List<SSubInvdetail> getAll = null;
        getAll = findBy("getOrderDetailsforRefund", orderNo);
        return getAll;
    }

    //Service to generate invoice
    //used in invoice process in payment submodule
    @POST
    @Path("generateInvoice")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String generateInvoice(@FormParam("recIds") String recIds,
            @FormParam("invoiceNo") String invoiceNo, @FormParam("invoiceDate") String invoiceDate,
            @FormParam("sInvpostageCharges") BigDecimal sInvPostageCharges,
            @FormParam("sInvhandlingCharges") BigDecimal sInvhandlingCharges,
            @FormParam("invoiceRcptDate") String invoiceRcptDate, @FormParam("postageCharge") BigDecimal postageCharge,
            @FormParam("handlingCharges") BigDecimal handlingCharges, @FormParam("totalNetAmount") String totalNetAmount,
            @FormParam("orderType") String orderType, @FormParam("invoiceAllOrder") String invoiceAllOrder,
            @FormParam("discount") String discount,
            @FormParam("currency") String currency) {
        //**********************************************
        // 1. Insert into s_sub_inv table
        // 3. Insert into s_sub_invdetail
        // 2. Update SMst Entry
        // 4. Update Budget: Subtract old total amount from ABPoraised and Add new total amount to expenseAmt
        // 5. Update Budget for misc charges: Add misc charges to expenseAmt
        //**********************************************
        recdIds = recIds.split(",");
        SMst sMst = null;
        ACurrency currencyCode;
        ABudget budget;
        SSubInv sSubInv = new SSubInv();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            currencyCode = aCurrencyFacadeREST.find(currency);
        } catch (NullPointerException e) {
            return "Invalid currency code.";
        }
        BigDecimal netAmount;
        BigDecimal invoiceAmount;
        BigDecimal totalCharge = BigDecimal.ZERO;

        for (String recId : recdIds) {
            sMst = sMstFacadeREST.find(recId);

            sSubInv.setSInvHandlingCharge(sInvhandlingCharges);
            sSubInv.setSInvPostageCharge(sInvPostageCharges);

            netAmount = sMst.getSPrice().add(sInvhandlingCharges.add(sInvPostageCharges));
            invoiceAmount = netAmount.multiply(currencyCode.getACRate());
            totalCharge = totalCharge.add(invoiceAmount);

        }
        try {
            sSubInv.setSInvDt(dateFormat.parse(invoiceDate));
            sSubInv.setSInvRcptDt(dateFormat.parse(invoiceRcptDate));
        } catch (ParseException ex) {
            sSubInv.setSInvDt(new Date());
            sSubInv.setSInvRcptDt(new Date());
            Logger.getLogger(SSubInvdetailFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        sSubInv.setSInvNo(invoiceNo);

        sSubInv.setSInvParty(sMst.getSSupCd());
        sSubInv.setSInvStatus("I");

        System.out.println("Net Amount: " + totalCharge);
        System.out.println("Invoice Amount: " + totalCharge);
        sSubInv.setSInvNetAmt(totalCharge);                  //Need to be verified   set to total in old code
        sSubInv.setSInvAmount(BigDecimal.ZERO);                                 //Need to be verified   set to 0
        sSubInv.setSInvRemain(totalCharge);                  //Need to be verified   set to total in old code
        sSubInvFacadeREST.createAndGet(sSubInv);

        //  1. Insert into s_sub_inv table
        //sub_inv_detail entry
        SSubInvdetail sSubInvdetail = new SSubInvdetail();
        SSubInvdetailPK invdetailPK = new SSubInvdetailPK();

        invdetailPK.setSInvNo(invoiceNo);
        invdetailPK.setSInvOrderNo(sMst.getSOrderNo());
        invdetailPK.setSInvRecid(sSubInv.getSInvId());

        sSubInvdetail.setSSubInvdetailPK(invdetailPK);

        sSubInvdetail.setSInvPostage(postageCharge);
        sSubInvdetail.setSInvHandling(handlingCharges);

        SMst dMst = sMstFacadeREST.find(recdIds[0]);
        Date startDate = dMst.getSStDt();
        Date endDate = dMst.getSEnDt();
        sSubInvdetail.setSInvStDt(startDate);
        sSubInvdetail.setSInvEnDt(endDate);

        if (invoiceAllOrder.equals("Y")) {
            sSubInvdetail.setSInvStVol(sMst.getSStIss());
            sSubInvdetail.setSInvEnVol(sMst.getSEnVol());
            sSubInvdetail.setSInvStIss(sMst.getSStIss());
            sSubInvdetail.setSInvEnIss(sMst.getSEnIss());
        }

        sSubInvdetail.setSInvPrice(sMst.getSPrice());

        sSubInvdetail.setSInvCurrency(currencyCode.getACCd());
        sSubInvdetail.setSInvConvRate(currencyCode.getACRate());
        //BigDecimal chargesTotal = sSubInvdetail.getSInvPostage().add(sSubInvdetail.getSInvHandling());
        //BigDecimal calculatedPrice = sSubInvdetail.getSInvPrice().add(chargesTotal);
        sSubInvdetail.setSInvNet(sSubInv.getSInvNetAmt());
        sSubInvdetail.setSInvNetRs(sSubInv.getSInvNetAmt());
        sSubInvdetail.setSInvDiscount(new BigDecimal(discount));
        createAndGet(sSubInvdetail);
        //sub_inv_detail finished

        BigDecimal oldPrice, amount;
        String sStatus, sMstStatus;

        for (String recId : recdIds) {
            // 2. Update SMst Entry
            oldPrice = BigDecimal.ZERO;
            sMst = sMstFacadeREST.find(recId);

            //Saving old Price so that it can be used to update budget value appropriately
            oldPrice = sMst.getSPrice();
            oldPrice = oldPrice.multiply(sMst.getSConvRate());
            oldPrice = oldPrice.multiply(new BigDecimal(sMst.getSApprCopy()));

            if ("F".equals(orderType)) {
                sStatus = "N";
                sMstStatus = "I";
            } else {
                sStatus = "Y";
                sMstStatus = "O";
            }

            sMst.setSInvNo(invoiceNo);

            if (invoiceAllOrder.equals("Y")) {
                sMst.setSStatus(sStatus);
                sMst.setSMstStatus(sMstStatus);
                sMst.setSPrice(oldPrice);
                sMst.setSCurrency(sSubInvdetail.getSInvCurrency());
                sMst.setSConvRate(sSubInvdetail.getSInvConvRate());
            } else {
                sMst.setSStatus(sStatus);
                sMst.setSMstStatus(sMstStatus);
            }
            sMstFacadeREST.edit(sMst);

            // 4. Update Budget: Subtract old total amount from ABPoraised and Add new total amount to expenseAmt
            budget = aBudgetFacadeREST.find(Integer.parseInt(sMst.getSBugetCd()));
            amount = budget.getABPoraisedAmt();         //amount = poraisedAmt
            amount = amount.subtract(oldPrice);         //amount = poraised - oldPrice
            budget.setABPoraisedAmt(amount);

            amount = budget.getABExpAmt();              //amount = expAmt
            amount = amount.add(sSubInvdetail.getSInvNet());    //amount = expAmt + newPrice
            budget.setABExpAmt(amount);

            aBudgetFacadeREST.edit(budget);
        }
        // 5. Update Budget for misc charges: Add misc charges to expenseAmt

        amount = handlingCharges.add(postageCharge);

        if (amount.compareTo(BigDecimal.ZERO) == 1) {    //if postage+handling is greater than zero
            budget = aBudgetFacadeREST.find(Integer.parseInt(sMst.getSBugetCd()));
            amount = amount.add(budget.getABExpAmt());      //amount = amount(postage+handling) + budgetExpAmt ie. new expAmt
            budget.setABExpAmt(amount);
            aBudgetFacadeREST.edit(budget);
        }
        return "Invoice Generated.";
    }
    /// create invoice - end ////////////////////////////////

    // Forward letter to account
    //This methods return the details about letter to forward to account
    //used in payment module
    @GET
    @Path("forwardToAccount/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> forwardToAccount(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {

            case "invoiceNo":
                q = "SELECT AutoGenerate.Prefix, AutoGenerate.LastSeed, letter_formats.subject, letter_formats.letter_format, ReportImage.ImageData, m_libinfo.Lib_Nm,  m_libinfo.Uni_Nm,\n"
                        + " m_libinfo.Librarian_nm, m_libinfo.Addr1, m_libinfo.Addr2, m_libinfo.City, m_libinfo.PIN, m_libinfo.Phone, m_libinfo.fax, m_libinfo.email,\n"
                        + "    s_sub_inv.s_inv_dt, s_sub_inv.s_inv_no, s_sub_inv.s_inv_net_amt, s_supplier.a_s_nm AS invparty, s_supplier_detail.a_s_city AS invsupcity\n"
                        + "	  FROM s_sub_inv, s_sub_invdetail, s_supplier_detail, s_supplier, ReportImage, AutoGenerate, letter_formats, m_libinfo, s_payment  \n"
                        + "	  WHERE s_sub_inv.s_inv_no = s_sub_invdetail.s_inv_no And s_sub_inv.s_inv_party = s_supplier_detail.a_s_cd And \n"
                        + "	  s_supplier_detail.a_s_cd = s_supplier.a_s_cd  and letter_name like 'fwd_acc_scm%' and ReportImage.ID=1 and \n"
                        + "	  AutoGenerate.Name like 'Serial Forward to Account No%'   and s_sub_invdetail.s_inv_no = s_sub_inv.s_inv_no and \n"
                        + "	  s_inv_party = s_supplier.a_s_cd  and s_pay_invid = s_sub_inv.s_inv_id   and s_sub_inv.s_inv_no = '" + valueString[0] + "' ";
                break;

            case "betweenPaymentDate":
                q = "SELECT AutoGenerate.Prefix, AutoGenerate.LastSeed, letter_formats.subject, letter_formats.letter_format, ReportImage.ImageData, m_libinfo.Lib_Nm,  \n"
                        + "m_libinfo.Uni_Nm, m_libinfo.Librarian_nm, m_libinfo.Addr1, m_libinfo.Addr2, m_libinfo.City, m_libinfo.PIN, m_libinfo.Phone, m_libinfo.fax, m_libinfo.email,   \n"
                        + "s_sub_inv.s_inv_dt, s_sub_inv.s_inv_no, s_sub_inv.s_inv_net_amt, s_supplier.a_s_nm AS invparty, s_supplier_detail.a_s_city AS invsupcity  \n"
                        + "FROM s_sub_inv, s_sub_invdetail, s_supplier_detail, s_supplier, ReportImage, AutoGenerate, letter_formats, m_libinfo, s_payment \n"
                        + "WHERE s_sub_inv.s_inv_no = s_sub_invdetail.s_inv_no And s_sub_inv.s_inv_party = s_supplier_detail.a_s_cd And \n"
                        + "s_supplier_detail.a_s_cd = s_supplier.a_s_cd  and letter_name like 'fwd_acc_scm%' and ReportImage.ID=1 and \n"
                        + "AutoGenerate.Name like 'Serial Forward to Account No%'   and s_sub_invdetail.s_inv_no=s_sub_inv.s_inv_no and s_inv_party = s_supplier.a_s_cd  \n"
                        + "and s_pay_invid = s_sub_inv.s_inv_id   and  (s_pay_dt > '" + valueString[0] + "' or s_pay_dt < '" + valueString[1] + "')";
                break;

            case "supplierWise":
                q = "      \n"
                        + "SELECT AutoGenerate.Prefix, AutoGenerate.LastSeed, letter_formats.subject, letter_formats.letter_format, ReportImage.ImageData, m_libinfo.Lib_Nm,  m_libinfo.Uni_Nm,\n"
                        + "m_libinfo.Librarian_nm, m_libinfo.Addr1, m_libinfo.Addr2, m_libinfo.City, m_libinfo.PIN, m_libinfo.Phone, m_libinfo.fax, m_libinfo.email,   \n"
                        + "s_sub_inv.s_inv_dt, s_sub_inv.s_inv_no, s_sub_inv.s_inv_net_amt, s_supplier.a_s_nm AS invparty, s_supplier_detail.a_s_city AS invsupcity  \n"
                        + "FROM s_sub_inv, s_sub_invdetail, s_supplier_detail, s_supplier, ReportImage, AutoGenerate, letter_formats, m_libinfo, s_payment \n"
                        + " WHERE s_sub_inv.s_inv_no = s_sub_invdetail.s_inv_no And s_sub_inv.s_inv_party = s_supplier_detail.a_s_cd And\n"
                        + " s_supplier_detail.a_s_cd = s_supplier.a_s_cd  and letter_name like 'fwd_acc_scm%' and ReportImage.ID=1 and\n"
                        + " AutoGenerate.Name like 'Serial Forward to Account No%'   and s_sub_invdetail.s_inv_no=s_sub_inv.s_inv_no \n"
                        + " and s_inv_party = s_supplier.a_s_cd  and s_pay_invid = s_sub_inv.s_inv_id   and s_sub_inv.s_inv_party in(select a_s_cd from s_supplier where a_s_cd ='" + valueString[0] + "') ";
                break;
        }
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        ArrayList<String> list = new ArrayList<String>();
        list.add("Serial Forward to Account No%");
        list.add("Serial Forward to Account");
        for(int i=0; i<list.size(); i++){
            autogenerateFacadeREST.updateAutogenerate(list.get(i));
        }
        return result;
    }

    // Forward letter to account
    //This methods return the details about letter to forward to account
    //used in payment module
    @GET
    @Path("forwardAllToAccount")
    @Produces({"application/xml", "application/json"})
    public List<Object> forwardAllToAccount() throws ParseException {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = "SELECT AutoGenerate.Prefix, AutoGenerate.LastSeed, letter_formats.subject, letter_formats.letter_format, ReportImage.ImageData, m_libinfo.Lib_Nm,  \n"
                + "m_libinfo.Uni_Nm, m_libinfo.Librarian_nm, m_libinfo.Addr1, m_libinfo.Addr2, m_libinfo.City, m_libinfo.PIN, m_libinfo.Phone, m_libinfo.fax, \n"
                + "m_libinfo.email,   s_sub_inv.s_inv_dt, s_sub_inv.s_inv_no, s_sub_inv.s_inv_net_amt, s_supplier.a_s_nm AS invparty, \n"
                + "s_supplier_detail.a_s_city AS invsupcity \n"
                + " FROM s_sub_inv, s_sub_invdetail, s_supplier_detail, s_supplier, ReportImage, AutoGenerate, letter_formats, m_libinfo, s_payment  \n"
                + " WHERE s_sub_inv.s_inv_no = s_sub_invdetail.s_inv_no And s_sub_inv.s_inv_party = s_supplier_detail.a_s_cd \n"
                + " And s_supplier_detail.a_s_cd = s_supplier.a_s_cd  and  letter_name like 'fwd_acc_scm%' and ReportImage.ID=1 and \n"
                + " AutoGenerate.Name like 'Serial Forward to Account No%'  and s_sub_invdetail.s_inv_no=s_sub_inv.s_inv_no and s_inv_party = s_supplier.a_s_cd  and s_payment.s_pay_invid = s_sub_invdetail.s_inv_recid";
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        ArrayList<String> list = new ArrayList<String>();
        list.add("Serial Forward to Account No");
        list.add("Serial Forward to Account");
        for(int i=0; i<list.size(); i++){
            autogenerateFacadeREST.updateAutogenerate(list.get(i));
        }
        return result;
    }

    // Forward letter to vendor
    //This methods return the details about letter to forward to vendor
    //used in payment module
    @GET
    @Path("forwardToVendor/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> forwardToVendor(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {

            case "invoiceNo":
                q = "SELECT AutoGenerate.Prefix, AutoGenerate.LastSeed, letter_formats.subject, letter_formats.letter_format, ReportImage.ImageData, m_libinfo.Lib_Nm,  m_libinfo.Uni_Nm, \n"
                        + "m_libinfo.Librarian_nm, m_libinfo.Addr1, m_libinfo.Addr2, m_libinfo.City, m_libinfo.PIN, m_libinfo.Phone, m_libinfo.fax, m_libinfo.email,  \n"
                        + "s_sub_inv.s_inv_dt, s_sub_inv.s_inv_no, s_sub_inv.s_inv_net_amt, s_supplier.a_s_nm AS invparty, s_supplier_detail.a_s_city AS invsupcity,  \n"
                        + "s_supplier_detail.a_s_add1, s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3, s_supplier_detail.a_s_pin, s_supplier_detail.a_s_country,  \n"
                        + "s_supplier_detail.a_s_state, s_supplier_detail.a_s_email, s_supplier_detail.a_s_phone, s_supplier_detail.a_s_fax   \n"
                        + "FROM s_payment, s_sub_inv, s_sub_invdetail, s_supplier_detail, s_supplier, ReportImage, AutoGenerate, letter_formats, m_libinfo  \n"
                        + "WHERE s_payment.s_pay_invid = s_sub_invdetail.s_inv_recid and s_sub_inv.s_inv_no = s_sub_invdetail.s_inv_no And s_sub_inv.s_inv_party = s_supplier_detail.a_s_cd \n"
                        + "And s_supplier_detail.a_s_cd = s_supplier.a_s_cd   and letter_name like 'fwd_vendor_scm%' and ReportImage.ID=1 and AutoGenerate.Name like 'Serial Forward to Vendor No%'  and s_sub_inv.s_inv_no = '" + valueString[0] + "' ";
                break;

            case "betweenPaymentDate":
                q = "SELECT AutoGenerate.Prefix, AutoGenerate.LastSeed, letter_formats.subject, letter_formats.letter_format, ReportImage.ImageData, m_libinfo.Lib_Nm,  m_libinfo.Uni_Nm, \n"
                        + "m_libinfo.Librarian_nm, m_libinfo.Addr1, m_libinfo.Addr2, m_libinfo.City, m_libinfo.PIN, m_libinfo.Phone, m_libinfo.fax, m_libinfo.email,  s_sub_inv.s_inv_dt, \n"
                        + "s_sub_inv.s_inv_no, s_sub_inv.s_inv_net_amt, s_supplier.a_s_nm AS invparty, s_supplier_detail.a_s_city AS invsupcity,  s_supplier_detail.a_s_add1, \n"
                        + "s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3, s_supplier_detail.a_s_pin, s_supplier_detail.a_s_country,  s_supplier_detail.a_s_state, \n"
                        + "s_supplier_detail.a_s_email, s_supplier_detail.a_s_phone, s_supplier_detail.a_s_fax   \n"
                        + "FROM s_payment, s_sub_inv, s_sub_invdetail, s_supplier_detail, s_supplier, ReportImage, AutoGenerate, letter_formats, m_libinfo  \n"
                        + "WHERE s_payment.s_pay_invid = s_sub_invdetail.s_inv_recid and s_sub_inv.s_inv_no = s_sub_invdetail.s_inv_no And \n"
                        + "s_sub_inv.s_inv_party = s_supplier_detail.a_s_cd And s_supplier_detail.a_s_cd = s_supplier.a_s_cd   and \n"
                        + "letter_name like 'fwd_vendor_scm%' and ReportImage.ID=1 and AutoGenerate.Name like 'Serial Forward to Vendor No%'  and  (s_pay_dt > '" + valueString[0] + "' or s_pay_dt < '" + valueString[1] + "')";
                break;

            case "supplierWise":
                q = "SELECT AutoGenerate.Prefix, AutoGenerate.LastSeed, letter_formats.subject, letter_formats.letter_format, ReportImage.ImageData, m_libinfo.Lib_Nm,  m_libinfo.Uni_Nm, \n"
                        + "m_libinfo.Librarian_nm, m_libinfo.Addr1, m_libinfo.Addr2, m_libinfo.City, m_libinfo.PIN, m_libinfo.Phone, m_libinfo.fax, m_libinfo.email,  \n"
                        + "s_sub_inv.s_inv_dt, s_sub_inv.s_inv_no, s_sub_inv.s_inv_net_amt, s_supplier.a_s_nm AS invparty, s_supplier_detail.a_s_city AS invsupcity,  \n"
                        + "s_supplier_detail.a_s_add1, s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3, s_supplier_detail.a_s_pin, s_supplier_detail.a_s_country,  \n"
                        + "s_supplier_detail.a_s_state, s_supplier_detail.a_s_email, s_supplier_detail.a_s_phone, s_supplier_detail.a_s_fax   \n"
                        + "FROM s_payment, s_sub_inv, s_sub_invdetail, s_supplier_detail, s_supplier, ReportImage, AutoGenerate, letter_formats, m_libinfo  \n"
                        + "WHERE s_payment.s_pay_invid = s_sub_invdetail.s_inv_recid and s_sub_inv.s_inv_no = s_sub_invdetail.s_inv_no And s_sub_inv.s_inv_party = s_supplier_detail.a_s_cd \n"
                        + "And s_supplier_detail.a_s_cd = s_supplier.a_s_cd   and letter_name like 'fwd_vendor_scm%' and ReportImage.ID=1 and \n"
                        + "AutoGenerate.Name like 'Serial Forward to Vendor No%'  and s_sub_inv.s_inv_party in(select a_s_cd from s_supplier where a_s_cd = '" + valueString[0] + "') ";
                break;
        }
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        ArrayList<String> list = new ArrayList<String>();
        list.add("Serial Forward to Vendor No%");
        list.add("Serial Forward to Vendor");
        
        for(int i=0; i<list.size(); i++){
            autogenerateFacadeREST.updateAutogenerate(list.get(i));
        }
        return result;
    }

    //This methods return the details about letter to forward to account
    //used in payment module
    @GET
    @Path("forwardAllToVendor")
    @Produces({"application/xml", "application/json"})
    public List<Object> forwardAllToVendor() throws ParseException {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = " SELECT AutoGenerate.Prefix, AutoGenerate.LastSeed, letter_formats.subject, letter_formats.letter_format, ReportImage.ImageData, m_libinfo.Lib_Nm,  \n"
                + " m_libinfo.Uni_Nm, m_libinfo.Librarian_nm, m_libinfo.Addr1, m_libinfo.Addr2, m_libinfo.City, m_libinfo.PIN, m_libinfo.Phone, m_libinfo.fax, \n"
                + " m_libinfo.email,  s_sub_inv.s_inv_dt, s_sub_inv.s_inv_no, s_sub_inv.s_inv_net_amt, s_supplier.a_s_nm AS invparty, \n"
                + " s_supplier_detail.a_s_city AS invsupcity, s_supplier_detail.a_s_add1, s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3, \n"
                + " s_supplier_detail.a_s_pin, s_supplier_detail.a_s_country,  s_supplier_detail.a_s_state, s_supplier_detail.a_s_email, \n"
                + " s_supplier_detail.a_s_phone, s_supplier_detail.a_s_fax   \n"
                + " FROM s_payment, s_sub_inv, s_sub_invdetail, s_supplier_detail, s_supplier, ReportImage, AutoGenerate, letter_formats, m_libinfo  \n"
                + " WHERE s_payment.s_pay_invid = s_sub_invdetail.s_inv_recid  and s_sub_inv.s_inv_no = s_sub_invdetail.s_inv_no And s_sub_inv.s_inv_party = s_supplier_detail.a_s_cd\n"
                + " And s_supplier_detail.a_s_cd = s_supplier.a_s_cd   and letter_name like 'fwd_vendor_scm%' and ReportImage.ID=1 and AutoGenerate.Name like 'Serial Forward to Vendor No%' ";
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        ArrayList<String> list = new ArrayList<String>();
        list.add("Serial Forward to Vendor No%");
        list.add("Serial Forward to Vendor");
        for(int i=0; i<list.size(); i++){
            autogenerateFacadeREST.updateAutogenerate(list.get(i));
        }
        return result;
    }
}
