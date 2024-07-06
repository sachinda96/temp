/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Form;
import soul.general_master.AAmtReceive;
import soul.general_master.ABudget;
import soul.general_master.ABudgetTransaction;
import soul.general_master.BHeads;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.abudget")
public class ABudgetFacadeREST extends AbstractFacade<ABudget> {
   
    @EJB
    private AAmtReceiveFacadeREST aAmtReceiveFacadeREST;
    @EJB
    private BHeadsFacadeREST bHeadsFacadeREST;
    @EJB
    private ABudgetTransactionFacadeREST aBudgetTransactionFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output;
    AAmtReceive amount = new AAmtReceive();
    List<ABudget> budgetList = new ArrayList<> ();
    List<BHeads> headsList = new ArrayList<> ();   
    Iterator<?> it;
    int count;
    AAmtReceive expenseAmount = null;
    ABudget budget;
    BHeads head;
        
    public ABudgetFacadeREST() {
        super(ABudget.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(ABudget entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(ABudget entity) {
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
    public ABudget find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("SingleResultBy/{namedQuery}/{attrValues}")
    @Produces({"application/xml", "application/json"})
    public ABudget findSingleResultBy(@PathParam("namedQuery") String query, @PathParam("attrValues") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findByBudgetCategoryYear1Year2":  valueList.add(valueString[0]);
                                                    valueList.add(valueString[1]);
                                                    valueList.add(valueString[2]);
                                                    break;
        }
        return super.findSingleResultBy("ABudget."+query, valueList);
    }

    @GET
    @Path("retrieveAll")
    @Override
    @Produces({"application/xml", "application/json"})
    public List<ABudget> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<ABudget> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        System.out.println("valueString[0]     .....  "+valueString[0]);
        switch(query)
        {
            case "findByBudgetCategoryYear1Year2":  valueList.add(valueString[0]);
                                                    valueList.add(valueString[1]);
                                                    valueList.add(valueString[2]);
                                                    break;
            case "findByBudgetYear1Year2" : valueList.add(valueString[0]);
                                            valueList.add(valueString[1]);                    
                                            break;
            case "findByCategory" :  valueList.add(valueString[0]);
                                       break;
            case "findAllBudgetHeadsForTitleEntry":  
                                    break;
            case "findByBudgetHead":  valueList.add(valueString[0]);
                                    break;
            case "findByBudgetCode":  valueList.add(valueString[0]);
                                    break;
        }
         return super.findBy("ABudget."+query, valueList);
    }
    
    @GET
    @Path("findAllBudgetHeadsForTitleEntry")
    @Produces({"application/xml", "application/json"})
    public List<ABudget> BudgetHeadDescrption() throws ParseException {
        String q = "";
        Query query;
        List<ABudget> result;
        q = "select * from b_heads,a_budget where budget_head = budget_code  and a_group_id != 'null'";
        query = getEntityManager().createNativeQuery(q,ABudget.class);
        result = (List<ABudget>) query.getResultList();
        return result;
    }
    
    @GET
    @Path("getBudgetAndAmount")
    @Produces({"text/plain"})
    public String getBudgetAndAmount()
    {
        String budgets = "";
        String amounts = "";
        List<ABudget> expenseBudgetList = findBy("findByCategory", "E");
        if(expenseBudgetList!= null)
        {
            for (ABudget budget : expenseBudgetList) {
                budgets = budgets + "," + budget.getABBudgetCode();
                amounts = amounts + "," + (budget.getABAmt().add(budget.getABOpBal()).subtract(budget.getABExpAmt()).subtract(budget.getABPoraisedAmt()));
            }
        }
        if(!budgets.equals(""))
        {
            budgets = budgets.substring(1);
            amounts = amounts.substring(1);
        }
    //    request.setAttribute("budgets", budgets);
    //    request.setAttribute("amounts", amounts);
        return budgets+" | "+amounts;
    }
    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<ABudget> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @PUT
    @Path("commit")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain") 
    public String commit(@FormParam("budgetCode") String budgetCode ,@FormParam("commitAmount") String commit_Amount,
     @FormParam("accept") String accept)
    {
        ABudget  budget = find(Integer.parseInt(budgetCode));
        BigDecimal commitAmount = budget.getABPoraisedAmt();
        //String newCommit = request.getAttribute("commitAmount")!=null?(String)request.getAttribute("commitAmount"):request.getParameter("commitAmount");
        String newCommit = commit_Amount;
        commitAmount = commitAmount.add(new BigDecimal(newCommit));
        budget.setABPoraisedAmt(commitAmount);
        edit(budget);
        if(accept.equals("forward"))
        {
          output =  "Amount Commited";
        }
        return output;
    }
    
    @PUT
    @Path("addToExpense")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain") 
    public String addToExpense(@FormParam("budgetCode") String budgetCode,@FormParam("expenseAmount") String expense_Amount,
    @FormParam("accept") String accept)
    {
        ABudget budget = find(Integer.parseInt(budgetCode));
        BigDecimal expenseAmount = budget.getABExpAmt();
        String newExpense = expense_Amount;
        expenseAmount = expenseAmount.add(new BigDecimal(newExpense));
        budget.setABExpAmt(expenseAmount);
        edit(budget);
        if(accept.equals("forward"))
        {
          output =  "Amount Added To Expense";
        }
        return output;
    }
    
       
    @GET
    @Path("getOpeningBalance/{yearFrom}/{yearTo}/{incomeHead}")
    @Produces({"text/plain"})
//    public String getOpeningBalance(@QueryParam("yearFrom") String year_From, 
//    @QueryParam("yearTo") String year_To, @QueryParam("incomeHead") String income_Head){
    public String getOpeningBalance(@PathParam("yearFrom") String year_From, 
    @PathParam("yearTo") String year_To, @PathParam("incomeHead") String income_Head)        
    {
        //System.out.println("getOpeningBalance");
          budgetList = findAll();
          String yearFrom = Integer.toString(Integer.parseInt(year_From)-1);
          String yearTo = Integer.toString(Integer.parseInt(year_To)-1);
          String incomeHead = income_Head;

          String openingBalance = "0";
          int i=0;

          ABudget budget = null;
          budget = findSingleResultBy("findByBudgetCodeYear1Year2", incomeHead+","+yearFrom+","+yearTo);
  
          if(budget != null)
          {
              openingBalance = budget.getABAmt().subtract(budget.getABExpAmt()).add(budget.getABOpBal()).toString();
              i = budget.getABBudgetCode();
          }
          return openingBalance+","+i;
          
  }
    
    @DELETE
    @Path("deleteIncome/{aAmtId}")
    @Produces("text/plain")
    public String deleteIncome(@PathParam("aAmtId") String aAmtId)
    {
        amount = aAmtReceiveFacadeREST.find(Integer.parseInt(aAmtId));
        BigDecimal amt = amount.getABBudgetCode().getABAmt();
        amt = amt.subtract(amount.getAmt());
        //System.out.println("Sub amt:"+amt);

        if(amt.add(amount.getABBudgetCode().getABOpBal()).compareTo(amount.getABBudgetCode().getABExpAmt())==-1)
        {
            //System.out.println("Total amount: "+amt.add(amount.getABBudgetCode().getABOpBal())+" Expense Amount:"+amount.getABBudgetCode().getABExpAmt());
            output = "You can not delete this entry, because IncomeSource amount is used by Some ExpenseHead!!";
        }
        else
        {
            aAmtReceiveFacadeREST.remove(Integer.parseInt(aAmtId));

            //to update ABudget entry of pervious year from which opening balance was carry forwarded
            if(amount.getABBudgetCode().getABOpBal().compareTo(new BigDecimal("0.00")) != 0)
            {
                ABudget budget = null;
                //budget = findSingleResultBy( "findByBudgetCodeYear1Year2", head+","+yearFrom+","+yearTo);
                budget = findSingleResultBy("findByBudgetCodeYear1Year2",amount.getABBudgetCode().getBudgetHead().getBudgetCode()+","+Integer.toString(Integer.parseInt(amount.getABBudgetCode().getABYear1())-1)+","+Integer.toString(Integer.parseInt(amount.getABBudgetCode().getABYear2())-1));
                budget.setABExpAmt(budget.getABExpAmt().subtract(amount.getABBudgetCode().getABOpBal()));
                edit(budget);
            }

            //System.out.println("if: "+(amt.compareTo(new BigDecimal(0.00))==0));
            if(amt.compareTo(new BigDecimal(0.00))==0)
                remove(Integer.parseInt(amount.getABBudgetCode().getABBudgetCode().toString()));
            else {
                amount.getABBudgetCode().setABAmt(amt);
                //System.out.println(amount.getABBudgetCode().getABAmt());
                edit(amount.getABBudgetCode());
                //System.out.println(budgetClient.find_XML(ABudget.class, amount.getABBudgetCode().getABBudgetCode().toString()).getABAmt());
            }
            output = "OK";
        }
        return output;
}
    
    @GET
    @Path("getIncomeSource/{transferFrom}/{yearFrom}/{yearTo}")
    @Produces({"text/plain"})
//    public String getIncomeSource(@QueryParam("transferFrom") String transferFrom,@QueryParam("yearFrom") String yearFrom,
//    @QueryParam("yearTo") String yearTo)
//    {
    public String getIncomeSource(@PathParam("transferFrom") String transferFrom,@PathParam("yearFrom") String yearFrom,
    @PathParam("yearTo") String yearTo)
    {    
           // genericType = new GenericType<List<ABudget>>(){};
           // restResponse = budgetClient.findAll_XML(Response.class);
            budgetList = findAll();
           // genericType = new GenericType<List<BHeads>>(){};
           // restResponse = new ResourceClient("bheads").findAll_XML(Response.class);
            headsList = bHeadsFacadeREST.findAll();

            //String transferFrom = request.getParameter("transferFrom");
            String fromExpense = "";
            it = headsList.iterator();
            //System.out.println("Before headList Size:"+headsList.size());
            while(it.hasNext())
            {
                head = (BHeads)it.next();
                if(head.getCategory().equals("I"))
                    it.remove();
            }
            //System.out.println("After headList Size:"+headsList.size());
            String[] expenseHeads = new String[headsList.size()];

            int i = 0;
            for(i=0; i<headsList.size(); i++)
            {
                head = headsList.get(i);
                if(head.getCategory().equals("E"))
                {   
                    expenseHeads[i] = head.getBudgetCode()+","+head.getShortDesc();
                    //System.out.println(expenseHeads[i]);
                }
            }

            it = budgetList.iterator();
            //System.out.println("output before :"+output);
            while(it.hasNext())
            {
                budget = (ABudget)it.next();

                //this for sending : <ExpenseHead BudgetCode>,<openingBalance>,<openingBalanceId>
                if(budget.getAGroupId() != null)
                {
                    //System.out.println("!=0");
                    if(budget.getABYear1().equals(Integer.toString(Integer.parseInt(yearFrom)-1)))
                    {
                        //System.out.println("year1 "+Integer.toString(Integer.parseInt(request.getParameter("yearFrom"))-1));
                        if(budget.getABYear2().equals(Integer.toString(Integer.parseInt(yearTo)-1)))
                        {
                            //System.out.println("year2 "+Integer.toString(Integer.parseInt(request.getParameter("yearTo"))-1));
                            for(i=0; i<headsList.size(); i++)
                            {
                                 if(expenseHeads[i].split(",")[0].equals(budget.getBudgetHead().getBudgetCode()))
                                 {
                                     //System.out.println("ExpenseHeads "+expenseHeads[i]);
                                     expenseHeads[i] = expenseHeads[i]+","+budget.getABAmt().add(budget.getABOpBal()).subtract(budget.getABExpAmt())+","+Integer.toString(budget.getABBudgetCode());
                                 }
                            }
                        }
                    }
                    if(transferFrom.equals("Expense"))
                    {
                        //System.out.println("year1"+budget.getABYear1()+"equals"+request.getParameter("yearFrom"));
                        if(budget.getABYear1().equals(yearFrom))
                        {
                        //System.out.println("In year1");
                            if(budget.getABYear2().equals(yearTo))
                            {
                                //System.out.println("year2"+budget.getABYear2()+"equals"+request.getParameter("yearTo"));
                                //System.out.println("In year1");
                                output = output+"|"+budget.getABBudgetCode().toString()+","+budget.getBudgetHead().getShortDesc()+","+budget.getABAmt().subtract(budget.getABExpAmt()).subtract(budget.getABPoraisedAmt()).add(budget.getABOpBal());                                               
                                //System.out.println(output);
                            }
                        }
                    }
                }
                else
                    if(transferFrom.equals("Income"))
                    {
                        if(budget.getABYear1().equals(yearFrom))
                        {
                            //System.out.println("In year1");
                            if(budget.getABYear2().equals(yearTo))
                            {
                                //System.out.println("In year1");
                                output = output+"|"+budget.getABBudgetCode().toString()+","+budget.getBudgetHead().getShortDesc()+","+budget.getABAmt().subtract(budget.getABExpAmt()).add(budget.getABOpBal());                                               
                                //System.out.println(output);
                            }
                        }
                    }
            }

            String expenseSources = "";
            //System.out.println("See the array");
            for(i=0;i<expenseHeads.length;i++)
            {
                if(expenseHeads[i].split(",").length!=3)
                {
                    expenseHeads[i] = expenseHeads[i]+","+"0.00";
                }
                //System.out.println(expenseHeads[i]+" length: "+expenseHeads[i].split(",").length);
                expenseSources = expenseSources + expenseHeads[i] +"|";
            }
            expenseSources = expenseSources.substring(0,expenseSources.length()-1);

            //System.out.println("expensesource:"+expenseSources);
            //System.out.println("output :"+output);
            if(output.length() != 0)
                output = output.substring(1,output.length());
            output = output+"&"+expenseSources;
            //System.out.println("output :"+output);
          return output;
    }
    
    @DELETE
    @Path("deleteExpense/{aAmtId}")
    @Produces("text/plain")
    public String deleteExpense(@PathParam("aAmtId") String aAmtId)
    {  
    expenseAmount = aAmtReceiveFacadeREST.find(Integer.parseInt(aAmtId));
    //**********this is to handle expense entries in ABudget
    BigDecimal amt = expenseAmount.getABBudgetCode().getABAmt(); //ABudget table entry amount
    amt = amt.subtract(expenseAmount.getAmt());//a_amt_receive amount is subtracted from ABudget entry amount
    //System.out.println("Sub amt:"+amt);
    //System.out.println("amt-amountOfAmtReceive+opening: "+amt.add(expenseAmount.getABBudgetCode().getABOpBal()));
    //System.out.println("compare to: with"+expenseAmount.getABBudgetCode().getABExpAmt()+" : "+amt.add(expenseAmount.getABBudgetCode().getABOpBal()).compareTo(expenseAmount.getABBudgetCode().getABExpAmt()));
    if(amt.add(expenseAmount.getABBudgetCode().getABOpBal()).compareTo(expenseAmount.getABBudgetCode().getABExpAmt()) == -1)
    {
       output = "Budget transfer is done from this budget, you can not delete this before deleting this!!";
    }
    else
    {
        aAmtReceiveFacadeREST.remove(Integer.parseInt(aAmtId));//row removed from a_amt_receive

        if(expenseAmount.getABBudgetCode().getABOpBal().compareTo(new BigDecimal("0.00")) != 0)
        {
            budget = getABudget(expenseAmount.getABBudgetCode().getBudgetHead().getBudgetCode(),  Integer.toString(Integer.parseInt(expenseAmount.getABBudgetCode().getABYear1())-1), Integer.toString(Integer.parseInt(expenseAmount.getABBudgetCode().getABYear2())-1));
           
            budget.setABExpAmt(budget.getABExpAmt().subtract(expenseAmount.getABBudgetCode().getABOpBal()));
            edit(budget);
        }

        System.out.println("if: "+(amt.compareTo(new BigDecimal(0.00))==0));
        //if ABudget entry's amount is 0.00 it is also removed else its amount is updated
        if(amt.compareTo(new BigDecimal(0.00))==0)
            remove(Integer.parseInt(expenseAmount.getABBudgetCode().getABBudgetCode().toString()));
        else {
            expenseAmount.getABBudgetCode().setABAmt(amt);
            edit(expenseAmount.getABBudgetCode());
        }

        //**************this is to delete entry from Budget_Transaction table************//
        ABudgetTransaction budgetTrans = null;
        budget = expenseAmount.getAGroupId();
        head = budget.getBudgetHead();

        String values = head.getBudgetCode()+","+expenseAmount.getABBudgetCode().getBudgetHead().getBudgetCode()+","+budget.getABYear1()+","+budget.getABYear2()+","+expenseAmount.getABBudgetCode().getABYear1();
        budgetTrans = aBudgetTransactionFacadeREST.findSingleResultBy("findByBudgetCodesYear1234", values);
        //System.out.println(budgetTrans);

        /*All commented becuse using named query to get required budgetTransaction
         * 
         * genericType = new GenericType<List<ABudgetTransaction>>() {};
        restResponse = new ResourceClient("abudgettransaction").findAll_XML(Response.class);
        List<ABudgetTransaction> budgetTransList = (List<ABudgetTransaction>) restResponse.readEntity(genericType);

        budgetTrans = null;

        String flag = "NotFound";
        it = budgetTransList.iterator();
        System.out.println(it.hasNext());
        for (it = budgetTransList.iterator(); it.hasNext();) 
        {
            budgetTrans  = (ABudgetTransaction) it.next();
            budget = expenseAmount.getAGroupId();
            head = budget.getBudgetHead();
            //System.out.println("budgetTrans.getABSourcecode().equals(head)"+budgetTrans.getABSourcecode().equals(head)+" head: "+head+" budgetTrans.getABSource:"+budgetTrans.getABSourcecode());
            if(budgetTrans.getABSourcecode().equals(head))
            {//System.out.println(budgetTrans.getABFyear1()+" : "+budget.getABYear1());
                if(budgetTrans.getABFyear1().equals(budget.getABYear1()))
                {//System.out.println(budgetTrans.getABFyear2()+" : "+budget.getABYear2());
                   if(budgetTrans.getABFyear2().equals(budget.getABYear2()))
                   {//System.out.println("budgetTrans.getABDestcode().equals(expenseAmount.getABBudgetCode().getBudgetHead())"+budgetTrans.getABDestcode().equals(expenseAmount.getABBudgetCode().getBudgetHead())+" expenseDestiCode: "+expenseAmount.getABBudgetCode().getBudgetHead()+" budgetTrans.getABDest:"+budgetTrans.getABDestcode());
                       if(budgetTrans.getABDestcode().equals(expenseAmount.getABBudgetCode().getBudgetHead()))
                       {//System.out.println(budgetTrans.getABFyear3()+" : "+expenseAmount.getABBudgetCode().getABYear1());
                           if(budgetTrans.getABFyear3().equals(expenseAmount.getABBudgetCode().getABYear1()))
                           {
                               flag = "Found";
                               break;
                           }
                       }
                   }
                }
            }
        }
        if(flag.equals("NotFound"))
            budgetTrans = null;
        System.out.println("budgetTrans:"+budgetTrans+" flag:"+flag);*/

        if(budgetTrans != null)
        {
            //ResourceClient budgetTransClient = new ResourceClient("abudgettransaction");
            aBudgetTransactionFacadeREST.remove(Integer.parseInt(Integer.toString(budgetTrans.getABTransid())));
        }


        //*************this is to update income entries from where amount to these expense wer allocated
        BigDecimal expenseAmt = expenseAmount.getAmt();

        ABudget incomeSource = expenseAmount.getAGroupId();
        BigDecimal budgetExpenseAmt = incomeSource.getABExpAmt();
        budgetExpenseAmt = budgetExpenseAmt.subtract(expenseAmt);
        incomeSource.setABExpAmt(budgetExpenseAmt);
        System.out.println("bud "+ incomeSource.getABExpAmt());
        edit(incomeSource);
        System.out.println("get  "+find(Integer.parseInt(Integer.toString(incomeSource.getABBudgetCode()))));
        output = "OK";
    }
    return output;
 }  
     public ABudget getABudget(String head, String yearFrom, String yearTo )
    {
        ABudget budget = null;
        System.out.println(head+","+yearFrom+","+yearTo);
        budget = findSingleResultBy("findByBudgetCodeYear1Year2", head+","+yearFrom+","+yearTo);
        return budget;
    } 
     
    @POST
    @Path("createOrSave")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrSave(Form form,@FormParam("operation") String operation)
    {
        if(operation.equals("Create"))
        {
            expenseAmount = getExpenseAmount(form);
            budget = find(Integer.parseInt(form.asMap().getFirst("incomeSource")));
            budget.setABExpAmt(budget.getABExpAmt().add(expenseAmount.getAmt()));
            edit(budget);
            aAmtReceiveFacadeREST.create(expenseAmount);
            //request.getRequestDispatcher("/expense.jsp").forward(request, response);
            //response.sendRedirect("expense.jsp");
           output = "Expense Budget Created.";
        }
        if(operation.equals("Save"))
        {  
            System.out.println("aa "+form.asMap().getFirst("expenseAmountId"));
            expenseAmount = aAmtReceiveFacadeREST.find(Integer.parseInt(form.asMap().getFirst("expenseAmountId")));
            try {
            expenseAmount.setRecAmtDate(new SimpleDateFormat("yyyy-MM-dd").parse(form.asMap().getFirst("date")));
            } catch (ParseException ex) {
                Logger.getLogger(ABudgetFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            expenseAmount.getABBudgetCode().setABActFlag(form.asMap().getFirst("status"));

            budget = expenseAmount.getAGroupId();
            if(expenseAmount.getAmt().compareTo(new BigDecimal(form.asMap().getFirst("allocatedAmount")))!=0)
            {
                expenseAmount.getABBudgetCode().setABAmt(expenseAmount.getABBudgetCode().getABAmt().subtract(expenseAmount.getAmt()));
                budget.setABExpAmt(budget.getABExpAmt().subtract(expenseAmount.getAmt()));

                //update expence a_amt_receive entry
                expenseAmount.setAmt(new BigDecimal(form.asMap().getFirst("allocatedAmount")));

                //update incomeSource budget entry and expenseSource budget entry
                budget.setABExpAmt(budget.getABExpAmt().add(expenseAmount.getAmt()));
                expenseAmount.getABBudgetCode().setABAmt(expenseAmount.getABBudgetCode().getABAmt().add(expenseAmount.getAmt()));
            }

            aAmtReceiveFacadeREST.edit(expenseAmount);
            edit(expenseAmount.getABBudgetCode());
            edit(budget);
            //request.getRequestDispatcher("expense.jsp").forward(request, response);
            //response.sendRedirect("expense.jsp");
            output = "Expense Budget Updated.";
        }
        return output;
    }  
    
   public  AAmtReceive getExpenseAmount(Form form)
    {
        AAmtReceive amt = new AAmtReceive();
        
        ABudget incomeSource = find(Integer.parseInt(form.asMap().getFirst("incomeSource")));
        
        BigDecimal receivedAmount = new BigDecimal(form.asMap().getFirst("allocatedAmount"));
        amt.setAmt(receivedAmount);
       
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(form.asMap().getFirst("date"));
        } catch (ParseException ex) {
            Logger.getLogger(ABudgetFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        amt.setRecAmtDate(date);
        
        String yearFrom = "";
        String yearTo = "";
        if(form.asMap().getFirst("transferFrom").equals("Income"))
        {
            yearFrom = form.asMap().getFirst("yearFrom");
            yearTo = form.asMap().getFirst("yearTo");
        }
        else
        {
            yearFrom = form.asMap().getFirst("yearFrom");
            yearTo = form.asMap().getFirst("yearTo");
        }
        String expenseSource = form.asMap().getFirst("expenseSource");
        String flag = "notFound";
        ABudget budget = null;
        
        budget = getABudget(expenseSource, yearFrom, yearTo);
//        System.out.println("dd1  "+budget.getABYear1());
        if(budget != null)
        {
            flag = "Found";
        }
        
        if(flag.equals("Found"))
        {
            budget.setABAmt(budget.getABAmt().add(receivedAmount));
            edit(budget);
            amt.setABBudgetCode(budget);
            if(form.asMap().getFirst("transferFrom").equals("Expense"))
            {
                //make entry in budget_transaction table
                ABudgetTransaction budgetTrans = new ABudgetTransaction();
                budgetTrans.setABSourcecode(incomeSource.getBudgetHead());
                budgetTrans.setABFyear1(form.asMap().getFirst("yearFrom"));
                budgetTrans.setABFyear2(form.asMap().getFirst("yearTo"));
                budgetTrans.setABDestcode(budget.getBudgetHead());
                budgetTrans.setABFyear3(budget.getABYear1());
                budgetTrans.setABFyear4(budget.getABYear2());
                budgetTrans.setABTransdate(date);
                budgetTrans.setABTransamt(receivedAmount);
                aBudgetTransactionFacadeREST.create(budgetTrans);
           }
            
        }
        else
        {
            budget = new ABudget();
            
            budget.setAGroupId(incomeSource);
            budget.setABYear1(yearFrom);
            budget.setABYear2(yearTo);
          //  ResourceClient bheadsClient = new ResourceClient("bheads");
            budget.setBudgetHead(bHeadsFacadeREST.find(expenseSource));
            try{
                budget.setABOpBal(new BigDecimal(form.asMap().getFirst("openingBalance")));
            } catch (NullPointerException e){
                budget.setABOpBal(new BigDecimal("0.00"));
            }
            
            
            //to add openingBalance amount to spent amount of pevious year, so that it makes 
            //impossible to peform any further budget transfer from that incomeSource
            if(budget.getABOpBal().compareTo(new BigDecimal(0.00))!=0)
            {
                ABudget openingBalanceBudget= find(Integer.parseInt(form.asMap().getFirst("openingBalanceId")));
                openingBalanceBudget.setABExpAmt(openingBalanceBudget.getABExpAmt().add(budget.getABOpBal()));
                edit(openingBalanceBudget);
            }
            
            
            budget.setABAmt(new BigDecimal("0.00"));
            budget.setABPoraisedAmt(new BigDecimal("0.00"));
            budget.setABActFlag(form.asMap().getFirst("status"));
            budget.setABExpAmt(new BigDecimal("0.00"));
            budget.setABEffectiveDate(date);
            budget.setABRemarks(form.asMap().getFirst("remarks"));
            System.out.println("Budget" + form.asMap().getFirst("expenseSource"));
            System.out.println("Budget" + form.asMap().getFirst("incomeSource"));
            System.out.println("Budget" + form.asMap().getFirst("openingBalanceId"));
            create(budget);
            //Question how does the amount of ABudget entry is correcrec though object created is with 0.00
            //Answer: in else part when, next line call the function itself correct amount is added in budget entry
            
            
            return getExpenseAmount(form);
        }
        amt.setAGroupId(incomeSource);
        System.out.println("D  "+amt.getABBudgetCode());
        return amt;
    }  
   
   //Add by parth
    @DELETE
    @Path("deletebudget/{userName}")
    @Produces("text/plain")
    public String deleteUser(@PathParam("userName") Integer userName)
    {
        count = Integer.parseInt(countREST());
        remove(userName);
        if(count == Integer.parseInt(countREST()))
        {
            output = "Someting went wrong record is not deleted!";
        }
        else
        {
            output = "OK";
        }
        return output;
    }
}
    
