/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
import soul.general_master.ASupplierDetail;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.asupplierdetail")
public class ASupplierDetailFacadeREST extends AbstractFacade<ASupplierDetail> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    
    ASupplierDetail supplier = null;
    String output = null;
    String vendorCode = null;
    List<ASupplierDetail> supplierList = new ArrayList<>();  
    int count;   
    
    public ASupplierDetailFacadeREST() {
        super(ASupplierDetail.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(ASupplierDetail entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(ASupplierDetail entity) {
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
    public ASupplierDetail find(@PathParam("id") String id) {
        return super.find(id);
    }
    
    @GET
    @Path("by/{attribute}/{value}")
    @Produces({"application/xml", "application/json"})
    public List<ASupplierDetail> findBy(@PathParam("attribute") String attribute, @PathParam("value") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(attribute)
        {
            case "findByASStatus" : valueList.add(valueString[0]);
                                    break;
            default:   valueList.add(valueString[0]);
                        break;
        }
        return super.findBy("ASupplierDetail."+attribute, valueList);
    }   

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<ASupplierDetail> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<ASupplierDetail> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
  
        
    @GET
    @Path("getCode/{nameCode}")  
    @Produces({"text/plain"})
  //  public String getCode(@QueryParam("nameCode") String nameCode)
    public String getCode(@PathParam("nameCode") String nameCode)        
    {
    //p    request.setAttribute("operation", "GetCode");
    //     String nameCode = request.getParameter("nameCode");
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
            supplier = find(code);
            if(supplier == null)
            { break;}
            else
            { continue;}
        }
        //System.out.println("code............ "+code);
       return code;
    }
    
    @GET
    @Path("retrieveAll/{accept}/{form}/{page}/{rows}")
    @Produces({"application/xml", "application/json"})
//    public List<ASupplierDetail> retrieveAll(@QueryParam("accept") String accept,@QueryParam("form") String form,
//    @QueryParam("page") String page,@QueryParam("rows") String rows)
//    {
    public List<ASupplierDetail> retrieveAll(@PathParam("accept") String accept,@PathParam("form") String form,
    @PathParam("page") String page,@PathParam("rows") String rows)
    {    
        List<ASupplierDetail> getAll = null;
        if(accept.equals("XML"))
            {
                getAll = findAll();
                int totalPages;
                String currentPage = page;
                String totalRecords = countREST();
//                if(!totalRecords.equals("0"))
//                    totalPages = (int) Math.ceil((double)Integer.parseInt(totalRecords)/Integer.parseInt(rows));
//                else
//                { totalPages = 0;}
//                try {
//                   output = getAll;
//                } finally {            
//                    out.close();
//                }
            }
        return getAll;
    }
    
    @GET
    @Path("RetrieveAllSupplier")
    @Produces({"application/xml", "application/json"})
   // public List<ASupplierDetail> RetrieveAllSupplier(@QueryParam("accept") String accept)
    public List<ASupplierDetail> RetrieveAllSupplier()        
    {
        supplierList = findBy("findByASStatus", "Supplier");
        supplierList.addAll(findBy("findByASStatus", "Supplier-Publisher"));
        return supplierList;
    }
    
    @GET
    @Path("RetrieveAllPublisherList")
    @Produces({"application/xml", "application/json"})
  //  public List<ASupplierDetail> RetrieveAllPublisherList(@QueryParam("accept") String accept)
     public List<ASupplierDetail> RetrieveAllPublisherList()        
    {
       // List<ASupplierDetail>  publisherList;
        supplierList = findBy("findByASStatus", "Publisher");
        String codes="";
        String names="";
        for(ASupplierDetail sup: supplierList)
        {
            codes = codes+","+sup.getASCd();
            names = names+","+sup.getASName();
        }
     
        supplierList.addAll(findBy("findByASStatus", "Supplier-Publisher"));
        for(ASupplierDetail sup: supplierList)
        {
            codes = codes+","+sup.getASCd();
            names = names+","+sup.getASName();
        }
   
        return supplierList;
    }
    
    @GET
    @Path("RetrieveAllPublisher")
    @Produces("text/plain")
   // public String RetrieveAllPublisher(@QueryParam("accept") String accept)
    public String RetrieveAllPublisher()        
    {
        String  publishers = null;
        supplierList = findBy("findByASStatus", "Publisher");
        String codes="";
        String names="";
        for(ASupplierDetail sup: supplierList)
        {
            codes = codes+","+sup.getASCd();
            names = names+","+sup.getASName();
        }
     
        supplierList.addAll(findBy("findByASStatus", "Supplier-Publisher"));
        for(ASupplierDetail sup: supplierList)
        {
            codes = codes+","+sup.getASCd();
            names = names+","+sup.getASName();
        }
        if(codes.length()>0)
        {
           publishers = codes.subSequence(1, codes.length())+"|"+names.subSequence(1, names.length());
        }
        return publishers;
      
    }
    
    @DELETE
    @Path("deleteVendor/{code}")
    @Produces("text/plain")
    public String deleteVendor(@PathParam("code") String vendorCode)
    {
        count = Integer.parseInt(countREST());
        //vendorCode = request.getAttribute("vendorCode") != null? (String)request.getAttribute("vendorCode") :  request.getParameter("vendorCode");
        remove(vendorCode);
        if(count == Integer.parseInt(countREST()))
        {
            output = "Someting went wrong Record is not deleted!!";
        }
        else 
        {
            output = "OK";
        }
        return output;
        //request.setAttribute("output", output);
        //rd.forward(request, response);
    }
    
    @POST
    @Path("createOrSave")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrSave(Form form,@FormParam("operation") String operation)
    {
        if(operation.equals("Create"))
        {
            supplier = getSupplierDetail(form);
            int count = Integer.parseInt(countREST());
            create(supplier);
            if(Integer.parseInt(countREST()) == count)
            {
                output = "Someting went wrongm, Vendor is not insterted!!";
            }
            else
            {
                output = "Vendor record inserted!!";
            }
           // request.setAttribute("output", output);
           // out.println(output);
            //rd.forward(request, response);
            //response.sendRedirect("vendor.jsp");
         }
        if(operation.equals("Save"))
        {
            supplier = getSupplierDetail(form);
            edit(supplier);
            output = "Vendor Information updated!!";
          //  request.setAttribute("output", output);
            //out.println(output);
            //rd.forward(request, response);
            //response.sendRedirect("vendor.jsp");
        }
        return output;
    }
        
        public  ASupplierDetail getSupplierDetail(Form form)
    {
        ASupplierDetail supplier = new ASupplierDetail();
      
        supplier.setASAct(form.asMap().getFirst("status"));
        supplier.setASName(form.asMap().getFirst("vendorName"));
        supplier.setASCd(form.asMap().getFirst("vendorCode"));
        supplier.setASSpl(form.asMap().getFirst("specialization"));
        supplier.setASStatus(form.asMap().getFirst("vendorStatus"));
        supplier.setASAdd1(form.asMap().getFirst("address1"));
        supplier.setASAdd2(form.asMap().getFirst("address2"));
        supplier.setASCity(form.asMap().getFirst("city"));
        supplier.setASPin(form.asMap().getFirst("pin"));
        supplier.setASState(form.asMap().getFirst("state"));
        supplier.setASCountry(form.asMap().getFirst("country"));
        supplier.setASContPer(form.asMap().getFirst("contactPerson"));
        supplier.setASDesg(form.asMap().getFirst("designation"));
        supplier.setASEmail(form.asMap().getFirst("email"));
        supplier.setASPhone(form.asMap().getFirst("phone"));
        supplier.setASFax(form.asMap().getFirst("fax"));
        supplier.setASTelex(form.asMap().getFirst("telex"));
        supplier.setASRemark(form.asMap().getFirst("remarks"));
        supplier.setASCIndex(null);
                                
        return supplier;
    }
        
    //All binders from a_supplier table
    @GET
    @Path("AllBinders")
    @Produces({"application/xml", "application/json"})
    public List<Object> AllBinders() throws ParseException {
        String q = "";
        Query query;
        q = "select a_s_name from s_supplier_detail where a_s_type = 'Binder' ";
        List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
}
