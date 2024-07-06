/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.PathSegment;
import soul.catalogue.Authoritytemplate;
import soul.catalogue.AuthoritytemplatePK;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.authoritytemplate")
public class AuthoritytemplateFacadeREST extends AbstractFacade<Authoritytemplate> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    private AuthoritytemplatePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;templateID=templateIDValue;sRNo=sRNoValue;tag=tagValue;subfield=subfieldValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.catalogue.AuthoritytemplatePK key = new soul.catalogue.AuthoritytemplatePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> templateID = map.get("templateID");
        if (templateID != null && !templateID.isEmpty()) {
            key.setTemplateID(templateID.get(0));
        }
        java.util.List<String> sRNo = map.get("sRNo");
        if (sRNo != null && !sRNo.isEmpty()) {
            key.setSRNo(sRNo.get(0));
        }
        java.util.List<String> tag = map.get("tag");
        if (tag != null && !tag.isEmpty()) {
            key.setTag(tag.get(0));
        }
        java.util.List<String> subfield = map.get("subfield");
        if (subfield != null && !subfield.isEmpty()) {
            key.setSubfield(subfield.get(0));
        }
        return key;
    }

    public AuthoritytemplateFacadeREST() {
        super(Authoritytemplate.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Authoritytemplate entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Authoritytemplate entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.catalogue.AuthoritytemplatePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Authoritytemplate find(@PathParam("id") PathSegment id) {
        soul.catalogue.AuthoritytemplatePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Authoritytemplate> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Authoritytemplate> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    //Added manually
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<Authoritytemplate> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueSting = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findTagByTemplateId":   valueList.add(valueSting[0]);
                                        break;
                                                
            case "findSubfieldByTemplateId":   valueList.add(valueSting[0]);
            valueList.add(valueSting[1]);
                                        break;
                
//            case "findByMaxSrNo": return super.findBy("Authoritytemplate."+query);
//                
            default:    valueList.add(valueSting[0]);
                        break;  
                        
        }
     
        return super.findBy("Authoritytemplate."+query, valueList);
    }
    
    
    @GET
    @Path("sortBy/{namedQuery}")
    @Produces({"application/xml", "application/json"})
    public List<Authoritytemplate> sortBy(@PathParam("namedQuery") String query){
        System.out.println("query "+query);
           return super.findBy("Authoritytemplate."+query);
    }
    
    @DELETE
    @Path("removeBy/{namedQuery}/{values}")
    public void removeBy(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
             case "deleteByTemplateId": valueList.add(valueString[0])  ; 
                break;
                 
             default:    valueList.add(valueString[0]);
                
                        break;
        }
        super.removeBy("Authoritytemplate."+query, valueList);
    } 
}

