/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.sru;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "sruserver")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sruserver.findAll", query = "SELECT s FROM Sruserver s")
    , @NamedQuery(name = "Sruserver.findById", query = "SELECT s FROM Sruserver s WHERE s.id = :id")
    , @NamedQuery(name = "Sruserver.findByBaseUrl", query = "SELECT s FROM Sruserver s WHERE s.baseUrl = :baseUrl")
    , @NamedQuery(name = "Sruserver.findByLibraryName", query = "SELECT s FROM Sruserver s WHERE s.libraryName = :libraryName")})
public class Sruserver implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "BaseUrl")
    private String baseUrl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "LibraryName")
    private String libraryName;

    public Sruserver() {
    }

    public Sruserver(Integer id) {
        this.id = id;
    }

    public Sruserver(Integer id, String baseUrl, String libraryName) {
        this.id = id;
        this.baseUrl = baseUrl;
        this.libraryName = libraryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
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
        if (!(object instanceof Sruserver)) {
            return false;
        }
        Sruserver other = (Sruserver) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.sru.Sruserver[ id=" + id + " ]";
    }
    
}
