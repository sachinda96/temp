/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.acquisition.suggestions.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import javax.ws.rs.core.Form;
import soul.acquisition.suggestions.AAccession;
import soul.acquisition.suggestions.AOrder;
import soul.acquisition.suggestions.AOrderPK;
import soul.acquisition.suggestions.AOrdermaster;
import soul.acquisition.suggestions.AReceive;
import soul.acquisition.suggestions.ARequest;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.acquisition.suggestions.areceive")
public class AReceiveFacadeREST extends AbstractFacade<AReceive> {
    @EJB
    private AOrderFacadeREST aOrderFacadeREST;
    @EJB
    private ARequestFacadeREST aRequestFacadeREST;
    @EJB
    private AAccessionFacadeREST aAccessionFacadeREST;
    @EJB
    private AOrdermasterFacadeREST aOrdermasterFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    List<AOrder> orderList = new ArrayList<>();
    ARequest req = new ARequest();
    AOrder order = new AOrder();
    AOrdermaster orderMaster = new  AOrdermaster();
    String output;
    
     public AReceiveFacadeREST() {
        super(AReceive.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(AReceive entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(AReceive entity) {
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
    public AReceive find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<AReceive> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<AReceive> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    
    //addded manually
   
    @POST
    @Path("receiveOrder")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String receiveOrder(Form form,@FormParam("itemNos") String item_Nos,
    @FormParam("selectedAll") String selectedAll,@FormParam("itemNo1") String itemNo1)
    {
        int itemNos = Integer.parseInt(item_Nos);
        
        for(int ii=1; ii-1<itemNos; ii++)
        {   
            String[] itemNo = form.asMap().getFirst("itemNo"+ii).split(";");
            String aRNo = itemNo[1].split("=")[1];
            String aOPoNo = itemNo[2].split("=")[1];
            System.out.println("aRNo  "+aRNo);
           // String aOPoNo = form.asMap().get("itemNo1").get(1);
            System.out.println("aOPoNo  "+aOPoNo);
            AOrderPK aorderPk = new AOrderPK();
            aorderPk.setARNo(Integer.parseInt(aRNo));
            aorderPk.setAOPoNo(Integer.parseInt(aOPoNo));
           
            order = aOrderFacadeREST.find(aorderPk);
            order.setARStatus("I");

            int totalReceive = order.getAOTotalreceivedCopy()+Integer.parseInt(form.asMap().getFirst("currentReceiving"+ii));
            System.out.println("totalReceive  "+order.getAOTotalreceivedCopy()+"  "+Integer.parseInt(form.asMap().getFirst("currentReceiving"+ii)));
            order.setAOTotalreceivedCopy(totalReceive);
            order.setAOPendingCopy(order.getAOOrderedCopy()-order.getAOTotalreceivedCopy());

            req = order.getARequest();
            if(req.getARStatus().equals("D"))           //partial ordered(J) request can not be said as "I" received 
                req.setARStatus("I");                   //otherwise remaining qty of these request will not be available for order
            aRequestFacadeREST.edit(req);
            aOrderFacadeREST.edit(order);

            //this part is to make entry in ||| a_receive ||| table
            AReceive receive = new AReceive();

            receive.setARequest(req);
            receive.setAOPoNo(order.getAOrdermaster());
            receive.setAIReceivedCopy(Integer.parseInt(form.asMap().getFirst("currentReceiving"+ii)));
            receive.setAIReceivedDt(new Date());

            create(receive);

            //this part is to make entry in ||| a_accession ||| table
            for(int i=0; i<receive.getAIReceivedCopy(); i++)
            {
                AAccession accession = new AAccession();

                accession.setARequest(req);
                accession.setARecDt(new Date());

                aAccessionFacadeREST.create(accession);
            }
        }
        output = itemNos+" Item(s) received.";

        //Below this for closing order if every item under a order is reveived
        if(selectedAll.equals("true"))
        {
            Boolean closeOrder = true;
            //genericType = new GenericType<List<AOrder>>(){};
            String[] itemNo_ = itemNo1.split(";");
            String aRNo = itemNo_[1].split("=")[1];
            String aOPoNo = itemNo_[2].split("=")[1];
            System.out.println("aRNo  "+aRNo);
           // String aOPoNo = form.asMap().get("itemNo1").get(1);
            System.out.println("aOPoNo  "+aOPoNo);
            AOrderPK aorderPk = new AOrderPK();
            aorderPk.setARNo(Integer.parseInt(aRNo));
            aorderPk.setAOPoNo(Integer.parseInt(aOPoNo));
            String orderMasterNo = Integer.toString(aOrderFacadeREST.find(aorderPk).getAOrderPK().getAOPoNo());
            orderList = aOrderFacadeREST.findBy("findByAOPoNo", orderMasterNo);
          //  orderList = (List<AOrder>) restResponse.readEntity(genericType);
             AOrder o = new AOrder();
            for (Iterator<AOrder> it = orderList.iterator(); it.hasNext();) {
                o = it.next();
                if(o.getAOOrderedCopy() != o.getAOTotalreceivedCopy())
                {
                    closeOrder = false;
                }
            }
            orderMaster = aOrdermasterFacadeREST.find(Integer.parseInt(orderMasterNo));
            if(closeOrder == true)
            {
                orderMaster.setAOStatus("X");
                aOrdermasterFacadeREST.edit(orderMaster);
                output = "Complete order Received and order is closed.";
            }
        }
        //response.sendRedirect("./jsp/receiveOrder.jsp");
       output = "Order Received.";
       return output;
     }
}
