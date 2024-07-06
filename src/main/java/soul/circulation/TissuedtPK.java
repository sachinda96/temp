/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author admin
 */
@Embeddable
public class TissuedtPK implements Serializable{
    @Basic(optional = false)
    @NotNull
    @Column(name="accno")
    private String accno;
    @Basic(optional = false)
    @NotNull
    @Column(name="issuedt")
    private Date issuedt;

    public TissuedtPK() {
    }
    
    public TissuedtPK(String accno, Date issuedt) {
        this.accno = accno;
        this.issuedt = issuedt;
    }

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public Date getIssuedt() {
        return issuedt;
    }

    public void setIssuedt(Date issuedt) {
        this.issuedt = issuedt;
    }
    
    
}
