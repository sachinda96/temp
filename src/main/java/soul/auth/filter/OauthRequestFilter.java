/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.auth.filter;


import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Key;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import soul.encryptdecrypt.service.SoulSecurity;
import soul.jwttoken.generaion.JWToken;
import soul.jwttoken.generaion.JwsKey;
import soul.security.annotation.SoulPrivilege;
import soul.userlog.Souluserlog;
import soul.userlog.service.SouluserlogFacadeREST;

//import javax.security.jacc.EJBMethodPermission;
/**
 *
 * @author admin
 */
//@Provider
//@Priority(Priorities.AUTHENTICATION)
public class OauthRequestFilter implements javax.ws.rs.container.ContainerRequestFilter{
    
    @Context
    private ResourceInfo resourceInfo;
    @EJB
    private SouluserlogFacadeREST souluserlogFacadeREST;
    @Context
    private HttpServletRequest servletRequest;
    
    
   // private static final String Authproperty = "Authorization";
   // private static final String Authschema = "Bearer";
    String rolesofuser;
    String s1; 
    String subject;
    SoulSecurity security = new SoulSecurity();
    JWToken jWToken = new JWToken();
    Date Todaydate = new Date();
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        
     //  MultivaluedMap<String,String> reqheader = requestContext.getHeaders();
       
       Souluserlog userlog = new Souluserlog();
       System.out.println(" ip : "+servletRequest.getRemoteAddr()+" : "+servletRequest.getRemotePort());
       userlog.setOriginIp(servletRequest.getRemoteAddr());
       requestContext.getHeaders().add("userip", servletRequest.getRemoteAddr());
       userlog.setLogTime(Todaydate);
       userlog.setLogType("Request Resource");
       String log_description = requestContext.getMethod()+" : "+requestContext.getUriInfo().getAbsolutePath().toString();
       
//        System.err.println("request header : "+reqheader);
//        System.err.println("request URL : "+requestContext.getUriInfo().getAbsolutePath().toString());
//        System.err.println("request Media Parameteres : "+requestContext.getMediaType().getParameters());
//        System.err.println("request Http method : "+requestContext.getMethod());
        System.out.println("Request Filter is Invoke.....");
        Method method = resourceInfo.getResourceMethod();
        // System.err.println("request Entity Stream : "+requestContext.getEntityStream());
        // log_description = log_description +" : "+
         //System.err.println("request method anootat : "+method.getAnnotations());
        System.out.println("method called "+method.getClass()+"  :  "+method.getName()+"  :  "+method.getClass().toString());
        if(!method.isAnnotationPresent(PermitAll.class)){
            
            if(method.isAnnotationPresent(DenyAll.class)){
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                return;
            }
            
            if (!method.getName().equals("auth") && !method.getName().equals("refreshauthtoken") && !method.getName().equals("autologout") && !method.getName().equals("resetuserbyuname") && !method.getName().equals("verifyusertoken")){
            
                    String authHeaderVal = requestContext.getHeaderString("Authorization");
                    System.out.println("Authorization  is ....." + authHeaderVal.substring(7));
            
                    if(authHeaderVal == null || authHeaderVal.isEmpty()){
                        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                         return;
                    }
                    

                    if(method.isAnnotationPresent(SoulPrivilege.class)){
                        SoulPrivilege rolesAllowed = method.getAnnotation(SoulPrivilege.class);
            //          List roleset = Arrays.asList(rolesAllowed.value());
                        String[] roleset = rolesAllowed.value();
                        s1 = roleset[0];
                        System.out.println("s1 from method : "+s1);
                 
                        if (authHeaderVal.startsWith("Bearer")) {
                        try {
                                System.out.println("JWT based Auth in action... time to verify th signature");
               // System.out.println("JWT being tested:\n" + authHeaderVal.split(" ")[1]);
                
               
                              
              //  final String subject = validate(authHeaderVal.substring(7),s1);
                                 Boolean result = validate(authHeaderVal.substring(7),s1);
                                 String subj = subject;
                                 userlog.setUsername(subj);
                                 log_description = log_description +" : "+s1+" : "+result.toString();
                                 userlog.setLogDescrption(log_description);
                                 
                                 souluserlogFacadeREST.create(userlog);
                                 
                                 requestContext.getHeaders().add("username", subj);
                                 System.out.println("ZZZ : " +requestContext.getHeaders());
                                System.out.println("Boolean result : "+result);
                                if (result==false){
                                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                                    return;
                                }
              

                            } catch (InvalidJwtException ex) {
                                    userlog.setUsername("NaN");
                                    log_description = log_description +" : "+s1+" : "+"No JWT token auth-failed.";
                                    userlog.setLogDescrption(log_description);
                                 
                                    souluserlogFacadeREST.create(userlog);
                                    
                                    System.out.println("JWT validation failed");

                                    requestContext.setProperty("auth-failed", true);
                                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                                    return;

                            }

                        } else {
                                userlog.setUsername("NaN");
                                log_description = log_description +" : "+s1+" : "+"No JWT token auth-failed.";
                                userlog.setLogDescrption(log_description);
                                 
                                 souluserlogFacadeREST.create(userlog);    
                                
                                System.out.println("No JWT token !");
                                requestContext.setProperty("auth-failed", true);
                                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                                return;
                        }
                    }else{
                        // if method is not rollalloed
                        
                        if (authHeaderVal.startsWith("Bearer")) {
                             
                            try {
                                 Boolean result = validate(authHeaderVal.substring(7),"N");
                                 String subj = subject;
                                 userlog.setUsername(subj);
                                 log_description = log_description +" : NaN"+" : "+result.toString();
                                 userlog.setLogDescrption(log_description);
                                 
                                 souluserlogFacadeREST.create(userlog);
                                 
                                 requestContext.getHeaders().add("username", subj);
                                  System.out.println("ZZZ : " +requestContext.getHeaders());
                                System.out.println("method is not rollalloed Boolean result : "+result);
                                if (result==false){
                                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                                    return;
                                }
                            } catch (InvalidJwtException ex) {
                                
                                userlog.setUsername("NaN");
                                log_description = log_description +" : "+s1+" : "+"No JWT token auth-failed.";
                                userlog.setLogDescrption(log_description);
                                 
                                souluserlogFacadeREST.create(userlog);
                                
                                Logger.getLogger(OauthRequestFilter.class.getName()).log(Level.SEVERE, null, ex);
                                 requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                                    return;
                            }
                                
                        }
                        else {
                                userlog.setUsername("NaN");
                                 log_description = log_description +" : N"+" : "+"No JWT token auth-failed.";
                                 userlog.setLogDescrption(log_description);
                                 
                                 souluserlogFacadeREST.create(userlog);
                                 
                                System.out.println("No JWT token !");
                                requestContext.setProperty("auth-failed", true);
                                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                                return;
                                }
                    }
            }  
        
         
        }

    }

    private Boolean validate(String jwt,String role) throws InvalidJwtException {
        Boolean result = false;
       
//        Base64.Decoder decoder = Base64.getUrlDecoder();
//        String[] parts = jwt.split("\\.");
//        System.out.println("Payload---------------------: "+new String(decoder.decode(parts[1]))); 
       
       // Key JsonWebKey = JwsKey.keys();
        RsaJsonWebKey rsaJsonWebKey = JwsKey.produce();

        System.out.println("RSA hash code... " + rsaJsonWebKey.hashCode());
        boolean tokn = jWToken.checkJWTTokeninMap(jwt);
        if(tokn){
        
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireSubject() // the JWT must have a subject claim
                .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
                .build(); // create the JwtConsumer instance

        try {
            //  Validate the JWT and process it to the Claims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
            String encrpsubject = (String) jwtClaims.getClaimValue("sub");
           String encrprolesofuser = (String) jwtClaims.getClaimValue("Privilleges");
           
           subject = security.decrypt(encrpsubject, SoulSecurity.Securitykey);
           rolesofuser = security.decrypt(encrprolesofuser, SoulSecurity.Securitykey);
                     
            if(role.equals("N")){
                 result = true;   
                 System.out.println("Return Result :"+ result ); 
            }else{
                String rolesofus = rolesofuser.substring(0, rolesofuser.length()-1);
                String[] roleuser = rolesofus.split(":");
            
                for(int i=0;i<roleuser.length;i++){
                    if(role.equals(roleuser[i])){
                         result = true;
                    }
                }  
                
                System.out.println("Return Result :"+ result ); 
                System.out.println("JWT USER Privilleges : "+rolesofuser);
                System.out.println("JWT validation succeeded! " + jwtClaims);
            }
            
             
        } catch (InvalidJwtException e) {
            e.printStackTrace(); //on purpose
            throw e;
        }
        }
        return result;
    }
        
        

    }
    

