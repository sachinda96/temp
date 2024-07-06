/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.catalogue.Location;
import soul.catalogue.MissingBook;
import soul.catalogue.TMissing;
import soul.catalogue.TMissingBiblocation;
import soul.circulation.MMember;
import soul.circulation.service.MMemberFacadeREST;
import soul.response.StringData;
import soul.response.StringprocessData;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;
import soul.util.function.DateNTimeChange;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.tmissing")
public class TMissingFacadeREST extends AbstractFacade<TMissing> {
    @EJB
    private TMissingBiblocationFacadeREST tMissingBiblocationFacadeREST;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private MMemberFacadeREST mMemberFacadeREST;
    @EJB
    private MBkstatusFacadeREST mBkstatusFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    ConvertStringIntoJson stringintojson;
    ConvertStringIntoXml stringintoxml;
   // String output;
   // TMissing missing;
  //  MMember member;
  //  Location location;
  //  StringData sd;
  //  StringprocessData spd;
    public TMissingFacadeREST() {
        super(TMissing.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TMissing entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TMissing entity) {
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
    public TMissing find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TMissing> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TMissing> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("byQuery/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<Object> findByQuery(@PathParam("namedQuery") String namedQuery, @PathParam("values") String values) {
        String q="";
        Query query;
        switch(namedQuery)
        {
            /*case "forFoundProcess": q = "SELECT Biblidetails_1.FValue, Biblidetails.FValue AS Expr1, \n"
                                    + "m_bkstatus.status_dscr, t_missing.mem_cd_miss_rep, t_missing.missing_date, t_missing.ID\n"
                                    + "FROM  Location INNER JOIN\n"
                                    + "Biblidetails AS Biblidetails_1 ON Location.RecID = Biblidetails_1.RecID INNER JOIN\n"
                                    + "Biblidetails ON Location.RecID = Biblidetails.RecID INNER JOIN\n"
                                    + "t_missing ON Location.p852 = t_missing.bk_accno INNER JOIN\n"
                                    + "m_bkstatus ON Location.Status = m_bkstatus.bk_issue_stat\n"
                                    + "where Biblidetails.SbFld='a' and Biblidetails.Tag='245' and \n"
                                    + "Biblidetails_1.SbFld='a' and Biblidetails_1.Tag='100' \n"
                                    + "and Location.p852='" + values + "' \n"
                                    + "and mem_cd_founder is null";
                                    break;*/
        }
        List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        
        return result;
    }
    
    @GET
    @Path("byDate/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<TMissing> byDt(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");       
        List<Object> valueList = new ArrayList<>();         
        switch(query)
        {
            case "findByMissDtBtwn": SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                                            try {
                                                valueList.add(dateFormat1.parse(valueString[0]));
                                                valueList.add(dateFormat1.parse(valueString[1]));
                                                        
                                            } catch (ParseException ex) {
                                                Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            break;
            default:    valueList.add(valueString[0]);
                        break;
        }
        //System.out.println("TMemrcpt."+query + valueList);
        return super.findBy("TMissing."+query, valueList);
    }
    
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<TMissing> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueSting = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findByIssueStat":     valueList.add(valueSting[0]);
                                        break;
            case "findByBkAccno":     valueList.add(valueSting[0]);
                                        break;                            
            default:    valueList.add(valueSting[0]);
                        break;
        }
        return super.findBy("TMissing."+query, valueList);
    }
   
    @GET
    @Path("retrieveAll/{accessionNo}/{form}")
    @Produces({"application/xml", "application/json"})
  //  public Response retrieveAll(@QueryParam("accessionNo") String accessionNo,@QueryParam("form") String form)
    public Response retrieveAll(@PathParam("accessionNo") String accessionNo,@PathParam("form") String form)        
    {
        Response.ResponseBuilder responseBuilder = Response.status(200);
     //   List<TMissing> list;
     //   List<TMissingBiblocation> list_biblocation;
        if("missingForm".equals(form))
         {
             List<TMissing> tMissing_list = findBy("findByIssueStat", "MI");
             GenericEntity<List<TMissing>> list1 = new GenericEntity<List<TMissing>>(tMissing_list) {};
             responseBuilder.entity(list1);
         }
        if("foundForm".equals(form))
         {
              List<TMissingBiblocation>  tMissingBiblocation_list = tMissingBiblocationFacadeREST.findBy("findByBkAccnoAndMissing", accessionNo);
              GenericEntity<List<TMissingBiblocation>> list2 = new GenericEntity<List<TMissingBiblocation>>(tMissingBiblocation_list) {};
              responseBuilder.entity(list2);
         }
          return responseBuilder.build();
    }
    
    @POST
    @Path("missing")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData missing(String missingdata) {
         StringprocessData spd;
         MMember member;
         TMissing missing;
        List<Location> listloc;
        Location locations;
        String notprocess="";
        String output="";
        String memcode="";
        String accno="";
        String datatype = missingdata.substring(0,1);
        if(datatype.equals("{")){
            stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(missingdata);
           
             memcode = jsonobj.getString("memcd");
             accno = jsonobj.getString("accno");
            
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(missingdata);
              
                memcode = stringintoxml.getdatafromxmltag(doc,"memcd");
                accno = stringintoxml.getdatafromxmltag(doc,"accno");
                
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }
        
        String[] Accno = accno.split(",");
         member = mMemberFacadeREST.find(memcode);
        for(int i=0;i<Accno.length;i++){
            listloc=locationFacadeREST.findBy("findByP852", Accno[i]);
            if(listloc.size()>0 && member!=null){
                locations=listloc.get(0);
                
                if(locations.getStatus().equals("AV") || locations.getStatus().equals("AR")){
                    
                        missing = new TMissing();
                        missing.setBkAccno(Accno[i]);
                        missing.setMemCdMissRep(member);
                        missing.setUserCd("super user");        //need to be changed according to logged in user
                        missing.setIssueStat("MI");
                        missing.setMissingDate(new Date());

                        create(missing);
                        
                        // Updation of location entry (Change in status)
                        locations.setStatus("MI");
                        locationFacadeREST.edit(locations);
                        
                        output +=Accno[i]+" missing.,";
                   
                }else{
                notprocess+=Accno[i]+" book status is not available or on-hold.,";
            }
            }else{
                notprocess+=Accno[i]+" book not available.,";
            }
        }
        String op;
        String nonop;
        if(output.length()>1){
            op = output.substring(0,output.length()-1);
        }else{
            op=output;
        }
        if(notprocess.length()>1){
            nonop = notprocess.substring(0,notprocess.length()-1);
        }else{
            nonop=notprocess;
        }
        spd = new StringprocessData();
        spd.setProcessdata(op);
        spd.setNonprocessdata(nonop);
        return spd;
    }
    
    @PUT
    @Path("found")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
     public StringprocessData found(String founddata)
    {    
        StringprocessData spd;
        List<Location> liloc =null;
        List<TMissing> litmis=null;
        String output="";
        String notprocess="";
        DateNTimeChange dt = new DateNTimeChange();
        String accno="";
        String founddt="";
        String memcd="";
        String datatype = founddata.substring(0,1);
        
        if(datatype.equals("{")){
            stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(founddata);
            accno = jsonobj.getString("accno");
             founddt = jsonobj.getString("founddt");
             memcd = jsonobj.getString("memcd");
            
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(founddata);
                accno = stringintoxml.getdatafromxmltag(doc,"accno");
                founddt = stringintoxml.getdatafromxmltag(doc,"founddt");
                memcd = stringintoxml.getdatafromxmltag(doc,"memcd");
                
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }
        
        MMember m = mMemberFacadeREST.find(memcd);
        liloc = locationFacadeREST.findBy("findByP852", accno);
        if(liloc.size()>0 && m!=null){
            Location ln = liloc.get(0);
            if(ln.getStatus().equals("MI")){
                litmis=findBy("findByBkAccno",accno);
                if(litmis.size()>0){
                    for(int i=0;i<litmis.size();i++){
                        TMissing tm = litmis.get(i);
                        if(tm.getIssueStat().equals("MI")){
                            tm.setMemCdFounder(m);
                            tm.setFoundDate(dt.getDateNTimechange(founddt));
                            tm.setIssueStat("AV");
                            
                            edit(tm);
                            
                            ln.setStatus("AV");
                            locationFacadeREST.edit(ln);
                            
                            output=accno+" Found..";
                        }
                    }
                }
            }else{
                notprocess=accno+" book status is not missing..";
            }
        }else{
            notprocess=accno+" book not available ..";
        }
        
        
        spd = new StringprocessData();
        spd.setProcessdata(output);
        spd.setNonprocessdata(notprocess);
        return spd;
    }
     
    @GET
    @Path("getMissingBooks")
    @Produces({"application/xml", "application/json"})
    public List<MissingBook> getMissingBooks() throws ParseException {
        String q = "select tm.bk_accno as accno,tm.mem_cd_miss_rep as memcd,mm.mem_firstnm as FirstName,mm.mem_lstnm as lastName,tm.missing_date as missingDate,bk.status_dscr as status,b1.fvalue as title,b2.fvalue as author from t_missing tm,m_member mm,location ln,m_bkstatus bk,biblidetails b1,biblidetails b2 where tm.mem_cd_miss_rep=mm.mem_cd and tm.bk_accno=ln.p852 and ln.status=bk.bk_issue_stat and ln.recid=b1.recid and b1.tag='245' and b1.sbfld='a' and b1.recid=b2.recid and b2.tag='100' and b2.sbfld='a' and tm.issue_stat='MI' ";
        List<MissingBook> result = new ArrayList<>();
        Query query;  
        query = getEntityManager().createNativeQuery(q,MissingBook.class);
        result = (List<MissingBook>) query.getResultList();
        return result;
    }
    
}
