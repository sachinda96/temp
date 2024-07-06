/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master;

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
@Table(name = "languagemaster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Languagemaster.findAll", query = "SELECT l FROM Languagemaster l"),
    @NamedQuery(name = "Languagemaster.findByBibLanguage", query = "SELECT l FROM Languagemaster l WHERE l.bibLanguage = :bibLanguage"),
    @NamedQuery(name = "Languagemaster.findByCode", query = "SELECT l FROM Languagemaster l WHERE l.code = :code")})
public class Languagemaster implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 100)
    @Column(name = "BibLanguage")
    private String bibLanguage;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Code")
    private String code;

    public Languagemaster() {
    }

    public Languagemaster(String code) {
        this.code = code;
    }

    public String getBibLanguage() {
        return bibLanguage;
    }

    public void setBibLanguage(String bibLanguage) {
        this.bibLanguage = bibLanguage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Languagemaster)) {
            return false;
        }
        Languagemaster other = (Languagemaster) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.Languagemaster[ code=" + code + " ]";
    }
    
}
