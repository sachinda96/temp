/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master;

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
@Table(name = "s_frequency")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SFrequency.findAll", query = "SELECT s FROM SFrequency s"),
    @NamedQuery(name = "SFrequency.findBySFCd", query = "SELECT s FROM SFrequency s WHERE s.sFCd = :sFCd"),
    @NamedQuery(name = "SFrequency.findBySFNm", query = "SELECT s FROM SFrequency s WHERE s.sFNm = :sFNm"),
    @NamedQuery(name = "SFrequency.findBySFDd", query = "SELECT s FROM SFrequency s WHERE s.sFDd = :sFDd"),
    @NamedQuery(name = "SFrequency.findBySFMm", query = "SELECT s FROM SFrequency s WHERE s.sFMm = :sFMm"),
    @NamedQuery(name = "SFrequency.findBySFYy", query = "SELECT s FROM SFrequency s WHERE s.sFYy = :sFYy"),
    @NamedQuery(name = "SFrequency.findBySFIss", query = "SELECT s FROM SFrequency s WHERE s.sFIss = :sFIss"),
    @NamedQuery(name = "SFrequency.findBySFDmy", query = "SELECT s FROM SFrequency s WHERE s.sFDmy = :sFDmy"),
    @NamedQuery(name = "SFrequency.findBySFExactName", query = "SELECT s FROM SFrequency s WHERE s.sFNm = :sFNm"),
    @NamedQuery(name = "SFrequency.findBySFLikeName", query = "SELECT s FROM SFrequency s WHERE s.sFNm like :sFNm"),
})
public class SFrequency implements Serializable {
    @OneToMany(mappedBy = "sRFreqCd")
    private Collection<SRequest> sRequestCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "s_f_cd")
    private String sFCd;
    @Size(max = 510)
    @Column(name = "s_f_nm")
    private String sFNm;
    @Column(name = "s_f_dd")
    private Integer sFDd;
    @Column(name = "s_f_mm")
    private Integer sFMm;
    @Column(name = "s_f_yy")
    private Integer sFYy;
    @Column(name = "s_f_iss")
    private Integer sFIss;
    @Size(max = 510)
    @Column(name = "s_f_dmy")
    private String sFDmy;

    public SFrequency() {
    }

    public SFrequency(String sFCd) {
        this.sFCd = sFCd;
    }

    public String getSFCd() {
        return sFCd;
    }

    public void setSFCd(String sFCd) {
        this.sFCd = sFCd;
    }

    public String getSFNm() {
        return sFNm;
    }

    public void setSFNm(String sFNm) {
        this.sFNm = sFNm;
    }

    public Integer getSFDd() {
        return sFDd;
    }

    public void setSFDd(Integer sFDd) {
        this.sFDd = sFDd;
    }

    public Integer getSFMm() {
        return sFMm;
    }

    public void setSFMm(Integer sFMm) {
        this.sFMm = sFMm;
    }

    public Integer getSFYy() {
        return sFYy;
    }

    public void setSFYy(Integer sFYy) {
        this.sFYy = sFYy;
    }

    public Integer getSFIss() {
        return sFIss;
    }

    public void setSFIss(Integer sFIss) {
        this.sFIss = sFIss;
    }

    public String getSFDmy() {
        return sFDmy;
    }

    public void setSFDmy(String sFDmy) {
        this.sFDmy = sFDmy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sFCd != null ? sFCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SFrequency)) {
            return false;
        }
        SFrequency other = (SFrequency) object;
        if ((this.sFCd == null && other.sFCd != null) || (this.sFCd != null && !this.sFCd.equals(other.sFCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serial_master.SFrequency[ sFCd=" + sFCd + " ]";
    }

    @XmlTransient
    public Collection<SRequest> getSRequestCollection() {
        return sRequestCollection;
    }

    public void setSRequestCollection(Collection<SRequest> sRequestCollection) {
        this.sRequestCollection = sRequestCollection;
    }
    
}
