/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.user_setting.service;

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
import soul.user_setting.Privilege;


/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.user_setting.privilege")
public class PrivilegeFacadeREST extends AbstractFacade<Privilege> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public PrivilegeFacadeREST() {
        super(Privilege.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Privilege entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Privilege entity) {
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
    public Privilege find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Privilege> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Privilege> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("retrieveAll/{accept}")
    @Produces({"application/xml", "application/json"})
   // public List<Privilege> retrieveAll(@QueryParam("accept") String accept,@QueryParam("form") String form)
     public List<Privilege> retrieveAll(@PathParam("accept") String accept)        
    {
      List<Privilege> getAll = null;
      if(accept.equals("XML"))
        {
            getAll = findAll();
        }
         return getAll;
    }
}
