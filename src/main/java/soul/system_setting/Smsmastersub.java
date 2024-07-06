/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.system_setting;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author admin
 */
@Entity
@Table(name = "smsmastersub")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Smsmastersub.findAll", query = "SELECT s FROM Smsmastersub s")
    , @NamedQuery(name = "Smsmastersub.findByMid", query = "SELECT s FROM Smsmastersub s WHERE s.mid = :mid")
    , @NamedQuery(name = "Smsmastersub.findBySrno", query = "SELECT s FROM Smsmastersub s WHERE s.srno = :srno")
    , @NamedQuery(name = "Smsmastersub.findByMsgType", query = "SELECT s FROM Smsmastersub s WHERE s.msgType = :msgType")})
public class Smsmastersub implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mid")
    private int mid;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "srno")
    private Integer srno;
    @Size(max = 10)
    @Column(name = "msgType")
    private String msgType;
    @Lob
    @Size(max = 16777215)
    @Column(name = "messagetext")
    private String messagetext;

    public Smsmastersub() {
    }

    public Smsmastersub(Integer srno) {
        this.srno = srno;
    }

    public Smsmastersub(Integer srno, int mid) {
        this.srno = srno;
        this.mid = mid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public Integer getSrno() {
        return srno;
    }

    public void setSrno(Integer srno) {
        this.srno = srno;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMessagetext() {
        return messagetext;
    }

    public void setMessagetext(String messagetext) {
        this.messagetext = messagetext;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (srno != null ? srno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Smsmastersub)) {
            return false;
        }
        Smsmastersub other = (Smsmastersub) object;
        if ((this.srno == null && other.srno != null) || (this.srno != null && !this.srno.equals(other.srno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.system_setting.Smsmastersub[ srno=" + srno + " ]";
    }
    
}
