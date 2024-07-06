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
@Table(name = "t_issue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TIssue.findAll", query = "SELECT t FROM TIssue t"),
    @NamedQuery(name = "TIssue.findByMemCd", query = "SELECT t FROM TIssue t WHERE t.tIssuePK.memCd = :memCd"),
    @NamedQuery(name = "TIssue.findByAccNo", query = "SELECT t FROM TIssue t WHERE t.tIssuePK.accNo = :accNo"),
    @NamedQuery(name = "TIssue.findByIssDt", query = "SELECT t FROM TIssue t WHERE t.issDt = :issDt"),
    @NamedQuery(name = "TIssue.findByIssDtBetween", query = "SELECT t FROM TIssue t WHERE t.issDt BETWEEN ?1 AND ?2"),
    @NamedQuery(name = "TIssue.findByUserCd", query = "SELECT t FROM TIssue t WHERE t.userCd = :userCd"),
    @NamedQuery(name = "TIssue.findByDueDtBetween", query = "SELECT t FROM TIssue t WHERE t.dueDt BETWEEN ?1 AND ?2"),
    @NamedQuery(name = "TIssue.countByMemCd", query = "SELECT COUNT(t) FROM TIssue t WHERE t.mMember.memCd = :memCd"),
    @NamedQuery(name = "TIssue.removeByMemcdAndAccNo", query = "DELETE FROM TIssue t WHERE t.mMember.memCd = ?1 AND t.tIssuePK.accNo =  ?2"),
   // @NamedQuery(name = "TIssue.getdetails" , query = "SELECT t from TIssue t WHERE t.mMember.memInst = ?1 and t.mMember.memDept = ?2 and t.mMember.memDegree = ?3"),
})



/*
 select * from m_member,t_issue where m_member.mem_cd = t_issue.mem_cd "
                + "and m_member.mem_inst = '" + memberInstitute + "' "
                + " and m_member.mem_dept = '" + memberDepartment + "' "
                + "and m_member.mem_degree = '" + memberDegree + "'
 */
public class TIssue implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TIssuePK tIssuePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iss_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issDt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "user_cd")
    private String userCd;
    @Column(name = "due_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDt;
    @JoinColumn(name = "mem_cd", referencedColumnName = "mem_cd", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MMember mMember;
    

    public TIssue() {
    }

    public TIssue(TIssuePK tIssuePK) {
        this.tIssuePK = tIssuePK;
    }

    public TIssue(TIssuePK tIssuePK, Date issDt, String userCd) {
        this.tIssuePK = tIssuePK;
        this.issDt = issDt;
        this.userCd = userCd;
    }

    public TIssue(String memCd, String accNo) {
        this.tIssuePK = new TIssuePK(memCd, accNo);
    }

    public TIssuePK getTIssuePK() {
        return tIssuePK;
    }

    public void setTIssuePK(TIssuePK tIssuePK) {
        this.tIssuePK = tIssuePK;
    }

    public Date getIssDt() {
        return issDt;
    }

    public void setIssDt(Date issDt) {
        this.issDt = issDt;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public Date getDueDt() {
        return dueDt;
    }

    public void setDueDt(Date dueDt) {
        this.dueDt = dueDt;
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
        hash += (tIssuePK != null ? tIssuePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TIssue)) {
            return false;
        }
        TIssue other = (TIssue) object;
        if ((this.tIssuePK == null && other.tIssuePK != null) || (this.tIssuePK != null && !this.tIssuePK.equals(other.tIssuePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TIssue[ tIssuePK=" + tIssuePK + " ]";
    }
    
}
