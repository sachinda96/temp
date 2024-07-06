/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author soullab
 */
@Embeddable
public class BiblidetailsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "RecID")
    private int recID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TagSrNo")
    private int tagSrNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "Tag")
    private String tag;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SbFldSrNo")
    private int sbFldSrNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "SbFld")
    private String sbFld;

    public BiblidetailsPK() {
    }

    public BiblidetailsPK(int recID, int tagSrNo, String tag, int sbFldSrNo, String sbFld) {
        this.recID = recID;
        this.tagSrNo = tagSrNo;
        this.tag = tag;
        this.sbFldSrNo = sbFldSrNo;
        this.sbFld = sbFld;
    }

    public int getRecID() {
        return recID;
    }

    public void setRecID(int recID) {
        this.recID = recID;
    }

    public int getTagSrNo() {
        return tagSrNo;
    }

    public void setTagSrNo(int tagSrNo) {
        this.tagSrNo = tagSrNo;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getSbFldSrNo() {
        return sbFldSrNo;
    }

    public void setSbFldSrNo(int sbFldSrNo) {
        this.sbFldSrNo = sbFldSrNo;
    }

    public String getSbFld() {
        return sbFld;
    }

    public void setSbFld(String sbFld) {
        this.sbFld = sbFld;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) recID;
        hash += (int) tagSrNo;
        hash += (tag != null ? tag.hashCode() : 0);
        hash += (int) sbFldSrNo;
        hash += (sbFld != null ? sbFld.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BiblidetailsPK)) {
            return false;
        }
        BiblidetailsPK other = (BiblidetailsPK) object;
        if (this.recID != other.recID) {
            return false;
        }
        if (this.tagSrNo != other.tagSrNo) {
            return false;
        }
        if ((this.tag == null && other.tag != null) || (this.tag != null && !this.tag.equals(other.tag))) {
            return false;
        }
        if (this.sbFldSrNo != other.sbFldSrNo) {
            return false;
        }
        if ((this.sbFld == null && other.sbFld != null) || (this.sbFld != null && !this.sbFld.equals(other.sbFld))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.catalogue.BiblidetailsPK[ recID=" + recID + ", tagSrNo=" + tagSrNo + ", tag=" + tag + ", sbFldSrNo=" + sbFldSrNo + ", sbFld=" + sbFld + " ]";
    }
    
}
