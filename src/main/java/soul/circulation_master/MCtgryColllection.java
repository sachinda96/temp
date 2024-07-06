/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation_master;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import soul.system_setting.AccessionCtype;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "m_ctgry_colllection")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MCtgryColllection.findAll", query = "SELECT m FROM MCtgryColllection m"),
    @NamedQuery(name = "MCtgryColllection.findByCtgryCd", query = "SELECT m FROM MCtgryColllection m WHERE m.mCtgryColllectionPK.ctgryCd = :ctgryCd"),
    @NamedQuery(name = "MCtgryColllection.findByCtCd", query = "SELECT m FROM MCtgryColllection m WHERE m.mCtgryColllectionPK.ctCd = :ctCd"),
    @NamedQuery(name = "MCtgryColllection.findByONIssueAllow", query = "SELECT m FROM MCtgryColllection m WHERE m.oNIssueAllow = :oNIssueAllow"),
    @NamedQuery(name = "MCtgryColllection.findByOPIssueAllow", query = "SELECT m FROM MCtgryColllection m WHERE m.oPIssueAllow = :oPIssueAllow"),
    @NamedQuery(name = "MCtgryColllection.findByOPTime", query = "SELECT m FROM MCtgryColllection m WHERE m.oPTime = :oPTime"),
    @NamedQuery(name = "MCtgryColllection.findByMaxReserveColletype", query = "SELECT m FROM MCtgryColllection m WHERE m.maxReserveColletype = :maxReserveColletype"),
    @NamedQuery(name = "MCtgryColllection.findByCtgryCdAndCtCd", query = "SELECT m FROM MCtgryColllection m WHERE m.mCtgryColllectionPK.ctgryCd = ?1 AND m.mCtgryColllectionPK.ctCd = ?2"),
})
public class MCtgryColllection implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MCtgryColllectionPK mCtgryColllectionPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ON_Issue_Allow")
    private char oNIssueAllow;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OP_Issue_Allow")
    private char oPIssueAllow;
    @Column(name = "OP_Time")
    @Temporal(TemporalType.TIME)
    private Date oPTime;
    @Column(name = "Max_Reserve_Colletype")
    private Integer maxReserveColletype;
    @JoinColumn(name = "ct_cd", referencedColumnName = "cid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AccessionCtype accessionCtype;
    @JoinColumn(name = "ctgry_cd", referencedColumnName = "ctgry_cd", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MCtgry mCtgry;

    public MCtgryColllection() {
    }

    public MCtgryColllection(MCtgryColllectionPK mCtgryColllectionPK) {
        this.mCtgryColllectionPK = mCtgryColllectionPK;
    }

    public MCtgryColllection(MCtgryColllectionPK mCtgryColllectionPK, char oNIssueAllow, char oPIssueAllow) {
        this.mCtgryColllectionPK = mCtgryColllectionPK;
        this.oNIssueAllow = oNIssueAllow;
        this.oPIssueAllow = oPIssueAllow;
    }

    public MCtgryColllection(String ctgryCd, String ctCd) {
        this.mCtgryColllectionPK = new MCtgryColllectionPK(ctgryCd, ctCd);
    }

    public MCtgryColllectionPK getMCtgryColllectionPK() {
        return mCtgryColllectionPK;
    }

    public void setMCtgryColllectionPK(MCtgryColllectionPK mCtgryColllectionPK) {
        this.mCtgryColllectionPK = mCtgryColllectionPK;
    }

    public char getONIssueAllow() {
        return oNIssueAllow;
    }

    public void setONIssueAllow(char oNIssueAllow) {
        this.oNIssueAllow = oNIssueAllow;
    }

    public char getOPIssueAllow() {
        return oPIssueAllow;
    }

    public void setOPIssueAllow(char oPIssueAllow) {
        this.oPIssueAllow = oPIssueAllow;
    }

    public Date getOPTime() {
        return oPTime;
    }

    public void setOPTime(Date oPTime) {
        this.oPTime = oPTime;
    }

    public Integer getMaxReserveColletype() {
        return maxReserveColletype;
    }

    public void setMaxReserveColletype(Integer maxReserveColletype) {
        this.maxReserveColletype = maxReserveColletype;
    }

    public AccessionCtype getAccessionCtype() {
        return accessionCtype;
    }

    public void setAccessionCtype(AccessionCtype accessionCtype) {
        this.accessionCtype = accessionCtype;
    }

    public MCtgry getMCtgry() {
        return mCtgry;
    }

    public void setMCtgry(MCtgry mCtgry) {
        this.mCtgry = mCtgry;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mCtgryColllectionPK != null ? mCtgryColllectionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MCtgryColllection)) {
            return false;
        }
        MCtgryColllection other = (MCtgryColllection) object;
        if ((this.mCtgryColllectionPK == null && other.mCtgryColllectionPK != null) || (this.mCtgryColllectionPK != null && !this.mCtgryColllectionPK.equals(other.mCtgryColllectionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation_master.MCtgryColllection[ mCtgryColllectionPK=" + mCtgryColllectionPK + " ]";
    }
    
}
