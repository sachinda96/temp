/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Logger;
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

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "t_memrcpt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TMemrcpt.findAll", query = "SELECT t FROM TMemrcpt t"),
    @NamedQuery(name = "TMemrcpt.findByFineDesc", query = "SELECT t FROM TMemrcpt t WHERE t.fineDesc = :fineDesc"),
    @NamedQuery(name = "TMemrcpt.findBySlipDt", query = "SELECT t FROM TMemrcpt t WHERE t.slipDt = :slipDt"),
    @NamedQuery(name = "TMemrcpt.findByMemCd", query = "SELECT t FROM TMemrcpt t WHERE t.memCd = :memCd"),
    @NamedQuery(name = "TMemrcpt.findByRcptNo", query = "SELECT t FROM TMemrcpt t WHERE t.rcptNo = :rcptNo"),
    @NamedQuery(name = "TMemrcpt.findByRcptDt", query = "SELECT t FROM TMemrcpt t WHERE t.rcptDt = :rcptDt"),
    @NamedQuery(name = "TMemrcpt.findByRcptAmt", query = "SELECT t FROM TMemrcpt t WHERE t.rcptAmt = :rcptAmt"),
    @NamedQuery(name = "TMemrcpt.findByChqDrftNo", query = "SELECT t FROM TMemrcpt t WHERE t.chqDrftNo = :chqDrftNo"),
    @NamedQuery(name = "TMemrcpt.findByBankbr", query = "SELECT t FROM TMemrcpt t WHERE t.bankbr = :bankbr"),
    @NamedQuery(name = "TMemrcpt.findByUserCd", query = "SELECT t FROM TMemrcpt t WHERE t.userCd = :userCd"),
    
    @NamedQuery(name = "TMemrcpt.findByRcptDtBtwn", query = "SELECT t FROM TMemrcpt t WHERE t.rcptDt BETWEEN ?1 AND ?2")
})
public class TMemrcpt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rcpt_no")
    private Integer rcptNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rcpt_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rcptDt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "rcpt_amt")
    private BigDecimal rcptAmt;
    @Size(max = 30)
    @Column(name = "chq_drft_no")
    private String chqDrftNo;
    @Size(max = 100)
    @Column(name = "Bank_br")
    private String bankbr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "user_cd")
    private String userCd;
    @JoinColumn(name = "slip_no", referencedColumnName = "slip_no")
    @ManyToOne(optional = false)
    private TMemfine slipNo;

    public TMemrcpt() {
    }

    public TMemrcpt(Integer rcptNo) {
        this.rcptNo = rcptNo;
    }

    public TMemrcpt(Integer rcptNo, String fineDesc, Date slipDt, String memCd, Date rcptDt, BigDecimal rcptAmt, String userCd) {
        this.rcptNo = rcptNo;
        this.fineDesc = fineDesc;
        this.slipDt = slipDt;
        this.memCd = memCd;
        this.rcptDt = rcptDt;
        this.rcptAmt = rcptAmt;
        this.userCd = userCd;
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

    public Integer getRcptNo() {
        return rcptNo;
    }

    public void setRcptNo(Integer rcptNo) {
        this.rcptNo = rcptNo;
    }

    public Date getRcptDt() {
        return rcptDt;
    }

    public void setRcptDt(Date rcptDt) {
        this.rcptDt = rcptDt;
    }

    public BigDecimal getRcptAmt() {
        return rcptAmt;
    }

    public void setRcptAmt(BigDecimal rcptAmt) {
        this.rcptAmt = rcptAmt;
    }

    public String getChqDrftNo() {
        return chqDrftNo;
    }

    public void setChqDrftNo(String chqDrftNo) {
        this.chqDrftNo = chqDrftNo;
    }

    public String getBankbr() {
        return bankbr;
    }

    public void setBankbr(String bankbr) {
        this.bankbr = bankbr;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public TMemfine getSlipNo() {
        return slipNo;
    }

    public void setSlipNo(TMemfine slipNo) {
        this.slipNo = slipNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rcptNo != null ? rcptNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TMemrcpt)) {
            return false;
        }
        TMemrcpt other = (TMemrcpt) object;
        if ((this.rcptNo == null && other.rcptNo != null) || (this.rcptNo != null && !this.rcptNo.equals(other.rcptNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TMemrcpt[ rcptNo=" + rcptNo + " ]";
    }
    private static final Logger LOG = Logger.getLogger(TMemrcpt.class.getName());
    
    
    
}
