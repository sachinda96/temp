///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package soul.catalogue.service;
//
//
//import ar.com.fdvs.dj.core.DynamicJasperHelper;
// import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
// import ar.com.fdvs.dj.core.layout.LayoutManager;
// import ar.com.fdvs.dj.domain.DynamicReport;
//import ar.com.fdvs.dj.test.ReportExporter;
//import ar.com.fdvs.dj.test.TestRepositoryProducts;
// import ar.com.fdvs.dj.util.SortUtils;
//import java.io.ByteArrayInputStream;
//import java.io.UnsupportedEncodingException;
// //import junit.framework.TestCase;
// import net.sf.jasperreports.engine.JRDataSource;
// import net.sf.jasperreports.engine.JasperFillManager;
// import net.sf.jasperreports.engine.JasperPrint;
// import net.sf.jasperreports.engine.JasperReport;
// import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
// import org.apache.commons.logging.Log;
//  import org.apache.commons.logging.LogFactory;
//  
//import java.sql.Connection;
//import java.sql.DriverManager;
// import java.util.*;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.data.JRXmlDataSource;
///**
// *
// * @author INFLIBNET
// */
//public abstract class BaseDjReportTest extends TestCase {
// 
//  	public Map getParams() {
//		return params;
// 	}
// 
// 	protected static final Log log = LogFactory.getLog(BaseDjReportTest.class);
// 
// 	 public JasperPrint jp;
//  	public JasperReport jr;
// 	protected Map params = new HashMap();
// 	protected DynamicReport dr;
// 
// 	public abstract DynamicReport buildReport() throws Exception;
// 
//  	public void testReport() throws Exception {
//			dr = buildReport(); 			/**
//70  			 * Get a JRDataSource implementation
//71  			 */
//			JRDataSource ds = getDataSource();
// 			/**
//76  			 * Creates the JasperReport object, we pass as a Parameter
//77  			 * the DynamicReport, a new ClassicLayoutManager instance (this
//78  			 * one does the magic) and the JRDataSource
//79  			 */
// 			jr = DynamicJasperHelper.generateJasperReport(dr, getLayoutManager(), params);
// 
//  			/**
//83  			 * Creates the JasperPrint object, we pass as a Parameter
//84  			 * the JasperReport object, and the JRDataSource
//85  			 */
//  			log.debug("Filling the report");
//  			if (ds != null)
//  				jp = JasperFillManager.fillReport(jr, params, ds);
//  			else
//  				jp = JasperFillManager.fillReport(jr, params);
//  
//  			log.debug("Filling done!");
//  			log.debug("Exporting the report (pdf, xls, etc)");
//              exportReport();
//  
//              log.debug("test finished");
//  
//  	}
// 
// 	protected LayoutManager getLayoutManager() {
//		return new ClassicLayoutManager();
// 	}
// public JasperPrint JasperVariable() throws Exception {
//  		
// 		return jp;
//  	}
//	protected void exportReport() throws Exception {
// 		ReportExporter.exportReport(jp, System.getProperty("user.dir")+ "/target/reports/" + this.getClass().getName() + ".pdf");
// 		exportToJRXML();
// 	}
//	
//	protected void exportToJRXML() throws Exception {
// 		if (this.jr != null){
//			DynamicJasperHelper.generateJRXML(this.jr, "UTF-8",System.getProperty("user.dir")+ "/target/reports/" + this.getClass().getName() + ".jrxml");
// 			
//		} else {
//			DynamicJasperHelper.generateJRXML(this.dr, this.getLayoutManager(), this.params, "UTF-8",System.getProperty("user.dir")+ "/target/reports/" + this.getClass().getName() + ".jrxml");
//		}
//	}	
//
// 	protected void exportToHTML() throws Exception {
//		ReportExporter.exportReportHtml(this.jp, System.getProperty("user.dir")+ "/target/reports/" + this.getClass().getName() + ".html");
//	}	
//
//	/**
//123 	 * @return JRDataSource
//124 	 */
// 	protected JRDataSource getDataSource() throws JRException, UnsupportedEncodingException {
//		Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
// 		dummyCollection = SortUtils.sortCollection(dummyCollection,dr.getColumns());
// 
//		//JRDataSource ds = new JRBeanCollectionDataSource(dummyCollection);		//Create a JRDataSource, the Collection used
//			String result = "<root><data><child>someData1</child><child>someData2</child><child>someData3</child></data></root>";
//  	       
//  JRXmlDataSource ds = new JRXmlDataSource(new ByteArrayInputStream(result.getBytes("UTF-16")), "/root/data/child");
// 																	//here contains dummy hardcoded objects...
// 		return ds;
//	}
//
// 	public Collection getDummyCollectionSorted(List columnlist) {
//		Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
//		return SortUtils.sortCollection(dummyCollection,columnlist);
//
// 	}
//
//	public DynamicReport getDynamicReport() {
//		return dr;
//	}
// 
//	/**
//145 	 * Uses a non blocking HSQL DB. Also uses HSQL default test data
//146 	 * @return a Connection
//147 	 * @throws Exception
//148 	 */
// 	public static Connection createSQLConnection() throws Exception {
// 		Connection con = null;
//		    Class.forName("com.mysql.jdbc.Driver");
//		con = DriverManager.getConnection("jdbc:mysql://172.16.16.66:3306/soul", "root","");
//
//         return con;
//	}
//
//     public int getYear(){
//         return Calendar.getInstance().get(Calendar.YEAR);
//   }
//     
//     
// }