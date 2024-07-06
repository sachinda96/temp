/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "s_mst")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SMst.findAll", query = "SELECT s FROM SMst s"),
    @NamedQuery(name = "SMst.findBySRecid", query = "SELECT s FROM SMst s WHERE s.sRecid = :sRecid"),
    @NamedQuery(name = "SMst.findBySTyAcqu", query = "SELECT s FROM SMst s WHERE s.sTyAcqu = :sTyAcqu"),
    @NamedQuery(name = "SMst.findBySBound", query = "SELECT s FROM SMst s WHERE s.sBound = :sBound"),
    @NamedQuery(name = "SMst.findBySTitle1", query = "SELECT s FROM SMst s WHERE s.sTitle = :sTitle"),
    @NamedQuery(name = "SMst.findBySTitle", query = "SELECT s FROM SMst s WHERE s.sTitle LIKE :sTitle group by s.sRecid"),
    @NamedQuery(name = "SMst.findBySAbrTitle", query = "SELECT s FROM SMst s WHERE s.sAbrTitle = :sAbrTitle"),
    @NamedQuery(name = "SMst.findBySLanCd", query = "SELECT s FROM SMst s WHERE s.sLanCd = :sLanCd"),
    @NamedQuery(name = "SMst.findBySIssn", query = "SELECT s FROM SMst s WHERE s.sIssn = :sIssn"),
    @NamedQuery(name = "SMst.findBySCoden", query = "SELECT s FROM SMst s WHERE s.sCoden = :sCoden"),
    @NamedQuery(name = "SMst.findBySClassCd", query = "SELECT s FROM SMst s WHERE s.sClassCd = :sClassCd"),
    @NamedQuery(name = "SMst.findBySSubCd", query = "SELECT s FROM SMst s WHERE s.sSubCd = :sSubCd"),
    @NamedQuery(name = "SMst.findBySPubCd", query = "SELECT s FROM SMst s WHERE s.sPubCd = :sPubCd"),
    @NamedQuery(name = "SMst.getSPubCd", query = "SELECT s FROM SMst s GROUP BY s.sPubCd"),
    @NamedQuery(name = "SMst.findBySSupCd", query = "SELECT s FROM SMst s WHERE s.sSupCd = :sSupCd"),
    @NamedQuery(name = "SMst.findBySDeptCd", query = "SELECT s FROM SMst s WHERE s.sDeptCd = :sDeptCd"),
    @NamedQuery(name = "SMst.findBySLocation", query = "SELECT s FROM SMst s WHERE s.sLocation = :sLocation"),
    @NamedQuery(name = "SMst.findBySAnnualIdx", query = "SELECT s FROM SMst s WHERE s.sAnnualIdx = :sAnnualIdx"),
    @NamedQuery(name = "SMst.findBySStDt", query = "SELECT s FROM SMst s WHERE s.sStDt = :sStDt"),
    @NamedQuery(name = "SMst.findBySStVol", query = "SELECT s FROM SMst s WHERE s.sStVol = :sStVol"),
    @NamedQuery(name = "SMst.findBySStIss", query = "SELECT s FROM SMst s WHERE s.sStIss = :sStIss"),
    @NamedQuery(name = "SMst.findBySEnDt", query = "SELECT s FROM SMst s WHERE s.sEnDt = :sEnDt"),
    @NamedQuery(name = "SMst.findBySEnVol", query = "SELECT s FROM SMst s WHERE s.sEnVol = :sEnVol"),
    @NamedQuery(name = "SMst.findBySEnIss", query = "SELECT s FROM SMst s WHERE s.sEnIss = :sEnIss"),
    @NamedQuery(name = "SMst.findBySIssPerVol", query = "SELECT s FROM SMst s WHERE s.sIssPerVol = :sIssPerVol"),
    @NamedQuery(name = "SMst.findBySFreqCd", query = "SELECT s FROM SMst s WHERE s.sFreqCd = :sFreqCd"),
    @NamedQuery(name = "SMst.findBySLeadtime", query = "SELECT s FROM SMst s WHERE s.sLeadtime = :sLeadtime"),
    @NamedQuery(name = "SMst.findBySMode", query = "SELECT s FROM SMst s WHERE s.sMode = :sMode"),
    @NamedQuery(name = "SMst.findBySBugetCd", query = "SELECT s FROM SMst s WHERE s.sBugetCd = :sBugetCd"),
    @NamedQuery(name = "SMst.findBySPrice", query = "SELECT s FROM SMst s WHERE s.sPrice = :sPrice"),
    @NamedQuery(name = "SMst.findBySCurrency", query = "SELECT s FROM SMst s WHERE s.sCurrency = :sCurrency"),
    @NamedQuery(name = "SMst.findBySConvRate", query = "SELECT s FROM SMst s WHERE s.sConvRate = :sConvRate"),
    @NamedQuery(name = "SMst.findBySCountry", query = "SELECT s FROM SMst s WHERE s.sCountry = :sCountry"),
    @NamedQuery(name = "SMst.findBySFirstYear", query = "SELECT s FROM SMst s WHERE s.sFirstYear = :sFirstYear"),
    @NamedQuery(name = "SMst.findBySPubCity", query = "SELECT s FROM SMst s WHERE s.sPubCity = :sPubCity"),
    @NamedQuery(name = "SMst.findBySPubCountry", query = "SELECT s FROM SMst s WHERE s.sPubCountry = :sPubCountry"),
    @NamedQuery(name = "SMst.findBySSupCity", query = "SELECT s FROM SMst s WHERE s.sSupCity = :sSupCity"),
    @NamedQuery(name = "SMst.findBySSupCountry", query = "SELECT s FROM SMst s WHERE s.sSupCountry = :sSupCountry"),
    @NamedQuery(name = "SMst.findBySStatus", query = "SELECT s FROM SMst s WHERE s.sStatus = :sStatus"),
    @NamedQuery(name = "SMst.findBySDirect", query = "SELECT s FROM SMst s WHERE s.sDirect = :sDirect"),
    @NamedQuery(name = "SMst.findBySUrl", query = "SELECT s FROM SMst s WHERE s.sUrl = :sUrl"),
    @NamedQuery(name = "SMst.findBySUniversity", query = "SELECT s FROM SMst s WHERE s.sUniversity = :sUniversity"),
    @NamedQuery(name = "SMst.findBySInflibnet", query = "SELECT s FROM SMst s WHERE s.sInflibnet = :sInflibnet"),
    @NamedQuery(name = "SMst.findBySCode", query = "SELECT s FROM SMst s WHERE s.sCode = :sCode"),
    @NamedQuery(name = "SMst.findBySEdition", query = "SELECT s FROM SMst s WHERE s.sEdition = :sEdition"),
    @NamedQuery(name = "SMst.findBySReqCopy", query = "SELECT s FROM SMst s WHERE s.sReqCopy = :sReqCopy"),
    @NamedQuery(name = "SMst.findBySApprCopy", query = "SELECT s FROM SMst s WHERE s.sApprCopy = :sApprCopy"),
    @NamedQuery(name = "SMst.findBySOrderNo", query = "SELECT s FROM SMst s WHERE s.sOrderNo = :sOrderNo"),
    @NamedQuery(name = "SMst.findBySMstStatus", query = "SELECT s FROM SMst s WHERE s.sMstStatus = :sMstStatus"),
    @NamedQuery(name = "SMst.findBySInvNo", query = "SELECT s FROM SMst s WHERE s.sInvNo = :sInvNo"),
    @NamedQuery(name = "SMst.findBySCatRecid", query = "SELECT s FROM SMst s WHERE s.sCatRecid = :sCatRecid"),
    @NamedQuery(name = "SMst.findBySPriceperissue", query = "SELECT s FROM SMst s WHERE s.sPriceperissue = :sPriceperissue"),
    
    @NamedQuery(name = "SMst.findBySMstStatussAndTyAcq", query = "SELECT s FROM SMst s WHERE s.sMstStatus IN ?1 AND s.sTyAcqu = ?2"),
    @NamedQuery(name = "SMst.findForScheduleGeneration", query = "SELECT s FROM SMst s WHERE (s.sMstStatus IN ('O','I','P') AND s.sTyAcqu = 'S') OR s.sTyAcqu IN ('G', 'E')"),
    @NamedQuery(name = "SMst.findBySOrderNoANDSInvNo", query = "SELECT s FROM SMst s WHERE s.sOrderNo = ?1 AND s.sInvNo = ?2"),
    @NamedQuery(name = "SMst.findBySMstStatuss", query = "SELECT s FROM SMst s WHERE s.sMstStatus IN :sMstStatus"),
    @NamedQuery(name = "SMst.findOrdersBySupplierName", query = "SELECT s FROM SMst s WHERE (s.sMstStatus IN ('D','A','M') or s.sMstStatus = 'V') and s.sTyAcqu = 'S' and s.sSupCd = :sSupCd"),
    @NamedQuery(name = "SMst.findOrdersByPublisherName", query = "SELECT s FROM SMst s WHERE (s.sMstStatus IN ('D','A','M') or s.sMstStatus = 'V') and s.sTyAcqu = 'S' and s.sPubCd = :sPubCd"),
    
    @NamedQuery(name = "SMst.findByMaxRecId", query = "SELECT s FROM SMst s WHERE s.sRecid = ( SELECT MAX(s.sRecid) FROM SMst s WHERE s.sRecid LIKE :recId )"),
    @NamedQuery(name = "SMst.listofSerialsToGenerateNewSchedule", query = "SELECT s FROM SMst s WHERE (s.sMstStatus IN ('O','I','P') and s.sTyAcqu = 'S') or s.sTyAcqu = 'G' or s.sTyAcqu = 'E'"),
    @NamedQuery(name = "SMst.listofSerialsToGenerateNewScheduleByTitle", query = "SELECT s FROM SMst s WHERE (s.sMstStatus IN ('O','I','P') and s.sTitle like :sTitle and s.sTyAcqu = 'S') or s.sTyAcqu = 'G' or s.sTyAcqu = 'E'"),
    @NamedQuery(name = "SMst.ordersFOrInvoiceProcess", query = "SELECT s FROM SMst s WHERE s.sMstStatus IN ('O','S')"),
    
    @NamedQuery(name = "SMst.countByPubCode", query = "SELECT s FROM SMst s WHERE s.sPubCd = ?1 or s.sSupCd = ?2"),
})
public class SMst implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sMst")
    private Collection<SSchedule> sScheduleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sMst")
    private Collection<SSubdetail> sSubdetailCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_recid")
    private String sRecid;
    @Size(max = 2)
    @Column(name = "s_ty_acqu")
    private String sTyAcqu;
    @Size(max = 2)
    @Column(name = "s_bound")
    private String sBound;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 510)
    @Column(name = "s_title")
    private String sTitle;
    @Size(max = 200)
    @Column(name = "s_abr_title")
    private String sAbrTitle;
    @Size(max = 6)
    @Column(name = "s_lan_cd")
    private String sLanCd;
    @Size(max = 60)
    @Column(name = "s_issn")
    private String sIssn;
    @Size(max = 60)
    @Column(name = "s_coden")
    private String sCoden;
    @Size(max = 100)
    @Column(name = "s_class_cd")
    private String sClassCd;
    @Size(max = 100)
    @Column(name = "s_sub_cd")
    private String sSubCd;
    @Size(max = 20)
    @Column(name = "s_pub_cd")
    private String sPubCd;
    @Size(max = 20)
    @Column(name = "s_sup_cd")
    private String sSupCd;
    @Size(max = 4)
    @Column(name = "s_dept_cd")
    private String sDeptCd;
    @Size(max = 100)
    @Column(name = "s_location")
    private String sLocation;
    @Size(max = 2)
    @Column(name = "s_annual_idx")
    private String sAnnualIdx;
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
    @Column(name = "s_iss_per_vol")
    private Integer sIssPerVol;
    @Size(max = 6)
    @Column(name = "s_freq_cd")
    private String sFreqCd;
    @Column(name = "s_leadtime")
    private Integer sLeadtime;
    @Size(max = 2)
    @Column(name = "s_mode")
    private String sMode;
    @Size(max = 20)
    @Column(name = "s_buget_cd")
    private String sBugetCd;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "s_price")
    private BigDecimal sPrice;
    @Size(max = 8)
    @Column(name = "s_currency")
    private String sCurrency;
    @Column(name = "s_conv_rate")
    private BigDecimal sConvRate;
    @Size(max = 8)
    @Column(name = "s_country")
    private String sCountry;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "s_note")
    private String sNote;
    @Column(name = "s_first_year")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sFirstYear;
    @Size(max = 100)
    @Column(name = "s_pub_city")
    private String sPubCity;
    @Size(max = 20)
    @Column(name = "s_pub_country")
    private String sPubCountry;
    @Size(max = 100)
    @Column(name = "s_sup_city")
    private String sSupCity;
    @Size(max = 20)
    @Column(name = "s_sup_country")
    private String sSupCountry;
    @Size(max = 2)
    @Column(name = "s_status")
    private String sStatus;
    @Size(max = 2)
    @Column(name = "s_direct")
    private String sDirect;
    @Size(max = 510)
    @Column(name = "s_url")
    private String sUrl;
    @Size(max = 26)
    @Column(name = "s_university")
    private String sUniversity;
    @Size(max = 30)
    @Column(name = "s_inflibnet")
    private String sInflibnet;
    @Size(max = 20)
    @Column(name = "s_code")
    private String sCode;
    @Size(max = 70)
    @Column(name = "s_edition")
    private String sEdition;
    @Column(name = "s_req_copy")
    private Integer sReqCopy;
    @Column(name = "s_appr_copy")
    private Integer sApprCopy;
    @Size(max = 100)
    @Column(name = "s_order_no")
    private String sOrderNo;
    @Size(max = 100)
    @Column(name = "s_mst_status")
    private String sMstStatus;
    @Size(max = 100)
    @Column(name = "s_inv_no")
    private String sInvNo;
    @Size(max = 100)
    @Column(name = "s_cat_recid")
    private String sCatRecid;
    @Column(name = "s_priceperissue")
    private BigDecimal sPriceperissue;
    @JoinColumn(name = "s_req_no", referencedColumnName = "s_r_no")
    @ManyToOne
    private SRequest sReqNo;

    public SMst() {
    }

    public SMst(String sRecid) {
        this.sRecid = sRecid;
    }

    public SMst(String sRecid, String sTitle) {
        this.sRecid = sRecid;
        this.sTitle = sTitle;
    }

    public String getSRecid() {
        return sRecid;
    }

    public void setSRecid(String sRecid) {
        this.sRecid = sRecid;
    }

    public String getSTyAcqu() {
        return sTyAcqu;
    }

    public void setSTyAcqu(String sTyAcqu) {
        this.sTyAcqu = sTyAcqu;
    }

    public String getSBound() {
        return sBound;
    }

    public void setSBound(String sBound) {
        this.sBound = sBound;
    }

    public String getSTitle() {
        return sTitle;
    }

    public void setSTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getSAbrTitle() {
        return sAbrTitle;
    }

    public void setSAbrTitle(String sAbrTitle) {
        this.sAbrTitle = sAbrTitle;
    }

    public String getSLanCd() {
        return sLanCd;
    }

    public void setSLanCd(String sLanCd) {
        this.sLanCd = sLanCd;
    }

    public String getSIssn() {
        return sIssn;
    }

    public void setSIssn(String sIssn) {
        this.sIssn = sIssn;
    }

    public String getSCoden() {
        return sCoden;
    }

    public void setSCoden(String sCoden) {
        this.sCoden = sCoden;
    }

    public String getSClassCd() {
        return sClassCd;
    }

    public void setSClassCd(String sClassCd) {
        this.sClassCd = sClassCd;
    }

    public String getSSubCd() {
        return sSubCd;
    }

    public void setSSubCd(String sSubCd) {
        this.sSubCd = sSubCd;
    }

    public String getSPubCd() {
        return sPubCd;
    }

    public void setSPubCd(String sPubCd) {
        this.sPubCd = sPubCd;
    }

    public String getSSupCd() {
        return sSupCd;
    }

    public void setSSupCd(String sSupCd) {
        this.sSupCd = sSupCd;
    }

    public String getSDeptCd() {
        return sDeptCd;
    }

    public void setSDeptCd(String sDeptCd) {
        this.sDeptCd = sDeptCd;
    }

    public String getSLocation() {
        return sLocation;
    }

    public void setSLocation(String sLocation) {
        this.sLocation = sLocation;
    }

    public String getSAnnualIdx() {
        return sAnnualIdx;
    }

    public void setSAnnualIdx(String sAnnualIdx) {
        this.sAnnualIdx = sAnnualIdx;
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

    public Integer getSIssPerVol() {
        return sIssPerVol;
    }

    public void setSIssPerVol(Integer sIssPerVol) {
        this.sIssPerVol = sIssPerVol;
    }

    public String getSFreqCd() {
        return sFreqCd;
    }

    public void setSFreqCd(String sFreqCd) {
        this.sFreqCd = sFreqCd;
    }

    public Integer getSLeadtime() {
        return sLeadtime;
    }

    public void setSLeadtime(Integer sLeadtime) {
        this.sLeadtime = sLeadtime;
    }

    public String getSMode() {
        return sMode;
    }

    public void setSMode(String sMode) {
        this.sMode = sMode;
    }

    public String getSBugetCd() {
        return sBugetCd;
    }

    public void setSBugetCd(String sBugetCd) {
        this.sBugetCd = sBugetCd;
    }

    public BigDecimal getSPrice() {
        return sPrice;
    }

    public void setSPrice(BigDecimal sPrice) {
        this.sPrice = sPrice;
    }

    public String getSCurrency() {
        return sCurrency;
    }

    public void setSCurrency(String sCurrency) {
        this.sCurrency = sCurrency;
    }

    public BigDecimal getSConvRate() {
        return sConvRate;
    }

    public void setSConvRate(BigDecimal sConvRate) {
        this.sConvRate = sConvRate;
    }

    public String getSCountry() {
        return sCountry;
    }

    public void setSCountry(String sCountry) {
        this.sCountry = sCountry;
    }

    public String getSNote() {
        return sNote;
    }

    public void setSNote(String sNote) {
        this.sNote = sNote;
    }

    public Date getSFirstYear() {
        return sFirstYear;
    }

    public void setSFirstYear(Date sFirstYear) {
        this.sFirstYear = sFirstYear;
    }

    public String getSPubCity() {
        return sPubCity;
    }

    public void setSPubCity(String sPubCity) {
        this.sPubCity = sPubCity;
    }

    public String getSPubCountry() {
        return sPubCountry;
    }

    public void setSPubCountry(String sPubCountry) {
        this.sPubCountry = sPubCountry;
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

    public String getSStatus() {
        return sStatus;
    }

    public void setSStatus(String sStatus) {
        this.sStatus = sStatus;
    }

    public String getSDirect() {
        return sDirect;
    }

    public void setSDirect(String sDirect) {
        this.sDirect = sDirect;
    }

    public String getSUrl() {
        return sUrl;
    }

    public void setSUrl(String sUrl) {
        this.sUrl = sUrl;
    }

    public String getSUniversity() {
        return sUniversity;
    }

    public void setSUniversity(String sUniversity) {
        this.sUniversity = sUniversity;
    }

    public String getSInflibnet() {
        return sInflibnet;
    }

    public void setSInflibnet(String sInflibnet) {
        this.sInflibnet = sInflibnet;
    }

    public String getSCode() {
        return sCode;
    }

    public void setSCode(String sCode) {
        this.sCode = sCode;
    }

    public String getSEdition() {
        return sEdition;
    }

    public void setSEdition(String sEdition) {
        this.sEdition = sEdition;
    }

    public Integer getSReqCopy() {
        return sReqCopy;
    }

    public void setSReqCopy(Integer sReqCopy) {
        this.sReqCopy = sReqCopy;
    }

    public Integer getSApprCopy() {
        return sApprCopy;
    }

    public void setSApprCopy(Integer sApprCopy) {
        this.sApprCopy = sApprCopy;
    }

    public String getSOrderNo() {
        return sOrderNo;
    }

    public void setSOrderNo(String sOrderNo) {
        this.sOrderNo = sOrderNo;
    }

    public String getSMstStatus() {
        return sMstStatus;
    }

    public void setSMstStatus(String sMstStatus) {
        this.sMstStatus = sMstStatus;
    }

    public String getSInvNo() {
        return sInvNo;
    }

    public void setSInvNo(String sInvNo) {
        this.sInvNo = sInvNo;
    }

    public String getSCatRecid() {
        return sCatRecid;
    }

    public void setSCatRecid(String sCatRecid) {
        this.sCatRecid = sCatRecid;
    }

    public BigDecimal getSPriceperissue() {
        return sPriceperissue;
    }

    public void setSPriceperissue(BigDecimal sPriceperissue) {
        this.sPriceperissue = sPriceperissue;
    }

    public SRequest getSReqNo() {
        return sReqNo;
    }
    public String getDues() {
        return "delete from t_memdue";
    }

    public void setSReqNo(SRequest sReqNo) {
        this.sReqNo = sReqNo;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sRecid != null ? sRecid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SMst)) {
            return false;
        }
        SMst other = (SMst) object;
        if ((this.sRecid == null && other.sRecid != null) || (this.sRecid != null && !this.sRecid.equals(other.sRecid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serialControl.SMst[ sRecid=" + sRecid + " ]";
    }
    
    @XmlTransient
    public Collection<SSubdetail> getSSubdetailCollection() {
        return sSubdetailCollection;
    }

    public void setSSubdetailCollection(Collection<SSubdetail> sSubdetailCollection) {
        this.sSubdetailCollection = sSubdetailCollection;
    }

    @XmlTransient
    public Collection<SSchedule> getSScheduleCollection() {
        return sScheduleCollection;
    }

    public void setSScheduleCollection(Collection<SSchedule> sScheduleCollection) {
        this.sScheduleCollection = sScheduleCollection;
    }
    
}
