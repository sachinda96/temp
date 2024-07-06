/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "t_replace")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TReplace.findAll", query = "SELECT t FROM TReplace t"),
    @NamedQuery(name = "TReplace.findByReplaceId", query = "SELECT t FROM TReplace t WHERE t.replaceId = :replaceId"),
    @NamedQuery(name = "TReplace.findByOldAccno", query = "SELECT t FROM TReplace t WHERE t.oldAccno = :oldAccno"),
    @NamedQuery(name = "TReplace.findByMemCd", query = "SELECT t FROM TReplace t WHERE t.memCd = :memCd"),
    @NamedQuery(name = "TReplace.findByTitle", query = "SELECT t FROM TReplace t WHERE t.title = :title"),
    @NamedQuery(name = "TReplace.findByAuthor", query = "SELECT t FROM TReplace t WHERE t.author = :author"),
    @NamedQuery(name = "TReplace.findByCallNo", query = "SELECT t FROM TReplace t WHERE t.callNo = :callNo"),
    @NamedQuery(name = "TReplace.findByReason", query = "SELECT t FROM TReplace t WHERE t.reason = :reason"),
    @NamedQuery(name = "TReplace.findByEdition", query = "SELECT t FROM TReplace t WHERE t.edition = :edition")})
public class TReplace implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "replace_id")
    private Integer replaceId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "old_accno")
    private String oldAccno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "mem_cd")
    private String memCd;
    @Size(max = 255)
    @Column(name = "title")
    private String title;
    @Size(max = 255)
    @Column(name = "author")
    private String author;
    @Size(max = 50)
    @Column(name = "call_no")
    private String callNo;
    @Size(max = 100)
    @Column(name = "reason")
    private String reason;
    @Size(max = 10)
    @Column(name = "edition")
    private String edition;

    public TReplace() {
    }

    public TReplace(Integer replaceId) {
        this.replaceId = replaceId;
    }

    public TReplace(Integer replaceId, String oldAccno, String memCd) {
        this.replaceId = replaceId;
        this.oldAccno = oldAccno;
        this.memCd = memCd;
    }

    public Integer getReplaceId() {
        return replaceId;
    }

    public void setReplaceId(Integer replaceId) {
        this.replaceId = replaceId;
    }

    public String getOldAccno() {
        return oldAccno;
    }

    public void setOldAccno(String oldAccno) {
        this.oldAccno = oldAccno;
    }

    public String getMemCd() {
        return memCd;
    }

    public void setMemCd(String memCd) {
        this.memCd = memCd;
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

    public String getCallNo() {
        return callNo;
    }

    public void setCallNo(String callNo) {
        this.callNo = callNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (replaceId != null ? replaceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TReplace)) {
            return false;
        }
        TReplace other = (TReplace) object;
        if ((this.replaceId == null && other.replaceId != null) || (this.replaceId != null && !this.replaceId.equals(other.replaceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TReplace[ replaceId=" + replaceId + " ]";
    }
    
}
