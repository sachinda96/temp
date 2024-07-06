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
@Table(name = "classificationscheme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Classificationscheme.findAll", query = "SELECT c FROM Classificationscheme c"),
    @NamedQuery(name = "Classificationscheme.findByClsfnScheme", query = "SELECT c FROM Classificationscheme c WHERE c.clsfnScheme = :clsfnScheme")})
public class Classificationscheme implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ClsfnScheme")
    private String clsfnScheme;

    public Classificationscheme() {
    }

    public Classificationscheme(String clsfnScheme) {
        this.clsfnScheme = clsfnScheme;
    }

    public String getClsfnScheme() {
        return clsfnScheme;
    }

    public void setClsfnScheme(String clsfnScheme) {
        this.clsfnScheme = clsfnScheme;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clsfnScheme != null ? clsfnScheme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Classificationscheme)) {
            return false;
        }
        Classificationscheme other = (Classificationscheme) object;
        if ((this.clsfnScheme == null && other.clsfnScheme != null) || (this.clsfnScheme != null && !this.clsfnScheme.equals(other.clsfnScheme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.classificationscheme[ clsfnScheme=" + clsfnScheme + " ]";
    }
    
}
