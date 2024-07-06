/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

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
@Table(name = "mem_photo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MemPhoto.findAll", query = "SELECT m FROM MemPhoto m"),
    @NamedQuery(name = "MemPhoto.findByMemCd", query = "SELECT m FROM MemPhoto m WHERE m.memCd = :memCd"),
    @NamedQuery(name = "MemPhoto.findByMemPhotoPath", query = "SELECT m FROM MemPhoto m WHERE m.memPhotoPath = :memPhotoPath")})
public class MemPhoto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "mem_cd")
    private String memCd;
    @Lob
    @Column(name = "member_photo")
    private byte[] memberPhoto;
    @Size(max = 400)
    @Column(name = "mem_photo_path")
    private String memPhotoPath;

    public MemPhoto() {
    }

    public MemPhoto(String memCd) {
        this.memCd = memCd;
    }

    public String getMemCd() {
        return memCd;
    }

    public void setMemCd(String memCd) {
        this.memCd = memCd;
    }

    public byte[] getMemberPhoto() {
        return memberPhoto;
    }

    public void setMemberPhoto(byte[] memberPhoto) {
        this.memberPhoto = memberPhoto;
    }

    public String getMemPhotoPath() {
        return memPhotoPath;
    }

    public void setMemPhotoPath(String memPhotoPath) {
        this.memPhotoPath = memPhotoPath;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memCd != null ? memCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemPhoto)) {
            return false;
        }
        MemPhoto other = (MemPhoto) object;
        if ((this.memCd == null && other.memCd != null) || (this.memCd != null && !this.memCd.equals(other.memCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.MemPhoto[ memCd=" + memCd + " ]";
    }
    
}
