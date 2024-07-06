/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "s_looseissuemapping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SLooseissuemapping.findAll", query = "SELECT s FROM SLooseissuemapping s")
    , @NamedQuery(name = "SLooseissuemapping.findById", query = "SELECT s FROM SLooseissuemapping s WHERE s.id = :id")
    , @NamedQuery(name = "SLooseissuemapping.findBySRecid", query = "SELECT s FROM SLooseissuemapping s WHERE s.sRecid = :sRecid")
    , @NamedQuery(name = "SLooseissuemapping.findBySCatRecid", query = "SELECT s FROM SLooseissuemapping s WHERE s.sCatRecid = :sCatRecid")
    , @NamedQuery(name = "SLooseissuemapping.findByVolume", query = "SELECT s FROM SLooseissuemapping s WHERE s.volume = :volume")
    , @NamedQuery(name = "SLooseissuemapping.findByIssue", query = "SELECT s FROM SLooseissuemapping s WHERE s.issue = :issue")
    , @NamedQuery(name = "SLooseissuemapping.findBySTitle", query = "SELECT s FROM SLooseissuemapping s WHERE s.sTitle = :sTitle")
    , @NamedQuery(name = "SLooseissuemapping.findBySAccessionno", query = "SELECT s FROM SLooseissuemapping s WHERE s.sAccessionno = :sAccessionno")
    , @NamedQuery(name = "SLooseissuemapping.findByAllowIssue", query = "SELECT s FROM SLooseissuemapping s WHERE s.allowIssue = :allowIssue")})
public class SLooseissuemapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "s_recid")
    private String sRecid;
    @Column(name = "s_cat_recid")
    private Integer sCatRecid;
    @Size(max = 100)
    @Column(name = "Volume")
    private String volume;
    @Size(max = 100)
    @Column(name = "Issue")
    private String issue;
    @Size(max = 2000)
    @Column(name = "s_title")
    private String sTitle;
    @Size(max = 100)
    @Column(name = "s_accessionno")
    private String sAccessionno;
    @Size(max = 6)
    @Column(name = "AllowIssue")
    private String allowIssue;

    public SLooseissuemapping() {
    }

    public SLooseissuemapping(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSRecid() {
        return sRecid;
    }

    public void setSRecid(String sRecid) {
        this.sRecid = sRecid;
    }

    public Integer getSCatRecid() {
        return sCatRecid;
    }

    public void setSCatRecid(Integer sCatRecid) {
        this.sCatRecid = sCatRecid;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getSTitle() {
        return sTitle;
    }

    public void setSTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getSAccessionno() {
        return sAccessionno;
    }

    public void setSAccessionno(String sAccessionno) {
        this.sAccessionno = sAccessionno;
    }

    public String getAllowIssue() {
        return allowIssue;
    }

    public void setAllowIssue(String allowIssue) {
        this.allowIssue = allowIssue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SLooseissuemapping)) {
            return false;
        }
        SLooseissuemapping other = (SLooseissuemapping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.serialControl.SLooseissuemapping[ id=" + id + " ]";
    }
    
}
