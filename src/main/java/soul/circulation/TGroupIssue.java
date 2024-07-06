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
@Table(name = "t_group_issue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TGroupIssue.findAll", query = "SELECT t FROM TGroupIssue t"),
    @NamedQuery(name = "TGroupIssue.findByGroupID", query = "SELECT t FROM TGroupIssue t WHERE t.tGroupIssuePK.groupID = :groupID"),
    @NamedQuery(name = "TGroupIssue.findByAccNo", query = "SELECT t FROM TGroupIssue t WHERE t.tGroupIssuePK.accNo = :accNo"),
    //@NamedQuery(name = "TGroupIssue.findByIssDt", query = "SELECT t FROM TGroupIssue t WHERE t.issDt = :issDt"),
    @NamedQuery(name = "TGroupIssue.findByDueDt", query = "SELECT t FROM TGroupIssue t WHERE t.dueDt = :dueDt"),
    @NamedQuery(name = "TGroupIssue.findByGroupIdAndAccNo", query = "SELECT t FROM TGroupIssue t WHERE t.tGroupIssuePK.groupID = ?1 and t.tGroupIssuePK.accNo = ?2"),
    @NamedQuery(name = "TGroupIssue.removeByAccNo", query = "DELETE FROM TGroupIssue t WHERE t.tGroupIssuePK.accNo = :accNo")
})
public class TGroupIssue implements Serializable {

    @Basic(optional = false)
    @NotNull
    //@Size(min = 1, max = 45)
    @Column(name = "status")
    private String bookStatus;
    @Column(name = "returndt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDt;
    @Column(name = "usercd")
    private String usercd;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TGroupIssuePK tGroupIssuePK;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "iss_dt")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date issDt;
    @Column(name = "due_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDt;

    public TGroupIssue() {
    }

    public TGroupIssue(TGroupIssuePK tGroupIssuePK) {
        this.tGroupIssuePK = tGroupIssuePK;
    }
   

    public TGroupIssue(int groupID, String accNo, Date issDt) {
        this.tGroupIssuePK = new TGroupIssuePK(groupID, accNo,issDt);
    }

    public TGroupIssuePK getTGroupIssuePK() {
        return tGroupIssuePK;
    }

    public void setTGroupIssuePK(TGroupIssuePK tGroupIssuePK) {
        this.tGroupIssuePK = tGroupIssuePK;
    }

//    public Date getIssDt() {
//        return issDt;
//    }
//
//    public void setIssDt(Date issDt) {
//        this.issDt = issDt;
//    }

    public Date getDueDt() {
        return dueDt;
    }

    public void setDueDt(Date dueDt) {
        this.dueDt = dueDt;
    }

    public String getUsercd() {
        return usercd;
    }

    public void setUsercd(String usercd) {
        this.usercd = usercd;
    }

//    public TGroupIssuePK gettGroupIssuePK() {
//        return tGroupIssuePK;
//    }
//
//    public void settGroupIssuePK(TGroupIssuePK tGroupIssuePK) {
//        this.tGroupIssuePK = tGroupIssuePK;
//    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tGroupIssuePK != null ? tGroupIssuePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TGroupIssue)) {
            return false;
        }
        TGroupIssue other = (TGroupIssue) object;
        if ((this.tGroupIssuePK == null && other.tGroupIssuePK != null) || (this.tGroupIssuePK != null && !this.tGroupIssuePK.equals(other.tGroupIssuePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TGroupIssue[ tGroupIssuePK=" + tGroupIssuePK + " ]";
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public Date getReturnDt() {
        return returnDt;
    }

    public void setReturnDt(Date returnDt) {
        this.returnDt = returnDt;
    }
    
}
