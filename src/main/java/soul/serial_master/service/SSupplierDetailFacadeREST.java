/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.StringJoiner;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import soul.acquisition.suggestions.ARequest;
import soul.serialControl.SMst;
import soul.serialControl.service.SMstFacadeREST;
import soul.serial_master.SSupplierDetail;
import soul.serialControl.SSubscription;
import soul.serialControl.service.SSubscriptionFacadeREST;
import soul.serial_master.SSupplier;
import soul.serial_master.service.SSupplierFacadeREST;
import soul.serial_master.SBinding;
import soul.serial_master.service.SBindingFacadeREST;
/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.serial_master.ssupplierdetail")
public class SSupplierDetailFacadeREST extends AbstractFacade<SSupplierDetail> {
    @EJB
    private SMstFacadeREST sMstFacadeREST;
    @EJB
    private SBindingFacadeREST sBindingFacadeREST;
    @EJB
    private SSupplierFacadeREST sSupplierFacadeREST;
    @EJB
    private SSubscriptionFacadeREST sSubscriptionFacadeREST;
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    
    String output = null;
    String vendorCode = null;
    SSupplierDetail supplierDetail;
    SSupplier supplier; 
    int count;

    public SSupplierDetailFacadeREST() {
        super(SSupplierDetail.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(SSupplierDetail entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(SSupplierDetail entity) {
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
    public SSupplierDetail find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
            case "findByASActAndASType":    valueList.add(valueString[0]);
                                            valueList.add(valueString[1]);
                                            break;
            
            case "findByASCd":    valueList.add(valueString[0]);
                                            break;
            case "findByASName":    valueList.add(valueString[0]+"%");
                                            break;
        }
        return super.findBy("SSupplierDetail."+query, valueList);
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
   
    ////used in schedule generration       
    @GET
    @Path("retrieveAllS/{code}")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> retrieveAllS(@PathParam("code") String code)
    {
        List<SSupplierDetail> getAll = null;
        getAll = findBy("findByASCd",code);
        return getAll;
    }
    
    ////used in schedule generration       
    @GET
    @Path("getCountryDetails/{name}")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> getCountryDetails(@PathParam("name") String name)
    {
        List<SSupplierDetail> getAll = null;
        getAll = findBy("findByASName",name);
        return getAll;
    }
    
    
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> retrieveAll()
    {
        List<SSupplierDetail> getAll = null;
        ArrayList<String> cities = new ArrayList<String>();
        ArrayList<String> status = new ArrayList<String>();
        getAll = findAll();
        
        //this block is used for retrieving the lsit of cities and status for the vendor report service
        for(int i=0; i<getAll.size(); i++){
            cities.add(i, getAll.get(i).getASCity());
            status.add(i, getAll.get(i).getASAct());
        }
        HashSet<String> uniqueCities = new HashSet<>(cities);
        HashSet<String> uniqueStatus = new HashSet<>(status);
        System.out.println("Cities: "+uniqueCities);
        System.out.println("Status: "+uniqueStatus);
        //this block ends here, and this particular services is being used in all other serivecs independently over this blocks
        
        return getAll;
    }
    
    @GET
    @Path("getCode/{nameCode}")
    @Produces({"text/plain"})
   // public String getCode(@QueryParam("accept") String accept,@QueryParam("nameCode") String nameCode)
    public String getCode(@PathParam("nameCode") String nameCode)        
    {
        // request.setAttribute("operation", "GetCode");
       //  String nameCode = request.getParameter("nameCode");
         String code=null;
         for(int i=1 ; i<999 ; i++)
         {
             code = null;
             if(i<10){
                 code = nameCode+"00"+i;
             }
             else if(i>=10 && i<100){
                 code = nameCode+"0"+i;
             }
             else
             {
                 code = nameCode+i;
             }

             SSupplierDetail s = find(code);
             if(s == null)
                 break;
             else
                 continue;
         }
      return code;
    }
   
    @DELETE
    @Path("deleteVendor/{code}")
    @Produces("text/plain")
    public String deleteVendor(@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{vendorName.pattern}") @PathParam("code") String vendorCode) throws ParseException {
        count = Integer.parseInt(countREST());
        try{
            remove(vendorCode);
            sSupplierFacadeREST.remove(vendorCode);
        }catch (IllegalArgumentException d){
            return "Invalid vendor code, or Vendor with code "+vendorCode+" does not exist";
        }
        
        int countofSMst = sMstFacadeREST.countByPubCode(vendorCode);
        int countofSubscription = sSubscriptionFacadeREST.countByPubCode(vendorCode);
        int countofBinding = sBindingFacadeREST.countByPubCode(vendorCode);

        if (countofSMst > 0 || countofSubscription > 0 || countofBinding > 0) {
            output = "Cannot delete vendor.";
        } else {
            if (count == Integer.parseInt(countREST())) {
                output = "Someting went wrong Record is not deleted!!";
            } else {
                output = "Vendor deleted";
            }
        }
        return output;
    }

    @POST
    @Path("createOrUpdateVendor")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrUpdateVendor(Form form, @FormParam("operation") String operation,
            @FormParam("type") String type, 
            @Pattern(regexp = "^[1-9][0-9]{5}$", message = "{pinCode.pattern}") @FormParam("pin") String pin,
            @Pattern(regexp = "^[a-zA-Z ]*$", message = "{vendorName.pattern}") @FormParam("name") String name, 
            @FormParam("city") String city,
            @FormParam("add1") String add1, 
            @FormParam("add2") String add2,
            @FormParam("add3") String add3, 
            @FormParam("contactPerson") String contactPerson,
            @FormParam("state") String state, 
            @FormParam("remark") String remark,
            @FormParam("country") String country, 
            @FormParam("searchCode") String searchCode,
            @Pattern(regexp = "[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}", message = "{email.pattern}") @FormParam("email") String email, 
            @FormParam("phone") String phone,
            @FormParam("fax") String fax, 
            @FormParam("telex") String telex,
            @FormParam("active") String active, 
            @FormParam("designation") String designation) {
        String code = name.substring(0,3).toUpperCase();
        List<SSupplier> getAll = null;
        getAll = sSupplierFacadeREST.findAll();
        ArrayList<String> allCodes = new ArrayList<String>();
        for (int i=0; i<getAll.size(); i++) {
            allCodes.add(i, getAll.get(i).getASCd());
        }
        
        System.out.println("codes: "+allCodes);
        ArrayList<Integer> codeIndex = new ArrayList<Integer>();
        for(int i=0; i<allCodes.size(); i++){
            codeIndex.add(i, Integer.parseInt(allCodes.get(i).substring(3,allCodes.get(i).length())));
        }
        System.out.println("codeIndex: "+codeIndex);
        Integer d = Collections.max(codeIndex);
        System.out.println("Max: "+d);
        code = code + ++d;
        System.out.println("NewCode: "+code);
        if (operation.equals("Create")) {            
            supplier = new SSupplier();
            supplier.setASCd(code);
            supplier.setASNm(name);
            supplier.setASType(type);
            sSupplierFacadeREST.create(supplier);
            
            supplierDetail = new SSupplierDetail();
            
            supplierDetail.setASAct(active);
            supplierDetail.setASName(name);
            supplierDetail.setASCd(code);
            supplierDetail.setASAdd1(add1);
            supplierDetail.setASAdd2(add2);
            supplierDetail.setASCity(add3);
            supplierDetail.setASPin(pin);
            supplierDetail.setASCity(city);
            supplierDetail.setASState(state);
            supplierDetail.setASCountry(country);
            supplierDetail.setASContPer(contactPerson);
            supplierDetail.setASDesg(designation);
            supplierDetail.setASEmail(email);
            supplierDetail.setASPhone(phone);
            supplierDetail.setASFax(fax);
            supplierDetail.setASTelex(telex);
            supplierDetail.setASRemark(remark);
            int count = Integer.parseInt(countREST());
            create(supplierDetail);
            if (Integer.parseInt(countREST()) == count) {
                output = "Someting went wrong, Vendor information is not insterted!!";
            } else {
                output = "Vendor record Inserted!!";
            }
        }
        if (operation.equals("Update")) {
            try{
                supplier = sSupplierFacadeREST.find(searchCode);
                supplierDetail = find(searchCode);
            }catch(NullPointerException e){
                return "Invalid code.";
            }
            
            supplier.setASNm(name);
            
            sSupplierFacadeREST.edit(supplier);
            
            supplierDetail.setASAct(active);
            supplierDetail.setASName(name);
            supplierDetail.setASAdd1(add1);
            supplierDetail.setASAdd2(add2);
            supplierDetail.setASCity(add3);
            supplierDetail.setASPin(pin);
            supplierDetail.setASCity(city);
            supplierDetail.setASState(state);
            supplierDetail.setASCountry(country);
            supplierDetail.setASContPer(contactPerson);
            supplierDetail.setASDesg(designation);
            supplierDetail.setASEmail(email);
            supplierDetail.setASPhone(phone);
            supplierDetail.setASFax(fax);
            supplierDetail.setASTelex(telex);
            supplierDetail.setASRemark(remark);
            int count = Integer.parseInt(countREST());
            edit(supplierDetail);
            output = "Vendor details updated.";
        }
        return output;
    }
        
    
    //suppliers for dropdown, in check-In -> Remainder 
    @GET
    @Path("allSuppliersForCheckInReminder")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> allSuppliersForCheckInReminder() {
        String q = "";  
        List<SSupplierDetail> result = new ArrayList<>();
        Query query;

        List<SSubscription> getAll = null;
        getAll = sSubscriptionFacadeREST.findBy("getSPubsup", "null");
        System.out.println("getaLL" + getAll);

        StringJoiner joiner = new StringJoiner("','");
        for (SSubscription get : getAll) {
            joiner.add(get.getSSupCd());
        }

        System.out.println("'" + joiner + "'");

        q = "select a_s_name,a_s_cd from s_supplier_detail where a_s_cd in ('"+joiner+"') ";
        query = getEntityManager().createNativeQuery(q, SSupplierDetail.class);
        result = (List<SSupplierDetail>) query.getResultList();
        return result;
    }
    
    //publishers for dropdown, in check-In -> Remainder 
    @GET
    @Path("allPublishersForCheckInReminder")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> allPublishersForCheckInReminder() {
        String q = "";
        List<SSupplierDetail> result = new ArrayList<>();
        Query query;

        List<SMst> getAll = null;
        getAll = sMstFacadeREST.findBy("getSPubCd", "null");
        System.out.println("getaLL" + getAll);

        StringJoiner joiner = new StringJoiner("','");
        for (SMst get : getAll) {
            joiner.add(get.getSPubCd());
        }

        System.out.println("'" + joiner + "'");

        q = "select a_s_name,a_s_cd from s_supplier_detail where a_s_cd in ('"+joiner+"') ";
        query = getEntityManager().createNativeQuery(q, SSupplierDetail.class);
        result = (List<SSupplierDetail>) query.getResultList();
        return result;
    }
    
    //all vendors -> report
    //vendor report by search parameters without filering
    @GET
    @Path("allVendors")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> allVendors() {
        String q = "";
        List<SSupplierDetail> result = new ArrayList<>();
        Query query;

        q = "SELECT s_supplier_detail.a_s_cd, s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1, \n"
                + "s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3,s_supplier_detail.a_s_cont_per, s_supplier_detail.a_s_act \n"
                + "FROM s_supplier, s_supplier_detail WHERE s_supplier.a_s_cd = s_supplier_detail.a_s_cd";
        query = getEntityManager().createNativeQuery(q, SSupplierDetail.class);
        result = (List<SSupplierDetail>) query.getResultList();
        return result;
    }
    
    //vendor report by search parameters without filering
    @GET
    @Path("vendorReport/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> vendorReport(@PathParam("Paramname") String Paramname,
            @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<SSupplierDetail> result = new ArrayList<>();
        Query query;
        switch (Paramname) {

            case "byStatus":
                q = "SELECT s_supplier_detail.a_s_cd, s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1, \n"
                        + "s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3,s_supplier_detail.a_s_cont_per, s_supplier_detail.a_s_act \n"
                        + "FROM s_supplier, s_supplier_detail WHERE s_supplier.a_s_cd = s_supplier_detail.a_s_cd and a_s_act = '" + Paramvalue + "' ";
                break;

            case "byCity":
                q = "SELECT s_supplier_detail.a_s_cd, s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1, \n"
                        + "s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3,s_supplier_detail.a_s_cont_per, s_supplier_detail.a_s_act \n"
                        + "FROM s_supplier, s_supplier_detail WHERE s_supplier.a_s_cd = s_supplier_detail.a_s_cd AND  a_s_city = '" + Paramvalue + "' ";
                break;
        }

        query = getEntityManager().createNativeQuery(q, SSupplierDetail.class);
        result = query.getResultList();
        return result;
    }
    
    
    //all vendors -> report
    //vendor report by search parameters with filering parameter
    @GET
    @Path("allVendorsByVendorType/{vendorType}")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> allVendorsByVendorType(@PathParam("vendorType") String vendorType) {
        String q = "";
        List<SSupplierDetail> result = new ArrayList<>();
        Query query;

        q = "SELECT s_supplier_detail.a_s_cd, s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1, \n"
                + "s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3,s_supplier_detail.a_s_cont_per, s_supplier_detail.a_s_act \n"
                + "FROM s_supplier, s_supplier_detail WHERE s_supplier.a_s_cd = s_supplier_detail.a_s_cd  and a_s_type = '" + vendorType + "' ";
        query = getEntityManager().createNativeQuery(q, SSupplierDetail.class);
        result = (List<SSupplierDetail>) query.getResultList();
        return result;
    }
    
    //all vendors -> report
    //vendor report by search parameters with filering parameter
    @GET
    @Path("allVendorsByVendorTypes/{vendorType1}/{vendorType2}")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> allVendorsByVendorTypes(@PathParam("vendorType1") String vendorType1,@PathParam("vendorType2") String vendorType2) {
        String q = "";
        List<SSupplierDetail> result = new ArrayList<>();
        Query query;

        q = "SELECT s_supplier_detail.a_s_cd, s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1, \n"
                + "s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3,s_supplier_detail.a_s_cont_per, s_supplier_detail.a_s_act \n"
                + "FROM s_supplier, s_supplier_detail WHERE s_supplier.a_s_cd = s_supplier_detail.a_s_cd  and (a_s_type ='" +vendorType1+ "' or a_s_type ='" +vendorType2+ "')";
        query = getEntityManager().createNativeQuery(q, SSupplierDetail.class);
        result = (List<SSupplierDetail>) query.getResultList();
        return result;
    }
    
    
    
    //vendor report by search parameters without filering
    @GET
    @Path("vendorReport/{Paramname}/{searchParam}/{vendorType1}/{vendorType2}")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> vendorReport(@PathParam("Paramname") String Paramname, @PathParam("searchParam") String searchParam,
            @PathParam("vendorType1") String vendorType1,@PathParam("vendorType2") String vendorType2) throws ParseException {
        String q = "";
        List<SSupplierDetail> result = new ArrayList<>();
        Query query;
        switch (Paramname) {

            case "byStatus":
                q = "SELECT s_supplier_detail.a_s_cd, s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1, \n"
                        + "s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3,s_supplier_detail.a_s_cont_per, s_supplier_detail.a_s_act \n"
                        + "FROM s_supplier, s_supplier_detail WHERE s_supplier.a_s_cd = s_supplier_detail.a_s_cd and a_s_act = '" + searchParam + "'  and (a_s_type = '" + vendorType1 + "' or a_s_type = '" + vendorType2 + "') ";
                break;

            case "byCity":
                q = "SELECT s_supplier_detail.a_s_cd, s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1, \n"
                        + "s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3,s_supplier_detail.a_s_cont_per, s_supplier_detail.a_s_act \n"
                        + "FROM s_supplier, s_supplier_detail WHERE s_supplier.a_s_cd = s_supplier_detail.a_s_cd AND  a_s_city = '" + searchParam + "'  and (a_s_type = '" + vendorType1 + "' or a_s_type = '" + vendorType2 + "') ";
                break;
        }

        query = getEntityManager().createNativeQuery(q, SSupplierDetail.class);
        result = query.getResultList();
        return result;
    }
    
    
    //vendor report by search parameters with filering and single vendor parameter
    @GET
    @Path("vendorReports/{Paramname}/{searchParam}/{vendorType}")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> vendorReports(@PathParam("Paramname") String Paramname,
            @PathParam("searchParam") String searchParam, @PathParam("vendorType") String vendorType) throws ParseException {
        String q = "";
        List<SSupplierDetail> result = new ArrayList<>();
        Query query;
        switch (Paramname) {

            case "byStatusAndVendorType":
                q = "SELECT s_supplier_detail.a_s_cd, s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1, \n"
                        + "s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3,s_supplier_detail.a_s_cont_per, s_supplier_detail.a_s_act \n"
                        + "FROM s_supplier, s_supplier_detail WHERE s_supplier.a_s_cd = s_supplier_detail.a_s_cd and a_s_act = '" + searchParam + "' and (a_s_type = '" + vendorType + "') ";
                break;

            case "byCityAndVendorType":
                q = "SELECT s_supplier_detail.a_s_cd, s_supplier_detail.a_s_name, s_supplier_detail.a_s_add1, \n"
                        + "s_supplier_detail.a_s_add2, s_supplier_detail.a_s_add3,s_supplier_detail.a_s_cont_per, s_supplier_detail.a_s_act \n"
                        + "FROM s_supplier, s_supplier_detail WHERE s_supplier.a_s_cd = s_supplier_detail.a_s_cd AND  a_s_city = '" + searchParam + "' and (a_s_type = '" + vendorType + "') ";
                break;
        }

        query = getEntityManager().createNativeQuery(q, SSupplierDetail.class);
        result = query.getResultList();
        return result;
    }
    
    @GET
    @Path("retrieveAllSupplier")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> retrieveAllSupplier()
    {
        String q = "";
        List<SSupplierDetail> activeSuppliers = new ArrayList<>();
        Query query;
        List<SSupplier> suppliers = new ArrayList<>();
        suppliers = sSupplierFacadeREST.findBy("findByASType", "Y");
        StringJoiner joiner = new StringJoiner("','");
        for (SSupplier get : suppliers) {
            joiner.add(get.getASCd());
        }
        q = "select * from s_supplier_detail where a_s_cd in ('"+joiner+"') and a_s_act = 'Active' ";
        query = getEntityManager().createNativeQuery(q, SSupplierDetail.class);
        activeSuppliers = (List<SSupplierDetail>) query.getResultList();
        return activeSuppliers;
     }

  
    @GET
    @Path("retrieveAllPublisher")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> retrieveAllPublisher()
    {
        String q = "";
        List<SSupplierDetail> activePublishers = new ArrayList<>();
        Query query;
        List<SSupplier> publishers = new ArrayList<>();
        publishers = sSupplierFacadeREST.findBy("findByASType", "X");
        StringJoiner joiner = new StringJoiner("','");
        for (SSupplier get : publishers) {
            joiner.add(get.getASCd());
        }
        q = "select * from s_supplier_detail where a_s_cd in ('"+joiner+"') and a_s_act = 'Active' ";
        query = getEntityManager().createNativeQuery(q, SSupplierDetail.class);
        activePublishers = (List<SSupplierDetail>) query.getResultList();
        return activePublishers;
    }
    
    @GET
    @Path("retrieveAllBinder")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> retrieveAllBinder()
    {
        String q = "";
        List<SSupplierDetail> activeBinders = new ArrayList<>();
        Query query;
        List<SSupplier> binders = new ArrayList<>();
        binders = sSupplierFacadeREST.findBy("findByASType", "Z");
        StringJoiner joiner = new StringJoiner("','");
        for (SSupplier get : binders) {
            joiner.add(get.getASCd());
        }
        q = "select * from s_supplier_detail where a_s_cd in ('"+joiner+"') and a_s_act = 'Active' ";
        query = getEntityManager().createNativeQuery(q, SSupplierDetail.class);
        activeBinders = (List<SSupplierDetail>) query.getResultList();
        return activeBinders;
    }
    
    
    //used in dropdown for binding report in serial control module, reports
    @GET
    @Path("retrieveAllBinders")
    @Produces({"application/xml", "application/json"})
    public List<Object> retrieveAllBinders()
    {
        String q = "";
        List<Object> activeBinders = new ArrayList<>();
        Query query;
        q = " select distinct a_s_nm from s_supplier,s_binding where a_s_cd=s_b_binder_cd ";
        query = getEntityManager().createNativeQuery(q);
        activeBinders =query.getResultList();
        return activeBinders; 
    }
    
    @GET
    @Path("getSupPubforInvoiceProcessing")
    @Produces({"application/xml", "application/json"})
    public List<SSupplierDetail> getSupPubforInvoiceProcessing() throws ParseException {
        String q = "";
        List<SSupplierDetail> result = new ArrayList<>();
        Query query;
        q = "select * from s_supplier_detail where a_s_cd in (select s_sup_cd\n" +
                " from s_subscription\n" +
                "  where s_order_no in (select s_order_no from s_mst where s_mst_status='O' or s_mst_status='S' and s_order_no != null) \n" +
                "  and s_order_no not in (select s_inv_order_no from s_sub_invdetail)) ";

        //List<Object> result;
        query = getEntityManager().createNativeQuery(q, SSupplierDetail.class);
        System.out.println("query: "+query);
        result = (List<SSupplierDetail>) query.getResultList();
        System.out.println("res: "+result);
        return result;
    }
    
    @GET
    @Path("getSupPubforPaymentProcess")
    @Produces({"application/xml", "application/json"})
    public List<Object> getSupPubforPaymentProcess() throws ParseException {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;
        q = "select distinct  a_s_nm, s_inv_party from s_supplier,s_sub_inv where a_s_cd = s_inv_party and  s_inv_remain > 0";

        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        System.out.println("query: "+query);
        result = (List<Object>) query.getResultList();
        System.out.println("res: "+result);
        return result;
    }
}

