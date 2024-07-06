/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.user_setting;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "sgroup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgroup.findAll", query = "SELECT s FROM Sgroup s"),
    @NamedQuery(name = "Sgroup.findByGroupId", query = "SELECT s FROM Sgroup s WHERE s.groupId = :groupId"),
    @NamedQuery(name = "Sgroup.findByGroupName", query = "SELECT s FROM Sgroup s WHERE s.groupName = :groupName"),
    @NamedQuery(name = "Sgroup.findByPrivilegeList", query = "SELECT s FROM Sgroup s WHERE s.privilegeList = :privilegeList")})
public class Sgroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GroupId")
    private Integer groupId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "GroupName")
    private String groupName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2000)
    @Column(name = "PrivilegeList")
    private String privilegeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupID")
    private Collection<Userdetail> userdetailCollection;

    public Sgroup() {
    }

    public Sgroup(Integer groupId) {
        this.groupId = groupId;
    }

    public Sgroup(Integer groupId, String groupName, String privilegeList) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.privilegeList = privilegeList;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(String privilegeList) {
        this.privilegeList = privilegeList;
    }

    @XmlTransient
    public Collection<Userdetail> getUserdetailCollection() {
        return userdetailCollection;
    }

    public void setUserdetailCollection(Collection<Userdetail> userdetailCollection) {
        this.userdetailCollection = userdetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupId != null ? groupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgroup)) {
            return false;
        }
        Sgroup other = (Sgroup) object;
        if ((this.groupId == null && other.groupId != null) || (this.groupId != null && !this.groupId.equals(other.groupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.user_setting.Sgroup[ groupId=" + groupId + " ]";
    }
    
}
