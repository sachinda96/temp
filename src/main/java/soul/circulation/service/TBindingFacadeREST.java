/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import soul.catalogue.BiblidetailsLocation;
import soul.catalogue.Location;
import soul.catalogue.TMissing;
import soul.catalogue.service.BiblidetailsLocationFacadeREST;
import soul.catalogue.service.LocationFacadeREST;
import soul.circulation.TBinding;
import soul.general_master.ABudget;
import soul.general_master.service.ABudgetFacadeREST;


/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.tbinding")
public class TBindingFacadeREST extends AbstractFacade<TBinding> {
    @EJB
    LocationFacadeREST locationFacadeREST;
    @EJB
    BiblidetailsLocationFacadeREST biblidetailsLocationFacadeREST;
    @EJB
    private ABudgetFacadeREST aBudgetFacadeREST;

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    List<TBinding> bindingList;
    public TBindingFacadeREST() {
        super(TBinding.class);
    }

//    @POST
//    @Override
//    @Consumes({"application/xml", "application/json"})
//    public void create(TBinding entity) {
//        super.create(entity);
//    }
     
      

    @POST
    @Consumes({"application/xml", "application/json","application/x-www-form-urlencoded"})
    public String create(@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{accNo.pattern}") @FormParam("accNos") String acc_Nos) {
        String[] accNos;
        String[] validStatus = {"AM", "AV", "DA", "TR"};
        Location location;
        accNos = acc_Nos.split(",");
        TBinding binding;
        BiblidetailsLocation bibLocation;

        int bindingCount = 0;
        String errorMsg = "";
        for(int i=0; i<accNos.length; i++)
        {
            location = locationFacadeREST.findBy("findByP852", accNos[i]).get(0);
            bibLocation = biblidetailsLocationFacadeREST.find(accNos[i]);
            if(Arrays.asList(validStatus).contains(location.getStatus()))
            {

                binding = new TBinding();
                binding.setBAccessNo(accNos[i]);
                binding.setBTitle(bibLocation.getTitle());

                create(binding);

                location.setStatus("BI");
                locationFacadeREST.edit(location);

                bindingCount++;

                output = bindingCount+" Items/Books selected for binding.";
            }
            else
            {
                errorMsg = errorMsg + accNos[i]+"      "+bibLocation.getStatusDscr()+"\n";
            }
        }
        if(bindingCount < accNos.length)
        {
            output = "Items with following accNos. can not be selected for binding."+"\n";
            output = output + "Accession No.     Status"+"\n";
            output = output + "\n" + errorMsg;
        }
      return output;
    }
    
    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TBinding entity) {
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
    public TBinding find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TBinding> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TBinding> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("byDate/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<TBinding> byDt(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");       
        List<Object> valueList = new ArrayList<>();         
        switch(query)
        {
            case "findByBindDtBtwn": SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
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
        return super.findBy("TBinding."+query, valueList);
    }

    
    //Added Manually
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<TBinding> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
            case "findByNullBOrderNo": 
                                        break;
            case "findByBFlag":         valueList.add(Integer.parseInt(valueString[0]));
                                        break;
            case "findByBOrderNoAndBFlag":  valueList.add(valueString[0]);
                                            valueList.add(Integer.parseInt(valueString[1]));
                                            break;
            default:    valueList.add(valueString[0]);
            //used for findByBOrderNo
        }
        return super.findBy("TBinding."+query, valueList);
    }
    
    //Added by Dashrath
    //Binding items list by order no of binding
    @GET
    @Path("BindinglistByOrderNo/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public  List<TBinding> BindinglistByOrderNo(@PathParam("searchParameter") String param)
    {   List<TBinding> list = null;       
        list = findBy("findByBOrderNo", param);
        return list;
     }
    
    
    @GET
    @Path("retrieveAll/{form}/{orderNo}")
    @Produces({"application/xml", "application/json"})
    //public  List<TBinding> retrieveAll(@QueryParam("form") String form,@QueryParam("orderNo") String orderNo)
    public  List<TBinding> retrieveAll(@PathParam("form") String form,@PathParam("orderNo") String orderNo)        
    {   List<TBinding> list = null;
        if("BindOrder".equals(form))    
        {
             list = findBy( "findByNullBOrderNo", "null");
        }
        else if("BindInvoice".equals(form))
        {
             list = findBy("findByBOrderNoAndBFlag", orderNo+","+"0");
        }
        return list;
     }
    
    
    @GET
    @Path("checkOrderNo/{orderNo}")
    //public String checkOrderNo(@QueryParam("orderNo") String orderNo)
    public String checkOrderNo(@PathParam("orderNo") String orderNo)        
    {
        bindingList = new ArrayList<>();
        bindingList = findBy("findByBOrderNo", orderNo);
        if(bindingList.isEmpty())
        {
            output = "NotUsed";
        }
         else
        {
            output = "AlreadyUsed";
        }
         return output;
    }
    
    @GET
    @Path("getAvailableBudget/{budgetCode}")
   // public BigDecimal getAvailableBudget(@QueryParam("budgetCode") String budgetCode)
    public BigDecimal getAvailableBudget(@PathParam("budgetCode") String budgetCode)        
    {
        ABudget budget = aBudgetFacadeREST.find(Integer.parseInt(budgetCode));
        BigDecimal availableAmount = budget.getABAmt().add(budget.getABOpBal()).subtract(budget.getABPoraisedAmt()).subtract(budget.getABExpAmt());
       // out.println(availableAmount);
       // output = String.val(availableAmount);
        return availableAmount;
    }
    
    @POST
    @Path("binding")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String binding(@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{accNo.pattern}") @FormParam("accNos") String acc_Nos)
    {  String[] validStatus = {"AM", "AV", "DA", "TR"};
        //System.out.println("accNos  "+request.getParameter("accNos"));
       String[] accNos = acc_Nos.split(",");
       int bindingCount = 0;
       String errorMsg = "";
       for(int i=0; i<accNos.length; i++)
       {
           Location location = locationFacadeREST.findBy("findByP852", accNos[i]).get(0);
           BiblidetailsLocation bibLocation = biblidetailsLocationFacadeREST.find(accNos[i]);
           if(Arrays.asList(validStatus).contains(location.getStatus()))
           {

               TBinding binding = new TBinding();
               binding.setBAccessNo(accNos[i]);
               binding.setBTitle(bibLocation.getTitle());

               create(binding);

               location.setStatus("BI");
               locationFacadeREST.edit(location);
               bindingCount++;
               output = bindingCount+" Items/Books selected for binding.";
           }
           else
           {
               errorMsg = errorMsg + accNos[i]+"      "+bibLocation.getStatusDscr()+"\n";
           }
       }
       if(bindingCount < accNos.length)
       {
           output = "Items with following accNos. can not be selected for binding."+"\n";
           output = output + "Accession No.     Status"+"\n";
           output = output + "\n" + errorMsg;
       }
       return output;
    }
    
    @PUT
    @Path("bindOrder")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String bindOrder(@FormParam("bindNos") String bind_Nos,@FormParam("binder") String binder,
    @FormParam("budget") String budget,@FormParam("price") String price,
    @FormParam("expectedDate") String expectedDate,@FormParam("bindType") String bindType,
    @FormParam("orderNo") String orderNo)
    {
            String[] bindNos = bind_Nos.split(",");
            for(int i=0; i<bindNos.length; i++)
            {
                TBinding binding = find(Integer.parseInt(bindNos[i]));

                binding.setBSendDt(new Date());
                binding.setBBinderCd(binder);
                binding.setBBudgetCd(Integer.parseInt(budget));
                binding.setBPrice(new BigDecimal(price));
                try {
                    binding.setBExpDt(format.parse(expectedDate));
                } catch (ParseException ex) {
                    Logger.getLogger(TBindingFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                binding.setBBindtype(bindType);
                binding.setBOrderNo(orderNo);
                binding.setBFlag(0);
                edit(binding);
   //Adds total amount for this order to ABPoraised(committed) amount of specified budget
                binding = find(Integer.parseInt(bindNos[0]));
                BigDecimal commitAmount = binding.getBPrice().multiply(new BigDecimal(bindNos.length));
                aBudgetFacadeREST.commit(Integer.toString(binding.getBBudgetCd()), commitAmount.toString(), "include");
                //request.setAttribute("operation", "Commit");
                //request.setAttribute("budgetCode", Integer.toString(binding.getBBudgetCd()));
                //request.setAttribute("accept", "include");
                //request.setAttribute("commitAmount", commitAmount.toString());
                //request.getRequestDispatcher("/BudgetServlet").include(request, response);

                output = "Order Placed.";
             }
            return output;
    }
    
    @GET
    @Path("getOrderNos")
    public String getOrderNos()
    {///////////// used for Invoice Tab////////////////////    
         bindingList = findBy("findByBFlag", "0");
        for(TBinding b: bindingList)
        {
            output = output+","+b.getBOrderNo();
        }
            if(output.length() > 0)
            {
                output = output.substring(1);
            }
            else
            {
                output = "";
            }
        return output;
    }
    
    @GET
    @Path("getOrderItemCount/{orderNo}")
    //public String getOrderItemCount(@QueryParam("orderNo") String orderNo)
    public String getOrderItemCount(@PathParam("orderNo") String orderNo)         
    {
        //gets no of items and no of received items in a order
        bindingList = findBy("findByBOrderNo", orderNo);
        int received = 0;
        for(TBinding b: bindingList)
        {
            if(b.getBFlag() == 1)
                received++;
        }
        output = bindingList.size()+","+received;
        return output;
    }
        
}
