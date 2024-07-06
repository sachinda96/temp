/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.PathSegment;
import soul.serialControl.SSubdetail;
import soul.serialControl.SSubdetailPK;
import soul.general_master.Autogenerate;
import soul.general_master.service.AutogenerateFacadeREST;
/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.serialcontrol.ssubdetail")
public class SSubdetailFacadeREST extends AbstractFacade<SSubdetail> {
    @EJB
    private AutogenerateFacadeREST autogenerateFacadeREST;
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    List<SSubdetail> getAll;

    
    private SSubdetailPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;sOrderNo=sOrderNoValue;sRecid=sRecidValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.serialControl.SSubdetailPK key = new soul.serialControl.SSubdetailPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> sOrderNo = map.get("sOrderNo");
        if (sOrderNo != null && !sOrderNo.isEmpty()) {
            key.setSOrderNo(sOrderNo.get(0));
        }
        java.util.List<String> sRecid = map.get("sRecid");
        if (sRecid != null && !sRecid.isEmpty()) {
            key.setSRecid(sRecid.get(0));
        }
        return key;
    }

    public SSubdetailFacadeREST() {
        super(SSubdetail.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(SSubdetail entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(SSubdetail entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.serialControl.SSubdetailPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public SSubdetail find(@PathParam("id") PathSegment id) {
        soul.serialControl.SSubdetailPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SSubdetail> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SSubdetail> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<SSubdetail> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        List<String> status = new ArrayList<>();

        switch (query) {
            case "findByOrderTypeNot":
                valueList.add(valueString[0] + "%");
                break;
                
            case "orderDetailsByOrderNo":
                valueList.add(valueString[0]);
                break;
            case "findForCancellation":    //No Parameter to set
                return super.findBy("SSubdetail." + query);

            case "findAllOrders":    //No Parameter to set
                return super.findBy("SSubdetail." + query);
                
                
            case "findBySMstStatussNotStanding":
                status.addAll(Arrays.asList(valueString[0].split("|")));
                valueList.add(status);
                break;
            default:
                valueList.add(valueString[0]);
                break;
            //

        }
        return super.findBy("SSubdetail." + query, valueList);
    }
    
    
    //FOr renew
    @GET
    @Path("findAllOrders")
    @Produces({"application/xml", "application/json"})
    public List<SSubdetail> findAllOrders() {
        List<SSubdetail> getAll = null;
        getAll = findBy("findAllOrders", "null");
        return getAll;
    }
    
    //FOr renew
    @GET
    @Path("findAllOrdersBySupplierCode/{supplierCode}")
    @Produces({"application/xml", "application/json"})
    public List<SSubdetail> findAllOrdersBySupplierCode(@Pattern(regexp = "^[A-Za-z0-9 &-_]*$", message = "{vendorName.pattern}") @PathParam("supplierCode") String supplierCode) {
        List<SSubdetail> getAll = null;
        getAll = findBy("findAllOrdersBySupplierCode", supplierCode);
        return getAll;
    }
    
    //FOr renew
    @GET
    @Path("findAllOrdersByTitle/{title}")
    @Produces({"application/xml", "application/json"})
    public List<SSubdetail> findAllOrdersByTitle(@PathParam("title") String title) {
        List<SSubdetail> getAll = null;
        getAll = findBy("findAllOrdersByTitle", title);
        return getAll;
    }
    
    //FOr renew
    @GET
    @Path("getOrderByNo/{orderNo}")
    @Produces({"application/xml", "application/json"})
    public List<SSubdetail> getOrderByNo(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{orderNo.pattern}") @PathParam("orderNo") String orderNo) {
        List<SSubdetail> getAll = null;
        getAll = findBy("getOrderByNo", orderNo);
        return getAll;
    }
    
    //FOr cancellation
    @GET
    @Path("getOrderDetails/{orderNo}")
    @Produces({"application/xml", "application/json"})
    public List<SSubdetail> getOrderDetails(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{orderNo.pattern}") @PathParam("orderNo") String orderNo) {
        List<SSubdetail> getAll = null;
        getAll = findBy("getOrderDetails", orderNo);
        return getAll;
    }
    
    //FOr purchasing order by orderno
    @GET
    @Path("purchaseByOrderNo/{orderNo}")
    @Produces({"application/xml", "application/json"})
    public List<SSubdetail> purchaseByOrderNo(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{orderNo.pattern}") @PathParam("orderNo") String orderNo) {
        List<SSubdetail> getAll = null;
        getAll = findBy("purchaseByOrderNo", orderNo);
        return getAll;
    }
    
    @GET
    @Path("retrieveAll/{form}")
    @Produces({"application/xml", "application/json"})
    public List<SSubdetail> retrieveAll(@PathParam("form") String form) 
    {
            //for renew order
            if(form.equals("UpdateOrder"))
            {
                getAll = findBy("findByOrderTypeNot", "M|D|V,S");
            }
            //For order cancellation
            if(form.equals("CancelOrder"))
            {
               getAll = findBy("findForCancellation", "NULL"); 
            }
            if(form.equals("InvoiceProcess"))
            {
                getAll = findBy("findBySMstStatussNotStanding", "O|S");
            }      
       return getAll;
    }
    
    
    //Purchase Order
    //This methods return the details about orders for purchasing
    @GET
    @Path("purchaseOrder/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json","text/plain"})
    public List<Object> purchaseOrder(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
               
        
        switch (Paramname) {
            
            case "byOrderNo":
                q = "SELECT s_subscription.s_order_no, s_subscription.s_order_dt, s_subscription.s_remark,\n" 
                        + " s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1, s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3, s_supplier_detail.a_s_city, \n" 
                        + " s_supplier_detail.a_s_pin, s_supplier_detail.a_s_state, s_supplier_detail.a_s_email, s_mst.s_title, s_subdetail.s_st_dt, s_subdetail.s_st_vol, \n" 
                        + " s_subdetail.s_st_iss, s_subdetail.s_en_dt, s_subdetail.s_en_vol, s_subdetail.s_en_iss, s_subdetail.s_mode, s_subdetail.s_copy,(s_copy * s_subdetail.s_price ) \n" 
                        + " as s_price, letter_formats.letter_format, letter_formats.subject,ImageData, Prefix,LastSeed ,publisher.a_s_name as publishername,\n" 
                        + " ('Iss.' + s_subdetail.s_st_iss + '-' + 'Iss.' + s_subdetail.s_en_iss) as issue  \n" 
                        + " FROM s_subscription,  s_supplier_detail, s_subdetail, s_mst ,letter_formats,ReportImage,AutoGenerate,s_supplier_detail as publisher\n" 
                        + " WHERE  s_subscription.s_sup_cd = s_supplier_detail.a_s_cd AND\n" 
                        + " s_subscription.s_order_no = s_subdetail.s_order_no AND s_subdetail.s_recid = s_mst.s_recid and\n" 
                        + " letter_name='pur_order' and ReportImage.ID=1 and AutoGenerate.Name='Serial PrintOrder No' and\n" 
                        + " s_subdetail.s_cancel is null  and s_mst.s_pub_cd =publisher.a_s_cd and s_subscription.s_order_no = '" + Paramvalue + "'";
                break;
                
            case "BySupplierCode":
                q = "SELECT s_subscription.s_order_no, s_subscription.s_order_dt, s_subscription.s_remark,\n" 
                        + " s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1, s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3, s_supplier_detail.a_s_city, \n" 
                        + " s_supplier_detail.a_s_pin, s_supplier_detail.a_s_state, s_supplier_detail.a_s_email, s_mst.s_title, s_subdetail.s_st_dt, s_subdetail.s_st_vol, \n" 
                        + " s_subdetail.s_st_iss, s_subdetail.s_en_dt, s_subdetail.s_en_vol, s_subdetail.s_en_iss, s_subdetail.s_mode, s_subdetail.s_copy,(s_copy * s_subdetail.s_price ) \n" 
                        + " as s_price, letter_formats.letter_format, letter_formats.subject,ImageData, Prefix,LastSeed ,publisher.a_s_name as publishername,\n" 
                        + " ('Iss.' + s_subdetail.s_st_iss + '-' + 'Iss.' + s_subdetail.s_en_iss) as issue  \n" 
                        + " FROM s_subscription,  s_supplier_detail, s_subdetail, s_mst ,letter_formats,ReportImage,AutoGenerate,s_supplier_detail as publisher\n" 
                        + " WHERE  s_subscription.s_sup_cd = s_supplier_detail.a_s_cd AND\n" 
                        + " s_subscription.s_order_no = s_subdetail.s_order_no AND s_subdetail.s_recid = s_mst.s_recid and\n" 
                        + " letter_name='pur_order' and ReportImage.ID=1 and AutoGenerate.Name='Serial PrintOrder No' and\n" 
                        + " s_subdetail.s_cancel is null  and s_mst.s_pub_cd =publisher.a_s_cd and\n" 
                        + " s_subscription.s_sup_cd in(select a_s_cd from s_supplier_detail where a_s_cd = '" + Paramvalue + "')  ";
                break;
                              
            case "byDateBetween":
                //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                q = "SELECT s_subscription.s_order_no, s_subscription.s_order_dt, s_subscription.s_remark,\n"
                        + " s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1, s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3, s_supplier_detail.a_s_city, \n"
                        + " s_supplier_detail.a_s_pin, s_supplier_detail.a_s_state, s_supplier_detail.a_s_email, s_mst.s_title, s_subdetail.s_st_dt, s_subdetail.s_st_vol, \n"
                        + " s_subdetail.s_st_iss, s_subdetail.s_en_dt, s_subdetail.s_en_vol, s_subdetail.s_en_iss, s_subdetail.s_mode, s_subdetail.s_copy,(s_copy * s_subdetail.s_price ) \n"
                        + " as s_price, letter_formats.letter_format, letter_formats.subject,ImageData, Prefix,LastSeed ,publisher.a_s_name as publishername,\n"
                        + " ('Iss.' + s_subdetail.s_st_iss + '-' + 'Iss.' + s_subdetail.s_en_iss) as issue  \n"
                        + " FROM s_subscription,  s_supplier_detail, s_subdetail, s_mst ,letter_formats,ReportImage,AutoGenerate,s_supplier_detail as publisher\n"
                        +" WHERE  s_subscription.s_sup_cd = s_supplier_detail.a_s_cd AND\n"
                        + " s_subscription.s_order_no = s_subdetail.s_order_no AND s_subdetail.s_recid = s_mst.s_recid and\n" 
                        + " letter_name='pur_order' and ReportImage.ID=1 and AutoGenerate.Name='Serial PrintOrder No' and\n" 
                        + " s_subdetail.s_cancel is null  and s_mst.s_pub_cd =publisher.a_s_cd and (s_order_dt BETWEEN '" + valueString[0] + "' AND '"+ valueString[1] + "') ";
               break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        ArrayList<String> list = new ArrayList<String>();
        list.add("Serial PrintOrder");
        list.add("Serial PrintOrder No");
        String letterNo = null;
        letterNo = autogenerateFacadeREST.generateLetterNo("Serial PrintOrder No");
        for(int i=0; i<list.size(); i++){
            autogenerateFacadeREST.updateAutogenerate(list.get(i));
        }
        System.out.println("letterNo: "+letterNo);
        return result; 
    }
    
    // Order Report
    //This methods return the details about orders for report generation    
    @GET
    @Path("orderReport/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> orderReport(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {

            case "allOrders":
                q = "SELECT distinct s_mst.s_title,s_subdetail.s_order_no,s_supplier_detail.a_s_name,s_edition.s_ed_name,s_subdetail.s_copy as s_appr_copy,(s_copy*s_subdetail.s_price) as s_price,\n"
                        + " (concat(DATE_FORMAT(s_mst.s_st_dt,'%Y-%m-%d'),' : ',DATE_FORMAT(s_mst.s_en_dt,'%Y-%m-%d'))) as s_st_dt\n"
                        + " FROM s_mst,s_subdetail,s_supplier_detail,s_edition,s_subscription   \n"
                        + " WHERE s_subdetail.s_order_no=s_subscription.s_order_no and  s_mst.s_recid= s_subdetail.s_recid  AND s_supplier_detail.a_s_cd=s_mst.s_pub_cd  \n"
                        + " AND (s_mst.s_edition = s_edition.s_ed_type or s_mst.s_edition is null or s_mst.s_edition='') and (s_order_dt BETWEEN '" + valueString[0] + "' AND '" + valueString[1] + "')";
                break;

            case "completedOrders":
                q = "SELECT distinct s_mst.s_title,s_subdetail.s_order_no,s_supplier_detail.a_s_name,s_edition.s_ed_name,s_subdetail.s_copy as s_appr_copy,(s_copy*s_subdetail.s_price) as s_price,\n"
                        + " (concat(DATE_FORMAT(s_mst.s_st_dt,'%Y-%m-%d'),' : ',DATE_FORMAT(s_mst.s_en_dt,'%Y-%m-%d'))) as s_st_dt\n"
                        + " FROM s_mst,s_subdetail,s_supplier_detail,s_edition,s_subscription   \n"
                        + " WHERE s_subdetail.s_order_no=s_subscription.s_order_no and s_mst.s_recid= s_subdetail.s_recid  AND s_supplier_detail.a_s_cd=s_mst.s_pub_cd \n"
                        + " AND (s_mst.s_edition = s_edition.s_ed_type or s_mst.s_edition is null or s_mst.s_edition='') and s_subdetail.s_order_no not in \n"
                        + " (select s_order_no from s_mst where s_mst_status='O') and s_subdetail.s_cancel is null and (s_order_dt BETWEEN '" + valueString[0] + "' AND '" + valueString[1] + "')";
                break;

            case "generatedOrders":
                q = "SELECT distinct s_mst.s_title,s_subdetail.s_order_no,s_supplier_detail.a_s_name,s_edition.s_ed_name,s_subdetail.s_copy as s_appr_copy,(s_copy*s_subdetail.s_price) as s_price,\n"
                        + " (concat(DATE_FORMAT(s_mst.s_st_dt,'%Y-%m-%d'),' : ',DATE_FORMAT(s_mst.s_en_dt,'%Y-%m-%d'))) as s_st_dt \n"
                        + " FROM s_mst,s_subdetail,s_supplier_detail,s_edition,s_subscription  \n"
                        + " WHERE s_subdetail.s_order_no=s_subscription.s_order_no and  s_mst.s_recid= s_subdetail.s_recid  AND s_supplier_detail.a_s_cd=s_mst.s_pub_cd  \n"
                        + " AND (s_mst.s_edition = s_edition.s_ed_type or s_mst.s_edition is null or s_mst.s_edition='') and  s_subdetail.s_order_no not in \n"
                        + " (select distinct s_inv_order_no from s_sub_invdetail) and s_subdetail.s_cancel is null and (s_order_dt BETWEEN '" + valueString[0] + "' AND '" + valueString[1] + "')";
                break;

            case "cancelledOrders":
                //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                q = "SELECT distinct s_mst.s_title,s_subdetail.s_order_no,s_supplier_detail.a_s_name,s_edition.s_ed_name,s_subdetail.s_copy as s_appr_copy,(s_copy*s_subdetail.s_price) as s_price,\n"
                        + " (concat(DATE_FORMAT(s_mst.s_st_dt,'%Y-%m-%d'),' : ',DATE_FORMAT(s_mst.s_en_dt,'%Y-%m-%d'))) as s_st_dt \n"
                        + " FROM s_mst,s_subdetail,s_supplier_detail,s_edition,s_subscription \n"
                        + " WHERE s_subdetail.s_order_no=s_subscription.s_order_no and s_mst.s_recid= s_subdetail.s_recid  AND s_supplier_detail.a_s_cd=s_mst.s_pub_cd \n"
                        + " AND (s_mst.s_edition = s_edition.s_ed_type or s_mst.s_edition is null or s_mst.s_edition='') and s_subscription.s_cancel='Y' and (s_order_dt BETWEEN '" + valueString[0] + "' AND '" + valueString[1] + "')";
                break;
        }
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
    
    
    // Reminder Report for Check-In
    //This methods return the details about orders for report generation for reminder  
    @GET
    @Path("reminderLetter/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> reminderLetter(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
            case "bySupplierCode":
                q = "SELECT s_subdetail.s_order_no, s_mst.s_title, s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1,\n"
                        + " s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3,  s_supplier_detail.a_s_city, s_supplier_detail.a_s_pin, \n"
                        + " s_supplier_detail.a_s_state, s_subdetail.s_st_vol, s_subdetail.s_st_iss, s_schedule.s_s_vol,  \n"
                        + " s_schedule.s_s_recid, s_schedule.s_s_reminder AS a_s_phone, s_schedule.s_s_srno, s_schedule.s_s_iss, \n"
                        + " s_schedule.s_s_pub_dt, s_schedule.s_s_exp_dt,  CountryMaster.Country, letter_formats.letter_format, \n"
                        + " AutoGenerate.Prefix, ReportImage.ImageData  \n"
                        + " FROM s_subdetail\n"
                        + " LEFT OUTER JOIN s_supplier_detail \n"
                        + " INNER JOIN CountryMaster ON s_supplier_detail.a_s_country = CountryMaster.Code\n"
                        + " RIGHT OUTER JOIN s_mst \n"
                        + " RIGHT OUTER JOIN s_subscription ON s_mst.s_order_no = s_subscription.s_order_no ON s_supplier_detail.a_s_cd = s_subscription.s_sup_cd \n"
                        + " ON s_subdetail.s_order_no = s_subscription.s_order_no RIGHT OUTER JOIN s_schedule \n"
                        + " ON  s_subdetail.s_recid = s_schedule.s_s_recid AND s_mst.s_recid = s_schedule.s_s_recid \n"
                        + " CROSS JOIN letter_formats \n"
                        + " CROSS JOIN AutoGenerate CROSS JOIN ReportImage \n"
                        + " WHERE (s_schedule.s_s_status LIKE 'E%')  AND letter_name='remin_scm' AND name='Serial Reminder No' \n"
                        + " AND ReportImage.ID=1  and s_subscription.s_sup_cd = '" + Paramvalue + "' and s_schedule.s_s_exp_dt < current_Date()";
                break;

            case "byPublisherCode":
                q = "SELECT s_subdetail.s_order_no, s_mst.s_title, s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1, \n"
                        + "s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3, s_supplier_detail.a_s_city, s_supplier_detail.a_s_pin, \n"
                        + "s_supplier_detail.a_s_state, s_subdetail.s_st_vol, s_subdetail.s_st_iss, s_schedule.s_s_vol,  \n"
                        + "s_schedule.s_s_recid, s_schedule.s_s_reminder AS a_s_phone, s_schedule.s_s_srno, s_schedule.s_s_iss, \n"
                        + "s_schedule.s_s_pub_dt, s_schedule.s_s_exp_dt, CountryMaster.Country, letter_formats.letter_format, \n"
                        + "AutoGenerate.Prefix, ReportImage.ImageData  \n"
                        + "FROM s_subdetail  \n"
                        + "LEFT OUTER JOIN s_supplier_detail \n"
                        + "INNER JOIN CountryMaster ON s_supplier_detail.a_s_country = CountryMaster.Code \n"
                        + "RIGHT OUTER JOIN s_mst RIGHT OUTER JOIN s_subscription ON s_mst.s_order_no = s_subscription.s_order_no \n"
                        + "ON s_supplier_detail.a_s_cd = s_subscription.s_sup_cd ON s_subdetail.s_order_no = s_subscription.s_order_no \n"
                        + "RIGHT OUTER JOIN s_schedule ON s_subdetail.s_recid = s_schedule.s_s_recid AND s_mst.s_recid = s_schedule.s_s_recid \n"
                        + "CROSS JOIN letter_formats \n"
                        + "CROSS JOIN AutoGenerate \n"
                        + "CROSS JOIN  ReportImage \n"
                        + "WHERE (s_schedule.s_s_status LIKE 'E%')  AND letter_name='remin_scm' AND name='Serial Reminder No' \n"
                        + "AND ReportImage.ID=1  and s_mst.s_pub_cd = '" + Paramvalue + "' and s_schedule.s_s_exp_dt < current_Date()";
                break;

        }
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        
        ArrayList<String> list = new ArrayList<String>();
        list.add("Serial Reminder");

        for(int i=0; i<list.size(); i++){
            autogenerateFacadeREST.updateAutogenerate(list.get(i));
        }
        return result;
    }
    
    // Reminder Report for Check-In
    //This methods return the details about orders for report generation for reminder  
    @GET
    @Path("reminderLetterBySupplierCodeAndDate/{code}/{date}")
    @Produces({"application/xml", "application/json"})
    public List<Object> reminderLetterBySupplierCodeAndDate(@PathParam("code") String code, @PathParam("date") String date) throws ParseException {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;
        q = "SELECT     s_subdetail.s_order_no, s_mst.s_title, s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1, s_supplier_detail.a_s_add2, \n"
                + "s_supplier_detail.a_s_add3,  s_supplier_detail.a_s_city, s_supplier_detail.a_s_pin, s_supplier_detail.a_s_state, s_subdetail.s_st_vol, \n"
                + "s_subdetail.s_st_iss, s_schedule.s_s_vol,  s_schedule.s_s_recid, s_schedule.s_s_reminder AS a_s_phone, s_schedule.s_s_srno, \n"
                + "s_schedule.s_s_iss, s_schedule.s_s_pub_dt, s_schedule.s_s_exp_dt,  CountryMaster.Country, letter_formats.letter_format, \n"
                + "AutoGenerate.Prefix, ReportImage.ImageData  FROM s_subdetail LEFT OUTER JOIN s_supplier_detail INNER JOIN CountryMaster ON \n"
                + "s_supplier_detail.a_s_country = CountryMaster.Code\n"
                + "RIGHT OUTER JOIN s_mst \n"
                + "RIGHT OUTER JOIN s_subscription ON s_mst.s_order_no = s_subscription.s_order_no ON \n"
                + "s_supplier_detail.a_s_cd = s_subscription.s_sup_cd ON s_subdetail.s_order_no = s_subscription.s_order_no \n"
                + " RIGHT OUTER JOIN s_schedule ON s_subdetail.s_recid = s_schedule.s_s_recid AND s_mst.s_recid = s_schedule.s_s_recid \n"
                + " CROSS JOIN letter_formats \n"
                + " CROSS JOIN AutoGenerate \n"
                + " CROSS JOIN   ReportImage WHERE     (s_schedule.s_s_status LIKE 'E%')  \n"
                + " AND letter_name='remin_scm' AND name='Serial Reminder No' AND ReportImage.ID=1  \n"
                + " and s_subscription.s_sup_cd = '" + code + "' and s_schedule.s_s_exp_dt < '" + date + "' ";
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        ArrayList<String> list = new ArrayList<String>();
        list.add("Serial Reminder");
        for(int i=0; i<list.size(); i++){
            autogenerateFacadeREST.updateAutogenerate(list.get(i));
        }
        return result;
    }
    
    // Reminder Report for Check-In
    //This methods return the details about orders for report generation for reminder  
    @GET
    @Path("reminderLetterByPublisherCodeAndDate/{code}/{date}")
    @Produces({"application/xml", "application/json"})
    public List<Object> reminderLetterByPublisherCodeAndDate(@PathParam("code") String code, @PathParam("date") String date) throws ParseException {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;

        q = "SELECT s_subdetail.s_order_no, s_mst.s_title, s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1, \n"
                + "s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3, s_supplier_detail.a_s_city, s_supplier_detail.a_s_pin, \n"
                + "s_supplier_detail.a_s_state, s_subdetail.s_st_vol, s_subdetail.s_st_iss, s_schedule.s_s_vol,  \n"
                + "s_schedule.s_s_recid, s_schedule.s_s_reminder AS a_s_phone, s_schedule.s_s_srno, s_schedule.s_s_iss, \n"
                + "s_schedule.s_s_pub_dt, s_schedule.s_s_exp_dt, CountryMaster.Country, letter_formats.letter_format, \n"
                + "AutoGenerate.Prefix, ReportImage.ImageData  \n"
                + "FROM s_subdetail  \n"
                + "LEFT OUTER JOIN s_supplier_detail \n"
                + "INNER JOIN CountryMaster ON s_supplier_detail.a_s_country = CountryMaster.Code \n"
                + "RIGHT OUTER JOIN s_mst RIGHT OUTER JOIN s_subscription ON s_mst.s_order_no = s_subscription.s_order_no \n"
                + "ON s_supplier_detail.a_s_cd = s_subscription.s_sup_cd ON s_subdetail.s_order_no = s_subscription.s_order_no \n"
                + "RIGHT OUTER JOIN s_schedule ON s_subdetail.s_recid = s_schedule.s_s_recid AND s_mst.s_recid = s_schedule.s_s_recid \n"
                + "CROSS JOIN letter_formats \n"
                + "CROSS JOIN AutoGenerate \n"
                + "CROSS JOIN  ReportImage \n"
                + "WHERE (s_schedule.s_s_status LIKE 'E%')  AND letter_name='remin_scm' AND name='Serial Reminder No' \n"
                + "AND ReportImage.ID=1  and s_mst.s_pub_cd = '" + code + "' and s_schedule.s_s_exp_dt < '" + date + "' ";
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        ArrayList<String> list = new ArrayList<String>();
        list.add("Serial Reminder");
        for(int i=0; i<list.size(); i++){
            autogenerateFacadeREST.updateAutogenerate(list.get(i));
        }
        return result;
    }
    
    
    //used in browse orders in invoice processing for retrieving data of order into grid by order no as parmater
    @GET
    @Path("orderDetailsByOrderNo/{orderNo}")
    @Produces({"application/xml", "application/json"})
    public List<SSubdetail> orderDetailsByOrderNo(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{orderNo.pattern}") @PathParam("orderNo") String orderNo) {
        List<SSubdetail> getAll = null;
        getAll = findBy("orderDetailsByOrderNo", orderNo);
        return getAll;
    }
}
