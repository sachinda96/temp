/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation_master;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import soul.circulation.MMember;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "m_ctgry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MCtgry.findAll", query = "SELECT m FROM MCtgry m"),
    @NamedQuery(name = "MCtgry.getCtgryCdAndName", query = "SELECT m.ctgryCd, m.ctgryDesc FROM MCtgry m"),
    @NamedQuery(name = "MCtgry.findByCtgryCd", query = "SELECT m FROM MCtgry m WHERE m.ctgryCd = :ctgryCd"),
    @NamedQuery(name = "MCtgry.findByCtgryDesc", query = "SELECT m FROM MCtgry m WHERE m.ctgryDesc = :ctgryDesc"),
    @NamedQuery(name = "MCtgry.findByCtgryDuration", query = "SELECT m FROM MCtgry m WHERE m.ctgryDuration = :ctgryDuration"),
    @NamedQuery(name = "MCtgry.findByCtgryCharges", query = "SELECT m FROM MCtgry m WHERE m.ctgryCharges = :ctgryCharges"),
    @NamedQuery(name = "MCtgry.findByMaxDue", query = "SELECT m FROM MCtgry m WHERE m.maxDue = :maxDue"),
    @NamedQuery(name = "MCtgry.findByLastOperdt", query = "SELECT m FROM MCtgry m WHERE m.lastOperdt = :lastOperdt"),
    @NamedQuery(name = "MCtgry.findByUserCd", query = "SELECT m FROM MCtgry m WHERE m.userCd = :userCd"),
    @NamedQuery(name = "MCtgry.findByCtgryDeposit", query = "SELECT m FROM MCtgry m WHERE m.ctgryDeposit = :ctgryDeposit"),
    @NamedQuery(name = "MCtgry.findByCtgryEndDt", query = "SELECT m FROM MCtgry m WHERE m.ctgryEndDt = :ctgryEndDt"),
    @NamedQuery(name = "MCtgry.findByMaxBookAllow", query = "SELECT m FROM MCtgry m WHERE m.maxBookAllow = :maxBookAllow"),
    @NamedQuery(name = "MCtgry.findByCtgryDescr", query = "SELECT m.ctgryDesc FROM MCtgry m")
})
public class MCtgry implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memCtgry")
    private Collection<MMember> mMemberCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mCtgry")
    private Collection<MCtgryMedia> mCtgryMediaCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "ctgry_cd")
    private String ctgryCd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ctgry_desc")
    private String ctgryDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ctgry_duration")
    private int ctgryDuration;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ctgry_charges")
    private BigDecimal ctgryCharges;
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_due")
    private BigDecimal maxDue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "last_operdt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastOperdt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "user_cd")
    private String userCd;
    @Column(name = "ctgry_deposit")
    private BigDecimal ctgryDeposit;
    @Column(name = "ctgry_end_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctgryEndDt;
    @Column(name = "max_BookAllow")
    private Integer maxBookAllow;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mCtgry")
    private Collection<MCtgryColllection> mCtgryColllectionCollection;

    public MCtgry() {
    }

    public MCtgry(String ctgryCd) {
        this.ctgryCd = ctgryCd;
    }

    public MCtgry(String ctgryCd, String ctgryDesc, int ctgryDuration, BigDecimal ctgryCharges, BigDecimal maxDue, Date lastOperdt, String userCd) {
        this.ctgryCd = ctgryCd;
        this.ctgryDesc = ctgryDesc;
        this.ctgryDuration = ctgryDuration;
        this.ctgryCharges = ctgryCharges;
        this.maxDue = maxDue;
        this.lastOperdt = lastOperdt;
        this.userCd = userCd;
    }

    public String getCtgryCd() {
        return ctgryCd;
    }

    public void setCtgryCd(String ctgryCd) {
        this.ctgryCd = ctgryCd;
    }

    public String getCtgryDesc() {
        return ctgryDesc;
    }

    public void setCtgryDesc(String ctgryDesc) {
        this.ctgryDesc = ctgryDesc;
    }

    public int getCtgryDuration() {
        return ctgryDuration;
    }

    public void setCtgryDuration(int ctgryDuration) {
        this.ctgryDuration = ctgryDuration;
    }

    public BigDecimal getCtgryCharges() {
        return ctgryCharges;
    }

    public void setCtgryCharges(BigDecimal ctgryCharges) {
        this.ctgryCharges = ctgryCharges;
    }

    public BigDecimal getMaxDue() {
        return maxDue;
    }

    public void setMaxDue(BigDecimal maxDue) {
        this.maxDue = maxDue;
    }

    public Date getLastOperdt() {
        return lastOperdt;
    }

    public void setLastOperdt(Date lastOperdt) {
        this.lastOperdt = lastOperdt;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public BigDecimal getCtgryDeposit() {
        return ctgryDeposit;
    }

    public void setCtgryDeposit(BigDecimal ctgryDeposit) {
        this.ctgryDeposit = ctgryDeposit;
    }

    public Date getCtgryEndDt() {
        return ctgryEndDt;
    }

    public void setCtgryEndDt(Date ctgryEndDt) {
        this.ctgryEndDt = ctgryEndDt;
    }

    public Integer getMaxBookAllow() {
        return maxBookAllow;
    }

    public void setMaxBookAllow(Integer maxBookAllow) {
        this.maxBookAllow = maxBookAllow;
    }

    @XmlTransient
    public Collection<MCtgryColllection> getMCtgryColllectionCollection() {
        return mCtgryColllectionCollection;
    }

    public void setMCtgryColllectionCollection(Collection<MCtgryColllection> mCtgryColllectionCollection) {
        this.mCtgryColllectionCollection = mCtgryColllectionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctgryCd != null ? ctgryCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MCtgry)) {
            return false;
        }
        MCtgry other = (MCtgry) object;
        if ((this.ctgryCd == null && other.ctgryCd != null) || (this.ctgryCd != null && !this.ctgryCd.equals(other.ctgryCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation_master.MCtgry[ ctgryCd=" + ctgryCd + " ]";
    }

    @XmlTransient
    public Collection<MCtgryMedia> getMCtgryMediaCollection() {
        return mCtgryMediaCollection;
    }

    public void setMCtgryMediaCollection(Collection<MCtgryMedia> mCtgryMediaCollection) {
        this.mCtgryMediaCollection = mCtgryMediaCollection;
    }

    @XmlTransient
    public Collection<MMember> getMMemberCollection() {
        return mMemberCollection;
    }

    public void setMMemberCollection(Collection<MMember> mMemberCollection) {
        this.mMemberCollection = mMemberCollection;
    }
    
}
