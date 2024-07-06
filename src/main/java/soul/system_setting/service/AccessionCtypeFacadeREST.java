/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.system_setting.service;

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
import soul.system_setting.AccessionCtype;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.system_setting.accessionctype")
public class AccessionCtypeFacadeREST extends AbstractFacade<AccessionCtype> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    AccessionCtype collectionType = new AccessionCtype();
    int count;
    String output;
    
    public AccessionCtypeFacadeREST() {
        super(AccessionCtype.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(AccessionCtype entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(AccessionCtype entity) {
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
    public AccessionCtype find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<AccessionCtype> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<AccessionCtype> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    //Added mannually
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<AccessionCtype> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values)
    {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
            default:    valueList.add(valueString[0]);
            //used for findByCollectionType
        }
        return super.findBy("AccessionCtype."+query, valueList);
    }   
    
    List<AccessionCtype> collectionTypeList;
    @GET
    @Path("retrieveAll/{accept}")
    @Produces({"application/xml", "application/json"})
    //public List<AccessionCtype> retrieveAll(@QueryParam("accept") String accept,@QueryParam("form") String form)
    public List<AccessionCtype> retrieveAll(@PathParam("accept") String accept)        
    {
      //  List<AccessionCtype> getAll = null;
        if(accept.equals("XML"))
        {
            collectionTypeList = findAll();
        }
        return collectionTypeList;
//        else if(accept.equals("ObjectList"))
//        {
//            GenericType<List<AccessionCtype>> genericType = new GenericType<List<AccessionCtype>>(){};
//            Response restResponse = collectionClient.findAll_XML(Response.class);
//            List<AccessionCtype> collectionTypeList = restResponse.readEntity(genericType);
//            request.setAttribute("collectionTypeList", collectionTypeList);
//        }
    }
    
    @GET
    @Path("retrieveAllcollectionTypeInfo")
    @Produces("text/plain")
  //  public String retrieveAllcollectionTypeInfo(@QueryParam("accept") String accept,@QueryParam("form") String form)
    public String retrieveAllcollectionTypeInfo()        
    {
    String collectionType = "";
    String prefix = "";
    String lastAccNo = "";
    String length = "";
    if(collectionTypeList!= null)
    {
        for(AccessionCtype c: collectionTypeList)
        {
            collectionType = collectionType+","+c.getCid();
            prefix = prefix+","+c.getPrefix();
            if(c.getLastaccno() == null)
            {
                lastAccNo = lastAccNo+","+"";
            }
            else
                lastAccNo = lastAccNo+","+c.getLastaccno();
            if(c.getTotallength() == 0)
            {
                length = length+","+"";
            }
            else
                length = length+","+c.getTotallength();
        }
    }
        String collectionTypeInfo = "";
        if(collectionType.length() != 0)
        {
            collectionTypeInfo = collectionType.substring(1)+"|"+prefix.substring(1)+"|"+lastAccNo.substring(1)+"|"+length.substring(1);
        }
       // request.setAttribute("collectionTypeInfo", collectionTypeInfo);
    return collectionTypeInfo;
    }
    
    @DELETE
    @Path("deleteAccessionCType/{code}")
    @Produces("text/plain")
    public String deleteAccessionCType(@PathParam("code") String code)
    {
        count = Integer.parseInt(countREST());
        remove(code);
        if(count == Integer.parseInt(countREST()))
        {
            output = "Someting went wrong record is not deleted!";
        }
        else
        {
            output= "OK";
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
                collectionType.setCid(form.asMap().getFirst("code"));
                collectionType.setCollectionType(form.asMap().getFirst("type"));
                collectionType.setPrefix(form.asMap().getFirst("prefix"));
                collectionType.setTotallength(Long.parseLong(form.asMap().getFirst("accessionLength")));
                count = Integer.parseInt(countREST());
                create(collectionType);
                if(count == Integer.parseInt(countREST()))
                {
                    output = "Someting went wrong, Collection Type record is not created!";
                }
                else
                {
                    output = "Collection Type record created.";
                }
                return output;
            }
        if(operation.equals("Save"))
            {
                collectionType = find(form.asMap().getFirst("code"));
                collectionType.setCollectionType(form.asMap().getFirst("type"));
                collectionType.setPrefix(form.asMap().getFirst("prefix"));
                collectionType.setTotallength(Long.parseLong(form.asMap().getFirst("accessionLength")));
                edit(collectionType);
                output = "Collection Type record Updated.";
            }
        return output;
    }
}
