/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import soul.serial_master.SSupplierDetail;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "countrymaster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Countrymaster.findAll", query = "SELECT c FROM Countrymaster c"),
    @NamedQuery(name = "Countrymaster.findByCode", query = "SELECT c FROM Countrymaster c WHERE c.code = :code"),
    @NamedQuery(name = "Countrymaster.findByCountry", query = "SELECT c FROM Countrymaster c WHERE c.country = :country")})
public class Countrymaster implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Code")
    private String code;
    @Size(max = 200)
    @Column(name = "Country")
    private String country;

    public Countrymaster() {
    }

    public Countrymaster(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
        if (!(object instanceof Countrymaster)) {
            return false;
        }
        Countrymaster other = (Countrymaster) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.Countrymaster[ code=" + code + " ]";
    }
    
}
