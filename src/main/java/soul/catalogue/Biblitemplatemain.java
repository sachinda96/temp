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
@Table(name = "biblitemplatemain")
@XmlRootElement

@NamedQueries({
    @NamedQuery(name = "Biblitemplatemain.findAll", query = "SELECT b FROM Biblitemplatemain b"),
    @NamedQuery(name = "Biblitemplatemain.findAllWithNoDefault", query = "SELECT b FROM Biblitemplatemain b where b.isDefault = 'No'"),
    @NamedQuery(name = "Biblitemplatemain.findByTemplateID", query = "SELECT b FROM Biblitemplatemain b WHERE b.templateID = :templateID"),
    @NamedQuery(name = "Biblitemplatemain.findByTemplateName", query = "SELECT b FROM Biblitemplatemain b WHERE b.templateName = :templateName"),
    @NamedQuery(name = "Biblitemplatemain.findByRemark", query = "SELECT b FROM Biblitemplatemain b WHERE b.remark = :remark"),
    @NamedQuery(name = "Biblitemplatemain.findByMaxTemplateId", query = "SELECT b FROM Biblitemplatemain b where b.templateID = (SELECT MAX(CAST(b.templateID AS UNSIGNED)) FROM Biblitemplatemain b)"),
    @NamedQuery(name = "Biblitemplatemain.checkIfTemplateNameExists", query = "SELECT COUNT(b) FROM Biblitemplatemain b WHERE b.templateName = :templateName")})
public class Biblitemplatemain implements Serializable {
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
    @Size(max = 4000)
    @Column(name = "Remark")
    private String remark;
    @Size(max = 10)
    @Column(name = "IsDefault")
    private String isDefault;

    public Biblitemplatemain() {
    }

    public Biblitemplatemain(String templateID) {
        this.templateID = templateID;
    }

    public Biblitemplatemain(String templateID, String templateName) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
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
        if (!(object instanceof Biblitemplatemain)) {
            return false;
        }
        Biblitemplatemain other = (Biblitemplatemain) object;
        if ((this.templateID == null && other.templateID != null) || (this.templateID != null && !this.templateID.equals(other.templateID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.Biblitemplatemain[ templateID=" + templateID + " ]";
    }
    
}
