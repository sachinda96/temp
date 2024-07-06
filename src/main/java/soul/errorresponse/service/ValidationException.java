/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.errorresponse.service;

import java.util.ArrayList;
import java.util.List;
import soul.errorresponce.ValidationErrorlistfields;

/**
 *
 * @author admin
 */
public class ValidationException extends Exception{
    
     private static final long serialVersionUID = 1L;
     List<ValidationErrorlistfields> casues;
     String msg;
     
     public ValidationException(String msg,List<ValidationErrorlistfields> casue){
         super(msg);
         this.casues=casue;
         this.msg=msg;
     }     
}
