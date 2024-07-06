/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation_master.service;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.ws.rs.core.Form;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import soul.circulation_master.MCalender;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation_master.mcalender")
public class MCalenderFacadeREST extends AbstractFacade<MCalender> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output;
    int count;
    
    public MCalenderFacadeREST() {
        super(MCalender.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(MCalender entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MCalender entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Date id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public MCalender find(@PathParam("id") Date id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MCalender> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MCalender> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    //Added manually
    
    @GET
    @Path("festivaldates")
    public List<Object> getFestivalDate() {
        String q = "select festival_dt from m_calender";
        Query query = getEntityManager().createNativeQuery(q);
        List<Object> result = (List<Object>) query.getResultList();
        return result;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    //Added mannually
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<MCalender> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values)
    {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
            case "findByFestivalDt":    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                                        try {
                                            valueList.add(dateFormat.parse(valueString[0]));
                                        } catch (ParseException ex) {
                                            Logger.getLogger(MCalenderFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        break;
        }
        return super.findBy("MCalender."+query, valueList);
    }      
    
    //add or update festival
    @POST
    @Path("addOrEditFestival")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    //@Produces("text/plain")
    public String addOrEditFestival(Form form, @FormParam("operation") String operation) {
        String date = form.asMap().getFirst("date");
        date = date + Calendar.getInstance().getTime();
        String occasion = form.asMap().getFirst("occasion");
        //MCalenders cals = new MCalenders();
        //MCalenders.MCalender cal = new MCalenders.MCalender();
        System.out.println("datedatedate " + date + "   " + occasion);
        MCalender cal = new MCalender();
        try {
            //cal.setFestivalDt(date);
            cal.setFestivalDt(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(MWeekoffdayFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        cal.setFestivalNm(occasion);
        count = Integer.parseInt(countREST());
        if (operation.equals("Add")) {
            create(cal);
            if (count == Integer.parseInt(countREST())) {
                // output = "Soemthing went wrong, Material privilege is not added.";
                output = "Soemthing went wrong,date is not added.";
            } else {
                output = "Festival Added";
            }
        } else {
            edit(cal);
            output = "Festival Updated.";
        }
        return output;
    }
    
    
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<MCalender> retrieveAll()
    {
        List<MCalender> getAll = null;
        getAll = findAll();
        return getAll;
    }
    
    @DELETE
    @Path("deleteHoliday/{festivalDt}")
    @Produces("text/plain")
    public String deleteHoliday(@PathParam("festivalDt") String festivalDt) throws ParseException
    {
        System.out.println("festivalDt  "+festivalDt);
        System.out.println("parse date :  "+new SimpleDateFormat("yyyy-MM-dd").parse(festivalDt));
        try {
            remove(new SimpleDateFormat("yyyy-MM-dd").parse(festivalDt));
        } catch (IllegalArgumentException | ParseException ex) {
            return "Something went wrong.";
        }
       return "Holiday deleted successfully";
    }
}
