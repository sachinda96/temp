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
public class SSubInvdetailPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_inv_no")
    private String sInvNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "s_inv_recid")
    private int sInvRecid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_inv_order_no")
    private String sInvOrderNo;

    public SSubInvdetailPK() {
    }

    public SSubInvdetailPK(String sInvNo, int sInvRecid, String sInvOrderNo) {
        this.sInvNo = sInvNo;
        this.sInvRecid = sInvRecid;
        this.sInvOrderNo = sInvOrderNo;
    }

    public String getSInvNo() {
        return sInvNo;
    }

    public void setSInvNo(String sInvNo) {
        this.sInvNo = sInvNo;
    }

    public int getSInvRecid() {
        return sInvRecid;
    }

    public void setSInvRecid(int sInvRecid) {
        this.sInvRecid = sInvRecid;
    }

    public String getSInvOrderNo() {
        return sInvOrderNo;
    }

    public void setSInvOrderNo(String sInvOrderNo) {
        this.sInvOrderNo = sInvOrderNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sInvNo != null ? sInvNo.hashCode() : 0);
        hash += (int) sInvRecid;
        hash += (sInvOrderNo != null ? sInvOrderNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SSubInvdetailPK)) {
            return false;
        }
        SSubInvdetailPK other = (SSubInvdetailPK) object;
        if ((this.sInvNo == null && other.sInvNo != null) || (this.sInvNo != null && !this.sInvNo.equals(other.sInvNo))) {
            return false;
        }
        if (this.sInvRecid != other.sInvRecid) {
            return false;
        }
        if ((this.sInvOrderNo == null && other.sInvOrderNo != null) || (this.sInvOrderNo != null && !this.sInvOrderNo.equals(other.sInvOrderNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serialControl.SSubInvdetailPK[ sInvNo=" + sInvNo + ", sInvRecid=" + sInvRecid + ", sInvOrderNo=" + sInvOrderNo + " ]";
    }
    
}
