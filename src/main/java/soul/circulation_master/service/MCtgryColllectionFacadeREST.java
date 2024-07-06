/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation_master.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import soul.circulation_master.MCtgry;
import soul.circulation_master.MCtgryColllection;
import soul.circulation_master.MCtgryColllectionPK;
import soul.system_setting.AccessionCtype;
import soul.system_setting.service.AccessionCtypeFacadeREST;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation_master.mctgrycolllection")
public class MCtgryColllectionFacadeREST extends AbstractFacade<MCtgryColllection> {
    @EJB
    private AccessionCtypeFacadeREST accessionCtypeFacadeREST;
    @EJB
    private MCtgryFacadeREST mCtgryFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
 
    int count;
    String categoryCode;
    String mediaCode;
    String collectionTypeCode;
    String output;
    AccessionCtypeFacadeREST collectionType;
   // MCtgry category;
 //   MCtgryMedia mCtgryMedia;
    MCtgryColllection mCtgryColllection;
    
    private MCtgryColllectionPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;ctgryCd=ctgryCdValue;ctCd=ctCdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.circulation_master.MCtgryColllectionPK key = new soul.circulation_master.MCtgryColllectionPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> ctgryCd = map.get("ctgryCd");
        if (ctgryCd != null && !ctgryCd.isEmpty()) {
            key.setCtgryCd(ctgryCd.get(0));
        }
        java.util.List<String> ctCd = map.get("ctCd");
        if (ctCd != null && !ctCd.isEmpty()) {
            key.setCtCd(ctCd.get(0));
        }
        return key;
    }

    public MCtgryColllectionFacadeREST() {
        super(MCtgryColllection.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(MCtgryColllection entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MCtgryColllection entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.circulation_master.MCtgryColllectionPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public MCtgryColllection find(@PathParam("id") PathSegment id) {
        soul.circulation_master.MCtgryColllectionPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MCtgryColllection> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MCtgryColllection> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<MCtgryColllection> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
            default:    valueList.add(valueString[0]);
            //findByCtgryCd    
        }
        return super.findBy("MCtgryColllection."+query, valueList);
    }
  
    
    public List<MCtgryColllection> findByCtgryCdAndCtCd(String ctgryCd,String ctCd)
    {
         List<Object> valueList = new ArrayList<>();
        
          valueList.add(ctgryCd);
          valueList.add(ctCd);   
        
        return super.findBy("MCtgryColllection.findByCtgryCdAndCtCd", valueList);
    }
    
    @GET
    @Path("retrieveAllCollectionPri")
    @Produces({"application/xml", "application/json"})
    public List<MCtgryColllection> retrieveAllCollectionPri(@PathParam("code") String code)
    {
        List<MCtgryColllection> getAll = findBy("findByCtgryCd", code);
        return getAll;
    }
   
    
    //Retrieve all collection previleges
    @GET
    @Path("retrieveAllCollectionPriveleges")
    @Produces({"application/xml", "application/json"})
    public List<MCtgryColllection> retrieveAllCollectionPriveleges()
    {
        List<MCtgryColllection> getAll = findAll();
        return getAll;
    }
    
    //Retrieve all collection previleges by category code as a parameter
    @GET
    @Path("retrieveAllCollectionPriveleges/{code}")
    @Produces({"application/xml", "application/json"})
    public List<MCtgryColllection> retrieveAllCollectionPriveleges(@PathParam("code") String code)
    {
        List<MCtgryColllection> getAll = findBy("findByCtgryCd", code);
        return getAll;
    }
    
    @POST
    @Path("addOrEditCollectionPriveleges")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String addOrEditCollectionPriveleges(Form form,@FormParam("operation") String operation)
    {
        if(operation.equals("AddCollectionPri"))
        {        
            mCtgryColllection = getMCtgryColllection(form);
            count = Integer.parseInt(countREST());
            create(mCtgryColllection);
            if(count == Integer.parseInt(countREST()))
            {
                output = "Soemthing went wrong, Material privilege is not added.";
            }
            else
            {
                output = "Added";
            }
        }
         if(operation.equals("EditCollectionPrivilege"))
        { 
            mCtgryColllection = getMCtgryColllection(form);
            edit(mCtgryColllection);
            output = "Updated";
        }
         return output;
    }
    
    public  MCtgryColllection getMCtgryColllection(Form form)
    {
        
        String categoryCode = form.asMap().getFirst("codep2");
        String collTypeCode = form.asMap().getFirst("colltype");
        MCtgryColllectionPK  mCtgryColllection_pk = new MCtgryColllectionPK(collTypeCode,categoryCode);
        //mCtgryColllection_pk.setCtCd(collTypeCode);
        //mCtgryColllection_pk.setCtgryCd(categoryCode);
       //MCtgryColllection ctgryColllection = find(MCtgryColllection.class, " ;ctgryCd="+categoryCode+";ctCd="+collTypeCode);
        MCtgryColllection ctgryColllection = find(mCtgryColllection_pk);
        if(ctgryColllection == null)
        {
            ctgryColllection = new MCtgryColllection();
            ctgryColllection.setAccessionCtype(accessionCtypeFacadeREST.find(collTypeCode));
            ctgryColllection.setMCtgry(mCtgryFacadeREST.find(categoryCode));
            MCtgryColllectionPK mCtgryColllectionPK = new MCtgryColllectionPK();
            mCtgryColllectionPK.setCtCd(collTypeCode);
            mCtgryColllectionPK.setCtgryCd(categoryCode);
            ctgryColllection.setMCtgryColllectionPK(mCtgryColllectionPK);
        }
        char on, op;
        String maxReserve = form.asMap().getFirst("maxitemres");
        on = form.asMap().getFirst("overnissue") != null ? 'Y' : 'N';
        op = form.asMap().getFirst("onpissue") != null ? 'Y' : 'N';
        
        ctgryColllection.setONIssueAllow(on);
        ctgryColllection.setOPIssueAllow(op);
        if(maxReserve != null)
            ctgryColllection.setMaxReserveColletype(Integer.parseInt(maxReserve));
        else
            ctgryColllection.setMaxReserveColletype(0);
        Date endtime;
        try {
            endtime = new SimpleDateFormat("HH:mm").parse(form.asMap().getFirst("endtime"));
            ctgryColllection.setOPTime(endtime);
        } catch (ParseException ex) {
            Logger.getLogger(MCtgryColllectionFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ctgryColllection;
    }
    
    
    //Service to add new collection with priveleges without js communication
    @POST
    @Path("addCollectionPriveleges")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String addCollectionPriveleges(Form form, @FormParam("collectionType") String collectionType,
            @FormParam("categoryCode") String categoryCode, @FormParam("ONIssueAllow") String ONIssueAllow,
            @FormParam("maxResColleType") String maxResColleType, @FormParam("OPIssueAllow") String OPIssueAllow,
            @FormParam("OPTime") String OPTime) {
        
        mCtgryColllection = getMCtgryColllectionForm(form);

        List<MCtgry> category = mCtgryFacadeREST.findAll();
        List<AccessionCtype> collect = accessionCtypeFacadeREST.findAll();
        category.spliterator();
        List<Object> categoryCodes = new ArrayList<>();
        List<Object> collectionCodes = new ArrayList<>();
        //System.out.println("Category: "+category);      
        //System.out.println("Collection: "+collect);
        for (int i = 0; i < category.size(); i++) {
            categoryCodes.add(category.get(i).getCtgryCd().toString());
        }
        for (int i = 0; i < collect.size(); i++) {
            collectionCodes.add(collect.get(i).getCid().toString());
        }

        System.out.println("Category codes: " + categoryCodes);
        System.out.println("Collection codes: " + collectionCodes);
        count = Integer.parseInt(countREST());
        
        if (categoryCodes.contains(categoryCode) && collectionCodes.contains(collectionType)) //System.out.println("True");
        {
            create(mCtgryColllection);
            System.out.println("Reached here.....");
            if (count == Integer.parseInt(countREST())) {
                output = "Soemthing went wrong, Material privilege is not added. Or Collection is already added.";
            } else {
                output = "Added";
            }
        } else {
            output = "Invalid Category or Collection code.";
        }
        return output;
    }
    
    
    //Service to edit collection with priveleges without js communication
    @PUT
    @Path("editCollectionPriveleges")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String editCollectionPriveleges(Form form, @FormParam("collectionType") String collectionType,
            @FormParam("categoryCode") String categoryCode, @FormParam("ONIssueAllow") String ONIssueAllow,
            @FormParam("maxResColleType") String maxResColleType, @FormParam("OPIssueAllow") String OPIssueAllow,
            @FormParam("OPTime") String OPTime) {
        mCtgryColllection = getMCtgryColllectionForm(form);
        edit(mCtgryColllection);
        output = "Updated";
        return output;
    }
    
    
    public  MCtgryColllection getMCtgryColllectionForm(Form form)
    {
        
        String categoryCode = form.asMap().getFirst("categoryCode");
        String collTypeCode = form.asMap().getFirst("collectionType");
        MCtgryColllectionPK  mCtgryColllection_pk = new MCtgryColllectionPK();
        mCtgryColllection_pk.setCtCd(collTypeCode);
        mCtgryColllection_pk.setCtgryCd(categoryCode);
       //MCtgryColllection ctgryColllection = find(MCtgryColllection.class, " ;ctgryCd="+categoryCode+";ctCd="+collTypeCode);
        MCtgryColllection ctgryColllection = find(mCtgryColllection_pk);
        if(ctgryColllection == null)
        {
            ctgryColllection = new MCtgryColllection();
            ctgryColllection.setAccessionCtype(accessionCtypeFacadeREST.find(collTypeCode));
            ctgryColllection.setMCtgry(mCtgryFacadeREST.find(categoryCode));
            MCtgryColllectionPK mCtgryColllectionPK = new MCtgryColllectionPK();
            mCtgryColllectionPK.setCtCd(collTypeCode);
            mCtgryColllectionPK.setCtgryCd(categoryCode);
            ctgryColllection.setMCtgryColllectionPK(mCtgryColllectionPK);
        }
        char on, op;
        String maxReserve = form.asMap().getFirst("maxResColleType");
        on = form.asMap().getFirst("ONIssueAllow") != null ? 'Y' : 'N';
        op = form.asMap().getFirst("OPIssueAllow") != null ? 'Y' : 'N';
        
        ctgryColllection.setONIssueAllow(on);
        ctgryColllection.setOPIssueAllow(op);
        if(maxReserve != null)
            ctgryColllection.setMaxReserveColletype(Integer.parseInt(maxReserve));
        else
            ctgryColllection.setMaxReserveColletype(0);
        Date endtime;
        try {
            endtime = new SimpleDateFormat("HH:mm:ss").parse(form.asMap().getFirst("OPTime"));
            ctgryColllection.setOPTime(endtime);
        } catch (ParseException ex) {
            Logger.getLogger(MCtgryColllectionFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ctgryColllection;
    }
    
    
    //Modified to work without js communication
    @DELETE
    @Path("deleteCollectionPrivilege/{codep2}/{colltype}")
    @Produces("text/plain")
    public String deleteCollectionPrivilege(@PathParam("codep2") String categoryCode,@PathParam("colltype") String collectionTypeCode)
    {
        count = Integer.parseInt(countREST());
       // categoryCode = request.getParameter("codep2");
       // collectionTypeCode = request.getParameter("colltype");
        MCtgryColllectionPK  mCtgryColllection_pk = new MCtgryColllectionPK();
        mCtgryColllection_pk.setCtCd(collectionTypeCode);
        mCtgryColllection_pk.setCtgryCd(categoryCode);
        try{
            remove(find(mCtgryColllection_pk));
        }catch(IllegalArgumentException e){
            return "Soemthing went wrong, Collection Privilege is not deleted.";
        }
        
        if(count == Integer.parseInt(countREST()))
        {
            output = "Soemthing went wrong, Collection Privilege is not deleted.";
        }
        else
        {
            output = "Collection Deleted.";
        }
        return output;
    }
}
