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
 * @author admin
 */
@Entity
@Table(name = "t_damage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TDamage.findAll", query = "SELECT t FROM TDamage t")
    , @NamedQuery(name = "TDamage.findByRecordId", query = "SELECT t FROM TDamage t WHERE t.recordId = :recordId")
    , @NamedQuery(name = "TDamage.findByAccNo", query = "SELECT t FROM TDamage t WHERE t.accNo = :accNo")
    , @NamedQuery(name = "TDamage.findByDamagedDt", query = "SELECT t FROM TDamage t WHERE t.damagedDt = :damagedDt")
    , @NamedQuery(name = "TDamage.findByRepairedDt", query = "SELECT t FROM TDamage t WHERE t.repairedDt = :repairedDt")
    , @NamedQuery(name = "TDamage.findByStatus", query = "SELECT t FROM TDamage t WHERE t.status = :status")
    , @NamedQuery(name = "TDamage.findByUserCd", query = "SELECT t FROM TDamage t WHERE t.userCd = :userCd")})
public class TDamage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "record_id")
    private Integer recordId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "acc_no")
    private String accNo;
    @Column(name = "damaged_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date damagedDt;
    @Column(name = "repaired_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repairedDt;
    @Size(max = 255)
    @Column(name = "status")
    private String status;
    @Size(max = 255)
    @Column(name = "user_cd")
    private String userCd;

    public TDamage() {
    }

    public TDamage(Integer recordId) {
        this.recordId = recordId;
    }

    public TDamage(Integer recordId, String accNo) {
        this.recordId = recordId;
        this.accNo = accNo;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public Date getDamagedDt() {
        return damagedDt;
    }

    public void setDamagedDt(Date damagedDt) {
        this.damagedDt = damagedDt;
    }

    public Date getRepairedDt() {
        return repairedDt;
    }

    public void setRepairedDt(Date repairedDt) {
        this.repairedDt = repairedDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordId != null ? recordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TDamage)) {
            return false;
        }
        TDamage other = (TDamage) object;
        if ((this.recordId == null && other.recordId != null) || (this.recordId != null && !this.recordId.equals(other.recordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.newchange.TDamage[ recordId=" + recordId + " ]";
    }
    
}
