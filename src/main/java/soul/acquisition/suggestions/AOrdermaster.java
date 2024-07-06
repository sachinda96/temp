/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.acquisition.suggestions;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import soul.general_master.ASupplierDetail;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "a_ordermaster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AOrdermaster.findAll", query = "SELECT a FROM AOrdermaster a"),
    @NamedQuery(name = "AOrdermaster.findByAOPoNo", query = "SELECT a FROM AOrdermaster a WHERE a.aOPoNo = :aOPoNo"),
    @NamedQuery(name = "AOrdermaster.findByAOTotRem", query = "SELECT a FROM AOrdermaster a WHERE a.aOTotRem = :aOTotRem"),
    @NamedQuery(name = "AOrdermaster.findByAOLastRem", query = "SELECT a FROM AOrdermaster a WHERE a.aOLastRem = :aOLastRem"),
    @NamedQuery(name = "AOrdermaster.findByAOPoDt", query = "SELECT a FROM AOrdermaster a WHERE a.aOPoDt = :aOPoDt"),
    @NamedQuery(name = "AOrdermaster.findByAOPoNo1", query = "SELECT a FROM AOrdermaster a WHERE a.aOPoNo1 = :aOPoNo1"),
    @NamedQuery(name = "AOrdermaster.findByAOExpDt", query = "SELECT a FROM AOrdermaster a WHERE a.aOExpDt = :aOExpDt"),
    @NamedQuery(name = "AOrdermaster.findByAOStanding", query = "SELECT a FROM AOrdermaster a WHERE a.aOStanding = :aOStanding"),
    @NamedQuery(name = "AOrdermaster.findByAOApayment", query = "SELECT a FROM AOrdermaster a WHERE a.aOApayment = :aOApayment"),
    @NamedQuery(name = "AOrdermaster.findByAOSAdd1", query = "SELECT a FROM AOrdermaster a WHERE a.aOSAdd1 = :aOSAdd1"),
    @NamedQuery(name = "AOrdermaster.findByAOSAdd2", query = "SELECT a FROM AOrdermaster a WHERE a.aOSAdd2 = :aOSAdd2"),
    @NamedQuery(name = "AOrdermaster.findByAOSCity", query = "SELECT a FROM AOrdermaster a WHERE a.aOSCity = :aOSCity"),
    @NamedQuery(name = "AOrdermaster.findByAOSState", query = "SELECT a FROM AOrdermaster a WHERE a.aOSState = :aOSState"),
    @NamedQuery(name = "AOrdermaster.findByAOSCountry", query = "SELECT a FROM AOrdermaster a WHERE a.aOSCountry = :aOSCountry"),
    @NamedQuery(name = "AOrdermaster.findByAOSPin", query = "SELECT a FROM AOrdermaster a WHERE a.aOSPin = :aOSPin"),
    @NamedQuery(name = "AOrdermaster.findByAOSEmail", query = "SELECT a FROM AOrdermaster a WHERE a.aOSEmail = :aOSEmail"),
    @NamedQuery(name = "AOrdermaster.findByAOCash", query = "SELECT a FROM AOrdermaster a WHERE a.aOCash = :aOCash"),
    @NamedQuery(name = "AOrdermaster.findByAOStatus", query = "SELECT a FROM AOrdermaster a WHERE a.aOStatus = :aOStatus"),
    @NamedQuery(name = "AOrdermaster.findBySupplierName", query = "SELECT a FROM AOrdermaster a WHERE a.aOSupCd.aSName = :aSName"),
    @NamedQuery(name = "AOrdermaster.findBySimple", query = "SELECT a  FROM AOrdermaster a  ORDER BY a.aOPoNo"),
    @NamedQuery(name = "AOrdermaster.findByAdvance", query = "select distinct c from AOrdermaster c left outer join fetch c.aRequestCollection  ORDER BY c.aOPoNo"),
    @NamedQuery(name = "AOrdermaster.findBySupplierCode", query = "SELECT a FROM AOrdermaster a WHERE a.aOSupCd.aSCd = :aSCd"),
    @NamedQuery(name = "AOrdermaster.findReminderBySuppCode ", query = "SELECT a FROM AOrdermaster a WHERE a.aOSupCd.aSCd = :aSCd and a.aOExpDt < CURRENT_DATE"),
    
    @NamedQuery(name = "AOrdermaster.findByAOPoNo1AndDate", query = "SELECT a FROM AOrdermaster a WHERE a.aOPoNo1 = ?1 AND a.aOPoDt BETWEEN ?1 AND ?2"),
    @NamedQuery(name = "AOrdermaster.findBySupplierNameAndDate", query = "SELECT a FROM AOrdermaster a WHERE a.aOSupCd.aSName = ?1 AND a.aOPoDt BETWEEN ?1 AND ?2"),
    @NamedQuery(name = "AOrdermaster.findBySupplierCodeAndDate", query = "SELECT a FROM AOrdermaster a WHERE a.aOSupCd.aSCd = ?1 AND a.aOPoDt BETWEEN ?1 AND ?2"),
    @NamedQuery(name = "AOrdermaster.findReminderBySuppCodeAndExpDate ", query = "SELECT a FROM AOrdermaster a WHERE a.aOSupCd.aSCd = ?1 and a.aOExpDt < ?2"),
    @NamedQuery(name = "AOrdermaster.findByAOStatusAndDate", query = "SELECT a FROM AOrdermaster a WHERE a.aOStatus = ?1 AND a.aOPoDt BETWEEN ?1 AND ?2"),
    @NamedQuery(name = "AOrdermaster.findByAOPoDtBetween", query = "SELECT a FROM AOrdermaster a WHERE a.aOPoDt BETWEEN ?1 AND ?2"),
})
public class AOrdermaster implements Serializable {
    @OneToMany(mappedBy = "aRPoNo")
    private Collection<ARequest> aRequestCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aIPoNo")
    private Collection<AInvoice> aInvoiceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aOPoNo")
    private Collection<AReceive> aReceiveCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "a_o_po_no")
    private Integer aOPoNo;
    @Column(name = "a_o_tot_rem")
    private Integer aOTotRem;
    @Column(name = "a_o_last_rem")
    @Temporal(TemporalType.DATE)
    private Date aOLastRem;
    @Column(name = "a_o_po_dt")
    @Temporal(TemporalType.DATE)
    private Date aOPoDt;
    @Size(max = 100)
    @Column(name = "a_o_po_no1")
    private String aOPoNo1;
    @Column(name = "a_o_exp_dt")
    @Temporal(TemporalType.DATE)
    private Date aOExpDt;
    @Size(max = 100)
    @Column(name = "a_o_standing")
    private String aOStanding;
    @Size(max = 100)
    @Column(name = "a_o_apayment")
    private String aOApayment;
    @Size(max = 510)
    @Column(name = "a_o_s_add1")
    private String aOSAdd1;
    @Size(max = 510)
    @Column(name = "a_o_s_add2")
    private String aOSAdd2;
    @Size(max = 100)
    @Column(name = "a_o_s_city")
    private String aOSCity;
    @Size(max = 100)
    @Column(name = "a_o_s_state")
    private String aOSState;
    @Size(max = 100)
    @Column(name = "a_o_s_country")
    private String aOSCountry;
    @Size(max = 100)
    @Column(name = "a_o_s_pin")
    private String aOSPin;
    @Size(max = 100)
    @Column(name = "a_o_s_email")
    private String aOSEmail;
    @Column(name = "a_o_cash")
    private Short aOCash;
    @Size(max = 100)
    @Column(name = "a_o_status")
    private String aOStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aOrdermaster")
    private Collection<AOrder> aOrderCollection;
    @JoinColumn(name = "a_o_sup_cd", referencedColumnName = "a_s_cd")
    @ManyToOne(optional = false)
    private ASupplierDetail aOSupCd;

    
    public AOrdermaster() {
    }

    public AOrdermaster(Integer aOPoNo) {
        this.aOPoNo = aOPoNo;
    }

    public Integer getAOPoNo() {
        return aOPoNo;
    }

    public void setAOPoNo(Integer aOPoNo) {
        this.aOPoNo = aOPoNo;
    }

    public Integer getAOTotRem() {
        return aOTotRem;
    }

    public void setAOTotRem(Integer aOTotRem) {
        this.aOTotRem = aOTotRem;
    }

    public Date getAOLastRem() {
        return aOLastRem;
    }

    public void setAOLastRem(Date aOLastRem) {
        this.aOLastRem = aOLastRem;
    }

    public Date getAOPoDt() {
        return aOPoDt;
    }

    public void setAOPoDt(Date aOPoDt) {
        this.aOPoDt = aOPoDt;
    }

    public String getAOPoNo1() {
        return aOPoNo1;
    }

    public void setAOPoNo1(String aOPoNo1) {
        this.aOPoNo1 = aOPoNo1;
    }

    public Date getAOExpDt() {
        return aOExpDt;
    }

    public void setAOExpDt(Date aOExpDt) {
        this.aOExpDt = aOExpDt;
    }

    public String getAOStanding() {
        return aOStanding;
    }

    public void setAOStanding(String aOStanding) {
        this.aOStanding = aOStanding;
    }

    public String getAOApayment() {
        return aOApayment;
    }

    public void setAOApayment(String aOApayment) {
        this.aOApayment = aOApayment;
    }

    public String getAOSAdd1() {
        return aOSAdd1;
    }

    public void setAOSAdd1(String aOSAdd1) {
        this.aOSAdd1 = aOSAdd1;
    }

    public String getAOSAdd2() {
        return aOSAdd2;
    }

    public void setAOSAdd2(String aOSAdd2) {
        this.aOSAdd2 = aOSAdd2;
    }

    public String getAOSCity() {
        return aOSCity;
    }

    public void setAOSCity(String aOSCity) {
        this.aOSCity = aOSCity;
    }

    public String getAOSState() {
        return aOSState;
    }

    public void setAOSState(String aOSState) {
        this.aOSState = aOSState;
    }

    public String getAOSCountry() {
        return aOSCountry;
    }

    public void setAOSCountry(String aOSCountry) {
        this.aOSCountry = aOSCountry;
    }

    public String getAOSPin() {
        return aOSPin;
    }

    public void setAOSPin(String aOSPin) {
        this.aOSPin = aOSPin;
    }

    public String getAOSEmail() {
        return aOSEmail;
    }

    public void setAOSEmail(String aOSEmail) {
        this.aOSEmail = aOSEmail;
    }

    public Short getAOCash() {
        return aOCash;
    }

    public void setAOCash(Short aOCash) {
        this.aOCash = aOCash;
    }

    public String getAOStatus() {
        return aOStatus;
    }

    public void setAOStatus(String aOStatus) {
        this.aOStatus = aOStatus;
    }

    @XmlTransient
    public Collection<AOrder> getAOrderCollection() {
        return aOrderCollection;
    }

    public void setAOrderCollection(Collection<AOrder> aOrderCollection) {
        this.aOrderCollection = aOrderCollection;
    }

    public ASupplierDetail getAOSupCd() {
        return aOSupCd;
    }

    public void setAOSupCd(ASupplierDetail aOSupCd) {
        this.aOSupCd = aOSupCd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aOPoNo != null ? aOPoNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AOrdermaster)) {
            return false;
        }
        AOrdermaster other = (AOrdermaster) object;
        if ((this.aOPoNo == null && other.aOPoNo != null) || (this.aOPoNo != null && !this.aOPoNo.equals(other.aOPoNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.acquisition.suggestions.AOrdermaster[ aOPoNo=" + aOPoNo + " ]";
    }

    @XmlTransient
    public Collection<AReceive> getAReceiveCollection() {
        return aReceiveCollection;
    }

    public void setAReceiveCollection(Collection<AReceive> aReceiveCollection) {
        this.aReceiveCollection = aReceiveCollection;
    }

    @XmlTransient
    public Collection<AInvoice> getAInvoiceCollection() {
        return aInvoiceCollection;
    }

    public void setAInvoiceCollection(Collection<AInvoice> aInvoiceCollection) {
        this.aInvoiceCollection = aInvoiceCollection;
    }

    @XmlTransient
    public Collection<ARequest> getARequestCollection() {
        return aRequestCollection;
    }

    public void setARequestCollection(Collection<ARequest> aRequestCollection) {
        this.aRequestCollection = aRequestCollection;
    }
    
}
