/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation_master;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "m_calender")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MCalender.findAll", query = "SELECT m FROM MCalender m"),
    @NamedQuery(name = "MCalender.findByFestivalDt", query = "SELECT m FROM MCalender m WHERE m.festivalDt = :festivalDt"),
    @NamedQuery(name = "MCalender.findByFestivalNm", query = "SELECT m FROM MCalender m WHERE m.festivalNm = :festivalNm")})
public class MCalender implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "festival_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date festivalDt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "festival_nm")
    private String festivalNm;

    public MCalender() {
    }

    public MCalender(Date festivalDt) {
        this.festivalDt = festivalDt;
    }

    public MCalender(Date festivalDt, String festivalNm) {
        this.festivalDt = festivalDt;
        this.festivalNm = festivalNm;
    }

    public Date getFestivalDt() {
        return festivalDt;
    }

    public void setFestivalDt(Date festivalDt) {
        this.festivalDt = festivalDt;
    }

    public String getFestivalNm() {
        return festivalNm;
    }

    public void setFestivalNm(String festivalNm) {
        this.festivalNm = festivalNm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (festivalDt != null ? festivalDt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MCalender)) {
            return false;
        }
        MCalender other = (MCalender) object;
        if ((this.festivalDt == null && other.festivalDt != null) || (this.festivalDt != null && !this.festivalDt.equals(other.festivalDt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.circulation_master.MCalender[ festivalDt=" + festivalDt + " ]";
    }
    
}
