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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "m_branch")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MBranch.findAll", query = "SELECT m FROM MBranch m"),
    @NamedQuery(name = "MBranch.findByBranchcd", query = "SELECT m FROM MBranch m WHERE m.mBranchPK.branchcd = :branchcd"),
    @NamedQuery(name = "MBranch.findByFcltycd", query = "SELECT m FROM MBranch m WHERE m.mBranchPK.fcltycd = :fcltycd"),
    @NamedQuery(name = "MBranch.findByBranchdescr", query = "SELECT m FROM MBranch m WHERE m.branchdescr = :branchdescr"),
    @NamedQuery(name = "MBranch.reportByInstitute", query = "SELECT m FROM MBranch m WHERE m.mFcltydept.fcltydeptcd = m.mFcltydept.instcd and m.mBranchPK.branchcd = m.mBranchmaster.branchCd and m.mFcltydept.fcltydeptcd = m.mBranchPK.fcltycd and m.mFcltydept.fldtag = 'I' and m.mFcltydept.fldtag = 'D' and m.mFcltydept.fcltydeptcd = ?1"),
    @NamedQuery(name = "MBranch.reportByDepartment", query = "SELECT m FROM MBranch m WHERE m.branchdescr = :branchdescr"),
    @NamedQuery(name = "MBranch.reportByBranch", query = "SELECT m FROM MBranch m WHERE m.branchdescr = :branchdescr"),
    @NamedQuery(name = "MBranch.deleteByBranchCdAndFacultyCd", query = "DELETE FROM MBranch m WHERE m.mBranchPK.branchcd = ?1 AND  m.mBranchPK.fcltycd = ?2")})
public class MBranch implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MBranchPK mBranchPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Branch_descr")
    private String branchdescr;
    @JoinColumn(name = "Fclty_cd", referencedColumnName = "Fclty_dept_cd", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MFcltydept mFcltydept;
    @JoinColumn(name = "Branch_cd", referencedColumnName = "branch_cd", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MBranchmaster mBranchmaster;

    public MBranch() {
    }

    public MBranch(MBranchPK mBranchPK) {
        this.mBranchPK = mBranchPK;
    }

    public MBranch(MBranchPK mBranchPK, String branchdescr) {
        this.mBranchPK = mBranchPK;
        this.branchdescr = branchdescr;
    }

    public MBranch(String branchcd, String fcltycd) {
        this.mBranchPK = new MBranchPK(branchcd, fcltycd);
    }

    public MBranchPK getMBranchPK() {
        return mBranchPK;
    }

    public void setMBranchPK(MBranchPK mBranchPK) {
        this.mBranchPK = mBranchPK;
    }

    public String getBranchdescr() {
        return branchdescr;
    }

    public void setBranchdescr(String branchdescr) {
        this.branchdescr = branchdescr;
    }

    public MFcltydept getMFcltydept() {
        return mFcltydept;
    }

    public void setMFcltydept(MFcltydept mFcltydept) {
        this.mFcltydept = mFcltydept;
    }

    public MBranchmaster getMBranchmaster() {
        return mBranchmaster;
    }

    public void setMBranchmaster(MBranchmaster mBranchmaster) {
        this.mBranchmaster = mBranchmaster;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mBranchPK != null ? mBranchPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MBranch)) {
            return false;
        }
        MBranch other = (MBranch) object;
        if ((this.mBranchPK == null && other.mBranchPK != null) || (this.mBranchPK != null && !this.mBranchPK.equals(other.mBranchPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.MBranch[ mBranchPK=" + mBranchPK + " ]";
    }
    
}
