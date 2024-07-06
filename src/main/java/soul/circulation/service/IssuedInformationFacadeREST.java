/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import org.apache.jasper.tagplugins.jstl.ForEach;
import soul.circulation.IssuedInformation;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.issuedinformation")
public class IssuedInformationFacadeREST extends AbstractFacade<IssuedInformation> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output;
    public IssuedInformationFacadeREST() {
        super(IssuedInformation.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(IssuedInformation entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(IssuedInformation entity) {
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
    public IssuedInformation find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<IssuedInformation> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<IssuedInformation> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    //Get issued item list by Book title
    @GET
    @Path("Title/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<IssuedInformation> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values)
    {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
            case "findByTitle":    valueList.add(valueString[0]+"%");
                                            break;
            //used for findByTitle
            default:    valueList.add(valueString[0]);
                                            break;
        }
        return super.findBy("IssuedInformation."+query, valueList);
    }     
   
    //used in transaction menu to get issued book detail
    @GET
    @Path("issueDetail/{memCd}")
    @Produces({"application/xml", "application/json","text/plain"})
     public Response issueDetail(@PathParam("memCd") String memCd)
     { 
       Response.ResponseBuilder responseBuilder = Response.status(200);
       if(!memCd.equals(""))
       {
          List<IssuedInformation> issInfo = findBy("findByMemCd", memCd); 
          GenericEntity<List<IssuedInformation>> list = new GenericEntity<List<IssuedInformation>>(issInfo) {};
          responseBuilder.entity(list);
        }
       else
       {
           output = "Empty MemberCode, Please enter valid Member Code.";
           responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);                      
       }
        return responseBuilder.build();
     }
     
  
    
//    @GET
//    @Path("borrowerDetail")
//    @Produces({"application/xml", "application/json","text/plain"})
//    public IssuedInformation brrowerDetail(@QueryParam("accNo") String accNo)
//    {
//        return  find(accNo);
//    }
    
}

