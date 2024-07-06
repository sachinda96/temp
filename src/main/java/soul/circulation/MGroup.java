/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "m_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MGroup.findAll", query = "SELECT m FROM MGroup m"),
    @NamedQuery(name = "MGroup.findByGroupID", query = "SELECT m FROM MGroup m WHERE m.mGroupPK.groupID = :groupID"),
    @NamedQuery(name = "MGroup.findGroupByGroupID", query = "SELECT m.mGroupPK.groupID FROM MGroup m WHERE m.mGroupPK.groupID = :groupID"),
    @NamedQuery(name = "MGroup.findByGroupIDandMemcd", query = "SELECT m FROM MGroup m WHERE m.mGroupPK.groupID = ?1 and m.mGroupPK.groupID = ?2"),
    @NamedQuery(name = "MGroup.findMaxGroupID", query = "SELECT m FROM MGroup m WHERE m.mGroupPK.groupID =(SELECT MAX(a.mGroupPK.groupID) FROM MGroup a)"),
    @NamedQuery(name = "MGroup.findByGroupName", query = "SELECT m FROM MGroup m WHERE m.groupName = :groupName"),
    @NamedQuery(name = "MGroup.findBySrNo", query = "SELECT m FROM MGroup m WHERE m.srNo = :srNo"),
    @NamedQuery(name = "MGroup.getMaxSrNo", query = "SELECT m FROM MGroup m WHERE m.mGroupPK.groupID = (SELECT MAX(b.srNo) FROM MGroup b)"),
    @NamedQuery(name = "MGroup.findByMemCd", query = "SELECT m FROM MGroup m WHERE m.mGroupPK.memCd = :memCd "),
    @NamedQuery(name = "MGroup.countByMemcd", query = "SELECT m.mGroupPK.memCd FROM MGroup m WHERE m.mGroupPK.memCd = :memCd"),
    @NamedQuery(name = "MGroup.findAllByGroupName", query = "SELECT m FROM MGroup m GROUP BY m.groupName"),
    @NamedQuery(name = "MGroup.findWithMaxSrNo", query = "SELECT m FROM MGroup m WHERE m.mGroupPK.groupID = :groupId ORDER BY m.srNo DESC"),
    @NamedQuery(name = "MGroup.RemoveByGroupID", query = "DELETE FROM MGroup m WHERE m.mGroupPK.groupID = :groupId"),
    @NamedQuery(name = "MGroup.RemoveByMemCd", query = "DELETE FROM MGroup m WHERE m.mGroupPK.memCd = :memCd")
    
})
public class MGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MGroupPK mGroupPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "GroupName")
    private String groupName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SrNo")
    private int srNo;
    @JoinColumn(name = "mem_cd", referencedColumnName = "mem_cd", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MMember mMember;
    @NotNull
    @Column(name = "status")
    private String status;
    
    public MGroup() {
    }

    public MGroup(MGroupPK mGroupPK) {
        this.mGroupPK = mGroupPK;
    }

    public MGroup(MGroupPK mGroupPK, String groupName, int srNo) {
        this.mGroupPK = mGroupPK;
        this.groupName = groupName;
        this.srNo = srNo;
    }
    

    
    public MGroup(int groupID, String memCd) {
        this.mGroupPK = new MGroupPK(groupID, memCd);
    }

    public MGroupPK getMGroupPK() {
        return mGroupPK;
    }

    public void setMGroupPK(MGroupPK mGroupPK) {
        this.mGroupPK = mGroupPK;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    public MMember getMMember() {
        return mMember;
    }

    public void setMMember(MMember mMember) {
        this.mMember = mMember;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mGroupPK != null ? mGroupPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MGroup)) {
            return false;
        }
        MGroup other = (MGroup) object;
        if ((this.mGroupPK == null && other.mGroupPK != null) || (this.mGroupPK != null && !this.mGroupPK.equals(other.mGroupPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.MGroup[ mGroupPK=" + mGroupPK + " ]";
    }
    
}
