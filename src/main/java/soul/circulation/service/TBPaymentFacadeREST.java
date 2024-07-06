/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import soul.circulation.TBInvoice;
import soul.circulation.TBPayment;
import soul.circulation.TBinding;
import soul.general_master.service.ABudgetFacadeREST;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.tbpayment")
public class TBPaymentFacadeREST extends AbstractFacade<TBPayment> {
    @EJB
    private TBInvoiceFacadeREST tBInvoiceFacadeREST;
    @EJB
    private TBindingFacadeREST tBindingFacadeREST;
    @EJB
    private ABudgetFacadeREST aBudgetFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    TBinding binding;
    TBInvoice invoice;
    String output;
  //  TBPayment payment;
  //  String[] bindNos;
   // List<TBinding> bindingList = new ArrayList<>();
  //  BiblidetailsLocation bibLocation;
  //  ObjectFactory objectFactory = new ObjectFactory();
   // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
    BigDecimal subtractFromCommit;
    
    public TBPaymentFacadeREST() {
        super(TBPayment.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TBPayment entity) {
        super.create(entity);
    }
    
    @POST
    @Path("createAndGet")
    @Override
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public TBPayment createAndGet(TBPayment entity) {
        return super.createAndGet(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TBPayment entity) {
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
    public TBPayment find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TBPayment> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TBPayment> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
 //added manually
    
    @POST
    @Path("payment")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String payment(@FormParam("paymentInvoiceNo") String paymentInvoiceNo,@FormParam("paymentDate") String paymentDate,
    @FormParam("paymentForwardDate") String paymentForwardDate,@FormParam("paymentAmount") String paymentAmount,
    @FormParam("paymentMode") String paymentMode,@FormParam("forwardMode") String forwardMode,
    @FormParam("bank") String bank,@FormParam("branch") String branch,
    @FormParam("ddChequeNo") String ddChequeNo,@FormParam("remarks") String remarks)
    {
        TBPayment payment = new TBPayment();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        payment.setBPayInvno(Integer.parseInt(paymentInvoiceNo));
        try {
            payment.setBPayDt(dateFormat.parse(paymentDate));
            payment.setBPayForwardDt(dateFormat.parse(paymentForwardDate));
        } catch (ParseException ex) {
            Logger.getLogger(TBPaymentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        payment.setBPayAmt(new BigDecimal(paymentAmount));
        payment.setBPayMode(paymentMode);
        
        if(payment.getBPayMode().equals("cash"))
        {
            payment.setBPayForwardMode(forwardMode);
            payment.setBPayBank(bank);
            payment.setBPayBranch(branch);
            payment.setBPayModeno(ddChequeNo);
        }
        
        payment.setBPayRemark(remarks);
        payment = createAndGet(payment);

        invoice = tBInvoiceFacadeREST.find(payment.getBPayInvno());
        invoice.setBInvFlag(1);
        tBInvoiceFacadeREST.edit(invoice);

        //Subtracts net invoiceAmount from commit and add it to expense
        subtractFromCommit = invoice.getBInvNetamt().negate();
        binding = tBindingFacadeREST.findBy("findByBOrderNo", invoice.getBInvOrderno()).get(0);

       // request.setAttribute("budgetCode", Integer.toString(binding.getBBudgetCd()));
        //request.setAttribute("accept", "include");

        //request.setAttribute("operation", "Commit");
        //request.setAttribute("commitAmount", subtractFromCommit.toString());
        //request.getRequestDispatcher("/BudgetServlet").include(request, response);
        aBudgetFacadeREST.commit(Integer.toString(binding.getBBudgetCd()), subtractFromCommit.toString(),  "include");
        //add to expense
        subtractFromCommit = invoice.getBInvNetamt();
        //request.setAttribute("operation", "AddToExpense");
        //request.setAttribute("expenseAmount", subtractFromCommit.toString());
        //request.getRequestDispatcher("/BudgetServlet").include(request, response);
        aBudgetFacadeREST.addToExpense(Integer.toString(binding.getBBudgetCd()), subtractFromCommit.toString(), "include");

        output = "PaymentReceived";
        return output;
    }
}
