/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl;

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
 * @author admin
 */
@Entity
@Table(name = "s_parallel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SParallel.findAll", query = "SELECT s FROM SParallel s")
    , @NamedQuery(name = "SParallel.findBySPRecid", query = "SELECT s FROM SParallel s WHERE s.sPRecid = :sPRecid")
    , @NamedQuery(name = "SParallel.findBySPTitle", query = "SELECT s FROM SParallel s WHERE s.sPTitle = :sPTitle")
    , @NamedQuery(name = "SParallel.findBySPLanCd", query = "SELECT s FROM SParallel s WHERE s.sPLanCd = :sPLanCd")})
public class SParallel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 510)
    @Column(name = "s_p_recid")
    private String sPRecid;
    @Size(max = 510)
    @Column(name = "s_p_title")
    private String sPTitle;
    @Size(max = 510)
    @Column(name = "s_p_lan_cd")
    private String sPLanCd;

    public SParallel() {
    }

    public SParallel(String sPRecid) {
        this.sPRecid = sPRecid;
    }

    public String getSPRecid() {
        return sPRecid;
    }

    public void setSPRecid(String sPRecid) {
        this.sPRecid = sPRecid;
    }

    public String getSPTitle() {
        return sPTitle;
    }

    public void setSPTitle(String sPTitle) {
        this.sPTitle = sPTitle;
    }

    public String getSPLanCd() {
        return sPLanCd;
    }

    public void setSPLanCd(String sPLanCd) {
        this.sPLanCd = sPLanCd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sPRecid != null ? sPRecid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SParallel)) {
            return false;
        }
        SParallel other = (SParallel) object;
        if ((this.sPRecid == null && other.sPRecid != null) || (this.sPRecid != null && !this.sPRecid.equals(other.sPRecid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serialControl.SParallel[ sPRecid=" + sPRecid + " ]";
    }
    
}
