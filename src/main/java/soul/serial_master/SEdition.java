/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master;

import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlTransient;
import soul.serialControl.SRequest;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "s_edition")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SEdition.findAll", query = "SELECT s FROM SEdition s"),
    @NamedQuery(name = "SEdition.findBySEdType", query = "SELECT s FROM SEdition s WHERE s.sEdType = :sEdType"),
    @NamedQuery(name = "SEdition.findBySEdName", query = "SELECT s FROM SEdition s WHERE s.sEdName = :sEdName")})
public class SEdition implements Serializable {
    @OneToMany(mappedBy = "sREdition")
    private Collection<SRequest> sRequestCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "s_ed_type")
    private String sEdType;
    @Size(max = 510)
    @Column(name = "s_ed_name")
    private String sEdName;

    public SEdition() {
    }

    public SEdition(String sEdType) {
        this.sEdType = sEdType;
    }

    public String getSEdType() {
        return sEdType;
    }

    public void setSEdType(String sEdType) {
        this.sEdType = sEdType;
    }

    public String getSEdName() {
        return sEdName;
    }

    public void setSEdName(String sEdName) {
        this.sEdName = sEdName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sEdType != null ? sEdType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SEdition)) {
            return false;
        }
        SEdition other = (SEdition) object;
        if ((this.sEdType == null && other.sEdType != null) || (this.sEdType != null && !this.sEdType.equals(other.sEdType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serial_master.SEdition[ sEdType=" + sEdType + " ]";
    }

    @XmlTransient
    public Collection<SRequest> getSRequestCollection() {
        return sRequestCollection;
    }

    public void setSRequestCollection(Collection<SRequest> sRequestCollection) {
        this.sRequestCollection = sRequestCollection;
    }
    
}
