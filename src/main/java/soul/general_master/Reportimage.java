/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "reportimage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reportimage.findAll", query = "SELECT r FROM Reportimage r"),
    @NamedQuery(name = "Reportimage.findById", query = "SELECT r FROM Reportimage r WHERE r.id = :id"),
    @NamedQuery(name = "Reportimage.findByIName", query = "SELECT r FROM Reportimage r WHERE r.iName = :iName"),
    @NamedQuery(name = "Reportimage.findByDescription", query = "SELECT r FROM Reportimage r WHERE r.description = :description"),
    @NamedQuery(name = "Reportimage.findByFilePath", query = "SELECT r FROM Reportimage r WHERE r.filePath = :filePath")})
public class Reportimage implements Serializable {
    @Lob
    @Column(name = "ImageData")
    private byte[] imageData;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 510)
    @Column(name = "IName")
    private String iName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "Description")
    private String description;
    @Size(max = 510)
    @Column(name = "FilePath")
    private String filePath;

    public Reportimage() {
    }

    public Reportimage(Long id) {
        this.id = id;
    }

    public Reportimage(Long id, String iName, String description) {
        this.id = id;
        this.iName = iName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIName() {
        return iName;
    }

    public void setIName(String iName) {
        this.iName = iName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reportimage)) {
            return false;
        }
        Reportimage other = (Reportimage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.Reportimage[ id=" + id + " ]";
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
    
}
