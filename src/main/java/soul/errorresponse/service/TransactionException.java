/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.errorresponse.service;

import java.util.Date;
import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import soul.userlog.Souluserlog;
import soul.userlog.service.SouluserlogFacadeREST;

/**
 *
 * @author admin
 */
public class TransactionException extends RuntimeException{
    

    private static final long serialVersionUID = 1L;
    String msg;
    String userexcode;
    public TransactionException(String msg,String userexcode){
        super(msg);
        this.msg=msg;
        this.userexcode=userexcode;
    }
    

}
