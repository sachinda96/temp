/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation_master.service;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.xml.bind.JAXBException;
import soul.circulation_master.MCalender;
import soul.circulation_master.MWeekoffday;
//import soul.circulation_master.client.calender;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation_master.mweekoffday")
public class MWeekoffdayFacadeREST extends AbstractFacade<MWeekoffday> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output;
    public MWeekoffdayFacadeREST() {
        super(MWeekoffday.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(MWeekoffday entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MWeekoffday entity) {
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
    public MWeekoffday find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MWeekoffday> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MWeekoffday> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("getday")
    public String getDay() {
        String q = "select * from m_weekoffday";
        Query query = getEntityManager().createNativeQuery(q);
        String result =  query.getResultList().toString();
        result=result.replace("[", "");
        result=result.replace("]", "");
        return result;
    }
    
    @DELETE
    @Path("deletepreviousentry")
    public int deletePreviousData() {
        String q = "delete from m_weekoffday";
        Query query = getEntityManager().createNativeQuery(q);
        int result=query.executeUpdate();
        return result;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
   
    
     // added manually
   
    @POST
    @Path("addweekoffday")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String addweekoffday(Form form,@FormParam("operation") String operation)
    {
            deletePreviousData();
            //int day = Integer.parseInt(request.getParameter("day"));
            String day = form.asMap().getFirst("day");
            //MWeekoffdays cals = new MWeekoffdays();
            //MWeekoffdays.MWeekoffday cal = new MWeekoffdays.MWeekoffday();
            MWeekoffday cal = new MWeekoffday();
            cal.setWeekOffDay(day);
             //cals.getMWeekoffday().add(cal);
//            JAXBContext jaxbContext = JAXBContext.newInstance(MWeekoffday.class);   //removed s
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            StringWriter op = new StringWriter();
//            jaxbMarshaller.marshal(cal, op);
//            String rXML = null;
//            rXML = op.toString().replaceAll("<mWeekoffdays>", "");
//            rXML = rXML.replaceAll("</mWeekoffdays>", "");
            //out.println(rXML);
            create(cal);
            output = "Weekoffday is updated";
            return output;
    }
    
    //without js communication
   // added manually
   //update week of day
    @PUT
    @Path("updateWeekOfDay")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String updateWeekOfDay(@FormParam("day") String day)
    {
            deletePreviousData();
            MWeekoffday cal = new MWeekoffday();
            cal.setWeekOffDay(day);
            edit(cal);
            output = "Weekoffday is updated";
            return output;
    }
}
