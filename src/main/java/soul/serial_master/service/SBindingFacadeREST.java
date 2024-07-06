/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import soul.catalogue.Biblidetails;
import soul.catalogue.BiblidetailsPK;
import soul.catalogue.Location;
import soul.catalogue.LocationPK;
import soul.serialControl.SMst;
import soul.serialControl.SSchedule;
import soul.serialControl.SSchedulePK;
import soul.serial_master.SBinding;
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
import soul.serialControl.service.SRequestFacadeREST;
import soul.serialControl.service.SSubInvFacadeREST;
import soul.serial_master.SSupplierDetail;

/**
 *
 * @author admin
 */
@Stateless
@Path("soul.serial_master.sbinding")
public class SBindingFacadeREST extends AbstractFacade<SBinding> {

    String volume;
    String issue;
    @EJB
    private BHeadsFacadeREST bHeadsFacadeREST;
    @EJB
    private SSubInvFacadeREST sSubInvFacadeREST;
    @EJB
    private MFcltydeptFacadeREST mFcltydeptFacadeREST;
    @EJB
    private SRequestFacadeREST sRequestFacadeREST;
    @EJB
    private SSupplierDetailFacadeREST sSupplierDetailFacadeREST;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private ABudgetFacadeREST aBudgetFacadeREST;
    @EJB
    private AutogenerateFacadeREST autogenerateFacadeREST;
    @EJB
    private SBindingSetFacadeREST sBindingSetFacadeREST;
    @EJB
    private BiblidetailsFacadeREST biblidetailsFacadeREST;
    @EJB
    private SInvoiceFacadeREST sInvoiceFacadeREST;
    @EJB
    private SMstFacadeREST sMstFacadeREST;

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    private SBindingPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;sBSrNo=sBSrNoValue;sBNo=sBNoValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.serial_master.SBindingPK key = new soul.serial_master.SBindingPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> sBSrNo = map.get("sBSrNo");
        if (sBSrNo != null && !sBSrNo.isEmpty()) {
            key.setSBSrNo(new java.lang.Long(sBSrNo.get(0)));
        }
        java.util.List<String> sBNo = map.get("sBNo");
        if (sBNo != null && !sBNo.isEmpty()) {
            key.setSBNo(sBNo.get(0));
        }
        return key;
    }

    public SBindingFacadeREST() {
        super(SBinding.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(SBinding entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, SBinding entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.serial_master.SBindingPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public SBinding find(@PathParam("id") PathSegment id) {
        soul.serial_master.SBindingPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SBinding> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SBinding> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    public int countByPubCode(String code) throws ParseException {
        List<SBinding> getAll = null;
        getAll = findBy("findBySBBinderCd", code);
        int size = getAll.size();
        return size;
    }

    @GET
    @Path("getOrderNos")
    @Produces({"application/xml", "application/json"})
    public List<Object> getOrderNos() {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = " select distinct s_b_order_no from s_binding where (s_b_binder_cd!='INHOUSE' or s_b_binder_cd='') and  length(s_b_order_no)>1 AND s_b_rcpt_dt IS NULL OR s_b_rcpt_dt='' ";
        query = getEntityManager().createNativeQuery(q);
        result = query.getResultList();
        return result;
    }
    
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<SBinding> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        List<String> smstStatus = new ArrayList<>();

        switch (query) {
            case "findBySBBinderCd":
                valueList.add(valueString[0]);
                break;
                
            case "setDetailsBySetNo":
                valueList.add(valueString[0]);
                break;
            case "findBySBNo":
                valueList.add(valueString[0]);
                break;
            case "findByDate":                          break;
            case "getSetsListForReceivingInhouse":      break;
            case "ordersListForBindingPurchaseOrder":   break;
            case "getDetailsByOrderNo":
                valueList.add(valueString[0]);
                break;
            case "findAllOrdersForInvoiceProcessing":
                valueList.add(valueString[0]);
                valueList.add(valueString[1]);
                valueList.add(valueString[2]);
                break;
                case "findOrdersForReceiving":
                valueList.add(valueString[0]);
                break;
            case "findBySBOrderNo":
                valueList.add(valueString[0]);
                break;
            case "getBindingOrdersForReceiving":
                valueList.add(valueString[0]);
                break;
            case "retrieveAllforPreparationOfOrder":
                break;
        }
        return super.findBy("SBinding." + query, valueList);
    }

    @DELETE
    @Path("removeBy/{namedQuery}/{values}")
    public void removeBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch (query) {
            case "deleteSet":
                valueList.add(valueString[0]);
                valueList.add(Long.valueOf(valueString[1]));
                break;
            default:
                valueList.add(valueString[0]);
                break;
        }
        super.removeBy("SBinding." + query, valueList);
    }

    //service to update reminder counter
    @POST
    @Path("newOrUpdateOrderProcess")
    @Produces("text/plain")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String prepareBindingSet(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{orderNo.pattern}") @FormParam("orderNo") String orderNo, 
            @FormParam("setNos") String setNos,
            @FormParam("orderRemarks") String orderRemarks, 
            @Pattern(regexp = "^[A-Za-z]*$", message = "{string.pattern}") @FormParam("operation") String operation,
            @FormParam("setPrices") String setPrices, @FormParam("setAtPrices") String setAtPrices,
            @FormParam("orderDate") String orderDate, @FormParam("expectedDate") String expectedDate,
             @FormParam("binderCode") String binderCode, 
            @FormParam("budgetCode") String budgetCode) {

        String output = null;
        String setNos_Split[] = setNos.split(",");
        String setPrices_Split[] = setPrices.split(",");
        String setAtPrices_Split[] = setAtPrices.split(",");
        String orderRemarks_Split[] = orderRemarks.split(",");
        BigDecimal poraisedAmount = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<ABudget> aBudget = aBudgetFacadeREST.findBy("findByBudgetCode", budgetCode);

        if (operation.contentEquals("new")) {
            for (int i = 0; i < setNos_Split.length; i++) {
                SBinding sBinding = new SBinding();
                SBindingPK sBindingPK = new SBindingPK();

                sBindingPK.setSBNo(setNos_Split[i]);
                Long bSrNo = new Long("1");
                sBindingPK.setSBSrNo(bSrNo);
                sBinding.setSBindingPK(sBindingPK);
                //sBinding = find(sBindingPK);
                System.out.println("sBinding: " + sBinding);
                sBinding.setSBOrderNo(orderNo);
                sBinding.setSBBinderCd(binderCode);
                sBinding.setSBBudgetCd(budgetCode);

                BigDecimal setPr = new BigDecimal(setPrices_Split[i]);
                BigDecimal setAtPr = new BigDecimal(setAtPrices_Split[i]);
                sBinding.setSBPrice(setAtPr);
                sBinding.setSBSetPrice(setPr);
                try {
                    sBinding.setSBSendDt(format.parse(orderDate));
                } catch (ParseException ex) {
                    Logger.getLogger(SBindingFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    sBinding.setSBExpDt(format.parse(expectedDate));
                } catch (ParseException ex) {
                    Logger.getLogger(SBindingFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                sBinding.setSBOrderRemark(orderRemarks_Split[i]);
                totalPrice = totalPrice.add(setAtPr);
                edit(sBinding);
            }
            poraisedAmount = poraisedAmount.add(totalPrice);
            aBudget.get(0).setABPoraisedAmt(poraisedAmount);
            aBudgetFacadeREST.edit(aBudget.get(0));

            output = "Order no: " + orderNo + " is created";
        } else if (operation.contentEquals("update")) {
            for (int i = 0; i < setNos_Split.length; i++) {
                SBinding sBinding = new SBinding();
                SBindingPK sBindingPK = new SBindingPK();

                sBindingPK.setSBNo(setNos_Split[i]);
                Long bSrNo = new Long("1");
                sBindingPK.setSBSrNo(bSrNo);
                sBinding.setSBindingPK(sBindingPK);
                //sBinding = find(sBindingPK);
                System.out.println("sBinding: " + sBinding);
                sBinding.setSBOrderNo(orderNo);
                sBinding.setSBBinderCd(binderCode);
                sBinding.setSBBudgetCd(budgetCode);

                BigDecimal setPr = new BigDecimal(setPrices_Split[i]);
                BigDecimal setAtPr = new BigDecimal(setAtPrices_Split[i]);
                sBinding.setSBPrice(setAtPr);
                sBinding.setSBSetPrice(setPr);
                try {
                    sBinding.setSBSendDt(format.parse(orderDate));
                } catch (ParseException ex) {
                    Logger.getLogger(SBindingFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    sBinding.setSBExpDt(format.parse(expectedDate));
                } catch (ParseException ex) {
                    Logger.getLogger(SBindingFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                sBinding.setSBOrderRemark(orderRemarks_Split[i]);
                totalPrice = totalPrice.add(setAtPr);
                edit(sBinding);
            }
            poraisedAmount = poraisedAmount.add(totalPrice);
            aBudget.get(0).setABPoraisedAmt(poraisedAmount);
            aBudgetFacadeREST.edit(aBudget.get(0));
            output = "Order no: " + orderNo + " is updated";
        } else {
            output = "Invalid operation...";
        }
        return output;
    }

    @GET
    @Path("retrieveAllforPreparationOfOrder")
    @Produces({"application/xml", "application/json"})
    public List<SBinding> retrieveAllforPreparationOfOrder() {
        List<SBinding> getAll = null;
        getAll = findBy("retrieveAllforPreparationOfOrder", "null");
        return getAll;
    }
    
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<SBinding> retrieveAll() {
        List<SBinding> getAll = null;
        getAll = findAll();
        return getAll;
    }
    
    @GET
    @Path("getAllSets")
    @Produces({"application/xml", "application/json"})
    public List<String> getAllSets() {
        List<SBinding> getAll = null;
        getAll = findAll();
        List<String> allSets = new ArrayList<String>();
        for(int i = 0; i<getAll.size(); i++){
            allSets.add(getAll.get(i).getSBindingPK().getSBNo());
        }
        return allSets;
    }

    //service for receiving order
    @GET
    @Path("getBindingOrdersForReceiving")
    @Produces({"application/xml", "application/json"})
    public List<SBinding> getBindingOrdersForReceiving() {

        String q = "";
        List<SBinding> result = new ArrayList<>();
        List<SInvoice> getAll = null;
        getAll = sInvoiceFacadeREST.getAllOrders();
        System.out.println("getaLL" + getAll);

        StringJoiner joiner = new StringJoiner("','");
        for (SInvoice get : getAll) {
            joiner.add(get.getSInvOrderno());
        }

        System.out.println("'" + joiner + "'");

        String[] splitter = joiner.toString().split(",");
        List<SBinding> getAllS = null;
        for (int i = 0; i < splitter.length; i++) {
            getAllS = findBy("getBindingOrdersForReceiving", splitter[i]);
        }

        return getAllS;
    }

    //service for receiving order
    @GET
    @Path("getDetailsByOrderNo/{orderNo}")
    @Produces({"application/xml", "application/json"})
    public List<SBinding> getDetailsByOrderNo(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{orderNo.pattern}") @PathParam("orderNo") String orderNo) {

        List<SBinding> result = null;
        result = findBy("getDetailsByOrderNo", orderNo);

        return result;
    }
    
    @GET
    @Path("findOrdersForInvoice/{binderCode}")
    @Produces({"application/xml", "application/json"})
    public List<SBinding> findOrdersForInvoice(@Pattern(regexp = "^[a-zA-Z ]*$", message = "{vendorName.pattern}") @PathParam("binderCode") String binderCode) {

        List<SBinding> result = null;
        result = findBy("findOrdersForReceiving", binderCode);

        return result;
    }

    //delete set
    @DELETE
    @Path("deleteOrder/{orderNo}")
    @Produces("text/plain")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String deleteOrder(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{orderNo.pattern}") @PathParam("orderNo") String orderNo) {

        List<SBinding> sBinding = findBy("findBySBOrderNo", orderNo);
        BigDecimal totPrice = BigDecimal.ZERO;
        for (int i = 0; i < sBinding.size(); i++) {
            sBinding.get(i).setSBOrderNo(null);
            totPrice = totPrice.add(sBinding.get(i).getSBPrice());
        }

        List<ABudget> aBudget = aBudgetFacadeREST.findBy("findByBudgetCode", sBinding.get(0).getSBBudgetCd());
        System.out.println("aBudget: " + aBudget);
        BigDecimal abPoraised = aBudget.get(0).getABPoraisedAmt();
        BigDecimal newPoraised = abPoraised.subtract(totPrice);
        aBudget.get(0).setABPoraisedAmt(newPoraised);
        for (int i = 0; i < sBinding.size(); i++) {
            edit(sBinding.get(i));
        }
        aBudgetFacadeREST.edit(aBudget.get(0));
        return "Order: " + orderNo + " deleted...";
    }

    public String autoAssignAccessionNos(@FormParam("prefix") String prefix, @FormParam("suffix") String suffix) {
        String accNos;
        accNos = prefix + suffix;
        return accNos;
    }

    //preparition of binding set
    //service to update reminder counter
    @POST
    @Path("prepareOrUpdateBindingSet")
    @Produces("text/plain")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String prepareBindingSet() {

        String output = null;

        return output;
    }

    @DELETE
    @Path("deleteFromReceiving/{setNos}")
    @Produces("text/plain")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String deleteFromReceiving(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{setNo.pattern}") @PathParam("setNos") String setNos) {

        String[] sets = setNos.split(",");
        for(int i=0; i<sets.length; i++){
            List<SBinding> sBinding = findBy("findBySBNo", sets[i]);
            sBinding.get(0).setSBAccessNo(null);
            sBinding.get(0).setSBAccessDt(null);
            sBinding.get(0).setSBRcptDt(null);
            sBinding.get(0).setSBClassCd(null);
            sBinding.get(0).setSBStpg(null);
            sBinding.get(0).setSBLocation(null);
            edit(sBinding.get(0));
        }   
        return "";
    }


    @POST
    @Path("splitSetIntoParts")
    @Produces("text/plain")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String splitSetIntoParts(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{setNo.pattern}") @FormParam("setNo") String setNo, @FormParam("parts") Integer parts) {

        List<SBinding> sBinding = findBy("findBySBNo", setNo);
        long srNo = sBinding.get(0).getSBindingPK().getSBSrNo();
        SBindingPK sBindingPK = new SBindingPK();
        //----------------------------------------------------------------------
        String binderCode = sBinding.get(0).getSBBinderCd();
        String budgetCode = sBinding.get(0).getSBBudgetCd();
        BigDecimal setAtPrice = sBinding.get(0).getSBPrice();
        String orderNo = sBinding.get(0).getSBOrderNo();
        Date sendDate = sBinding.get(0).getSBSendDt();
        Date expectedDate = sBinding.get(0).getSBExpDt();
        String bindingType = sBinding.get(0).getSBBindtype();
        String remark = sBinding.get(0).getSBRemark();
        String emboseType = sBinding.get(0).getSBEmbosetype();
        String orderRemark = sBinding.get(0).getSBOrderRemark();
        String bindingColor = sBinding.get(0).getSBBindcol();
        String index = sBinding.get(0).getSBIndex();
        BigDecimal partedParts = new BigDecimal(parts);
        BigDecimal partedPrice = setAtPrice.divide(partedParts, 4, RoundingMode.HALF_UP);
        //----------------------------------------------------------------------

        for (int i = 1; i < parts; i++) {
            SBinding sB = new SBinding();
            SBindingPK sbpk = new SBindingPK();
            sbpk.setSBNo(setNo);
            sbpk.setSBSrNo(srNo + i);
            sB.setSBindingPK(sbpk);
            sB.setSBBinderCd(binderCode);
            sB.setSBBudgetCd(budgetCode);
            sB.setSBPrice(partedPrice);
            sB.setSBOrderNo(orderNo);
            sB.setSBSendDt(sendDate);
            sB.setSBExpDt(expectedDate);
            sB.setSBBindtype(bindingType);
            sB.setSBRemark(remark);
            sB.setSBEmbosetype(emboseType);
            sB.setSBOrderRemark(orderRemark);
            sB.setSBBindcol(bindingColor);
            sB.setSBIndex(index);
            create(sB);
        }
        Long bSrNo = new Long("1");
        sBindingPK.setSBNo(setNo);
        sBindingPK.setSBSrNo(bSrNo);
        sBinding.get(0).setSBindingPK(sBindingPK);
        sBinding.get(0).setSBPrice(partedPrice);
        edit(sBinding.get(0));

        return "Setno: " + setNo + " splitted into " + parts + " parts...";
    }

    @POST
    @Path("receivingOrAccessioning")
    @Produces("text/plain")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String receivingOrAccessioning(@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{accNo.pattern}") @FormParam("accNos") String accNos, 
            @FormParam("classCodes") String classCodes,
            @FormParam("location") String location, 
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("stpg") String stpg,
            @FormParam("accDate") String accDate,
            @Pattern(regexp = "^[A-Za-z0-9 ,-_]*$", message = "{setNo.pattern}") @FormParam("setNos") String setNos, @FormParam("operation") String operation) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String output = null;
        String cvalue = null;
        List<SBinding> sb = findAll();
        HashMap<String, ArrayList<String>> items = new HashMap<String, ArrayList<String>>();
        String setNo_Split[] = setNos.split(",");
        String accNo_Split[] = accNos.split(",");
        String classCode_Split[] = classCodes.split(",");
        String location_Split[] = location.split(",");
        String stpg_Split[] = stpg.split(",");
        String accDate_Split[] = accDate.split(",");
        String[][] val = new String[5][2];
        StringBuilder bib563 = new StringBuilder();
        Date date = new Date();
        List<SBinding> sBinding = new ArrayList<SBinding>();
        if (operation.contentEquals("new")) {
            for (int i = 0; i < setNo_Split.length; i++) {
                sBinding = findBy("findBySBNo", setNo_Split[i]);
                System.out.println("sBinding size:" +sBinding.size());
                long srNo = sBinding.get(0).getSBindingPK().getSBSrNo();
                SBindingPK sBindingPK = new SBindingPK();
            }
            for (int j = 0; j < sBinding.size(); j++) {
                    Long bSrNo = new Long("1");
                    sBinding.get(j).setSBAccessNo(accNo_Split[j]);
                    try {
                        sBinding.get(j).setSBAccessDt(format.parse(accDate_Split[j]));
                    } catch (ParseException ex) {
                        Logger.getLogger(SBindingFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    sBinding.get(j).setSBRcptDt(date);
                    sBinding.get(j).setSBClassCd(classCode_Split[j]);
                    sBinding.get(j).setSBLocation(location_Split[j]);
                    sBinding.get(j).setSBStpg(Integer.parseInt(stpg_Split[j]));
                    edit(sBinding.get(j));

                    List<SBindingSet> result = new ArrayList<>();
                    System.out.println("sBinding: " + sBinding);

                    List<SBindingSet> bindingSet = sBindingSetFacadeREST.findBy("findBySBSetno", setNo_Split[j]);
                    for (int l = 0; l < bindingSet.size(); l++) {
                        result.add(bindingSet.get(l));
                    }

                    ArrayList<String> al = new ArrayList<String>();
                    for (int s = 0; s < setNo_Split.length; s++) {
                        cvalue = setNo_Split[s];
                        for (int d = 0; d < result.size(); d++) {
                            if (result.get(j).getSBindingSetPK().getSBSetno().contentEquals(cvalue)) {
                                al.add(result.get(d).getSBindingSetPK().getSBVolno());
                                al.add(result.get(d).getSBindingSetPK().getSBIssueno());
                                System.out.println("al" + al);
                            }
                            items.put(cvalue, al);
                        }
                    }

                    for (Entry<String, ArrayList<String>> entry : items.entrySet()) {
                        System.out.println(entry.getKey() + " - " + entry.getValue());
                        bib563.append(entry.getKey());
                        for (int k = 0; k < entry.getValue().size(); k = k + 2) {
                            bib563.append("(Volume." + entry.getValue().get(k) + ",Issue." + entry.getValue().get(k + 1) + " )");
                        }
                        bib563.append(", Binded by: " + sBinding.get(j).getSBBinderCd());
                        bib563.append("," + sBinding.get(j).getSBBindtype());
                        bib563.append("," + sBinding.get(j).getSBEmbosetype());
                        bib563.append("," + sBinding.get(j).getSBBindcol() + "; ");
                    }
                    System.out.println("query:" + bib563);
                }

            List<String> catRecIds = new ArrayList<>();
            for (int i = 0; i < setNo_Split.length; i++) {
                List<SMst> sMst = sMstFacadeREST.getCatRecId(setNo_Split[i]);
                for (SMst sM : sMst) {
                    catRecIds.add(sM.getSCatRecid());
                    System.out.println(sM.getSCatRecid());
                }
            }

            for (int i = 0; i < setNo_Split.length; i++) {
                for (int p = 0; p < catRecIds.size(); p++) {
                    List<Biblidetails> biblidetails = biblidetailsFacadeREST.findBy("findByRecID", catRecIds.get(p));
                    Biblidetails bb = new Biblidetails();
                    BiblidetailsPK biblidetailsPk = new BiblidetailsPK();
                    System.out.println("bib: " + biblidetails);
                    int sbFldSrNo;
                    if (biblidetails.get(p).getBiblidetailsPK().getTag().contentEquals("563")) {
                        sbFldSrNo = biblidetails.get(p).getBiblidetailsPK().getSbFldSrNo() + 1;
                    } else {
                        sbFldSrNo = 1;
                    }

                    biblidetailsPk.setRecID(Integer.parseInt(catRecIds.get(p)));
                    biblidetailsPk.setSbFld("a");
                    biblidetailsPk.setSbFldSrNo(sbFldSrNo);
                    biblidetailsPk.setTag("563");
                    biblidetailsPk.setTagSrNo(1);
                    bb.setBiblidetailsPK(biblidetailsPk);
                    bb.setFValue(bib563.toString());
                    bb.setInd("");
                    try{
                        if(sb.get(i).getSBindingPK().getSBNo().contentEquals(setNo_Split[i]))
                            return "Record already exists";
                        else
                            biblidetailsFacadeREST.create(bb);
                    } catch (Exception d){
                        return "Record already exists";
                    }
                    
                }
                Location loc = new Location();
                LocationPK locpk = new LocationPK();
                
                List<SBindingSet> sBindingSet = sBindingSetFacadeREST.findBy("findBySBSetno", setNo_Split[i]);
                String recId = sBindingSet.get(0).getSBindingSetPK().getSBRecid();
                SMst sMst;
                try {
                    sMst = sMstFacadeREST.find(recId);
                } catch (NullPointerException d) {
                    return "Invalid record id.";
                }
                String catRecId = sMst.getSCatRecid();
                locpk.setP852(accNo_Split[i]);
                locpk.setRecID(Integer.parseInt(catRecId));
                loc.setLocationPK(locpk);
                //loc.setA852(srNo);
                //loc.setM852(srNo);
                //loc.setC852(srNo);
                //loc.setInd(srNo);
                loc.setB852(sMst.getSDeptCd()); //change to dynamic
                loc.setF852("000"); //change to dynamic
                try {
                    loc.setK852(sMst.getSClassCd());
                } catch (NullPointerException e) {
                    loc.setK852(null);
                }

                ABudget bud = aBudgetFacadeREST.find(Integer.parseInt(sMst.getSBugetCd()));
                System.out.println("abcdefg:   " + bud);
                System.out.println("abcdefg:   " + bud.getBudgetHead());

                BHeads bhead = bHeadsFacadeREST.find(bud.getBudgetHead().getBudgetCode());
                String invoiceNo = null;
                try {
                    invoiceNo = sMst.getSInvNo();
                    if (!invoiceNo.isEmpty()) {
                        List<SSubInv> invDet = sSubInvFacadeREST.findBy("findBySInvNo", invoiceNo);
                        Date invDate = invDet.get(0).getSInvDt();
                        loc.setInvoiceDate(invDate);
                    } else {
                        loc.setInvoiceDate(null);
                    }
                } catch (NullPointerException d) {
                    loc.setInvoiceDate(null);
                }

                MFcltydept deptDet = mFcltydeptFacadeREST.find(Integer.parseInt(sMst.getSDeptCd()));
                List<SRequest> sR = sRequestFacadeREST.findBy("findBySRTitle", sMst.getSTitle());
                Libmaterials mc = sR.get(0).getSRPhycd();
                List<SSupplierDetail> supplier = sSupplierDetailFacadeREST.findBy("findByASCd", sMst.getSSupCd());
                String supplierName = supplier.get(0).getASName();

                loc.setDepartment(deptDet);
                System.out.println("abcdefg:   " + bhead);
                System.out.println("abcdefg:   " + bhead.getShortDesc());
                loc.setBudget(bhead.getShortDesc());
                loc.setCurrency(sMst.getSCurrency());
                loc.setDateofAcq(date);

                //loc.setInvoiceNo(volume);
                loc.setIssueRestricted("N");
                loc.setLastOperatedDT(date);
                loc.setMaterial(mc);
                loc.setSupplier(supplierName);
                loc.setStatus("AV");
                locationFacadeREST.create(loc);
            }
            output = "Record updated...";
        } else if (operation.contentEquals("update")) {
            for (int i = 0; i < setNo_Split.length; i++) {
                sBinding = findBy("findBySBNo", setNo_Split[i]);
                long srNo = sBinding.get(0).getSBindingPK().getSBSrNo();
                SBindingPK sBindingPK = new SBindingPK();
                for (int j = 0; j < sBinding.size(); j++) {
                    Long bSrNo = new Long("1");
                    sBinding.get(j).setSBAccessNo(accNo_Split[i]);
                    try {
                        sBinding.get(j).setSBAccessDt(format.parse(accDate_Split[i]));
                    } catch (ParseException ex) {
                        Logger.getLogger(SBindingFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    sBinding.get(j).setSBRcptDt(date);
                    sBinding.get(j).setSBClassCd(classCode_Split[i]);
                    sBinding.get(j).setSBLocation(location_Split[i]);
                    sBinding.get(j).setSBStpg(Integer.parseInt(stpg_Split[i]));
                    edit(sBinding.get(j));
                }
                output = "Record Updated...";
            }
        } else {
            output = "Invalid operation";
        }
        return output;
    }

    @GET
    @Path("getLastAccNo")
    @Produces({"text/plain"})
    public String getLastAccNo() {
        //proceed further by getting maximum date from the list and take accession no of the max date
        List<SBinding> sBinding = findBy("findByDate","null");
        List<String> dates = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        for(int i = 0; i<sBinding.size(); i++){
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(format.parse(sBinding.get(i).getSBAccessDt().toString()));
                String formatedDate = cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DATE);
                dates.add(formatedDate);
            } catch (ParseException ex) {
                Logger.getLogger(SBindingFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String maxDate = Collections.max(dates);
        int maxIndex = 0;
        String lastAccNo = null;
        for(int i = 0; i<dates.size(); i++){
            if(maxDate.contentEquals(dates.get(i))){
                maxIndex = i;
            }
        }
        
        for(int i = 0; i<sBinding.size(); i++){
            if(i==maxIndex){
                lastAccNo = sBinding.get(i).getSBAccessNo();
            }
        }
        System.out.println("dates: "+dates);
        System.out.println("lastAccNo: "+lastAccNo);
        return lastAccNo;
    }
    
    
    
    @GET
    @Path("findAllOrdersForInvoiceProcessing/{binderCode}")
    @Produces({"application/xml", "application/json","text/plain"})
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
//<<<<<<< .mine
//    public List<Object> findAllOrdersForInvoiceProcessing( @PathParam("binderCode") String binderCode) {
//=======
    public List<SBinding> findAllOrdersForInvoiceProcessing( @PathParam("binderCode") String binderCode) {

        
        String q = "";
        List<SBinding> result = new ArrayList<>();
        Query query;

        List<SInvoice> getAll = null;
        getAll = sInvoiceFacadeREST.getAllI();
        System.out.println("getaLL" + getAll);

        StringJoiner joinerI = new StringJoiner("','");
        for (SInvoice get : getAll) {
            joinerI.add(get.getSInvOrderno());
        }
        
        List<SBinding> getAllS = null;
        getAllS = findOrdersForInvoice(binderCode);
        System.out.println("getaLL" + getAllS);

        StringJoiner joinerS = new StringJoiner("','");
        for (SBinding get : getAllS) {
            joinerS.add(get.getSBOrderNo());
        }

        System.out.println("'" + joinerI + "'");
        System.out.println("'" + joinerS + "'");
        

        q = "select * from s_binding where s_b_order_no!='' and s_b_binder_cd!='INHOUSE' \n" +
                    "and s_b_order_no not in ('" + joinerI + "') and s_b_binder_cd = '" + binderCode + "' \n" +
                    " and s_b_order_no not in ('" + joinerS + "') group by s_b_order_no";

        query = getEntityManager().createNativeQuery(q,SBinding.class);
        result = query.getResultList();
        
        return result;
    }
    
    
    //binding report starts here
    @GET
    @Path("bindingReportBySetNo/{setNo}")
    @Produces({"application/xml", "application/json"})
    public List<Object> bindingReportBySetNo(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{setNo.pattern}") @PathParam("setNo") String setNo) {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = "SELECT distinct s_binding.s_b_no, s_supplier.a_s_nm, b_heads.short_desc, \n" +
                "s_binding.s_b_price, s_binding.s_b_order_no, DATE_FORMAT(s_binding.s_b_send_dt, \"%Y-%m-%d %T\"), s_binding.s_b_remark \n" +
                "FROM s_binding, s_supplier, b_heads,a_budget \n" +
                "WHERE s_binding.s_b_binder_cd = s_supplier.a_s_cd AND s_binding.s_b_budget_cd = b_heads.budget_code\n" +
                "and b_heads.budget_code = a_budget.budget_head and s_b_no = '" + setNo + "' ";
        query = getEntityManager().createNativeQuery(q);
        result = query.getResultList();
        return result;
    }
    
//    @GET
//    @Path("listForReceivingInHouse")
//    @Produces({"application/xml", "application/json"})
//    public List<SBinding> listForReceivingInHouse(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{setNo.pattern}") @PathParam("setNo") String setNo) {
//        String q = "";
//        List<SBinding> result = new ArrayList<>();
//        Query query;
//
//        q = "select * from s_binding where (length(s_b_access_no)>=1 or s_b_access_no is not null) and s_b_binder_cd = 'INHOUSE' ";
//        query = getEntityManager().createNativeQuery(q, SBinding.class);
//        result = query.getResultList();
//        return result;
//    }
    
    //For commercial binding
    @GET
    @Path("bindingReportByBinderName/{binderName}")
    @Produces({"application/xml", "application/json"})
    public List<Object> bindingReportByBinderCode(@Pattern(regexp = "^[a-zA-Z ]*$", message = "{vendorName.pattern}") @PathParam("binderName") String binderName) {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = "SELECT distinct s_binding.s_b_no, s_supplier.a_s_nm, b_heads.short_desc, s_binding.s_b_price, s_binding.s_b_order_no, DATE_FORMAT(s_binding.s_b_send_dt, \"%Y-%m-%d %T\"), s_binding.s_b_remark \n" +
                "FROM s_binding, s_supplier, b_heads,a_budget \n" +
                "WHERE s_binding.s_b_binder_cd = s_supplier.a_s_cd AND s_binding.s_b_budget_cd = b_heads.budget_code\n" +
                "  and b_heads.budget_code = a_budget.budget_head and s_b_binder_cd in (select a_s_cd from s_supplier where a_s_nm = '" + binderName + "')";
        query = getEntityManager().createNativeQuery(q);
        result = query.getResultList();
        return result;
    }
    
    //If required change the query to dynamic by passing INHOUE binder code as parameter
    //For inhouse binding
    @GET
    @Path("bindingReportInHouseBindng")
    @Produces({"application/xml", "application/json"})
    public List<Object> bindingReportInHouseBindng() {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = "SELECT distinct s_binding.s_b_no, s_supplier.a_s_nm, b_heads.short_desc, s_binding.s_b_price, s_binding.s_b_order_no, DATE_FORMAT(s_binding.s_b_send_dt, \"%Y-%m-%d %T\"), s_binding.s_b_remark \n" +
                "FROM s_binding, s_supplier, b_heads,a_budget \n" +
                "WHERE s_binding.s_b_binder_cd = s_supplier.a_s_cd AND s_binding.s_b_budget_cd = b_heads.budget_code\n" +
                "  and b_heads.budget_code = a_budget.budget_head and s_b_binder_cd in (select a_s_cd from s_supplier where a_s_nm = 'INHOUSE')";
        query = getEntityManager().createNativeQuery(q);
        result = query.getResultList();
        return result;
    }
    
    @GET
    @Path("bindingReportByDateBetween/{date}")
    @Produces({"application/xml", "application/json"})
    public List<Object> bindingReportByDateBetween(@PathParam("date") String date) {
        String[] valueString = date.split(",");
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = "SELECT distinct s_binding.s_b_no, s_supplier.a_s_nm, b_heads.short_desc, \n" +
                "s_binding.s_b_price, s_binding.s_b_order_no, DATE_FORMAT(s_binding.s_b_send_dt, \"%Y-%m-%d %T\"), s_binding.s_b_remark \n" +
                "FROM s_binding, s_supplier, b_heads,a_budget \n" +
                "WHERE s_binding.s_b_binder_cd = s_supplier.a_s_cd AND s_binding.s_b_budget_cd = b_heads.budget_code\n" +
                "and b_heads.budget_code = a_budget.budget_head  and s_b_rcpt_dt is not null and (s_b_rcpt_dt > '" + valueString[0] + "' and s_b_rcpt_dt < '" + valueString[1] + "')";
        query = getEntityManager().createNativeQuery(q);
        result = query.getResultList();
        return result;
    }
    
    @GET
    @Path("bindingReportBySetNoAndTitle/{setNo}/{title}")
    @Produces({"application/xml", "application/json"})
    public List<Object> bindingReportBySetNoAndTitle(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{setNo.pattern}") @PathParam("setNo") String setNo, @PathParam("title") String title) {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = "SELECT distinct s_binding.s_b_no, s_supplier.a_s_nm, b_heads.short_desc, \n" +
                "s_binding.s_b_price, s_binding.s_b_order_no, DATE_FORMAT(s_binding.s_b_send_dt, \"%Y-%m-%d %T\"), s_binding.s_b_remark \n" +
                "FROM s_binding, s_supplier, b_heads,a_budget \n" +
                "WHERE s_binding.s_b_binder_cd = s_supplier.a_s_cd AND s_binding.s_b_budget_cd = b_heads.budget_code\n" +
                "and b_heads.budget_code = a_budget.budget_head and s_b_no = '" + setNo + "' \n" +
                "and s_b_no in (select s_b_setno from s_binding_set where s_b_recid in (select s_recid from s_mst where s_title like '" + title + "%'))";
        query = getEntityManager().createNativeQuery(q);
        result = query.getResultList();
        return result;
    }
    
    //for commercial binding
    @GET
    @Path("bindingReportByBinderNameAndTitle/{binderName}/{title}")
    @Produces({"application/xml", "application/json"})
    public List<Object> bindingReportByBinderNameAndTitle(@Pattern(regexp = "^[a-zA-Z ]*$", message = "{vendorName.pattern}") @PathParam("binderName") String binderName, 
                                    @PathParam("title") String title) {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = "SELECT distinct s_binding.s_b_no, s_supplier.a_s_nm, b_heads.short_desc, \n" +
                "s_binding.s_b_price, s_binding.s_b_order_no, DATE_FORMAT(s_binding.s_b_send_dt, \"%Y-%m-%d %T\"), s_binding.s_b_remark \n" +
                "FROM s_binding, s_supplier, b_heads,a_budget\n" +
                "WHERE s_binding.s_b_binder_cd = s_supplier.a_s_cd AND s_binding.s_b_budget_cd = b_heads.budget_code\n" +
                "and b_heads.budget_code = a_budget.budget_head\n" +
                "and s_b_binder_cd in (select a_s_cd from s_supplier where a_s_nm = '" + binderName + "' ) \n" +
                "and s_b_no in (select s_b_setno from s_binding_set where s_b_recid in (select s_recid from s_mst where s_title like '" + title + "%'))";
        query = getEntityManager().createNativeQuery(q);
        result = query.getResultList();
        return result;
    }
     
    //If required change the query to dynamic by passing INHOUSE binder code as parameter
    //for inhouse binding
    @GET
    @Path("bindingReportInHouseByTitle/{title}")
    @Produces({"application/xml", "application/json"})
    public List<Object> bindingReportByBinderNameAndTitle(@PathParam("title") String title) {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = "SELECT distinct s_binding.s_b_no, s_supplier.a_s_nm, b_heads.short_desc, \n" +
                "s_binding.s_b_price, s_binding.s_b_order_no, DATE_FORMAT(s_binding.s_b_send_dt, \"%Y-%m-%d %T\"), s_binding.s_b_remark \n" +
                "FROM s_binding, s_supplier, b_heads,a_budget\n" +
                "WHERE s_binding.s_b_binder_cd = s_supplier.a_s_cd AND s_binding.s_b_budget_cd = b_heads.budget_code\n" +
                "and b_heads.budget_code = a_budget.budget_head\n" +
                "and s_b_binder_cd in (select a_s_cd from s_supplier where a_s_nm = 'INHOUSE' ) \n" +
                "and s_b_no in (select s_b_setno from s_binding_set where s_b_recid in (select s_recid from s_mst where s_title like '" + title + "%'))";
        query = getEntityManager().createNativeQuery(q);
        result = query.getResultList();
        return result;
    }
    
    @GET
    @Path("bindingReportByTitleAndDateBetween/{title}/{date}")
    @Produces({"application/xml", "application/json"})
    public List<Object> bindingReportByDateBetween(@PathParam("date") String date,@PathParam("title") String title) {
        String[] valueString = date.split(",");
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = "SELECT distinct s_binding.s_b_no, s_supplier.a_s_nm, b_heads.short_desc, \n" +
                "s_binding.s_b_price, s_binding.s_b_order_no,  DATE_FORMAT(s_binding.s_b_send_dt, \"%Y-%m-%d %T\"), s_binding.s_b_remark \n" +
                "FROM s_binding, s_supplier, b_heads,a_budget \n" +
                "WHERE s_binding.s_b_binder_cd = s_supplier.a_s_cd AND s_binding.s_b_budget_cd = b_heads.budget_code\n" +
                "and b_heads.budget_code = a_budget.budget_head  and s_b_rcpt_dt is not null and (s_b_rcpt_dt > '" + valueString[0] + "' and s_b_rcpt_dt < '" + valueString[1] + "') \n" +
                "and s_b_no in (select s_b_setno from s_binding_set where s_b_recid in (select s_recid from s_mst where s_title like '" + title + "%'))";
        query = getEntityManager().createNativeQuery(q);
        result = query.getResultList();
        return result;
    }
    //binding report ends here
    
    
    //Reminder to binder reports starts here
    @GET
    @Path("binderListForReminder")
    @Produces({"application/xml", "application/json"})
    public List<SSupplier> binderListForReminder() {
        String q = "";
        List<SSupplier> result = new ArrayList<>();
        Query query;

        q = "select a_s_nm,a_s_cd from s_supplier where a_s_cd in( select distinct(s_b_binder_cd) "
                + "from s_binding where s_b_order_no!='' and s_b_binder_cd!='INHOUSE' and (s_b_access_no is null or s_b_access_no='') );";
        
        query = getEntityManager().createNativeQuery(q, SSupplier.class);
        result = query.getResultList();
        return result;
    }
    
    @GET
    @Path("reminderToBinder/{binderName}")
    @Produces({"application/xml", "application/json"})
    public List<Object> reminderToBinder(@Pattern(regexp = "^[a-zA-Z ]*$", message = "{vendorName.pattern}") @PathParam("binderName") String binderName) {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = "SELECT s_supplier.a_s_nm, s_supplier_detail.a_s_add1, s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3, s_supplier_detail.a_s_city, \n" +
                "s_supplier_detail.a_s_pin,  s_supplier_detail.a_s_country, s_supplier_detail.a_s_state, s_supplier_detail.a_s_remark, s_supplier_detail.a_s_email, \n" +
                "s_supplier_detail.a_s_phone,  s_supplier_detail.a_s_fax, s_binding.s_b_price, s_binding.s_b_order_no, s_binding.s_b_send_dt, s_binding.s_b_exp_dt, \n" +
                "s_binding.s_b_bindtype,  s_binding.s_b_embosetype, s_binding.s_b_bindcol, s_binding.s_b_no as s_b_index, ReportImage.ImageData, letter_formats.letter_format, AutoGenerate.Prefix \n" +
                "FROM AutoGenerate, ReportImage, s_supplier_detail, s_binding, s_supplier, letter_formats  \n" +
                "WHERE(s_supplier_detail.a_s_cd = s_binding.s_b_binder_cd And s_supplier_detail.a_s_cd = s_supplier.a_s_cd)  \n" +
                "and (s_b_order_no!='' and s_b_binder_cd!='INHOUSE' and (s_b_access_no is null or s_b_access_no='' ))  \n" +
                "and letter_name='remin_scm' and name='Serial Reminder No' and ReportImage.ID=1 and s_supplier.a_s_nm = '" + binderName + "' and s_b_exp_dt < current_Date() LIMIT 1";
        
        query = getEntityManager().createNativeQuery(q);
        System.out.println("query: "+query);
        result = query.getResultList();
        ArrayList<String> list = new ArrayList<String>();
        list.add("Serial Reminder");
        for(int i=0; i<list.size(); i++){
            autogenerateFacadeREST.updateAutogenerate(list.get(i));
        }
        return result;
    }
    
    @GET
    @Path("reminderToBinder/{binderName}/{expectedUpTo}")
    @Produces({"application/xml", "application/json"})
    public List<Object> reminderToBinder(@Pattern(regexp = "^[a-zA-Z ]*$", message = "{vendorName.pattern}") @PathParam("binderName") String binderName, 
            @PathParam("expectedUpTo") String expectedUpTo) {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = "SELECT s_supplier.a_s_nm, s_supplier_detail.a_s_add1, s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3, s_supplier_detail.a_s_city, \n" +
                "s_supplier_detail.a_s_pin,  s_supplier_detail.a_s_country, s_supplier_detail.a_s_state, s_supplier_detail.a_s_remark, s_supplier_detail.a_s_email, \n" +
                "s_supplier_detail.a_s_phone,  s_supplier_detail.a_s_fax, s_binding.s_b_price, s_binding.s_b_order_no, s_binding.s_b_send_dt, s_binding.s_b_exp_dt, \n" +
                "s_binding.s_b_bindtype,  s_binding.s_b_embosetype, s_binding.s_b_bindcol, s_binding.s_b_no as s_b_index, ReportImage.ImageData, letter_formats.letter_format, AutoGenerate.Prefix \n" +
                "FROM AutoGenerate, ReportImage, s_supplier_detail, s_binding, s_supplier, letter_formats  \n" +
                "WHERE(s_supplier_detail.a_s_cd = s_binding.s_b_binder_cd And s_supplier_detail.a_s_cd = s_supplier.a_s_cd)  \n" +
                "and (s_b_order_no!='' and s_b_binder_cd!='INHOUSE' and (s_b_access_no is null or s_b_access_no='' ))  \n" +
                "and letter_name='remin_scm' and name='Serial Reminder No' and ReportImage.ID=1 and s_supplier.a_s_nm = '" + binderName + "' and s_b_exp_dt < '" + expectedUpTo + "' LIMIT 1";
        
        query = getEntityManager().createNativeQuery(q);
        result = query.getResultList();
        ArrayList<String> list = new ArrayList<String>();
        list.add("Serial Reminder");
        for(int i=0; i<list.size(); i++){
            autogenerateFacadeREST.updateAutogenerate(list.get(i));
        }
        return result;
    }
    
    //Reminder to binder reports ends here
    
    //Order binding reports starts here
    @GET
    @Path("ordersListForBindingPurchaseOrder")
    @Produces({"application/xml", "application/json"})
    public List<SBinding> ordersListForindingPurchaseOrder() {
        List<SBinding> getAll = null;
        getAll = findBy("ordersListForBindingPurchaseOrder","null");
        return getAll;
    }
    
  
    
    @GET
    @Path("orderBinding/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> forwardToAccount(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {

            case "binderCOde":
                q = "SELECT s_supplier.a_s_nm, s_supplier_detail.a_s_add1, s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3, \n" +
                        "s_supplier_detail.a_s_city,  s_supplier_detail.a_s_pin, s_supplier_detail.a_s_country, s_supplier_detail.a_s_state, \n" +
                        "s_supplier_detail.a_s_email, s_supplier_detail.a_s_phone,  s_supplier_detail.a_s_fax, AutoGenerate.Prefix, \n" +
                        "ReportImage.ImageData,  s_binding.s_b_no, s_binding.s_b_budget_cd, s_binding.s_b_price, s_binding.s_b_order_no, \n" +
                        "s_binding.s_b_send_dt, s_binding.s_b_location,   s_binding.s_b_stpg, s_binding.s_b_exp_dt, s_binding.s_b_rcpt_dt, \n" +
                        "s_binding.s_b_bindtype, s_binding.s_b_access_no, s_binding.s_b_remark,   s_binding.s_b_embosetype, s_binding.s_b_enpg, \n" +
                        "s_binding.s_b_access_dt, s_binding.s_b_bindcol, s_binding.s_b_index, letter_formats.subject, letter_formats.letter_format,\n" +
                        "s_binding.s_b_order_remark as s_b_remark  \n" +
                        "FROM AutoGenerate, ReportImage, s_binding, s_supplier_detail, s_supplier, letter_formats  \n" +
                        "WHERE s_binding.s_b_binder_cd = s_supplier.a_s_cd AND s_supplier_detail.a_s_cd = s_supplier.a_s_cd  and letter_name='pur_order' \n" +
                        "and ReportImage.ID=1 and AutoGenerate.Name='Serial PrintOrder No' and s_b_order_no!='' \n" +
                        "and s_b_binder_cd = '" + valueString[0] + "' and (s_b_access_no is null or s_b_access_no='')  LIMIT 1 ";
                break;

            case "orderNo":
                q = "SELECT s_supplier.a_s_nm, s_supplier_detail.a_s_add1, s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3, \n" 
                       + "s_supplier_detail.a_s_city,  s_supplier_detail.a_s_pin, s_supplier_detail.a_s_country, s_supplier_detail.a_s_state, \n" 
                       + "s_supplier_detail.a_s_email, s_supplier_detail.a_s_phone,  s_supplier_detail.a_s_fax, AutoGenerate.Prefix, \n" 
                       + "ReportImage.ImageData,  s_binding.s_b_no, s_binding.s_b_budget_cd, s_binding.s_b_price, s_binding.s_b_order_no, \n" 
                       + "s_binding.s_b_send_dt, s_binding.s_b_location,   s_binding.s_b_stpg, s_binding.s_b_exp_dt, s_binding.s_b_rcpt_dt, \n" 
                       + "s_binding.s_b_bindtype, s_binding.s_b_access_no, s_binding.s_b_remark,   s_binding.s_b_embosetype, s_binding.s_b_enpg, \n" 
                       + "s_binding.s_b_access_dt, s_binding.s_b_bindcol, s_binding.s_b_index, letter_formats.subject, letter_formats.letter_format,\n" 
                       + "s_binding.s_b_order_remark as s_b_remark  \n" 
                       + "FROM AutoGenerate, ReportImage, s_binding, s_supplier_detail, s_supplier, letter_formats  \n" 
                       + "WHERE s_binding.s_b_binder_cd = s_supplier.a_s_cd AND s_supplier_detail.a_s_cd = s_supplier.a_s_cd  and letter_name='pur_order' \n" 
                       + "and ReportImage.ID=1 and AutoGenerate.Name='Serial PrintOrder No' and s_b_order_no!='' and s_b_binder_cd!='INHOUSE' \n" 
                       + "and (s_b_access_no is null or s_b_access_no='')  and s_binding.s_b_order_no = '" + valueString[0] + "' LIMIT 1";
                break;

            case "orderDateBetween":
                q = " SELECT s_supplier.a_s_nm, s_supplier_detail.a_s_add1, s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3, \n"                                           
                       + "s_supplier_detail.a_s_city,  s_supplier_detail.a_s_pin, s_supplier_detail.a_s_country, s_supplier_detail.a_s_state, \n" 
                       + "s_supplier_detail.a_s_email, s_supplier_detail.a_s_phone,  s_supplier_detail.a_s_fax, AutoGenerate.Prefix, \n" 
                       + "ReportImage.ImageData,  s_binding.s_b_no, s_binding.s_b_budget_cd, s_binding.s_b_price, s_binding.s_b_order_no, \n" 
                       + "s_binding.s_b_send_dt, s_binding.s_b_location,   s_binding.s_b_stpg, s_binding.s_b_exp_dt, s_binding.s_b_rcpt_dt, \n" 
                       + "s_binding.s_b_bindtype, s_binding.s_b_access_no, s_binding.s_b_remark,   s_binding.s_b_embosetype, s_binding.s_b_enpg, \n" 
                       + "s_binding.s_b_access_dt, s_binding.s_b_bindcol, s_binding.s_b_index, letter_formats.subject, letter_formats.letter_format\n" 
                       + ",s_binding.s_b_order_remark as s_b_remark  \n" 
                       + "FROM AutoGenerate, ReportImage, s_binding, s_supplier_detail, s_supplier, letter_formats  \n" 
                       + "WHERE s_binding.s_b_binder_cd = s_supplier.a_s_cd AND s_supplier_detail.a_s_cd = s_supplier.a_s_cd  and \n" 
                       + "letter_name='pur_order' and ReportImage.ID=1 and AutoGenerate.Name='Serial PrintOrder No' and s_b_order_no!='' and \n" 
                       + "s_b_binder_cd!='INHOUSE' and (s_b_access_no is null or s_b_access_no='')  and  (s_b_send_dt > '" + valueString[0] + "' or s_b_send_dt < '" + valueString[1] + "') LIMIT 1";
                break;
        }
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        ArrayList<String> list = new ArrayList<String>();

        list.add("Serial PrintOrder No");
        String letterNo = null;
        letterNo = autogenerateFacadeREST.generateLetterNo("Serial PrintOrder No");
        for(int i=0; i<list.size(); i++){
            autogenerateFacadeREST.updateAutogenerate(list.get(i));
        }
        System.out.println("letterNo: "+letterNo);
        return result;
    }
        
    //Order binding reports ends here
    
    //Obound volume labels starts here
    @GET
    @Path("orderListForBoundingVolumes")
    @Produces({"application/xml", "application/json"})
    public List<Object> orderListForBoundingVolumes() {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = "select distinct(s_b_order_no),s_b_rcpt_dt,s_b_send_dt from s_binding where s_b_order_no!='' and s_b_binder_cd!='INHOUSE' \n" +
                "and s_b_order_no not in (select distinct s_inv_orderno from s_invoice where s_inv_orderno is not null)  \n" +
                "and s_b_order_no not in (select s_b_order_no from s_binding where s_b_rcpt_dt is null)";
        
        query = getEntityManager().createNativeQuery(q);
        result = query.getResultList();
        return result;
    }
    
    @GET
    @Path("bindersListForBoundingVolumes")
    @Produces({"application/xml", "application/json"})
    public List<Object> bindersListForBoundingVolumes() {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = "select a_s_cd,a_s_nm from s_supplier where a_s_cd in (select distinct s_b_binder_cd from s_binding where s_b_order_no!='' \n" +
                "and s_b_binder_cd!='INHOUSE' and s_b_order_no not in (select distinct s_inv_orderno from s_invoice where s_inv_orderno is not null)  \n" +
                "and s_b_order_no not in (select s_b_order_no from s_binding where s_b_rcpt_dt is null))";
        
        query = getEntityManager().createNativeQuery(q);
        result = query.getResultList();
        return result;
    }
    
    @GET
    @Path("boundVolumes/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> boundVolumes(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {

            case "binderCOde":
                q = "SELECT distinct s_b_no as set_no,s_s_recid as s_b_no,s_b_price,s_binding_set.s_b_volno, s_binding_set.s_b_issueno, \n" +
                        "s_schedule.s_s_rcpt_dt,  s_binding.s_b_price, s_binding.s_b_access_no, s_binding.s_b_class_cd,\n" +
                        "EXTRACT(MONTH FROM s_schedule.s_s_rcpt_dt) as smonth, EXTRACT(YEAR FROM s_schedule.s_s_rcpt_dt) as syear,s_b_set_price \n" +
                        "FROM s_binding, s_binding_set, s_schedule  \n" +
                        "WHERE s_binding.s_b_no = s_binding_set.s_b_setno AND s_binding_set.s_b_recid = s_schedule.s_s_recid \n" +
                        "AND  s_binding_set.s_b_setno = s_schedule.s_s_bind_no  and \n" +
                        "s_binding.s_b_binder_cd in(select a_s_cd from s_supplier where a_s_cd = '" + valueString[0] + "') ";
                break;

            case "orderNo":
                q = "SELECT distinct s_b_no as set_no,s_s_recid as s_b_no,s_b_price,s_binding_set.s_b_volno, s_binding_set.s_b_issueno, \n" +
                        "s_schedule.s_s_rcpt_dt,  s_binding.s_b_price, s_binding.s_b_access_no, s_binding.s_b_class_cd,\n" +
                        "EXTRACT(MONTH FROM s_schedule.s_s_rcpt_dt) as smonth, EXTRACT(YEAR FROM s_schedule.s_s_rcpt_dt) as syear,s_b_set_price \n" +
                        "FROM s_binding, s_binding_set, s_schedule  \n" +
                        "WHERE s_binding.s_b_no = s_binding_set.s_b_setno AND \n" +
                        "s_binding_set.s_b_recid = s_schedule.s_s_recid AND  s_binding_set.s_b_setno = s_schedule.s_s_bind_no  \n" +
                        "and s_binding.s_b_order_no = '" + valueString[0] + "' ";
                break;

                case "orderDateBetween":
                q = "SELECT distinct s_b_no as set_no,s_s_recid as s_b_no,s_b_price,s_binding_set.s_b_volno, s_binding_set.s_b_issueno, \n" +
                        "s_schedule.s_s_rcpt_dt,  s_binding.s_b_price, s_binding.s_b_access_no, s_binding.s_b_class_cd,\n" +
                        "EXTRACT(MONTH FROM s_schedule.s_s_rcpt_dt) as smonth, EXTRACT(YEAR FROM s_schedule.s_s_rcpt_dt) as syear,s_b_set_price \n" +
                        "FROM s_binding, s_binding_set, s_schedule  \n" +
                        "WHERE s_binding.s_b_no = s_binding_set.s_b_setno AND \n" +
                        "s_binding_set.s_b_recid = s_schedule.s_s_recid AND  s_binding_set.s_b_setno = s_schedule.s_s_bind_no  \n" +
                        "and  (s_b_send_dt > '" + valueString[0] + "' and s_b_send_dt < '" + valueString[1] + "')";
                break;
        }
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
    //Order binding reports ends here
    
    @GET
    @Path("getSetsListForReceivingInhouse")
    @Produces({"application/xml", "application/json"})
    public List<SBinding> getSetsListForReceivingInhouse() {

        List<SBinding> result = null;
        result = findBy("getSetsListForReceivingInhouse", "null");

        return result;
    }
    
    // used in receiving in INHOUSE BINDING
    @GET
    @Path("setDetailsBySetNo/{setNo}")
    @Produces({"application/xml", "application/json"})
    public List<SBinding> setDetailsBySetNo(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{setNo.pattern}") @PathParam("setNo") String setNo) {

        List<SBinding> result = null;
        result = findBy("setDetailsBySetNo", setNo);

        return result;
    }
    
    @DELETE
    @Path("deleteFromReceivingInHouse/{setNo}")
    @Produces("text/plain")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String deleteFromReceivingInHouse(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{setNo.pattern}") @PathParam("setNo") String setNo) {

        List<SBinding> sBinding = findBy("findBySBNo", setNo);
        sBinding.get(0).setSBAccessNo(null);
        sBinding.get(0).setSBClassCd(null);
        sBinding.get(0).setSBLocation(null);
        edit(sBinding.get(0));
        return "Setno: " + setNo + " updated...";
    }
    
    @GET
    @Path("orderRetrieveForReceiving")
    @Produces({"application/xml", "application/json"})
    public List<SBinding> orderRetrieveForReceiving() {
        String q = "";
        List<SBinding> result = new ArrayList<>();
        Query query;

        q = "Select s_b_sr_no, s_b_no ,s_b_access_no,s_b_access_dt,s_b_class_cd,s_b_location,s_b_stpg from s_binding where s_b_access_no !='' and s_b_binder_cd!='INHOUSE' and s_b_order_no \n" +
                "not in (select distinct s_inv_orderno from s_invoice,s_binding where s_inv_orderno=s_b_order_no) group by s_b_order_no";
        
        query = getEntityManager().createNativeQuery(q,SBinding.class);
        result = query.getResultList();
        return result;
    }
    
       @GET
    @Path("listForReceivingInHouse")
    @Produces({"application/xml", "application/json"})
    public List<SBinding> listForReceivingInHouse(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{setNo.pattern}") @PathParam("setNo") String setNo) {
        String q = "";
        List<SBinding> result = new ArrayList<>();
        Query query;

        q = "select * from s_binding where (length(s_b_access_no)>=1 or s_b_access_no is not null) and s_b_binder_cd = 'INHOUSE' ";
        query = getEntityManager().createNativeQuery(q, SBinding.class);
        result = query.getResultList();
        return result;
    }
}
