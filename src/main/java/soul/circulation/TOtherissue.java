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
@Table(name = "t_otherissue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TOtherissue.findAll", query = "SELECT t FROM TOtherissue t"),
    @NamedQuery(name = "TOtherissue.findByMemCd", query = "SELECT t FROM TOtherissue t WHERE t.tOtherissuePK.memCd = :memCd"),
    @NamedQuery(name = "TOtherissue.findByAccNo", query = "SELECT t FROM TOtherissue t WHERE t.tOtherissuePK.accNo = :accNo"),
    @NamedQuery(name = "TOtherissue.findByIssueReson", query = "SELECT t FROM TOtherissue t WHERE t.issueReson = :issueReson"),
    @NamedQuery(name = "TOtherissue.findByIssueRemarks", query = "SELECT t FROM TOtherissue t WHERE t.issueRemarks = :issueRemarks"),
    @NamedQuery(name = "TOtherissue.findByIssueDate", query = "SELECT t FROM TOtherissue t WHERE t.issueDate = :issueDate"),
    @NamedQuery(name = "TOtherissue.findByDueDate", query = "SELECT t FROM TOtherissue t WHERE t.dueDate = :dueDate"),
    @NamedQuery(name = "TOtherissue.removeByMemcdAndAccNo", query = "DELETE FROM TOtherissue t WHERE t.tOtherissuePK.memCd = ?1 and t.tOtherissuePK.accNo = ?2 ")
})
public class TOtherissue implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TOtherissuePK tOtherissuePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "Issue_Reson")
    private String issueReson;
    @Size(max = 50)
    @Column(name = "Issue_Remarks")
    private String issueRemarks;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Issue_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "due_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    @JoinColumn(name = "mem_cd", referencedColumnName = "mem_cd", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MMember mMember;

    public TOtherissue() {
    }

    public TOtherissue(TOtherissuePK tOtherissuePK) {
        this.tOtherissuePK = tOtherissuePK;
    }

    public TOtherissue(TOtherissuePK tOtherissuePK, String issueReson, Date issueDate, Date dueDate) {
        this.tOtherissuePK = tOtherissuePK;
        this.issueReson = issueReson;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public TOtherissue(String memCd, String accNo) {
        this.tOtherissuePK = new TOtherissuePK(memCd, accNo);
    }

    public TOtherissuePK getTOtherissuePK() {
        return tOtherissuePK;
    }

    public void setTOtherissuePK(TOtherissuePK tOtherissuePK) {
        this.tOtherissuePK = tOtherissuePK;
    }

    public String getIssueReson() {
        return issueReson;
    }

    public void setIssueReson(String issueReson) {
        this.issueReson = issueReson;
    }

    public String getIssueRemarks() {
        return issueRemarks;
    }

    public void setIssueRemarks(String issueRemarks) {
        this.issueRemarks = issueRemarks;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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
        hash += (tOtherissuePK != null ? tOtherissuePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TOtherissue)) {
            return false;
        }
        TOtherissue other = (TOtherissue) object;
        if ((this.tOtherissuePK == null && other.tOtherissuePK != null) || (this.tOtherissuePK != null && !this.tOtherissuePK.equals(other.tOtherissuePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TOtherissue[ tOtherissuePK=" + tOtherissuePK + " ]";
    }
    
}
