/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.acquisition.suggestions.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.ws.rs.QueryParam;
import soul.acquisition.suggestions.AInvoice;
import soul.acquisition.suggestions.APayment;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.acquisition.suggestions.apayment")
public class APaymentFacadeREST extends AbstractFacade<APayment> {
    @EJB
    private AInvoiceFacadeREST aInvoiceFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    List<APayment> getAll;
    String output;
    AInvoice invoice;
    APayment payment;
    int count;
    
    public APaymentFacadeREST() {
        super(APayment.class);
    }

    /*@POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(APayment entity) {
        super.create(entity);
    }*/
    
    @POST
    @Override
    @Path("createAndGet")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public APayment createAndGet(APayment entity) {
        return super.createAndGet(entity);
    }


    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(APayment entity) {
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
    public APayment find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<APayment> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("by/{namedQuery}/{attrValues}")
    @Produces({"application/xml", "application/json"})
    public List<APayment> findBy(@PathParam("namedQuery") String query, @PathParam("attrValues") String attrValues) {
        String[] valueString = attrValues.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findByAPPayType": valueList.add(valueString[0]);
                                    break;
        }
        return super.findBy("APayment."+query, valueList);
    }


    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<APayment> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("retrieveAllByPayType/{payType}")
    public List<APayment> retrieveAllByPayType(@PathParam("payType") String payType)
    {   
     
              switch(payType)
              {
                  case "PaymentSettlement":   getAll = findBy("findByAPPayType", "accounts");                                        
                                              break;
              }
   
         return getAll;
//          else
//          {
//              GenericType<List<APayment>> genericType = new GenericType<List<APayment>>(){};
//              List<APayment> paymentList = new ArrayList<>();
//              Response restResponse = paymentClient.findAll_XML(Response.class);
//              switch(form)
//              {
//                  case "PaymentSettlement":   restResponse = paymentClient.findBy(Response.class, "findByAPPayType", "accounts");
//                                              break;
//              }
//              request.setAttribute("paymentList", restResponse.readEntity(genericType));
//          }
    }
    
    @POST
    @Path("processPayment")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String processPayment(@FormParam("referenceNo") String referenceNo,@FormParam("totalAmount") String totalAmount,
    @FormParam("bankCharges") String bankCharges,@FormParam("paymentBy") String paymentBy,
    @FormParam("bank") String bank,@FormParam("branch") String branch,
    @FormParam("chequeDDNo") String chequeDDNo,@FormParam("date") String date,
    @FormParam("invoiceNos") String invoice_Nos,@FormParam("remarks") String remarks)
    {   
        // get payment
        APayment payment = new APayment();
        payment.setAPRefNo(referenceNo);
        payment.setAPAmount(new BigDecimal(totalAmount));
        if(bankCharges != null)
            payment.setAPBkCharges(new BigDecimal(bankCharges));
        payment.setAPPayType(paymentBy);
        if(!payment.getAPPayType().equals("cash"))
        {
            payment.setAPBank(bank);
            payment.setAPBranch(branch);
            payment.setAPChequeNo(chequeDDNo);
        }
        try {
            payment.setAPChequeDt(new SimpleDateFormat("YYYY-MM-dd").parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(APaymentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        count = Integer.parseInt(countREST());
        payment = createAndGet(payment);
        if(Integer.parseInt(countREST()) == count)
        {
            output = "Someting went wrong, Payment Process is not Completed!";
        }
        else
        {
            String[] invoiceNos = invoice_Nos.split(",");
            for(int i=0; i<invoiceNos.length; i++)
            {
                invoice = aInvoiceFacadeREST.find(Integer.parseInt(invoiceNos[i]));
                invoice.setAPPayId(payment);
                invoice.setAIPayType(payment.getAPPayType());
                invoice.setAPStatus("P");
                invoice.setAIPayNote(remarks);
                aInvoiceFacadeREST.edit(invoice);
            }
            output = "Payment Process Completed";
        }
       return output;
     }
    
    @POST
    @Path("processPaymentSettlement")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String save(@FormParam("paymentNo") String paymentNo,@FormParam("paymentBySet") String paymentBySet,
    @FormParam("bankSet") String bankSet,@FormParam("branchSet") String branchSet,
    @FormParam("chequeDDNoSet") String chequeDDNoSet,@FormParam("dateSet") String dateSet)
    {
        //This is for PaymentSettlement Process
        payment = find(Integer.parseInt(paymentNo));
        payment.setAPPayType(paymentBySet);
        if(!payment.getAPPayType().equals("cash"))
        {
            payment.setAPBank(bankSet);
            payment.setAPBranch(branchSet);
            payment.setAPChequeNo(chequeDDNoSet);

        }
        try {
            payment.setAPChequeDt(new SimpleDateFormat("YYYY-MM-dd").parse(dateSet));
            System.out.println("date: "+payment.getAPChequeDt());
        } catch (ParseException ex) {
            Logger.getLogger(APaymentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        edit(payment);
        output = "Payment Settlement Completed.";
       return output;
    }

    @POST//This is for refund process
    @Path("refund")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String refund(@FormParam("referenceNo") String referenceNo,@FormParam("totalAmount") String totalAmount,
    @FormParam("bankCharges") String bankCharges,@FormParam("paymentBy") String paymentBy,
    @FormParam("bank") String bank,@FormParam("branch") String branch,
    @FormParam("chequeDDNo") String chequeDDNo,@FormParam("date") String date,
    @FormParam("invoiceNos") String invoice_Nos)
    {   // get payment
        APayment payment = new APayment();
        payment.setAPRefNo(referenceNo);
        payment.setAPAmount(new BigDecimal(totalAmount));
        if(bankCharges != null)
            payment.setAPBkCharges(new BigDecimal(bankCharges));
        payment.setAPPayType(paymentBy);
        if(!payment.getAPPayType().equals("cash"))
        {
            payment.setAPBank(bank);
            payment.setAPBranch(branch);
            payment.setAPChequeNo(chequeDDNo);
            
        }
        try {
            payment.setAPChequeDt(new SimpleDateFormat("YYYY-MM-dd").parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(APaymentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         String[] invoiceNos = invoice_Nos.split(",");
         payment.setAINoRefund(new Long(invoiceNos.length));
         count = Integer.parseInt(countREST());
         payment = createAndGet(payment);
         if(Integer.parseInt(countREST()) == count)
         {
             output = "Someting went wrong, Refund Process is not Completed!";
         }
         else
         {
             for(int i=0; i<invoiceNos.length; i++)
             {
                 invoice = aInvoiceFacadeREST.find(Integer.parseInt(invoiceNos[i]));
                 invoice.setRefNoteDt(payment.getAPChequeDt());
                 invoice.setRefAmt(payment.getAPAmount());
                 invoice.setRefStatus("Y");
                 invoice.setAPStatus("R");
                 aInvoiceFacadeREST.edit(invoice);
             }
             output = "Refund Process Completed";
         }
         return output;
     }
    
   
  
}
