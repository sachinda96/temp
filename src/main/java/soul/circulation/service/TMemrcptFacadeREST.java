/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import soul.circulation.TMemrcpt;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.tmemrcpt")
public class TMemrcptFacadeREST extends AbstractFacade<TMemrcpt> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public TMemrcptFacadeREST() {
        super(TMemrcpt.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TMemrcpt entity) {
        super.create(entity);
    }
    
    //Added Manually
    @POST
    @Override
    @Path("createAndGet")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public TMemrcpt createAndGet(TMemrcpt entity) {
        return super.createAndGet(entity);
    }


    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TMemrcpt entity) {
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
    public TMemrcpt find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TMemrcpt> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TMemrcpt> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("byDt/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<TMemrcpt> findByRcptDt(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");       
        List<Object> valueList = new ArrayList<>();        
        switch(query)
        {
            case "findByRcptDtBtwn": SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                                            try {
                                                valueList.add(dateFormat1.parse(valueString[0]));
                                                valueList.add(dateFormat1.parse(valueString[1]));
                                                        
                                            } catch (ParseException ex) {
                                                Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            break;
            case "findByMmCd":              valueList.add((valueString[0]));
                                            break;
            default:    valueList.add(valueString[0]);
                        break;
        }
        //System.out.println("TMemrcpt."+query + valueList);
        return super.findBy("TMemrcpt."+query, valueList);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    //Fine Payer
    //This method will return the list of members who paid fine between the specified dates by receipt date
    
    @GET
    @Path("GenerateReport/GetFinePayerbyReceiptDateBetween/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> GetFinePayerbyReceiptDateBetween(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
            case "byReceiptDateBetween":
                q = "SELECT     t_memrcpt.rcpt_no, t_memrcpt.rcpt_dt, t_memrcpt.slip_no, t_memrcpt.slip_dt, t_memrcpt.fine_desc, \n" +
                        "t_memrcpt.rcpt_amt, t_memrcpt.mem_cd, m_member.mem_tag,   m_member.mem_firstnm, m_member.mem_midnm, m_member.mem_lstnm,\n" +
                        " t_memrcpt.user_cd AS chq_drft_no, t_memfine.accn_no AS Bank_br  \n" +
                        " FROM t_memrcpt  LEFT OUTER JOIN  t_memfine ON t_memrcpt.mem_cd = t_memfine.mem_cd AND t_memrcpt.slip_no = t_memfine.slip_no \n" +
                        " LEFT OUTER JOIN  m_member ON t_memrcpt.mem_cd = m_member.mem_cd \n" +
                        " AND t_memrcpt.mem_cd = m_member.mem_cd  \n" +
                        " where  t_memrcpt.rcpt_dt BETWEEN '" + valueString[0] + "' AND '" + valueString[1] + "'";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
    
    //Receipt Generation
    // THis method will return the payment information of member   
    @GET
    @Path("ReceiptGeneration/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> ReceiptGeneration(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
            case "bySlipNo":
                q = " SELECT  distinct   t_memrcpt.slip_no, t_memrcpt.fine_desc, t_memrcpt.slip_dt, t_memrcpt.mem_cd, t_memrcpt.rcpt_no, t_memrcpt.rcpt_dt, \n" +
                        " t_memrcpt.rcpt_amt, t_memrcpt.chq_drft_no,  t_memrcpt.Bank_br, m_member.mem_tag, m_member.mem_firstnm, m_member.mem_midnm, \n" +
                        " m_member.mem_lstnm,t_memfine.accn_no,  Biblidetails.FValue,t_memfine.fine_amt  \n" +
                        " FROM  t_memrcpt, m_member, Biblidetails, Location, t_memfine,t_lost \n" +
                        " Where  t_memfine.mem_cd = m_member.mem_cd and Biblidetails.RecID = Location.RecID  and t_lost.acc_no = Location.p852 and \n" +
                        " t_memfine.slip_no = t_memrcpt.slip_no and Biblidetails.tag ='245' and biblidetails.sbfld in ('a') and location.p852 = t_memfine.accn_no  and \n" +
                        " t_memrcpt.slip_no = '" + Paramvalue + "' ";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
    
}
