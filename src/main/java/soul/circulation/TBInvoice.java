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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "t_b_invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TBInvoice.findAll", query = "SELECT t FROM TBInvoice t"),
    @NamedQuery(name = "TBInvoice.findByBInvNo", query = "SELECT t FROM TBInvoice t WHERE t.bInvNo = :bInvNo"),
    @NamedQuery(name = "TBInvoice.findByBInvDt", query = "SELECT t FROM TBInvoice t WHERE t.bInvDt = :bInvDt"),
    @NamedQuery(name = "TBInvoice.findByBInvParty", query = "SELECT t FROM TBInvoice t WHERE t.bInvParty = :bInvParty"),
    @NamedQuery(name = "TBInvoice.findByBInvRcptdt", query = "SELECT t FROM TBInvoice t WHERE t.bInvRcptdt = :bInvRcptdt"),
    @NamedQuery(name = "TBInvoice.findByBInvOrderno", query = "SELECT t FROM TBInvoice t WHERE t.bInvOrderno = :bInvOrderno"),
    @NamedQuery(name = "TBInvoice.findByBInvOrderset", query = "SELECT t FROM TBInvoice t WHERE t.bInvOrderset = :bInvOrderset"),
    @NamedQuery(name = "TBInvoice.findByBInvRcptset", query = "SELECT t FROM TBInvoice t WHERE t.bInvRcptset = :bInvRcptset"),
    @NamedQuery(name = "TBInvoice.findByBInvPrice", query = "SELECT t FROM TBInvoice t WHERE t.bInvPrice = :bInvPrice"),
    @NamedQuery(name = "TBInvoice.findByBInvDiscount", query = "SELECT t FROM TBInvoice t WHERE t.bInvDiscount = :bInvDiscount"),
    @NamedQuery(name = "TBInvoice.findByBInvOverdue", query = "SELECT t FROM TBInvoice t WHERE t.bInvOverdue = :bInvOverdue"),
    @NamedQuery(name = "TBInvoice.findByBInvMisamt", query = "SELECT t FROM TBInvoice t WHERE t.bInvMisamt = :bInvMisamt"),
    @NamedQuery(name = "TBInvoice.findByBInvNetamt", query = "SELECT t FROM TBInvoice t WHERE t.bInvNetamt = :bInvNetamt"),
    @NamedQuery(name = "TBInvoice.findByBInvForwardDt", query = "SELECT t FROM TBInvoice t WHERE t.bInvForwardDt = :bInvForwardDt"),
    @NamedQuery(name = "TBInvoice.findByBInvFlag", query = "SELECT t FROM TBInvoice t WHERE t.bInvFlag = :bInvFlag")})
public class TBInvoice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "b_inv_no")
    private Integer bInvNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "b_inv_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bInvDt;
    @Size(max = 5)
    @Column(name = "b_inv_party")
    private String bInvParty;
    @Column(name = "b_inv_rcptdt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bInvRcptdt;
    @Size(max = 15)
    @Column(name = "b_inv_orderno")
    private String bInvOrderno;
    @Column(name = "b_inv_orderset")
    private Integer bInvOrderset;
    @Column(name = "b_inv_rcptset")
    private Integer bInvRcptset;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "b_inv_price")
    private BigDecimal bInvPrice;
    @Column(name = "b_inv_discount")
    private BigDecimal bInvDiscount;
    @Column(name = "b_inv_overdue")
    private BigDecimal bInvOverdue;
    @Column(name = "b_inv_misamt")
    private BigDecimal bInvMisamt;
    @Column(name = "b_inv_netamt")
    private BigDecimal bInvNetamt;
    @Column(name = "b_inv_forward_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bInvForwardDt;
    @Column(name = "b_inv_flag")
    private Integer bInvFlag;

    public TBInvoice() {
    }

    public TBInvoice(Integer bInvNo) {
        this.bInvNo = bInvNo;
    }

    public TBInvoice(Integer bInvNo, Date bInvDt) {
        this.bInvNo = bInvNo;
        this.bInvDt = bInvDt;
    }

    public Integer getBInvNo() {
        return bInvNo;
    }

    public void setBInvNo(Integer bInvNo) {
        this.bInvNo = bInvNo;
    }

    public Date getBInvDt() {
        return bInvDt;
    }

    public void setBInvDt(Date bInvDt) {
        this.bInvDt = bInvDt;
    }

    public String getBInvParty() {
        return bInvParty;
    }

    public void setBInvParty(String bInvParty) {
        this.bInvParty = bInvParty;
    }

    public Date getBInvRcptdt() {
        return bInvRcptdt;
    }

    public void setBInvRcptdt(Date bInvRcptdt) {
        this.bInvRcptdt = bInvRcptdt;
    }

    public String getBInvOrderno() {
        return bInvOrderno;
    }

    public void setBInvOrderno(String bInvOrderno) {
        this.bInvOrderno = bInvOrderno;
    }

    public Integer getBInvOrderset() {
        return bInvOrderset;
    }

    public void setBInvOrderset(Integer bInvOrderset) {
        this.bInvOrderset = bInvOrderset;
    }

    public Integer getBInvRcptset() {
        return bInvRcptset;
    }

    public void setBInvRcptset(Integer bInvRcptset) {
        this.bInvRcptset = bInvRcptset;
    }

    public BigDecimal getBInvPrice() {
        return bInvPrice;
    }

    public void setBInvPrice(BigDecimal bInvPrice) {
        this.bInvPrice = bInvPrice;
    }

    public BigDecimal getBInvDiscount() {
        return bInvDiscount;
    }

    public void setBInvDiscount(BigDecimal bInvDiscount) {
        this.bInvDiscount = bInvDiscount;
    }

    public BigDecimal getBInvOverdue() {
        return bInvOverdue;
    }

    public void setBInvOverdue(BigDecimal bInvOverdue) {
        this.bInvOverdue = bInvOverdue;
    }

    public BigDecimal getBInvMisamt() {
        return bInvMisamt;
    }

    public void setBInvMisamt(BigDecimal bInvMisamt) {
        this.bInvMisamt = bInvMisamt;
    }

    public BigDecimal getBInvNetamt() {
        return bInvNetamt;
    }

    public void setBInvNetamt(BigDecimal bInvNetamt) {
        this.bInvNetamt = bInvNetamt;
    }

    public Date getBInvForwardDt() {
        return bInvForwardDt;
    }

    public void setBInvForwardDt(Date bInvForwardDt) {
        this.bInvForwardDt = bInvForwardDt;
    }

    public Integer getBInvFlag() {
        return bInvFlag;
    }

    public void setBInvFlag(Integer bInvFlag) {
        this.bInvFlag = bInvFlag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bInvNo != null ? bInvNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TBInvoice)) {
            return false;
        }
        TBInvoice other = (TBInvoice) object;
        if ((this.bInvNo == null && other.bInvNo != null) || (this.bInvNo != null && !this.bInvNo.equals(other.bInvNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TBInvoice[ bInvNo=" + bInvNo + " ]";
    }
    
}
