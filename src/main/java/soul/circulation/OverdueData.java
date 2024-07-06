/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@Entity
@XmlRootElement
public class OverdueData {
    
    @Id
    @Column(name="slipNo")
    private String slipno;
    @Column(name="slipDate")
    private Date slipdate;
    @Column(name="lastRecpNo")
    private String lastrcptno;
    @Column(name="lastRecpDate")
    private Date lastrecptDate;
    @Column(name="reason")
    private String reason;
    @Column(name="accno")
    private String accno;
    @Column(name="dueamt")
    private BigDecimal rcptamt;
    @Column(name="totalfine")
    private BigDecimal fineamt;
    @Column(name="memname")
    private String memname;
    
     public OverdueData(){}

    public OverdueData(String slipno, Date slipdate, String lastrcptno, Date lastrecptDate, String reason, String accno, BigDecimal rcptamt, BigDecimal fineamt, String memname) {
        this.slipno = slipno;
        this.slipdate = slipdate;
        this.lastrcptno = lastrcptno;
        this.lastrecptDate = lastrecptDate;
        this.reason = reason;
        this.accno = accno;
        this.rcptamt = rcptamt;
        this.fineamt = fineamt;
        this.memname = memname;
    }

    public String getMemname() {
        return memname;
    }

    public void setMemname(String memname) {
        this.memname = memname;
    }

    

    public String getSlipno() {
        return slipno;
    }

    public void setSlipno(String slipno) {
        this.slipno = slipno;
    }

    public Date getSlipdate() {
        return slipdate;
    }

    public void setSlipdate(Date slipdate) {
        this.slipdate = slipdate;
    }

    public String getLastrcptno() {
        return lastrcptno;
    }

    public void setLastrcptno(String lastrcptno) {
        this.lastrcptno = lastrcptno;
    }

    public Date getLastrecptDate() {
        return lastrecptDate;
    }

    public void setLastrecptDate(Date lastrecptDate) {
        this.lastrecptDate = lastrecptDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public BigDecimal getRcptamt() {
        return rcptamt;
    }

    public void setRcptamt(BigDecimal rcptamt) {
        this.rcptamt = rcptamt;
    }

    public BigDecimal getFineamt() {
        return fineamt;
    }

    public void setFineamt(BigDecimal fineamt) {
        this.fineamt = fineamt;
    }
   
    
    
}
