/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.acquisition.suggestions.service;

//import ExceptionService.DataException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.ValidationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import soul.acquisition.suggestions.AAccession;
import soul.acquisition.suggestions.ARequest;
import soul.circulation.MMember;
import soul.circulation.service.MMemberFacadeREST;
import soul.general_master.ABudget;
import soul.general_master.ACurrency;
import soul.general_master.ASupplierDetail;
import soul.general_master.Libmaterials;
import soul.general_master.MFcltydept;
import soul.general_master.service.ABudgetFacadeREST;
import soul.general_master.service.ACurrencyFacadeREST;
import soul.general_master.service.ASupplierDetailFacadeREST;
import soul.general_master.service.LibmaterialsFacadeREST;
import soul.general_master.service.MFcltydeptFacadeREST;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.acquisition.suggestions.arequest")
public class ARequestFacadeREST extends AbstractFacade<ARequest> {
    @EJB
    private MFcltydeptFacadeREST mFcltydeptFacadeREST;
    @EJB
    private MMemberFacadeREST mMemberFacadeREST;
    @EJB
    private ASupplierDetailFacadeREST aSupplierDetailFacadeREST;
    @EJB
    private LibmaterialsFacadeREST  libmaterialsFacadeREST;
    @EJB
    private ACurrencyFacadeREST aCurrencyFacadeREST;
    @EJB
    private ABudgetFacadeREST aBudgetFacadeREST;
    @EJB
    private AAccessionFacadeREST aAccessionFacadeREST;
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output = "";
    MMember mmember;
    ASupplierDetail aSupplierDetail;
    Libmaterials libmaterials;
    ACurrency aCurrency;
    ABudget aBudget;
    MFcltydept dept;
    ARequest req;
   
    public ARequestFacadeREST() {
        super(ARequest.class);
    }
 private static final String text = "Message from Server :\n%s";
    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(ARequest entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(ARequest entity) {
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
    public ARequest find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<ARequest> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<ARequest> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
  //  @GET
  //  @Path("by/{namedQuery}/{attrValue}")
  //  @Produces({"application/xml", "application/json"})
    public List<ARequest> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        List<String> status = new ArrayList<>();
        
        status.addAll(Arrays.asList(valueString[0].split("|")));
        
        switch(query)
        {
            case "findRequestedOnApproval":   return super.findBy("ARequest."+query);
                
            case "findNotApproved": valueList.add(status);
                                    break;    
                
            case "findByARIsbn":    valueList.add(0, valueString[0]);
                                    break;
            default:    valueList.add(status);
        }
        return super.findBy("ARequest."+query, valueList);
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
    @GET  // used in request listing in report 
    @Path("getRequestListByParam/{paramName}/{paramValue}")
    @Produces({"application/xml", "application/json"})
    public List<ARequest> getRequestListByParam(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) 
    {
        String[] valueString = paramValue.split(",");
        List<Object> valueList = new ArrayList<>();
        List<ARequest> getRequestList = null;
        switch(paramName){
            case "RequestNo":
                valueList.add(Integer.parseInt(valueString[0]));
                getRequestList = super.findBy("ARequest.findByARNo", valueList );
                break;
            case "RequestedBy": 
                String memName = valueString[0];
                String firstName = valueString[0].split(" ")[0];
                String lastName = valueString[0].split(" ")[1];
                valueList.add(firstName);
                valueList.add(lastName);
                getRequestList = super.findBy("ARequest.findByMemberName" , valueList);
                break;
            case "Department":
                valueList.add(Integer.parseInt(valueString[0]));
                getRequestList = super.findBy("ARequest.findByDeptCd" , valueList);
                break;
            case "Budget":
                valueList.add(valueString[0]);
                getRequestList = super.findBy("ARequest.findByBudget" , valueList);
                break;
            case "RejectedRequestByDate":
                try {
                    valueList.add(dateFormat.parse(valueString[0]));
                } catch (ParseException ex) {
                    Logger.getLogger(ARequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                getRequestList = super.findBy("ARequest.findRejectedRequestByDate" , valueList);
                break;
            case "Status":
                valueList.add(valueString[0]);
                getRequestList = super.findBy("ARequest.findByARStatus" , valueList);
                break;
            case "MemberCode":
                valueList.add(valueString[0]);
                getRequestList = super.findBy("ARequest.findByMemberCode" , valueList);
                break;
            case "SupplierCode":
                valueList.add(valueString[0]);
                getRequestList = super.findBy("ARequest.findBySupplierCd" , valueList);
                break;
             case "PublisherCode":
                valueList.add(valueString[0]);
                getRequestList = super.findBy("ARequest.findByPublisherCd" , valueList);
                break;
             case "Currency":
                valueList.add(valueString[0]);
                getRequestList = super.findBy("ARequest.findByCurrencyCd" , valueList);
                break;
                
        }
        return  getRequestList;
    }
    
    
    @GET
    @Path("getRequestForNewOrderByParam/{paramName}/{paramValue}")
    @Produces({"application/xml", "application/json"})
    public List<ARequest> getApprovedRequestListByParam(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) 
    {
        String[] valueString = paramValue.split(",");
        List<Object> valueList = new ArrayList<>();
        List<ARequest> getRequestList = null;
        switch(paramName){
            case "department":
                valueList.add(Integer.parseInt(valueString[0]));
                getRequestList = super.findBy("ARequest.findApprovedReqByDept", valueList );
                break;
            case "supplier":
                valueList.add(valueString[0]);
                getRequestList = super.findBy("ARequest.findApprovedReqBySupplier", valueList );
                break;
            case "budget":
                valueList.add(Integer.parseInt(valueString[0]));
                getRequestList = super.findBy("ARequest.findApprovedReqByBudget", valueList );
                break;
    }
        return getRequestList;
    }
    
    @GET
    @Path("getRequestForNewOrder")
    @Produces({"application/xml", "application/json"})
    public List<ARequest> getApprovedRequestList() 
    {
        List<ARequest> getRequestList = null;
        getRequestList = findBy("findByARStatus", "A|J");
        return getRequestList;
    }
    
    
    @GET
    @Path("retrieveAll/{form}")
    @Produces({"application/xml", "application/json"})
   // public List<ARequest> retrieveAll(@QueryParam("form") String form) 
    public List<ARequest> retrieveAll(@PathParam("form") String form)        
    {
        // if(accept.equals("XML"))
         //{
            List<ARequest> getAll = null;
            switch (form) {
                case "Request":
                    getAll = findBy("findRequestedOnApproval", "null");
                    break;
                case "Gratis":
                    getAll = findBy("findByARStatus", "G");
                    break;
                case "Direct":
                    getAll = findBy("findByARStatus", "A");
                    break;
                case "DuplicateCheck":
                    getAll = findAll();
                    break;
                case "selectForApproval": //status = "R" 
                    getAll = findBy("findByARStatus", "R");
                     break;
                case "approvalProcess": //status = "R" 
                    getAll = findBy("findNotApproved", "B|O");
                     break;
                case "deleteRequest": // E for rejected
                    getAll = findBy("findByARStatus", "E");
                     break;
                case "newOrder": // status approved|
                    getAll = findBy("findByARStatus", "A|J");
                     break;
            }
            return getAll;
    }
    
    
    
    @GET
    @Path("retrieveAllRequestByType/{requestType}")
    @Produces({"application/xml", "application/json"})
    public List<ARequest> retrieveAllRequestByType(@PathParam("requestType") String requestType) 
    {
        // if(accept.equals("XML"))
         //{
            List<ARequest> getAll = null;
            switch (requestType) {
                case "Request":
                    getAll = findBy("findRequestedOnApproval", "null");
                    break;
                case "Gratis":
                    getAll = findBy("findByARStatus", "G");
                    break;
                case "Direct":
                    getAll = findBy("findByARStatus", "A");
                    break;
                case "DuplicateCheck":
                    getAll = findAll();
                    break;
                case "selectForApproval": //status = "R" 
                    getAll = findBy("findByARStatus", "R");
                     break;
                case "approvalProcess": //status = "R" 
                    getAll = findBy("findNotApproved", "B|O");
                     break;
                case "deleteRequest": // E for rejected
                    getAll = findBy("findByARStatus", "E");
                     break;
                case "newOrder": // status approved|
                    getAll = findBy("findByARStatus", "A|J");
                     break;
            }
            return getAll;
    }
    
            
    @GET
    @Path("getRequestListForApprovalProcessByParam/{paramName}/{paramValue}")
    @Produces({"application/xml", "application/json"})
    public List<ARequest> getRequestListForApprovalProcessByParam(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) 
    {
        String[] valueString = paramValue.split(",");
        List<Object> valueList = new ArrayList<>();
        List<ARequest> getRequestList = null;
        switch(paramName){
            case "department":
                valueList.add(Integer.parseInt(valueString[0]));
                getRequestList = super.findBy("ARequest.findForApprovalReqByDept", valueList );
                break;
            case "budget":
                valueList.add(Integer.parseInt(valueString[0]));
                getRequestList = super.findBy("ARequest.findForApprovalReqByBudget", valueList );
                break;
    }
        return getRequestList;
    }
    
    @DELETE
    @Path("deleteARequest/{requestNo}")
   // @Consumes({"application/x-www-form-urlencoded"})
    @Produces({"text/plain"})
    public String deleteARequest(@PathParam("requestNo") String requestNo)
     {
        req = find(Integer.parseInt(requestNo));
        if(req == null)
        {
           //  throw new DataException("No request found with requestNo : " + requestNo);
        }
       int  count = Integer.parseInt(countREST());
       remove(Integer.parseInt(requestNo));
       if(count == Integer.parseInt(countREST()))
        {
           output =  "Someting went wrong record is not deleted!";
        }
       else
        {
            output ="OK";
        }
        return output;
      }
  
    
    @DELETE  // used in delete req to delete rejected request
    @Path("deleteRejectedRequest/{requestNumbers}")
    @Consumes({"application/x-www-form-urlencoded"})
   // @Produces({"text/plain"})
    public String deleteRejectedRequest(@Pattern(regexp = "^\\d+(\\,\\d+\\,?)*$", message = "{reqNos.pattern}") @PathParam("requestNumbers") String request_Numbers) {
        String[] requestNumbers = request_Numbers.split(",");
        List<String> selectedList = new ArrayList<>();
        List<String> notSelectedList = new ArrayList<>();


        for (int i = 0; i < requestNumbers.length; i++) {

            req = find(Integer.parseInt(requestNumbers[i]));

            if (req == null) {  // check for request no. validity
             //   throw new DataException("invalid requestNo : " + requestNumbers[i]);
            }

            if (req.getARStatus().contentEquals("E")) {
                remove(Integer.parseInt(requestNumbers[i]));
                selectedList.add(requestNumbers[i]);
            } else {
                notSelectedList.add(requestNumbers[i]);
            }
        }
        if (!selectedList.isEmpty()) {
            output = " Request(s) " + StringUtils.join(selectedList, ",") + " deleted";
        }
        if (!notSelectedList.isEmpty()) {
            output = output + "\n Following request(s) : " + StringUtils.join(notSelectedList, ",") + " cannot be deleted";
        }

        return output;
    }
    
    
    @GET 
    @Path("checkISBN/{isbn}")
    @Produces({"text/plain"})
   // public String checkISBN(@QueryParam("isbn") String isbn) 
    public String checkISBN(@PathParam("isbn") String isbn)        
    {  String output1 = "";
       List<ARequest>  requestList = findBy("findByARIsbn", isbn);
         for (Iterator<ARequest> it = requestList.iterator(); it.hasNext();) 
         {
            ARequest  req  = it.next();
            String by = req.getARGiftedBy() == null ? ", Requester: "+req.getARNm().getMemFirstnm()+" "+req.getARNm().getMemLstnm() : ", Gifted By: "+req.getARGiftedBy();
             output1 = output1+"\nRequest No: "+req.getARNo()+by+", Status: "+req.getARStatus()+"\n";
         }
        if(!output1.equals(""))
            {
                output1 = "Following requests are already made for ISBN: "+isbn+"\n"+output1;
            }
        else
            {
                output1 = "Not Found";
            }
        return output1;
    }
    
    @POST
    @Path("createRequest")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createRequest(@FormParam("referenceNo") String referenceNo,
    @FormParam("date") String date,@FormParam("department") String department,
    @FormParam("reqStatus") String reqStatus,@FormParam("giftedBy") String giftedBy,
    @FormParam("firstName") String firstName,@FormParam("supplier") String supplier,
    @FormParam("publisher") String publisher,@FormParam("materialType") String materialType,
    @FormParam("isbn") String isbn,@FormParam("edition") String edition,
    @FormParam("currency") String currency,@FormParam("price") String price,
    @FormParam("copies") String copies,@FormParam("title") String title,
    @FormParam("year") String year,@FormParam("rate") String rate,
    @FormParam("ignoreBud") String ignore_Bud,@FormParam("form") String form,
    @FormParam("budget") String budget,@FormParam("approvedBy") String approvedBy,
    @FormParam("apprCopies") String apprCopies,@FormParam("apprDate") String apprDate,
    @FormParam("requestedBy") String requestedBy,@FormParam("lastName") String lastName,
    @FormParam("remarks") String remarks)
      {
        ARequest   req = new ARequest();
         //req.setARNo(Integer.parseInt(request.getParameter("requestNo")));
        req.setARRefNo(referenceNo);
        try {
            req.setARDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(ARequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            output = "Please enter date in the format yyyy-MM-dd";
        }
        if(department != null)
        req.setARDeptCd(mFcltydeptFacadeREST.find(Integer.parseInt(department)));
        String status = reqStatus;
        switch(status)
        {
            case "requested":   status = "R";
                                break;
            case "approval":    status = "O";
                                break;    
            case "Gifted":      status = "G";
                                break;
            case "Approved":    status = "A";
                                break;    
        }
        req.setARStatus(status);
        //For Gratis item, giftedBy Column is set in second column
        if(requestedBy != null)
        {
            req.setARNm(mMemberFacadeREST.find(requestedBy));
        }
        req.setARGiftedBy(giftedBy);
        //&*****************************
        if(supplier != null)
        {
            req.setARSupCd(aSupplierDetailFacadeREST.find(supplier));
        }
        req.setARTitle(title);
        req.setARFname(firstName);
        req.setARAuthor(lastName);
        req.setARPubCd(aSupplierDetailFacadeREST.find(publisher));
        req.setARPhycd(libmaterialsFacadeREST.find(materialType));
        req.setARIsbn(isbn);
        req.setAREdition(edition);
        req.setARYear(year);
        req.setARCurrencyCd(aCurrencyFacadeREST.find(currency));
        if(!price.trim().equals(""))
            req.setARPrice(new BigDecimal(price));
        else
            req.setARPrice(null);
        if(!copies.trim().equals(""))
            req.setARRequestCopy(new Short(copies));
        else
            req.setARRequestCopy(null);
        if(!rate.trim().equals(""))    
            req.setARConvRate(new BigDecimal(rate));
        else
            req.setARConvRate(null);
        if(ignore_Bud != null)
        {
            String ignoreBud[] = ignore_Bud.split(",");
            if(ignoreBud == null)
                req.setARBudgetCd(aBudgetFacadeREST.find(Integer.parseInt(budget)));
            else
                req.setARBudgetCd(null);
        }
        req.setARRemark(remarks);
        
        if(form.equals("Direct")||form.equals("DirectOrder"))
        {
            req.setARApprBy(approvedBy);
            if(!apprCopies.trim().equals(""))    
                req.setARApprCopy(new Short(apprCopies));
            else
                req.setARApprCopy(null);
            try {
                req.setARApprDt(new SimpleDateFormat("yyyy-MM-dd").parse(apprDate));
            } catch (ParseException ex) {
                Logger.getLogger(ARequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            req.setARApprRemark(remarks);
        }
      //      req = getRequest(request);
            int count = Integer.parseInt(countREST());
            req = createAndGet(req);
            if(count == Integer.parseInt(countREST()))
            {
                output = "Someting went wrong Request is not created!";
            }
            else
            {
                output = "Request Created.";
            }
            switch (form) {
                case "Request":
                    //response.sendRedirect("jsp/request.jsp");
                    output =  output;
                    break;
                case "Gratis":
                    //response.sendRedirect("jsp/gratisItem.jsp");
                    output =  output;
                    break;
                case "Direct":
                    //response.sendRedirect("jsp/directApproval.jsp");
                    output =  output;
                    break;
                case "DirectOrder":
                    output = String.valueOf(req.getARNo());
                    break;
            }
             return output;
         }
    
    
    //used for new request
    @POST
    @Path("createNewRequest")
    @Consumes({"application/x-www-form-urlencoded"})
    public Response  createNewRequest(@FormParam("referenceNo") String referenceNo,
            @FormParam("date") String date, 
            @Pattern(regexp = "[0-9]+", message = "{dept.id.pattern}") @FormParam("departmentCode")  String department,
            @Pattern(regexp = "(requested|approval)", message = "{reqStatus.pattern}") @FormParam("reqStatus") String reqStatus,
            @FormParam("supplier") String supplier,
            @NotNull(message = "{publisher.id.notnull}") @FormParam("publisher") String publisher,
            @NotNull(message = "{materialType.id.notnull}") @FormParam("materialType") String materialType,
            @Pattern(regexp = "[0-9]+",message = "isbn.valid") @FormParam("isbn") String isbn, 
            @NotNull(message = "{edition.notnull}") @FormParam("edition") String edition,
            @Pattern(regexp = "[A-Z]+", message = "{currency.id.pattern}") @FormParam("currency") String currency,
            @Pattern(regexp = "^(\\d*\\.)?\\d+$", message = "{price.valid}")@FormParam("price") String price,
            @NotNull(message = "{copies.notnull}") @FormParam("copies") String copies, 
            @NotNull(message = "{title.notnull}") @FormParam("title") String title,
            @Size(min=4, max=4) @Pattern(regexp = "[0-9]+",message = "year.valid") @FormParam("year") String year, @FormParam("rate") String rate,
            @Pattern(regexp="(true|false)" , message = "{ignoreBud.pattern}") @FormParam("ignoreBud") String ignore_Bud, 
            @FormParam("budget") String budget, 
            @Pattern(regexp = "[0-9]+", message = "{reqBy.pattern}") @FormParam("requestedBy") String requestedBy,
            @NotNull(message = "{firstname.notnull}")@FormParam("firstName") String firstName,
            @NotNull(message = "{lastname.notnull}") @FormParam("lastName") String lastName,
            @FormParam("giftedby") String giftedby,@FormParam("remarks") String remarks) 
  {
        dept = mFcltydeptFacadeREST.find(Integer.parseInt(department));
        mmember = mMemberFacadeREST.find(requestedBy);
        aSupplierDetail = aSupplierDetailFacadeREST.find(publisher);
        libmaterials = libmaterialsFacadeREST.find(materialType);
        aCurrency = aCurrencyFacadeREST.find(currency);
        
      //  validating dept,member,supplier,currency,materials
        if (dept == null) {
          //  throw new DataException("No department found with deptCd : " + department);
        }
        
         if (aSupplierDetail == null) {
          //  throw new DataException("No publisher found with publisherCode : " + publisher);
        }
         
         if (mmember == null) {
          ///  throw new DataException("No member found with memCd : " + requestedBy);
        }
        List<MMember> memberList =  mMemberFacadeREST.findBy("findByMemDeptCd",department);
       
        if(!memberList.contains(mmember))  //check whether member belongs to the given dept
        {
          //  throw new DataException("Requesting member "+ requestedBy+" does not belong to department " + dept.getFcltydeptdscr());
        }
        
         if (libmaterials == null) {
         //   throw new DataException("No material found with materialCode : " + materialType);
        }
         
         if (aCurrency == null) {
          //  throw new DataException("No currency found with currencyCode : " + currency);
        }
        
        ARequest req = new ARequest();
        req.setARRefNo(referenceNo);
        try {
            req.setARDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(ARequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            output = "Please enter date in the format yyyy-MM-dd";
        }

        req.setARDeptCd(dept);

        String status = reqStatus;
        switch (status) {
            case "requested":
                status = "R";
                break;
            case "approval":
                status = "O";
                break;
         }
        req.setARStatus(status);
        req.setARNm(mmember);

        //&*****************************
        if (status == "O") {
            if (supplier != null) {
                aSupplierDetail = aSupplierDetailFacadeREST.find(supplier);
                if (aSupplierDetail == null) {
                 //   throw new DataException("No supplier found with supplierCode : " + supplier);
                }
                req.setARSupCd(aSupplierDetail);
            } else {
               // throw new DataException("Supplier must not be null");
            }
        }
        req.setARTitle(title);
        req.setARFname(firstName);
        req.setARAuthor(lastName);
        req.setARPubCd(aSupplierDetail);
        req.setARPhycd(libmaterials);
        req.setARIsbn(isbn);
        req.setAREdition(edition);
        req.setARYear(year);
         req.setARCurrencyCd(aCurrency);
       
            req.setARPrice(new BigDecimal(price));
        
       
            req.setARRequestCopy(new Short(copies));
        
     
            req.setARConvRate(new BigDecimal(rate));
      
        if (ignore_Bud.contentEquals("true")) {
            //   String ignoreBud[] = ignore_Bud.split(",");
            // if (ignore_Bud == null) {
            aBudget = aBudgetFacadeREST.find(Integer.parseInt(budget));
            if (aBudget == null) {
             //   throw new DataException("No budget found with budgetCode : " + budget);

            }
            req.setARBudgetCd(aBudget);
        } else {
            req.setARBudgetCd(null);
        }
         req.setARRemark(remarks);
        //      req = getRequest(request);
        int count = Integer.parseInt(countREST());
        req = createAndGet(req);
        if (count == Integer.parseInt(countREST())) {
            output = "Someting went wrong Request is not created!";
        } else {
            output = "Request Created.";
        }
        return Response.status(Response.Status.OK).entity(output).type(MediaType.TEXT_PLAIN).build();
        // return Response.status(Response.Status.CREATED).type(MediaType.TEXT_PLAIN).entity(req).build();
    }

   
    //used for gratis request entry
    @POST
    @Path("createGratisRequest")
    @Consumes({"application/x-www-form-urlencoded"})
    public Response  createGratisRequest(@FormParam("referenceNo") String referenceNo,
            @FormParam("date") String date, 
            @NotNull(message = "{publisher.id.notnull}") @FormParam("publisher") String publisher,
            @NotNull(message = "{materialType.id.notnull}") @FormParam("materialType") String materialType,
            @Pattern(regexp = "[0-9]+",message = "isbn.valid") @FormParam("isbn") String isbn, 
            @NotNull(message = "{edition.notnull}") @FormParam("edition") String edition,
            @Pattern(regexp = "[A-Z]+", message = "{currency.id.pattern}") @FormParam("currency") String currency,
            @Pattern(regexp = "^(\\d*\\.)?\\d+$", message = "{price.valid}")@FormParam("price") String price,
            @NotNull(message = "{copies.notnull}") @FormParam("copies") String copies, 
            @NotNull(message = "{title.notnull}") @FormParam("title") String title,
            @Size(min=4, max=4) @Pattern(regexp = "[0-9]+",message = "year.valid") @FormParam("year") String year, @FormParam("rate") String rate,
            @NotNull(message = "{firstname.notnull}")@FormParam("firstName") String firstName,
            @NotNull(message = "{lastname.notnull}") @FormParam("lastName") String lastName,
            @FormParam("giftedby") String giftedby,@FormParam("remarks") String remarks) 
  {
      aSupplierDetail = aSupplierDetailFacadeREST.find(publisher);
      libmaterials = libmaterialsFacadeREST.find(materialType);
      aCurrency = aCurrencyFacadeREST.find(currency);

      //  validating currency,materials,publisher

      if (aSupplierDetail == null) {
         // throw new DataException("No publisher found with publisherCode : " + publisher);
      }
      if (libmaterials == null) {
         // throw new DataException("No material found with materialCode : " + materialType);
      }

      if (aCurrency == null) {
        //  throw new DataException("No currency found with currencyCode : " + currency);
      }

      ARequest req = new ARequest();
      req.setARRefNo(referenceNo);
      try {
          req.setARDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
      } catch (ParseException ex) {
          Logger.getLogger(ARequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
          output = "Please enter date in the format yyyy-MM-dd";
      }
      req.setARStatus("G");
      //For Gratis item, giftedBy Column is set in second column
      req.setARGiftedBy(giftedby);

      req.setARTitle(title);
      req.setARFname(firstName);
      req.setARAuthor(lastName);
      req.setARPubCd(aSupplierDetail);
      req.setARPhycd(libmaterials);
      req.setARIsbn(isbn);
      req.setAREdition(edition);
      req.setARYear(year);
      req.setARCurrencyCd(aCurrency);
      req.setARPrice(new BigDecimal(price));
      req.setARRequestCopy(new Short(copies));
      req.setARConvRate(new BigDecimal(rate));
      req.setARRemark(remarks);
      //      req = getRequest(request);
      int count = Integer.parseInt(countREST());
      req = createAndGet(req);
      if (count == Integer.parseInt(countREST())) {
          output = "Someting went wrong Request is not created!";
      } else {
          output = "Request Created.";
      }
      return Response.status(Response.Status.OK).entity(output).type(MediaType.TEXT_PLAIN).build();
      // return Response.status(Response.Status.CREATED).type(MediaType.TEXT_PLAIN).entity(req).build();
    } 
    
    
    
     //used for direct Approval and direct order
    @POST
    @Path("createDirectRequest")
    @Consumes({"application/x-www-form-urlencoded"})
    public Response  createDirectRequest(@FormParam("referenceNo") String referenceNo,
            @FormParam("date") String date, 
            @Pattern(regexp = "[0-9]+", message = "{dept.id.pattern}") @FormParam("departmentCode")  String department,
            @NotNull(message = "{publisher.id.notnull}") @FormParam("publisher") String publisher,
            @NotNull(message = "{materialType.id.notnull}") @FormParam("materialType") String materialType,
            @Pattern(regexp = "[0-9]+",message = "isbn.valid") @FormParam("isbn") String isbn, 
            @NotNull(message = "{edition.notnull}") @FormParam("edition") String edition,
            @Pattern(regexp = "[A-Z]+", message = "{currency.id.pattern}") @FormParam("currency") String currency,
            @Pattern(regexp = "^(\\d*\\.)?\\d+$", message = "{price.valid}")@FormParam("price") String price,
            @NotNull(message = "{copies.notnull}") @FormParam("copies") String copies, 
            @NotNull(message = "{title.notnull}") @FormParam("title") String title,
            @Size(min=4, max=4) @Pattern(regexp = "[0-9]+",message = "year.valid") @FormParam("year") String year, @FormParam("rate") String rate,
            @NotNull(message = "{budget.notnull}")  @FormParam("budget") String budget, 
            @Pattern(regexp = "[0-9]+", message = "{reqBy.pattern}") @FormParam("requestedBy") String requestedBy,
            @NotNull(message = "{firstname.notnull}")@FormParam("firstName") String firstName,
            @NotNull(message = "{lastname.notnull}") @FormParam("lastName") String lastName,
            @FormParam("approvedBy") String approvedBy,
            @NotNull(message = "{apprCopies.notnull}") @FormParam("apprCopies") String apprCopies,
            @FormParam("apprDate") String apprDate,
            @FormParam("remarks") String remarks) 
  {
        dept = mFcltydeptFacadeREST.find(Integer.parseInt(department));
        mmember = mMemberFacadeREST.find(requestedBy);
        aSupplierDetail = aSupplierDetailFacadeREST.find(publisher);
        libmaterials = libmaterialsFacadeREST.find(materialType);
        aCurrency = aCurrencyFacadeREST.find(currency);
        
      //  validating dept,member,publisher,currency,materials
        if (dept == null) {
           // throw new DataException("No department found with deptCd : " + department);
        }
        
         if (aSupplierDetail == null) {
          //  throw new DataException("No publisher found with publisherCode : " + publisher);
        }
         
         if (mmember == null) {
           // throw new DataException("No member found with memCd : " + requestedBy);
        }
        List<MMember> memberList =  mMemberFacadeREST.findBy("findByMemDeptCd",department);
       
        if(!memberList.contains(mmember))  //check whether member belongs to the given dept
        {
          // throw new DataException("Requesting member "+ requestedBy+" does not belong to department " + dept.getFcltydeptdscr());
        }
        
         if (libmaterials == null) {
           // throw new DataException("No material found with materialCode : " + materialType);
        }
         
         if (aCurrency == null) {
          //  throw new DataException("No currency found with currencyCode : " + currency);
        }
        
        ARequest req = new ARequest();
        req.setARRefNo(referenceNo);
        try {
            req.setARDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(ARequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            output = "Please enter date in the format yyyy-MM-dd";
        }

        req.setARDeptCd(dept);
        req.setARStatus("A");
        req.setARNm(mmember);

        //&*****************************
      
        req.setARTitle(title);
        req.setARFname(firstName);
        req.setARAuthor(lastName);
        req.setARPubCd(aSupplierDetail);
        req.setARPhycd(libmaterials);
        req.setARIsbn(isbn);
        req.setAREdition(edition);
        req.setARYear(year);
         req.setARCurrencyCd(aCurrency);
      
            req.setARApprBy(approvedBy);
//            if(approvedBy>copies)
//            {
//                throw new DataException();
//            }
                req.setARApprCopy(new Short(apprCopies));
           
            try {
                req.setARApprDt(new SimpleDateFormat("yyyy-MM-dd").parse(apprDate));
            } catch (ParseException ex) {
                Logger.getLogger(ARequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            req.setARApprRemark(remarks);
             req.setARPrice(new BigDecimal(price));
             req.setARRequestCopy(new Short(copies));
             req.setARConvRate(new BigDecimal(rate));
      
      
            req.setARBudgetCd(aBudget);
       
         req.setARRemark(remarks);
        //      req = getRequest(request);
        int count = Integer.parseInt(countREST());
        req = createAndGet(req);
        if (count == Integer.parseInt(countREST())) {
            output = "Someting went wrong Request is not created!";
        } else {
            output = "Request Created.";
        }
        return Response.status(Response.Status.OK).entity(output).type(MediaType.TEXT_PLAIN).build();
        // return Response.status(Response.Status.CREATED).type(MediaType.TEXT_PLAIN).entity(req).build();
    }
    
    
    
    @PUT
    @Path("updateNewRequest")
    @Consumes({"application/x-www-form-urlencoded"})
    public Response  updateNewRequest(@Pattern(regexp = "[0-9]+", message = "{req.id.pattern}") 
            @FormParam("requestNo") String requestNo,
            @FormParam("referenceNo") String referenceNo,
            @FormParam("date") String date, 
            @Pattern(regexp = "[0-9]+", message = "{dept.id.pattern}") @FormParam("departmentCode")  String department,
            @Pattern(regexp = "(requested|approval)", message = "{reqStatus.pattern}") @FormParam("reqStatus") String reqStatus,
            @FormParam("supplier") String supplier,
            @NotNull(message = "{publisher.id.notnull}") @FormParam("publisher") String publisher,
            @NotNull(message = "{materialType.id.notnull}") @FormParam("materialType") String materialType,
            @Pattern(regexp = "[0-9]+",message = "isbn.valid") @FormParam("isbn") String isbn, 
            @NotNull(message = "{edition.notnull}") @FormParam("edition") String edition,
            @Pattern(regexp = "[A-Z]+", message = "{currency.id.pattern}") @FormParam("currency") String currency,
            @Pattern(regexp = "^(\\d*\\.)?\\d+$", message = "{price.valid}")@FormParam("price") String price,
            @NotNull(message = "{copies.notnull}") @FormParam("copies") String copies, 
            @NotNull(message = "{title.notnull}") @FormParam("title") String title,
            @Size(min=4, max=4) @Pattern(regexp = "[0-9]+",message = "year.valid") @FormParam("year") String year, @FormParam("rate") String rate,
            @Pattern(regexp="(true|false)" , message = "{ignoreBud.pattern}") @FormParam("ignoreBud") String ignore_Bud, 
            @FormParam("budget") String budget, 
            @Pattern(regexp = "[0-9]+", message = "{reqBy.pattern}") @FormParam("requestedBy") String requestedBy,
            @NotNull(message = "{firstname.notnull}")@FormParam("firstName") String firstName,
            @NotNull(message = "{lastname.notnull}") @FormParam("lastName") String lastName,
            @FormParam("giftedby") String giftedby,@FormParam("remarks") String remarks) 
  {
        dept = mFcltydeptFacadeREST.find(Integer.parseInt(department));
        mmember = mMemberFacadeREST.find(requestedBy);
        aSupplierDetail = aSupplierDetailFacadeREST.find(publisher);
        libmaterials = libmaterialsFacadeREST.find(materialType);
        aCurrency = aCurrencyFacadeREST.find(currency);
        req = find(Integer.parseInt(requestNo));
        
      //  validating reqno,dept,member,supplier,currency,materials
         if(req == null)
        {
          //   throw new DataException("No request found with requestNo : " + requestNo);
        }
        if (dept == null) {
          //  throw new DataException("No department found with deptCd : " + department);
        }
        
         if (aSupplierDetail == null) {
           // throw new DataException("No publisher found with publisherCode : " + publisher);
        }
         
         if (mmember == null) {
           // throw new DataException("No member found with memCd : " + requestedBy);
        }
        List<MMember> memberList =  mMemberFacadeREST.findBy("findByMemDeptCd",department);
       
        if(!memberList.contains(mmember))  //check whether member belongs to the given dept
        {
         //   throw new DataException("Requesting member "+ requestedBy+" does not belong to department " + dept.getFcltydeptdscr());
        }
        
         if (libmaterials == null) {
          //  throw new DataException("No material found with materialCode : " + materialType);
        }
         
         if (aCurrency == null) {
          //  throw new DataException("No currency found with currencyCode : " + currency);
        }
        
        ARequest req = new ARequest();
        req.setARRefNo(referenceNo);
        try {
            req.setARDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(ARequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            output = "Please enter date in the format yyyy-MM-dd";
        }

        req.setARDeptCd(dept);

        String status = reqStatus;
        switch (status) {
            case "requested":
                status = "R";
                break;
            case "approval":
                status = "O";
                break;
         }
        req.setARStatus(status);
        req.setARNm(mmember);

        //&*****************************
        if (status == "O") {
            if (supplier != null) {
                aSupplierDetail = aSupplierDetailFacadeREST.find(supplier);
                if (aSupplierDetail == null) {
                  //  throw new DataException("No supplier found with supplierCode : " + supplier);
                }
                req.setARSupCd(aSupplierDetail);
            } else {
              //  throw new DataException("Supplier must not be null");
            }
        }
        req.setARTitle(title);
        req.setARFname(firstName);
        req.setARAuthor(lastName);
        req.setARPubCd(aSupplierDetail);
        req.setARPhycd(libmaterials);
        req.setARIsbn(isbn);
        req.setAREdition(edition);
        req.setARYear(year);
         req.setARCurrencyCd(aCurrency);
       
            req.setARPrice(new BigDecimal(price));
        
       
            req.setARRequestCopy(new Short(copies));
        
     
            req.setARConvRate(new BigDecimal(rate));
      
        if (ignore_Bud.contentEquals("true")) {
            //   String ignoreBud[] = ignore_Bud.split(",");
            // if (ignore_Bud == null) {
            aBudget = aBudgetFacadeREST.find(Integer.parseInt(budget));
            if (aBudget == null) {
              //  throw new DataException("No budget found with budgetCode : " + budget);

            }
            req.setARBudgetCd(aBudget);
        } else {
            req.setARBudgetCd(null);
        }
         req.setARRemark(remarks);
        //      req = getRequest(request);
        int count = Integer.parseInt(countREST());
        req = createAndGet(req);
        if (count == Integer.parseInt(countREST())) {
            output = "Someting went wrong Request is not updated!";
        } else {
            output = "Request updated.";
        }
        return Response.status(Response.Status.OK).entity(output).type(MediaType.TEXT_PLAIN).build();
        // return Response.status(Response.Status.CREATED).type(MediaType.TEXT_PLAIN).entity(req).build();
    }

   
    //used for gratis request entry
    @PUT
    @Path("updateGratisRequest")
    @Consumes({"application/x-www-form-urlencoded"})
    public Response  updateGratisRequest(@Pattern(regexp = "[0-9]+", message = "{req.id.pattern}") 
            @FormParam("requestNo") String requestNo,
            @FormParam("referenceNo") String referenceNo,
            @FormParam("date") String date, 
            @NotNull(message = "{publisher.id.notnull}") @FormParam("publisher") String publisher,
            @NotNull(message = "{materialType.id.notnull}") @FormParam("materialType") String materialType,
            @Pattern(regexp = "[0-9]+",message = "isbn.valid") @FormParam("isbn") String isbn, 
            @NotNull(message = "{edition.notnull}") @FormParam("edition") String edition,
            @Pattern(regexp = "[A-Z]+", message = "{currency.id.pattern}") @FormParam("currency") String currency,
            @Pattern(regexp = "^(\\d*\\.)?\\d+$", message = "{price.valid}")@FormParam("price") String price,
            @NotNull(message = "{copies.notnull}") @FormParam("copies") String copies, 
            @NotNull(message = "{title.notnull}") @FormParam("title") String title,
            @Size(min=4, max=4) @Pattern(regexp = "[0-9]+",message = "year.valid") @FormParam("year") String year, @FormParam("rate") String rate,
            @NotNull(message = "{firstname.notnull}")@FormParam("firstName") String firstName,
            @NotNull(message = "{lastname.notnull}") @FormParam("lastName") String lastName,
            @FormParam("giftedby") String giftedby,@FormParam("remarks") String remarks) 
  {
      aSupplierDetail = aSupplierDetailFacadeREST.find(publisher);
      libmaterials = libmaterialsFacadeREST.find(materialType);
      aCurrency = aCurrencyFacadeREST.find(currency);
      req = find(Integer.parseInt(requestNo));

      //  validating currency,materials,publisher

    if(req == null)
    {
        // throw new DataException("No request found with requestNo : " + requestNo);
    } 
      if (aSupplierDetail == null) {
        //  throw new DataException("No publisher found with publisherCode : " + publisher);
      }
      if (libmaterials == null) {
        //  throw new DataException("No material found with materialCode : " + materialType);
      }

      if (aCurrency == null) {
        //  throw new DataException("No currency found with currencyCode : " + currency);
      }

      
      req.setARRefNo(referenceNo);
      try {
          req.setARDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
      } catch (ParseException ex) {
          Logger.getLogger(ARequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
          output = "Please enter date in the format yyyy-MM-dd";
      }
      req.setARStatus("G");
      //For Gratis item, giftedBy Column is set in second column
      req.setARGiftedBy(giftedby);

      req.setARTitle(title);
      req.setARFname(firstName);
      req.setARAuthor(lastName);
      req.setARPubCd(aSupplierDetail);
      req.setARPhycd(libmaterials);
      req.setARIsbn(isbn);
      req.setAREdition(edition);
      req.setARYear(year);
      req.setARCurrencyCd(aCurrency);
      req.setARPrice(new BigDecimal(price));
      req.setARRequestCopy(new Short(copies));
      req.setARConvRate(new BigDecimal(rate));
      req.setARRemark(remarks);
      //      req = getRequest(request);
      int count = Integer.parseInt(countREST());
      req = createAndGet(req);
      if (count == Integer.parseInt(countREST())) {
          output = "Someting went wrong Request is not upadted!";
      } else {
          output = "Request updated.";
      }
      return Response.status(Response.Status.OK).entity(output).type(MediaType.TEXT_PLAIN).build();
      // return Response.status(Response.Status.CREATED).type(MediaType.TEXT_PLAIN).entity(req).build();
    } 
    
    
    
     //used for  direct Approval and direct order
    @PUT
    @Path("updateDirectRequest")
    @Consumes({"application/x-www-form-urlencoded"})
    public Response  updateDirectRequest(@Pattern(regexp = "[0-9]+", message = "{req.id.pattern}") 
            @FormParam("requestNo") String requestNo,
            @FormParam("referenceNo") String referenceNo,
            @FormParam("date") String date, 
            @Pattern(regexp = "[0-9]+", message = "{dept.id.pattern}") @FormParam("departmentCode")  String department,
            @NotNull(message = "{publisher.id.notnull}") @FormParam("publisher") String publisher,
            @NotNull(message = "{materialType.id.notnull}") @FormParam("materialType") String materialType,
            @Pattern(regexp = "[0-9]+",message = "isbn.valid") @FormParam("isbn") String isbn, 
            @NotNull(message = "{edition.notnull}") @FormParam("edition") String edition,
            @Pattern(regexp = "[A-Z]+", message = "{currency.id.pattern}") @FormParam("currency") String currency,
            @Pattern(regexp = "^(\\d*\\.)?\\d+$", message = "{price.valid}")@FormParam("price") String price,
            @NotNull(message = "{copies.notnull}") @FormParam("copies") String copies, 
            @NotNull(message = "{title.notnull}") @FormParam("title") String title,
            @Size(min=4, max=4) @Pattern(regexp = "[0-9]+",message = "year.valid") @FormParam("year") String year, @FormParam("rate") String rate,
            @NotNull(message = "{budget.notnull}")  @FormParam("budget") String budget, 
            @Pattern(regexp = "[0-9]+", message = "{reqBy.pattern}") @FormParam("requestedBy") String requestedBy,
            @NotNull(message = "{firstname.notnull}")@FormParam("firstName") String firstName,
            @NotNull(message = "{lastname.notnull}") @FormParam("lastName") String lastName,
            @FormParam("approvedBy") String approvedBy,
            @NotNull(message = "{apprCopies.notnull}") @FormParam("apprCopies") String apprCopies,
            @FormParam("apprDate") String apprDate,
            @FormParam("remarks") String remarks) 
  {
        dept = mFcltydeptFacadeREST.find(Integer.parseInt(department));
        mmember = mMemberFacadeREST.find(requestedBy);
        aSupplierDetail = aSupplierDetailFacadeREST.find(publisher);
        libmaterials = libmaterialsFacadeREST.find(materialType);
        aCurrency = aCurrencyFacadeREST.find(currency);
        req = find(Integer.parseInt(requestNo));
        
      //  validating dept,member,publisher,currency,materials
        if (dept == null) {
          //  throw new DataException("No department found with deptCd : " + department);
        }
        
         if (aSupplierDetail == null) {
          //  throw new DataException("No publisher found with publisherCode : " + publisher);
        }
         
         if (mmember == null) {
          //  throw new DataException("No member found with memCd : " + requestedBy);
        }
        List<MMember> memberList =  mMemberFacadeREST.findBy("findByMemDeptCd",department);
       
        if(!memberList.contains(mmember))  //check whether member belongs to the given dept
        {
          //  throw new DataException("Requesting member "+ requestedBy+" does not belong to department " + dept.getFcltydeptdscr());
        }
        
         if (libmaterials == null) {
         //   throw new DataException("No material found with materialCode : " + materialType);
        }
         
         if (aCurrency == null) {
          //  throw new DataException("No currency found with currencyCode : " + currency);
        }
         
        if(req == null)
        {
          //   throw new DataException("No request found with requestNo : " + requestNo);
        }
            
        req.setARRefNo(referenceNo);
        try {
            req.setARDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(ARequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            output = "Please enter date in the format yyyy-MM-dd";
        }

        req.setARDeptCd(dept);
        req.setARStatus("A");
        req.setARNm(mmember);

        //&*****************************
      
        req.setARTitle(title);
        req.setARFname(firstName);
        req.setARAuthor(lastName);
        req.setARPubCd(aSupplierDetail);
        req.setARPhycd(libmaterials);
        req.setARIsbn(isbn);
        req.setAREdition(edition);
        req.setARYear(year);
         req.setARCurrencyCd(aCurrency);
      
            req.setARApprBy(approvedBy);
//            if(approvedBy>copies)
//            {
//                throw new DataException();
//            }
                req.setARApprCopy(new Short(apprCopies));
           
            try {
                req.setARApprDt(new SimpleDateFormat("yyyy-MM-dd").parse(apprDate));
            } catch (ParseException ex) {
                Logger.getLogger(ARequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            req.setARApprRemark(remarks);
             req.setARPrice(new BigDecimal(price));
             req.setARRequestCopy(new Short(copies));
             req.setARConvRate(new BigDecimal(rate));
      
      
            req.setARBudgetCd(aBudget);
       
         req.setARRemark(remarks);
        //      req = getRequest(request);
        int count = Integer.parseInt(countREST());
        req = createAndGet(req);
        if (count == Integer.parseInt(countREST())) {
            output = "Someting went wrong Request is not updated!";
        } else {
            output = "Request updated.";
        }
        return Response.status(Response.Status.OK).entity(output).type(MediaType.TEXT_PLAIN).build();
        // return Response.status(Response.Status.CREATED).type(MediaType.TEXT_PLAIN).entity(req).build();
    }
    
    
    
    
    @PUT
    @Path("updateRequest")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String updateRequest(@FormParam("requestNo") String requestNo,@FormParam("referenceNo") String referenceNo,
    @FormParam("date") String date,@FormParam("department") String department,
    @FormParam("reqStatus") String reqStatus,@FormParam("giftedBy") String giftedBy,
    @FormParam("firstName") String firstName,@FormParam("supplier") String supplier,
    @FormParam("publisher") String publisher,@FormParam("materialType") String materialType,
    @FormParam("isbn") String isbn,@FormParam("edition") String edition,
    @FormParam("currency") String currency,@FormParam("price") String price,
    @FormParam("copies") String copies,@FormParam("title") String title,
    @FormParam("year") String year,@FormParam("rate") String rate,
    @FormParam("ignoreBud") String ignore_Bud,@FormParam("form") String form,
    @FormParam("budget") String budget,@FormParam("approvedBy") String approvedBy,
    @FormParam("apprCopies") String apprCopies,@FormParam("apprDate") String apprDate,
    @FormParam("requestedBy") String requestedBy,@FormParam("lastName") String lastName,
    @FormParam("remarks") String remarks)
      {
       ARequest req = null;
        if(!"".equals(requestNo))
            req = find(Integer.parseInt(requestNo));
        if(req == null)
            req = new ARequest();
        
        //req.setARNo(Integer.parseInt(request.getParameter("requestNo")));
        req.setARRefNo(referenceNo);
        try {
            req.setARDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(ARequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(department != null)
        req.setARDeptCd(mFcltydeptFacadeREST.find(Integer.parseInt(department)));
        String status = reqStatus;
        switch(status)
        {
            case "requested":   status = "R";
                                break;
            case "approval":    status = "O";
                                break;    
            case "Gifted":      status = "G";
                                break;
            case "Approved":    status = "A";
                                break;    
        }
        req.setARStatus(status);
        //For Gratis item, giftedBy Column is set in second column
        if(requestedBy != null)
        {
            req.setARNm(mMemberFacadeREST.find(requestedBy));
        }
        req.setARGiftedBy(giftedBy);
        //&*****************************
        if(supplier != null)
        {
            req.setARSupCd(aSupplierDetailFacadeREST.find(supplier));
        }
        req.setARTitle(title);
        req.setARFname(firstName);
        req.setARAuthor(lastName);
        req.setARPubCd(aSupplierDetailFacadeREST.find(publisher));
        req.setARPhycd(libmaterialsFacadeREST.find(materialType));
        req.setARIsbn(isbn);
        req.setAREdition(edition);
        req.setARYear(year);
        req.setARCurrencyCd(aCurrencyFacadeREST.find(currency));
        if(!price.trim().equals(""))
            req.setARPrice(new BigDecimal(price));
        else
            req.setARPrice(null);
        if(!copies.trim().equals(""))
            req.setARRequestCopy(new Short(copies));
        else
            req.setARRequestCopy(null);
        if(!rate.trim().equals(""))    
            req.setARConvRate(new BigDecimal(rate));
        else
            req.setARConvRate(null);
        if(ignore_Bud != null)
        {
            String ignoreBud[] = ignore_Bud.split(",");
            if(ignoreBud == null)
                req.setARBudgetCd(aBudgetFacadeREST.find(Integer.parseInt(budget)));
            else
                req.setARBudgetCd(null);
        }
        req.setARRemark(remarks);
        
        if(form.equals("Direct")||form.equals("DirectOrder"))
        {
            req.setARApprBy(approvedBy);
            if(!apprCopies.trim().equals(""))    
                req.setARApprCopy(new Short(apprCopies));
            else
                req.setARApprCopy(null);
            try {
                req.setARApprDt(new SimpleDateFormat("yyyy-MM-dd").parse(apprDate));
            } catch (ParseException ex) {
                Logger.getLogger(ARequestFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            req.setARApprRemark(remarks);
        }
            edit(req);
                output = "Request updated.";
            switch (form) {
                case "Request":
                    //response.sendRedirect("jsp/request.jsp");
                    output =  output;
                    break;
                case "Gratis":
                    //response.sendRedirect("jsp/gratisItem.jsp");
                    output =  output;
                    break;
                case "Direct":
                    //response.sendRedirect("jsp/directApproval.jsp");
                    output =  output;
                    break;
            }
              return output;
         }
    
    
    
    
    
    @PUT // used in (send for approval, approve or reject items)
 //   @Path("approvalProcess")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String approvalProcess_Old(@FormParam("requestNumbers") String request_Numbers,@FormParam("form") String form,
    @FormParam("approvedRejectedBy") String approvedRejectedBy,@FormParam("remarks") String remarks,
    @FormParam("status") String statusFilter,@FormParam("operation") String operation)
    {   String status = "";
        String setStatus = "";
        String sendRedirect = "";
        String output = "";
        switch (form) {
            case "selectForApproval":
                status = "R";           //requested
                setStatus = "B";      //selectedForApproval
                sendRedirect = "jsp/selectForApproval.jsp";
                output = "Request(s) sent for Approval.";
                break;
            case "newOrder":    
                status = "A|J";       //"A" for "approved"
                setStatus = "D";    //"D" fop "ordered"
                sendRedirect = "jsp/newOrder.jsp";
                break;
            case "approvalProcess":
                if(statusFilter==null)
                    status = "B|O";       //selectedForApproval
                else
                    status = statusFilter.equals("gifted")?"G":statusFilter;   //to set status to G for gifted
              //  setStatus = (String)request.getAttribute("setStatus");
                if(operation.equals("Approve")){ setStatus = "A";}
                if(operation.equals("Reject")){ setStatus = "E";}
                sendRedirect = "jsp/approvalProcess.jsp";
                break;
            case "deleteRequest":
                status = "E";       //"E" for "rejected"
                sendRedirect = "jsp/deleteRequest.jsp";
                break;
        }
        String[] requestNumbers = request_Numbers.split(",");
        ARequest req = new ARequest();
        for(int i=0; i<requestNumbers.length; i++)
        {
            req = find(Integer.parseInt(requestNumbers[i]));
            System.out.println("req.getARStatus() "+req.getARStatus()+"    setstatus "+setStatus);
            if(!req.getARStatus().equals("G"))      //does not change status of Gifted Item to 'A'
            {                                        //on approval but only sets appr_date
                req.setARStatus(setStatus);
            }
            if(form.equals("approvalProcess"))
            {
                req.setARApprBy(approvedRejectedBy);
                req.setARApprRemark(remarks);
                if(setStatus.equals("A"))       //"A" for "approved"
                {
                    req.setARApprCopy(req.getARRequestCopy());
                    req.setARApprDt(new Date());
                    output = "Request(s) Approved.";
                }
                else if(setStatus.equals("E"))       //"E" for "rejected"
                {
                    req.setARRejDt(new Date());
                    output = "Request(s) Rejected.";
                }
            }
            
            edit(req);

            //If Request was for Gratis Item make entry in A_Accession
            //for each copy of request enry
            if(req.getARStatus().equals("G"))
            {
                for(int j=0; j<req.getARRequestCopy(); j++)
                {
                    AAccession accession = new AAccession();

                    accession.setARequest(req);
                    accession.setARecDt(new Date());

                    aAccessionFacadeREST.create(accession);
                }
            }
        }
        //response.sendRedirect(sendRedirect);
       return output;
    }
    
    
    @PUT // used in (send for approval items)  
    @Path("selectForApproval")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String selectForApproval(@Pattern(regexp = "^\\d+(\\,\\d+\\,?)*$", message = "{reqNos.pattern}")@FormParam("requestNumbers") String request_Numbers)
    {   
        String setStatus = "";
        String output = "";
        setStatus = "B";      //selectedForApproval
        //  output = "Request(s) sent for Approval.";

        String[] requestNumbers = request_Numbers.split(",");
        List<String> selectedList = new ArrayList<>();
        List<String> notSelectedList = new ArrayList<>();

        ARequest req = new ARequest();
        for (int i = 0; i < requestNumbers.length; i++) {
            req = find(Integer.parseInt(requestNumbers[i]));

            if (req == null) {  // check for request no. validity
              //  throw new DataException("invalid requestNo : " + requestNumbers[i]);
            }
            
            if (req.getARStatus().contentEquals("R") || req.getARStatus().contentEquals("G")) {
                if (!req.getARStatus().equals("G")) //does not change status of Gifted Item to 'A' on approval but only sets appr_date
                {
                    req.setARStatus(setStatus);
                    selectedList.add(requestNumbers[i]);
                }

                edit(req);
            } else {
                notSelectedList.add(requestNumbers[i]);
            }
            //If Request was for Gratis Item make entry in A_Accession
            //for each copy of request enry
            if (req.getARStatus().equals("G")) {
                for (int j = 0; j < req.getARRequestCopy(); j++) {
                    AAccession accession = new AAccession();

                    accession.setARequest(req);
                    accession.setARecDt(new Date());

                    aAccessionFacadeREST.create(accession);
                }
            }
        }
        if (!selectedList.isEmpty()) {
            output = " Request(s) " + StringUtils.join(selectedList, ",") + " sent for Approval";
        }
        if (!notSelectedList.isEmpty()) {
            output = output + "\n Following request(s) : " + StringUtils.join(notSelectedList, ",") + " cannot be sent for approval";
        }
        return output;
    }
    
    @PUT // used in (approve or reject items)
    @Path("approvalProcess")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String approvalProcess(@Pattern(regexp = "^\\d+(\\,\\d+\\,?)*$", message = "{reqNos.pattern}") @FormParam("requestNumbers") String request_Numbers,
    @Pattern(regexp = "[a-zA-Z]+(\\.?\\s?[a-zA-Z]?)*$", message = "{appRej.pattern}") @FormParam("approvedRejectedBy") String approvedRejectedBy,
    @Pattern(regexp = "(\\s?[a-z][A-Z]?)*$", message = "{remarks.pattern}")  @FormParam("remarks") String remarks,
    @Pattern(regexp = "(approve|reject)+", message = "{operation.pattern}") @FormParam("operation") String operation)
    {  
        String status = "";
        String setStatus = "";
        String output = "";
        String output1 = "";

        if (operation.equalsIgnoreCase("Approve")) {
            setStatus = "A";
            output = " approved ";
        }
        if (operation.equalsIgnoreCase("Reject")) {
            setStatus = "E";
            output = " rejected ";
        }

        String[] requestNumbers = request_Numbers.split(",");
        List<String> selectedList = new ArrayList<>();
        List<String> notSelectedList = new ArrayList<>();

        ARequest req = new ARequest();
        for (int i = 0; i < requestNumbers.length; i++) {
            req = find(Integer.parseInt(requestNumbers[i]));
           
            if (req == null) {  // check for request no. validity
             //   throw new DataException("invalid requestNo : " + requestNumbers[i]);
            }
            
            if (req.getARStatus().contentEquals("B") || req.getARStatus().contentEquals("O") || req.getARStatus().contentEquals("G")) {
                if (!req.getARStatus().equals("G")) //does not change status of Gifted Item to 'A' on approval but only sets appr_date
                {
                    req.setARStatus(setStatus);
                    selectedList.add(requestNumbers[i]);
                }

                req.setARApprBy(approvedRejectedBy);
                req.setARApprRemark(remarks);
                if (setStatus.equals("A")) //"A" for "approved"
                {
                    req.setARApprCopy(req.getARRequestCopy());
                    req.setARApprDt(new Date());

                } else if (setStatus.equals("E")) //"E" for "rejected"
                {
                    req.setARRejDt(new Date());
                }
                edit(req);
            } else {
                notSelectedList.add(requestNumbers[i]);
            }
            //If Request was for Gratis Item make entry in A_Accession
            //for each copy of request enry
            if (req.getARStatus().equals("G")) {
                for (int j = 0; j < req.getARRequestCopy(); j++) {
                    AAccession accession = new AAccession();

                    accession.setARequest(req);
                    accession.setARecDt(new Date());

                    aAccessionFacadeREST.create(accession);
                }
            }
        }
        if (!selectedList.isEmpty()) {
            output1 = " Request(s) " + StringUtils.join(selectedList, ",") + output;
        }
        if (!notSelectedList.isEmpty()) {
            output1 = output1 + "\n Following request(s) : " + StringUtils.join(notSelectedList, ",") + " cannot be " + output;
        }
        return output1;
    }
    
//   public String returnStatus(String status)
//   {
//       return status;
//   }
   
//    @POST
//    @Path("approve")
//    @Consumes({"application/x-www-form-urlencoded"})
//    @Produces("text/plain")
//    public String approve(@FormParam("requestNumbers") String request_Numbers,@FormParam("form") String form,
//    @FormParam("approvedRejectedBy") String approvedRejectedBy,@FormParam("remarks") String remarks,
//    @FormParam("status") String statusFilter)
//    {
//        
//    }
 
 
     // used in letter fro approval
    @GET
    @Path("getDataForLetterForApproval/{paramName}/{paramValue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> getDataForLetterForApproval(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) throws ParseException {
        String q="";
        Query query;
        String valuestring[] = paramValue.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        switch(paramName)
        {
            case "department": q = "select a_request.a_r_title,a_request.a_r_author,B.a_s_name as \n" +
                                "  PublisherName ,a_request.a_r_appr_dt,a_request.a_r_edition,\n" +
                                "  letter_formats.letter_format,letter_formats.subject, \n" +
                                "  a_request.a_r_appr_copy\n" +
                                "  from a_request,letter_formats,a_supplier_detail B\n" +
                                "  where \n" +
                                "  B.a_s_cd = a_request.a_r_pub_cd and letter_formats.letter_name='app_letter' \n" +
                                "  and a_request.a_r_no in (Select a_r_no from a_request where a_r_dept_cd = '"+valuestring[0]+"' and a_r_status in ('A'))" ;
                                   break;
            case "requester":  q = "select a_request.a_r_title,a_request.a_r_author,B.a_s_name as \n" +
                                "  PublisherName ,a_request.a_r_appr_dt,a_request.a_r_edition,\n" +
                                "  letter_formats.letter_format,letter_formats.subject, \n" +
                                "  a_request.a_r_appr_copy\n" +
                                "  from a_request,letter_formats,a_supplier_detail B\n" +
                                "  where \n" +
                                "  B.a_s_cd = a_request.a_r_pub_cd and letter_formats.letter_name='app_letter' \n" +
                                "  and a_request.a_r_no in (Select a_r_no from a_request where a_r_nm like '"+valuestring[0]+"' and a_r_status in ('A'))" ;
                                   break;
            case "approvalDate":   
                                Date date1 = new java.sql.Date(dateFormat.parse(valuestring[0]).getTime());
                                Date date2 = new java.sql.Date(dateFormat.parse(valuestring[1]).getTime());
                                q = "select a_request.a_r_title,a_request.a_r_author,B.a_s_name as \n" +
                                "  PublisherName ,a_request.a_r_appr_dt,a_request.a_r_edition,\n" +
                                "  letter_formats.letter_format,letter_formats.subject, \n" +
                                "  a_request.a_r_appr_copy\n" +
                                "  from a_request,letter_formats,a_supplier_detail B\n" +
                                "  where \n" +
                                "  B.a_s_cd = a_request.a_r_pub_cd and letter_formats.letter_name='app_letter' \n" +
                                "  and a_request.a_r_no in (Select a_r_no from a_request where a_r_appr_dt between '"+date1+"' and '"+date2+"' and a_r_status in ('A'))" ;
                                   break;
        }
        System.out.println("query  "+q);
        List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        long a = System.currentTimeMillis();
        result = (List<Object>) query.getResultList();
        long b = System.currentTimeMillis();
        System.out.println("time...........  "+(b-a));
        return result;
    }
}
