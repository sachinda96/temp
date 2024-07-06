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
@Table(name = "authoritytemplatemain")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Authoritytemplatemain.findAll", query = "SELECT a FROM Authoritytemplatemain a"),
    @NamedQuery(name = "Authoritytemplatemain.findAllWithNoDefault", query = "SELECT a FROM Authoritytemplatemain a where a.isDefault = 'No'"),
    @NamedQuery(name = "Authoritytemplatemain.findByTemplateID", query = "SELECT a FROM Authoritytemplatemain a WHERE a.templateID = :templateID"),
    @NamedQuery(name = "Authoritytemplatemain.findByTemplateName", query = "SELECT a FROM Authoritytemplatemain a WHERE a.templateName = :templateName"),
    @NamedQuery(name = "Authoritytemplatemain.findByIsDefault", query = "SELECT a FROM Authoritytemplatemain a WHERE a.isDefault = :isDefault"),
    @NamedQuery(name = "Authoritytemplatemain.findByRemark", query = "SELECT a FROM Authoritytemplatemain a WHERE a.remark = :remark"),
    @NamedQuery(name = "Authoritytemplatemain.findByMaxTemplateId", query = "SELECT a FROM Authoritytemplatemain a where a.templateID = (SELECT MAX(CAST(a.templateID AS UNSIGNED)) FROM Authoritytemplatemain a)"),
    @NamedQuery(name = "Authoritytemplatemain.checkIfTemplateNameExists", query = "SELECT COUNT(a) FROM Authoritytemplatemain a WHERE a.templateName = :templateName")})
public class Authoritytemplatemain implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 18)
    @Column(name = "TemplateID")
    private String templateID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "TemplateName")
    private String templateName;
    @Size(max = 10)
    @Column(name = "IsDefault")
    private String isDefault;
    @Size(max = 4000)
    @Column(name = "Remark")
    private String remark;

    public Authoritytemplatemain() {
    }

    public Authoritytemplatemain(String templateID) {
        this.templateID = templateID;
    }

    public Authoritytemplatemain(String templateID, String templateName) {
        this.templateID = templateID;
        this.templateName = templateName;
    }

    public String getTemplateID() {
        return templateID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (templateID != null ? templateID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Authoritytemplatemain)) {
            return false;
        }
        Authoritytemplatemain other = (Authoritytemplatemain) object;
        if ((this.templateID == null && other.templateID != null) || (this.templateID != null && !this.templateID.equals(other.templateID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.Authoritytemplatemain[ templateID=" + templateID + " ]";
    }
    
}
