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
@Table(name = "t_memlog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TMemlog.findAll", query = "SELECT t FROM TMemlog t"),
    @NamedQuery(name = "TMemlog.findByLogID", query = "SELECT t FROM TMemlog t WHERE t.logID = :logID"),
    @NamedQuery(name = "TMemlog.findByMemCd", query = "SELECT t FROM TMemlog t WHERE t.memCd = :memCd"),
    @NamedQuery(name = "TMemlog.findByLogintime", query = "SELECT t FROM TMemlog t WHERE t.logintime = :logintime"),
    @NamedQuery(name = "TMemlog.findByLogouttime", query = "SELECT t FROM TMemlog t WHERE t.logouttime = :logouttime"),

    @NamedQuery(name = "TMemlog.findByLogouttimeISNULL", query = "SELECT t FROM TMemlog t WHERE t.logouttime IS NULL"),
    @NamedQuery(name = "TMemlog.findByLoginDateBetween", query = "SELECT t FROM TMemlog t WHERE t.logintime BETWEEN ?1 AND ?2"),
    @NamedQuery(name = "TMemlog.findByMemCdAndLogouttimeNULL", query = "SELECT t FROM TMemlog t WHERE t.memCd = :memCd AND t.logouttime IS NULL"),
    @NamedQuery(name = "TMemlog.findByLogouttimeNULL", query = "SELECT t FROM TMemlog t WHERE t.logouttime IS NULL"),
    @NamedQuery(name = "TMemlog.findByLogoutTimeAndMemCd", query = "SELECT t FROM TMemlog t WHERE t.logouttime IS NULL and t.memCd= :memCd")
})
public class TMemlog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "LogID")
    private String logID;
    @Size(max = 12)
    @Column(name = "mem_cd")
    private String memCd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Login_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logintime;
    @Column(name = "Logout_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logouttime;

    public TMemlog() {
    }

    public TMemlog(String logID) {
        this.logID = logID;
    }

    public TMemlog(String logID, Date logintime) {
        this.logID = logID;
        this.logintime = logintime;
    }

    public String getLogID() {
        return logID;
    }

    public void setLogID(String logID) {
        this.logID = logID;
    }

    public String getMemCd() {
        return memCd;
    }

    public void setMemCd(String memCd) {
        this.memCd = memCd;
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    public Date getLogouttime() {
        return logouttime;
    }

    public void setLogouttime(Date logouttime) {
        this.logouttime = logouttime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logID != null ? logID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TMemlog)) {
            return false;
        }
        TMemlog other = (TMemlog) object;
        if ((this.logID == null && other.logID != null) || (this.logID != null && !this.logID.equals(other.logID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TMemlog[ logID=" + logID + " ]";
    }
    
}
