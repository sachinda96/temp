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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "a_budget_transaction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ABudgetTransaction.findAll", query = "SELECT a FROM ABudgetTransaction a"),
    @NamedQuery(name = "ABudgetTransaction.findByABTransid", query = "SELECT a FROM ABudgetTransaction a WHERE a.aBTransid = :aBTransid"),
    @NamedQuery(name = "ABudgetTransaction.findByABFyear1", query = "SELECT a FROM ABudgetTransaction a WHERE a.aBFyear1 = :aBFyear1"),
    @NamedQuery(name = "ABudgetTransaction.findByABFyear2", query = "SELECT a FROM ABudgetTransaction a WHERE a.aBFyear2 = :aBFyear2"),
    @NamedQuery(name = "ABudgetTransaction.findByABFyear3", query = "SELECT a FROM ABudgetTransaction a WHERE a.aBFyear3 = :aBFyear3"),
    @NamedQuery(name = "ABudgetTransaction.findByABFyear4", query = "SELECT a FROM ABudgetTransaction a WHERE a.aBFyear4 = :aBFyear4"),
    @NamedQuery(name = "ABudgetTransaction.findByABTransamt", query = "SELECT a FROM ABudgetTransaction a WHERE a.aBTransamt = :aBTransamt"),
    @NamedQuery(name = "ABudgetTransaction.findByABRemarks", query = "SELECT a FROM ABudgetTransaction a WHERE a.aBRemarks = :aBRemarks"),
    @NamedQuery(name = "ABudgetTransaction.findByABTransdate", query = "SELECT a FROM ABudgetTransaction a WHERE a.aBTransdate = :aBTransdate"),
    
    @NamedQuery(name = "ABudgetTransaction.findByBudgetCodesYear1234", query = "SELECT a FROM ABudgetTransaction a WHERE a.aBSourcecode.budgetCode = ?1 AND a.aBDestcode.budgetCode = ?2 AND a.aBFyear1 = ?3 AND a.aBFyear2 = ?4 AND a.aBFyear3 = ?5")})
public class ABudgetTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "a_b_transid")
    private Integer aBTransid;
    @Size(max = 20)
    @Column(name = "a_b_fyear1")
    private String aBFyear1;
    @Size(max = 20)
    @Column(name = "a_b_fyear2")
    private String aBFyear2;
    @Size(max = 20)
    @Column(name = "a_b_fyear3")
    private String aBFyear3;
    @Size(max = 20)
    @Column(name = "a_b_fyear4")
    private String aBFyear4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "a_b_transamt")
    private BigDecimal aBTransamt;
    @Size(max = 510)
    @Column(name = "a_b_remarks")
    private String aBRemarks;
    @Column(name = "a_b_transdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date aBTransdate;
    @JoinColumn(name = "a_b_sourcecode", referencedColumnName = "budget_code")
    @ManyToOne
    private BHeads aBSourcecode;
    @JoinColumn(name = "a_b_destcode", referencedColumnName = "budget_code")
    @ManyToOne
    private BHeads aBDestcode;

    public ABudgetTransaction() {
    }

    public ABudgetTransaction(Integer aBTransid) {
        this.aBTransid = aBTransid;
    }

    public Integer getABTransid() {
        return aBTransid;
    }

    public void setABTransid(Integer aBTransid) {
        this.aBTransid = aBTransid;
    }

    public String getABFyear1() {
        return aBFyear1;
    }

    public void setABFyear1(String aBFyear1) {
        this.aBFyear1 = aBFyear1;
    }

    public String getABFyear2() {
        return aBFyear2;
    }

    public void setABFyear2(String aBFyear2) {
        this.aBFyear2 = aBFyear2;
    }

    public String getABFyear3() {
        return aBFyear3;
    }

    public void setABFyear3(String aBFyear3) {
        this.aBFyear3 = aBFyear3;
    }

    public String getABFyear4() {
        return aBFyear4;
    }

    public void setABFyear4(String aBFyear4) {
        this.aBFyear4 = aBFyear4;
    }

    public BigDecimal getABTransamt() {
        return aBTransamt;
    }

    public void setABTransamt(BigDecimal aBTransamt) {
        this.aBTransamt = aBTransamt;
    }

    public String getABRemarks() {
        return aBRemarks;
    }

    public void setABRemarks(String aBRemarks) {
        this.aBRemarks = aBRemarks;
    }

    public Date getABTransdate() {
        return aBTransdate;
    }

    public void setABTransdate(Date aBTransdate) {
        this.aBTransdate = aBTransdate;
    }

    public BHeads getABSourcecode() {
        return aBSourcecode;
    }

    public void setABSourcecode(BHeads aBSourcecode) {
        this.aBSourcecode = aBSourcecode;
    }

    public BHeads getABDestcode() {
        return aBDestcode;
    }

    public void setABDestcode(BHeads aBDestcode) {
        this.aBDestcode = aBDestcode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aBTransid != null ? aBTransid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ABudgetTransaction)) {
            return false;
        }
        ABudgetTransaction other = (ABudgetTransaction) object;
        if ((this.aBTransid == null && other.aBTransid != null) || (this.aBTransid != null && !this.aBTransid.equals(other.aBTransid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.ABudgetTransaction[ aBTransid=" + aBTransid + " ]";
    }
    
}
