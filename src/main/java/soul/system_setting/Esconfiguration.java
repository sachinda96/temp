/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.system_setting;

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
 * @author admin
 */
@Entity
@Table(name = "esconfiguration")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Esconfiguration.findAll", query = "SELECT e FROM Esconfiguration e")
    , @NamedQuery(name = "Esconfiguration.findById", query = "SELECT e FROM Esconfiguration e WHERE e.id = :id")
    , @NamedQuery(name = "Esconfiguration.findByIdentity", query = "SELECT e FROM Esconfiguration e WHERE e.identity = :identity")
    , @NamedQuery(name = "Esconfiguration.findByDescription", query = "SELECT e FROM Esconfiguration e WHERE e.description = :description")
    , @NamedQuery(name = "Esconfiguration.findByVdata", query = "SELECT e FROM Esconfiguration e WHERE e.vdata = :vdata")})
public class Esconfiguration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "identity")
    private String identity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5000)
    @Column(name = "vdata")
    private String vdata;

    public Esconfiguration() {
    }

    public Esconfiguration(Integer id) {
        this.id = id;
    }

    public Esconfiguration(Integer id, String identity, String description, String vdata) {
        this.id = id;
        this.identity = identity;
        this.description = description;
        this.vdata = vdata;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVdata() {
        return vdata;
    }

    public void setVdata(String vdata) {
        this.vdata = vdata;
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
        if (!(object instanceof Esconfiguration)) {
            return false;
        }
        Esconfiguration other = (Esconfiguration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.system_setting.Esconfiguration[ id=" + id + " ]";
    }
    
}
