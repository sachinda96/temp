/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation;

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
public class GroupmemberData {
    
    @Id
    @Column(name="srno")
    private int srno;
    @Column(name="groupid")
    private int groupid;
    @Column(name="groupname")
    private String groupname;
    @Column(name="memcd")
    private String memcode;
    @Column(name="memname")
    private String memname;

    public GroupmemberData() {
    }

    public GroupmemberData(int srno, int groupid, String groupname, String memcode, String memname) {
        this.srno = srno;
        this.groupid = groupid;
        this.groupname = groupname;
        this.memcode = memcode;
        this.memname = memname;
    }

    public int getSrno() {
        return srno;
    }

    public void setSrno(int srno) {
        this.srno = srno;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getMemcode() {
        return memcode;
    }

    public void setMemcode(String memcode) {
        this.memcode = memcode;
    }

    public String getMemname() {
        return memname;
    }

    public void setMemname(String memname) {
        this.memname = memname;
    }
    
    
    
}
