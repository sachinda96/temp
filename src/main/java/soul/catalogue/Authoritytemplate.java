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
@Table(name = "authoritytemplate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Authoritytemplate.findAll", query = "SELECT a FROM Authoritytemplate a"),
    @NamedQuery(name = "Authoritytemplate.findByTemplateID", query = "SELECT a FROM Authoritytemplate a WHERE a.authoritytemplatePK.templateID = :templateID ORDER BY a.authoritytemplatePK.tag"),
    @NamedQuery(name = "Authoritytemplate.findTagByTemplateId", query = "SELECT a FROM Authoritytemplate a WHERE a.authoritytemplatePK.templateID = :templateID  group by a.authoritytemplatePK.tag ORDER BY a.authoritytemplatePK.tag "),
    @NamedQuery(name = "Authoritytemplate.findBySRNo", query = "SELECT a FROM Authoritytemplate a WHERE a.authoritytemplatePK.sRNo = :sRNo"),
    @NamedQuery(name = "Authoritytemplate.findByTag", query = "SELECT a FROM Authoritytemplate a WHERE a.authoritytemplatePK.tag = :tag"),
    @NamedQuery(name = "Authoritytemplate.findBySubfield", query = "SELECT a FROM Authoritytemplate a WHERE a.authoritytemplatePK.subfield = :subfield"),
    @NamedQuery(name = "Authoritytemplate.findByDescription", query = "SELECT a FROM Authoritytemplate a WHERE a.description = :description"),
    @NamedQuery(name = "Authoritytemplate.deleteByTemplateId", query = "Delete FROM Authoritytemplate a where a.authoritytemplatePK.templateID = :templateID")})
public class Authoritytemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AuthoritytemplatePK authoritytemplatePK;
    @Size(max = 512)
    @Column(name = "Description")
    private String description;

    public Authoritytemplate() {
    }

    public Authoritytemplate(AuthoritytemplatePK authoritytemplatePK) {
        this.authoritytemplatePK = authoritytemplatePK;
    }

    public Authoritytemplate(String templateID, String sRNo, String tag, String subfield) {
        this.authoritytemplatePK = new AuthoritytemplatePK(templateID, sRNo, tag, subfield);
    }

    public AuthoritytemplatePK getAuthoritytemplatePK() {
        return authoritytemplatePK;
    }

    public void setAuthoritytemplatePK(AuthoritytemplatePK authoritytemplatePK) {
        this.authoritytemplatePK = authoritytemplatePK;
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
        hash += (authoritytemplatePK != null ? authoritytemplatePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Authoritytemplate)) {
            return false;
        }
        Authoritytemplate other = (Authoritytemplate) object;
        if ((this.authoritytemplatePK == null && other.authoritytemplatePK != null) || (this.authoritytemplatePK != null && !this.authoritytemplatePK.equals(other.authoritytemplatePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.Authoritytemplate[ authoritytemplatePK=" + authoritytemplatePK + " ]";
    }
    
}
