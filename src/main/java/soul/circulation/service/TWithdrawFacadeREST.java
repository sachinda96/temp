/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

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
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.circulation.TDamage;
import soul.circulation.TWithdraw;
import soul.catalogue.Location;
import soul.catalogue.MBkstatus;
import soul.catalogue.service.LocationFacadeREST;
import soul.catalogue.service.MBkstatusFacadeREST;
import soul.circulation.MMember;
import soul.response.StringprocessData;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;

/**
 *
 * @author admin
 */
@Stateless
@Path("soul.circulation.twithdraw")
public class TWithdrawFacadeREST extends AbstractFacade<TWithdraw> {

    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private MBkstatusFacadeREST mBkStatusFacadeRest;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    Date TodayDate = new Date();
    ConvertStringIntoJson stringintojson;
    ConvertStringIntoXml stringintoxml;
   // private String[] accNos;
   // TWithdraw withdraw;
  //  TWithdraw reintroduced = null;
  //  List<Location> locationList = new ArrayList<>();
  //  List<MBkstatus> statusDescription = new ArrayList<>();
   // Location location;
  //  String status;

    public TWithdrawFacadeREST() {
        super(TWithdraw.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TWithdraw entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(TWithdraw entity) {
        super.edit(entity);
    }
//    
//    @PUT
//    @Consumes({"application/x-www-form-urlencoded"})
//    public void edit(TWithdraw entity) {
//        super.edit(entity);
//    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public TWithdraw find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TWithdraw> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TWithdraw> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<TWithdraw> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueSting = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findByAccNo":     valueList.add(valueSting[0]);
                                        break;
            default:    valueList.add(valueSting[0]);
                        break;
        }
        return super.findBy("TWithdraw."+query, valueList);
    }
    
    //This method is used to make entry for a book which is withdrawl and udate status of item to "WI" in location table
    @POST 
    @Path("withdrawBook")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public StringprocessData withdrawBook(String accessionNos) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess="";
        String accnos="";
        String datatype = accessionNos.substring(0,1);
        if(datatype.equals("{")){
            stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(accessionNos);
             accnos = jsonobj.getString("accnoNdep");
          
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(accessionNos);
                accnos = stringintoxml.getdatafromxmltag(doc,"accnoNdep");
                
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }
        
        
        String accnoNdepvalue[] = accnos.split(",");
        List<Location> loclist=null;
        for(int i=0;i<accnoNdepvalue.length;i++){
            String findvalue[] = accnoNdepvalue[i].split("-");
            String accno = findvalue[0];
            String depvalue = findvalue[1];
            loclist = locationFacadeREST.getByAcc(accno);
            if(loclist.size()>0){
                Location ln = loclist.get(0);
                if(ln.getStatus().equals("AV")){
                    String prices;
                    if(ln.getPrice().isEmpty() || ln.getPrice().equals(" ")){
                        prices="0";
                    }else{
                        prices=ln.getPrice();
                    }
                    TWithdraw tw = new TWithdraw();
                    tw.setAccNo(accno);
                    tw.setDepreciatedPrice(depvalue);
                    tw.setOriginalPrice(prices);
                    tw.setWithdrawBy("Superuser");
                    tw.setReintroducedBy("F");
                    tw.setWithdrawDt(TodayDate);
                    
                    create(tw);
                    
                    ln.setStatus("WI");
                    ln.setLastOperatedDT(TodayDate);
                    locationFacadeREST.edit(ln);
                    
                    output+=accno+" withdraw.,";
                }else{
                    notprocess+=accno+" book status is not available.,";
                }
            }else{
                notprocess+=accno+" book is not available.,";
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
        
        spd.setProcessdata(op);
        spd.setNonprocessdata(nonop);
        return spd;
    }  
    
    //This service is used for making the entry of reintroducing book into library 
    //reintroduce book in t_withdraw table and udate status of item to "AV" in location table
    @PUT
    @Path("reintroduceBook")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public StringprocessData reintroduceBook(String reintroducesdata) {
        StringprocessData spd = new StringprocessData();  
        
        String datatype =reintroducesdata.substring(0,1);
        String accessionNos="";
        if(datatype.equals("{")){
            stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(reintroducesdata);
            
             accessionNos = jsonobj.getString("accessionNos");
            
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(reintroducesdata);
                
                accessionNos = stringintoxml.getdatafromxmltag(doc,"accessionNos");
               
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }
        
        String[] accno = accessionNos.split(",");
        List<Location> liloc = null;
        String output="";
        String notprocess="";
        List<TWithdraw> litw=null;
        
        for(int i=0;i<accno.length;i++){
            liloc = locationFacadeREST.getByAcc(accno[i]);
            if(liloc.size()>0){
                Location ln = liloc.get(0);
                
                litw=findBy("findByAccNo",accno[i]);
                if(litw.size()>0){
                    for(int j=0;j<litw.size();j++){
                        TWithdraw tw = litw.get(j);
                        if(tw.getReintroducedBy().equals("F")){
                            if(ln.getStatus().equals("WI")){
                                tw.setReintroducedBy("Superuser");
                                tw.setReintroducedDt(TodayDate);
                            
                                edit(tw);
                            
                                ln.setStatus("AV");
                                locationFacadeREST.edit(ln);
                            
                                output+=accno[i]+" reintroduce.,";
                            }else{
                                notprocess+=accno[i]+" book status is not withdraw.,";
                            }    
                        }
                    }
                }else{
                     notprocess+=accno[i]+" book not availabe.,";  
                }
                
            }else{
                notprocess+=accno[i]+" book not availabe.,";    
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
        
        spd.setProcessdata(op);
        spd.setNonprocessdata(nonop);    
        return spd;
    }
        }         

