/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.catalogue.Location;
import soul.catalogue.service.LocationFacadeREST;
import soul.circulation.MMember;
import soul.circulation.TIssue;
import soul.circulation.TMemdue;
import soul.circulation.TMemfine;
import soul.circulation.TOtherissue;
import soul.circulation.TOtherreturn;
import soul.circulation.TOtherreturnPK;
import soul.circulation.TReceive;
import soul.circulation.TReceivePK;
import soul.circulation.TReserve;
import soul.response.StringprocessData;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;

/**
 *
 * @author admin
 */
@Stateless
@Path("soul.circulation.totherreturn")
public class TOtherreturnFacadeREST extends AbstractFacade<TOtherreturn> {

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private TMemfineFacadeREST tMemfineFacadeREST;
    @EJB
    private TMemdueFacadeREST tMemdueFacadeREST;
    @EJB
    private TMemrcptFacadeREST tMemrcptFacadeREST;
    @EJB
    private TOtherissueFacadeREST otherissueFacadeREST;
    @EJB
    private MMemberFacadeREST mMemberFacadeREST;
    Date Todaydate = new Date();

    private TOtherreturnPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;memCd=memCdValue;accNo=accNoValue;issueDate=issueDateValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.circulation.TOtherreturnPK key = new soul.circulation.TOtherreturnPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> memCd = map.get("memCd");
        if (memCd != null && !memCd.isEmpty()) {
            key.setMemCd(memCd.get(0));
        }
        java.util.List<String> accNo = map.get("accNo");
        if (accNo != null && !accNo.isEmpty()) {
            key.setAccNo(accNo.get(0));
        }
        java.util.List<String> issueDate = map.get("issueDate");
        if (issueDate != null && !issueDate.isEmpty()) {
            key.setIssueDate(new java.util.Date(issueDate.get(0)));
        }
        return key;
    }

    public TOtherreturnFacadeREST() {
        super(TOtherreturn.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TOtherreturn entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, TOtherreturn entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.circulation.TOtherreturnPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public TOtherreturn find(@PathParam("id") PathSegment id) {
        soul.circulation.TOtherreturnPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TOtherreturn> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TOtherreturn> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @POST
    @Path("return")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData returnbook(String accnosdata) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String accno="";
        String datatype = accnosdata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(accnosdata);
            accno = jsonobj.getString("accno");
          
        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(accnosdata);
                accno = stringintoxml.getdatafromxmltag(doc, "accno");
          
            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }
        
        String[] accnos = accno.split(",");
        
        for(int i=0;i<accnos.length;i++){
            List<Location> liloc=null;
                liloc=locationFacadeREST.getByAcc(accnos[i]);
                if(liloc.size()>0){
                    Location ln=null;
                    ln = liloc.get(0);
                    if(ln.getStatus().equals("OP") || ln.getStatus().equals("ON")) {
                        String reson ="";
                        if(ln.getStatus().equals("OP")){
                            reson="Return from on primises";
                        }else{
                            reson="Return from over night";
                        }
                        List<TOtherissue>litissue=null;
                        litissue=otherissueFacadeREST.findBy("findByAccNo", accnos[i]);
                        if(litissue.size()>0){
                            MMember m =null;
                            m= mMemberFacadeREST.find(litissue.get(0).getMMember().getMemCd());
                            if(m!=null){
                               int dueprocess=0;
                               BigDecimal fineamt;
                               if(litissue.get(0).getDueDate().before(Todaydate)){
                                   fineamt =BigDecimal.valueOf(Double.valueOf("0.00"));
                                   // fineamt = getdueofbook(m.getMemCtgry().getCtgryCd(),ln.getMaterial().getCode(),litissue.get(0).getDueDt());
                                    if(fineamt.compareTo(BigDecimal.valueOf(Double.valueOf("0.00")))==1){
                                        TMemfine fine = new TMemfine();
                                        fine.setAccnNo(accnos[i]);
                                        fine.setFineAmt(fineamt);
                                        fine.setMemCd(m.getMemCd());
                                        fine.setIssDt(litissue.get(0).getIssueDate());
                                        fine.setRetDt(litissue.get(0).getDueDate());
                                        fine.setSlipDt(Todaydate);
                                        fine.setUserCd("Superuser");
                                        fine.setFineDesc("issue OverDue");
                                        
                                        fine = tMemfineFacadeREST.createAndGet(fine);
                                        
                                        // due enter in memdue table
                                        TMemdue due = new TMemdue();
                                        due.setDueAmt(fine.getFineAmt());
                                        due.setMMember(m);
                                        due.setTMemfine(fine);
                                        due.setSlipDt(fine.getSlipDt());
                                        due.setSlipNo(fine.getSlipNo());
                                        tMemdueFacadeREST.create(due);
                                        
                                        TOtherreturnPK torpk = new TOtherreturnPK();
                                        torpk.setAccNo(accnos[i]);
                                        torpk.setMemCd(m.getMemCd());
                                        torpk.setIssueDate(litissue.get(0).getIssueDate());
                                            
                                        TOtherreturn tor = new TOtherreturn();
                                        tor.setTOtherreturnPK(torpk);
                                        tor.setReturnDate(Todaydate);
                                        tor.setReturnRemarks(litissue.get(0).getIssueRemarks());
                                        tor.setReturnReson(reson);
                                        
                                        create(tor);
                                        
                                        otherissueFacadeREST.removebyMemcdNAccno(m.getMemCd(),accnos[i]);
                                        
                                        output += accnos[i]+" Book is retrn to library -"+fineamt.toString()+"-"+fine.getSlipNo()+",";
                                    }else{
                                        // retrun book
                                        TOtherreturnPK torpk = new TOtherreturnPK();
                                        torpk.setAccNo(accnos[i]);
                                        torpk.setMemCd(m.getMemCd());
                                        torpk.setIssueDate(litissue.get(0).getIssueDate());
                                            
                                        TOtherreturn tor = new TOtherreturn();
                                        tor.setTOtherreturnPK(torpk);
                                        tor.setReturnDate(Todaydate);
                                        tor.setReturnRemarks(litissue.get(0).getIssueRemarks());
                                        tor.setReturnReson(reson);
                                        
                                        create(tor);
                                        
                                        otherissueFacadeREST.removebyMemcdNAccno(m.getMemCd(),accnos[i]);
                                        
                                        output += accnos[i]+" Book is retrn to library,";
                                        
                                    }
                               }else{
                                   // return book
                                   
                                        TOtherreturnPK torpk = new TOtherreturnPK();
                                        torpk.setAccNo(accnos[i]);
                                        torpk.setMemCd(m.getMemCd());
                                        torpk.setIssueDate(litissue.get(0).getIssueDate());
                                            
                                        TOtherreturn tor = new TOtherreturn();
                                        tor.setTOtherreturnPK(torpk);
                                        tor.setReturnDate(Todaydate);
                                        tor.setReturnRemarks(litissue.get(0).getIssueRemarks());
                                        tor.setReturnReson(reson);
                                        
                                        create(tor);
                                        
                                        otherissueFacadeREST.removebyMemcdNAccno(m.getMemCd(),accnos[i]);
                                        
                                        output += accnos[i]+" Book is retrn to library,";
                               }
                               
                              
                               ln.setStatus("AV");
                               
                               locationFacadeREST.edit(ln);
                               
                            }else{
                                notprocess+=accnos[i]+" Member data not found,";
                            }
                        }else{
                            notprocess+=accnos[i]+" Book not found,";
                        }
                    }else{
                        notprocess+=accnos[i]+" Book status is not On primises or Over night issue,";
                    }
                }else{
                    notprocess+=accnos[i]+" Book not available,";
                }
        }
        
        String op;
        String nonop;
        if (output.length() > 1) {
            op = output.substring(0, output.length() - 1);
        } else {
            op = output;
        }
        if (notprocess.length() > 1) {
            nonop = notprocess.substring(0, notprocess.length() - 1);
        } else {
            nonop = notprocess;
        }

        spd.setProcessdata(op);
        spd.setNonprocessdata(nonop);
        return spd;
     }    
}
