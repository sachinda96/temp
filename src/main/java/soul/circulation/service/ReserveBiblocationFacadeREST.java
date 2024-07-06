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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import soul.circulation.IssuedInformation;
import soul.circulation.ReserveBiblocation;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.reservebiblocation")
public class ReserveBiblocationFacadeREST extends AbstractFacade<ReserveBiblocation> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output;
    public ReserveBiblocationFacadeREST() {
        super(ReserveBiblocation.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(ReserveBiblocation entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(ReserveBiblocation entity) {
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
    public ReserveBiblocation find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<ReserveBiblocation> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<ReserveBiblocation> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    //Added mannually
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<ReserveBiblocation> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values)
    {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
            default:    valueList.add(valueString[0]);
            //used for findByTitle, findByMemCd, findByP852
        }
        return super.findBy("ReserveBiblocation."+query, valueList);
    }
    
     @GET
     @Path("bookReserveDetail/{accNo}")
     @Produces({"application/xml", "application/json"})
    // public List<ReserveBiblocation> bookReserveDetail(@QueryParam("accNo") String accNo)
     public List<ReserveBiblocation> bookReserveDetail(@PathParam("accNo") String accNo)        
     {
       return findBy("findByP852", accNo);
     }
     
     //used in transaction menu to get reserved bk details
     @GET
     @Path("memberReserveDetail/{memCd}")
     @Produces({"application/xml", "application/json"})
     public Response memberReserveDetail(@PathParam("memCd") String memCd)
     {
        Response.ResponseBuilder responseBuilder = Response.status(200);
        if(!memCd.equals(""))
        {
          List<ReserveBiblocation> issInfo = findBy("findByMemCd", memCd); 
          GenericEntity<List<ReserveBiblocation>> list = new GenericEntity<List<ReserveBiblocation>>(issInfo) {};
          responseBuilder.entity(list);
        }
        else
        {
            output = "Empty MemberCode, Please enter valid Member Code.";
            responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);  
        }
         return responseBuilder.build();
      }
  
}
