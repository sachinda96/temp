/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import soul.circulation.IllReqprocdmp;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.illreqprocdmp")
public class IllReqprocdmpFacadeREST extends AbstractFacade<IllReqprocdmp> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public IllReqprocdmpFacadeREST() {
        super(IllReqprocdmp.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(IllReqprocdmp entity) {
        super.create(entity);
    }
    
    @POST
    @Path("createAndGet")
    @Override
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public IllReqprocdmp createAndGet(IllReqprocdmp entity) {
        return super.createAndGet(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(IllReqprocdmp entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public IllReqprocdmp find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<IllReqprocdmp> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<IllReqprocdmp> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<IllReqprocdmp> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        switch(query)
        {
          
            case "findBySendDtBetween": try {
                                            valueList.add(dateFormat.parse(valueString[0]));
                                            valueList.add(dateFormat.parse(valueString[1]));
                                        } catch (ParseException ex) {
                                            Logger.getLogger(IllReqprocdmpFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                            break;
            default:    valueList.add(valueString[0]);
        }
        return super.findBy("IllReqprocdmp."+query, valueList);
    }
    
}
