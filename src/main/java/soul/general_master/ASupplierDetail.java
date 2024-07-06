/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import soul.acquisition.suggestions.AOrdermaster;
import soul.acquisition.suggestions.ARequest;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "a_supplier_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ASupplierDetail.findAll", query = "SELECT a FROM ASupplierDetail a"),
    @NamedQuery(name = "ASupplierDetail.findByASCd", query = "SELECT a FROM ASupplierDetail a WHERE a.aSCd = :aSCd"),
    @NamedQuery(name = "ASupplierDetail.findByASSpl", query = "SELECT a FROM ASupplierDetail a WHERE a.aSSpl = :aSSpl"),
    @NamedQuery(name = "ASupplierDetail.findByASStatus", query = "SELECT a FROM ASupplierDetail a WHERE a.aSStatus = :aSStatus"),
    @NamedQuery(name = "ASupplierDetail.findByASName", query = "SELECT a FROM ASupplierDetail a WHERE a.aSName = :aSName"),
    @NamedQuery(name = "ASupplierDetail.findByASAdd1", query = "SELECT a FROM ASupplierDetail a WHERE a.aSAdd1 = :aSAdd1"),
    @NamedQuery(name = "ASupplierDetail.findByASAdd2", query = "SELECT a FROM ASupplierDetail a WHERE a.aSAdd2 = :aSAdd2"),
    @NamedQuery(name = "ASupplierDetail.findByASCountry", query = "SELECT a FROM ASupplierDetail a WHERE a.aSCountry = :aSCountry"),
    @NamedQuery(name = "ASupplierDetail.findByASState", query = "SELECT a FROM ASupplierDetail a WHERE a.aSState = :aSState"),
    @NamedQuery(name = "ASupplierDetail.findByASCity", query = "SELECT a FROM ASupplierDetail a WHERE a.aSCity = :aSCity"),
    @NamedQuery(name = "ASupplierDetail.findByASPin", query = "SELECT a FROM ASupplierDetail a WHERE a.aSPin = :aSPin"),
    @NamedQuery(name = "ASupplierDetail.findByASRemark", query = "SELECT a FROM ASupplierDetail a WHERE a.aSRemark = :aSRemark"),
    @NamedQuery(name = "ASupplierDetail.findByASContPer", query = "SELECT a FROM ASupplierDetail a WHERE a.aSContPer = :aSContPer"),
    @NamedQuery(name = "ASupplierDetail.findByASEmail", query = "SELECT a FROM ASupplierDetail a WHERE a.aSEmail = :aSEmail"),
    @NamedQuery(name = "ASupplierDetail.findByASPhone", query = "SELECT a FROM ASupplierDetail a WHERE a.aSPhone = :aSPhone"),
    @NamedQuery(name = "ASupplierDetail.findByASFax", query = "SELECT a FROM ASupplierDetail a WHERE a.aSFax = :aSFax"),
    @NamedQuery(name = "ASupplierDetail.findByASTelex", query = "SELECT a FROM ASupplierDetail a WHERE a.aSTelex = :aSTelex"),
    @NamedQuery(name = "ASupplierDetail.findByASAct", query = "SELECT a FROM ASupplierDetail a WHERE a.aSAct = :aSAct"),
    @NamedQuery(name = "ASupplierDetail.findByASDesg", query = "SELECT a FROM ASupplierDetail a WHERE a.aSDesg = :aSDesg"),
    @NamedQuery(name = "ASupplierDetail.findByASCIndex", query = "SELECT a FROM ASupplierDetail a WHERE a.aSCIndex = :aSCIndex")})
public class ASupplierDetail implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aOSupCd")
    private Collection<AOrdermaster> aOrdermasterCollection;
    @OneToMany(mappedBy = "aRPubCd")
    private Collection<ARequest> aRequestCollection;
    @OneToMany(mappedBy = "aRSupCd")
    private Collection<ARequest> aRequestCollection1;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "a_s_cd")
    private String aSCd;
    @Size(max = 100)
    @Column(name = "a_s_spl")
    private String aSSpl;
    @Size(max = 100)
    @Column(name = "a_s_status")
    private String aSStatus;
    @Size(max = 500)
    @Column(name = "a_s_name")
    private String aSName;
    @Size(max = 100)
    @Column(name = "a_s_add1")
    private String aSAdd1;
    @Size(max = 100)
    @Column(name = "a_s_add2")
    private String aSAdd2;
    @Size(max = 100)
    @Column(name = "a_s_country")
    private String aSCountry;
    @Size(max = 100)
    @Column(name = "a_s_state")
    private String aSState;
    @Size(max = 100)
    @Column(name = "a_s_city")
    private String aSCity;
    @Size(max = 100)
    @Column(name = "a_s_pin")
    private String aSPin;
    @Size(max = 300)
    @Column(name = "a_s_remark")
    private String aSRemark;
    @Size(max = 100)
    @Column(name = "a_s_cont_per")
    private String aSContPer;
    @Size(max = 100)
    @Column(name = "a_s_email")
    private String aSEmail;
    @Size(max = 100)
    @Column(name = "a_s_phone")
    private String aSPhone;
    @Size(max = 100)
    @Column(name = "a_s_fax")
    private String aSFax;
    @Size(max = 100)
    @Column(name = "a_s_telex")
    private String aSTelex;
    @Size(max = 100)
    @Column(name = "a_s_act")
    private String aSAct;
    @Size(max = 100)
    @Column(name = "a_s_desg")
    private String aSDesg;
    @Column(name = "a_s_c_index")
    private Long aSCIndex;

    public ASupplierDetail() {
    }

    public ASupplierDetail(String aSCd) {
        this.aSCd = aSCd;
    }

    public String getASCd() {
        return aSCd;
    }

    public void setASCd(String aSCd) {
        this.aSCd = aSCd;
    }

    public String getASSpl() {
        return aSSpl;
    }

    public void setASSpl(String aSSpl) {
        this.aSSpl = aSSpl;
    }

    public String getASStatus() {
        return aSStatus;
    }

    public void setASStatus(String aSStatus) {
        this.aSStatus = aSStatus;
    }

    public String getASName() {
        return aSName;
    }

    public void setASName(String aSName) {
        this.aSName = aSName;
    }

    public String getASAdd1() {
        return aSAdd1;
    }

    public void setASAdd1(String aSAdd1) {
        this.aSAdd1 = aSAdd1;
    }

    public String getASAdd2() {
        return aSAdd2;
    }

    public void setASAdd2(String aSAdd2) {
        this.aSAdd2 = aSAdd2;
    }

    public String getASCountry() {
        return aSCountry;
    }

    public void setASCountry(String aSCountry) {
        this.aSCountry = aSCountry;
    }

    public String getASState() {
        return aSState;
    }

    public void setASState(String aSState) {
        this.aSState = aSState;
    }

    public String getASCity() {
        return aSCity;
    }

    public void setASCity(String aSCity) {
        this.aSCity = aSCity;
    }

    public String getASPin() {
        return aSPin;
    }

    public void setASPin(String aSPin) {
        this.aSPin = aSPin;
    }

    public String getASRemark() {
        return aSRemark;
    }

    public void setASRemark(String aSRemark) {
        this.aSRemark = aSRemark;
    }

    public String getASContPer() {
        return aSContPer;
    }

    public void setASContPer(String aSContPer) {
        this.aSContPer = aSContPer;
    }

    public String getASEmail() {
        return aSEmail;
    }

    public void setASEmail(String aSEmail) {
        this.aSEmail = aSEmail;
    }

    public String getASPhone() {
        return aSPhone;
    }

    public void setASPhone(String aSPhone) {
        this.aSPhone = aSPhone;
    }

    public String getASFax() {
        return aSFax;
    }

    public void setASFax(String aSFax) {
        this.aSFax = aSFax;
    }

    public String getASTelex() {
        return aSTelex;
    }

    public void setASTelex(String aSTelex) {
        this.aSTelex = aSTelex;
    }

    public String getASAct() {
        return aSAct;
    }

    public void setASAct(String aSAct) {
        this.aSAct = aSAct;
    }

    public String getASDesg() {
        return aSDesg;
    }

    public void setASDesg(String aSDesg) {
        this.aSDesg = aSDesg;
    }

    public Long getASCIndex() {
        return aSCIndex;
    }

    public void setASCIndex(Long aSCIndex) {
        this.aSCIndex = aSCIndex;
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
        if (!(object instanceof ASupplierDetail)) {
            return false;
        }
        ASupplierDetail other = (ASupplierDetail) object;
        if ((this.aSCd == null && other.aSCd != null) || (this.aSCd != null && !this.aSCd.equals(other.aSCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.ASupplierDetail[ aSCd=" + aSCd + " ]";
    }

    @XmlTransient
    public Collection<ARequest> getARequestCollection() {
        return aRequestCollection;
    }

    public void setARequestCollection(Collection<ARequest> aRequestCollection) {
        this.aRequestCollection = aRequestCollection;
    }

    @XmlTransient
    public Collection<ARequest> getARequestCollection1() {
        return aRequestCollection1;
    }

    public void setARequestCollection1(Collection<ARequest> aRequestCollection1) {
        this.aRequestCollection1 = aRequestCollection1;
    }

    @XmlTransient
    public Collection<AOrdermaster> getAOrdermasterCollection() {
        return aOrdermasterCollection;
    }

    public void setAOrdermasterCollection(Collection<AOrdermaster> aOrdermasterCollection) {
        this.aOrdermasterCollection = aOrdermasterCollection;
    }
}
