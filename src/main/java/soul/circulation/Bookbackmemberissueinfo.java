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
public class Bookbackmemberissueinfo {
    
    @Id
    @Column(name="accno")
    private String accno;
    @Column(name="Bookname")
    private String title;
    @Column(name="author")
    private String author;
    @Column(name="issuedt")
    private Date issuedt;
    @Column(name="duedt")
    private Date Duedate;
    @Column(name="price")
    private String price;
    @Column(name="memcd")
    private String memcd;
    @Column(name="usercd")
    private String usercd;
    
    public Bookbackmemberissueinfo(){}

    public Bookbackmemberissueinfo(String memcd, String accno, String title, String author, Date issuedt, Date Duedate, String price,String usercd) {
        this.memcd = memcd;
        this.accno = accno;
        this.title = title;
        this.author = author;
        this.issuedt = issuedt;
        this.Duedate = Duedate;
        this.price = price;
        this.usercd=usercd;
    }

    public String getUsercd() {
        return usercd;
    }

    public void setUsercd(String usercd) {
        this.usercd = usercd;
    }
    
    

    public String getMemcd() {
        return memcd;
    }

    public void setMemcd(String memcd) {
        this.memcd = memcd;
    }

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getIssuedt() {
        return issuedt;
    }

    public void setIssuedt(Date issuedt) {
        this.issuedt = issuedt;
    }

    public Date getDuedate() {
        return Duedate;
    }

    public void setDuedate(Date Duedate) {
        this.Duedate = Duedate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    
    
}
