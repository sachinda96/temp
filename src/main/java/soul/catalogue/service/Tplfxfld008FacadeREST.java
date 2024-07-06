/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue.service;

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
import soul.catalogue.Tplfxfld008;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.tplfxfld008")
public class Tplfxfld008FacadeREST extends AbstractFacade<Tplfxfld008> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    Tplfxfld008 fixedfield = new Tplfxfld008();
    String getAll;
    List<Tplfxfld008> getfixedfieldList = new ArrayList<>();
    String output;
    int count;
    
    public Tplfxfld008FacadeREST() {
        super(Tplfxfld008.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Tplfxfld008 entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Tplfxfld008 entity) {
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
    public Tplfxfld008 find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Tplfxfld008> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Tplfxfld008> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
     ///manually added
    @GET
    @Path("count/by/{namedQuery}/{values}")
    @Produces("text/plain")
    public String countBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
            case "checkIfTemplateNameExists": valueList.add(valueString[0]);
                break;
            default:    valueList.add(valueString[0]);
            
        }
        return String.valueOf(super.countBy("Tplfxfld008."+query, valueList));
    }
   // added manually
    
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<Tplfxfld008> retrieveAll()
    {
        List<Tplfxfld008> getAll = null;
        getAll = findAll();
        return getAll;
    }
      
    @PUT
    @Path("updateFixFieldTemplate")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String updateFixFieldTemplate(Form form)
    {
        String fixedfieldName = form.asMap().getFirst("fixedfieldName");
        String fixedfieldValue = form.asMap().getFirst("fixedfieldValue");
        String fixedfieldId = form.asMap().getFirst("fixedfieldId");
        String fixedfieldTpofmtrl = form.asMap().getFirst("fixedfieldTpofmtrl");
            
        fixedfield.setTName(fixedfieldName);
        fixedfield.setTValue(fixedfieldValue.replace(",", ""));
        fixedfield.setIsDefault("NO");
        fixedfield.setId(Integer.parseInt(fixedfieldId));
        fixedfield.setTpofmtrl(fixedfieldTpofmtrl);
       // output = tplldrClient.countBy("checkIfTemplateNameExists", leaderName);
        edit(fixedfield);
        output = "updated Successfully";
        return output;
    }
    
    @POST
    @Path("createFixFieldTemplate")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createFixFieldTemplate(Form form)
    {
        String fixedfieldName = form.asMap().getFirst("fixedfieldName");
        String fixedfieldValue = form.asMap().getFirst("fixedfieldValue");
      //  String fixedfieldId = form.asMap().getFirst("fixedfieldId");
        String fixedfieldTpofmtrl = form.asMap().getFirst("fixedfieldTpofmtrl");
        
        fixedfield.setTName(fixedfieldName);
        fixedfield.setTValue(fixedfieldValue.replace(",", ""));
        fixedfield.setTpofmtrl(fixedfieldTpofmtrl);
        fixedfield.setIsDefault("NO");
              
        output = countBy("checkIfTemplateNameExists", fixedfieldName);
        if(output.contentEquals("0"))
         {
             String initialCount = countREST();
             create(fixedfield);
             if(Integer.parseInt(countREST()) > (Integer.parseInt(initialCount)))//check if count is incremented
                {
                    output = "Saved Successfully";
                }
             else
                {
                    output = "Data could not be saved";
                }
         }
         else
         {
             output = "Template name already exists..";
         }
      return output;
    }
    
    @DELETE
    @Path("deleteFixedField/{fixedfieldId}")
    @Produces("text/plain")
    public String deleteFixedField(@PathParam("fixedfieldId") String fixedfieldId)
    {
        remove(Integer.parseInt(fixedfieldId));
        output = "Deleted Successfully";
        return output;
    }
}
