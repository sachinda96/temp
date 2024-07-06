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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "a_receive")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AReceive.findAll", query = "SELECT a FROM AReceive a"),
    @NamedQuery(name = "AReceive.findByAReceiveId", query = "SELECT a FROM AReceive a WHERE a.aReceiveId = :aReceiveId"),
    @NamedQuery(name = "AReceive.findByAIReceivedCopy", query = "SELECT a FROM AReceive a WHERE a.aIReceivedCopy = :aIReceivedCopy"),
    @NamedQuery(name = "AReceive.findByAIReceivedDt", query = "SELECT a FROM AReceive a WHERE a.aIReceivedDt = :aIReceivedDt"),
    @NamedQuery(name = "AReceive.findByAIReceivedPrice", query = "SELECT a FROM AReceive a WHERE a.aIReceivedPrice = :aIReceivedPrice"),
    @NamedQuery(name = "AReceive.findByAICurrencyCd", query = "SELECT a FROM AReceive a WHERE a.aICurrencyCd = :aICurrencyCd"),
    @NamedQuery(name = "AReceive.findByAIConvRate", query = "SELECT a FROM AReceive a WHERE a.aIConvRate = :aIConvRate"),
    @NamedQuery(name = "AReceive.findByAIDiscount", query = "SELECT a FROM AReceive a WHERE a.aIDiscount = :aIDiscount"),
    @NamedQuery(name = "AReceive.findByAIInvoiceId", query = "SELECT a FROM AReceive a WHERE a.aIInvoiceId = :aIInvoiceId"),
    @NamedQuery(name = "AReceive.findByAIInvoiceDt", query = "SELECT a FROM AReceive a WHERE a.aIInvoiceDt = :aIInvoiceDt"),
    @NamedQuery(name = "AReceive.findByAIPaymentNote", query = "SELECT a FROM AReceive a WHERE a.aIPaymentNote = :aIPaymentNote"),
    @NamedQuery(name = "AReceive.findByAIMischrg", query = "SELECT a FROM AReceive a WHERE a.aIMischrg = :aIMischrg"),
    @NamedQuery(name = "AReceive.findByAIInvprocdt", query = "SELECT a FROM AReceive a WHERE a.aIInvprocdt = :aIInvprocdt")})
public class AReceive implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "a_receive_id")
    private Integer aReceiveId;
    @Column(name = "a_i_received_copy")
    private Integer aIReceivedCopy;
    @Column(name = "a_i_received_dt")
    @Temporal(TemporalType.DATE)
    private Date aIReceivedDt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "a_i_received_price")
    private BigDecimal aIReceivedPrice;
    @Size(max = 100)
    @Column(name = "a_i_currency_cd")
    private String aICurrencyCd;
    @Column(name = "a_i_conv_rate")
    private BigDecimal aIConvRate;
    @Column(name = "a_i_discount")
    private BigDecimal aIDiscount;
    @Column(name = "a_i_invoice_id")
    private Long aIInvoiceId;
    @Column(name = "a_i_invoice_dt")
    @Temporal(TemporalType.DATE)
    private Date aIInvoiceDt;
    @Size(max = 100)
    @Column(name = "a_i_payment_note")
    private String aIPaymentNote;
    @Column(name = "a_i_mischrg")
    private BigDecimal aIMischrg;
    @Column(name = "a_i_invprocdt")
    @Temporal(TemporalType.DATE)
    private Date aIInvprocdt;
    @JoinColumn(name = "a_o_po_no", referencedColumnName = "a_o_po_no")
    @ManyToOne(optional = false)
    private AOrdermaster aOPoNo;
    @JoinColumn(name = "a_r_no", referencedColumnName = "a_r_no")
    @ManyToOne(optional = false)
    private ARequest aRequest;

    public AReceive() {
    }

    public AReceive(Integer aReceiveId) {
        this.aReceiveId = aReceiveId;
    }

    public Integer getAReceiveId() {
        return aReceiveId;
    }

    public void setAReceiveId(Integer aReceiveId) {
        this.aReceiveId = aReceiveId;
    }

    public Integer getAIReceivedCopy() {
        return aIReceivedCopy;
    }

    public void setAIReceivedCopy(Integer aIReceivedCopy) {
        this.aIReceivedCopy = aIReceivedCopy;
    }

    public Date getAIReceivedDt() {
        return aIReceivedDt;
    }

    public void setAIReceivedDt(Date aIReceivedDt) {
        this.aIReceivedDt = aIReceivedDt;
    }

    public BigDecimal getAIReceivedPrice() {
        return aIReceivedPrice;
    }

    public void setAIReceivedPrice(BigDecimal aIReceivedPrice) {
        this.aIReceivedPrice = aIReceivedPrice;
    }

    public String getAICurrencyCd() {
        return aICurrencyCd;
    }

    public void setAICurrencyCd(String aICurrencyCd) {
        this.aICurrencyCd = aICurrencyCd;
    }

    public BigDecimal getAIConvRate() {
        return aIConvRate;
    }

    public void setAIConvRate(BigDecimal aIConvRate) {
        this.aIConvRate = aIConvRate;
    }

    public BigDecimal getAIDiscount() {
        return aIDiscount;
    }

    public void setAIDiscount(BigDecimal aIDiscount) {
        this.aIDiscount = aIDiscount;
    }

    public Long getAIInvoiceId() {
        return aIInvoiceId;
    }

    public void setAIInvoiceId(Long aIInvoiceId) {
        this.aIInvoiceId = aIInvoiceId;
    }

    public Date getAIInvoiceDt() {
        return aIInvoiceDt;
    }

    public void setAIInvoiceDt(Date aIInvoiceDt) {
        this.aIInvoiceDt = aIInvoiceDt;
    }

    public String getAIPaymentNote() {
        return aIPaymentNote;
    }

    public void setAIPaymentNote(String aIPaymentNote) {
        this.aIPaymentNote = aIPaymentNote;
    }

    public BigDecimal getAIMischrg() {
        return aIMischrg;
    }

    public void setAIMischrg(BigDecimal aIMischrg) {
        this.aIMischrg = aIMischrg;
    }

    public Date getAIInvprocdt() {
        return aIInvprocdt;
    }

    public void setAIInvprocdt(Date aIInvprocdt) {
        this.aIInvprocdt = aIInvprocdt;
    }

    public AOrdermaster getAOPoNo() {
        return aOPoNo;
    }

    public void setAOPoNo(AOrdermaster aOPoNo) {
        this.aOPoNo = aOPoNo;
    }

    public ARequest getARequest() {
        return aRequest;
    }

    public void setARequest(ARequest aRequest) {
        this.aRequest = aRequest;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aReceiveId != null ? aReceiveId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AReceive)) {
            return false;
        }
        AReceive other = (AReceive) object;
        if ((this.aReceiveId == null && other.aReceiveId != null) || (this.aReceiveId != null && !this.aReceiveId.equals(other.aReceiveId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.acquisition.suggestions.AReceive[ aReceiveId=" + aReceiveId + " ]";
    }
    
}
