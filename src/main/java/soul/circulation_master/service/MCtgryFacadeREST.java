/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation_master.service;

//import ExceptionService.DataException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static java.time.Clock.system;
import java.util.ArrayList;
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
import javax.ws.rs.core.Form;
import soul.circulation_master.MCtgry;
import soul.errorresponce.ValidationErrorlistfields;
import soul.errorresponse.service.ValidationException;
import soul.response.postdata;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation_master.mctgry")
public class MCtgryFacadeREST extends AbstractFacade<MCtgry> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    int count;
    String categoryCode;
    String mediaCode;
    String collectionTypeCode;
    String output;
    MCtgry category;
    postdata pd;
   // MCtgryMedia mCtgryMedia;
   // MCtgryColllection mCtgryColllection;
    
    public MCtgryFacadeREST() {
        super(MCtgry.class);
    }

    @POST
   // @Override
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public postdata createq(MCtgry entity) throws ValidationException {
        String rs;
        System.out.println("MCtgry entity : "+ entity.getCtgryCd() +" : "+entity.getCtgryEndDt().toString());
        // validation 
        List<ValidationErrorlistfields> exceptionlist = new ArrayList<>();
        MCtgry memctgry = find(entity.getCtgryCd());
        if(memctgry != null){
            exceptionlist.add(new ValidationErrorlistfields("Category is exsits in database","10","Category Code"));
        }
        if(exceptionlist.size()>0){
            throw new ValidationException("Duplicate data",exceptionlist);
        }
        super.create(entity);
        rs = "Category Code : "+entity.getCtgryCd() +" and Category Name "+entity.getCtgryDesc() + " inserted succesfully.";
        pd = new postdata("/webresources/soul.circulation_master.mctgry",rs);
        return pd;
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MCtgry entity) {
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
    public MCtgry find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MCtgry> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MCtgry> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
        //Added manually
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<MCtgry> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        switch (query) {
            case "findByCtgryDesc":
                valueList.add(valueString[0]);
                break;

        }
        return super.findBy("MCtgry." + query, valueList);
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
    @Path("retrieveAllCategory")
    @Produces({"application/xml", "application/json"})
    public List<MCtgry> retrieveAllCategory()
    {
        List<MCtgry> getAll = findAll();
        return getAll;
    }
    
    // added manually
    //Returns all gategories
    @GET
    @Path("MemberCategoryTypes")
    @Produces({"application/xml", "application/json"})
    public List<MCtgry> AllCategories()
    {
        List<MCtgry> getAll = findAll();
        return getAll;
    }
    
    
    //Modifeid to work without js communication
    @DELETE
    @Path("deleteCategory/{code}")
    @Produces("text/plain")
    public String deleteCategory(@PathParam("code") String code)
    {
        count = Integer.parseInt(countREST());
        try{
            remove(code);
        }catch(IllegalArgumentException e){
            output = "Soemthing went wrong, Category is not deleted.";
        }
        
        if(count == Integer.parseInt(countREST()))
        {
            output = "Soemthing went wrong, Category is not deleted.";
        }
        else
        {
            output = "Category Deleted";
        }
      return output;
    }
    
    @POST
    @Path("addOrEditCategory")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String addOrEditCategory(Form form,@FormParam("operation") String operation)
    {
        if(operation.equals("AddCategory"))
        {
            category = getCategory(form);
            count = Integer.parseInt(countREST());
            create(category);
            if(count == Integer.parseInt(countREST()))
            {
                output = "Soemthing went wrong, Category is not added.";
            }
            else
            {
                output = "Added";
            }
        }
        if(operation.equals("EditCategory"))
        {
            category = getCategory(form);
            edit(category);
            output = "Updated";
        }
         return output;
    }
    
     public  MCtgry getCategory(Form form)
    {
        MCtgry category = find(form.asMap().getFirst("code"));
        
        if(category == null)
        {
            category = new MCtgry();
            category.setCtgryCd(form.asMap().getFirst("code"));
        }
        category.setCtgryDesc(form.asMap().getFirst("cat"));
        category.setCtgryDuration(Integer.parseInt(form.asMap().getFirst("duration")));
        category.setCtgryCharges(new BigDecimal(form.asMap().getFirst("membrsipcharges")));
        category.setCtgryDeposit(new BigDecimal(form.asMap().getFirst("depositamt")));
        category.setMaxDue(new BigDecimal(form.asMap().getFirst("maxalloverdue")));
        category.setMaxBookAllow(Integer.parseInt(form.asMap().getFirst("maxallItems")));
        try {
            category.setCtgryEndDt(new SimpleDateFormat("yyyy-MM-dd").parse(form.asMap().getFirst("catenddate")));
            category.setLastOperdt(category.getCtgryEndDt());
        } catch (ParseException ex) {
            Logger.getLogger(MCtgryFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        category.setUserCd("Super User");           //Need to changed to dynamic value as per user logged in
        
        return category;
    }
     

    //Without js communication
    //Add new category 
    @POST
    @Path("AddCategory")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String AddCategory(Form form, @FormParam("categoryCd") String categoryCd, @FormParam("categoryDesc") String categoryDesc,
                                @FormParam("duration") String duration, @FormParam("charges") String charges,
                                @FormParam("depositAmount") String depositAmount, @FormParam("maxDueAmount") String maxDueAmount,
                                @FormParam("maxAllowedItems") String maxAllowedItems, @FormParam("categoryEndDate") String categoryEndDate)
    {
        String output = null;
        category = getCategoryForm(form);        
        /*if(category == null)
        {
            category = new MCtgry();
            category.setCtgryCd(form.asMap().getFirst("categoryCd"));
        }
        category.setCtgryDesc(form.asMap().getFirst("categoryDesc"));
        category.setCtgryDuration(Integer.parseInt(form.asMap().getFirst("duration")));
        category.setCtgryCharges(new BigDecimal(form.asMap().getFirst("charges")));
        category.setCtgryDeposit(new BigDecimal(form.asMap().getFirst("depositAmount")));
        category.setMaxDue(new BigDecimal(form.asMap().getFirst("maxDueAmount")));
        category.setMaxBookAllow(Integer.parseInt(form.asMap().getFirst("maxAllowedItems")));
        try {
            category.setCtgryEndDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(form.asMap().getFirst("categoryEndDate")));
            category.setLastOperdt(category.getCtgryEndDt());
        } catch (ParseException ex) {
            Logger.getLogger(MCtgryFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        category.setUserCd("Super User");*/
        count = Integer.parseInt(countREST());
        create(category);
            if (count == Integer.parseInt(countREST())) {
            output = "Soemthing went wrong, Category is not added.";
        } else {
            output = "Category Added.";
        }
        return output;
    }
    
    //Without js communication
    //update category details 
    @PUT
    @Path("EditCategory")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String EditCategory(Form form, @FormParam("categoryCd") String categoryCd, @FormParam("categoryDesc") String categoryDesc,
                                @FormParam("duration") String duration, @FormParam("charges") String charges,
                                @FormParam("depositAmount") String depositAmount, @FormParam("maxDueAmount") String maxDueAmount,
                                @FormParam("maxAllowedItems") String maxAllowedItems, @FormParam("categoryEndDate") String categoryEndDate)
    {
        String output = null;
        category = getCategoryForm(form);        
        /*MCtgry category = find(form.asMap().getFirst("categoryCd"));
        
        if(category == null)
        {
            category = new MCtgry();
            category.setCtgryCd(form.asMap().getFirst("categoryCd"));
        }
        category.setCtgryDesc(form.asMap().getFirst("categoryDesc"));
        category.setCtgryDuration(Integer.parseInt(form.asMap().getFirst("duration")));
        category.setCtgryCharges(new BigDecimal(form.asMap().getFirst("charges")));
        category.setCtgryDeposit(new BigDecimal(form.asMap().getFirst("depositAmount")));
        category.setMaxDue(new BigDecimal(form.asMap().getFirst("maxDueAmount")));
        category.setMaxBookAllow(Integer.parseInt(form.asMap().getFirst("maxAllowedItems")));
        try {
            category.setCtgryEndDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(form.asMap().getFirst("categoryEndDate")));
            category.setLastOperdt(category.getCtgryEndDt());
        } catch (ParseException ex) {
            Logger.getLogger(MCtgryFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        category.setUserCd("Super User");*/
        count = Integer.parseInt(countREST());
        edit(category);
        output = "Category Update Successfull.";
        return output;
    }
    
    public  MCtgry getCategoryForm(Form form)
    {
        MCtgry category = find(form.asMap().getFirst("categoryCd"));
        
        if(category == null)
        {
            category = new MCtgry();
            category.setCtgryCd(form.asMap().getFirst("categoryCd"));
        }
        category.setCtgryDesc(form.asMap().getFirst("categoryDesc"));
        category.setCtgryDuration(Integer.parseInt(form.asMap().getFirst("duration")));
        category.setCtgryCharges(new BigDecimal(form.asMap().getFirst("charges")));
        category.setCtgryDeposit(new BigDecimal(form.asMap().getFirst("depositAmount")));
        category.setMaxDue(new BigDecimal(form.asMap().getFirst("maxDueAmount")));
        category.setMaxBookAllow(Integer.parseInt(form.asMap().getFirst("maxAllowedItems")));
        try {
            category.setCtgryEndDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(form.asMap().getFirst("categoryEndDate")));
            category.setLastOperdt(category.getCtgryEndDt());
        } catch (ParseException ex) {
            Logger.getLogger(MCtgryFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        category.setUserCd("Super User");           //Need to changed to dynamic value as per user logged in
        
        return category;
    }
    
    @GET
    @Path("CategoryListing/{findBy}/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<Object> findDepartmentAndInstitute(@PathParam("findBy") String Paramname, @PathParam("searchParameter") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();

        Query query;
        switch (Paramname) {
            case "byCategoryCode":
                MCtgry ctgry = find(Paramvalue);
                if (ctgry == null) {
                   // throw new DataException("No category found with instCd : " + Paramvalue);
                }
                q = "SELECT m_ctgry.ctgry_cd, m_ctgry.ctgry_desc, m_ctgry.ctgry_duration, m_ctgry.ctgry_charges, m_ctgry.max_due, \n" +
                    " m_ctgry.ctgry_deposit, m_ctgry_media.ctgry_cd AS Expr1, m_ctgry_media.media_cd, m_ctgry_media.iss_period, m_ctgry_media.max_allowed, \n" +
                    " m_ctgry_media.fine_charges, m_ctgry_media.res_period, m_ctgry_media.fine_phase_1_prd, m_ctgry_media.fine_phase_2_prd, \n" +
                    " m_ctgry_media.fine_phase_1_charge, m_ctgry_media.fine_phase_2_charge, LibMaterials.Description as media_descr \n" +
                    " FROM m_ctgry, m_ctgry_media, LibMaterials  \n" +
                    " where (m_ctgry.ctgry_cd = m_ctgry_media.ctgry_cd) and m_ctgry_media.media_cd = LibMaterials.Code \n" +
                    " and m_ctgry.ctgry_cd = '" + Paramvalue + "'";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
    
    
    @GET
    @Path("CategoryListing")
    @Produces({"application/xml", "application/json"})
    public List<Object> findDepartmentAndInstitute() throws ParseException {
        String q = "";
        List<Object> result = new ArrayList<>();

        Query query;
        q = "SELECT m_ctgry.ctgry_cd, m_ctgry.ctgry_desc, m_ctgry.ctgry_duration, m_ctgry.ctgry_charges, m_ctgry.max_due, m_ctgry.ctgry_deposit, m_ctgry_media.ctgry_cd AS Expr1,  \n" +
            " m_ctgry_media.media_cd, m_ctgry_media.iss_period, m_ctgry_media.max_allowed, m_ctgry_media.fine_charges, m_ctgry_media.res_period,  m_ctgry_media.fine_phase_1_prd,\n" +
            " m_ctgry_media.fine_phase_2_prd, m_ctgry_media.fine_phase_1_charge, m_ctgry_media.fine_phase_2_charge,  LibMaterials.Description as media_descr \n" +
            " FROM m_ctgry, m_ctgry_media, LibMaterials  \n" +
            " where (m_ctgry.ctgry_cd = m_ctgry_media.ctgry_cd) and m_ctgry_media.media_cd = LibMaterials.Code";
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
    
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<MCtgry> retrieveAll()
    {
        List<MCtgry> getAll = null;
        getAll = findAll();
        return getAll;
    }    
}
