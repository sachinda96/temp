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
import soul.catalogue.Reporttemplatemain;
import soul.catalogue.Rpttemplate;
import soul.catalogue.RpttemplatePK;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.reporttemplatemain")
public class ReporttemplatemainFacadeREST extends AbstractFacade<Reporttemplatemain> {
    @EJB 
    private RpttemplateFacadeREST rpttemplateFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public ReporttemplatemainFacadeREST() {
        super(Reporttemplatemain.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Reporttemplatemain entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Reporttemplatemain entity) {
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
    public Reporttemplatemain find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Reporttemplatemain> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Reporttemplatemain> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @POST
    @Path("createReportTemplate")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String  createReportTemplate(@FormParam("templateName") String templateName, @FormParam("isDefault") String isDefault,
    @FormParam("remark ") String remark, @FormParam("height") String height,
    @FormParam("width") String width, @FormParam("rptSbrptName") String rptSbrptName,
    @FormParam("rptHeight") String rptHeight, @FormParam("rptWidth") String rptWidth,
    @FormParam("rptLeft") String rptLeft, @FormParam("rptTop") String rptTop)
    {
       //1) save data in reportTemplateMain
       Reporttemplatemain  reporttemplatemain = new Reporttemplatemain();
       reporttemplatemain.setReportTempName(templateName);
       reporttemplatemain.setIsDefault(isDefault);
       reporttemplatemain.setRemark(remark);
       reporttemplatemain.setHeight(height);
       reporttemplatemain.setWidth(width);
       reporttemplatemain = super.createAndGet(reporttemplatemain);
       //2) get templateId
       //3) split rptTemplate data values
       String rptSbrptName_Split[] = rptSbrptName.split(",");
       String rptHeight_Split[] = rptHeight.split(",");
       String rptWidth_Split[] = rptWidth.split(",");
       String rptLeft_Split[] = rptLeft.split(",");
       String rptTop_Split[] = rptTop.split(",");
       
       for(int i=0;i<rptSbrptName_Split.length;i++)
       {
           Rpttemplate  rpttemplate = new Rpttemplate();
           RpttemplatePK rpttemplatePK = new RpttemplatePK();
           rpttemplatePK.setRptTempID(String.valueOf(reporttemplatemain.getTempID()));
           rpttemplatePK.setRptSbrptName(rptSbrptName_Split[i]);
           
           rpttemplate.setRpttemplatePK(rpttemplatePK);
           rpttemplate.setRptHeight(rptHeight_Split[i]);
           rpttemplate.setRptWidth(rptWidth_Split[i]);
           rpttemplate.setRptLeft(rptLeft_Split[i]);
           rpttemplate.setRptTop(rptTop_Split[i]);
        //4) save data along with template id in  rptTemplate
           rpttemplateFacadeREST.create(rpttemplate);
       }
      return "template saved successfully";
    }
    
    
    @PUT
    @Path("updateReportTemplate")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String  updateReportTemplate(@FormParam("templateId") String templateId ,@FormParam("templateName") String templateName, @FormParam("isDefault") String isDefault,
    @FormParam("remark ") String remark, @FormParam("height") String height,
    @FormParam("width") String width, @FormParam("rptSbrptName") String rptSbrptName,
    @FormParam("rptHeight") String rptHeight, @FormParam("rptWidth") String rptWidth,
    @FormParam("rptLeft") String rptLeft, @FormParam("rptTop") String rptTop)
    {
       //1) save data in reportTemplateMain
       Reporttemplatemain  reporttemplatemain = find(Integer.parseInt(templateId));
       
       reporttemplatemain.setReportTempName(templateName);
       reporttemplatemain.setIsDefault(isDefault);
       reporttemplatemain.setRemark(remark);
       reporttemplatemain.setHeight(height);
       reporttemplatemain.setWidth(width);
       super.edit(reporttemplatemain);
         //2) remove previous rows
       rpttemplateFacadeREST.removeByTempId(templateId);
       //3) get templateId
       //4) split rptTemplate data values
       String rptSbrptName_Split[] = rptSbrptName.split(",");
       String rptHeight_Split[] = rptHeight.split(",");
       String rptWidth_Split[] = rptWidth.split(",");
       String rptLeft_Split[] = rptLeft.split(",");
       String rptTop_Split[] = rptTop.split(",");
     
       
       for(int i=0;i<rptSbrptName_Split.length;i++)
       {
           Rpttemplate  rpttemplate = new Rpttemplate();
         
           RpttemplatePK rpttemplatePK = new RpttemplatePK();
           rpttemplatePK.setRptTempID(templateId);
           rpttemplatePK.setRptSbrptName(rptSbrptName_Split[i]);
           //rpttemplatePK.setRptTempID(templateId));
          // rpttemplatePK.setRptSbrptName(rptSbrptName_Split[i]);
           
           rpttemplate.setRpttemplatePK(rpttemplatePK);
           rpttemplate.setRptHeight(rptHeight_Split[i]);
           rpttemplate.setRptWidth(rptWidth_Split[i]);
           rpttemplate.setRptLeft(rptLeft_Split[i]);
           rpttemplate.setRptTop(rptTop_Split[i]);
        //5) add data in  rptTemplate
           rpttemplateFacadeREST.create(rpttemplate);
       }
      return "template updated successfully";
    }
    
   @DELETE
   @Path("removeByTempId/{tempId}")
   @Produces("text/plain")
   public String removeByTempId(@PathParam("tempId") String tempId)
   {
       remove(Integer.parseInt(tempId)); // remove from reportTemplateMain
       rpttemplateFacadeREST.removeByTempId(tempId);  // remove from rptTemplate
       return "deleted successfully";
   }
   
  
}
