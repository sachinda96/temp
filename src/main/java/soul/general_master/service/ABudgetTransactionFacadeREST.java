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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import soul.general_master.ABudgetTransaction;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.abudgettransaction")
public class ABudgetTransactionFacadeREST extends AbstractFacade<ABudgetTransaction> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public ABudgetTransactionFacadeREST() {
        super(ABudgetTransaction.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(ABudgetTransaction entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(ABudgetTransaction entity) {
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
    public ABudgetTransaction find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("SingleResultBy/{namedQuery}/{attrValues}")
    @Produces({"application/xml", "application/json"})
    public ABudgetTransaction findSingleResultBy(@PathParam("namedQuery") String query, @PathParam("attrValues") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findByBudgetCodesYear1234":  valueList.add(valueString[0]);
                                               valueList.add(valueString[1]);
                                               valueList.add(valueString[2]);
                                               valueList.add(valueString[3]);
                                               valueList.add(valueString[4]);
                                               break;
        }
        System.out.println(valueList.size());
        return super.findSingleResultBy("ABudgetTransaction."+query, valueList);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<ABudgetTransaction> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<ABudgetTransaction> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
}
