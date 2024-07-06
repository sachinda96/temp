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
@Table(name = "autogenerate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Autogenerate.findAll", query = "SELECT a FROM Autogenerate a"),
    @NamedQuery(name = "Autogenerate.findById", query = "SELECT a FROM Autogenerate a WHERE a.id = :id"),
    @NamedQuery(name = "Autogenerate.findByName", query = "SELECT a FROM Autogenerate a WHERE a.name LIKE :name"),
    @NamedQuery(name = "Autogenerate.findByPrefix", query = "SELECT a FROM Autogenerate a WHERE a.prefix = :prefix"),
    @NamedQuery(name = "Autogenerate.findByIsActive", query = "SELECT a FROM Autogenerate a WHERE a.isActive = :isActive"),
    @NamedQuery(name = "Autogenerate.findByLastSeed", query = "SELECT a FROM Autogenerate a WHERE a.lastSeed = :lastSeed")})
public class Autogenerate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Prefix")
    private String prefix;
    @Column(name = "IsActive")
    private String isActive;
    @Column(name = "LastSeed")
    private Long lastSeed;

    public Autogenerate() {
    }

    public Autogenerate(Long id) {
        this.id = id;
    }

    public Autogenerate(Long id, String name, String prefix) {
        this.id = id;
        this.name = name;
        this.prefix = prefix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Long getLastSeed() {
        return lastSeed;
    }

    public void setLastSeed(Long lastSeed) {
        this.lastSeed = lastSeed;
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
        if (!(object instanceof Autogenerate)) {
            return false;
        }
        Autogenerate other = (Autogenerate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.Autogenerate[ id=" + id + " ]";
    }
    
}
