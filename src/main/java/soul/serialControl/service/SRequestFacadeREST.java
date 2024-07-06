/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl.service;

//import ExceptionService.DataException;
import java.awt.Label;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Pattern;
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
import javax.ws.rs.core.Form;
import soul.catalogue.Biblidetails;
import soul.catalogue.BiblidetailsPK;
import soul.catalogue.service.BiblidetailsFacadeREST;
import soul.circulation.MMember;
import soul.circulation.service.MMemberFacadeREST;
import soul.general_master.MFcltydept;
import soul.general_master.service.ABudgetFacadeREST;
import soul.general_master.ABudget;
import soul.general_master.service.ACurrencyFacadeREST;
import soul.general_master.ACurrency;
import soul.general_master.service.BkSubjectFacadeREST;
import soul.general_master.service.LibmaterialsFacadeREST;
import soul.general_master.service.MFcltydeptFacadeREST;
import soul.serialControl.SMst;
import soul.serialControl.SRequest;
import soul.general_master.BkSubject;
import soul.serial_master.SFrequency;
import soul.serial_master.SSupplierDetail;
import soul.serial_master.service.SEditionFacadeREST;
import soul.serial_master.SEdition;
import soul.general_master.Libmaterials;
import soul.serial_master.service.SFrequencyFacadeREST;
import soul.serial_master.service.SSupplierDetailFacadeREST;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.serialcontrol.srequest")
public class SRequestFacadeREST extends AbstractFacade<SRequest> {

    @EJB
    private MFcltydeptFacadeREST mFcltydeptFacadeREST;
    @EJB
    private MMemberFacadeREST mMemberFacadeREST;
    @EJB
    private SSupplierDetailFacadeREST sSuppDetailFacadeREST;
    @EJB
    private SFrequencyFacadeREST sFrequencyFacadeREST;
    @EJB
    private LibmaterialsFacadeREST libmaterialsFacadeREST;
    @EJB
    private BkSubjectFacadeREST bkSubjectFacadeREST;
    @EJB
    private SEditionFacadeREST sEditionFacadeREST;
    @EJB
    private ABudgetFacadeREST aBudgetFacadeREST;
    @EJB
    private ACurrencyFacadeREST aCurrencyFacadeREST;
    @EJB
    private SMstFacadeREST sMstFacadeREST;
    @EJB
    private BiblidetailsFacadeREST biblidetailsFacadeREST;

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    int count = 0;
    SRequest sRequest;
    String output;
    String[] requestNos;
    List<SRequest> requestList = new ArrayList<>();
    SMst sMst;
    Biblidetails biblidetails;

    public SRequestFacadeREST() {
        super(SRequest.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(SRequest entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(SRequest entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public SRequest find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        //System.out.println("dates : "+values);
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        List<String> status = new ArrayList<>();

        status.addAll(Arrays.asList(valueString[0].split("|")));

        switch (query) {
            case "findBySRStatuss":
                valueList.add(status);
                break;
            case "findByMemCd":
                valueList.add(valueString[0]);
                break;
//            case "findByDepartmentWise":
//                valueList.add(valueString[0]);
//                break;
            case "findByTitleAndRequestStatus":
                valueList.add(valueString[0]+"%");
                valueList.add(valueString[1]);
                break;
            case "findByDepartmentAndRequestStatus":
                valueList.add(valueString[0]+"%");
                valueList.add(valueString[1]);
                break;
            case "findByPublisherAndRequestStatus":
                valueList.add(valueString[0]+"%");
                valueList.add(valueString[1]);
                break;
            case "findBySupplierAndRequestStatus":
                valueList.add(valueString[0]+"%");
                valueList.add(valueString[1]);
                break;
            case "findBySubjectAndRequestStatus":
                valueList.add(valueString[0]+"%");
                valueList.add(valueString[1]);
                break;
            case "findByBudgetAndRequestStatus":
                valueList.add(valueString[0]+"%");
                valueList.add(valueString[1]);
                break;
                
            case "findByRequestNoWise":
                valueList.add(Integer.parseInt(valueString[0]));
                valueList.add(Integer.parseInt(valueString[1]));
                break;
            case "findByRequestIDAndRequestStatus":
                valueList.add(Integer.parseInt(valueString[0]));
                valueList.add(valueString[1]);
                break;
            case "findByDateBetweenAndRequestStatus":
                 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                     System.out.println(" dat1. "+dateFormat.parse(valueString[0]));
                    System.out.println(" dat2 ."+dateFormat.parse(valueString[1]));
                    valueList.add(dateFormat.parse(valueString[0]));
                    valueList.add(dateFormat.parse(valueString[1]));

                } catch (ParseException ex) {
                    Logger.getLogger(SRequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:    
                int flag=1;
                switch(query){
                    default:
                        System.out.println("Nested default executed...");
                        valueList.add(valueString[0]);
                        flag=0;
                        break;
                }
                if(flag==1){
                    valueList.add(valueString[0]);
                    valueList.add(valueString[1]);
                }
                break;
                
            //Note that a List of string objects is set as Status Parameter
            //used by findBySRStatuss
        }
        return super.findBy("SRequest." + query, valueList);
    }

    @GET
    @Path("retrieveAllRequests")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> retrieveAllRequests() {
        
        List<SRequest> getAll = findAll();     
        return getAll;
    }
    @GET
    @Path("retrieveAll/{form}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> retrieveAll(@PathParam("form") String form) {
        String status = null;
        if (form.equals("Parcel")) {
            status = "P";
        } else if ("Approval".equals(form)) {
            status = "S";
        } else if ("ReApprove".equals(form)) {
            status = "R";
        } else if ("MergeWithDatabase".equals(form)) {
            status = "A";
        }
        List<SRequest> getAll = null;
        //  if(accept.equals("XML"))
        {
            System.out.println("status " + status);
            getAll = findBy("findBySRStatuss", status);
        }
        return getAll;
//         else if(accept.equals("ObjectList"))
//         {
//             GenericType<List<SRequest>> genericType = new GenericType<List<SRequest>>(){};
//             restResponse = requestClient.findBy(Response.class, "findBySRStatuss", "P");
//             requestList = restResponse.readEntity(genericType);
//             request.setAttribute("requestList", requestList);
//         }
    }
        
    @GET
    @Path("findByRequestIDAndRequestStatus/{requestId}/{status}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findByRequestIDAndRequestStatus(@Pattern(regexp = "^[0-9]*$", message = "{requestId.pattern}") @PathParam("requestId") String requestId, 
                                        @Pattern(regexp = "^[a-zA-Z]{1}$", message = "{status.pattern}") @PathParam("status") String status) {
        List<SRequest> getAll = null;

        //System.out.println("status " + status);
        getAll = findBy("findByRequestIDAndRequestStatus", requestId+","+status);

        return getAll;
    }
    
    @GET
    @Path("findByDepartmentAndRequestStatus/{dept}/{status}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findByDepartmentAndRequestStatus(@Pattern(regexp = "^[A-Za-z0-9 &-_]*$", message = "{department.pattern}") @PathParam("dept") String dept, 
            @Pattern(regexp = "^[a-zA-Z]{1}$", message = "{status.pattern}") @PathParam("status") String status) {
        List<SRequest> getAll = null;

        //System.out.println("status " + status);
        getAll = findBy("findByDepartmentAndRequestStatus", dept+","+status);

        return getAll;
    }
 
    @GET
    @Path("findByTitleAndRequestStatus/{title}/{status}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findByTitleAndRequestStatus(@PathParam("title") String title, 
            @Pattern(regexp = "^[a-zA-Z]{1}$", message = "{status.pattern}") @PathParam("status") String status) {
        List<SRequest> getAll = null;

        //System.out.println("status " + status);
        getAll = findBy("findByTitleAndRequestStatus", title+","+status);

        return getAll;
    }
    
    @GET
    @Path("findByPublisherAndRequestStatus/{publisher}/{status}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findByPublisherAndRequestStatus(@Pattern(regexp = "^[A-Za-z0-9 &-_]*$", message = "{vendorName.pattern}") @PathParam("publisher") String publisher, 
            @Pattern(regexp = "^[a-zA-Z]{1}$", message = "{status.pattern}") @PathParam("status") String status) {
        List<SRequest> getAll = null;

        //System.out.println("status " + status);
        getAll = findBy("findByPublisherAndRequestStatus", publisher+","+status);

        return getAll;
    }
    
    @GET
    @Path("findBySupplierAndRequestStatus/{supplier}/{status}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findBySupplierAndRequestStatus(@Pattern(regexp = "^[A-Za-z0-9 &-_]*$", message = "{vendorName.pattern}") @PathParam("supplier") String supplier, 
            @Pattern(regexp = "^[a-zA-Z]{1}$", message = "{status.pattern}") @PathParam("status") String status) {
        List<SRequest> getAll = null;

        //System.out.println("status " + status);
        getAll = findBy("findBySupplierAndRequestStatus", supplier+","+status);

        return getAll;
    }

    @GET
    @Path("findByDateBetweenAndRequestStatus/{dateBetween}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findByDateBetweenAndRequestStatus(@PathParam("dateBetween") String dateBetween) {
        List<SRequest> getAll = null;

        //System.out.println("status " + status);
        getAll = findBy("findByDateBetweenAndRequestStatus", dateBetween);

        return getAll;
    }
    
    @GET
    @Path("findBySubjectAndRequestStatus/{subject}/{status}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findBySubjectAndRequestStatus(@PathParam("subject") String subject,
            @Pattern(regexp = "^[a-zA-Z]{1}$", message = "{status.pattern}") @PathParam("status") String status) {
        List<SRequest> getAll = null;

        //System.out.println("status " + status);
        getAll = findBy("findBySubjectAndRequestStatus", subject+","+status);

        return getAll;
    }
    
    @GET
    @Path("findByBudgetAndRequestStatus/{budget}/{status}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findByBudgetAndRequestStatus(@Pattern(regexp = "^[a-zA-Z0-9 _]*$", message = "{budget.pattern}") @PathParam("budget") String budget, 
            @Pattern(regexp = "^[a-zA-Z]{1}$", message = "{status.pattern}") @PathParam("status") String status) {
        List<SRequest> getAll = null;

        //System.out.println("status " + status);
        getAll = findBy("findByBudgetAndRequestStatus", budget+","+status);

        return getAll;
    }
    
    @GET
    @Path("findByRequestNoWise/{requestNo}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findByRequestNoWise(@Pattern(regexp = "^[0-9,]*$", message = "{requestId.pattern}") @PathParam("requestNo") String requestNo) {
        List<SRequest> getAll = null;

        //System.out.println("status " + status);
        getAll = findBy("findByRequestNoWise", requestNo);

        return getAll;
    }
    
    @GET
    @Path("findByDepartmentWise/{dept}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findByDepartmentWise(@Pattern(regexp = "^[A-Za-z0-9 &-_]*$", message = "{department.pattern}") @PathParam("dept") String dept) {
        List<SRequest> getAll = null;

        //System.out.println("status " + status);
        getAll = findBy("findByDepartmentWise", dept);

        return getAll;
    }
    
    @GET
    @Path("findByBudgetWise/{budget}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findByBudgetWise(@Pattern(regexp = "^[a-zA-Z0-9 _]*$", message = "{budget.pattern}") @PathParam("budget") String budget) {
        List<SRequest> getAll = null;

        //System.out.println("status " + status);
        getAll = findBy("findByBudgetWise", budget);

        return getAll;
    }
    @GET
    @Path("findBySupplier/{supplier}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findBySupplier(@Pattern(regexp = "^[A-Za-z0-9 &-_]*$", message = "{vendorName.pattern}") @PathParam("supplier") String supplier) {
        List<SRequest> getAll = null;

        //System.out.println("status " + status);
        getAll = findBy("findBySupplier", supplier);

        return getAll;
    }
    
    @GET
    @Path("findByRequestStatus/{status}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findByRequestStatus(@Pattern(regexp = "^[a-zA-Z]{1}$", message = "{status.pattern}") @PathParam("status") String status) {
        List<SRequest> getAll = null;

        //System.out.println("status " + status);
        getAll = findBy("findByRequestStatus", status);

        return getAll;
    }
        
    @GET
    @Path("findByPublisher/{publisher}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findByPublisher(@Pattern(regexp = "^[A-Za-z0-9 &-_]*$", message = "{vendorName.pattern}") @PathParam("publisher") String publisher) {
        List<SRequest> getAll = null;

        //System.out.println("status " + status);
        getAll = findBy("findByPublisher", publisher);

        return getAll;
    }
    
    @GET
    @Path("findByRequesterWise/{requesterName}")
    @Produces({"application/xml", "application/json"})
    public List<SRequest> findByRequesterWise(@Pattern(regexp = "^[A-Za-z0-9 &-_]*$", message = "{requesterName.pattern}") @PathParam("requesterName") String requesterName) {
        List<SRequest> getAll = null;

        //System.out.println("status " + status);
        getAll = findBy("findByRequesterWise", requesterName);

        return getAll;
    }

    protected SRequest getRequestDetail(Form form) {
        SRequest s_request = null;

        if (!"".equals(form.asMap().getFirst("txtId"))) {
            s_request = find(form.asMap().getFirst("txtId"));
        }
        
        if (s_request == null) {
            s_request = new SRequest();
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(form.asMap().getFirst("reqDate"));
        } catch (ParseException ex) {
            Logger.getLogger(SRequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        s_request.setSRDate(date);
        s_request.setSRRefno(form.asMap().getFirst("txtRefNo"));
        s_request.setSRTitle(form.asMap().getFirst("txtTitle"));
        s_request.setSRDeptCd(mFcltydeptFacadeREST.find(Integer.parseInt(form.asMap().getFirst("selTitleDepartment"))));
        s_request.setSRNm(mMemberFacadeREST.find(form.asMap().getFirst("selTitleName")));
        s_request.setSRPubCd(sSuppDetailFacadeREST.find(form.asMap().getFirst("selPubName")));
        s_request.setSRPubCity(form.asMap().getFirst("pubPlace"));
        s_request.setSRPubCountry(form.asMap().getFirst("pubCountry"));
        s_request.setSRSupCd(sSuppDetailFacadeREST.find(form.asMap().getFirst("selSupName")));
        s_request.setSRSupCity(form.asMap().getFirst("supPlace"));
        s_request.setSRSupCountry(form.asMap().getFirst("supCountry"));
        s_request.setSRFreqCd(sFrequencyFacadeREST.find(form.asMap().getFirst("frequency")));
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(form.asMap().getFirst("dateFrom"));
        } catch (ParseException ex) {
            Logger.getLogger(SRequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        s_request.setSRFromDt(date);
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(form.asMap().getFirst("dateTo"));
        } catch (ParseException ex) {
            Logger.getLogger(SRequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        s_request.setSRToDt(date);
        s_request.setSRIssn(form.asMap().getFirst("txtIssn"));
        s_request.setSRReqCopy(Integer.parseInt(form.asMap().getFirst("txtReqCopies")));
        s_request.setSRPhycd(libmaterialsFacadeREST.find(form.asMap().getFirst("txtPhyMedia")));
        s_request.setSRSubCd(bkSubjectFacadeREST.find(form.asMap().getFirst("selSubject")));
        s_request.setSREdition(sEditionFacadeREST.find(form.asMap().getFirst("selEdition")));
        s_request.setSRBudgetCd(aBudgetFacadeREST.find(Integer.parseInt(form.asMap().getFirst("selBudget"))));
        s_request.setSRCurrencyCd(aCurrencyFacadeREST.find(form.asMap().getFirst("currency")));
        s_request.setSRStatus("P");
        s_request.setSRConvRate(new BigDecimal(form.asMap().getFirst("rate")));
        s_request.setSRPrice(new BigDecimal(form.asMap().getFirst("txtOriginalPrice")));
        s_request.setSRRemark(form.asMap().getFirst("txtRemarks"));
        return s_request;
    }

    @POST
    @Path("save")
    @Produces("text/plain")
    public String save(Form form) {
        sRequest = getRequestDetail(form);
        count = Integer.parseInt(countREST());
        create(sRequest);
        if (count == Integer.parseInt(countREST())) {
            output = "Record Not Inserted..";
        } else {
            output = "Record Inserted..";
        }
        return output;
    }
    
//    @PUT
//    @Path("updateRequest")
//    @Produces("text/plain")
//    public String updateRequest(Form form) {
//        sRequest = newRequest(form);
//        edit(sRequest);
//        output = "Record updated";
//        return output;
//    }
//    
    @POST
    @Path("newRequest")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    //@Produces("text/plain")
    public String newRequest(@FormParam("requestDate") String requestDate, 
            @Pattern(regexp = "^[0-9]*$", message = "{requestId.pattern}") @FormParam("referenceNo") String referenceNo, 
            @FormParam("requestTitle") String requestTitle, 
            @Pattern(regexp = "^[0-9]{1}$", message = "{department.pattern}") @FormParam("department") String department, 
            @Pattern(regexp = "^([a-zA-Z0-9]+([ ][a-zA-Z0-9])*)+{12}+$", message = "{member.pattern}") @FormParam("member") String member,
            @Pattern(regexp = "^[A-Za-z0-9 &-_]*$", message = "{vendorName.pattern}") @FormParam("publisher") String publisher, 
            @Pattern(regexp = "^[A-Za-z0-9 &-_]*$", message = "{vendorName.pattern}") @FormParam("supplier") String supplier,  
            @Pattern(regexp = "^[a-zA-Z]{1}$", message = "{frequency.pattern}") @FormParam("frequency") String frequency, 
            @FormParam("subscritionDateFrom") String subscritionDateFrom, 
            @FormParam("subscritionDateTo") String subscritionDateTo, 
            @FormParam("ISSN") String ISSN, 
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("requestedCopies") String requestedCopies, 
            @Pattern(regexp = "^[0-9]{3}$", message = "{physicalMedia.pattern}") @FormParam("physicalMedia") String physicalMedia,
            @Pattern(regexp = "^[0-9]{3}$", message = "{subject.pattern}") @FormParam("subject") String subject, 
            @Pattern(regexp = "^[a-zA-Z]{1}$", message = "{edition.pattern}") @FormParam("edition") String edition, 
            @Pattern(regexp = "^[0-9]{1,3}$", message = "{budget.pattern}") @FormParam("budget") String budget,
            @Pattern(regexp = "^[a-zA-Z]{3}$", message = "{currency.pattern}") @FormParam("currency") String currency, 
            @FormParam("originalPrice") String originalPrice, 
            @FormParam("remarks") String remarks) {
        
        SRequest s_request = new SRequest();
        
        
        try {
            s_request.setSRDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(requestDate));
            s_request.setSRFromDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(subscritionDateFrom));
            s_request.setSRToDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(subscritionDateTo));
        } catch (ParseException ex) {
            Logger.getLogger(SRequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        BigDecimal price = new BigDecimal(originalPrice);
        MFcltydept dept = mFcltydeptFacadeREST.find(Integer.parseInt(department));
        SSupplierDetail supplierCode = sSuppDetailFacadeREST.find(supplier);
        SSupplierDetail publisherCode = sSuppDetailFacadeREST.find(publisher);
        MMember memCode = mMemberFacadeREST.find(member);
        BkSubject bkSub = bkSubjectFacadeREST.find(subject);
        SFrequency sFrequ = sFrequencyFacadeREST.find(frequency);
        SEdition sEd = sEditionFacadeREST.find(edition);
        ABudget budgetCode = aBudgetFacadeREST.find(Integer.parseInt(budget));
        Libmaterials phyCd = libmaterialsFacadeREST.find(physicalMedia);
        ACurrency curr = aCurrencyFacadeREST.find(currency);    
        
        if (dept == null) 
          //  throw new DataException("No publisher found with publisher code : " + department);
        System.out.println("Department COde: " + dept);
            s_request.setSRDeptCd(dept);

        try {
            memCode = mMemberFacadeREST.find(member);
            String memDept = memCode.getMemDept().getFcltydeptcd();
            String reqstedDept = dept.getFcltydeptcd();
            if (mMemberFacadeREST.CheckMemberAvalibility(member)) {
                System.out.println("Member Code: " + memCode.getMemCd() + "Member Name: " + memCode.getGrentrFirstnm());
                if (memDept == reqstedDept) {
                    s_request.setSRNm(memCode);
                } else {
                    return "Member doesnot belong to \"" + dept.getFcltydeptdscr() + "\" department";
                }
            } else {
                return "Invalid Member Code";
            }
        } catch (NullPointerException e) {
            return "Invalid department code.";
        }
        
        if (publisherCode == null) 
          //  throw new DataException("No publisher found with publisher code : " + publisher);
        System.out.println("Publisher COde: " + publisherCode);
        s_request.setSRPubCd(publisherCode);
        
        if (supplierCode == null) 
          //  throw new DataException("No supplier found with supplier code : " + supplier);
        System.out.println("Supplier Code: " + supplierCode);
        s_request.setSRSupCd(supplierCode);
        
        if (sFrequ == null) 
          //  throw new DataException("No frequency found with frequency code : " + frequency);
        System.out.println("Frequency Code: " + sFrequ + "Frequency Name: "+sFrequ.getSFNm());
        s_request.setSRFreqCd(sFrequ);
  
        if (bkSub == null) 
          //  throw new DataException("No subject found with subjectCode : " + subject);
        System.out.println("Supplier Code: " + bkSub);
        s_request.setSRSubCd(bkSub);
          
        if (edition == null) 
         //   throw new DataException("No edition found with editionCode : " + edition);
        System.out.println("Edition: " + sEd);
        s_request.setSREdition(sEd);      
        
        if(phyCd == null)
          //  throw new DataException("No media found with mediaCode : " + physicalMedia);
        System.out.println("Supplier COde: " + phyCd);
        s_request.setSRPhycd(phyCd);

        if (budgetCode == null) 
          //  throw new DataException("No budget found with budgetCode : " + budget);
        System.out.println("Supplier COde: " + budgetCode);
        s_request.setSRBudgetCd(budgetCode);
        
        BigDecimal calculatedPrice = curr.getACRate().multiply(price);
        s_request.setSRTitle(requestTitle);             
        s_request.setSRPubCity(s_request.getSRPubCd().getASCity());
        s_request.setSRPubCountry(s_request.getSRPubCd().getASCountry());  
        s_request.setSRSupCity(s_request.getSRSupCd().getASCity());
        s_request.setSRSupCountry(s_request.getSRSupCd().getASCountry());   
        s_request.setSRIssn(ISSN);     
        s_request.setSRReqCopy(Integer.parseInt(requestedCopies));
        s_request.setSRRefno(referenceNo);    
        s_request.setSRStatus("P");
        s_request.setSRPrice(price);
        s_request.setSRRemark(remarks);
        s_request.setSRCurrencyCd(curr);
        BigDecimal convRate = curr.getACRate();
        s_request.setSRConvRate(convRate);
        //count = Integer.parseInt(countREST());
        //create(s_request);
        count = super.count();
        create(s_request);
        if (count == super.count()) {
            output = "Someting went wrong, Request is not saved.";
        } else {
            output = "Request made with RequestID: " + s_request.getSRNo() + ".";
        }
        return output;
    }
    
    @PUT
    @Path("updateRequest")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    //@Produces("text/plain")
    public String updateRequest(@Pattern(regexp = "^[0-9]*$", message = "{requestId.pattern}") @FormParam("requestId") String requestId, 
            @FormParam("requestDate") String requestDate, 
            @Pattern(regexp = "^[0-9]*$", message = "{requestId.pattern}") @FormParam("referenceNo") String referenceNo, 
            @FormParam("requestTitle") String requestTitle, 
            @Pattern(regexp = "^[0-9]{1}$", message = "{department.pattern}") @FormParam("department") String department, 
            @Pattern(regexp = "^([a-zA-Z0-9]+([ ][a-zA-Z0-9])*)+{12}+$", message = "{member.pattern}") @FormParam("member") String member,
            @Pattern(regexp = "^[A-Za-z0-9 &-_]*$", message = "{vendorName.pattern}") @FormParam("publisher") String publisher, 
            @Pattern(regexp = "^[A-Za-z0-9 &-_]*$", message = "{vendorName.pattern}") @FormParam("supplier") String supplier,  
            @Pattern(regexp = "^[a-zA-Z]{1}$", message = "{frequency.pattern}") @FormParam("frequency") String frequency, 
            @FormParam("subscritionDateFrom") String subscritionDateFrom, 
            @FormParam("subscritionDateTo") String subscritionDateTo, 
            @FormParam("issn") String ISSN, 
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("requestedCopies") String requestedCopies, 
            @Pattern(regexp = "^[0-9]{3}$", message = "{physicalMedia.pattern}") @FormParam("physicalMedia") String physicalMedia,
            @Pattern(regexp = "^[0-9]{3}$", message = "{subject.pattern}") @FormParam("subject") String subject, 
            @Pattern(regexp = "^[a-zA-Z]{1}$", message = "{edition.pattern}") @FormParam("edition") String edition, 
            @Pattern(regexp = "^[0-9]{1,3}$", message = "{budget.pattern}") @FormParam("budget") String budget,
            @Pattern(regexp = "^[a-zA-Z]{3}$", message = "{currency.pattern}") @FormParam("currency") String currency, 
            @FormParam("originalPrice") String originalPrice, 
            @FormParam("remarks") String remarks) {
        
        SRequest s_request= null;
        try{
            s_request = find(Integer.parseInt(requestId));
        } catch (NullPointerException e){
            return  "Invalid request ID.";
        }
             
        try {
            s_request.setSRDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(requestDate));
            s_request.setSRFromDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(subscritionDateFrom));
            s_request.setSRToDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(subscritionDateTo));
        } catch (ParseException ex) {
            Logger.getLogger(SRequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        BigDecimal price = new BigDecimal(originalPrice);
        MFcltydept dept = mFcltydeptFacadeREST.find(Integer.parseInt(department));
        SSupplierDetail supplierCode = sSuppDetailFacadeREST.find(supplier);
        SSupplierDetail publisherCode = sSuppDetailFacadeREST.find(publisher);
        MMember memCode = mMemberFacadeREST.find(member);
        BkSubject bkSub = bkSubjectFacadeREST.find(subject);
        SFrequency sFrequ = sFrequencyFacadeREST.find(frequency);
        SEdition sEd = sEditionFacadeREST.find(edition);
        ABudget budgetCode = aBudgetFacadeREST.find(Integer.parseInt(budget));
        Libmaterials phyCd = libmaterialsFacadeREST.find(physicalMedia);
        ACurrency curr = aCurrencyFacadeREST.find(currency);    
        
        if (dept == null) 
          //  throw new DataException("No publisher found with publisher code : " + department);
        System.out.println("Department COde: " + dept);
            s_request.setSRDeptCd(dept);

        try {
            memCode = mMemberFacadeREST.find(member);
            String memDept = memCode.getMemDept().getFcltydeptcd();
            String reqstedDept = dept.getFcltydeptcd();
            if (mMemberFacadeREST.CheckMemberAvalibility(member)) {
                System.out.println("Member Code: " + memCode.getMemCd() + "Member Name: " + memCode.getGrentrFirstnm());
                if (memDept == reqstedDept) {
                    s_request.setSRNm(memCode);
                } else {
                    return "Member doesnot belong to \"" + dept.getFcltydeptdscr() + "\" department";
                }
            } else {
                return "Invalid Member Code";
            }
        } catch (NullPointerException e) {
            return "Invalid department code.";
        }
        
        if (publisherCode == null) 
           // throw new DataException("No publisher found with publisher code : " + publisher);
        System.out.println("Publisher COde: " + publisherCode);
        s_request.setSRPubCd(publisherCode);
        
        if (supplierCode == null) 
          //  throw new DataException("No supplier found with supplier code : " + supplier);
        System.out.println("Supplier Code: " + supplierCode);
        s_request.setSRSupCd(supplierCode);
        
        if (sFrequ == null) 
          //  throw new DataException("No frequency found with frequency code : " + frequency);
        System.out.println("Frequency Code: " + sFrequ + "Frequency Name: "+sFrequ.getSFNm());
        s_request.setSRFreqCd(sFrequ);
  
        if (bkSub == null) 
          //  throw new DataException("No subject found with subjectCode : " + subject);
        System.out.println("Supplier Code: " + bkSub);
        s_request.setSRSubCd(bkSub);
          
        if (edition == null) 
          //  throw new DataException("No edition found with editionCode : " + edition);
        System.out.println("Edition: " + sEd);
        s_request.setSREdition(sEd);      
        
        if(phyCd == null)
         //   throw new DataException("No media found with mediaCode : " + physicalMedia);
        System.out.println("Supplier COde: " + phyCd);
        s_request.setSRPhycd(phyCd);

        if (budgetCode == null) 
         //   throw new DataException("No budget found with budgetCode : " + budget);
        System.out.println("Supplier COde: " + budgetCode);
        s_request.setSRBudgetCd(budgetCode);
        
        BigDecimal calculatedPrice = curr.getACRate().multiply(price);
        s_request.setSRTitle(requestTitle);
        
        s_request.setSRPubCity(s_request.getSRPubCd().getASCity());
        s_request.setSRPubCountry(s_request.getSRPubCd().getASCountry());  
        s_request.setSRSupCity(s_request.getSRSupCd().getASCity());
        s_request.setSRSupCountry(s_request.getSRSupCd().getASCountry());   
        s_request.setSRIssn(ISSN);     
        s_request.setSRReqCopy(Integer.parseInt(requestedCopies));
        s_request.setSRRefno(referenceNo);    
        s_request.setSRStatus("P");
        s_request.setSRPrice(price);
        s_request.setSRRemark(remarks);
        s_request.setSRCurrencyCd(curr);
        BigDecimal convRate = curr.getACRate();
        s_request.setSRConvRate(convRate);
        count = super.count();
        super.edit(s_request);
        output = "Request with RequestID: " + requestId + " is now updated.";
        return output;
    }
    
    
    @DELETE
    @Path("delete/{requestNo}")
    @Produces("text/plain")
    public String delete(@PathParam("requestNo") String requestNo) {
        count = Integer.parseInt(countREST());
        remove(Integer.parseInt(requestNo));
        if (count == Integer.parseInt(countREST())) {
            output = "Someting went wrong record is not deleted!";
        } else {
            output = "OK";
        }
        return output;
    }

    @PUT
    @Path("sendForApproval")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String sendForApproval(@FormParam("requestIds") String request_Ids) {
        String[] requestIds = request_Ids.split(",");
        for (int i = 0; i < requestIds.length; i++) {
            sRequest = find(Integer.parseInt(requestIds[i]));
            sRequest.setSRStatus("S");
            edit(sRequest);
        }
        return requestIds.length + " sent for approval.";
    }

    @PUT
    @Path("Approve")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String approve(@Pattern(regexp = "^[0-9]*$", message = "{requestId.pattern}") @FormParam("requestNo") String requestNo, 
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("approveCopies") String approveCopies,
            @FormParam("authorisedBy") String authorisedBy, 
            @FormParam("remarks") String remarks,
            @FormParam("date") String date) {
        try {
            sRequest = find(Integer.parseInt(requestNo));
        } catch (NullPointerException e) {
            return "Incvalid requestID: " + requestNo;
        }

        int remainedReqCopies = sRequest.getSRReqCopy() - Integer.parseInt(approveCopies);
        sRequest.setSRStatus("A");
        sRequest.setSRApprCopy(Integer.parseInt(approveCopies));
        sRequest.setSRApprBy(authorisedBy);
        sRequest.setSRRemark(remarks);
        try {
            sRequest.setSRApprDt(new SimpleDateFormat("YYYY-DD-MM HH:mm:ss").parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(SRequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        edit(sRequest);
        return sRequest.getSRApprCopy() + " copies approved for request with request no. " + requestNo + ".";
    }

    @PUT
    @Path("Reject")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String reject(@Pattern(regexp = "^[0-9]*$", message = "{requestId.pattern}") @FormParam("requestNo") String requestNo, 
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("approveCopies") String approveCopies,
            @FormParam("authorisedBy") String authorisedBy, @FormParam("remarks") String remarks,
            @FormParam("date") String date) {
        sRequest = find(Integer.parseInt(requestNo));
        sRequest.setSRStatus("R");
        sRequest.setSRApprCopy(Integer.parseInt(approveCopies));
        sRequest.setSRApprBy(authorisedBy);
        sRequest.setSRRemark(remarks);
        try {
            sRequest.setSRApprDt(new SimpleDateFormat("YYYY-DD-MM HH:mm:ss").parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(SRequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        edit(sRequest);
        return "Request with request no. " + requestNo + " rejected.";
    }

    /////////////////////////////// merge begin////////////////////////////////////////////
    @PUT
    @Path("MergeInToDatabase")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String merge(Form form, @Pattern(regexp = "^[0-9,]*$", message = "{requestId.pattern}") @FormParam("requestIds") String request_Ids, 
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("approveCopies") String approveCopies,
            @FormParam("authorisedBy") String authorisedBy, @FormParam("remarks") String remarks,
            @FormParam("date") String date) {
        String[] requestNos = request_Ids.split(",");
        List<String> approvedRecNos = new ArrayList<>();
        String notApprovedRecNos = "";
        String invalidRecNos = "";
        String recIds = "";
        String message = "Merging Status of current session:\n\n\n" + "Request No:   Record No:";
             
        
        
        for (int i = 0; i < requestNos.length; i++) {
            try {
                sRequest = find(Integer.parseInt(requestNos[i]));
                if (sRequest.getSRStatus().contentEquals("M") || !sRequest.getSRStatus().contentEquals("A")) {
                    notApprovedRecNos = notApprovedRecNos + " " + sRequest.getSRNo() + ",";
                    approvedRecNos.add("null");
                    
                    System.out.println("i "+i);
                } else {
                    System.out.println("sRequest" + sRequest);
                    sMst = getSMst(sRequest);
                    System.out.println("called...2");
                    recIds = getRecId(sRequest);
                    sMst.setSRecid(recIds);
                    System.out.println("RecId: " + recIds);
                    sMstFacadeREST.create(sMst);

                    //Entry in Biblidetails Table
                    biblidetails = createBiblidetails(sMst);

                    sMst = sMstFacadeREST.find(recIds);
                    sMst.setSCatRecid(Integer.toString(biblidetails.getBiblidetailsPK().getRecID()));

                    sRequest.setSRStatus("M");
                    sRequest.setSRRecid(recIds);
                    edit(sRequest);
                    approvedRecNos.add(String.valueOf(sRequest.getSRNo()));
                    System.out.println("i "+i+sRequest.getSRNo());
                    System.out.println(approvedRecNos);
                    message = message + "\n" + "    " + approvedRecNos.get(i) +"  " + biblidetails.getBiblidetailsPK().getRecID();
                }
            } catch (NullPointerException e) {
                invalidRecNos = invalidRecNos + " " + requestNos[i] + ",";
                approvedRecNos.add("null");
            }

        }
        //Do entry in Location and Biblidetails
        System.out.println("Approved Request no(s): " + approvedRecNos);
        System.out.println("Not Approved Request no(s): " + notApprovedRecNos);
        System.out.println("Invalid Request no(s): " + invalidRecNos);
        //return "Approved Request no(s): " +approvedRecNos;
        return message + "\nNot Approved Record Id(s): "+notApprovedRecNos+"\nInvalid Record Id(s): "+invalidRecNos;
    }

    private SMst getSMst(SRequest sRequest) {
        SMst mst = new SMst();
        System.out.println("called...");
        mst.setSReqNo(sRequest);
        mst.setSDeptCd(sRequest.getSRDeptCd().getFcltydeptcd().toString());
        mst.setSStatus(sRequest.getSRStatus());
        mst.setSStDt(sRequest.getSRFromDt());
        mst.setSEnDt(sRequest.getSRToDt());
        mst.setSTitle(sRequest.getSRTitle());
        mst.setSPubCd(sRequest.getSRPubCd().getASCd());
        mst.setSSupCd(sRequest.getSRSupCd().getASCd());
        mst.setSBugetCd(sRequest.getSRBudgetCd().getABBudgetCode().toString());
        mst.setSPrice(sRequest.getSRPrice());
        mst.setSCurrency(sRequest.getSRCurrencyCd().getACCd());
        mst.setSConvRate(sRequest.getSRConvRate());
        mst.setSApprCopy(sRequest.getSRApprCopy());
        mst.setSPubCountry(sRequest.getSRPubCd().getASCountry());
        mst.setSPubCity(sRequest.getSRPubCity());
        mst.setSSupCountry(sRequest.getSRSupCountry());
        mst.setSSupCity(sRequest.getSRSupCity());
        mst.setSReqCopy(sRequest.getSRReqCopy());
        mst.setSIssn(sRequest.getSRIssn());
        mst.setSFreqCd(sRequest.getSRFreqCd().getSFCd());
        mst.setSSubCd(sRequest.getSRSubCd().getBkClassno());
        mst.setSEdition(sRequest.getSREdition().getSEdType());
        //mst.setSRecid(null);      RecId will be set in main function whcih calls this function
        mst.setSNote(sRequest.getSRRemark());
        mst.setSTyAcqu("S");
        mst.setSDirect("D");
        mst.setSMstStatus("M");
        System.out.println("called...1");
        return mst;
    }

    private String getRecId(SRequest sRequest) {
        String recId = "";
        recId = sRequest.getSRTitle().substring(0, 1);
        System.out.println("RecId:"+ recId);
        List<SMst> mstList;
        mstList = sMstFacadeREST.findBy("findByMaxRecId", recId);
        if (mstList.size() > 0) {
            recId = recId + String.format("%04d", Integer.parseInt(mstList.get(0).getSRecid().substring(1)) + 1);
        } else {
            recId = recId + String.format("%04d", 1);
        }

        return recId;
    }

    private Biblidetails getBiblidetails(int recId, String tag, int tagSrNo, String sbFld, int sbFldSrNo, String ind, String fValue) {
        Biblidetails biblidetails = new Biblidetails();
        BiblidetailsPK biblidetailsPK = new BiblidetailsPK();

        biblidetailsPK.setRecID(recId);
        biblidetailsPK.setTag(tag);
        biblidetailsPK.setTagSrNo(tagSrNo);
        biblidetailsPK.setSbFld(sbFld);
        biblidetailsPK.setSbFldSrNo(sbFldSrNo);

        biblidetails.setInd(ind);
        biblidetails.setFValue(fValue);
        biblidetails.setBiblidetailsPK(biblidetailsPK);

        return biblidetails;

    }

    private Biblidetails createBiblidetails(SMst sMst) {
        Biblidetails biblidetails = new Biblidetails();
        List<Biblidetails> maxBibList = new ArrayList<>();
        int recId;
        //ObjectFactory objectFactory = new ObjectFactory();

        maxBibList = biblidetailsFacadeREST.findBy("findByMaxRecID", "NULL");
        if (maxBibList.isEmpty()) {
            recId = 1;
        } else {
            recId = maxBibList.get(0).getBiblidetailsPK().getRecID() + 1;
        }

        //getBiblidetails(int recId, String tag, int tagSrNo, String sbFld, int sbFldSrNo, String ind, String fValue)
        //                           recId,  tag, tagSrNo, sbFld, sbFldSrNo, ind, fValue)
        biblidetails = getBiblidetails(recId, "000", 1, "", 0, null, "     nc  a22     1u 4500");
        biblidetailsFacadeREST.create(biblidetails);
        
        
        //fixfield value
        StringBuilder fxdFld = new StringBuilder();
        for (int i = 0; i < 1; i++) {
            fxdFld.append("000000i00000000000bn1mabcabcf||||h000000");
        }
        System.out.println("fxdFld: "+fxdFld);
        String strDateYear = "";
        String CountryCode = "";
        String langcode = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat monthFormat= new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat= new SimpleDateFormat("dd");
        SimpleDateFormat yearFormat= new SimpleDateFormat("yy");
        String month = monthFormat.format(cal.getTime());
        String day = dayFormat.format(cal.getTime());
        String year = yearFormat.format(cal.getTime());
        String currDate = (year+""+month+""+day);
        
        fxdFld.delete(0, 6);
        System.out.println("fxdFld: "+fxdFld);
        fxdFld.insert(0, currDate);
        System.out.println("fxdFld: "+fxdFld);
        
        if(!strDateYear.equals("")){
            fxdFld.delete(7, 11);
            fxdFld.insert(7,strDateYear);
        }
        System.out.println("fxdFld: "+fxdFld);
        if(!CountryCode.equals("")){
            fxdFld.delete(15, 18);
            fxdFld.insert(15,CountryCode);
        }
        System.out.println("fxdFld: "+fxdFld);
        if(!langcode.equals("")){
            fxdFld.delete(35, 38);
            fxdFld.insert(35,langcode);
        }
        System.out.println("fxdFld: "+fxdFld);
        String fixedField = fxdFld.toString();
        biblidetails = getBiblidetails(recId, "008", 1, "", 0, null, fixedField);
        biblidetailsFacadeREST.create(biblidetails);

        if (sMst.getSCoden() != null) {
            biblidetails = getBiblidetails(recId, "030", 1, "", 0, null, sMst.getSCoden());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (sMst.getSIssn() != null) {
            biblidetails = getBiblidetails(recId, "022", 1, "a", 1, null, sMst.getSIssn());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (sMst.getSAbrTitle() != null) {
            biblidetails = getBiblidetails(recId, "210", 1, "a", 1, "0", sMst.getSAbrTitle());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (sMst.getSTitle() != null) {
            biblidetails = getBiblidetails(recId, "245", 1, "a", 1, "00", sMst.getSTitle());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (sMst.getSTitle() != null) {
            biblidetails = getBiblidetails(recId, "222", 1, "a", 1, "00", sMst.getSTitle());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (sMst.getSCoden() != null) {
            String tag222 = sMst.getSTitle() + " " + sMst.getSCoden();
            biblidetails = getBiblidetails(recId, "222", 1, "b", 1, "0", tag222);
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (sMst.getSNote() != null) {
            biblidetails = getBiblidetails(recId, "246", 1, "", 0, null, sMst.getSNote());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (sMst.getSUrl() != null) {
            biblidetails = getBiblidetails(recId, "256", 1, "", 0, null, sMst.getSUrl());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (sMst.getSReqNo().getSRPubCd() != null) {
            biblidetails = getBiblidetails(recId, "260", 1, "b", 1, null, sMst.getSReqNo().getSRPhycd().getDescription());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (sMst.getSPubCity() != null) {
            biblidetails = getBiblidetails(recId, "260", 1, "a", 1, null, sMst.getSPubCity());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (sMst.getSEdition() != null) {
            biblidetails = getBiblidetails(recId, "250", 1, "a", 0, null, sMst.getSEdition());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (sMst.getSReqNo().getSRFreqCd() != null) {
            biblidetails = getBiblidetails(recId, "310", 1, "a", 1, null, sMst.getSReqNo().getSRFreqCd().getSFNm());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (sMst.getSSubCd() != null) {
            biblidetails = getBiblidetails(recId, "630", 1, "a", 1, null, sMst.getSSubCd());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (sMst.getSUrl() != null) {
            biblidetails = getBiblidetails(recId, "856", 1, "u", 1, null, sMst.getSUrl());
            biblidetailsFacadeREST.create(biblidetails);
        }

        return biblidetails;
    }

    //////////////////////////////////merge - end////////////////////////////////////////////// 
}
