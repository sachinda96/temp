/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.acquisition.suggestions;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import soul.circulation.MMember;
import soul.general_master.ABudget;
import soul.general_master.ACurrency;
import soul.general_master.ASupplierDetail;
import soul.general_master.Libmaterials;
import soul.general_master.MFcltydept;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "a_request")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ARequest.findAll", query = "SELECT a FROM ARequest a"),
    @NamedQuery(name = "ARequest.findByARNo", query = "SELECT a FROM ARequest a WHERE a.aRNo = :aRNo"),
    @NamedQuery(name = "ARequest.findByARDate", query = "SELECT a FROM ARequest a WHERE a.aRDate = :aRDate"),
    @NamedQuery(name = "ARequest.findByARTitle", query = "SELECT a FROM ARequest a WHERE a.aRTitle = :aRTitle"),
    @NamedQuery(name = "ARequest.findByARAuthor", query = "SELECT a FROM ARequest a WHERE a.aRAuthor = :aRAuthor"),
    @NamedQuery(name = "ARequest.findByARPrice", query = "SELECT a FROM ARequest a WHERE a.aRPrice = :aRPrice"),
    @NamedQuery(name = "ARequest.findByARConvRate", query = "SELECT a FROM ARequest a WHERE a.aRConvRate = :aRConvRate"),
    @NamedQuery(name = "ARequest.findByARDiscount", query = "SELECT a FROM ARequest a WHERE a.aRDiscount = :aRDiscount"),
    @NamedQuery(name = "ARequest.findByARPostAmt", query = "SELECT a FROM ARequest a WHERE a.aRPostAmt = :aRPostAmt"),
    @NamedQuery(name = "ARequest.findByARForwardAmt", query = "SELECT a FROM ARequest a WHERE a.aRForwardAmt = :aRForwardAmt"),
    @NamedQuery(name = "ARequest.findByARApprCopy", query = "SELECT a FROM ARequest a WHERE a.aRApprCopy = :aRApprCopy"),
    @NamedQuery(name = "ARequest.findByARApprDt", query = "SELECT a FROM ARequest a WHERE a.aRApprDt = :aRApprDt"),
    @NamedQuery(name = "ARequest.findByARApprBy", query = "SELECT a FROM ARequest a WHERE a.aRApprBy = :aRApprBy"),
    @NamedQuery(name = "ARequest.findByARApprRemark", query = "SELECT a FROM ARequest a WHERE a.aRApprRemark = :aRApprRemark"),
    @NamedQuery(name = "ARequest.findByARPoDt", query = "SELECT a FROM ARequest a WHERE a.aRPoDt = :aRPoDt"),
    @NamedQuery(name = "ARequest.findByAREdition", query = "SELECT a FROM ARequest a WHERE a.aREdition = :aREdition"),
    @NamedQuery(name = "ARequest.findByARYear", query = "SELECT a FROM ARequest a WHERE a.aRYear = :aRYear"),
    @NamedQuery(name = "ARequest.findByARRequestCopy", query = "SELECT a FROM ARequest a WHERE a.aRRequestCopy = :aRRequestCopy"),
    @NamedQuery(name = "ARequest.findByARRemark", query = "SELECT a FROM ARequest a WHERE a.aRRemark = :aRRemark"),
    @NamedQuery(name = "ARequest.findByARIsbn", query = "SELECT a FROM ARequest a WHERE a.aRIsbn = :aRIsbn"),
    @NamedQuery(name = "ARequest.findByARFname", query = "SELECT a FROM ARequest a WHERE a.aRFname = :aRFname"),
    @NamedQuery(name = "ARequest.findByARBiblevel", query = "SELECT a FROM ARequest a WHERE a.aRBiblevel = :aRBiblevel"),
    @NamedQuery(name = "ARequest.findByARCopytobeorder", query = "SELECT a FROM ARequest a WHERE a.aRCopytobeorder = :aRCopytobeorder"),
    @NamedQuery(name = "ARequest.findByARBinding", query = "SELECT a FROM ARequest a WHERE a.aRBinding = :aRBinding"),
    @NamedQuery(name = "ARequest.findByARVolpartno", query = "SELECT a FROM ARequest a WHERE a.aRVolpartno = :aRVolpartno"),
    @NamedQuery(name = "ARequest.findByARNoofvolpart", query = "SELECT a FROM ARequest a WHERE a.aRNoofvolpart = :aRNoofvolpart"),
    @NamedQuery(name = "ARequest.findByARStanding", query = "SELECT a FROM ARequest a WHERE a.aRStanding = :aRStanding"),
    @NamedQuery(name = "ARequest.findByAROnapprDt", query = "SELECT a FROM ARequest a WHERE a.aROnapprDt = :aROnapprDt"),
    @NamedQuery(name = "ARequest.findByARRejDt", query = "SELECT a FROM ARequest a WHERE a.aRRejDt = :aRRejDt"),
    @NamedQuery(name = "ARequest.findByARStatusStr", query = "SELECT a FROM ARequest a WHERE a.aRStatusStr = :aRStatusStr"),
    @NamedQuery(name = "ARequest.findByARBoolean", query = "SELECT a FROM ARequest a WHERE a.aRBoolean = :aRBoolean"),
    @NamedQuery(name = "ARequest.findByARRefNo", query = "SELECT a FROM ARequest a WHERE a.aRRefNo = :aRRefNo"),
    @NamedQuery(name = "ARequest.findByARGiftedBy", query = "SELECT a FROM ARequest a WHERE a.aRGiftedBy = :aRGiftedBy"),
    
    @NamedQuery(name = "ARequest.findByARStatus", query = "SELECT a FROM ARequest a WHERE a.aRStatus IN :aRStatus"),
    @NamedQuery(name = "ARequest.findApprovedReqByDept", query = "SELECT a FROM ARequest a WHERE a.aRStatus IN ('A','J') AND a.aRDeptCd.fcltydeptcd = :fcltydeptcd"),
    @NamedQuery(name = "ARequest.findApprovedReqBySupplier", query = "SELECT a FROM ARequest a WHERE a.aRStatus IN ('A','J') AND a.aRSupCd.aSCd = :aSCd"),
    @NamedQuery(name = "ARequest.findApprovedReqByBudget", query = "SELECT a FROM ARequest a WHERE a.aRStatus IN ('A','J') AND a.aRBudgetCd.aBBudgetCode = :aBBudgetCode "),

    @NamedQuery(name = "ARequest.findForApprovalReqByDept", query = "SELECT a FROM ARequest a WHERE a.aRStatus IN ('B','O') AND a.aRDeptCd.fcltydeptcd = :fcltydeptcd"),
    @NamedQuery(name = "ARequest.findForApprovalReqByBudget", query = "SELECT a FROM ARequest a WHERE a.aRStatus IN ('B','O') AND a.aRBudgetCd.aBBudgetCode = :aBBudgetCode "),
    
    @NamedQuery(name = "ARequest.findNotApproved", query = "SELECT a FROM ARequest a WHERE a.aRStatus IN :aRStatus OR (a.aRApprDt IS NULL AND a.aRStatus = 'G')"),
    @NamedQuery(name = "ARequest.findRequestedOnApproval", query = "SELECT a FROM ARequest a WHERE a.aRStatus = 'R' OR a.aRStatus = 'O'"),  //'R' for 'requested' and 'O' for approval
    @NamedQuery(name = "ARequest.findByMemberName", query = "SELECT a FROM ARequest a WHERE a.aRNm = (SELECT m.memCd FROM MMember m WHERE m.memFirstnm = ?1 AND m.memLstnm = ?2) ") ,
    @NamedQuery(name = "ARequest.findByDeptName", query = "SELECT a FROM ARequest a WHERE a.aRDeptCd = (SELECT m.fcltydeptcd FROM MFcltydept m WHERE m.fcltydeptdscr = :fcltydeptdscr)") ,
    @NamedQuery(name = "ARequest.findByBudget", query = "SELECT a FROM ARequest a WHERE a.aRBudgetCd = (SELECT m.aBBudgetCode  FROM ABudget m   WHERE m.budgetHead = (Select b.budgetCode from BHeads b where b.shortDesc = :shortDesc))") ,
    @NamedQuery(name = "ARequest.findRejectedRequestByDate", query = "SELECT a FROM ARequest a WHERE a.aRStatus = 'E' AND a.aRDate < :aRDate") ,
    @NamedQuery(name = "ARequest.findByMemberCode", query = "SELECT a FROM ARequest a WHERE a.aRNm.memCd = :memCd "),
    @NamedQuery(name = "ARequest.findBySupplierCd", query = "SELECT a FROM ARequest a WHERE a.aRSupCd.aSCd = :aSCd "),
    @NamedQuery(name = "ARequest.findByPublisherCd", query = "SELECT a FROM ARequest a WHERE a.aRPubCd.aSCd = :aSCd "),
    @NamedQuery(name = "ARequest.findByCurrencyCd", query = "SELECT a FROM ARequest a WHERE a.aRCurrencyCd.aCCd = :aCCd ")
    
})

public class ARequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "a_r_no")
    private Integer aRNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "a_r_date")
    @Temporal(TemporalType.DATE)
    private Date aRDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "a_r_status")
    private String aRStatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 510)
    @Column(name = "a_r_title")
    private String aRTitle;
    @Size(max = 200)
    @Column(name = "a_r_author")
    private String aRAuthor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "a_r_price")
    private BigDecimal aRPrice;
    @Column(name = "a_r_conv_rate")
    private BigDecimal aRConvRate;
    @Column(name = "a_r_discount")
    private BigDecimal aRDiscount;
    @Column(name = "a_r_post_amt")
    private BigDecimal aRPostAmt;
    @Column(name = "a_r_forward_amt")
    private BigDecimal aRForwardAmt;
    @Column(name = "a_r_appr_copy")
    private Short aRApprCopy;
    @Column(name = "a_r_appr_dt")
    @Temporal(TemporalType.DATE)
    private Date aRApprDt;
    @Size(max = 150)
    @Column(name = "a_r_appr_by")
    private String aRApprBy;
    @Size(max = 500)
    @Column(name = "a_r_appr_remark")
    private String aRApprRemark;
    @Column(name = "a_r_po_dt")
    @Temporal(TemporalType.DATE)
    private Date aRPoDt;
    @Size(max = 100)
    @Column(name = "a_r_edition")
    private String aREdition;
    @Size(max = 100)
    @Column(name = "a_r_year")
    private String aRYear;
    @Column(name = "a_r_request_copy")
    private Short aRRequestCopy;
    @Size(max = 500)
    @Column(name = "a_r_remark")
    private String aRRemark;
    @Size(max = 100)
    @Column(name = "a_r_isbn")
    private String aRIsbn;
    @Size(max = 200)
    @Column(name = "a_r_fname")
    private String aRFname;
    @Size(max = 100)
    @Column(name = "a_r_biblevel")
    private String aRBiblevel;
    @Column(name = "a_r_copytobeorder")
    private Long aRCopytobeorder;
    @Size(max = 100)
    @Column(name = "a_r_binding")
    private String aRBinding;
    @Size(max = 100)
    @Column(name = "a_r_volpartno")
    private String aRVolpartno;
    @Size(max = 100)
    @Column(name = "a_r_noofvolpart")
    private String aRNoofvolpart;
    @Size(max = 100)
    @Column(name = "a_r_standing")
    private String aRStanding;
    @Column(name = "a_r_onappr_dt")
    @Temporal(TemporalType.DATE)
    private Date aROnapprDt;
    @Column(name = "a_r_rej_dt")
    @Temporal(TemporalType.DATE)
    private Date aRRejDt;
    @Size(max = 100)
    @Column(name = "a_r_status_str")
    private String aRStatusStr;
    @Size(max = 100)
    @Column(name = "a_r_boolean")
    private String aRBoolean;
    @Size(max = 100)
    @Column(name = "a_r_ref_no")
    private String aRRefNo;
    @Size(max = 200)
    @Column(name = "a_r_gifted_by")
    private String aRGiftedBy;
    @JoinColumn(name = "a_r_dept_cd", referencedColumnName = "Fclty_dept_cd")
    @ManyToOne
    private MFcltydept aRDeptCd;
    @JoinColumn(name = "a_r_nm", referencedColumnName = "mem_cd")
    @ManyToOne
    private MMember aRNm;
    @JoinColumn(name = "a_r_phycd", referencedColumnName = "Code")
    @ManyToOne
    private Libmaterials aRPhycd;
    @JoinColumn(name = "a_r_budget_cd", referencedColumnName = "a_b_budget_code")
    @ManyToOne
    private ABudget aRBudgetCd;
    @JoinColumn(name = "a_r_pub_cd", referencedColumnName = "a_s_cd")
    @ManyToOne
    private ASupplierDetail aRPubCd;
    @JoinColumn(name = "a_r_budget_code", referencedColumnName = "a_b_budget_code")
    @ManyToOne
    private ABudget aRBudgetCode;
    @JoinColumn(name = "a_r_sup_cd", referencedColumnName = "a_s_cd")
    @ManyToOne
    private ASupplierDetail aRSupCd;
    @JoinColumn(name = "a_r_currency_cd", referencedColumnName = "a_c_cd")
    @ManyToOne
    private ACurrency aRCurrencyCd;
    @JoinColumn(name = "a_r_po_no", referencedColumnName = "a_o_po_no")
    @ManyToOne
    private AOrdermaster aRPoNo;

    public ARequest() {
    }

    public ARequest(Integer aRNo) {
        this.aRNo = aRNo;
    }

    public ARequest(Integer aRNo, Date aRDate, String aRStatus, String aRTitle) {
        this.aRNo = aRNo;
        this.aRDate = aRDate;
        this.aRStatus = aRStatus;
        this.aRTitle = aRTitle;
    }

    public Integer getARNo() {
        return aRNo;
    }

    public void setARNo(Integer aRNo) {
        this.aRNo = aRNo;
    }

    public Date getARDate() {
        return aRDate;
    }

    public void setARDate(Date aRDate) {
        this.aRDate = aRDate;
    }

    public String getARStatus() {
        return aRStatus;
    }

    public void setARStatus(String aRStatus) {
        this.aRStatus = aRStatus;
    }

    public String getARTitle() {
        return aRTitle;
    }

    public void setARTitle(String aRTitle) {
        this.aRTitle = aRTitle;
    }

    public String getARAuthor() {
        return aRAuthor;
    }

    public void setARAuthor(String aRAuthor) {
        this.aRAuthor = aRAuthor;
    }

    public BigDecimal getARPrice() {
        return aRPrice;
    }

    public void setARPrice(BigDecimal aRPrice) {
        this.aRPrice = aRPrice;
    }

    public BigDecimal getARConvRate() {
        return aRConvRate;
    }

    public void setARConvRate(BigDecimal aRConvRate) {
        this.aRConvRate = aRConvRate;
    }

    public BigDecimal getARDiscount() {
        return aRDiscount;
    }

    public void setARDiscount(BigDecimal aRDiscount) {
        this.aRDiscount = aRDiscount;
    }

    public BigDecimal getARPostAmt() {
        return aRPostAmt;
    }

    public void setARPostAmt(BigDecimal aRPostAmt) {
        this.aRPostAmt = aRPostAmt;
    }

    public BigDecimal getARForwardAmt() {
        return aRForwardAmt;
    }

    public void setARForwardAmt(BigDecimal aRForwardAmt) {
        this.aRForwardAmt = aRForwardAmt;
    }

    public Short getARApprCopy() {
        return aRApprCopy;
    }

    public void setARApprCopy(Short aRApprCopy) {
        this.aRApprCopy = aRApprCopy;
    }

    public Date getARApprDt() {
        return aRApprDt;
    }

    public void setARApprDt(Date aRApprDt) {
        this.aRApprDt = aRApprDt;
    }

    public String getARApprBy() {
        return aRApprBy;
    }

    public void setARApprBy(String aRApprBy) {
        this.aRApprBy = aRApprBy;
    }

    public String getARApprRemark() {
        return aRApprRemark;
    }

    public void setARApprRemark(String aRApprRemark) {
        this.aRApprRemark = aRApprRemark;
    }

    public Date getARPoDt() {
        return aRPoDt;
    }

    public void setARPoDt(Date aRPoDt) {
        this.aRPoDt = aRPoDt;
    }

    public String getAREdition() {
        return aREdition;
    }

    public void setAREdition(String aREdition) {
        this.aREdition = aREdition;
    }

    public String getARYear() {
        return aRYear;
    }

    public void setARYear(String aRYear) {
        this.aRYear = aRYear;
    }

    public Short getARRequestCopy() {
        return aRRequestCopy;
    }

    public void setARRequestCopy(Short aRRequestCopy) {
        this.aRRequestCopy = aRRequestCopy;
    }

    public String getARRemark() {
        return aRRemark;
    }

    public void setARRemark(String aRRemark) {
        this.aRRemark = aRRemark;
    }

    public String getARIsbn() {
        return aRIsbn;
    }

    public void setARIsbn(String aRIsbn) {
        this.aRIsbn = aRIsbn;
    }

    public String getARFname() {
        return aRFname;
    }

    public void setARFname(String aRFname) {
        this.aRFname = aRFname;
    }

    public String getARBiblevel() {
        return aRBiblevel;
    }

    public void setARBiblevel(String aRBiblevel) {
        this.aRBiblevel = aRBiblevel;
    }

    public Long getARCopytobeorder() {
        return aRCopytobeorder;
    }

    public void setARCopytobeorder(Long aRCopytobeorder) {
        this.aRCopytobeorder = aRCopytobeorder;
    }

    public String getARBinding() {
        return aRBinding;
    }

    public void setARBinding(String aRBinding) {
        this.aRBinding = aRBinding;
    }

    public String getARVolpartno() {
        return aRVolpartno;
    }

    public void setARVolpartno(String aRVolpartno) {
        this.aRVolpartno = aRVolpartno;
    }

    public String getARNoofvolpart() {
        return aRNoofvolpart;
    }

    public void setARNoofvolpart(String aRNoofvolpart) {
        this.aRNoofvolpart = aRNoofvolpart;
    }

    public String getARStanding() {
        return aRStanding;
    }

    public void setARStanding(String aRStanding) {
        this.aRStanding = aRStanding;
    }

    public Date getAROnapprDt() {
        return aROnapprDt;
    }

    public void setAROnapprDt(Date aROnapprDt) {
        this.aROnapprDt = aROnapprDt;
    }

    public Date getARRejDt() {
        return aRRejDt;
    }

    public void setARRejDt(Date aRRejDt) {
        this.aRRejDt = aRRejDt;
    }

    public String getARStatusStr() {
        return aRStatusStr;
    }

    public void setARStatusStr(String aRStatusStr) {
        this.aRStatusStr = aRStatusStr;
    }

    public String getARBoolean() {
        return aRBoolean;
    }

    public void setARBoolean(String aRBoolean) {
        this.aRBoolean = aRBoolean;
    }

    public String getARRefNo() {
        return aRRefNo;
    }

    public void setARRefNo(String aRRefNo) {
        this.aRRefNo = aRRefNo;
    }

    public String getARGiftedBy() {
        return aRGiftedBy;
    }

    public void setARGiftedBy(String aRGiftedBy) {
        this.aRGiftedBy = aRGiftedBy;
    }

    public MFcltydept getARDeptCd() {
        return aRDeptCd;
    }

    public void setARDeptCd(MFcltydept aRDeptCd) {
        this.aRDeptCd = aRDeptCd;
    }

    public MMember getARNm() {
        return aRNm;
    }

    public void setARNm(MMember aRNm) {
        this.aRNm = aRNm;
    }

    public Libmaterials getARPhycd() {
        return aRPhycd;
    }

    public void setARPhycd(Libmaterials aRPhycd) {
        this.aRPhycd = aRPhycd;
    }

    public ABudget getARBudgetCd() {
        return aRBudgetCd;
    }

    public void setARBudgetCd(ABudget aRBudgetCd) {
        this.aRBudgetCd = aRBudgetCd;
    }

    public ASupplierDetail getARPubCd() {
        return aRPubCd;
    }

    public void setARPubCd(ASupplierDetail aRPubCd) {
        this.aRPubCd = aRPubCd;
    }

    public ABudget getARBudgetCode() {
        return aRBudgetCode;
    }

    public void setARBudgetCode(ABudget aRBudgetCode) {
        this.aRBudgetCode = aRBudgetCode;
    }

    public ASupplierDetail getARSupCd() {
        return aRSupCd;
    }

    public void setARSupCd(ASupplierDetail aRSupCd) {
        this.aRSupCd = aRSupCd;
    }

    public ACurrency getARCurrencyCd() {
        return aRCurrencyCd;
    }

    public void setARCurrencyCd(ACurrency aRCurrencyCd) {
        this.aRCurrencyCd = aRCurrencyCd;
    }

    public AOrdermaster getARPoNo() {
        return aRPoNo;
    }

    public void setARPoNo(AOrdermaster aRPoNo) {
        this.aRPoNo = aRPoNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aRNo != null ? aRNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ARequest)) {
            return false;
        }
        ARequest other = (ARequest) object;
        if ((this.aRNo == null && other.aRNo != null) || (this.aRNo != null && !this.aRNo.equals(other.aRNo))) {
            return false;
        }
        return true;
    }
    //Added Manually
    @Override
    public String toString() {
        return "soul.acquisition.suggestions.ARequest[ aRNo=" + aRNo + " ]";
    }
    
}
