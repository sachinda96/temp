/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "rpttemplate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rpttemplate.findAll", query = "SELECT r FROM Rpttemplate r"),
    @NamedQuery(name = "Rpttemplate.findByRptTempID", query = "SELECT r FROM Rpttemplate r WHERE r.rpttemplatePK.rptTempID = :rptTempID order by r.rptLeft asc"),
    @NamedQuery(name = "Rpttemplate.findByRptSbrptName", query = "SELECT r FROM Rpttemplate r WHERE r.rpttemplatePK.rptSbrptName = :rptSbrptName"),
    @NamedQuery(name = "Rpttemplate.findByRptHeight", query = "SELECT r FROM Rpttemplate r WHERE r.rptHeight = :rptHeight"),
    @NamedQuery(name = "Rpttemplate.findByRptWidth", query = "SELECT r FROM Rpttemplate r WHERE r.rptWidth = :rptWidth"),
    @NamedQuery(name = "Rpttemplate.findByRptLeft", query = "SELECT r FROM Rpttemplate r WHERE r.rptLeft = :rptLeft"),
    @NamedQuery(name = "Rpttemplate.findByRptTop", query = "SELECT r FROM Rpttemplate r WHERE r.rptTop = :rptTop"),
 
    @NamedQuery(name = "Rpttemplate.removeByRptTempID", query = "DELETE FROM Rpttemplate r WHERE r.rpttemplatePK.rptTempID = :rptTempID")
})
public class Rpttemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RpttemplatePK rpttemplatePK;
    @Size(max = 10)
    @Column(name = "rptHeight")
    private String rptHeight;
    @Size(max = 10)
    @Column(name = "rptWidth")
    private String rptWidth;
    @Size(max = 10)
    @Column(name = "rptLeft")
    private String rptLeft;
    @Size(max = 10)
    @Column(name = "rptTop")
    private String rptTop;

    public Rpttemplate() {
    }

    public Rpttemplate(RpttemplatePK rpttemplatePK) {
        this.rpttemplatePK = rpttemplatePK;
    }

    public Rpttemplate(String rptTempID, String rptSbrptName) {
        this.rpttemplatePK = new RpttemplatePK(rptTempID, rptSbrptName);
    }

    public RpttemplatePK getRpttemplatePK() {
        return rpttemplatePK;
    }

    public void setRpttemplatePK(RpttemplatePK rpttemplatePK) {
        this.rpttemplatePK = rpttemplatePK;
    }

    public String getRptHeight() {
        return rptHeight;
    }

    public void setRptHeight(String rptHeight) {
        this.rptHeight = rptHeight;
    }

    public String getRptWidth() {
        return rptWidth;
    }

    public void setRptWidth(String rptWidth) {
        this.rptWidth = rptWidth;
    }

    public String getRptLeft() {
        return rptLeft;
    }

    public void setRptLeft(String rptLeft) {
        this.rptLeft = rptLeft;
    }

    public String getRptTop() {
        return rptTop;
    }

    public void setRptTop(String rptTop) {
        this.rptTop = rptTop;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rpttemplatePK != null ? rpttemplatePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rpttemplate)) {
            return false;
        }
        Rpttemplate other = (Rpttemplate) object;
        if ((this.rpttemplatePK == null && other.rpttemplatePK != null) || (this.rpttemplatePK != null && !this.rpttemplatePK.equals(other.rpttemplatePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.Rpttemplate[ rpttemplatePK=" + rpttemplatePK + " ]";
    }
    
}
