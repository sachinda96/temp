/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.loginNout.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import org.json.JSONObject;
import soul.encryptdecrypt.service.SoulSecurity;
import soul.jwttoken.generaion.JWToken;
import soul.user_setting.service.UserdetailFacadeREST;
import soul.user_setting.Userdetail;
import soul.userlog.Souluserlog;
import soul.userlog.service.SouluserlogFacadeREST;
import soul.util.function.ConvertStringIntoJson;
/**
 *
 * @author admin
 */
@Path("login")
public class loginservice {
    
    List<Userdetail> usedetaillist;
    Userdetail userdetail;
    JWToken jWToken = new JWToken();
    Date todayDate = new Date();
    SoulSecurity security = new SoulSecurity();
    @EJB
    private UserdetailFacadeREST userdetailFacadeRest;
    @EJB
    private SouluserlogFacadeREST souluserlogFacadeREST;
    @Context
    private HttpServletRequest servletRequest;
    
    @POST
    @Path("authentication")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response auth(String userdata){
        String uname="";
        String pwd="";
        
        ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
        JSONObject jsonobj = stringintojson.convertTOJson(userdata);
        uname = jsonobj.getString("id");
        pwd =jsonobj.getString("pwd");
        System.err.println("login uname : "+uname);
        System.err.println("Login pwd : "+pwd);
        Souluserlog userlog = new Souluserlog();
        String userip = servletRequest.getRemoteAddr();
        System.err.println("Origin : "+ userip);
        userlog.setOriginIp(userip);
      
        Response resp;
         
        if (!uname.equals("") && !pwd.equals("")){
            
             userlog.setUsername(uname);
             userlog.setLogTime(todayDate);
             userlog.setLogType("LOGIN");
             
              System.err.println(uname);
              System.err.println(pwd);
              userdetail = userdetailFacadeRest.findSingleResultBy("findByUName",uname);
            
                if(uname.equals(userdetail.getUName()) && pwd.equals(userdetail.getUPass())){
                                                               
                    System.out.println("User : " + userdetail.getUName());
                    System.out.println("User : " + userdetail.getGroupID().getPrivilegeList());
                    String jwt = jWToken.buildJWT(userdetail.getUName(),userdetail.getGroupID().getPrivilegeList());
                    String refjwt;
                    
                    if (jwt.equals("null")){
                        refjwt="null";
                        userlog.setLogDescrption("Login fail.");
                    }else{
                        refjwt=security.encrypt(jWToken.getrefreshtokenid(userdetail.getUName()),SoulSecurity.Securitykey);
                        userlog.setLogDescrption("Login successful.");
                    }
                    
                    souluserlogFacadeREST.create(userlog);
                    
                    resp  = Response.ok()
                           .header("jwt",jwt)
                           .header("Ref-JWT",refjwt)
                           .build();
                   
                   System.out.println("Authentication Done.");
                   return resp;
                }
                else{
                    userlog.setLogDescrption("Login fail.");
                    souluserlogFacadeREST.create(userlog);
                      System.err.println("Else excute");
                   return resp = Response.status(Response.Status.UNAUTHORIZED).build();
                }
            
        }else{
              System.err.println("Else excute");
              return resp = Response.status(Response.Status.UNAUTHORIZED).build();
        }      
    }
    
    
    @POST
    @Path("refreshauthtoken")
    @Consumes(MediaType.APPLICATION_JSON)
     public Response refreshauthtoken(String jwtdata){
         String jwttoken="";
         String refreshidtoken="";
         
        ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
        JSONObject jsonobj = stringintojson.convertTOJson(jwtdata);
        jwttoken = jsonobj.getString("jwt");
        refreshidtoken =jsonobj.getString("refjwt");
        
        Response response= Response.status(Response.Status.UNAUTHORIZED).build();
        System.err.println("JWt : " +jwttoken);
        System.err.println("JWt R : " +refreshidtoken);
        String refid = jWToken.jwtrefreshidwithtoken(jwttoken, security.decrypt(refreshidtoken,SoulSecurity.Securitykey));
        if (!refid.equals("null")){
             response = Response.ok().header("Ref-JWT", security.encrypt(refid,SoulSecurity.Securitykey))
                     .header("jwt", jWToken.getTokenfromrefid(refid)).build();
        }
        return response;
    }
    
    @POST
    @Path("logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String autologout(String refreshidtokendata){
        String refreshidtoken="";
        ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
        JSONObject jsonobj = stringintojson.convertTOJson(refreshidtokendata);
        refreshidtoken = jsonobj.getString("jwtreftoken");
        System.err.println("Logout token : "+refreshidtoken);
        String result;
        String uname = jWToken.getuserid(security.decrypt(refreshidtoken,SoulSecurity.Securitykey));
        String userip = servletRequest.getRemoteAddr();
         Souluserlog userlog = new Souluserlog();
         userlog.setLogType("LOGOUT");
         userlog.setLogTime(todayDate);
         userlog.setOriginIp(userip);
        if(!uname.equals("N")){
            userlog.setUsername(uname);
        }else{
            userlog.setUsername("NAN");
        }
        boolean bs =   jWToken.DeleteMapOfUnameAndRefidAndJWT(security.decrypt(refreshidtoken,SoulSecurity.Securitykey));
         System.err.println("Method call with rs...................... " + bs);
          if(bs){
               result="S";
               userlog.setLogDescrption("Logout successfully.");
          }
          else{
              userlog.setLogDescrption("Logout fail.");
               result="F";
          }
          
          souluserlogFacadeREST.create(userlog);
               
        return result;
    }
    
    @POST
    @Path("reset")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String resetuserbyuname(String userdata){
        String username="";
        String password="";
        
        ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
        JSONObject jsonobj = stringintojson.convertTOJson(userdata);
        username = jsonobj.getString("id");
        password =jsonobj.getString("pwd");
        System.err.println("login reset uname : "+username);
        System.err.println("Login reset pwd : "+password);
        String result="null";
        Userdetail userdt = userdetailFacadeRest.findSingleResultBy("findByUName",username);
        String userip = servletRequest.getRemoteAddr();
        Souluserlog userlog = new Souluserlog();
        if(userdt != null){
           if(userdt.getUName().equals(username) && userdt.getUPass().equals(password)){
               boolean s = jWToken.resetuserlogin(username);
               if(s){
                   result="S";
               }
           }
        }
    
        return result;
    }
    
    @POST
    @Path("verify")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String verifyusertoken(String reftokendata){
        String reftoken="";
        
        ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
        JSONObject jsonobj = stringintojson.convertTOJson(reftokendata);
        reftoken = jsonobj.getString("jwtreftoken");
        
        String result="null";
        String rs = jWToken.verifytoken(security.decrypt(reftoken,SoulSecurity.Securitykey));
        if(rs.equals("S")){
            result="S";
        }
    
        return result;
    }
    
    
}
