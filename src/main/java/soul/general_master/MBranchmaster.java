/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "m_branchmaster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MBranchmaster.findAll", query = "SELECT m FROM MBranchmaster m"),
    @NamedQuery(name = "MBranchmaster.findByBranchCd", query = "SELECT m FROM MBranchmaster m WHERE m.branchCd = :branchCd"),
    @NamedQuery(name = "MBranchmaster.findByBranchname", query = "SELECT m FROM MBranchmaster m WHERE m.branchname = :branchname")})
public class MBranchmaster implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memDegree")
    private Collection<MMember> mMemberCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mBranchmaster")
    private Collection<MBranch> mBranchCollection;
    private static final long serialVersionUID = 1L;
    @Id
    //GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "branch_cd")
    private String branchCd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Branch_name")
    private String branchname;

    public MBranchmaster() {
    }

    public MBranchmaster(String branchCd) {
        this.branchCd = branchCd;
    }

    public MBranchmaster(String branchCd, String branchname) {
        this.branchCd = branchCd;
        this.branchname = branchname;
    }

    public String getBranchCd() {
        return branchCd;
    }

    public void setBranchCd(String branchCd) {
        this.branchCd = branchCd;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (branchCd != null ? branchCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MBranchmaster)) {
            return false;
        }
        MBranchmaster other = (MBranchmaster) object;
        if ((this.branchCd == null && other.branchCd != null) || (this.branchCd != null && !this.branchCd.equals(other.branchCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.MBranchmaster[ branchCd=" + branchCd + " ]";
    }

    @XmlTransient
    public Collection<MBranch> getMBranchCollection() {
        return mBranchCollection;
    }

    public void setMBranchCollection(Collection<MBranch> mBranchCollection) {
        this.mBranchCollection = mBranchCollection;
    }

    @XmlTransient
    public Collection<MMember> getMMemberCollection() {
        return mMemberCollection;
    }

    public void setMMemberCollection(Collection<MMember> mMemberCollection) {
        this.mMemberCollection = mMemberCollection;
    }
    
}