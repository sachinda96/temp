/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "t_missing_biblocation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TMissingBiblocation.findAll", query = "SELECT t FROM TMissingBiblocation t"),
    @NamedQuery(name = "TMissingBiblocation.findById", query = "SELECT t FROM TMissingBiblocation t WHERE t.id = :id"),
    @NamedQuery(name = "TMissingBiblocation.findByMissingDate", query = "SELECT t FROM TMissingBiblocation t WHERE t.missingDate = :missingDate"),
    @NamedQuery(name = "TMissingBiblocation.findByFoundDate", query = "SELECT t FROM TMissingBiblocation t WHERE t.foundDate = :foundDate"),
    @NamedQuery(name = "TMissingBiblocation.findByBkAccno", query = "SELECT t FROM TMissingBiblocation t WHERE t.bkAccno = :bkAccno"),
    @NamedQuery(name = "TMissingBiblocation.findByMemCdMissRep", query = "SELECT t FROM TMissingBiblocation t WHERE t.memCdMissRep = :memCdMissRep"),
    @NamedQuery(name = "TMissingBiblocation.findByMemCdFounder", query = "SELECT t FROM TMissingBiblocation t WHERE t.memCdFounder = :memCdFounder"),
    @NamedQuery(name = "TMissingBiblocation.findByRemark", query = "SELECT t FROM TMissingBiblocation t WHERE t.remark = :remark"),
    @NamedQuery(name = "TMissingBiblocation.findByUserCd", query = "SELECT t FROM TMissingBiblocation t WHERE t.userCd = :userCd"),
    @NamedQuery(name = "TMissingBiblocation.findByIssueStat", query = "SELECT t FROM TMissingBiblocation t WHERE t.issueStat = :issueStat"),
    @NamedQuery(name = "TMissingBiblocation.findByP852", query = "SELECT t FROM TMissingBiblocation t WHERE t.p852 = :p852"),
    @NamedQuery(name = "TMissingBiblocation.findByRecID", query = "SELECT t FROM TMissingBiblocation t WHERE t.recID = :recID"),
    @NamedQuery(name = "TMissingBiblocation.findByIssueRestricted", query = "SELECT t FROM TMissingBiblocation t WHERE t.issueRestricted = :issueRestricted"),
    @NamedQuery(name = "TMissingBiblocation.findByStatus", query = "SELECT t FROM TMissingBiblocation t WHERE t.status = :status"),
    @NamedQuery(name = "TMissingBiblocation.findByStatusDscr", query = "SELECT t FROM TMissingBiblocation t WHERE t.statusDscr = :statusDscr"),
    @NamedQuery(name = "TMissingBiblocation.findByPrice", query = "SELECT t FROM TMissingBiblocation t WHERE t.price = :price"),
    @NamedQuery(name = "TMissingBiblocation.findByMaterial", query = "SELECT t FROM TMissingBiblocation t WHERE t.material = :material"),
    @NamedQuery(name = "TMissingBiblocation.findByDescription", query = "SELECT t FROM TMissingBiblocation t WHERE t.description = :description"),
    
    @NamedQuery(name = "TMissingBiblocation.findByBkAccnoAndMissing", query = "SELECT t FROM TMissingBiblocation t WHERE t.bkAccno = :bkAccno AND t.status = 'MI'"),
})
public class TMissingBiblocation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @Id
    private int id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "missing_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date missingDate;
    @Column(name = "found_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date foundDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "bk_accno")
    private String bkAccno;
    @Size(max = 12)
    @Column(name = "mem_cd_miss_rep")
    private String memCdMissRep;
    @Size(max = 12)
    @Column(name = "mem_cd_founder")
    private String memCdFounder;
    @Size(max = 100)
    @Column(name = "remark")
    private String remark;
    @Size(max = 20)
    @Column(name = "user_cd")
    private String userCd;
    @Size(max = 2)
    @Column(name = "issue_stat")
    private String issueStat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "p852")
    private String p852;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RecID")
    private int recID;
    @Column(name = "IssueRestricted")
    private Character issueRestricted;
    @Size(max = 50)
    @Column(name = "Status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "status_dscr")
    private String statusDscr;
    @Size(max = 50)
    @Column(name = "Price")
    private String price;
    @Size(max = 3)
    @Column(name = "Material")
    private String material;
    @Size(max = 200)
    @Column(name = "Description")
    private String description;
    @Lob
    @Size(max = 65535)
    @Column(name = "title")
    private String title;
    @Lob
    @Size(max = 65535)
    @Column(name = "author")
    private String author;

    public TMissingBiblocation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getMissingDate() {
        return missingDate;
    }

    public void setMissingDate(Date missingDate) {
        this.missingDate = missingDate;
    }

    public Date getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public String getBkAccno() {
        return bkAccno;
    }

    public void setBkAccno(String bkAccno) {
        this.bkAccno = bkAccno;
    }

    public String getMemCdMissRep() {
        return memCdMissRep;
    }

    public void setMemCdMissRep(String memCdMissRep) {
        this.memCdMissRep = memCdMissRep;
    }

    public String getMemCdFounder() {
        return memCdFounder;
    }

    public void setMemCdFounder(String memCdFounder) {
        this.memCdFounder = memCdFounder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public String getIssueStat() {
        return issueStat;
    }

    public void setIssueStat(String issueStat) {
        this.issueStat = issueStat;
    }

    public String getP852() {
        return p852;
    }

    public void setP852(String p852) {
        this.p852 = p852;
    }

    public int getRecID() {
        return recID;
    }

    public void setRecID(int recID) {
        this.recID = recID;
    }

    public Character getIssueRestricted() {
        return issueRestricted;
    }

    public void setIssueRestricted(Character issueRestricted) {
        this.issueRestricted = issueRestricted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDscr() {
        return statusDscr;
    }

    public void setStatusDscr(String statusDscr) {
        this.statusDscr = statusDscr;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
}
