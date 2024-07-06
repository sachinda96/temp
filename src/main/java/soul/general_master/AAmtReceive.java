/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "a_amt_receive")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AAmtReceive.findAll", query = "SELECT a FROM AAmtReceive a"),
    @NamedQuery(name = "AAmtReceive.findByAAmtId", query = "SELECT a FROM AAmtReceive a WHERE a.aAmtId = :aAmtId"),
    @NamedQuery(name = "AAmtReceive.findByAmt", query = "SELECT a FROM AAmtReceive a WHERE a.amt = :amt"),
    @NamedQuery(name = "AAmtReceive.findByRecAmtDate", query = "SELECT a FROM AAmtReceive a WHERE a.recAmtDate = :recAmtDate"),
    
    @NamedQuery(name = "AAmtReceive.findIncomeHeads", query = "SELECT a FROM AAmtReceive a WHERE a.aGroupId IS NULL"),
    @NamedQuery(name = "AAmtReceive.findExpenseHeads", query = "SELECT a FROM AAmtReceive a WHERE a.aGroupId IS NOT NULL")
})
public class AAmtReceive implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "a_amt_id")
    private Integer aAmtId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "amt")
    private BigDecimal amt;
    @Column(name = "rec_amt_date")
    @Temporal(TemporalType.DATE)
    private Date recAmtDate;
    @JoinColumn(name = "a_group_id", referencedColumnName = "a_b_budget_code")
    @ManyToOne
    private ABudget aGroupId;
    @JoinColumn(name = "a_b_budget_code", referencedColumnName = "a_b_budget_code")
    @ManyToOne(optional = false)
    private ABudget aBBudgetCode;

    public AAmtReceive() {
    }

    public AAmtReceive(Integer aAmtId) {
        this.aAmtId = aAmtId;
    }

    public AAmtReceive(Integer aAmtId, BigDecimal amt) {
        this.aAmtId = aAmtId;
        this.amt = amt;
    }

    public Integer getAAmtId() {
        return aAmtId;
    }

    public void setAAmtId(Integer aAmtId) {
        this.aAmtId = aAmtId;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public Date getRecAmtDate() {
        return recAmtDate;
    }

    public void setRecAmtDate(Date recAmtDate) {
        this.recAmtDate = recAmtDate;
    }

    public ABudget getAGroupId() {
        return aGroupId;
    }

    public void setAGroupId(ABudget aGroupId) {
        this.aGroupId = aGroupId;
    }

    public ABudget getABBudgetCode() {
        return aBBudgetCode;
    }

    public void setABBudgetCode(ABudget aBBudgetCode) {
        this.aBBudgetCode = aBBudgetCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aAmtId != null ? aAmtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AAmtReceive)) {
            return false;
        }
        AAmtReceive other = (AAmtReceive) object;
        if ((this.aAmtId == null && other.aAmtId != null) || (this.aAmtId != null && !this.aAmtId.equals(other.aAmtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.AAmtReceive[ aAmtId=" + aAmtId + " ]";
    }
    
}
