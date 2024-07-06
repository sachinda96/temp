/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import soul.catalogue.Biblitemplate;
import soul.catalogue.BiblitemplatePK;
import soul.catalogue.Biblitemplatemain;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.biblitemplatemain")
public class BiblitemplatemainFacadeREST extends AbstractFacade<Biblitemplatemain> {
    @EJB
    private BiblitemplateFacadeREST biblitemplateFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output;
//    HttpServletRequest request;
//    HttpServletResponse response;
//    PrintWriter out ;
   
    public BiblitemplatemainFacadeREST() {
        super(Biblitemplatemain.class);
    }

    @POST
    @Path("create")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String create(@FormParam("templateName") String templateName,@FormParam("templateRemark") String templateRemark,
    @FormParam("tagArray") String tagArray,
    @FormParam("subfieldArray") String subfieldArray,@FormParam("descriptionArray") String descriptionArray) 
    {
         // add to biblitemplatemain
        String templateExists = countBy("checkIfTemplateNameExists" , templateName);
        int  count= Integer.parseInt(biblitemplateFacadeREST.countREST());
        System.out.println("checkTemplateExists "+templateExists);
        if(templateExists.contentEquals("no"))
        {  
            List<Biblitemplatemain>  templateList ;
            Biblitemplatemain entity = new Biblitemplatemain();
            templateList = super.findBy("Biblitemplatemain.findByMaxTemplateId");
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
            // add to biblitemplate
              String bibliTemplateTagArraySplit[] = tagArray.split("~");
            String bibliTemplateSubfieldArraySplit[] = subfieldArray.split("~");
            String bibliDscriptionArraySplit[] = descriptionArray.split("~");
            for(int i = 0;i< bibliTemplateTagArraySplit.length;i++)
            {
                Biblitemplate template = new Biblitemplate();
                BiblitemplatePK biblitemplatePK = new BiblitemplatePK();
             //   System.out.println("i "+i+"  "+bibliTemplateTagArraySplit[i]+" "+bibliTemplateSubfieldArraySplit[i]);
                biblitemplatePK.setTemplateID(nextId);
                biblitemplatePK.setTag(bibliTemplateTagArraySplit[i]);
                String srNo = String.valueOf((i+1));
                biblitemplatePK.setSRNo(srNo);
                biblitemplatePK.setSubfield(bibliTemplateSubfieldArraySplit[i]);
                template.setBiblitemplatePK(biblitemplatePK);
                template.setDescription(bibliDscriptionArraySplit[i]);
                biblitemplateFacadeREST.create(template);
             }
           if(count == Integer.parseInt(biblitemplateFacadeREST.countREST()))
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
        List<Biblitemplatemain>  templateList ;
        Biblitemplatemain entity = new Biblitemplatemain();
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
        biblitemplateFacadeREST.removeBy("deleteByTemplateId", templateId); // remove old entry
        String bibliTemplateTagArraySplit[] = tagArray.split("~");
        String bibliTemplateSubfieldArraySplit[] = subfieldArray.split("~");
        String bibliDscriptionArraySplit[] = descriptionArray.split("~");
        for(int i = 0;i< bibliTemplateTagArraySplit.length;i++)
       {    
           Biblitemplate biblitemplate = new Biblitemplate();
           BiblitemplatePK biblitemplatePK = new BiblitemplatePK();
           System.out.println("i "+i+"  "+bibliTemplateTagArraySplit[i]+" "+bibliTemplateSubfieldArraySplit[i]);
           biblitemplatePK.setTemplateID(templateId);
           biblitemplatePK.setTag(bibliTemplateTagArraySplit[i]);
           String srNo = String.valueOf((i+1));
           biblitemplatePK.setSRNo(srNo);
           biblitemplatePK.setSubfield(bibliTemplateSubfieldArraySplit[i]);
           biblitemplate.setBiblitemplatePK(biblitemplatePK);
           biblitemplate.setDescription(bibliDscriptionArraySplit[i]);
           biblitemplateFacadeREST.create(biblitemplate);
       }
        output = "Template updated successfully";
        return output;
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Biblitemplatemain find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Biblitemplatemain> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Biblitemplatemain> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    /*returns max templateId*/
    /*used in save template to generate new templateId*/
//    @GET
//    @Path("by/{namedQuery}/{values}")
//    @Produces({"application/xml", "application/json"})
    public List<Biblitemplatemain> findByMaxTemplateId(@PathParam("namedQuery") String query){
        return super.findBy("Biblitemplatemain."+query);
     }
    
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<Biblitemplatemain> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueSting = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
             case "findByMaxTemplateId":  
                                    return super.findBy("Biblitemplatemain."+query);
                                                
             case "findAllWithNoDefault":return super.findBy("Biblitemplatemain."+query);
                
             case "findByTemplateID":valueList.add(valueSting[0]);
                 break;
            default:    valueList.add(valueSting[0]);
                        break;  
                      
        }
        return super.findBy("Biblitemplatemain."+query, valueList);
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
//        return String.valueOf(super.countBy("Biblitemplatemain."+query, valueList));
//    }
    
    
    /// added manually ----
    
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
   // public List<Biblitemplatemain> retrieveAll(@QueryParam("accept") String accept,@QueryParam("form") String form)
    public List<Biblitemplatemain> retrieveAll()        
    {
        List<Biblitemplatemain> getAll = null;
        getAll = findAll();
        return getAll;
    }
    
    @GET
    @Path("count/by/{namedQuery}/{values}")
    @Produces("text/plain")
    public String countBy(@PathParam("namedQuery") String query, @PathParam("values") String values)  {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        valueList.add(valueString[0]);
        // used in BibliTemplatemain to checkIfTemplateExists 
        // if count of template name is more than 1 then , template already exists
       String output = String.valueOf(super.countBy("Biblitemplatemain."+query,valueList));
        if(output.contentEquals("0"))
         {
             return "no";
         }
         else
         {
             return "yes";
         }
     }
     
    @DELETE
    @Path("deleteTemplate/{code}")
    @Produces("text/plain")
    public String deleteTemplate(@PathParam("code") String code)
    {
        remove(super.find(code));
        biblitemplateFacadeREST.removeBy("deleteByTemplateId", code); // remove old entry
        output = "Template deleted successfully";
        return output;
    }
}
