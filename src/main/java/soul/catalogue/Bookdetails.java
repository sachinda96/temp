/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author INFLIBNET
 */

@Entity
@XmlRootElement
public class Bookdetails {
     @Id
     @Column(name = "RecId")
     private String recId;
     @Column(name = "AccessionNo")
     private String accNo;
     @Column(name = "Status")
     private String status;
     @Column(name = "Title")
     private String title;
     @Column(name = "Author")
     private String author;
     @Column(name = "Classno")
     private String classNo;
     @Column(name = "Bookno")
     private String bookNo;
     @Column(name = "Materialtype")
     private String material;
     @Column(name = "Issuerestric")
     private String issueRestricted;
     @Column(name = "Price")
     private String price;
     @Column(name = "Edition")
     private String edition;
     @Column(name = "booklibcd")
     private String booklibcode;
     @Column(name = "bookshelvlocation")
     private String bookshelvlocation;
     @Column(name="duedt")
     @Temporal(javax.persistence.TemporalType.DATE)
     private Date duedate;
     
     public Bookdetails(){
     };

    public Bookdetails(String recId, String accNo, String status, String title, String author, String classNo, String bookNo, String material, String issueRestricted, String price, String edition, String booklibcode,String bookshelvlocation,Date duedate) {
        this.recId = recId;
        this.accNo = accNo;
        this.status = status;
        this.title = title;
        this.author = author;
        this.classNo = classNo;
        this.bookNo = bookNo;
        this.material = material;
        this.issueRestricted = issueRestricted;
        this.price = price;
        this.edition = edition;
        this.booklibcode = booklibcode;
        this.bookshelvlocation=bookshelvlocation;
        this.duedate=duedate;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }
    
    

    public String getBookshelvlocation() {
        return bookshelvlocation;
    }

    public void setBookshelvlocation(String bookshelvlocation) {
        this.bookshelvlocation = bookshelvlocation;
    }
     
       

    public String getBooklibcode() {
        return booklibcode;
    }

    public void setBooklibcode(String booklibcode) {
        this.booklibcode = booklibcode;
    }
    
        
        
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public String getBookNo() {
        return bookNo;
    }

    public void setBookNo(String bookNo) {
        this.bookNo = bookNo;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getIssueRestricted() {
        return issueRestricted;
    }

    public void setIssueRestricted(String issueRestricted) {
        this.issueRestricted = issueRestricted;
    }
    
     public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

     @Override
    public String toString() {
        return "Bookdetails{" + "recId=" + recId + ", accNo=" + accNo + ", status=" + status + ", title=" + title + ", author=" + author + ", classNo=" + classNo + ", bookNo=" + bookNo + ", material=" + material + ", issueRestricted=" + issueRestricted + '}';
    }

 
     
}
