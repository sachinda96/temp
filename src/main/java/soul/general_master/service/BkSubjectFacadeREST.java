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
import soul.general_master.BkSubject;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.bksubject")
public class BkSubjectFacadeREST extends AbstractFacade<BkSubject> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    BkSubject subject = new BkSubject();
    int count;
    String output;
    
    public BkSubjectFacadeREST() {
        super(BkSubject.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(BkSubject entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(BkSubject entity) {
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
    public BkSubject find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<BkSubject> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<BkSubject> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<BkSubject> retrieveAll()
    {
         List<BkSubject> getAll = null;
             getAll = findAll();
         return getAll;
    }
    
    @GET
    @Path("getClassNoBySubject/{subject}")
    @Produces("text/plain")
    public String getClassNoBySubject(@PathParam("subject") String subject)
    {
        String classNo;
        List<BkSubject> getAll = findBy("getClassNo",subject);
        classNo = getAll.get(0).getBkClassno();
        return classNo;
    }
    
    @GET
    @Path("findBy/{namedQuery}/{attrValues}")
    @Produces({"application/xml", "application/json"})
    public List<BkSubject> findBy(@PathParam("namedQuery") String query, @PathParam("attrValues") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "getClassNo":   valueList.add(valueString[0]+"%");
                                        break;
        }
        return super.findBy("BkSubject."+query, valueList);
    }
    
    @POST
    @Path("createOrSave")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrSave(Form form,@FormParam("operation") String operation)
    {
        if(operation.equals("Create"))
        {
            System.out.println(form.asMap().getFirst("code"));
            subject.setBkClassno(form.asMap().getFirst("code"));
            subject.setBkSubjectName(form.asMap().getFirst("subject"));
            count = Integer.parseInt(countREST());
            create(subject);
            if(Integer.parseInt(countREST()) == count)
            {
                output = "Something went wrong, subject is not added.";
            }
            else
            {
                output = "Subject added to database.";
            }
             //response.sendRedirect("subject.jsp");
        }
         if(operation.equals("Save"))
        {
            subject = find(form.asMap().getFirst("code"));
            subject.setBkSubjectName(form.asMap().getFirst("subject"));
            edit(subject);
            //response.sendRedirect("subject.jsp");
            output = "Subject updated.";
         }
         return output;
    }
           
    @DELETE
    @Path("deleteSubject/{code}")
    @Produces("text/plain")
    public String deleteSubject(@PathParam("code") String code)
    {
        count = Integer.parseInt(countREST());
        try {
            remove(code);
        } catch (IllegalArgumentException d) {
            return "Invalid subject code, or subject with code " + code + " does not exist";
        }
        
        if(count == Integer.parseInt(countREST()))
        {
            output = "Someting went wrong record is not deleted!";
        }
        else
        {
            output = "OK";
        }
        return output;
    }
}
