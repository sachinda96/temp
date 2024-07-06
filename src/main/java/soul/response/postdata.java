/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.response;

import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@XmlRootElement
public class postdata {
    
    private String location;
    private String message;
       
    public postdata(){}
    
    public postdata(String apipath,String msg){
        this.location=apipath;
        this.message=msg;
       
    }
    
    public postdata(String msg){
        this.message=msg;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
   }
