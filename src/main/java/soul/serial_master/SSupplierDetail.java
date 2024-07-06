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
import soul.general_master.Countrymaster;
import soul.serialControl.SRequest;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "s_supplier_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SSupplierDetail.findAll", query = "SELECT s FROM SSupplierDetail s"),
    @NamedQuery(name = "SSupplierDetail.findByASCd", query = "SELECT s FROM SSupplierDetail s WHERE s.aSCd = :aSCd"),
    @NamedQuery(name = "SSupplierDetail.findByASName", query = "SELECT s FROM SSupplierDetail s WHERE s.aSName LIKE :aSName"),
    @NamedQuery(name = "SSupplierDetail.findByASAdd1", query = "SELECT s FROM SSupplierDetail s WHERE s.aSAdd1 = :aSAdd1"),
    @NamedQuery(name = "SSupplierDetail.findByASAdd2", query = "SELECT s FROM SSupplierDetail s WHERE s.aSAdd2 = :aSAdd2"),
    @NamedQuery(name = "SSupplierDetail.findByASAdd3", query = "SELECT s FROM SSupplierDetail s WHERE s.aSAdd3 = :aSAdd3"),
    @NamedQuery(name = "SSupplierDetail.findByASCity", query = "SELECT s FROM SSupplierDetail s WHERE s.aSCity = :aSCity"),
    @NamedQuery(name = "SSupplierDetail.findByASPin", query = "SELECT s FROM SSupplierDetail s WHERE s.aSPin = :aSPin"),
    @NamedQuery(name = "SSupplierDetail.findByASCountry", query = "SELECT s FROM SSupplierDetail s WHERE s.aSCountry = :aSCountry"),
    @NamedQuery(name = "SSupplierDetail.findByASState", query = "SELECT s FROM SSupplierDetail s WHERE s.aSState = :aSState"),
    @NamedQuery(name = "SSupplierDetail.findByASRemark", query = "SELECT s FROM SSupplierDetail s WHERE s.aSRemark = :aSRemark"),
    @NamedQuery(name = "SSupplierDetail.findByASLocal", query = "SELECT s FROM SSupplierDetail s WHERE s.aSLocal = :aSLocal"),
    @NamedQuery(name = "SSupplierDetail.findByASContPer", query = "SELECT s FROM SSupplierDetail s WHERE s.aSContPer = :aSContPer"),
    @NamedQuery(name = "SSupplierDetail.findByASEmail", query = "SELECT s FROM SSupplierDetail s WHERE s.aSEmail = :aSEmail"),
    @NamedQuery(name = "SSupplierDetail.findByASPhone", query = "SELECT s FROM SSupplierDetail s WHERE s.aSPhone = :aSPhone"),
    @NamedQuery(name = "SSupplierDetail.findByASFax", query = "SELECT s FROM SSupplierDetail s WHERE s.aSFax = :aSFax"),
    @NamedQuery(name = "SSupplierDetail.findByASTelex", query = "SELECT s FROM SSupplierDetail s WHERE s.aSTelex = :aSTelex"),
    @NamedQuery(name = "SSupplierDetail.findByASAct", query = "SELECT s FROM SSupplierDetail s WHERE s.aSAct = :aSAct"),
    @NamedQuery(name = "SSupplierDetail.findByASDesg", query = "SELECT s FROM SSupplierDetail s WHERE s.aSDesg = :aSDesg"),
    @NamedQuery(name = "SSupplierDetail.findByASCIndex", query = "SELECT s FROM SSupplierDetail s WHERE s.aSCIndex = :aSCIndex"),
    @NamedQuery(name = "SSupplierDetail.findByRowguid", query = "SELECT s FROM SSupplierDetail s WHERE s.rowguid = :rowguid"),
    @NamedQuery(name = "SSupplierDetail.findByASBankNm", query = "SELECT s FROM SSupplierDetail s WHERE s.aSBankNm = :aSBankNm"),
    @NamedQuery(name = "SSupplierDetail.findByASBankBr", query = "SELECT s FROM SSupplierDetail s WHERE s.aSBankBr = :aSBankBr"),
    @NamedQuery(name = "SSupplierDetail.findByASPayTerm", query = "SELECT s FROM SSupplierDetail s WHERE s.aSPayTerm = :aSPayTerm"),
    @NamedQuery(name = "SSupplierDetail.findByASCrDay", query = "SELECT s FROM SSupplierDetail s WHERE s.aSCrDay = :aSCrDay"),
    @NamedQuery(name = "SSupplierDetail.findByASDiscount", query = "SELECT s FROM SSupplierDetail s WHERE s.aSDiscount = :aSDiscount"),
    
    //@NamedQuery(name = "SSupplierDetail.findByASActAndASType", query = "SELECT s FROM SSupplierDetail s WHERE s.aSAct = ?1 AND s.aSType = ?2")
})
public class SSupplierDetail implements Serializable {
    @OneToMany(mappedBy = "sRSupCd")
    private Collection<SRequest> sRequestCollection;
    @OneToMany(mappedBy = "sRPubCd")
    private Collection<SRequest> sRequestCollection1;   
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "a_s_cd")
    private String aSCd;
    @Size(max = 200)
    @Column(name = "a_s_name")
    private String aSName;
    @Size(max = 510)
    @Column(name = "a_s_add1")
    private String aSAdd1;
    @Size(max = 400)
    @Column(name = "a_s_add2")
    private String aSAdd2;
    @Size(max = 400)
    @Column(name = "a_s_add3")
    private String aSAdd3;
    @Size(max = 200)
    @Column(name = "a_s_city")
    private String aSCity;
    @Size(max = 10)
    @Column(name = "a_s_pin")
    private String aSPin;
    @Size(max = 20)
    @Column(name = "a_s_country")
    private String aSCountry;
    @Size(max = 100)
    @Column(name = "a_s_state")
    private String aSState;
    @Size(max = 510)
    @Column(name = "a_s_remark")
    private String aSRemark;
    @Size(max = 20)
    @Column(name = "a_s_local")
    private String aSLocal;
    @Size(max = 300)
    @Column(name = "a_s_cont_per")
    private String aSContPer;
    @Size(max = 200)
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
    @Size(max = 40)
    @Column(name = "a_s_act")
    private String aSAct;
    @Size(max = 200)
    @Column(name = "a_s_desg")
    private String aSDesg;
    @Column(name = "a_s_c_index")
    private Integer aSCIndex;
    @Size(max = 64)
    @Column(name = "rowguid")
    private String rowguid;
    @Size(max = 510)
    @Column(name = "a_s_bank_nm")
    private String aSBankNm;
    @Size(max = 510)
    @Column(name = "a_s_bank_br")
    private String aSBankBr;
    @Size(max = 10)
    @Column(name = "a_s_pay_term")
    private String aSPayTerm;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "a_s_cr_day")
    private Double aSCrDay;
    @Column(name = "a_s_discount")
    private Double aSDiscount;

    public SSupplierDetail() {
    }

    public SSupplierDetail(String aSCd) {
        this.aSCd = aSCd;
    }

    public String getASCd() {
        return aSCd;
    }

    public void setASCd(String aSCd) {
        this.aSCd = aSCd;
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

    public String getASAdd3() {
        return aSAdd3;
    }

    public void setASAdd3(String aSAdd3) {
        this.aSAdd3 = aSAdd3;
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

    public String getASRemark() {
        return aSRemark;
    }

    public void setASRemark(String aSRemark) {
        this.aSRemark = aSRemark;
    }

    public String getASLocal() {
        return aSLocal;
    }

    public void setASLocal(String aSLocal) {
        this.aSLocal = aSLocal;
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

    public Integer getASCIndex() {
        return aSCIndex;
    }

    public void setASCIndex(Integer aSCIndex) {
        this.aSCIndex = aSCIndex;
    }

    public String getRowguid() {
        return rowguid;
    }

    public void setRowguid(String rowguid) {
        this.rowguid = rowguid;
    }

    public String getASBankNm() {
        return aSBankNm;
    }

    public void setASBankNm(String aSBankNm) {
        this.aSBankNm = aSBankNm;
    }

    public String getASBankBr() {
        return aSBankBr;
    }

    public void setASBankBr(String aSBankBr) {
        this.aSBankBr = aSBankBr;
    }

    public String getASPayTerm() {
        return aSPayTerm;
    }

    public void setASPayTerm(String aSPayTerm) {
        this.aSPayTerm = aSPayTerm;
    }

    public Double getASCrDay() {
        return aSCrDay;
    }

    public void setASCrDay(Double aSCrDay) {
        this.aSCrDay = aSCrDay;
    }

    public Double getASDiscount() {
        return aSDiscount;
    }

    public void setASDiscount(Double aSDiscount) {
        this.aSDiscount = aSDiscount;
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
        if (!(object instanceof SSupplierDetail)) {
            return false;
        }
        SSupplierDetail other = (SSupplierDetail) object;
        if ((this.aSCd == null && other.aSCd != null) || (this.aSCd != null && !this.aSCd.equals(other.aSCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serial_master.SSupplierDetail[ aSCd=" + aSCd + " ]";
    }
    
}
