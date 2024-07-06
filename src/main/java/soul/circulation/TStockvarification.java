/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
 * @author admin
 */
@Entity
@Table(name = "t_stockvarification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TStockvarification.findAll", query = "SELECT t FROM TStockvarification t")
    , @NamedQuery(name = "TStockvarification.findByAccNo", query = "SELECT t FROM TStockvarification t WHERE t.accNo = :accNo")
    , @NamedQuery(name = "TStockvarification.findByFoundlocation", query = "SELECT t FROM TStockvarification t WHERE t.foundlocation = :foundlocation")
    , @NamedQuery(name = "TStockvarification.findByOriginallocation", query = "SELECT t FROM TStockvarification t WHERE t.originallocation = :originallocation")
    , @NamedQuery(name = "TStockvarification.findByStatus", query = "SELECT t FROM TStockvarification t WHERE t.status = :status")
    , @NamedQuery(name = "TStockvarification.findByFound", query = "SELECT t FROM TStockvarification t WHERE t.found = :found")
    , @NamedQuery(name = "TStockvarification.findByFounddt", query = "SELECT t FROM TStockvarification t WHERE t.founddt = :founddt")})
public class TStockvarification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "acc_no")
    private String accNo;
    @Size(max = 100)
    @Column(name = "Found_location")
    private String foundlocation;
    @Size(max = 100)
    @Column(name = "Original_location")
    private String originallocation;
    @Size(max = 50)
    @Column(name = "Status")
    private String status;
    @Size(max = 100)
    @Column(name = "Found")
    private String found;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Found_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date founddt;

    public TStockvarification() {
    }

    public TStockvarification(String accNo) {
        this.accNo = accNo;
    }

    public TStockvarification(String accNo, Date founddt) {
        this.accNo = accNo;
        this.founddt = founddt;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getFoundlocation() {
        return foundlocation;
    }

    public void setFoundlocation(String foundlocation) {
        this.foundlocation = foundlocation;
    }

    public String getOriginallocation() {
        return originallocation;
    }

    public void setOriginallocation(String originallocation) {
        this.originallocation = originallocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }

    public Date getFounddt() {
        return founddt;
    }

    public void setFounddt(Date founddt) {
        this.founddt = founddt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accNo != null ? accNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TStockvarification)) {
            return false;
        }
        TStockvarification other = (TStockvarification) object;
        if ((this.accNo == null && other.accNo != null) || (this.accNo != null && !this.accNo.equals(other.accNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TStockvarification[ accNo=" + accNo + " ]";
    }
    
}
