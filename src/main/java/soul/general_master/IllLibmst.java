/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master;

import java.io.Serializable;
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
import soul.circulation.IllExtissue;
import soul.circulation.IllExtissuedmp;
import soul.circulation.IllReqprocdmp;
import soul.circulation.IllRqstmst;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "ill_libmst")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IllLibmst.findAll", query = "SELECT i FROM IllLibmst i"),
    @NamedQuery(name = "IllLibmst.findByLibCd", query = "SELECT i FROM IllLibmst i WHERE i.libCd = :libCd"),
    @NamedQuery(name = "IllLibmst.findByLibNm", query = "SELECT i FROM IllLibmst i WHERE i.libNm = :libNm"),
    @NamedQuery(name = "IllLibmst.findByLibAdd1", query = "SELECT i FROM IllLibmst i WHERE i.libAdd1 = :libAdd1"),
    @NamedQuery(name = "IllLibmst.findByLibAdd2", query = "SELECT i FROM IllLibmst i WHERE i.libAdd2 = :libAdd2"),
    @NamedQuery(name = "IllLibmst.findByLibCity", query = "SELECT i FROM IllLibmst i WHERE i.libCity = :libCity"),
    @NamedQuery(name = "IllLibmst.findByLibPin", query = "SELECT i FROM IllLibmst i WHERE i.libPin = :libPin"),
    @NamedQuery(name = "IllLibmst.findByLibPhone", query = "SELECT i FROM IllLibmst i WHERE i.libPhone = :libPhone"),
    @NamedQuery(name = "IllLibmst.findByLibFax", query = "SELECT i FROM IllLibmst i WHERE i.libFax = :libFax"),
    @NamedQuery(name = "IllLibmst.findByLibEmail", query = "SELECT i FROM IllLibmst i WHERE i.libEmail = :libEmail"),
    @NamedQuery(name = "IllLibmst.findByLibrarianNm", query = "SELECT i FROM IllLibmst i WHERE i.librarianNm = :librarianNm"),
    @NamedQuery(name = "IllLibmst.findByLibCntPrsn", query = "SELECT i FROM IllLibmst i WHERE i.libCntPrsn = :libCntPrsn"),
    @NamedQuery(name = "IllLibmst.findByEfctDtFrm", query = "SELECT i FROM IllLibmst i WHERE i.efctDtFrm = :efctDtFrm"),
    @NamedQuery(name = "IllLibmst.findByEfctDtTo", query = "SELECT i FROM IllLibmst i WHERE i.efctDtTo = :efctDtTo"),
    @NamedQuery(name = "IllLibmst.findByAgrmntLtrNo", query = "SELECT i FROM IllLibmst i WHERE i.agrmntLtrNo = :agrmntLtrNo"),
    @NamedQuery(name = "IllLibmst.findByRemarks", query = "SELECT i FROM IllLibmst i WHERE i.remarks = :remarks"),
    @NamedQuery(name = "IllLibmst.findByInstDeptTag", query = "SELECT i FROM IllLibmst i WHERE i.instDeptTag = :instDeptTag")})
public class IllLibmst implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "libCd")
    private Collection<IllExtissuedmp> illExtissuedmpCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "libCd")
    private Collection<IllExtissue> illExtissueCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "libCd")
    private Collection<IllReqprocdmp> illReqprocdmpCollection;
    @OneToMany(mappedBy = "libCd")
    private Collection<IllRqstmst> illRqstmstCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "lib_cd")
    private String libCd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "lib_nm")
    private String libNm;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "lib_add1")
    private String libAdd1;
    @Size(max = 250)
    @Column(name = "lib_add2")
    private String libAdd2;
    @Size(max = 250)
    @Column(name = "lib_city")
    private String libCity;
    @Size(max = 12)
    @Column(name = "lib_pin")
    private String libPin;
    @Size(max = 50)
    @Column(name = "lib_phone")
    private String libPhone;
    @Size(max = 50)
    @Column(name = "lib_fax")
    private String libFax;
    @Size(max = 200)
    @Column(name = "lib_email")
    private String libEmail;
    @Size(max = 250)
    @Column(name = "librarian_nm")
    private String librarianNm;
    @Size(max = 250)
    @Column(name = "lib_cnt_prsn")
    private String libCntPrsn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "efct_dt_frm")
    @Temporal(TemporalType.DATE)
    private Date efctDtFrm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "efct_dt_to")
    @Temporal(TemporalType.DATE)
    private Date efctDtTo;
    @Size(max = 50)
    @Column(name = "agrmnt_ltr_no")
    private String agrmntLtrNo;
    @Size(max = 250)
    @Column(name = "remarks")
    private String remarks;
    @Size(max = 2)
    @Column(name = "inst_dept_tag")
    private String instDeptTag;

    public IllLibmst() {
    }

    public IllLibmst(String libCd) {
        this.libCd = libCd;
    }

    public IllLibmst(String libCd, String libNm, String libAdd1, Date efctDtFrm, Date efctDtTo) {
        this.libCd = libCd;
        this.libNm = libNm;
        this.libAdd1 = libAdd1;
        this.efctDtFrm = efctDtFrm;
        this.efctDtTo = efctDtTo;
    }

    public String getLibCd() {
        return libCd;
    }

    public void setLibCd(String libCd) {
        this.libCd = libCd;
    }

    public String getLibNm() {
        return libNm;
    }

    public void setLibNm(String libNm) {
        this.libNm = libNm;
    }

    public String getLibAdd1() {
        return libAdd1;
    }

    public void setLibAdd1(String libAdd1) {
        this.libAdd1 = libAdd1;
    }

    public String getLibAdd2() {
        return libAdd2;
    }

    public void setLibAdd2(String libAdd2) {
        this.libAdd2 = libAdd2;
    }

    public String getLibCity() {
        return libCity;
    }

    public void setLibCity(String libCity) {
        this.libCity = libCity;
    }

    public String getLibPin() {
        return libPin;
    }

    public void setLibPin(String libPin) {
        this.libPin = libPin;
    }

    public String getLibPhone() {
        return libPhone;
    }

    public void setLibPhone(String libPhone) {
        this.libPhone = libPhone;
    }

    public String getLibFax() {
        return libFax;
    }

    public void setLibFax(String libFax) {
        this.libFax = libFax;
    }

    public String getLibEmail() {
        return libEmail;
    }

    public void setLibEmail(String libEmail) {
        this.libEmail = libEmail;
    }

    public String getLibrarianNm() {
        return librarianNm;
    }

    public void setLibrarianNm(String librarianNm) {
        this.librarianNm = librarianNm;
    }

    public String getLibCntPrsn() {
        return libCntPrsn;
    }

    public void setLibCntPrsn(String libCntPrsn) {
        this.libCntPrsn = libCntPrsn;
    }

    public Date getEfctDtFrm() {
        return efctDtFrm;
    }

    public void setEfctDtFrm(Date efctDtFrm) {
        this.efctDtFrm = efctDtFrm;
    }

    public Date getEfctDtTo() {
        return efctDtTo;
    }

    public void setEfctDtTo(Date efctDtTo) {
        this.efctDtTo = efctDtTo;
    }

    public String getAgrmntLtrNo() {
        return agrmntLtrNo;
    }

    public void setAgrmntLtrNo(String agrmntLtrNo) {
        this.agrmntLtrNo = agrmntLtrNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getInstDeptTag() {
        return instDeptTag;
    }

    public void setInstDeptTag(String instDeptTag) {
        this.instDeptTag = instDeptTag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (libCd != null ? libCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IllLibmst)) {
            return false;
        }
        IllLibmst other = (IllLibmst) object;
        if ((this.libCd == null && other.libCd != null) || (this.libCd != null && !this.libCd.equals(other.libCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.IllLibmst[ libCd=" + libCd + " ]";
    }

    @XmlTransient
    public Collection<IllRqstmst> getIllRqstmstCollection() {
        return illRqstmstCollection;
    }

    public void setIllRqstmstCollection(Collection<IllRqstmst> illRqstmstCollection) {
        this.illRqstmstCollection = illRqstmstCollection;
    }

    @XmlTransient
    public Collection<IllReqprocdmp> getIllReqprocdmpCollection() {
        return illReqprocdmpCollection;
    }

    public void setIllReqprocdmpCollection(Collection<IllReqprocdmp> illReqprocdmpCollection) {
        this.illReqprocdmpCollection = illReqprocdmpCollection;
    }

    @XmlTransient
    public Collection<IllExtissuedmp> getIllExtissuedmpCollection() {
        return illExtissuedmpCollection;
    }

    public void setIllExtissuedmpCollection(Collection<IllExtissuedmp> illExtissuedmpCollection) {
        this.illExtissuedmpCollection = illExtissuedmpCollection;
    }

    @XmlTransient
    public Collection<IllExtissue> getIllExtissueCollection() {
        return illExtissueCollection;
    }

    public void setIllExtissueCollection(Collection<IllExtissue> illExtissueCollection) {
        this.illExtissueCollection = illExtissueCollection;
    }
    
}
