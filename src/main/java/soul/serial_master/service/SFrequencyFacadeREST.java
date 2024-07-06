/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master.service;

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
import soul.serial_master.SFrequency;
import soul.serial_master.SMode;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.serial_master.sfrequency")
public class SFrequencyFacadeREST extends AbstractFacade<SFrequency> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    SFrequency frequency = new SFrequency();
    int count;
    String output;
    
    public SFrequencyFacadeREST() {
        super(SFrequency.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(SFrequency entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(SFrequency entity) {
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
    public SFrequency find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SFrequency> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SFrequency> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<SFrequency> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        List<String> smstStatus = new ArrayList<>();

        switch (query) {
            case "findBySFCd":  //used in schedule generration
                valueList.add(valueString[0]);
                break;

            case "findBySFExactName":  //used in frequency report  generration
                valueList.add(valueString[0]);
                break;
            case "findBySFLikeName":  //used in frequency report  generration
                valueList.add(valueString[0]+"%");
                break;
        }
        return super.findBy("SFrequency." + query, valueList);
    }
    
    //added manually
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<SFrequency> retrieveAll()
    { 
        List<SFrequency> getAll = null;
           getAll = findAll();
        return getAll;
    }
    
    
    ////used in schedule generration
    @GET
    @Path("retrieveAll/{code}")
    @Produces({"application/xml", "application/json"})
    public List<SFrequency> retrieveAll(@PathParam("code") String code) {
        List<SFrequency> getAll = null;

        getAll = findBy("findBySFCd", code);
        return getAll;
    }
    
    
    //used in frequency report  generration
    @GET
    @Path("frequencyReportLike/{searchParam}")
    @Produces({"application/xml", "application/json"})
    public List<SFrequency> frequencyReportLikeName(@PathParam("searchParam") String searchParam) {
        List<SFrequency> getAll = null;

        getAll = findBy("findBySFLikeName", searchParam);
        return getAll;
    }   
    
    
    //used in frequency report  generration
    @GET
    @Path("frequencyReportExact/{searchParam}")
    @Produces({"application/xml", "application/json"})
    public List<SFrequency> frequencyReportExactName(@PathParam("searchParam") String searchParam) {
        List<SFrequency> getAll = null;

        getAll = findBy("findBySFExactName", searchParam);
        return getAll;
    }
    
    
    @DELETE
    @Path("deleteFrequency/{code}")
    @Produces("text/plain")
    public String deleteFrequency(@PathParam("code") String code) {
        count = Integer.parseInt(countREST());
        try {
            remove(code);
        } catch (IllegalArgumentException d) {
            return "Invalid frequency code, or frequency with code " + code + " does not exist";
        }

        if (count == Integer.parseInt(countREST())) {
            output = "Someting went wrong record is not deleted!";
        } else {
            output = "Frequency Removed.";
        }
        return output;
    }
    
    @POST
    @Path("createOrUpdateFrequency")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrUpdateFrequency(@FormParam("operation") String operation,
            @FormParam("code") String code, @FormParam("description") String description,
            @FormParam("days") String days, @FormParam("months") String months,
            @FormParam("years") String years, @FormParam("dmy") String dmy,
            @FormParam("issuesPerYear") String issuesPerYear)
    {
        if (operation.equals("Create")) {
            SFrequency frequency = new SFrequency();

            frequency.setSFCd(code);
            frequency.setSFNm(description);

            switch (dmy) {
                case "Days":
                    frequency.setSFDd(Integer.parseInt(days));
                    frequency.setSFMm(0);
                    frequency.setSFYy(0);
                    break;
                case "Months":
                    frequency.setSFMm(Integer.parseInt(months));
                    frequency.setSFDd(0);
                    frequency.setSFYy(0);
                    break;
                case "Years":
                    frequency.setSFYy(Integer.parseInt(years));
                    frequency.setSFDd(0);
                    frequency.setSFMm(0);
                    break;
            }

            frequency.setSFIss(Integer.parseInt(issuesPerYear));
            frequency.setSFDmy(dmy.substring(0, 1));

            count = Integer.parseInt(countREST());
            create(frequency);
            if (count == Integer.parseInt(countREST())) {
                output = "Someting went wrong frequency is not creted.";
            } else {
                output = "Frequency created.";
            }
            //response.sendRedirect("jsp/frequency.jsp");
        }
        if(operation.equals("Update"))
        { 
            SFrequency frequency;
            try
            {
                frequency = find(code);
            }catch(NullPointerException e){
                return "Invalid code.";
            }
            
            //frequency.setSFCd(code);
            frequency.setSFNm(description);

            switch (dmy) {
                case "Days":
                    frequency.setSFDd(Integer.parseInt(days));
                    frequency.setSFMm(0);
                    frequency.setSFYy(0);
                    break;
                case "Months":
                    frequency.setSFMm(Integer.parseInt(months));
                    frequency.setSFDd(0);
                    frequency.setSFYy(0);
                    break;
                case "Years":
                    frequency.setSFYy(Integer.parseInt(years));
                    frequency.setSFDd(0);
                    frequency.setSFMm(0);
                    break;
            }

            frequency.setSFIss(Integer.parseInt(issuesPerYear));
            frequency.setSFDmy(dmy.substring(0, 1));

            count = Integer.parseInt(countREST());
            edit(frequency);
            output = "Frequency updated.";                              
        }
        return output;
    }
    
     public SFrequency getFrequency(Form form)
    {
        SFrequency frequency = new SFrequency();
        
        frequency.setSFCd(form.asMap().getFirst("code"));
        frequency.setSFNm(form.asMap().getFirst("frequency"));
        
        String dmy = form.asMap().getFirst("dmy");
        switch(dmy)
        {
            case "Days":    frequency.setSFDd(Integer.parseInt(form.asMap().getFirst("numbers")));
                            frequency.setSFMm(0);
                            frequency.setSFYy(0);
                            break;
            case "Months":  frequency.setSFMm(Integer.parseInt(form.asMap().getFirst("numbers")));
                            frequency.setSFDd(0);
                            frequency.setSFYy(0);    
                            break;
            case "Years":   frequency.setSFYy(Integer.parseInt(form.asMap().getFirst("numbers")));
                            frequency.setSFDd(0);
                            frequency.setSFMm(0);
                            break;
        }

        frequency.setSFIss(Integer.parseInt(form.asMap().getFirst("issues")));
        frequency.setSFDmy(form.asMap().getFirst("dmy").substring(0, 1));
        
        return frequency;
    }
}
