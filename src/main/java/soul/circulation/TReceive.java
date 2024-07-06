/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "t_receive")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TReceive.findAll", query = "SELECT t FROM TReceive t"),
    @NamedQuery(name = "TReceive.findByMemCd", query = "SELECT t FROM TReceive t WHERE t.tReceivePK.memCd = :memCd"),
    @NamedQuery(name = "TReceive.findByAccnNo", query = "SELECT t FROM TReceive t WHERE t.tReceivePK.accnNo = :accnNo"),
    @NamedQuery(name = "TReceive.findByIssDt", query = "SELECT t FROM TReceive t WHERE t.tReceivePK.issDt = :issDt"),
    @NamedQuery(name = "TReceive.findByIssUserCd", query = "SELECT t FROM TReceive t WHERE t.issUserCd = :issUserCd"),
    @NamedQuery(name = "TReceive.findByRecvDt", query = "SELECT t FROM TReceive t WHERE t.recvDt = :recvDt"),
    @NamedQuery(name = "TReceive.findByRecvUserCd", query = "SELECT t FROM TReceive t WHERE t.recvUserCd = :recvUserCd"),
    @NamedQuery(name = "TReceive.findBySlipNo", query = "SELECT t FROM TReceive t WHERE t.slipNo = :slipNo"),
    @NamedQuery(name = "TReceive.findByRecvStatus", query = "SELECT t FROM TReceive t WHERE t.recvStatus = :recvStatus"),
    @NamedQuery(name = "TReceive.findByRecvDtBtwn", query = "SELECT t FROM TReceive t WHERE t.recvDt BETWEEN ?1 AND ?2")
})
public class TReceive implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TReceivePK tReceivePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "iss_user_cd")
    private String issUserCd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "recv_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recvDt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "recv_user_cd")
    private String recvUserCd;
    @Size(max = 7)
    @Column(name = "slip_no")
    private String slipNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "recv_status")
    private char recvStatus;
    @JoinColumn(name = "mem_cd", referencedColumnName = "mem_cd", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MMember mMember;

    public TReceive() {
    }

    public TReceive(TReceivePK tReceivePK) {
        this.tReceivePK = tReceivePK;
    }

    public TReceive(TReceivePK tReceivePK, String issUserCd, Date recvDt, String recvUserCd, char recvStatus) {
        this.tReceivePK = tReceivePK;
        this.issUserCd = issUserCd;
        this.recvDt = recvDt;
        this.recvUserCd = recvUserCd;
        this.recvStatus = recvStatus;
    }

    public TReceive(String memCd, String accnNo, Date issDt) {
        this.tReceivePK = new TReceivePK(memCd, accnNo, issDt);
    }

    public TReceivePK getTReceivePK() {
        return tReceivePK;
    }

    public void setTReceivePK(TReceivePK tReceivePK) {
        this.tReceivePK = tReceivePK;
    }

    public String getIssUserCd() {
        return issUserCd;
    }

    public void setIssUserCd(String issUserCd) {
        this.issUserCd = issUserCd;
    }

    public Date getRecvDt() {
        return recvDt;
    }

    public void setRecvDt(Date recvDt) {
        this.recvDt = recvDt;
    }

    public String getRecvUserCd() {
        return recvUserCd;
    }

    public void setRecvUserCd(String recvUserCd) {
        this.recvUserCd = recvUserCd;
    }

    public String getSlipNo() {
        return slipNo;
    }

    public void setSlipNo(String slipNo) {
        this.slipNo = slipNo;
    }

    public char getRecvStatus() {
        return recvStatus;
    }

    public void setRecvStatus(char recvStatus) {
        this.recvStatus = recvStatus;
    }

    public MMember getMMember() {
        return mMember;
    }

    public void setMMember(MMember mMember) {
        this.mMember = mMember;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tReceivePK != null ? tReceivePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TReceive)) {
            return false;
        }
        TReceive other = (TReceive) object;
        if ((this.tReceivePK == null && other.tReceivePK != null) || (this.tReceivePK != null && !this.tReceivePK.equals(other.tReceivePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TReceive[ tReceivePK=" + tReceivePK + " ]";
    }
    
}
