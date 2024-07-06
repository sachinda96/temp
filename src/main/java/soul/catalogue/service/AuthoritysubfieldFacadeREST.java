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
import soul.catalogue.Authoritysubfield;
import soul.catalogue.AuthoritysubfieldPK;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.authoritysubfield")
public class AuthoritysubfieldFacadeREST extends AbstractFacade<Authoritysubfield> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    private AuthoritysubfieldPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;tag=tagValue;subfield=subfieldValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.catalogue.AuthoritysubfieldPK key = new soul.catalogue.AuthoritysubfieldPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
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

    public AuthoritysubfieldFacadeREST() {
        super(Authoritysubfield.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Authoritysubfield entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Authoritysubfield entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.catalogue.AuthoritysubfieldPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Authoritysubfield find(@PathParam("id") PathSegment id) {
        soul.catalogue.AuthoritysubfieldPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Authoritysubfield> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Authoritysubfield> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<Authoritysubfield> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueSting = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findByTag":   valueList.add(valueSting[0]);
                                break;
            //used in controlJs, editControlJs to get data by tagNo and templateId                             break;
            case "findByTagANDtemplate":   valueList.add(valueSting[0]);
                                           valueList.add(valueSting[1]);
                                           valueList.add(valueSting[1]);
                                           break;
            default:    valueList.add(valueSting[0]);
                        break;  
                        //used for findByP852
        }
        return super.findBy("Authoritysubfield."+query, valueList);
    }
}
