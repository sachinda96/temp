/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl;

import java.io.Serializable;
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
 * @author soullab
 */
@Entity
@Table(name = "s_mst_in_schedule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SMstInSchedule.findAll", query = "SELECT s FROM SMstInSchedule s"),
    @NamedQuery(name = "SMstInSchedule.findBySRecid", query = "SELECT s FROM SMstInSchedule s WHERE s.sRecid = :sRecid"),
    @NamedQuery(name = "SMstInSchedule.findBySTitle", query = "SELECT s FROM SMstInSchedule s WHERE s.sTitle = :sTitle"),
    @NamedQuery(name = "SMstInSchedule.findBySStDt", query = "SELECT s FROM SMstInSchedule s WHERE s.sStDt = :sStDt"),
    @NamedQuery(name = "SMstInSchedule.findBySEnDt", query = "SELECT s FROM SMstInSchedule s WHERE s.sEnDt = :sEnDt"),
    @NamedQuery(name = "SMstInSchedule.findBySStVol", query = "SELECT s FROM SMstInSchedule s WHERE s.sStVol = :sStVol"),
    @NamedQuery(name = "SMstInSchedule.findBySStIss", query = "SELECT s FROM SMstInSchedule s WHERE s.sStIss = :sStIss"),
    @NamedQuery(name = "SMstInSchedule.findBySEnVol", query = "SELECT s FROM SMstInSchedule s WHERE s.sEnVol = :sEnVol"),
    @NamedQuery(name = "SMstInSchedule.findBySEnIss", query = "SELECT s FROM SMstInSchedule s WHERE s.sEnIss = :sEnIss"),
    @NamedQuery(name = "SMstInSchedule.findBySIssPerVol", query = "SELECT s FROM SMstInSchedule s WHERE s.sIssPerVol = :sIssPerVol"),
    @NamedQuery(name = "SMstInSchedule.findBySIssn", query = "SELECT s FROM SMstInSchedule s WHERE s.sIssn = :sIssn"),
    @NamedQuery(name = "SMstInSchedule.findBySFCd", query = "SELECT s FROM SMstInSchedule s WHERE s.sFCd = :sFCd"),
    @NamedQuery(name = "SMstInSchedule.findBySFNm", query = "SELECT s FROM SMstInSchedule s WHERE s.sFNm = :sFNm"),
    @NamedQuery(name = "SMstInSchedule.findByASCd", query = "SELECT s FROM SMstInSchedule s WHERE s.aSCd = :aSCd"),
    @NamedQuery(name = "SMstInSchedule.findByASName", query = "SELECT s FROM SMstInSchedule s WHERE s.aSName = :aSName"),
    @NamedQuery(name = "SMstInSchedule.findByASCity", query = "SELECT s FROM SMstInSchedule s WHERE s.aSCity = :aSCity"),
    @NamedQuery(name = "SMstInSchedule.findByASCountry", query = "SELECT s FROM SMstInSchedule s WHERE s.aSCountry = :aSCountry"),
    @NamedQuery(name = "SMstInSchedule.findBySMode", query = "SELECT s FROM SMstInSchedule s WHERE s.sMode = :sMode"),
    @NamedQuery(name = "SMstInSchedule.findBySApprCopy", query = "SELECT s FROM SMstInSchedule s WHERE s.sApprCopy = :sApprCopy")})
public class SMstInSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_recid")
    @Id
    private String sRecid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 510)
    @Column(name = "s_title")
    private String sTitle;
    @Column(name = "s_st_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sStDt;
    @Column(name = "s_en_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sEnDt;
    @Size(max = 100)
    @Column(name = "s_st_vol")
    private String sStVol;
    @Size(max = 100)
    @Column(name = "s_st_iss")
    private String sStIss;
    @Size(max = 100)
    @Column(name = "s_en_vol")
    private String sEnVol;
    @Size(max = 100)
    @Column(name = "s_en_iss")
    private String sEnIss;
    @Column(name = "s_iss_per_vol")
    private Integer sIssPerVol;
    @Size(max = 60)
    @Column(name = "s_issn")
    private String sIssn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_f_cd")
    private String sFCd;
    @Size(max = 510)
    @Column(name = "s_f_nm")
    private String sFNm;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "a_s_cd")
    private String aSCd;
    @Size(max = 200)
    @Column(name = "a_s_name")
    private String aSName;
    @Size(max = 200)
    @Column(name = "a_s_city")
    private String aSCity;
    @Size(max = 20)
    @Column(name = "a_s_country")
    private String aSCountry;
    @Size(max = 2)
    @Column(name = "s_mode")
    private String sMode;
    @Column(name = "s_appr_copy")
    private Integer sApprCopy;

    public SMstInSchedule() {
    }

    public String getSRecid() {
        return sRecid;
    }

    public void setSRecid(String sRecid) {
        this.sRecid = sRecid;
    }

    public String getSTitle() {
        return sTitle;
    }

    public void setSTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public Date getSStDt() {
        return sStDt;
    }

    public void setSStDt(Date sStDt) {
        this.sStDt = sStDt;
    }

    public Date getSEnDt() {
        return sEnDt;
    }

    public void setSEnDt(Date sEnDt) {
        this.sEnDt = sEnDt;
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

    public String getSIssn() {
        return sIssn;
    }

    public void setSIssn(String sIssn) {
        this.sIssn = sIssn;
    }

    public String getSFCd() {
        return sFCd;
    }

    public void setSFCd(String sFCd) {
        this.sFCd = sFCd;
    }

    public String getSFNm() {
        return sFNm;
    }

    public void setSFNm(String sFNm) {
        this.sFNm = sFNm;
    }

    public String getASCd() {
        return aSCd;
    }

    public void setASCd(String aSCd) {
        this.aSCd = aSCd;
    }

    public String getASName() {
        return aSName;
    }

    public void setASName(String aSName) {
        this.aSName = aSName;
    }

    public String getASCity() {
        return aSCity;
    }

    public void setASCity(String aSCity) {
        this.aSCity = aSCity;
    }

    public String getASCountry() {
        return aSCountry;
    }

    public void setASCountry(String aSCountry) {
        this.aSCountry = aSCountry;
    }

    public String getSMode() {
        return sMode;
    }

    public void setSMode(String sMode) {
        this.sMode = sMode;
    }

    public Integer getSApprCopy() {
        return sApprCopy;
    }

    public void setSApprCopy(Integer sApprCopy) {
        this.sApprCopy = sApprCopy;
    }
    
}
