/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation_master;

import java.io.Serializable;
import java.math.BigDecimal;
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
import soul.general_master.Libmaterials;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "m_ctgry_media")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MCtgryMedia.findAll", query = "SELECT m FROM MCtgryMedia m"),
    @NamedQuery(name = "MCtgryMedia.findByCtgryCd", query = "SELECT m FROM MCtgryMedia m WHERE m.mCtgryMediaPK.ctgryCd = :ctgryCd"),
    @NamedQuery(name = "MCtgryMedia.findByMediaCd", query = "SELECT m FROM MCtgryMedia m WHERE m.mCtgryMediaPK.mediaCd = :mediaCd"),
    @NamedQuery(name = "MCtgryMedia.findByIssPeriod", query = "SELECT m FROM MCtgryMedia m WHERE m.issPeriod = :issPeriod"),
    @NamedQuery(name = "MCtgryMedia.findByMaxAllowed", query = "SELECT m FROM MCtgryMedia m WHERE m.maxAllowed = :maxAllowed"),
    @NamedQuery(name = "MCtgryMedia.findByFineCharges", query = "SELECT m FROM MCtgryMedia m WHERE m.fineCharges = :fineCharges"),
    @NamedQuery(name = "MCtgryMedia.findByResPeriod", query = "SELECT m FROM MCtgryMedia m WHERE m.resPeriod = :resPeriod"),
    @NamedQuery(name = "MCtgryMedia.findByMaxReserve", query = "SELECT m FROM MCtgryMedia m WHERE m.maxReserve = :maxReserve"),
    @NamedQuery(name = "MCtgryMedia.findByFinePhase1Prd", query = "SELECT m FROM MCtgryMedia m WHERE m.finePhase1Prd = :finePhase1Prd"),
    @NamedQuery(name = "MCtgryMedia.findByFinePhase2Prd", query = "SELECT m FROM MCtgryMedia m WHERE m.finePhase2Prd = :finePhase2Prd"),
    @NamedQuery(name = "MCtgryMedia.findByFinePhase1Charge", query = "SELECT m FROM MCtgryMedia m WHERE m.finePhase1Charge = :finePhase1Charge"),
    @NamedQuery(name = "MCtgryMedia.findByFinePhase2Charge", query = "SELECT m FROM MCtgryMedia m WHERE m.finePhase2Charge = :finePhase2Charge"),
    @NamedQuery(name = "MCtgryMedia.findByCtgryCdAndMediaCd", query = "SELECT c FROM MCtgryMedia c WHERE c.mCtgryMediaPK.ctgryCd = ?1 AND c.mCtgryMediaPK.mediaCd = ?2")})
public class MCtgryMedia implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MCtgryMediaPK mCtgryMediaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iss_period")
    private int issPeriod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_allowed")
    private int maxAllowed;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "fine_charges")
    private BigDecimal fineCharges;
    @Basic(optional = false)
    @NotNull
    @Column(name = "res_period")
    private int resPeriod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_reserve")
    private int maxReserve;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fine_phase_1_prd")
    private int finePhase1Prd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fine_phase_2_prd")
    private int finePhase2Prd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fine_phase_1_charge")
    private BigDecimal finePhase1Charge;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fine_phase_2_charge")
    private BigDecimal finePhase2Charge;
    @JoinColumn(name = "ctgry_cd", referencedColumnName = "ctgry_cd", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MCtgry mCtgry;
    @JoinColumn(name = "media_cd", referencedColumnName = "Code", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Libmaterials libmaterials;

    public MCtgryMedia() {
    }

    public MCtgryMedia(MCtgryMediaPK mCtgryMediaPK) {
        this.mCtgryMediaPK = mCtgryMediaPK;
    }

    public MCtgryMedia(MCtgryMediaPK mCtgryMediaPK, int issPeriod, int maxAllowed, BigDecimal fineCharges, int resPeriod, int maxReserve, int finePhase1Prd, int finePhase2Prd, BigDecimal finePhase1Charge, BigDecimal finePhase2Charge) {
        this.mCtgryMediaPK = mCtgryMediaPK;
        this.issPeriod = issPeriod;
        this.maxAllowed = maxAllowed;
        this.fineCharges = fineCharges;
        this.resPeriod = resPeriod;
        this.maxReserve = maxReserve;
        this.finePhase1Prd = finePhase1Prd;
        this.finePhase2Prd = finePhase2Prd;
        this.finePhase1Charge = finePhase1Charge;
        this.finePhase2Charge = finePhase2Charge;
    }

    public MCtgryMedia(String ctgryCd, String mediaCd) {
        this.mCtgryMediaPK = new MCtgryMediaPK(ctgryCd, mediaCd);
    }

    public MCtgryMediaPK getMCtgryMediaPK() {
        return mCtgryMediaPK;
    }

    public void setMCtgryMediaPK(MCtgryMediaPK mCtgryMediaPK) {
        this.mCtgryMediaPK = mCtgryMediaPK;
    }

    public int getIssPeriod() {
        return issPeriod;
    }

    public void setIssPeriod(int issPeriod) {
        this.issPeriod = issPeriod;
    }

    public int getMaxAllowed() {
        return maxAllowed;
    }

    public void setMaxAllowed(int maxAllowed) {
        this.maxAllowed = maxAllowed;
    }

    public BigDecimal getFineCharges() {
        return fineCharges;
    }

    public void setFineCharges(BigDecimal fineCharges) {
        this.fineCharges = fineCharges;
    }

    public int getResPeriod() {
        return resPeriod;
    }

    public void setResPeriod(int resPeriod) {
        this.resPeriod = resPeriod;
    }

    public int getMaxReserve() {
        return maxReserve;
    }

    public void setMaxReserve(int maxReserve) {
        this.maxReserve = maxReserve;
    }

    public int getFinePhase1Prd() {
        return finePhase1Prd;
    }

    public void setFinePhase1Prd(int finePhase1Prd) {
        this.finePhase1Prd = finePhase1Prd;
    }

    public int getFinePhase2Prd() {
        return finePhase2Prd;
    }

    public void setFinePhase2Prd(int finePhase2Prd) {
        this.finePhase2Prd = finePhase2Prd;
    }

    public BigDecimal getFinePhase1Charge() {
        return finePhase1Charge;
    }

    public void setFinePhase1Charge(BigDecimal finePhase1Charge) {
        this.finePhase1Charge = finePhase1Charge;
    }

    public BigDecimal getFinePhase2Charge() {
        return finePhase2Charge;
    }

    public void setFinePhase2Charge(BigDecimal finePhase2Charge) {
        this.finePhase2Charge = finePhase2Charge;
    }

    public MCtgry getMCtgry() {
        return mCtgry;
    }

    public void setMCtgry(MCtgry mCtgry) {
        this.mCtgry = mCtgry;
    }

    public Libmaterials getLibmaterials() {
        return libmaterials;
    }

    public void setLibmaterials(Libmaterials libmaterials) {
        this.libmaterials = libmaterials;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mCtgryMediaPK != null ? mCtgryMediaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MCtgryMedia)) {
            return false;
        }
        MCtgryMedia other = (MCtgryMedia) object;
        if ((this.mCtgryMediaPK == null && other.mCtgryMediaPK != null) || (this.mCtgryMediaPK != null && !this.mCtgryMediaPK.equals(other.mCtgryMediaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation_master.MCtgryMedia[ mCtgryMediaPK=" + mCtgryMediaPK + " ]";
    }
    
}
