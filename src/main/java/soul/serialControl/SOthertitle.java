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
@Table(name = "s_othertitle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SOthertitle.findAll", query = "SELECT s FROM SOthertitle s")
    , @NamedQuery(name = "SOthertitle.findBySORecid", query = "SELECT s FROM SOthertitle s WHERE s.sORecid = :sORecid")
    , @NamedQuery(name = "SOthertitle.findBySOTitle", query = "SELECT s FROM SOthertitle s WHERE s.sOTitle = :sOTitle")})
public class SOthertitle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 510)
    @Column(name = "s_o_recid")
    private String sORecid;
    @Size(max = 510)
    @Column(name = "s_o_title")
    private String sOTitle;

    public SOthertitle() {
    }

    public SOthertitle(String sORecid) {
        this.sORecid = sORecid;
    }

    public String getSORecid() {
        return sORecid;
    }

    public void setSORecid(String sORecid) {
        this.sORecid = sORecid;
    }

    public String getSOTitle() {
        return sOTitle;
    }

    public void setSOTitle(String sOTitle) {
        this.sOTitle = sOTitle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sORecid != null ? sORecid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SOthertitle)) {
            return false;
        }
        SOthertitle other = (SOthertitle) object;
        if ((this.sORecid == null && other.sORecid != null) || (this.sORecid != null && !this.sORecid.equals(other.sORecid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serialControl.SOthertitle[ sORecid=" + sORecid + " ]";
    }
    
}
