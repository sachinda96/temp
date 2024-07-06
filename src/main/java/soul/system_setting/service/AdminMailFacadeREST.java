/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.system_setting.service;

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
import javax.ws.rs.core.Form;
import soul.system_setting.AdminMail;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.system_setting.adminmail")
public class AdminMailFacadeREST extends AbstractFacade<AdminMail> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    AdminMail adminMail;
    public AdminMailFacadeREST() {
        super(AdminMail.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(AdminMail entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(AdminMail entity) {
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
    public AdminMail find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<AdminMail> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<AdminMail> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("getEmailConfig")
    @Produces({"application/xml", "application/json"})
    public AdminMail getEmailConfig() {
       return super.findAll().get(0);
    }
    
    @PUT
    @Path("updateEmailConfig")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String updateEmailConfig(@FormParam("domainName") String domainName, @FormParam("smtpServer") String smtpServer,
    @FormParam("authorizedEmailId") String authorizedEmailId, @FormParam("userName") String userName,
    @FormParam("password") String password)
    {
        adminMail = getEmailConfig();
      
      
       adminMail.setDomainName(domainName);
       adminMail.setSmtp(smtpServer);
       adminMail.setEmail(authorizedEmailId);
       adminMail.setUname(userName);
       adminMail.setPwd(password);
       edit(adminMail);
       return "Configuration Updated!";
    }
}
