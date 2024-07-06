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
public class BiblitemplatePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 18)
    @Column(name = "TemplateID")
    private String templateID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 18)
    @Column(name = "SRNo")
    private String sRNo;
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

    public BiblitemplatePK() {
    }

    public BiblitemplatePK(String templateID, String sRNo, String tag, String subfield) {
        this.templateID = templateID;
        this.sRNo = sRNo;
        this.tag = tag;
        this.subfield = subfield;
    }

    public String getTemplateID() {
        return templateID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    public String getSRNo() {
        return sRNo;
    }

    public void setSRNo(String sRNo) {
        this.sRNo = sRNo;
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
        hash += (templateID != null ? templateID.hashCode() : 0);
        hash += (sRNo != null ? sRNo.hashCode() : 0);
        hash += (tag != null ? tag.hashCode() : 0);
        hash += (subfield != null ? subfield.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BiblitemplatePK)) {
            return false;
        }
        BiblitemplatePK other = (BiblitemplatePK) object;
        if ((this.templateID == null && other.templateID != null) || (this.templateID != null && !this.templateID.equals(other.templateID))) {
            return false;
        }
        if ((this.sRNo == null && other.sRNo != null) || (this.sRNo != null && !this.sRNo.equals(other.sRNo))) {
            return false;
        }
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
        return "soul.catalogue.BiblitemplatePK[ templateID=" + templateID + ", sRNo=" + sRNo + ", tag=" + tag + ", subfield=" + subfield + " ]";
    }
    
}
