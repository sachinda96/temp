/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "ill_extissue_biblocation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IllExtissueBiblocation.findAll", query = "SELECT i FROM IllExtissueBiblocation i"),
    @NamedQuery(name = "IllExtissueBiblocation.findByIssueId", query = "SELECT i FROM IllExtissueBiblocation i WHERE i.issueId = :issueId"),
    @NamedQuery(name = "IllExtissueBiblocation.findByLibCd", query = "SELECT i FROM IllExtissueBiblocation i WHERE i.libCd = :libCd"),
    @NamedQuery(name = "IllExtissueBiblocation.findByAccNo", query = "SELECT i FROM IllExtissueBiblocation i WHERE i.accNo = :accNo"),
    @NamedQuery(name = "IllExtissueBiblocation.findByMaterial", query = "SELECT i FROM IllExtissueBiblocation i WHERE i.material = :material"),
    @NamedQuery(name = "IllExtissueBiblocation.findByDescription", query = "SELECT i FROM IllExtissueBiblocation i WHERE i.description = :description"),

    @NamedQuery(name = "IllExtissueBiblocation.findByLibCdAndMaterial", query = "SELECT i FROM IllExtissueBiblocation i WHERE i.libCd = ?1 AND i.material = ?2"),
})
public class IllExtissueBiblocation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "issue_id")
    @Id
    private int issueId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "lib_cd")
    private String libCd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "acc_no")
    private String accNo;
    @Size(max = 3)
    @Column(name = "Material")
    private String material;
    @Size(max = 200)
    @Column(name = "Description")
    private String description;

    public IllExtissueBiblocation() {
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public String getLibCd() {
        return libCd;
    }

    public void setLibCd(String libCd) {
        this.libCd = libCd;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
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
    
}
