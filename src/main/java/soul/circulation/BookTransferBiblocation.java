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
@Table(name = "book_transfer_biblocation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BookTransferBiblocation.findAll", query = "SELECT b FROM BookTransferBiblocation b"),
    @NamedQuery(name = "BookTransferBiblocation.findByTransferId", query = "SELECT b FROM BookTransferBiblocation b WHERE b.transferId = :transferId"),
    @NamedQuery(name = "BookTransferBiblocation.findBySourceLib", query = "SELECT b FROM BookTransferBiblocation b WHERE b.sourceLib = :sourceLib"),
    @NamedQuery(name = "BookTransferBiblocation.findBySourceLibName", query = "SELECT b FROM BookTransferBiblocation b WHERE b.sourceLibName = :sourceLibName"),
    @NamedQuery(name = "BookTransferBiblocation.findByDestLib", query = "SELECT b FROM BookTransferBiblocation b WHERE b.destLib = :destLib"),
    @NamedQuery(name = "BookTransferBiblocation.findByDestLibName", query = "SELECT b FROM BookTransferBiblocation b WHERE b.destLibName = :destLibName"),
    @NamedQuery(name = "BookTransferBiblocation.findByAccNo", query = "SELECT b FROM BookTransferBiblocation b WHERE b.accNo = :accNo"),
    @NamedQuery(name = "BookTransferBiblocation.findByUserCd", query = "SELECT b FROM BookTransferBiblocation b WHERE b.userCd = :userCd"),
    @NamedQuery(name = "BookTransferBiblocation.findByTransferDt", query = "SELECT b FROM BookTransferBiblocation b WHERE b.transferDt = :transferDt"),
    @NamedQuery(name = "BookTransferBiblocation.findByTransferStatus", query = "SELECT b FROM BookTransferBiblocation b WHERE b.transferStatus = :transferStatus"),
    @NamedQuery(name = "BookTransferBiblocation.findByReturnDt", query = "SELECT b FROM BookTransferBiblocation b WHERE b.returnDt = :returnDt"),
    @NamedQuery(name = "BookTransferBiblocation.findByP852", query = "SELECT b FROM BookTransferBiblocation b WHERE b.p852 = :p852"),
    @NamedQuery(name = "BookTransferBiblocation.findByRecID", query = "SELECT b FROM BookTransferBiblocation b WHERE b.recID = :recID")})
public class BookTransferBiblocation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "transfer_id")
    @Id
    private int transferId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "source_lib")
    private String sourceLib;
    @Size(max = 250)
    @Column(name = "source_lib_name")
    private String sourceLibName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "dest_lib")
    private String destLib;
    @Size(max = 250)
    @Column(name = "dest_lib_name")
    private String destLibName;
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
    @Column(name = "transfer_status")
    private String transferStatus;
    @Column(name = "return_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "p852")
    private String p852;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RecID")
    private int recID;
    @Lob
    @Size(max = 65535)
    @Column(name = "title")
    private String title;
    @Lob
    @Size(max = 65535)
    @Column(name = "author")
    private String author;

    public BookTransferBiblocation() {
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getSourceLib() {
        return sourceLib;
    }

    public void setSourceLib(String sourceLib) {
        this.sourceLib = sourceLib;
    }

    public String getSourceLibName() {
        return sourceLibName;
    }

    public void setSourceLibName(String sourceLibName) {
        this.sourceLibName = sourceLibName;
    }

    public String getDestLib() {
        return destLib;
    }

    public void setDestLib(String destLib) {
        this.destLib = destLib;
    }

    public String getDestLibName() {
        return destLibName;
    }

    public void setDestLibName(String destLibName) {
        this.destLibName = destLibName;
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

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public Date getReturnDt() {
        return returnDt;
    }

    public void setReturnDt(Date returnDt) {
        this.returnDt = returnDt;
    }

    public String getP852() {
        return p852;
    }

    public void setP852(String p852) {
        this.p852 = p852;
    }

    public int getRecID() {
        return recID;
    }

    public void setRecID(int recID) {
        this.recID = recID;
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
    
}
