/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master;

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
@Table(name = "s_mode")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SMode.findAll", query = "SELECT s FROM SMode s"),
    @NamedQuery(name = "SMode.findBySModeCd", query = "SELECT s FROM SMode s WHERE s.sModeCd = :sModeCd"),
    @NamedQuery(name = "SMode.findBySModeDesc", query = "SELECT s FROM SMode s WHERE s.sModeDesc = :sModeDesc"),
    @NamedQuery(name = "SMode.findBySModeDescLike", query = "SELECT s FROM SMode s WHERE s.sModeDesc like :sModeDesc")})
public class SMode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 510)
    @Column(name = "s_mode_cd")
    private String sModeCd;
    @Size(max = 510)
    @Column(name = "s_mode_desc")
    private String sModeDesc;

    public SMode() {
    }

    public SMode(String sModeCd) {
        this.sModeCd = sModeCd;
    }

    public String getSModeCd() {
        return sModeCd;
    }

    public void setSModeCd(String sModeCd) {
        this.sModeCd = sModeCd;
    }

    public String getSModeDesc() {
        return sModeDesc;
    }

    public void setSModeDesc(String sModeDesc) {
        this.sModeDesc = sModeDesc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sModeCd != null ? sModeCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SMode)) {
            return false;
        }
        SMode other = (SMode) object;
        if ((this.sModeCd == null && other.sModeCd != null) || (this.sModeCd != null && !this.sModeCd.equals(other.sModeCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serial_master.SMode[ sModeCd=" + sModeCd + " ]";
    }
    
}
