/*
 * To change this template, choose Tools | Templates
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
import soul.circulation.IllExtissueBiblocation;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.illextissuebiblocation")
public class IllExtissueBiblocationFacadeREST extends AbstractFacade<IllExtissueBiblocation> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public IllExtissueBiblocationFacadeREST() {
        super(IllExtissueBiblocation.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(IllExtissueBiblocation entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(IllExtissueBiblocation entity) {
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
    public IllExtissueBiblocation find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<IllExtissueBiblocation> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<IllExtissueBiblocation> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<IllExtissueBiblocation> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
          
            case "findByLibCdAndMaterial":  valueList.add(valueString[0]);
                                            valueList.add(valueString[1]);
                                            break;
            default:    valueList.add(valueString[0]);
                        break;
                        //used for 
        }
        return super.findBy("IllExtissueBiblocation."+query, valueList);
    }
    
}
