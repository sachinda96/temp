/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.ws.rs.core.MediaType;
import soul.general_master.Autogenerate;

/**
 *
 * @author admin
 */
@Stateless
@Path("soul.general_master.autogenerate")
public class AutogenerateFacadeREST extends AbstractFacade<Autogenerate> {

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    Autogenerate update;
    Autogenerate updateNo;

    public AutogenerateFacadeREST() {
        super(Autogenerate.class);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Autogenerate entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Autogenerate entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Autogenerate find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Autogenerate> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Autogenerate> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
    @Path("retrieveAll")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Autogenerate> retrieveAll() {
        return super.findAll();
    }
    
    @PUT
    @Path("updateStatus")
    @Produces("text/plain")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String updateStatus(@FormParam("isActive") String isActive,@FormParam("id") String id)
    {
        String[] isActiveSplit = isActive.split(",");
        String[] idSplit = id.split(",");
        for(int i=0;i<idSplit.length;i++)
        {
            Autogenerate autogenerate = find(Long.parseLong(idSplit[i]));
            autogenerate.setIsActive(isActiveSplit[i]);
            edit(autogenerate);
        }
        return "record Updated!!";
    }
    
    @GET
    @Path("getSerialOrderId")
    @Produces({"application/xml", "application/json","text/plain"})
    public List<Autogenerate> getSerialOrderId() {
        List<Autogenerate> getAll = new ArrayList<>();
        getAll = findBy("findByName", "Serial Order");
        return getAll;
    }
    
        //Added Manually
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<Autogenerate> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
            case "findByName":     valueList.add(valueString[0]);
                                                  
                                                break;
                                                
            //case "findByName":      break;                                                  
                                                
            default:    valueList.add(valueString[0]);
                        break;
                        //
                
        }
        return super.findBy("Autogenerate."+query, valueList);
    }
    
    @PUT
    @Path("updateAutogenerate")
    @Produces({"application/xml", "application/json", "text/plain"})
    public void updateAutogenerate(String name) {
        //Autogenerate prefix update
        List<Autogenerate> autoGenerate = new ArrayList<>();
        autoGenerate = findBy("findByName", name);

        System.out.println("reportSize: " + autoGenerate.size());
        for (int i = 0; i < autoGenerate.size(); i++) {
            System.out.println("reportSize: " + autoGenerate.get(i));
        }
        long id = autoGenerate.get(0).getId();
        update = find(id);
        long newLastSeed = update.getLastSeed() + 1;
        update.setLastSeed(newLastSeed);
        super.edit(update);
    }
    
    @GET
    @Path("generateLetterNo/{name}")
    @Produces({"application/xml", "application/json", "text/plain"})
    public String generateLetterNo(@PathParam("name") String name) {
        //Autogenerate prefix update
        List<Autogenerate> autoGenerate = new ArrayList<>();
        //1. Get letter details by name
        autoGenerate = findBy("findByName", name);
        String letterNo = "";
        String newLetterNo = null;
        System.out.println("autoGenerate: "+autoGenerate.get(0).getIsActive().toString().trim());
        System.out.println("status:"+autoGenerate.get(0).getIsActive().toString().trim().contentEquals("Y"));
        //2. if letter is active then generate letter no else not
        if (autoGenerate.get(0).getIsActive().toString().trim().contentEquals("Y")) {
            letterNo = autoGenerate.get(0).getPrefix();
            System.out.println("letter name: "+name+"\n letter prefix: "+letterNo);
            String prefix = letterNo.substring(letterNo.length() - 2);
            long id = autoGenerate.get(0).getId();
            //3. get details of same letter by id, to update lastseed later
            update = find(id);
            long newLastSeed = update.getLastSeed() + 1;
            //4.extract last two characters from string for month or year
            String ym = letterNo.substring(letterNo.length() - 2);
            System.out.println("prefixSub: "+ym);
            if (letterNo.substring(letterNo.length() - 2).contentEquals("YY")) {
                letterNo = letterNo.replace("YY", Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2));
                System.out.println("yearPrefix: " + letterNo);
            } else {
                letterNo = letterNo.replace("MM", Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1));
                System.out.println("monthPrefix: " + letterNo);
            }
            //update.setLastSeed(newLastSeed);
            newLetterNo = letterNo +"/"+newLastSeed;
            System.out.println("newLetterNo: "+newLetterNo);
        //    super.edit(update);
        } else {
            System.out.println("newLetterNo: "+newLetterNo);
        }
        return newLetterNo;
    }
}
