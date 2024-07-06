/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author admin
 */
@Embeddable
public class SBindingPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "s_b_sr_no")
    private long sBSrNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_b_no")
    private String sBNo;

    public SBindingPK() {
    }

    public SBindingPK(long sBSrNo, String sBNo) {
        this.sBSrNo = sBSrNo;
        this.sBNo = sBNo;
    }

    public long getSBSrNo() {
        return sBSrNo;
    }

    public void setSBSrNo(long sBSrNo) {
        this.sBSrNo = sBSrNo;
    }

    public String getSBNo() {
        return sBNo;
    }

    public void setSBNo(String sBNo) {
        this.sBNo = sBNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) sBSrNo;
        hash += (sBNo != null ? sBNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SBindingPK)) {
            return false;
        }
        SBindingPK other = (SBindingPK) object;
        if (this.sBSrNo != other.sBSrNo) {
            return false;
        }
        if ((this.sBNo == null && other.sBNo != null) || (this.sBNo != null && !this.sBNo.equals(other.sBNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serial_master.SBindingPK[ sBSrNo=" + sBSrNo + ", sBNo=" + sBNo + " ]";
    }
    
}
