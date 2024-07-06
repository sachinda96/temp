/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "authoritybiblidetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Authoritybiblidetails.findAll", query = "SELECT a FROM Authoritybiblidetails a"),
    @NamedQuery(name = "Authoritybiblidetails.findByRecID", query = "SELECT a FROM Authoritybiblidetails a WHERE a.authoritybiblidetailsPK.recID = :recID"),
    @NamedQuery(name = "Authoritybiblidetails.findByTagSrNo", query = "SELECT a FROM Authoritybiblidetails a WHERE a.authoritybiblidetailsPK.tagSrNo = :tagSrNo"),
    @NamedQuery(name = "Authoritybiblidetails.findByTag", query = "SELECT a FROM Authoritybiblidetails a WHERE a.authoritybiblidetailsPK.tag = :tag"),
    @NamedQuery(name = "Authoritybiblidetails.findByInd", query = "SELECT a FROM Authoritybiblidetails a WHERE a.ind = :ind"),
    @NamedQuery(name = "Authoritybiblidetails.findBySbFldSrNo", query = "SELECT a FROM Authoritybiblidetails a WHERE a.authoritybiblidetailsPK.sbFldSrNo = :sbFldSrNo"),
    @NamedQuery(name = "Authoritybiblidetails.findBySbFld", query = "SELECT a FROM Authoritybiblidetails a WHERE a.authoritybiblidetailsPK.sbFld = :sbFld"),
    @NamedQuery(name = "Authoritybiblidetails.findByFValue", query = "SELECT a FROM Authoritybiblidetails a WHERE a.fValue = :fValue"),

    @NamedQuery(name = "Authoritybiblidetails.findByRecIDMaxSbFldSrNoAndTag887", query = "SELECT b FROM Authoritybiblidetails b WHERE b.authoritybiblidetailsPK.recID = :recId AND b.authoritybiblidetailsPK.tag = '887' AND b.authoritybiblidetailsPK.sbFldSrNo = (SELECT MAX(b.authoritybiblidetailsPK.sbFldSrNo) FROM Authoritybiblidetails b)"),
    @NamedQuery(name = "Authoritybiblidetails.findByTitle", query = "SELECT b FROM Authoritybiblidetails b WHERE b.authoritybiblidetailsPK.tag = \"245\" AND b.authoritybiblidetailsPK.sbFld = \"a\" AND b.fValue = :title"),
    @NamedQuery(name = "Authoritybiblidetails.findByMaxRecID", query = "SELECT b FROM Authoritybiblidetails b WHERE b.authoritybiblidetailsPK.recID = (SELECT MAX(b.authoritybiblidetailsPK.recID) FROM Authoritybiblidetails b)"),
    @NamedQuery(name = "Authoritybiblidetails.deleteByRecID", query = "DELETE FROM Authoritybiblidetails b WHERE b.authoritybiblidetailsPK.recID = :recID"),
    @NamedQuery(name = "Authoritybiblidetails.countRecId", query = "Select COUNT (b) FROM Authoritybiblidetails b WHERE b.authoritybiblidetailsPK.recID = :recID"),
    @NamedQuery(name = "Authoritybiblidetails.deleteByRecID&tag&sbfld", query = "DELETE FROM Authoritybiblidetails b WHERE b.authoritybiblidetailsPK.recID = ?1 and b.authoritybiblidetailsPK.tag = ?2 and b.authoritybiblidetailsPK.sbFld = ?3 "),
    @NamedQuery(name = "Authoritybiblidetails.deleteByPk", query = "DELETE FROM Authoritybiblidetails a WHERE a.authoritybiblidetailsPK.recID = ?1 and a.authoritybiblidetailsPK.tag = ?2 and a.authoritybiblidetailsPK.sbFld = ?3 and a.authoritybiblidetailsPK.tagSrNo = ?4  and a.authoritybiblidetailsPK.sbFldSrNo = ?5"),
    @NamedQuery(name = "Authoritybiblidetails.findBetweenStartAndEndRecId", query = "SELECT b FROM Authoritybiblidetails b WHERE b.authoritybiblidetailsPK.recID BETWEEN  ?1 AND ?2"),
    @NamedQuery(name = "Authoritybiblidetails.deleteBetweenStartAndEndRecId", query = "DELETE FROM Authoritybiblidetails b WHERE b.authoritybiblidetailsPK.recID BETWEEN  ?1 AND ?2")})

public class Authoritybiblidetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AuthoritybiblidetailsPK authoritybiblidetailsPK;
    @Size(max = 4)
    @Column(name = "Ind")
    private String ind;
    @Size(max = 8000)
    @Column(name = "FValue")
    private String fValue;

    public Authoritybiblidetails() {
    }

    public Authoritybiblidetails(AuthoritybiblidetailsPK authoritybiblidetailsPK) {
        this.authoritybiblidetailsPK = authoritybiblidetailsPK;
    }

    public Authoritybiblidetails(int recID, int tagSrNo, String tag, int sbFldSrNo, String sbFld) {
        this.authoritybiblidetailsPK = new AuthoritybiblidetailsPK(recID, tagSrNo, tag, sbFldSrNo, sbFld);
    }

    public AuthoritybiblidetailsPK getAuthoritybiblidetailsPK() {
        return authoritybiblidetailsPK;
    }

    public void setAuthoritybiblidetailsPK(AuthoritybiblidetailsPK authoritybiblidetailsPK) {
        this.authoritybiblidetailsPK = authoritybiblidetailsPK;
    }

    public String getInd() {
        return ind;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }

    public String getFValue() {
        return fValue;
    }

    public void setFValue(String fValue) {
        this.fValue = fValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (authoritybiblidetailsPK != null ? authoritybiblidetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Authoritybiblidetails)) {
            return false;
        }
        Authoritybiblidetails other = (Authoritybiblidetails) object;
        if ((this.authoritybiblidetailsPK == null && other.authoritybiblidetailsPK != null) || (this.authoritybiblidetailsPK != null && !this.authoritybiblidetailsPK.equals(other.authoritybiblidetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.Authoritybiblidetails[ authoritybiblidetailsPK=" + authoritybiblidetailsPK + " ]";
    }
    
}
