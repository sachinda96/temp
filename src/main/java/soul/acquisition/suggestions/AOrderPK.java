/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.acquisition.suggestions;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author soullab
 */
@Embeddable
public class AOrderPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "a_r_no")
    private int aRNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "a_o_po_no")
    private int aOPoNo;

    public AOrderPK() {
    }

    public AOrderPK(int aRNo, int aOPoNo) {
        this.aRNo = aRNo;
        this.aOPoNo = aOPoNo;
    }

    public int getARNo() {
        return aRNo;
    }

    public void setARNo(int aRNo) {
        this.aRNo = aRNo;
    }

    public int getAOPoNo() {
        return aOPoNo;
    }

    public void setAOPoNo(int aOPoNo) {
        this.aOPoNo = aOPoNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) aRNo;
        hash += (int) aOPoNo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AOrderPK)) {
            return false;
        }
        AOrderPK other = (AOrderPK) object;
        if (this.aRNo != other.aRNo) {
            return false;
        }
        if (this.aOPoNo != other.aOPoNo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.acquisition.suggestions.AOrderPK[ aRNo=" + aRNo + ", aOPoNo=" + aOPoNo + " ]";
    }
    
}
