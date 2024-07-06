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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.PathSegment;
import soul.catalogue.Biblitemplate;
import soul.catalogue.BiblitemplatePK;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.biblitemplate")
public class BiblitemplateFacadeREST extends AbstractFacade<Biblitemplate> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    
    private BiblitemplatePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;templateID=templateIDValue;sRNo=sRNoValue;tag=tagValue;subfield=subfieldValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.catalogue.BiblitemplatePK key = new soul.catalogue.BiblitemplatePK();
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

    public BiblitemplateFacadeREST() {
        super(Biblitemplate.class);
    }

//    @POST
//    @Path("add")
//    @Consumes({"application/xml", "application/json"})
//    public void create(@QueryParam("templateId") String templateId, @QueryParam("tagArray") String tagArray,@QueryParam("subfieldArray") String subfieldArray,@QueryParam("descriptionArray") String descriptionArray) 
//    {  
//       
//        String bibliTemplateTagArraySplit[] = tagArray.split("~");
//        String bibliTemplateSubfieldArraySplit[] = subfieldArray.split("~");
//        String bibliDscriptionArraySplit[] = descriptionArray.split("~");
//        for(int i = 0;i< bibliTemplateTagArraySplit.length;i++)
//       {
//           Biblitemplate template = new Biblitemplate();
//           BiblitemplatePK biblitemplatePK = new BiblitemplatePK();
//        //   System.out.println("i "+i+"  "+bibliTemplateTagArraySplit[i]+" "+bibliTemplateSubfieldArraySplit[i]);
//           biblitemplatePK.setTemplateID(templateId);
//           biblitemplatePK.setTag(bibliTemplateTagArraySplit[i]);
//           String srNo = String.valueOf((i+1));
//           biblitemplatePK.setSRNo(srNo);
//           biblitemplatePK.setSubfield(bibliTemplateSubfieldArraySplit[i]);
//           template.setBiblitemplatePK(biblitemplatePK);
//           template.setDescription(bibliDscriptionArraySplit[i]);
//           super.create(template);
//           
//       }
//    }
    
//    @PUT
//    @Path("update")
//    @Consumes({"application/xml", "application/json"})
//    public void edit(@QueryParam("templateId") String templateId, @QueryParam("tagArray") String tagArray,@QueryParam("subfieldArray") String subfieldArray,@QueryParam("descriptionArray") String descriptionArray) 
//    {  
//        //super.remove(templateId);
//        String bibliTemplateTagArraySplit[] = tagArray.split("~");
//        String bibliTemplateSubfieldArraySplit[] = subfieldArray.split("~");
//        String bibliDscriptionArraySplit[] = descriptionArray.split("~");
//        for(int i = 0;i< bibliTemplateTagArraySplit.length;i++)
//       {    
//           Biblitemplate entity = new Biblitemplate();
//           BiblitemplatePK biblitemplatePK = new BiblitemplatePK();
//        //   System.out.println("i "+i+"  "+bibliTemplateTagArraySplit[i]+" "+bibliTemplateSubfieldArraySplit[i]);
//           biblitemplatePK.setTemplateID(templateId);
//           biblitemplatePK.setTag(bibliTemplateTagArraySplit[i]);
//           String srNo = String.valueOf((i+1));
//           biblitemplatePK.setSRNo(srNo);
//           biblitemplatePK.setSubfield(bibliTemplateSubfieldArraySplit[i]);
//           entity.setBiblitemplatePK(biblitemplatePK);
//           entity.setDescription(bibliDscriptionArraySplit[i]);
//           super.edit(entity);
//       }
//        
//    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.catalogue.BiblitemplatePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Biblitemplate find(@PathParam("id") PathSegment id) {
        soul.catalogue.BiblitemplatePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Biblitemplate> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Biblitemplate> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<Biblitemplate> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueSting = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findTagByTemplateId":   valueList.add(valueSting[0]);
                                        break;
                                                
            case "findSubfieldByTemplateId":   valueList.add(valueSting[0]);
            valueList.add(valueSting[1]);
                                        break;
            case "findByTemplateID":   valueList.add(valueSting[0]);
                                       break;
                
//            case "findByMaxSrNo": return super.findBy("Biblitemplate."+query);
//                
            default:    valueList.add(valueSting[0]);
                        break;  
                        
        }
     
        return super.findBy("Biblitemplate."+query, valueList);
    }
    
    
    @GET
    @Path("sortBy/{namedQuery}")
    @Produces({"application/xml", "application/json"})
    public List<Biblitemplate> sortBy(@PathParam("namedQuery") String query){
        System.out.println("query "+query);
           return super.findBy("Biblitemplate."+query);
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
        super.removeBy("Biblitemplate."+query, valueList);
    } 
}
