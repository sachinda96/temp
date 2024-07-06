/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.userlog;

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
 * @author admin
 */
@Entity
@Table(name = "souluserlog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Souluserlog.findAll", query = "SELECT s FROM Souluserlog s")
    , @NamedQuery(name = "Souluserlog.findBySrno", query = "SELECT s FROM Souluserlog s WHERE s.srno = :srno")
    , @NamedQuery(name = "Souluserlog.findByUsername", query = "SELECT s FROM Souluserlog s WHERE s.username = :username")
    , @NamedQuery(name = "Souluserlog.findByLogTime", query = "SELECT s FROM Souluserlog s WHERE s.logTime = :logTime")
    , @NamedQuery(name = "Souluserlog.findByLogType", query = "SELECT s FROM Souluserlog s WHERE s.logType = :logType")
    , @NamedQuery(name = "Souluserlog.findByOriginIp", query = "SELECT s FROM Souluserlog s WHERE s.originIp = :originIp")
    , @NamedQuery(name = "Souluserlog.findByLogDescrption", query = "SELECT s FROM Souluserlog s WHERE s.logDescrption = :logDescrption")})
public class Souluserlog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "srno")
    private Integer srno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "log_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "log_type")
    private String logType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "origin_ip")
    private String originIp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6000)
    @Column(name = "log_descrption")
    private String logDescrption;

    public Souluserlog() {
    }

    public Souluserlog(Integer srno) {
        this.srno = srno;
    }

    public Souluserlog(Integer srno, String username, Date logTime, String logType, String originIp, String logDescrption) {
        this.srno = srno;
        this.username = username;
        this.logTime = logTime;
        this.logType = logType;
        this.originIp = originIp;
        this.logDescrption = logDescrption;
    }

    public Integer getSrno() {
        return srno;
    }

    public void setSrno(Integer srno) {
        this.srno = srno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getOriginIp() {
        return originIp;
    }

    public void setOriginIp(String originIp) {
        this.originIp = originIp;
    }

    public String getLogDescrption() {
        return logDescrption;
    }

    public void setLogDescrption(String logDescrption) {
        this.logDescrption = logDescrption;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (srno != null ? srno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Souluserlog)) {
            return false;
        }
        Souluserlog other = (Souluserlog) object;
        if ((this.srno == null && other.srno != null) || (this.srno != null && !this.srno.equals(other.srno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.userlog.Souluserlog[ srno=" + srno + " ]";
    }
    
}
