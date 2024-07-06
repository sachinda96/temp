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
@Table(name = "t_reserve")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TReserve.findAll", query = "SELECT t FROM TReserve t"),
    @NamedQuery(name = "TReserve.findByMemCd", query = "SELECT t FROM TReserve t WHERE t.tReservePK.memCd = :memCd"),
    @NamedQuery(name = "TReserve.findByRecordNo", query = "SELECT t FROM TReserve t WHERE t.tReservePK.recordNo = :recordNo"),
    @NamedQuery(name = "TReserve.findByResvDt", query = "SELECT t FROM TReserve t WHERE t.resvDt = :resvDt"),
    @NamedQuery(name = "TReserve.findByUserCd", query = "SELECT t FROM TReserve t WHERE t.userCd = :userCd"),
    @NamedQuery(name = "TReserve.findBySrNo", query = "SELECT t FROM TReserve t WHERE t.srNo = :srNo"),
    @NamedQuery(name = "TReserve.findByHoldDt", query = "SELECT t FROM TReserve t WHERE t.holdDt = :holdDt"),
    @NamedQuery(name = "TReserve.findByResvDtBtwn", query = "SELECT t FROM TReserve t WHERE t.resvDt BETWEEN ?1 AND ?2"),
    @NamedQuery(name = "TReserve.countByMemCd", query = "SELECT COUNT(t) FROM TReserve t WHERE t.mMember.memCd = :memCd"),
    @NamedQuery(name = "TReserve.findByRecNoAndMaxSrNo", query = "SELECT t FROM TReserve t WHERE t.tReservePK.recordNo = :recordNo GROUP BY t.tReservePK.recordNo HAVING t.srNo = MAX(t.srNo)"),
    @NamedQuery(name = "TReserve.findByRecNoAndMinSrNo", query = "SELECT t FROM TReserve t WHERE t.tReservePK.recordNo = :recordNo GROUP BY t.tReservePK.recordNo HAVING t.srNo = MIN(t.srNo)"),
    @NamedQuery(name = "removeByMemcdAndRecordNo", query = "DELETE  FROM TReserve t WHERE t.tReservePK.memCd = ?1 AND  t.tReservePK.recordNo = ?2")
})
public class TReserve implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TReservePK tReservePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "resv_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resvDt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "user_cd")
    private String userCd;
    @Column(name = "sr_no")
    private Integer srNo;
    @Column(name = "hold_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date holdDt;
    @JoinColumn(name = "mem_cd", referencedColumnName = "mem_cd", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MMember mMember;

    public TReserve() {
    }

    public TReserve(TReservePK tReservePK) {
        this.tReservePK = tReservePK;
    }

    public TReserve(TReservePK tReservePK, Date resvDt, String userCd) {
        this.tReservePK = tReservePK;
        this.resvDt = resvDt;
        this.userCd = userCd;
    }

    public TReserve(String memCd, int recordNo) {
        this.tReservePK = new TReservePK(memCd, recordNo);
    }

    public TReservePK getTReservePK() {
        return tReservePK;
    }

    public void setTReservePK(TReservePK tReservePK) {
        this.tReservePK = tReservePK;
    }

    public Date getResvDt() {
        return resvDt;
    }

    public void setResvDt(Date resvDt) {
        this.resvDt = resvDt;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public Date getHoldDt() {
        return holdDt;
    }

    public void setHoldDt(Date holdDt) {
        this.holdDt = holdDt;
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
        hash += (tReservePK != null ? tReservePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TReserve)) {
            return false;
        }
        TReserve other = (TReserve) object;
        if ((this.tReservePK == null && other.tReservePK != null) || (this.tReservePK != null && !this.tReservePK.equals(other.tReservePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TReserve[ tReservePK=" + tReservePK + " ]";
    }
    
}
