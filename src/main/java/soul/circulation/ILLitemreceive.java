/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@Entity
@XmlRootElement
public class ILLitemreceive {
    @Id
    @Column(name = "requestid")
    private String requestid;
    @Column(name = "memcd")
    private String memcode;
    @Column(name = "title")
    private String title;
    @Column(name = "mem_name")
    private String memfirst;
    @Column(name = "recievedt")
    private String requestdt;

    public ILLitemreceive() {
    }

    public ILLitemreceive(String requestid, String memcode, String title, String memfirst, String requestdt) {
        this.requestid = requestid;
        this.memcode = memcode;
        this.title = title;
        this.memfirst = memfirst;
        this.requestdt = requestdt;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getMemcode() {
        return memcode;
    }

    public void setMemcode(String memcode) {
        this.memcode = memcode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemfirst() {
        return memfirst;
    }

    public void setMemfirst(String memfirst) {
        this.memfirst = memfirst;
    }

    public String getRequestdt() {
        return requestdt;
    }

    public void setRequestdt(String requestdt) {
        this.requestdt = requestdt;
    }
    
    
    
}
