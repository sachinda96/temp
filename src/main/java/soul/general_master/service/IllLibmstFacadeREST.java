/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import soul.general_master.IllLibmst;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.illlibmst")
public class IllLibmstFacadeREST extends AbstractFacade<IllLibmst> {

    @PersistenceContext(unitName = "SoulRestAppPU")

    private EntityManager em;
    IllLibmst library = new IllLibmst();
    int count;
    String output;
    List<IllLibmst> libList = new ArrayList<>();

    public IllLibmstFacadeREST() {
        super(IllLibmst.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(IllLibmst entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(IllLibmst entity) {
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
    public IllLibmst find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<IllLibmst> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<IllLibmst> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("by/{attribute}/{value}")
    @Produces({"application/xml", "application/json"})
    public List<IllLibmst> findBy(@PathParam("attribute") String attribute, @PathParam("value") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();

        switch (attribute) {
            case "findByLibCd":
                valueList.add(valueString[0]);
                break;
            case "findByLibNm":
                valueList.add(valueString[0]);
                break;
            default:
                valueList.add(valueString[0]);
                //used for findByInstDeptTag
                break;
        }
        return super.findBy("IllLibmst." + attribute, valueList);
    }

    
     @GET
    @Path("GetdepartmentalLib")
    @Produces({"application/xml", "application/json"})
    public List<IllLibmst> GETAllLibraires() {
        List<IllLibmst> IllLibmstList = null;
        List<Object> valueList = new ArrayList<>();
        valueList.add("DL");
        IllLibmstList = super.findBy("IllLibmst.findByInstDeptTag", valueList);
        return IllLibmstList;
    }

    @GET
    @Path("GetinstitutionalLib")
    @Produces({"application/xml", "application/json"})
    public List<IllLibmst> retrieveAllILLibrary() {
         List<Object> valueList = new ArrayList<>();
        valueList.add("IL");
        List<IllLibmst> libList = super.findBy("IllLibmst.findByInstDeptTag", valueList);
        return libList;
    }

    @GET
    @Path("getCode/{nameCode}")
    @Produces({"application/xml", "application/json"})
   // public String getCode(@QueryParam("nameCode") String nameCode) {
    public String getCode(@PathParam("nameCode") String nameCode) {    
        //  request.setAttribute("operation", "GetCode");
        String code = null;
        for (int i = 1; i < 999; i++) {
            code = null;
            if (i < 10) {
                code = nameCode + "00" + i;
            } else if (i >= 10 && i < 100) {
                code = nameCode + "0" + i;
            } else {
                code = nameCode + i;
            }

            library = find(code);
            if (library == null) {
                break;
            } else {
                continue;
            }
        }
        return code;
    }

    @POST
    @Path("createOrSave")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrSave(Form form, @FormParam("operation") String operation,
            @FormParam("code") String code, @FormParam("libType") String libType,
            @FormParam("name") String name, @FormParam("contact") String contact,
            @FormParam("address") String address, @FormParam("librarian") String librarian,
            @FormParam("city") String city, @FormParam("fax") String fax,
            @FormParam("pin") String pin, @FormParam("phone") String phone,
            @FormParam("effFrom") String effFrom, @FormParam("effTo") String effTo,
            @FormParam("email") String email, @FormParam("remarks") String remarks) {
        if (operation.equals("Create")) {
            count = Integer.parseInt(countREST());

            library.setInstDeptTag(libType);
            library.setLibCd(code);
            library.setLibNm(name);
            library.setLibAdd1(address);
            library.setLibCity(city);
            library.setLibPin(pin);
            library.setLibPhone(phone);
            library.setLibFax(fax);
            library.setLibrarianNm(librarian);
            library.setLibCntPrsn(contact);
            Date date = new Date();
            try {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(effFrom);
                System.out.println("in new lib:" + date);
            } catch (ParseException ex) {
                Logger.getLogger(IllLibmstFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                library.setEfctDtFrm(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(effFrom));
                library.setEfctDtTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(effTo));
            } catch (ParseException ex) {
                Logger.getLogger(IllLibmstFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            library.setLibEmail(email);
            library.setRemarks(remarks);
            create(library);
            if (count == Integer.parseInt(countREST())) {
                output = "Someting went wrong New Library is not created!!!";
            } else {
                output = "New Library Created!!!";
            }
        } else if (operation.equals("Save")) {
            IllLibmst library = find(code);
            library.setInstDeptTag(libType);
            library.setLibNm(name);
            library.setLibAdd1(address);
            library.setLibCity(city);
            library.setLibPin(pin);
            library.setLibPhone(phone);
            library.setLibFax(fax);
            library.setLibrarianNm(librarian);
            library.setLibCntPrsn(contact);
            Date date = new Date();
            try {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(effFrom);
                System.out.println("in new lib:" + date);
            } catch (ParseException ex) {
                Logger.getLogger(IllLibmstFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                library.setEfctDtFrm(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(effFrom));
                library.setEfctDtTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(effTo));
            } catch (ParseException ex) {
                Logger.getLogger(IllLibmstFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            library.setLibEmail(email);
            library.setRemarks(remarks);
            edit(library);
            output = "Library Information Updated!!!";
        } else {
            output = "Invalid operation!!!";
        }
        return output;
    }

    @DELETE
    @Path("deleteNewLibrary/{code}")
    @Produces("text/plain")
    public String deleteNewLibrary(@PathParam("code") String code) {
        count = Integer.parseInt(countREST());
        remove(code);
        if (count == Integer.parseInt(countREST())) {
            output = "Someting went wrong record is not deleted!";
        } else {
            output = "OK";
        }
        return output;
    }
}
