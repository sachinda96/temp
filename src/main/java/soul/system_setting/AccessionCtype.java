/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.system_setting;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import soul.circulation_master.MCtgryColllection;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "accession_ctype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessionCtype.findAll", query = "SELECT a FROM AccessionCtype a"),
    @NamedQuery(name = "AccessionCtype.findByCid", query = "SELECT a FROM AccessionCtype a WHERE a.cid = :cid"),
    @NamedQuery(name = "AccessionCtype.findByCollectionType", query = "SELECT a FROM AccessionCtype a WHERE a.collectionType = :collectionType"),
    @NamedQuery(name = "AccessionCtype.findByPrefix", query = "SELECT a FROM AccessionCtype a WHERE a.prefix = :prefix"),
    @NamedQuery(name = "AccessionCtype.findByLastaccno", query = "SELECT a FROM AccessionCtype a WHERE a.lastaccno = :lastaccno"),
    @NamedQuery(name = "AccessionCtype.findByTotallength", query = "SELECT a FROM AccessionCtype a WHERE a.totallength = :totallength")})
public class AccessionCtype implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accessionCtype")
    private Collection<MCtgryColllection> mCtgryColllectionCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "cid")
    private String cid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "collection_type")
    private String collectionType;
    @Size(max = 100)
    @Column(name = "prefix")
    private String prefix;
    @Size(max = 100)
    @Column(name = "lastaccno")
    private String lastaccno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "totallength")
    private long totallength;

    public AccessionCtype() {
    }

    public AccessionCtype(String cid) {
        this.cid = cid;
    }

    public AccessionCtype(String cid, String collectionType, long totallength) {
        this.cid = cid;
        this.collectionType = collectionType;
        this.totallength = totallength;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getLastaccno() {
        return lastaccno;
    }

    public void setLastaccno(String lastaccno) {
        this.lastaccno = lastaccno;
    }

    public long getTotallength() {
        return totallength;
    }

    public void setTotallength(long totallength) {
        this.totallength = totallength;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccessionCtype)) {
            return false;
        }
        AccessionCtype other = (AccessionCtype) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.system_setting.AccessionCtype[ cid=" + cid + " ]";
    }

    @XmlTransient
    public Collection<MCtgryColllection> getMCtgryColllectionCollection() {
        return mCtgryColllectionCollection;
    }

    public void setMCtgryColllectionCollection(Collection<MCtgryColllection> mCtgryColllectionCollection) {
        this.mCtgryColllectionCollection = mCtgryColllectionCollection;
    }
    
}
