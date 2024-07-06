/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;
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
import soul.general_master.ACurrency;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.acurrency")
public class ACurrencyFacadeREST extends AbstractFacade<ACurrency> {

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    List<ACurrency> currencyList = new ArrayList();
    ACurrency currency = new ACurrency();
    int count = 0;
    String output = "";

    public ACurrencyFacadeREST() {
        super(ACurrency.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(ACurrency entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(ACurrency entity) {
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
    public ACurrency find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<ACurrency> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<ACurrency> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
   // public List<ACurrency> retrieveAll(@QueryParam("accept") String accept) {
    public List<ACurrency> retrieveAll() {        
        currencyList = findAll();
        return currencyList;
//        else if(accept.equals("ObjectList"))
//        {
//            request.setAttribute("currencies", currencyList);
//        }
    }

    //Added Manually
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<ACurrency> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        switch (query) {
            case "findByACCd":
                valueList.add(valueString[0]);
                break;
            case "findByDateBetween": {
                try {
                    valueList.add(dateFormat.parse(valueString[0]));
                    valueList.add(dateFormat.parse(valueString[1]));
                } catch (ParseException ex) {
                    Logger.getLogger(ACurrencyFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            break;
            case "findByDateGtr":
                try {
                    valueList.add(dateFormat.parse(valueString[0]));
                } catch (ParseException ex) {
                    Logger.getLogger(ACurrencyFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            default:
                valueList.add(valueString[0]);
                break;
            //

        }
        return super.findBy("ACurrency." + query, valueList);
    }

    @GET
    @Path("retrieveAllForAcquiInvoice")
    @Produces("text/plain")
   // public String retrieveAllForAcquiInvoice(@QueryParam("accept") String accept) {
     public String retrieveAllForAcquiInvoice() {    
        currencyList = findAll();
        String currencyNames = "";
        String currencyCodes = "";
        String rates = "";
        String currencies = "";
        if (currencyList != null) {
            for (ACurrency c : currencyList) {
                currencyCodes = currencyCodes + "," + c.getACCd();
                currencyNames = currencyNames + "," + c.getACNm();
                rates = rates + "," + c.getACRate();
            }
            currencies = currencyCodes.substring(1) + "|" + currencyNames.substring(1) + "|" + rates.substring(1);
        }
        return currencies;
    }

    @POST
    @Path("addCurrency")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String addCurrency(@FormParam("currencyCode") String currencyCode,
            @FormParam("country") String country, @FormParam("conversionRate") BigDecimal conversionRate,
            @FormParam("currency") String currencyName, @FormParam("date") String date) {
        currency.setACCd(currencyCode);
        currency.setACNm(currencyName);
        currency.setACRate(conversionRate);
        try {
            currency.setACDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(ACurrencyFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        currency.setACCountry(country);
        count = Integer.parseInt(countREST());
        create(currency);
        if (count == Integer.parseInt(countREST())) {
            output = "Someting went wrong, Failed to insert currency!!";
            System.out.println("Not created!!");
        } else {
            output = "New Currency Inserted!!";
        }
        return output;
    }

    @POST
    @Path("updateCurrency")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String updateCurrency(@FormParam("currencyCode") String currencyCode,
            @FormParam("country") String country, @FormParam("conversionRate") BigDecimal conversionRate,
            @FormParam("currency") String currencyName, @FormParam("date") String date) {
        currency = find(currencyCode);
        currency.setACNm(currencyName);
        currency.setACRate(conversionRate);
        try {
            currency.setACDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(ACurrencyFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        currency.setACCountry(country);
        output = "Currency " + currency.getACNm() + " Updated.";
        return output;
    }

    @DELETE
    @Path("deleteCurrency/{currencyCode}")
    @Produces("text/plain")
    public String deleteCurrency(@PathParam("currencyCode") String currencyCode) {
        count = Integer.parseInt(countREST());

        try {
            remove(currencyCode);
        } catch (IllegalArgumentException d) {
            return "Invalid currency code, or currency with code " + currencyCode + " does not exist";
        }

        if (count == (Integer.parseInt(countREST()))) {
            output = "Something went wrong, Record is not Deleted!!";
        } else {
            output = "Currency record deleted!!!";
        }
        return output;
    }

    @GET
    @Path("currencyReport/{paramName}/{date}")
    @Produces({"application/xml", "application/json"})
    public List<ACurrency> findOrdersForPurchasing(@PathParam("paramName") String paramName, @PathParam("date") String date) {
        String q = "";
        String[] valueString = date.split(",");
        List<ACurrency> result = new ArrayList<>();
        switch (paramName) {

            case "dateBetween":
                result = findBy("findByDateBetween", date);
                break;

            case "dateGtrThen":
                result = findBy("findByDateGtr", date);
                break;

        }
        return result;
    }
}
