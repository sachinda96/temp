/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

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
@Table(name = "biblocation_issue_reserve")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BiblocationIssueReserve.findAll", query = "SELECT b FROM BiblocationIssueReserve b"),
    @NamedQuery(name = "BiblocationIssueReserve.findByP852", query = "SELECT b FROM BiblocationIssueReserve b WHERE b.p852 = :p852"),
    @NamedQuery(name = "BiblocationIssueReserve.findByK852", query = "SELECT b FROM BiblocationIssueReserve b WHERE b.k852 = :k852"),
    @NamedQuery(name = "BiblocationIssueReserve.findByM852", query = "SELECT b FROM BiblocationIssueReserve b WHERE b.m852 = :m852"),
    @NamedQuery(name = "BiblocationIssueReserve.findByRecID", query = "SELECT b FROM BiblocationIssueReserve b WHERE b.recID = :recID"),
    @NamedQuery(name = "BiblocationIssueReserve.findByIssueRestricted", query = "SELECT b FROM BiblocationIssueReserve b WHERE b.issueRestricted = :issueRestricted"),
    @NamedQuery(name = "BiblocationIssueReserve.findByStatus", query = "SELECT b FROM BiblocationIssueReserve b WHERE b.status = :status"),
    @NamedQuery(name = "BiblocationIssueReserve.findByStatusDscr", query = "SELECT b FROM BiblocationIssueReserve b WHERE b.statusDscr = :statusDscr"),
    @NamedQuery(name = "BiblocationIssueReserve.findByPrice", query = "SELECT b FROM BiblocationIssueReserve b WHERE b.price = :price"),
    @NamedQuery(name = "BiblocationIssueReserve.findByMaterial", query = "SELECT b FROM BiblocationIssueReserve b WHERE b.material = :material"),
    @NamedQuery(name = "BiblocationIssueReserve.findByDescription", query = "SELECT b FROM BiblocationIssueReserve b WHERE b.description = :description"),
    @NamedQuery(name = "BiblocationIssueReserve.findByMemCd", query = "SELECT b FROM BiblocationIssueReserve b WHERE b.memCd = :memCd"),
    @NamedQuery(name = "BiblocationIssueReserve.findByIssDt", query = "SELECT b FROM BiblocationIssueReserve b WHERE b.issDt = :issDt"),
    @NamedQuery(name = "BiblocationIssueReserve.findByUserCd", query = "SELECT b FROM BiblocationIssueReserve b WHERE b.userCd = :userCd"),
    @NamedQuery(name = "BiblocationIssueReserve.findByDueDt", query = "SELECT b FROM BiblocationIssueReserve b WHERE b.dueDt = :dueDt"),
    @NamedQuery(name = "BiblocationIssueReserve.findByMaxResvDt", query = "SELECT b FROM BiblocationIssueReserve b WHERE b.maxResvDt = :maxResvDt")})
public class BiblocationIssueReserve implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "p852")
    @Id
    private String p852;
    @Size(max = 50)
    @Column(name = "k852")
    private String k852;
    @Size(max = 50)
    @Column(name = "m852")
    private String m852;
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
    @Size(max = 12)
    @Column(name = "mem_cd")
    private String memCd;
    @Column(name = "iss_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issDt;
    @Size(max = 20)
    @Column(name = "user_cd")
    private String userCd;
    @Column(name = "due_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDt;
    @Column(name = "max_resv_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date maxResvDt;

    public BiblocationIssueReserve() {
    }

    public String getP852() {
        return p852;
    }

    public void setP852(String p852) {
        this.p852 = p852;
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

    public String getMemCd() {
        return memCd;
    }

    public void setMemCd(String memCd) {
        this.memCd = memCd;
    }

    public Date getIssDt() {
        return issDt;
    }

    public void setIssDt(Date issDt) {
        this.issDt = issDt;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public Date getDueDt() {
        return dueDt;
    }

    public void setDueDt(Date dueDt) {
        this.dueDt = dueDt;
    }

    public Date getMaxResvDt() {
        return maxResvDt;
    }

    public void setMaxResvDt(Date maxResvDt) {
        this.maxResvDt = maxResvDt;
    }
    
}
