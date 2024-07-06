/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "s_binding_set")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SBindingSet.findAll", query = "SELECT s FROM SBindingSet s")
    , @NamedQuery(name = "SBindingSet.findBySBSetno", query = "SELECT s FROM SBindingSet s WHERE s.sBindingSetPK.sBSetno = :sBSetno")
    , @NamedQuery(name = "SBindingSet.findBySBVolno", query = "SELECT s FROM SBindingSet s WHERE s.sBindingSetPK.sBVolno = :sBVolno")
    , @NamedQuery(name = "SBindingSet.findBySBIssueno", query = "SELECT s FROM SBindingSet s WHERE s.sBindingSetPK.sBIssueno = :sBIssueno")
    , @NamedQuery(name = "SBindingSet.findBySBRecid", query = "SELECT s FROM SBindingSet s WHERE s.sBindingSetPK.sBRecid = :sBRecid")
    , @NamedQuery(name = "SBindingSet.deleteSet", query = "DELETE from SBindingSet s WHERE s.sBindingSetPK.sBSetno = :sBSetno")})
public class SBindingSet implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SBindingSetPK sBindingSetPK;

    public SBindingSet() {
    }

    public SBindingSet(SBindingSetPK sBindingSetPK) {
        this.sBindingSetPK = sBindingSetPK;
    }

    public SBindingSet(String sBSetno, String sBVolno, String sBIssueno, String sBRecid) {
        this.sBindingSetPK = new SBindingSetPK(sBSetno, sBVolno, sBIssueno, sBRecid);
    }

    public SBindingSetPK getSBindingSetPK() {
        return sBindingSetPK;
    }

    public void setSBindingSetPK(SBindingSetPK sBindingSetPK) {
        this.sBindingSetPK = sBindingSetPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sBindingSetPK != null ? sBindingSetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SBindingSet)) {
            return false;
        }
        SBindingSet other = (SBindingSet) object;
        if ((this.sBindingSetPK == null && other.sBindingSetPK != null) || (this.sBindingSetPK != null && !this.sBindingSetPK.equals(other.sBindingSetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serial_master.SBindingSet[ sBindingSetPK=" + sBindingSetPK + " ]";
    }
    
}
