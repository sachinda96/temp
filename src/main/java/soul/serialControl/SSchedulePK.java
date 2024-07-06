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
public class SSchedulePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "s_s_srno")
    private int sSSrno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_s_recid")
    private String sSRecid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_s_vol")
    private String sSVol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_s_iss")
    private String sSIss;

    public SSchedulePK() {
    }

    public SSchedulePK(int sSSrno, String sSRecid, String sSVol, String sSIss) {
        this.sSSrno = sSSrno;
        this.sSRecid = sSRecid;
        this.sSVol = sSVol;
        this.sSIss = sSIss;
    }

    public int getSSSrno() {
        return sSSrno;
    }

    public void setSSSrno(int sSSrno) {
        this.sSSrno = sSSrno;
    }

    public String getSSRecid() {
        return sSRecid;
    }

    public void setSSRecid(String sSRecid) {
        this.sSRecid = sSRecid;
    }

    public String getSSVol() {
        return sSVol;
    }

    public void setSSVol(String sSVol) {
        this.sSVol = sSVol;
    }

    public String getSSIss() {
        return sSIss;
    }

    public void setSSIss(String sSIss) {
        this.sSIss = sSIss;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) sSSrno;
        hash += (sSRecid != null ? sSRecid.hashCode() : 0);
        hash += (sSVol != null ? sSVol.hashCode() : 0);
        hash += (sSIss != null ? sSIss.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SSchedulePK)) {
            return false;
        }
        SSchedulePK other = (SSchedulePK) object;
        if (this.sSSrno != other.sSSrno) {
            return false;
        }
        if ((this.sSRecid == null && other.sSRecid != null) || (this.sSRecid != null && !this.sSRecid.equals(other.sSRecid))) {
            return false;
        }
        if ((this.sSVol == null && other.sSVol != null) || (this.sSVol != null && !this.sSVol.equals(other.sSVol))) {
            return false;
        }
        if ((this.sSIss == null && other.sSIss != null) || (this.sSIss != null && !this.sSIss.equals(other.sSIss))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serialControl.SSchedulePK[ sSSrno=" + sSSrno + ", sSRecid=" + sSRecid + ", sSVol=" + sSVol + ", sSIss=" + sSIss + " ]";
    }
    
}
