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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
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
import soul.general_master.ABudget;
import soul.general_master.service.ABudgetFacadeREST;
import soul.serialControl.SMst;
import soul.serialControl.SSubdetail;
import soul.serialControl.SSubdetailPK;
import soul.serialControl.SSubscription;
import soul.general_master.Autogenerate;
import soul.general_master.service.AutogenerateFacadeREST;
import soul.general_master.ACurrency;
import soul.general_master.service.ACurrencyFacadeREST;
import soul.general_master.ASupplierDetail;
import soul.general_master.service.ASupplierDetailFacadeREST;
import soul.serial_master.SSupplierDetail;
import soul.serial_master.service.SSupplierDetailFacadeREST;
/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.serialcontrol.ssubscription")
public class SSubscriptionFacadeREST extends AbstractFacade<SSubscription> {
    @EJB
    private SMstFacadeREST sMstFacadeREST;
    @EJB
    private SSubdetailFacadeREST sSubdetailFacadeREST;
    @EJB
    private AutogenerateFacadeREST autogenerateFacadeREST;
    @EJB
    private ABudgetFacadeREST aBudgetFacadeREST;
    @EJB
    private ACurrencyFacadeREST aCurrencyFacadeREST;
    @EJB
    private SSupplierDetailFacadeREST sSupplierDetailFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String getAll = "";
    int count = 0;
    List<SMst> sMstList = new ArrayList<>();
    SSubscription sSubscription;
    
    Autogenerate autogenerate;
    ASupplierDetail supplierDetail;
    SSubdetail sSubdetail;
    BigDecimal totalPrice;
    private String[] recIds;
    ABudget budget;
    SMst sMst;
    String[] rows;
    private String[] recdIds;
    StringReader reader;
    JsonReader jsonReader;
    JsonObject jsonObject;
    Autogenerate auto;
    BigDecimal convRate, apprCopies, poraisedAmount;
    
    public SSubscriptionFacadeREST() {
        super(SSubscription.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(SSubscription entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(SSubscription entity) {
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
    public SSubscription find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SSubscription> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SSubscription> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("saveOrderProcess/{rows}")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
   // public String saveOrderProcess(Form form,@QueryParam("rows") List<String> row)
    public String saveOrderProcess(Form form,@PathParam("rows") List<String> row)        
    {
        /*Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String string = parameterNames.nextElement();
            System.out.println("Name :"+string);
        }*/

        /***********************************************
         * 1. Entry in SSubscription
         * 2. Entry in SSubdetail
         * 3. Update of SMst
         * 4. Update of ABudget AbPoraised amount (Committed Amount)
         */

     //   rows = rows[];
        String[] rows = new String[row.size()];
        rows = row.toArray(rows);
      //  String[]  rows = (String[]) row.toArray();
        reader = new StringReader(rows[0]);
        jsonReader = Json.createReader(reader);
        jsonObject = jsonReader.readObject();
        sMst = sMstFacadeREST.find(jsonObject.getString("recordId"));    //recordId is column name of grid


        // 1. Entey in SSubscription
        sSubscription = getSubscription(form, null, sMst);
        create(sSubscription);

        for(int i=0; i<rows.length; i++){
            reader = new StringReader(rows[i]);
            jsonReader = Json.createReader(reader);
            jsonObject = jsonReader.readObject();

            sMst = sMstFacadeREST.find(jsonObject.getString("recordId"));    //recordId is column name of grid

            // 2. Entey in SSubdetail
            sSubdetail = getSubdetail(form, jsonObject, sSubscription, sMst);
            sSubdetailFacadeREST.create(sSubdetail);

            // 3. Update of SMst
            sMst = setSMst(form, sMst, sSubdetail, jsonObject);
            sMstFacadeREST.edit(sMst);

            //4. Update of ABudget
            convRate = sMst.getSConvRate();
            apprCopies = new BigDecimal(sMst.getSApprCopy());
            totalPrice = sSubdetail.getSPrice().multiply(convRate).multiply(apprCopies);

            budget = sMst.getSReqNo().getSRBudgetCd();
            poraisedAmount = budget.getABPoraisedAmt();
            poraisedAmount = poraisedAmount.add(totalPrice);
            budget.setABPoraisedAmt(poraisedAmount);

            aBudgetFacadeREST.edit(budget);
        }
        return "Order Placed.";
   }
    
     @POST
    @Path("renew")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
   // public String renew(Form form,@QueryParam("rows") List<String> row)
    public String renew(Form form,@PathParam("rows") List<String> row)        
    {
        String[] rows = new String[row.size()];
        rows = row.toArray(rows);
       // rows = request.getParameterValues("rows[]");
        reader = new StringReader(rows[0]);
        jsonReader = Json.createReader(reader);
        jsonObject = jsonReader.readObject();
        sMst = sMstFacadeREST.find(jsonObject.getString("recordId"));    //recordId is column name of grid


        // 1. Entey in SSubscription
        sSubscription = getSubscription(form, null, sMst);
        sSubscription.setSType("R");        //For Renew Order
        create(sSubscription);

        for(int i=0; i<rows.length; i++){
            reader = new StringReader(rows[i]);
            jsonReader = Json.createReader(reader);
            jsonObject = jsonReader.readObject();

            sMst = sMstFacadeREST.find(jsonObject.getString("recordId"));    //recordId is column name of grid

            // 2. Entey in SSubdetail
            sSubdetail = getSubdetail(form, jsonObject, sSubscription, sMst);
            //sSubscription.setSType("R");
            sSubdetail.setSStatus("Y");     //For Renew Order
            sSubdetailFacadeREST.create((sSubdetail));

            // 3. Update of SMst
            sMst = setSMst(form, sMst, sSubdetail, jsonObject);
            sMstFacadeREST.edit(sMst);

            //4. Update of ABudget
            convRate = sMst.getSConvRate();
            apprCopies = new BigDecimal(sMst.getSApprCopy());
            totalPrice = sSubdetail.getSPrice().multiply(convRate).multiply(apprCopies);

            budget = sMst.getSReqNo().getSRBudgetCd();
            poraisedAmount = budget.getABPoraisedAmt();
            poraisedAmount = poraisedAmount.add(totalPrice);
            budget.setABPoraisedAmt(poraisedAmount);

            aBudgetFacadeREST.edit(budget);
        }
       return "Order Renewed.";
     }
    
    
      //*********** Subscription ********************
    static SSubscription getSubscription(Form form, JsonObject jsonObject, SMst sMst){
        SSubscription sSubscription;
        sSubscription = new SSubscription();
        
        sSubscription.setSOrderNo(form.asMap().getFirst("orderNo"));
        try {
            sSubscription.setSOrderDt(new SimpleDateFormat("yyyy-MM-dd").parse(form.asMap().getFirst("orderDate")));
        } catch (ParseException ex) {
            sSubscription.setSOrderDt(new Date());
            Logger.getLogger(SSubscriptionFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        sSubscription.setSType("N");
        sSubscription.setSCancel("N");
        sSubscription.setSOrderType(form.asMap().getFirst("newTypeOfOrder"));
        sSubscription.setSRemark(form.asMap().getFirst("remarks"));
        sSubscription.setSSupCd(sMst.getSSupCd());
        sSubscription.setSSupCity(sMst.getSSupCity());
        sSubscription.setSSupCountry(sMst.getSSupCountry());
        String type = form.asMap().getFirst("supPubSelect");
        type = "supplier".equals(type) ? "Y" : "publisher".equals(type) ? "X" : "";
        sSubscription.setSPubsup(type);
        sSubscription.setSInvstatus("N");

        return sSubscription;
    }
    
    //*********** Subdetail *****************
    
      //*********** Subdetail *****************
    static SSubdetail getSubdetail(Form form, JsonObject jsonObject, SSubscription sSubscription, SMst sMst){
        SSubdetail sSubdetail;
        String status = "R";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        sSubdetail = new SSubdetail();
        
        SSubdetailPK sSubdetailPK = new SSubdetailPK();
        sSubdetailPK.setSOrderNo(sSubscription.getSOrderNo());
        sSubdetailPK.setSRecid(sMst.getSRecid());
        sSubdetail.setSSubdetailPK(sSubdetailPK);
        sSubdetail.setSSubscription(sSubscription);
        sSubdetail.setSMst(sMst);
        try {
            sSubdetail.setSStDt(dateFormat.parse(jsonObject.getString("subscriptionDate")));
            sSubdetail.setSEnDt(dateFormat.parse(jsonObject.getString("expiryDate")));
        } catch (ParseException ex) {
            Logger.getLogger(SSubscriptionFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        sSubdetail.setSStVol("".equals(jsonObject.getString("volumeFrom")) ? "0" : jsonObject.getString("volumeFrom"));
        sSubdetail.setSStIss("".equals(jsonObject.getString("issueFrom")) ? "0" : jsonObject.getString("issueFrom"));
        sSubdetail.setSEnVol("".equals(jsonObject.getString("volumeTo")) ? "0" : jsonObject.getString("volumeTo"));
        sSubdetail.setSEnIss("".equals(jsonObject.getString("issueTo")) ? "0" : jsonObject.getString("issueTo"));
        
        sSubdetail.setSStatus(status);
        sSubdetail.setSCopy(sMst.getSApprCopy());
        sSubdetail.setSMode(jsonObject.getString("deliveryType"));
        
        BigDecimal price;
        if(sSubscription.getSOrderType().equals("S")){
            try{
                price = new BigDecimal(jsonObject.getString("pricePerIssue"));
            }catch(Exception e){
                price = sMst.getSPrice();
            }
        }
        else
            price = sMst.getSPrice();
        
        sSubdetail.setSPrice(price);
        
        return sSubdetail;
    }
    
    //*********** SMst *****************
    static SMst setSMst(Form form, SMst sMst, SSubdetail sSubdetail, JsonObject jsonObject){
        
        sMst.setSStDt(sSubdetail.getSStDt());
        sMst.setSEnDt(sSubdetail.getSEnDt());
        //set volumeFrom, volumeTo, issueFrom & issueTo to
        //0 if not set by user else to value set by user
        sMst.setSStVol("".equals(jsonObject.getString("volumeFrom")) ? "0" : jsonObject.getString("volumeFrom"));
        sMst.setSEnVol("".equals(jsonObject.getString("volumeTo")) ? "0" : jsonObject.getString("volumeTo"));
        sMst.setSStIss("".equals(jsonObject.getString("issueFrom")) ? "0" : jsonObject.getString("issueFrom"));
        sMst.setSEnIss("".equals(jsonObject.getString("issueTo")) ? "0" : jsonObject.getString("issueTo"));
        
        sMst.setSMode(jsonObject.getString("deliveryType"));
        sMst.setSCurrency(jsonObject.getString("currency"));
        sMst.setSConvRate(new BigDecimal(jsonObject.getString("rate")));
        sMst.setSOrderNo(sSubdetail.getSSubdetailPK().getSOrderNo());
        sMst.setSMstStatus("O");
        
        if(sSubdetail.getSSubscription().getSOrderType().equals("S")){
            try {
                sMst.setSPriceperissue(new BigDecimal(jsonObject.getString("pricePerIssue")));
            } catch (Exception e) {
                sMst.setSPriceperissue(null);
            }
            
        }
        return sMst;
    }
    
    //Added Manually
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<SSubscription> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        List<String> smstStatus = new ArrayList<>();
        
        switch(query)
        {
            case "findOrdersBySupplierCode":     valueList.add(valueString[0]);
                                                  
                                                break;
                                                
            case "findOrdersForPurchasing": break;
            case "getSPubsup": break;
            case "getSupPubforInvoiceProcessing": break;
            
            case "getOrderDetailsByOrderNo": valueList.add(valueString[0]);
                
                break;
                                                
            default:    valueList.add(valueString[0]);
                        break;
                        //
                
        }
        return super.findBy("SSubscription."+query, valueList);
    }
    
    @GET
    @Path("findOrdersBySupplierCode/{supplierCode}")
    @Produces({"application/xml", "application/json"})
    public List<SSubscription> findOrdersBySupplierCode(@PathParam("supplierCode") String supplierCode) {
        List<SSubscription> getAll = null;
        getAll = findBy("findOrdersBySupplierCode", supplierCode);
        return getAll;
    }
    
    @GET
    @Path("findOrdersForPurchasing")
    @Produces({"application/xml", "application/json"})
    public List<SSubscription> findOrdersForPurchasing() {
        List<SSubscription> getAll = null;
        getAll = findBy("findOrdersForPurchasing", "null");
        return getAll;
    }
    
    //for retrieving data in check-in service -> check-in tab
    @GET
    @Path("getOrderDetailsByOrderNo/{orderNo}")
    @Produces({"application/xml", "application/json"})
    public List<SSubscription> getOrderDetailsByOrderNo(@PathParam("orderNo") String orderNo) {
        List<SSubscription> getAll = null;
        getAll = findBy("findBySOrderNo", orderNo);
        return getAll;
    }
    
    
    @GET
    @Path("checkOrderNo")
    @Produces("text/plain")
  //  public String checkOrderNo(@QueryParam("orderNo") String orderNo) 
    public String checkOrderNo(@PathParam("orderNo") String orderNo)        
    {
        //Checks if orderNo is already used or not
       sSubscription = null;
       sSubscription = find(orderNo);
       if(sSubscription == null)
          return "Not Found";
       else
          return "Found";   
    }
  
    @POST
    @Path("cancelOrder")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
   // public String cancelOrder(Form form, @QueryParam("NoOfTitles") String NoOfTitles, @QueryParam("recordIds") List<String> record_Ids) {
    public String cancelOrder(Form form, @PathParam("NoOfTitles") String NoOfTitles, @PathParam("recordIds") List<String> record_Ids) {    
        /*Enumeration<String> parameterNames = request.getParameterNames();
      while (parameterNames.hasMoreElements()) {
          String string = parameterNames.nextElement();
          System.out.println("Name :"+string);
      }*/
        int noOfTitles = Integer.parseInt(NoOfTitles);
        String orderNo = form.asMap().getFirst("orderNo");
        String recordId;
        BigDecimal price, convRate, apprCopies, totalPrice, poraisedAmount;
        ABudget budget;
        SSubdetailPK sSubdetailPK = new SSubdetailPK();
        for (int i = 0; i < noOfTitles; i++) {
            String[] record_Id = new String[record_Ids.size()];
            record_Id = record_Ids.toArray(record_Id);
            // rows = request.getParameterValues("rows[]");
            // reader = new StringReader(rows[0]);
            recordId = record_Id[i];

            //Update of SMst : sMstStatus
            sMst = sMstFacadeREST.find(recordId);
            sMst.setSMstStatus("D");
            sMstFacadeREST.edit(sMst);

            //Update of SSubdetail: 1. sCancel = Y 2. sStatus = N
            sSubdetailPK.setSOrderNo(orderNo);
            sSubdetailPK.setSRecid(recordId);
            sSubdetail = sSubdetailFacadeREST.find(sSubdetailPK);
            sSubdetail.setSCancel("Y");
            sSubdetail.setSStatus("N");
            sSubdetailFacadeREST.edit(sSubdetail);

            if (form.asMap().getFirst("cancelCompleteOrder") != null) {
                sSubscription = find(orderNo);
                sSubscription.setSCancel("Y");
                sSubscription.setSCanremark(form.asMap().getFirst("remarks"));
                try {
                    sSubscription.setSCancelDt(new SimpleDateFormat("yyyy-MM-dd").parse(form.asMap().getFirst("date")));
                } catch (ParseException ex) {
                    sSubscription.setSCancelDt(new Date());
                    Logger.getLogger(SSubscriptionFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }

                edit(sSubscription);
            }

            //Update of Budget's ABPoraised amount
            convRate = sMst.getSConvRate();
            apprCopies = new BigDecimal(sMst.getSApprCopy());
            totalPrice = sSubdetail.getSPrice().multiply(convRate).multiply(apprCopies);

            budget = sMst.getSReqNo().getSRBudgetCd();
            poraisedAmount = budget.getABPoraisedAmt();
            poraisedAmount = poraisedAmount.subtract(totalPrice);
            budget.setABPoraisedAmt(poraisedAmount);

            aBudgetFacadeREST.edit(budget);
        }
        return "Order Canceled.";
    }
    
    
    //used for making order
    //without js communication
    @POST
    @Path("orderProcess")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String orderProcess(@FormParam("recordId") String recordIds,
            @FormParam("orderDate") String subscOrderDate, 
            @Pattern(regexp = "^[a-zA-Z]{1}$", message = "{orderType.pattern}") @FormParam("subscOrderType") String subscOrderType,
            @FormParam("remarks") String subscRemarks,
            @FormParam("subscriptionDate") String subDetSubscriptionDate, @FormParam("expiryDate") String subDetExpiryDate,
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("volumeFrom") String subDetvolumeFrom, 
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("issueFrom") String subDetissueFrom,
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("volumeTo") String subDetvolumeTo, 
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("issueTo") String subDetissueTo,
            @Pattern(regexp = "^[A-Z]{1}$", message = "{delivery.pattern}") @FormParam("deliveryType") String sMstdeliveryType, 
            @Pattern(regexp = "^[a-zA-Z]{3}$", message = "{currency.pattern}") @FormParam("currency") String sMstcurrency
            ) {
        System.out.println("recordId  "+recordIds);
        //Generate new order no dynamically and update autogenerate
        //SR/ORD/YY/SS -> YY current year, SS last seed from autogenerate
        String orderNo = "SR/ORD/"; //change it to dynamic from authogenerate table
        orderNo = orderNo + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2) + "/";
        System.out.println("orderNo: " + orderNo);
        List<Autogenerate> autogenerate = new ArrayList<>();
        autogenerate = autogenerateFacadeREST.findBy("findByName", "Serial Order");
        long lastSeed = autogenerate.get(0).getLastSeed();
        auto = autogenerateFacadeREST.find(autogenerate.get(0).getId());
        //System.out.println(lastSeed++);
        lastSeed = lastSeed + 1;
        String newOrderNo = orderNo + lastSeed;
        auto.setLastSeed(lastSeed);
        autogenerateFacadeREST.edit(auto);
        System.out.println("orderNo: " + newOrderNo);
        //Generate new order no dynamically and update autogenerate ends here 
        
        sSubscription = new SSubscription();
        //sMst = new SMst();
        sSubdetail = new SSubdetail();
        budget = new ABudget();
        
        try {
            sSubscription.setSOrderDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(subscOrderDate));
        } catch (ParseException ex) {
            sSubscription.setSOrderDt(new Date());
            Logger.getLogger(SSubscriptionFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
           
         recdIds = recordIds.split(",");
        for (String recId : recdIds) {
            try {
                sMst = sMstFacadeREST.find(recId);
            } catch (NullPointerException e) {
                return "Invalid record ID.";
            }
        }
        sSubscription.setSOrderNo(newOrderNo);
        sSubscription.setSType("N");
        sSubscription.setSCancel("N");
        sSubscription.setSOrderType(subscOrderType);
        sSubscription.setSRemark(subscRemarks);
        sSubscription.setSSupCd(sMst.getSSupCd());
        sSubscription.setSSupCity(sMst.getSSupCity());
        sSubscription.setSSupCountry(sMst.getSSupCountry());
        String pubCode = sMst.getSPubCd();
        if(pubCode.equals(sMst.getSSupCd())){
            sSubscription.setSPubsup("Y");
        }else{
            sSubscription.setSPubsup("X");
        }
        
        sSubscription.setSInvstatus("N");
       
       // sSubscription.setSInvstatus("N");
        create(sSubscription);
        //Subscription finished

        recdIds = recordIds.split(",");
        for (String recId : recdIds) {
            try {
                sMst = sMstFacadeREST.find(recId);
            } catch (NullPointerException e) {
                return "Invalid record ID.";
            }
            String subDetstatus = "R";
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            // 1. Entey in SSubdetail
            sSubdetail = new SSubdetail();

            SSubdetailPK sSubdetailPK = new SSubdetailPK();
            sSubdetailPK.setSOrderNo(sSubscription.getSOrderNo());
            sSubdetailPK.setSRecid(sMst.getSRecid());
            sSubdetail.setSSubdetailPK(sSubdetailPK);
            sSubdetail.setSSubscription(sSubscription);
            sSubdetail.setSMst(sMst);
            try {
                sSubdetail.setSStDt(dateFormat.parse(subDetSubscriptionDate));
                sSubdetail.setSEnDt(dateFormat.parse(subDetExpiryDate));
            } catch (ParseException ex) {
                Logger.getLogger(SSubscriptionFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            sSubdetail.setSStVol(subDetvolumeFrom);
            sSubdetail.setSStIss(subDetissueFrom);
            sSubdetail.setSEnVol(subDetvolumeTo);
            sSubdetail.setSEnIss(subDetissueTo);

            sSubdetail.setSStatus(subDetstatus);
            sSubdetail.setSCopy(sMst.getSApprCopy());
            sSubdetail.setSMode(sMstdeliveryType);

            BigDecimal subDetPrice;
            if (sSubscription.getSOrderType().equals("S")) {
                try {
                    subDetPrice = new BigDecimal(jsonObject.getString("pricePerIssue"));
                } catch (Exception e) {
                    subDetPrice = sMst.getSPrice();
                }
            } else {
                subDetPrice = sMst.getSPrice();
            }

            sSubdetail.setSPrice(subDetPrice);
            sSubdetailFacadeREST.create(sSubdetail);

            //SSubdetail finished
            // 3. Update of SMst
            sMst.setSStDt(sSubdetail.getSStDt());
            sMst.setSEnDt(sSubdetail.getSEnDt());
            //set volumeFrom, volumeTo, issueFrom & issueTo to
            //0 if not set by user else to value set by user
            sMst.setSStVol(subDetvolumeFrom);
            sMst.setSStIss(subDetissueFrom);
            sMst.setSEnVol(subDetvolumeTo);
            sMst.setSEnIss(subDetissueTo);
            sMst.setSMode(sMstdeliveryType);
            sMst.setSCurrency(sMstcurrency);
            List<ACurrency> sMstConvRate = aCurrencyFacadeREST.findBy("findByACCd", sMstcurrency);
            sMst.setSConvRate(sMstConvRate.get(0).getACRate());
            sMst.setSOrderNo(sSubdetail.getSSubdetailPK().getSOrderNo());
            sMst.setSMstStatus("O");

            if (sSubdetail.getSSubscription().getSOrderType().equals("S")) {
                try {
                    sMst.setSPriceperissue(new BigDecimal(jsonObject.getString("pricePerIssue")));
                } catch (Exception e) {
                    sMst.setSPriceperissue(null);
                }
            }
            sMstFacadeREST.edit(sMst);
            //sMst finished

            //4. Update of ABudget
            convRate = sMst.getSConvRate();
            apprCopies = new BigDecimal(sMst.getSApprCopy());
            totalPrice = sSubdetail.getSPrice().multiply(convRate).multiply(apprCopies);

            budget = sMst.getSReqNo().getSRBudgetCd();
            poraisedAmount = budget.getABPoraisedAmt();
            poraisedAmount = poraisedAmount.add(totalPrice);
            budget.setABPoraisedAmt(poraisedAmount);

            aBudgetFacadeREST.edit(budget);
            //Update of a_budget finisghed
        }
        return "Order Placed";
    }
    
    //used for making order
    //without js communication
    @POST
    @Path("renewOrder")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String renewOrder(@FormParam("recordId") String recordIds,
            @FormParam("subscOrderDate") String subscOrderDate, 
            @Pattern(regexp = "^[a-zA-Z]{1}$", message = "{orderType.pattern}") @FormParam("subscOrderType") String subscOrderType,
            @FormParam("subscRemarks") String subscRemarks,
            @FormParam("subscriptionDate") String subDetSubscriptionDate, @FormParam("expiryDate") String subDetExpiryDate,
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("volumeFrom") String subDetvolumeFrom, 
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("issueFrom") String subDetissueFrom,
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("volumeTo") String subDetvolumeTo, 
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("issueTo") String subDetissueTo,
            @Pattern(regexp = "^[A-Z]{1}$", message = "{delivery.pattern}") @FormParam("deliveryType") String sMstdeliveryType, 
            @Pattern(regexp = "^[a-zA-Z]{3}$", message = "{currency.pattern}") @FormParam("currency") String sMstcurrency
            ) {
        //Generate new order no dynamically 
        //SR/ORD/YY/SS -> YY current year, SS last seed from autogenerate
        String orderNo = "SR/RENEW/"; //change it to dynamic from authogenerate table
        orderNo = orderNo + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2) + "/";
        System.out.println("orderNo: " + orderNo);
        List<Autogenerate> autogenerate = new ArrayList<>();
        autogenerate = autogenerateFacadeREST.findBy("findByName", "Serial Renewal Order");
        long lastSeed = autogenerate.get(0).getLastSeed();
        
        auto = autogenerateFacadeREST.find(autogenerate.get(0).getId());
        //System.out.println(lastSeed++);
        lastSeed = lastSeed + 1;
        String newOrderNo = orderNo + lastSeed;
        auto.setLastSeed(lastSeed);
        autogenerateFacadeREST.edit(auto);
        System.out.println("orderNo: " + newOrderNo);

        sSubscription = new SSubscription();
        //sMst = new SMst();
        sSubdetail = new SSubdetail();
        budget = new ABudget();
        
        try {
            sSubscription.setSOrderDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(subscOrderDate));
        } catch (ParseException ex) {
            sSubscription.setSOrderDt(new Date());
            Logger.getLogger(SSubscriptionFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
         System.out.println("recordIds "+recordIds);  
        recdIds = recordIds.split(",");
        for (String recId : recdIds) {
            try {
                sMst = sMstFacadeREST.find(recId);
            } catch (NullPointerException e) {
                return "Invalid record ID.";
            }
        }
        sSubscription.setSOrderNo(newOrderNo);
        sSubscription.setSType("R");
        sSubscription.setSCancel("N");
        sSubscription.setSOrderType(subscOrderType);
        sSubscription.setSRemark(subscRemarks);
        sSubscription.setSSupCd(sMst.getSSupCd());
        sSubscription.setSSupCity(sMst.getSSupCity());
        sSubscription.setSSupCountry(sMst.getSSupCountry());
        String pubCode = sMst.getSPubCd();
        if(pubCode.equals(sMst.getSSupCd())){
            sSubscription.setSPubsup("Y");
        }else{
            sSubscription.setSPubsup("X");
        }
        
        sSubscription.setSInvstatus("N");
       
       // sSubscription.setSInvstatus("N");
        create(sSubscription);
        //Subscription finished

        recdIds = recordIds.split(",");
        for (String recId : recdIds) {
            try {
                sMst = sMstFacadeREST.find(recId);
            } catch (NullPointerException e) {
                return "Invalid record ID.";
            }
            //String subDetstatus = "R";
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            // 1. Entey in SSubdetail
            sSubdetail = new SSubdetail();

            SSubdetailPK sSubdetailPK = new SSubdetailPK();
            sSubdetailPK.setSOrderNo(sSubscription.getSOrderNo());
            sSubdetailPK.setSRecid(sMst.getSRecid());
            sSubdetail.setSSubdetailPK(sSubdetailPK);
            sSubdetail.setSSubscription(sSubscription);
            sSubdetail.setSMst(sMst);
            try {
                sSubdetail.setSStDt(dateFormat.parse(subDetSubscriptionDate));
                sSubdetail.setSEnDt(dateFormat.parse(subDetExpiryDate));
            } catch (ParseException ex) {
                Logger.getLogger(SSubscriptionFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            sSubdetail.setSStVol(subDetvolumeFrom);
            sSubdetail.setSStIss(subDetissueFrom);
            sSubdetail.setSEnVol(subDetvolumeTo);
            sSubdetail.setSEnIss(subDetissueTo);
            sSubdetail.setSStatus("Y");
            sSubdetail.setSCopy(sMst.getSApprCopy());
            sSubdetail.setSMode(sMstdeliveryType);

            BigDecimal subDetPrice;
            if (sSubscription.getSOrderType().equals("S")) {
                try {
                    subDetPrice = new BigDecimal(jsonObject.getString("pricePerIssue"));
                } catch (Exception e) {
                    subDetPrice = sMst.getSPrice();
                }
            } else {
                subDetPrice = sMst.getSPrice();
            }

            sSubdetail.setSPrice(subDetPrice);
            sSubdetailFacadeREST.create(sSubdetail);

            //SSubdetail finished
            // 3. Update of SMst
            sMst.setSStDt(sSubdetail.getSStDt());
            sMst.setSEnDt(sSubdetail.getSEnDt());
            //set volumeFrom, volumeTo, issueFrom & issueTo to
            //0 if not set by user else to value set by user
            sMst.setSStVol(subDetvolumeFrom);
            sMst.setSStIss(subDetissueFrom);
            sMst.setSEnVol(subDetvolumeTo);
            sMst.setSEnIss(subDetissueTo);
            sMst.setSMode(sMstdeliveryType);
            sMst.setSCurrency(sMstcurrency);
            List<ACurrency> sMstConvRate = aCurrencyFacadeREST.findBy("findByACCd", sMstcurrency);
            sMst.setSConvRate(sMstConvRate.get(0).getACRate());
            sMst.setSOrderNo(sSubdetail.getSSubdetailPK().getSOrderNo());
            sMst.setSMstStatus("O");

            if (sSubdetail.getSSubscription().getSOrderType().equals("S")) {
                try {
                    sMst.setSPriceperissue(new BigDecimal(jsonObject.getString("pricePerIssue")));
                } catch (Exception e) {
                    sMst.setSPriceperissue(null);
                }
            }
            sMstFacadeREST.edit(sMst);
            //sMst finished

            //4. Update of ABudget
            convRate = sMst.getSConvRate();
            apprCopies = new BigDecimal(sMst.getSApprCopy());
            totalPrice = sSubdetail.getSPrice().multiply(convRate).multiply(apprCopies);

            budget = sMst.getSReqNo().getSRBudgetCd();
            poraisedAmount = budget.getABPoraisedAmt();
            poraisedAmount = poraisedAmount.add(totalPrice);
            budget.setABPoraisedAmt(poraisedAmount);

            aBudgetFacadeREST.edit(budget);
            //Update of a_budget finisghed
        }
        return "Order Renewed";
    }
    
    //used for cancelling order
    //without js communication
    @POST
    @Path("orderCancellation")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain","application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String orderCancellation(@FormParam("recordIds") String recordIds, @FormParam("cancelCompleteOrder") String cancelCompleteOrder,
            @FormParam("remarks") String remarks, @FormParam("date") String date) {
       
        recIds = recordIds.split(",");
        String output = null;
        String orderNo;
        String recordId;
        String cancelledRecIds = "";
        String notcancelledRecIds = "";
        String invalidRecIds = "";
        BigDecimal price, convRate, apprCopies, totalPrice, poraisedAmount;
        ABudget budget;
        SSubdetailPK sSubdetailPK = new SSubdetailPK();
        for (String recId : recIds) {
            

            //Update of SMst : sMstStatus
            try{
                sMst = sMstFacadeREST.find(recId);
            }catch(NullPointerException e){
                invalidRecIds = invalidRecIds.concat(recId);
            }
            sMst.setSStatus("N");
            sMst.setSMstStatus("D");
            sMstFacadeREST.edit(sMst);

            //Update of SSubdetail: 1. sCancel = Y 2. sStatus = N
            sSubdetailPK.setSOrderNo(sMst.getSOrderNo());
            sSubdetailPK.setSRecid(recId);
            try{
                sSubdetail = sSubdetailFacadeREST.find(sSubdetailPK);
            }catch(NullPointerException e){
                invalidRecIds = invalidRecIds.concat(recId);
            }
            sSubdetail.setSCancel("Y");
            sSubdetail.setSStatus("N");
            sSubdetailFacadeREST.edit(sSubdetail);
            
            if (cancelCompleteOrder != null) {
                try {
                    sSubscription = find(sMst.getSOrderNo());
                } catch (NullPointerException e) {
                    return "A";
                }

                sSubscription.setSCancel("Y");
                sSubscription.setSCanremark(remarks);
                try {
                    sSubscription.setSCancelDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
                } catch (ParseException ex) {
                    sSubscription.setSCancelDt(new Date());
                    Logger.getLogger(SSubscriptionFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                output = "D";
                edit(sSubscription);
            }

            //Update of Budget's ABPoraised amount
            convRate = sMst.getSConvRate();
            apprCopies = new BigDecimal(sMst.getSApprCopy());
            totalPrice = sSubdetail.getSPrice().multiply(convRate).multiply(apprCopies);

            budget = sMst.getSReqNo().getSRBudgetCd();
            poraisedAmount = budget.getABPoraisedAmt();
            poraisedAmount = poraisedAmount.subtract(totalPrice);
            budget.setABPoraisedAmt(poraisedAmount);
            aBudgetFacadeREST.edit(budget);
            
            cancelledRecIds = cancelledRecIds.concat(recId);
        }
        return " Order Canceled.";
    }
    
    //Purchase Order
    //This methods return the details about orders for purchasing
    @GET
    @Path("getSupPubforInvoiceProcessing")
    @Produces({"application/xml", "application/json"})
    public List<SSubscription> getSupPubforInvoiceProcessing() throws ParseException {
        String q = "";
        List<SSubscription> result = new ArrayList<>();
        Query query;
        q = "select * from s_supplier_detail where a_s_cd in (select s_sup_cd\n" +
                " from s_subscription\n" +
                "  where s_order_no in (select s_order_no from s_mst where s_mst_status='O' or s_mst_status='S' and s_order_no != null) \n" +
                "  and s_order_no not in (select s_inv_order_no from s_sub_invdetail)) ";

        //List<Object> result;
        query = getEntityManager().createNativeQuery(q, SSubscription.class);
        System.out.println("query: "+query);
        result = (List<SSubscription>) query.getResultList();
        System.out.println("res: "+result);
        return result;
    }
    
    //used in browse orders in invoice processing for searching orders by publisher or supplier code
    @GET
    @Path("allOrderByPublisherSupplier/{name}")
    @Produces({"application/xml", "application/json"})
    public List<SSubscription> allOrderByPublisherSupplier(@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{vendorName.pattern}") @PathParam("name") String name) {
        String q = "";
        List<SSubscription> result = new ArrayList<>();
        Query query;
        
        //used in subquery to match in publisher or supplier code
        List<SSupplierDetail> getSuppliers = null;
        getSuppliers = sSupplierDetailFacadeREST.findBy("findByASName", name);

        StringJoiner suppliers = new StringJoiner("','");
        for (SSupplierDetail get : getSuppliers) {
            suppliers.add(get.getASCd());
        }

        System.out.println("getSuppliers: ('" + suppliers + "')");
        
        //used in subquery in matching orders
        List<SMst> getOrders = null;
        getOrders = sMstFacadeREST.findBy("ordersFOrInvoiceProcess", "null");

        StringJoiner orders = new StringJoiner("','");
        for (SMst get : getOrders) {
            orders.add(get.getSOrderNo());
        }

        System.out.println("getOrders: ('" + orders + "')");

        q = "select s_order_no from s_subscription  where s_sup_cd in ('" + suppliers + "') \n" +
                    "and s_order_no in ('" + orders + "') and s_order_type!='S'";
        
        query = getEntityManager().createNativeQuery(q, SSubscription.class);
        System.out.println("query: ('" + query + "')");
        result = (List<SSubscription>) query.getResultList();
        return result;
    }
    
    public int countByPubCode(String code) throws ParseException {
        List<SSubscription> getAll = null;
        getAll = findBy("findOrdersBySupplierCode", code);
        int size = getAll.size();
        return size;
    }
    
}
