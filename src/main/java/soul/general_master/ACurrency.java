/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import soul.acquisition.suggestions.ARequest;
import soul.serialControl.SRequest;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "a_currency")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ACurrency.findAll", query = "SELECT a FROM ACurrency a"),
    @NamedQuery(name = "ACurrency.findByACCd", query = "SELECT a FROM ACurrency a WHERE a.aCCd = :aCCd"),
    @NamedQuery(name = "ACurrency.findByACNm", query = "SELECT a FROM ACurrency a WHERE a.aCNm = :aCNm"),
    @NamedQuery(name = "ACurrency.findByACRate", query = "SELECT a FROM ACurrency a WHERE a.aCRate = :aCRate"),
    @NamedQuery(name = "ACurrency.findByACCountry", query = "SELECT a FROM ACurrency a WHERE a.aCCountry = :aCCountry"),
    @NamedQuery(name = "ACurrency.findByACDt", query = "SELECT a FROM ACurrency a WHERE a.aCDt = :aCDt"),
    @NamedQuery(name = "ACurrency.findByDateBetween", query = "SELECT a FROM ACurrency a WHERE a.aCDt BETWEEN ?1 and ?2"),
    @NamedQuery(name = "ACurrency.findByDateGtr", query = "SELECT a FROM ACurrency a WHERE a.aCDt >= :aCDt")})
public class ACurrency implements Serializable {
    @OneToMany(mappedBy = "sRCurrencyCd")
    private Collection<SRequest> sRequestCollection;
    @OneToMany(mappedBy = "aRCurrencyCd")
    private Collection<ARequest> aRequestCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "a_c_cd")
    private String aCCd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "a_c_nm")
    private String aCNm;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "a_c_rate")
    private BigDecimal aCRate;
    @Size(max = 470)
    @Column(name = "a_c_country")
    private String aCCountry;
    @Column(name = "a_c_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date aCDt;

    public ACurrency() {
    }

    public ACurrency(String aCCd) {
        this.aCCd = aCCd;
    }

    public ACurrency(String aCCd, String aCNm, BigDecimal aCRate) {
        this.aCCd = aCCd;
        this.aCNm = aCNm;
        this.aCRate = aCRate;
    }

    public String getACCd() {
        return aCCd;
    }

    public void setACCd(String aCCd) {
        this.aCCd = aCCd;
    }

    public String getACNm() {
        return aCNm;
    }

    public void setACNm(String aCNm) {
        this.aCNm = aCNm;
    }

    public BigDecimal getACRate() {
        return aCRate;
    }

    public void setACRate(BigDecimal aCRate) {
        this.aCRate = aCRate;
    }

    public String getACCountry() {
        return aCCountry;
    }

    public void setACCountry(String aCCountry) {
        this.aCCountry = aCCountry;
    }

    public Date getACDt() {
        return aCDt;
    }

    public void setACDt(Date aCDt) {
        this.aCDt = aCDt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aCCd != null ? aCCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ACurrency)) {
            return false;
        }
        ACurrency other = (ACurrency) object;
        if ((this.aCCd == null && other.aCCd != null) || (this.aCCd != null && !this.aCCd.equals(other.aCCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.ACurrency[ aCCd=" + aCCd + " ]";
    }

    @XmlTransient
    public Collection<ARequest> getARequestCollection() {
        return aRequestCollection;
    }

    public void setARequestCollection(Collection<ARequest> aRequestCollection) {
        this.aRequestCollection = aRequestCollection;
    }

    @XmlTransient
    public Collection<SRequest> getSRequestCollection() {
        return sRequestCollection;
    }

    public void setSRequestCollection(Collection<SRequest> sRequestCollection) {
        this.sRequestCollection = sRequestCollection;
    }
}
