/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation_master;

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
public class MCtgryColllectionPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "ctgry_cd")
    private String ctgryCd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ct_cd")
    private String ctCd;

    public MCtgryColllectionPK() {
    }

    public MCtgryColllectionPK(String ctgryCd, String ctCd) {
        this.ctgryCd = ctgryCd;
        this.ctCd = ctCd;
    }

    public String getCtgryCd() {
        return ctgryCd;
    }

    public void setCtgryCd(String ctgryCd) {
        this.ctgryCd = ctgryCd;
    }

    public String getCtCd() {
        return ctCd;
    }

    public void setCtCd(String ctCd) {
        this.ctCd = ctCd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctgryCd != null ? ctgryCd.hashCode() : 0);
        hash += (ctCd != null ? ctCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MCtgryColllectionPK)) {
            return false;
        }
        MCtgryColllectionPK other = (MCtgryColllectionPK) object;
        if ((this.ctgryCd == null && other.ctgryCd != null) || (this.ctgryCd != null && !this.ctgryCd.equals(other.ctgryCd))) {
            return false;
        }
        if ((this.ctCd == null && other.ctCd != null) || (this.ctCd != null && !this.ctCd.equals(other.ctCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation_master.MCtgryColllectionPK[ ctgryCd=" + ctgryCd + ", ctCd=" + ctCd + " ]";
    }
    
}
