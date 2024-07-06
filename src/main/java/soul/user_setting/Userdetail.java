/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.user_setting;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "userdetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userdetail.findAll", query = "SELECT u FROM Userdetail u"),
    @NamedQuery(name = "Userdetail.findByUserId", query = "SELECT u FROM Userdetail u WHERE u.userId = :userId"),
    @NamedQuery(name = "Userdetail.findByUPass", query = "SELECT u FROM Userdetail u WHERE u.uPass = :uPass"),
    @NamedQuery(name = "Userdetail.findByUName", query = "SELECT u FROM Userdetail u WHERE u.uName = :uName"),
    @NamedQuery(name = "Userdetail.findByAddr", query = "SELECT u FROM Userdetail u WHERE u.addr = :addr"),
    @NamedQuery(name = "Userdetail.findByCity", query = "SELECT u FROM Userdetail u WHERE u.city = :city"),
    @NamedQuery(name = "Userdetail.findByPin", query = "SELECT u FROM Userdetail u WHERE u.pin = :pin"),
    @NamedQuery(name = "Userdetail.findByState", query = "SELECT u FROM Userdetail u WHERE u.state = :state"),
    @NamedQuery(name = "Userdetail.findByCountry", query = "SELECT u FROM Userdetail u WHERE u.country = :country"),
    @NamedQuery(name = "Userdetail.findByPhone", query = "SELECT u FROM Userdetail u WHERE u.phone = :phone"),
    @NamedQuery(name = "Userdetail.findByExtention", query = "SELECT u FROM Userdetail u WHERE u.extention = :extention"),
    @NamedQuery(name = "Userdetail.findByMobile", query = "SELECT u FROM Userdetail u WHERE u.mobile = :mobile"),
    @NamedQuery(name = "Userdetail.findByEmail", query = "SELECT u FROM Userdetail u WHERE u.email = :email"),
    @NamedQuery(name = "Userdetail.findByComment", query = "SELECT u FROM Userdetail u WHERE u.comment = :comment"),
    @NamedQuery(name = "Userdetail.findByQuestion", query = "SELECT u FROM Userdetail u WHERE u.question = :question"),
    @NamedQuery(name = "Userdetail.findByAnswer", query = "SELECT u FROM Userdetail u WHERE u.answer = :answer"),
    @NamedQuery(name = "Userdetail.findByLibID", query = "SELECT u FROM Userdetail u WHERE u.libID = :libID")})
public class Userdetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UserId")
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "U_Pass")
    private String uPass;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 510)
    @Column(name = "U_Name")
    private String uName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 510)
    @Column(name = "Addr")
    private String addr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "City")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "Pin")
    private String pin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "State")
    private String state;
    @Size(max = 200)
    @Column(name = "Country")
    private String country;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "Phone")
    private String phone;
    @Size(max = 100)
    @Column(name = "Extention")
    private String extention;
    @Size(max = 100)
    @Column(name = "Mobile")
    private String mobile;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 500)
    @Column(name = "Email")
    private String email;
    @Size(max = 500)
    @Column(name = "Comment")
    private String comment;
    @Size(max = 400)
    @Column(name = "Question")
    private String question;
    @Size(max = 400)
    @Column(name = "Answer")
    private String answer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "LibID")
    private String libID;
    @JoinColumn(name = "GroupID", referencedColumnName = "GroupId")
    @ManyToOne(optional = false)
    private Sgroup groupID;

    public Userdetail() {
    }

    public Userdetail(Integer userId) {
        this.userId = userId;
    }

    public Userdetail(Integer userId, String uPass, String uName, String addr, String city, String pin, String state, String libID) {
        this.userId = userId;
        this.uPass = uPass;
        this.uName = uName;
        this.addr = addr;
        this.city = city;
        this.pin = pin;
        this.state = state;
        this.libID = libID;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUPass() {
        return uPass;
    }

    public void setUPass(String uPass) {
        this.uPass = uPass;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getLibID() {
        return libID;
    }

    public void setLibID(String libID) {
        this.libID = libID;
    }

    public Sgroup getGroupID() {
        return groupID;
    }

    public void setGroupID(Sgroup groupID) {
        this.groupID = groupID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userdetail)) {
            return false;
        }
        Userdetail other = (Userdetail) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.user_setting.Userdetail[ userId=" + userId + " ]";
    }
    
}
