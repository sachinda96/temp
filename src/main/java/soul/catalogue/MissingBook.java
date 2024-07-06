/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import soul.circulation.MMember;

/**
 *
 * @author INFLIBNET
 */
@Entity
@XmlRootElement
public class MissingBook {
    
   @Id
   @Column(name = "accno")
   private String accNo;
   @Column(name = "memcd")
   private String memCdMissRep;
   @Column(name = "FirstName")
   private String FirstName;
   @Column(name = "lastName")
   private String LastName;
   @Column(name = "missingDate")
   private Date missingDate;
   @Column(name = "status")
   private String Status;
   @Column(name = "title")
   private String Title;
   @Column(name = "author")
   private String Author;
   
    public MissingBook() {
        
    }
    public MissingBook(String accNo, String memCdMissRep, String FirstName, String LastName, Date missingDate, String Status, String Title, String Author) {
        this.accNo = accNo;
        this.memCdMissRep = memCdMissRep;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.missingDate = missingDate;
        this.Status = Status;
        this.Title = Title;
        this.Author = Author;
    }


    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getMemCdMissRep() {
        return memCdMissRep;
    }

    public void setMemCdMissRep(String memCdMissRep) {
        this.memCdMissRep = memCdMissRep;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public Date getMissingDate() {
        return missingDate;
    }

    public void setMissingDate(Date missingDate) {
        this.missingDate = missingDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

   
       
}
