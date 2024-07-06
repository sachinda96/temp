/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation_master;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "m_weekoffday")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MWeekoffday.findAll", query = "SELECT m FROM MWeekoffday m"),
    @NamedQuery(name = "MWeekoffday.findByWeekOffDay", query = "SELECT m FROM MWeekoffday m WHERE m.weekOffDay = :weekOffDay")})
public class MWeekoffday implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "WeekOffDay")
    private String weekOffDay;

    public MWeekoffday() {
    }

    public MWeekoffday(String weekOffDay) {
        this.weekOffDay = weekOffDay;
    }

    public String getWeekOffDay() {
        return weekOffDay;
    }

    public void setWeekOffDay(String weekOffDay) {
        this.weekOffDay = weekOffDay;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weekOffDay != null ? weekOffDay.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MWeekoffday)) {
            return false;
        }
        MWeekoffday other = (MWeekoffday) object;
        if ((this.weekOffDay == null && other.weekOffDay != null) || (this.weekOffDay != null && !this.weekOffDay.equals(other.weekOffDay))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation_master.MWeekoffday[ weekOffDay=" + weekOffDay + " ]";
    }
    
}
