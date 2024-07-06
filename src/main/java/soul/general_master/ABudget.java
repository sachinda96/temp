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
import soul.acquisition.suggestions.AInvoice;
import soul.acquisition.suggestions.ARequest;
import soul.serialControl.SRequest;


/**
 *
 * @author soullab
 */
@Entity
@Table(name = "a_budget")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ABudget.findAll", query = "SELECT a FROM ABudget a"),
    @NamedQuery(name = "ABudget.findByABBudgetCode", query = "SELECT a FROM ABudget a WHERE a.aBBudgetCode = :aBBudgetCode"),
    @NamedQuery(name = "ABudget.findByABYear1", query = "SELECT a FROM ABudget a WHERE a.aBYear1 = :aBYear1"),
    @NamedQuery(name = "ABudget.findByABYear2", query = "SELECT a FROM ABudget a WHERE a.aBYear2 = :aBYear2"),
    @NamedQuery(name = "ABudget.findByBudgetCode", query = "SELECT a FROM ABudget a WHERE a.budgetHead.budgetCode = :budgetCode"),
    @NamedQuery(name = "ABudget.findByABOpBal", query = "SELECT a FROM ABudget a WHERE a.aBOpBal = :aBOpBal"),
    @NamedQuery(name = "ABudget.findByABAmt", query = "SELECT a FROM ABudget a WHERE a.aBAmt = :aBAmt"),
    @NamedQuery(name = "ABudget.findByABPoraisedAmt", query = "SELECT a FROM ABudget a WHERE a.aBPoraisedAmt = :aBPoraisedAmt"),
    @NamedQuery(name = "ABudget.findByABActFlag", query = "SELECT a FROM ABudget a WHERE a.aBActFlag = :aBActFlag"),
    @NamedQuery(name = "ABudget.findByABExpAmt", query = "SELECT a FROM ABudget a WHERE a.aBExpAmt = :aBExpAmt"),
    @NamedQuery(name = "ABudget.findByABEffectiveDate", query = "SELECT a FROM ABudget a WHERE a.aBEffectiveDate = :aBEffectiveDate"),
    @NamedQuery(name = "ABudget.findByABRemarks", query = "SELECT a FROM ABudget a WHERE a.aBRemarks = :aBRemarks"),
   
    @NamedQuery(name = "ABudget.findByCategory", query = "SELECT a FROM ABudget a WHERE a.budgetHead.category = :category"),
    @NamedQuery(name = "ABudget.findByBudgetYear1Year2", query = "SELECT a FROM ABudget a WHERE a.aBYear1 = ?1 AND a.aBYear2 = ?2"),
    @NamedQuery(name = "ABudget.findByBudgetCategoryYear1Year2", query = "SELECT a FROM ABudget a WHERE a.budgetHead.category = ?1 AND a.aBYear1 = ?2 AND a.aBYear2 = ?3"),
    @NamedQuery(name = "ABudget.findByBudgetCodeYear1Year2", query = "SELECT a FROM ABudget a WHERE a.budgetHead.budgetCode = ?1 AND a.aBYear1 = ?2 AND a.aBYear2 = ?3"),
    @NamedQuery(name = "ABudget.findAllBudgetHeadsForTitleEntry", query = "SELECT a FROM ABudget a WHERE a.budgetHead.budgetCode = :budgetHead and a.aGroupId!='null' "),
})
public class ABudget implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aIMiscBudget")
    private Collection<AInvoice> aInvoiceCollection;
    @OneToMany(mappedBy = "sRBudgetCode")
    private Collection<SRequest> sRequestCollection;
    @OneToMany(mappedBy = "sRBudgetCd")
    private Collection<SRequest> sRequestCollection1;
    @OneToMany(mappedBy = "aRBudgetCd")
    private Collection<ARequest> aRequestCollection;
    @OneToMany(mappedBy = "aRBudgetCode")
    private Collection<ARequest> aRequestCollection1;
    @OneToMany(mappedBy = "aRBudgetCd")
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "a_b_budget_code")
    private Integer aBBudgetCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "a_b_year1")
    private String aBYear1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "a_b_year2")
    private String aBYear2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "a_b_op_bal")
    private BigDecimal aBOpBal;
    @Column(name = "a_b_amt")
    private BigDecimal aBAmt;
    @Column(name = "a_b_poraised_amt")
    private BigDecimal aBPoraisedAmt;
    @Size(max = 100)
    @Column(name = "a_b_act_flag")
    private String aBActFlag;
    @Column(name = "a_b_exp_amt")
    private BigDecimal aBExpAmt;
    @Column(name = "a_b_effective_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date aBEffectiveDate;
    @Size(max = 500)
    @Column(name = "a_b_remarks")
    private String aBRemarks;
    @JoinColumn(name = "budget_head", referencedColumnName = "budget_code")
    @ManyToOne(optional = false)
    private BHeads budgetHead;
    @OneToMany(mappedBy = "aGroupId")
    private Collection<ABudget> aBudgetCollection;
    @JoinColumn(name = "a_group_id", referencedColumnName = "a_b_budget_code")
    @ManyToOne
    private ABudget aGroupId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aBBudgetCode")
    private Collection<AAmtReceive> aAmtReceiveCollection;
    @OneToMany(mappedBy = "aGroupId")
    private Collection<AAmtReceive> aAmtReceiveCollection1;

    public ABudget() {
    }

    public ABudget(Integer aBBudgetCode) {
        this.aBBudgetCode = aBBudgetCode;
    }

    public ABudget(Integer aBBudgetCode, String aBYear1, String aBYear2) {
        this.aBBudgetCode = aBBudgetCode;
        this.aBYear1 = aBYear1;
        this.aBYear2 = aBYear2;
    }

    public Integer getABBudgetCode() {
        return aBBudgetCode;
    }

    public void setABBudgetCode(Integer aBBudgetCode) {
        this.aBBudgetCode = aBBudgetCode;
    }

    public String getABYear1() {
        return aBYear1;
    }

    public void setABYear1(String aBYear1) {
        this.aBYear1 = aBYear1;
    }

    public String getABYear2() {
        return aBYear2;
    }

    public void setABYear2(String aBYear2) {
        this.aBYear2 = aBYear2;
    }

    public BigDecimal getABOpBal() {
        return aBOpBal;
    }

    public void setABOpBal(BigDecimal aBOpBal) {
        this.aBOpBal = aBOpBal;
    }

    public BigDecimal getABAmt() {
        return aBAmt;
    }

    public void setABAmt(BigDecimal aBAmt) {
        this.aBAmt = aBAmt;
    }

    public BigDecimal getABPoraisedAmt() {
        return aBPoraisedAmt;
    }

    public void setABPoraisedAmt(BigDecimal aBPoraisedAmt) {
        this.aBPoraisedAmt = aBPoraisedAmt;
    }

    public String getABActFlag() {
        return aBActFlag;
    }

    public void setABActFlag(String aBActFlag) {
        this.aBActFlag = aBActFlag;
    }

    public BigDecimal getABExpAmt() {
        return aBExpAmt;
    }

    public void setABExpAmt(BigDecimal aBExpAmt) {
        this.aBExpAmt = aBExpAmt;
    }

    public Date getABEffectiveDate() {
        return aBEffectiveDate;
    }

    public void setABEffectiveDate(Date aBEffectiveDate) {
        this.aBEffectiveDate = aBEffectiveDate;
    }

    public String getABRemarks() {
        return aBRemarks;
    }

    public void setABRemarks(String aBRemarks) {
        this.aBRemarks = aBRemarks;
    }

    public BHeads getBudgetHead() {
        return budgetHead;
    }

    public void setBudgetHead(BHeads budgetHead) {
        this.budgetHead = budgetHead;
    }

    @XmlTransient
    public Collection<ABudget> getABudgetCollection() {
        return aBudgetCollection;
    }

    public void setABudgetCollection(Collection<ABudget> aBudgetCollection) {
        this.aBudgetCollection = aBudgetCollection;
    }

    public ABudget getAGroupId() {
        return aGroupId;
    }

    public void setAGroupId(ABudget aGroupId) {
        this.aGroupId = aGroupId;
    }

    @XmlTransient
    public Collection<AAmtReceive> getAAmtReceiveCollection() {
        return aAmtReceiveCollection;
    }

    public void setAAmtReceiveCollection(Collection<AAmtReceive> aAmtReceiveCollection) {
        this.aAmtReceiveCollection = aAmtReceiveCollection;
    }

    @XmlTransient
    public Collection<AAmtReceive> getAAmtReceiveCollection1() {
        return aAmtReceiveCollection1;
    }

    public void setAAmtReceiveCollection1(Collection<AAmtReceive> aAmtReceiveCollection1) {
        this.aAmtReceiveCollection1 = aAmtReceiveCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aBBudgetCode != null ? aBBudgetCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ABudget)) {
            return false;
        }
        ABudget other = (ABudget) object;
        if ((this.aBBudgetCode == null && other.aBBudgetCode != null) || (this.aBBudgetCode != null && !this.aBBudgetCode.equals(other.aBBudgetCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.ABudget[ aBBudgetCode=" + aBBudgetCode + " ]";
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
    public Collection<SRequest> getSRequestCollection() {
        return sRequestCollection;
    }

    public void setSRequestCollection(Collection<SRequest> sRequestCollection) {
        this.sRequestCollection = sRequestCollection;
    }

    @XmlTransient
    public Collection<SRequest> getSRequestCollection1() {
        return sRequestCollection1;
    }

    public void setSRequestCollection1(Collection<SRequest> sRequestCollection1) {
        this.sRequestCollection1 = sRequestCollection1;
    }

    @XmlTransient
    public Collection<AInvoice> getAInvoiceCollection() {
        return aInvoiceCollection;
    }

    public void setAInvoiceCollection(Collection<AInvoice> aInvoiceCollection) {
        this.aInvoiceCollection = aInvoiceCollection;
    }
}

   