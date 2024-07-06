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
@Table(name = "s_schedule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SSchedule.findAll", query = "SELECT s FROM SSchedule s"),
    @NamedQuery(name = "SSchedule.findBySSSrno", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSSrno = :sSSrno"),
    @NamedQuery(name = "SSchedule.findBySSRecid", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = :sSRecid"),
    @NamedQuery(name = "SSchedule.findBySSVol", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSVol = :sSVol"),
    @NamedQuery(name = "SSchedule.findBySSIss", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSIss = :sSIss"),
    @NamedQuery(name = "SSchedule.findBySSPubDt", query = "SELECT s FROM SSchedule s WHERE s.sSPubDt = :sSPubDt"),
    @NamedQuery(name = "SSchedule.findBySSExpDt", query = "SELECT s FROM SSchedule s WHERE s.sSExpDt = :sSExpDt"),
    @NamedQuery(name = "SSchedule.findBySSExpDate", query = "SELECT s FROM SSchedule s WHERE s.sSExpDt < :sSExpDt"),
    
    @NamedQuery(name = "SSchedule.findBySSStatus", query = "SELECT s FROM SSchedule s WHERE s.sSStatus = :sSStatus"),
    @NamedQuery(name = "SSchedule.findBySSRcptDt", query = "SELECT s FROM SSchedule s WHERE s.sSRcptDt = :sSRcptDt"),
    @NamedQuery(name = "SSchedule.findBySSRcptBetween", query = "SELECT s FROM SSchedule s WHERE s.sSRcptDt >= ?1 AND s.sSRcptDt <= ?2"),
    @NamedQuery(name = "SSchedule.findBySSRemark", query = "SELECT s FROM SSchedule s WHERE s.sSRemark = :sSRemark"),
    @NamedQuery(name = "SSchedule.findBySSPriceperissue", query = "SELECT s FROM SSchedule s WHERE s.sSPriceperissue = :sSPriceperissue"),
    @NamedQuery(name = "SSchedule.findBySSBindNo", query = "SELECT s FROM SSchedule s WHERE s.sSBindNo = :sSBindNo"),
    @NamedQuery(name = "SSchedule.findBySSReminder", query = "SELECT s FROM SSchedule s WHERE s.sSReminder = :sSReminder"),
    @NamedQuery(name = "SSchedule.findBySSFlag", query = "SELECT s FROM SSchedule s WHERE s.sSFlag = :sSFlag"),
    //@NamedQuery(name = "SSchedule.getPRimaryKey", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSIss = :sSFlag"),
    @NamedQuery(name = "SSchedule.findByForCheckIn", query = "SELECT s FROM SSchedule s WHERE s.sSStatus LIKE 'E%' OR s.sSStatus LIKE 'R%' OR s.sSStatus LIKE 'N%'"),
    @NamedQuery(name = "SSchedule.findBySSStatusANDLtExpDt", query = "SELECT s FROM SSchedule s WHERE s.sSStatus = ?1 AND s.sSExpDt < ?2"),
    @NamedQuery(name = "SSchedule.findBySSRecidANDStatus", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = ?1 AND s.sSStatus = ?2"),
    @NamedQuery(name = "SSchedule.findByMaxSSSrno", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSSrno = (SELECT MAX(s.sSchedulePK.sSSrno) FROM SSchedule s WHERE s.sSchedulePK.sSRecid = :sSRecid)"),
    @NamedQuery(name = "SSchedule.findBySSRecidAndGtEqSrNo", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = ?1 AND s.sSchedulePK.sSSrno >= ?2"),
    @NamedQuery(name = "SSchedule.listofAllNonReceivedIssues", query = "SELECT s FROM SSchedule s WHERE s.sSStatus LIKE 'E' and s.sSchedulePK.sSRecid = s.sMst.sRecid and s.sSExpDt < 'current_Date()'"),  
    @NamedQuery(name = "SSchedule.listofAllNonReceivedIssuesByExpectedDateUpTo", query = "SELECT s FROM SSchedule s WHERE s.sSStatus LIKE 'E' and s.sSchedulePK.sSRecid = s.sMst.sRecid and s.sSExpDt < :sSExpDt"),  
    @NamedQuery(name = "SSchedule.listofTitlesCheckInDetails", query = "SELECT s FROM SSchedule s WHERE s.sMst.sRecid = s.sSchedulePK.sSRecid and (s.sSStatus like 'E%' OR s.sSStatus like 'R%' OR s.sSStatus like 'N%') GROUP BY s.sMst.sTitle"),  
    @NamedQuery(name = "SSchedule.listofItemsforCheckIn", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = :sSRecid and s.sSStatus NOT IN ('Received','Binding') order by s.sSchedulePK.sSSrno"),
    @NamedQuery(name = "SSchedule.retrieveAllforRemovingReceived", query = "SELECT s FROM SSchedule s WHERE s.sSStatus LIKE 'Received%' and s.sSchedulePK.sSRecid = s.sMst.sRecid and s.sSRcptDt < 'current_Date()'"),
    @NamedQuery(name = "SSchedule.listofTitlesCheckInDetails", query = "SELECT s FROM SSchedule s WHERE s.sMst.sRecid = s.sSchedulePK.sSRecid and (s.sSStatus like 'E%' OR s.sSStatus like 'R%' OR s.sSStatus like 'N%') GROUP BY s.sMst.sTitle"),
    @NamedQuery(name = "SSchedule.checkInReportByAnyStatus", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = s.sMst.sRecid GROUP BY s.sSchedulePK.sSSrno"),
    @NamedQuery(name = "SSchedule.checkInReportofExpectedIssues", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = s.sMst.sRecid and s.sSStatus LIKE 'E%' GROUP BY s.sSchedulePK.sSSrno"),
    @NamedQuery(name = "SSchedule.checkInReportofReceivedIssues", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = s.sMst.sRecid and s.sSStatus LIKE 'R%' ORDER BY s.sSchedulePK.sSSrno"),
    @NamedQuery(name = "SSchedule.checkInReportofNonReceivedIssues", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = s.sMst.sRecid and s.sSStatus LIKE 'N%' GROUP BY s.sSchedulePK.sSSrno"),
    @NamedQuery(name = "SSchedule.checkInReportofBindedIssues", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = s.sMst.sRecid and s.sSStatus LIKE 'B%' GROUP BY s.sSchedulePK.sSSrno"),
    @NamedQuery(name = "SSchedule.checkInReportOfAnyStatusByTitle", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = s.sMst.sRecid and s.sMst.sTitle LIKE :sTitle GROUP BY s.sSchedulePK.sSSrno"),
    @NamedQuery(name = "SSchedule.checkInReportOfExpectedIssuesByTitle", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = s.sMst.sRecid and s.sSStatus LIKE 'E%' and s.sMst.sTitle LIKE :sTitle GROUP BY s.sSchedulePK.sSSrno"),
    @NamedQuery(name = "SSchedule.checkInReportOfReceivedIssuesByTitle", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = s.sMst.sRecid and s.sSStatus LIKE 'R%' and s.sMst.sTitle LIKE :sTitle GROUP BY s.sSchedulePK.sSSrno"),
    @NamedQuery(name = "SSchedule.checkInReportOfNonReceivedIssuesByTitle", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = s.sMst.sRecid and s.sSStatus LIKE 'N%' and s.sMst.sTitle LIKE :sTitle GROUP BY s.sSchedulePK.sSSrno"),
    @NamedQuery(name = "SSchedule.checkInReportOfBindedIssuesByTitle", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = s.sMst.sRecid and s.sSStatus LIKE 'B%' and s.sMst.sTitle LIKE :sTitle GROUP BY s.sSchedulePK.sSSrno"),
    @NamedQuery(name = "SSchedule.getForCounterUpdate", query = "SELECT s FROM SSchedule s WHERE s.sSStatus LIKE 'E%' and s.sSchedulePK.sSRecid = :sSRecid GROUP BY s.sSchedulePK.sSSrno"),    
    @NamedQuery(name = "SSchedule.findSetByRecId", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = ?1 and s.sSStatus LIKE 'R%' and s.sSRcptDt <= ?2"),
    @NamedQuery(name = "SSchedule.getForRegenerate", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = ?1 and s.sSStatus LIKE 'R%' and s.sMst.sStDt >= ?2 "),    
    @NamedQuery(name = "SSchedule.deleteSchedule", query = "DELETE FROM SSchedule s WHERE s.sSchedulePK.sSRecid = ?1 and s.sSPubDt >= ?2"),    //delete
    @NamedQuery(name = "SSchedule.findAllExpected", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = :sSRecid and s.sSStatus like '%E' "),
    @NamedQuery(name = "SSchedule.deleteAllExpected", query = "DELETE FROM SSchedule s WHERE s.sSchedulePK.sSRecid = ?1 and s.sSStatus  = ?2"),
    @NamedQuery(name = "SSchedule.findAllExpectedUpto", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = :sSRecid and s.sSExpDt <= :sSExpDt and s.sSStatus like '%E' "),
    @NamedQuery(name = "SSchedule.deleteAllExpectedUpto", query = "DELETE FROM SSchedule s WHERE s.sSchedulePK.sSRecid = ?1 and s.sSExpDt <= ?2 and s.sSStatus  = ?3"),
    @NamedQuery(name = "SSchedule.existornot", query = "SELECT MAX(s.sSchedulePK.sSSrno) FROM SSchedule s WHERE s.sSStatus = 'Received' and s.sSchedulePK.sSRecid = ?1 and s.sSPubDt >= ?2 and s.sSPubDt <= ?3"),
    @NamedQuery(name = "SSchedule.deleteExisting", query = "DELETE FROM SSchedule s WHERE s.sSchedulePK.sSRecid = ?1 and s.sSPubDt >= ?2 and s.sSPubDt  <= ?3"),
    @NamedQuery(name = "SSchedule.deleteCheckIn", query = "DELETE FROM SSchedule s WHERE s.sSchedulePK.sSRecid = ?1 and s.sSchedulePK.sSSrno = ?2 and s.sSchedulePK.sSIss = ?3 and s.sSchedulePK.sSVol = ?4"),    //delete
    @NamedQuery(name = "SSchedule.getScheduleToUpdate", query = "SELECT s FROM SSchedule s WHERE s.sSchedulePK.sSRecid = ?1 and s.sSFlag IS NULL"),    //delete
    @NamedQuery(name = "SSchedule.removeReceived", query = "DELETE FROM SSchedule s WHERE s.sSchedulePK.sSRecid = ?1 and s.sSchedulePK.sSSrno = ?2 and s.sSchedulePK.sSIss = ?3 and s.sSchedulePK.sSVol = ?4"),    //delete
})
public class SSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SSchedulePK sSchedulePK;
    @Column(name = "s_s_pub_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sSPubDt;
    @Column(name = "s_s_exp_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sSExpDt;
    @Size(max = 100)
    @Column(name = "s_s_status")
    private String sSStatus;
    @Column(name = "s_s_rcpt_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sSRcptDt;
    @Size(max = 510)
    @Column(name = "s_s_remark")
    private String sSRemark;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "s_s_priceperissue")
    private BigDecimal sSPriceperissue;
    @Size(max = 510)
    @Column(name = "s_s_bind_no")
    private String sSBindNo;
    @Size(max = 510)
    @Column(name = "s_s_reminder")
    private String sSReminder;
    @Column(name = "s_s_flag")
    private Integer sSFlag;
    @JoinColumn(name = "s_s_recid", referencedColumnName = "s_recid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SMst sMst;

    public SSchedule() {
    }

    public SSchedule(SSchedulePK sSchedulePK) {
        this.sSchedulePK = sSchedulePK;
    }

    public SSchedule(int sSSrno, String sSRecid, String sSVol, String sSIss) {
        this.sSchedulePK = new SSchedulePK(sSSrno, sSRecid, sSVol, sSIss);
    }

    public SSchedulePK getSSchedulePK() {
        return sSchedulePK;
    }

    public void setSSchedulePK(SSchedulePK sSchedulePK) {
        this.sSchedulePK = sSchedulePK;
    }

    public Date getSSPubDt() {
        return sSPubDt;
    }

    public void setSSPubDt(Date sSPubDt) {
        this.sSPubDt = sSPubDt;
    }

    public Date getSSExpDt() {
        return sSExpDt;
    }

    public void setSSExpDt(Date sSExpDt) {
        this.sSExpDt = sSExpDt;
    }

    public String getSSStatus() {
        return sSStatus;
    }

    public void setSSStatus(String sSStatus) {
        this.sSStatus = sSStatus;
    }

    public Date getSSRcptDt() {
        return sSRcptDt;
    }

    public void setSSRcptDt(Date sSRcptDt) {
        this.sSRcptDt = sSRcptDt;
    }

    public String getSSRemark() {
        return sSRemark;
    }

    public void setSSRemark(String sSRemark) {
        this.sSRemark = sSRemark;
    }

    public BigDecimal getSSPriceperissue() {
        return sSPriceperissue;
    }

    public void setSSPriceperissue(BigDecimal sSPriceperissue) {
        this.sSPriceperissue = sSPriceperissue;
    }

    public String getSSBindNo() {
        return sSBindNo;
    }

    public void setSSBindNo(String sSBindNo) {
        this.sSBindNo = sSBindNo;
    }

    public String getSSReminder() {
        return sSReminder;
    }

    public void setSSReminder(String sSReminder) {
        this.sSReminder = sSReminder;
    }

    public Integer getSSFlag() {
        return sSFlag;
    }

    public void setSSFlag(Integer sSFlag) {
        this.sSFlag = sSFlag;
    }

    public SMst getSMst() {
        return sMst;
    }

    public void setSMst(SMst sMst) {
        this.sMst = sMst;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sSchedulePK != null ? sSchedulePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SSchedule)) {
            return false;
        }
        SSchedule other = (SSchedule) object;
        if ((this.sSchedulePK == null && other.sSchedulePK != null) || (this.sSchedulePK != null && !this.sSchedulePK.equals(other.sSchedulePK))) {
            return false;
        }
        return true;
    }
    public String getRcpts() {
        return "delete from t_memrcpt";
    }

    @Override
    public String toString() {
        return "soul.serialControl.SSchedule[ sSchedulePK=" + sSchedulePK + " ]";
    }
    
}
