/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

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
import javax.ws.rs.core.MediaType;
import soul.circulation.Memcardtemplatemain;
import soul.circulation.Memcardtemplatesub;
import soul.circulation.MemcardtemplatesubPK;

/**
 *
 * @author admin
 */
@Stateless
@Path("soul.circulation.memcardtemplatemain")
public class MemcardtemplatemainFacadeREST extends AbstractFacade<Memcardtemplatemain> {

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    @EJB
    private MemcardtemplatemainFacadeREST memcardtemplatemainFacadeREST;

    @EJB
    private MemcardtemplatesubFacadeREST memcardtemplatesubFacadeREST;

    public MemcardtemplatemainFacadeREST() {
        super(Memcardtemplatemain.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Memcardtemplatemain entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Memcardtemplatemain entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    public Memcardtemplatemain find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memcardtemplatemain> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memcardtemplatemain> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Added Manually
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<Memcardtemplatemain> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        //List<String> inString = new ArrayList<>();
        List<String> inString = new ArrayList<>();

        switch (query) {
            case "findMaxTemplateId":
                valueList.add(Integer.parseInt(valueString[0]));
                break;
            default:
                valueList.add(valueString[0]);
                break;

        }
        System.out.println("Memcardtemplatemain." + query + valueList);
        return super.findBy("Memcardtemplatemain." + query, valueList);
    }

    @GET
    @Path("GetTemplateNames")
    @Produces({"application/xml", "application/json"})
    public List<Memcardtemplatemain> GetTemplateNames() {
        List<Memcardtemplatemain> getAll = null;
        getAll = findAll();
        return getAll;
    }

    //This service returns all the templates by name
    @GET
    @Path("GetTemplateByName/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<Memcardtemplatemain> GetTemplateByName(@PathParam("searchParameter") String templateName) {
        List<Memcardtemplatemain> template = findBy("findByMTempName", templateName);
        return template;
    }

    @POST
    @Path("addmemberCardTemplate")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String memberCardTemplate(@FormParam("templateName") String templateName, @FormParam("isDefault") String isDefault,
            @FormParam("remark ") String remark, @FormParam("height") String height,
            @FormParam("width") String width, @FormParam("memCardTempSubName") String memCardTempSubName,
            @FormParam("memCardTempSubHeight") String memCardTempSubHeight, @FormParam("memCardTempSubWidth") String memCardTempSubWidth,
            @FormParam("memCardTempSubLeft") String memCardTempSubLeft, @FormParam("memCardTempSubTop") String memCardTempSubTop) {
        //1) save data in reportTemplateMain
        List<Memcardtemplatemain> getId = super.findAll();
        int tempId = 0;
        Memcardtemplatemain memcardtemplatemain = new Memcardtemplatemain();
        for (int i = 0; i < getId.size(); i++) {
            tempId = getId.get(i).getMTempID();
            System.out.println(tempId);
        }
        tempId = tempId + 1;
        System.out.println("Max TemplateID: " + tempId);
        memcardtemplatemain.setMTempID(tempId);
        memcardtemplatemain.setMTempName(templateName);
        memcardtemplatemain.setMIsDefault(isDefault);
        memcardtemplatemain.setMRemark(remark);
        memcardtemplatemain.setMHeight(height);
        memcardtemplatemain.setMWidth(width);
        memcardtemplatemain = super.createAndGet(memcardtemplatemain);
        //2) get templateId
        //3) split rptTemplate data values
        String memCardTempSubName_Split[] = memCardTempSubName.split(",");
        String memCardTempSubHeight_Split[] = memCardTempSubHeight.split(",");
        String memCardTempSubWidth_Split[] = memCardTempSubWidth.split(",");
        String memCardTempSubLeft_Split[] = memCardTempSubLeft.split(",");
        String memCardTempSubTop_Split[] = memCardTempSubTop.split(",");

        for (int i = 0; i < memCardTempSubName_Split.length; i++) {
            Memcardtemplatesub subMemCardTemplate = new Memcardtemplatesub();
            MemcardtemplatesubPK subMemCardTemplatePK = new MemcardtemplatesubPK();
            subMemCardTemplatePK.setMrptTempID(memcardtemplatemain.getMTempID());
            subMemCardTemplatePK.setMFieldName(memCardTempSubName_Split[i]);

            subMemCardTemplate.setMemcardtemplatesubPK(subMemCardTemplatePK);
            subMemCardTemplate.setMrptHeight(memCardTempSubHeight_Split[i]);
            subMemCardTemplate.setMrptWidth(memCardTempSubWidth_Split[i]);
            subMemCardTemplate.setMrptLeft(memCardTempSubLeft_Split[i]);
            subMemCardTemplate.setMrptTop(memCardTempSubTop_Split[i]);
            //4) save data along with template id in  rptTemplate
            memcardtemplatesubFacadeREST.create(subMemCardTemplate);
        }
        return "template saved successfully";
    }

    @PUT
    @Path("updateMemberCardTemplate")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String updateMemberCardTemplate(@FormParam("templateId") String templateId, @FormParam("templateName") String templateName,
            @FormParam("isDefault") String isDefault, @FormParam("remark ") String remark, @FormParam("height") String height,
            @FormParam("width") String width, @FormParam("memCardTempSubName") String memCardTempSubName,
            @FormParam("memCardTempSubHeight") String memCardTempSubHeight, @FormParam("memCardTempSubWidth") String memCardTempSubWidth,
            @FormParam("memCardTempSubLeft") String memCardTempSubLeft, @FormParam("memCardTempSubTop") String memCardTempSubTop) {

        //1) save data in reportTemplateMain
        Memcardtemplatemain memcardtemplatemain;
        try {
            memcardtemplatemain = find(Integer.parseInt(templateId));
            memcardtemplatemain.setMTempName(templateName);
            memcardtemplatemain.setMIsDefault(isDefault);
            memcardtemplatemain.setMRemark(remark);
            memcardtemplatemain.setMHeight(height);
            memcardtemplatemain.setMWidth(width);
            super.edit(memcardtemplatemain);
        } catch (NullPointerException d) {
            return "Invalid template id.";
        }

        //2) remove previous rows
        memcardtemplatesubFacadeREST.removeByTempId(templateId);
        //3) get templateId
        //4) split rptTemplate data values
        String rptSbrptName_Split[] = memCardTempSubName.split(",");
        String memCardTempSubHeight_Split[] = memCardTempSubHeight.split(",");
        String memCardTempSubWidth_Split[] = memCardTempSubWidth.split(",");
        String memCardTempSubLeft_Split[] = memCardTempSubLeft.split(",");
        String memCardTempSubTop_Split[] = memCardTempSubTop.split(",");

        for (int i = 0; i < rptSbrptName_Split.length; i++) {
            Memcardtemplatesub subMemCardTemplate = new Memcardtemplatesub();
            MemcardtemplatesubPK subMemCardTemplatePK = new MemcardtemplatesubPK();
            subMemCardTemplatePK.setMrptTempID(Integer.parseInt(templateId));
            subMemCardTemplatePK.setMFieldName(rptSbrptName_Split[i]);
            //rpttemplatePK.setRptTempID(templateId));
            // rpttemplatePK.setRptSbrptName(rptSbrptName_Split[i]);

            subMemCardTemplate.setMemcardtemplatesubPK(subMemCardTemplatePK);
            subMemCardTemplate.setMrptHeight(memCardTempSubHeight_Split[i]);
            subMemCardTemplate.setMrptWidth(memCardTempSubWidth_Split[i]);
            subMemCardTemplate.setMrptLeft(memCardTempSubLeft_Split[i]);
            subMemCardTemplate.setMrptTop(memCardTempSubTop_Split[i]);
            //5) add data in  memcardtemplatesub
            memcardtemplatesubFacadeREST.create(subMemCardTemplate);
        }
        return "template updated successfully";
    }

    @DELETE
    @Path("removeByTempId/{tempId}")
    @Produces("text/plain")
    public String removeByTempId(@PathParam("tempId") String tempId) {
        String output;
        try {
            remove(Integer.parseInt(tempId)); // remove from memCardTemplateMain
            memcardtemplatesubFacadeREST.removeByTempId(tempId);  // remove from memCardTemplateMainsub
            output = "deleted successfully";
        } catch (IllegalArgumentException e) {
            output = "Invalid template id.";
        }
        return output;
    }
}
