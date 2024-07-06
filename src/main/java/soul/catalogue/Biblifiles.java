/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

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
@Table(name = "biblifiles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Biblifiles.findAll", query = "SELECT b FROM Biblifiles b"),
    @NamedQuery(name = "Biblifiles.findByRecID", query = "SELECT b FROM Biblifiles b WHERE b.recID = :recID"),
    @NamedQuery(name = "Biblifiles.findByFilePath", query = "SELECT b FROM Biblifiles b WHERE b.filePath = :filePath"),
    @NamedQuery(name = "Biblifiles.findByFileName", query = "SELECT b FROM Biblifiles b WHERE b.fileName = :fileName"),
    @NamedQuery(name = "Biblifiles.findByExtension", query = "SELECT b FROM Biblifiles b WHERE b.extension = :extension"),
    @NamedQuery(name = "Biblifiles.deleteBetweenStartAndEndRecId", query = "DELETE FROM Biblifiles b WHERE b.recID BETWEEN  ?1 AND ?2")})
public class Biblifiles implements Serializable {
    @Lob
    @Column(name = "FileData")
    private byte[] fileData;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RecID")
    private Integer recID;
    @Size(max = 200)
    @Column(name = "FilePath")
    private String filePath;
    @Size(max = 100)
    @Column(name = "FileName")
    private String fileName;
    @Size(max = 100)
    @Column(name = "Extension")
    private String extension;

    public Biblifiles() {
    }

    public Biblifiles(Integer recID) {
        this.recID = recID;
    }

    public Integer getRecID() {
        return recID;
    }

    public void setRecID(Integer recID) {
        this.recID = recID;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recID != null ? recID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Biblifiles)) {
            return false;
        }
        Biblifiles other = (Biblifiles) object;
        if ((this.recID == null && other.recID != null) || (this.recID != null && !this.recID.equals(other.recID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.Biblifiles[ recID=" + recID + " ]";
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
    
}
