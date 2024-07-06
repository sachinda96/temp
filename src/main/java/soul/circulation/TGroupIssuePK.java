/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author soullab
 */
@Embeddable
public class TGroupIssuePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "GroupID")
    private int groupID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "acc_no")
    private String accNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iss_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issDt;

    public TGroupIssuePK() {
    }

    public TGroupIssuePK(int groupID, String accNo, Date issDt) {
        this.groupID = groupID;
        this.accNo = accNo;
        this.issDt = issDt;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public Date getIssDt() {
        return issDt;
    }

    public void setIssDt(Date issDt) {
        this.issDt = issDt;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) groupID;
        hash += (accNo != null ? accNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TGroupIssuePK)) {
            return false;
        }
        TGroupIssuePK other = (TGroupIssuePK) object;
        if (this.groupID != other.groupID) {
            return false;
        }
        if ((this.accNo == null && other.accNo != null) || (this.accNo != null && !this.accNo.equals(other.accNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TGroupIssuePK[ groupID=" + groupID + ", accNo=" + accNo + " ]";
    }
    
}
