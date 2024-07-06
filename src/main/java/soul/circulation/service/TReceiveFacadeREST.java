/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.PathSegment;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import soul.circulation.TReceive;
import soul.circulation.TReceivePK;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.treceive")
public class TReceiveFacadeREST extends AbstractFacade<TReceive> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    private TReceivePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;memCd=memCdValue;accnNo=accnNoValue;issDt=issDtValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.circulation.TReceivePK key = new soul.circulation.TReceivePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> memCd = map.get("memCd");
        if (memCd != null && !memCd.isEmpty()) {
            key.setMemCd(memCd.get(0));
        }
        java.util.List<String> accnNo = map.get("accnNo");
        if (accnNo != null && !accnNo.isEmpty()) {
            key.setAccnNo(accnNo.get(0));
        }
        java.util.List<String> issDt = map.get("issDt");
        if (issDt != null && !issDt.isEmpty()) {
            key.setIssDt(new java.util.Date(issDt.get(0)));
        }
        return key;
    }

    public TReceiveFacadeREST() {
        super(TReceive.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TReceive entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TReceive entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.circulation.TReceivePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public TReceive find(@PathParam("id") PathSegment id) {
        soul.circulation.TReceivePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TReceive> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TReceive> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
 
   @GET
   @Path("iiop/{searchParameter}/{param}")
   @Produces({"application/xml", "application/json"})
   public List<TReceive>  iiop(@PathParam("searchParameter") String code, @PathParam("param") String param)
   {    
       List<Object> valueList = new ArrayList<>();
       
        switch (code) {
           case "memCd":
               valueList.add(param);
               code = "findByMemCd";
               break;  
           case "accNo":
               valueList.add(param);
               code = "findByAccnNo";
               break;
       }
           List<TReceive> details = super.findBy("TReceive."+code, valueList);  
           return details;
   }
   
   
  
   
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    //Item Returned Over A Period
    //This method will return the list of items which are returned over a period
    @GET
    @Path("ItemReturnedOverAPeriod/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public HashMap<String, ArrayList<String>> ItemReturnedOverAPeriod(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
            case "byUserCode":
                q = "select t_receive.accn_no, m_Member.mem_cd, m_member.mem_firstnm,m_member.mem_lstnm, Biblidetails.FValue, DATE_FORMAT(t_receive.iss_dt,'%d-%m-%Y'), DATE_FORMAT(t_receive.recv_dt,'%d-%m-%Y') from t_receive \n"
                        + " right outer join m_member on t_receive.mem_cd = m_member.mem_cd\n"
                        + " join Location on t_receive.accn_no = Location.p852\n"
                        + " join Biblidetails on Location.RecID = Biblidetails.RecID\n"
                        + " where t_receive.recv_user_cd = '" + Paramvalue + "' and Biblidetails.Tag = '245' and Biblidetails.SbFld = 'a'";
                break;
                
            case "byReceiveDateBetween":
                q = "select t_receive.accn_no, m_Member.mem_cd, m_member.mem_firstnm,m_member.mem_lstnm, Biblidetails.FValue, DATE_FORMAT(t_receive.iss_dt,'%d-%m-%Y'), DATE_FORMAT(t_receive.recv_dt,'%d-%m-%Y') \n"
                        + " from t_receive join m_member on m_member.mem_cd = t_receive.mem_cd \n" 
			+ " join Location on t_receive.accn_no = Location.p852 \n"
			+ " join Biblidetails on Location.RecID = Biblidetails.RecID "
                        + " where t_receive.iss_dt BETWEEN '" + valueString[0] + "' AND '" + valueString[1] + "' \n"
			+ " and (Location.p852 IS NOT NULL) AND (Biblidetails.Tag = '245') AND (Biblidetails.SbFld = 'a') ";
                break;
        }
        //List<Object> result;
        
        HashMap<String, String> response = new HashMap<String, String>();
        HashMap<String, ArrayList<String>> multiMap = new HashMap<String, ArrayList<String>>();
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        ArrayList<String> code = new ArrayList<String>();
        ArrayList<String> fname = new ArrayList<String>();
        ArrayList<String> lname = new ArrayList<String>();
        ArrayList<String> acc = new ArrayList<String>();
        ArrayList<String> title = new ArrayList<String>();
        ArrayList<String> iss = new ArrayList<String>();
        ArrayList<String> recv = new ArrayList<String>();
        Object[] rows = (Object[]) result.get(0);
        System.out.println("0:"+rows[0].toString());
        System.out.println("1:"+rows[1].toString());
        System.out.println("2:"+rows[2].toString());
        System.out.println("3:"+rows[3].toString());
        System.out.println("4:"+rows[4].toString());
        System.out.println("5:"+rows[5].toString());
        System.out.println("6:"+rows[6].toString());
        for (int i = 0; i < result.size(); i++) {
            Object[] row = (Object[]) result.get(i);
            acc.add(row[0].toString());
            code.add(row[1].toString());
            fname.add(row[2].toString());
            lname.add(row[3].toString());
            title.add(row[4].toString());
            iss.add(row[5].toString());
            recv.add(row[6].toString());
        }

        multiMap.put("Member Code", code);
        multiMap.put("First Name", fname);
        multiMap.put("Last Name", lname);
        multiMap.put("Accession No", acc);
        multiMap.put("Title", title);
        multiMap.put("Issue Date", iss);
        multiMap.put("Receive Date", recv);
        
        //Iterate over multimap
//        Set<Entry<String, ArrayList<String>>> setMap = multiMap.entrySet();
//        Iterator<Entry<String, ArrayList<String>>> iteratorMap = setMap.iterator();
//        while (iteratorMap.hasNext()) {
//            Map.Entry<String, ArrayList<String>> entry
//                    = (Map.Entry<String, ArrayList<String>>) iteratorMap.next();
//            String key = entry.getKey();
//            List<String> values = entry.getValue();
//            System.out.println("Key = '" + key + "' has values: " + values);
//        }

        return multiMap;
    }
    
    @GET
    @Path("ItemReturnedOverAPeriod/{paramValue}")
    public String membershipReportByTemplateName(@PathParam("templateId") String templateId, @PathParam("paramName") String paramName,
            @PathParam("paramValue") String paramValue) throws ParseException, JRException, ClassNotFoundException, SQLException, FileNotFoundException, FileNotFoundException {
        
        System.out.println("DA");
        Connection con = null;
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("DA3");
        Map<String, Object> params;
        params = new HashMap<>();
        con = DriverManager.getConnection("jdbc:mysql://172.16.16.66:3306/soul?zeroDateTimeBehavior=convertToNull", "root", "");
        System.out.println("a");
        HashMap<String, ArrayList<String>> reportData = ItemReturnedOverAPeriod("byUserCode",paramValue);
        System.out.println(reportData.get("Member Code"));
        params.put("Member Code", reportData.get("Member Code"));
        params.put("First Name", reportData.get("First Name"));
        params.put("Accession No", reportData.get("Accession No"));
        params.put("Title", reportData.get("Title"));
        params.put("Issue Date", reportData.get("Issue Date"));
        params.put("Receive Date", reportData.get("Receive Date"));
        System.out.println("d");
        System.out.println("params"+params);
        JasperReport jr = JasperCompileManager.compileReport("F:/SRA29012018/SoulRestApp/web/Report/iss-ret-report.jrxml");
        JasperPrint jp = JasperFillManager.fillReport(jr, params, con);
        System.out.println("e");
        
        JasperExportManager.exportReportToPdfFile(jp, System.getProperty("user.home") +"/Desktop/sample_report4.pdf");
        System.out.println("f");
        //JasperViewer viewer = new JasperViewer(jp, false);
        System.out.println("g");
        //viewer.setVisible(true);
        System.out.println("h");
        return "Success...";
    }
}
