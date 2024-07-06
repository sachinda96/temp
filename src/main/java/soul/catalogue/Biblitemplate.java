/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "biblitemplate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Biblitemplate.findAll", query = "SELECT b FROM Biblitemplate b"),
    @NamedQuery(name = "Biblitemplate.sortByTag", query = "SELECT b FROM Biblitemplate b ORDER BY b.biblitemplatePK.tag "),
    @NamedQuery(name = "Biblitemplate.findByTemplateID", query = "SELECT b FROM Biblitemplate b WHERE b.biblitemplatePK.templateID = :templateID  ORDER BY b.biblitemplatePK.tag"),
    @NamedQuery(name = "Biblitemplate.findTagByTemplateId", query = "SELECT b FROM Biblitemplate b WHERE b.biblitemplatePK.templateID = :templateID  group by b.biblitemplatePK.tag ORDER BY b.biblitemplatePK.tag "),
    @NamedQuery(name = "Biblitemplate.findSubfieldByTemplateId", query = "SELECT b FROM Biblitemplate b WHERE b.biblitemplatePK.templateID = ?1  and b.biblitemplatePK.tag = ?2"),
    @NamedQuery(name = "Biblitemplate.findBySRNo", query = "SELECT b FROM Biblitemplate b WHERE b.biblitemplatePK.sRNo = :sRNo"),
    @NamedQuery(name = "Biblitemplate.findByTag", query = "SELECT b FROM Biblitemplate b WHERE b.biblitemplatePK.tag = :tag"),
    @NamedQuery(name = "Biblitemplate.findBySubfield", query = "SELECT b FROM Biblitemplate b WHERE b.biblitemplatePK.subfield = :subfield"),
    @NamedQuery(name = "Biblitemplate.findByMaxSrNo", query = "SELECT b FROM Biblitemplate b WHERE b.biblitemplatePK.sRNo = ((SELECT MAX(b.biblitemplatePK.sRNo) FROM Biblitemplate b) )"),
    @NamedQuery(name = "Biblitemplate.findByDescription", query = "SELECT b FROM Biblitemplate b WHERE b.description = :description"),
    @NamedQuery(name = "Biblitemplate.deleteByTemplateId", query = "Delete FROM Biblitemplate b where b.biblitemplatePK.templateID = :templateID")
})
public class Biblitemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BiblitemplatePK biblitemplatePK;
    @Size(max = 512)
    @Column(name = "Description")
    private String description;

    public Biblitemplate() {
    }

    public Biblitemplate(BiblitemplatePK biblitemplatePK) {
        this.biblitemplatePK = biblitemplatePK;
    }

    public Biblitemplate(String templateID, String sRNo, String tag, String subfield) {
        this.biblitemplatePK = new BiblitemplatePK(templateID, sRNo, tag, subfield);
    }

    public BiblitemplatePK getBiblitemplatePK() {
        return biblitemplatePK;
    }

    public void setBiblitemplatePK(BiblitemplatePK biblitemplatePK) {
        this.biblitemplatePK = biblitemplatePK;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (biblitemplatePK != null ? biblitemplatePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Biblitemplate)) {
            return false;
        }
        Biblitemplate other = (Biblitemplate) object;
        if ((this.biblitemplatePK == null && other.biblitemplatePK != null) || (this.biblitemplatePK != null && !this.biblitemplatePK.equals(other.biblitemplatePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.Biblitemplate[ biblitemplatePK=" + biblitemplatePK + " ]";
    }
    
}
