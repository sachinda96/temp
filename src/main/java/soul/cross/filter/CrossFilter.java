/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.cross.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author admin
 */
@Provider
public class CrossFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext response) {
      response.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        response.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, OPTIONS");
        response.getHeaders().putSingle("Access-Control-Allow-Headers", "x-requested-with, Content-Type,origin, accept, authorization");
        response.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
        response.getHeaders().putSingle("Access-Control-Expose-Headers", "*");
        response.getHeaders().putSingle("Access-Control-Max-Age", "1209600");
        
          if (requestContext.getMethod().equals("OPTIONS")) {
            System.out.println("Pre-flight request call...........................");
           response.setStatus(200);
        }
    }
    
}
