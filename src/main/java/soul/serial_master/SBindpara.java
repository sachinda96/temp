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
@Table(name = "s_bindpara")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SBindpara.findAll", query = "SELECT s FROM SBindpara s"),
    @NamedQuery(name = "SBindpara.findBySBindCd", query = "SELECT s FROM SBindpara s WHERE s.sBindCd = :sBindCd"),
    @NamedQuery(name = "SBindpara.getItemBindingDetails", query = "SELECT s FROM SBindpara s WHERE s.sBindCd LIKE :sBindCd"),
    @NamedQuery(name = "SBindpara.findBySBindDesc", query = "SELECT s.sBindDesc FROM SBindpara s WHERE s.sBindDesc = :sBindDesc")})
public class SBindpara implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 510)
    @Column(name = "s_bind_cd")
    private String sBindCd;
    @Size(max = 510)
    @Column(name = "s_bind_desc")
    private String sBindDesc;

    public SBindpara() {
    }

    public SBindpara(String sBindCd) {
        this.sBindCd = sBindCd;
    }

    public String getSBindCd() {
        return sBindCd;
    }

    public void setSBindCd(String sBindCd) {
        this.sBindCd = sBindCd;
    }

    public String getSBindDesc() {
        return sBindDesc;
    }

    public void setSBindDesc(String sBindDesc) {
        this.sBindDesc = sBindDesc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sBindCd != null ? sBindCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SBindpara)) {
            return false;
        }
        SBindpara other = (SBindpara) object;
        if ((this.sBindCd == null && other.sBindCd != null) || (this.sBindCd != null && !this.sBindCd.equals(other.sBindCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serial_master.SBindpara[ sBindCd=" + sBindCd + " ]";
    }
    
}
