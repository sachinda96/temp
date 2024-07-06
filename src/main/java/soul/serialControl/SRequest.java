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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import soul.circulation.MMember;
import soul.general_master.ABudget;
import soul.general_master.ACurrency;
import soul.general_master.BkSubject;
import soul.general_master.Libmaterials;
import soul.general_master.MFcltydept;
import soul.serial_master.SEdition;
import soul.serial_master.SFrequency;
import soul.serial_master.SSupplierDetail;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "s_request")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SRequest.findAll", query = "SELECT s FROM SRequest s"),
    @NamedQuery(name = "SRequest.findBySRNo", query = "SELECT s FROM SRequest s WHERE s.sRNo = :sRNo"),
    @NamedQuery(name = "SRequest.findBySRDate", query = "SELECT s FROM SRequest s WHERE s.sRDate = :sRDate"),
    @NamedQuery(name = "SRequest.findBySRStatus", query = "SELECT s FROM SRequest s WHERE s.sRStatus = :sRStatus"),
    @NamedQuery(name = "SRequest.findBySRFromDt", query = "SELECT s FROM SRequest s WHERE s.sRFromDt = :sRFromDt"),
    @NamedQuery(name = "SRequest.findBySRToDt", query = "SELECT s FROM SRequest s WHERE s.sRToDt = :sRToDt"),
    @NamedQuery(name = "SRequest.findBySRTitle", query = "SELECT s FROM SRequest s WHERE s.sRTitle = :sRTitle"),
    @NamedQuery(name = "SRequest.findBySRPrice", query = "SELECT s FROM SRequest s WHERE s.sRPrice = :sRPrice"),
    @NamedQuery(name = "SRequest.findBySRConvRate", query = "SELECT s FROM SRequest s WHERE s.sRConvRate = :sRConvRate"),
    @NamedQuery(name = "SRequest.findBySRDiscount", query = "SELECT s FROM SRequest s WHERE s.sRDiscount = :sRDiscount"),
    @NamedQuery(name = "SRequest.findBySRPostAmt", query = "SELECT s FROM SRequest s WHERE s.sRPostAmt = :sRPostAmt"),
    @NamedQuery(name = "SRequest.findBySRForwardAmt", query = "SELECT s FROM SRequest s WHERE s.sRForwardAmt = :sRForwardAmt"),
    @NamedQuery(name = "SRequest.findBySRApprCopy", query = "SELECT s FROM SRequest s WHERE s.sRApprCopy = :sRApprCopy"),
    @NamedQuery(name = "SRequest.findBySRApprDt", query = "SELECT s FROM SRequest s WHERE s.sRApprDt = :sRApprDt"),
    @NamedQuery(name = "SRequest.findBySRApprBy", query = "SELECT s FROM SRequest s WHERE s.sRApprBy = :sRApprBy"),
    @NamedQuery(name = "SRequest.findBySRRemark", query = "SELECT s FROM SRequest s WHERE s.sRRemark = :sRRemark"),
    @NamedQuery(name = "SRequest.findBySRPubCountry", query = "SELECT s FROM SRequest s WHERE s.sRPubCountry = :sRPubCountry"),
    @NamedQuery(name = "SRequest.findBySRPubCity", query = "SELECT s FROM SRequest s WHERE s.sRPubCity = :sRPubCity"),
    @NamedQuery(name = "SRequest.findBySRSupCountry", query = "SELECT s FROM SRequest s WHERE s.sRSupCountry = :sRSupCountry"),
    @NamedQuery(name = "SRequest.findBySRSupCity", query = "SELECT s FROM SRequest s WHERE s.sRSupCity = :sRSupCity"),
    @NamedQuery(name = "SRequest.findBySRReqCopy", query = "SELECT s FROM SRequest s WHERE s.sRReqCopy = :sRReqCopy"),
    @NamedQuery(name = "SRequest.findBySRNetAmount", query = "SELECT s FROM SRequest s WHERE s.sRNetAmount = :sRNetAmount"),
    @NamedQuery(name = "SRequest.findBySRIssn", query = "SELECT s FROM SRequest s WHERE s.sRIssn = :sRIssn"),
    @NamedQuery(name = "SRequest.findBySRRefno", query = "SELECT s FROM SRequest s WHERE s.sRRefno = :sRRefno"),
    @NamedQuery(name = "SRequest.findBySRRecid", query = "SELECT s FROM SRequest s WHERE s.sRRecid = :sRRecid"),
    @NamedQuery(name = "SRequest.findByRequestStatus", query = "SELECT s FROM SRequest s WHERE s.sRStatus = :sRStatuss"),
    @NamedQuery(name = "SRequest.findBySRStatuss", query = "SELECT s FROM SRequest s WHERE s.sRStatus IN :sRStatuss"),
    
    
    @NamedQuery(name = "SRequest.findAllNewRequests", query = "SELECT s FROM SRequest s WHERE s.sRStatus = 'P' "),
    @NamedQuery(name = "SRequest.findByTitleAndRequestStatus", query = "SELECT s FROM SRequest s WHERE s.sRTitle LIKE ?1 and s.sRStatus = ?2 "),
    @NamedQuery(name = "SRequest.findByRequestIDAndRequestStatus", query = "SELECT s FROM SRequest s WHERE s.sRNo = ?1 and s.sRStatus = ?2 "),
    @NamedQuery(name = "SRequest.findByPublisherAndRequestStatus", query = "SELECT s FROM SRequest s WHERE s.sRPubCd.aSName LIKE ?1 and s.sRStatus = ?2 "),
    @NamedQuery(name = "SRequest.findBySupplierAndRequestStatus", query = "SELECT s FROM SRequest s WHERE s.sRSupCd.aSName LIKE ?1 and s.sRStatus = ?2 "),
    @NamedQuery(name = "SRequest.findBySubjectAndRequestStatus", query = "SELECT s FROM SRequest s WHERE s.sRSubCd.bkSubjectName LIKE ?1 and s.sRStatus = ?2 "),
    @NamedQuery(name = "SRequest.findByBudgetAndRequestStatus", query = "SELECT s FROM SRequest s WHERE s.sRBudgetCd.budgetHead.shortDesc LIKE ?1 and s.sRStatus = ?2 "),
    @NamedQuery(name = "SRequest.findByDateBetweenAndRequestStatus", query = "SELECT s FROM SRequest s WHERE s.sRStatus = 'R' and s.sRDate BETWEEN ?1 and ?2"),
    @NamedQuery(name = "SRequest.findByDepartmentAndRequestStatus", query = "SELECT s FROM SRequest s WHERE s.sRDeptCd.fcltydeptdscr LIKE ?1 and s.sRStatus = ?2 "),
    
    @NamedQuery(name = "SRequest.findByRequestNoWise", query = "SELECT s FROM SRequest s WHERE s.sRNo >= ?1 AND s.sRNo <= ?2"),
    @NamedQuery(name = "SRequest.findByDepartmentWise", query = "SELECT s FROM SRequest s WHERE s.sRDeptCd.fcltydeptdscr LIKE :fcltydeptdscr"),
    @NamedQuery(name = "SRequest.findByBudgetWise", query = "SELECT s FROM SRequest s WHERE s.sRBudgetCd.budgetHead.shortDesc LIKE :shortDesc"),
    @NamedQuery(name = "SRequest.findByPublisher", query = "SELECT s FROM SRequest s WHERE s.sRPubCd.aSName LIKE :aSName"),
    @NamedQuery(name = "SRequest.findBySupplier", query = "SELECT s FROM SRequest s WHERE s.sRSupCd.aSName LIKE :aSName"),
    @NamedQuery(name = "SRequest.findByRequesterWise", query = "SELECT s FROM SRequest s WHERE s.sRNm.memCd LIKE :memCd"),
    @NamedQuery(name = "SRequest.findByMemCd", query = "SELECT s FROM SRequest s WHERE s.sRNm.memCd = :memCd"),
})
public class SRequest implements Serializable {
    @OneToMany(mappedBy = "sReqNo")
    private Collection<SMst> sMstCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "s_r_no")
    private Integer sRNo;
    @Column(name = "s_r_date")
    @Temporal(TemporalType.DATE)
    private Date sRDate;
    @Size(max = 20)
    @Column(name = "s_r_status")
    private String sRStatus;
    @Column(name = "s_r_from_dt")
    @Temporal(TemporalType.DATE)
    private Date sRFromDt;
    @Column(name = "s_r_to_dt")
    @Temporal(TemporalType.DATE)
    private Date sRToDt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 510)
    @Column(name = "s_r_title")
    private String sRTitle;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "s_r_price")
    private BigDecimal sRPrice;
    @Column(name = "s_r_conv_rate")
    private BigDecimal sRConvRate;
    @Column(name = "s_r_discount")
    private BigDecimal sRDiscount;
    @Column(name = "s_r_post_amt")
    private BigDecimal sRPostAmt;
    @Column(name = "s_r_forward_amt")
    private BigDecimal sRForwardAmt;
    @Column(name = "s_r_appr_copy")
    private Integer sRApprCopy;
    @Column(name = "s_r_appr_dt")
    @Temporal(TemporalType.DATE)
    private Date sRApprDt;
    @Size(max = 200)
    @Column(name = "s_r_appr_by")
    private String sRApprBy;
    @Size(max = 510)
    @Column(name = "s_r_remark")
    private String sRRemark;
    @Size(max = 200)
    @Column(name = "s_r_pub_country")
    private String sRPubCountry;
    @Size(max = 200)
    @Column(name = "s_r_pub_city")
    private String sRPubCity;
    @Size(max = 200)
    @Column(name = "s_r_sup_country")
    private String sRSupCountry;
    @Size(max = 200)
    @Column(name = "s_r_sup_city")
    private String sRSupCity;
    @Column(name = "s_r_req_copy")
    private Integer sRReqCopy;
    @Column(name = "s_r_net_amount")
    private BigDecimal sRNetAmount;
    @Size(max = 100)
    @Column(name = "s_r_issn")
    private String sRIssn;
    @Size(max = 60)
    @Column(name = "s_r_refno")
    private String sRRefno;
    @Size(max = 100)
    @Column(name = "s_r_recid")
    private String sRRecid;
    @JoinColumn(name = "s_r_sub_cd", referencedColumnName = "bk_classno")
    @ManyToOne
    private BkSubject sRSubCd;
    @JoinColumn(name = "s_r_phycd", referencedColumnName = "Code")
    @ManyToOne
    private Libmaterials sRPhycd;
    @JoinColumn(name = "s_r_currency_cd", referencedColumnName = "a_c_cd")
    @ManyToOne
    private ACurrency sRCurrencyCd;
    @JoinColumn(name = "s_r_budget_code", referencedColumnName = "a_b_budget_code")
    @ManyToOne
    private ABudget sRBudgetCode;
    @JoinColumn(name = "s_r_budget_cd", referencedColumnName = "a_b_budget_code")
    @ManyToOne
    private ABudget sRBudgetCd;
    @JoinColumn(name = "s_r_sup_cd", referencedColumnName = "a_s_cd")
    @ManyToOne
    private SSupplierDetail sRSupCd;
    @JoinColumn(name = "s_r_pub_cd", referencedColumnName = "a_s_cd")
    @ManyToOne
    private SSupplierDetail sRPubCd;
    @JoinColumn(name = "s_r_nm", referencedColumnName = "mem_cd")
    @ManyToOne
    private MMember sRNm;
    @JoinColumn(name = "s_r_dept_cd", referencedColumnName = "Fclty_dept_cd")
    @ManyToOne
    private MFcltydept sRDeptCd;
    @JoinColumn(name = "s_r_freq_cd", referencedColumnName = "s_f_cd")
    @ManyToOne
    private SFrequency sRFreqCd;
    @JoinColumn(name = "s_r_edition", referencedColumnName = "s_ed_type")
    @ManyToOne
    private SEdition sREdition;

    public SRequest() {
    }

    public SRequest(Integer sRNo) {
        this.sRNo = sRNo;
    }

    public SRequest(Integer sRNo, String sRTitle) {
        this.sRNo = sRNo;
        this.sRTitle = sRTitle;
    }

    public Integer getSRNo() {
        return sRNo;
    }

    public void setSRNo(Integer sRNo) {
        this.sRNo = sRNo;
    }

    public Date getSRDate() {
        return sRDate;
    }

    public void setSRDate(Date sRDate) {
        this.sRDate = sRDate;
    }

    public String getSRStatus() {
        return sRStatus;
    }

    public void setSRStatus(String sRStatus) {
        this.sRStatus = sRStatus;
    }

    public Date getSRFromDt() {
        return sRFromDt;
    }

    public void setSRFromDt(Date sRFromDt) {
        this.sRFromDt = sRFromDt;
    }

    public Date getSRToDt() {
        return sRToDt;
    }

    public void setSRToDt(Date sRToDt) {
        this.sRToDt = sRToDt;
    }

    public String getSRTitle() {
        return sRTitle;
    }

    public void setSRTitle(String sRTitle) {
        this.sRTitle = sRTitle;
    }

    public BigDecimal getSRPrice() {
        return sRPrice;
    }

    public void setSRPrice(BigDecimal sRPrice) {
        this.sRPrice = sRPrice;
    }

    public BigDecimal getSRConvRate() {
        return sRConvRate;
    }

    public void setSRConvRate(BigDecimal sRConvRate) {
        this.sRConvRate = sRConvRate;
    }

    public BigDecimal getSRDiscount() {
        return sRDiscount;
    }

    public void setSRDiscount(BigDecimal sRDiscount) {
        this.sRDiscount = sRDiscount;
    }

    public BigDecimal getSRPostAmt() {
        return sRPostAmt;
    }

    public void setSRPostAmt(BigDecimal sRPostAmt) {
        this.sRPostAmt = sRPostAmt;
    }

    public BigDecimal getSRForwardAmt() {
        return sRForwardAmt;
    }

    public void setSRForwardAmt(BigDecimal sRForwardAmt) {
        this.sRForwardAmt = sRForwardAmt;
    }

    public Integer getSRApprCopy() {
        return sRApprCopy;
    }

    public void setSRApprCopy(Integer sRApprCopy) {
        this.sRApprCopy = sRApprCopy;
    }

    public Date getSRApprDt() {
        return sRApprDt;
    }

    public void setSRApprDt(Date sRApprDt) {
        this.sRApprDt = sRApprDt;
    }

    public String getSRApprBy() {
        return sRApprBy;
    }

    public void setSRApprBy(String sRApprBy) {
        this.sRApprBy = sRApprBy;
    }

    public String getSRRemark() {
        return sRRemark;
    }

    public void setSRRemark(String sRRemark) {
        this.sRRemark = sRRemark;
    }

    public String getSRPubCountry() {
        return sRPubCountry;
    }

    public void setSRPubCountry(String sRPubCountry) {
        this.sRPubCountry = sRPubCountry;
    }

    public String getSRPubCity() {
        return sRPubCity;
    }

    public void setSRPubCity(String sRPubCity) {
        this.sRPubCity = sRPubCity;
    }

    public String getSRSupCountry() {
        return sRSupCountry;
    }

    public void setSRSupCountry(String sRSupCountry) {
        this.sRSupCountry = sRSupCountry;
    }

    public String getSRSupCity() {
        return sRSupCity;
    }

    public void setSRSupCity(String sRSupCity) {
        this.sRSupCity = sRSupCity;
    }

    public Integer getSRReqCopy() {
        return sRReqCopy;
    }

    public void setSRReqCopy(Integer sRReqCopy) {
        this.sRReqCopy = sRReqCopy;
    }

    public BigDecimal getSRNetAmount() {
        return sRNetAmount;
    }

    public void setSRNetAmount(BigDecimal sRNetAmount) {
        this.sRNetAmount = sRNetAmount;
    }

    public String getSRIssn() {
        return sRIssn;
    }

    public void setSRIssn(String sRIssn) {
        this.sRIssn = sRIssn;
    }

    public String getSRRefno() {
        return sRRefno;
    }

    public void setSRRefno(String sRRefno) {
        this.sRRefno = sRRefno;
    }

    public String getSRRecid() {
        return sRRecid;
    }

    public void setSRRecid(String sRRecid) {
        this.sRRecid = sRRecid;
    }

    public BkSubject getSRSubCd() {
        return sRSubCd;
    }

    public void setSRSubCd(BkSubject sRSubCd) {
        this.sRSubCd = sRSubCd;
    }

    public Libmaterials getSRPhycd() {
        return sRPhycd;
    }

    public void setSRPhycd(Libmaterials sRPhycd) {
        this.sRPhycd = sRPhycd;
    }

    public ACurrency getSRCurrencyCd() {
        return sRCurrencyCd;
    }

    public void setSRCurrencyCd(ACurrency sRCurrencyCd) {
        this.sRCurrencyCd = sRCurrencyCd;
    }

    public ABudget getSRBudgetCode() {
        return sRBudgetCode;
    }

    public void setSRBudgetCode(ABudget sRBudgetCode) {
        this.sRBudgetCode = sRBudgetCode;
    }

    public ABudget getSRBudgetCd() {
        return sRBudgetCd;
    }

    public void setSRBudgetCd(ABudget sRBudgetCd) {
        this.sRBudgetCd = sRBudgetCd;
    }

    public SSupplierDetail getSRSupCd() {
        return sRSupCd;
    }

    public void setSRSupCd(SSupplierDetail sRSupCd) {
        this.sRSupCd = sRSupCd;
    }

    public SSupplierDetail getSRPubCd() {
        return sRPubCd;
    }

    public void setSRPubCd(SSupplierDetail sRPubCd) {
        this.sRPubCd = sRPubCd;
    }

    public MMember getSRNm() {
        return sRNm;
    }

    public void setSRNm(MMember sRNm) {
        this.sRNm = sRNm;
    }

    public MFcltydept getSRDeptCd() {
        return sRDeptCd;
    }

    public void setSRDeptCd(MFcltydept sRDeptCd) {
        this.sRDeptCd = sRDeptCd;
    }

    public SFrequency getSRFreqCd() {
        return sRFreqCd;
    }

    public void setSRFreqCd(SFrequency sRFreqCd) {
        this.sRFreqCd = sRFreqCd;
    }

    public SEdition getSREdition() {
        return sREdition;
    }

    public void setSREdition(SEdition sREdition) {
        this.sREdition = sREdition;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sRNo != null ? sRNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SRequest)) {
            return false;
        }
        SRequest other = (SRequest) object;
        if ((this.sRNo == null && other.sRNo != null) || (this.sRNo != null && !this.sRNo.equals(other.sRNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serialControl.SRequest[ sRNo=" + sRNo + " ]";
    }

    @XmlTransient
    public Collection<SMst> getSMstCollection() {
        return sMstCollection;
    }

    public void setSMstCollection(Collection<SMst> sMstCollection) {
        this.sMstCollection = sMstCollection;
    }
    
}
