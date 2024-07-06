/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.acquisition.suggestions.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import soul.acquisition.suggestions.AOrder;
import soul.acquisition.suggestions.AOrdermaster;
import soul.acquisition.suggestions.ARequest;
import soul.catalogue.Biblidetails;
import soul.general_master.ASupplierDetail;
import soul.general_master.Autogenerate;
import soul.general_master.LetterFormats;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.acquisition.suggestions.aordermaster")
public class AOrdermasterFacadeREST extends AbstractFacade<AOrdermaster> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public AOrdermasterFacadeREST() {
        super(AOrdermaster.class);
    }

    /*  This method is not used for CREATE operation but below defined createAndGetId() method is used
     * @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(AOrdermaster entity) {
        super.create(entity);
    }*/
    
    @POST
    @Override
    @Path("createAndGet")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public AOrdermaster createAndGet(AOrdermaster entity) {
        return super.createAndGet(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(AOrdermaster entity) {
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
    public AOrdermaster find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<AOrdermaster> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<AOrdermaster> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @GET
    @Path("getOrderAndSupplierCode")
    @Produces("text/plain")
    public String getOrderAndSupplierCode()
    {
        List<AOrdermaster> orderMasterList = findAll();
        String orderMasterNos = "";
        String supplierNos = "";
        if(orderMasterList.size() != 0)
        {
            for(AOrdermaster om: orderMasterList)
            {
                if(om.getAOStatus().equals("D"))
                {
                    orderMasterNos = orderMasterNos+","+om.getAOPoNo();
                    supplierNos = supplierNos+","+om.getAOSupCd().getASCd();
                }
            }
        }
        String orderMasterSupplierCode = "";
        if(orderMasterNos.length() != 0) 
        {
            orderMasterSupplierCode = orderMasterNos.substring(1)+"|"+supplierNos.substring(1);
        }
       return orderMasterSupplierCode;
    }
    
   
   //for report generation in order without date
    @GET
    @Path("searchOrder/{paramName}/{paramValue}")
    @Produces({"application/xml", "application/json"})
    public List<AOrdermaster> getOrderListByParam(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) throws ParseException
    {
        String[] valueString = paramValue.split(",");
        List<Object> valueList = new ArrayList<>();
        List<AOrdermaster> getOrderList = null;
        switch(paramName){
            case "orderNo":
                valueList.add(valueString[0]);
                getOrderList = super.findBy("AOrdermaster.findByAOPoNo1", valueList );
                break;
            case "supplierCode":
                valueList.add(valueString[0]);
                getOrderList = super.findBy("AOrdermaster.findBySupplierCode", valueList );
                break;
            case "supplierName":
                valueList.add(valueString[0]);
                getOrderList = super.findBy("AOrdermaster.findBySupplierName", valueList );
                break;
            case "status":
                valueList.add(valueString[0]);
                getOrderList = super.findBy("AOrdermaster.findByAOStatus", valueList );
                break;
            case "date":
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[0]));
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[1]));
                getOrderList = super.findBy("AOrdermaster.findByAOPoDtBetween", valueList );
                break;
            case "reminderBySupplier":
                valueList.add(valueString[0]);
                getOrderList = super.findBy("AOrdermaster.findReminderBySuppCode", valueList );
                break;
        }
        return getOrderList;
    }
    
    //for report generation in order with date
    @GET
    @Path("searchOrder/{paramName}/{paramValue}/date/{date}")
    @Produces({"application/xml", "application/json"})
    public List<AOrdermaster> getOrderListByParamAndDate(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue
            ,@PathParam("date") String date) throws ParseException 
    {
        String[] dateValue = date.split(",");
        List<Object> valueList = new ArrayList<>();
        List<AOrdermaster> getOrderList = null;
        switch(paramName){
            case "orderNo":
                valueList.add(paramValue);
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue[0]));
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue[1]));
                getOrderList = super.findBy("AOrdermaster.findByAOPoNo1AndDate", valueList );
                break;
            case "supplierCode":
                valueList.add(paramValue);
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue[0]));
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue[1]));
                getOrderList = super.findBy("AOrdermaster.findBySupplierCodeAndDate", valueList );
                break;
            case "supplierName":
                valueList.add(paramValue);
               valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue[0]));
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue[1]));
                getOrderList = super.findBy("AOrdermaster.findBySupplierNameAndDate", valueList );
                break;
            case "status":
                valueList.add(paramValue);
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue[0]));
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue[1]));
                getOrderList = super.findBy("AOrdermaster.findByAOStatusAndDate", valueList );
                break;
//            case "date":
//               // valueList.add(Integer.parseInt(paramValue));
//                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue[0]));
//                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue[1]));
//                getOrderList = super.findBy("AOrdermaster.findByAOPoDtBetween", valueList );
//                break;
            case "reminderBySupplier":
                valueList.add(paramValue);
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue[0]));
                getOrderList = super.findBy("AOrdermaster.findReminderBySuppCodeAndExpDate", valueList );
                break;
        }
        return getOrderList;
    }
    
    
    // used in purchase order
    @GET
    @Path("getDataForPurchaseOrder/{paramName}/{paramValue}")
    @Produces({"application/json"})
    public List<Object> getDataForPurchaseOrder(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) {
        String q="";
        Query query;
        String valuestring[] = paramValue.split(",");
        
        switch(paramName)
        {
            case "orderNo": q = "SELECT a_ordermaster.a_o_po_no1, a_ordermaster.a_o_sup_cd, a_ordermaster.a_o_po_dt,"
                            + "a_ordermaster.a_o_po_no, letter_formats.letter_format,    "
                            + "letter_formats.subject, a_order.a_r_no, a_order.a_o_price, a_order.a_o_ordered_copy,"
                            + " A.a_s_name AS SupplierName,  A.a_s_add1 AS supadd1, A.a_s_add2 AS supadd2, A.a_s_country AS supcountry,"
                            + " A.a_s_state AS supstate, A.a_s_city AS supcity,  A.a_s_pin AS suppin, A.a_s_email AS supemail,"
                            + " A.a_s_phone AS supphone, A.a_s_fax AS supfax,prefix,LastSeed, a_request.a_r_title,a_request.a_r_author,"
                            + "a_request.a_r_pub_cd as PublisherName,  a_request.a_r_price, a_request.a_r_edition, a_request.a_r_year, "
                            + " a_request.a_r_isbn, a_request.a_r_conv_rate, a_ordermaster.a_o_exp_dt FROM  a_ordermaster, a_order,"
                            + " a_supplier_detail A, a_request, letter_formats,AutoGenerate WHERE a_ordermaster.a_o_po_no = a_order.a_o_po_no And "
                            + "a_ordermaster.a_o_sup_cd = A.a_s_cd And a_order.a_r_no = a_request.a_r_no and   (letter_formats.letter_name = 'pur_order')and a_ordermaster.a_o_po_no1 in "
                            + "(Select a_o_po_no1 from a_ordermaster where a_o_po_no1 like '"+valuestring[0]+"') "
                            + "and AutoGenerate.name='Acquisition Purchase Order No'" ;
                            break;
            case "orderDate":      q = "SELECT a_ordermaster.a_o_po_no1, a_ordermaster.a_o_sup_cd, a_ordermaster.a_o_po_dt,"
                            + "a_ordermaster.a_o_po_no, letter_formats.letter_format,    "
                            + "letter_formats.subject, a_order.a_r_no, a_order.a_o_price, a_order.a_o_ordered_copy,"
                            + " A.a_s_name AS SupplierName,  A.a_s_add1 AS supadd1, A.a_s_add2 AS supadd2, A.a_s_country AS supcountry,"
                            + " A.a_s_state AS supstate, A.a_s_city AS supcity,  A.a_s_pin AS suppin, A.a_s_email AS supemail,"
                            + " A.a_s_phone AS supphone, A.a_s_fax AS supfax,prefix,LastSeed, a_request.a_r_title,a_request.a_r_author,"
                            + "a_request.a_r_pub_cd as PublisherName,  a_request.a_r_price, a_request.a_r_edition, a_request.a_r_year, "
                            + " a_request.a_r_isbn, a_request.a_r_conv_rate, a_ordermaster.a_o_exp_dt FROM  a_ordermaster, a_order,"
                            + " a_supplier_detail A, a_request, letter_formats,AutoGenerate WHERE a_ordermaster.a_o_po_no = a_order.a_o_po_no And "
                            + "a_ordermaster.a_o_sup_cd = A.a_s_cd And a_order.a_r_no = a_request.a_r_no and   (letter_formats.letter_name = 'pur_order')and a_ordermaster.a_o_po_no1 in "
                            + "(Select a_o_po_no1 from a_ordermaster where a_o_po_dt between  '"+valuestring[0]+"' and '"+valuestring[1]+"')"
                            + "and AutoGenerate.name='Acquisition Purchase Order No'" ;
                            break;
            case "supplier":      q = "SELECT a_ordermaster.a_o_po_no1, a_ordermaster.a_o_sup_cd, a_ordermaster.a_o_po_dt,"
                            + "a_ordermaster.a_o_po_no, letter_formats.letter_format,    "
                            + "letter_formats.subject, a_order.a_r_no, a_order.a_o_price, a_order.a_o_ordered_copy,"
                            + " A.a_s_name AS SupplierName,  A.a_s_add1 AS supadd1, A.a_s_add2 AS supadd2, A.a_s_country AS supcountry,"
                            + " A.a_s_state AS supstate, A.a_s_city AS supcity,  A.a_s_pin AS suppin, A.a_s_email AS supemail,"
                            + " A.a_s_phone AS supphone, A.a_s_fax AS supfax,prefix,LastSeed, a_request.a_r_title,a_request.a_r_author,"
                            + "a_request.a_r_pub_cd as PublisherName,  a_request.a_r_price, a_request.a_r_edition, a_request.a_r_year, "
                            + " a_request.a_r_isbn, a_request.a_r_conv_rate, a_ordermaster.a_o_exp_dt FROM  a_ordermaster, a_order,"
                            + " a_supplier_detail A, a_request, letter_formats,AutoGenerate WHERE a_ordermaster.a_o_po_no = a_order.a_o_po_no And "
                            + "a_ordermaster.a_o_sup_cd = A.a_s_cd And a_order.a_r_no = a_request.a_r_no and   (letter_formats.letter_name = 'pur_order')and a_ordermaster.a_o_po_no1 in "
                            + "(Select a_o_po_no1 from a_ordermaster where a_o_sup_cd like '"+valuestring[0]+"')"
                            + "and AutoGenerate.name='Acquisition Purchase Order No'" ;
                            break;
        }
        //System.out.println("query  "+q);
        List<Object> result;
         long a = System.currentTimeMillis();
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
         long b = System.currentTimeMillis();
         System.out.println("time.......  "+(b-a));
        
        return result;
    }
    
    
    //////criteria sample testing
    
//    @GET
//    @Path("getDataForPurchaseOrderCriteria/{paramName}/{paramValue}")
//    @Produces({"application/xml","application/json"})
//    public List<Object> getDataForPurchaseOrderCriteria(@PathParam("paramName") String paramName,@PathParam("paramValue") String paramValue) throws ParseException {
//        String q="";
//   //     Query query;
//        String valuestring[] = paramValue.split(",");
//     //   String valuestring[] = paramValue.split(",");
//        List<Object> results = null;
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Object> q1 = cb.createQuery(Object.class);
//
//        Subquery<AOrdermaster> sq1 = q1.subquery(AOrdermaster.class);// for subquery
//        Root<AOrdermaster> s1 = sq1.from(AOrdermaster.class);// for subquery
//
//        Root<AOrdermaster> r1 = q1.from(AOrdermaster.class);
//        Root<LetterFormats> r2 = q1.from(LetterFormats.class);
//        Root<Autogenerate> r3 = q1.from(Autogenerate.class);
//
//        Join<AOrdermaster, AOrder> j1 = r1.join("aOrderCollection");
//        Join<AOrder, ARequest> j2 = j1.join("aRequest");
//        Join<AOrdermaster, ASupplierDetail> j3 = r1.join("aOSupCd");
//        Join<ARequest, ASupplierDetail> j4 = j2.join("aRPubCd");
//        //  Join<AOrder, AOrdermaster> p2 = p1.join("aOrderCollection");
//
//        q1.multiselect(r1.get("aOPoNo1"), j3.get("aSCd"), r1.get("aOPoDt"),
//                r1.get("aOPoNo"), r2.get("letterFormat"), r2.get("subject"), j1.get("aOPrice"), j1.get("aOOrderedCopy"),
//                j3.get("aSName"), j2.get("aRNo"), j3.get("aSAdd1"), j3.get("aSAdd2"), j3.get("aSCountry"),
//                j3.get("aSState"), j3.get("aSCity"), j3.get("aSPin"),
//                j3.get("aSEmail"), j3.get("aSPhone"), j3.get("aSFax"), r3.get("prefix"),
//                r3.get("lastSeed"), j2.get("aRTitle"), j2.get("aRAuthor"), j4.get("aSCd"),
//                j2.get("aRPrice"), j2.get("aREdition"), j2.get("aRYear"), j2.get("aRIsbn"),
//                j2.get("aRConvRate"), r1.get("aOExpDt"));
//        //q1.where(cb.equal(p1.get("aOPoNo"), "8"));
//
//
//        Predicate p1 = cb.and(cb.equal(r2.get("letterName"), "pur_order"));
//        Predicate p2 = cb.and(cb.equal(r3.get("name"), "Acquisition Purchase Order No"));
//        Expression<AOrdermaster> path1 = s1.get("aOPoNo1");
//        Predicate p3 = null;
//        Expression<String> path2  = null;
//        switch(paramName)
//        {
//            case "orderNo":
//                path2 = s1.get("aOPoNo1");
//                p3 = cb.like(path2, valuestring[0]);
//                break;
//            case "orderDate":    
//                path2 = s1.get("aOPoDt");
//                p3 = cb.between(path2, valuestring[0],valuestring[1]);
//                break;
//            case "supplier":    
//                Join<AOrdermaster, ASupplierDetail> sj1 = s1.join("aOSupCd", JoinType.LEFT);
//                path2 = sj1.get("aSCd");
//                p3 = cb.like(path2, valuestring[0]);
//                break;
//        }
//        sq1.select(path1).where(p3);
//        Predicate p4 = cb.and(r1.get("aOPoNo1").in(sq1));
//       
//        q1.where(p1, p2, p4);
//        results = em.createQuery(q1).getResultList();
//        return results;
//    }
//    //////////////////////////////
//    
     // used in reminder letter
    @GET
    @Path("getDataForReminderLetter/{suppCd}/{date}")
    @Produces({"application/json"})
    public List<Object> getDataForReminderLetter(@PathParam("suppCd") String paramValue,
    @PathParam("date") String date) throws ParseException {
        String q="";
        String paramQueryAdd ="";
        Query query;
        String valuestring[] = paramValue.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if((!date.equals("''")))
        { 
            Date dateValue = new java.sql.Date(dateFormat.parse(date).getTime());
            paramQueryAdd = "and a_o_exp_dt <='"+dateValue+"'";
        }
        q = "select a_ordermaster.a_o_po_no1,a_ordermaster.a_o_sup_cd, a_ordermaster.a_o_po_dt,a_ordermaster.a_o_exp_dt,\n" +
            "  a_ordermaster.a_o_po_no,letter_formats.letter_format,letter_formats.subject, a_order.a_r_no,a_order.a_o_price,\n" +
            "  a_order.a_o_ordered_copy,prefix, a_request.a_r_title,a_request.a_r_author,a_request.a_r_pub_cd,B.a_s_name as \n" +
            "  PublisherName, A.a_s_name as SupplierName,B.a_s_add1,B.a_s_add2,B.a_s_city,B.a_s_pin,B.a_s_state,B.a_s_email \n" +
            "  from a_ordermaster,a_order,a_request,letter_formats,a_supplier_detail A,a_supplier_detail B, AutoGenerate \n" +
            "  where a_ordermaster.a_o_po_no = a_order.a_o_po_no and a_order.a_r_no = a_request.a_r_no and A.a_s_cd = a_ordermaster.a_o_sup_cd \n" +
            "  and B.a_s_cd = a_request.a_r_pub_cd and letter_formats.letter_name='remin_lett' and AutoGenerate.name='Acquisition Reminder No' \n" +
            "  and a_ordermaster.a_o_po_no1 in (Select a_o_po_no1 from a_ordermaster where a_o_sup_cd ='"+valuestring[0]+"' "+paramQueryAdd+")";
    
        System.out.println("query  "+q);
        List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
   
    
    // used in reminder letter by criteria
//    @GET
//    @Path("getDataForReminderLetterCriteria/{paramValue}/{date}")
//    @Produces({"application/json"})
//    public List<Object> getDataForReminderLetterCriteria(@PathParam("paramValue") String paramValue,
//    @PathParam("date") String date) throws ParseException {
//        String q="";
//       // String paramQueryAdd ="";
//        List<Object> results = null;
//        Query query;
//        String valuestring[] = paramValue.split(",");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//       
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Object> q1 = cb.createQuery(Object.class);
//        
//        Root<AOrdermaster> r1 = q1.from(AOrdermaster.class);
//        Root<LetterFormats> r2 = q1.from(LetterFormats.class);
//        Root<Autogenerate> r3 = q1.from(Autogenerate.class);
//        
//        Subquery<AOrdermaster> sq1 = q1.subquery(AOrdermaster.class); // for subquery
//        Root<AOrdermaster> s1 = sq1.from(AOrdermaster.class);  // for subquery
//        
//        Join<AOrdermaster,AOrder> j1 = r1.join("aOrderCollection");
//        Join<AOrder, ARequest> j2 = j1.join("aRequest");
//        Join<AOrdermaster, ASupplierDetail> j3 = r1.join("aOSupCd");
//        Join<ARequest, ASupplierDetail> j4 = j2.join("aRPubCd");
//        
//        q1.multiselect(r1.get("aOPoNo1"), j3.get("aSCd"), r1.get("aOPoDt"),r1.get("aOExpDt"),
//                r1.get("aOPoNo"), r2.get("letterFormat"), r2.get("subject"),j2.get("aRNo"), j1.get("aOPrice"),
//                j1.get("aOOrderedCopy"),r3.get("prefix"),j2.get("aRTitle"),  j2.get("aRAuthor"), j4.get("aSCd"),
//                j4.get("aSName"),j3.get("aSAdd1"), j3.get("aSAdd2"), j3.get("aSCountry"),
//                j3.get("aSState"), j3.get("aSCity"), j3.get("aSPin"),j3.get("aSEmail"));
//        
//        Predicate p1 = cb.and(cb.equal(r2.get("letterName"), "remin_lett"));
//        Predicate p2 = cb.and(cb.equal(r3.get("name"), "Acquisition Reminder No"));
//        
//        Expression<AOrdermaster> e1 = s1.get("aOPoNo1");
//        Predicate p3 = null;
//        Expression<String> e2 = null;
//     //   Join<AOrdermaster, ASupplierDetail> sj1 = s1.join("aOSupCd");
//        Join<AOrdermaster, ASupplierDetail> sj1 = s1.join("aOSupCd");
//        
//        e2 = sj1.get("aSCd");
//        p3 = cb.like(e2, valuestring[0]);
//  
//        sq1.select(e1).where(p3);
//        Predicate p4 = cb.and(r1.get("aOPoNo1").in(sq1));
//        Predicate p5 = null;
//         if((!date.equals("''")))
//        { 
//            Date dateValue = new java.sql.Date(dateFormat.parse(date).getTime());
//          //  paramQueryAdd = "and a_o_exp_dt <='"+dateValue+"'";
//            Expression<Date> e3 = r1.get("aOExpDt");
//            p5 = cb.and(cb.lessThan(e3, dateValue));
//        }
//        q1.where (p1, p2, p4, p5);
//        results  = em.createQuery(q1).getResultList();
//        return results ;
//    }
   
}
