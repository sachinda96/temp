/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "m_bkstatus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MBkstatus.findAll", query = "SELECT m FROM MBkstatus m"),
    @NamedQuery(name = "MBkstatus.findByBkIssueStat", query = "SELECT m FROM MBkstatus m WHERE m.bkIssueStat = :bkIssueStat"),
    @NamedQuery(name = "MBkstatus.findByStatusDscr", query = "SELECT m FROM MBkstatus m WHERE m.statusDscr = :statusDscr")})
public class MBkstatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "bk_issue_stat")
    private String bkIssueStat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "status_dscr")
    private String statusDscr;

    public MBkstatus() {
    }

    public MBkstatus(String bkIssueStat) {
        this.bkIssueStat = bkIssueStat;
    }

    public MBkstatus(String bkIssueStat, String statusDscr) {
        this.bkIssueStat = bkIssueStat;
        this.statusDscr = statusDscr;
    }

    public String getBkIssueStat() {
        return bkIssueStat;
    }

    public void setBkIssueStat(String bkIssueStat) {
        this.bkIssueStat = bkIssueStat;
    }

    public String getStatusDscr() {
        return statusDscr;
    }

    public void setStatusDscr(String statusDscr) {
        this.statusDscr = statusDscr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bkIssueStat != null ? bkIssueStat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MBkstatus)) {
            return false;
        }
        MBkstatus other = (MBkstatus) object;
        if ((this.bkIssueStat == null && other.bkIssueStat != null) || (this.bkIssueStat != null && !this.bkIssueStat.equals(other.bkIssueStat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.MBkstatus[ bkIssueStat=" + bkIssueStat + " ]";
    }
    
}
