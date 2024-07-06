/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author soullab
 */
@Embeddable
public class BiblisubfieldPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "Tag")
    private String tag;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "Subfield")
    private String subfield;

    public BiblisubfieldPK() {
    }

    public BiblisubfieldPK(String tag, String subfield) {
        this.tag = tag;
        this.subfield = subfield;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSubfield() {
        return subfield;
    }

    public void setSubfield(String subfield) {
        this.subfield = subfield;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tag != null ? tag.hashCode() : 0);
        hash += (subfield != null ? subfield.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BiblisubfieldPK)) {
            return false;
        }
        BiblisubfieldPK other = (BiblisubfieldPK) object;
        if ((this.tag == null && other.tag != null) || (this.tag != null && !this.tag.equals(other.tag))) {
            return false;
        }
        if ((this.subfield == null && other.subfield != null) || (this.subfield != null && !this.subfield.equals(other.subfield))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.BiblisubfieldPK[ tag=" + tag + ", subfield=" + subfield + " ]";
    }
    
}
