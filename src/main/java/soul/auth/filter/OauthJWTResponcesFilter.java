/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.auth.filter;

import java.io.IOException;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import soul.userlog.Souluserlog;
import soul.userlog.service.SouluserlogFacadeREST;

/**
 *
 * @author admin
 */
//@Provider
public class OauthJWTResponcesFilter implements javax.ws.rs.container.ContainerResponseFilter{
    
     @EJB
    private SouluserlogFacadeREST souluserlogFacadeREST;
    @Context
    private HttpServletRequest servletRequest;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        
        System.out.println("Response Filter INvoke.......");
        System.err.println("requestContext inside Response Filter : "+ requestContext.getHeaders());
        String findex = requestContext.getHeaderString("Ex");
        if(!findex.isEmpty()){
        Souluserlog userlog = new Souluserlog();
       // String s[]=errorexcode.split(",");
        userlog.setOriginIp(requestContext.getHeaderString("userip"));
        userlog.setLogTime(new Date());
        userlog.setLogType("Exception");
        userlog.setUsername(requestContext.getHeaderString("username"));
        String logdescription = requestContext.getMethod()+" : "+requestContext.getUriInfo().getAbsolutePath().toString()+" : " + requestContext.getHeaderString("Excode") + " : " + requestContext.getHeaderString("Exusercode");
        userlog.setLogDescrption(logdescription);      
        souluserlogFacadeREST.create(userlog);
        }
        responseContext.getHeaders().add("Done-BY", "JWT-RESPONSE");
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
