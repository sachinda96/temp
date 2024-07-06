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
import javax.validation.constraints.NotNull;
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
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.circulation.TDamage;
import soul.catalogue.Location;
import soul.catalogue.MBkstatus;
import soul.catalogue.service.LocationFacadeREST;
import soul.catalogue.service.MBkstatusFacadeREST;
import soul.circulation.MMember;
import soul.circulation.TStockvarification;
import soul.response.StringprocessData;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;

/**
 *
 * @author admin
 */
@Stateless
@Path("soul.circulation.tdamage")
public class TDamageFacadeREST extends AbstractFacade<TDamage> {

    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private MBkstatusFacadeREST mBkStatusFacadeRest;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    ConvertStringIntoJson stringintojson;
    ConvertStringIntoXml stringintoxml;
  //  List<Location> locationList = new ArrayList<>();
  //  List<MBkstatus> statusDescription = new ArrayList<>();
  //  List<TStockvarification> stockVerification = new ArrayList<>();
   // Location location;
  //  String status;
    //TDamage damage;
    
    public TDamageFacadeREST() {
        super(TDamage.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TDamage entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(TDamage entity) {
        super.edit(entity);
    }

//    @PUT
//    @Override
//    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
//    public void edit(TDamage entity) {
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
    public TDamage find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TDamage> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TDamage> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<TDamage> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueSting = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findByAccNo":     valueList.add(valueSting[0]);
                                        break;
            default:    valueList.add(valueSting[0]);
                        break;
        }
        return super.findBy("TDamage."+query, valueList);
    }
    
    
    //This method is used to make entry for a book which is damaged and udate status of item to "DA" in location table
    @POST 
    @Path("markAsDamage")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public StringprocessData damagedBook(String accessionNo) {
        List<Location> loclist=null;
        StringprocessData spd;
        Location loc=null;
        String output="";
        String notprocess="";
        String accnos="";
        String datatype = accessionNo.substring(0,1);
        if(datatype.equals("{")){
            stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(accessionNo);
           
             accnos = jsonobj.getString("accessionNo");
           
        }else if(datatype.equals("<")){
            try {
                stringintoxml = new ConvertStringIntoXml();
                Document doc=stringintoxml.getxmldata(accessionNo);
                
                accnos = stringintoxml.getdatafromxmltag(doc,"accessionNo");
                
            } catch (Exception ex) {
                System.err.println("ex :"+ex.getMessage());
            }
        }
        
        String[] Accno =  accnos.split(",");
        for(int i=0;i<Accno.length;i++){
            loclist = locationFacadeREST.getByAcc(Accno[i]);
            if(loclist.size()>0){
                loc = loclist.get(0);
                if(loc.getStatus().equals("AV")){
                    TDamage damage = new TDamage();
                    damage.setAccNo(Accno[i]);
                    damage.setDamagedDt(new Date());
                    damage.setStatus("Damaged");
                    damage.setUserCd("null");
                    
                    create(damage);
                    
                    loc.setStatus("DA");
                    loc.setLastOperatedDT(new Date());
                    locationFacadeREST.edit(loc);
                    
                    output+=Accno[i]+" book is Damage.,";
                }
                else{
                    notprocess+=Accno[i]+" book status is not available.,";
                }
            }else{
                notprocess+=Accno[i]+" book is not available.,";
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
        spd =new StringprocessData();
        spd.setProcessdata(op);
        spd.setNonprocessdata(nonop);
        return spd;
    }
    
    @PUT
    @Path("markAsRepaired/{accessionNo}")
    @Produces({"application/xml", "application/json"})
    public StringprocessData Repaired(@PathParam ("accessionNo") String accessionNo) {
        StringprocessData spd;
        List<TDamage> litdamage=null;
        String output="";
        String notprocess="";
        String[] Accno =  accessionNo.split(",");
        
        for(int i=0;i<Accno.length;i++){
             litdamage = findBy("findByAccNo",Accno[i]);
             if(litdamage.size()>0){
                 
                 for(int j=0;j<litdamage.size();j++){
                     
                    TDamage repaired = litdamage.get(j);
                    
                    if(repaired.getStatus().equals("Damaged")){
                        
                        String statusCode = locationFacadeREST.getByAcc(Accno[i].trim()).get(0).getStatus();
               
                        if ("DA".equals(statusCode)) {

                            repaired.setStatus("Repaired");
                            repaired.setRepairedDt(new Date());
                            edit(repaired);
                            locationFacadeREST.repaired(Accno[i]);
                            output += Accno[i]+" damage.,";
                        } else {
                            notprocess += Accno[i]+" book status is not Damage.,";
                        }
                    }     
                }
                  
             } else {
                    notprocess += Accno[i]+" book not available.,";
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
        spd =new StringprocessData();
        spd.setProcessdata(op);
        spd.setNonprocessdata(nonop);
        return spd;
    } 
}
