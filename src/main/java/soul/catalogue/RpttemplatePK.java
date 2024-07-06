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
public class RpttemplatePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 18)
    @Column(name = "rptTempID")
    private String rptTempID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "rptSbrptName")
    private String rptSbrptName;

    public RpttemplatePK() {
    }

    public RpttemplatePK(String rptTempID, String rptSbrptName) {
        this.rptTempID = rptTempID;
        this.rptSbrptName = rptSbrptName;
    }

    public String getRptTempID() {
        return rptTempID;
    }

    public void setRptTempID(String rptTempID) {
        this.rptTempID = rptTempID;
    }

    public String getRptSbrptName() {
        return rptSbrptName;
    }

    public void setRptSbrptName(String rptSbrptName) {
        this.rptSbrptName = rptSbrptName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rptTempID != null ? rptTempID.hashCode() : 0);
        hash += (rptSbrptName != null ? rptSbrptName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RpttemplatePK)) {
            return false;
        }
        RpttemplatePK other = (RpttemplatePK) object;
        if ((this.rptTempID == null && other.rptTempID != null) || (this.rptTempID != null && !this.rptTempID.equals(other.rptTempID))) {
            return false;
        }
        if ((this.rptSbrptName == null && other.rptSbrptName != null) || (this.rptSbrptName != null && !this.rptSbrptName.equals(other.rptSbrptName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.RpttemplatePK[ rptTempID=" + rptTempID + ", rptSbrptName=" + rptSbrptName + " ]";
    }
    
}
