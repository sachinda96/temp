/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import soul.general_master.IllLibmst;
import soul.general_master.Libmaterials;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "ill_rqstmst")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IllRqstmst.findAll", query = "SELECT i FROM IllRqstmst i"),
    @NamedQuery(name = "IllRqstmst.findByIllRequestNo", query = "SELECT i FROM IllRqstmst i WHERE i.illRequestNo = :illRequestNo"),
    @NamedQuery(name = "IllRqstmst.findByMemCd", query = "SELECT i FROM IllRqstmst i WHERE i.memCd = :memCd"),
    @NamedQuery(name = "IllRqstmst.findByTitle", query = "SELECT i FROM IllRqstmst i WHERE i.title = :title"),
    @NamedQuery(name = "IllRqstmst.findByAuth1", query = "SELECT i FROM IllRqstmst i WHERE i.auth1 = :auth1"),
    @NamedQuery(name = "IllRqstmst.findByAuth2", query = "SELECT i FROM IllRqstmst i WHERE i.auth2 = :auth2"),
    @NamedQuery(name = "IllRqstmst.findByPublisher", query = "SELECT i FROM IllRqstmst i WHERE i.publisher = :publisher"),
    @NamedQuery(name = "IllRqstmst.findByPubYear", query = "SELECT i FROM IllRqstmst i WHERE i.pubYear = :pubYear"),
    @NamedQuery(name = "IllRqstmst.findByRqstDt", query = "SELECT i FROM IllRqstmst i WHERE i.rqstDt = :rqstDt"),
    @NamedQuery(name = "IllRqstmst.findByReminderDt", query = "SELECT i FROM IllRqstmst i WHERE i.reminderDt = :reminderDt"),
    @NamedQuery(name = "IllRqstmst.findByRqstStatus", query = "SELECT i FROM IllRqstmst i WHERE i.rqstStatus = :rqstStatus"),
    @NamedQuery(name = "IllRqstmst.findByArrivalDt", query = "SELECT i FROM IllRqstmst i WHERE i.arrivalDt = :arrivalDt"),
    @NamedQuery(name = "IllRqstmst.findByRecieveUserCode", query = "SELECT i FROM IllRqstmst i WHERE i.recieveUserCode = :recieveUserCode"),
    @NamedQuery(name = "IllRqstmst.findByIssueDt", query = "SELECT i FROM IllRqstmst i WHERE i.issueDt = :issueDt"),
    @NamedQuery(name = "IllRqstmst.findByIssueUserCode", query = "SELECT i FROM IllRqstmst i WHERE i.issueUserCode = :issueUserCode"),
    @NamedQuery(name = "IllRqstmst.findByRecieveDt", query = "SELECT i FROM IllRqstmst i WHERE i.recieveDt = :recieveDt"),
    @NamedQuery(name = "IllRqstmst.findByReturnUserCode", query = "SELECT i FROM IllRqstmst i WHERE i.returnUserCode = :returnUserCode"),
    @NamedQuery(name = "IllRqstmst.findByRequestId", query = "SELECT i FROM IllRqstmst i WHERE i.requestId = :requestId"),
    @NamedQuery(name = "IllRqstmst.findBySendDt", query = "SELECT i FROM IllRqstmst i WHERE i.sendDt = :sendDt"),
    @NamedQuery(name = "IllRqstmst.findBySendUserCode", query = "SELECT i FROM IllRqstmst i WHERE i.sendUserCode = :sendUserCode"),
    @NamedQuery(name = "IllRqstmst.findByLibCd", query = "SELECT i FROM IllRqstmst i WHERE i.libCd = :libCd"),
    
    @NamedQuery(name = "IllRqstmst.findByMemCdAndStatus", query = "SELECT i FROM IllRqstmst i WHERE i.memCd = ?1 AND i.rqstStatus = ?2 "),
    @NamedQuery(name = "IllRqstmst.findByLibCdAndMemCd", query = "SELECT i FROM IllRqstmst i WHERE i.rqstStatus = 'A' AND i.libCd.libCd = ?1 AND i.memCd = ?2 "),
    @NamedQuery(name = "IllRqstmst.findByLibCdAndStatus", query = "SELECT i FROM IllRqstmst i WHERE i.libCd.libCd = ?1 AND i.rqstStatus = ?2 ")
})
public class IllRqstmst implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ill_request_no")
    private Integer illRequestNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "mem_cd")
    private String memCd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "auth1")
    private String auth1;
    @Size(max = 250)
    @Column(name = "auth2")
    private String auth2;
    @Size(max = 250)
    @Column(name = "publisher")
    private String publisher;
    @Size(max = 50)
    @Column(name = "pub_year")
    private String pubYear;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rqst_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rqstDt;
    @Column(name = "reminder_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reminderDt;
    @Column(name = "rqst_status")
    private String rqstStatus;
    @Column(name = "arrival_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalDt;
    @Size(max = 20)
    @Column(name = "recieve_user_code")
    private String recieveUserCode;
    @Column(name = "issue_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueDt;
    @Size(max = 20)
    @Column(name = "issue_user_code")
    private String issueUserCode;
    @Column(name = "recieve_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recieveDt;
    @Size(max = 20)
    @Column(name = "return_user_code")
    private String returnUserCode;
    @Column(name = "request_id")
    private Integer requestId;
    @Column(name = "send_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDt;
    @Size(max = 20)
    @Column(name = "send_user_code")
    private String sendUserCode;
    @JoinColumn(name = "media", referencedColumnName = "Code")
    @ManyToOne(optional = false)
    private Libmaterials media;
    @JoinColumn(name = "lib_cd", referencedColumnName = "lib_cd")
    @ManyToOne
    private IllLibmst libCd;

    public IllRqstmst() {
    }

    public IllRqstmst(Integer illRequestNo) {
        this.illRequestNo = illRequestNo;
    }

    public IllRqstmst(Integer illRequestNo, String memCd, String title, String auth1, Date rqstDt) {
        this.illRequestNo = illRequestNo;
        this.memCd = memCd;
        this.title = title;
        this.auth1 = auth1;
        this.rqstDt = rqstDt;
    }

    public Integer getIllRequestNo() {
        return illRequestNo;
    }

    public void setIllRequestNo(Integer illRequestNo) {
        this.illRequestNo = illRequestNo;
    }

    public String getMemCd() {
        return memCd;
    }

    public void setMemCd(String memCd) {
        this.memCd = memCd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuth1() {
        return auth1;
    }

    public void setAuth1(String auth1) {
        this.auth1 = auth1;
    }

    public String getAuth2() {
        return auth2;
    }

    public void setAuth2(String auth2) {
        this.auth2 = auth2;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPubYear() {
        return pubYear;
    }

    public void setPubYear(String pubYear) {
        this.pubYear = pubYear;
    }

    public Date getRqstDt() {
        return rqstDt;
    }

    public void setRqstDt(Date rqstDt) {
        this.rqstDt = rqstDt;
    }

    public Date getReminderDt() {
        return reminderDt;
    }

    public void setReminderDt(Date reminderDt) {
        this.reminderDt = reminderDt;
    }

    public String getRqstStatus() {
        return rqstStatus;
    }

    public void setRqstStatus(String rqstStatus) {
        this.rqstStatus = rqstStatus;
    }

    public Date getArrivalDt() {
        return arrivalDt;
    }

    public void setArrivalDt(Date arrivalDt) {
        this.arrivalDt = arrivalDt;
    }

    public String getRecieveUserCode() {
        return recieveUserCode;
    }

    public void setRecieveUserCode(String recieveUserCode) {
        this.recieveUserCode = recieveUserCode;
    }

    public Date getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(Date issueDt) {
        this.issueDt = issueDt;
    }

    public String getIssueUserCode() {
        return issueUserCode;
    }

    public void setIssueUserCode(String issueUserCode) {
        this.issueUserCode = issueUserCode;
    }

    public Date getRecieveDt() {
        return recieveDt;
    }

    public void setRecieveDt(Date recieveDt) {
        this.recieveDt = recieveDt;
    }

    public String getReturnUserCode() {
        return returnUserCode;
    }

    public void setReturnUserCode(String returnUserCode) {
        this.returnUserCode = returnUserCode;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Date getSendDt() {
        return sendDt;
    }

    public void setSendDt(Date sendDt) {
        this.sendDt = sendDt;
    }

    public String getSendUserCode() {
        return sendUserCode;
    }

    public void setSendUserCode(String sendUserCode) {
        this.sendUserCode = sendUserCode;
    }

    public Libmaterials getMedia() {
        return media;
    }

    public void setMedia(Libmaterials media) {
        this.media = media;
    }

    public IllLibmst getLibCd() {
        return libCd;
    }

    public void setLibCd(IllLibmst libCd) {
        this.libCd = libCd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (illRequestNo != null ? illRequestNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IllRqstmst)) {
            return false;
        }
        IllRqstmst other = (IllRqstmst) object;
        if ((this.illRequestNo == null && other.illRequestNo != null) || (this.illRequestNo != null && !this.illRequestNo.equals(other.illRequestNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.IllRqstmst[ illRequestNo=" + illRequestNo + " ]";
    }
    
}
