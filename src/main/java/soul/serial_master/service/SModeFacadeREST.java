/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master.service;

import java.util.ArrayList;
import java.util.Arrays;
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
import soul.serialControl.SMst;
import soul.serial_master.SMode;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.serial_master.smode")
public class SModeFacadeREST extends AbstractFacade<SMode> {

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    SMode dMode = new SMode();
    int count;
    String output;

    public SModeFacadeREST() {
        super(SMode.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(SMode entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(SMode entity) {
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
    public SMode find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SMode> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SMode> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<SMode> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        List<String> smstStatus = new ArrayList<>();

        switch (query) {
            case "findBySModeCd":       //used in schedule generration
                valueList.add(valueString[0]);
                break;
            case "findBySModeDescLike":       //used in schedule generration
                valueList.add(valueString[0]+"%");
                break;
            case "findBySModeDesc":       //used in schedule generration
                valueList.add(valueString[0]);
                break;
        }
        return super.findBy("SMode." + query, valueList);
    }

    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<SMode> retrieveAll() {
        List<SMode> getAll = null;

        getAll = findAll();
        return getAll;
    }
    
    
    //used in schedule generration
    @GET
    @Path("retrieveAll/{code}")
    @Produces({"application/xml", "application/json"})
    public List<SMode> retrieveAll(@PathParam("code") String code) {
        List<SMode> getAll = null;

        getAll = findBy("findBySModeCd", code);
        return getAll;
    }
    
    //used in schedule generration
    @GET
    @Path("deliveryModeReportLike/{searchParam}")
    @Produces({"application/xml", "application/json"})
    public List<SMode> deliveryModeReportLike(@PathParam("searchParam") String searchParam) {
        List<SMode> getAll = null;

        getAll = findBy("findBySModeDescLike", searchParam);
        return getAll;
    }
    
    //used in schedule generration
    @GET
    @Path("deliveryModeReportExact/{searchParam}")
    @Produces({"application/xml", "application/json"})
    public List<SMode> deliveryModeReportExact(@PathParam("searchParam") String searchParam) {
        List<SMode> getAll = null;

        getAll = findBy("findBySModeDesc", searchParam);
        return getAll;
    }

//    @GET
//    @Path("retrieveAll")
//    @Produces({"application/xml", "application/json"})
//    public List<SMode> retrieveAll(@QueryParam("accept") String accept,@QueryParam("form") String form)
//    {  
//        List<SMode> getAll = null;
//        if(accept.equals("XML"))
//       {
//           getAll = findAll();
//       }
//       return getAll;
//    }
// p           case "Retrieve":    dMode = deliveryClient.find_XML(SMode.class, request.getParameter("code"));
//                                request.setAttribute("dMode", dMode);
//                                break;
    @POST
    @Path("createOrUpdateDeliveryMode")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrUpdateDeliveryMode(@FormParam("operation") String operation,
            @FormParam("code") String code, @FormParam("description") String description) {

        if (operation.equals("Create")) {
            SMode dMode = new SMode();
            dMode.setSModeCd(code);
            dMode.setSModeDesc(description);
            count = Integer.parseInt(countREST());
            create(dMode);
            if (count == Integer.parseInt(countREST())) {
                output = "Someting went wrong new Delivery Mode is not created!";
            } else {
                output = "Delivery Mode created.";
            }
            // out.println(output);
            //response.sendRedirect("jsp/deliveryMode.jsp");
        }
        if (operation.equals("Update")) {
            SMode dMode;
            try {
                dMode = find(code);
            } catch (NullPointerException e) {
                return "Invalid code.";
            }

            dMode.setSModeDesc(description);
            edit(dMode);
            output = "Delivery Mode updated.";
            // out.println(output);
            //response.sendRedirect("jsp/deliveryMode.jsp");
        }
        return output;
    }

    @DELETE
    @Path("deleteDeliveryMode/{code}")
    @Produces("text/plain")
    public String deleteDeliveryMode(@PathParam("code") String code) {
        count = Integer.parseInt(countREST());
        try {
            remove(code);
        } catch (IllegalArgumentException d) {
            return "Invalid frequency code, or frequency with code " + code + " does not exist";
        }
        if (count == Integer.parseInt(countREST())) {
            output = "Someting went wrong record is not deleted!";
        } else {
            output = "Delivery mode deleted.";
        }
        return output;
    }

}
