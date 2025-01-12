/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master.service;

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
import soul.general_master.LetterFormats;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.letterformats")
public class LetterFormatsFacadeREST extends AbstractFacade<LetterFormats> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public LetterFormatsFacadeREST() {
        super(LetterFormats.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(LetterFormats entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(LetterFormats entity) {
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
    public LetterFormats find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<LetterFormats> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<LetterFormats> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<LetterFormats> retrieveAll() {
        return super.findAll();
    }
    
    @PUT
    @Path("updateLetterFormat")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String updateLetterFormat(@FormParam("letterName") String letterName,
    @FormParam("letterSubject") String letterSubject,@FormParam("letterFormat") String letterFormat) {
        
        LetterFormats letterFormats = find(letterName);
        letterFormats.setSubject(letterSubject);
        letterFormats.setLetterFormat(letterFormat);
        edit(letterFormats);
        return "Letter layout updated successfully";
    }
}
