/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master.service;

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
import soul.errorresponce.ValidationErrorlistfields;
import soul.errorresponse.service.ValidationException;
import soul.general_master.Countrymaster;
import soul.response.postdata;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.countrymaster")
public class CountrymasterFacadeREST extends AbstractFacade<Countrymaster> {

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    Countrymaster country = null;
    String code = "";
    String output = "";
    int count = 0;
     postdata pd;
     
    public CountrymasterFacadeREST() {
        super(Countrymaster.class);
    }

    @POST
  // @Override
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public postdata createq(Countrymaster entity) throws ValidationException {
        String rs;
        
        // validation 
                List<ValidationErrorlistfields> exceptionlist = new ArrayList<>();
                Countrymaster cm = find(entity.getCode());
                if(cm != null){
                   exceptionlist.add(new ValidationErrorlistfields("Country is exsits in database","10","Country Code"));
                }
                if(exceptionlist.size()>0){
                    throw new ValidationException("Duplicate data",exceptionlist);
                }
                
                //  System.out.println("call.. in country master");
               // Save into datavse        
                super.create(entity);
             // prepare response to send         
                rs = "Country Code : "+entity.getCode() +" and Country Name "+entity.getCountry();
                List<Object> li = new ArrayList<Object>();
                li.add(findAll());
                pd = new postdata("/webresources/soul.general_master.countrymaster",rs); 
       // return prepared response
        return pd;
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Countrymaster entity) {
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
    public Countrymaster find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Countrymaster> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Countrymaster> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    @GET
    @Path("retrieveAll/{accept}/{form}")
    @Produces({"application/xml", "application/json"})
   // public List<Countrymaster> retrieveAll(@QueryParam("accept") String accept, @QueryParam("form") String form) {
    public List<Countrymaster> retrieveAll(@PathParam("accept") String accept, @PathParam("form") String form) {    
        List<Countrymaster> getAll = new ArrayList<>();
        if (accept.equals("XML")) {
            getAll = findAll();
        }
        return getAll;
    }

    @GET
    @Path("getAllCountries")
    @Produces({"application/xml", "application/json"})
    public List<Countrymaster> getAllCountries() {
        List<Countrymaster> getAll = new ArrayList<>();
        getAll = findAll();
        return getAll;
    }

    @POST
    @Path("createOrSave")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrSave(Form form, @FormParam("operation") String operation) {
        if (operation.equals("Create")) {
            count = Integer.parseInt(countREST());
            country = new Countrymaster();
            country.setCode(form.asMap().getFirst("code"));
            country.setCountry(form.asMap().getFirst("country").toUpperCase());
            create(country);
            if (count == Integer.parseInt(countREST())) {
                output = "Something went wrong, record is not inserted!!";
            } else {
                output = "Country Added!!!";
            }
        }
        if (operation.equals("Save")) {
            country = new Countrymaster();
            country.setCode(form.asMap().getFirst("code"));
            country.setCountry(form.asMap().getFirst("country"));
            edit(country);
            output = "Country Information Updated!!";
        }
        return output;
    }

    @DELETE
    @Path("deleteCountry/{code}")
    @Produces("text/plain")
    public String deleteCountry(@PathParam("code") String code) {
        count = Integer.parseInt(countREST());
        remove(code);
        if (count == Integer.parseInt(countREST())) {
            output = "Someting went. Wrong record is not deleted!!";
        } else {
            output = "Country record deleted!!!";
        }
        return output;
    }

}
