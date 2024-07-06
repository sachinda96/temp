/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import soul.general_master.Libmaterials;
import soul.general_master.MFcltydept;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Location.findAll", query = "SELECT  l FROM Location l"),
    @NamedQuery(name = "Location.findByRecID", query = "SELECT l FROM Location l WHERE l.locationPK.recID = :recID"),
    @NamedQuery(name = "Location.findByRecordID", query = "SELECT l FROM Location l WHERE l.locationPK.recID = :recID"),
    @NamedQuery(name = "Location.findByInd", query = "SELECT l FROM Location l WHERE l.ind = :ind"),
    @NamedQuery(name = "Location.findByT852", query = "SELECT l FROM Location l WHERE l.t852 = :t852"),
    @NamedQuery(name = "Location.findByP852", query = "SELECT l FROM Location l WHERE l.locationPK.p852 = :p852"),
    @NamedQuery(name = "Location.findByB852", query = "SELECT l FROM Location l WHERE l.b852 = :b852"),
    @NamedQuery(name = "Location.findByA852", query = "SELECT l FROM Location l WHERE l.a852 = :a852"),
    @NamedQuery(name = "Location.findByC852", query = "SELECT l FROM Location l WHERE l.c852 = :c852"),
    @NamedQuery(name = "Location.findByK852", query = "SELECT l FROM Location l WHERE l.k852 = :k852"),
    @NamedQuery(name = "Location.findByM852", query = "SELECT l FROM Location l WHERE l.m852 = :m852"),
    @NamedQuery(name = "Location.findByF852", query = "SELECT l FROM Location l WHERE l.f852 = :f852"),
    @NamedQuery(name = "Location.findByIssueRestricted", query = "SELECT l FROM Location l WHERE l.issueRestricted = :issueRestricted"),
    @NamedQuery(name = "Location.findByCurrency", query = "SELECT l FROM Location l WHERE l.currency = :currency"),
    @NamedQuery(name = "Location.findByPrice", query = "SELECT l FROM Location l WHERE l.price = :price"),
    @NamedQuery(name = "Location.findByStatus", query = "SELECT l FROM Location l WHERE l.status = :status"),
    @NamedQuery(name = "Location.findByDateofAcq", query = "SELECT l FROM Location l WHERE l.dateofAcq = :dateofAcq"),
    @NamedQuery(name = "Location.findBySupplier", query = "SELECT l FROM Location l WHERE l.supplier = :supplier"),
    @NamedQuery(name = "Location.findByInvoiceNo", query = "SELECT l FROM Location l WHERE l.invoiceNo = :invoiceNo"),
    @NamedQuery(name = "Location.findByInvoiceDate", query = "SELECT l FROM Location l WHERE l.invoiceDate = :invoiceDate"),
    @NamedQuery(name = "Location.findByBudget", query = "SELECT l FROM Location l WHERE l.budget = :budget"),
    @NamedQuery(name = "Location.findByUserName", query = "SELECT l FROM Location l WHERE l.userName = :userName"),
    @NamedQuery(name = "Location.findByLastOperatedDT", query = "SELECT l FROM Location l WHERE l.lastOperatedDT = :lastOperatedDT"),
    
    @NamedQuery(name = "Location.findByRecIDAndStatus", query = "SELECT l FROM Location l WHERE l.locationPK.recID = ?1 AND l.status = ?2"),
    @NamedQuery(name = "Location.findByRecIDMaxDate", query = "SELECT l FROM Location l WHERE l.locationPK.recID = :recID GROUP BY l.locationPK.recID HAVING l.dateofAcq = MAX(l.dateofAcq)"),
    @NamedQuery(name = "Location.findByMaxT852", query = "SELECT l FROM Location l WHERE l.locationPK.recID = :recID GROUP BY l.locationPK.recID HAVING l.t852 =  MAX(l.t852)"),
    @NamedQuery(name = "Location.findByMaxRecID", query = "SELECT l FROM Location l WHERE l.locationPK.recID = (SELECT MAX(l.locationPK.recID) FROM Location l)"),
    @NamedQuery(name = "Location.countByAccNumRecId", query = "Select COUNT (l) FROM Location l WHERE l.locationPK.recID = ?1 and l.locationPK.p852 = ?2 "),
    @NamedQuery(name = "Location.deleteByRecID", query = "DELETE FROM Location b WHERE b.locationPK.recID = :recID"),
    @NamedQuery(name = "Location.deleteBetweenStartAndEndRecId", query = "DELETE FROM Location l WHERE l.locationPK.recID BETWEEN  ?1 AND ?2"),
    @NamedQuery(name = "Location.findBetweenStartAndEndRecId", query = "SELECT l FROM Location l WHERE l.locationPK.recID  BETWEEN ?1 AND ?2"),
    @NamedQuery(name = "Location.findByf852AndDateOfAcq", query = "SELECT l FROM Location l WHERE l.f852 LIKE ?1 AND l.dateofAcq  BETWEEN ?2 AND ?3")
    
})
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LocationPK locationPK;
    @Size(max = 2)
    @Column(name = "Ind")
    private String ind;
    @Column(name = "t852")
    private Integer t852;
    @Size(max = 50)
    @Column(name = "b852")
    private String b852;
    @Size(max = 50)
    @Column(name = "a852")
    private String a852;
    @Size(max = 50)
    @Column(name = "c852")
    private String c852;
    @Size(max = 50)
    @Column(name = "k852")
    private String k852;
    @Size(max = 50)
    @Column(name = "m852")
    private String m852;
    @Size(max = 50)
    @Column(name = "f852")
    private String f852;
    @Column(name = "IssueRestricted")
    private String issueRestricted;
    @Size(max = 3)
    @Column(name = "Currency")
    private String currency;
    @Size(max = 50)
    @Column(name = "Price")
    private String price;
    @Size(max = 50)
    @Column(name = "Status")
    private String status;
    @Column(name = "DateofAcq")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateofAcq;
    @Size(max = 50)
    @Column(name = "Supplier")
    private String supplier;
    @Size(max = 50)
    @Column(name = "InvoiceNo")
    private String invoiceNo;
    @Column(name = "InvoiceDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceDate;
    @Size(max = 50)
    @Column(name = "Budget")
    private String budget;
    @Size(max = 100)
    @Column(name = "UserName")
    private String userName;
    @Column(name = "LastOperatedDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastOperatedDT;
    @JoinColumn(name = "Material", referencedColumnName = "Code")
    @ManyToOne
    private Libmaterials material;
    @JoinColumn(name = "Department", referencedColumnName = "Fclty_dept_cd")
    @ManyToOne
    private MFcltydept department;

    public Location() {
    }

    public Location(LocationPK locationPK) {
        this.locationPK = locationPK;
    }

    public Location(int recID, String p852) {
        this.locationPK = new LocationPK(recID, p852);
    }

    public LocationPK getLocationPK() {
        return locationPK;
    }

    public void setLocationPK(LocationPK locationPK) {
        this.locationPK = locationPK;
    }

    public String getInd() {
        return ind;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }

    public Integer getT852() {
        return t852;
    }

    public void setT852(Integer t852) {
        this.t852 = t852;
    }

    public String getB852() {
        return b852;
    }

    public void setB852(String b852) {
        this.b852 = b852;
    }

    public String getA852() {
        return a852;
    }

    public void setA852(String a852) {
        this.a852 = a852;
    }

    public String getC852() {
        return c852;
    }

    public void setC852(String c852) {
        this.c852 = c852;
    }

    public String getK852() {
        return k852;
    }

    public void setK852(String k852) {
        this.k852 = k852;
    }

    public String getM852() {
        return m852;
    }

    public void setM852(String m852) {
        this.m852 = m852;
    }

    public String getF852() {
        return f852;
    }

    public void setF852(String f852) {
        this.f852 = f852;
    }

    public String getIssueRestricted() {
        return issueRestricted;
    }

    public void setIssueRestricted(String issueRestricted) {
        this.issueRestricted = issueRestricted;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateofAcq() {
        return dateofAcq;
    }

    public void setDateofAcq(Date dateofAcq) {
        this.dateofAcq = dateofAcq;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLastOperatedDT() {
        return lastOperatedDT;
    }

    public void setLastOperatedDT(Date lastOperatedDT) {
        this.lastOperatedDT = lastOperatedDT;
    }

    public Libmaterials getMaterial() {
        return material;
    }

    public void setMaterial(Libmaterials material) {
        this.material = material;
    }

    public MFcltydept getDepartment() {
        return department;
    }

    public void setDepartment(MFcltydept department) {
        this.department = department;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationPK != null ? locationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.locationPK == null && other.locationPK != null) || (this.locationPK != null && !this.locationPK.equals(other.locationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.Location[ locationPK=" + locationPK + " ]";
    }
    
}
