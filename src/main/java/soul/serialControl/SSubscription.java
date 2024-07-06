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
@Table(name = "s_subscription")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SSubscription.findAll", query = "SELECT s FROM SSubscription s"),
    @NamedQuery(name = "SSubscription.findBySOrderNo", query = "SELECT s FROM SSubscription s WHERE s.sOrderNo = :sOrderNo"),
    @NamedQuery(name = "SSubscription.findBySOrderDt", query = "SELECT s FROM SSubscription s WHERE s.sOrderDt = :sOrderDt"),
    @NamedQuery(name = "SSubscription.findBySType", query = "SELECT s FROM SSubscription s WHERE s.sType = :sType"),
    @NamedQuery(name = "SSubscription.findBySCancel", query = "SELECT s FROM SSubscription s WHERE s.sCancel = :sCancel"),
    @NamedQuery(name = "SSubscription.findBySOrderType", query = "SELECT s FROM SSubscription s WHERE s.sOrderType = :sOrderType"),
    @NamedQuery(name = "SSubscription.findBySRemark", query = "SELECT s FROM SSubscription s WHERE s.sRemark = :sRemark"),
    @NamedQuery(name = "SSubscription.findBySCancelDt", query = "SELECT s FROM SSubscription s WHERE s.sCancelDt = :sCancelDt"),
    @NamedQuery(name = "SSubscription.findBySSupCd", query = "SELECT s FROM SSubscription s WHERE s.sSupCd = :sSupCd"),
    @NamedQuery(name = "SSubscription.findOrdersBySupplierCode", query = "SELECT s FROM SSubscription s WHERE s.sSupCd = :sSupCd"),
    @NamedQuery(name = "SSubscription.findBySSupCity", query = "SELECT s FROM SSubscription s WHERE s.sSupCity = :sSupCity"),
    @NamedQuery(name = "SSubscription.findBySSupCountry", query = "SELECT s FROM SSubscription s WHERE s.sSupCountry = :sSupCountry"),
    @NamedQuery(name = "SSubscription.findBySPubsup", query = "SELECT s FROM SSubscription s WHERE s.sPubsup = :sPubsup"),
    @NamedQuery(name = "SSubscription.findBySCanremark", query = "SELECT s FROM SSubscription s WHERE s.sCanremark = :sCanremark"),
    @NamedQuery(name = "SSubscription.findBySInvstatus", query = "SELECT s FROM SSubscription s WHERE s.sInvstatus = :sInvstatus"),
    @NamedQuery(name = "SSubscription.findBySOrderPrice", query = "SELECT s FROM SSubscription s WHERE s.sOrderPrice = :sOrderPrice"),
    @NamedQuery(name = "SSubscription.findOrdersForPurchasing", query = "SELECT s FROM SSubscription s WHERE s.sCancel = 'N' "),
    @NamedQuery(name = "SSubscription.getSPubsup", query = "SELECT s FROM SSubscription s WHERE s.sPubsup = 'Y' "),
})
public class SSubscription implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sSubscription")
    private Collection<SSubInvdetail> sSubInvdetailCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_order_no")
    private String sOrderNo;
    @Column(name = "s_order_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sOrderDt;
    @Size(max = 510)
    @Column(name = "s_type")
    private String sType;
    @Size(max = 510)
    @Column(name = "s_cancel")
    private String sCancel;
    @Size(max = 510)
    @Column(name = "s_order_type")
    private String sOrderType;
    @Size(max = 510)
    @Column(name = "s_remark")
    private String sRemark;
    @Column(name = "s_cancel_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sCancelDt;
    @Size(max = 510)
    @Column(name = "s_sup_cd")
    private String sSupCd;
    @Size(max = 510)
    @Column(name = "s_sup_city")
    private String sSupCity;
    @Size(max = 510)
    @Column(name = "s_sup_country")
    private String sSupCountry;
    @Size(max = 510)
    @Column(name = "s_pubsup")
    private String sPubsup;
    @Size(max = 510)
    @Column(name = "s_canremark")
    private String sCanremark;
    @Size(max = 510)
    @Column(name = "s_invstatus")
    private String sInvstatus;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "s_order_price")
    private BigDecimal sOrderPrice;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sSubscription")
    private Collection<SSubdetail> sSubdetailCollection;

    public SSubscription() {
    }

    public SSubscription(String sOrderNo) {
        this.sOrderNo = sOrderNo;
    }

    public String getSOrderNo() {
        return sOrderNo;
    }

    public void setSOrderNo(String sOrderNo) {
        this.sOrderNo = sOrderNo;
    }

    public Date getSOrderDt() {
        return sOrderDt;
    }

    public void setSOrderDt(Date sOrderDt) {
        this.sOrderDt = sOrderDt;
    }

    public String getSType() {
        return sType;
    }

    public void setSType(String sType) {
        this.sType = sType;
    }

    public String getSCancel() {
        return sCancel;
    }

    public void setSCancel(String sCancel) {
        this.sCancel = sCancel;
    }

    public String getSOrderType() {
        return sOrderType;
    }

    public void setSOrderType(String sOrderType) {
        this.sOrderType = sOrderType;
    }

    public String getSRemark() {
        return sRemark;
    }

    public void setSRemark(String sRemark) {
        this.sRemark = sRemark;
    }

    public Date getSCancelDt() {
        return sCancelDt;
    }

    public void setSCancelDt(Date sCancelDt) {
        this.sCancelDt = sCancelDt;
    }

    public String getSSupCd() {
        return sSupCd;
    }

    public void setSSupCd(String sSupCd) {
        this.sSupCd = sSupCd;
    }

    public String getSSupCity() {
        return sSupCity;
    }

    public void setSSupCity(String sSupCity) {
        this.sSupCity = sSupCity;
    }

    public String getSSupCountry() {
        return sSupCountry;
    }

    public void setSSupCountry(String sSupCountry) {
        this.sSupCountry = sSupCountry;
    }

    public String getSPubsup() {
        return sPubsup;
    }

    public void setSPubsup(String sPubsup) {
        this.sPubsup = sPubsup;
    }

    public String getSCanremark() {
        return sCanremark;
    }

    public void setSCanremark(String sCanremark) {
        this.sCanremark = sCanremark;
    }

    public String getSInvstatus() {
        return sInvstatus;
    }

    public void setSInvstatus(String sInvstatus) {
        this.sInvstatus = sInvstatus;
    }

    public BigDecimal getSOrderPrice() {
        return sOrderPrice;
    }

    public void setSOrderPrice(BigDecimal sOrderPrice) {
        this.sOrderPrice = sOrderPrice;
    }

    @XmlTransient
    public Collection<SSubdetail> getSSubdetailCollection() {
        return sSubdetailCollection;
    }

    public void setSSubdetailCollection(Collection<SSubdetail> sSubdetailCollection) {
        this.sSubdetailCollection = sSubdetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sOrderNo != null ? sOrderNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SSubscription)) {
            return false;
        }
        SSubscription other = (SSubscription) object;
        if ((this.sOrderNo == null && other.sOrderNo != null) || (this.sOrderNo != null && !this.sOrderNo.equals(other.sOrderNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serialControl.SSubscription[ sOrderNo=" + sOrderNo + " ]";
    }

    @XmlTransient
    public Collection<SSubInvdetail> getSSubInvdetailCollection() {
        return sSubInvdetailCollection;
    }

    public void setSSubInvdetailCollection(Collection<SSubInvdetail> sSubInvdetailCollection) {
        this.sSubInvdetailCollection = sSubInvdetailCollection;
    }
    
}
