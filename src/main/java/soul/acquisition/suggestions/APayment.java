/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.acquisition.suggestions;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "a_payment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "APayment.findAll", query = "SELECT a FROM APayment a"),
    @NamedQuery(name = "APayment.findByAPPayId", query = "SELECT a FROM APayment a WHERE a.aPPayId = :aPPayId"),
    @NamedQuery(name = "APayment.findByAPRefNo", query = "SELECT a FROM APayment a WHERE a.aPRefNo = :aPRefNo"),
    @NamedQuery(name = "APayment.findByAPAmount", query = "SELECT a FROM APayment a WHERE a.aPAmount = :aPAmount"),
    @NamedQuery(name = "APayment.findByAPChequeNo", query = "SELECT a FROM APayment a WHERE a.aPChequeNo = :aPChequeNo"),
    @NamedQuery(name = "APayment.findByAPChequeDt", query = "SELECT a FROM APayment a WHERE a.aPChequeDt = :aPChequeDt"),
    @NamedQuery(name = "APayment.findByAPBranch", query = "SELECT a FROM APayment a WHERE a.aPBranch = :aPBranch"),
    @NamedQuery(name = "APayment.findByAPPayType", query = "SELECT a FROM APayment a WHERE a.aPPayType = :aPPayType"),
    @NamedQuery(name = "APayment.findByAPBank", query = "SELECT a FROM APayment a WHERE a.aPBank = :aPBank"),
    @NamedQuery(name = "APayment.findByAPBkCharges", query = "SELECT a FROM APayment a WHERE a.aPBkCharges = :aPBkCharges"),
    @NamedQuery(name = "APayment.findByAINoRefund", query = "SELECT a FROM APayment a WHERE a.aINoRefund = :aINoRefund")})
public class APayment implements Serializable {
    @OneToMany(mappedBy = "aPPayId")
    private Collection<AInvoice> aInvoiceCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "a_p_pay_id")
    private Integer aPPayId;
    @Size(max = 100)
    @Column(name = "a_p_ref_no")
    private String aPRefNo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "a_p_amount")
    private BigDecimal aPAmount;
    @Size(max = 100)
    @Column(name = "a_p_cheque_no")
    private String aPChequeNo;
    @Column(name = "a_p_cheque_dt")
    @Temporal(TemporalType.DATE)
    private Date aPChequeDt;
    @Size(max = 100)
    @Column(name = "a_p_branch")
    private String aPBranch;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "a_p_pay_type")
    private String aPPayType;
    @Size(max = 100)
    @Column(name = "a_p_bank")
    private String aPBank;
    @Column(name = "a_p_bk_charges")
    private BigDecimal aPBkCharges;
    @Column(name = "a_i_no_refund")
    private Long aINoRefund;

    public APayment() {
    }

    public APayment(Integer aPPayId) {
        this.aPPayId = aPPayId;
    }

    public APayment(Integer aPPayId, BigDecimal aPAmount, String aPPayType, BigDecimal aPBkCharges) {
        this.aPPayId = aPPayId;
        this.aPAmount = aPAmount;
        this.aPPayType = aPPayType;
        this.aPBkCharges = aPBkCharges;
    }

    public Integer getAPPayId() {
        return aPPayId;
    }

    public void setAPPayId(Integer aPPayId) {
        this.aPPayId = aPPayId;
    }

    public String getAPRefNo() {
        return aPRefNo;
    }

    public void setAPRefNo(String aPRefNo) {
        this.aPRefNo = aPRefNo;
    }

    public BigDecimal getAPAmount() {
        return aPAmount;
    }

    public void setAPAmount(BigDecimal aPAmount) {
        this.aPAmount = aPAmount;
    }

    public String getAPChequeNo() {
        return aPChequeNo;
    }

    public void setAPChequeNo(String aPChequeNo) {
        this.aPChequeNo = aPChequeNo;
    }

    public Date getAPChequeDt() {
        return aPChequeDt;
    }

    public void setAPChequeDt(Date aPChequeDt) {
        this.aPChequeDt = aPChequeDt;
    }

    public String getAPBranch() {
        return aPBranch;
    }

    public void setAPBranch(String aPBranch) {
        this.aPBranch = aPBranch;
    }

    public String getAPPayType() {
        return aPPayType;
    }

    public void setAPPayType(String aPPayType) {
        this.aPPayType = aPPayType;
    }

    public String getAPBank() {
        return aPBank;
    }

    public void setAPBank(String aPBank) {
        this.aPBank = aPBank;
    }

    public BigDecimal getAPBkCharges() {
        return aPBkCharges;
    }

    public void setAPBkCharges(BigDecimal aPBkCharges) {
        this.aPBkCharges = aPBkCharges;
    }

    public Long getAINoRefund() {
        return aINoRefund;
    }

    public void setAINoRefund(Long aINoRefund) {
        this.aINoRefund = aINoRefund;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aPPayId != null ? aPPayId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof APayment)) {
            return false;
        }
        APayment other = (APayment) object;
        if ((this.aPPayId == null && other.aPPayId != null) || (this.aPPayId != null && !this.aPPayId.equals(other.aPPayId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.acquisition.suggestions.APayment[ aPPayId=" + aPPayId + " ]";
    }

    @XmlTransient
    public Collection<AInvoice> getAInvoiceCollection() {
        return aInvoiceCollection;
    }

    public void setAInvoiceCollection(Collection<AInvoice> aInvoiceCollection) {
        this.aInvoiceCollection = aInvoiceCollection;
    }
    
}
