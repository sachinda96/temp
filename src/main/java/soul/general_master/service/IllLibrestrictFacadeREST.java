/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master.service;

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
import javax.ws.rs.core.PathSegment;
import soul.general_master.IllLibrestrict;
import soul.general_master.IllLibrestrictPK;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.illlibrestrict")
public class IllLibrestrictFacadeREST extends AbstractFacade<IllLibrestrict> {
    @EJB IllLibmstFacadeREST illLibmstFacadeREST;
    @EJB LibmaterialsFacadeREST libmaterialsFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    IllLibrestrict libPrivilege = new IllLibrestrict();
    String id = "";
    int count;
    String output;
    
    private IllLibrestrictPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;libCd=libCdValue;libMediaCd=libMediaCdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.general_master.IllLibrestrictPK key = new soul.general_master.IllLibrestrictPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> libCd = map.get("libCd");
        if (libCd != null && !libCd.isEmpty()) {
            key.setLibCd(libCd.get(0));
        }
        java.util.List<String> libMediaCd = map.get("libMediaCd");
        if (libMediaCd != null && !libMediaCd.isEmpty()) {
            key.setLibMediaCd(libMediaCd.get(0));
        }
        return key;
    }

    public IllLibrestrictFacadeREST() {
        super(IllLibrestrict.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(IllLibrestrict entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(IllLibrestrict entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.general_master.IllLibrestrictPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public IllLibrestrict find(@PathParam("id") PathSegment id) {
        soul.general_master.IllLibrestrictPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<IllLibrestrict> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<IllLibrestrict> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    
   // /manually added
    
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<IllLibrestrict> findByIdAndMeadiaCd(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query){
            case "findByIdAndMeadiaCd" :    valueList.add(valueString[0]);
                                            valueList.add(valueString[1]);
            default:valueList.add(valueString[0]);
        }
       
        return super.findBy("IllLibrestrict."+query, valueList);
    }
  
    @DELETE
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public void removeBylibCdAndlibMediaCd(@PathParam("values") String values){
       String[] valueString = values.split(",");
       List<Object> valueList = new ArrayList<>();
       valueList.add(valueString[0]);
       valueList.add(valueString[1]);
       super.removeBy("IllLibrestrict.removeBylibCdAndlibMediaCd", valueList);
    }
    
    
    @GET
    @Path("retrieveAll/{accept}/{form}")
    @Produces({"application/xml", "application/json"})
   // public List<IllLibrestrict> retrieveAll(@QueryParam("accept") String accept,@QueryParam("form") String form)
    public List<IllLibrestrict> retrieveAll(@PathParam("accept") String accept,@PathParam("form") String form)        
    {
        List<IllLibrestrict> getAll = null;
        if(accept.equals("XML"))
        {
            getAll = findAll();
        }
//        else
//        {
//            GenericType<List<IllLibrestrict>> genericType ;
//            genericType = new GenericType<List<IllLibrestrict>>(){};
//            List<IllLibrestrict> libList = new ArrayList<>();
//            Response restResponse = libPrivilegeClient.findAll_XML(Response.class);
//            libList = restResponse.readEntity(genericType);
//            request.setAttribute("libList", libList);
//        }
        return getAll;
    }   
    @GET
    @Path("retrieveAllLibraryPriveleges/{code}")
    @Produces({"application/xml", "application/json"})
    public List<IllLibrestrict> getlibraryprivilagedata(@PathParam("code") String code)
    {
        List<IllLibrestrict> getall = null;
        getall =  findByIdAndMeadiaCd("findByLibCd" , code);
        return getall;
        
    }
   
    @DELETE
    @Path("deleteLibraryPrivilege/{libName}/{material}")
    @Produces("text/plain")
    public String deleteLibraryPrivilege(@PathParam("libName") String libName,@PathParam("material") String material)
    {
        count = Integer.parseInt(countREST());
       // id = " ;libCd="+libName+";libMediaCd="+material;
        removeBylibCdAndlibMediaCd(libName+","+material);
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
    @Path("createOrSave")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrSave(Form form,@FormParam("operation") String operation)
    {
        if(operation.equals("Create"))
        {
            libPrivilege.setIllLibrestrictPK(new IllLibrestrictPK(form.asMap().getFirst("libName"), form.asMap().getFirst("material")));
            libPrivilege.setIllLibmst(illLibmstFacadeREST.find(form.asMap().getFirst("libName")));
            libPrivilege.setLibmaterials(libmaterialsFacadeREST.find(form.asMap().getFirst("material")));
            libPrivilege.setMxDays(Integer.parseInt(form.asMap().getFirst("issueDays")));
            libPrivilege.setMxAllowed(Integer.parseInt(form.asMap().getFirst("maxAllowed")));
            libPrivilege.setFineChrg(Float.parseFloat(form.asMap().getFirst("fine")));

            count = Integer.parseInt(countREST());

            create(libPrivilege);

            if(count == Integer.parseInt(countREST()))
            {
                output = "Someting went wrong entry is not inserted!";
            }
            else
            {
                output = "Library Privilege added to database";
            }
         }
         if(operation.equals("Save"))
         {
            id = form.asMap().getFirst("libName")+","+form.asMap().getFirst("material");
            libPrivilege = (IllLibrestrict) findByIdAndMeadiaCd("findByIdAndMeadiaCd",id);

            libPrivilege.setMxDays(Integer.parseInt(form.asMap().getFirst("issueDays")));
            libPrivilege.setMxAllowed(Integer.parseInt(form.asMap().getFirst("maxAllowed")));
            libPrivilege.setFineChrg(Float.parseFloat(form.asMap().getFirst("fine")));
            edit(libPrivilege);
            output = "Library Privilege information updated.";
         }
         return output;
    }
}
