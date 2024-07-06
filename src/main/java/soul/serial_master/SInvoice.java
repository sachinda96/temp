/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author admin
 */
@Entity
@Table(name = "s_invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SInvoice.findAll", query = "SELECT s FROM SInvoice s")
    , @NamedQuery(name = "SInvoice.findBySInvNo", query = "SELECT s FROM SInvoice s WHERE s.sInvNo = :sInvNo")
    , @NamedQuery(name = "SInvoice.findBySInvId", query = "SELECT s FROM SInvoice s WHERE s.sInvId = :sInvId")
    , @NamedQuery(name = "SInvoice.findBySInvDt", query = "SELECT s FROM SInvoice s WHERE s.sInvDt = :sInvDt")
    , @NamedQuery(name = "SInvoice.findBySInvParty", query = "SELECT s FROM SInvoice s WHERE s.sInvParty = :sInvParty")
    , @NamedQuery(name = "SInvoice.findBySInvRcptdt", query = "SELECT s FROM SInvoice s WHERE s.sInvRcptdt = :sInvRcptdt")
    , @NamedQuery(name = "SInvoice.findBySInvOrderno", query = "SELECT s FROM SInvoice s WHERE s.sInvOrderno = :sInvOrderno")
    , @NamedQuery(name = "SInvoice.findBySInvOrderset", query = "SELECT s FROM SInvoice s WHERE s.sInvOrderset = :sInvOrderset")
    , @NamedQuery(name = "SInvoice.findBySInvRcptset", query = "SELECT s FROM SInvoice s WHERE s.sInvRcptset = :sInvRcptset")
    , @NamedQuery(name = "SInvoice.findBySInvPrice", query = "SELECT s FROM SInvoice s WHERE s.sInvPrice = :sInvPrice")
    , @NamedQuery(name = "SInvoice.findBySInvDiscount", query = "SELECT s FROM SInvoice s WHERE s.sInvDiscount = :sInvDiscount")
    , @NamedQuery(name = "SInvoice.findBySInvOverdue", query = "SELECT s FROM SInvoice s WHERE s.sInvOverdue = :sInvOverdue")
    , @NamedQuery(name = "SInvoice.findBySInvMisamt", query = "SELECT s FROM SInvoice s WHERE s.sInvMisamt = :sInvMisamt")
    , @NamedQuery(name = "SInvoice.findBySInvNetamt", query = "SELECT s FROM SInvoice s WHERE s.sInvNetamt = :sInvNetamt")
    , @NamedQuery(name = "SInvoice.getAllInvoicesForPayment", query = "SELECT s FROM SInvoice s WHERE s.sInvType = 'I' ") ,
    @NamedQuery(name = "SInvoice.getAllI", query = "SELECT s FROM SInvoice s WHERE s.sInvOrderno IS NOT NULL ") 
    , @NamedQuery(name = "SInvoice.findBySInvForwardDt", query = "SELECT s FROM SInvoice s WHERE s.sInvForwardDt = :sInvForwardDt")
    , @NamedQuery(name = "SInvoice.findBySInvForwardNo", query = "SELECT s FROM SInvoice s WHERE s.sInvForwardNo = :sInvForwardNo")
    , @NamedQuery(name = "SInvoice.findBySInvForwardTo", query = "SELECT s FROM SInvoice s WHERE s.sInvForwardTo = :sInvForwardTo")
    , @NamedQuery(name = "SInvoice.findBySInvType", query = "SELECT s FROM SInvoice s WHERE s.sInvType = :sInvType")})
public class SInvoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 100)
    @Column(name = "s_inv_no")
    private String sInvNo;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "s_inv_id")
    private Integer sInvId;
    @Column(name = "s_inv_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sInvDt;
    @Size(max = 510)
    @Column(name = "s_inv_party")
    private String sInvParty;
    @Column(name = "s_inv_rcptdt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sInvRcptdt;
    @Size(max = 100)
    @Column(name = "s_inv_orderno")
    private String sInvOrderno;
    @Size(max = 16)
    @Column(name = "s_inv_orderset")
    private String sInvOrderset;
    @Size(max = 16)
    @Column(name = "s_inv_rcptset")
    private String sInvRcptset;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "s_inv_price")
    private BigDecimal sInvPrice;
    @Column(name = "s_inv_discount")
    private BigDecimal sInvDiscount;
    @Column(name = "s_inv_overdue")
    private BigDecimal sInvOverdue;
    @Column(name = "s_inv_misamt")
    private BigDecimal sInvMisamt;
    @Column(name = "s_inv_netamt")
    private BigDecimal sInvNetamt;
    @Column(name = "s_inv_forward_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sInvForwardDt;
    @Size(max = 100)
    @Column(name = "s_inv_forward_no")
    private String sInvForwardNo;
    @Size(max = 510)
    @Column(name = "s_inv_forward_to")
    private String sInvForwardTo;
    @Size(max = 510)
    @Column(name = "s_inv_type")
    private String sInvType;

    public SInvoice() {
    }

    public SInvoice(Integer sInvId) {
        this.sInvId = sInvId;
    }

    public String getSInvNo() {
        return sInvNo;
    }

    public void setSInvNo(String sInvNo) {
        this.sInvNo = sInvNo;
    }

    public Integer getSInvId() {
        return sInvId;
    }

    public void setSInvId(Integer sInvId) {
        this.sInvId = sInvId;
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

    public Date getSInvRcptdt() {
        return sInvRcptdt;
    }

    public void setSInvRcptdt(Date sInvRcptdt) {
        this.sInvRcptdt = sInvRcptdt;
    }

    public String getSInvOrderno() {
        return sInvOrderno;
    }

    public void setSInvOrderno(String sInvOrderno) {
        this.sInvOrderno = sInvOrderno;
    }

    public String getSInvOrderset() {
        return sInvOrderset;
    }

    public void setSInvOrderset(String sInvOrderset) {
        this.sInvOrderset = sInvOrderset;
    }

    public String getSInvRcptset() {
        return sInvRcptset;
    }

    public void setSInvRcptset(String sInvRcptset) {
        this.sInvRcptset = sInvRcptset;
    }

    public BigDecimal getSInvPrice() {
        return sInvPrice;
    }

    public void setSInvPrice(BigDecimal sInvPrice) {
        this.sInvPrice = sInvPrice;
    }

    public BigDecimal getSInvDiscount() {
        return sInvDiscount;
    }

    public void setSInvDiscount(BigDecimal sInvDiscount) {
        this.sInvDiscount = sInvDiscount;
    }

    public BigDecimal getSInvOverdue() {
        return sInvOverdue;
    }

    public void setSInvOverdue(BigDecimal sInvOverdue) {
        this.sInvOverdue = sInvOverdue;
    }

    public BigDecimal getSInvMisamt() {
        return sInvMisamt;
    }

    public void setSInvMisamt(BigDecimal sInvMisamt) {
        this.sInvMisamt = sInvMisamt;
    }

    public BigDecimal getSInvNetamt() {
        return sInvNetamt;
    }

    public void setSInvNetamt(BigDecimal sInvNetamt) {
        this.sInvNetamt = sInvNetamt;
    }

    public Date getSInvForwardDt() {
        return sInvForwardDt;
    }

    public void setSInvForwardDt(Date sInvForwardDt) {
        this.sInvForwardDt = sInvForwardDt;
    }

    public String getSInvForwardNo() {
        return sInvForwardNo;
    }

    public void setSInvForwardNo(String sInvForwardNo) {
        this.sInvForwardNo = sInvForwardNo;
    }

    public String getSInvForwardTo() {
        return sInvForwardTo;
    }

    public void setSInvForwardTo(String sInvForwardTo) {
        this.sInvForwardTo = sInvForwardTo;
    }

    public String getSInvType() {
        return sInvType;
    }

    public void setSInvType(String sInvType) {
        this.sInvType = sInvType;
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
        if (!(object instanceof SInvoice)) {
            return false;
        }
        SInvoice other = (SInvoice) object;
        if ((this.sInvId == null && other.sInvId != null) || (this.sInvId != null && !this.sInvId.equals(other.sInvId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serial_master.SInvoice[ sInvId=" + sInvId + " ]";
    }
    
}
