/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue.service;

//import ExceptionService.DataException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamWriter;
import org.apache.commons.lang3.StringUtils;
import soul.catalogue.Location;
import soul.catalogue.LocationPK;
import soul.catalogue.Biblidetails;
import soul.catalogue.BiblidetailsLocation;
import soul.catalogue.Bookdetails;
import soul.catalogue.TitleDetails;
import soul.circulation.BiblocationIssueReserve;
import soul.circulation.TIssue;
import soul.circulation.TIssuePK;
import soul.circulation.TReplace;
import soul.circulation.TReserve;
import soul.circulation.service.BiblocationIssueReserveFacadeREST;
import soul.circulation.service.TIssueFacadeREST;
import soul.circulation.service.TReserveFacadeREST;
import soul.errorresponce.ValidationErrorlistfields;
import soul.errorresponse.service.ValidationException;
import soul.general_master.Libmaterials;
import soul.general_master.MFcltydept;
import soul.response.StringData;
import soul.user_setting.Userdetail;
import soul.user_setting.service.UserdetailFacadeREST;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.location")
public class LocationFacadeREST extends AbstractFacade<Location> {

    @EJB
    private BiblidetailsFacadeREST biblidetailsFacadeREST;
    @EJB
    private BiblocationIssueReserveFacadeREST biblocationIssueReserveFacadeREST;
    @EJB
    private MBkstatusFacadeREST mBkstatusFacadeREST;
    @EJB
    private TIssueFacadeREST tIssueFacadeREST;
    @EJB
    private TReserveFacadeREST tReserveFacadeREST;
    @EJB
    private BiblidetailsLocationFacadeREST biblidetailsLocationFacadeREST;
    @EJB
    private UserdetailFacadeREST userdetailFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    Biblidetails bibliObj = new Biblidetails();
    List<Location> locationList;
    String output;
    private String[] accNo;
    Location location;
    List<TIssue> issue;
    TReserve reserve;
   
    String status, accessionNo;

    private LocationPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;recID=recIDValue;p852=p852Value'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.catalogue.LocationPK key = new soul.catalogue.LocationPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> recID = map.get("recID");
        if (recID != null && !recID.isEmpty()) {
            key.setRecID(new java.lang.Integer(recID.get(0)));
        }
        java.util.List<String> p852 = map.get("p852");
        if (p852 != null && !p852.isEmpty()) {
            key.setP852(p852.get(0));
        }
        return key;
    }

    public LocationFacadeREST() {
        super(Location.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Location entity) {
        super.create(entity);
    }

    @POST
    @Path("createLocation")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    //  @Produces("text/plain")
    public void createLocation(@FormParam("recId") String recId, @FormParam("copyNo") String copyNo, @FormParam("LocInd1Val") String LocInd1Val,
            @FormParam("LocInd2Val") String LocInd2Val, @FormParam("accNo") String accNo,
            @FormParam("collection") String collection, @FormParam("codedLocation") String codedLocation,
            @FormParam("department") String department, @FormParam("supplier") String supplier,
            @FormParam("material") String material, @FormParam("classNo") String classNo,
            @FormParam("location") String locationdata, @FormParam("budget") String budget,
            @FormParam("invoiceNo") String invoiceNo, @FormParam("statusType") String statusType,
            @FormParam("bookNo") String bookNo, @FormParam("shelvingLocation") String shelvingLocation,
            @FormParam("currency") String currency, @FormParam("currencyRate") String price,
            @FormParam("invoiceDate") String invoiceDate, @FormParam("dateOfAcquisition") String dateOfAcquisition,
            @FormParam("issueRestricted") String issueRestricted) throws ParseException {
        String copyNoData[] = copyNo.split(",", -1);
        String LocInd1ValData[] = LocInd1Val.split(",", -1);
        String LocInd2ValData[] = LocInd2Val.split(",", -1);
        String accNoData[] = accNo.split(",", -1);
        String collectionData[] = collection.split(",", -1);
        String codedLocationData[] = codedLocation.split(",", -1);
        String departmentData[] = department.split(",", -1);
        String supplierData[] = supplier.split(",", -1);
        String materialData[] = material.split(",", -1);
        String classNoData[] = classNo.split(",", -1);
        String locationdataData[] = locationdata.split(",", -1);
        String budgetData[] = budget.split(",", -1);
        String invoiceNoData[] = invoiceNo.split(",", -1);
        String statusTypeData[] = statusType.split(",", -1);
        String bookNoData[] = bookNo.split(",", -1);
        String shelvingLocationData[] = shelvingLocation.split(",", -1);
        String currencyData[] = currency.split(",", -1);
        String priceData[] = price.split(",", -1);
        String invoiceDateData[] = invoiceDate.split(",", -1);
        String dateOfAcquisitionData[] = dateOfAcquisition.split(",", -1);
        String issueRestrictedData[] = issueRestricted.split(",", -1);
        // List<Biblidetails> countRecId = biblidetailsFacadeREST.findBy("findByMaxRecID", "NULL");
        // System.out.println("countRecId " + countRecId);
        // int nextRecId = (countRecId.get(0).getBiblidetailsPK().getRecID());
        output = null;
        for (int i = 0; i < copyNoData.length; i++) {
            location = new Location();
            LocationPK locationPk = new LocationPK();
            MFcltydept mFcltydept = new MFcltydept();
            Libmaterials libmaterial = new Libmaterials();
            locationPk.setRecID(Integer.parseInt(recId));
            locationPk.setP852(accNoData[i]);
            location.setLocationPK(locationPk);
            location.setT852(Integer.parseInt(copyNoData[i]));
            location.setInd(LocInd1ValData[i] + "" + LocInd2ValData[i]);
            if (!collectionData[i].contentEquals("select") && !collectionData[i].contentEquals("")) {
                location.setB852(collectionData[i]);
            }
            if (!codedLocationData[i].contentEquals("select") && !codedLocationData[i].contentEquals("")) {
                location.setF852(codedLocationData[i]);
            }

            if (!departmentData[i].contentEquals("select") && !departmentData[i].contentEquals("")) {
                String dept = String.valueOf(departmentData[i]);
                mFcltydept.setFcltydeptcd(dept);
                location.setDepartment(mFcltydept);
            }
            if (!supplierData[i].contentEquals("select") && !supplierData[i].contentEquals("")) {
                location.setSupplier(supplierData[i]);
            }
            if (!materialData[i].contentEquals("select") && !materialData[i].contentEquals("")) {
                libmaterial.setCode(materialData[i]);
                location.setMaterial(libmaterial);
            }
            location.setK852(classNoData[i]);
            location.setA852(locationdataData[i]);
            location.setBudget(budgetData[i]);
            location.setInvoiceNo(invoiceNoData[i]);
            if (!statusTypeData[i].contentEquals("select") && !statusTypeData[i].contentEquals("")) {
                location.setStatus(statusTypeData[i]);
            }
            location.setM852(bookNoData[i]);
            location.setC852(shelvingLocationData[i]);
            if (!currencyData[i].contentEquals("select") && !currencyData[i].contentEquals("")) {
                location.setCurrency(currencyData[i]);
            }
            if (!priceData[i].contentEquals("")) {
                location.setPrice(priceData[i]);
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (!invoiceDateData[i].contentEquals("")) {
                Date InvDate = formatter.parse(invoiceDateData[i]);
                System.out.println("date " + new java.sql.Date(InvDate.getTime()));
                location.setInvoiceDate(new java.sql.Date(InvDate.getTime()));
            }
            if (!dateOfAcquisitionData[i].contentEquals("")) {
                Date AcqDate = formatter.parse(dateOfAcquisitionData[i]);
                location.setDateofAcq(new java.sql.Date(AcqDate.getTime()));
            }

            location.setIssueRestricted(issueRestrictedData[i]);

            char charIssueRestricted;
//                    if(issueRestrictedData[i].contentEquals("Y"))
//                    {
//                         charIssueRestricted = 'Y';
//                    }
//                    else
//                    {
//                         charIssueRestricted = 'N';
//                    }
            // location.setIssueRestricted(Character.getNumericValue(charIssueRestricted));
            Date lastUpdatedDate = new Date();
            location.setLastOperatedDT(new java.sql.Date(lastUpdatedDate.getTime()));
            location.setUserName("superuser");
            int count = Integer.parseInt(countREST());
            super.create(location);
            if (Integer.parseInt(countREST()) == count) {
               // throw new DataException("error!!");
            }
        }
        //    return output;
    }

    @PUT
    @Path("update")
    //  @Override
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public void update(@FormParam("currentRecId") String currentRecId, @FormParam("copyNo") String copyNo, @FormParam("LocInd1Val") String LocInd1Val,
            @FormParam("LocInd2Val") String LocInd2Val, @FormParam("accNo") String accNo,
            @FormParam("collection") String collection, @FormParam("codedLocation") String codedLocation,
            @FormParam("department") String department, @FormParam("supplier") String supplier,
            @FormParam("material") String material, @FormParam("classNo") String classNo,
            @FormParam("location") String locationdata, @FormParam("budget") String budget,
            @FormParam("invoiceNo") String invoiceNo, @FormParam("statusType") String statusType,
            @FormParam("bookNo") String bookNo, @FormParam("shelvingLocation") String shelvingLocation,
            @FormParam("currency") String currency, @FormParam("currencyRate") String currencyRate,
            @FormParam("invoiceDate") String invoiceDate, @FormParam("dateOfAcquisition") String dateOfAcquisition,
            @FormParam("issueRestricted") String issueRestricted) throws ParseException {
        try {
            int currentRecIdInt = Integer.parseInt(currentRecId);
            String copyNoData[] = copyNo.split(",", -1);
            String LocInd1ValData[] = LocInd1Val.split(",", -1);
            String LocInd2ValData[] = LocInd2Val.split(",", -1);
            String accNoData[] = accNo.split(",", -1);
            String collectionData[] = collection.split(",", -1);
            String codedLocationData[] = codedLocation.split(",", -1);
            String departmentData[] = department.split(",", -1);
            String supplierData[] = supplier.split(",", -1);
            String materialData[] = material.split(",", -1);
            String classNoData[] = classNo.split(",", -1);
            String locationdataData[] = locationdata.split(",", -1);
            String budgetData[] = budget.split(",", -1);
            String invoiceNoData[] = invoiceNo.split(",", -1);
            String statusTypeData[] = statusType.split(",", -1);
            String bookNoData[] = bookNo.split(",", -1);
            String shelvingLocationData[] = shelvingLocation.split(",", -1);
            String currencyData[] = currency.split(",", -1);
            String currencyRateData[] = currencyRate.split(",", -1);
            String invoiceDateData[] = invoiceDate.split(",", -1);
            String dateOfAcquisitionData[] = dateOfAcquisition.split(",", -1);
            String issueRestrictedData[] = issueRestricted.split(",", -1);
            // remove previous data
            // removeBy("deleteByRecID", currentRecId);
            String output = null;

            for (int i = 0; i < copyNoData.length; i++) {
                Location location = new Location();
                LocationPK locationPk = new LocationPK();
                MFcltydept mFcltydept = new MFcltydept();
                Libmaterials libmaterial = new Libmaterials();
                locationPk.setRecID(currentRecIdInt);
                locationPk.setP852(accNoData[i]);
                location.setLocationPK(locationPk);
                location.setT852(Integer.parseInt(copyNoData[i]));
                location.setInd(LocInd1ValData[i] + "" + LocInd2ValData[i]);
                if (!collectionData[i].contentEquals("select")) {
                    location.setB852(collectionData[i]);
                }
                if (!codedLocationData[i].contentEquals("select")) {
                    location.setF852(codedLocationData[i]);
                }
                if (!departmentData[i].contentEquals("select")) {
                    String dept = String.valueOf(departmentData[i]);
                    mFcltydept.setFcltydeptcd(dept);
                    location.setDepartment(mFcltydept);
                }
                if (!supplierData[i].contentEquals("select")) {
                    location.setSupplier(supplierData[i]);
                }
                if (!materialData[i].contentEquals("select")) {
                    libmaterial.setCode(materialData[i]);
                    location.setMaterial(libmaterial);
                }
                location.setK852(classNoData[i]);
                location.setA852(locationdataData[i]);
                location.setBudget(budgetData[i]);
                location.setInvoiceNo(invoiceNoData[i]);
                if (!statusTypeData[i].contentEquals("select")) {
                    location.setStatus(statusTypeData[i]);
                }
                location.setM852(bookNoData[i]);
                location.setC852(shelvingLocationData[i]);
                if (!currencyData[i].contentEquals("select")) {
                    location.setCurrency(currencyData[i]);
                    location.setPrice(currencyRateData[i]);
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date InvDate = formatter.parse(invoiceDateData[i]);
                System.out.println("daaaaaaaaaaaate " + new java.sql.Date(InvDate.getTime()));
                location.setInvoiceDate(new java.sql.Date(InvDate.getTime()));
                Date AcqDate = formatter.parse(dateOfAcquisitionData[i]);
                location.setDateofAcq(new java.sql.Date(AcqDate.getTime()));
                location.setIssueRestricted(issueRestrictedData[i]);
                char charIssueRestricted;
//                    if(issueRestrictedData[i].contentEquals("Y"))
//                    {
//                         charIssueRestricted = 'Y';
//                    }
//                    else
//                    {
//                         charIssueRestricted = 'N';
//                    }
                // location.setIssueRestricted(Character.getNumericValue(charIssueRestricted));
                Date lastUpdatedDate = new Date();
                location.setLastOperatedDT(new java.sql.Date(lastUpdatedDate.getTime()));
                location.setUserName("superuser");
                int count = Integer.parseInt(countREST());
                super.edit(location);

            }
        } catch (Exception e) {
           // throw new DataException("error");
        }
    }

    @GET
    @Path("checkAccessionNoAlreadyExist/{accNo}")
    public String checkAccessionNoAlreadyExist(@PathParam("accNo") String accNo) {
        List<Location> l = getByAcc(accNo);
        if (l.size() > 0) {
            return "yes";
        }

        return "no";
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.catalogue.LocationPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Location find(@PathParam("id") PathSegment id) {
        soul.catalogue.LocationPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Path("getByAcc/{id}")
    @Produces({"application/xml", "application/json"})
    public List<Location> getByAcc(@PathParam("id") String id) {
      //  System.out.println("............... " + findBy("findByP852", id).size());
        return findBy("findByP852", id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Location> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Location> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Added Manually
    @GET
    @Path("count/by/{namedQuery}/{values}")
    @Produces("text/plain")
    public String countBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();

        switch (query) {
            case "countByAccNumRecId":
                valueList.add(Integer.parseInt(valueString[0]));
                valueList.add(valueString[1]);
                break;
            default:
                valueList.add(valueString[0]);
            //used for countRecId
        }
        return String.valueOf(super.countBy("Location." + query, valueList));
    }

    @GET
    @Path("byQuery/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<Location> findByQuery(@PathParam("namedQuery") String namedQuery, @PathParam("values") String values) {
        String[] valueSting = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch (namedQuery) {
            /*case "forWithdrawBook": q = "SELECT Location.p852, m_bkstatus.status_dscr,  Biblidetails.FValue AS Expr1, \n"
                                    + "Biblidetails_1.FValue,  LibMaterials.Description\n"
                                    + "FROM  Biblidetails INNER JOIN\n"
                                    + "Location ON Biblidetails.RecID = Location.RecID INNER JOIN\n"
                                    + "Biblidetails AS Biblidetails_1 ON Location.RecID = Biblidetails_1.RecID INNER JOIN\n"
                                    + "m_bkstatus ON Location.Status = m_bkstatus.bk_issue_stat INNER JOIN\n"
                                    + "LibMaterials ON Location.Material = LibMaterials.Code\n"
                                    + "where Biblidetails.SbFld='a' and Biblidetails.Tag='245' and \n"
                                    + "Biblidetails_1.SbFld='a' and Biblidetails_1.Tag='100' and Location.p852='" + values + "' COLLATE latin1_general_cs";
                                    break;
                
            /*case "forReIntroduce":  q = "SELECT Location.p852, m_bkstatus.status_dscr, Biblidetails.FValue\n"
                                    + "FROM  Biblidetails INNER JOIN\n"
                                    + "Location ON Biblidetails.RecID = Location.RecID INNER JOIN\n"
                                    + "m_bkstatus ON Location.Status = m_bkstatus.bk_issue_stat\n"
                                    + "where Biblidetails.SbFld='a' and Biblidetails.Tag='245'\n"
                                    + "and Location.Status='WI'";    
                                    break;
            
            /*case "forDamage":       q = "SELECT Location.p852,  Biblidetails.FValue AS Expr1,m_bkstatus.status_dscr, \n"
                                    + "Biblidetails_1.FValue\n"
                                    + "FROM  Biblidetails INNER JOIN\n"
                                    + "Location ON Biblidetails.RecID = Location.RecID INNER JOIN\n"
                                    + "Biblidetails AS Biblidetails_1 ON Location.RecID = Biblidetails_1.RecID INNER JOIN\n"
                                    + "m_bkstatus ON Location.Status = m_bkstatus.bk_issue_stat\n"
                                    + "where Biblidetails.SbFld='a' and Biblidetails.Tag='245' and \n"
                                    + "Biblidetails_1.SbFld='a' and Biblidetails_1.Tag='100' and Location.Status='DA'";  
                                    break;    */
 /*case "forSearchByTitle":    q = "SELECT Location.p852,Biblidetails.FValue, t_issue.mem_cd, \n"
                                        + "m_member.mem_firstnm, m_member.mem_lstnm,\n"
                                        + "m_fcltydept.Fclty_dept_dscr, m_branchmaster.Branch_name,  \n"
                                        + "m_member.mem_prmntcity, m_member.mem_prmntpin, \n"
                                        + "m_member.mem_prmntadd1, m_member.mem_prmntphone, t_issue.iss_dt, t_issue.due_dt\n"
                                        + "FROM  Biblidetails INNER JOIN\n"
                                        + "Location ON Biblidetails.RecID = Location.RecID INNER JOIN\n"
                                        + "t_issue ON Location.p852 = t_issue.acc_no INNER JOIN\n"
                                        + "m_member ON t_issue.mem_cd = m_member.mem_cd INNER JOIN\n"
                                        + "m_fcltydept ON m_member.mem_dept = m_fcltydept.Fclty_dept_cd INNER JOIN\n"
                                        + "m_branchmaster ON m_member.mem_degree = m_branchmaster.branch_cd\n"
                                        + "where Biblidetails.Tag='245' and Biblidetails.SbFld='a' \n"
                                        + "and Biblidetails.FValue='"+values+"'";    
                                        break;
            /*case "forGroupIR":          q = "SELECT DISTINCT Location.p852, Location.IssueRestricted, m_bkstatus.status_dscr, Biblidetails_1.FValue, \n"
                                        + "Biblidetails.FValue AS Expr1, LibMaterials.Description\n"
                                        + "FROM  Location INNER JOIN\n"
                                        + "m_bkstatus ON Location.Status = m_bkstatus.bk_issue_stat INNER JOIN\n"
                                        + "Biblidetails AS Biblidetails_1 ON Location.RecID = Biblidetails_1.RecID INNER JOIN\n"
                                        + "Biblidetails ON Location.RecID = Biblidetails.RecID INNER JOIN\n"
                                        + "LibMaterials ON Location.Material = LibMaterials.Code where Biblidetails.Tag='245' \n"
                                        + "and Biblidetails.sbFld='a' and Biblidetails_1.Tag='100' \n"
                                        + "and Biblidetails_1.sbFld='a' and "
                                        + "Location.p852 = '" + values + "'";     
                                        //Called from GroupIR.java(Servlet)*/
 /*case "forBookTransfer":     q = "SELECT DISTINCT Location.p852, Location.Status, \n"
                                        + "LibMaterials.Description, Biblidetails.FValue, Biblidetails_1.FValue AS Expr1\n"
                                        + "FROM  Location INNER JOIN \n"
                                        + "Biblidetails ON Location.RecID = Biblidetails.RecID INNER JOIN \n"
                                        + "Biblidetails AS Biblidetails_1 ON Location.RecID = Biblidetails_1.RecID INNER JOIN \n"
                                        + "LibMaterials ON Location.Material = LibMaterials.Code where Biblidetails.Tag='245' \n"
                                        + "and Biblidetails.sbFld='a' and Biblidetails_1.Tag='100' \n"
                                        + "and Biblidetails_1.sbFld='a' and Location.p852 = '" + values + "'";
                                        break;*/

            case "findByRecordID":
                valueList.add(valueSting[0]);
                break;
        }
        return super.findBy("Location." + namedQuery, valueList);
    }

    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<Location> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueSting = values.split(",");
        List<Object> valueList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        switch (query) {
            case "findByMaxRecID":  //used by AccessioningServlet
                return super.findBy("Location." + query, valueList).subList(0, 1);
            case "findByRecIDMaxDate":
                valueList.add(Integer.parseInt("1"));
                break;
            case "findByMaxT852":
                valueList.add(Integer.parseInt("1"));
                break;

            case "findByP852":
                valueList.add(valueSting[0]);
                break;

            case "findByRecordID":
                valueList.add(valueSting[0]);
                break;

            case "findByRecIDAndStatus":
                valueList.add(Integer.parseInt(valueSting[0]));
                valueList.add(valueSting[1]);
                break;

            //used in editControlJs to get location data by recId
            case "findByRecID":
                valueList.add(Integer.parseInt(valueSting[0]));
                break;
            case "findBetweenStartAndEndRecId":
                valueList.add(Integer.parseInt(valueSting[0]));
                valueList.add(Integer.parseInt(valueSting[1]));
                break;
            case "findByf852AndDateOfAcq":
                try {
                    Date date1 = dateFormat.parse(valueSting[1]);
                    Date date2 = dateFormat.parse(valueSting[2]);
                    valueList.add(valueSting[0]);
                    valueList.add(new java.sql.Date(date1.getTime()));
                    valueList.add(new java.sql.Date(date2.getTime()));
                } catch (ParseException ex) {
                    Logger.getLogger(BiblidetailsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                if (valueSting.length > 0) {
                    valueList.add(valueSting[0]);
                } else {
                    valueList.add(valueSting[0] = "Invalid Accession No.");
                }
                break;
        }
        return super.findBy("Location." + query, valueList);
    }

    @GET
    @Path("Book/{id}")
    @Produces({"application/xml", "application/json"})
    public Location find(@PathParam("id") String id) {
        return super.find(id);
    }

    @DELETE
    @Path("removeBy/{namedQuery}/{values}")
    public void removeBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch (query) {
            case "deleteByRecID":
                valueList.add(Integer.parseInt(valueString[0]));
                break;
            default:
                valueList.add(valueString[0]);
                //used for RemoveByMemCd, 
                break;
        }
        super.removeBy("Location." + query, valueList);
    }

    @PUT //used in transaction menu and damage
    @Path("damaged")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String damaged(@PathParam("accno") String accno) {
        location = findBy("findByP852", accno).get(0);
        //  location = restResponse.readEntity(genericType).get(0);
        status = location.getStatus();
        if ("WI".equals(status)) {
            output = "Item is in withdrawn status and can not be marked as Damaged.";
        } else if ("DA".equals(status)) {
            output = "Item is already marked as Damaged.";
        } else {
            location.setStatus("DA");
            edit(location);
            output = "Item marked as Damaged.";
        }
        return output;
    }

    @GET
    @Path("BookAvailability/{searchParameter}")
    @Produces("text/plain")
    public String checkforBookAvailability(@PathParam("searchParameter") String accessionNo) {
        location = findBy("findByP852", accessionNo).get(0);
        status = location.getStatus();
        if (!"AV".equals(status)) {
            output = "No";  //Not available
        } else {
            output = "Yes"; //Available
        }
        return output;
    }

    @PUT //used in damage 
    @Path("repaired")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String repaired(@PathParam("accno") String accno) {
        location = findBy("findByP852", accno).get(0);
        status = location.getStatus();
        if (!"DA".equals(status)) {
            output = "Item is not marked as Damaged thus can not be marked as Repaired.";
        } else {
            location.setStatus("AV");
            edit(location);
            output = "Item marked as Repaired.";
        }
        return output;
    }

    @PUT // used in transaction menu and book withdrawal 
    @Path("withdraw")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String withdraw(@FormParam("accessionNos") String accession_Nos) {
        String[] accessionNos = accession_Nos.split(",");

        for (int i = 0; i < accessionNos.length; i++) {
            location = findBy("findByP852", accessionNos[i]).get(0);
            String status = location.getStatus();
            if (status.contentEquals("AV")) {
                // location = restResponse.readEntity(genericType).get(0);
                location.setStatus("WI");
                edit(location);
                output = "Book Withdrawn.";
            } else if (status.contentEquals("WI")) {
                return "Book is already withdrawn.";
            } else {
                return "Book is not available, cannot be withdraw.";
            }
        }
        return output;
    }

    @PUT // used in book withdrawal to re-introduce withdrawn books
    @Path("reIntroduce")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String reIntroduce(@FormParam("accessionNos") String accession_Nos) {
        String[] accessionNos = accession_Nos.split(",");
        for (int i = 0; i < accessionNos.length; i++) {
            location = findBy("findByP852", accessionNos[i]).get(0);
            // location = restResponse.readEntity(genericType).get(0);
            location.setStatus("AV");
            edit(location);
        }
        output = accessionNos.length + " book(s) is(are) re-introduced and made availabel.";
        return output;
    }

    @GET
    @Path("bookDetails/{accno}")
    @Produces({"application/xml", "application/json", "text/plain"})
   // public Response bookDetails(@QueryParam("accno") String accno) {
    public Response bookDetails(@PathParam("accno") String accno) {    
        Response.ResponseBuilder responseBuilder = Response.status(200);
        String accessionNo = accno;
        locationList = findBy("findByP852", accessionNo);
        if (locationList.isEmpty()) {
            output = "Book not found with '" + accessionNo + "' Accession No. Please enter valid Accession No.";
            responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
        } else {
            location = locationList.get(0);
            //    System.out.println("location.getStatus()  "+location.getStatus() +"    "+location.getStatus().equals("AV"));
            if (location.getStatus().equals("AV")) {
                BiblidetailsLocation biblidetailsLocation = biblidetailsLocationFacadeREST.find(accessionNo);
                responseBuilder.type(MediaType.APPLICATION_XML).entity(biblidetailsLocation);
            } else {
                // output = "Book is currently in '"+mBkstatusFacadeREST.find(location.getStatus()).getStatusDscr()+"' state and can not be marked as Damaged.";
                output = "Book is currently in '" + mBkstatusFacadeREST.find(location.getStatus()).getStatusDscr() + "' state and can not be issued.";
                responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
            }
        }
        return responseBuilder.build();
    }

    @GET
    @Path("readForGroupIR/{accNo}")
    @Produces({"application/xml", "application/json", "text/plain"})
    public Response readForGroupIR(@PathParam("accNo") String accNo) {
        Response.ResponseBuilder responseBuilder = Response.status(200);
        if ("".equals(accNo)) {
            accNo = "null";
        }
        locationList = findBy("findByP852", accNo);
        if (!locationList.isEmpty()) {
            location = locationList.get(0);
            if (!"AV".equals(location.getStatus()) && !"GR".equals(location.getStatus())) {
                output = "Item is currently " + mBkstatusFacadeREST.find(location.getStatus()).getStatusDscr() + ".";
                responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
            } else if ("Y".equals(location.getIssueRestricted())) {
                output = "Item is Restricted for Issue.";
                responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
            } else {
                List<BiblidetailsLocation> biblidetailsLocation_list = biblidetailsLocationFacadeREST.findBy("findByP852", accNo);
                GenericEntity<List<BiblidetailsLocation>> list = new GenericEntity<List<BiblidetailsLocation>>(biblidetailsLocation_list) {
                };
                responseBuilder.entity(list);
            }
        } else {
            output = "Invalid Accession No.";
            responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
        }
        return responseBuilder.build();
    }

    

    @GET
    @Path("DistinctShelvingLocation")
    @Produces({"application/xml", "application/json"})
    public List<String> GETC852() throws ParseException {
        String q = "";
        List<String> result = new ArrayList<>();
        Query query;
        q = "select distinct c852 from location";
        query = getEntityManager().createNativeQuery(q);
        result = (List<String>) query.getResultList();
        return result;
    }

    @POST
    @Path("setLocationToImportedData/{recId}")
    public String setLocationToImportedData(@FormParam("copyNo") String copyNo, @FormParam("LocInd1Val") String LocInd1Val,
            @FormParam("LocInd2Val") String LocInd2Val, @FormParam("accNo") String accNo,
            @FormParam("collection") String collection, @FormParam("codedLocation") String codedLocation,
            @FormParam("department") String department, @FormParam("supplier") String supplier,
            @FormParam("material") String material, @FormParam("classNo") String classNo,
            @FormParam("location") String locationdata, @FormParam("budget") String budget,
            @FormParam("invoiceNo") String invoiceNo, @FormParam("statusType") String statusType,
            @FormParam("bookNo") String bookNo, @FormParam("shelvingLocation") String shelvingLocation,
            @FormParam("currency") String currency, @FormParam("currencyRate") String price,
            @FormParam("invoiceDate") String invoiceDate, @FormParam("dateOfAcquisition") String dateOfAcquisition,
            @FormParam("issueRestricted") String issueRestricted, @PathParam("recId") String recId) throws ParseException {
        String copyNoData[] = copyNo.split(",", -1);
        String LocInd1ValData[] = LocInd1Val.split(",", -1);
        String LocInd2ValData[] = LocInd2Val.split(",", -1);
        String accNoData[] = accNo.split(",", -1);
        String collectionData[] = collection.split(",", -1);
        String codedLocationData[] = codedLocation.split(",", -1);
        String departmentData[] = department.split(",", -1);
        String supplierData[] = supplier.split(",", -1);
        String materialData[] = material.split(",", -1);
        String classNoData[] = classNo.split(",", -1);
        String locationdataData[] = locationdata.split(",", -1);
        String budgetData[] = budget.split(",", -1);
        String invoiceNoData[] = invoiceNo.split(",", -1);
        String statusTypeData[] = statusType.split(",", -1);
        String bookNoData[] = bookNo.split(",", -1);
        String shelvingLocationData[] = shelvingLocation.split(",", -1);
        String currencyData[] = currency.split(",", -1);
        String priceData[] = price.split(",", -1);
        String invoiceDateData[] = invoiceDate.split(",", -1);
        String dateOfAcquisitionData[] = dateOfAcquisition.split(",", -1);
        String issueRestrictedData[] = issueRestricted.split(",", -1);

        String output = null;
        for (int i = 0; i < copyNoData.length; i++) {
            Location location = new Location();
            LocationPK locationPk = new LocationPK();
            MFcltydept mFcltydept = new MFcltydept();
            Libmaterials libmaterial = new Libmaterials();
            locationPk.setRecID(Integer.parseInt(recId));
            locationPk.setP852(accNoData[i]);
            location.setLocationPK(locationPk);
            location.setT852(Integer.parseInt(copyNoData[i]));
            location.setInd(LocInd1ValData[i] + "" + LocInd2ValData[i]);
            if (!collectionData[i].contentEquals("select") && !collectionData[i].contentEquals("")) {
                location.setB852(collectionData[i]);
            }
            if (!codedLocationData[i].contentEquals("select") && !codedLocationData[i].contentEquals("")) {
                location.setF852(codedLocationData[i]);
            }

            if (!departmentData[i].contentEquals("select") && !departmentData[i].contentEquals("")) {
                String dept = String.valueOf(departmentData[i]);
                mFcltydept.setFcltydeptcd(dept);
                location.setDepartment(mFcltydept);
            }
            if (!supplierData[i].contentEquals("select") && !supplierData[i].contentEquals("")) {
                location.setSupplier(supplierData[i]);
            }
            if (!materialData[i].contentEquals("select") && !materialData[i].contentEquals("")) {
                libmaterial.setCode(materialData[i]);
                location.setMaterial(libmaterial);
            }
            location.setK852(classNoData[i]);
            location.setA852(locationdataData[i]);
            location.setBudget(budgetData[i]);
            location.setInvoiceNo(invoiceNoData[i]);
            if (!statusTypeData[i].contentEquals("select") && !statusTypeData[i].contentEquals("")) {
                location.setStatus(statusTypeData[i]);
            }
            location.setM852(bookNoData[i]);
            location.setC852(shelvingLocationData[i]);
            if (!currencyData[i].contentEquals("select") && !currencyData[i].contentEquals("")) {
                location.setCurrency(currencyData[i]);
            }
            if (!priceData[i].contentEquals("")) {
                location.setPrice(priceData[i]);
            }

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            if (!invoiceDateData[i].contentEquals("")) {
                Date InvDate = formatter.parse(invoiceDateData[i]);
                System.out.println("date " + new java.sql.Date(InvDate.getTime()));
                location.setInvoiceDate(new java.sql.Date(InvDate.getTime()));
            }
            if (!dateOfAcquisitionData[i].contentEquals("")) {
                Date AcqDate = formatter.parse(dateOfAcquisitionData[i]);
                location.setDateofAcq(new java.sql.Date(AcqDate.getTime()));
            }

            location.setIssueRestricted(issueRestrictedData[i]);

            char charIssueRestricted;
            Date lastUpdatedDate = new Date();
            location.setLastOperatedDT(new java.sql.Date(lastUpdatedDate.getTime()));
            location.setUserName("superuser");
            int count = Integer.parseInt(countREST());
            super.create(location);
            if (Integer.parseInt(countREST()) != count) {
                output = "success";
            } else {
                output = "Data not saved";
            }
        }
        return output;
    }

    // used in title in process to mark entries as available
    @PUT
    @Path("markAsAvailable")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String markAsAvailable(@FormParam("accessionNos") String accessionNos) {
        String q1 = "";
        String q2 = "";
        Query query1;
        Query query2;
        //String valuestring[] = paramValue.split(",");
        //StringBuilder s = new StringBuilder(accessionNos);
        //  accessionNos.replace(",", "','");
        q1 = "UPDATE location SET Status = 'AV' where p852 in ('" + accessionNos.replace(",", "','") + "')";
        q2 = "UPDATE a_accession SET a_a_incircul = 'Y' where a_a_accno in ('" + accessionNos.replace(",", "','") + "')";
        System.out.println("query  " + q1 + "  " + q2);
        List<Object> result;
        query1 = getEntityManager().createNativeQuery(q1);
        query2 = getEntityManager().createNativeQuery(q2);
        query1.executeUpdate();
        query2.executeUpdate();

        return "Marked for circulation";
    }

    // @GET
// @Path("findByAccession")
// @Produces({"application/xml", "application/json"})      
//  public <T> T getXmlData(class<T> responseType , )  {
//        
//        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
//    }       
//        
//    -------------------------------------------------------------------------------------
//    
//     @GET
//    @Path("getBookDetail")
//    @Produces({"application/xml", "application/json"})
//    public String getBookDetail(@QueryParam("accNo") String accNo)
//    {
//      if(!accNo.equals(""))
//        {
//            locationList = findBy("findByP852", accNo);
//            if(locationList.isEmpty())
//            {
//                output = "Book not found with '"+accNo+"' Accession No. Please enter valid Accession No.";
//            }
//            else
//            {
//             //   output = (biblocationIssueReserveFacadeREST.find(accNo)).get;
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                XMLEncoder xmlEncoder = new XMLEncoder(baos);
//                xmlEncoder.writeObject(biblocationIssueReserveFacadeREST.find(accNo));
//                xmlEncoder.close();
//
//                output = baos.toString();
//                System.out.println("out "+output);
//             }
//        }
//        else
//            output = "Empty Accession No., Please enter valid Accession No.";
//        return output;
//    }
//        //display biblidetails for global Search
//    @GET
//    @Path("getBibdetailsforGlobalSearch/by/tagAndSubfield/{tag}/{subfield}/searchWord/{searchWord}")
//    @Produces({"application/xml", "application/json"})
//    public List<Location> getBibdetailsforGlobalSearch(@PathParam("tag") String tag,@PathParam("subfield") String subfield
//            ,@PathParam("searchWord") String searchWord)
//      {
//         List<Biblidetails>  getBiblidetails = biblidetailsFacadeREST.getBibdetailsforGlobalSearch(tag, subfield, searchWord);
//         List<String> recIdList = new ArrayList<>();
//         for(Biblidetails b :getBiblidetails)
//        {
//            recIdList.add(String.valueOf(b.getBiblidetailsPK().getRecID()));
//            }
//        String recIdListString = StringUtils.join(recIdList, ',');
//        recIdListString = recIdListString.replace(",", "','");
//        recIdListString = "'"+recIdListString+"'";
//   //     String q = "Select * from biblidetails_location b  where b.RecID IN ("+recIdListString+")";GlobalSearchclass
//        String q = "Select * from  location l where l.RecID IN ("+recIdListString+")";
//        List<Location> result;
//        Query query = getEntityManager().createNativeQuery(q,Location.class);
//        result = (List<Location>) query.getResultList();
//        return result;
//    }
    
//    @PUT
//    @Path("resetItems/{accNos}")
//    @Produces({"application/xml", "application/json", "text/plain"})
//    public String resetItems(@PathParam("accNos") String accNos) {
//        String verified = "";
//        String notVerified = "";
//        accNo = accNos.split(",");
//        for (String acc : accNo) {
//            List<Location> location = findBy("findByP852", acc);
//            if (location.size() > 0) {
//                location.get(0).setStatus("AV");
//                edit(location.get(0));
//                verified = verified + " " + acc + ",";
//            } else {
//                notVerified = notVerified + " " + acc + ",";
//            }
//        }
//        return "Status of Accession nos:(" + verified + ")Updated";
//    }
    
    @PUT
    @Path("resetItems/{accNos}")
    @Produces({"application/xml", "application/json"})
    public StringData resetItems(@PathParam("accNos") String accNos) {
        System.out.println("Called");
        String result = "";
        accNo = accNos.split(",");
        String notresetaccno="";
       for(int i=0; i<accNo.length; i++)
       {     
           try{
               location = findBy("findByP852", accNo[i]).get(0); 
           }catch(ArrayIndexOutOfBoundsException e){
               result += accNo[i]+" Invalid Accession No"+",";
               continue;
           }
           
           try{
               issue = tIssueFacadeREST.findBy("findByAccNo",accNo[i]);
           }catch(ArrayIndexOutOfBoundsException e){
               System.out.println("ArrayIndexOutOfBoundsException"+e);
           }
           
//           try{
//               System.out.println("location.getLocationPK().getRecID()--"+location.getLocationPK().getRecID());
//               int recid=location.getLocationPK().getRecID();
//               reserve = tReserveFacadeREST.findBy("findByRecNoAndMinSrNo", Integer.toString(location.getLocationPK().getRecID())).get(0);
//          
//           }catch(ArrayIndexOutOfBoundsException a){
//              a.printStackTrace();
//           }catch(Exception e){
//               e.printStackTrace();
//           }
           
            if(issue.size()==0 && location.getStatus().equals("IS"))
                 {
                       location.setStatus("AV");
                       edit(location);
                       
//                       if(location.getStatus().equals("AR")){
//                         
//                           System.out.println("getRecID---"+location.getLocationPK().getRecID());
//                           System.out.println("reserve---"+reserve.toString());
//                           System.out.println("reserve.get(0)---"+reserve);
//                           System.out.println("memcod---"+reserve.getMMember().getMemCd());
//                           tReserveFacadeREST.removeByMemcdAndRecordNo(reserve.getMMember().getMemCd(),location.getLocationPK().getRecID());
//                       }else{
                          // result += accNo[i]+" Not Eligable for Reset"+",";
                       //}
                  result += accNo[i]+",";
                 }else{
                notresetaccno += accNo[i]+",";
                }
           
       }
        
        return new StringData(result+" Reset SuccessFully"+"/"+notresetaccno+" not Reset only issue And On Hold Item Is Allowed For Reset");
    }
    
    @GET
    @Path("getBookDetailsBystatus/{status}")
    @Produces({"application/xml", "application/json"})
    public List<Bookdetails> getBookDetailsBystatus(@PathParam("status") String status) throws ParseException {
        String q = "select distinct l.RecId As RecId, b.FValue as Title, E.FValue as Author, l.p852 as AccessionNo, s.status_dscr as Status from Biblidetails b left join Biblidetails e on b.RecID = e.RecID and (e.Tag = 100 or e.Tag = 700) and e.SbFld = 'a' inner join location l on b.RecID = l.RecID left join m_bkstatus s on l.Status = s.bk_issue_stat left join LibMaterials f on l.Material = f.Code where b.Tag = 245 and b.SbFld = 'a' and l.status='"+status+"' ";
        List<Bookdetails> result = new ArrayList<>();
        Query query;  
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q,Bookdetails.class);
        result = (List<Bookdetails>) query.getResultList();
        return result;
    }
    
    @GET
    @Path("getBookDetails/{accessionNo}")
    @Produces({"application/xml", "application/json"})
    public List<Bookdetails> getBookDetails(@PathParam("accessionNo") String accessionNo) throws ParseException {
        String q = "select distinct l.RecID as Recid,b1.FValue as Title,b2.FValue as Author,b3.FValue as Edition,l.p852 as AccessionNo,l.Price as Price,l.k852 as Classno,s.status_dscr as Status,l.m852 as Bookno,li.Description as Materialtype,l.IssueRestricted as Issuerestric,l.f852 as booklibcd,l.c852 as bookshelvlocation from Biblidetails b left join Biblidetails b1 on b.RecID=b1.RecID and b1.Tag='245' and b1.SbFld='a' left join Biblidetails b2 on b1.RecID=b2.RecID and (b2.Tag='100' or b2.tag='700') and b2.SbFld='a' left join Biblidetails b3 on b2.RecID=b3.RecID and b3.Tag='250' and b3.SbFld='a' inner join Location l on l.RecID = b.RecID left join m_bkstatus s on l.Status = s.bk_issue_stat left join LibMaterials li on li.Code=l.Material where l.p852 IN ('"+accessionNo+"')";
        List<Bookdetails> result = new ArrayList<>();
        Query query;  
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q,Bookdetails.class);
        result = (List<Bookdetails>) query.getResultList();
        return result;
    }
    
    @GET
    @Path("getBookDetails/{accessionNo}/{chk1}/{chk2}/{chk3}")
    @Produces({"application/xml", "application/json"})
    public List<Bookdetails> getBookDetailsforissue(@PathParam("accessionNo") String accessionNo,
            @PathParam("chk1") String chk1,@PathParam("chk2") String chk2,@PathParam("chk3") String chk3) throws ParseException, ValidationException {
        
        String q = "select distinct l.RecID as Recid,b1.FValue as Title,b2.FValue as Author,b3.FValue as Edition,l.p852 as AccessionNo,l.Price as Price,l.k852 as Classno,s.status_dscr as Status,l.m852 as Bookno,li.Description as Materialtype,l.IssueRestricted as Issuerestric,l.f852 as booklibcd,l.c852 as bookshelvlocation from Biblidetails b left join Biblidetails b1 on b.RecID=b1.RecID and b1.Tag='245' and b1.SbFld='a' left join Biblidetails b2 on b1.RecID=b2.RecID and (b2.Tag='100' or b2.tag='700') and b2.SbFld='a' left join Biblidetails b3 on b2.RecID=b3.RecID and b3.Tag='250' and b3.SbFld='a' inner join Location l on l.RecID = b.RecID left join m_bkstatus s on l.Status = s.bk_issue_stat left join LibMaterials li on li.Code=l.Material where l.p852 IN ('"+accessionNo+"')";
        List<Bookdetails> result = new ArrayList<>();
        Query query;  
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q,Bookdetails.class);
        result = (List<Bookdetails>) query.getResultList();
        List<ValidationErrorlistfields> livalidationerror = new ArrayList<>();
        if(result.size()>0){
            //issue restricted check
            if(chk1.equals("Y")){
                if(result.get(0).getIssueRestricted().equals("Y")){
                    livalidationerror.add(new ValidationErrorlistfields("Book is IssueRestricted.","NaN", "IssueRestricted"));
                }
            }
            //f852 check for belong to library
            if(chk2.equals("Y")){
               Userdetail usde =   userdetailFacadeREST.findSingleResultBy("findByUName","superuser");
               int k=0;
               if(usde.getLibID().equals("00000") || usde.getLibID().equals("99999") || usde.getLibID().equals(result.get(0).getBooklibcode())){
                   k=1;
               }
               if(k==0){
                   livalidationerror.add(new ValidationErrorlistfields("User not allowed to issue book "+result.get(0).getStatus(),"NaN", "User Libcode"));
               }
            }
            //Status of book
            if(chk3.equals("Y")){
                int i=0;
                if(result.get(0).getStatus().equals("Available") || result.get(0).getStatus().equals("On Hold") || result.get(0).getStatus().equals("Reserved") || result.get(0).getStatus().equals("Issued") || result.get(0).getStatus().equals("On Premises Issue") || result.get(0).getStatus().equals("Over Night Issue")){
                    i=1;
                }
                if(i==0){
                     livalidationerror.add(new ValidationErrorlistfields("Book status is "+result.get(0).getStatus(),"NaN", "Status"));
                }
            }
        }
        
        if(livalidationerror.size()>0){
            throw new ValidationException("Validation of Data",livalidationerror);
        }
        
        
        return result;
    }
    
    @GET
    @Path("getBookDetails/{accessionNo}/{memcode}/{chk1}/{chk2}/{chk3}")
    @Produces({"application/xml", "application/json"})
    public List<Bookdetails> getBookDetailsforissuewithmemcode(@PathParam("accessionNo") String accessionNo,@PathParam("memcode") String memcode,
            @PathParam("chk1") String chk1,@PathParam("chk2") String chk2,@PathParam("chk3") String chk3) throws ParseException, ValidationException {
        
        String q = "select distinct l.RecID as Recid,b1.FValue as Title,b2.FValue as Author,b3.FValue as Edition,l.p852 as AccessionNo,l.Price as Price,l.k852 as Classno,s.status_dscr as Status,l.m852 as Bookno,li.Description as Materialtype,l.IssueRestricted as Issuerestric,l.f852 as booklibcd,l.c852 as bookshelvlocation from Biblidetails b left join Biblidetails b1 on b.RecID=b1.RecID and b1.Tag='245' and b1.SbFld='a' left join Biblidetails b2 on b1.RecID=b2.RecID and (b2.Tag='100' or b2.tag='700') and b2.SbFld='a' left join Biblidetails b3 on b2.RecID=b3.RecID and b3.Tag='250' and b3.SbFld='a' inner join Location l on l.RecID = b.RecID left join m_bkstatus s on l.Status = s.bk_issue_stat left join LibMaterials li on li.Code=l.Material where l.p852 IN ('"+accessionNo+"')";
        List<Bookdetails> result = new ArrayList<>();
        Query query;  
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q,Bookdetails.class);
        result = (List<Bookdetails>) query.getResultList();
        List<ValidationErrorlistfields> livalidationerror = new ArrayList<>();
        if(result.size()>0){
            Date issuedt=null;
            Date reservedt=null;
            Date duedt = null;
            Bookdetails b1 = result.get(0);
//            b1.setDuedate(duedt);
//            result.clear();
//            result.add(b1);
            if(result.get(0).getStatus().equals("Available") || result.get(0).getStatus().equals("On Hold")){
                duedt = tIssueFacadeREST.getDueDate(accessionNo, memcode);   
            }else if(result.get(0).getStatus().equals("Book Bank")){
                
            }else if(result.get(0).getStatus().equals("Group Issue")){
                
            }else if(result.get(0).getStatus().equals("Issued")){
                List<TIssue> t = tIssueFacadeREST.findBy("findByAccNo",accessionNo);
                if(t.size()>0){
                    duedt = tIssueFacadeREST.getDueDate(accessionNo, memcode);
                    issuedt = tIssueFacadeREST.getDueDate(accessionNo, memcode);
                }else{
                    duedt=null;
                    issuedt=null;
                }
                
            }else if(result.get(0).getStatus().equals("Reserved")){
                
            }else if(result.get(0).getStatus().equals("Over Night Issue")){
                
            }else if(result.get(0).getStatus().equals("On Premises Issue")){
                
            }else{
            
            }
            
            
            //issue restricted check
            if(chk1.equals("Y")){
                if(result.get(0).getIssueRestricted().equals("Y")){
                    livalidationerror.add(new ValidationErrorlistfields("Book is IssueRestricted.","NaN", "IssueRestricted"));
                }
            }
            //f852 check for belong to library
            if(chk2.equals("Y")){
               Userdetail usde =   userdetailFacadeREST.findSingleResultBy("findByUName","superuser");
               int k=0;
               if(usde.getLibID().equals("00000") || usde.getLibID().equals("99999") || usde.getLibID().equals(result.get(0).getBooklibcode())){
                   k=1;
               }
               if(k==0){
                   livalidationerror.add(new ValidationErrorlistfields("User not allowed to issue book "+result.get(0).getStatus(),"NaN", "User Libcode"));
               }
            }
            //Status of book
            if(chk3.equals("Y")){
                int i=0;
                if(result.get(0).getStatus().equals("Available") || result.get(0).getStatus().equals("On Hold") || result.get(0).getStatus().equals("Reserved") || result.get(0).getStatus().equals("Issued") || result.get(0).getStatus().equals("On Premises Issue") || result.get(0).getStatus().equals("Over Night Issue")){
                    i=1;
                }
                if(i==0){
                     livalidationerror.add(new ValidationErrorlistfields("Book status is "+result.get(0).getStatus(),"NaN", "Status"));
                }
            }
        }
        
        if(livalidationerror.size()>0){
            throw new ValidationException("Validation of Data",livalidationerror);
        }
        
        
        return result;
    }
    
    @GET
    @Path("detailsByTitle/{transaction}/{title}")
    @Produces({"application/xml", "application/json"})
    public List<TitleDetails> detailsByTitle(@PathParam("transaction") String transaction,@PathParam("title") String title) throws ParseException {
       String q="";
        if(transaction.equals("issued")){
        q="select l.p852 as accNo,b.fvalue as Title,"
                + "t.mem_cd as Member_code,m.mem_firstnm as Member_firstName,"
                + "m.mem_lstnm as mem_lastname,t.iss_dt as Issue_dt,t.due_dt as Due_dt,"
                + "md.Fclty_dept_dscr as Department,mb.Branch_name as BranchName,"
                + "m.mem_prmntadd1 as Member_address,m.mem_prmntpin as Memver_pin,m.mem_prmntcity as Member_city,"
                + "m.mem_prmntphone as Member_phone from location l,biblidetails b,t_issue t,"
                + "m_member m,m_fcltydept md,m_branchmaster mb "
                + "where t.acc_no = l.p852 and l.recid=b.recid and b.tag='245' and b.sbfld='a' "
                + "and t.mem_cd=m.mem_cd and m.mem_dept=md.Fclty_dept_cd and m.mem_degree=mb.branch_cd "
                + "and l.status='IS' and b.fvalue like '"+title+"%'";
        }else{
        q="select  l.p852 as accNo,b.fvalue as Title,ie.lib_cd,ie.rqst_dt,ie.send_dt,ie.rqst_ref,il.lib_nm,il.lib_add1,il.lib_city,il.lib_pin,"
        + "il.lib_phone from location l, biblidetails b,ill_extissue ie,ill_libmst il "
        + "where l.recid=b.recid and b.tag=245 and b.SBfLD='a' and ie.acc_no=l.p852 "
        + "and ie.lib_cd=il.lib_cd and recv_dt='0000-00-00' and l.status='IL' and b.fvalue like '"+title+"%'";
        }
        
        List<TitleDetails> result = new ArrayList<>();
        Query query;  
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q,TitleDetails.class);
        result = (List<TitleDetails>) query.getResultList();
        return result;
    }
}


// switch (Paramname) {
//            case "GetFinePayersByUserName":
//                q = "select t_memrcpt.rcpt_no, t_memrcpt.rcpt_dt, t_memrcpt.slip_no, t_memrcpt.slip_dt, t_memrcpt.fine_desc, t_memrcpt.rcpt_amt, t_memrcpt.mem_cd, t_memrcpt.user_cd,\n"
//                        + " m_member.mem_tag, m_member.mem_firstnm, m_member.mem_lstnm, m_member.mem_midnm, t_memrcpt.user_cd, t_memfine.accn_no\n" 
//                        + " from t_memrcpt \n" 
//                        + " LEFT OUTER JOIN  t_memfine ON t_memrcpt.mem_cd = t_memfine.mem_cd AND t_memrcpt.slip_no = t_memfine.slip_no \n" 
//                        + " LEFT OUTER JOIN  m_member ON t_memrcpt.mem_cd = m_member.mem_cd \n"
//                        + " WHERE t_memrcpt.user_cd = '" + Paramvalue + "'";
//                break;
//                
//            case "GenerateSlipByMemberCode":
//                q = "SELECT m_member.mem_cd, m_member.mem_firstnm, m_member.mem_lstnm, t_memrcpt.slip_no, \n" +
//                        "   t_memrcpt.slip_dt,  t_memrcpt.rcpt_no, t_memrcpt.rcpt_dt, t_memrcpt.rcpt_amt FROM m_member, t_memrcpt  where m_member.mem_cd = t_memrcpt.mem_cd \n"
//                        + "   and t_memrcpt.slip_no in ( SELECT t_memfine.slip_no \n"
//                        + "   FROM t_memfine\n"
//                        + "   where t_memfine.mem_cd = '" + Paramvalue + "' )\n"
//                        + "   and t_memrcpt.mem_cd = '" + Paramvalue + "' and t_memrcpt.rcpt_no= (select (max(rcpt_no))  from t_memrcpt)";
//                break;
//                
//                case "GenerateSlipBySlipNo":
//                q = "SELECT m_member.mem_cd, m_member.mem_firstnm, m_member.mem_lstnm, t_memrcpt.slip_no, \n" +
//                        "   t_memrcpt.slip_dt,  t_memrcpt.rcpt_no, t_memrcpt.rcpt_dt, t_memrcpt.rcpt_amt FROM m_member, t_memrcpt  where m_member.mem_cd = t_memrcpt.mem_cd \n"
//                        + "   and t_memrcpt.slip_no in ( SELECT t_memfine.slip_no \n"
//                        + "   FROM t_memfine\n"
//                        + "   where t_memfine.slip_no = '" + Paramvalue + "' )\n"
//                        + "   and t_memrcpt.slip_no = '" + Paramvalue + "' and t_memrcpt.rcpt_no= (select (max(rcpt_no))  from t_memrcpt)";
//                break; 
//        }