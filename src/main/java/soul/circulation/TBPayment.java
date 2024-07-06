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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "t_b_payment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TBPayment.findAll", query = "SELECT t FROM TBPayment t"),
    @NamedQuery(name = "TBPayment.findByBPayNo", query = "SELECT t FROM TBPayment t WHERE t.bPayNo = :bPayNo"),
    @NamedQuery(name = "TBPayment.findByBPayDt", query = "SELECT t FROM TBPayment t WHERE t.bPayDt = :bPayDt"),
    @NamedQuery(name = "TBPayment.findByBPayInvno", query = "SELECT t FROM TBPayment t WHERE t.bPayInvno = :bPayInvno"),
    @NamedQuery(name = "TBPayment.findByBPayAmt", query = "SELECT t FROM TBPayment t WHERE t.bPayAmt = :bPayAmt"),
    @NamedQuery(name = "TBPayment.findByBPayBank", query = "SELECT t FROM TBPayment t WHERE t.bPayBank = :bPayBank"),
    @NamedQuery(name = "TBPayment.findByBPayBranch", query = "SELECT t FROM TBPayment t WHERE t.bPayBranch = :bPayBranch"),
    @NamedQuery(name = "TBPayment.findByBPayMode", query = "SELECT t FROM TBPayment t WHERE t.bPayMode = :bPayMode"),
    @NamedQuery(name = "TBPayment.findByBPayModeno", query = "SELECT t FROM TBPayment t WHERE t.bPayModeno = :bPayModeno"),
    @NamedQuery(name = "TBPayment.findByBPayForwardMode", query = "SELECT t FROM TBPayment t WHERE t.bPayForwardMode = :bPayForwardMode"),
    @NamedQuery(name = "TBPayment.findByBPayForwardDt", query = "SELECT t FROM TBPayment t WHERE t.bPayForwardDt = :bPayForwardDt"),
    @NamedQuery(name = "TBPayment.findByBPayRemark", query = "SELECT t FROM TBPayment t WHERE t.bPayRemark = :bPayRemark")})
public class TBPayment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "b_pay_no")
    private Integer bPayNo;
    @Column(name = "b_pay_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bPayDt;
    @Column(name = "b_pay_invno")
    private Integer bPayInvno;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "b_pay_amt")
    private BigDecimal bPayAmt;
    @Size(max = 35)
    @Column(name = "b_pay_bank")
    private String bPayBank;
    @Size(max = 35)
    @Column(name = "b_pay_branch")
    private String bPayBranch;
    @Size(max = 10)
    @Column(name = "b_pay_mode")
    private String bPayMode;
    @Size(max = 15)
    @Column(name = "b_pay_modeno")
    private String bPayModeno;
    @Size(max = 10)
    @Column(name = "b_pay_forward_mode")
    private String bPayForwardMode;
    @Column(name = "b_pay_forward_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bPayForwardDt;
    @Size(max = 255)
    @Column(name = "b_pay_remark")
    private String bPayRemark;

    public TBPayment() {
    }

    public TBPayment(Integer bPayNo) {
        this.bPayNo = bPayNo;
    }

    public Integer getBPayNo() {
        return bPayNo;
    }

    public void setBPayNo(Integer bPayNo) {
        this.bPayNo = bPayNo;
    }

    public Date getBPayDt() {
        return bPayDt;
    }

    public void setBPayDt(Date bPayDt) {
        this.bPayDt = bPayDt;
    }

    public Integer getBPayInvno() {
        return bPayInvno;
    }

    public void setBPayInvno(Integer bPayInvno) {
        this.bPayInvno = bPayInvno;
    }

    public BigDecimal getBPayAmt() {
        return bPayAmt;
    }

    public void setBPayAmt(BigDecimal bPayAmt) {
        this.bPayAmt = bPayAmt;
    }

    public String getBPayBank() {
        return bPayBank;
    }

    public void setBPayBank(String bPayBank) {
        this.bPayBank = bPayBank;
    }

    public String getBPayBranch() {
        return bPayBranch;
    }

    public void setBPayBranch(String bPayBranch) {
        this.bPayBranch = bPayBranch;
    }

    public String getBPayMode() {
        return bPayMode;
    }

    public void setBPayMode(String bPayMode) {
        this.bPayMode = bPayMode;
    }

    public String getBPayModeno() {
        return bPayModeno;
    }

    public void setBPayModeno(String bPayModeno) {
        this.bPayModeno = bPayModeno;
    }

    public String getBPayForwardMode() {
        return bPayForwardMode;
    }

    public void setBPayForwardMode(String bPayForwardMode) {
        this.bPayForwardMode = bPayForwardMode;
    }

    public Date getBPayForwardDt() {
        return bPayForwardDt;
    }

    public void setBPayForwardDt(Date bPayForwardDt) {
        this.bPayForwardDt = bPayForwardDt;
    }

    public String getBPayRemark() {
        return bPayRemark;
    }

    public void setBPayRemark(String bPayRemark) {
        this.bPayRemark = bPayRemark;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bPayNo != null ? bPayNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TBPayment)) {
            return false;
        }
        TBPayment other = (TBPayment) object;
        if ((this.bPayNo == null && other.bPayNo != null) || (this.bPayNo != null && !this.bPayNo.equals(other.bPayNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TBPayment[ bPayNo=" + bPayNo + " ]";
    }
    
}
