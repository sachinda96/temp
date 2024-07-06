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
@Table(name = "a_accession")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AAccession.findAll", query = "SELECT a FROM AAccession a"),
    @NamedQuery(name = "AAccession.findByAAccId", query = "SELECT a FROM AAccession a WHERE a.aAccId = :aAccId"),
    @NamedQuery(name = "AAccession.findByAAAccno", query = "SELECT a FROM AAccession a WHERE a.aAAccno = :aAAccno"),
    @NamedQuery(name = "AAccession.findByAAPayment", query = "SELECT a FROM AAccession a WHERE a.aAPayment = :aAPayment"),
    @NamedQuery(name = "AAccession.findByAAIncircul", query = "SELECT a FROM AAccession a WHERE a.aAIncircul = :aAIncircul"),
    @NamedQuery(name = "AAccession.findByAADate", query = "SELECT a FROM AAccession a WHERE a.aADate = :aADate"),
    @NamedQuery(name = "AAccession.findByAIInvoiceNo", query = "SELECT a FROM AAccession a WHERE a.aIInvoiceNo = :aIInvoiceNo"),
    @NamedQuery(name = "AAccession.findByAIDiscount", query = "SELECT a FROM AAccession a WHERE a.aIDiscount = :aIDiscount"),
    @NamedQuery(name = "AAccession.findByARecDt", query = "SELECT a FROM AAccession a WHERE a.aRecDt = :aRecDt"),
    @NamedQuery(name = "AAccession.findByACatRecid", query = "SELECT a FROM AAccession a WHERE a.aCatRecid = :aCatRecid"),
    
    @NamedQuery(name = "AAccession.findByAccnoNULL", query = "SELECT a FROM AAccession a WHERE a.aAAccno IS NULL"),
    @NamedQuery(name = "AAccession.findByAccnoNULLForRegular", query = "SELECT a FROM AAccession a WHERE a.aAAccno IS NULL AND a.aRequest.aRStatus = 'I'"),
    @NamedQuery(name = "AAccession.findByAccnoNULLForGratis", query = "SELECT a FROM AAccession a WHERE a.aAAccno IS NULL AND a.aRequest.aRStatus = 'G'"),
    @NamedQuery(name = "AAccession.findByAccnoNullANDTitle", query = "SELECT a FROM AAccession a WHERE a.aAAccno IS NULL AND a.aRequest.aRTitle = ?1 AND a.aRequest.aRStatus = ?2"),
    @NamedQuery(name = "AAccession.findByAccnoNullANDSupplier", query = "SELECT a FROM AAccession a WHERE a.aAAccno IS NULL AND a.aRequest.aRPoNo.aOSupCd.aSCd = ?1 AND a.aRequest.aRStatus = ?2"),
    @NamedQuery(name = "AAccession.findByAccnoNullANDReceiveDate", query = "SELECT a FROM AAccession a WHERE a.aAAccno IS NULL AND a.aRequest.aRPoDt BETWEEN ?1 AND ?2  AND a.aRequest.aRStatus = ?3"),
    @NamedQuery(name = "AAccession.findByAccnoNullANDOrderNo", query = "SELECT a FROM AAccession a WHERE a.aAAccno IS NULL AND a.aRequest.aRPoNo.aOPoNo = ?1 AND a.aRequest.aRStatus = ?2"),
    @NamedQuery(name = "AAccession.findByOrderNo", query = "SELECT a FROM AAccession a WHERE  a.aAAccno IS NOT  NULL AND a.aRequest.aRPoNo.aOPoNo = :aOPoNo"),
    @NamedQuery(name = "AAccession.findByBudgetCd", query = "SELECT a FROM AAccession a WHERE  a.aAAccno IS NOT  NULL AND a.aRequest.aRBudgetCd.aBBudgetCode = :aBBudgetCode"),
    @NamedQuery(name = "AAccession.findByAccessionPrefix", query = "SELECT a FROM AAccession a WHERE  a.aAAccno IS NOT  NULL AND a.aAAccno LIKE :aAAccno"),
    @NamedQuery(name = "AAccession.findByDateBetween", query = "SELECT a FROM AAccession a WHERE  a.aAAccno IS NOT  NULL AND a.aRequest.aRDate BETWEEN ?1 AND ?2"),
    @NamedQuery(name = "AAccession.findByMaterialCd", query = "SELECT a FROM AAccession a WHERE  a.aAAccno IS NOT  NULL AND a.aRequest.aRPhycd.code = :aRPhycd"),
    @NamedQuery(name = "AAccession.findByAccessionRange", query = "SELECT a FROM AAccession a WHERE  a.aAAccno IS NOT  NULL AND a.aAAccno BETWEEN ?1 AND ?2")
})
public class AAccession implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "a_acc_id")
    private Integer aAccId;
    @Size(max = 100)
    @Column(name = "a_a_accno")
    private String aAAccno;
    @Size(max = 100)
    @Column(name = "a_a_payment")
    private String aAPayment;
    @Size(max = 100)
    @Column(name = "a_a_incircul")
    private String aAIncircul;
    @Column(name = "a_a_date")
    @Temporal(TemporalType.DATE)
    private Date aADate;
    @Column(name = "a_i_invoice_no")
    private Integer aIInvoiceNo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "a_i_discount")
    private BigDecimal aIDiscount;
    @Column(name = "a_rec_dt")
    @Temporal(TemporalType.DATE)
    private Date aRecDt;
    @Size(max = 100)
    @Column(name = "a_cat_recid")
    private String aCatRecid;
    @JoinColumn(name = "a_r_no", referencedColumnName = "a_r_no")
    @ManyToOne(optional = false)
    private ARequest aRequest;

    public AAccession() {
    }

    public AAccession(Integer aAccId) {
        this.aAccId = aAccId;
    }

    public Integer getAAccId() {
        return aAccId;
    }

    public void setAAccId(Integer aAccId) {
        this.aAccId = aAccId;
    }

    public String getAAAccno() {
        return aAAccno;
    }

    public void setAAAccno(String aAAccno) {
        this.aAAccno = aAAccno;
    }

    public String getAAPayment() {
        return aAPayment;
    }

    public void setAAPayment(String aAPayment) {
        this.aAPayment = aAPayment;
    }

    public String getAAIncircul() {
        return aAIncircul;
    }

    public void setAAIncircul(String aAIncircul) {
        this.aAIncircul = aAIncircul;
    }

    public Date getAADate() {
        return aADate;
    }

    public void setAADate(Date aADate) {
        this.aADate = aADate;
    }

    public Integer getAIInvoiceNo() {
        return aIInvoiceNo;
    }
    

    public void setAIInvoiceNo(Integer aIInvoiceNo) {
        this.aIInvoiceNo = aIInvoiceNo;
    }

    public BigDecimal getAIDiscount() {
        return aIDiscount;
    }

    public void setAIDiscount(BigDecimal aIDiscount) {
        this.aIDiscount = aIDiscount;
    }

    public Date getARecDt() {
        return aRecDt;
    }

    public void setARecDt(Date aRecDt) {
        this.aRecDt = aRecDt;
    }

    public String getACatRecid() {
        return aCatRecid;
    }

    public void setACatRecid(String aCatRecid) {
        this.aCatRecid = aCatRecid;
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
        hash += (aAccId != null ? aAccId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AAccession)) {
            return false;
        }
        AAccession other = (AAccession) object;
        if ((this.aAccId == null && other.aAccId != null) || (this.aAccId != null && !this.aAccId.equals(other.aAccId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.acquisition.suggestions.AAccession[ aAccId=" + aAccId + " ]";
    }
    
}
