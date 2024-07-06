/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.acquisition.suggestions;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "a_order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AOrder.findAll", query = "SELECT a FROM AOrder a"),
    @NamedQuery(name = "AOrder.findByARNo", query = "SELECT a FROM AOrder a WHERE a.aOrderPK.aRNo = :aRNo"),
    @NamedQuery(name = "AOrder.findByAOPoNo", query = "SELECT a FROM AOrder a WHERE a.aOrderPK.aOPoNo = :aOPoNo"),
    @NamedQuery(name = "AOrder.findByAOPrice", query = "SELECT a FROM AOrder a WHERE a.aOPrice = :aOPrice"),
    @NamedQuery(name = "AOrder.findByAOOrderedCopy", query = "SELECT a FROM AOrder a WHERE a.aOOrderedCopy = :aOOrderedCopy"),
    @NamedQuery(name = "AOrder.findByAOTotalreceivedCopy", query = "SELECT a FROM AOrder a WHERE a.aOTotalreceivedCopy = :aOTotalreceivedCopy"),
    @NamedQuery(name = "AOrder.findByAOPendingCopy", query = "SELECT a FROM AOrder a WHERE a.aOPendingCopy = :aOPendingCopy"),
    @NamedQuery(name = "AOrder.findByAOPaidCopies", query = "SELECT a FROM AOrder a WHERE a.aOPaidCopies = :aOPaidCopies"),
    @NamedQuery(name = "AOrder.findByARStatus", query = "SELECT a FROM AOrder a WHERE a.aRStatus = :aRStatus"),
    @NamedQuery(name = "AOrder.findByAIConvrate", query = "SELECT a FROM AOrder a WHERE a.aIConvrate = :aIConvrate"),
    
    @NamedQuery(name = "AOrder.findByAOStatussANDARStatuss", query = "SELECT a FROM AOrder a WHERE (a.aOrdermaster.aOStatus = ?1 OR a.aOrdermaster.aOStatus = ?2)  AND (a.aRStatus = ?3 OR a.aRStatus = ?4 OR a.aRStatus = ?5)"),
    @NamedQuery(name = "AOrder.findByForInvoice", query = "SELECT a FROM AOrder a WHERE (a.aOrdermaster.aOStatus = ?1 OR a.aOrdermaster.aOStatus = ?2)  AND (a.aRStatus = ?3 OR a.aRStatus = ?4 OR a.aRStatus = ?5) AND ((a.aOrdermaster.aOApayment IN ('L1', 'S1')) OR a.aOrdermaster.aOApayment IS NULL)"),
    @NamedQuery(name = "AOrder.findOrderNoBySupplierForReceive", query = "SELECT a FROM AOrder a WHERE a.aRStatus = 'D' AND  a.aOrdermaster.aOSupCd.aSName = :aSName"),
    @NamedQuery(name = "AOrder.findOrderNoBySupplierForInvoiceRegular", query = "SELECT a FROM AOrder a WHERE a.aRStatus = 'I' AND a.aOrdermaster.aOStatus = 'X' AND  a.aOrdermaster.aOSupCd.aSCd = :aSCd  AND ((a.aOrdermaster.aOApayment IN ('L1', 'S1')) OR a.aOrdermaster.aOApayment IS NULL)" ),
    @NamedQuery(name = "AOrder.findOrderNoBySupplierForInvoiceAdvance", query = "SELECT a FROM AOrder a WHERE (a.aRStatus = 'D' OR a.aRStatus = 'J') AND a.aOrdermaster.aOStatus = 'D' AND  a.aOrdermaster.aOSupCd.aSCd = :aSCd  AND ((a.aOrdermaster.aOApayment IN ('L1', 'S1')) OR a.aOrdermaster.aOApayment IS NULL)"),
    @NamedQuery(name = "AOrder.findOrderNoForInvoiceRegular", query = "SELECT a FROM AOrder a WHERE a.aRStatus = 'I' AND a.aOrdermaster.aOStatus = 'X'   AND ((a.aOrdermaster.aOApayment IN ('L1', 'S1')) OR a.aOrdermaster.aOApayment IS NULL)" ),
    @NamedQuery(name = "AOrder.findOrderNoForInvoiceAdvance", query = "SELECT a FROM AOrder a WHERE (a.aRStatus = 'D' OR a.aRStatus = 'J') AND a.aOrdermaster.aOStatus = 'D'  AND ((a.aOrdermaster.aOApayment IN ('L1', 'S1')) OR a.aOrdermaster.aOApayment IS NULL)"),
    @NamedQuery(name = "AOrder.findByAOPoNoAndStatusOrdered", query = "SELECT a FROM AOrder a WHERE a.aOrderPK.aOPoNo = :aOPoNo AND a.aOrdermaster.aOStatus = 'D' "),
    @NamedQuery(name = "AOrder.findByAOStatusStatusOrdered", query = "SELECT a FROM AOrder a WHERE  a.aOrdermaster.aOStatus = 'D' ")

})
public class AOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AOrderPK aOrderPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "a_o_price")
    private BigDecimal aOPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "a_o_ordered_copy")
    private int aOOrderedCopy;
    @Column(name = "a_o_totalreceived_copy")
    private Integer aOTotalreceivedCopy;
    @Column(name = "a_o_pending_copy")
    private Integer aOPendingCopy;
    @Column(name = "a_o_paid_copies")
    private Integer aOPaidCopies;
    @Size(max = 100)
    @Column(name = "a_r_status")
    private String aRStatus;
    @Column(name = "a_i_convrate")
    private Long aIConvrate;
    @JoinColumn(name = "a_o_po_no", referencedColumnName = "a_o_po_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AOrdermaster aOrdermaster;
    @JoinColumn(name = "a_r_no", referencedColumnName = "a_r_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ARequest aRequest;

    public AOrder() {
    }

    public AOrder(AOrderPK aOrderPK) {
        this.aOrderPK = aOrderPK;
    }

    public AOrder(AOrderPK aOrderPK, BigDecimal aOPrice, int aOOrderedCopy) {
        this.aOrderPK = aOrderPK;
        this.aOPrice = aOPrice;
        this.aOOrderedCopy = aOOrderedCopy;
    }

    public AOrder(int aRNo, int aOPoNo) {
        this.aOrderPK = new AOrderPK(aRNo, aOPoNo);
    }

    public AOrderPK getAOrderPK() {
        return aOrderPK;
    }

    public void setAOrderPK(AOrderPK aOrderPK) {
        this.aOrderPK = aOrderPK;
    }

    public BigDecimal getAOPrice() {
        return aOPrice;
    }

    public void setAOPrice(BigDecimal aOPrice) {
        this.aOPrice = aOPrice;
    }

    public int getAOOrderedCopy() {
        return aOOrderedCopy;
    }

    public void setAOOrderedCopy(int aOOrderedCopy) {
        this.aOOrderedCopy = aOOrderedCopy;
    }

    public Integer getAOTotalreceivedCopy() {
        return aOTotalreceivedCopy;
    }

    public void setAOTotalreceivedCopy(Integer aOTotalreceivedCopy) {
        this.aOTotalreceivedCopy = aOTotalreceivedCopy;
    }

    public Integer getAOPendingCopy() {
        return aOPendingCopy;
    }

    public void setAOPendingCopy(Integer aOPendingCopy) {
        this.aOPendingCopy = aOPendingCopy;
    }

    public Integer getAOPaidCopies() {
        return aOPaidCopies;
    }

    public void setAOPaidCopies(Integer aOPaidCopies) {
        this.aOPaidCopies = aOPaidCopies;
    }

    public String getARStatus() {
        return aRStatus;
    }

    public void setARStatus(String aRStatus) {
        this.aRStatus = aRStatus;
    }

    public Long getAIConvrate() {
        return aIConvrate;
    }

    public void setAIConvrate(Long aIConvrate) {
        this.aIConvrate = aIConvrate;
    }

    public AOrdermaster getAOrdermaster() {
        return aOrdermaster;
    }

    public void setAOrdermaster(AOrdermaster aOrdermaster) {
        this.aOrdermaster = aOrdermaster;
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
        hash += (aOrderPK != null ? aOrderPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AOrder)) {
            return false;
        }
        AOrder other = (AOrder) object;
        if ((this.aOrderPK == null && other.aOrderPK != null) || (this.aOrderPK != null && !this.aOrderPK.equals(other.aOrderPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.acquisition.suggestions.AOrder[ aOrderPK=" + aOrderPK + " ]";
    }
    
}
