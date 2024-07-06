/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "ctgry_media_issue_reserve")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CtgryMediaIssueReserve.findAll", query = "SELECT c FROM CtgryMediaIssueReserve c"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByCtgryCd", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.ctgryMediaIssueReservePK.ctgryCd = :ctgryCd"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByMediaCd", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.ctgryMediaIssueReservePK.mediaCd = :mediaCd"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByIssPeriod", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.issPeriod = :issPeriod"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByMaxAllowed", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.maxAllowed = :maxAllowed"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByFineCharges", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.fineCharges = :fineCharges"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByResPeriod", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.resPeriod = :resPeriod"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByMaxReserve", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.maxReserve = :maxReserve"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByFinePhase1Prd", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.finePhase1Prd = :finePhase1Prd"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByFinePhase2Prd", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.finePhase2Prd = :finePhase2Prd"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByFinePhase1Charge", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.finePhase1Charge = :finePhase1Charge"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByFinePhase2Charge", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.finePhase2Charge = :finePhase2Charge"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByMemCd", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.memCd = :memCd"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByMemCtgry", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.memCtgry = :memCtgry"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByCtgryDesc", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.ctgryDesc = :ctgryDesc"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByMaterial", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.material = :material"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByIssCount", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.issCount = :issCount"),
    @NamedQuery(name = "CtgryMediaIssueReserve.findByResCount", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.resCount = :resCount"),
    
    @NamedQuery(name = "CtgryMediaIssueReserve.findByCtgryCdAndMediaCd", query = "SELECT c FROM CtgryMediaIssueReserve c WHERE c.ctgryMediaIssueReservePK.ctgryCd = ?1 AND c.ctgryMediaIssueReservePK.mediaCd = ?2"),
})
public class CtgryMediaIssueReserve implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CtgryMediaIssueReservePK ctgryMediaIssueReservePK;
    @Size(max = 200)
    @Column(name = "Description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iss_period")
    private int issPeriod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_allowed")
    private int maxAllowed;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "fine_charges")
    private BigDecimal fineCharges;
    @Basic(optional = false)
    @NotNull
    @Column(name = "res_period")
    private int resPeriod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_reserve")
    private int maxReserve;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fine_phase_1_prd")
    private int finePhase1Prd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fine_phase_2_prd")
    private int finePhase2Prd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fine_phase_1_charge")
    private BigDecimal finePhase1Charge;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fine_phase_2_charge")
    private BigDecimal finePhase2Charge;
    @Size(max = 12)
    @Column(name = "mem_cd")
    private String memCd;
    @Size(max = 2)
    @Column(name = "mem_ctgry")
    private String memCtgry;
    @Size(max = 50)
    @Column(name = "ctgry_desc")
    private String ctgryDesc;
    @Size(max = 3)
    @Column(name = "Material")
    private String material;
    @Column(name = "issCount")
    private BigInteger issCount;
    @Column(name = "resCount")
    private BigInteger resCount;

    public CtgryMediaIssueReserve() {
    }

    public CtgryMediaIssueReserve(CtgryMediaIssueReservePK ctgryMediaIssueReservePK) {
        this.ctgryMediaIssueReservePK = ctgryMediaIssueReservePK;
    }

    public CtgryMediaIssueReserve(CtgryMediaIssueReservePK ctgryMediaIssueReservePK, int issPeriod, int maxAllowed, BigDecimal fineCharges, int resPeriod, int maxReserve, int finePhase1Prd, int finePhase2Prd, BigDecimal finePhase1Charge, BigDecimal finePhase2Charge) {
        this.ctgryMediaIssueReservePK = ctgryMediaIssueReservePK;
        this.issPeriod = issPeriod;
        this.maxAllowed = maxAllowed;
        this.fineCharges = fineCharges;
        this.resPeriod = resPeriod;
        this.maxReserve = maxReserve;
        this.finePhase1Prd = finePhase1Prd;
        this.finePhase2Prd = finePhase2Prd;
        this.finePhase1Charge = finePhase1Charge;
        this.finePhase2Charge = finePhase2Charge;
    }

    public CtgryMediaIssueReserve(String ctgryCd, String mediaCd) {
        this.ctgryMediaIssueReservePK = new CtgryMediaIssueReservePK(ctgryCd, mediaCd);
    }

    public CtgryMediaIssueReservePK getCtgryMediaIssueReturnPK() {
        return ctgryMediaIssueReservePK;
    }
    
    public CtgryMediaIssueReservePK getCtgryMediaIssueReservePK() {
        return ctgryMediaIssueReservePK;
    }

    public void setCtgryMediaIssueReservePK(CtgryMediaIssueReservePK ctgryMediaIssueReservePK) {
        this.ctgryMediaIssueReservePK = ctgryMediaIssueReservePK;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIssPeriod() {
        return issPeriod;
    }

    public void setIssPeriod(int issPeriod) {
        this.issPeriod = issPeriod;
    }

    public int getMaxAllowed() {
        return maxAllowed;
    }

    public void setMaxAllowed(int maxAllowed) {
        this.maxAllowed = maxAllowed;
    }

    public BigDecimal getFineCharges() {
        return fineCharges;
    }

    public void setFineCharges(BigDecimal fineCharges) {
        this.fineCharges = fineCharges;
    }

    public int getResPeriod() {
        return resPeriod;
    }

    public void setResPeriod(int resPeriod) {
        this.resPeriod = resPeriod;
    }

    public int getMaxReserve() {
        return maxReserve;
    }

    public void setMaxReserve(int maxReserve) {
        this.maxReserve = maxReserve;
    }

    public int getFinePhase1Prd() {
        return finePhase1Prd;
    }

    public void setFinePhase1Prd(int finePhase1Prd) {
        this.finePhase1Prd = finePhase1Prd;
    }

    public int getFinePhase2Prd() {
        return finePhase2Prd;
    }

    public void setFinePhase2Prd(int finePhase2Prd) {
        this.finePhase2Prd = finePhase2Prd;
    }

    public BigDecimal getFinePhase1Charge() {
        return finePhase1Charge;
    }

    public void setFinePhase1Charge(BigDecimal finePhase1Charge) {
        this.finePhase1Charge = finePhase1Charge;
    }

    public BigDecimal getFinePhase2Charge() {
        return finePhase2Charge;
    }

    public void setFinePhase2Charge(BigDecimal finePhase2Charge) {
        this.finePhase2Charge = finePhase2Charge;
    }

    public String getMemCd() {
        return memCd;
    }

    public void setMemCd(String memCd) {
        this.memCd = memCd;
    }

    public String getMemCtgry() {
        return memCtgry;
    }

    public void setMemCtgry(String memCtgry) {
        this.memCtgry = memCtgry;
    }

    public String getCtgryDesc() {
        return ctgryDesc;
    }

    public void setCtgryDesc(String ctgryDesc) {
        this.ctgryDesc = ctgryDesc;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public BigInteger getIssCount() {
        return issCount;
    }

    public void setIssCount(BigInteger issCount) {
        this.issCount = issCount;
    }

    public BigInteger getResCount() {
        return resCount;
    }

    public void setResCount(BigInteger resCount) {
        this.resCount = resCount;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.ctgryMediaIssueReservePK);
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
        final CtgryMediaIssueReserve other = (CtgryMediaIssueReserve) obj;
        if (!Objects.equals(this.ctgryMediaIssueReservePK, other.ctgryMediaIssueReservePK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CtgryMediaIssueReserve[" + "ctgryMediaIssueReservePK=" + ctgryMediaIssueReservePK + ']';
    }
    
    
    
}
