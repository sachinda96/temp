/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author soullab
 */
@Embeddable
public class LocationPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "RecID")
    private int recID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "p852")
    private String p852;

    public LocationPK() {
    }

    public LocationPK(int recID, String p852) {
        this.recID = recID;
        this.p852 = p852;
    }

    public int getRecID() {
        return recID;
    }

    public void setRecID(int recID) {
        this.recID = recID;
    }

    public String getP852() {
        return p852;
    }

    public void setP852(String p852) {
        this.p852 = p852;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) recID;
        hash += (p852 != null ? p852.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LocationPK)) {
            return false;
        }
        LocationPK other = (LocationPK) object;
        if (this.recID != other.recID) {
            return false;
        }
        if ((this.p852 == null && other.p852 != null) || (this.p852 != null && !this.p852.equals(other.p852))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.LocationPK[ recID=" + recID + ", p852=" + p852 + " ]";
    }
    
}
