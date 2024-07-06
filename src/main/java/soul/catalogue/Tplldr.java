/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "tplldr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tplldr.findAll", query = "SELECT t FROM Tplldr t"),
    @NamedQuery(name = "Tplldr.findById", query = "SELECT t FROM Tplldr t WHERE t.id = :id"),
    @NamedQuery(name = "Tplldr.findByTName", query = "SELECT t FROM Tplldr t WHERE t.tName = :tName"),
    @NamedQuery(name = "Tplldr.findByTValue", query = "SELECT t FROM Tplldr t WHERE t.tValue = :tValue"),
    @NamedQuery(name = "Tplldr.findByIsDefault", query = "SELECT t FROM Tplldr t WHERE t.isDefault = :isDefault"),
    @NamedQuery(name = "Tplldr.checkIfTemplateNameExists", query = "SELECT COUNT(t) FROM Tplldr t WHERE t.tName = :tName")})
public class Tplldr implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 510)
    @Column(name = "TName")
    private String tName;
    @Size(max = 48)
    @Column(name = "TValue")
    private String tValue;
    @Size(max = 100)
    @Column(name = "IsDefault")
    private String isDefault;

    public Tplldr() {
    }

    public Tplldr(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTName() {
        return tName;
    }

    public void setTName(String tName) {
        this.tName = tName;
    }

    public String getTValue() {
        return tValue;
    }

    public void setTValue(String tValue) {
        this.tValue = tValue;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tplldr)) {
            return false;
        }
        Tplldr other = (Tplldr) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.Tplldr[ id=" + id + " ]";
    }
    
}
