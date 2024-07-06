/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.xml.bind.annotation.XmlTransient;
import soul.acquisition.suggestions.ARequest;
import soul.catalogue.Location;
import soul.circulation.IllRqstmst;
import soul.circulation_master.MCtgryMedia;
import soul.serialControl.SRequest;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "libmaterials")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Libmaterials.findAll", query = "SELECT l FROM Libmaterials l"),
    @NamedQuery(name = "Libmaterials.findByCode", query = "SELECT l FROM Libmaterials l WHERE l.code = :code"),
    @NamedQuery(name = "Libmaterials.findByDescription", query = "SELECT l FROM Libmaterials l WHERE l.description = :description")})
public class Libmaterials implements Serializable {
    @OneToMany(mappedBy = "sRPhycd")
    private Collection<SRequest> sRequestCollection;
    @OneToMany(mappedBy = "aRPhycd")
    private Collection<ARequest> aRequestCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "media")
    private Collection<IllRqstmst> illRqstmstCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "libmaterials")
    private Collection<MCtgryMedia> mCtgryMediaCollection;
    @OneToMany(mappedBy = "material")
    private Collection<Location> locationCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "Code")
    private String code;
    @Size(max = 200)
    @Column(name = "Description")
    private String description;

    public Libmaterials() {
    }

    public Libmaterials(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Libmaterials)) {
            return false;
        }
        Libmaterials other = (Libmaterials) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.Libmaterials[ code=" + code + " ]";
    }

    @XmlTransient
    public Collection<Location> getLocationCollection() {
        return locationCollection;
    }

    public void setLocationCollection(Collection<Location> locationCollection) {
        this.locationCollection = locationCollection;
    }

    @XmlTransient
    public Collection<MCtgryMedia> getMCtgryMediaCollection() {
        return mCtgryMediaCollection;
    }

    public void setMCtgryMediaCollection(Collection<MCtgryMedia> mCtgryMediaCollection) {
        this.mCtgryMediaCollection = mCtgryMediaCollection;
    }

    @XmlTransient
    public Collection<IllRqstmst> getIllRqstmstCollection() {
        return illRqstmstCollection;
    }

    public void setIllRqstmstCollection(Collection<IllRqstmst> illRqstmstCollection) {
        this.illRqstmstCollection = illRqstmstCollection;
    }

    @XmlTransient
    public Collection<ARequest> getARequestCollection() {
        return aRequestCollection;
    }

    public void setARequestCollection(Collection<ARequest> aRequestCollection) {
        this.aRequestCollection = aRequestCollection;
    }

    @XmlTransient
    public Collection<SRequest> getSRequestCollection() {
        return sRequestCollection;
    }

    public void setSRequestCollection(Collection<SRequest> sRequestCollection) {
        this.sRequestCollection = sRequestCollection;
    }
}
