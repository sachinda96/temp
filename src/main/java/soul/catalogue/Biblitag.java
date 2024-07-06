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
@Table(name = "biblitag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Biblitag.findAll", query = "SELECT b FROM Biblitag b"),
    @NamedQuery(name = "Biblitag.findByTag", query = "SELECT b FROM Biblitag b WHERE b.tag = :tag"),
    @NamedQuery(name = "Biblitag.findByIsRepeatable", query = "SELECT b FROM Biblitag b WHERE b.isRepeatable = :isRepeatable"),
    @NamedQuery(name = "Biblitag.findByIndicators", query = "SELECT b FROM Biblitag b WHERE b.indicators = :indicators"),
    @NamedQuery(name = "Biblitag.findBySubfield", query = "SELECT b FROM Biblitag b WHERE b.subfield = :subfield"),
    @NamedQuery(name = "Biblitag.findByDescription", query = "SELECT b FROM Biblitag b WHERE b.description = :description"),
    @NamedQuery(name = "Biblitag.findByRemark", query = "SELECT b FROM Biblitag b WHERE b.remark = :remark")})
public class Biblitag implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "Tag")
    private String tag;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "IsRepeatable")
    private String isRepeatable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "Indicators")
    private String indicators;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "Subfield")
    private String subfield;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "Description")
    private String description;
    @Size(max = 4000)
    @Column(name = "Remark")
    private String remark;

    public Biblitag() {
    }

    public Biblitag(String tag) {
        this.tag = tag;
    }

    public Biblitag(String tag, String isRepeatable, String indicators, String subfield, String description) {
        this.tag = tag;
        this.isRepeatable = isRepeatable;
        this.indicators = indicators;
        this.subfield = subfield;
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIsRepeatable() {
        return isRepeatable;
    }

    public void setIsRepeatable(String isRepeatable) {
        this.isRepeatable = isRepeatable;
    }

    public String getIndicators() {
        return indicators;
    }

    public void setIndicators(String indicators) {
        this.indicators = indicators;
    }

    public String getSubfield() {
        return subfield;
    }

    public void setSubfield(String subfield) {
        this.subfield = subfield;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        hash += (tag != null ? tag.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Biblitag)) {
            return false;
        }
        Biblitag other = (Biblitag) object;
        if ((this.tag == null && other.tag != null) || (this.tag != null && !this.tag.equals(other.tag))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.Biblitag[ tag=" + tag + " ]";
    }
    
}
