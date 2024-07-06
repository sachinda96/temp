/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "biblisubfield")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Biblisubfield.findAll", query = "SELECT b FROM Biblisubfield b"),
    @NamedQuery(name = "Biblisubfield.findByTag", query = "SELECT b FROM Biblisubfield b WHERE b.biblisubfieldPK.tag = :tag"),
    @NamedQuery(name = "Biblisubfield.findByTagANDtemplate", query = "SELECT b FROM Biblisubfield b WHERE b.biblisubfieldPK.subfield in(SELECT k.biblitemplatePK.subfield FROM Biblitemplate k WHERE k.biblitemplatePK.templateID = ?1  AND k.biblitemplatePK.tag = ?2) AND b.biblisubfieldPK.tag = ?3"), 
    @NamedQuery(name = "Biblisubfield.findByTagANDSbfld", query = "SELECT b FROM Biblisubfield b WHERE b.biblisubfieldPK.subfield IN ?1 AND b.biblisubfieldPK.tag = ?2"),
    @NamedQuery(name = "Biblisubfield.findBySubfield", query = "SELECT b FROM Biblisubfield b WHERE b.biblisubfieldPK.subfield = :subfield"),
    @NamedQuery(name = "Biblisubfield.findByIsRepeatable", query = "SELECT b FROM Biblisubfield b WHERE b.isRepeatable = :isRepeatable"),
    @NamedQuery(name = "Biblisubfield.findByDescription", query = "SELECT b FROM Biblisubfield b WHERE b.description = :description"),
    @NamedQuery(name = "Biblisubfield.findByRemark", query = "SELECT b FROM Biblisubfield b WHERE b.remark = :remark")})
public class Biblisubfield implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BiblisubfieldPK biblisubfieldPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "IsRepeatable")
    private String isRepeatable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "Description")
    private String description;
    @Size(max = 4000)
    @Column(name = "Remark")
    private String remark;

    public Biblisubfield() {
    }

    public Biblisubfield(BiblisubfieldPK biblisubfieldPK) {
        this.biblisubfieldPK = biblisubfieldPK;
    }

    public Biblisubfield(BiblisubfieldPK biblisubfieldPK, String isRepeatable, String description) {
        this.biblisubfieldPK = biblisubfieldPK;
        this.isRepeatable = isRepeatable;
        this.description = description;
    }

    public Biblisubfield(String tag, String subfield) {
        this.biblisubfieldPK = new BiblisubfieldPK(tag, subfield);
    }

    public BiblisubfieldPK getBiblisubfieldPK() {
        return biblisubfieldPK;
    }

    public void setBiblisubfieldPK(BiblisubfieldPK biblisubfieldPK) {
        this.biblisubfieldPK = biblisubfieldPK;
    }

    public String getIsRepeatable() {
        return isRepeatable;
    }

    public void setIsRepeatable(String isRepeatable) {
        this.isRepeatable = isRepeatable;
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
        hash += (biblisubfieldPK != null ? biblisubfieldPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Biblisubfield)) {
            return false;
        }
        Biblisubfield other = (Biblisubfield) object;
        if ((this.biblisubfieldPK == null && other.biblisubfieldPK != null) || (this.biblisubfieldPK != null && !this.biblisubfieldPK.equals(other.biblisubfieldPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.Biblisubfield[ biblisubfieldPK=" + biblisubfieldPK + " ]";
    }
    
}
