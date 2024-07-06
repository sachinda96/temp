/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "ill_librestrict")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IllLibrestrict.findAll", query = "SELECT i FROM IllLibrestrict i"),
    @NamedQuery(name = "IllLibrestrict.findByLibCd", query = "SELECT i FROM IllLibrestrict i WHERE i.illLibrestrictPK.libCd = :libCd"),
    @NamedQuery(name = "IllLibrestrict.findByLibMediaCd", query = "SELECT i FROM IllLibrestrict i WHERE i.illLibrestrictPK.libMediaCd = :libMediaCd"),
    @NamedQuery(name = "IllLibrestrict.findByMxAllowed", query = "SELECT i FROM IllLibrestrict i WHERE i.mxAllowed = :mxAllowed"),
    @NamedQuery(name = "IllLibrestrict.findByFineChrg", query = "SELECT i FROM IllLibrestrict i WHERE i.fineChrg = :fineChrg"),
    @NamedQuery(name = "IllLibrestrict.findByMxDays", query = "SELECT i FROM IllLibrestrict i WHERE i.mxDays = :mxDays"),
    @NamedQuery(name = "IllLibrestrict.findByIdAndMeadiaCd", query = "SELECT i FROM IllLibrestrict i WHERE i.illLibrestrictPK.libCd = ?1 and i.illLibrestrictPK.libMediaCd = ?2"),
    @NamedQuery(name = "IllLibrestrict.removeBylibCdAndlibMediaCd", query = "DELETE FROM IllLibrestrict i WHERE i.illLibrestrictPK.libCd = ?1 and i.illLibrestrictPK.libMediaCd = ?2")})
public class IllLibrestrict implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IllLibrestrictPK illLibrestrictPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mx_allowed")
    private int mxAllowed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fine_chrg")
    private float fineChrg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mx_days")
    private int mxDays;
    @JoinColumn(name = "lib_media_cd", referencedColumnName = "Code", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Libmaterials libmaterials;
    @JoinColumn(name = "lib_cd", referencedColumnName = "lib_cd", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private IllLibmst illLibmst;

    public IllLibrestrict() {
    }

    public IllLibrestrict(IllLibrestrictPK illLibrestrictPK) {
        this.illLibrestrictPK = illLibrestrictPK;
    }

    public IllLibrestrict(IllLibrestrictPK illLibrestrictPK, int mxAllowed, float fineChrg, int mxDays) {
        this.illLibrestrictPK = illLibrestrictPK;
        this.mxAllowed = mxAllowed;
        this.fineChrg = fineChrg;
        this.mxDays = mxDays;
    }

    public IllLibrestrict(String libCd, String libMediaCd) {
        this.illLibrestrictPK = new IllLibrestrictPK(libCd, libMediaCd);
    }

    public IllLibrestrictPK getIllLibrestrictPK() {
        return illLibrestrictPK;
    }

    public void setIllLibrestrictPK(IllLibrestrictPK illLibrestrictPK) {
        this.illLibrestrictPK = illLibrestrictPK;
    }

    public int getMxAllowed() {
        return mxAllowed;
    }

    public void setMxAllowed(int mxAllowed) {
        this.mxAllowed = mxAllowed;
    }

    public float getFineChrg() {
        return fineChrg;
    }

    public void setFineChrg(float fineChrg) {
        this.fineChrg = fineChrg;
    }

    public int getMxDays() {
        return mxDays;
    }

    public void setMxDays(int mxDays) {
        this.mxDays = mxDays;
    }

    public Libmaterials getLibmaterials() {
        return libmaterials;
    }

    public void setLibmaterials(Libmaterials libmaterials) {
        this.libmaterials = libmaterials;
    }

    public IllLibmst getIllLibmst() {
        return illLibmst;
    }

    public void setIllLibmst(IllLibmst illLibmst) {
        this.illLibmst = illLibmst;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (illLibrestrictPK != null ? illLibrestrictPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IllLibrestrict)) {
            return false;
        }
        IllLibrestrict other = (IllLibrestrict) object;
        if ((this.illLibrestrictPK == null && other.illLibrestrictPK != null) || (this.illLibrestrictPK != null && !this.illLibrestrictPK.equals(other.illLibrestrictPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.IllLibrestrict[ illLibrestrictPK=" + illLibrestrictPK + " ]";
    }
    
}
