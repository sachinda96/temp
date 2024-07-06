/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "t_bookbankissuereturn")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TBookbankissuereturn.findAll", query = "SELECT t FROM TBookbankissuereturn t"),
    @NamedQuery(name = "TBookbankissuereturn.findByMemCd", query = "SELECT t FROM TBookbankissuereturn t WHERE t.tBookbankissuereturnPK.memCd = :memCd"),
    @NamedQuery(name = "TBookbankissuereturn.findByAccNo", query = "SELECT t FROM TBookbankissuereturn t WHERE t.tBookbankissuereturnPK.accNo = :accNo"),
    @NamedQuery(name = "TBookbankissuereturn.findByIssueDt", query = "SELECT t FROM TBookbankissuereturn t WHERE t.tBookbankissuereturnPK.issueDt = :issueDt"),
    @NamedQuery(name = "TBookbankissuereturn.findByAccprice", query = "SELECT t FROM TBookbankissuereturn t WHERE t.accprice = :accprice"),
    @NamedQuery(name = "TBookbankissuereturn.findByDueDt", query = "SELECT t FROM TBookbankissuereturn t WHERE t.dueDt = :dueDt"),
    @NamedQuery(name = "TBookbankissuereturn.findByUserCd", query = "SELECT t FROM TBookbankissuereturn t WHERE t.userCd = :userCd"),
    @NamedQuery(name = "TBookbankissuereturn.findByStatus", query = "SELECT t FROM TBookbankissuereturn t WHERE t.status = :status"),
    @NamedQuery(name = "TBookbankissuereturn.findByReturnDt", query = "SELECT t FROM TBookbankissuereturn t WHERE t.returnDt = :returnDt"),
    @NamedQuery(name = "TBookbankissuereturn.findByReturnUserCd", query = "SELECT t FROM TBookbankissuereturn t WHERE t.returnUserCd = :returnUserCd"),
    
    @NamedQuery(name = "TBookbankissuereturn.findByMemCdAndIssuedandAccNo", query = "SELECT t FROM TBookbankissuereturn t WHERE t.tBookbankissuereturnPK.memCd = ?1 AND t.tBookbankissuereturnPK.accNo = ?2 AND t.status = 'BB'"),
    @NamedQuery(name = "TBookbankissuereturn.findByMemCdAndIssued", query = "SELECT t FROM TBookbankissuereturn t WHERE t.tBookbankissuereturnPK.memCd = :memCd  AND t.status = 'BB'"),
    @NamedQuery(name = "TBookbankissuereturn.findByMemCdAndAccNoAndIssueDt", query = "SELECT t FROM TBookbankissuereturn t WHERE t.tBookbankissuereturnPK.memCd = ?1 AND t.tBookbankissuereturnPK.accNo = ?2 AND t.tBookbankissuereturnPK.issueDt = ?3")
})
public class TBookbankissuereturn implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TBookbankissuereturnPK tBookbankissuereturnPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "accprice")
    private BigDecimal accprice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "due_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDt;
    @Size(max = 50)
    @Column(name = "user_cd")
    private String userCd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "status")
    private String status;
    @Column(name = "return_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDt;
    @Size(max = 50)
    @Column(name = "return_user_cd")
    private String returnUserCd;

    public TBookbankissuereturn() {
    }

    public TBookbankissuereturn(TBookbankissuereturnPK tBookbankissuereturnPK) {
        this.tBookbankissuereturnPK = tBookbankissuereturnPK;
    }

    public TBookbankissuereturn(TBookbankissuereturnPK tBookbankissuereturnPK, BigDecimal accprice, Date dueDt, String status) {
        this.tBookbankissuereturnPK = tBookbankissuereturnPK;
        this.accprice = accprice;
        this.dueDt = dueDt;
        this.status = status;
    }

    public TBookbankissuereturn(String memCd, String accNo, Date issueDt) {
        this.tBookbankissuereturnPK = new TBookbankissuereturnPK(memCd, accNo, issueDt);
    }

    public TBookbankissuereturnPK getTBookbankissuereturnPK() {
        return tBookbankissuereturnPK;
    }

    public void setTBookbankissuereturnPK(TBookbankissuereturnPK tBookbankissuereturnPK) {
        this.tBookbankissuereturnPK = tBookbankissuereturnPK;
    }

    public BigDecimal getAccprice() {
        return accprice;
    }

    public void setAccprice(BigDecimal accprice) {
        this.accprice = accprice;
    }

    public Date getDueDt() {
        return dueDt;
    }

    public void setDueDt(Date dueDt) {
        this.dueDt = dueDt;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getReturnDt() {
        return returnDt;
    }

    public void setReturnDt(Date returnDt) {
        this.returnDt = returnDt;
    }

    public String getReturnUserCd() {
        return returnUserCd;
    }

    public void setReturnUserCd(String returnUserCd) {
        this.returnUserCd = returnUserCd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tBookbankissuereturnPK != null ? tBookbankissuereturnPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TBookbankissuereturn)) {
            return false;
        }
        TBookbankissuereturn other = (TBookbankissuereturn) object;
        if ((this.tBookbankissuereturnPK == null && other.tBookbankissuereturnPK != null) || (this.tBookbankissuereturnPK != null && !this.tBookbankissuereturnPK.equals(other.tBookbankissuereturnPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TBookbankissuereturn[ tBookbankissuereturnPK=" + tBookbankissuereturnPK + " ]";
    }
    
}
