/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation_master;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import soul.circulation.MMember;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "m_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MType.findAll", query = "SELECT m FROM MType m"),
    @NamedQuery(name = "MType.findByMemberType", query = "SELECT m FROM MType m WHERE m.memberType = :memberType"),
    @NamedQuery(name = "MType.findByMaximumMoney", query = "SELECT m FROM MType m WHERE m.maximumMoney = :maximumMoney"),
    @NamedQuery(name = "MType.findByMaximumBooks", query = "SELECT m FROM MType m WHERE m.maximumBooks = :maximumBooks"),
    @NamedQuery(name = "MType.findByEndDate", query = "SELECT m FROM MType m WHERE m.endDate = :endDate")})
public class MType implements Serializable {
    @OneToMany(mappedBy = "memType")
    private Collection<MMember> mMemberCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "MemberType")
    private String memberType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "MaximumMoney")
    private BigDecimal maximumMoney;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MaximumBooks")
    private long maximumBooks;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EndDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    public MType() {
    }

    public MType(String memberType) {
        this.memberType = memberType;
    }

    public MType(String memberType, BigDecimal maximumMoney, long maximumBooks, Date endDate) {
        this.memberType = memberType;
        this.maximumMoney = maximumMoney;
        this.maximumBooks = maximumBooks;
        this.endDate = endDate;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public BigDecimal getMaximumMoney() {
        return maximumMoney;
    }

    public void setMaximumMoney(BigDecimal maximumMoney) {
        this.maximumMoney = maximumMoney;
    }

    public long getMaximumBooks() {
        return maximumBooks;
    }

    public void setMaximumBooks(long maximumBooks) {
        this.maximumBooks = maximumBooks;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memberType != null ? memberType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MType)) {
            return false;
        }
        MType other = (MType) object;
        if ((this.memberType == null && other.memberType != null) || (this.memberType != null && !this.memberType.equals(other.memberType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation_master.MType[ memberType=" + memberType + " ]";
    }

    @XmlTransient
    public Collection<MMember> getMMemberCollection() {
        return mMemberCollection;
    }

    public void setMMemberCollection(Collection<MMember> mMemberCollection) {
        this.mMemberCollection = mMemberCollection;
    }
    
}
