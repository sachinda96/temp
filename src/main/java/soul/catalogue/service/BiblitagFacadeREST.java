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
import soul.catalogue.Biblitag;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.biblitag")
public class BiblitagFacadeREST extends AbstractFacade<Biblitag> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public BiblitagFacadeREST() {
        super(Biblitag.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Biblitag entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Biblitag entity) {
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
    public Biblitag find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Biblitag> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Biblitag> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<Biblitag> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueSting = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {   //used in controlJs , editControlJs to get data  by tagNo 
            case "findByTag":   valueList.add(valueSting[0]);
                                        break;
                     
            default:    valueList.add(valueSting[0]);
                        break;  
                       
        }
        return super.findBy("Biblitag."+query, valueList);
    }
    
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    //public List<Biblitag> retrieveAll(@QueryParam("accept") String accept,@QueryParam("form") String form)
    public List<Biblitag> retrieveAll()        
    {
        List<Biblitag> getAll = null;
        getAll = findAll();
        return getAll;
    }
}
