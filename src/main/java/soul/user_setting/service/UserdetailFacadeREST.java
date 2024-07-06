/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.user_setting.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
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
import soul.user_setting.Userdetail;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.user_setting.userdetail")
public class UserdetailFacadeREST extends AbstractFacade<Userdetail> {
    @EJB
    private SgroupFacadeREST sgroupFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    Userdetail user = new Userdetail();
    int count;
    String output;
    
    public UserdetailFacadeREST() {
        super(Userdetail.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Userdetail entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Userdetail entity) {
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
    public Userdetail find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("by/singleResult/{namedQuery}/{attrValues}")
    @Produces({"application/xml", "application/json"})
    public Userdetail findSingleResultBy(@PathParam("namedQuery") String query, @PathParam("attrValues") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findByUName": valueList.add(valueString[0]);
                                break;
        }
        return super.findSingleResultBy("Userdetail."+query, valueList);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Userdetail> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Userdetail> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<Userdetail> retrieveAll()
    {
        List<Userdetail> getAll = null;
             getAll = findAll();
          return getAll;
    }
    
    @GET
    @Path("checkUserName/{userName}")
    @Produces("text/plain")
    public String checkUserName(@PathParam("userName") String userName)
    {
        user = findSingleResultBy("findByUName", userName);
        if(user == null)
        {
            output = "Available";
        }
        else
        {
            output = "Not Available";
        }
       return output;    
    }
    
    @POST
    @Path("createOrSave")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrSave(Form form,@FormParam("operation") String operation)
    {
        if(operation.equals("Create"))
        {
            user = getUser(form);
            count = Integer.parseInt(countREST());
            create(user);
            if(count == Integer.parseInt(countREST()))
            {
                output = "Someting went wrong User is not created!";
            }
            else
            {
                output = "User Created.";
            }
        }
        if(operation.equals("Save"))
        {
            user = find(Integer.parseInt(form.asMap().getFirst("userId")));
            user.setLibID(form.asMap().getFirst("library"));
            user.setAddr(form.asMap().getFirst("address"));
            user.setCity(form.asMap().getFirst("city"));
            user.setState(form.asMap().getFirst("state"));
            user.setPin(form.asMap().getFirst("pin"));
            user.setCountry(form.asMap().getFirst("country"));
            user.setEmail(form.asMap().getFirst("email"));
            user.setPhone(form.asMap().getFirst("phone"));
            user.setExtention(form.asMap().getFirst("telex"));
            user.setMobile(form.asMap().getFirst("mobile"));
            edit(user);
            output = "User Information Updated.";
        }
        return output;
    }
    
     public  Userdetail getUser(Form form)
    {
        Userdetail user = new Userdetail();
        user.setUName(form.asMap().getFirst("userName").toLowerCase());
        user.setUPass(form.asMap().getFirst("password"));        //Encryption is to be done
        user.setGroupID(sgroupFacadeREST.find(Integer.parseInt(form.asMap().getFirst("groupName"))));
        user.setComment(form.asMap().getFirst("comment"));
        user.setQuestion(form.asMap().getFirst("secQues"));
        user.setAnswer(form.asMap().getFirst("answer"));
        user.setLibID(form.asMap().getFirst("library"));
        user.setAddr(form.asMap().getFirst("address"));
        user.setCity(form.asMap().getFirst("city"));
        user.setState(form.asMap().getFirst("state"));
        user.setPin(form.asMap().getFirst("pin"));
        user.setCountry(form.asMap().getFirst("country"));
        user.setEmail(form.asMap().getFirst("email"));
        user.setPhone(form.asMap().getFirst("phone"));
        user.setExtention(form.asMap().getFirst("telex"));
        user.setMobile(form.asMap().getFirst("mobile"));
        
        return user;
    }
     
    @DELETE
    @Path("deleteUser/{userName}")
    @Produces("text/plain")
    public String deleteUser(@PathParam("userName") String userName)
    {
        count = Integer.parseInt(countREST());
        remove(Integer.parseInt(userName));
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
    
    @POST
    @Path("changePassword")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String changePassword(Form form,@FormParam("operation") String operation)
    {
        String userName = form.asMap().getFirst("userName");
        String password = form.asMap().getFirst("oldPassword");
        user = findSingleResultBy("findByUName", form.asMap().getFirst("userName"));
        if(user == null)
        {
            output = "No user with \"Super User\" username found";
        }
        else
        {
            if(user.getUPass().equals(password))
            {
                user.setUPass(form.asMap().getFirst("newPassword"));
                user.setQuestion(form.asMap().getFirst("secQues"));
                user.setAnswer(form.asMap().getFirst("answer"));
                edit(user);
                output = "Password Changed.";
            }
            else
            {
                output = "Incorrect Password!";
            }
        }
       return output;
    }
}
