/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "s_binding")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SBinding.findAll", query = "SELECT s FROM SBinding s")
    , @NamedQuery(name = "SBinding.findBySBSrNo", query = "SELECT s FROM SBinding s WHERE s.sBindingPK.sBSrNo = :sBSrNo")
    , @NamedQuery(name = "SBinding.findByDate", query = "SELECT s FROM SBinding s WHERE s.sBAccessDt IS NOT NULL")
    , @NamedQuery(name = "SBinding.findBySBNo", query = "SELECT s FROM SBinding s WHERE s.sBindingPK.sBNo = :sBNo")
    , @NamedQuery(name = "SBinding.findBySBBinderCd", query = "SELECT s FROM SBinding s WHERE s.sBBinderCd = :sBBinderCd")
    , @NamedQuery(name = "SBinding.findOrdersForReceiving", query = "SELECT s FROM SBinding s WHERE s.sBBinderCd = :sBBinderCd and s.sBRcptDt IS NULL")
    , @NamedQuery(name = "SBinding.findBySBBudgetCd", query = "SELECT s FROM SBinding s WHERE s.sBBudgetCd = :sBBudgetCd")
    , @NamedQuery(name = "SBinding.findBySBPrice", query = "SELECT s FROM SBinding s WHERE s.sBPrice = :sBPrice")
    , @NamedQuery(name = "SBinding.findBySBSetPrice", query = "SELECT s FROM SBinding s WHERE s.sBSetPrice = :sBSetPrice")
    , @NamedQuery(name = "SBinding.findBySBOrderNo", query = "SELECT s FROM SBinding s WHERE s.sBOrderNo = :sBOrderNo")
    , @NamedQuery(name = "SBinding.findBySBSendDt", query = "SELECT s FROM SBinding s WHERE s.sBSendDt = :sBSendDt")
    , @NamedQuery(name = "SBinding.findBySBLocation", query = "SELECT s FROM SBinding s WHERE s.sBLocation = :sBLocation")
    , @NamedQuery(name = "SBinding.findBySBStpg", query = "SELECT s FROM SBinding s WHERE s.sBStpg = :sBStpg")
    , @NamedQuery(name = "SBinding.findBySBExpDt", query = "SELECT s FROM SBinding s WHERE s.sBExpDt = :sBExpDt")
    , @NamedQuery(name = "SBinding.findBySBRcptDt", query = "SELECT s FROM SBinding s WHERE s.sBRcptDt = :sBRcptDt")
    , @NamedQuery(name = "SBinding.findBySBBindtype", query = "SELECT s FROM SBinding s WHERE s.sBBindtype = :sBBindtype")
    , @NamedQuery(name = "SBinding.findBySBAccessNo", query = "SELECT s FROM SBinding s WHERE s.sBAccessNo = :sBAccessNo")
    , @NamedQuery(name = "SBinding.findBySBRemark", query = "SELECT s FROM SBinding s WHERE s.sBRemark = :sBRemark")
    , @NamedQuery(name = "SBinding.findBySBFlag", query = "SELECT s FROM SBinding s WHERE s.sBFlag = :sBFlag")
    , @NamedQuery(name = "SBinding.findBySBEmbosetext", query = "SELECT s FROM SBinding s WHERE s.sBEmbosetext = :sBEmbosetext")
    , @NamedQuery(name = "SBinding.findBySBEmbosetype", query = "SELECT s FROM SBinding s WHERE s.sBEmbosetype = :sBEmbosetype")
    , @NamedQuery(name = "SBinding.findBySBOrderRemark", query = "SELECT s FROM SBinding s WHERE s.sBOrderRemark = :sBOrderRemark")
    , @NamedQuery(name = "SBinding.findBySBEnpg", query = "SELECT s FROM SBinding s WHERE s.sBEnpg = :sBEnpg")
    , @NamedQuery(name = "SBinding.findBySBAccessDt", query = "SELECT s FROM SBinding s WHERE s.sBAccessDt = :sBAccessDt")
    , @NamedQuery(name = "SBinding.findBySBClassCd", query = "SELECT s FROM SBinding s WHERE s.sBClassCd = :sBClassCd")
    , @NamedQuery(name = "SBinding.findBySBBindcol", query = "SELECT s FROM SBinding s WHERE s.sBBindcol = :sBBindcol")
    , @NamedQuery(name = "SBinding.findBySBIndex", query = "SELECT s FROM SBinding s WHERE s.sBIndex = :sBIndex")
    , @NamedQuery(name = "SBinding.getSetsListForReceivingInhouse", query = "SELECT s FROM SBinding s WHERE s.sBBinderCd = 'INHOUSE' ")
    , @NamedQuery(name = "SBinding.setDetailsBySetNo", query = "SELECT s FROM SBinding s WHERE s.sBindingPK.sBNo = :sBNo and s.sBBinderCd = 'INHOUSE' ")
    , @NamedQuery(name = "SBinding.retrieveAllforPreparationOfOrder", query = "SELECT s FROM SBinding s WHERE (s.sBOrderNo = '' or s.sBOrderNo IS NULL) and (s.sBBinderCd != 'INHOUSE' or s.sBBinderCd IS NULL)")
    , @NamedQuery(name = "SBinding.getBindingOrdersForReceiving", query = "SELECT s FROM SBinding s WHERE s.sBOrderNo != '' and s.sBBinderCd != 'INHOUSE' and (s.sBAccessNo IS NULL or s.sBAccessNo = '') AND s.sBOrderNo != :sBOrderNo GROUP BY s.sBOrderNo")
    , @NamedQuery(name = "SBinding.getDetailsByOrderNo", query = "SELECT s FROM SBinding s WHERE s.sBOrderNo = :sBOrderNo and (s.sBAccessNo IS NULL or s.sBAccessNo = '') ")
    , @NamedQuery(name = "SBinding.deleteSet", query = "DELETE FROM SBinding s WHERE s.sBindingPK.sBNo = ?1 and s.sBindingPK.sBSrNo = ?2")
    , @NamedQuery(name = "SBinding.ordersListForBindingPurchaseOrder", query = "SELECT s FROM SBinding s WHERE s.sBOrderNo != '' and s.sBBinderCd != 'INHOUSE' and (s.sBAccessNo IS NULL or s.sBAccessNo = '') GROUP BY s.sBOrderNo"),
    @NamedQuery(name = "SBinding.findAllOrdersForInvoiceProcessing", query = "SELECT s FROM SBinding s WHERE s.sBOrderNo != '' and s.sBBinderCd != 'INHOUSE' and s.sBOrderNo != ?1 and s.sBBinderCd = ?2 and s.sBOrderNo != ?3")})
public class SBinding implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected SBindingPK sBindingPK;
    @Size(max = 510)
    @Column(name = "s_b_binder_cd")
    private String sBBinderCd;
    @Size(max = 510)
    @Column(name = "s_b_budget_cd")
    private String sBBudgetCd;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "s_b_price")
    private BigDecimal sBPrice;
    @Column(name = "s_b_set_price")
    private BigDecimal sBSetPrice;
    @Size(max = 510)
    @Column(name = "s_b_order_no")
    private String sBOrderNo;
    @Column(name = "s_b_send_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sBSendDt;
    @Size(max = 510)
    @Column(name = "s_b_location")
    private String sBLocation;
    @Column(name = "s_b_stpg")
    private Integer sBStpg;
    @Column(name = "s_b_exp_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sBExpDt;
    @Column(name = "s_b_rcpt_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sBRcptDt;
    @Size(max = 510)
    @Column(name = "s_b_bindtype")
    private String sBBindtype;
    @Size(max = 510)
    @Column(name = "s_b_access_no")
    private String sBAccessNo;
    @Size(max = 510)
    @Column(name = "s_b_remark")
    private String sBRemark;
    @Column(name = "s_b_flag")
    private Integer sBFlag;
    @Size(max = 100)
    @Column(name = "s_b_embosetext")
    private String sBEmbosetext;
    @Size(max = 510)
    @Column(name = "s_b_embosetype")
    private String sBEmbosetype;
    @Size(max = 510)
    @Column(name = "s_b_order_remark")
    private String sBOrderRemark;
    @Column(name = "s_b_enpg")
    private Integer sBEnpg;
    @Column(name = "s_b_access_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sBAccessDt;
    @Size(max = 510)
    @Column(name = "s_b_class_cd")
    private String sBClassCd;
    @Size(max = 510)
    @Column(name = "s_b_bindcol")
    private String sBBindcol;
    @Size(max = 510)
    @Column(name = "s_b_index")
    private String sBIndex;

    public SBinding() {
    }

    public SBinding(SBindingPK sBindingPK) {
        this.sBindingPK = sBindingPK;
    }

    public SBinding(long sBSrNo, String sBNo) {
        this.sBindingPK = new SBindingPK(sBSrNo, sBNo);
    }

    public SBindingPK getSBindingPK() {
        return sBindingPK;
    }

    public void setSBindingPK(SBindingPK sBindingPK) {
        this.sBindingPK = sBindingPK;
    }

    public String getSBBinderCd() {
        return sBBinderCd;
    }

    public void setSBBinderCd(String sBBinderCd) {
        this.sBBinderCd = sBBinderCd;
    }

    public String getSBBudgetCd() {
        return sBBudgetCd;
    }

    public void setSBBudgetCd(String sBBudgetCd) {
        this.sBBudgetCd = sBBudgetCd;
    }

    public BigDecimal getSBPrice() {
        return sBPrice;
    }

    public void setSBPrice(BigDecimal sBPrice) {
        this.sBPrice = sBPrice;
    }

    public BigDecimal getSBSetPrice() {
        return sBSetPrice;
    }

    public void setSBSetPrice(BigDecimal sBSetPrice) {
        this.sBSetPrice = sBSetPrice;
    }

    public String getSBOrderNo() {
        return sBOrderNo;
    }

    public void setSBOrderNo(String sBOrderNo) {
        this.sBOrderNo = sBOrderNo;
    }

    public Date getSBSendDt() {
        return sBSendDt;
    }

    public void setSBSendDt(Date sBSendDt) {
        this.sBSendDt = sBSendDt;
    }

    public String getSBLocation() {
        return sBLocation;
    }

    public void setSBLocation(String sBLocation) {
        this.sBLocation = sBLocation;
    }

    public Integer getSBStpg() {
        return sBStpg;
    }

    public void setSBStpg(Integer sBStpg) {
        this.sBStpg = sBStpg;
    }

    public Date getSBExpDt() {
        return sBExpDt;
    }

    public void setSBExpDt(Date sBExpDt) {
        this.sBExpDt = sBExpDt;
    }

    public Date getSBRcptDt() {
        return sBRcptDt;
    }

    public void setSBRcptDt(Date sBRcptDt) {
        this.sBRcptDt = sBRcptDt;
    }

    public String getSBBindtype() {
        return sBBindtype;
    }

    public void setSBBindtype(String sBBindtype) {
        this.sBBindtype = sBBindtype;
    }

    public String getSBAccessNo() {
        return sBAccessNo;
    }

    public void setSBAccessNo(String sBAccessNo) {
        this.sBAccessNo = sBAccessNo;
    }

    public String getSBRemark() {
        return sBRemark;
    }

    public void setSBRemark(String sBRemark) {
        this.sBRemark = sBRemark;
    }

    public Integer getSBFlag() {
        return sBFlag;
    }

    public void setSBFlag(Integer sBFlag) {
        this.sBFlag = sBFlag;
    }

    public String getSBEmbosetext() {
        return sBEmbosetext;
    }

    public void setSBEmbosetext(String sBEmbosetext) {
        this.sBEmbosetext = sBEmbosetext;
    }

    public String getSBEmbosetype() {
        return sBEmbosetype;
    }

    public void setSBEmbosetype(String sBEmbosetype) {
        this.sBEmbosetype = sBEmbosetype;
    }

    public String getSBOrderRemark() {
        return sBOrderRemark;
    }

    public void setSBOrderRemark(String sBOrderRemark) {
        this.sBOrderRemark = sBOrderRemark;
    }

    public Integer getSBEnpg() {
        return sBEnpg;
    }

    public void setSBEnpg(Integer sBEnpg) {
        this.sBEnpg = sBEnpg;
    }

    public Date getSBAccessDt() {
        return sBAccessDt;
    }

    public void setSBAccessDt(Date sBAccessDt) {
        this.sBAccessDt = sBAccessDt;
    }

    public String getSBClassCd() {
        return sBClassCd;
    }

    public void setSBClassCd(String sBClassCd) {
        this.sBClassCd = sBClassCd;
    }

    public String getSBBindcol() {
        return sBBindcol;
    }

    public void setSBBindcol(String sBBindcol) {
        this.sBBindcol = sBBindcol;
    }

    public String getSBIndex() {
        return sBIndex;
    }

    public void setSBIndex(String sBIndex) {
        this.sBIndex = sBIndex;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sBindingPK != null ? sBindingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SBinding)) {
            return false;
        }
        SBinding other = (SBinding) object;
        if ((this.sBindingPK == null && other.sBindingPK != null) || (this.sBindingPK != null && !this.sBindingPK.equals(other.sBindingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serial_master.SBinding[ sBindingPK=" + sBindingPK + " ]";
    }
    
}
