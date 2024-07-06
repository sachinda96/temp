/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.errorresponce;

import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@XmlRootElement
public class ValidationErrorlistfields {
    
    private String message;
    private String code;
    private String field;
    
    public ValidationErrorlistfields(){}
    
   public ValidationErrorlistfields(String msg,String code,String field){
       this.message=msg;
       this.code = code;
       this.field = field;
   }
    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }


}
