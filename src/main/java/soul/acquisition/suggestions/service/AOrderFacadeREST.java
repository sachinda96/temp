/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.acquisition.suggestions.service;

//import ExceptionService.DataException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
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
import soul.acquisition.suggestions.AOrder;
import soul.acquisition.suggestions.AOrderPK;
import soul.acquisition.suggestions.AOrdermaster;
import soul.acquisition.suggestions.ARequest;
import soul.general_master.ABudget;
import soul.general_master.ASupplierDetail;
import soul.general_master.service.ABudgetFacadeREST;
import soul.general_master.service.ASupplierDetailFacadeREST;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.acquisition.suggestions.aorder")
public class AOrderFacadeREST extends AbstractFacade<AOrder> {
    @EJB
    private ASupplierDetailFacadeREST  aSupplierDetailFacadeREST;
    @EJB
    private AOrdermasterFacadeREST aOrdermasterFacadeREST;
    @EJB
    private ARequestFacadeREST aRequestFacadeREST;
    @EJB
    private ABudgetFacadeREST aBudgetFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    ASupplierDetail aSupplier = null;
    String output;
      
    private AOrderPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;aRNo=aRNoValue;aOPoNo=aOPoNoValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.acquisition.suggestions.AOrderPK key = new soul.acquisition.suggestions.AOrderPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> aRNo = map.get("aRNo");
        if (aRNo != null && !aRNo.isEmpty()) {
            key.setARNo(new java.lang.Integer(aRNo.get(0)));
        }
        java.util.List<String> aOPoNo = map.get("aOPoNo");
        if (aOPoNo != null && !aOPoNo.isEmpty()) {
            key.setAOPoNo(new java.lang.Integer(aOPoNo.get(0)));
        }
        return key;
    }

    public AOrderFacadeREST() {
        super(AOrder.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(AOrder entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(AOrder entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.acquisition.suggestions.AOrderPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public AOrder find(@PathParam("id") PathSegment id) {
        soul.acquisition.suggestions.AOrderPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<AOrder> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<AOrder> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
    @GET
    @Path("by/{namedQuery}/{attrValues}")
    @Produces({"application/xml", "application/json"})
    public List<AOrder> findBy(@PathParam("namedQuery") String query, @PathParam("attrValues") String attrValues) {
        
        String[] valueString = attrValues.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findByARStatus":              valueList.add(valueString[0]); 
                                                break;
            case "findByARNo":                  valueList.add(Integer.parseInt(valueString[0])); 
                                                break;
            case "findByAOPoNo":                valueList.add(Integer.parseInt(valueString[0])); 
                                                break;    
            case "findByAOStatussANDARStatuss":   String[] statuses;
                                                  statuses = valueString[0].split("\\|");
                                                  if(statuses.length == 1)
                                                  {
                                                      valueList.add(valueString[0]);
                                                      valueList.add("");
                                                  } 
                                                  else
                                                  {
                                                      valueList.add(statuses[0]); 
                                                      valueList.add(statuses[1]);
                                                  }
                                                  statuses = valueString[1].split("\\|");
                                                  for(int i=0; i<statuses.length; i++)
                                                  {
                                                      valueList.add(statuses[i]); 
                                                  }
                                                  while(valueList.size() < 5)
                                                  {
                                                      valueList.add("");
                                                  }
                                                  break;
                
            case "findByForInvoice":              statuses = valueString[0].split("\\|");
                                                  if(statuses.length == 1)
                                                  {
                                                      valueList.add(valueString[0]);
                                                      valueList.add("");
                                                  } 
                                                  else
                                                  {
                                                      valueList.add(statuses[0]); 
                                                      valueList.add(statuses[1]);
                                                  }
                                                  statuses = valueString[1].split("\\|");
                                                  for(int i=0; i<statuses.length; i++)
                                                  {
                                                      valueList.add(statuses[i]); 
                                                  }
                                                  while(valueList.size() < 5)
                                                  {
                                                      valueList.add("");
                                                  }
                                                  break;    
        }
        return super.findBy("AOrder."+query, valueList);
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
    @Path("retrieveOrdersByType/{orderType}")
    public List<AOrder> retrieveAll(@PathParam("orderType") String orderType)
    {
        List<AOrder> getAll = null;
               switch (orderType) {
                  case "CancelOrder":
                      getAll = findBy("findByAOStatussANDARStatuss", "D,D|J");
                      break;
                  case "ReceiveOrder":
                      getAll = findBy("findByAOStatussANDARStatuss", "D,D|I|J");
                      break;
                  case "AcquisitionInvoice":
                      getAll = findBy("findByForInvoice", "D|X,D|I|J");
                      break;    
                  default: getAll =  findAll();
             
        }
//        else if(accept.equals("ObjectList"))
//        {
//            genericType = new GenericType<List<AOrder>>(){};
//            restResponse = orderClient.findAll_XML(Response.class);
//            orderList = (List<AOrder>) restResponse.readEntity(genericType);
//
//            genericType = new GenericType<List<AOrdermaster>>(){};
//            restResponse = orderMasterClient.findAll_XML(Response.class);
//            orderMasterList = (List<AOrdermaster>) restResponse.readEntity(genericType);
//
//            request.setAttribute("orderList", orderList);
//            request.setAttribute("orderMasterList", orderMasterList);
//        }
        return getAll;
    }
    
    
    
    @POST
    @Path("createNewOrder")
    @Consumes({"application/x-www-form-urlencoded"})
    public String createNewOrder(Form form,  @FormParam("supplier") String supplier,
    @FormParam("orderDate") String orderDate,@FormParam("expDate") String expDate,
    @FormParam("orderNo") String orderNo,@FormParam("itemNos") String itemNos)
    {
        // /----------------   getOrderMaster -------------------------------------//
        AOrdermaster orderMaster = new AOrdermaster();

        aSupplier = aSupplierDetailFacadeREST.find(supplier);
        if (aSupplier == null) {
          //  throw new DataException("Please enter valid supplier code : " + supplier);
        }

        System.out.println("aSupplier " + aSupplier);
        orderMaster.setAOSupCd((ASupplierDetail) aSupplier);
        try {
            orderMaster.setAOPoDt(new SimpleDateFormat("yyyy-MM-dd").parse(orderDate));
            orderMaster.setAOExpDt(new SimpleDateFormat("yyyy-MM-dd").parse(expDate));
        } catch (ParseException ex) {
            Logger.getLogger(AOrderFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }

        orderMaster.setAOPoNo1(orderNo);
        orderMaster.setAOSAdd1(form.asMap().getFirst("address1"));
        orderMaster.setAOSAdd2(form.asMap().getFirst("address2"));
        orderMaster.setAOSCity(form.asMap().getFirst("city"));
        orderMaster.setAOSState(form.asMap().getFirst("state"));
        orderMaster.setAOSCountry(form.asMap().getFirst("country"));
        orderMaster.setAOSPin(form.asMap().getFirst("pin"));
        orderMaster.setAOSEmail(form.asMap().getFirst("email"));
        orderMaster.setAOStatus("D");     // "D" means Ordered
        //orderMaster = getOrderMaster(request, response);
        orderMaster = aOrdermasterFacadeREST.createAndGet(orderMaster);
        //-------------------------- getOrderList--------------------------------//
        List<AOrder> orderList = new ArrayList<>();
        AOrder order = null;
        int noOfRequests = Integer.parseInt(itemNos);

        for (int i = 0; i < noOfRequests; i++) {
            order = new AOrder();

            //   request.setAttribute("operation", "Retrieve");
            // request.setAttribute("accept", "Object");

            //  request.setAttribute("requestNo", info.getQueryParameters().getFirst("request"+(i+1)));
            String orderRequestStatus = "";
            //  request.getRequestDispatcher("./RequestServlet").include(request, response);
            System.out.println("req........  " + form.asMap().getFirst("request" + (i + 1)));
            ARequest req = (ARequest) aRequestFacadeREST.find(Integer.parseInt(form.asMap().getFirst("request" + (i + 1))));

            if (req == null) {
              //  throw new DataException("Please enter valid request No. : " + "request" + (i + 1));
            }

            System.out.println("req  " + req);
            order.setAOrdermaster(orderMaster);
            order.setAOrderPK(new AOrderPK(Integer.parseInt(form.asMap().getFirst("request" + (i + 1))), orderMaster.getAOPoNo()));

            order.setAOOrderedCopy(Integer.parseInt(form.asMap().getFirst("orderedCopies" + (i + 1))));

            if (form.asMap().getFirst("partialOrdered" + (i + 1)).equals("true")) {
                orderRequestStatus = "J";
            } else {
                orderRequestStatus = "D";
            }

            order.setAOPendingCopy(order.getAOOrderedCopy());
            order.setAOTotalreceivedCopy(0);
            order.setAOPaidCopies(0);

            order.setAOPrice(req.getARPrice());
            order.setAIConvrate(req.getARConvRate().longValue());

            order.setARStatus(orderRequestStatus);     //"D" for Ordered

            //set budget for item and increase its Poraised amount
            ABudget budget = aBudgetFacadeREST.find(Integer.parseInt(form.asMap().getFirst("budgetCode" + (i + 1))));

            if (req == null) {
              //  throw new DataException("Please enter valid budget code : " + "request" + (i + 1));
            }
            BigDecimal amount = budget.getABPoraisedAmt().add(order.getAOPrice().multiply(new BigDecimal(order.getAOOrderedCopy())).multiply(req.getARConvRate()));
            budget.setABPoraisedAmt(amount);
            aBudgetFacadeREST.edit(budget);
            //budget is set

            //set request
            req.setARBudgetCd(budget);
            req.setARPoDt(orderMaster.getAOPoDt());
            req.setARPoNo(orderMaster);
            req.setARStatus(orderRequestStatus);
            aRequestFacadeREST.edit(req);
            order.setARequest(req);
            //request is set
            orderList.add(order);
        }

        // orderList = getOrderList(request, response, orderMaster);
        Iterator i = orderList.iterator();
        AOrder o = new AOrder();
        while (i.hasNext()) {
            o = (AOrder) i.next();
              create(o);
        }
        /*if(form.equals("NewOrder"))
         response.sendRedirect("./jsp/newOrder.jsp");
         else if(form.equals("DirectOrder"))
         response.sendRedirect("./jsp/directOrder.jsp");*/
        output = "Order Placed.";
        return output;
    }
    
  @PUT
  @Path("cancelOrder")
  @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
  public String cancelOrder(Form form,@FormParam("itemNos") String item_Nos,@FormParam("selectedAll") String selectedAll,
  @FormParam("itemNo1") String item_No1)
  
  {    
        AOrdermaster orderMaster = new AOrdermaster();
        ARequest req = new ARequest();
        AOrder order = new AOrder();
        ABudget budget = new ABudget();
        List<AOrdermaster> orderMasterList = new ArrayList<>();
        int itemNos = Integer.parseInt(item_Nos);
        if(selectedAll.equals("true"))
        { 
           String[] itemNo1 = item_No1.split(";");
           String aRNo = itemNo1[1].split("=")[1];
           String aOPoNo = itemNo1[2].split("=")[1];
            System.out.println("aRNo  "+aRNo);
           // String aOPoNo = form.asMap().get("itemNo1").get(1);
            System.out.println("aOPoNo  "+aOPoNo);
            AOrderPK aorderPk = new AOrderPK();
            aorderPk.setARNo(Integer.parseInt(aRNo));
            aorderPk.setAOPoNo(Integer.parseInt(aOPoNo));
            orderMaster = find(aorderPk).getAOrdermaster();
            orderMaster.setAOStatus("C");
            aOrdermasterFacadeREST.edit(orderMaster);
        }
        for(int ii=1; ii-1<itemNos; ii++)
        {
             String[] itemNo_ = form.asMap().getFirst("itemNo"+ii).split(";");
             String aRNo_ = itemNo_[1].split("=")[1];
             String aOPoNo_ = itemNo_[2].split("=")[1];
            // AOrderPK aorderPk = getPrimaryKey(itemNo1);
            // String aRNo = form.asMap().get("itemNo1").get(0);
            System.out.println("aRNo_  "+aRNo_);
            // String aOPoNo = form.asMap().get("itemNo1").get(1);
            System.out.println("aOPoNo_  "+aOPoNo_);
            AOrderPK aorderPk_ = new AOrderPK();
            aorderPk_.setARNo(Integer.parseInt(aRNo_));
            aorderPk_.setAOPoNo(Integer.parseInt(aOPoNo_));
            order = find(aorderPk_);
            order.setARStatus("C");
            req = order.getARequest();
            if(!req.getARStatus().equals("J"))      //partial ordered request must not be set to approved status
                req.setARStatus("A");               //otherwise all items previously ordered will be excess than user can currently order

            //set budget for item and increase its Poraised amount
            budget = req.getARBudgetCd();
            BigDecimal amount = budget.getABPoraisedAmt().subtract(order.getAOPrice().multiply(new BigDecimal(order.getAOOrderedCopy())).multiply(req.getARConvRate()));
            budget.setABPoraisedAmt(amount);
            aBudgetFacadeREST.edit(budget);
            //budget is set

            aRequestFacadeREST.edit(req);
            edit(order);
        }
        output = "Order Canceled.";
       return output;
        //response.sendRedirect("./jsp/cancelOrder.jsp");
   }
  
  @GET
  @Path("getOrders/{requestNo}")
  //public int getOrders(@QueryParam("requestNo") String requestNo)
  public int getOrders(@PathParam("requestNo") String requestNo)        
  {
      //  genericType = new GenericType<List<AOrder>>(){};
      //  restResponse = orderClient.findBy(Response.class, "findByARNo", (String)request.getParameter("requestNo"));
        List<AOrder>  orderList = (List<AOrder>) findBy("findByARNo",requestNo);
        int orderedCopies = 0;
        AOrder o = new AOrder();
        for (Iterator<AOrder> it = orderList.iterator(); it.hasNext();) {
            o = it.next();
            if(!o.getARStatus().equals("C"))            //canceled ordered quantities of items must not be included in ordered items becouse they
                orderedCopies += o.getAOOrderedCopy();  //must be available to be ordered    
        }
        return orderedCopies;
   }
 
//    @GET
//    @Path("getApprovedOrderListByParam/{paramName}/{paramValue}")
//    @Produces({"application/xml", "application/json"})
//    public List<AOrder> getOrderListByParam(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) 
//    {
//        String[] valueString = paramValue.split(",");
//        List<Object> valueList = new ArrayList<>();
//        List<AOrder> getOrderList = null;
//        switch(paramName){
//            case "SupplierName":
//                valueList.add((valueString[0]));
//                getOrderList = super.findBy("AOrder.findBySupplierName", valueList );
//                break;
//            case "Department":
//                valueList.add((valueString[0]));
//                getOrderList = super.findBy("AOrder.findByDept", valueList );
//                break;
//          }
//        return  getOrderList;
//    }
  
          
    @GET
    @Path("getAllOrderedItemList")
    public List<AOrder> getAllOrderedItemList()
    {
        List<AOrder> getOrderList = null;
        getOrderList = super.findBy("AOrder.findByAOStatusStatusOrdered");
        return getOrderList;
     }      
          
    @GET
    @Path("getAllOrderedItemListByOrderNo/{orderNo}")
    public List<AOrder> getAllOrderedItemListByOrderNo(@PathParam("orderNo") String orderNo)
    {
        //String[] valueString = paramValue.split(",");
        List<Object> valueList = new ArrayList<>();
        List<AOrder> getOrderList = null;
        valueList.add(Integer.parseInt(orderNo));
        getOrderList = super.findBy("AOrder.findByAOPoNoAndStatusOrdered" , valueList);
        return getOrderList;
     }
    
    
    @GET
    @Path("getOrderNoListBySupplierForReceive/{supplier}")
    @Produces("text/plain")
    public String getOrderNoListBySupplierForReceive(@PathParam("supplier") String supplier)
    {
        //String[] valueString = paramValue.split(",");
        List<Object> valueList = new ArrayList<>();
        List<AOrder> getOrderList = new ArrayList<>();
        List<Integer> getOrderNoList = new ArrayList<>();
        valueList.add(supplier);
        getOrderList = super.findBy("AOrder.findOrderNoBySupplierForReceive" , valueList);
        System.out.println("getOrderList"  +getOrderList.size());
        String  orderNo = "";
        for(int i =0;i<getOrderList.size();i++)
        {            
           orderNo = orderNo+String.valueOf(getOrderList.get(i).getAOrdermaster().getAOPoNo());
           if(i<getOrderList.size()-1)
           {
               orderNo = orderNo+",";
           }
        }
       
        return orderNo;
     }
    
    @GET
    @Path("getOrderNoListBySupplierForInvoice/{supplier}/{paymentType}")
    @Produces("text/plain")
    public String getOrderNoListBySupplierForInvoice(@PathParam("supplier") String supplier,@PathParam("paymentType") String paymentType)
    {
        //String[] valueString = paramValue.split(",");
        List<Object> valueList = new ArrayList<>();
        List<AOrder> getOrderList = new ArrayList<>();
        List<Integer> getOrderNoList = new ArrayList<>();
        valueList.add(supplier);  //suppcD
        if(paymentType.contentEquals("regular"))
        {
            getOrderList = super.findBy("AOrder.findOrderNoBySupplierForInvoiceRegular" , valueList);
        }
        if(paymentType.contentEquals("advance"))
        {
             getOrderList = super.findBy("AOrder.findOrderNoBySupplierForInvoiceAdvance" , valueList);
        }
        
        System.out.println("getOrderList"  +getOrderList.size());
        String  orderNo = "";
        for(int i =0;i<getOrderList.size();i++)
        {            
           orderNo = orderNo+String.valueOf(getOrderList.get(i).getAOrdermaster().getAOPoNo());
           if(i<getOrderList.size()-1)
           {
               orderNo = orderNo+",";
           }
        }
       
        return orderNo;
     }
    
    @GET
    @Path("getOrderNoListForInvoice/{paymentType}")
    @Produces("text/plain")
    public String getOrderNoListBySupplierForInvoice(@PathParam("paymentType") String paymentType)
    {
        //String[] valueString = paramValue.split(",");
        List<Object> valueList = new ArrayList<>();
        List<AOrder> getOrderList = new ArrayList<>();
        List<Integer> getOrderNoList = new ArrayList<>();
      
        if(paymentType.equalsIgnoreCase("regular"))
        {
            getOrderList = super.findBy("AOrder.findOrderNoForInvoiceRegular" , valueList);
        }
        if(paymentType.equalsIgnoreCase("advance"))
        {
             getOrderList = super.findBy("AOrder.findOrderNoForInvoiceAdvance" , valueList);
        }
        
        System.out.println("getOrderList"  +getOrderList.size());
        String  orderNo = "";
        for(int i =0;i<getOrderList.size();i++)
        {            
           orderNo = orderNo+String.valueOf(getOrderList.get(i).getAOrdermaster().getAOPoNo());
           if(i<getOrderList.size()-1)
           {
               orderNo = orderNo+",";
           }
        }
       
        return orderNo;
     }
}
