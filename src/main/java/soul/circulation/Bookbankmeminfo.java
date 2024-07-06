/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.util.Date;
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
public class Bookbankmeminfo {
    
    @Id
    @Column(name="memcd")
    private String memcd;
    @Column(name="memname")
    private String memname;
    @Column(name="memctgry")
    private String memcategory;
    @Column(name="memdept")
    private String department;
    @Column(name="memtype")
    private String memtype;
    @Column(name="memstatus")
    private String memstatus;
    @Column(name="totalitemissue")
    private String totalitemissue;
    @Column(name="maxbookallow")
    private String maxbookallow;
    @Column(name="maxamtallow")
    private String maxamtallow;
    @Column(name="duedt")
    private Date duedate;
    
    public Bookbankmeminfo(){}

    public Bookbankmeminfo(String memcd, String memname, String memcategory, String department, String memtype, String memstatus, String totalitemissue, String maxbookallow, String maxamtallow, Date duedate) {
        this.memcd = memcd;
        this.memname = memname;
        this.memcategory = memcategory;
        this.department = department;
        this.memtype = memtype;
        this.memstatus = memstatus;
        this.totalitemissue = totalitemissue;
        this.maxbookallow = maxbookallow;
        this.maxamtallow = maxamtallow;
        this.duedate = duedate;
    }    

    public String getMemstatus() {
        return memstatus;
    }

    public void setMemstatus(String memstatus) {
        this.memstatus = memstatus;
    }
    
    

    public String getMemcd() {
        return memcd;
    }

    public void setMemcd(String memcd) {
        this.memcd = memcd;
    }

    public String getMemname() {
        return memname;
    }

    public void setMemname(String memname) {
        this.memname = memname;
    }

    public String getMemcategory() {
        return memcategory;
    }

    public void setMemcategory(String memcategory) {
        this.memcategory = memcategory;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMemtype() {
        return memtype;
    }

    public void setMemtype(String memtype) {
        this.memtype = memtype;
    }

    public String getTotalitemissue() {
        return totalitemissue;
    }

    public void setTotalitemissue(String totalitemissue) {
        this.totalitemissue = totalitemissue;
    }

    public String getMaxbookallow() {
        return maxbookallow;
    }

    public void setMaxbookallow(String maxbookallow) {
        this.maxbookallow = maxbookallow;
    }

    public String getMaxamtallow() {
        return maxamtallow;
    }

    public void setMaxamtallow(String maxamtallow) {
        this.maxamtallow = maxamtallow;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }
    
    
    
    
    
}
