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
import javax.persistence.Lob;
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
@Table(name = "t_binding")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TBinding.findAll", query = "SELECT t FROM TBinding t"),
    @NamedQuery(name = "TBinding.findByBNo", query = "SELECT t FROM TBinding t WHERE t.bNo = :bNo"),
    @NamedQuery(name = "TBinding.findByBSendDt", query = "SELECT t FROM TBinding t WHERE t.bSendDt = :bSendDt"),
    @NamedQuery(name = "TBinding.findByBBinderCd", query = "SELECT t FROM TBinding t WHERE t.bBinderCd = :bBinderCd"),
    @NamedQuery(name = "TBinding.findByBBudgetCd", query = "SELECT t FROM TBinding t WHERE t.bBudgetCd = :bBudgetCd"),
    @NamedQuery(name = "TBinding.findByBLocation", query = "SELECT t FROM TBinding t WHERE t.bLocation = :bLocation"),
    @NamedQuery(name = "TBinding.findByBPrice", query = "SELECT t FROM TBinding t WHERE t.bPrice = :bPrice"),
    @NamedQuery(name = "TBinding.findByBTotpg", query = "SELECT t FROM TBinding t WHERE t.bTotpg = :bTotpg"),
    @NamedQuery(name = "TBinding.findByBExpDt", query = "SELECT t FROM TBinding t WHERE t.bExpDt = :bExpDt"),
    @NamedQuery(name = "TBinding.findByBRcptDt", query = "SELECT t FROM TBinding t WHERE t.bRcptDt = :bRcptDt"),
    @NamedQuery(name = "TBinding.findByBBindtype", query = "SELECT t FROM TBinding t WHERE t.bBindtype = :bBindtype"),
    @NamedQuery(name = "TBinding.findByBOrderNo", query = "SELECT t FROM TBinding t WHERE t.bOrderNo = :bOrderNo"),
    @NamedQuery(name = "TBinding.findByBAccessNo", query = "SELECT t FROM TBinding t WHERE t.bAccessNo = :bAccessNo"),
    @NamedQuery(name = "TBinding.findByBRemark", query = "SELECT t FROM TBinding t WHERE t.bRemark = :bRemark"),
    @NamedQuery(name = "TBinding.findByBFlag", query = "SELECT t FROM TBinding t WHERE t.bFlag = :bFlag"),
    @NamedQuery(name = "TBinding.findByBclassCd", query = "SELECT t FROM TBinding t WHERE t.bclassCd = :bclassCd"),
    
    @NamedQuery(name = "TBinding.findByBOrderNoAndBFlag", query = "SELECT t FROM TBinding t WHERE t.bOrderNo = ?1 AND t.bFlag = ?2"),
    @NamedQuery(name = "TBinding.findByBindDtBtwn", query = "SELECT t FROM TBinding t WHERE t.bSendDt BETWEEN ?1 AND ?2"),
    @NamedQuery(name = "TBinding.findByNullBOrderNo", query = "SELECT t FROM TBinding t WHERE t.bOrderNo IS NULL")
})
public class TBinding implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "b_no")
    private Integer bNo;
    @Column(name = "b_send_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bSendDt;
    @Size(max = 6)
    @Column(name = "b_binder_cd")
    private String bBinderCd;
    @Column(name = "b_budget_cd")
    private Integer bBudgetCd;
    @Size(max = 15)
    @Column(name = "b_location")
    private String bLocation;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "b_price")
    private BigDecimal bPrice;
    @Column(name = "b_totpg")
    private Integer bTotpg;
    @Column(name = "b_exp_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bExpDt;
    @Column(name = "b_rcpt_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bRcptDt;
    @Size(max = 510)
    @Column(name = "b_bindtype")
    private String bBindtype;
    @Size(max = 15)
    @Column(name = "b_order_no")
    private String bOrderNo;
    @Size(max = 50)
    @Column(name = "b_access_no")
    private String bAccessNo;
    @Size(max = 255)
    @Column(name = "b_remark")
    private String bRemark;
    @Column(name = "b_flag")
    private Integer bFlag;
    @Size(max = 50)
    @Column(name = "b_class_cd")
    private String bclassCd;
    @Lob
    @Size(max = 65535)
    @Column(name = "b_title")
    private String bTitle;

    public TBinding() {
    }

    public TBinding(Integer bNo) {
        this.bNo = bNo;
    }

    public Integer getBNo() {
        return bNo;
    }

    public void setBNo(Integer bNo) {
        this.bNo = bNo;
    }

    public Date getBSendDt() {
        return bSendDt;
    }

    public void setBSendDt(Date bSendDt) {
        this.bSendDt = bSendDt;
    }

    public String getBBinderCd() {
        return bBinderCd;
    }

    public void setBBinderCd(String bBinderCd) {
        this.bBinderCd = bBinderCd;
    }

    public Integer getBBudgetCd() {
        return bBudgetCd;
    }

    public void setBBudgetCd(Integer bBudgetCd) {
        this.bBudgetCd = bBudgetCd;
    }

    public String getBLocation() {
        return bLocation;
    }

    public void setBLocation(String bLocation) {
        this.bLocation = bLocation;
    }

    public BigDecimal getBPrice() {
        return bPrice;
    }

    public void setBPrice(BigDecimal bPrice) {
        this.bPrice = bPrice;
    }

    public Integer getBTotpg() {
        return bTotpg;
    }

    public void setBTotpg(Integer bTotpg) {
        this.bTotpg = bTotpg;
    }

    public Date getBExpDt() {
        return bExpDt;
    }

    public void setBExpDt(Date bExpDt) {
        this.bExpDt = bExpDt;
    }

    public Date getBRcptDt() {
        return bRcptDt;
    }

    public void setBRcptDt(Date bRcptDt) {
        this.bRcptDt = bRcptDt;
    }

    public String getBBindtype() {
        return bBindtype;
    }

    public void setBBindtype(String bBindtype) {
        this.bBindtype = bBindtype;
    }

    public String getBOrderNo() {
        return bOrderNo;
    }

    public void setBOrderNo(String bOrderNo) {
        this.bOrderNo = bOrderNo;
    }

    public String getBAccessNo() {
        return bAccessNo;
    }

    public void setBAccessNo(String bAccessNo) {
        this.bAccessNo = bAccessNo;
    }

    public String getBRemark() {
        return bRemark;
    }

    public void setBRemark(String bRemark) {
        this.bRemark = bRemark;
    }

    public Integer getBFlag() {
        return bFlag;
    }

    public void setBFlag(Integer bFlag) {
        this.bFlag = bFlag;
    }

    public String getBclassCd() {
        return bclassCd;
    }

    public void setBclassCd(String bclassCd) {
        this.bclassCd = bclassCd;
    }

    public String getBTitle() {
        return bTitle;
    }

    public void setBTitle(String bTitle) {
        this.bTitle = bTitle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bNo != null ? bNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TBinding)) {
            return false;
        }
        TBinding other = (TBinding) object;
        if ((this.bNo == null && other.bNo != null) || (this.bNo != null && !this.bNo.equals(other.bNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TBinding[ bNo=" + bNo + " ]";
    }
    
}
