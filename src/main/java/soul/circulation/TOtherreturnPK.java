/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author admin
 */
@Embeddable
public class TOtherreturnPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "mem_cd")
    private String memCd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "acc_no")
    private String accNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Issue_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueDate;

    public TOtherreturnPK() {
    }

    public TOtherreturnPK(String memCd, String accNo, Date issueDate) {
        this.memCd = memCd;
        this.accNo = accNo;
        this.issueDate = issueDate;
    }

    public String getMemCd() {
        return memCd;
    }

    public void setMemCd(String memCd) {
        this.memCd = memCd;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memCd != null ? memCd.hashCode() : 0);
        hash += (accNo != null ? accNo.hashCode() : 0);
        hash += (issueDate != null ? issueDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TOtherreturnPK)) {
            return false;
        }
        TOtherreturnPK other = (TOtherreturnPK) object;
        if ((this.memCd == null && other.memCd != null) || (this.memCd != null && !this.memCd.equals(other.memCd))) {
            return false;
        }
        if ((this.accNo == null && other.accNo != null) || (this.accNo != null && !this.accNo.equals(other.accNo))) {
            return false;
        }
        if ((this.issueDate == null && other.issueDate != null) || (this.issueDate != null && !this.issueDate.equals(other.issueDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TOtherreturnPK[ memCd=" + memCd + ", accNo=" + accNo + ", issueDate=" + issueDate + " ]";
    }
    
}
