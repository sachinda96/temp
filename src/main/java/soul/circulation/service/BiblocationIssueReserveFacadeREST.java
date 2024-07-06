/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

//import ExceptionService.DataException;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.util.List;
import javax.ejb.EJB;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import soul.catalogue.Location;
import soul.catalogue.service.LocationFacadeREST;
import soul.circulation.BiblocationIssueReserve;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.biblocationissuereserve")
public class BiblocationIssueReserveFacadeREST extends AbstractFacade<BiblocationIssueReserve> {
    @EJB 
    private LocationFacadeREST locationFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output;
    List<Location> locationList;
    
    public BiblocationIssueReserveFacadeREST() {
        super(BiblocationIssueReserve.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(BiblocationIssueReserve entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(BiblocationIssueReserve entity) {
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
    public BiblocationIssueReserve find(@PathParam("id") String id) {
        return super.findCacheByPass(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<BiblocationIssueReserve> findAll() {
        return super.findAllCacheByPass();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<BiblocationIssueReserve> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
        
    //Details of book by accession no
    // used in transaction js 
    @GET
    @Path("getBookDetail/{searchParameter}")
    @Produces({"application/xml", "application/json", "text/plain"})
    public Response getBookDetail(@PathParam("searchParameter") String accNo) {
        final ResponseBuilder responseBuilder = Response.status(200);
        if (!accNo.equals("")) {
            locationList = locationFacadeREST.findBy("findByP852", accNo);
            if (locationList.isEmpty()) {
                output = "Book not found with '" + accNo + "' Accession No. Please enter valid Accession No.";
                //throw new DataException("No book found with accession no : "+accNo);
                
                responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
                
            } else {
                responseBuilder.type(MediaType.APPLICATION_XML).entity(find(accNo));
            }
        } else {
            output = "Empty Accession No., Please enter valid Accession No.";
            responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
        }
        return responseBuilder.build();
    }
}
