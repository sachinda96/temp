/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue.service;

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
import soul.catalogue.Authoritytemplate;
import soul.catalogue.AuthoritytemplatePK;
import soul.catalogue.Authoritytemplatemain;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.authoritytemplatemain")
public class AuthoritytemplatemainFacadeREST extends AbstractFacade<Authoritytemplatemain> {
    @EJB
    private AuthoritytemplateFacadeREST authoritytemplateFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output;
    
    public AuthoritytemplatemainFacadeREST() {
        super(Authoritytemplatemain.class);
    }

//    @POST
//    @Override
//    @Consumes({"application/xml", "application/json"})
//    public void create(Authoritytemplatemain entity) {
//        super.create(entity);
//    }
    @POST
    @Path("create/{subfieldArray}")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
//    public String create(@FormParam("templateName") String templateName,@FormParam("templateRemark") String templateRemark,
//    @FormParam("tagArray") String tagArray,
//    @QueryParam("subfieldArray") String subfieldArray,@FormParam("descriptionArray") String descriptionArray) 
//    {
    public String create(@FormParam("templateName") String templateName,@FormParam("templateRemark") String templateRemark,
    @FormParam("tagArray") String tagArray,
    @PathParam("subfieldArray") String subfieldArray,@FormParam("descriptionArray") String descriptionArray) 
    {    
         // add to authoritytemplatemain
        String templateExists = countBy("checkIfTemplateNameExists" , templateName);
        int  count= Integer.parseInt(authoritytemplateFacadeREST.countREST());
        System.out.println("checkTemplateExists "+templateExists);
        if(templateExists.contentEquals("no"))
        {  
            List<Authoritytemplatemain>  templateList ;
            Authoritytemplatemain entity = new Authoritytemplatemain();
            templateList = super.findBy("Authoritytemplatemain.findByMaxTemplateId");
            String getRowWithMaxId = templateList.get(0).getTemplateID(); 
            int getRowWithMaxId_Int = Integer.parseInt(getRowWithMaxId);
            getRowWithMaxId_Int = getRowWithMaxId_Int +1; // increment maxId by 1 to generate new id
            String nextId = String.valueOf(getRowWithMaxId_Int); // to be used in templateId
            System.out.println("nextId "+nextId);
            entity.setTemplateID(nextId);
            entity.setTemplateName(templateName);
            entity.setRemark(templateRemark);
            entity.setIsDefault("No");
            create(entity);
            // add to authoritytemplate
            String authorityTemplateTagArraySplit[] = tagArray.split("~");
            String authorityTemplateSubfieldArraySplit[] = subfieldArray.split("~");
            String authorityDscriptionArraySplit[] = descriptionArray.split("~");
            for(int i = 0;i< authorityTemplateTagArraySplit.length;i++)
            {
                Authoritytemplate template = new Authoritytemplate();
                AuthoritytemplatePK authoritytemplatePK = new AuthoritytemplatePK();
             //   System.out.println("i "+i+"  "+authorityTemplateTagArraySplit[i]+" "+authorityTemplateSubfieldArraySplit[i]);
                authoritytemplatePK.setTemplateID(nextId);
                authoritytemplatePK.setTag(authorityTemplateTagArraySplit[i]);
                String srNo = String.valueOf((i+1));
                authoritytemplatePK.setSRNo(srNo);
                authoritytemplatePK.setSubfield(authorityTemplateSubfieldArraySplit[i]);
                template.setAuthoritytemplatePK(authoritytemplatePK);
                template.setDescription(authorityDscriptionArraySplit[i]);
                authoritytemplateFacadeREST.create(template);
             }
           if(count == Integer.parseInt(authoritytemplateFacadeREST.countREST()))
            {
                output = "Something went wrong, template is not added.";
            }
            else
            {
                output = "Template added successfully";
            }
        }
        else
        {
            output = "exists";
        }
           return output;
    }
    
    @PUT
    @Path("update")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String update(@FormParam("templateId") String templateId,@FormParam("templateName") String templateName,@FormParam("templateRemark") String templateRemark,
    @FormParam("tagArray") String tagArray,@FormParam("subfieldArray") String subfieldArray,
    @FormParam("descriptionArray") String descriptionArray)
    {   // update biblitemplatemain
        List<Authoritytemplatemain>  templateList ;
        Authoritytemplatemain entity = new Authoritytemplatemain();
//        templateList = super.findBy("Biblitemplatemain.findByMaxTemplateId");
//        String getRowWithMaxId = templateList.get(0).getTemplateID(); 
//        int getRowWithMaxId_Int = Integer.parseInt(getRowWithMaxId);
//        getRowWithMaxId_Int = getRowWithMaxId_Int +1; // increment maxId by 1 to generate new id
//        String nextId = String.valueOf(getRowWithMaxId_Int);
//        System.out.println("nextId "+nextId);
        entity.setTemplateID(templateId);
        entity.setTemplateName(templateName);
        entity.setRemark(templateRemark);
        entity.setIsDefault("No");
        edit(entity);
        // update biblitemplate
        authoritytemplateFacadeREST.removeBy("deleteByTemplateId", templateId); // remove old entry
        String authorityTemplateTagArraySplit[] = tagArray.split("~");
        String authorityTemplateSubfieldArraySplit[] = subfieldArray.split("~");
        String authorityDscriptionArraySplit[] = descriptionArray.split("~");
        for(int i = 0;i< authorityTemplateTagArraySplit.length;i++)
       {    
           Authoritytemplate authoritytemplate = new Authoritytemplate();
           AuthoritytemplatePK authoritytemplatePK = new AuthoritytemplatePK();
           System.out.println("i "+i+"  "+authorityTemplateTagArraySplit[i]+" "+authorityTemplateSubfieldArraySplit[i]);
           authoritytemplatePK.setTemplateID(templateId);
           authoritytemplatePK.setTag(authorityTemplateTagArraySplit[i]);
           String srNo = String.valueOf((i+1));
           authoritytemplatePK.setSRNo(srNo);
           authoritytemplatePK.setSubfield(authorityTemplateSubfieldArraySplit[i]);
           authoritytemplate.setAuthoritytemplatePK(authoritytemplatePK);
           authoritytemplate.setDescription(authorityDscriptionArraySplit[i]);
           authoritytemplateFacadeREST.create(authoritytemplate);
       }
        output = "Template updated successfully";
        return output;
    }

    
    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Authoritytemplatemain entity) {
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
    public Authoritytemplatemain find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Authoritytemplatemain> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Authoritytemplatemain> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<Authoritytemplatemain> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueSting = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
             case "findByMaxTemplateId":  
                                    return super.findBy("Authoritytemplatemain."+query);
                                                
             case "findAllWithNoDefault":return super.findBy("Authoritytemplatemain."+query);
                
             case "findByTemplateID":valueList.add(valueSting[0]);
                 break;
            default:    valueList.add(valueSting[0]);
                        break;  
                      
        }
        return super.findBy("Authoritytemplatemain."+query, valueList);
    }
    
//    @GET
//    @Path("count/by/{namedQuery}/{values}")
//    @Produces("text/plain")
//    public String countBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
//        String[] valueString = values.split(",");
//        List<Object> valueList = new ArrayList<>();
//        
//        switch(query)
//        {
//            case "checkIfTemplateNameExists": valueList.add(valueString[0]);
//                break;
//            default:    valueList.add(valueString[0]);
//            //used for countRecId
//        }
//        return String.valueOf(super.countBy("Authoritytemplatemain."+query, valueList));
//    }
    
    
    @GET
    @Path("count/by/{namedQuery}/{values}")
    @Produces("text/plain")
    public String countBy(@PathParam("namedQuery") String query, @PathParam("values") String values)  {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        valueList.add(valueString[0]);
        // used in Authoritytemplatemain to checkIfTemplateExists 
        // if count of template name is more than 1 then , template already exists
       String output = String.valueOf(super.countBy("Authoritytemplatemain."+query,valueList));
        if(output.contentEquals("0"))
         {
             return "no";
         }
         else
         {
             return "yes";
         }
     }
      
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
   // public List<Authoritytemplatemain> retrieveAll(@QueryParam("accept") String accept,@QueryParam("form") String form)
    public List<Authoritytemplatemain> retrieveAll()        
    {
        List<Authoritytemplatemain> getAll = null;
        getAll = findAll();
        return getAll;
    }
    
    @DELETE
    @Path("deleteTemplate/{code}")
    @Produces("text/plain")
    public String deleteTemplate(@PathParam("code") String code)
    {
        remove(super.find(code));
        authoritytemplateFacadeREST.removeBy("deleteByTemplateId", code); // remove old entry
        output = "Template deleted successfully";
        return output;
    }
}
