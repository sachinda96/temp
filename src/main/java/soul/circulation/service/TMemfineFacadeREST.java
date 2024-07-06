/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.json.JSONObject;
import soul.circulation.TIssue;
import soul.circulation.TMemdue;
import soul.circulation.TMemfine;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.tmemfine")
public class TMemfineFacadeREST extends AbstractFacade<TMemfine> {
    @EJB
    private TIssueFacadeREST tIssueFacadeREST;
    @EJB
    private TMemdueFacadeREST tMemdueFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output="", memCd, accNo;
    String[] accNos;
    TIssue issue;
    TMemdue due;
    TMemfine fine;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MM-yyyy");
    Map<String, String> accNoSlipNo = new HashMap<>();
    
    public TMemfineFacadeREST() {
        super(TMemfine.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TMemfine entity) {
        super.create(entity);
    }
    
    //Added Manually
    @POST
    @Override
    @Path("createAndGet")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public TMemfine createAndGet(TMemfine entity) {
        return super.createAndGet(entity);
    }


    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TMemfine entity) {
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
    public TMemfine find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TMemfine> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TMemfine> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    // added manually
    
    // used in transaction menu to pay fine and return the items
//    @POST
//    @Path("returnAndSaveFine")
//    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
//    @Produces("text/plain")
//    public String returnAndSaveFine(@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{accNo.pattern}") @FormParam("accNos") String acc_Nos,
//            @FormParam("fine") String fine_Amounts,
//            @FormParam("slipDate") String slipDate, @FormParam("reason") String reason,
//            @FormParam("formName") String formName) {
//        accNos = acc_Nos.split(",");
//        String[] fineAmounts = fine_Amounts.split(",");
// 
//        for(int i=0; i<accNos.length; i++)
//        {
//            try{
//               issue = tIssueFacadeREST.findBy("findByAccNo",accNos[i]).get(0); 
//           }catch(ArrayIndexOutOfBoundsException e){
//               return "Invalid accession no, or book with \"Accession no: "+accNos[i]+"\" is not issued, so cannot be returned.";               
//           }
//              
//            fine = new TMemfine();
//            fine.setAccnNo(accNos[i]);
//            fine.setFineAmt(new BigDecimal(fineAmounts[i]));
//            fine.setMemCd(issue.getMMember().getMemCd());
//            fine.setIssDt(issue.getIssDt());
//            fine.setRetDt(new Date());
//            try {
//                fine.setSlipDt(format.parse(slipDate));
//            }
//            catch (ParseException ex) {
//                Logger.getLogger(TMemfineFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            fine.setUserCd("superuser");        //Make it dynamic
//            fine.setFineDesc(reason);
//
//            fine = createAndGet(fine);
//            accNoSlipNo.put(fine.getAccnNo(), fine.getSlipNo().toString());
//
//            due = new TMemdue();
//            due.setDueAmt(fine.getFineAmt());
//            due.setMMember(issue.getMMember());
//            due.setTMemfine(fine);
//            due.setSlipDt(fine.getSlipDt());
//            due.setSlipNo(fine.getSlipNo());
//
//            due = tMemdueFacadeREST.createAndGet(due);
//        }
//        //Remove issue entry and Make receive entry and update location entry
//        System.out.println("accNoSlipNo(Fine ) "+accNoSlipNo);
//        //tIssueFacadeREST.accNoSlipNo = accNoSlipNo;
//        output =  tIssueFacadeREST.returnIssue(acc_Nos ,accNoSlipNo);
//        return output;
//       
//    }
    

    @POST
    @Path("returnfine")
    @Consumes({"application/xml", "application/json"})
    @Produces("text/plain")
    public String returnFine(String dueData) {
        
        JSONObject jsonObject = new JSONObject(dueData);
        System.out.println(" Json : "+dueData);
        
        String acc_Nos = jsonObject.getString("acc_Nos").toString();
        String fine_Amounts=jsonObject.getString("fine_Amounts").toString();
        String slipDate=jsonObject.getString("slipDate").toString();
        String reason=jsonObject.getString("reason").toString();
        
        System.out.println("acc_Nos----"+acc_Nos);
        System.out.println("amountPaying----"+fine_Amounts);
        System.out.println("receiptDate----"+slipDate);
        System.out.println("reason----"+reason);
        
        accNos = acc_Nos.split(",");
        String[] fineAmounts = fine_Amounts.split(",");
 
        for(int i=0; i<accNos.length; i++)
        {
            try{
               issue = tIssueFacadeREST.findBy("findByAccNo",accNos[i]).get(0); 
           }catch(ArrayIndexOutOfBoundsException e){
               return "Invalid accession no, or book with \"Accession no: "+accNos[i]+"\" is not issued, so cannot be returned.";               
        }
            
            fine = new TMemfine();
            fine.setAccnNo(accNos[i]);
            fine.setFineAmt(new BigDecimal(fineAmounts[i]));
            fine.setMemCd(issue.getMMember().getMemCd());
            fine.setIssDt(issue.getIssDt());
            fine.setRetDt(new Date());
            try {
                fine.setSlipDt(format.parse(slipDate));
            }
            catch (ParseException ex) {
                Logger.getLogger(TMemfineFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            fine.setUserCd("superuser");        //Make it dynamic
            fine.setFineDesc(reason);
            fine = createAndGet(fine);
            accNoSlipNo.put(fine.getAccnNo(), fine.getSlipNo().toString());

            due = new TMemdue();
            due.setDueAmt(fine.getFineAmt());
            due.setMMember(issue.getMMember());
            due.setTMemfine(fine);
            due.setSlipDt(fine.getSlipDt());
            due.setSlipNo(fine.getSlipNo());
            due = tMemdueFacadeREST.createAndGet(due);
        }
        //Remove issue entry and Make receive entry and update location entry
        System.out.println("accNoSlipNo(Fine ) "+accNoSlipNo);
        //tIssueFacadeREST.accNoSlipNo = accNoSlipNo;
       // tIssueFacadeREST.returnIssue(acc_Nos ,accNoSlipNo);
       
       ObjectMapper objectMapper = new ObjectMapper();

        try {
            String json = objectMapper.writeValueAsString(accNoSlipNo);
            System.out.println(json);
            output =json;
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return output;
       
    }
    
    @GET
    @Path("GenerateReport/{findBy}/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<Object> GenerateReport(@PathParam("findBy") String Paramname, @PathParam("searchParameter") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;        
        switch (Paramname) {
            case "GetFinePayersByUserName":
                q = "select t_memrcpt.rcpt_no, t_memrcpt.rcpt_dt, t_memrcpt.slip_no, t_memrcpt.slip_dt, t_memrcpt.fine_desc, t_memrcpt.rcpt_amt, t_memrcpt.mem_cd, t_memrcpt.user_cd,\n"
                        + " m_member.mem_tag, m_member.mem_firstnm, m_member.mem_lstnm, m_member.mem_midnm, t_memrcpt.user_cd, t_memfine.accn_no\n" 
                        + " from t_memrcpt \n" 
                        + " LEFT OUTER JOIN  t_memfine ON t_memrcpt.mem_cd = t_memfine.mem_cd AND t_memrcpt.slip_no = t_memfine.slip_no \n" 
                        + " LEFT OUTER JOIN  m_member ON t_memrcpt.mem_cd = m_member.mem_cd \n"
                        + " WHERE t_memrcpt.user_cd = '" + Paramvalue + "'";
                break;
                
            case "GenerateSlipByMemberCode":
                q = "SELECT m_member.mem_cd, m_member.mem_firstnm, m_member.mem_lstnm, t_memrcpt.slip_no, \n" +
                        "   t_memrcpt.slip_dt,  t_memrcpt.rcpt_no, t_memrcpt.rcpt_dt, t_memrcpt.rcpt_amt FROM m_member, t_memrcpt  where m_member.mem_cd = t_memrcpt.mem_cd \n"
                        + "   and t_memrcpt.slip_no in ( SELECT t_memfine.slip_no \n"
                        + "   FROM t_memfine\n"
                        + "   where t_memfine.mem_cd = '" + Paramvalue + "' )\n"
                        + "   and t_memrcpt.mem_cd = '" + Paramvalue + "' and t_memrcpt.rcpt_no= (select (max(rcpt_no))  from t_memrcpt)";
                break;
                
                case "GenerateSlipBySlipNo":
                q = "SELECT m_member.mem_cd, m_member.mem_firstnm, m_member.mem_lstnm, t_memrcpt.slip_no, \n" +
                        "   t_memrcpt.slip_dt,  t_memrcpt.rcpt_no, t_memrcpt.rcpt_dt, t_memrcpt.rcpt_amt FROM m_member, t_memrcpt  where m_member.mem_cd = t_memrcpt.mem_cd \n"
                        + "   and t_memrcpt.slip_no in ( SELECT t_memfine.slip_no \n"
                        + "   FROM t_memfine\n"
                        + "   where t_memfine.slip_no = '" + Paramvalue + "' )\n"
                        + "   and t_memrcpt.slip_no = '" + Paramvalue + "' and t_memrcpt.rcpt_no= (select (max(rcpt_no))  from t_memrcpt)";
                break; 
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
}
