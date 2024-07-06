/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "b_heads")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BHeads.findAll", query = "SELECT b FROM BHeads b"),
    @NamedQuery(name = "BHeads.findByBudgetCode", query = "SELECT b FROM BHeads b WHERE b.budgetCode = :budgetCode"),
    @NamedQuery(name = "BHeads.findByShortDesc", query = "SELECT b FROM BHeads b WHERE b.shortDesc = :shortDesc"),
    @NamedQuery(name = "BHeads.findByCategory", query = "SELECT b FROM BHeads b WHERE b.category = :category"),
    @NamedQuery(name = "BHeads.findByBudgetExpDate", query = "SELECT b FROM BHeads b WHERE b.budgetExpDate = :budgetExpDate")})
public class BHeads implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budgetHead")
    private Collection<ABudget> aBudgetCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "budget_code")
    private String budgetCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 510)
    @Column(name = "short_desc")
    private String shortDesc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "category")
    private String category;
    @Column(name = "budget_exp_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date budgetExpDate;
    @OneToMany(mappedBy = "aBSourcecode")
    private Collection<ABudgetTransaction> aBudgetTransactionCollection;
    @OneToMany(mappedBy = "aBDestcode")
    private Collection<ABudgetTransaction> aBudgetTransactionCollection1;

    public BHeads() {
    }

    public BHeads(String budgetCode) {
        this.budgetCode = budgetCode;
    }

    public BHeads(String budgetCode, String shortDesc, String category) {
        this.budgetCode = budgetCode;
        this.shortDesc = shortDesc;
        this.category = category;
    }

    public String getBudgetCode() {
        return budgetCode;
    }

    public void setBudgetCode(String budgetCode) {
        this.budgetCode = budgetCode;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getBudgetExpDate() {
        return budgetExpDate;
    }

    public void setBudgetExpDate(Date budgetExpDate) {
        this.budgetExpDate = budgetExpDate;
    }

    @XmlTransient
    public Collection<ABudgetTransaction> getABudgetTransactionCollection() {
        return aBudgetTransactionCollection;
    }

    public void setABudgetTransactionCollection(Collection<ABudgetTransaction> aBudgetTransactionCollection) {
        this.aBudgetTransactionCollection = aBudgetTransactionCollection;
    }

    @XmlTransient
    public Collection<ABudgetTransaction> getABudgetTransactionCollection1() {
        return aBudgetTransactionCollection1;
    }

    public void setABudgetTransactionCollection1(Collection<ABudgetTransaction> aBudgetTransactionCollection1) {
        this.aBudgetTransactionCollection1 = aBudgetTransactionCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (budgetCode != null ? budgetCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BHeads)) {
            return false;
        }
        BHeads other = (BHeads) object;
        if ((this.budgetCode == null && other.budgetCode != null) || (this.budgetCode != null && !this.budgetCode.equals(other.budgetCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.BHeads[ budgetCode=" + budgetCode + " ]";
    }

    @XmlTransient
    public Collection<ABudget> getABudgetCollection() {
        return aBudgetCollection;
    }

    public void setABudgetCollection(Collection<ABudget> aBudgetCollection) {
        this.aBudgetCollection = aBudgetCollection;
    }
    
}
