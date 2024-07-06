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
import soul.catalogue.TMissingBiblocation;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.tmissingbiblocation")
public class TMissingBiblocationFacadeREST extends AbstractFacade<TMissingBiblocation> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public TMissingBiblocationFacadeREST() {
        super(TMissingBiblocation.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TMissingBiblocation entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TMissingBiblocation entity) {
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
    public TMissingBiblocation find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TMissingBiblocation> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TMissingBiblocation> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<TMissingBiblocation> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueSting = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findByBkAccnoAndMissing":     valueList.add(valueSting[0]);
                                                break;
            default:    valueList.add(valueSting[0]);
                        break;
        }
        return super.findBy("TMissingBiblocation."+query, valueList);
    }
    
    
    
}
