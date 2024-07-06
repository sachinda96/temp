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
public class Memdetails {
    @Id
    @Column(name="memcd")
    private String memcd;
    @Column(name="memid")
    private String memid;
    @Column(name="memname")
    private String memname;
    @Column(name="Status")
    private String memsatus;
    @Column(name="dept")
    private String memdept;
    @Column(name="inst")
    private String meminst;
    @Column(name="branch")
    private String branch;
    @Column(name="crgty")
    private String crgty;

    public Memdetails() {
    }

    public Memdetails(String memcd, String memid, String memname, String memsatus, String memdept, String meminst, String branch, String crgty) {
        this.memcd = memcd;
        this.memid = memid;
        this.memname = memname;
        this.memsatus = memsatus;
        this.memdept = memdept;
        this.meminst = meminst;
        this.branch = branch;
        this.crgty = crgty;
    }

    
    public String getMemcd() {
        return memcd;
    }

    public void setMemcd(String memcd) {
        this.memcd = memcd;
    }

    public String getMemid() {
        return memid;
    }

    public void setMemid(String memid) {
        this.memid = memid;
    }

    public String getMemname() {
        return memname;
    }

    public void setMemname(String memname) {
        this.memname = memname;
    }

    public String getMemsatus() {
        return memsatus;
    }

    public void setMemsatus(String memsatus) {
        this.memsatus = memsatus;
    }

    public String getMemdept() {
        return memdept;
    }

    public void setMemdept(String memdept) {
        this.memdept = memdept;
    }

    public String getMeminst() {
        return meminst;
    }

    public void setMeminst(String meminst) {
        this.meminst = meminst;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCrgty() {
        return crgty;
    }

    public void setCrgty(String crgty) {
        this.crgty = crgty;
    }
    
    
    
    
}
