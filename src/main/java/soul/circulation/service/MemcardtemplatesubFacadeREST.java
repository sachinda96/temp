/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import soul.circulation.Memcardtemplatesub;
import soul.circulation.MemcardtemplatesubPK;

/**
 *
 * @author admin
 */
@Stateless
@Path("soul.circulation.memcardtemplatesub")
public class MemcardtemplatesubFacadeREST extends AbstractFacade<Memcardtemplatesub> {

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    private MemcardtemplatesubPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;mrptTempID=mrptTempIDValue;mFieldName=mFieldNameValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.circulation.MemcardtemplatesubPK key = new soul.circulation.MemcardtemplatesubPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> mrptTempID = map.get("mrptTempID");
        if (mrptTempID != null && !mrptTempID.isEmpty()) {
            key.setMrptTempID(new java.lang.Integer(mrptTempID.get(0)));
        }
        java.util.List<String> mFieldName = map.get("mFieldName");
        if (mFieldName != null && !mFieldName.isEmpty()) {
            key.setMFieldName(mFieldName.get(0));
        }
        return key;
    }

    public MemcardtemplatesubFacadeREST() {
        super(Memcardtemplatesub.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Memcardtemplatesub entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Memcardtemplatesub entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.circulation.MemcardtemplatesubPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Memcardtemplatesub find(@PathParam("id") PathSegment id) {
        soul.circulation.MemcardtemplatesubPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memcardtemplatesub> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memcardtemplatesub> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    //Added Manually
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<Memcardtemplatesub> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        //List<String> inString = new ArrayList<>();
        List<String> inString = new ArrayList<>();

        switch (query) {
            case "findByMrptTempID":    valueList.add(Integer.parseInt(valueString[0]));
                        break;
            default:
                valueList.add(valueString[0]);
                break;

        }
        System.out.println("Memcardtemplatesub." + query + valueList);
        return super.findBy("Memcardtemplatesub." + query, valueList);
    }
    
    @GET
    @Path("GetTemplateNames")
    @Produces({"application/xml", "application/json"})
    public List<Memcardtemplatesub> GetTemplateNames()
    { 
        List<Memcardtemplatesub> getAll;
        getAll = findAll();
        return getAll;                                 
    }
    
    //This service returns all the templates by name
    @GET
    @Path("GetTemplateByName/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<Memcardtemplatesub> GetTemplateByName(@PathParam("searchParameter") String templateName) {
        List<Memcardtemplatesub> template = findBy("findByMFieldName", templateName);
        return template;
    }
    
     //This service returns all the templates by name
    @GET
    @Path("GetTemplateByID/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<Memcardtemplatesub> GetTemplateByID(@PathParam("searchParameter") String templateID) {
        List<Memcardtemplatesub> template = findBy("findByMrptTempID", templateID);
        return template;
    }
    
    @DELETE
    @Path("removeByTempId/{tempId}")
    public void removeByTempId(@PathParam("tempId") String tempId)
    {
      List<Object> valueList = new ArrayList<>();
      valueList.add(Integer.parseInt(tempId));
      removeBy("Memcardtemplatesub.removeByTempId", valueList);
    }
}
