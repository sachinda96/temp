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
import soul.catalogue.Tplldr;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.tplldr")
public class TplldrFacadeREST extends AbstractFacade<Tplldr> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    Tplldr leader = new Tplldr();
    String getAll;
    List<Tplldr> getLeaderList = new ArrayList<>();
    String output;
    int count;
    
    public TplldrFacadeREST() {
        super(Tplldr.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Tplldr entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Tplldr entity) {
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
    public Tplldr find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Tplldr> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Tplldr> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
        return String.valueOf(super.countBy("Tplldr."+query, valueList));
    }
   
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<Tplldr> retrieveAll()
    {
        List<Tplldr> getAll = null;
        getAll = findAll();
        return getAll;
    }
    
    @POST
    @Path("createLeaderTemplate")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createLeaderTemplate(Form form)
    {   
        String leaderName = form.asMap().getFirst("leaderName");
        String leaderValue = form.asMap().getFirst("leaderValue");
       // String leaderId = form.asMap().getFirst("leaderId");
        leader.setTName(leaderName);
        leader.setTValue(leaderValue.replace(",", ""));
        leader.setIsDefault("N");
        output = countBy("checkIfTemplateNameExists", leaderName);
        if(output.contentEquals("0"))
        {
            String initialCount = countREST();
            create(leader);
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
    
    @PUT
    @Path("updateLeaderTemplate")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String updateLeaderTemplate(Form form)
    {   
        String leaderName = form.asMap().getFirst("leaderName");
        String leaderValue = form.asMap().getFirst("leaderValue");
        String leaderId = form.asMap().getFirst("leaderId");
        leader.setTName(leaderName);
        leader.setTValue(leaderValue.replace(",", ""));
        leader.setIsDefault("N");
        leader.setId(Integer.parseInt(leaderId));
           // output = tplldrClient.countBy("checkIfTemplateNameExists", leaderName);
        edit(leader);
        output = "updated Successfully";
        
        return output;
    }
    
    
    @DELETE
    @Path("deleteLeader/{leaderId}")
    @Produces("text/plain")
    public String deleteLeader(@PathParam("leaderId") String leaderId)
    {
        // output = tplldrClient.countBy("checkIfTemplateNameExists", leaderName);
        remove(Integer.parseInt(leaderId));
        output = "Deleted Successfully";
        return output;
    }
}
