/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue.service;

import java.util.List;
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
import soul.catalogue.Classificationscheme;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.classificationscheme")
public class ClassificationschemeFacadeREST extends AbstractFacade<Classificationscheme> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public ClassificationschemeFacadeREST() {
        super(Classificationscheme.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Classificationscheme entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Classificationscheme entity) {
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
    public Classificationscheme find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Classificationscheme> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Classificationscheme> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
//    @PUT
//    @Path("updateClassificationScheme/{newScheme}")
//    @Produces("text/plain")
//    public String updateClassificationScheme(@PathParam("newScheme") String newScheme)
//    {
//        Classificationscheme classScheme = null;
//        String oldScheme = findAll().get(0).getClsfnScheme();
//        classScheme = find(oldScheme);
//        super.remove(classScheme);
//        
//        classScheme.setClsfnScheme(newScheme);
//        super.create(classScheme);
//        return "classification scheme updated successfully";
//    }
    
     @POST
    @Path("updateClassificationScheme")
    public String updateClassificationScheme(@FormParam("newScheme") String newScheme)
    {
        Classificationscheme classScheme = findAll().get(0);
        remove(classScheme);
        Classificationscheme classSchemeNew = new Classificationscheme();
        classSchemeNew.setClsfnScheme(newScheme);
        create(classSchemeNew);
        return "classification scheme updated successfully";
    }
}