/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl;

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
@Table(name = "s_sub_inv")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SSubInv.findAll", query = "SELECT s FROM SSubInv s"),
    @NamedQuery(name = "SSubInv.findBySInvId", query = "SELECT s FROM SSubInv s WHERE s.sInvId = :sInvId"),
    @NamedQuery(name = "SSubInv.findBySInvNo", query = "SELECT s FROM SSubInv s WHERE s.sInvNo = :sInvNo"),
    @NamedQuery(name = "SSubInv.findBySInvDt", query = "SELECT s FROM SSubInv s WHERE s.sInvDt = :sInvDt"),
    @NamedQuery(name = "SSubInv.findBySInvParty", query = "SELECT s FROM SSubInv s WHERE s.sInvParty = :sInvParty"),
    @NamedQuery(name = "SSubInv.findBySInvRcptDt", query = "SELECT s FROM SSubInv s WHERE s.sInvRcptDt = :sInvRcptDt"),
    @NamedQuery(name = "SSubInv.findBySInvOrderno", query = "SELECT s FROM SSubInv s WHERE s.sInvOrderno = :sInvOrderno"),
    @NamedQuery(name = "SSubInv.findBySInvCurrency", query = "SELECT s FROM SSubInv s WHERE s.sInvCurrency = :sInvCurrency"),
    @NamedQuery(name = "SSubInv.findBySInvConvRate", query = "SELECT s FROM SSubInv s WHERE s.sInvConvRate = :sInvConvRate"),
    @NamedQuery(name = "SSubInv.findBySInvNet", query = "SELECT s FROM SSubInv s WHERE s.sInvNet = :sInvNet"),
    @NamedQuery(name = "SSubInv.findBySInvNetRs", query = "SELECT s FROM SSubInv s WHERE s.sInvNetRs = :sInvNetRs"),
    @NamedQuery(name = "SSubInv.findBySInvRemark", query = "SELECT s FROM SSubInv s WHERE s.sInvRemark = :sInvRemark"),
    @NamedQuery(name = "SSubInv.findBySInvForwardNo", query = "SELECT s FROM SSubInv s WHERE s.sInvForwardNo = :sInvForwardNo"),
    @NamedQuery(name = "SSubInv.findBySInvForwardDt", query = "SELECT s FROM SSubInv s WHERE s.sInvForwardDt = :sInvForwardDt"),
    @NamedQuery(name = "SSubInv.findBySInvForwardTo", query = "SELECT s FROM SSubInv s WHERE s.sInvForwardTo = :sInvForwardTo"),
    @NamedQuery(name = "SSubInv.findBySInvForwardRemark", query = "SELECT s FROM SSubInv s WHERE s.sInvForwardRemark = :sInvForwardRemark"),
    @NamedQuery(name = "SSubInv.findBySInvStatus", query = "SELECT s FROM SSubInv s WHERE s.sInvStatus = :sInvStatus"),
    @NamedQuery(name = "SSubInv.findBySInvDiscountPercent", query = "SELECT s FROM SSubInv s WHERE s.sInvDiscountPercent = :sInvDiscountPercent"),
    @NamedQuery(name = "SSubInv.findBySInvHandlingCharge", query = "SELECT s FROM SSubInv s WHERE s.sInvHandlingCharge = :sInvHandlingCharge"),
    @NamedQuery(name = "SSubInv.findBySInvPostageCharge", query = "SELECT s FROM SSubInv s WHERE s.sInvPostageCharge = :sInvPostageCharge"),
    @NamedQuery(name = "SSubInv.findBySInvNetAmt", query = "SELECT s FROM SSubInv s WHERE s.sInvNetAmt = :sInvNetAmt"),
    @NamedQuery(name = "SSubInv.findBySInvAmount", query = "SELECT s FROM SSubInv s WHERE s.sInvAmount = :sInvAmount"),
    @NamedQuery(name = "SSubInv.findBySInvRemain", query = "SELECT s FROM SSubInv s WHERE s.sInvRemain = :sInvRemain"),
    @NamedQuery(name = "SSubInv.findBySInvRefAmt", query = "SELECT s FROM SSubInv s WHERE s.sInvRefAmt = :sInvRefAmt"),
    @NamedQuery(name = "SSubInv.findBySInvRefNoteDt", query = "SELECT s FROM SSubInv s WHERE s.sInvRefNoteDt = :sInvRefNoteDt"),
    @NamedQuery(name = "SSubInv.findBySInvRefStatus", query = "SELECT s FROM SSubInv s WHERE s.sInvRefStatus = :sInvRefStatus"),
    @NamedQuery(name = "SSubInv.findBySInvRefBank", query = "SELECT s FROM SSubInv s WHERE s.sInvRefBank = :sInvRefBank"),
    @NamedQuery(name = "SSubInv.findBySInvRefBranch", query = "SELECT s FROM SSubInv s WHERE s.sInvRefBranch = :sInvRefBranch"),
    @NamedQuery(name = "SSubInv.findBySInvRefChequeno", query = "SELECT s FROM SSubInv s WHERE s.sInvRefChequeno = :sInvRefChequeno"),
    
    @NamedQuery(name = "SSubInv.findBySInvStatusANDPositiveRemain", query = "SELECT s FROM SSubInv s WHERE s.sInvStatus = 'I' AND s.sInvRemain > 0 AND s.sInvAmount < s.sInvNetAmt"),
    @NamedQuery(name = "SSubInv.getRefundReport", query = "SELECT s FROM SSubInv s WHERE s.sInvRefStatus = 'Y' AND (s.sInvRefNoteDt >= ?1 and s.sInvRefNoteDt <= ?2)"),
    @NamedQuery(name = "SSubInv.findBySupplierPublisher", query = "SELECT s FROM SSubInv s WHERE s.sInvStatus = 'I' AND s.sInvRemain > 0 AND s.sInvAmount < s.sInvNetAmt AND s.sInvParty = :sInvParty"),
    
})
public class SSubInv implements Serializable {
    @OneToMany(mappedBy = "sPayInvid")
    private Collection<SPayment> sPaymentCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "s_inv_id")
    private Integer sInvId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_inv_no")
    private String sInvNo;
    @Column(name = "s_inv_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sInvDt;
    @Size(max = 510)
    @Column(name = "s_inv_party")
    private String sInvParty;
    @Column(name = "s_inv_rcpt_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sInvRcptDt;
    @Size(max = 100)
    @Column(name = "s_inv_orderno")
    private String sInvOrderno;
    @Size(max = 510)
    @Column(name = "s_inv_currency")
    private String sInvCurrency;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "s_inv_conv_rate")
    private BigDecimal sInvConvRate;
    @Column(name = "s_inv_net")
    private BigDecimal sInvNet;
    @Column(name = "s_inv_net_rs")
    private BigDecimal sInvNetRs;
    @Size(max = 510)
    @Column(name = "s_inv_remark")
    private String sInvRemark;
    @Size(max = 100)
    @Column(name = "s_inv_forward_no")
    private String sInvForwardNo;
    @Column(name = "s_inv_forward_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sInvForwardDt;
    @Size(max = 510)
    @Column(name = "s_inv_forward_to")
    private String sInvForwardTo;
    @Size(max = 510)
    @Column(name = "s_inv_forward_remark")
    private String sInvForwardRemark;
    @Size(max = 100)
    @Column(name = "s_inv_status")
    private String sInvStatus;
    @Column(name = "s_inv_discount_percent")
    private BigDecimal sInvDiscountPercent;
    @Column(name = "s_inv_handling_charge")
    private BigDecimal sInvHandlingCharge;
    @Column(name = "s_inv_postage_charge")
    private BigDecimal sInvPostageCharge;
    @Column(name = "s_inv_net_amt")
    private BigDecimal sInvNetAmt;
    @Column(name = "s_inv_amount")
    private BigDecimal sInvAmount;
    @Column(name = "s_inv_remain")
    private BigDecimal sInvRemain;
    @Column(name = "s_inv_ref_amt")
    private BigDecimal sInvRefAmt;
    @Column(name = "s_inv_ref_note_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sInvRefNoteDt;
    @Size(max = 100)
    @Column(name = "s_inv_ref_status")
    private String sInvRefStatus;
    @Size(max = 500)
    @Column(name = "s_inv_ref_bank")
    private String sInvRefBank;
    @Size(max = 500)
    @Column(name = "s_inv_ref_branch")
    private String sInvRefBranch;
    @Size(max = 200)
    @Column(name = "s_inv_ref_chequeno")
    private String sInvRefChequeno;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sSubInv")
    private Collection<SSubInvdetail> sSubInvdetailCollection;

    public SSubInv() {
    }

    public SSubInv(Integer sInvId) {
        this.sInvId = sInvId;
    }

    public SSubInv(Integer sInvId, String sInvNo) {
        this.sInvId = sInvId;
        this.sInvNo = sInvNo;
    }

    public Integer getSInvId() {
        return sInvId;
    }

    public void setSInvId(Integer sInvId) {
        this.sInvId = sInvId;
    }

    public String getSInvNo() {
        return sInvNo;
    }

    public void setSInvNo(String sInvNo) {
        this.sInvNo = sInvNo;
    }

    public Date getSInvDt() {
        return sInvDt;
    }

    public void setSInvDt(Date sInvDt) {
        this.sInvDt = sInvDt;
    }

    public String getSInvParty() {
        return sInvParty;
    }

    public void setSInvParty(String sInvParty) {
        this.sInvParty = sInvParty;
    }

    public Date getSInvRcptDt() {
        return sInvRcptDt;
    }

    public void setSInvRcptDt(Date sInvRcptDt) {
        this.sInvRcptDt = sInvRcptDt;
    }

    public String getSInvOrderno() {
        return sInvOrderno;
    }

    public void setSInvOrderno(String sInvOrderno) {
        this.sInvOrderno = sInvOrderno;
    }

    public String getSInvCurrency() {
        return sInvCurrency;
    }

    public void setSInvCurrency(String sInvCurrency) {
        this.sInvCurrency = sInvCurrency;
    }

    public BigDecimal getSInvConvRate() {
        return sInvConvRate;
    }

    public void setSInvConvRate(BigDecimal sInvConvRate) {
        this.sInvConvRate = sInvConvRate;
    }

    public BigDecimal getSInvNet() {
        return sInvNet;
    }

    public void setSInvNet(BigDecimal sInvNet) {
        this.sInvNet = sInvNet;
    }

    public BigDecimal getSInvNetRs() {
        return sInvNetRs;
    }

    public void setSInvNetRs(BigDecimal sInvNetRs) {
        this.sInvNetRs = sInvNetRs;
    }

    public String getSInvRemark() {
        return sInvRemark;
    }

    public void setSInvRemark(String sInvRemark) {
        this.sInvRemark = sInvRemark;
    }

    public String getSInvForwardNo() {
        return sInvForwardNo;
    }

    public void setSInvForwardNo(String sInvForwardNo) {
        this.sInvForwardNo = sInvForwardNo;
    }

    public Date getSInvForwardDt() {
        return sInvForwardDt;
    }

    public void setSInvForwardDt(Date sInvForwardDt) {
        this.sInvForwardDt = sInvForwardDt;
    }

    public String getSInvForwardTo() {
        return sInvForwardTo;
    }

    public void setSInvForwardTo(String sInvForwardTo) {
        this.sInvForwardTo = sInvForwardTo;
    }

    public String getSInvForwardRemark() {
        return sInvForwardRemark;
    }

    public void setSInvForwardRemark(String sInvForwardRemark) {
        this.sInvForwardRemark = sInvForwardRemark;
    }

    public String getSInvStatus() {
        return sInvStatus;
    }

    public void setSInvStatus(String sInvStatus) {
        this.sInvStatus = sInvStatus;
    }

    public BigDecimal getSInvDiscountPercent() {
        return sInvDiscountPercent;
    }

    public void setSInvDiscountPercent(BigDecimal sInvDiscountPercent) {
        this.sInvDiscountPercent = sInvDiscountPercent;
    }

    public BigDecimal getSInvHandlingCharge() {
        return sInvHandlingCharge;
    }

    public void setSInvHandlingCharge(BigDecimal sInvHandlingCharge) {
        this.sInvHandlingCharge = sInvHandlingCharge;
    }

    public BigDecimal getSInvPostageCharge() {
        return sInvPostageCharge;
    }

    public void setSInvPostageCharge(BigDecimal sInvPostageCharge) {
        this.sInvPostageCharge = sInvPostageCharge;
    }

    public BigDecimal getSInvNetAmt() {
        return sInvNetAmt;
    }

    public void setSInvNetAmt(BigDecimal sInvNetAmt) {
        this.sInvNetAmt = sInvNetAmt;
    }

    public BigDecimal getSInvAmount() {
        return sInvAmount;
    }

    public void setSInvAmount(BigDecimal sInvAmount) {
        this.sInvAmount = sInvAmount;
    }

    public BigDecimal getSInvRemain() {
        return sInvRemain;
    }

    public void setSInvRemain(BigDecimal sInvRemain) {
        this.sInvRemain = sInvRemain;
    }

    public BigDecimal getSInvRefAmt() {
        return sInvRefAmt;
    }

    public void setSInvRefAmt(BigDecimal sInvRefAmt) {
        this.sInvRefAmt = sInvRefAmt;
    }

    public Date getSInvRefNoteDt() {
        return sInvRefNoteDt;
    }

    public void setSInvRefNoteDt(Date sInvRefNoteDt) {
        this.sInvRefNoteDt = sInvRefNoteDt;
    }

    public String getSInvRefStatus() {
        return sInvRefStatus;
    }

    public void setSInvRefStatus(String sInvRefStatus) {
        this.sInvRefStatus = sInvRefStatus;
    }

    public String getSInvRefBank() {
        return sInvRefBank;
    }

    public void setSInvRefBank(String sInvRefBank) {
        this.sInvRefBank = sInvRefBank;
    }

    public String getSInvRefBranch() {
        return sInvRefBranch;
    }

    public void setSInvRefBranch(String sInvRefBranch) {
        this.sInvRefBranch = sInvRefBranch;
    }

    public String getSInvRefChequeno() {
        return sInvRefChequeno;
    }

    public void setSInvRefChequeno(String sInvRefChequeno) {
        this.sInvRefChequeno = sInvRefChequeno;
    }

    @XmlTransient
    public Collection<SSubInvdetail> getSSubInvdetailCollection() {
        return sSubInvdetailCollection;
    }

    public void setSSubInvdetailCollection(Collection<SSubInvdetail> sSubInvdetailCollection) {
        this.sSubInvdetailCollection = sSubInvdetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sInvId != null ? sInvId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SSubInv)) {
            return false;
        }
        SSubInv other = (SSubInv) object;
        if ((this.sInvId == null && other.sInvId != null) || (this.sInvId != null && !this.sInvId.equals(other.sInvId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serialControl.SSubInv[ sInvId=" + sInvId + " ]";
    }

    @XmlTransient
    public Collection<SPayment> getSPaymentCollection() {
        return sPaymentCollection;
    }

    public void setSPaymentCollection(Collection<SPayment> sPaymentCollection) {
        this.sPaymentCollection = sPaymentCollection;
    }
    
}
