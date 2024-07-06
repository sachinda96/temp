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
@Table(name = "ill_extissuedmp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IllExtissuedmp.findAll", query = "SELECT i FROM IllExtissuedmp i"),
    @NamedQuery(name = "IllExtissuedmp.findByIssueId", query = "SELECT i FROM IllExtissuedmp i WHERE i.issueId = :issueId"),
    @NamedQuery(name = "IllExtissuedmp.findByAccNo", query = "SELECT i FROM IllExtissuedmp i WHERE i.accNo = :accNo"),
    @NamedQuery(name = "IllExtissuedmp.findByTitle", query = "SELECT i FROM IllExtissuedmp i WHERE i.title = :title"),
    @NamedQuery(name = "IllExtissuedmp.findByRqstDt", query = "SELECT i FROM IllExtissuedmp i WHERE i.rqstDt = :rqstDt"),
    @NamedQuery(name = "IllExtissuedmp.findBySendDt", query = "SELECT i FROM IllExtissuedmp i WHERE i.sendDt = :sendDt"),
    @NamedQuery(name = "IllExtissuedmp.findByRqstRef", query = "SELECT i FROM IllExtissuedmp i WHERE i.rqstRef = :rqstRef"),
    @NamedQuery(name = "IllExtissuedmp.findBySendUCd", query = "SELECT i FROM IllExtissuedmp i WHERE i.sendUCd = :sendUCd"),
    @NamedQuery(name = "IllExtissuedmp.findByRecvDt", query = "SELECT i FROM IllExtissuedmp i WHERE i.recvDt = :recvDt"),
    @NamedQuery(name = "IllExtissuedmp.findByRecvUCd", query = "SELECT i FROM IllExtissuedmp i WHERE i.recvUCd = :recvUCd"),

    @NamedQuery(name = "IllExtissuedmp.findByRqstDtBetween", query = "SELECT i FROM IllExtissuedmp i WHERE i.rqstDt BETWEEN ?1 AND ?2")
})
public class IllExtissuedmp implements Serializable {
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
    @Column(name = "send_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDt;
    @Size(max = 200)
    @Column(name = "rqst_ref")
    private String rqstRef;
    @Size(max = 20)
    @Column(name = "send_u_cd")
    private String sendUCd;
    @Column(name = "recv_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recvDt;
    @Size(max = 20)
    @Column(name = "recv_u_cd")
    private String recvUCd;
    @JoinColumn(name = "lib_cd", referencedColumnName = "lib_cd")
    @ManyToOne(optional = false)
    private IllLibmst libCd;

    public IllExtissuedmp() {
    }

    public IllExtissuedmp(Integer issueId) {
        this.issueId = issueId;
    }

    public IllExtissuedmp(Integer issueId, String accNo, String title, Date rqstDt) {
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

    public Date getSendDt() {
        return sendDt;
    }

    public void setSendDt(Date sendDt) {
        this.sendDt = sendDt;
    }

    public String getRqstRef() {
        return rqstRef;
    }

    public void setRqstRef(String rqstRef) {
        this.rqstRef = rqstRef;
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
        if (!(object instanceof IllExtissuedmp)) {
            return false;
        }
        IllExtissuedmp other = (IllExtissuedmp) object;
        if ((this.issueId == null && other.issueId != null) || (this.issueId != null && !this.issueId.equals(other.issueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.IllExtissuedmp[ issueId=" + issueId + " ]";
    }
    
}
