/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soullab
 */
@Entity
@SqlResultSetMapping(
         name = "GlobalSearchMapping",
        entities = {
            @EntityResult(
                    entityClass = BiblidetailsLocation.class,
                    fields = {
             //    @FieldResult(name = "p852", column = "p852"),
//        @FieldResult(name = "f852", column = "f852"),
//        @FieldResult(name = "k852", column = "k852"),
//        @FieldResult(name = "m852", column = "m852"),
//        @FieldResult(name = "recID", column = "RecID"),
//        @FieldResult(name = "issueRestricted", column = "IssueRestricted"),
//        @FieldResult(name = "status", column = "Status"),
//        @FieldResult(name = "statusDscr", column = "status_dscr"),
//        @FieldResult(name = "price", column = "Price"),
//        @FieldResult(name = "material", column = "Material"),
//        @FieldResult(name = "description", column = "Description"),
      //  @FieldResult(name = "title", column = "title"),
      //  @FieldResult(name = "author", column = "author"),
//        @FieldResult(name = "edition", column = "edition"),
//        @FieldResult(name = "isbn", column = "isbn"),
//        @FieldResult(name = "dateofAcq", column = "DateofAcq")
                      }),
            @EntityResult(
                    entityClass = Location.class,
                    fields = {
                   //     @FieldResult(name = "p852", column = "p852"),
                    //    @FieldResult(name = "a852", column = "a852"),
                    //    @FieldResult(name = "f852", column = "f852"),
                    //    @FieldResult(name = "k852", column = "k852")
                    })}) 

//@XmlRootElement
//@JsonPOJOBuilder
public class GlobalSearchClass implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "p852")
    @Id
    private String p852;
   // @Size(max = 50)
//    @Column(name = "f852")
//    private String f852;
//    @Size(max = 50)
//    @Column(name = "k852")
//    private String k852;
//    
//    @Size(max = 50)
//    @Column(name = "a852")
//    private String a852;
//    
//    @Size(max = 50)
//    @Column(name = "title")
//    private String title;
//    
//    @Size(max = 50)
//    @Column(name = "author")
//    private String author;
//
//    public String getP852() {
//        return p852;
//    }
//
//    public void setP852(String p852) {5890
//        this.p852 = p852;
//    }
//
//    public String getF852() {
//        return f852;
//    }
//
//    public void setF852(String f852) {
//        this.f852 = f852;
//    }
//
//    public String getK852() {
//        return k852;
//    }
//
//    public void setK852(String k852) {
//        this.k852 = k852;
//    }
//
//    public String getA852() {
//        return a852;
//    }
//
//    public void setA852(String a852) {
//        this.a852 = a852;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
    
    
  
}
