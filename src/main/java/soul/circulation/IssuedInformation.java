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
@Table(name = "issued_information")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IssuedInformation.findAll", query = "SELECT i FROM IssuedInformation i"),
    @NamedQuery(name = "IssuedInformation.findByMemCd", query = "SELECT i FROM IssuedInformation i WHERE i.memCd = :memCd"),
    @NamedQuery(name = "IssuedInformation.findByAccNo", query = "SELECT i FROM IssuedInformation i WHERE i.accNo = :accNo"),
    @NamedQuery(name = "IssuedInformation.findByIssDt", query = "SELECT i FROM IssuedInformation i WHERE i.issDt = :issDt"),
    @NamedQuery(name = "IssuedInformation.findByUserCd", query = "SELECT i FROM IssuedInformation i WHERE i.userCd = :userCd"),
    @NamedQuery(name = "IssuedInformation.findByDueDt", query = "SELECT i FROM IssuedInformation i WHERE i.dueDt = :dueDt"),
    @NamedQuery(name = "IssuedInformation.findByRecID", query = "SELECT i FROM IssuedInformation i WHERE i.recID = :recID"),
    @NamedQuery(name = "IssuedInformation.findByK852", query = "SELECT i FROM IssuedInformation i WHERE i.k852 = :k852"),
    @NamedQuery(name = "IssuedInformation.findByM852", query = "SELECT i FROM IssuedInformation i WHERE i.m852 = :m852"),
    @NamedQuery(name = "IssuedInformation.findByStatus", query = "SELECT i FROM IssuedInformation i WHERE i.status = :status"),
    @NamedQuery(name = "IssuedInformation.findByStatusDscr", query = "SELECT i FROM IssuedInformation i WHERE i.statusDscr = :statusDscr"),
    @NamedQuery(name = "IssuedInformation.findByMemFirstnm", query = "SELECT i FROM IssuedInformation i WHERE i.memFirstnm = :memFirstnm"),
    @NamedQuery(name = "IssuedInformation.findByMemLstnm", query = "SELECT i FROM IssuedInformation i WHERE i.memLstnm = :memLstnm"),
    @NamedQuery(name = "IssuedInformation.findByMemPrmntadd1", query = "SELECT i FROM IssuedInformation i WHERE i.memPrmntadd1 = :memPrmntadd1"),
    @NamedQuery(name = "IssuedInformation.findByMemPrmntcity", query = "SELECT i FROM IssuedInformation i WHERE i.memPrmntcity = :memPrmntcity"),
    @NamedQuery(name = "IssuedInformation.findByMemPrmntpin", query = "SELECT i FROM IssuedInformation i WHERE i.memPrmntpin = :memPrmntpin"),
    @NamedQuery(name = "IssuedInformation.findByMemPrmntphone", query = "SELECT i FROM IssuedInformation i WHERE i.memPrmntphone = :memPrmntphone"),
    @NamedQuery(name = "IssuedInformation.findByMemDept", query = "SELECT i FROM IssuedInformation i WHERE i.memDept = :memDept"),
    @NamedQuery(name = "IssuedInformation.findByFcltydeptdscr", query = "SELECT i FROM IssuedInformation i WHERE i.fcltydeptdscr = :fcltydeptdscr"),
    @NamedQuery(name = "IssuedInformation.findByMemDegree", query = "SELECT i FROM IssuedInformation i WHERE i.memDegree = :memDegree"),
    @NamedQuery(name = "IssuedInformation.findByBranchname", query = "SELECT i FROM IssuedInformation i WHERE i.branchname = :branchname"),
    @NamedQuery(name = "IssuedInformation.findByMemCtgry", query = "SELECT i FROM IssuedInformation i WHERE i.memCtgry = :memCtgry"),
    @NamedQuery(name = "IssuedInformation.findByCtgryDesc", query = "SELECT i FROM IssuedInformation i WHERE i.ctgryDesc = :ctgryDesc"),
    
    @NamedQuery(name = "IssuedInformation.findByTitle", query = "SELECT i FROM IssuedInformation i WHERE i.title Like :title")
})
public class IssuedInformation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "mem_cd")
    private String memCd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "acc_no")
    @Id
    private String accNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iss_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issDt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "user_cd")
    private String userCd;
    @Column(name = "due_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDt;
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
    @Size(max = 50)
    @Column(name = "k852")
    private String k852;
    @Size(max = 50)
    @Column(name = "m852")
    private String m852;
    @Size(max = 50)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "status_dscr")
    private String statusDscr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mem_firstnm")
    private String memFirstnm;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mem_lstnm")
    private String memLstnm;
    @Size(max = 200)
    @Column(name = "mem_prmntadd1")
    private String memPrmntadd1;
    @Size(max = 100)
    @Column(name = "mem_prmntcity")
    private String memPrmntcity;
    @Size(max = 12)
    @Column(name = "mem_prmntpin")
    private String memPrmntpin;
    @Size(max = 60)
    @Column(name = "mem_prmntphone")
    private String memPrmntphone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mem_dept")
    private int memDept;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "Fclty_dept_dscr")
    private String fcltydeptdscr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mem_degree")
    private int memDegree;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Branch_name")
    private String branchname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "mem_ctgry")
    private String memCtgry;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ctgry_desc")
    private String ctgryDesc;

    public IssuedInformation() {
    }

    public String getMemCd() {
        return memCd;
    }

    public void setMemCd(String memCd) {
        this.memCd = memCd;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public Date getIssDt() {
        return issDt;
    }

    public void setIssDt(Date issDt) {
        this.issDt = issDt;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public Date getDueDt() {
        return dueDt;
    }

    public void setDueDt(Date dueDt) {
        this.dueDt = dueDt;
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

    public String getK852() {
        return k852;
    }

    public void setK852(String k852) {
        this.k852 = k852;
    }

    public String getM852() {
        return m852;
    }

    public void setM852(String m852) {
        this.m852 = m852;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDscr() {
        return statusDscr;
    }

    public void setStatusDscr(String statusDscr) {
        this.statusDscr = statusDscr;
    }

    public String getMemFirstnm() {
        return memFirstnm;
    }

    public void setMemFirstnm(String memFirstnm) {
        this.memFirstnm = memFirstnm;
    }

    public String getMemLstnm() {
        return memLstnm;
    }

    public void setMemLstnm(String memLstnm) {
        this.memLstnm = memLstnm;
    }

    public String getMemPrmntadd1() {
        return memPrmntadd1;
    }

    public void setMemPrmntadd1(String memPrmntadd1) {
        this.memPrmntadd1 = memPrmntadd1;
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

    public int getMemDept() {
        return memDept;
    }

    public void setMemDept(int memDept) {
        this.memDept = memDept;
    }

    public String getFcltydeptdscr() {
        return fcltydeptdscr;
    }

    public void setFcltydeptdscr(String fcltydeptdscr) {
        this.fcltydeptdscr = fcltydeptdscr;
    }

    public int getMemDegree() {
        return memDegree;
    }

    public void setMemDegree(int memDegree) {
        this.memDegree = memDegree;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
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
    
}
