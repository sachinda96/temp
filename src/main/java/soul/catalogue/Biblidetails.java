/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
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
@Table(name = "biblidetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Biblidetails.findAll", query = "SELECT b FROM Biblidetails b"),
    @NamedQuery(name = "Biblidetails.findByRecID", query = "SELECT b FROM Biblidetails b WHERE b.biblidetailsPK.recID = :recID"),
    @NamedQuery(name = "Biblidetails.findByTagSrNo", query = "SELECT b FROM Biblidetails b WHERE b.biblidetailsPK.tagSrNo = :tagSrNo"),
    @NamedQuery(name = "Biblidetails.findByTag", query = "SELECT b FROM Biblidetails b WHERE b.biblidetailsPK.tag = :tag"),
    @NamedQuery(name = "Biblidetails.findByInd", query = "SELECT b FROM Biblidetails b WHERE b.ind = :ind"),
    @NamedQuery(name = "Biblidetails.findBySbFldSrNo", query = "SELECT b FROM Biblidetails b WHERE b.biblidetailsPK.sbFldSrNo = :sbFldSrNo"),
    @NamedQuery(name = "Biblidetails.findBySbFld", query = "SELECT b FROM Biblidetails b WHERE b.biblidetailsPK.sbFld = :sbFld"),
    @NamedQuery(name = "Biblidetails.findByRecIDAndTag887", query = "SELECT b FROM Biblidetails b WHERE b.biblidetailsPK.recID = :recId AND b.biblidetailsPK.tag = '887' "),
    @NamedQuery(name = "Biblidetails.findByRecIDAndTag563", query = "SELECT b FROM Biblidetails b WHERE b.biblidetailsPK.recID = :recId AND b.biblidetailsPK.tag = '563' "),
    @NamedQuery(name = "Biblidetails.findByRecIDMaxSbFldSrNoAndTag887", query = "SELECT b FROM Biblidetails b WHERE b.biblidetailsPK.recID = :recId AND b.biblidetailsPK.tag = '887' AND b.biblidetailsPK.sbFldSrNo = (SELECT MAX(b.biblidetailsPK.sbFldSrNo) FROM Biblidetails b)"),
    @NamedQuery(name = "Biblidetails.findByTitle", query = "SELECT b FROM Biblidetails b WHERE b.biblidetailsPK.tag = \"245\" AND b.biblidetailsPK.sbFld = \"a\" AND b.fValue = :title"),
    @NamedQuery(name = "Biblidetails.findByMaxRecID", query = "SELECT b FROM Biblidetails b WHERE b.biblidetailsPK.recID = (SELECT MAX(b.biblidetailsPK.recID) FROM Biblidetails b)"),
    @NamedQuery(name = "Biblidetails.deleteByRecID", query = "DELETE FROM Biblidetails b WHERE b.biblidetailsPK.recID = :recID"),
    @NamedQuery(name = "Biblidetails.deleteByPk", query = "DELETE FROM Biblidetails b WHERE b.biblidetailsPK.recID = ?1 and b.biblidetailsPK.tag = ?2 and b.biblidetailsPK.sbFld = ?3 and b.biblidetailsPK.tagSrNo = ?4  and b.biblidetailsPK.sbFldSrNo = ?5"),
    @NamedQuery(name = "Authoritybiblidetails.findBetweenStartAndEndRecId", query = "SELECT b FROM Authoritybiblidetails b WHERE b.authoritybiblidetailsPK.recID BETWEEN  ?1 AND ?2"),
    @NamedQuery(name = "Biblidetails.countRecId", query = "Select COUNT (b) FROM Biblidetails b WHERE b.biblidetailsPK.recID = :recID"),
    @NamedQuery(name = "Biblidetails.deleteByRecID&tag&sbfld", query = "DELETE FROM Biblidetails b WHERE b.biblidetailsPK.recID = ?1 and b.biblidetailsPK.tag = ?2 and b.biblidetailsPK.sbFld = ?3 "),
    @NamedQuery(name = "Biblidetails.findBetweenStartAndEndRecId", query = "SELECT b FROM Biblidetails b WHERE b.biblidetailsPK.recID BETWEEN  ?1 AND ?2"),
    @NamedQuery(name = "Biblidetails.deleteBetweenStartAndEndRecId", query = "DELETE FROM Biblidetails b WHERE b.biblidetailsPK.recID BETWEEN  ?1 AND ?2"),
    @NamedQuery(name = "Biblidetails.findFvalueByTagAndSubfieldAndSearchWord", query = "SELECT b FROM Biblidetails b WHERE b.biblidetailsPK.tag = ?1 AND b.biblidetailsPK.sbFld = ?2 AND b.fValue LIKE ?3"),
    
    @NamedQuery(name = "Biblidetails.findByf852AndDateOfAcq", query = "SELECT b FROM Biblidetails b where b.biblidetailsPK.recID IN (SELECT l.locationPK.recID from Location l where l.f852 LIKE ?1 AND l.dateofAcq BETWEEN ?2 AND ?3) "),
    @NamedQuery(name = "Biblidetails.findRecIdToSetLocationByTitle", query = "select b from Biblidetails b  where b.biblidetailsPK.tag in ('210','222','240','242','243','245','246','247')  and b.biblidetailsPK.sbFld in ('a','b')   and b.fValue LIKE :fValue and  b.biblidetailsPK.recID   not in (Select distinct l.locationPK.recID from Location l)"),
    @NamedQuery(name = "Biblidetails.findByListOfRecId", query = "select b from Biblidetails b  where b.biblidetailsPK.recID IN :recIds "),
    // @NamedQuery(name = "Biblidetails.findMaxRecId", query = "SELECT MAX(recID) FROM Biblidetails")

})
public class Biblidetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BiblidetailsPK biblidetailsPK;
    @Size(max = 2)
    @Column(name = "Ind")
    private String ind;
    @Lob
    @Size(max = 65535)
    @Column(name = "FValue")
    private String fValue;

    public Biblidetails() {
    }

    public Biblidetails(BiblidetailsPK biblidetailsPK) {
        this.biblidetailsPK = biblidetailsPK;
    }

    public Biblidetails(int recID, int tagSrNo, String tag, int sbFldSrNo, String sbFld) {
        this.biblidetailsPK = new BiblidetailsPK(recID, tagSrNo, tag, sbFldSrNo, sbFld);
    }

    public BiblidetailsPK getBiblidetailsPK() {
        return biblidetailsPK;
    }

    public void setBiblidetailsPK(BiblidetailsPK biblidetailsPK) {
        this.biblidetailsPK = biblidetailsPK;
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
        hash += (biblidetailsPK != null ? biblidetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Biblidetails)) {
            return false;
        }
        Biblidetails other = (Biblidetails) object;
        if ((this.biblidetailsPK == null && other.biblidetailsPK != null) || (this.biblidetailsPK != null && !this.biblidetailsPK.equals(other.biblidetailsPK))) {
            return false;
        }
        return true;
    }
    
    public String getFine() {
        return "delete from t_memfine";
    }

    @Override
    public String toString() {
        return "soul.catalogue.Biblidetails[ biblidetailsPK=" + biblidetailsPK + " ]";
    }
    
}
