/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "memcardtemplatesub")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Memcardtemplatesub.findAll", query = "SELECT m FROM Memcardtemplatesub m")
    , @NamedQuery(name = "Memcardtemplatesub.findByMrptTempID", query = "SELECT m FROM Memcardtemplatesub m WHERE m.memcardtemplatesubPK.mrptTempID = :mrptTempID")
    , @NamedQuery(name = "Memcardtemplatesub.findByMFieldName", query = "SELECT m FROM Memcardtemplatesub m WHERE m.memcardtemplatesubPK.mFieldName = :mFieldName")
    , @NamedQuery(name = "Memcardtemplatesub.findByMrptHeight", query = "SELECT m FROM Memcardtemplatesub m WHERE m.mrptHeight = :mrptHeight")
    , @NamedQuery(name = "Memcardtemplatesub.findByMrptWidth", query = "SELECT m FROM Memcardtemplatesub m WHERE m.mrptWidth = :mrptWidth")
    , @NamedQuery(name = "Memcardtemplatesub.findByMrptLeft", query = "SELECT m FROM Memcardtemplatesub m WHERE m.mrptLeft = :mrptLeft")
    , @NamedQuery(name = "Memcardtemplatesub.removeByTempId", query = "DELETE FROM Memcardtemplatesub m WHERE m.memcardtemplatesubPK.mrptTempID = :mrptTempID")
    , @NamedQuery(name = "Memcardtemplatesub.findByMrptTop", query = "SELECT m FROM Memcardtemplatesub m WHERE m.mrptTop = :mrptTop")})
public class Memcardtemplatesub implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MemcardtemplatesubPK memcardtemplatesubPK;
    @Size(max = 10)
    @Column(name = "mrptHeight")
    private String mrptHeight;
    @Size(max = 10)
    @Column(name = "mrptWidth")
    private String mrptWidth;
    @Size(max = 10)
    @Column(name = "mrptLeft")
    private String mrptLeft;
    @Size(max = 10)
    @Column(name = "mrptTop")
    private String mrptTop;
    @JoinColumn(name = "mrptTempID", referencedColumnName = "mTempID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Memcardtemplatemain memcardtemplatemain;

    public Memcardtemplatesub() {
    }

    public Memcardtemplatesub(MemcardtemplatesubPK memcardtemplatesubPK) {
        this.memcardtemplatesubPK = memcardtemplatesubPK;
    }

    public Memcardtemplatesub(int mrptTempID, String mFieldName) {
        this.memcardtemplatesubPK = new MemcardtemplatesubPK(mrptTempID, mFieldName);
    }

    public MemcardtemplatesubPK getMemcardtemplatesubPK() {
        return memcardtemplatesubPK;
    }

    public void setMemcardtemplatesubPK(MemcardtemplatesubPK memcardtemplatesubPK) {
        this.memcardtemplatesubPK = memcardtemplatesubPK;
    }

    public String getMrptHeight() {
        return mrptHeight;
    }

    public void setMrptHeight(String mrptHeight) {
        this.mrptHeight = mrptHeight;
    }

    public String getMrptWidth() {
        return mrptWidth;
    }

    public void setMrptWidth(String mrptWidth) {
        this.mrptWidth = mrptWidth;
    }

    public String getMrptLeft() {
        return mrptLeft;
    }

    public void setMrptLeft(String mrptLeft) {
        this.mrptLeft = mrptLeft;
    }

    public String getMrptTop() {
        return mrptTop;
    }

    public void setMrptTop(String mrptTop) {
        this.mrptTop = mrptTop;
    }

    public Memcardtemplatemain getMemcardtemplatemain() {
        return memcardtemplatemain;
    }

    public void setMemcardtemplatemain(Memcardtemplatemain memcardtemplatemain) {
        this.memcardtemplatemain = memcardtemplatemain;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memcardtemplatesubPK != null ? memcardtemplatesubPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Memcardtemplatesub)) {
            return false;
        }
        Memcardtemplatesub other = (Memcardtemplatesub) object;
        if ((this.memcardtemplatesubPK == null && other.memcardtemplatesubPK != null) || (this.memcardtemplatesubPK != null && !this.memcardtemplatesubPK.equals(other.memcardtemplatesubPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.Memcardtemplatesub[ memcardtemplatesubPK=" + memcardtemplatesubPK + " ]";
    }
    
}
