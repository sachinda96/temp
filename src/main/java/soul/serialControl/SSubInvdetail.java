/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "s_sub_invdetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SSubInvdetail.findAll", query = "SELECT s FROM SSubInvdetail s"),
    @NamedQuery(name = "SSubInvdetail.findBySInvNo", query = "SELECT s FROM SSubInvdetail s WHERE s.sSubInvdetailPK.sInvNo = :sInvNo"),
    @NamedQuery(name = "SSubInvdetail.findBySInvRecid", query = "SELECT s FROM SSubInvdetail s WHERE s.sSubInvdetailPK.sInvRecid = :sInvRecid"),
    @NamedQuery(name = "SSubInvdetail.findBySInvOrderNo", query = "SELECT s FROM SSubInvdetail s WHERE s.sSubInvdetailPK.sInvOrderNo = :sInvOrderNo"),
    @NamedQuery(name = "SSubInvdetail.findBySInvStDt", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvStDt = :sInvStDt"),
    @NamedQuery(name = "SSubInvdetail.findBySInvStVol", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvStVol = :sInvStVol"),
    @NamedQuery(name = "SSubInvdetail.findBySInvStIss", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvStIss = :sInvStIss"),
    @NamedQuery(name = "SSubInvdetail.findBySInvEnDt", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvEnDt = :sInvEnDt"),
    @NamedQuery(name = "SSubInvdetail.findBySInvEnVol", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvEnVol = :sInvEnVol"),
    @NamedQuery(name = "SSubInvdetail.findBySInvEnIss", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvEnIss = :sInvEnIss"),
    @NamedQuery(name = "SSubInvdetail.findBySInvCurrency", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvCurrency = :sInvCurrency"),
    @NamedQuery(name = "SSubInvdetail.findBySInvConvRate", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvConvRate = :sInvConvRate"),
    @NamedQuery(name = "SSubInvdetail.findBySInvPrice", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvPrice = :sInvPrice"),
    @NamedQuery(name = "SSubInvdetail.findBySInvPriceRs", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvPriceRs = :sInvPriceRs"),
    @NamedQuery(name = "SSubInvdetail.findBySInvDiscount", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvDiscount = :sInvDiscount"),
    @NamedQuery(name = "SSubInvdetail.findBySInvDiscounrRs", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvDiscounrRs = :sInvDiscounrRs"),
    @NamedQuery(name = "SSubInvdetail.findBySInvPostage", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvPostage = :sInvPostage"),
    @NamedQuery(name = "SSubInvdetail.findBySInvPostageRs", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvPostageRs = :sInvPostageRs"),
    @NamedQuery(name = "SSubInvdetail.findBySInvHandling", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvHandling = :sInvHandling"),
    @NamedQuery(name = "SSubInvdetail.findBySInvHandlingRs", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvHandlingRs = :sInvHandlingRs"),
    @NamedQuery(name = "SSubInvdetail.findBySInvNet", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvNet = :sInvNet"),
    @NamedQuery(name = "SSubInvdetail.findBySInvNetRs", query = "SELECT s FROM SSubInvdetail s WHERE s.sInvNetRs = :sInvNetRs"),
    @NamedQuery(name = "SSubInvdetail.getOrderDetailsforRefund", query = "SELECT s FROM SSubInvdetail s WHERE s.sSubInvdetailPK.sInvNo = s.sSubInv.sInvNo AND s.sSubInv.sInvRefStatus is NULL AND s.sSubInvdetailPK.sInvOrderNo = :sInvOrderNo"),
})
public class SSubInvdetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SSubInvdetailPK sSubInvdetailPK;
    @Column(name = "s_inv_st_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sInvStDt;
    @Size(max = 100)
    @Column(name = "s_inv_st_vol")
    private String sInvStVol;
    @Size(max = 100)
    @Column(name = "s_inv_st_iss")
    private String sInvStIss;
    @Column(name = "s_inv_en_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sInvEnDt;
    @Size(max = 100)
    @Column(name = "s_inv_en_vol")
    private String sInvEnVol;
    @Size(max = 100)
    @Column(name = "s_inv_en_iss")
    private String sInvEnIss;
    @Size(max = 510)
    @Column(name = "s_inv_currency")
    private String sInvCurrency;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "s_inv_conv_rate")
    private BigDecimal sInvConvRate;
    @Column(name = "s_inv_price")
    private BigDecimal sInvPrice;
    @Column(name = "s_inv_price_rs")
    private BigDecimal sInvPriceRs;
    @Column(name = "s_inv_discount")
    private BigDecimal sInvDiscount;
    @Column(name = "s_inv_discounr_rs")
    private BigDecimal sInvDiscounrRs;
    @Column(name = "s_inv_postage")
    private BigDecimal sInvPostage;
    @Column(name = "s_inv_postage_rs")
    private BigDecimal sInvPostageRs;
    @Column(name = "s_inv_handling")
    private BigDecimal sInvHandling;
    @Column(name = "s_inv_handling_rs")
    private BigDecimal sInvHandlingRs;
    @Column(name = "s_inv_net")
    private BigDecimal sInvNet;
    @Column(name = "s_inv_net_rs")
    private BigDecimal sInvNetRs;
    @JoinColumn(name = "s_inv_recid", referencedColumnName = "s_inv_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SSubInv sSubInv;
    @JoinColumn(name = "s_inv_order_no", referencedColumnName = "s_order_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SSubscription sSubscription;

    public SSubInvdetail() {
    }

    public SSubInvdetail(SSubInvdetailPK sSubInvdetailPK) {
        this.sSubInvdetailPK = sSubInvdetailPK;
    }

    public SSubInvdetail(String sInvNo, int sInvRecid, String sInvOrderNo) {
        this.sSubInvdetailPK = new SSubInvdetailPK(sInvNo, sInvRecid, sInvOrderNo);
    }

    public SSubInvdetailPK getSSubInvdetailPK() {
        return sSubInvdetailPK;
    }

    public void setSSubInvdetailPK(SSubInvdetailPK sSubInvdetailPK) {
        this.sSubInvdetailPK = sSubInvdetailPK;
    }

    public Date getSInvStDt() {
        return sInvStDt;
    }

    public void setSInvStDt(Date sInvStDt) {
        this.sInvStDt = sInvStDt;
    }

    public String getSInvStVol() {
        return sInvStVol;
    }

    public void setSInvStVol(String sInvStVol) {
        this.sInvStVol = sInvStVol;
    }

    public String getSInvStIss() {
        return sInvStIss;
    }

    public void setSInvStIss(String sInvStIss) {
        this.sInvStIss = sInvStIss;
    }

    public Date getSInvEnDt() {
        return sInvEnDt;
    }

    public void setSInvEnDt(Date sInvEnDt) {
        this.sInvEnDt = sInvEnDt;
    }

    public String getSInvEnVol() {
        return sInvEnVol;
    }

    public void setSInvEnVol(String sInvEnVol) {
        this.sInvEnVol = sInvEnVol;
    }

    public String getSInvEnIss() {
        return sInvEnIss;
    }

    public void setSInvEnIss(String sInvEnIss) {
        this.sInvEnIss = sInvEnIss;
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

    public BigDecimal getSInvPrice() {
        return sInvPrice;
    }

    public void setSInvPrice(BigDecimal sInvPrice) {
        this.sInvPrice = sInvPrice;
    }

    public BigDecimal getSInvPriceRs() {
        return sInvPriceRs;
    }

    public void setSInvPriceRs(BigDecimal sInvPriceRs) {
        this.sInvPriceRs = sInvPriceRs;
    }

    public BigDecimal getSInvDiscount() {
        return sInvDiscount;
    }

    public void setSInvDiscount(BigDecimal sInvDiscount) {
        this.sInvDiscount = sInvDiscount;
    }

    public BigDecimal getSInvDiscounrRs() {
        return sInvDiscounrRs;
    }

    public void setSInvDiscounrRs(BigDecimal sInvDiscounrRs) {
        this.sInvDiscounrRs = sInvDiscounrRs;
    }

    public BigDecimal getSInvPostage() {
        return sInvPostage;
    }

    public void setSInvPostage(BigDecimal sInvPostage) {
        this.sInvPostage = sInvPostage;
    }

    public BigDecimal getSInvPostageRs() {
        return sInvPostageRs;
    }

    public void setSInvPostageRs(BigDecimal sInvPostageRs) {
        this.sInvPostageRs = sInvPostageRs;
    }

    public BigDecimal getSInvHandling() {
        return sInvHandling;
    }

    public void setSInvHandling(BigDecimal sInvHandling) {
        this.sInvHandling = sInvHandling;
    }

    public BigDecimal getSInvHandlingRs() {
        return sInvHandlingRs;
    }

    public void setSInvHandlingRs(BigDecimal sInvHandlingRs) {
        this.sInvHandlingRs = sInvHandlingRs;
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

    public SSubInv getSSubInv() {
        return sSubInv;
    }

    public void setSSubInv(SSubInv sSubInv) {
        this.sSubInv = sSubInv;
    }

    public SSubscription getSSubscription() {
        return sSubscription;
    }

    public void setSSubscription(SSubscription sSubscription) {
        this.sSubscription = sSubscription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sSubInvdetailPK != null ? sSubInvdetailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SSubInvdetail)) {
            return false;
        }
        SSubInvdetail other = (SSubInvdetail) object;
        if ((this.sSubInvdetailPK == null && other.sSubInvdetailPK != null) || (this.sSubInvdetailPK != null && !this.sSubInvdetailPK.equals(other.sSubInvdetailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serialControl.SSubInvdetail[ sSubInvdetailPK=" + sSubInvdetailPK + " ]";
    }
    
}
