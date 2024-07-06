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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "t_book_transfer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TBookTransfer.findAll", query = "SELECT t FROM TBookTransfer t"),
    @NamedQuery(name = "TBookTransfer.findByTransferId", query = "SELECT t FROM TBookTransfer t WHERE t.transferId = :transferId"),
    @NamedQuery(name = "TBookTransfer.findBySourceLib", query = "SELECT t FROM TBookTransfer t WHERE t.sourceLib = :sourceLib"),
    @NamedQuery(name = "TBookTransfer.findByDestLib", query = "SELECT t FROM TBookTransfer t WHERE t.destLib = :destLib"),
    @NamedQuery(name = "TBookTransfer.findByAccNo", query = "SELECT t FROM TBookTransfer t WHERE t.accNo = :accNo"),
    @NamedQuery(name = "TBookTransfer.findByUserCd", query = "SELECT t FROM TBookTransfer t WHERE t.userCd = :userCd"),
    @NamedQuery(name = "TBookTransfer.findByTransferDt", query = "SELECT t FROM TBookTransfer t WHERE t.transferDt = :transferDt"),
    @NamedQuery(name = "TBookTransfer.findByStatus", query = "SELECT t FROM TBookTransfer t WHERE t.status = :status"),
    @NamedQuery(name = "TBookTransfer.findByReturnDt", query = "SELECT t FROM TBookTransfer t WHERE t.returnDt = :returnDt")})
public class TBookTransfer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "transfer_id")
    private Integer transferId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "source_lib")
    private String sourceLib;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "dest_lib")
    private String destLib;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "acc_no")
    private String accNo;
    @Size(max = 20)
    @Column(name = "user_cd")
    private String userCd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "transfer_Dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transferDt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "Status")
    private String status;
    @Column(name = "return_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDt;

    public TBookTransfer() {
    }

    public TBookTransfer(Integer transferId) {
        this.transferId = transferId;
    }

    public TBookTransfer(Integer transferId, String sourceLib, String destLib, String accNo, Date transferDt, String status) {
        this.transferId = transferId;
        this.sourceLib = sourceLib;
        this.destLib = destLib;
        this.accNo = accNo;
        this.transferDt = transferDt;
        this.status = status;
    }

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public String getSourceLib() {
        return sourceLib;
    }

    public void setSourceLib(String sourceLib) {
        this.sourceLib = sourceLib;
    }

    public String getDestLib() {
        return destLib;
    }

    public void setDestLib(String destLib) {
        this.destLib = destLib;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public Date getTransferDt() {
        return transferDt;
    }

    public void setTransferDt(Date transferDt) {
        this.transferDt = transferDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getReturnDt() {
        return returnDt;
    }

    public void setReturnDt(Date returnDt) {
        this.returnDt = returnDt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transferId != null ? transferId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TBookTransfer)) {
            return false;
        }
        TBookTransfer other = (TBookTransfer) object;
        if ((this.transferId == null && other.transferId != null) || (this.transferId != null && !this.transferId.equals(other.transferId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.TBookTransfer[ transferId=" + transferId + " ]";
    }
    
}
