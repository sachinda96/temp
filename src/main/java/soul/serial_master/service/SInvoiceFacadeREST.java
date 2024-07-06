/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
import soul.catalogue.Biblidetails;
import soul.catalogue.BiblidetailsPK;
import soul.catalogue.Location;
import soul.catalogue.LocationPK;
import soul.serialControl.SMst;
import soul.serialControl.SSchedule;
import soul.serialControl.SSchedulePK;
import soul.serial_master.SBinding;
import soul.serial_master.service.SBindingFacadeREST;
import soul.serial_master.SBindingPK;
import soul.serial_master.SBindingSet;
import soul.serial_master.SBindingSetPK;
import soul.serial_master.service.SBindingSetFacadeREST;
import soul.general_master.ABudget;
import soul.general_master.service.ABudgetFacadeREST;
import soul.serialControl.service.SMstFacadeREST;
import soul.serial_master.SInvoice;
import soul.serial_master.service.SInvoiceFacadeREST;
import soul.catalogue.service.BiblidetailsFacadeREST;
import soul.catalogue.service.LocationFacadeREST;
import soul.serial_master.SSupplier;
import soul.general_master.Autogenerate;
import soul.general_master.BHeads;
import soul.general_master.Libmaterials;
import soul.general_master.MFcltydept;
import soul.general_master.service.AutogenerateFacadeREST;
import soul.general_master.service.BHeadsFacadeREST;
import soul.general_master.service.MFcltydeptFacadeREST;
import soul.serialControl.SRequest;
import soul.serialControl.SSubInv;
import soul.serialControl.service.SPaymentFacadeREST;
import soul.serialControl.service.SRequestFacadeREST;
import soul.serialControl.service.SSubInvFacadeREST;
import soul.serial_master.SSupplierDetail;
import soul.serialControl.SPayment;
import soul.serialControl.service.SPaymentFacadeREST;

/**
 *
 * @author admin
 */
@Stateless
@Path("soul.serial_master.sinvoice")
public class SInvoiceFacadeREST extends AbstractFacade<SInvoice> {

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    @EJB
    private ABudgetFacadeREST aBudgetFacadeREST;
    @EJB
    private AutogenerateFacadeREST autogenerateFacadeREST;
    @EJB
    private SBindingFacadeREST sBindingFacadeREST;
    @EJB
    private SPaymentFacadeREST sPaymentFacadeREST;
    
    public SInvoiceFacadeREST() {
        super(SInvoice.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(SInvoice entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, SInvoice entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public SInvoice find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SInvoice> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SInvoice> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
    @Path("getAllOrders")
    @Produces({"application/xml", "application/json"})
    public List<SInvoice> getAllOrders() {
        String q = "";
        List<SInvoice> result = new ArrayList<>();
        Query query;

        q = "select distinct s_inv_orderno from s_invoice,s_binding where s_inv_orderno=s_b_order_no order by s_inv_orderno";
        query = getEntityManager().createNativeQuery(q, SSchedule.class);
        result = (List<SInvoice>) query.getResultList();
        return result;
    }
    
    @GET
    @Path("getAllI")
    @Produces({"application/xml", "application/json"})
    public List<SInvoice> getAllI() {
        String q = "";
        List<SInvoice> result = findBy("getAllI","null");
//        Query query;
//
//        q = "select distinct s_inv_orderno from s_invoice where s_inv_orderno is not null";
//        query = getEntityManager().createNativeQuery(q, SInvoice.class);
//        result = (List<SInvoice>) query.getResultList();
        return result;
    }
    
    @GET
    @Path("getAllInvoicesForPayment")
    @Produces({"application/xml", "application/json"})
    public List<SInvoice> getAllInvoicesForPayment() {

        List<SInvoice> result = null;
        result = findBy("getAllInvoicesForPayment", "null");

        return result;
    }
    
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<SInvoice> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        List<String> smstStatus = new ArrayList<>();

        switch (query) {
            case "getAllInvoicesForPayment":   break;
            case "getAllI":   break;
        }
        return super.findBy("SInvoice." + query, valueList);
    }
    
    @POST
    @Path("generateInvoice")
    @Produces("text/plain")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String generateInvoice(@FormParam("orderNo") String orderNo,
                            @FormParam("invoiceForwardDate") String invoiceForwardDate,
                            
                            @FormParam("invoiceDate") String invoiceDate,@FormParam("invoiceMisAmount") BigDecimal invoiceMisAmount,
                            @FormParam("invoiceParty") String invoiceParty,@FormParam("invoiceOverDue") BigDecimal invoiceOverDue,
                            @FormParam("invoiceRcptDate") String invoiceRcptDate,@FormParam("invDiscount") BigDecimal invDiscount,
                            @FormParam("invoiceOrderNo") String invoiceOrderNo,
                            @FormParam("invForwardNo") String invForwardNo,@FormParam("invForwardTo") String invForwardTo) {

        String output = null;
        List<SInvoice> invoices = findAll();
        String newInvoiceNo = null;
        Calendar cal = Calendar.getInstance();
        String year = Integer.toString(cal.get(Calendar.YEAR));
        String YY = year.substring(2, year.length());
        Integer newInvoiceId;
        String genInvNo = null;
        System.out.println("Size: "+invoices.size());
        if (invoices.size() == 0) {
            newInvoiceId = 1;
        } else {
            List<Integer> allInvoicesId = new ArrayList<Integer>();
            for (int i = 0; i < invoices.size(); i++) {
                allInvoicesId.add(invoices.get(i).getSInvId());
            }
            newInvoiceId = Collections.max(allInvoicesId) + 1;
        }
        if(invoices.size() == 0){
            genInvNo = "SR/INV/"+YY+"/1";
        } else {
            ArrayList<String> list = new ArrayList<String>();
            String invoiceNo;
            list.add("Serial InvoiceProcessing");
            for(int i=0; i<list.size(); i++){
                autogenerateFacadeREST.updateAutogenerate(list.get(i));
            }
            invoiceNo = autogenerateFacadeREST.generateLetterNo("Serial InvoiceProcessing");
            newInvoiceNo = invoiceNo.substring(0, (invoiceNo.length() - 2));
            System.out.println("D:"+newInvoiceNo);
            System.out.println("Invoiceno: "+invoiceNo);
            List<String> allInvoices = new ArrayList<String>();
            for(int i = 0; i< invoices.size(); i++){
                allInvoices.add(invoices.get(i).getSInvNo());
            }
            genInvNo = newInvoiceNo + newInvoiceId;
            System.out.println("D:"+genInvNo);
        }
        
        
        System.out.println("newInvoiceNo: "+genInvNo);
        System.out.println("newInvoiceNo: "+newInvoiceId);
        
        List<SBinding> all = new ArrayList<SBinding>();
        SimpleDateFormat format = new SimpleDateFormat("YYYY-mm-dd HH:mm:ss");
        BigDecimal invPrice = BigDecimal.ZERO;
        List<String> budgets = new ArrayList<>();
        List<SBinding> sBinding = sBindingFacadeREST.findBy("findBySBOrderNo", orderNo);
        System.out.println("SBinding: " + orderNo);
        for (int j = 0; j < sBinding.size(); j++) {
            budgets.add(sBinding.get(j).getSBBudgetCd());
            invPrice = invPrice.add(sBinding.get(j).getSBPrice());
        }
        System.out.println("SBinding: " + sBinding);
        System.out.println("SBinding all: " + all);
        
        
        
        SInvoice sInvoice = new SInvoice();
        
        sInvoice.setSInvType("I");
        sInvoice.setSInvNo(genInvNo);
        sInvoice.setSInvId(newInvoiceId);
        try {
            sInvoice.setSInvDt(format.parse(invoiceDate));
        } catch (ParseException ex) {
            Logger.getLogger(SBindingFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        sInvoice.setSInvParty(invoiceParty);
        try {
            sInvoice.setSInvRcptdt(format.parse(invoiceRcptDate));
        } catch (ParseException ex) {
            Logger.getLogger(SBindingFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        sInvoice.setSInvOrderno(invoiceOrderNo);
        BigDecimal gross = invPrice.subtract(invDiscount);
        sInvoice.setSInvPrice(invPrice);

        // discuss one more time what to do with overdue
        // delete column from datagrid in client side
       sInvoice.setSInvPrice(invPrice.add(invoiceOverDue));
        sInvoice.setSInvDiscount(invDiscount);
        sInvoice.setSInvOverdue(invoiceOverDue);
        sInvoice.setSInvMisamt(invoiceMisAmount);
        BigDecimal newInvNetAmount = gross.add(invoiceMisAmount);
        sInvoice.setSInvNetamt(newInvNetAmount);
        try {
            sInvoice.setSInvForwardDt(format.parse(invoiceForwardDate));
        } catch (ParseException ex) {
            Logger.getLogger(SBindingFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        sInvoice.setSInvForwardNo(invForwardNo);
        sInvoice.setSInvForwardTo(invForwardTo);
        int count = super.count();
        
        create(sInvoice);
        if(count == super.count()){
            output = "Something went wrong, failed to generate invoice";
        } else {
            output = "Invoice: " + genInvNo + " generated...";
        }
        System.out.println("Budgets: "+budgets);
        Object[] st = budgets.toArray();
        for (Object s : st) {
            if (budgets.indexOf(s) != budgets.lastIndexOf(s)) {
                budgets.remove(budgets.lastIndexOf(s)); 
            }
        }
        System.out.println("Budgets: "+budgets);
        List<ABudget> editBudget = new ArrayList<ABudget>();
        for(int i = 0; i<budgets.size(); i++){
            List<ABudget> aBudget = aBudgetFacadeREST.findBy("findByBudgetCode", budgets.get(i));
            for(int j = 0; j<aBudget.size(); j++){
                editBudget.add(aBudget.get(j));
            }        
        }
        
        for(int i = 0; i<editBudget.size(); i++){
            BigDecimal expAmount = editBudget.get(i).getABExpAmt().add(newInvNetAmount);
            BigDecimal poRaised = editBudget.get(i).getABPoraisedAmt().subtract(invPrice);
            aBudgetFacadeREST.edit(editBudget.get(i));
        }
        
        return output;
    }
}
