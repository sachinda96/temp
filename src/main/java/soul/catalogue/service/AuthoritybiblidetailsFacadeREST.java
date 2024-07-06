/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue.service;

import java.util.ArrayList;
import java.util.List;
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
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import soul.catalogue.Authoritybiblidetails;
import soul.catalogue.AuthoritybiblidetailsPK;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.authoritybiblidetails")
public class AuthoritybiblidetailsFacadeREST extends AbstractFacade<Authoritybiblidetails> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    private AuthoritybiblidetailsPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;recID=recIDValue;tagSrNo=tagSrNoValue;tag=tagValue;sbFldSrNo=sbFldSrNoValue;sbFld=sbFldValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.catalogue.AuthoritybiblidetailsPK key = new soul.catalogue.AuthoritybiblidetailsPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> recID = map.get("recID");
        if (recID != null && !recID.isEmpty()) {
            key.setRecID(new java.lang.Integer(recID.get(0)));
        }
        java.util.List<String> tagSrNo = map.get("tagSrNo");
        if (tagSrNo != null && !tagSrNo.isEmpty()) {
            key.setTagSrNo(new java.lang.Integer(tagSrNo.get(0)));
        }
        java.util.List<String> tag = map.get("tag");
        if (tag != null && !tag.isEmpty()) {
            key.setTag(tag.get(0));
        }
        java.util.List<String> sbFldSrNo = map.get("sbFldSrNo");
        if (sbFldSrNo != null && !sbFldSrNo.isEmpty()) {
            key.setSbFldSrNo(new java.lang.Integer(sbFldSrNo.get(0)));
        }
        java.util.List<String> sbFld = map.get("sbFld");
        if (sbFld != null && !sbFld.isEmpty()) {
            key.setSbFld(sbFld.get(0));
        }
        return key;
    }

    public AuthoritybiblidetailsFacadeREST() {
        super(Authoritybiblidetails.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Authoritybiblidetails entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Authoritybiblidetails entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.catalogue.AuthoritybiblidetailsPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Authoritybiblidetails find(@PathParam("id") PathSegment id) {
        soul.catalogue.AuthoritybiblidetailsPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Authoritybiblidetails> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Authoritybiblidetails> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    
    //added manually
    
    @POST
    @Path("addAuthorityTemplateDataEntry")
    @Consumes({"application/xml", "application/json","application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public Response addTemplateDataEntry(@FormParam("tagSrNo") String tagSrNo ,@FormParam("tagArray") String tagArray,
    @FormParam("sbfldSrNo") String sbfldSrNo, @FormParam("sbfldArray") String sbfldArray, @FormParam("hasIndArray") String hasIndArray,
    @FormParam("fval") String fval)
    {
        try
        {
               // *save authorityBibliDetails*
               create(tagSrNo,tagArray,sbfldSrNo,sbfldArray,hasIndArray,fval);
        }
        catch (Exception e) 
            {
                return Response.status(500).entity("error").build();
                 
            }
                return  Response.status(200).entity("saved successfully").build();
    }

    
    @PUT
    @Path("updateAuthorityTemplateDataEntry")
    @Consumes({"application/xml", "application/json","application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public Response updateAuthorityTemplateDataEntry(@FormParam("currentRecId") String currentRecId,@FormParam("tagSrNo") String tagSrNo ,@FormParam("tagArray") String tagArray,
    @FormParam("sbfldSrNo") String sbfldSrNo, @FormParam("sbfldArray") String sbfldArray, @FormParam("hasIndArray") String hasIndArray,
    @FormParam("fval") String fval)
    {
        try
        {
                 // *save authorityBibliDetails*
            update(currentRecId,tagSrNo,tagArray,sbfldSrNo,sbfldArray,hasIndArray,fval);
        }
        catch (Exception e) 
            {
                return Response.status(500).entity("error").build();
                 
            }
               return  Response.status(200).entity("updated succesfully").build();
    }
    
    @POST
    @Path("add/{tagSrNo}/{tagArray}/{sbfldSrNo}/{sbfldArray}/{hasIndArray}/{fval}")
    @Consumes({"application/xml", "application/json"})
   /* public String create(@QueryParam("tagSrNo") String tagSrNo ,@QueryParam("tagArray") String tagArray,
    @QueryParam("sbfldSrNo") String sbfldSrNo, @QueryParam("sbfldArray") String sbfldArray, @QueryParam("hasIndArray") String hasIndArray ,
    @QueryParam("fval") String fval)
    {*/
    public String create(@PathParam("tagSrNo") String tagSrNo ,@PathParam("tagArray") String tagArray,
    @PathParam("sbfldSrNo") String sbfldSrNo, @PathParam("sbfldArray") String sbfldArray, @PathParam("hasIndArray") String hasIndArray ,
    @PathParam("fval") String fval)
    {    
      
        String output = null;
        // splitting each parameter
        String tagSrNoData[] = tagSrNo.split("~",-1);
        String tagArrayData[] = tagArray.split("~",-1);
        String sbfldSrNoData[] = sbfldSrNo.split("~",-1);
        String sbfldArrayData[] = sbfldArray.split("~",-1);
        String hasIndArrayData[] = hasIndArray.split("~",-1);
        String fvalData[] = fval.split("~",-1);
        // to get next id
         int nextRecId;
         int countRows = count();
         if(countRows == 0)
         {
             nextRecId = 1;
         }
         else
         {
            List<Authoritybiblidetails> countRecId = findBy("findByMaxRecID", "NULL");
            nextRecId = (countRecId.get(0).getAuthoritybiblidetailsPK().getRecID() +1) ;
            System.out.println("nextRecId  "+countRecId.get(0).getAuthoritybiblidetailsPK().getRecID());
         }
        
        for(int i=0;i<tagSrNoData.length;i++)
        {
           Authoritybiblidetails authoritybiblidetails = new Authoritybiblidetails();
           AuthoritybiblidetailsPK authoritybiblidetailsPK = new AuthoritybiblidetailsPK();
           if(!fvalData[i].isEmpty())
           {
           authoritybiblidetailsPK.setRecID(nextRecId);
           authoritybiblidetailsPK.setTagSrNo(Integer.parseInt(tagSrNoData[i]));
           authoritybiblidetailsPK.setTag(tagArrayData[i]);
           authoritybiblidetailsPK.setSbFldSrNo(Integer.parseInt(sbfldSrNoData[i]));
           authoritybiblidetailsPK.setSbFld(sbfldArrayData[i]);
           if(hasIndArrayData[i].contentEquals("no"))
           {
              authoritybiblidetails.setInd("");
           }
           else{
               authoritybiblidetails.setInd(hasIndArrayData[i]);
           }
        //   authoritybiblidetails.setInd(hasIndArrayData[i]);
           authoritybiblidetails.setFValue(fvalData[i]);
           authoritybiblidetails.setAuthoritybiblidetailsPK(authoritybiblidetailsPK); 
           int count = Integer.parseInt(countREST());
           super.create(authoritybiblidetails);
           if(Integer.parseInt(countREST()) != count)
                   {

                       output = "success";
                   }
                   else
                   {
                       removeBy("deleteByRecID", String.valueOf((count+1))); 
                       output = "Data not saved";
                   }
           }
        }
        return output;
    }
    
    @PUT
   // @POST
    @Path("update/{currentRecId}/{tagSrNo}/{tagArray}/{sbfldSrNo}/{sbfldArray}/{hasIndArray}/{fval}")
    @Consumes({"application/xml", "application/json"})
   /* public void update(@QueryParam("currentRecId") String currentRecId ,@QueryParam("tagSrNo") String tagSrNo ,@QueryParam("tagArray") String tagArray,
    @QueryParam("sbfldSrNo") String sbfldSrNo, @QueryParam("sbfldArray") String sbfldArray, @QueryParam("hasIndArray") String hasIndArray ,
    @QueryParam("fval") String fval)
    {*/
    public void update(@PathParam("currentRecId") String currentRecId ,@PathParam("tagSrNo") String tagSrNo ,@PathParam("tagArray") String tagArray,
    @PathParam("sbfldSrNo") String sbfldSrNo, @PathParam("sbfldArray") String sbfldArray, @PathParam("hasIndArray") String hasIndArray ,
    @PathParam("fval") String fval)
    {    
        int currentRecIdInt = Integer.parseInt(currentRecId);
        System.out.println("currentRecId  "+currentRecId);
        System.out.println("editdata............"+tagSrNo+"....."+tagArray+"..."+sbfldSrNo+".."+sbfldArray+".."+hasIndArray+".."+fval);  
                           //request.getRequestDispatcher("country.jsp").forward(request, response);
                           //response.sendRedirect("country.jsp");
                        //   out.println(output);
        String edittagSrNoData[] = tagSrNo.split("~",-1);
        String edittagArrayData[] = tagArray.split("~",-1);
        String editsbfldSrNoData[] = sbfldSrNo.split("~",-1);
        String editsbfldArrayData[] = sbfldArray.split("~",-1);
        String edithasIndArrayData[] = hasIndArray.split("~",-1);
        String editfvalData[] = fval.split("~",-1);
       // int RecId = biblidetailsClient.findByQuery_XML(Integer.class, "findMaxRecId", "null");
        System.out.println("edittagSrNoData "+edittagSrNoData.length);
        System.out.println("edithasIndArrayData "+edithasIndArrayData.length);
        System.out.println("edittagArrayData "+edittagArrayData.length);
        System.out.println("editsbfldSrNoData "+editsbfldSrNoData.length);
        System.out.println("editsbfldArrayData "+editsbfldArrayData.length);
        System.out.println("editfvalData "+editfvalData.length);
        for(int i=0;i<edittagSrNoData.length;i++)
       {
           Authoritybiblidetails authoritybiblidetails = new Authoritybiblidetails();
           AuthoritybiblidetailsPK authoritybiblidetailsPK = new AuthoritybiblidetailsPK();   
           if(!editfvalData[i].isEmpty())
           {

           authoritybiblidetailsPK.setRecID(currentRecIdInt);
           authoritybiblidetailsPK.setTagSrNo(Integer.parseInt(edittagSrNoData[i]));
           authoritybiblidetailsPK.setTag(edittagArrayData[i]);
           authoritybiblidetailsPK.setSbFldSrNo(Integer.parseInt(editsbfldSrNoData[i]));
           authoritybiblidetailsPK.setSbFld(editsbfldArrayData[i]);
           if(!edithasIndArrayData[i].contentEquals("no"))
           {
              authoritybiblidetails.setInd(edithasIndArrayData[i]);
           }
        //   authoritybiblidetails.setInd(hasIndArrayData[i]);
               System.out.println("data...........update.. "+i+" "+currentRecIdInt+" "+edittagSrNoData[i]+" "+edittagArrayData[i]+" "+editsbfldSrNoData[i]+"  "+editsbfldArrayData[i]+" "+editsbfldSrNoData[i]+" "+editfvalData[i]);
           authoritybiblidetails.setFValue(editfvalData[i]);
           authoritybiblidetails.setAuthoritybiblidetailsPK(authoritybiblidetailsPK);
           super.edit(authoritybiblidetails);
            }
        }
     }
    
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<Authoritybiblidetails> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
            case "findByMaxRecID":  //used by AccessioningServlet
                                    return super.findBy("Authoritybiblidetails."+query);
            case "findByRecIDMaxSbFldSrNoAndTag887":     valueList.add(Integer.parseInt(valueString[0]));
                                                break;
            case "findByRecID" :valueList.add(Integer.parseInt(valueString[0]));
                break;
                
         
            default:    valueList.add(valueString[0]);
                        break;  
                        //used for findByTitle
           
        }
        return super.findBy("Authoritybiblidetails."+query, valueList);
    }
    
    
    @DELETE
    @Path("removeBy/{namedQuery}/{values}")
    public void removeBy(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
             case "deleteByRecID": valueList.add(Integer.parseInt(valueString[0]))  ; 
                break;
//            case "deleteByPk":  valueList.add(Integer.parseInt(valueString[0]))  ; 
//                              valueList.add(valueString[1]);
//                              valueList.add(valueString[2]);
//                              valueList.add(Integer.parseInt(valueString[3]))  ; 
//                              valueList.add(Integer.parseInt(valueString[4]))  ; 
//              break;   
             case "deleteByRecID&tag&sbfld": valueList.add(Integer.parseInt(valueString[0]));
                                             valueList.add(valueString[1]);
                                             valueList.add(valueString[2]);
                 break;
            default:    valueList.add(valueString[0]);
                //used for RemoveByMemCd, 
                        break;
        } 
        super.removeBy("Authoritybiblidetails."+query, valueList);
    } 
   
    @DELETE
    @Path("deleteByPk/{currentRecId}/{tagArray}/{sbfldArray}/{tagSrNo}/{sbfldSrNo}")
  /*  public void deleteByPk(@QueryParam("currentRecId") String recId, @QueryParam("tagArray") String tagArray,
    @QueryParam("sbfldArray") String sbfldArray , @QueryParam("tagSrNo") String tagSrNo ,@QueryParam("sbfldSrNo") String sbfldSrNo ){*/
    public void deleteByPk(@PathParam("currentRecId") String recId, @PathParam("tagArray") String tagArray,
    @PathParam("sbfldArray") String sbfldArray , @PathParam("tagSrNo") String tagSrNo ,@PathParam("sbfldSrNo") String sbfldSrNo ){    
        
        List<Object> valueList = new ArrayList<>();
        String tagArrayData[] = tagArray.split("~",-1);
        String sbfldArrayData[] = sbfldArray.split("~",-1);
        String tagSrNoArrayData[] = tagSrNo.split("~",-1);
        String sbfldSrNoArrayData[] = sbfldSrNo.split("~",-1);
       
        for(int i=2;i<tagArrayData.length;i++) //omit i=0&1 because it has no subfield (ie - leader & fxfield)
                {
                  
                        valueList.add(Integer.parseInt(recId));
                        valueList.add(tagArrayData[i]);
                        valueList.add(sbfldArrayData[i]);
                        valueList.add(tagSrNoArrayData[i]);
                        valueList.add(sbfldSrNoArrayData[i]);
                        super.removeBy("Authoritybiblidetails.deleteByPk", valueList);
                        valueList.clear();
                 }         
       

       // super.removeBy("Biblidetails.deleteByRecID&tag&sbfld", valueList);
    } 
    
    
    
    @DELETE
    @Path("deleteByRecID&tag&sbfld/{currentRecId}/{tagArray}/{sbfldArray}")
   /* public void deleteByRecID_tag_sbfld(@QueryParam("currentRecId") String recId, @QueryParam("tagArray") String tagArray,
    @QueryParam("sbfldArray") String sbfldArray ){*/
    public void deleteByRecID_tag_sbfld(@PathParam("currentRecId") String recId, @PathParam("tagArray") String tagArray,
    @PathParam("sbfldArray") String sbfldArray ){    
        
        List<Object> valueList = new ArrayList<>();
        String tagArrayData[] = tagArray.split("~",-1);
        
        String sbfldArrayData[] = sbfldArray.split("~",-1);
       
        for(int i=2;i<tagArrayData.length;i++) //omit i=0&1 because it has no subfield (ie - leader & fxfield)
                {
                  
                        valueList.add(Integer.parseInt(recId));
                        valueList.add(tagArrayData[i]);
                        valueList.add(sbfldArrayData[i]);
                        super.removeBy("Authoritybiblidetails.deleteByRecID&tag&sbfld", valueList);
                        valueList.clear();
               }     
    }
    
    @GET
    @Path("count/by/{namedQuery}/{values}")
    @Produces("text/plain")
    public String countBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        String output = null;
        switch(query)
        {   // used in controljs to check whether a recorId exists or not
            case "countRecId": valueList.add(Integer.parseInt(valueString[0]));
            String count = String.valueOf(super.countBy("Authoritybiblidetails."+query, valueList));
            
            if(count.contentEquals("0"))
                   {
                     output= "no";
                   }
                   else
                   {
                     output= "yes";
                   }
                break;
            default:    valueList.add(valueString[0]);
            //used for countRecId
        }
        return output;
    }
}
