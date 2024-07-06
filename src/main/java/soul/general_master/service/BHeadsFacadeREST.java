/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import soul.general_master.BHeads;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.bheads")
public class BHeadsFacadeREST extends AbstractFacade<BHeads> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public BHeadsFacadeREST() {
        super(BHeads.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(BHeads entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(BHeads entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public BHeads find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
     @Produces({"application/xml", "application/json"})
    public List<BHeads> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<BHeads> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
   
    // added manually
      String output = null;
        String id = null;
        int count = 0;
        BHeads head = new BHeads();
        String budgetCode;
        String budgetDesc;
        String category;
        String expDate;
        
    @GET
    @Path("checkBudgetCode/{budgetCode}")
    @Produces("text/plain")
   // public String checkBudgetCode(@QueryParam("budgetCode") String budgetCode)
    public String checkBudgetCode(@PathParam("budgetCode") String budgetCode)        
    {
        id = budgetCode;
        if(!id.equals(""))
        {
            head = find(id);
            if(head == null)
            {
                output = "Record is not available!!";
            }
            else
            {
               output = "Record available!!";
             }
        }
        return output;
   }
    
    @GET
    @Path("retrieveAll/{accept}")
    @Produces({"application/xml", "application/json"})
    //public List<BHeads> retrieveAll(@QueryParam("accept") String accept)
     public List<BHeads> retrieveAll(@PathParam("accept") String accept)        
    {
        List<BHeads> getAll = null;
        if(accept.equals("XML"))
        {
             getAll = findAll();
        }
        return getAll;
    }
    
    @POST
    @Path("Create")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String Create(@FormParam("budgetCode") String budgetCode, @FormParam("category") String category,
            @FormParam("budgetDescription") String budgetDescription,@FormParam("expDate") String expDate) {
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        head.setBudgetCode(budgetCode);
        head.setShortDesc(budgetDescription);
        head.setCategory(category);
        if (category.equals("I")) {
            head.setBudgetExpDate(null);
        } else {
            try {
                head.setBudgetExpDate(format.parse(expDate));
            } catch (ParseException ex) {
                Logger.getLogger(BHeadsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        count = Integer.parseInt(countREST());
        create(head);
        if (count == Integer.parseInt(countREST())) {
            output = "Something went wrong, Record is not Inseted!!";
        } else {
            output = "Record Inseted!!";
        }
        return output;
    }
    
    @PUT
    @Path("Save")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String Save(@FormParam("budgetCode") String budgetCode, @FormParam("category") String category,
            @FormParam("budgetDescription") String budgetDescription,@FormParam("expDate") String expDate)
    {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        head = find(budgetCode);
        head.setShortDesc(budgetDescription);
        head.setCategory(category);
        if (category.equals("I")) {
            head.setBudgetExpDate(null);
        } else {
            try {
                head.setBudgetExpDate(format.parse(expDate));
            } catch (ParseException ex) {
                Logger.getLogger(BHeadsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "BudgetHead Updated.";
    }


    @DELETE
    @Path("deleteBhead/{budgetCode}")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String deleteBhead(@PathParam("budgetCode") String budgetCode)
    {        
                            id = budgetCode;
                            count = Integer.parseInt(countREST());
                            remove(id);
                            if(count == Integer.parseInt(countREST()))
                            {
                                output = "You can not delete a Budget Head which is in use. First delete all budget entries for income and expense.";
                            }
                            else output = "Record Deleted!!";
                          //  request.setAttribute("output", output);
                          //  request.setAttribute("operation", operation);
                           return output;
    }
    

    
    //Budgethead description from b_heads table
    @GET
    @Path("BudgetHeadDescrption")
    @Produces({"application/xml", "application/json"})
    public List<BHeads> BudgetHeadDescrption() throws ParseException {
        String q = "";
        Query query;
        q = "select budget_code,short_desc from b_heads order by short_desc";
        List<BHeads> result;
        query = getEntityManager().createNativeQuery(q,BHeads.class);
        result = (List<BHeads>) query.getResultList();
        return result;
    }
    
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<BHeads> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        System.out.println("valueString[0]     .....  "+valueString[0]);
        switch(query)
        {
            case "findByBudgetCode":  valueList.add(valueString[0]);
                                    break;
                                    
            case "findByCategory":  valueList.add(valueString[0]);
                                    break;
        }
         return super.findBy("BHeads."+query, valueList);
    }
    
    @GET
    @Path("getAllIncomeHeads")
    @Produces({"application/xml", "application/json"})
    public List<BHeads> getAllIncomeHeads() {
        List<BHeads> result = findBy("findByCategory", "I");;
        return result;
    }
    @GET
    @Path("getAllExpenseHeads")
    @Produces({"application/xml", "application/json"})
    public List<BHeads> getAllExpenses() {
        List<BHeads> result = findBy("findByCategory", "E");;
        return result;
    }
    
    @GET
    @Path("getAlive")
    @Produces({"application/xml", "application/json"})
    public int getAlive() {
        int lived = 0;
        int round = 1;
        ArrayList peoples = new ArrayList();
        for (int i=0; i<100; ++i){
            peoples.add(i+1);
        }
        while(peoples.size() > 1){
            for(int i = 0; i<peoples.size(); ++i){
                peoples.remove(i);
                round++;
            }
            System.out.println("Round: "+round+"->"+peoples);
        }
        return lived;
    }
}
