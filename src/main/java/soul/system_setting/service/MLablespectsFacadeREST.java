/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.system_setting.service;

import com.sun.xml.wss.impl.policy.MLSPolicy;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import soul.circulation.MMember;
import soul.system_setting.MLablespects;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.system_setting.mlablespects")
public class MLablespectsFacadeREST extends AbstractFacade<MLablespects> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    MLablespects label = new MLablespects();
    int count;
    String output;
    
    public MLablespectsFacadeREST() {
        super(MLablespects.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(MLablespects entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MLablespects entity) {
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
    public MLablespects find(@PathParam("id") String id) throws UnsupportedEncodingException {
        return super.find(URLDecoder.decode(id, "UTF-8") );
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MLablespects> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MLablespects> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    //added manually
    
    @GET
    @Path("retrieveAll/{accept}")
    @Produces({"application/xml", "application/json"})
   // public List<MLablespects> retrieveAll(@QueryParam("accept") String accept,@QueryParam("form") String form)
     public List<MLablespects> retrieveAll(@PathParam("accept") String accept)        
    {
        List<MLablespects> getAll = null;
        if(accept.equals("XML"))
        {
            getAll = findAll();
        }
        return getAll;
    }
    
   @GET
   @Path("GetTemplateNames")
   @Produces({"application/xml", "application/json"})
   public List<MLablespects>  GetTemplateNames()
   {    
       
        List<MLablespects> label = findAll();  
        return label;
   }
    
    @DELETE
    @Path("deleteLabelSettings/{labelName}")
    @Produces("text/plain")
    public String deleteLabelSettings(@PathParam("labelName") String labelName)
    {
        count = Integer.parseInt(countREST());
        System.out.println(labelName);
        remove(labelName);
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
            label = getLabel(form);
            count = Integer.parseInt(countREST());
            create(label);
            if(count == Integer.parseInt(countREST()))
            {
                output = "Someting went wrong Label Setting record is not created!";
            }
            else
            {
                output = "Label Setting record created.";
            }
        }
        if(operation.equals("Save"))
        {
           //label = find(form.asMap().getFirst("code"));
            label = getLabel(form);
            edit(label);
            output = "Label Setting record updated.";
        }
         return output;
    }
    
     public MLablespects getLabel(Form form)
    {
        MLablespects label = new MLablespects();
        
        label.setLableName(form.asMap().getFirst("labelName"));
        label.setPageHeight(Double.parseDouble(form.asMap().getFirst("height")));
        label.setPageWidth(Double.parseDouble(form.asMap().getFirst("width")));
        label.setTopMargin(Double.parseDouble(form.asMap().getFirst("topMargin")));
        label.setBottomMargin(Double.parseDouble(form.asMap().getFirst("bottomMargin")));
        label.setRightMargin(Double.parseDouble(form.asMap().getFirst("rightMargin")));
        label.setLeftMargin(Double.parseDouble(form.asMap().getFirst("leftMargin")));
        label.setNoOfColumns(Integer.parseInt(form.asMap().getFirst("ncolumns")));
        label.setColumnSpace(Double.parseDouble(form.asMap().getFirst("columnsSpace")));
        label.setNoOfRows(Integer.parseInt(form.asMap().getFirst("nrows")));
        label.setRowSpace(Double.parseDouble(form.asMap().getFirst("rowsSpace")));
        
        return label;
    }
}
