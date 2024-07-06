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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "t_withdraw")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TWithdraw.findAll", query = "SELECT t FROM TWithdraw t")
    , @NamedQuery(name = "TWithdraw.findByAccNo", query = "SELECT t FROM TWithdraw t WHERE t.accNo = :accNo")
    , @NamedQuery(name = "TWithdraw.findByWithdrawDt", query = "SELECT t FROM TWithdraw t WHERE t.withdrawDt = :withdrawDt")
    , @NamedQuery(name = "TWithdraw.findByOriginalPrice", query = "SELECT t FROM TWithdraw t WHERE t.originalPrice = :originalPrice")
    , @NamedQuery(name = "TWithdraw.findByWithdrawBy", query = "SELECT t FROM TWithdraw t WHERE t.withdrawBy = :withdrawBy")
    , @NamedQuery(name = "TWithdraw.findByReintroducedBy", query = "SELECT t FROM TWithdraw t WHERE t.reintroducedBy = :reintroducedBy")
    , @NamedQuery(name = "TWithdraw.findByDepreciatedPrice", query = "SELECT t FROM TWithdraw t WHERE t.depreciatedPrice = :depreciatedPrice")
    , @NamedQuery(name = "TWithdraw.findByReintroducedDt", query = "SELECT t FROM TWithdraw t WHERE t.reintroducedDt = :reintroducedDt")})
public class TWithdraw implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "record_id")
    private Integer recordId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "acc_no")
    private String accNo;
    @Size(max = 255)
    @Column(name = "depreciated_price")
    private String depreciatedPrice;
    @Size(max = 255)
    @Column(name = "original_price")
    private String originalPrice;
    @Size(max = 255)
    @Column(name = "reintroduced_by")
    private String reintroducedBy;
    @Column(name = "reintroduced_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reintroducedDt;
    @Size(max = 255)
    @Column(name = "withdraw_by")
    private String withdrawBy;
    @Column(name = "withdraw_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date withdrawDt;

    public TWithdraw() {
    }

    public TWithdraw(Integer recordId) {
        this.recordId = recordId;
    }

    public TWithdraw(Integer recordId, String accNo) {
        this.recordId = recordId;
        this.accNo = accNo;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getDepreciatedPrice() {
        return depreciatedPrice;
    }

    public void setDepreciatedPrice(String depreciatedPrice) {
        this.depreciatedPrice = depreciatedPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getReintroducedBy() {
        return reintroducedBy;
    }

    public void setReintroducedBy(String reintroducedBy) {
        this.reintroducedBy = reintroducedBy;
    }

    public Date getReintroducedDt() {
        return reintroducedDt;
    }

    public void setReintroducedDt(Date reintroducedDt) {
        this.reintroducedDt = reintroducedDt;
    }

    public String getWithdrawBy() {
        return withdrawBy;
    }

    public void setWithdrawBy(String withdrawBy) {
        this.withdrawBy = withdrawBy;
    }

    public Date getWithdrawDt() {
        return withdrawDt;
    }

    public void setWithdrawDt(Date withdrawDt) {
        this.withdrawDt = withdrawDt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordId != null ? recordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TWithdraw)) {
            return false;
        }
        TWithdraw other = (TWithdraw) object;
        if ((this.recordId == null && other.recordId != null) || (this.recordId != null && !this.recordId.equals(other.recordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.newchange.TWithdraw[ recordId=" + recordId + " ]";
    }
    
}
