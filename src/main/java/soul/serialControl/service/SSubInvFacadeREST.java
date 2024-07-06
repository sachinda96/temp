/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl.service;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
//import org.json.JSONObject;
//import org.json.XML;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
//import org.json.JSONObject;
//import org.json.XML;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.Json;
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
//import org.json.JSONException;
import soul.serialControl.SMst;
import soul.serialControl.SPayment;
import soul.serialControl.SSubInv;
import soul.serialControl.SSubInvdetail;
import soul.serialControl.SSubscription;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.serialcontrol.ssubinv")
public class SSubInvFacadeREST extends AbstractFacade<SSubInv> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    private String[] invNos;
    private Object it;
    public SSubInvFacadeREST() {
        super(SSubInv.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(SSubInv entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(SSubInv entity) {
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
    public SSubInv find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SSubInv> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SSubInv> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    //Added Manually
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<SSubInv> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
            case "findBySInvStatusANDPositiveRemain":   break;
            
            case "getRefundReport":   
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                                            try {
                                                valueList.add(dateFormat1.parse(valueString[0]));
                                                valueList.add(dateFormat1.parse(valueString[1]));
                                                        
                                            } catch (ParseException ex) {
                                                Logger.getLogger(SSubInvFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            
                                    break;
                     
            case "findBySInvNo":    valueList.add(valueString[0]);
                        break;
            default:    valueList.add(valueString[0]);
                        break;
                        //SerialInvoiceProcess, findBySInvStatusANDPositiveRemain by SerialPaymentProcess
                
        }
        return super.findBy("SSubInv."+query, valueList);
    }

    @GET
    @Path("checkInvoiceNo/{invoiceNo}")
    @Produces("text/plain")
  //  public String checkInvoiceNo(@QueryParam("invoiceNo") String invoiceNo)
    public String checkInvoiceNo(@PathParam("invoiceNo") String invoiceNo)        
    {
        List<SSubInv> invList = findBy("findBySInvNo", invoiceNo);
        if(invList.size() > 0)
            return "Used";
        else
            return "NotUsed";
    }
    
    @GET
    @Path("retrieveAll/{accept}")
    @Produces({"application/xml", "application/json"})
   // public List<SSubInv> retrieveAll(@QueryParam("accept") String accept)
    public List<SSubInv> retrieveAll(@PathParam("accept") String accept)        
    {
        List<SSubInv> getAll = null;
        if (accept.equals("XML")) 
        {
           getAll = findBy("findBySInvStatusANDPositiveRemain", "I");
        }
        return getAll;
//                                    else if(accept.equals("ObjectList")) 
//                                    {
//                                        GenericType<List<SSubInv>> genericType = new GenericType<List<SSubInv>>(){};
//                                        Response restResponse = invClient.findBy(Response.class, "findBySInvStatusANDPositiveRemain", "I");
//                                        List<SSubInv> invList = restResponse.readEntity(genericType);
//                                        request.setAttribute("invList", invList);
//                                    }
    }
    
    
    //for invoice report dropdown
    @GET
    @Path("retrieveAllPaymentStatus")
    @Produces({"application/xml", "application/json"})
    public List<SSubInv> retrieveAll()
    {
        List<SSubInv> getAll = null;
        getAll = findAll();
        return getAll;
    }
    
    //data retrival for payment process
    
    @GET
    @Path("retrieveAllforPayment")
    @Produces({"application/xml", "application/json"})
    public List<SSubInv> retrieveAllforPayment()
    {
        List<SSubInv> getAll = null;
        getAll = findBy("findBySInvStatusANDPositiveRemain", "null");
        return getAll;
    }
    
    @GET
    @Path("findBySupplierPublisherForPayment/{name}")
    @Produces({"application/xml", "application/json"})
    public List<SSubInv> findBySupplierPublisher(@Pattern(regexp = "^[A-Za-z0-9 &-_]*$", message = "{vendorName.pattern}") @PathParam("name") String name)
    {
        List<SSubInv> getAll = null;
        getAll = findBy("findBySupplierPublisher", name);
        return getAll;
    }
    
    
    //Refund process
    //This methods return the details about orders for which payment has done and to be processed for refund
    @GET
    @Path("getOrdersforRefund")
    @Produces({"application/xml", "application/json"})
    public List<Object> getOrdersforRefund() throws ParseException {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;
        q = "select distinct s_order_no from s_subdetail,s_sub_inv,s_sub_invdetail, s_schedule where s_schedule.s_s_recid = s_subdetail.s_recid AND s_s_status='Non-Received' \n" +
                " and  s_subdetail.s_order_no = s_sub_invdetail.s_inv_order_no and s_sub_inv.s_inv_no = s_sub_invdetail.s_inv_no\n" +
                " and s_sub_inv.s_inv_remain = 0.00 and s_sub_inv.s_inv_ref_status is null";

        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
    
    @GET
    @Path("refundReport/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<SSubInv> refundReport(@PathParam("Paramvalue") String Paramvalue)
    {
        String[] valueString = Paramvalue.split(",");
        List<SSubInv> getAll = null;
        getAll = findBy("getRefundReport", valueString[0]+","+valueString[1]);
        return getAll;
    }
    
    
    @PUT
    @Path("refundProcess")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain", "application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String refundProcess(@FormParam("invoiceNos") String invoiceNos,
            @Pattern(regexp = "^[a-zA-Z ]*$", message = "{string.pattern}") @FormParam("paymentBy") String paymentBy, 
            @Pattern(regexp = "^[a-zA-Z ]*$", message = "{string.pattern}") @FormParam("bankName") String bankName,
            @Pattern(regexp = "^[a-zA-Z ]*$", message = "{string.pattern}") @FormParam("branchName") String branchName, 
            @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "{string.pattern}") @FormParam("chequeDdNo") String payModeNo,
            @FormParam("refundDate") String refundNoteDate, @FormParam("totalAmount") BigDecimal totalAmount) {
        
        invNos = invoiceNos.split(",");
        String output = " : refunded";
        String refunded = "";
        for (String invNo : invNos) {
            SSubInv s = find(Integer.parseInt(invNo));
            
            s.setSInvRefAmt(totalAmount);
            s.setSInvRefBank(bankName);
            s.setSInvRefBranch(branchName);
            
            s.setSInvRefChequeno(payModeNo);
          
            
            try {
                s.setSInvRefNoteDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(refundNoteDate));
            } catch (ParseException ex) {
                Logger.getLogger(SPaymentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            s.setSInvRefStatus("Y");
            if (!paymentBy.equals("cash")) {  //if payment metod is other than cash
                s.setSInvRefBank(bankName);
                s.setSInvRefBranch(branchName);
                s.setSInvRefChequeno(payModeNo);
            }
            
            super.edit(s);
            refunded = String.join(",", invNo);
        }
        return "Refund done.";
    }
    
    
    // Invoice Report
    //This methods return the details about invoices for report generation    
    @GET
    @Path("invoiceReport/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public HashMap<String, Object>  invoiceReport(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {

            case "budgetWise":
                q = "SELECT s_inv_net_amt ,s_sub_inv.s_inv_dt as s_pay_dt,s_mst.s_title, s_mst.s_inv_no, s_mst.s_order_no, s_mst.s_buget_cd, s_mst.s_pub_cd, a_currency.a_c_nm,\n"
                        + " s_mst.s_sup_cd,s_supplier_detail.a_s_name AS Publiser, s_supplier_1.a_s_name AS Supplier \n"
                        + " FROM s_mst, s_sub_inv, a_currency, s_supplier_detail, s_supplier_detail s_supplier_1\n"
                        + "  WHERE s_mst.s_inv_no = s_sub_inv.s_inv_no AND s_mst.s_currency = a_currency.a_c_cd AND s_mst.s_pub_cd = s_supplier_detail.a_s_cd \n"
                        + "   AND s_mst.s_sup_cd = s_supplier_1.a_s_cd and s_buget_cd = '" + Paramvalue + "'";
                break;

            case "departmentWise":
                q = "SELECT s_inv_net_amt ,s_sub_inv.s_inv_dt as s_pay_dt,s_mst.s_title, s_mst.s_inv_no, s_mst.s_order_no, s_mst.s_buget_cd, s_mst.s_pub_cd, a_currency.a_c_nm, \n"
                        + "s_mst.s_sup_cd,s_supplier_detail.a_s_name AS Publiser, s_supplier_1.a_s_name AS Supplier \n"
                        + " FROM s_mst, s_sub_inv, a_currency,  s_supplier_detail, s_supplier_detail s_supplier_1\n"
                        + " WHERE s_mst.s_inv_no = s_sub_inv.s_inv_no AND s_mst.s_currency = a_currency.a_c_cd AND s_mst.s_pub_cd = s_supplier_detail.a_s_cd  \n"
                        + " AND s_mst.s_sup_cd = s_supplier_1.a_s_cd and \n"
                        + " s_dept_cd in (select fclty_dept_cd from m_fcltydept where fclty_dept_dscr = '" + Paramvalue + "%' and fld_tag='D' )";
                break;

            case "byPaymentStatus":
                q = " SELECT s_inv_net_amt,s_sub_inv.s_inv_dt as s_pay_dt, s_mst.s_title, s_mst.s_inv_no, s_mst.s_order_no, s_mst.s_buget_cd, s_mst.s_pub_cd, a_currency.a_c_nm, \n"
                        + " s_mst.s_sup_cd,s_supplier_detail.a_s_name AS Publiser, s_supplier_1.a_s_name AS Supplier  \n"
                        + " FROM s_mst, s_sub_inv, a_currency,  s_supplier_detail, s_supplier_detail s_supplier_1\n"
                        + " WHERE s_mst.s_inv_no = s_sub_inv.s_inv_no AND s_mst.s_currency = a_currency.a_c_cd\n"
                        + "  AND s_mst.s_pub_cd = s_supplier_detail.a_s_cd AND  s_mst.s_sup_cd = s_supplier_1.a_s_cd and s_inv_status = '" + Paramvalue + "'";
                break;

            case "publisherWise":
                q = "SELECT s_inv_net_amt ,s_sub_inv.s_inv_dt as s_pay_dt,s_mst.s_title, s_mst.s_inv_no, s_mst.s_order_no, s_mst.s_buget_cd, s_mst.s_pub_cd, a_currency.a_c_nm, \n"
                        + "s_mst.s_sup_cd,s_supplier_detail.a_s_name AS Publiser, s_supplier_1.a_s_name AS Supplier  \n"
                        + "FROM s_mst, s_sub_inv, a_currency, s_supplier_detail, s_supplier_detail s_supplier_1\n"
                        + " WHERE s_mst.s_inv_no = s_sub_inv.s_inv_no AND s_mst.s_currency = a_currency.a_c_cd AND s_mst.s_pub_cd = s_supplier_detail.a_s_cd \n"
                        + " AND  s_mst.s_sup_cd = s_supplier_1.a_s_cd and s_pub_cd in (select a_s_cd from s_supplier_detail where a_s_name Like '" + Paramvalue + "%')";
                break;

            case "supplierWise":
                //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                q = "SELECT s_inv_net_amt ,s_sub_inv.s_inv_dt as s_pay_dt,s_mst.s_title, s_mst.s_inv_no, s_mst.s_order_no, s_mst.s_buget_cd, s_mst.s_pub_cd, a_currency.a_c_nm, \n"
                        + "s_mst.s_sup_cd,s_supplier_detail.a_s_name AS Publiser, s_supplier_1.a_s_name AS Supplier  \n"
                        + "FROM s_mst, s_sub_inv, a_currency, s_supplier_detail, s_supplier_detail s_supplier_1\n"
                        + " WHERE s_mst.s_inv_no = s_sub_inv.s_inv_no AND s_mst.s_currency = a_currency.a_c_cd AND s_mst.s_pub_cd = s_supplier_detail.a_s_cd \n"
                        + "    AND  s_mst.s_sup_cd = s_supplier_1.a_s_cd and s_sup_cd in (select a_s_cd from s_supplier_detail where a_s_name  Like '" + Paramvalue + "%')";
                break;

            case "currencyWise":
                //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                q = "SELECT s_inv_net_amt ,s_sub_inv.s_inv_dt as s_pay_dt,s_mst.s_title, s_mst.s_inv_no, s_mst.s_order_no, s_mst.s_buget_cd, s_mst.s_pub_cd, a_currency.a_c_nm, \n"
                        + "s_mst.s_sup_cd,s_supplier_detail.a_s_name AS Publiser, s_supplier_1.a_s_name AS Supplier  \n"
                        + "FROM s_mst, s_sub_inv, a_currency, s_supplier_detail, s_supplier_detail s_supplier_1\n"
                        + " WHERE s_mst.s_inv_no = s_sub_inv.s_inv_no AND s_mst.s_currency = a_currency.a_c_cd AND s_mst.s_pub_cd = s_supplier_detail.a_s_cd \n"
                        + "   AND  s_mst.s_sup_cd = s_supplier_1.a_s_cd and s_mst.s_currency in (select a_c_cd from a_currency where a_c_nm Like '" + Paramvalue + "%')";
                break;

            case "invoiceDateBetween":
                //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                q = "SELECT s_inv_net_amt ,s_sub_inv.s_inv_dt as s_pay_dt,s_mst.s_title, s_mst.s_inv_no, s_mst.s_order_no, s_mst.s_buget_cd, s_mst.s_pub_cd, a_currency.a_c_nm, \n"
                        + "s_mst.s_sup_cd,s_supplier_detail.a_s_name AS Publiser, s_supplier_1.a_s_name AS Supplier  \n"
                        + "FROM s_mst, s_sub_inv, a_currency, s_supplier_detail, s_supplier_detail s_supplier_1\n"
                        + " WHERE s_mst.s_inv_no = s_sub_inv.s_inv_no AND s_mst.s_currency = a_currency.a_c_cd AND s_mst.s_pub_cd = s_supplier_detail.a_s_cd \n"
                        + "  AND  s_mst.s_sup_cd = s_supplier_1.a_s_cd and (s_inv_dt > '" + valueString[0] + "' AND s_inv_dt <'" + valueString[1] + "')";
                break;

            case "titleWise":
                //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                q = "SELECT s_inv_net_amt ,s_sub_inv.s_inv_dt as s_pay_dt,s_mst.s_title, s_mst.s_inv_no, s_mst.s_order_no, s_mst.s_buget_cd, s_mst.s_pub_cd, a_currency.a_c_nm, \n"
                        + "s_mst.s_sup_cd,s_supplier_detail.a_s_name AS Publiser, s_supplier_1.a_s_name AS Supplier  \n"
                        + "FROM s_mst, s_sub_inv, a_currency, s_supplier_detail, s_supplier_detail s_supplier_1\n"
                        + " WHERE s_mst.s_inv_no = s_sub_inv.s_inv_no AND s_mst.s_currency = a_currency.a_c_cd AND s_mst.s_pub_cd = s_supplier_detail.a_s_cd \n"
                        + "  AND  s_mst.s_sup_cd = s_supplier_1.a_s_cd and s_mst.s_title LIKE '" + Paramvalue + "'  ";
                break;

        }
        query = getEntityManager().createNativeQuery(q);

        result = (List<Object>) query.getResultList();
        HashMap<String, String> response = new HashMap<String, String>();
        HashMap<String, Object> fresult = new HashMap<> ();
        for (int i = 0; i < result.size(); i++) {
            Object[] row = (Object[]) result.get(i);
           // System.out.println(row[0]+" , "+result.size()+","+(Object[]) result.get(i));
            //   System.out.println("Element "+i+Arrays.toString(row));
            response.put("Net Amount", row[0].toString());
            response.put("Invoice Date", row[1].toString().substring(0, row[1].toString().length() - 2));
            response.put("Title", row[2].toString());
            response.put("Invoice No", row[3].toString());
            response.put("Order No", row[4].toString());
            response.put("Budget Code", row[5].toString());
            response.put("Publisher Code", row[6].toString());
            response.put("Currency", row[7].toString());
            response.put("Supplier Code", row[8].toString());
            response.put("Publisher Name", row[9].toString());
            response.put("Supplier Name", row[10].toString());
            
            fresult.put("InvReport :"+i, response);
        }
        
        
//        XStream magicApi = new XStream();
//        String xml = magicApi.toXML(fresult);
//        JSONObject obj = new JSONObject(fresult);
//           String xml=null;		
//        try {
//          
//            xml = XML.toString(obj);
//             
//        } catch (JSONException ex) {
//            Logger.getLogger(SSubInvFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
//		
       return fresult;
       
        //return  result;
    }

   
}
