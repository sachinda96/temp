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
public class IllExtissueload {
    @Id
    @Column(name = "Accno")
    private String Accno;
    @Column(name = "Issueid")
    private String issueid;
    @Column(name = "Lib_Name")
    private String libname;
    @Column(name = "Request_Date")
    private String RequestDate;
    @Column(name = "Referance")
    private String Reference;
    @Column(name = "Send_Date")
    private String sendDate;

    public IllExtissueload() {
    }

    public IllExtissueload(String Accno,String issueid, String libname, String RequestDate, String Reference, String sendDate) {
        this.Accno = Accno;
        this.libname = libname;
        this.RequestDate = RequestDate;
        this.Reference = Reference;
        this.sendDate = sendDate;
        this.issueid=issueid;
    }

    public String getIssueid() {
        return issueid;
    }

    public void setIssueid(String issueid) {
        this.issueid = issueid;
    }
    
    
    
    public String getAccno() {
        return Accno;
    }

    public void setAccno(String Accno) {
        this.Accno = Accno;
    }

    public String getLibname() {
        return libname;
    }

    public void setLibname(String libname) {
        this.libname = libname;
    }

    public String getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(String RequestDate) {
        this.RequestDate = RequestDate;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String Reference) {
        this.Reference = Reference;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
    
    
    
}
