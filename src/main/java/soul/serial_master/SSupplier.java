/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master;

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
@Table(name = "s_supplier")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SSupplier.findAll", query = "SELECT s FROM SSupplier s")
    , @NamedQuery(name = "SSupplier.findByASType", query = "SELECT s FROM SSupplier s WHERE s.aSType = :aSType")
    , @NamedQuery(name = "SSupplier.findByASCd", query = "SELECT s FROM SSupplier s WHERE s.aSCd = :aSCd")
    , @NamedQuery(name = "SSupplier.findByASNm", query = "SELECT s FROM SSupplier s WHERE s.aSNm = :aSNm")})
public class SSupplier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 200)
    @Column(name = "a_s_type")
    private String aSType;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "a_s_cd")
    private String aSCd;
    @Size(max = 200)
    @Column(name = "a_s_nm")
    private String aSNm;

    public SSupplier() {
    }

    public SSupplier(String aSCd) {
        this.aSCd = aSCd;
    }

    public String getASType() {
        return aSType;
    }

    public void setASType(String aSType) {
        this.aSType = aSType;
    }

    public String getASCd() {
        return aSCd;
    }

    public void setASCd(String aSCd) {
        this.aSCd = aSCd;
    }

    public String getASNm() {
        return aSNm;
    }

    public void setASNm(String aSNm) {
        this.aSNm = aSNm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aSCd != null ? aSCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SSupplier)) {
            return false;
        }
        SSupplier other = (SSupplier) object;
        if ((this.aSCd == null && other.aSCd != null) || (this.aSCd != null && !this.aSCd.equals(other.aSCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serial_master.SSupplier[ aSCd=" + aSCd + " ]";
    }
    
}
