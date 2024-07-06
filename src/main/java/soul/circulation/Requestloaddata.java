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
public class Requestloaddata {
    @Id
    @Column(name = "requestid")
    private String requestid;
    @Column(name = "memcode")
    private String memcode;
    @Column(name = "memname")
    private String memname;
    @Column(name = "description")
    private String description;
    @Column(name = "title")
    private String title;
    @Column(name = "author1")
    private String author1;
    @Column(name = "author2")
    private String author2;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "publisherYear")
    private String publisherYear;
    @Column(name = "requestDt")
    private String requestDate;
    @Column(name = "libname")
    private String libname;
    @Column(name = "libcode")
    private String libcode;
    @Column(name = "arrivaldt")
    private Date ArrivalDt;
    @Column(name = "issuedt")
    private Date issuedt;
    @Column(name = "receivedt")
    private Date receivedt;
    @Column(name = "mediatype")
    private String mediatype;

    public Requestloaddata() {
    }

    public Requestloaddata(String memcode, String description, String title, String author1, String author2, String publisher, String publisherYear, String requestDate, String libname, String libcode, String requestid,Date ArrivalDt,Date issuedt,String mediatype,String memname,Date receivedt) {
        this.memcode = memcode;
        this.description = description;
        this.title = title;
        this.author1 = author1;
        this.author2 = author2;
        this.publisher = publisher;
        this.publisherYear = publisherYear;
        this.requestDate = requestDate;
        this.libname = libname;
        this.libcode = libcode;
        this.requestid = requestid;
        this.ArrivalDt = ArrivalDt;
        this.issuedt=issuedt;
        this.mediatype=mediatype;
        this.memname=memname;
        this.receivedt=receivedt;
    }

    public Date getReceivedt() {
        return receivedt;
    }

    public void setReceivedt(Date receivedt) {
        this.receivedt = receivedt;
    }
    
    public String getMemname() {
        return memname;
    }

    public void setMemname(String memname) {
        this.memname = memname;
    }
    
    public Date getArrivalDt() {
        return ArrivalDt;
    }

    public void setArrivalDt(Date ArrivalDt) {
        this.ArrivalDt = ArrivalDt;
    }

    public Date getIssuedt() {
        return issuedt;
    }

    public void setIssuedt(Date issuedt) {
        this.issuedt = issuedt;
    }

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }
    
    public String getMemcode() {
        return memcode;
    }

    public void setMemcode(String memcode) {
        this.memcode = memcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor1() {
        return author1;
    }

    public void setAuthor1(String author1) {
        this.author1 = author1;
    }

    public String getAuthor2() {
        return author2;
    }

    public void setAuthor2(String author2) {
        this.author2 = author2;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisherYear() {
        return publisherYear;
    }

    public void setPublisherYear(String publisherYear) {
        this.publisherYear = publisherYear;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getLibname() {
        return libname;
    }

    public void setLibname(String libname) {
        this.libname = libname;
    }

    public String getLibcode() {
        return libcode;
    }

    public void setLibcode(String libcode) {
        this.libcode = libcode;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }
    
    
}
