/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.errorresponce;

import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@XmlRootElement
public class Errorlist {
    
    private String message;
    private List<ValidationErrorlistfields> errors;
    
    public Errorlist(){}
    
    public Errorlist(String msg,List<ValidationErrorlistfields> error){
        this.message = msg;
        this.errors=error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ValidationErrorlistfields> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationErrorlistfields> errors) {
        this.errors = errors;
    }
    
}
