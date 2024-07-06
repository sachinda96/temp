/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@Entity
@XmlRootElement
public class Tissuedetails {
    
    @EmbeddedId
    protected TissuedtPK tissuedtPK;
    @Column(name = "Bookname")
    private String bookname;
    @Column(name = "author")
    private String author;
    @Column(name = "usercd")
    private String usercd;
    @Column(name = "duedt")
    private Date duedt;
    
    
    public Tissuedetails(){}

    public Tissuedetails(TissuedtPK tissuedtPK, String bookname, String author, String usercd, Date duedt) {
        this.tissuedtPK = tissuedtPK;
        this.bookname = bookname;
        this.author = author;
        this.usercd = usercd;
        this.duedt = duedt;
    }

    public TissuedtPK getTissuedtPK() {
        return tissuedtPK;
    }

    public void setTissuedtPK(TissuedtPK tissuedtPK) {
        this.tissuedtPK = tissuedtPK;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
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

    public Date getDuedt() {
        return duedt;
    }

    public void setDuedt(Date duedt) {
        this.duedt = duedt;
    }
    
    

         
    
}
