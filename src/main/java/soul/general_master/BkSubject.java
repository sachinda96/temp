/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
import soul.serialControl.SRequest;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "bk_subject")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BkSubject.findAll", query = "SELECT b FROM BkSubject b"),
    @NamedQuery(name = "BkSubject.findByBkSubjectName", query = "SELECT b FROM BkSubject b WHERE b.bkSubjectName = :bkSubjectName"),
    @NamedQuery(name = "BkSubject.getClassNo", query = "SELECT b FROM BkSubject b WHERE b.bkSubjectName like :bkSubjectName"),
    @NamedQuery(name = "BkSubject.findByBkClassno", query = "SELECT b FROM BkSubject b WHERE b.bkClassno = :bkClassno")})
public class BkSubject implements Serializable {
    @OneToMany(mappedBy = "sRSubCd")
    private Collection<SRequest> sRequestCollection;
    private static final long serialVersionUID = 1L;
    @Size(max = 100)
    @Column(name = "bk_subject_name")
    private String bkSubjectName;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "bk_classno")
    private String bkClassno;

    public BkSubject() {
    }

    public BkSubject(String bkClassno) {
        this.bkClassno = bkClassno;
    }

    public String getBkSubjectName() {
        return bkSubjectName;
    }

    public void setBkSubjectName(String bkSubjectName) {
        this.bkSubjectName = bkSubjectName;
    }

    public String getBkClassno() {
        return bkClassno;
    }

    public void setBkClassno(String bkClassno) {
        this.bkClassno = bkClassno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bkClassno != null ? bkClassno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BkSubject)) {
            return false;
        }
        BkSubject other = (BkSubject) object;
        if ((this.bkClassno == null && other.bkClassno != null) || (this.bkClassno != null && !this.bkClassno.equals(other.bkClassno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.BkSubject[ bkClassno=" + bkClassno + " ]";
    }

    @XmlTransient
    public Collection<SRequest> getSRequestCollection() {
        return sRequestCollection;
    }

    public void setSRequestCollection(Collection<SRequest> sRequestCollection) {
        this.sRequestCollection = sRequestCollection;
    }
    
}
