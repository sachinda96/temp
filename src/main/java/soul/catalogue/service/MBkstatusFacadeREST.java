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
import javax.ws.rs.QueryParam;
import soul.catalogue.MBkstatus;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.mbkstatus")
public class MBkstatusFacadeREST extends AbstractFacade<MBkstatus> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public MBkstatusFacadeREST() {
        super(MBkstatus.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(MBkstatus entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MBkstatus entity) {
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
    public MBkstatus find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MBkstatus> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MBkstatus> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
   // public List<MBkstatus> retrieveAll(@QueryParam("accept") String accept,@QueryParam("form") String form)
    public List<MBkstatus> retrieveAll()        
    {
        List<MBkstatus> getAll = null;
        getAll = findAll();
        return getAll;
    }
    
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<MBkstatus> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
            case "findByBkIssueStat":    valueList.add(valueString[0]);
                                        break;
        }
        return super.findBy("MBkstatus."+query, valueList);
    }
}
