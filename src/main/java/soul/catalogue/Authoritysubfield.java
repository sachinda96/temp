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
@Table(name = "authoritysubfield")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Authoritysubfield.findAll", query = "SELECT a FROM Authoritysubfield a"),
    @NamedQuery(name = "Authoritysubfield.findByTag", query = "SELECT a FROM Authoritysubfield a WHERE a.authoritysubfieldPK.tag = :tag"),
    @NamedQuery(name = "Authoritysubfield.findBySubfield", query = "SELECT a FROM Authoritysubfield a WHERE a.authoritysubfieldPK.subfield = :subfield"),
    @NamedQuery(name = "Authoritysubfield.findByIsRepeatable", query = "SELECT a FROM Authoritysubfield a WHERE a.isRepeatable = :isRepeatable"),
    @NamedQuery(name = "Authoritysubfield.findByDescription", query = "SELECT a FROM Authoritysubfield a WHERE a.description = :description"),
    @NamedQuery(name = "Authoritysubfield.findByRemark", query = "SELECT a FROM Authoritysubfield a WHERE a.remark = :remark"),
    @NamedQuery(name = "Authoritysubfield.findByTagANDtemplate", query = "SELECT a FROM Authoritysubfield a WHERE a.authoritysubfieldPK.subfield in(SELECT k.authoritytemplatePK.subfield FROM Authoritytemplate k WHERE k.authoritytemplatePK.templateID = ?1  AND k.authoritytemplatePK.tag = ?2) AND a.authoritysubfieldPK.tag = ?3")}) 
  
public class Authoritysubfield implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AuthoritysubfieldPK authoritysubfieldPK;
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

    public Authoritysubfield() {
    }

    public Authoritysubfield(AuthoritysubfieldPK authoritysubfieldPK) {
        this.authoritysubfieldPK = authoritysubfieldPK;
    }

    public Authoritysubfield(AuthoritysubfieldPK authoritysubfieldPK, String isRepeatable, String description) {
        this.authoritysubfieldPK = authoritysubfieldPK;
        this.isRepeatable = isRepeatable;
        this.description = description;
    }

    public Authoritysubfield(String tag, String subfield) {
        this.authoritysubfieldPK = new AuthoritysubfieldPK(tag, subfield);
    }

    public AuthoritysubfieldPK getAuthoritysubfieldPK() {
        return authoritysubfieldPK;
    }

    public void setAuthoritysubfieldPK(AuthoritysubfieldPK authoritysubfieldPK) {
        this.authoritysubfieldPK = authoritysubfieldPK;
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
        hash += (authoritysubfieldPK != null ? authoritysubfieldPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Authoritysubfield)) {
            return false;
        }
        Authoritysubfield other = (Authoritysubfield) object;
        if ((this.authoritysubfieldPK == null && other.authoritysubfieldPK != null) || (this.authoritysubfieldPK != null && !this.authoritysubfieldPK.equals(other.authoritysubfieldPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.Authoritysubfield[ authoritysubfieldPK=" + authoritysubfieldPK + " ]";
    }
    
}
