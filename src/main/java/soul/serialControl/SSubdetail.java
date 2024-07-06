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
@Table(name = "s_subdetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SSubdetail.findAll", query = "SELECT s FROM SSubdetail s"),
    @NamedQuery(name = "SSubdetail.findBySOrderNo", query = "SELECT s FROM SSubdetail s WHERE s.sSubdetailPK.sOrderNo = :sOrderNo"),
    @NamedQuery(name = "SSubdetail.findBySSNm", query = "SELECT s FROM SSubdetail s WHERE s.sSNm = :sSNm"),
    @NamedQuery(name = "SSubdetail.findBySRecid", query = "SELECT s FROM SSubdetail s WHERE s.sSubdetailPK.sRecid = :sRecid"),
    @NamedQuery(name = "SSubdetail.findBySStDt", query = "SELECT s FROM SSubdetail s WHERE s.sStDt = :sStDt"),
    @NamedQuery(name = "SSubdetail.findBySStVol", query = "SELECT s FROM SSubdetail s WHERE s.sStVol = :sStVol"),
    @NamedQuery(name = "SSubdetail.findBySStIss", query = "SELECT s FROM SSubdetail s WHERE s.sStIss = :sStIss"),
    @NamedQuery(name = "SSubdetail.findBySEnDt", query = "SELECT s FROM SSubdetail s WHERE s.sEnDt = :sEnDt"),
    @NamedQuery(name = "SSubdetail.findBySEnVol", query = "SELECT s FROM SSubdetail s WHERE s.sEnVol = :sEnVol"),
    @NamedQuery(name = "SSubdetail.findBySEnIss", query = "SELECT s FROM SSubdetail s WHERE s.sEnIss = :sEnIss"),
    @NamedQuery(name = "SSubdetail.findBySMode", query = "SELECT s FROM SSubdetail s WHERE s.sMode = :sMode"),
    @NamedQuery(name = "SSubdetail.findBySCopy", query = "SELECT s FROM SSubdetail s WHERE s.sCopy = :sCopy"),
    @NamedQuery(name = "SSubdetail.findBySCancel", query = "SELECT s FROM SSubdetail s WHERE s.sCancel = :sCancel"),
    @NamedQuery(name = "SSubdetail.findBySStatus", query = "SELECT s FROM SSubdetail s WHERE s.sStatus = :sStatus"),
    @NamedQuery(name = "SSubdetail.findBySCancelDt", query = "SELECT s FROM SSubdetail s WHERE s.sCancelDt = :sCancelDt"),
    @NamedQuery(name = "SSubdetail.findBySReqNo", query = "SELECT s FROM SSubdetail s WHERE s.sReqNo = :sReqNo"),
    @NamedQuery(name = "SSubdetail.findBySPrice", query = "SELECT s FROM SSubdetail s WHERE s.sPrice = :sPrice"),
    
    @NamedQuery(name = "SSubdetail.findBySMstStatussNotStanding", query = "SELECT s FROM SSubdetail s WHERE s.sMst.sMstStatus IN :statuss AND s.sSubscription.sOrderType != 'S'"),
    @NamedQuery(name = "SSubdetail.findByOrderTypeNot", query = "SELECT s FROM SSubdetail s WHERE s.sSubscription.sOrderType <> :sOrderType"),
    
    @NamedQuery(name = "SSubdetail.findAllOrders", query = "SELECT s FROM SSubdetail s WHERE s.sSubdetailPK.sOrderNo IN (SELECT s.sMst.sOrderNo FROM SSubdetail s) AND s.sSubdetailPK.sOrderNo IN (SELECT s.sSubscription.sOrderNo FROM SSubdetail s where s.sSubscription.sOrderType != 'S')"),
    @NamedQuery(name = "SSubdetail.findAllOrdersBySupplierCode", query = "SELECT s FROM SSubdetail s WHERE s.sSubscription.sCancel = 'N' AND s.sSubscription.sSupCd = :sSupCd AND s.sSubscription.sOrderNo IN (SELECT s.sMst.sOrderNo FROM SSubdetail s WHERE s.sMst.sMstStatus != 'O')"),
    @NamedQuery(name = "SSubdetail.findAllOrdersByTitle", query = "SELECT s FROM SSubdetail s WHERE s.sSubdetailPK.sOrderNo IN (SELECT s.sMst.sOrderNo FROM SSubdetail s where s.sMst.sTitle like :sTitle) AND s.sSubdetailPK.sOrderNo IN (SELECT s.sSubscription.sOrderNo FROM SSubdetail s where s.sSubscription.sOrderType != 'S')"),
    @NamedQuery(name = "SSubdetail.findForCancellation", query = "SELECT s FROM SSubdetail s WHERE s.sSubscription.sCancel = 'N' AND ((s.sStatus <> 'Y' AND s.sMst.sMstStatus = 'O') OR (s.sStatus <> 'N' AND s.sMst.sMstStatus = 'I'))  AND s.sMst.sInvNo IS NULL "),
    
    @NamedQuery(name = "SSubdetail.getOrderByNo", query = "SELECT s FROM SSubdetail s WHERE s.sMst.sRecid = s.sSubdetailPK.sRecid AND s.sMst.sTitle IN (SELECT s.sMst.sTitle FROM SSubdetail s where s.sMst.sOrderNo = :sOrderNo) AND s.sSubdetailPK.sOrderNo = :sOrderNo"),
    @NamedQuery(name = "SSubdetail.getOrderDetails", query = "SELECT s FROM SSubdetail s WHERE s.sMst.sOrderNo = :sOrderNo AND (s.sMst.sMstStatus = 'O' OR s.sMst.sMstStatus = 'I') AND s.sMst.sTyAcqu = 'S'"),
    
    @NamedQuery(name = "SSubdetail.purchaseByOrderNo", query = "SELECT s FROM SSubdetail s WHERE s.sSubscription.sOrderNo = s.sSubdetailPK.sOrderNo AND s.sSubdetailPK.sRecid = s.sMst.sRecid AND s.sCancel is NULL AND s.sSubscription.sOrderNo = :sOrderNo"),
    @NamedQuery(name = "SSubdetail.orderDetailsByOrderNo", query = "SELECT s FROM SSubdetail s WHERE s.sMst.sRecid = s.sSubdetailPK.sRecid AND s.sSubdetailPK.sOrderNo = :sOrderNo"),
})
public class SSubdetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SSubdetailPK sSubdetailPK;
    @Size(max = 510)
    @Column(name = "s_s_nm")
    private String sSNm;
    @Column(name = "s_st_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sStDt;
    @Size(max = 100)
    @Column(name = "s_st_vol")
    private String sStVol;
    @Size(max = 100)
    @Column(name = "s_st_iss")
    private String sStIss;
    @Column(name = "s_en_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sEnDt;
    @Size(max = 100)
    @Column(name = "s_en_vol")
    private String sEnVol;
    @Size(max = 100)
    @Column(name = "s_en_iss")
    private String sEnIss;
    @Size(max = 510)
    @Column(name = "s_mode")
    private String sMode;
    @Column(name = "s_copy")
    private Integer sCopy;
    @Size(max = 510)
    @Column(name = "s_cancel")
    private String sCancel;
    @Size(max = 510)
    @Column(name = "s_status")
    private String sStatus;
    @Column(name = "s_cancel_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sCancelDt;
    @Column(name = "s_req_no")
    private Integer sReqNo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "s_price")
    private BigDecimal sPrice;
    @JoinColumn(name = "s_recid", referencedColumnName = "s_recid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SMst sMst;
    @JoinColumn(name = "s_order_no", referencedColumnName = "s_order_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SSubscription sSubscription;

    public SSubdetail() {
    }

    public SSubdetail(SSubdetailPK sSubdetailPK) {
        this.sSubdetailPK = sSubdetailPK;
    }

    public SSubdetail(String sOrderNo, String sRecid) {
        this.sSubdetailPK = new SSubdetailPK(sOrderNo, sRecid);
    }

    public SSubdetailPK getSSubdetailPK() {
        return sSubdetailPK;
    }

    public void setSSubdetailPK(SSubdetailPK sSubdetailPK) {
        this.sSubdetailPK = sSubdetailPK;
    }

    public String getSSNm() {
        return sSNm;
    }

    public void setSSNm(String sSNm) {
        this.sSNm = sSNm;
    }

    public Date getSStDt() {
        return sStDt;
    }

    public void setSStDt(Date sStDt) {
        this.sStDt = sStDt;
    }

    public String getSStVol() {
        return sStVol;
    }

    public void setSStVol(String sStVol) {
        this.sStVol = sStVol;
    }

    public String getSStIss() {
        return sStIss;
    }

    public void setSStIss(String sStIss) {
        this.sStIss = sStIss;
    }

    public Date getSEnDt() {
        return sEnDt;
    }

    public void setSEnDt(Date sEnDt) {
        this.sEnDt = sEnDt;
    }

    public String getSEnVol() {
        return sEnVol;
    }

    public void setSEnVol(String sEnVol) {
        this.sEnVol = sEnVol;
    }

    public String getSEnIss() {
        return sEnIss;
    }

    public void setSEnIss(String sEnIss) {
        this.sEnIss = sEnIss;
    }

    public String getSMode() {
        return sMode;
    }

    public void setSMode(String sMode) {
        this.sMode = sMode;
    }

    public Integer getSCopy() {
        return sCopy;
    }

    public void setSCopy(Integer sCopy) {
        this.sCopy = sCopy;
    }

    public String getSCancel() {
        return sCancel;
    }

    public void setSCancel(String sCancel) {
        this.sCancel = sCancel;
    }

    public String getSStatus() {
        return sStatus;
    }

    public void setSStatus(String sStatus) {
        this.sStatus = sStatus;
    }

    public Date getSCancelDt() {
        return sCancelDt;
    }

    public void setSCancelDt(Date sCancelDt) {
        this.sCancelDt = sCancelDt;
    }

    public Integer getSReqNo() {
        return sReqNo;
    }

    public void setSReqNo(Integer sReqNo) {
        this.sReqNo = sReqNo;
    }

    public BigDecimal getSPrice() {
        return sPrice;
    }

    public void setSPrice(BigDecimal sPrice) {
        this.sPrice = sPrice;
    }

    public SMst getSMst() {
        return sMst;
    }

    public void setSMst(SMst sMst) {
        this.sMst = sMst;
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
        hash += (sSubdetailPK != null ? sSubdetailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SSubdetail)) {
            return false;
        }
        SSubdetail other = (SSubdetail) object;
        if ((this.sSubdetailPK == null && other.sSubdetailPK != null) || (this.sSubdetailPK != null && !this.sSubdetailPK.equals(other.sSubdetailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serialControl.SSubdetail[ sSubdetailPK=" + sSubdetailPK + " ]";
    }
    
}
