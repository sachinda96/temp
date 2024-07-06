/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
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
@Table(name = "reserve_biblocation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReserveBiblocation.findAll", query = "SELECT r FROM ReserveBiblocation r"),
    @NamedQuery(name = "ReserveBiblocation.findByMemCd", query = "SELECT r FROM ReserveBiblocation r WHERE r.reserveBiblocationPK.memCd = :memCd"),
    @NamedQuery(name = "ReserveBiblocation.findByRecordNo", query = "SELECT r FROM ReserveBiblocation r WHERE r.reserveBiblocationPK.recordNo = :recordNo"),
    @NamedQuery(name = "ReserveBiblocation.findByResvDt", query = "SELECT r FROM ReserveBiblocation r WHERE r.resvDt = :resvDt"),
    @NamedQuery(name = "ReserveBiblocation.findByUserCd", query = "SELECT r FROM ReserveBiblocation r WHERE r.userCd = :userCd"),
    @NamedQuery(name = "ReserveBiblocation.findBySrNo", query = "SELECT r FROM ReserveBiblocation r WHERE r.srNo = :srNo"),
    @NamedQuery(name = "ReserveBiblocation.findByHoldDt", query = "SELECT r FROM ReserveBiblocation r WHERE r.holdDt = :holdDt"),
    @NamedQuery(name = "ReserveBiblocation.findByP852", query = "SELECT r FROM ReserveBiblocation r WHERE r.p852 = :p852"),
    @NamedQuery(name = "ReserveBiblocation.findByStatus", query = "SELECT r FROM ReserveBiblocation r WHERE r.status = :status"),
    @NamedQuery(name = "ReserveBiblocation.findByStatusDscr", query = "SELECT r FROM ReserveBiblocation r WHERE r.statusDscr = :statusDscr"),
    @NamedQuery(name = "ReserveBiblocation.findByMemFirstnm", query = "SELECT r FROM ReserveBiblocation r WHERE r.memFirstnm = :memFirstnm"),
    @NamedQuery(name = "ReserveBiblocation.findByMemMidnm", query = "SELECT r FROM ReserveBiblocation r WHERE r.memMidnm = :memMidnm"),
    @NamedQuery(name = "ReserveBiblocation.findByMemLstnm", query = "SELECT r FROM ReserveBiblocation r WHERE r.memLstnm = :memLstnm"),
    @NamedQuery(name = "ReserveBiblocation.findByMemPrmntphone", query = "SELECT r FROM ReserveBiblocation r WHERE r.memPrmntphone = :memPrmntphone"),
    @NamedQuery(name = "ReserveBiblocation.findByMemDept", query = "SELECT r FROM ReserveBiblocation r WHERE r.memDept = :memDept"),
    @NamedQuery(name = "ReserveBiblocation.findByFcltydeptdscr", query = "SELECT r FROM ReserveBiblocation r WHERE r.fcltydeptdscr = :fcltydeptdscr")})
public class ReserveBiblocation implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReserveBiblocationPK reserveBiblocationPK;
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
    private Long srNo;
    @Column(name = "hold_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date holdDt;
    @Lob
    @Size(max = 65535)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "p852")
    private String p852;
    @Lob
    @Size(max = 65535)
    @Column(name = "author")
    private String author;
    @Size(max = 50)
    @Column(name = "Status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "status_dscr")
    private String statusDscr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mem_firstnm")
    private String memFirstnm;
    @Size(max = 100)
    @Column(name = "mem_midnm")
    private String memMidnm;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mem_lstnm")
    private String memLstnm;
    @Size(max = 60)
    @Column(name = "mem_prmntphone")
    private String memPrmntphone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mem_dept")
    private int memDept;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "Fclty_dept_dscr")
    private String fcltydeptdscr;

    public ReserveBiblocation() {
    }

    public ReserveBiblocation(ReserveBiblocationPK reserveBiblocationPK) {
        this.reserveBiblocationPK = reserveBiblocationPK;
    }
    
    public ReserveBiblocation(String memCd, Long recordId) {
        this.reserveBiblocationPK = new ReserveBiblocationPK(memCd, recordId);
    }
    
    public ReserveBiblocationPK getReserveBiblocationPK() {
        return reserveBiblocationPK;
    }

    public void setReserveBiblocationPK(ReserveBiblocationPK reserveBiblocationPK) {
        this.reserveBiblocationPK = reserveBiblocationPK;
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

    public Long getSrNo() {
        return srNo;
    }

    public void setSrNo(Long srNo) {
        this.srNo = srNo;
    }

    public Date getHoldDt() {
        return holdDt;
    }

    public void setHoldDt(Date holdDt) {
        this.holdDt = holdDt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getP852() {
        return p852;
    }

    public void setP852(String p852) {
        this.p852 = p852;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDscr() {
        return statusDscr;
    }

    public void setStatusDscr(String statusDscr) {
        this.statusDscr = statusDscr;
    }

    public String getMemFirstnm() {
        return memFirstnm;
    }

    public void setMemFirstnm(String memFirstnm) {
        this.memFirstnm = memFirstnm;
    }

    public String getMemMidnm() {
        return memMidnm;
    }

    public void setMemMidnm(String memMidnm) {
        this.memMidnm = memMidnm;
    }

    public String getMemLstnm() {
        return memLstnm;
    }

    public void setMemLstnm(String memLstnm) {
        this.memLstnm = memLstnm;
    }

    public String getMemPrmntphone() {
        return memPrmntphone;
    }

    public void setMemPrmntphone(String memPrmntphone) {
        this.memPrmntphone = memPrmntphone;
    }

    public int getMemDept() {
        return memDept;
    }

    public void setMemDept(int memDept) {
        this.memDept = memDept;
    }

    public String getFcltydeptdscr() {
        return fcltydeptdscr;
    }

    public void setFcltydeptdscr(String fcltydeptdscr) {
        this.fcltydeptdscr = fcltydeptdscr;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.reserveBiblocationPK);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReserveBiblocation other = (ReserveBiblocation) obj;
        if (!Objects.equals(this.reserveBiblocationPK, other.reserveBiblocationPK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReserveBiblocation[" + "reserveBiblocationPK=" + reserveBiblocationPK + ']';
    }
    
}
