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
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import soul.circulation.CtgryMediaIssueReserve;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.ctgrymediaissuereserve")
public class CtgryMediaIssueReserveFacadeREST extends AbstractFacade<CtgryMediaIssueReserve> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public CtgryMediaIssueReserveFacadeREST() {
        super(CtgryMediaIssueReserve.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(CtgryMediaIssueReserve entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(CtgryMediaIssueReserve entity) {
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
    public CtgryMediaIssueReserve find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<CtgryMediaIssueReserve> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<CtgryMediaIssueReserve> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<CtgryMediaIssueReserve> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values)
    {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {   
            case "findByCtgryCdAndMediaCd": valueList.add(valueString[0]);// used in issue process
                                            valueList.add(valueString[1]);
                                            break;
            default:    valueList.add(valueString[0]);
            //used for findByMemCd
        }
        return super.findByCacheByPass("CtgryMediaIssueReserve."+query, valueList);
    } 
    
   
   // used in transaction menu to get privilege details 
   @GET
   @Path("privilegeDetail/{memCd}")
   @Produces({"text/plain","application/xml", "application/json"})
   public Response privilegeDetail(@PathParam("memCd") String memCd)
   {   final Response.ResponseBuilder responseBuilder = Response.status(200);
       if(!memCd.equals(""))
       { 
          List<CtgryMediaIssueReserve> ctgrylist = findBy("findByMemCd", memCd); 
          GenericEntity<List<CtgryMediaIssueReserve>> list = new GenericEntity<List<CtgryMediaIssueReserve>>(ctgrylist) {};
          responseBuilder.entity(list);
        }
       else
       {
           String output = "Empty MemberCode, Please enter valid Member Code.";
           responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
       }
       return responseBuilder.build();
   } 
}
