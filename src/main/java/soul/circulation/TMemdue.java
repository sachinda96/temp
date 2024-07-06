/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "t_memdue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TMemdue.findAll", query = "SELECT t FROM TMemdue t"),
    @NamedQuery(name = "TMemdue.findBySlipNo", query = "SELECT t FROM TMemdue t WHERE t.slipNo = :slipNo"),
    @NamedQuery(name = "TMemdue.findBySlipDt", query = "SELECT t FROM TMemdue t WHERE t.slipDt = :slipDt"),
    @NamedQuery(name = "TMemdue.findByLstRcptNo", query = "SELECT t FROM TMemdue t WHERE t.lstRcptNo = :lstRcptNo"),
    @NamedQuery(name = "TMemdue.findByLstRcptDt", query = "SELECT t FROM TMemdue t WHERE t.lstRcptDt = :lstRcptDt"),
    @NamedQuery(name = "TMemdue.findByDueAmt", query = "SELECT t FROM TMemdue t WHERE t.dueAmt = :dueAmt"),
    @NamedQuery(name = "TMemdue.findByDueAmount", query = "SELECT t FROM TMemdue t WHERE t.mMember.memCd = :memCd"),
    @NamedQuery(name = "TMemdue.findByMemCd", query = "SELECT t FROM TMemdue t WHERE t.mMember.memCd = :memCd"),
    @NamedQuery(name = "TMemdue.findDueSumByMemCd", query = "SELECT SUM(t.dueAmt) FROM TMemdue t WHERE t.mMember.memCd = :memCd"),
    @NamedQuery(name = "TMemdue.findBySlipDtBtwn", query = "SELECT t FROM TMemdue t WHERE t.slipDt BETWEEN ?1 AND ?2")
})
public class TMemdue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "slip_no")
    private Integer slipNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "slip_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date slipDt;
    @Size(max = 10)
    @Column(name = "lst_rcpt_no")
    private String lstRcptNo;
    @Column(name = "lst_rcpt_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lstRcptDt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "due_amt")
    private BigDecimal dueAmt;
    @JoinColumn(name = "mem_cd", referencedColumnName = "mem_cd")
    @ManyToOne(optional = false)
    private MMember mMember;
    @JoinColumn(name = "slip_no", referencedColumnName = "slip_no", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private TMemfine tMemfine;

    public TMemdue() {
    }

    public TMemdue(Integer slipNo) {
        this.slipNo = slipNo;
    }

    public TMemdue(Integer slipNo, Date slipDt, BigDecimal dueAmt) {
        this.slipNo = slipNo;
        this.slipDt = slipDt;
        this.dueAmt = dueAmt;
    }

    public Integer getSlipNo() {
        return slipNo;
    }

    public void setSlipNo(Integer slipNo) {
        this.slipNo = slipNo;
    }

    public Date getSlipDt() {
        return slipDt;
    }

    public void setSlipDt(Date slipDt) {
        this.slipDt = slipDt;
    }

    public String getLstRcptNo() {
        return lstRcptNo;
    }

    public void setLstRcptNo(String lstRcptNo) {
        this.lstRcptNo = lstRcptNo;
    }

    public Date getLstRcptDt() {
        return lstRcptDt;
    }

    public void setLstRcptDt(Date lstRcptDt) {
        this.lstRcptDt = lstRcptDt;
    }

    public BigDecimal getDueAmt() {
        return dueAmt;
    }

    public void setDueAmt(BigDecimal dueAmt) {
        this.dueAmt = dueAmt;
    }

    public MMember getMMember() {
        return mMember;
    }

    public void setMMember(MMember mMember) {
        this.mMember = mMember;
    }

    public TMemfine getTMemfine() {
        return tMemfine;
    }

    public void setTMemfine(TMemfine tMemfine) {
        this.tMemfine = tMemfine;
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
        if (!(object instanceof TMemdue)) {
            return false;
        }
        TMemdue other = (TMemdue) object;
        if ((this.slipNo == null && other.slipNo != null) || (this.slipNo != null && !this.slipNo.equals(other.slipNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TMemdue[ slipNo=" + slipNo + " ]";
    }
    
}
