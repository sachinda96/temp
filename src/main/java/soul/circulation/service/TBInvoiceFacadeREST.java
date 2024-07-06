/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

//import com.sun.org.apache.xpath.internal.axes.LocPathIterator;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import soul.catalogue.BiblidetailsLocation;
import soul.catalogue.Location;
import soul.catalogue.service.LocationFacadeREST;
import soul.circulation.TBInvoice;
import soul.circulation.TBPayment;
import soul.circulation.TBinding;
import soul.general_master.service.ABudgetFacadeREST;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.tbinvoice")
public class TBInvoiceFacadeREST extends AbstractFacade<TBInvoice> {
    @EJB
    private TBindingFacadeREST tBindingFacadeREST;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private ABudgetFacadeREST aBudgetFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output;
    TBinding binding;
    TBInvoice invoice;
    TBPayment payment;
    String[] bindNos;
    List<TBinding> bindingList = new ArrayList<>();
    BiblidetailsLocation bibLocation;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
    BigDecimal subtractFromCommit;
       
    public TBInvoiceFacadeREST() {
        super(TBInvoice.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TBInvoice entity) {
        super.create(entity);
    }
    
    @POST
    @Path("createAndGet")
    @Override
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public TBInvoice createAndGet(TBInvoice entity) {
        return super.createAndGet(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TBInvoice entity) {
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
    public TBInvoice find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TBInvoice> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TBInvoice> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    //Added Manually
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<TBInvoice> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(query)
        {
          
            case "findByBInvFlag":  valueList.add(Integer.parseInt(valueString[0]));
                                    break;
            default:    valueList.add(Integer.parseInt(valueString[0]));
        }
        return super.findBy("TBInvoice."+query, valueList);
    }
    
    //Added by Dashrath
    //Binding items list by order no of binding
    @GET
    @Path("InvoiceDetailsbyInvoiceNo/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public  List<TBInvoice> InvoiceDetailsbyInvoiceNo(@PathParam("searchParameter") String param)
    {   List<TBInvoice> list = null;       
        list = findBy("findByBInvNo", param);
        return list;
     }
    
    @GET  // used in binding
    @Path("checkInvoiceNo/{invoiceNo}")
    @Produces("text/plain")
   // public String checkInvoiceNo(@QueryParam("invoiceNo") String invoiceNo)
    public String checkInvoiceNo(@PathParam("invoiceNo") String invoiceNo)        
    {
        List<TBInvoice> invoiceList = new ArrayList<>();
        invoice = find(Integer.parseInt(invoiceNo));
        if(invoice == null)
            {
                output = "NotUsed";
            }
        else
            {
                output = "AlreadyUsed";
            }
        return output;  
    }
    
    
    @POST  // used in binding to generate invoice
    @Path("createInvoice")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createInvoice(@FormParam("bindNos") String bind_Nos,@FormParam("invoiceNo") String invoiceNo,
    @FormParam("invoiceOrderNo") String invoiceOrderNo,@FormParam("invoiceDate") String invoiceDate,
    @FormParam("invoiceReceiveDate") String invoiceReceiveDate,@FormParam("forwardingDate") String forwardingDate,
    @FormParam("orderedDocuments") String orderedDocuments,@FormParam("invoiceAmount") String invoiceAmount,
    @FormParam("discountAmt") String discountAmt,@FormParam("overdueAmt") String overdueAmt,
    @FormParam("miscAmt") String miscAmt,@FormParam("netAmt") String netAmt)
     {
        String[] bindNos = bind_Nos.split(",");
        invoice = new TBInvoice();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        invoice.setBInvNo(Integer.parseInt(invoiceNo));
        invoice.setBInvOrderno(invoiceOrderNo);
        try {
            invoice.setBInvDt(dateFormat.parse(invoiceDate));
            invoice.setBInvRcptdt(dateFormat.parse(invoiceReceiveDate));
            invoice.setBInvForwardDt(dateFormat.parse(forwardingDate));
        } catch (ParseException ex) {
            Logger.getLogger(TBindingFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        invoice.setBInvOrderset(Integer.parseInt(orderedDocuments));
        invoice.setBInvPrice(new BigDecimal(invoiceAmount));
        invoice.setBInvDiscount(new BigDecimal(discountAmt)); 
        invoice.setBInvOverdue(new BigDecimal(overdueAmt)); 
        invoice.setBInvMisamt(new BigDecimal(miscAmt));
        invoice.setBInvNetamt(new BigDecimal(netAmt));
        invoice.setBInvFlag(0);
        
      //  invoice = getInvoice();
        invoice.setBInvRcptset(bindNos.length);

        invoice = createAndGet(invoice);                                    

        for(int i=0; i<bindNos.length; i++)
        {
            TBinding binding = tBindingFacadeREST.find(Integer.parseInt(bindNos[i]));
            binding.setBFlag(1);
            tBindingFacadeREST.edit(binding);

            Location location = locationFacadeREST.findBy("findByP852", binding.getBAccessNo()).get(0);
            location.setStatus("AV");
            locationFacadeREST.edit(location);
        }

        //Subtracts sum of Misc., Discount and Overdue from commit
        subtractFromCommit = invoice.getBInvDiscount().add(invoice.getBInvMisamt()).add(invoice.getBInvOverdue()).negate();
        binding = tBindingFacadeREST.find(Integer.parseInt(bindNos[0]));
        aBudgetFacadeREST.commit(Integer.toString(binding.getBBudgetCd()), subtractFromCommit.toString(), "include");
       // request.setAttribute("operation", "Commit");
       // request.setAttribute("budgetCode", Integer.toString(binding.getBBudgetCd()));
       // request.setAttribute("accept", "include");
       // request.setAttribute("commitAmount", subtractFromCommit.toString());
       // request.getRequestDispatcher("/BudgetServlet").include(request, response);
        output = "Invoice Generated";
        return output;
    }
    
//     public static TBInvoice getInvoice(String invoiceNo,String invoiceOrderNo)
//    {
//       
//        
//        return invoice;
//    }
}
