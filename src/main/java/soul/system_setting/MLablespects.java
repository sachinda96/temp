/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.system_setting;

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
@Table(name = "m_lablespects")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MLablespects.findAll", query = "SELECT m FROM MLablespects m"),
    @NamedQuery(name = "MLablespects.findByLableName", query = "SELECT m FROM MLablespects m WHERE m.lableName = :lableName"),
    @NamedQuery(name = "MLablespects.findByPageHeight", query = "SELECT m FROM MLablespects m WHERE m.pageHeight = :pageHeight"),
    @NamedQuery(name = "MLablespects.findByPageWidth", query = "SELECT m FROM MLablespects m WHERE m.pageWidth = :pageWidth"),
    @NamedQuery(name = "MLablespects.findByTopMargin", query = "SELECT m FROM MLablespects m WHERE m.topMargin = :topMargin"),
    @NamedQuery(name = "MLablespects.findByBottomMargin", query = "SELECT m FROM MLablespects m WHERE m.bottomMargin = :bottomMargin"),
    @NamedQuery(name = "MLablespects.findByRightMargin", query = "SELECT m FROM MLablespects m WHERE m.rightMargin = :rightMargin"),
    @NamedQuery(name = "MLablespects.findByLeftMargin", query = "SELECT m FROM MLablespects m WHERE m.leftMargin = :leftMargin"),
    @NamedQuery(name = "MLablespects.findByNoOfColumns", query = "SELECT m FROM MLablespects m WHERE m.noOfColumns = :noOfColumns"),
    @NamedQuery(name = "MLablespects.findByColumnSpace", query = "SELECT m FROM MLablespects m WHERE m.columnSpace = :columnSpace"),
    @NamedQuery(name = "MLablespects.findByNoOfRows", query = "SELECT m FROM MLablespects m WHERE m.noOfRows = :noOfRows"),
    @NamedQuery(name = "MLablespects.findByRowSpace", query = "SELECT m FROM MLablespects m WHERE m.rowSpace = :rowSpace")})
public class MLablespects implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "LableName")
    private String lableName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PageHeight")
    private double pageHeight;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PageWidth")
    private double pageWidth;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TopMargin")
    private double topMargin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BottomMargin")
    private double bottomMargin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RightMargin")
    private double rightMargin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LeftMargin")
    private double leftMargin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NoOfColumns")
    private int noOfColumns;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ColumnSpace")
    private double columnSpace;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NoOfRows")
    private int noOfRows;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RowSpace")
    private double rowSpace;

    public MLablespects() {
    }

    public MLablespects(String lableName) {
        this.lableName = lableName;
    }

    public MLablespects(String lableName, double pageHeight, double pageWidth, double topMargin, double bottomMargin, double rightMargin, double leftMargin, int noOfColumns, double columnSpace, int noOfRows, double rowSpace) {
        this.lableName = lableName;
        this.pageHeight = pageHeight;
        this.pageWidth = pageWidth;
        this.topMargin = topMargin;
        this.bottomMargin = bottomMargin;
        this.rightMargin = rightMargin;
        this.leftMargin = leftMargin;
        this.noOfColumns = noOfColumns;
        this.columnSpace = columnSpace;
        this.noOfRows = noOfRows;
        this.rowSpace = rowSpace;
    }

    public String getLableName() {
        return lableName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName;
    }

    public double getPageHeight() {
        return pageHeight;
    }

    public void setPageHeight(double pageHeight) {
        this.pageHeight = pageHeight;
    }

    public double getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(double pageWidth) {
        this.pageWidth = pageWidth;
    }

    public double getTopMargin() {
        return topMargin;
    }

    public void setTopMargin(double topMargin) {
        this.topMargin = topMargin;
    }

    public double getBottomMargin() {
        return bottomMargin;
    }

    public void setBottomMargin(double bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    public double getRightMargin() {
        return rightMargin;
    }

    public void setRightMargin(double rightMargin) {
        this.rightMargin = rightMargin;
    }

    public double getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(double leftMargin) {
        this.leftMargin = leftMargin;
    }

    public int getNoOfColumns() {
        return noOfColumns;
    }

    public void setNoOfColumns(int noOfColumns) {
        this.noOfColumns = noOfColumns;
    }

    public double getColumnSpace() {
        return columnSpace;
    }

    public void setColumnSpace(double columnSpace) {
        this.columnSpace = columnSpace;
    }

    public int getNoOfRows() {
        return noOfRows;
    }

    public void setNoOfRows(int noOfRows) {
        this.noOfRows = noOfRows;
    }

    public double getRowSpace() {
        return rowSpace;
    }

    public void setRowSpace(double rowSpace) {
        this.rowSpace = rowSpace;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lableName != null ? lableName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MLablespects)) {
            return false;
        }
        MLablespects other = (MLablespects) object;
        if ((this.lableName == null && other.lableName != null) || (this.lableName != null && !this.lableName.equals(other.lableName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soul.system_setting.MLablespects[ lableName=" + lableName + " ]";
    }
    
}
