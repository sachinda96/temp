/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.system_setting;

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
@Table(name = "admin_mail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdminMail.findAll", query = "SELECT a FROM AdminMail a"),
    @NamedQuery(name = "AdminMail.findByDomainName", query = "SELECT a FROM AdminMail a WHERE a.domainName = :domainName"),
    @NamedQuery(name = "AdminMail.findBySmtp", query = "SELECT a FROM AdminMail a WHERE a.smtp = :smtp"),
    @NamedQuery(name = "AdminMail.findByEmail", query = "SELECT a FROM AdminMail a WHERE a.email = :email"),
    @NamedQuery(name = "AdminMail.findByUname", query = "SELECT a FROM AdminMail a WHERE a.uname = :uname"),
    @NamedQuery(name = "AdminMail.findByPwd", query = "SELECT a FROM AdminMail a WHERE a.pwd = :pwd")})
public class AdminMail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DomainName")
    private String domainName;
    @Size(max = 100)
    @Column(name = "SMTP")
    private String smtp;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 200)
    @Column(name = "Email")
    private String email;
    @Size(max = 100)
    @Column(name = "Uname")
    private String uname;
    @Size(max = 100)
    @Column(name = "Pwd")
    private String pwd;
    @Column(name = "Port")
    private String port;

    public AdminMail() {
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    
    

    public AdminMail(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (domainName != null ? domainName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdminMail)) {
            return false;
        }
        AdminMail other = (AdminMail) object;
        if ((this.domainName == null && other.domainName != null) || (this.domainName != null && !this.domainName.equals(other.domainName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.system_setting.AdminMail[ domainName=" + domainName + " ]";
    }
    
}
