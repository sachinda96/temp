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
import soul.general_master.Libmaterials;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.libmaterials")
public class LibmaterialsFacadeREST extends AbstractFacade<Libmaterials> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    Libmaterials material = new Libmaterials();
    int count;
    String output;
    
    public LibmaterialsFacadeREST() {
        super(Libmaterials.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Libmaterials entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Libmaterials entity) {
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
    public Libmaterials find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Libmaterials> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Libmaterials> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("SingleResultBy/{namedQuery}/{attrValues}")
    @Produces({"application/xml", "application/json"})
    public Libmaterials findSingleResultBy(@PathParam("namedQuery") String query, @PathParam("attrValues") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findByDescription":   valueList.add(valueString[0]);
                                        break;
        }
        return super.findSingleResultBy("Libmaterials."+query, valueList);
    }
 
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<Libmaterials> retrieveAll()
    {
        List<Libmaterials> getAll = null;
        getAll = findAll();
        return getAll;
    }

    @POST
    @Path("createOrSave")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrSave(Form form, @FormParam("operation") String operation) {
        if (operation.equals("Create")) {
            System.out.println(form.asMap().getFirst("code"));
            material.setCode(form.asMap().getFirst("code"));
            material.setDescription(form.asMap().getFirst("description"));
            count = Integer.parseInt(countREST());
            create(material);
            if (count == Integer.parseInt(countREST())) {
                output = "Someting went wrong Physical Media is not inserted!";
            } else {
                output = "Physical Media inserted";
            }
        }
        if (operation.equals("Save")) {
            material = find(form.asMap().getFirst("code"));
            material.setDescription(form.asMap().getFirst("description"));
            edit(material);
            output = "Physical Media information updated";
        }
        return output;
    }
        
    @DELETE
    @Path("deletePhysicalMedia/{code}")
    @Produces("text/plain")
    public String deletePhysicalMedia(@PathParam("code") String code) {
        count = Integer.parseInt(countREST());
        remove(code);
        if (count == Integer.parseInt(countREST())) {
            output = "Someting went wrong record is not deleted!";
        } else {
            output = "OK";
        }
        return output;
    }
}
