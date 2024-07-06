/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import soul.acquisition.suggestions.ARequest;
import soul.circulation_master.MCtgry;
import soul.circulation_master.MType;
import soul.general_master.MBranchmaster;
import soul.general_master.MFcltydept;
import soul.serialControl.SRequest;
import soul.user_setting.Userdetail;
/**
 *
 * @author soullab
 */
@Entity
@Table(name = "m_member")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MMember.findAll", query = "SELECT m FROM MMember m"),
    @NamedQuery(name = "MMember.findByMemCd", query = "SELECT m FROM MMember m WHERE m.memCd = :memCd"),
    @NamedQuery(name = "MMember.findByMemFirstnm", query = "SELECT m FROM MMember m WHERE m.memFirstnm Like :memFirstnm"),
    @NamedQuery(name = "MMember.findByMemMidnm", query = "SELECT m FROM MMember m WHERE m.memMidnm = :memMidnm"),
    @NamedQuery(name = "MMember.findByMemLstnm", query = "SELECT m FROM MMember m WHERE m.memLstnm = :memLstnm"),
    @NamedQuery(name = "MMember.findByMemTag", query = "SELECT m FROM MMember m WHERE m.memTag = :memTag"),
    @NamedQuery(name = "MMember.findByMemStatus", query = "SELECT m FROM MMember m WHERE m.memStatus = :memStatus"),
    @NamedQuery(name = "MMember.findByMemYear", query = "SELECT m FROM MMember m WHERE m.memYear = :memYear"),
    @NamedQuery(name = "MMember.findByMemPrmntadd1", query = "SELECT m FROM MMember m WHERE m.memPrmntadd1 = :memPrmntadd1"),
    @NamedQuery(name = "MMember.findByMemPrmntadd2", query = "SELECT m FROM MMember m WHERE m.memPrmntadd2 = :memPrmntadd2"),
    @NamedQuery(name = "MMember.findByMemPrmntcity", query = "SELECT m FROM MMember m WHERE m.memPrmntcity = :memPrmntcity"),
    @NamedQuery(name = "MMember.findByMemPrmntpin", query = "SELECT m FROM MMember m WHERE m.memPrmntpin = :memPrmntpin"),
    @NamedQuery(name = "MMember.findByMemPrmntphone", query = "SELECT m FROM MMember m WHERE m.memPrmntphone = :memPrmntphone"),
    @NamedQuery(name = "MMember.findByMemTmpadd1", query = "SELECT m FROM MMember m WHERE m.memTmpadd1 = :memTmpadd1"),
    @NamedQuery(name = "MMember.findByMemTmpadd2", query = "SELECT m FROM MMember m WHERE m.memTmpadd2 = :memTmpadd2"),
    @NamedQuery(name = "MMember.findByMemTmpcity", query = "SELECT m FROM MMember m WHERE m.memTmpcity = :memTmpcity"),
    @NamedQuery(name = "MMember.findByMemTmppin", query = "SELECT m FROM MMember m WHERE m.memTmppin = :memTmppin"),
    @NamedQuery(name = "MMember.findByMemTmpphone", query = "SELECT m FROM MMember m WHERE m.memTmpphone = :memTmpphone"),
    @NamedQuery(name = "MMember.findByMemEmail", query = "SELECT m FROM MMember m WHERE m.memEmail = :memEmail"),
    @NamedQuery(name = "MMember.findByMemEfctvDt", query = "SELECT m FROM MMember m WHERE m.memEfctvDt = :memEfctvDt"),
    @NamedQuery(name = "MMember.findByMemEntryDt", query = "SELECT m FROM MMember m WHERE m.memEntryDt = :memEntryDt"),
    @NamedQuery(name = "MMember.findByMemEffctupto", query = "SELECT m FROM MMember m WHERE m.memEffctupto = :memEffctupto"),
    @NamedQuery(name = "MMember.findByUserCd", query = "SELECT m FROM MMember m WHERE m.userCd = :userCd"),
    @NamedQuery(name = "MMember.findByMemId", query = "SELECT m FROM MMember m WHERE m.memId = :memId"),
    @NamedQuery(name = "MMember.findByRemarks", query = "SELECT m FROM MMember m WHERE m.remarks = :remarks"),
    @NamedQuery(name = "MMember.findByRcptDt", query = "SELECT m FROM MMember m WHERE m.rcptDt = :rcptDt"),
    @NamedQuery(name = "MMember.findByDateOfBirth", query = "SELECT m FROM MMember m WHERE m.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "MMember.findByNoDueTag", query = "SELECT m FROM MMember m WHERE m.noDueTag = :noDueTag"),
    @NamedQuery(name = "MMember.findByBookCtgry", query = "SELECT m FROM MMember m WHERE m.bookCtgry = :bookCtgry"),
    @NamedQuery(name = "MMember.findByCardissude", query = "SELECT m FROM MMember m WHERE m.cardissude = :cardissude"),
    @NamedQuery(name = "MMember.findByMemFineAmt", query = "SELECT m FROM MMember m WHERE m.memFineAmt = :memFineAmt"),
    @NamedQuery(name = "MMember.findByMemPassword", query = "SELECT m FROM MMember m WHERE m.memPassword = :memPassword"),
    @NamedQuery(name = "MMember.findByGrentrLastnm", query = "SELECT m FROM MMember m WHERE m.grentrLastnm = :grentrLastnm"),
    @NamedQuery(name = "MMember.findByGrentrFirstnm", query = "SELECT m FROM MMember m WHERE m.grentrFirstnm = :grentrFirstnm"),
    @NamedQuery(name = "MMember.findByGrentrAdd1", query = "SELECT m FROM MMember m WHERE m.grentrAdd1 = :grentrAdd1"),
    @NamedQuery(name = "MMember.findByGrentrCity", query = "SELECT m FROM MMember m WHERE m.grentrCity = :grentrCity"),
    @NamedQuery(name = "MMember.findByGrentrPin", query = "SELECT m FROM MMember m WHERE m.grentrPin = :grentrPin"),
    @NamedQuery(name = "MMember.findByGrentrPhone", query = "SELECT m FROM MMember m WHERE m.grentrPhone = :grentrPhone"),
    @NamedQuery(name = "MMember.findByGrentrEmail", query = "SELECT m FROM MMember m WHERE m.grentrEmail = :grentrEmail"),
    @NamedQuery(name = "MMember.findByGrentrMemId", query = "SELECT m FROM MMember m WHERE m.grentrMemId = :grentrMemId"),
    @NamedQuery(name = "MMember.findByGrentrFormNo", query = "SELECT m FROM MMember m WHERE m.grentrFormNo = :grentrFormNo"),
    @NamedQuery(name = "MMember.findByMemAutoNo", query = "SELECT m FROM MMember m WHERE m.memAutoNo = :memAutoNo"),
    @NamedQuery(name = "MMember.findByMemGraduationdt", query = "SELECT m FROM MMember m WHERE m.memGraduationdt = :memGraduationdt"),
    @NamedQuery(name = "MMember.findByMemGender", query = "SELECT m FROM MMember m WHERE m.memGender = :memGender"),
    @NamedQuery(name = "MMember.findByMembershipAmt", query = "SELECT m FROM MMember m WHERE m.membershipAmt = :membershipAmt"),
    @NamedQuery(name = "MMember.findByMemMaxdueAmt", query = "SELECT m FROM MMember m WHERE m.memMaxdueAmt = :memMaxdueAmt"),
    @NamedQuery(name = "MMember.findByMemDeposit", query = "SELECT m FROM MMember m WHERE m.memDeposit = :memDeposit"),
    @NamedQuery(name = "MMember.findByRcptNo1", query = "SELECT m FROM MMember m WHERE m.rcptNo1 = :rcptNo1"),
    @NamedQuery(name = "MMember.findByRcptNo1Dt", query = "SELECT m FROM MMember m WHERE m.rcptNo1Dt = :rcptNo1Dt"),
    @NamedQuery(name = "MMember.findByRcptNo2", query = "SELECT m FROM MMember m WHERE m.rcptNo2 = :rcptNo2"),
    @NamedQuery(name = "MMember.findByRcptNo2Dt", query = "SELECT m FROM MMember m WHERE m.rcptNo2Dt = :rcptNo2Dt"),
    
    @NamedQuery(name = "MMember.findByMemFirstnmLike", query = "SELECT m FROM MMember m WHERE m.memFirstnm Like :memFirstnm"),
    @NamedQuery(name = "MMember.findByMaxMemCd", query = "SELECT m FROM MMember m WHERE m.memCd = (SELECT MAX(m.memCd) FROM MMember m WHERE m.memCd LIKE :memcd )"),
    @NamedQuery(name = "MMember.findByMemDeptDscr", query = "SELECT m FROM MMember m WHERE m.memDept.fcltydeptdscr Like :deptDscr"),
    @NamedQuery(name = "MMember.findByMemDeptCd", query = "SELECT m FROM MMember m WHERE m.memDept.fcltydeptcd = :deptCd"),
    @NamedQuery(name = "MMember.findByMemDegreeName", query = "SELECT m FROM MMember m WHERE m.memDegree.branchname Like :branchName"),
    @NamedQuery(name = "MMember.findByMemCtgryDesc", query = "SELECT m FROM MMember m WHERE m.memCtgry.ctgryDesc Like :ctgryDesc"),
    @NamedQuery(name = "MMember.findByMemEntryDtBetween", query = "SELECT m FROM MMember m WHERE m.memEntryDt BETWEEN ?1 AND ?2"),
        
    @NamedQuery(name = "MMember.findByCourseDesignation", query = "SELECT m FROM MMember m WHERE m.memDegree.branchname = :branchname "),
    @NamedQuery(name = "MMember.findByActiveMembers", query = "SELECT m FROM MMember m WHERE m.memStatus Like :memStatus"),
    @NamedQuery(name = "MMember.findBySuspendedMembers", query = "SELECT m FROM MMember m WHERE m.memStatus Like :memStatus"),
    @NamedQuery(name = "MMember.findByInstitute", query = "SELECT m FROM MMember m WHERE m.memInst.fcltydeptdscr Like :fcltydeptdscr"),  
    @NamedQuery(name = "MMember.findByMemberCodes", query = "SELECT m FROM MMember m WHERE m.memCd IN :memCd"),
    @NamedQuery(name = "MMember.removeByMemberCode", query = "DELETE FROM MMember m WHERE m.memCd = :memCd"),
    @NamedQuery(name = "MMember.findByInstituteCode", query = "SELECT m FROM MMember m WHERE m.memInst.fcltydeptcd = :fcltydeptcd"),
    @NamedQuery(name = "MMember.findByDepartmentCode", query = "SELECT m FROM MMember m WHERE m.memDept.fcltydeptcd = :fcltydeptcd")
})
public class MMember implements Serializable {
    @OneToMany(mappedBy = "sRNm")
    private Collection<SRequest> sRequestCollection;
    @OneToMany(mappedBy = "aRNm")
    private Collection<ARequest> aRequestCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memCd")
    private Collection<IllReqprocdmp> illReqprocdmpCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mMember")
    private Collection<TOtherissue> tOtherissueCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mMember")
    private Collection<TReceive> tReceiveCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mMember")
    private Collection<TReserve> tReserveCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mMember")
    private Collection<TMemdue> tMemdueCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mMember")
    private Collection<TIssue> tIssueCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "mem_cd")
    private String memCd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mem_firstnm")
    private String memFirstnm;
    @Size(max = 100)
    @Column(name = "mem_midnm")
    private String memMidnm;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mem_lstnm")
    private String memLstnm;
    @Size(max = 6)
    @Column(name = "mem_tag")
    private String memTag;
    @Size(max = 1)
    @Column(name = "mem_status")
    private String memStatus;
    @Column(name = "mem_year")
    @Temporal(TemporalType.TIMESTAMP)
    private Date memYear;
    @Size(max = 200)
    @Column(name = "mem_prmntadd1")
    private String memPrmntadd1;
    @Size(max = 200)
    @Column(name = "mem_prmntadd2")
    private String memPrmntadd2;
    @Size(max = 100)
    @Column(name = "mem_prmntcity")
    private String memPrmntcity;
    @Size(max = 12)
    @Column(name = "mem_prmntpin")
    private String memPrmntpin;
    @Size(max = 60)
    @Column(name = "mem_prmntphone")
    private String memPrmntphone;
    @Size(max = 200)
    @Column(name = "mem_tmpadd1")
    private String memTmpadd1;
    @Size(max = 200)
    @Column(name = "mem_tmpadd2")
    private String memTmpadd2;
    @Size(max = 100)
    @Column(name = "mem_tmpcity")
    private String memTmpcity;
    @Size(max = 12)
    @Column(name = "mem_tmppin")
    private String memTmppin;
    @Size(max = 30)
    @Column(name = "mem_tmpphone")
    private String memTmpphone;
    @Size(max = 100)
    @Column(name = "mem_email")
    private String memEmail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mem_efctv_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date memEfctvDt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mem_entry_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date memEntryDt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mem_effctupto")
    @Temporal(TemporalType.TIMESTAMP)
    private Date memEffctupto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "user_cd")
    private String userCd;
    @Size(max = 20)
    @Column(name = "mem_id")
    private String memId;
    @Size(max = 250)
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "rcpt_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rcptDt;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    @Column(name = "no_due_tag")
    private String noDueTag;
    @Size(max = 2)
    @Column(name = "book_ctgry")
    private String bookCtgry;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Card_issude")
    private String cardissude;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "mem_fine_amt")
    private BigDecimal memFineAmt;
    @Size(max = 50)
    @Column(name = "mem_password")
    private String memPassword;
    @Size(max = 200)
    @Column(name = "grentr_lastnm")
    private String grentrLastnm;
    @Size(max = 200)
    @Column(name = "grentr_firstnm")
    private String grentrFirstnm;
    @Size(max = 200)
    @Column(name = "grentr_add1")
    private String grentrAdd1;
    @Size(max = 200)
    @Column(name = "grentr_city")
    private String grentrCity;
    @Size(max = 50)
    @Column(name = "grentr_pin")
    private String grentrPin;
    @Size(max = 60)
    @Column(name = "grentr_phone")
    private String grentrPhone;
    @Size(max = 100)
    @Column(name = "grentr_email")
    private String grentrEmail;
    @Size(max = 20)
    @Column(name = "grentr_mem_id")
    private String grentrMemId;
    @Size(max = 150)
    @Column(name = "grentr_form_no")
    private String grentrFormNo;
    @Column(name = "mem_auto_no")
    private Long memAutoNo;
    @Column(name = "mem_graduationdt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date memGraduationdt;
    @Size(max = 12)
    @Column(name = "mem_gender")
    private String memGender;
    @Column(name = "membership_amt")
    private BigDecimal membershipAmt;
    @Column(name = "mem_maxdue_amt")
    private BigDecimal memMaxdueAmt;
    @Column(name = "mem_deposit")
    private BigDecimal memDeposit;
    @Size(max = 15)
    @Column(name = "rcpt_no1")
    private String rcptNo1;
    @Column(name = "rcpt_no1_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rcptNo1Dt;
    @Size(max = 15)
    @Column(name = "rcpt_no2")
    private String rcptNo2;
    @Column(name = "rcpt_no2_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rcptNo2Dt;
    @JoinColumn(name = "mem_ctgry", referencedColumnName = "ctgry_cd")
    @ManyToOne(optional = false)
    private MCtgry memCtgry;
    @JoinColumn(name = "mem_degree", referencedColumnName = "branch_cd")
    @ManyToOne(optional = false)
    private MBranchmaster memDegree;
    @JoinColumn(name = "mem_dept", referencedColumnName = "Fclty_dept_cd")
    @ManyToOne(optional = false)
    private MFcltydept memDept;
    @JoinColumn(name = "mem_inst", referencedColumnName = "Fclty_dept_cd")
    @ManyToOne(optional = false)
    private MFcltydept memInst;
    @JoinColumn(name = "mem_type", referencedColumnName = "MemberType")
    @ManyToOne
    private MType memType;

    public MMember() {
    }

    public MMember(String memCd) {
        this.memCd = memCd;
    }

    public MMember(String memCd, String memFirstnm, String memLstnm, Date memEfctvDt, Date memEntryDt, Date memEffctupto, String userCd, String cardissude, BigDecimal memFineAmt) {
        this.memCd = memCd;
        this.memFirstnm = memFirstnm;
        this.memLstnm = memLstnm;
        this.memEfctvDt = memEfctvDt;
        this.memEntryDt = memEntryDt;
        this.memEffctupto = memEffctupto;
        this.userCd = userCd;
        this.cardissude = cardissude;
        this.memFineAmt = memFineAmt;
    }

    public String getMemCd() {
        return memCd;
    }

    public void setMemCd(String memCd) {
        this.memCd = memCd;
    }

    public String getMemFirstnm() {
        return memFirstnm;
    }

    public void setMemFirstnm(String memFirstnm) {
        this.memFirstnm = memFirstnm;
    }

    public String getMemMidnm() {
        return memMidnm;
    }

    public void setMemMidnm(String memMidnm) {
        this.memMidnm = memMidnm;
    }

    public String getMemLstnm() {
        return memLstnm;
    }

    public void setMemLstnm(String memLstnm) {
        this.memLstnm = memLstnm;
    }

    public String getMemTag() {
        return memTag;
    }

    public void setMemTag(String memTag) {
        this.memTag = memTag;
    }

    public String getMemStatus() {
        return memStatus;
    }

    public void setMemStatus(String memStatus) {
        this.memStatus = memStatus;
    }

    public Date getMemYear() {
        return memYear;
    }

    public void setMemYear(Date memYear) {
        this.memYear = memYear;
    }

    public String getMemPrmntadd1() {
        return memPrmntadd1;
    }

    public void setMemPrmntadd1(String memPrmntadd1) {
        this.memPrmntadd1 = memPrmntadd1;
    }

    public String getMemPrmntadd2() {
        return memPrmntadd2;
    }

    public void setMemPrmntadd2(String memPrmntadd2) {
        this.memPrmntadd2 = memPrmntadd2;
    }

    public String getMemPrmntcity() {
        return memPrmntcity;
    }

    public void setMemPrmntcity(String memPrmntcity) {
        this.memPrmntcity = memPrmntcity;
    }

    public String getMemPrmntpin() {
        return memPrmntpin;
    }

    public void setMemPrmntpin(String memPrmntpin) {
        this.memPrmntpin = memPrmntpin;
    }

    public String getMemPrmntphone() {
        return memPrmntphone;
    }

    public void setMemPrmntphone(String memPrmntphone) {
        this.memPrmntphone = memPrmntphone;
    }

    public String getMemTmpadd1() {
        return memTmpadd1;
    }

    public void setMemTmpadd1(String memTmpadd1) {
        this.memTmpadd1 = memTmpadd1;
    }

    public String getMemTmpadd2() {
        return memTmpadd2;
    }

    public void setMemTmpadd2(String memTmpadd2) {
        this.memTmpadd2 = memTmpadd2;
    }

    public String getMemTmpcity() {
        return memTmpcity;
    }

    public void setMemTmpcity(String memTmpcity) {
        this.memTmpcity = memTmpcity;
    }

    public String getMemTmppin() {
        return memTmppin;
    }

    public void setMemTmppin(String memTmppin) {
        this.memTmppin = memTmppin;
    }

    public String getMemTmpphone() {
        return memTmpphone;
    }

    public void setMemTmpphone(String memTmpphone) {
        this.memTmpphone = memTmpphone;
    }

    public String getMemEmail() {
        return memEmail;
    }

    public void setMemEmail(String memEmail) {
        this.memEmail = memEmail;
    }

    public Date getMemEfctvDt() {
        return memEfctvDt;
    }

    public void setMemEfctvDt(Date memEfctvDt) {
        this.memEfctvDt = memEfctvDt;
    }

    public Date getMemEntryDt() {
        return memEntryDt;
    }

    public void setMemEntryDt(Date memEntryDt) {
        this.memEntryDt = memEntryDt;
    }

    public Date getMemEffctupto() {
        return memEffctupto;
    }

    public void setMemEffctupto(Date memEffctupto) {
        this.memEffctupto = memEffctupto;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public String getMemId() {
        return memId;
    }

    public void setMemId(String memId) {
        this.memId = memId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getRcptDt() {
        return rcptDt;
    }

    public void setRcptDt(Date rcptDt) {
        this.rcptDt = rcptDt;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNoDueTag() {
        return noDueTag;
    }

    public void setNoDueTag(String noDueTag) {
        this.noDueTag = noDueTag;
    }

    public String getBookCtgry() {
        return bookCtgry;
    }

    public void setBookCtgry(String bookCtgry) {
        this.bookCtgry = bookCtgry;
    }

    public String getCardissude() {
        return cardissude;
    }

    public void setCardissude(String cardissude) {
        this.cardissude = cardissude;
    }

    public BigDecimal getMemFineAmt() {
        return memFineAmt;
    }

    public void setMemFineAmt(BigDecimal memFineAmt) {
        this.memFineAmt = memFineAmt;
    }

    public String getMemPassword() {
        return memPassword;
    }

    public void setMemPassword(String memPassword) {
        this.memPassword = memPassword;
    }

    public String getGrentrLastnm() {
        return grentrLastnm;
    }

    public void setGrentrLastnm(String grentrLastnm) {
        this.grentrLastnm = grentrLastnm;
    }

    public String getGrentrFirstnm() {
        return grentrFirstnm;
    }

    public void setGrentrFirstnm(String grentrFirstnm) {
        this.grentrFirstnm = grentrFirstnm;
    }

    public String getGrentrAdd1() {
        return grentrAdd1;
    }

    public void setGrentrAdd1(String grentrAdd1) {
        this.grentrAdd1 = grentrAdd1;
    }

    public String getGrentrCity() {
        return grentrCity;
    }

    public void setGrentrCity(String grentrCity) {
        this.grentrCity = grentrCity;
    }

    public String getGrentrPin() {
        return grentrPin;
    }

    public void setGrentrPin(String grentrPin) {
        this.grentrPin = grentrPin;
    }

    public String getGrentrPhone() {
        return grentrPhone;
    }

    public void setGrentrPhone(String grentrPhone) {
        this.grentrPhone = grentrPhone;
    }

    public String getGrentrEmail() {
        return grentrEmail;
    }

    public void setGrentrEmail(String grentrEmail) {
        this.grentrEmail = grentrEmail;
    }

    public String getGrentrMemId() {
        return grentrMemId;
    }

    public void setGrentrMemId(String grentrMemId) {
        this.grentrMemId = grentrMemId;
    }

    public String getGrentrFormNo() {
        return grentrFormNo;
    }

    public void setGrentrFormNo(String grentrFormNo) {
        this.grentrFormNo = grentrFormNo;
    }

    public Long getMemAutoNo() {
        return memAutoNo;
    }

    public void setMemAutoNo(Long memAutoNo) {
        this.memAutoNo = memAutoNo;
    }

    public Date getMemGraduationdt() {
        return memGraduationdt;
    }

    public void setMemGraduationdt(Date memGraduationdt) {
        this.memGraduationdt = memGraduationdt;
    }

    public String getMemGender() {
        return memGender;
    }

    public void setMemGender(String memGender) {
        this.memGender = memGender;
    }

    public BigDecimal getMembershipAmt() {
        return membershipAmt;
    }

    public void setMembershipAmt(BigDecimal membershipAmt) {
        this.membershipAmt = membershipAmt;
    }

    public BigDecimal getMemMaxdueAmt() {
        return memMaxdueAmt;
    }

    public void setMemMaxdueAmt(BigDecimal memMaxdueAmt) {
        this.memMaxdueAmt = memMaxdueAmt;
    }

    public BigDecimal getMemDeposit() {
        return memDeposit;
    }

    public void setMemDeposit(BigDecimal memDeposit) {
        this.memDeposit = memDeposit;
    }

    public String getRcptNo1() {
        return rcptNo1;
    }

    public void setRcptNo1(String rcptNo1) {
        this.rcptNo1 = rcptNo1;
    }

    public Date getRcptNo1Dt() {
        return rcptNo1Dt;
    }

    public void setRcptNo1Dt(Date rcptNo1Dt) {
        this.rcptNo1Dt = rcptNo1Dt;
    }

    public String getRcptNo2() {
        return rcptNo2;
    }

    public void setRcptNo2(String rcptNo2) {
        this.rcptNo2 = rcptNo2;
    }

    public Date getRcptNo2Dt() {
        return rcptNo2Dt;
    }

    public void setRcptNo2Dt(Date rcptNo2Dt) {
        this.rcptNo2Dt = rcptNo2Dt;
    }

    public MCtgry getMemCtgry() {
        return memCtgry;
    }

    public void setMemCtgry(MCtgry memCtgry) {
        this.memCtgry = memCtgry;
    }

    public MBranchmaster getMemDegree() {
        return memDegree;
    }

    public void setMemDegree(MBranchmaster memDegree) {
        this.memDegree = memDegree;
    }

    public MFcltydept getMemDept() {
        return memDept;
    }

    public void setMemDept(MFcltydept memDept) {
        this.memDept = memDept;
    }

    public MFcltydept getMemInst() {
        return memInst;
    }

    public void setMemInst(MFcltydept memInst) {
        this.memInst = memInst;
    }

    public MType getMemType() {
        return memType;
    }

    public void setMemType(MType memType) {
        this.memType = memType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memCd != null ? memCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MMember)) {
            return false;
        }
        MMember other = (MMember) object;
        if ((this.memCd == null && other.memCd != null) || (this.memCd != null && !this.memCd.equals(other.memCd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation.MMember[ memCd=" + memCd + " ]";
    }

    public String getIssues() {
        return "delete from t_issue";
    }

    @XmlTransient
    public Collection<TMemdue> getTMemdueCollection() {
        return tMemdueCollection;
    }

    public void setTMemdueCollection(Collection<TMemdue> tMemdueCollection) {
        this.tMemdueCollection = tMemdueCollection;
    }

    @XmlTransient
    public Collection<TIssue> getTIssueCollection() {
        return tIssueCollection;
    }

    public void setTIssueCollection(Collection<TIssue> tIssueCollection) {
        this.tIssueCollection = tIssueCollection;
    }

    @XmlTransient
    public Collection<TReserve> getTReserveCollection() {
        return tReserveCollection;
    }

    public void setTReserveCollection(Collection<TReserve> tReserveCollection) {
        this.tReserveCollection = tReserveCollection;
    }

    @XmlTransient
    public Collection<TReceive> getTReceiveCollection() {
        return tReceiveCollection;
    }

    public void setTReceiveCollection(Collection<TReceive> tReceiveCollection) {
        this.tReceiveCollection = tReceiveCollection;
    }

    @XmlTransient
    public Collection<TOtherissue> getTOtherissueCollection() {
        return tOtherissueCollection;
    }

    public void setTOtherissueCollection(Collection<TOtherissue> tOtherissueCollection) {
        this.tOtherissueCollection = tOtherissueCollection;
    }

    @XmlTransient
    public Collection<IllReqprocdmp> getIllReqprocdmpCollection() {
        return illReqprocdmpCollection;
    }

    public void setIllReqprocdmpCollection(Collection<IllReqprocdmp> illReqprocdmpCollection) {
        this.illReqprocdmpCollection = illReqprocdmpCollection;
    }

    @XmlTransient
    public Collection<ARequest> getARequestCollection() {
        return aRequestCollection;
    }

    public void setARequestCollection(Collection<ARequest> aRequestCollection) {
        this.aRequestCollection = aRequestCollection;
    }

    @XmlTransient
    public Collection<SRequest> getSRequestCollection() {
        return sRequestCollection;
    }

    public void setSRequestCollection(Collection<SRequest> sRequestCollection) {
        this.sRequestCollection = sRequestCollection;
    }
}
