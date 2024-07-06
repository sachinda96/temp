/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.acquisition.suggestions;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import soul.general_master.ABudget;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "a_invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AInvoice.findAll", query = "SELECT a FROM AInvoice a"),
    @NamedQuery(name = "AInvoice.findByAIId", query = "SELECT a FROM AInvoice a WHERE a.aIId = :aIId"),
    @NamedQuery(name = "AInvoice.findByAINo", query = "SELECT a FROM AInvoice a WHERE a.aINo = :aINo"),
    @NamedQuery(name = "AInvoice.findByAIRefNo", query = "SELECT a FROM AInvoice a WHERE a.aIRefNo = :aIRefNo"),
    @NamedQuery(name = "AInvoice.findByAIDt", query = "SELECT a FROM AInvoice a WHERE a.aIDt = :aIDt"),
    @NamedQuery(name = "AInvoice.findByAIProcDt", query = "SELECT a FROM AInvoice a WHERE a.aIProcDt = :aIProcDt"),
    @NamedQuery(name = "AInvoice.findByAINetamount", query = "SELECT a FROM AInvoice a WHERE a.aINetamount = :aINetamount"),
    @NamedQuery(name = "AInvoice.findByAIMisc", query = "SELECT a FROM AInvoice a WHERE a.aIMisc = :aIMisc"),
    @NamedQuery(name = "AInvoice.findByAITotal", query = "SELECT a FROM AInvoice a WHERE a.aITotal = :aITotal"),
    @NamedQuery(name = "AInvoice.findByAIPayType", query = "SELECT a FROM AInvoice a WHERE a.aIPayType = :aIPayType"),
    @NamedQuery(name = "AInvoice.findByAIPayNote", query = "SELECT a FROM AInvoice a WHERE a.aIPayNote = :aIPayNote"),
    @NamedQuery(name = "AInvoice.findByRefAmt", query = "SELECT a FROM AInvoice a WHERE a.refAmt = :refAmt"),
    @NamedQuery(name = "AInvoice.findByRefNoteDt", query = "SELECT a FROM AInvoice a WHERE a.refNoteDt = :refNoteDt"),
    @NamedQuery(name = "AInvoice.findByRefStatus", query = "SELECT a FROM AInvoice a WHERE a.refStatus = :refStatus"),
    @NamedQuery(name = "AInvoice.findByAPStatus", query = "SELECT a FROM AInvoice a WHERE a.aPStatus = :aPStatus"),
    
    @NamedQuery(name = "AInvoice.findByAIPoNoANDRegular", query = "SELECT a FROM AInvoice a WHERE a.aIPoNo.aOPoNo = :aIPoNo AND a.aIPoNo.aOApayment IN ('L1', 'L')"),
    @NamedQuery(name = "AInvoice.findByAOApaymentAOStatusRefStatusNull", query = "SELECT a FROM AInvoice a WHERE a.aIPoNo.aOApayment IN ?1 AND a.aIPoNo.aOStatus = ?2 AND a.refStatus IS NULL"),
    @NamedQuery(name = "AInvoice.findByNULLAPStatus", query = "SELECT a FROM AInvoice a WHERE a.aPStatus IS NULL"),
    @NamedQuery(name = "AInvoice.findByAOApaymentAOStatusRefStatusNullANDOrderNo", query = "SELECT a FROM AInvoice a WHERE a.aIPoNo.aOApayment IN ?1 AND a.aIPoNo.aOStatus = ?2 AND a.refStatus IS NULL AND a.aIPoNo.aOPoNo = ?3"),
    @NamedQuery(name = "AInvoice.findBySuppCd", query = "SELECT a FROM AInvoice a WHERE a.aIPoNo.aOSupCd.aSCd = :aSCd"),
    @NamedQuery(name = "AInvoice.findByDateBetween", query = "SELECT a FROM AInvoice a WHERE a.aIDt BETWEEN ?1 AND ?2"),
    @NamedQuery(name = "AInvoice.findByPaymentStatusNULL", query = "SELECT a FROM AInvoice a WHERE a.aPPayId IS NULL"),
    @NamedQuery(name = "AInvoice.findByPaymentStatusNOTNULL", query = "SELECT a FROM AInvoice a WHERE a.aPPayId IS NOT NULL"),
    
    @NamedQuery(name = "AInvoice.findBySuppCdAndPaymentNotNull", query = "SELECT a FROM AInvoice a WHERE a.aIPoNo.aOSupCd.aSCd = :aSCd AND a.aPPayId IS NOT NULL"),
    @NamedQuery(name = "AInvoice.findByPayCheckDateBetween", query = "SELECT a FROM AInvoice a WHERE a.aPPayId.aPChequeDt BETWEEN ?1 AND ?2"),
    @NamedQuery(name = "AInvoice.findByBudgetAndPaymentNotNull", query = "SELECT a FROM AInvoice a WHERE a.aIMiscBudget.aBBudgetCode = :aBBudgetCode AND a.aPPayId IS NOT NULL"),
    @NamedQuery(name = "AInvoice.findRefundByDate", query = "SELECT a FROM AInvoice a WHERE a.aIDt BETWEEN ?1 and ?2"),
   
    // @NamedQuery(name = "AInvoice.findByAOApaymentAOStatusRefStatusNullForRefund", query = "SELECT a FROM AInvoice a WHERE a.aIPoNo.aOApayment IN ?1 AND a.aIPoNo.aOStatus = ?2  AND   a.refStatus IS NULL AND a.aPStatus IS NOT NULL")
})

public class AInvoice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "a_i_id")
    private Integer aIId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "a_i_no")
    private String aINo;
    @Size(max = 100)
    @Column(name = "a_i_ref_no")
    private String aIRefNo;
    @Column(name = "a_i_dt")
    @Temporal(TemporalType.DATE)
    private Date aIDt;
    @Column(name = "a_i_proc_dt")
    @Temporal(TemporalType.DATE)
    private Date aIProcDt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "a_i_netamount")
    private BigDecimal aINetamount;
    @Column(name = "a_i_misc")
    private BigDecimal aIMisc;
    @Column(name = "a_i_total")
    private BigDecimal aITotal;
    @Size(max = 100)
    @Column(name = "a_i_pay_type")
    private String aIPayType;
    @Size(max = 300)
    @Column(name = "a_i_pay_note")
    private String aIPayNote;
    @Column(name = "ref_amt")
    private BigDecimal refAmt;
    @Column(name = "ref_note_dt")
    @Temporal(TemporalType.DATE)
    private Date refNoteDt;
    @Size(max = 100)
    @Column(name = "ref_status")
    private String refStatus;
    @Size(max = 100)
    @Column(name = "a_p_status")
    private String aPStatus;
    @JoinColumn(name = "a_i_misc_budget", referencedColumnName = "a_b_budget_code")
    @ManyToOne
    private ABudget aIMiscBudget;
    @JoinColumn(name = "a_i_po_no", referencedColumnName = "a_o_po_no")
    @ManyToOne(optional = false)
    private AOrdermaster aIPoNo;
    @JoinColumn(name = "a_p_pay_id", referencedColumnName = "a_p_pay_id")
    @ManyToOne
    private APayment aPPayId;

    public AInvoice() {
    }

    public AInvoice(Integer aIId) {
        this.aIId = aIId;
    }

    public AInvoice(Integer aIId, String aINo) {
        this.aIId = aIId;
        this.aINo = aINo;
    }

    public Integer getAIId() {
        return aIId;
    }

    public void setAIId(Integer aIId) {
        this.aIId = aIId;
    }

    public String getAINo() {
        return aINo;
    }

    public void setAINo(String aINo) {
        this.aINo = aINo;
    }

    public String getAIRefNo() {
        return aIRefNo;
    }

    public void setAIRefNo(String aIRefNo) {
        this.aIRefNo = aIRefNo;
    }

    public Date getAIDt() {
        return aIDt;
    }

    public void setAIDt(Date aIDt) {
        this.aIDt = aIDt;
    }

    public Date getAIProcDt() {
        return aIProcDt;
    }

    public void setAIProcDt(Date aIProcDt) {
        this.aIProcDt = aIProcDt;
    }

    public BigDecimal getAINetamount() {
        return aINetamount;
    }

    public void setAINetamount(BigDecimal aINetamount) {
        this.aINetamount = aINetamount;
    }

    public BigDecimal getAIMisc() {
        return aIMisc;
    }

    public void setAIMisc(BigDecimal aIMisc) {
        this.aIMisc = aIMisc;
    }

    public BigDecimal getAITotal() {
        return aITotal;
    }

    public void setAITotal(BigDecimal aITotal) {
        this.aITotal = aITotal;
    }

    public String getAIPayType() {
        return aIPayType;
    }

    public void setAIPayType(String aIPayType) {
        this.aIPayType = aIPayType;
    }

    public String getAIPayNote() {
        return aIPayNote;
    }

    public void setAIPayNote(String aIPayNote) {
        this.aIPayNote = aIPayNote;
    }

    public BigDecimal getRefAmt() {
        return refAmt;
    }

    public void setRefAmt(BigDecimal refAmt) {
        this.refAmt = refAmt;
    }

    public Date getRefNoteDt() {
        return refNoteDt;
    }

    public void setRefNoteDt(Date refNoteDt) {
        this.refNoteDt = refNoteDt;
    }

    public String getRefStatus() {
        return refStatus;
    }

    public void setRefStatus(String refStatus) {
        this.refStatus = refStatus;
    }

    public String getAPStatus() {
        return aPStatus;
    }

    public void setAPStatus(String aPStatus) {
        this.aPStatus = aPStatus;
    }

    public ABudget getAIMiscBudget() {
        return aIMiscBudget;
    }

    public void setAIMiscBudget(ABudget aIMiscBudget) {
        this.aIMiscBudget = aIMiscBudget;
    }

    public AOrdermaster getAIPoNo() {
        return aIPoNo;
    }

    public void setAIPoNo(AOrdermaster aIPoNo) {
        this.aIPoNo = aIPoNo;
    }

    public APayment getAPPayId() {
        return aPPayId;
    }

    public void setAPPayId(APayment aPPayId) {
        this.aPPayId = aPPayId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aIId != null ? aIId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AInvoice)) {
            return false;
        }
        AInvoice other = (AInvoice) object;
        if ((this.aIId == null && other.aIId != null) || (this.aIId != null && !this.aIId.equals(other.aIId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.acquisition.suggestions.AInvoice[ aIId=" + aIId + " ]";
    }
    
}
