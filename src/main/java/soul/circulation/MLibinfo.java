/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

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
@Table(name = "m_libinfo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MLibinfo.findAll", query = "SELECT m FROM MLibinfo m"),
    @NamedQuery(name = "MLibinfo.findById", query = "SELECT m FROM MLibinfo m WHERE m.id = :id"),
    @NamedQuery(name = "MLibinfo.findByLibNm", query = "SELECT m FROM MLibinfo m WHERE m.libNm = :libNm"),
    @NamedQuery(name = "MLibinfo.findByUniNm", query = "SELECT m FROM MLibinfo m WHERE m.uniNm = :uniNm"),
    @NamedQuery(name = "MLibinfo.findByLibrariannm", query = "SELECT m FROM MLibinfo m WHERE m.librariannm = :librariannm"),
    @NamedQuery(name = "MLibinfo.findByAddr1", query = "SELECT m FROM MLibinfo m WHERE m.addr1 = :addr1"),
    @NamedQuery(name = "MLibinfo.findByAddr2", query = "SELECT m FROM MLibinfo m WHERE m.addr2 = :addr2"),
    @NamedQuery(name = "MLibinfo.findByCity", query = "SELECT m FROM MLibinfo m WHERE m.city = :city"),
    @NamedQuery(name = "MLibinfo.findByPin", query = "SELECT m FROM MLibinfo m WHERE m.pin = :pin"),
    @NamedQuery(name = "MLibinfo.findByPhone", query = "SELECT m FROM MLibinfo m WHERE m.phone = :phone"),
    @NamedQuery(name = "MLibinfo.findByFax", query = "SELECT m FROM MLibinfo m WHERE m.fax = :fax"),
    @NamedQuery(name = "MLibinfo.findByEmail", query = "SELECT m FROM MLibinfo m WHERE m.email = :email"),
    @NamedQuery(name = "MLibinfo.findByLibraryID", query = "SELECT m FROM MLibinfo m WHERE m.libraryID = :libraryID")})
public class MLibinfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "Lib_Nm")
    private String libNm;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "Uni_Nm")
    private String uniNm;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Librarian_nm")
    private String librariannm;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "Addr1")
    private String addr1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "Addr2")
    private String addr2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "City")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "PIN")
    private String pin;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "Phone")
    private String phone;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "fax")
    private String fax;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "email")
    private String email;
    @Size(max = 100)
    @Column(name = "LibraryID")
    private String libraryID;

    public MLibinfo() {
    }

    public MLibinfo(Integer id) {
        this.id = id;
    }

    public MLibinfo(Integer id, String libNm, String uniNm, String librariannm, String addr1, String addr2, String city, String pin) {
        this.id = id;
        this.libNm = libNm;
        this.uniNm = uniNm;
        this.librariannm = librariannm;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.city = city;
        this.pin = pin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibNm() {
        return libNm;
    }

    public void setLibNm(String libNm) {
        this.libNm = libNm;
    }

    public String getUniNm() {
        return uniNm;
    }

    public void setUniNm(String uniNm) {
        this.uniNm = uniNm;
    }

    public String getLibrariannm() {
        return librariannm;
    }

    public void setLibrariannm(String librariannm) {
        this.librariannm = librariannm;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLibraryID() {
        return libraryID;
    }

    public void setLibraryID(String libraryID) {
        this.libraryID = libraryID;
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
        if (!(object instanceof MLibinfo)) {
            return false;
        }
        MLibinfo other = (MLibinfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.MLibinfo[ id=" + id + " ]";
    }
    
}
