/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "reporttemplatemain")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reporttemplatemain.findAll", query = "SELECT r FROM Reporttemplatemain r"),
    @NamedQuery(name = "Reporttemplatemain.findByTempID", query = "SELECT r FROM Reporttemplatemain r WHERE r.tempID = :tempID"),
    @NamedQuery(name = "Reporttemplatemain.findByReportTempName", query = "SELECT r FROM Reporttemplatemain r WHERE r.reportTempName = :reportTempName"),
    @NamedQuery(name = "Reporttemplatemain.findByIsDefault", query = "SELECT r FROM Reporttemplatemain r WHERE r.isDefault = :isDefault"),
    @NamedQuery(name = "Reporttemplatemain.findByRemark", query = "SELECT r FROM Reporttemplatemain r WHERE r.remark = :remark"),
    @NamedQuery(name = "Reporttemplatemain.findByHeight", query = "SELECT r FROM Reporttemplatemain r WHERE r.height = :height"),
    @NamedQuery(name = "Reporttemplatemain.findByWidth", query = "SELECT r FROM Reporttemplatemain r WHERE r.width = :width")})
public class Reporttemplatemain implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TempID")
    private Integer tempID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "ReportTempName")
    private String reportTempName;
    @Size(max = 2)
    @Column(name = "IsDefault")
    private String isDefault;
    @Size(max = 4000)
    @Column(name = "Remark")
    private String remark;
    @Size(max = 100)
    @Column(name = "Height")
    private String height;
    @Size(max = 100)
    @Column(name = "Width")
    private String width;
    

    public Reporttemplatemain() {
    }

    public Reporttemplatemain(Integer tempID) {
        this.tempID = tempID;
    }

    public Reporttemplatemain(Integer tempID, String reportTempName) {
        this.tempID = tempID;
        this.reportTempName = reportTempName;
    }

    public Integer getTempID() {
        return tempID;
    }

    public void setTempID(Integer tempID) {
        this.tempID = tempID;
    }

    public String getReportTempName() {
        return reportTempName;
    }

    public void setReportTempName(String reportTempName) {
        this.reportTempName = reportTempName;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tempID != null ? tempID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reporttemplatemain)) {
            return false;
        }
        Reporttemplatemain other = (Reporttemplatemain) object;
        if ((this.tempID == null && other.tempID != null) || (this.tempID != null && !this.tempID.equals(other.tempID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.Reporttemplatemain[ tempID=" + tempID + " ]";
    }
    
}
