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
@Table(name = "ill_reqprocdmp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IllReqprocdmp.findAll", query = "SELECT i FROM IllReqprocdmp i"),
    @NamedQuery(name = "IllReqprocdmp.findByRequestId", query = "SELECT i FROM IllReqprocdmp i WHERE i.requestId = :requestId"),
    @NamedQuery(name = "IllReqprocdmp.findByTitle", query = "SELECT i FROM IllReqprocdmp i WHERE i.title = :title"),
    @NamedQuery(name = "IllReqprocdmp.findByArrDt", query = "SELECT i FROM IllReqprocdmp i WHERE i.arrDt = :arrDt"),
    @NamedQuery(name = "IllReqprocdmp.findByRecvUCd", query = "SELECT i FROM IllReqprocdmp i WHERE i.recvUCd = :recvUCd"),
    @NamedQuery(name = "IllReqprocdmp.findByIssDt", query = "SELECT i FROM IllReqprocdmp i WHERE i.issDt = :issDt"),
    @NamedQuery(name = "IllReqprocdmp.findByIssUCd", query = "SELECT i FROM IllReqprocdmp i WHERE i.issUCd = :issUCd"),
    @NamedQuery(name = "IllReqprocdmp.findByRetDt", query = "SELECT i FROM IllReqprocdmp i WHERE i.retDt = :retDt"),
    @NamedQuery(name = "IllReqprocdmp.findByRetUCd", query = "SELECT i FROM IllReqprocdmp i WHERE i.retUCd = :retUCd"),
    @NamedQuery(name = "IllReqprocdmp.findBySendDt", query = "SELECT i FROM IllReqprocdmp i WHERE i.sendDt = :sendDt"),
    @NamedQuery(name = "IllReqprocdmp.findBySendUCd", query = "SELECT i FROM IllReqprocdmp i WHERE i.sendUCd = :sendUCd"),

    @NamedQuery(name = "IllReqprocdmp.findBySendDtBetween", query = "SELECT i FROM IllReqprocdmp i WHERE i.sendDt BETWEEN ?1 AND ?2"),
})
public class IllReqprocdmp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "request_id")
    private Integer requestId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "arr_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrDt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "recv_u_cd")
    private String recvUCd;
    @Column(name = "iss_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issDt;
    @Size(max = 20)
    @Column(name = "iss_u_cd")
    private String issUCd;
    @Column(name = "ret_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date retDt;
    @Size(max = 20)
    @Column(name = "ret_u_cd")
    private String retUCd;
    @Column(name = "send_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDt;
    @Size(max = 20)
    @Column(name = "send_u_cd")
    private String sendUCd;
    @JoinColumn(name = "mem_cd", referencedColumnName = "mem_cd")
    @ManyToOne(optional = false)
    private MMember memCd;
    @JoinColumn(name = "lib_cd", referencedColumnName = "lib_cd")
    @ManyToOne(optional = false)
    private IllLibmst libCd;

    public IllReqprocdmp() {
    }

    public IllReqprocdmp(Integer requestId) {
        this.requestId = requestId;
    }

    public IllReqprocdmp(Integer requestId, String title, Date arrDt, String recvUCd) {
        this.requestId = requestId;
        this.title = title;
        this.arrDt = arrDt;
        this.recvUCd = recvUCd;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getArrDt() {
        return arrDt;
    }

    public void setArrDt(Date arrDt) {
        this.arrDt = arrDt;
    }

    public String getRecvUCd() {
        return recvUCd;
    }

    public void setRecvUCd(String recvUCd) {
        this.recvUCd = recvUCd;
    }

    public Date getIssDt() {
        return issDt;
    }

    public void setIssDt(Date issDt) {
        this.issDt = issDt;
    }

    public String getIssUCd() {
        return issUCd;
    }

    public void setIssUCd(String issUCd) {
        this.issUCd = issUCd;
    }

    public Date getRetDt() {
        return retDt;
    }

    public void setRetDt(Date retDt) {
        this.retDt = retDt;
    }

    public String getRetUCd() {
        return retUCd;
    }

    public void setRetUCd(String retUCd) {
        this.retUCd = retUCd;
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

    public MMember getMemCd() {
        return memCd;
    }

    public void setMemCd(MMember memCd) {
        this.memCd = memCd;
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
        hash += (requestId != null ? requestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IllReqprocdmp)) {
            return false;
        }
        IllReqprocdmp other = (IllReqprocdmp) object;
        if ((this.requestId == null && other.requestId != null) || (this.requestId != null && !this.requestId.equals(other.requestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.IllReqprocdmp[ requestId=" + requestId + " ]";
    }
    
}
