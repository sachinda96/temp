/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.response;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@XmlRootElement
public class SouldataResponse {
    private String location;
    private String message;
    private List<Object> datalist;
    
    public SouldataResponse(){}
    
    public SouldataResponse(String apipath,String msg,List<Object> dtlist){
        this.location=apipath;
        this.message=msg;
        this.datalist=dtlist;
    }
    
    public SouldataResponse(String msg){
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
    
     public List<Object> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<Object> datalist) {
        this.datalist = datalist;
    }
    
}
