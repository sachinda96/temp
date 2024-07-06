/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "t_memfine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TMemfine.findAll", query = "SELECT t FROM TMemfine t"),
    @NamedQuery(name = "TMemfine.findBySlipNo", query = "SELECT t FROM TMemfine t WHERE t.slipNo = :slipNo"),
    @NamedQuery(name = "TMemfine.findByFineDesc", query = "SELECT t FROM TMemfine t WHERE t.fineDesc = :fineDesc"),
    @NamedQuery(name = "TMemfine.findBySlipDt", query = "SELECT t FROM TMemfine t WHERE t.slipDt = :slipDt"),
    @NamedQuery(name = "TMemfine.findByMemCd", query = "SELECT t FROM TMemfine t WHERE t.memCd = :memCd"),
    @NamedQuery(name = "TMemfine.findByAccnNo", query = "SELECT t FROM TMemfine t WHERE t.accnNo = :accnNo"),
    @NamedQuery(name = "TMemfine.findByIssDt", query = "SELECT t FROM TMemfine t WHERE t.issDt = :issDt"),
    @NamedQuery(name = "TMemfine.findByRetDt", query = "SELECT t FROM TMemfine t WHERE t.retDt = :retDt"),
    @NamedQuery(name = "TMemfine.findByFineAmt", query = "SELECT t FROM TMemfine t WHERE t.fineAmt = :fineAmt"),
    
    @NamedQuery(name = "TMemfine.findByUserCd", query = "SELECT t FROM TMemfine t WHERE t.userCd = :userCd")
})
public class TMemfine implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "slip_no")
    private Integer slipNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 160)
    @Column(name = "fine_desc")
    private String fineDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "slip_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date slipDt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "mem_cd")
    private String memCd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "accn_no")
    private String accnNo;
    @Column(name = "iss_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issDt;
    @Column(name = "ret_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date retDt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "fine_amt")
    private BigDecimal fineAmt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "user_cd")
    private String userCd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "slipNo")
    private Collection<TMemrcpt> tMemrcptCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tMemfine")
    private TMemdue tMemdue;

    public TMemfine() {
    }

    public TMemfine(Integer slipNo) {
        this.slipNo = slipNo;
    }

    public TMemfine(Integer slipNo, String fineDesc, Date slipDt, String memCd, String accnNo, BigDecimal fineAmt, String userCd) {
        this.slipNo = slipNo;
        this.fineDesc = fineDesc;
        this.slipDt = slipDt;
        this.memCd = memCd;
        this.accnNo = accnNo;
        this.fineAmt = fineAmt;
        this.userCd = userCd;
    }

    public Integer getSlipNo() {
        return slipNo;
    }

    public void setSlipNo(Integer slipNo) {
        this.slipNo = slipNo;
    }

    public String getFineDesc() {
        return fineDesc;
    }

    public void setFineDesc(String fineDesc) {
        this.fineDesc = fineDesc;
    }

    public Date getSlipDt() {
        return slipDt;
    }

    public void setSlipDt(Date slipDt) {
        this.slipDt = slipDt;
    }

    public String getMemCd() {
        return memCd;
    }

    public void setMemCd(String memCd) {
        this.memCd = memCd;
    }

    public String getAccnNo() {
        return accnNo;
    }

    public void setAccnNo(String accnNo) {
        this.accnNo = accnNo;
    }

    public Date getIssDt() {
        return issDt;
    }

    public void setIssDt(Date issDt) {
        this.issDt = issDt;
    }

    public Date getRetDt() {
        return retDt;
    }

    public void setRetDt(Date retDt) {
        this.retDt = retDt;
    }

    public BigDecimal getFineAmt() {
        return fineAmt;
    }

    public void setFineAmt(BigDecimal fineAmt) {
        this.fineAmt = fineAmt;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    @XmlTransient
    public Collection<TMemrcpt> getTMemrcptCollection() {
        return tMemrcptCollection;
    }

    public void setTMemrcptCollection(Collection<TMemrcpt> tMemrcptCollection) {
        this.tMemrcptCollection = tMemrcptCollection;
    }

    @XmlTransient
    public TMemdue getTMemdue() {
        return tMemdue;
    }

    public void setTMemdue(TMemdue tMemdue) {
        this.tMemdue = tMemdue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (slipNo != null ? slipNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TMemfine)) {
            return false;
        }
        TMemfine other = (TMemfine) object;
        if ((this.slipNo == null && other.slipNo != null) || (this.slipNo != null && !this.slipNo.equals(other.slipNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TMemfine[ slipNo=" + slipNo + " ]";
    }
    
}
