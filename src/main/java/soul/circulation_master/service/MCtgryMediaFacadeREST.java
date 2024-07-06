/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation_master.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
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
import javax.ws.rs.core.PathSegment;
import soul.circulation_master.MCtgryMedia;
import soul.circulation_master.MCtgryMediaPK;
import soul.general_master.service.LibmaterialsFacadeREST;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation_master.mctgrymedia")
public class MCtgryMediaFacadeREST extends AbstractFacade<MCtgryMedia> {
    @EJB 
    private LibmaterialsFacadeREST libmaterialsFacadeREST;
    @EJB
    private MCtgryFacadeREST mCtgryFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    int count;
    String categoryCode;
    String mediaCode;
    String collectionTypeCode;
    String output;
   // MCtgry category;
    MCtgryMedia mCtgryMedia;
   // MCtgryColllection mCtgryColllection;
    
    private MCtgryMediaPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;ctgryCd=ctgryCdValue;mediaCd=mediaCdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.circulation_master.MCtgryMediaPK key = new soul.circulation_master.MCtgryMediaPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> ctgryCd = map.get("ctgryCd");
        if (ctgryCd != null && !ctgryCd.isEmpty()) {
            key.setCtgryCd(ctgryCd.get(0));
        }
        java.util.List<String> mediaCd = map.get("mediaCd");
        if (mediaCd != null && !mediaCd.isEmpty()) {
            key.setMediaCd(mediaCd.get(0));
        }
        return key;
    }

    public MCtgryMediaFacadeREST() {
        super(MCtgryMedia.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(MCtgryMedia entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MCtgryMedia entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.circulation_master.MCtgryMediaPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public MCtgryMedia find(@PathParam("id") PathSegment id) {
        soul.circulation_master.MCtgryMediaPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MCtgryMedia> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MCtgryMedia> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<MCtgryMedia> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
            case "findByCtgryCdAndMediaCd":
                        valueList.add(valueString[0]);
                        valueList.add(valueString[1]);
                        break;
            default:    valueList.add(valueString[0]);
            //used for findByCtgryCd
        }
        return super.findBy("MCtgryMedia."+query, valueList);
    }
    
    
    
    @GET
    @Path("retrieveAllMediaPri/{code}")
    @Produces({"application/xml", "application/json"})
    //public List<MCtgryMedia> retrieveAllMediaPri(@QueryParam("code") String code)
    public List<MCtgryMedia> retrieveAllMediaPri(@PathParam("code") String code)        
    {
        List<MCtgryMedia> getAll = findBy("findByCtgryCd", code);
        return getAll;
    }
    
 
    //Retrieves all media previleges
    @GET
    @Path("retrieveAllMediaPriveleges")
    @Produces({"application/xml", "application/json"})
    public List<MCtgryMedia> retrieveAllMediaPriveleges()
    {
        List<MCtgryMedia> getAll = findAll();
        return getAll;
    }
    
    //Retrieves all media previleges by category code as a parameter
    @GET
    @Path("retrieveAllMediaPriveleges/{code}")
    @Produces({"application/xml", "application/json"})
    public List<MCtgryMedia> retrieveAllMediaPriveleges(@PathParam("code") String code)
    {
        List<MCtgryMedia> getAll = findBy("findByCtgryCd", code);
        return getAll;
    }
    
    
    
    @POST
    @Path("addOrEditLibMatPriveleges")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String addOrEditLibMatPri(Form form,@FormParam("operation") String operation)
    {
        if(operation.equals("AddLibMatPrivileges"))
        {
            mCtgryMedia = getMCtgryMedia(form);
            count = Integer.parseInt(countREST());
            create(mCtgryMedia);
            if(count == Integer.parseInt(countREST()))
            {
                output = "Soemthing went wrong, Material privilege is not added.";
            }
            else
            {
                output = "Added";
            }
        }
         if(operation.equals("EditLibMatPrivileges"))
        {
            mCtgryMedia = getMCtgryMedia(form);
            edit(mCtgryMedia);
            output = "Updated";
        }
          return output; 
    }
    
      public MCtgryMedia getMCtgryMedia(Form form)
    {
        String categoryCode = form.asMap().getFirst("codep1");
        String mediaCode = form.asMap().getFirst("material");
        MCtgryMediaPK  mCtgryMedia_pk = new MCtgryMediaPK(categoryCode,mediaCode);
        //mCtgryMedia_pk.setCtgryCd(categoryCode);
       // mCtgryMedia_pk.setMediaCd(mediaCode);
       // MCtgryMedia mCtgryMedia = find(" ;ctgryCd="+categoryCode+";mediaCd="+mediaCode);
        MCtgryMedia mCtgryMedia = find(mCtgryMedia_pk);
        
        if(mCtgryMedia == null)
        {
            mCtgryMedia = new MCtgryMedia();
            mCtgryMedia.setLibmaterials(libmaterialsFacadeREST.find(mediaCode));
            mCtgryMedia.setMCtgry(mCtgryFacadeREST.find(categoryCode));
            MCtgryMediaPK mCtgryMediaPK = new MCtgryMediaPK();
            mCtgryMediaPK.setCtgryCd(categoryCode);
            mCtgryMediaPK.setMediaCd(mediaCode);
            mCtgryMedia.setMCtgryMediaPK(mCtgryMediaPK);
        }
        
        mCtgryMedia.setIssPeriod(Integer.parseInt(form.asMap().getFirst("isperiod")));
        mCtgryMedia.setMaxReserve(Integer.parseInt(form.asMap().getFirst("maxres")));
        mCtgryMedia.setMaxAllowed(Integer.parseInt(form.asMap().getFirst("maxall")));
        mCtgryMedia.setResPeriod(Integer.parseInt(form.asMap().getFirst("resperiod")));
        mCtgryMedia.setFinePhase1Prd(Integer.parseInt(form.asMap().getFirst("duration1")));
        mCtgryMedia.setFinePhase1Charge(new BigDecimal(form.asMap().getFirst("charges1")));
        mCtgryMedia.setFinePhase2Prd(Integer.parseInt(form.asMap().getFirst("duration2")));
        mCtgryMedia.setFinePhase2Charge(new BigDecimal(form.asMap().getFirst("charges2")));
        mCtgryMedia.setFineCharges(new BigDecimal(form.asMap().getFirst("dcharges")));
        
        return mCtgryMedia;
    }
    
    //Modified to work without js communication  
    @DELETE
    @Path("deleteLibMatPrivilege/{codep1}/{material}")
    @Produces("text/plain")
    public String deleteLibMatPrivilege(@PathParam("codep1") String categoryCode,@PathParam("material") String mediaCode)
    {
        count = Integer.parseInt(countREST());
       // categoryCode = request.getParameter("codep1");
      //  mediaCode = request.getParameter("material");
        MCtgryMediaPK  mCtgryMedia_pk = new MCtgryMediaPK();
        mCtgryMedia_pk.setCtgryCd(categoryCode);
        mCtgryMedia_pk.setMediaCd(mediaCode);
        try{
            remove(find(mCtgryMedia_pk));
        }catch(IllegalArgumentException e){
            return "Soemthing went wrong, Media Category is not deleted.";
        }
        if(count == Integer.parseInt(countREST()))
        {
            output = "Soemthing went wrong, Media Category is not deleted.";
        }
        else
        {
            output = "OK";
        }
        return output;
    }
    
}
