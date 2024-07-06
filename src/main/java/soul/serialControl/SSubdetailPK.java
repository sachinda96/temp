/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author soullab
 */
@Embeddable
public class SSubdetailPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_order_no")
    private String sOrderNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_recid")
    private String sRecid;

    public SSubdetailPK() {
    }

    public SSubdetailPK(String sOrderNo, String sRecid) {
        this.sOrderNo = sOrderNo;
        this.sRecid = sRecid;
    }

    public String getSOrderNo() {
        return sOrderNo;
    }

    public void setSOrderNo(String sOrderNo) {
        this.sOrderNo = sOrderNo;
    }

    public String getSRecid() {
        return sRecid;
    }

    public void setSRecid(String sRecid) {
        this.sRecid = sRecid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sOrderNo != null ? sOrderNo.hashCode() : 0);
        hash += (sRecid != null ? sRecid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SSubdetailPK)) {
            return false;
        }
        SSubdetailPK other = (SSubdetailPK) object;
        if ((this.sOrderNo == null && other.sOrderNo != null) || (this.sOrderNo != null && !this.sOrderNo.equals(other.sOrderNo))) {
            return false;
        }
        if ((this.sRecid == null && other.sRecid != null) || (this.sRecid != null && !this.sRecid.equals(other.sRecid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serialControl.SSubdetailPK[ sOrderNo=" + sOrderNo + ", sRecid=" + sRecid + " ]";
    }
    
}
