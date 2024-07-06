/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue.service;

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
import javax.ws.rs.core.PathSegment;
import soul.catalogue.Rpttemplate;
import soul.catalogue.RpttemplatePK;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.rpttemplate")
public class RpttemplateFacadeREST extends AbstractFacade<Rpttemplate> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    private RpttemplatePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;rptTempID=rptTempIDValue;rptSbrptName=rptSbrptNameValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.catalogue.RpttemplatePK key = new soul.catalogue.RpttemplatePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> rptTempID = map.get("rptTempID");
        if (rptTempID != null && !rptTempID.isEmpty()) {
            key.setRptTempID(rptTempID.get(0));
        }
        java.util.List<String> rptSbrptName = map.get("rptSbrptName");
        if (rptSbrptName != null && !rptSbrptName.isEmpty()) {
            key.setRptSbrptName(rptSbrptName.get(0));
        }
        return key;
    }

    public RpttemplateFacadeREST() {
        super(Rpttemplate.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Rpttemplate entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Rpttemplate entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.catalogue.RpttemplatePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Rpttemplate find(@PathParam("id") PathSegment id) {
        soul.catalogue.RpttemplatePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Rpttemplate> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Rpttemplate> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @DELETE
    @Path("removeByTempId/{tempId}")
    public String removeByTempId(@PathParam("tempId") String tempId)
    {
      List<Object> valueList = new ArrayList<>();
      valueList.add(tempId);
      removeBy("Rpttemplate.removeByRptTempID", valueList);
      return "template deleted successfully";
    }
    
   @GET
   @Path("getRptByTempId/{templateId}")
   public List<Rpttemplate>  getRptByTempId(@PathParam("templateId") String templateId )
   {
       List<Rpttemplate> getRptDetails = null;
       List<Object> valueList = new ArrayList<>();
       valueList.add(templateId);
       getRptDetails = findBy("Rpttemplate.findByRptTempID", valueList);
       return getRptDetails;
   }
}
