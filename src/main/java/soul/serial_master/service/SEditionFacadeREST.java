/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master.service;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Form;
import soul.serial_master.SEdition;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.serial_master.sedition")
public class SEditionFacadeREST extends AbstractFacade<SEdition> {

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    SEdition editionType = new SEdition();
    int count;
    String output;

    public SEditionFacadeREST() {
        super(SEdition.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(SEdition entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(SEdition entity) {
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
    public SEdition find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SEdition> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SEdition> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    // added manually
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<SEdition> retrieveAll() {
        List<SEdition> getAll = null;
        getAll = findAll();
        return getAll;
    }
  
    @POST
    @Path("createOrUpdateEdition")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrUpdateEdition(@FormParam("operation") String operation,
            @FormParam("code") String code, @FormParam("description") String description) {
        if (operation.equals("Create")) {
            editionType.setSEdType(code);
            editionType.setSEdName(description);
            count = Integer.parseInt(countREST());
            create(editionType);
            if (count == Integer.parseInt(countREST())) {
                output = "Someting went wrong Edition record is not created";
            } else {
                output = "Edition record created.";
            }
        }
        //response.sendRedirect("jsp/edition.jsp");
        if (operation.equals("Update")) {
            try {
                editionType = find(code);
            } catch (NullPointerException e) {
                return "Invalid code.";
            }

            editionType.setSEdName(description);
            edit(editionType);
            output = "Edition record updated.";
        }
        return output;
    }

    @DELETE
    @Path("deleteEdition/{code}")
    @Produces("text/plain")
    public String deleteEdition(@PathParam("code") String code) {
        count = Integer.parseInt(countREST());
        try {
            remove(code);
        } catch (IllegalArgumentException d) {
            return "Invalid Edition code, or Edition with code " + code + " does not exist";
        }
        if (count == Integer.parseInt(countREST())) {
            output = "Someting went wrong record is not deleted!";
        } else {
            output = "Edition Record Deleted";
        }
        return output;
    }

}
