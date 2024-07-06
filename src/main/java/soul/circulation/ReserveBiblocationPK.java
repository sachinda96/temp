/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import java.util.Objects;
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
public class ReserveBiblocationPK implements Serializable{
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "mem_cd")
    private String memCd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "record_no")
    private long recordNo;
    
    public ReserveBiblocationPK() {
    }

    public ReserveBiblocationPK(String memCd, long recordNo) {
        this.memCd = memCd;
        this.recordNo = recordNo;
    }

    public String getMemCd() {
        return memCd;
    }

    public void setMemCd(String memCd) {
        this.memCd = memCd;
    }

    public long getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(long recordNo) {
        this.recordNo = recordNo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.memCd);
        hash = 47 * hash + (int) (this.recordNo ^ (this.recordNo >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReserveBiblocationPK other = (ReserveBiblocationPK) obj;
        if (!Objects.equals(this.memCd, other.memCd)) {
            return false;
        }
        if (this.recordNo != other.recordNo) {
            return false;
        }
        return true;
    }
    
    
    
    @Override
    public String toString() {
        return "soul.circulation.ReserveBiblocationPK[" + "memCd=" + memCd + ", recordNo=" + recordNo + ']';
    }
}
