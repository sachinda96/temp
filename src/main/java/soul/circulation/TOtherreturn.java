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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "t_otherreturn")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TOtherreturn.findAll", query = "SELECT t FROM TOtherreturn t")
    , @NamedQuery(name = "TOtherreturn.findByMemCd", query = "SELECT t FROM TOtherreturn t WHERE t.tOtherreturnPK.memCd = :memCd")
    , @NamedQuery(name = "TOtherreturn.findByAccNo", query = "SELECT t FROM TOtherreturn t WHERE t.tOtherreturnPK.accNo = :accNo")
    , @NamedQuery(name = "TOtherreturn.findByReturnReson", query = "SELECT t FROM TOtherreturn t WHERE t.returnReson = :returnReson")
    , @NamedQuery(name = "TOtherreturn.findByReturnRemarks", query = "SELECT t FROM TOtherreturn t WHERE t.returnRemarks = :returnRemarks")
    , @NamedQuery(name = "TOtherreturn.findByIssueDate", query = "SELECT t FROM TOtherreturn t WHERE t.tOtherreturnPK.issueDate = :issueDate")
    , @NamedQuery(name = "TOtherreturn.findByReturnDate", query = "SELECT t FROM TOtherreturn t WHERE t.returnDate = :returnDate")})
public class TOtherreturn implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TOtherreturnPK tOtherreturnPK;
    @Size(max = 2)
    @Column(name = "Return_Reson")
    private String returnReson;
    @Size(max = 50)
    @Column(name = "Return_Remarks")
    private String returnRemarks;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Return_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;

    public TOtherreturn() {
    }

    public TOtherreturn(TOtherreturnPK tOtherreturnPK) {
        this.tOtherreturnPK = tOtherreturnPK;
    }

    public TOtherreturn(TOtherreturnPK tOtherreturnPK, Date returnDate) {
        this.tOtherreturnPK = tOtherreturnPK;
        this.returnDate = returnDate;
    }

    public TOtherreturn(String memCd, String accNo, Date issueDate) {
        this.tOtherreturnPK = new TOtherreturnPK(memCd, accNo, issueDate);
    }

    public TOtherreturnPK getTOtherreturnPK() {
        return tOtherreturnPK;
    }

    public void setTOtherreturnPK(TOtherreturnPK tOtherreturnPK) {
        this.tOtherreturnPK = tOtherreturnPK;
    }

    public String getReturnReson() {
        return returnReson;
    }

    public void setReturnReson(String returnReson) {
        this.returnReson = returnReson;
    }

    public String getReturnRemarks() {
        return returnRemarks;
    }

    public void setReturnRemarks(String returnRemarks) {
        this.returnRemarks = returnRemarks;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tOtherreturnPK != null ? tOtherreturnPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TOtherreturn)) {
            return false;
        }
        TOtherreturn other = (TOtherreturn) object;
        if ((this.tOtherreturnPK == null && other.tOtherreturnPK != null) || (this.tOtherreturnPK != null && !this.tOtherreturnPK.equals(other.tOtherreturnPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TOtherreturn[ tOtherreturnPK=" + tOtherreturnPK + " ]";
    }
    
}
