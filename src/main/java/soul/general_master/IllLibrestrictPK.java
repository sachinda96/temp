/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master;

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
public class IllLibrestrictPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "lib_cd")
    private String libCd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "lib_media_cd")
    private String libMediaCd;

    public IllLibrestrictPK() {
    }

    public IllLibrestrictPK(String libCd, String libMediaCd) {
        this.libCd = libCd;
        this.libMediaCd = libMediaCd;
    }

    public String getLibCd() {
        return libCd;
    }

    public void setLibCd(String libCd) {
        this.libCd = libCd;
    }

    public String getLibMediaCd() {
        return libMediaCd;
    }

    public void setLibMediaCd(String libMediaCd) {
        this.libMediaCd = libMediaCd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (libCd != null ? libCd.hashCode() : 0);
        hash += (libMediaCd != null ? libMediaCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IllLibrestrictPK)) {
            return false;
        }
        IllLibrestrictPK other = (IllLibrestrictPK) object;
        if ((this.libCd == null && other.libCd != null) || (this.libCd != null && !this.libCd.equals(other.libCd))) {
            return false;
        }
        if ((this.libMediaCd == null && other.libMediaCd != null) || (this.libMediaCd != null && !this.libMediaCd.equals(other.libMediaCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.IllLibrestrictPK[ libCd=" + libCd + ", libMediaCd=" + libMediaCd + " ]";
    }
    
}
