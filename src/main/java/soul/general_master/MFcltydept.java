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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import soul.acquisition.suggestions.ARequest;
import soul.catalogue.Location;
import soul.circulation.MMember;
import soul.serialControl.SRequest;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "m_fcltydept")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MFcltydept.findAll", query = "SELECT m FROM MFcltydept m"),
    @NamedQuery(name = "MFcltydept.findByFldtag", query = "SELECT m FROM MFcltydept m WHERE m.fldtag = :fldtag"),
    @NamedQuery(name = "MFcltydept.findByFcltydeptcd", query = "SELECT m FROM MFcltydept m WHERE m.fcltydeptcd = :fcltydeptcd"),
    @NamedQuery(name = "MFcltydept.findByInst", query = "SELECT m FROM MFcltydept m WHERE m.fcltydeptcd = 'I' "),
    @NamedQuery(name = "MFcltydept.findByFcltydeptdscr", query = "SELECT m FROM MFcltydept m WHERE m.fcltydeptdscr = :fcltydeptdscr"),
    @NamedQuery(name = "MFcltydept.findByFcltydeptadd1", query = "SELECT m FROM MFcltydept m WHERE m.fcltydeptadd1 = :fcltydeptadd1"),
    @NamedQuery(name = "MFcltydept.findByFcltydeptadd2", query = "SELECT m FROM MFcltydept m WHERE m.fcltydeptadd2 = :fcltydeptadd2"),
    @NamedQuery(name = "MFcltydept.findByFcltydeptcity", query = "SELECT m FROM MFcltydept m WHERE m.fcltydeptcity = :fcltydeptcity"),
    @NamedQuery(name = "MFcltydept.findByFcltydeptpin", query = "SELECT m FROM MFcltydept m WHERE m.fcltydeptpin = :fcltydeptpin"),
    @NamedQuery(name = "MFcltydept.findByFcltydeptphone", query = "SELECT m FROM MFcltydept m WHERE m.fcltydeptphone = :fcltydeptphone"),
    @NamedQuery(name = "MFcltydept.findByFcltydeptfax", query = "SELECT m FROM MFcltydept m WHERE m.fcltydeptfax = :fcltydeptfax"),
    @NamedQuery(name = "MFcltydept.findByInstituteName", query = "SELECT m FROM MFcltydept m WHERE m.fcltydeptdscr = :fcltydeptdscr AND m.fldtag = 'I' "),
    @NamedQuery(name = "MFcltydept.findByDepartmentName", query = "SELECT m FROM MFcltydept m WHERE m.fcltydeptdscr = :fcltydeptdscr AND m.fldtag = 'D' "),
    @NamedQuery(name = "MFcltydept.findDepartmentInstCdAndFldTag", query = "SELECT m FROM MFcltydept m WHERE m.instcd.fcltydeptcd = :instcd AND m.fldtag = 'D' "),
    @NamedQuery(name = "MFcltydept.findByFcltydeptemail", query = "SELECT m FROM MFcltydept m WHERE m.fcltydeptemail = :fcltydeptemail"),
    @NamedQuery(name = "MFcltydept.findByInstcd", query = "SELECT m FROM MFcltydept m WHERE m.instcd.fcltydeptcd = :instcd"),
})
public class MFcltydept implements Serializable {
    @OneToMany(mappedBy = "sRDeptCd")
    private Collection<SRequest> sRequestCollection;
    @OneToMany(mappedBy = "aRDeptCd")
    private Collection<ARequest> aRequestCollection;
    @OneToMany(mappedBy = "department")
    private Collection<Location> locationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memDept")
    private Collection<MMember> mMemberCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memInst")
    private Collection<MMember> mMemberCollection1;
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fld_tag")
    private char fldtag;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Fclty_dept_cd")
    private String fcltydeptcd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "Fclty_dept_dscr")
    private String fcltydeptdscr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "Fclty_dept_add1")
    private String fcltydeptadd1;
    @Size(max = 400)
    @Column(name = "Fclty_dept_add2")
    private String fcltydeptadd2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "Fclty_dept_city")
    private String fcltydeptcity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 24)
    @Column(name = "Fclty_dept_pin")
    private String fcltydeptpin;
    @Size(max = 200)
    @Column(name = "Fclty_dept_phone")
    private String fcltydeptphone;
    @Size(max = 200)
    @Column(name = "Fclty_dept_fax")
    private String fcltydeptfax;
    @Size(max = 200)
    @Column(name = "Fclty_dept_email")
    private String fcltydeptemail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mFcltydept")
    private Collection<MBranch> mBranchCollection;
    @OneToMany(mappedBy = "instcd")
    private Collection<MFcltydept> mFcltydeptCollection;
    @JoinColumn(name = "Inst_cd", referencedColumnName = "Fclty_dept_cd")
    @ManyToOne
    private MFcltydept instcd;

    public MFcltydept() {
    }

    public MFcltydept(String fcltydeptcd) {
        this.fcltydeptcd = fcltydeptcd;
    }

    public MFcltydept(String fcltydeptcd, char fldtag, String fcltydeptdscr, String fcltydeptadd1, String fcltydeptcity, String fcltydeptpin) {
        this.fcltydeptcd = fcltydeptcd;
        this.fldtag = fldtag;
        this.fcltydeptdscr = fcltydeptdscr;
        this.fcltydeptadd1 = fcltydeptadd1;
        this.fcltydeptcity = fcltydeptcity;
        this.fcltydeptpin = fcltydeptpin;
    }

    public char getFldtag() {
        return fldtag;
    }

    public void setFldtag(char fldtag) {
        this.fldtag = fldtag;
    }

    public String getFcltydeptcd() {
        return fcltydeptcd;
    }

    public void setFcltydeptcd(String fcltydeptcd) {
        this.fcltydeptcd = fcltydeptcd;
    }

    public String getFcltydeptdscr() {
        return fcltydeptdscr;
    }

    public void setFcltydeptdscr(String fcltydeptdscr) {
        this.fcltydeptdscr = fcltydeptdscr;
    }

    public String getFcltydeptadd1() {
        return fcltydeptadd1;
    }

    public void setFcltydeptadd1(String fcltydeptadd1) {
        this.fcltydeptadd1 = fcltydeptadd1;
    }

    public String getFcltydeptadd2() {
        return fcltydeptadd2;
    }

    public void setFcltydeptadd2(String fcltydeptadd2) {
        this.fcltydeptadd2 = fcltydeptadd2;
    }

    public String getFcltydeptcity() {
        return fcltydeptcity;
    }

    public void setFcltydeptcity(String fcltydeptcity) {
        this.fcltydeptcity = fcltydeptcity;
    }

    public String getFcltydeptpin() {
        return fcltydeptpin;
    }

    public void setFcltydeptpin(String fcltydeptpin) {
        this.fcltydeptpin = fcltydeptpin;
    }

    public String getFcltydeptphone() {
        return fcltydeptphone;
    }

    public void setFcltydeptphone(String fcltydeptphone) {
        this.fcltydeptphone = fcltydeptphone;
    }

    public String getFcltydeptfax() {
        return fcltydeptfax;
    }

    public void setFcltydeptfax(String fcltydeptfax) {
        this.fcltydeptfax = fcltydeptfax;
    }

    public String getFcltydeptemail() {
        return fcltydeptemail;
    }

    public void setFcltydeptemail(String fcltydeptemail) {
        this.fcltydeptemail = fcltydeptemail;
    }

    @XmlTransient
    public Collection<MBranch> getMBranchCollection() {
        return mBranchCollection;
    }

    public void setMBranchCollection(Collection<MBranch> mBranchCollection) {
        this.mBranchCollection = mBranchCollection;
    }

    @XmlTransient
    public Collection<MFcltydept> getMFcltydeptCollection() {
        return mFcltydeptCollection;
    }

    public void setMFcltydeptCollection(Collection<MFcltydept> mFcltydeptCollection) {
        this.mFcltydeptCollection = mFcltydeptCollection;
    }

    public MFcltydept getInstcd() {
        return instcd;
    }

    public void setInstcd(MFcltydept instcd) {
        this.instcd = instcd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fcltydeptcd != null ? fcltydeptcd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MFcltydept)) {
            return false;
        }
        MFcltydept other = (MFcltydept) object;
        if ((this.fcltydeptcd == null && other.fcltydeptcd != null) || (this.fcltydeptcd != null && !this.fcltydeptcd.equals(other.fcltydeptcd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.MFcltydept[ fcltydeptcd=" + fcltydeptcd + " ]";
    }

    @XmlTransient
    public Collection<MMember> getMMemberCollection() {
        return mMemberCollection;
    }

    public void setMMemberCollection(Collection<MMember> mMemberCollection) {
        this.mMemberCollection = mMemberCollection;
    }

    @XmlTransient
    public Collection<MMember> getMMemberCollection1() {
        return mMemberCollection1;
    }

    public void setMMemberCollection1(Collection<MMember> mMemberCollection1) {
        this.mMemberCollection1 = mMemberCollection1;
    }

    @XmlTransient
    public Collection<Location> getLocationCollection() {
        return locationCollection;
    }

    public void setLocationCollection(Collection<Location> locationCollection) {
        this.locationCollection = locationCollection;
    }

    @XmlTransient
    public Collection<ARequest> getARequestCollection() {
        return aRequestCollection;
    }

    public void setARequestCollection(Collection<ARequest> aRequestCollection) {
        this.aRequestCollection = aRequestCollection;
    }

    @XmlTransient
    public Collection<SRequest> getSRequestCollection() {
        return sRequestCollection;
    }

    public void setSRequestCollection(Collection<SRequest> sRequestCollection) {
        this.sRequestCollection = sRequestCollection;
    }
    
}
