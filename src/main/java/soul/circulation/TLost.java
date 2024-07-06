/*
 * To change this template, choose Tools | Templates
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "t_lost")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TLost.findAll", query = "SELECT t FROM TLost t"),
    @NamedQuery(name = "TLost.findByMemCd", query = "SELECT t FROM TLost t WHERE t.tLostPK.memCd = :memCd"),
    @NamedQuery(name = "TLost.findByAccNo", query = "SELECT t FROM TLost t WHERE t.tLostPK.accNo = :accNo"),
    @NamedQuery(name = "TLost.findByReason", query = "SELECT t FROM TLost t WHERE t.reason = :reason"),
    @NamedQuery(name = "TLost.findByRepDt", query = "SELECT t FROM TLost t WHERE t.tLostPK.repDt = :repDt"),
    @NamedQuery(name = "TLost.findByRepResponsible", query = "SELECT t FROM TLost t WHERE t.repResponsible = :repResponsible"),
    @NamedQuery(name = "TLost.findByReplaceBook", query = "SELECT t FROM TLost t WHERE t.replaceBook = :replaceBook"),
    @NamedQuery(name = "TLost.findByAmountRecover", query = "SELECT t FROM TLost t WHERE t.amountRecover = :amountRecover"),
    
    @NamedQuery(name = "TLost.findAllNotProcessed", query = "SELECT t FROM TLost t WHERE t.amountRecover='N' AND t.replaceBook='N'"),
    @NamedQuery(name = "TLost.findByRepDtBtwn", query = "SELECT t FROM TLost t WHERE t.tLostPK.repDt BETWEEN ?1 AND ?2")
})
public class TLost implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TLostPK tLostPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "reason")
    private String reason;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "rep_responsible")
    private String repResponsible;
    @Basic(optional = false)
    @NotNull
    @Column(name = "replace_book")
    private String replaceBook;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount_recover")
    private String amountRecover;

    public TLost() {
    }

    public TLost(TLostPK tLostPK) {
        this.tLostPK = tLostPK;
    }

    public TLost(TLostPK tLostPK, String reason, String repResponsible, String replaceBook, String amountRecover) {
        this.tLostPK = tLostPK;
        this.reason = reason;
        this.repResponsible = repResponsible;
        this.replaceBook = replaceBook;
        this.amountRecover = amountRecover;
    }

    public TLost(String memCd, String accNo, Date repDt) {
        this.tLostPK = new TLostPK(memCd, accNo, repDt);
    }

    public TLostPK getTLostPK() {
        return tLostPK;
    }

    public void setTLostPK(TLostPK tLostPK) {
        this.tLostPK = tLostPK;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRepResponsible() {
        return repResponsible;
    }

    public void setRepResponsible(String repResponsible) {
        this.repResponsible = repResponsible;
    }

    public String getReplaceBook() {
        return replaceBook;
    }

    public void setReplaceBook(String replaceBook) {
        this.replaceBook = replaceBook;
    }

    public String getAmountRecover() {
        return amountRecover;
    }

    public void setAmountRecover(String amountRecover) {
        this.amountRecover = amountRecover;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tLostPK != null ? tLostPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TLost)) {
            return false;
        }
        TLost other = (TLost) object;
        if ((this.tLostPK == null && other.tLostPK != null) || (this.tLostPK != null && !this.tLostPK.equals(other.tLostPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TLost[ tLostPK=" + tLostPK + " ]";
    }
    
}
