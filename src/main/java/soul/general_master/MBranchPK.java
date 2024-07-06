/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author soullab
 */
@Embeddable
public class MBranchPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "Branch_cd")
    private String branchcd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fclty_cd")
    private String fcltycd;

    public MBranchPK() {
    }

    public MBranchPK(String branchcd, String fcltycd) {
        this.branchcd = branchcd;
        this.fcltycd = fcltycd;
    }

    public String getBranchcd() {
        return branchcd;
    }

    public void setBranchcd(String branchcd) {
        this.branchcd = branchcd;
    }

    public String getFcltycd() {
        return fcltycd;
    }

    public void setFcltycd(String fcltycd) {
        this.fcltycd = fcltycd;
    }

//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (int) branchcd;
//        //hash += (String) fcltycd;
//        return hash;
//    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MBranchPK)) {
            return false;
        }
        MBranchPK other = (MBranchPK) object;
        if (this.branchcd != other.branchcd) {
            return false;
        }
        if (this.fcltycd != other.fcltycd) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.MBranchPK[ branchcd=" + branchcd + ", fcltycd=" + fcltycd + " ]";
    }
    
}
