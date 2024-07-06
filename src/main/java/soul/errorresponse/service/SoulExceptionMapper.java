/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.errorresponse.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider; 
import soul.errorresponce.CommonErrorProvider;
import soul.errorresponce.Errorlist;
import soul.errorresponce.ValidationErrorlistfields;
import soul.errorresponce.Errormsg;
import soul.userlog.Souluserlog;
import soul.userlog.service.SouluserlogFacadeREST;

/**
 *
 * @author admin
 */
@Provider
public class SoulExceptionMapper  implements ExceptionMapper<Throwable> {
  @Override
  public Response toResponse(Throwable exception) {
        if (exception instanceof DataNotFoundException) {

            return Response.status(Status.NOT_FOUND)
                    .entity(new Errormsg(exception.getMessage()))
                    .build();
        } else if (exception instanceof ValidationException) {
            
            System.out.println(Arrays.toString(exception.getStackTrace()));
            String[] s = Arrays.toString(exception.getStackTrace()).split(",");
            String[] f = s[0].split(".");
            System.out.println(f.toString() + "S : " + s.length + " : " + s[0] + " F : " + f.length + " : ");
            ValidationException exception1 = (ValidationException) exception;
            System.out.println("ex ValidationException : " + exception1.msg + " : " + exception1.casues);
                        
            return Response.status(Status.BAD_REQUEST)
                    .entity(new Errorlist("Validation.. " + exception.getMessage(), exception1.casues))
                    .build();
        } else if (exception instanceof TransactionException) {

            System.out.println(Arrays.toString(exception.getStackTrace()));
            String[] s = Arrays.toString(exception.getStackTrace()).split(",");
            String[] f = s[0].split(".");
            System.out.println(f.toString() + "S : " + s.length + " : " + s[0] + " F : " + f.length + " : ");
            TransactionException exception1 = (TransactionException) exception;
            System.out.println("ex TransactionException : " + exception1.msg + " : " + exception1.userexcode);
            
            return Response.status(Status.BAD_REQUEST)
                    .entity(new CommonErrorProvider(exception.getMessage(), exception1.userexcode))
                    .build();
        } else {  
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new Errormsg("SOUL Error : Somthing is wrong " + exception.getMessage()))
                    .build();
        }
    }
}
