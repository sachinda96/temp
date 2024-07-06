/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.ws.rs.core.Form;
import soul.general_master.AAmtReceive;
import soul.general_master.ABudget;
import soul.general_master.BHeads;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.aamtreceive")
public class AAmtReceiveFacadeREST extends AbstractFacade<AAmtReceive> {
    @EJB
    private ABudgetFacadeREST  aBudgetFacadeREST;
    @EJB
    private BHeadsFacadeREST bHeadsFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output = null;
    
    public AAmtReceiveFacadeREST() {
        super(AAmtReceive.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(AAmtReceive entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(AAmtReceive entity) {
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
    public AAmtReceive find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<AAmtReceive> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("getHeads/{head}")
    @Produces({"application/xml", "application/json"})
    public List<AAmtReceive> findWhere(@PathParam("head") String headQuery) {        
        return super.findWhere("AAmtReceive."+headQuery);
    }
    

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<AAmtReceive> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("retrieveAll/{accept}/{form}")
    @Produces({"application/xml", "application/json"})
   // public List<AAmtReceive> retrieveAll(@QueryParam("accept") String accept,@QueryParam("form") String form)
    public List<AAmtReceive> retrieveAll(@PathParam("accept") String accept,@PathParam("form") String form)        
    {
        List<AAmtReceive> getAll = null;
        if(accept.equals("XML"))
       {
           if(form.equals("incomeForm"))
           {
               getAll = findWhere("findIncomeHeads");
           }
            if(form.equals("expenseForm"))
           {
               getAll = findWhere("findExpenseHeads");
           }
       }
       
        return getAll;
//       else
//       {
//           GenericType<List<AAmtReceive>> genericType = new GenericType<List<AAmtReceive>>() {};
//           restResponse = receiveAmountClient.findAll_XML(Response.class);
//           amountList = restResponse.readEntity(genericType);
//           Iterator<?> it;
//           it = amountList.iterator();
//           while(it.hasNext())
//           {
//              amount = (AAmtReceive) it.next();
//              if(amount.getABBudgetCode().getAGroupId()!= null)
//                  it.remove();
//              request.setAttribute("amountList", amountList);
//           }
//       }
    }
    
     AAmtReceive amount = new AAmtReceive();
     ABudget budget = new ABudget();
        
@POST
    @Path("createOrSave")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrSave(Form form,@FormParam("operation") String operation)
    {
        if(operation.equals("Create"))
        {
            amount = getReceiveAmount(form);
            create(amount);
           output = "Income Budget Created.";
        }
        if(operation.equals("Save"))
        {    
              amount = find(Integer.parseInt(form.asMap().getFirst("receivedAmountId")));
              String currAmt;
              currAmt = form.asMap().getFirst("receivedAmount");
              BigDecimal difference = amount.getAmt().subtract(new BigDecimal(currAmt));

              budget = amount.getABBudgetCode();
              budget.setABAmt(amount.getABBudgetCode().getABAmt().subtract(difference));
              amount.setAmt(new BigDecimal(form.asMap().getFirst("receivedAmount")));

              try {
                  amount.setRecAmtDate(new SimpleDateFormat("yyyy-MM-dd").parse(form.asMap().getFirst("date")));
              } catch (ParseException ex) {
                  Logger.getLogger(AAmtReceiveFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
              }
              aBudgetFacadeREST.edit(budget);
              edit(amount);
              output = "Income Budget Updated.";
        }
        return output;
    }
    
     public AAmtReceive getReceiveAmount(Form form)
    {
        AAmtReceive amt = new AAmtReceive();
        
        BigDecimal receivedAmount = new BigDecimal(form.asMap().getFirst("receivedAmount"));
        amt.setAmt(receivedAmount);
       
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(form.asMap().getFirst("date"));
        } catch (ParseException ex) {
            Logger.getLogger(AAmtReceiveFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        amt.setRecAmtDate(date);

        String yearFrom = form.asMap().getFirst("yearFrom");
        String yearTo = form.asMap().getFirst("yearTo");
        String incomeHead = form.asMap().getFirst("incomeHead");
        String flag = "notFound";
        ABudget budget = null;
        budget = aBudgetFacadeREST.findSingleResultBy("findByBudgetCodeYear1Year2", incomeHead+","+yearFrom+","+yearTo);

       
        if(budget != null)
        {
            flag = "Found";
        }
        
        if(flag.equals("Found"))
        {
            budget.setABAmt(budget.getABAmt().add(receivedAmount));
            aBudgetFacadeREST.edit(budget);
            amt.setABBudgetCode(budget);
        }
        else
        {
            budget = new ABudget();
            budget.setAGroupId(null);
            budget.setABYear1(yearFrom);
            budget.setABYear2(yearTo);
          
            budget.setBudgetHead(bHeadsFacadeREST.find(incomeHead));
            
            System.out.println(budget.getBudgetHead());
            
            budget.setABOpBal(new BigDecimal(form.asMap().getFirst("openingBalance")));
            
            //to add openingBalance amount to spent amount of pevious year, so that it makes 
            //impossible to peform any further budget transfer from that incomeSource
            if(budget.getABOpBal().compareTo(new BigDecimal(0.00))!=0)
            {
                ABudget openingBalanceBudget= aBudgetFacadeREST.find(Integer.parseInt(form.asMap().getFirst("openingBalanceId")));
                openingBalanceBudget.setABExpAmt(openingBalanceBudget.getABExpAmt().add(budget.getABOpBal()));
                aBudgetFacadeREST.edit(openingBalanceBudget);
            }
            
            budget.setABAmt(new BigDecimal("0.00"));
            budget.setABPoraisedAmt(new BigDecimal("0.00"));
            budget.setABActFlag(null);
            budget.setABExpAmt(new BigDecimal("0.00"));
            budget.setABEffectiveDate(date);
            budget.setABRemarks(form.asMap().getFirst("remarks"));
            
            aBudgetFacadeREST.create(budget);
            //Question how does the amount of ABudget entry is correct though object created is with 0.00
            //Answer: in else part when, next line call the function itself correct amount is added in budget entry
            return getReceiveAmount(form);
        }
        amt.setAGroupId(null);
        return amt;
    }
     
     public Integer setGroupId(String incomeHead, ABudget budget){
         Integer id = 0;
         if(incomeHead.contentEquals("I"))
         {
             id = 0;
         }
         else
         {
            id = budget.getABBudgetCode();
             System.out.println("id........  "+id);
         }
         return id;
     }
//    @POST
//    @Path("addIncome")
//    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
//    @Produces("text/plain")
//    public String addIncome(@FormParam("openingBalance") BigDecimal openingBalance,@FormParam("remarks") String remarks,
//    @FormParam("incomeHead") String incomeHead,@FormParam("yearFrom") String yearFrom,
//            @FormParam("operation") String operation,@FormParam("receivedAmount") BigDecimal receivedAmount,
//                    @FormParam("yearTo") String yearTo,
//                            @FormParam("date") String date){
//        
//        AAmtReceive amt = new AAmtReceive();
//        ABudget budget = new ABudget();
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        
//        
//        budget.setAGroupId(0);
//        budget.setABYear1(yearFrom);
//        budget.setABYear2(yearTo);
//        budget.setBudgetHead(incomeHead);
//        budget.setABOpBal(openingBalance);
//        budget.setABAmt(BigDecimal.ZERO);
//        budget.setABPoraisedAmt(BigDecimal.ONE);
//        budget.setABActFlag(output);
//        budget.setABExpAmt(BigDecimal.ZERO);
//        budget.setABEffectiveDate(format.parse(aBEffectiveDate));
//        budget.setABRemarks(output);
//        
//        amt.setABBudgetCode(incomeHead);
//        amt.setAGroupId(budget);
//        amt.setAmt(receivedAmount);
//        amt.setRecAmtDate(date);
//        
//        return output;
//    }
}
