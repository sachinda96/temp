/*
 * To change this template, choose Tools | Templates
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
 * @author soullab
 */
@Embeddable
public class MGroupPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "GroupID")
    private int groupID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "mem_cd")
    private String memCd;

    public MGroupPK() {
    }

    public MGroupPK(int groupID, String memCd) {
        this.groupID = groupID;
        this.memCd = memCd;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getMemCd() {
        return memCd;
    }

    public void setMemCd(String memCd) {
        this.memCd = memCd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) groupID;
        hash += (memCd != null ? memCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MGroupPK)) {
            return false;
        }
        MGroupPK other = (MGroupPK) object;
        if (this.groupID != other.groupID) {
            return false;
        }
        if ((this.memCd == null && other.memCd != null) || (this.memCd != null && !this.memCd.equals(other.memCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.MGroupPK[ groupID=" + groupID + ", memCd=" + memCd + " ]";
    }
    
}
