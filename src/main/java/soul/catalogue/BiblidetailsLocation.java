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
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
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

// @SqlResultSetMapping(
//         name = "GlobalSearchMapping",
//        entities = {
//            @EntityResult(
//                    entityclass = BiblidetailsLocation.class,
//                    fields = {
//                 @FieldResult(name = "p852", column = "p852"),
//        @FieldResult(name = "f852", column = "f852"),
//        @FieldResult(name = "k852", column = "k852"),
//        @FieldResult(name = "m852", column = "m852"),
//        @FieldResult(name = "recID", column = "RecID"),
//        @FieldResult(name = "issueRestricted", column = "IssueRestricted"),
//        @FieldResult(name = "status", column = "Status"),
//        @FieldResult(name = "statusDscr", column = "status_dscr"),
//        @FieldResult(name = "price", column = "Price"),
//        @FieldResult(name = "material", column = "Material"),
//        @FieldResult(name = "description", column = "Description"),
//        @FieldResult(name = "title", column = "title"),
//        @FieldResult(name = "author", column = "author"),
//        @FieldResult(name = "edition", column = "edition"),
//        @FieldResult(name = "isbn", column = "isbn"),
//        @FieldResult(name = "dateofAcq", column = "DateofAcq")
//                      }),
//            @EntityResult(
//                    entityclass = Location.class,
//                    fields = {
//                        @FieldResult(name = "recID", column = "RecID"),
//                        @FieldResult(name = "a852", column = "a852"),
//                        @FieldResult(name = "f852", column = "f852"),
//                        @FieldResult(name = "k852", column = "k852")})})
@Entity
@Table(name = "biblidetails_location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BiblidetailsLocation.findAll", query = "SELECT b FROM BiblidetailsLocation b"),
    @NamedQuery(name = "BiblidetailsLocation.findByP852", query = "SELECT b FROM BiblidetailsLocation b WHERE b.p852 = :p852"),
    @NamedQuery(name = "BiblidetailsLocation.findByK852", query = "SELECT b FROM BiblidetailsLocation b WHERE b.k852 = :k852"),
    @NamedQuery(name = "BiblidetailsLocation.findByM852", query = "SELECT b FROM BiblidetailsLocation b WHERE b.m852 = :m852"),
    @NamedQuery(name = "BiblidetailsLocation.findByRecID", query = "SELECT b FROM BiblidetailsLocation b WHERE b.recID = :recID"),
    @NamedQuery(name = "BiblidetailsLocation.recids", query = "SELECT b FROM BiblidetailsLocation b WHERE b.recID IN (:recIdListsend)"),
    @NamedQuery(name = "BiblidetailsLocation.findByIssueRestricted", query = "SELECT b FROM BiblidetailsLocation b WHERE b.issueRestricted = :issueRestricted"),
    @NamedQuery(name = "BiblidetailsLocation.findByStatus", query = "SELECT b FROM BiblidetailsLocation b WHERE b.status = :status"),
    @NamedQuery(name = "BiblidetailsLocation.findByStatusDscr", query = "SELECT b FROM BiblidetailsLocation b WHERE b.statusDscr = :statusDscr"),
    @NamedQuery(name = "BiblidetailsLocation.findByPrice", query = "SELECT b FROM BiblidetailsLocation b WHERE b.price = :price"),
    @NamedQuery(name = "BiblidetailsLocation.findByMaterial", query = "SELECT b FROM BiblidetailsLocation b WHERE b.material = :material"),
    @NamedQuery(name = "BiblidetailsLocation.findByDescription", query = "SELECT b FROM BiblidetailsLocation b WHERE b.description = :description"),
    @NamedQuery(name = "BiblidetailsLocation.findByLibCdAndStatus", query = "SELECT b FROM BiblidetailsLocation b WHERE b.f852 = ?1 AND b.statusDscr = ?2 "),
    @NamedQuery(name = "BiblidetailsLocation.findByTitleLike", query = "SELECT b FROM BiblidetailsLocation b WHERE b.title LIKE :titleLike"),
    @NamedQuery(name = "BiblidetailsLocation.findByTitleLikeAndStatusIP", query = "SELECT b FROM BiblidetailsLocation b WHERE b.title LIKE :titleLike And b.status = 'IP'"),
    @NamedQuery(name = "BiblidetailsLocation.findByDateOfAcqBetweenAndStatusIP", query = "SELECT b FROM BiblidetailsLocation b WHERE b.dateofAcq BETWEEN ?1 AND ?2 AND b.status = 'IP'"),
    @NamedQuery(name = "BiblidetailsLocation.findByP852AndStatusIP", query = "SELECT b FROM BiblidetailsLocation b WHERE b.p852 = :p852 AND b.status = 'IP'"),
    @NamedQuery(name = "BiblidetailsLocation.findByP852AndStatus", query = "SELECT b FROM BiblidetailsLocation b WHERE b.p852 = :p852 AND (b.status = 'IS' or b.status = 'AR') "),
    @NamedQuery(name = "BiblidetailsLocation.findByStatusIP", query = "SELECT b FROM BiblidetailsLocation b WHERE  b.status = 'IP'"),
    @NamedQuery(name = "BiblidetailsLocation.findByListOfRecId", query = "select b from BiblidetailsLocation b  where b.recID IN :recIds "),
    @NamedQuery(name = "BiblidetailsLocation.findByListOfAccNo", query = "select b from BiblidetailsLocation b  where b.p852 IN :p852s "),

})
public class BiblidetailsLocation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "p852")
    @Id
    private String p852;
    @Size(max = 50)
    @Column(name = "f852")
    private String f852;
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
    @Lob
    @Size(max = 65535)
    @Column(name = "edition")
    private String edition;
    @Lob
    @Size(max = 65535)
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "DateofAcq")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateofAcq;
    public BiblidetailsLocation() {
    }

    public String getP852() {
        return p852;
    }

    public void setP852(String p852) {
        this.p852 = p852;
    }

    public String getF852() {
        return f852;
    }

    public void setF852(String f852) {
        this.f852 = f852;
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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getDateofAcq() {
        return dateofAcq;
    }

    public void setDateofAcq(Date dateofAcq) {
        this.dateofAcq = dateofAcq;
    }
    
}
