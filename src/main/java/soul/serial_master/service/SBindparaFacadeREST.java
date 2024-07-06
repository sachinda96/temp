/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
import soul.serial_master.SBindpara;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.serial_master.sbindpara")
public class SBindparaFacadeREST extends AbstractFacade<SBindpara> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    SBindpara bindType = new SBindpara();
    int count;
    String output;
    
    public SBindparaFacadeREST() {
        super(SBindpara.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(SBindpara entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(SBindpara entity) {
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
    public SBindpara find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SBindpara> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SBindpara> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
   // public List<SBindpara> retrieveAll(@QueryParam("accept") String accept, @QueryParam("form") String form) {
    public List<SBindpara> retrieveAll() {    
        List<SBindpara> getAll = null;
        getAll = findAll();
        return getAll;
    }

    @POST
    @Path("createOrUpdateBindingType")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrUpdateBindingType(@FormParam("operation") String operation,
            @FormParam("code") String code, @FormParam("description") String description) {
        if (operation.equals("Create")) {
            bindType.setSBindCd(code);
            bindType.setSBindDesc(description);
            count = Integer.parseInt(countREST());
            create(bindType);
            if (count == Integer.parseInt(countREST())) {
                output = "Someting went wrong Binding record is not created!";
            } else         {
                output = "Binding record is created.";
            }
        }
         if(operation.equals("Update"))
        { 
            try
            {
                bindType = find(code);
            }catch(NullPointerException e){
                return "Invalid code.";
            }
            
            bindType.setSBindDesc(description);
            edit(bindType);
            output = "Binding record is updated.";
        }
          return output;                    
     }
    
    @DELETE
    @Path("deleteBindingType/{code}")
    @Produces("text/plain")
    public String deleteBindingType(@PathParam("code") String code) {
        count = Integer.parseInt(countREST());
        try {
            remove(code);
        } catch (IllegalArgumentException d) {
            return "Invalid binding type code, or binding type with code " + code + " does not exist";
        }
        if (count == Integer.parseInt(countREST())) {
            output = "Someting went wrong record is not deleted!";
        } else {
            output = "Binding type deleted.";
        }
        return output;
    }
    
    //Binding type details from bindpara table
    @GET
    @Path("BindingType")
    @Produces({"application/xml", "application/json"})
    public List<SBindpara> BindingType() throws ParseException {
        String q = "";
        Query query;
        q = "select * from s_bindpara";
        List<SBindpara> result;
        query = getEntityManager().createNativeQuery(q, SBindpara.class);
        result = query.getResultList();
        return result;
    }
    
    //Binding details for commercial binding
    @GET
    @Path("getItemBindingDetails/{code}")
    @Produces({"application/xml", "application/json"})
    public List<SBindpara> getItemBindingDetails(@PathParam("code") String code) throws ParseException {
        List<SBindpara> getAll = findBy("getItemBindingDetails", code);
        return getAll;
    }
    
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<SBindpara> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        List<String> smstStatus = new ArrayList<>();

        switch (query) {
            case "getItemBindingDetails":
                valueList.add(valueString[0]+"%");
                break;
        }
        return super.findBy("SBindpara." + query, valueList);
    }
}
