/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

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
public class MemcardtemplatesubPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "mrptTempID")
    private int mrptTempID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mFieldName")
    private String mFieldName;

    public MemcardtemplatesubPK() {
    }

    public MemcardtemplatesubPK(int mrptTempID, String mFieldName) {
        this.mrptTempID = mrptTempID;
        this.mFieldName = mFieldName;
    }

    public int getMrptTempID() {
        return mrptTempID;
    }

    public void setMrptTempID(int mrptTempID) {
        this.mrptTempID = mrptTempID;
    }

    public String getMFieldName() {
        return mFieldName;
    }

    public void setMFieldName(String mFieldName) {
        this.mFieldName = mFieldName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) mrptTempID;
        hash += (mFieldName != null ? mFieldName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemcardtemplatesubPK)) {
            return false;
        }
        MemcardtemplatesubPK other = (MemcardtemplatesubPK) object;
        if (this.mrptTempID != other.mrptTempID) {
            return false;
        }
        if ((this.mFieldName == null && other.mFieldName != null) || (this.mFieldName != null && !this.mFieldName.equals(other.mFieldName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.MemcardtemplatesubPK[ mrptTempID=" + mrptTempID + ", mFieldName=" + mFieldName + " ]";
    }
    
}
