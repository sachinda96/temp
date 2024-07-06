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
public class TReceivePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "mem_cd")
    private String memCd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "accn_no")
    private String accnNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iss_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issDt;

    public TReceivePK() {
    }

    public TReceivePK(String memCd, String accnNo, Date issDt) {
        this.memCd = memCd;
        this.accnNo = accnNo;
        this.issDt = issDt;
    }

    public String getMemCd() {
        return memCd;
    }

    public void setMemCd(String memCd) {
        this.memCd = memCd;
    }

    public String getAccnNo() {
        return accnNo;
    }

    public void setAccnNo(String accnNo) {
        this.accnNo = accnNo;
    }

    public Date getIssDt() {
        return issDt;
    }

    public void setIssDt(Date issDt) {
        this.issDt = issDt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memCd != null ? memCd.hashCode() : 0);
        hash += (accnNo != null ? accnNo.hashCode() : 0);
        hash += (issDt != null ? issDt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TReceivePK)) {
            return false;
        }
        TReceivePK other = (TReceivePK) object;
        if ((this.memCd == null && other.memCd != null) || (this.memCd != null && !this.memCd.equals(other.memCd))) {
            return false;
        }
        if ((this.accnNo == null && other.accnNo != null) || (this.accnNo != null && !this.accnNo.equals(other.accnNo))) {
            return false;
        }
        if ((this.issDt == null && other.issDt != null) || (this.issDt != null && !this.issDt.equals(other.issDt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TReceivePK[ memCd=" + memCd + ", accnNo=" + accnNo + ", issDt=" + issDt + " ]";
    }
    
}
