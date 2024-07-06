/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation_master.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.ws.rs.core.Form;
import soul.circulation_master.MType;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation_master.mtype")
public class MTypeFacadeREST extends AbstractFacade<MType> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output;
    int count;
    public MTypeFacadeREST() {
        super(MType.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(MType entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MType entity) {
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
    public MType find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MType> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MType> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    //added manually
    
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<MType> retrieveAll()
    {
        List<MType> getAll = null;
        getAll = findAll();
        return getAll;
    }
    
    @POST
    @Path("deleteMemberType")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String deleteMemberType(@FormParam("memberType") String memberType)
    {
        try{
            remove(memberType);
        } catch (IllegalArgumentException | NullPointerException e){
            return "Invalid member Type";
        }
      
      return "Member Type Deleted.";
    }
    
    @POST
    @Path("addOrUpdate")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String addOrUpdate(Form form,@FormParam("operation") String operation) 
    {
            String mtype = form.asMap().getFirst("mtype");
            String edate = form.asMap().getFirst("edate");
            int maxbook = Integer.parseInt(form.asMap().getFirst("maxbook"));
            BigDecimal maxamount = new BigDecimal(form.asMap().getFirst("maxamount"));
            MType m1 = new MType();
            m1.setMemberType(mtype);
            try {
                m1.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(edate));
            } catch (ParseException ex) {
                Logger.getLogger(MTypeFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            m1.setMaximumBooks(maxbook);
            m1.setMaximumMoney(maxamount);
//            JAXBContext jaxbContext = JAXBContext.newInstance(MType.class);
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            StringWriter op = new StringWriter();
//            jaxbMarshaller.marshal(objectFactory.createMType(m1), op);
//            String rXML = null;
//            rXML = op.toString().replaceAll("<mTypes>", "");
//            rXML = rXML.replaceAll("</mTypes>", "");
//            out.println(rXML);
            edit(m1);
            output = "saved.....";
        
          return output;
    }
    
    
    //member type update without js communication
    @POST
    @Path("addOrUpdateMemberType")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String addOrUpdateMemberType(Form form, @FormParam("operation") String operation) {
        String mtype = form.asMap().getFirst("mtype");
        String edate = form.asMap().getFirst("edate");
        edate = edate + Calendar.getInstance().getTime();
        int maxbook = Integer.parseInt(form.asMap().getFirst("maxbook"));
        BigDecimal maxamount = new BigDecimal(form.asMap().getFirst("maxamount"));
        MType m1 = new MType();

        count = Integer.parseInt(countREST());
        if (operation.equals("Add")) {           
            m1.setMemberType(mtype);
            try {
                m1.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(edate));
            } catch (ParseException ex) {
                Logger.getLogger(MTypeFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            m1.setMaximumBooks(maxbook);
            m1.setMaximumMoney(maxamount);
            create(m1);
            if (count == Integer.parseInt(countREST())) {
                // output = "Soemthing went wrong, Material privilege is not added.";
                output = "Soemthing went wrong,Member type not added.";
            } else {
                output = "Member type Added";
            }
        } else if (operation.equals("Update")) {
            //m1.setMemberType(mtype);
            try {
                MType m2 = MTypeFacadeREST.this.find(mtype);
                m2.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(edate));
                m2.setMaximumBooks(maxbook);
                m2.setMaximumMoney(maxamount);
                edit(m2);
                output = "Member Type updated";
            } catch (NullPointerException | ParseException ex) {
                output = "Invalid Member type. Cannot update.";
            }
        } else {
            output = "Invalid Operation";
        }
        return output;
    }
}
