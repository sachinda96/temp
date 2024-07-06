/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "s_payment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SPayment.findAll", query = "SELECT s FROM SPayment s"),
    @NamedQuery(name = "SPayment.retrieveAllP", query = "SELECT DISTINCT s FROM SPayment s"),
    @NamedQuery(name = "SPayment.findBySPayNo", query = "SELECT s FROM SPayment s WHERE s.sPayNo = :sPayNo"),
    @NamedQuery(name = "SPayment.findBySPayDt", query = "SELECT s FROM SPayment s WHERE s.sPayDt = :sPayDt"),
    @NamedQuery(name = "SPayment.findBySPayAmt", query = "SELECT s FROM SPayment s WHERE s.sPayAmt = :sPayAmt"),
    @NamedQuery(name = "SPayment.findBySPayBank", query = "SELECT s FROM SPayment s WHERE s.sPayBank = :sPayBank"),
    @NamedQuery(name = "SPayment.findBySPayBranch", query = "SELECT s FROM SPayment s WHERE s.sPayBranch = :sPayBranch"),
    @NamedQuery(name = "SPayment.findBySPayMode", query = "SELECT s FROM SPayment s WHERE s.sPayMode = :sPayMode"),
    @NamedQuery(name = "SPayment.findBySPayModeno", query = "SELECT s FROM SPayment s WHERE s.sPayModeno = :sPayModeno"),
    @NamedQuery(name = "SPayment.findBySPayForwardMode", query = "SELECT s FROM SPayment s WHERE s.sPayForwardMode = :sPayForwardMode"),
    @NamedQuery(name = "SPayment.findBySPayForwardDt", query = "SELECT s FROM SPayment s WHERE s.sPayForwardDt = :sPayForwardDt"),
    @NamedQuery(name = "SPayment.findBySPayRemark", query = "SELECT s FROM SPayment s WHERE s.sPayRemark = :sPayRemark"),
    @NamedQuery(name = "SPayment.findBySPayForwardNo", query = "SELECT s FROM SPayment s WHERE s.sPayForwardNo = :sPayForwardNo"),
    @NamedQuery(name = "SPayment.findBySPayChequeDt", query = "SELECT s FROM SPayment s WHERE s.sPayChequeDt = :sPayChequeDt"),
    @NamedQuery(name = "SPayment.findBySPayAckNo", query = "SELECT s FROM SPayment s WHERE s.sPayAckNo = :sPayAckNo"),
    @NamedQuery(name = "SPayment.findBySPayAckDt", query = "SELECT s FROM SPayment s WHERE s.sPayAckDt = :sPayAckDt"),
    @NamedQuery(name = "SPayment.findBySPayAckRemark", query = "SELECT s FROM SPayment s WHERE s.sPayAckRemark = :sPayAckRemark"),
    @NamedQuery(name = "SPayment.findBySPayBankCharge", query = "SELECT s FROM SPayment s WHERE s.sPayBankCharge = :sPayBankCharge")})
public class SPayment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "s_pay_no")
    private Integer sPayNo;
    @Column(name = "s_pay_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sPayDt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "s_pay_amt")
    private BigDecimal sPayAmt;
    @Size(max = 510)
    @Column(name = "s_pay_bank")
    private String sPayBank;
    @Size(max = 510)
    @Column(name = "s_pay_branch")
    private String sPayBranch;
    @Size(max = 510)
    @Column(name = "s_pay_mode")
    private String sPayMode;
    @Size(max = 510)
    @Column(name = "s_pay_modeno")
    private String sPayModeno;
    @Size(max = 510)
    @Column(name = "s_pay_forward_mode")
    private String sPayForwardMode;
    @Column(name = "s_pay_forward_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sPayForwardDt;
    @Size(max = 510)
    @Column(name = "s_pay_remark")
    private String sPayRemark;
    @Size(max = 510)
    @Column(name = "s_pay_forward_no")
    private String sPayForwardNo;
    @Column(name = "s_pay_cheque_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sPayChequeDt;
    @Size(max = 510)
    @Column(name = "s_pay_ack_no")
    private String sPayAckNo;
    @Column(name = "s_pay_ack_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sPayAckDt;
    @Size(max = 510)
    @Column(name = "s_pay_ack_remark")
    private String sPayAckRemark;
    @Column(name = "s_pay_bank_charge")
    private BigDecimal sPayBankCharge;
    @JoinColumn(name = "s_pay_invid", referencedColumnName = "s_inv_id")
    @ManyToOne
    private SSubInv sPayInvid;

    public SPayment() {
    }

    public SPayment(Integer sPayNo) {
        this.sPayNo = sPayNo;
    }

    public Integer getSPayNo() {
        return sPayNo;
    }

    public void setSPayNo(Integer sPayNo) {
        this.sPayNo = sPayNo;
    }

    public Date getSPayDt() {
        return sPayDt;
    }

    public void setSPayDt(Date sPayDt) {
        this.sPayDt = sPayDt;
    }

    public BigDecimal getSPayAmt() {
        return sPayAmt;
    }

    public void setSPayAmt(BigDecimal sPayAmt) {
        this.sPayAmt = sPayAmt;
    }

    public String getSPayBank() {
        return sPayBank;
    }

    public void setSPayBank(String sPayBank) {
        this.sPayBank = sPayBank;
    }

    public String getSPayBranch() {
        return sPayBranch;
    }

    public void setSPayBranch(String sPayBranch) {
        this.sPayBranch = sPayBranch;
    }

    public String getSPayMode() {
        return sPayMode;
    }

    public void setSPayMode(String sPayMode) {
        this.sPayMode = sPayMode;
    }

    public String getSPayModeno() {
        return sPayModeno;
    }

    public void setSPayModeno(String sPayModeno) {
        this.sPayModeno = sPayModeno;
    }

    public String getSPayForwardMode() {
        return sPayForwardMode;
    }

    public void setSPayForwardMode(String sPayForwardMode) {
        this.sPayForwardMode = sPayForwardMode;
    }

    public Date getSPayForwardDt() {
        return sPayForwardDt;
    }

    public void setSPayForwardDt(Date sPayForwardDt) {
        this.sPayForwardDt = sPayForwardDt;
    }

    public String getSPayRemark() {
        return sPayRemark;
    }

    public void setSPayRemark(String sPayRemark) {
        this.sPayRemark = sPayRemark;
    }

    public String getSPayForwardNo() {
        return sPayForwardNo;
    }

    public void setSPayForwardNo(String sPayForwardNo) {
        this.sPayForwardNo = sPayForwardNo;
    }

    public Date getSPayChequeDt() {
        return sPayChequeDt;
    }

    public void setSPayChequeDt(Date sPayChequeDt) {
        this.sPayChequeDt = sPayChequeDt;
    }

    public String getSPayAckNo() {
        return sPayAckNo;
    }

    public void setSPayAckNo(String sPayAckNo) {
        this.sPayAckNo = sPayAckNo;
    }

    public Date getSPayAckDt() {
        return sPayAckDt;
    }

    public void setSPayAckDt(Date sPayAckDt) {
        this.sPayAckDt = sPayAckDt;
    }

    public String getSPayAckRemark() {
        return sPayAckRemark;
    }

    public void setSPayAckRemark(String sPayAckRemark) {
        this.sPayAckRemark = sPayAckRemark;
    }

    public BigDecimal getSPayBankCharge() {
        return sPayBankCharge;
    }

    public void setSPayBankCharge(BigDecimal sPayBankCharge) {
        this.sPayBankCharge = sPayBankCharge;
    }

    public SSubInv getSPayInvid() {
        return sPayInvid;
    }

    public void setSPayInvid(SSubInv sPayInvid) {
        this.sPayInvid = sPayInvid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sPayNo != null ? sPayNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SPayment)) {
            return false;
        }
        SPayment other = (SPayment) object;
        if ((this.sPayNo == null && other.sPayNo != null) || (this.sPayNo != null && !this.sPayNo.equals(other.sPayNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serialControl.SPayment[ sPayNo=" + sPayNo + " ]";
    }
    
}
