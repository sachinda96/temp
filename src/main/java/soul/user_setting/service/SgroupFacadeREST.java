/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.user_setting.service;

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
import soul.user_setting.Sgroup;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.user_setting.sgroup")
public class SgroupFacadeREST extends AbstractFacade<Sgroup> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    
    Sgroup group = new Sgroup();
    String privilegeArray;
    String privilege[];
    String privileges;
    String groupName;
    String output ="";
    
    public SgroupFacadeREST() {
        super(Sgroup.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Sgroup entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Sgroup entity) {
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
    public Sgroup find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Sgroup> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Sgroup> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<Sgroup> retrieveAll() {
        List<Sgroup> getAll = null;
        getAll = findAll();
        return getAll;
    }

    @POST
    @Path("createOrSaveOrDelete")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrSaveOrDelete(Form form,@FormParam("operation") String operation,@FormParam("privilege") List<String> privilege)
    {
        if(operation.equals("Create"))
        {
           // privilegeArray = form.asMap().getFirst("privilege");
            System.out.println("privilege  " +privilege);
          //  privilege = privilegeArray.split(",");
            privileges = privilege.get(0);
            for(int i=1; i < privilege.size(); i++)
            {
                privileges = privileges+":"+privilege.get(i);
            }
            groupName = form.asMap().getFirst("name").equals("Add New") ? form.asMap().getFirst("newGroupName") : form.asMap().getFirst("name");
            group.setGroupName(groupName);
            group.setPrivilegeList(privileges);
            create(group);
            //response.sendRedirect("jsp/administration/userSetting/group.jsp");
            output = "group created";
        }
        if(operation.equals("Save"))
        {
            //privilegeArray = form.asMap().getFirst("privilege");
            System.out.println("privilege ......... " +privilege);
           // privilege = privilegeArray.split(",");
            
             privileges = privilege.get(0);
             System.out.println("1111");
            for(int i=1; i < privilege.size(); i++)
            {
                privileges = privileges+":"+privilege.get(i);
             }
            group = find(Integer.parseInt(form.asMap().getFirst("selectedGroupId")));
             group.setPrivilegeList(privileges);
             edit(group);
            output = "group updated";
            // response.sendRedirect("jsp/administration/userSetting/group.jsp");
        }
         if(operation.equals("Delete"))
        {
            int count = Integer.parseInt(countREST());
            remove(Integer.parseInt(form.asMap().getFirst("selectedGroupId")));
            if(count == Integer.parseInt(countREST()))
            {
                output = "err";
               // response.sendRedirect("jsp/administration/userSetting/group.jsp?output=Delete&msg=err");
            }
            else
            {
                 output = "ok";
               // response.sendRedirect("jsp/administration/userSetting/group.jsp?operation=Delete&msg=ok");
            }
        }
         return output;
    }
}
