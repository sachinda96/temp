/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master.service;

import java.util.ArrayList;
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
import soul.general_master.Languagemaster;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.languagemaster")
public class LanguagemasterFacadeREST extends AbstractFacade<Languagemaster> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    Languagemaster language = new Languagemaster();
    int count;
    String output;
    
    public LanguagemasterFacadeREST() {
        super(Languagemaster.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Languagemaster entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Languagemaster entity) {
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
    public Languagemaster find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Languagemaster> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Languagemaster> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<Languagemaster> retrieveAll()
    {
        List<Languagemaster> getAll = null;
            getAll = findAll();
        return getAll;
    }
  
    @POST
    @Path("createOrSave")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrSave(Form form,@FormParam("operation") String operation)
    {
        if(operation.equals("Create"))
        {
           language.setCode(form.asMap().getFirst("code"));
           language.setBibLanguage(form.asMap().getFirst("language"));
           count = Integer.parseInt(countREST());
           create(language);
           if(count == Integer.parseInt(countREST()))
           {
               output = "Someting went wrong Language is not inserted!";
           }
           else
           {
                 output = "Language inserted in database.";
           }
         }
        //response.sendRedirect("language.jsp");
        if(operation.equals("Save"))
        {
            language = find(form.asMap().getFirst("code"));
            language.setBibLanguage(form.asMap().getFirst("language"));
            edit(language);
            output = "Language information updated.";
        }
        return output;
  }
                                   
      
                                    
    @DELETE
    @Path("deleteLanguage/{code}")
    @Produces("text/plain")
    public String deleteLanguage(@PathParam("code") String code)
    {
        count = Integer.parseInt(countREST());
        remove(code);
        if(count == Integer.parseInt(countREST()))
        {
           output = "Someting went wrong language is not deleted!";
        }
        else
        {
            output = "Language record deleted!!!";
        }
        return output;
     }
    
}
