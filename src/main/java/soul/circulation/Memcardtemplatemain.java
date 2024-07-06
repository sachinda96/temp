/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
 * @author admin
 */
@Entity
@Table(name = "memcardtemplatemain")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Memcardtemplatemain.findAll", query = "SELECT m FROM Memcardtemplatemain m")
    , @NamedQuery(name = "Memcardtemplatemain.findByMTempID", query = "SELECT m FROM Memcardtemplatemain m WHERE m.mTempID = :mTempID")
    , @NamedQuery(name = "Memcardtemplatemain.findByMTempName", query = "SELECT m FROM Memcardtemplatemain m WHERE m.mTempName LIKE :mTempName")
    , @NamedQuery(name = "Memcardtemplatemain.findByMIsDefault", query = "SELECT m FROM Memcardtemplatemain m WHERE m.mIsDefault = :mIsDefault")
    , @NamedQuery(name = "Memcardtemplatemain.findByMRemark", query = "SELECT m FROM Memcardtemplatemain m WHERE m.mRemark = :mRemark")
    , @NamedQuery(name = "Memcardtemplatemain.findByMHeight", query = "SELECT m FROM Memcardtemplatemain m WHERE m.mHeight = :mHeight")
    , @NamedQuery(name = "Memcardtemplatemain.findByMWidth", query = "SELECT m FROM Memcardtemplatemain m WHERE m.mWidth = :mWidth")
    , @NamedQuery(name = "Memcardtemplatemain.findMaxTemplateId", query = "SELECT m FROM Memcardtemplatemain m WHERE m.mTempID = (SELECT MAX(b.mTempID) FROM Memcardtemplatemain b)")})
public class Memcardtemplatemain implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "mTempID")
    private Integer mTempID;
    @Size(max = 60)
    @Column(name = "mTempName")
    private String mTempName;
    @Size(max = 2)
    @Column(name = "mIsDefault")
    private String mIsDefault;
    @Size(max = 3000)
    @Column(name = "mRemark")
    private String mRemark;
    @Size(max = 100)
    @Column(name = "mHeight")
    private String mHeight;
    @Size(max = 100)
    @Column(name = "mWidth")
    private String mWidth;
    @Lob
    @Size(max = 65535)
    @Column(name = "mInstruction")
    private String mInstruction;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memcardtemplatemain")
    private Collection<Memcardtemplatesub> memcardtemplatesubCollection;

    public Memcardtemplatemain() {
    }

    public Memcardtemplatemain(Integer mTempID) {
        this.mTempID = mTempID;
    }

    public Integer getMTempID() {
        return mTempID;
    }

    public void setMTempID(Integer mTempID) {
        this.mTempID = mTempID;
    }

    public String getMTempName() {
        return mTempName;
    }

    public void setMTempName(String mTempName) {
        this.mTempName = mTempName;
    }

    public String getMIsDefault() {
        return mIsDefault;
    }

    public void setMIsDefault(String mIsDefault) {
        this.mIsDefault = mIsDefault;
    }

    public String getMRemark() {
        return mRemark;
    }

    public void setMRemark(String mRemark) {
        this.mRemark = mRemark;
    }

    public String getMHeight() {
        return mHeight;
    }

    public void setMHeight(String mHeight) {
        this.mHeight = mHeight;
    }

    public String getMWidth() {
        return mWidth;
    }

    public void setMWidth(String mWidth) {
        this.mWidth = mWidth;
    }

    public String getMInstruction() {
        return mInstruction;
    }

    public void setMInstruction(String mInstruction) {
        this.mInstruction = mInstruction;
    }

    @XmlTransient
    public Collection<Memcardtemplatesub> getMemcardtemplatesubCollection() {
        return memcardtemplatesubCollection;
    }

    public void setMemcardtemplatesubCollection(Collection<Memcardtemplatesub> memcardtemplatesubCollection) {
        this.memcardtemplatesubCollection = memcardtemplatesubCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mTempID != null ? mTempID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Memcardtemplatemain)) {
            return false;
        }
        Memcardtemplatemain other = (Memcardtemplatemain) object;
        if ((this.mTempID == null && other.mTempID != null) || (this.mTempID != null && !this.mTempID.equals(other.mTempID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.Memcardtemplatemain[ mTempID=" + mTempID + " ]";
    }
    
}
