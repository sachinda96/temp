/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author INFLIBNET
 */
@Entity
@XmlRootElement
public class TitleDetails implements Serializable{
    @Column(name = "accNo")
    @Id
    private String accNo;
    @Column(name = "Title")
    private String Title;
    @Column(name = "Member_code")
    private String MemberCode;
    @Column(name = "Member_firstName")
    private String MemberFirstName;
    @Column(name = "Member_lastName")
    private String MemberLastName;
    @Column(name = "issue_dt")
    private String IssueDt;
    @Column(name = "Due_dt")
    private String DueDt;
    @Column(name = "Department")
    private String Department;
    @Column(name = "BranchName")
    private String BranchName;
    @Column(name = "Member_address")
    private String MemberAddress;
    @Column(name = "Member_pin")
    private String MemberPin;
    @Column(name = "Member_city")
    private String MemberCity;
    @Column(name = "Member_phone")
    private String MemberPhone;
    @Column(name = "lib_cd")
    private String LibCd;
    @Column(name = "rqst_dt")
    private String RqstDt;
    @Column(name = "send_dt")
    private String SendDt;
    @Column(name = "rqst_ref")
    private String RqstRef;
    @Column(name = "lib_nm")
    private String LibNm;
    @Column(name = "lib_add1")
    private String LibAdd1;
    @Column(name = "lib_city")
    private String LibCity;
    @Column(name = "lib_pin")
    private String LibPin;
    @Column(name = "lib_phone")
    private String LibPhone;


    public TitleDetails() {
        
    }

    public TitleDetails(String accNo, String Title) {
        this.accNo = accNo;
        this.Title = Title;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getMemberCode() {
        return MemberCode;
    }

    public void setMemberCode(String MemberCode) {
        this.MemberCode = MemberCode;
    }

    public String getMemberFirstName() {
        return MemberFirstName;
    }

    public void setMemberFirstName(String MemberFirstName) {
        this.MemberFirstName = MemberFirstName;
    }

    public String getMemberLastName() {
        return MemberLastName;
    }

    public void setMemberLastName(String MemberLastName) {
        this.MemberLastName = MemberLastName;
    }

    public String getIssue_dt() {
        return IssueDt;
    }

    public void setIssue_dt(String Issue_dt) {
        this.IssueDt = Issue_dt;
    }

    public String getDueDt() {
        return DueDt;
    }

    public void setDueDt(String DueDt) {
        this.DueDt = DueDt;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String BranchName) {
        this.BranchName = BranchName;
    }

    public String getMemberAddress() {
        return MemberAddress;
    }

    public void setMemberAddress(String MemberAddress) {
        this.MemberAddress = MemberAddress;
    }

    public String getMemberPin() {
        return MemberPin;
    }

    public void setMemberPin(String MemberPin) {
        this.MemberPin = MemberPin;
    }

    public String getMemberCity() {
        return MemberCity;
    }

    public void setMemberCity(String MemberCity) {
        this.MemberCity = MemberCity;
    }

    public String getMemberPhone() {
        return MemberPhone;
    }

    public void setMemberPhone(String MemberPhone) {
        this.MemberPhone = MemberPhone;
    }

    public String getLibCd() {
        return LibCd;
    }

    public void setLibCd(String LibCd) {
        this.LibCd = LibCd;
    }

    public String getRqstDt() {
        return RqstDt;
    }

    public void setRqstDt(String RqstDt) {
        this.RqstDt = RqstDt;
    }

    public String getSendDt() {
        return SendDt;
    }

    public void setSendDt(String SendDt) {
        this.SendDt = SendDt;
    }

    public String getRqstRef() {
        return RqstRef;
    }

    public void setRqstRef(String RqstRef) {
        this.RqstRef = RqstRef;
    }

    public String getLibNm() {
        return LibNm;
    }

    public void setLibNm(String LibNm) {
        this.LibNm = LibNm;
    }

    public String getLibAdd1() {
        return LibAdd1;
    }

    public void setLibAdd1(String LibAdd1) {
        this.LibAdd1 = LibAdd1;
    }

    public String getLibCity() {
        return LibCity;
    }

    public void setLibCity(String LibCity) {
        this.LibCity = LibCity;
    }

    public String getLibPin() {
        return LibPin;
    }

    public void setLibPin(String LibPin) {
        this.LibPin = LibPin;
    }

    public String getLibPhone() {
        return LibPhone;
    }

    public void setLibPhone(String LibPhone) {
        this.LibPhone = LibPhone;
    }
    
  
   
}
