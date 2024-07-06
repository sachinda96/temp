/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl.service;

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
import soul.serialControl.SMstInSchedule;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.serialcontrol.smstinschedule")
public class SMstInScheduleFacadeREST extends AbstractFacade<SMstInSchedule> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public SMstInScheduleFacadeREST() {
        super(SMstInSchedule.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(SMstInSchedule entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(SMstInSchedule entity) {
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
    public SMstInSchedule find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SMstInSchedule> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SMstInSchedule> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("retrieveAllModifySchedule/{accept}/{form}")
    @Produces({"application/xml", "application/json"})
   // public List<SMstInSchedule> retrieveAllModifySchedule(@QueryParam("accept") String accept,@QueryParam("form") String form)
    public List<SMstInSchedule> retrieveAllModifySchedule(@PathParam("accept") String accept,@PathParam("form") String form)        
    {
        List<SMstInSchedule> getAll = null;
        if(accept.equals("XML"))
        {
              if(form.equals("ModifySchedule"))
            {
                 getAll = findAll();
            }
        }
        return getAll;
    }
}
