/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import soul.circulation.MMember;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "t_missing")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TMissing.findAll", query = "SELECT t FROM TMissing t"),
    @NamedQuery(name = "TMissing.findById", query = "SELECT t FROM TMissing t WHERE t.id = :id"),
    @NamedQuery(name = "TMissing.findByMissingDate", query = "SELECT t FROM TMissing t WHERE t.missingDate = :missingDate"),
    @NamedQuery(name = "TMissing.findByFoundDate", query = "SELECT t FROM TMissing t WHERE t.foundDate = :foundDate"),
    @NamedQuery(name = "TMissing.findByBkAccno", query = "SELECT t FROM TMissing t WHERE t.bkAccno = :bkAccno"),
    @NamedQuery(name = "TMissing.findByRemark", query = "SELECT t FROM TMissing t WHERE t.remark = :remark"),
    @NamedQuery(name = "TMissing.findByUserCd", query = "SELECT t FROM TMissing t WHERE t.userCd = :userCd"),
    @NamedQuery(name = "TMissing.findByMissDtBtwn", query = "SELECT t FROM TMissing t WHERE t.missingDate BETWEEN ?1 AND ?2"),
    @NamedQuery(name = "TMissing.findByIssueStat", query = "SELECT t FROM TMissing t WHERE t.issueStat = :issueStat")
})
public class TMissing implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "missing_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date missingDate;
    @Column(name = "found_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date foundDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "bk_accno")
    private String bkAccno;
    @Size(max = 100)
    @Column(name = "remark")
    private String remark;
    @Size(max = 20)
    @Column(name = "user_cd")
    private String userCd;
    @Size(max = 2)
    @Column(name = "issue_stat")
    private String issueStat;
    @JoinColumn(name = "mem_cd_miss_rep", referencedColumnName = "mem_cd")
    @ManyToOne
    private MMember memCdMissRep;
    @JoinColumn(name = "mem_cd_founder", referencedColumnName = "mem_cd")
    @ManyToOne
    private MMember memCdFounder;

    public TMissing() {
    }

    public TMissing(Integer id) {
        this.id = id;
    }

    public TMissing(Integer id, Date missingDate, String bkAccno) {
        this.id = id;
        this.missingDate = missingDate;
        this.bkAccno = bkAccno;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMissingDate() {
        return missingDate;
    }

    public void setMissingDate(Date missingDate) {
        this.missingDate = missingDate;
    }

    public Date getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public String getBkAccno() {
        return bkAccno;
    }

    public void setBkAccno(String bkAccno) {
        this.bkAccno = bkAccno;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public String getIssueStat() {
        return issueStat;
    }

    public void setIssueStat(String issueStat) {
        this.issueStat = issueStat;
    }

    public MMember getMemCdMissRep() {
        return memCdMissRep;
    }

    public void setMemCdMissRep(MMember memCdMissRep) {
        this.memCdMissRep = memCdMissRep;
    }

    public MMember getMemCdFounder() {
        return memCdFounder;
    }

    public void setMemCdFounder(MMember memCdFounder) {
        this.memCdFounder = memCdFounder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TMissing)) {
            return false;
        }
        TMissing other = (TMissing) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.TMissing[ id=" + id + " ]";
    }
    
}
