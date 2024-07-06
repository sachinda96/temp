/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@XmlRootElement
public class StringprocessData {
    
    private String processdata;
    private String nonprocessdata;
    
    public StringprocessData(){}

    public StringprocessData(String processdata, String nonprocessdata) {
        this.processdata = processdata;
        this.nonprocessdata = nonprocessdata;
    }
    
    

    public String getProcessdata() {
        return processdata;
    }

    public void setProcessdata(String processdata) {
        this.processdata = processdata;
    }

    public String getNonprocessdata() {
        return nonprocessdata;
    }

    public void setNonprocessdata(String nonprocessdata) {
        this.nonprocessdata = nonprocessdata;
    }
    
    
}
