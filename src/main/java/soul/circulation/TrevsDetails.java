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
public class TrevsDetails {
    @Id
    @Column(name="recodid")
    private int recordid;
    @Column(name="Bookname")
    private String Bookname;
    @Column(name="author")
    private String author;
    @Column(name="usercd")
    private String usercd;
    @Column(name="memcode")
    private String memcode;
    @Column(name="resvdt")
    private Date revsdt;
    @Column(name="holddt")
    private Date holddt;
    @Column(name="status")
    private String status;
    @Column(name="srno")
    private String srno;

    public TrevsDetails() {
    }

    public TrevsDetails(int recordid, String Bookname, String author, String usercd, String memcode, Date revsdt, Date holddt, String status, String srno) {
        this.recordid = recordid;
        this.Bookname = Bookname;
        this.author = author;
        this.usercd = usercd;
        this.memcode = memcode;
        this.revsdt = revsdt;
        this.holddt = holddt;
        this.status = status;
        this.srno = srno;
    }

    public int getRecordid() {
        return recordid;
    }

    public void setRecordid(int recordid) {
        this.recordid = recordid;
    }

    public String getBookname() {
        return Bookname;
    }

    public void setBookname(String Bookname) {
        this.Bookname = Bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUsercd() {
        return usercd;
    }

    public void setUsercd(String usercd) {
        this.usercd = usercd;
    }

    public String getMemcode() {
        return memcode;
    }

    public void setMemcode(String memcode) {
        this.memcode = memcode;
    }

    public Date getRevsdt() {
        return revsdt;
    }

    public void setRevsdt(Date revsdt) {
        this.revsdt = revsdt;
    }

    public Date getHolddt() {
        return holddt;
    }

    public void setHolddt(Date holddt) {
        this.holddt = holddt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSrno() {
        return srno;
    }

    public void setSrno(String srno) {
        this.srno = srno;
    }
    
    
}
