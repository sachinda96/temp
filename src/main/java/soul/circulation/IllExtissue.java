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
import javax.xml.bind.annotation.XmlRootElement;
import soul.general_master.IllLibmst;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "ill_extissue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IllExtissue.findAll", query = "SELECT i FROM IllExtissue i"),
    @NamedQuery(name = "IllExtissue.findByIssueId", query = "SELECT i FROM IllExtissue i WHERE i.issueId = :issueId"),
    @NamedQuery(name = "IllExtissue.findByAccNo", query = "SELECT i FROM IllExtissue i WHERE i.accNo = :accNo"),
    @NamedQuery(name = "IllExtissue.findByTitle", query = "SELECT i FROM IllExtissue i WHERE i.title = :title"),
    @NamedQuery(name = "IllExtissue.findByRqstDt", query = "SELECT i FROM IllExtissue i WHERE i.rqstDt = :rqstDt"),
    @NamedQuery(name = "IllExtissue.findByRqstRef", query = "SELECT i FROM IllExtissue i WHERE i.rqstRef = :rqstRef"),
    @NamedQuery(name = "IllExtissue.findBySendDt", query = "SELECT i FROM IllExtissue i WHERE i.sendDt = :sendDt"),
    @NamedQuery(name = "IllExtissue.findBySendUCd", query = "SELECT i FROM IllExtissue i WHERE i.sendUCd = :sendUCd"),
    @NamedQuery(name = "IllExtissue.findByRecvDt", query = "SELECT i FROM IllExtissue i WHERE i.recvDt = :recvDt"),
    @NamedQuery(name = "IllExtissue.findByRecvUCd", query = "SELECT i FROM IllExtissue i WHERE i.recvUCd = :recvUCd"),
    @NamedQuery(name = "IllExtissue.findByLendBookStatus", query = "SELECT i FROM IllExtissue i WHERE i.lendBookStatus = :lendBookStatus"),

    @NamedQuery(name = "IllExtissue.findByLibCd", query = "SELECT i FROM IllExtissue i WHERE i.libCd.libCd = :libCd"),
})
public class IllExtissue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "issue_id")
    private Integer issueId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "acc_no")
    private String accNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rqst_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rqstDt;
    @Size(max = 200)
    @Column(name = "rqst_ref")
    private String rqstRef;
    @Column(name = "send_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDt;
    @Size(max = 20)
    @Column(name = "send_u_cd")
    private String sendUCd;
    @Column(name = "recv_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recvDt;
    @Size(max = 20)
    @Column(name = "recv_u_cd")
    private String recvUCd;
    @Column(name = "lend_book_status")
    private String lendBookStatus;
    @JoinColumn(name = "lib_cd", referencedColumnName = "lib_cd")
    @ManyToOne(optional = false)
    private IllLibmst libCd;

    public IllExtissue() {
    }

    public IllExtissue(Integer issueId) {
        this.issueId = issueId;
    }

    public IllExtissue(Integer issueId, String accNo, String title, Date rqstDt) {
        this.issueId = issueId;
        this.accNo = accNo;
        this.title = title;
        this.rqstDt = rqstDt;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRqstDt() {
        return rqstDt;
    }

    public void setRqstDt(Date rqstDt) {
        this.rqstDt = rqstDt;
    }

    public String getRqstRef() {
        return rqstRef;
    }

    public void setRqstRef(String rqstRef) {
        this.rqstRef = rqstRef;
    }

    public Date getSendDt() {
        return sendDt;
    }

    public void setSendDt(Date sendDt) {
        this.sendDt = sendDt;
    }

    public String getSendUCd() {
        return sendUCd;
    }

    public void setSendUCd(String sendUCd) {
        this.sendUCd = sendUCd;
    }

    public Date getRecvDt() {
        return recvDt;
    }

    public void setRecvDt(Date recvDt) {
        this.recvDt = recvDt;
    }

    public String getRecvUCd() {
        return recvUCd;
    }

    public void setRecvUCd(String recvUCd) {
        this.recvUCd = recvUCd;
    }

    public String getLendBookStatus() {
        return lendBookStatus;
    }

    public void setLendBookStatus(String lendBookStatus) {
        this.lendBookStatus = lendBookStatus;
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
        hash += (issueId != null ? issueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IllExtissue)) {
            return false;
        }
        IllExtissue other = (IllExtissue) object;
        if ((this.issueId == null && other.issueId != null) || (this.issueId != null && !this.issueId.equals(other.issueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.IllExtissue[ issueId=" + issueId + " ]";
    }
    
}
