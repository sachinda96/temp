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
public class MCtgryMediaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "ctgry_cd")
    private String ctgryCd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "media_cd")
    private String mediaCd;

    public MCtgryMediaPK() {
    }

    public MCtgryMediaPK(String ctgryCd, String mediaCd) {
        this.ctgryCd = ctgryCd;
        this.mediaCd = mediaCd;
    }

    public String getCtgryCd() {
        return ctgryCd;
    }

    public void setCtgryCd(String ctgryCd) {
        this.ctgryCd = ctgryCd;
    }

    public String getMediaCd() {
        return mediaCd;
    }

    public void setMediaCd(String mediaCd) {
        this.mediaCd = mediaCd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctgryCd != null ? ctgryCd.hashCode() : 0);
        hash += (mediaCd != null ? mediaCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MCtgryMediaPK)) {
            return false;
        }
        MCtgryMediaPK other = (MCtgryMediaPK) object;
        if ((this.ctgryCd == null && other.ctgryCd != null) || (this.ctgryCd != null && !this.ctgryCd.equals(other.ctgryCd))) {
            return false;
        }
        if ((this.mediaCd == null && other.mediaCd != null) || (this.mediaCd != null && !this.mediaCd.equals(other.mediaCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation_master.MCtgryMediaPK[ ctgryCd=" + ctgryCd + ", mediaCd=" + mediaCd + " ]";
    }
    
}
