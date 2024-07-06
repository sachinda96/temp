/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author admin
 */
@Embeddable
public class SBindingSetPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_b_setno")
    private String sBSetno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_b_volno")
    private String sBVolno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_b_issueno")
    private String sBIssueno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 510)
    @Column(name = "s_b_recid")
    private String sBRecid;

    public SBindingSetPK() {
    }

    public SBindingSetPK(String sBSetno, String sBVolno, String sBIssueno, String sBRecid) {
        this.sBSetno = sBSetno;
        this.sBVolno = sBVolno;
        this.sBIssueno = sBIssueno;
        this.sBRecid = sBRecid;
    }

    public String getSBSetno() {
        return sBSetno;
    }

    public void setSBSetno(String sBSetno) {
        this.sBSetno = sBSetno;
    }

    public String getSBVolno() {
        return sBVolno;
    }

    public void setSBVolno(String sBVolno) {
        this.sBVolno = sBVolno;
    }

    public String getSBIssueno() {
        return sBIssueno;
    }

    public void setSBIssueno(String sBIssueno) {
        this.sBIssueno = sBIssueno;
    }

    public String getSBRecid() {
        return sBRecid;
    }

    public void setSBRecid(String sBRecid) {
        this.sBRecid = sBRecid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sBSetno != null ? sBSetno.hashCode() : 0);
        hash += (sBVolno != null ? sBVolno.hashCode() : 0);
        hash += (sBIssueno != null ? sBIssueno.hashCode() : 0);
        hash += (sBRecid != null ? sBRecid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SBindingSetPK)) {
            return false;
        }
        SBindingSetPK other = (SBindingSetPK) object;
        if ((this.sBSetno == null && other.sBSetno != null) || (this.sBSetno != null && !this.sBSetno.equals(other.sBSetno))) {
            return false;
        }
        if ((this.sBVolno == null && other.sBVolno != null) || (this.sBVolno != null && !this.sBVolno.equals(other.sBVolno))) {
            return false;
        }
        if ((this.sBIssueno == null && other.sBIssueno != null) || (this.sBIssueno != null && !this.sBIssueno.equals(other.sBIssueno))) {
            return false;
        }
        if ((this.sBRecid == null && other.sBRecid != null) || (this.sBRecid != null && !this.sBRecid.equals(other.sBRecid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serial_master.SBindingSetPK[ sBSetno=" + sBSetno + ", sBVolno=" + sBVolno + ", sBIssueno=" + sBIssueno + ", sBRecid=" + sBRecid + " ]";
    }
    
}
