/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import soul.acquisition.suggestions.AOrdermaster;

/**
 *
 * @author soullab
 */
@Entity
@Table(name = "letter_formats")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LetterFormats.findAll", query = "SELECT l FROM LetterFormats l"),
    @NamedQuery(name = "LetterFormats.findByLetterName", query = "SELECT l FROM LetterFormats l WHERE l.letterName = :letterName"),
    @NamedQuery(name = "LetterFormats.findByLetterFullname", query = "SELECT l FROM LetterFormats l WHERE l.letterFullname = :letterFullname")})
public class LetterFormats implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "letter_name")
    private String letterName;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "letter_format")
    private String letterFormat;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "subject")
    private String subject;
    @Size(max = 300)
    @Column(name = "letter_fullname")
    private String letterFullname;

  
    
    public LetterFormats() {
    }

    public LetterFormats(String letterName) {
        this.letterName = letterName;
    }

    public String getLetterName() {
        return letterName;
    }

    public void setLetterName(String letterName) {
        this.letterName = letterName;
    }

    public String getLetterFormat() {
        return letterFormat;
    }

    public void setLetterFormat(String letterFormat) {
        this.letterFormat = letterFormat;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLetterFullname() {
        return letterFullname;
    }

    public void setLetterFullname(String letterFullname) {
        this.letterFullname = letterFullname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (letterName != null ? letterName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LetterFormats)) {
            return false;
        }
        LetterFormats other = (LetterFormats) object;
        if ((this.letterName == null && other.letterName != null) || (this.letterName != null && !this.letterName.equals(other.letterName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.general_master.LetterFormats[ letterName=" + letterName + " ]";
    }

    
}

