/*
 * To change this template, choose Tools | Templates
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
 * @author soullab
 */
@Embeddable
public class TLostPK implements Serializable {
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
    @Column(name = "rep_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repDt;

    public TLostPK() {
    }

    public TLostPK(String memCd, String accNo, Date repDt) {
        this.memCd = memCd;
        this.accNo = accNo;
        this.repDt = repDt;
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

    public Date getRepDt() {
        return repDt;
    }

    public void setRepDt(Date repDt) {
        this.repDt = repDt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memCd != null ? memCd.hashCode() : 0);
        hash += (accNo != null ? accNo.hashCode() : 0);
        hash += (repDt != null ? repDt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TLostPK)) {
            return false;
        }
        TLostPK other = (TLostPK) object;
        if ((this.memCd == null && other.memCd != null) || (this.memCd != null && !this.memCd.equals(other.memCd))) {
            return false;
        }
        if ((this.accNo == null && other.accNo != null) || (this.accNo != null && !this.accNo.equals(other.accNo))) {
            return false;
        }
        if ((this.repDt == null && other.repDt != null) || (this.repDt != null && !this.repDt.equals(other.repDt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TLostPK[ memCd=" + memCd + ", accNo=" + accNo + ", repDt=" + repDt + " ]";
    }
    
}
