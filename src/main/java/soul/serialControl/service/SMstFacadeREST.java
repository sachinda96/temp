/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serialControl.service;

//import ExceptionService.DataException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
import soul.general_master.ABudget;
import soul.general_master.ACurrency;
import soul.general_master.BkSubject;
import soul.general_master.Libmaterials;
import soul.general_master.MFcltydept;
import soul.general_master.service.ABudgetFacadeREST;
import soul.general_master.service.ACurrencyFacadeREST;
import soul.general_master.service.BkSubjectFacadeREST;
import soul.general_master.service.LibmaterialsFacadeREST;
import soul.general_master.service.MFcltydeptFacadeREST;
import soul.serialControl.SMst;
import soul.serialControl.SMstInSchedule;
import soul.serialControl.SOthertitle;
import soul.serialControl.SParallel;
import soul.serialControl.SSchedule;
import soul.serialControl.SSchedulePK;
import soul.serialControl.SSubscription;
import soul.serial_master.SBindingSet;
import soul.serial_master.SEdition;
import soul.serial_master.SFrequency;
import soul.serial_master.SSupplierDetail;
import soul.serial_master.service.SFrequencyFacadeREST;
import soul.serial_master.service.SBindingSetFacadeREST;
import soul.serial_master.service.SEditionFacadeREST;
import soul.serial_master.service.SSupplierDetailFacadeREST;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.serialcontrol.smst")
public class SMstFacadeREST extends AbstractFacade<SMst> {
    @EJB
    private SScheduleFacadeREST sScheduleFacadeREST;
    @EJB
    private SBindingSetFacadeREST sBindingSetFacadeREST;
    @EJB
    private SSubscriptionFacadeREST sSubscriptionFacadeREST;
    @EJB
    private SFrequencyFacadeREST sFrequencyFacadeREST;
    @EJB
    private SMstInScheduleFacadeREST sMstInScheduleFacadeREST;
    @EJB
    private MFcltydeptFacadeREST mFcltydeptFacadeREST;
    @EJB
    private MMemberFacadeREST mMemberFacadeREST;
    @EJB
    private SSupplierDetailFacadeREST sSuppDetailFacadeREST;
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
    @EJB
    private SOthertitleFacadeREST sOthertitleFacadeREST;
    @EJB
    private SParallelFacadeREST sParallelFacadeREST;
    
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output;
    
    
    public SMstFacadeREST() {
        super(SMst.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(SMst entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(SMst entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public SMst find(@PathParam("id") String id) {
        return super.find(id);
    }
    
    @PUT
    @Path("viewers/view/dues/data")
    public void countAll() {
        SMst s = new SMst();
        Query query = getEntityManager().createNativeQuery(s.getDues());
        int result = query.executeUpdate();
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SMst> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SMst> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<SMst> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        List<String> smstStatus = new ArrayList<>();
        
        switch(query)
        {
            case "findByMaxRecId" :   valueList.add(valueString[0]+"%");
                                    break;
            case "findBySTitle" :   valueList.add(valueString[0]+"%");
                                    break;
            case "findBySTitle1" :   valueList.add(valueString[0]);
                                    break;
            case "findBySRecid" :   valueList.add(valueString[0]);
                                    break;
            case "findBySMstStatussAndTyAcq":   smstStatus.addAll(Arrays.asList(valueString[0].split("|")));
                                                valueList.add(smstStatus);
                                                valueList.add(valueString[1]);
                                                break;
            case "findBySOrderNoANDSInvNo":     valueList.add(valueString[0]);
                                                valueList.add(valueString[1]);  
                                                break;
            case "countByPubCode":              valueList.add(valueString[0]); 
            valueList.add(valueString[0]); 
                                                break;
            case "findOrdersBySupplierName":     valueList.add(valueString[0]);
                                                  
                                                break;
            case "findAllOrders":     valueList.add(valueString[0]);
                                                  
                                                break;
            case "getSPubCd":   //no parameter to set
                                                break;
            case "ordersFOrInvoiceProcess":   //no parameter to set
                                                break;
            case "findForScheduleGeneration":   //no parameter to set
                                                break;
            case "listofAllNonReceivedIssues":   //no parameter to set
                                                break;
            case "listofSerialsToModifyOrDeleteSchedule":   //no parameter to set
                                                break;
            case "listofSerialsToGenerateNewSchedule":   //no parameter to set
                                                break;
            case "listofSerialsToGenerateNewScheduleByTitle":  
                valueList.add(valueString[0]+"%");
                                                break;
            case "titleForInvoiceDropdown":   //no parameter to set
                                                break;
            default:    valueList.add(valueString[0]);
                        break;
                        //
                
        }
        return super.findBy("SMst."+query, valueList);
    }
    
    @GET
    @Path("retrieveAll/{accept}/{form}")
    @Produces({"application/xml", "application/json"})
  //  public List<SMst> retrieveAll(@QueryParam("accept") String accept, @QueryParam("form") String form) {
    public List<SMst> retrieveAll(@PathParam("accept") String accept, @PathParam("form") String form) {    
        List<SMst> getAll = null;
        if (accept.equals("XML")) {
            if (form.equals("OrderProcess")) {
                getAll = findBy("findBySMstStatussAndTyAcq", "M|D|V,S");
            }
            if (form.equals("GenerateSchedule")) {
                getAll = findBy("findForScheduleGeneration", "null");
            }
         }
      
       return getAll;
    }
    
    
    
    @GET
    @Path("findOrdersBySupplierName/{supplierName}")
    @Produces({"application/xml", "application/json"})
    public List<SMst> findOrdersBySupplierName(@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{vendorName.pattern}") @PathParam("supplierName") String supplierName) {
        List<SMst> getAll = null;
        getAll = findBy("findOrdersBySupplierName", supplierName);
        return getAll;
    }
    
    
    //list of serials, in schedule generation to generate new schedule 
    @GET
    @Path("findByRecID/{recId}")
    @Produces({"application/xml", "application/json"})
    public List<SMst> findBySRecid(@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{recId.pattern}") @PathParam("recId") String recId) {
        List<SMst> getAll = null;
        getAll = findBy("findBySRecid", recId);
        return getAll;
    }
    
    
    //list of serials, in schedule generation to generate new schedule 
    @GET
    @Path("listofSerialsToGenerateNewSchedule")
    @Produces({"application/xml", "application/json"})
    public List<SMst> listofSerialsToGenerateNewSchedule() {
        List<SMst> getAll = null;
        getAll = findBy("listofSerialsToGenerateNewSchedule", "null");
        return getAll;
    }
    
    ////list of serials, in schedule generation to generate new schedule filtered by title
    @GET
    @Path("listofSerialsToGenerateNewSchedule/{title}")
    @Produces({"application/xml", "application/json"})
    public List<SMst> listofSerialsToGenerateNewSchedule(@PathParam("title") String title) {
        List<SMst> getAll = null;
        getAll = findBy("listofSerialsToGenerateNewScheduleByTitle", title);
        return getAll;
    }
    
   
    @GET
    @Path("findOrdersByPublisherName/{publisherCode}")
    @Produces({"application/xml", "application/json"})
    public List<SMst> findOrdersByPublisherName(@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{vendorName.pattern}") @PathParam("publisherCode") String publisherCode) {
        List<SMst> getAll = null;
        getAll = findBy("findOrdersByPublisherName", publisherCode);
        return getAll;
    }

    
    //list of serials, in schedule generation to modify or delete
    @GET
    @Path("getTitles/{title}")
    @Produces({"application/xml", "application/json"})
    public List<SMst> getTitles(@PathParam("title") String title) {
        List<SMst> getAll = null;
        getAll = findBy("findBySTitle", title);
        return getAll;
    }
    
    @POST
    @Path("generateSchedule")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String generateSchedule(@FormParam("repeatIssCheck") String repeatIssCheck,@FormParam("recId") String recId,
            @FormParam("masterChangeCheck") String masterChangeCheck,@FormParam("fulVolCheck") String fulVolCheck,
            @FormParam("noOfCopies") Integer noOfCopies, 
            @FormParam("leadTime") Integer leadTime,
            @FormParam("volNoFrom") Integer volNoFrom, 
            @FormParam("volNoTo") Integer volNoTo,
            @FormParam("issNoFrom") Integer issNoFrom, 
            @FormParam("issNoTo") Integer issNoTo,
            @FormParam("issPerVol") Integer issPerVol,
            @FormParam("issuePerFirstVol") Integer issuePerFirstVol,
            @FormParam("volPrefix") String volPrefix, 
            @FormParam("issPrefix") String issPrefix,
            @FormParam("firstIssueDate") String firstIssueDate, @FormParam("endDate") String endDate)
    {
        String getAll = "";
        List<SMst> mstList;
        List<SSchedule> scheduleList;
       
        int srNo = 0, srNoForDisplay/* displaying in grid*/;
        Boolean repeatIssNo = false, standingOrder = false, changeMaster = false, fullVolCheck = false;
        BigDecimal pricePerIssue;
        SMst sMst;
        SSchedule sSchedule;
       
        SSubscription subscription;
      
        
        //Get srNo and set srNoForDisplay to srNo + 1 for displaying schedules in grid after creation
        scheduleList = sScheduleFacadeREST.findBy("findByMaxSSSrno", recId);
            srNo = scheduleList.get(0).getSSchedulePK().getSSSrno();
        srNoForDisplay = srNo + 1;

        //set repeatIssNo flag
        if(repeatIssCheck.contentEquals("yes"))
            repeatIssNo = true;
        else
            repeatIssNo = false;

        //set standingOrder flag and pricePerIssue in case of Standing order
        sMst = find(recId);
        String freq = sMst.getSFreqCd();
        subscription = sSubscriptionFacadeREST.find(sMst.getSOrderNo());
        if("S".equals(subscription.getSOrderType())){
            standingOrder = true;
            if(sMst.getSPriceperissue() != null)
                pricePerIssue = sMst.getSPriceperissue();
            else
                pricePerIssue = BigDecimal.ZERO;
        }
        else{
            standingOrder = false;
            pricePerIssue = BigDecimal.ZERO;
        }


        //set changeMaster flag
        if(masterChangeCheck.contentEquals("yes"))
            changeMaster = true;
        else
            changeMaster = false;

        //Change SMst entry if changeMaster flag is true
        Date firstPublicationDate = new Date()/* not required to set still done to keep it inintialized*/,
                expectedDate = new Date() /* not required to set still done to keep it inintialized*/;
        try {
            firstPublicationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(firstIssueDate);
            sMst.setSStDt(firstPublicationDate);     //set start date in case if changeMaster is true, this change also get stored
        } catch (ParseException ex) {
            Logger.getLogger(SMstFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }


        if(changeMaster){
            sMst.setSApprCopy(noOfCopies);
            sMst.setSStVol(Integer.toString(volNoFrom));
            sMst.setSEnVol(Integer.toString(volNoTo));
            sMst.setSStIss(Integer.toString(issNoFrom));
            sMst.setSEnIss(Integer.toString(issNoTo));
            sMst.setSLeadtime(leadTime);
            //sMst.setSStDt(publicationDate); ====> done when pulicationDate is set
            sMst.setSIssPerVol(issPerVol);

            edit(sMst);
        }

        //get frequency
        SFrequency frequency = sFrequencyFacadeREST.find(freq);
        String dmy = frequency.getSFDmy();
        int dmyValue = 0;
        int issuesInYear = frequency.getSFIss();
        switch(dmy){
            case "D":
                dmyValue = frequency.getSFDd();
                break;
            case "M":
                dmyValue = frequency.getSFMm();
                break;
            case "Y":
                dmyValue = frequency.getSFYy();
                break;
        }


        //Entry in SSchedule: 
        //Entry for each copy each issue as specified by user in terms of issue/volume
        //With iterative volumeNos till endDate or last date specified
        if(fulVolCheck.contentEquals("yes"))
            fullVolCheck = true;
        else
            fullVolCheck = false;
        int issFrom, issTo, firstVolIssFrom = 0, volFrom, volTo, issNoForLoop = 0;
        issFrom = issNoFrom;
        issTo = issNoTo;
        volTo = volNoTo;

        if(fullVolCheck){
            firstVolIssFrom = issuePerFirstVol + 1;
        }

        Boolean whileBreakFlag = true;
        Date publicationDate;

        try{

            for(int i=0; i<noOfCopies; i++){
                whileBreakFlag = true;      //for each iteration of noOfCopy set to true to make entry for each copy

                volFrom = volNoFrom;

                publicationDate = firstPublicationDate;


                //If firstVol is not full set first issueNo to firstVolIssFrom
                if(fullVolCheck){
                    issNoForLoop = firstVolIssFrom;
                    issPerVol = issuePerFirstVol;
                }
                else    
                    issNoForLoop = issFrom;

                //while loop is for making entry for different volume nos
                while(whileBreakFlag){

                    //for loop is for making entry for differnt issue nos
                    for(int j=0 ; j < issPerVol; j++, issNoForLoop++){
                        srNo = srNo + 1;
                        expectedDate = addToDate(publicationDate, "D", leadTime);

                        sSchedule = getSchedule(recId, srNo, volFrom, issNoForLoop, volPrefix, issPrefix, publicationDate, expectedDate);
                        sSchedule.setSMst(sMst);
                        if(standingOrder)
                            sSchedule.setSSPriceperissue(pricePerIssue);
                        sScheduleFacadeREST.create(sSchedule);
                        //entry in schedule

                        publicationDate = addToDate(publicationDate, dmy, dmyValue);
                        if(publicationDate.compareTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate)) > 0){
                            whileBreakFlag = false;
                            break;
                        }
                    }//end of for

                    if(!repeatIssNo)           //if repeatIssNo is true don't reinitialize issNoForloop and let it continue till all volumeNos are used
                        issNoForLoop = issFrom;        //To continue issNoForLoop in for loop issNoTo is incremented twice issueNoForLoop + 1

                    if(fullVolCheck){
                        issPerVol = issPerVol;  //issPerVol againg set to normal valu for further volumes after first volume
                    }


                    volFrom++;
                    if(volFrom > volTo)
                        whileBreakFlag = false;
                }//end of while
            }// end of for noOfCopy

        }catch(Exception e){
          output = "Error";
        }
        //after schedule is generated s_mst_status is changed to 'S'
        sMst.setSMstStatus("S");
        edit(sMst);
       output = srNoForDisplay+","+sMst.getSRecid();

        return output;
    }
    
       public static Date addToDate(Date date, String addTo, int dmy)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch (addTo) {
            case "D":
                cal.add(Calendar.DATE, dmy);
                break;
            case "M":
                cal.add(Calendar.MONTH, dmy); 
                break;
            case "Y":
                cal.add(Calendar.YEAR, dmy);
                break;
        }
        return cal.getTime();
    }
    
    public static SSchedule getSchedule(String recId, int srNo, int volNo, int issNo, String volPrifix, String issPrifix, Date publicationDate, Date expectedDate){
        SSchedule sSchedule = new SSchedule();
        SSchedulePK sSchedulePK = new SSchedulePK();
        
        sSchedulePK.setSSSrno(srNo);
        sSchedulePK.setSSRecid(recId);
        sSchedulePK.setSSVol(volPrifix+Integer.toString(volNo));
        sSchedulePK.setSSIss(issPrifix+Integer.toString(issNo));
        
        sSchedule.setSSchedulePK(sSchedulePK);
        sSchedule.setSSPubDt(publicationDate);
        sSchedule.setSSExpDt(expectedDate);
        sSchedule.setSSStatus("Expected");
        
        return sSchedule;
    }
    
    
    //titles for Invoice report dropdown
    @GET
    @Path("getTitlesforInvoiceReport")
    @Produces({"application/xml", "application/json"})
    public List<Object> getTitlesforInvoiceReport() throws ParseException {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;
        q = "select s_title from s_mst where s_recid in (select s_recid from s_subdetail where s_order_no in (select s_inv_order_no from s_sub_invdetail ))";

        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
    
    //list of serials, in schedule generation to generate new/modify
    @GET
    @Path("listofSerialsToModifyOrDeleteSchedule")
    @Produces({"application/xml", "application/json"})
    public List<SMst> listofSerialsToModifyOrDeleteSchedule() throws ParseException {
        String q = "";
        List<SMst> result = new ArrayList<>();
        Query query;
        q = "select * from s_mst where s_recid in (select distinct s_s_recid from s_schedule)";

        //List<Object> result;
        query = getEntityManager().createNativeQuery(q,SMst.class);
        result = (List<SMst>) query.getResultList();
        return result;
    }
    
    //list of serials, in schedule generation to generate new/modify schedule or  filtered by title
    @GET
    @Path("listofSerialsToModifyOrDeleteSchedule/{title}")
    @Produces({"application/xml", "application/json"})
    public List<SMst> listofSerialsToModifyOrDeleteSchedule(@PathParam("title") String title) throws ParseException {
        String q = "";
        List<SMst> result = new ArrayList<>();
        Query query;
        q = "select * from s_mst where s_recid in (select distinct s_s_recid from s_schedule) and  s_title like '" +title+ "%' ";

        //List<Object> result;
        query = getEntityManager().createNativeQuery(q,SMst.class);
        result = (List<SMst>) query.getResultList();
        return result;
    }
    
    public int countByPubCode(String code) throws ParseException {
        List<SMst> getAll = null;
        getAll = findBy("countByPubCode", code);
        int size = getAll.size();
        return size;
    }
    
    public List<SMst> getCatRecId(String setNo) throws ParseException {
        String q = "";
        List<SMst> result = new ArrayList<>();
        Query query;
        List<SBindingSet> getAll = null;
        getAll = sBindingSetFacadeREST.findBy("findBySBSetno", setNo);
        System.out.println("getaLL" + getAll);

        StringJoiner joiner = new StringJoiner("','");
        for (SBindingSet get : getAll) {
            joiner.add(get.getSBindingSetPK().getSBRecid());
        }
        q = "SELECT * FROM s_mst WHERE s_recid IN ('" + joiner + "') ";
        query = getEntityManager().createNativeQuery(q,SMst.class);
        result = (List<SMst>) query.getResultList();
        return result;
    }
    
    /**
     *
     * @param bound
     * @param title
     * @param abrtitle
     * @param supplier
     * @param language
     * @param publisher
     * @param issn
     * @param department
     * @param coden
     * @param location
     * @param classcode
     * @param annualIndex
     * @param startDate
     * @param issPerVol
     * @param endDate
     * @param frequency
     * @param startIssue
     * @param leadTIme
     * @param startVolume
     * @param mode
     * @param endIssue
     * @param budget
     * @param endVolume
     * @param price
     * @param currency
     * @param note
     * @param edition
     * @param convRate
     * @param firstYear
     * @param country
     * @param url
     * @param physicalMedia
     * @param approvedCopy
     * @param subject
     * @return
     * @throws ParseException
     */
    @POST
    @Path("titleEntry")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain"})
    public String titleEntry(@FormParam("bound") String bound, @FormParam("title") String title,
            @FormParam("abrtitle") String abrtitle, @FormParam("supplier") String supplier,
            @FormParam("language") String language, @FormParam("publisher") String publisher,
            @FormParam("issn") String issn, @FormParam("department") String department,
            @FormParam("coden") String coden, @FormParam("location") String location,
            @FormParam("classcode") String classcode, @FormParam("annualIndex") String annualIndex,
            @FormParam("subscrFrom") String subscrFrom, @FormParam("issPerVol") String issPerVol,
            @FormParam("subscrTo") String subscrTo, @FormParam("frequency") String frequency, @FormParam("parallelLanguage") String parallelLanguage,
            @FormParam("startIssue") String startIssue, @FormParam("leadTime") String leadTIme, @FormParam("parallelTitle") String parallelTitle,
            @FormParam("startVolume") String startVolume, @FormParam("deliveryMode") String mode, @FormParam("otherTitle") String otherTitle,
            @FormParam("endIssue") String endIssue, @FormParam("budget") String budget, @FormParam("status") String status,
            @FormParam("endVolume") String endVolume, @FormParam("price") BigDecimal price,
            @FormParam("currency") String currency, @FormParam("note") String note,@FormParam("edition") String edition,
            @FormParam("convRate") String convRate, @FormParam("yearOfPub") String firstYear, @FormParam("code") String code,
            @FormParam("country") String country, @FormParam("url") String url,@FormParam("physicalMedia") String physicalMedia,
            @FormParam("approvedCopy") String approvedCopy, @FormParam("subject") String subject) throws ParseException {
        SMst smst = new SMst();
        MFcltydept dept = mFcltydeptFacadeREST.find(Integer.parseInt(department));
        SSupplierDetail supplierCode = sSuppDetailFacadeREST.find(supplier);
        SSupplierDetail publisherCode = sSuppDetailFacadeREST.find(publisher);
        BkSubject bkSub = bkSubjectFacadeREST.find(subject);
        SFrequency sFrequ = sFrequencyFacadeREST.find(frequency);
        SEdition sEd = sEditionFacadeREST.find(edition);
        //ABudget budgetCode = aBudgetFacadeREST.find(Integer.parseInt(budget));
        Libmaterials phyCd = libmaterialsFacadeREST.find(physicalMedia);
        ACurrency curr = aCurrencyFacadeREST.find(currency);
        BkSubject sub = bkSubjectFacadeREST.find(subject);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (dept == null) 
          //  throw new DataException("No publisher found with publisher code : " + department);
        System.out.println("Department COde: " + dept);
        
        if (publisherCode == null) 
           // throw new DataException("No publisher found with publisher code : " + publisher);
        System.out.println("Publisher COde: " + publisherCode);
        
        if (supplierCode == null) 
          //  throw new DataException("No supplier found with supplier code : " + supplier);
        System.out.println("Supplier Code: " + supplierCode);
        
        if (sFrequ == null) 
           // throw new DataException("No frequency found with frequency code : " + frequency);
        System.out.println("Frequency Code: " + sFrequ + "Frequency Name: "+sFrequ.getSFNm());
  
        if (bkSub == null) 
           // throw new DataException("No subject found with subjectCode : " + subject);
        System.out.println("Supplier Code: " + bkSub);
          
        if (edition == null) 
           // throw new DataException("No edition found with editionCode : " + edition);
        System.out.println("Edition: " + sEd);      
        
        if(phyCd == null)
          //  throw new DataException("No media found with mediaCode : " + physicalMedia);
        System.out.println("Supplier COde: " + phyCd);

//        if (budgetCode == null) 
//            throw new DataException("No budget found with budgetCode : " + budget);
//        System.out.println("Supplier COde: " + budgetCode);
        
        smst.setSRecid(createNewRecId(title));  
        smst.setSTyAcqu("M");
        smst.setSBound(bound);
        smst.setSTitle(title);
        smst.setSAbrTitle(abrtitle);
        smst.setSLanCd(language);
        smst.setSIssn(issn);
        smst.setSCoden(coden);
        smst.setSClassCd(sub.getBkClassno());
        smst.setSSubCd(sub.getBkSubjectName());
        smst.setSPubCd(publisher);
        smst.setSSupCd(supplier);
        smst.setSDeptCd(department);
        smst.setSLocation(location);
        smst.setSAnnualIdx(annualIndex);
        smst.setSStDt(dateFormat.parse(subscrFrom));
        smst.setSEnDt(dateFormat.parse(subscrTo));
        smst.setSFirstYear(dateFormat.parse(firstYear));
        smst.setSStVol(startVolume);
        smst.setSEnVol(endVolume);
        smst.setSStIss(startIssue);
        smst.setSEnIss(endVolume);
        smst.setSIssPerVol(Integer.parseInt(issPerVol));
        smst.setSFreqCd(frequency);
        smst.setSLeadtime(Integer.parseInt(leadTIme));
        smst.setSBugetCd(budget);
        smst.setSMode(mode);
        smst.setSPrice(price);
        smst.setSCurrency(currency);
        smst.setSConvRate(curr.getACRate());
        smst.setSNote(note);
        smst.setSPubCity(publisherCode.getASCity());
        smst.setSPubCountry(publisherCode.getASCountry());
        smst.setSSupCity(supplierCode.getASCity());
        smst.setSSupCountry(supplierCode.getASCountry());
        smst.setSStatus(status);
        smst.setSDirect("D");
        smst.setSUrl(url);
        smst.setSCode(code);
        smst.setSEdition(edition);
        smst.setSApprCopy(1);
        
        
        
        //--biblidetails entry start
        Biblidetails biblidetails = new Biblidetails();
        biblidetails = createBiblidetails(smst, supplierCode.getASName(), publisherCode.getASName(), sFrequ.getSFNm());
        smst.setSCatRecid(Integer.toString(biblidetails.getBiblidetailsPK().getRecID()));
        create(smst);
        //--biblidetails entry finished
        
        //-- other title entry
        SOthertitle ot = new SOthertitle();
        ot.setSORecid(smst.getSRecid());
        ot.setSOTitle(otherTitle);
        sOthertitleFacadeREST.create(ot);
        //-- other title entry ends here
        
        //-- s_parallel title entry
        SParallel pl = new SParallel();
        pl.setSPRecid(smst.getSRecid());
        pl.setSPTitle(parallelTitle);
        pl.setSPLanCd(parallelLanguage);
        sParallelFacadeREST.create(pl);
        //-- other title entry ends here
        return "Record with mark record: "+smst.getSCatRecid()+" created.";
    }
    
    @GET
    @Path("getNewRecId/{title}")
    public String createNewRecId(@PathParam("title") String title){
        
        List<SMst> allRecords = findAll();
        ArrayList<String> recIds = new ArrayList<>();
        for (int i=0; i<allRecords.size(); i++){
            recIds.add(allRecords.get(i).getSRecid());
        }
        
        ArrayList<String> newRecIds = new ArrayList<>();
        for(int i=0; i<recIds.size(); i++){
            if(recIds.get(i).charAt(0) == title.charAt(0)){
                newRecIds.add(recIds.get(i));
            }
        }
        StringBuilder recId = new StringBuilder();
        int size = newRecIds.size();
        recId.append(title.charAt(0));
        
        if(size < 10)
            recId.append("000" + (size + 1));
        else if(size>= 10 && size<100)
            recId.append("00" + (size + 1));
        else if(size>= 100 && size<1000)
            recId.append("0" + (size + 1));
        else if(size>=1000 && size<10000)
            recId.append("" + (size + 1));
        return recId.toString();
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
    
    @POST
    @Path("titleUpdate")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces({"text/plain"})
    public String titleUpdate(@FormParam("bound") String bound, @FormParam("title") String title,
            @FormParam("abrtitle") String abrtitle, @FormParam("supplier") String supplier,
            @FormParam("language") String language, @FormParam("publisher") String publisher,
            @FormParam("issn") String issn, @FormParam("department") String department,
            @FormParam("coden") String coden, @FormParam("location") String location,
            @FormParam("classcode") String classcode, @FormParam("annualIndex") String annualIndex,
            @FormParam("startDate") String startDate, @FormParam("issPerVol") String issPerVol,
            @FormParam("endDate") String endDate, @FormParam("frequency") String frequency, @FormParam("parallelLanguage") String parallelLanguage,
            @FormParam("startIssue") String startIssue, @FormParam("leadTIme") String leadTIme, @FormParam("parallelTitle") String parallelTitle,
            @FormParam("startVolume") String startVolume, @FormParam("mode") String mode, @FormParam("otherTitle") String otherTitle,
            @FormParam("endIssue") String endIssue, @FormParam("budget") String budget, @FormParam("status") String status,
            @FormParam("endVolume") String endVolume, @FormParam("price") BigDecimal price,
            @FormParam("currency") String currency, @FormParam("note") String note,@FormParam("edition") String edition,
            @FormParam("convRate") String convRate, @FormParam("firstYear") String firstYear, @FormParam("code") String code,
            @FormParam("country") String country, @FormParam("url") String url,@FormParam("physicalMedia") String physicalMedia,
            @FormParam("approvedCopy") String approvedCopy, @FormParam("subject") String subject) throws ParseException {
        List<SMst> smst = findBy("findBySTitle",title);
        MFcltydept dept = mFcltydeptFacadeREST.find(Integer.parseInt(department));
        SSupplierDetail supplierCode = sSuppDetailFacadeREST.find(supplier);
        SSupplierDetail publisherCode = sSuppDetailFacadeREST.find(publisher);
        BkSubject bkSub = bkSubjectFacadeREST.find(subject);
        SFrequency sFrequ = sFrequencyFacadeREST.find(frequency);
        SEdition sEd = sEditionFacadeREST.find(edition);
        ABudget budgetCode = aBudgetFacadeREST.find(Integer.parseInt(budget));
        Libmaterials phyCd = libmaterialsFacadeREST.find(physicalMedia);
        ACurrency curr = aCurrencyFacadeREST.find(currency);
        BkSubject sub = bkSubjectFacadeREST.find(subject);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (dept == null) 
         //   throw new DataException("No publisher found with publisher code : " + department);
        System.out.println("Department COde: " + dept);
        
        if (publisherCode == null) 
          //  throw new DataException("No publisher found with publisher code : " + publisher);
        System.out.println("Publisher COde: " + publisherCode);
        
        if (supplierCode == null) 
          //  throw new DataException("No supplier found with supplier code : " + supplier);
        System.out.println("Supplier Code: " + supplierCode);
        
        if (sFrequ == null) 
          //  throw new DataException("No frequency found with frequency code : " + frequency);
        System.out.println("Frequency Code: " + sFrequ + "Frequency Name: "+sFrequ.getSFNm());
  
        if (bkSub == null) 
          //  throw new DataException("No subject found with subjectCode : " + subject);
        System.out.println("Supplier Code: " + bkSub);
          
        if (edition == null) 
          //  throw new DataException("No edition found with editionCode : " + edition);
        System.out.println("Edition: " + sEd);      
        
        if(phyCd == null)
          //  throw new DataException("No media found with mediaCode : " + physicalMedia);
        System.out.println("Supplier COde: " + phyCd);

        if (budgetCode == null) 
         //   throw new DataException("No budget found with budgetCode : " + budget);
        System.out.println("Supplier COde: " + budgetCode);
        
        smst.get(0).setSTyAcqu("M");
        smst.get(0).setSBound(bound);
        smst.get(0).setSAbrTitle(abrtitle);
        smst.get(0).setSLanCd(language);
        smst.get(0).setSIssn(issn);
        smst.get(0).setSCoden(coden);
        smst.get(0).setSClassCd(sub.getBkClassno());
        smst.get(0).setSSubCd(sub.getBkSubjectName());
        smst.get(0).setSPubCd(publisher);
        smst.get(0).setSSupCd(supplier);
        smst.get(0).setSDeptCd(department);
        smst.get(0).setSLocation(location);
        smst.get(0).setSAnnualIdx(annualIndex);
        smst.get(0).setSStDt(dateFormat.parse(startDate));
        smst.get(0).setSEnDt(dateFormat.parse(endDate));
        smst.get(0).setSFirstYear(dateFormat.parse(firstYear));
        smst.get(0).setSStVol(startVolume);
        smst.get(0).setSEnVol(endVolume);
        smst.get(0).setSStIss(startIssue);
        smst.get(0).setSEnIss(endVolume);
        smst.get(0).setSIssPerVol(Integer.parseInt(issPerVol));
        smst.get(0).setSFreqCd(frequency);
        smst.get(0).setSLeadtime(Integer.parseInt(leadTIme));
        smst.get(0).setSBugetCd(budget);
        smst.get(0).setSMode(mode);
        smst.get(0).setSPrice(price);
        smst.get(0).setSCurrency(currency);
        smst.get(0).setSConvRate(curr.getACRate());
        smst.get(0).setSNote(note);
        smst.get(0).setSPubCity(publisherCode.getASCity());
        smst.get(0).setSPubCountry(publisherCode.getASCountry());
        smst.get(0).setSSupCity(supplierCode.getASCity());
        smst.get(0).setSSupCountry(supplierCode.getASCountry());
        smst.get(0).setSStatus(status);
        smst.get(0).setSDirect("D");
        smst.get(0).setSUrl(url);
        smst.get(0).setSCode(code);
        smst.get(0).setSEdition(edition);
        smst.get(0).setSApprCopy(1);
        
        
        
        //--biblidetails entry start
        biblidetailsFacadeREST.removeBy("deleteByRecID",smst.get(0).getSCatRecid());
        Biblidetails biblidetails = new Biblidetails();
        biblidetails = createBiblidetails(smst.get(0), supplierCode.getASName(), publisherCode.getASName(), sFrequ.getSFNm());
        
        edit(smst.get(0));
        //--biblidetails entry finished
        
        //-- other title entry
        SOthertitle ot = sOthertitleFacadeREST.find(smst.get(0).getSRecid());
        ot.setSOTitle(otherTitle);
        sOthertitleFacadeREST.edit(ot);
        //-- other title entry ends here
        
        //-- s_parallel title entry
        SParallel pl = sParallelFacadeREST.find(smst.get(0).getSRecid());
        pl.setSPTitle(parallelTitle);
        pl.setSPLanCd(parallelLanguage);
        sParallelFacadeREST.edit(pl);
        //-- other title entry ends here
        return "Record with mark record: "+smst.get(0).getSCatRecid()+" created.";
    }
    
    private Biblidetails createBiblidetails(SMst smst, String supplierName, String publisherName, String frequency) {
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

        if (smst.getSCoden() != null) {
            biblidetails = getBiblidetails(recId, "030", 1, "", 0, null, smst.getSCoden());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (smst.getSIssn() != null) {
            biblidetails = getBiblidetails(recId, "022", 1, "a", 1, null, smst.getSIssn());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (smst.getSAbrTitle() != null) {
            biblidetails = getBiblidetails(recId, "210", 1, "a", 1, "0", smst.getSAbrTitle());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (smst.getSTitle() != null) {
            biblidetails = getBiblidetails(recId, "245", 1, "a", 1, "00", smst.getSTitle());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (smst.getSTitle() != null) {
            biblidetails = getBiblidetails(recId, "222", 1, "a", 1, "00", smst.getSTitle());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (smst.getSCoden() != null) {
            String tag222 = smst.getSTitle() + " " + smst.getSCoden();
            biblidetails = getBiblidetails(recId, "222", 1, "b", 1, null, tag222);
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (smst.getSNote() != null) {
            biblidetails = getBiblidetails(recId, "246", 1, "", 0, null, smst.getSNote());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (smst.getSUrl() != null) {
            biblidetails = getBiblidetails(recId, "256", 1, "", 0, null, smst.getSUrl());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (smst.getSPubCd() != null) {
            biblidetails = getBiblidetails(recId, "260", 1, "b", 1, null, publisherName);
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (smst.getSPubCity() != null) {
            biblidetails = getBiblidetails(recId, "260", 1, "a", 1, null, smst.getSPubCity());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (smst.getSEdition() != null) {
            biblidetails = getBiblidetails(recId, "250", 1, "a", 1, null, smst.getSEdition());
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (smst.getSFreqCd() != null) {
            biblidetails = getBiblidetails(recId, "310", 1, "a", 1, null, frequency);
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (smst.getSSubCd() != null) {
            biblidetails = getBiblidetails(recId, "630", 1, "a", 1, null, supplierName);
            biblidetailsFacadeREST.create(biblidetails);
        }

        if (smst.getSUrl() != null) {
            biblidetails = getBiblidetails(recId, "856", 1, "u", 1, null, smst.getSUrl());
            biblidetailsFacadeREST.create(biblidetails);
        }

        return biblidetails;
    }
    
    @GET
    @Path("titleReport/{paramName}/{paramValue}")
    @Produces({"application/xml", "application/json"})
    public List<String> titleReport(@PathParam("paramName") String paramName,@PathParam("paramValue") String paramValue) throws ParseException {
        String q = "";
        String[] valueString = paramValue.split(",");
        List<String> result = new ArrayList<>();
        Query query;
        switch (paramName) {
            case "byLocation":
                q = "SELECT distinct s_recid, s_mst.s_title, s_mst.s_issn, s_mst.s_sub_cd, s_supplier_1.a_s_nm AS Publisher, s_supplier.a_s_nm AS Publisher,\n" +
                        "m_fcltydept.Fclty_dept_dscr, s_frequency.s_f_nm, s_mst.s_freq_cd, s_mode.s_mode_desc, b_heads.short_desc, s_mst.s_price, \n" +
                        "s_mst.s_status, s_mst.s_inv_no, s_mst.s_order_no \n" +
                        "FROM s_mst, s_supplier s_supplier_1, s_supplier, m_fcltydept, s_frequency, s_mode, b_heads, bk_subject, LibMaterials, LanguageMaster,a_budget \n" +
                        "WHERE s_mst.s_pub_cd = s_supplier_1.a_s_cd AND s_mst.s_sup_cd = s_supplier.a_s_cd AND s_mst.s_dept_cd = m_fcltydept.Fclty_dept_cd AND \n" +
                        "s_mst.s_freq_cd = s_frequency.s_f_cd AND s_mst.s_mode = s_mode.s_mode_cd AND \n" +
                        "s_mst.s_buget_cd = a_budget.a_b_budget_code and budget_code = budget_head and s_location = '" + paramValue + "' ";
                break;

            case "byDepartment":
                q = "SELECT distinct s_recid, s_mst.s_title, s_mst.s_issn, s_mst.s_sub_cd, s_supplier_1.a_s_nm AS Publisher, s_supplier.a_s_nm AS Publisher, m_fcltydept.Fclty_dept_dscr, \n" +
                        "s_frequency.s_f_nm, s_mst.s_freq_cd, s_mode.s_mode_desc, b_heads.short_desc, s_mst.s_price, s_mst.s_status, s_mst.s_inv_no, s_mst.s_order_no\n" +
                        " FROM s_mst, s_supplier s_supplier_1, s_supplier, m_fcltydept, s_frequency, s_mode, b_heads, bk_subject, LibMaterials, LanguageMaster,a_budget\n" +
                        " WHERE s_mst.s_pub_cd = s_supplier_1.a_s_cd AND s_mst.s_sup_cd = s_supplier.a_s_cd AND s_mst.s_dept_cd = m_fcltydept.Fclty_dept_cd\n" +
                        " AND s_mst.s_freq_cd = s_frequency.s_f_cd AND s_mst.s_mode = s_mode.s_mode_cd AND s_mst.s_buget_cd = a_budget.a_b_budget_code\n" +
                        " and budget_code=budget_head   and s_dept_cd in (select Fclty_dept_cd from m_fcltydept where fclty_dept_dscr like '" + paramValue + "') ";
                break;

            case "bySubscription":
                q = "SELECT distinct s_recid, s_mst.s_title, s_mst.s_issn, s_mst.s_sub_cd, s_supplier_1.a_s_nm AS Publisher, s_supplier.a_s_nm AS Publisher, m_fcltydept.Fclty_dept_dscr, \n" +
                        "s_frequency.s_f_nm, s_mst.s_freq_cd, s_mode.s_mode_desc, b_heads.short_desc, s_mst.s_price, s_mst.s_status, s_mst.s_inv_no, s_mst.s_order_no \n" +
                        "FROM s_mst, s_supplier s_supplier_1, s_supplier, m_fcltydept, s_frequency, s_mode, b_heads, bk_subject, LibMaterials, LanguageMaster,a_budget \n" +
                        "WHERE s_mst.s_pub_cd = s_supplier_1.a_s_cd AND s_mst.s_sup_cd = s_supplier.a_s_cd AND s_mst.s_dept_cd = m_fcltydept.Fclty_dept_cd AND \n" +
                        "s_mst.s_freq_cd = s_frequency.s_f_cd AND s_mst.s_mode = s_mode.s_mode_cd AND \n" +
                        "s_mst.s_buget_cd = a_budget.a_b_budget_code and budget_code=budget_head   and s_order_no = '" + paramValue + "' ";
                break;

            case "bySupplier":
                q = "SELECT distinct s_recid, s_mst.s_title, s_mst.s_issn, s_mst.s_sub_cd, s_supplier_1.a_s_nm AS Publisher, s_supplier.a_s_nm AS Publisher, m_fcltydept.Fclty_dept_dscr, \n" +
                        "s_frequency.s_f_nm, s_mst.s_freq_cd, s_mode.s_mode_desc, b_heads.short_desc, s_mst.s_price, s_mst.s_status, s_mst.s_inv_no, s_mst.s_order_no \n" +
                        "FROM s_mst, s_supplier s_supplier_1, s_supplier, m_fcltydept, s_frequency, s_mode, b_heads, bk_subject, LibMaterials, LanguageMaster,a_budget \n" +
                        "WHERE s_mst.s_pub_cd = s_supplier_1.a_s_cd AND s_mst.s_sup_cd = s_supplier.a_s_cd AND s_mst.s_dept_cd = m_fcltydept.Fclty_dept_cd AND \n" +
                        "s_mst.s_freq_cd = s_frequency.s_f_cd AND s_mst.s_mode = s_mode.s_mode_cd AND \n" +
                        "s_mst.s_buget_cd = a_budget.a_b_budget_code and budget_code=budget_head and s_mst.s_pub_cd in (select a_s_cd from s_supplier where a_s_nm = '" + paramValue + "' ";
                break;
                
            case "byPublisher":
                q = "SELECT distinct s_recid, s_mst.s_title, s_mst.s_issn, s_mst.s_sub_cd, s_supplier_1.a_s_nm AS Publisher, s_supplier.a_s_nm AS Publisher, m_fcltydept.Fclty_dept_dscr, \n" +
                    "s_frequency.s_f_nm, s_mst.s_freq_cd, s_mode.s_mode_desc, b_heads.short_desc, s_mst.s_price, s_mst.s_status, s_mst.s_inv_no, s_mst.s_order_no \n" +
                    "FROM s_mst, s_supplier s_supplier_1, s_supplier, m_fcltydept, s_frequency, s_mode, b_heads, bk_subject, LibMaterials, LanguageMaster,a_budget \n" +
                    "WHERE s_mst.s_pub_cd = s_supplier_1.a_s_cd AND s_mst.s_sup_cd = s_supplier.a_s_cd AND s_mst.s_dept_cd = m_fcltydept.Fclty_dept_cd AND \n" +
                    "s_mst.s_freq_cd = s_frequency.s_f_cd AND s_mst.s_mode = s_mode.s_mode_cd AND \n" +
                    "s_mst.s_buget_cd =  a_budget.a_b_budget_code and budget_code=budget_head and s_mst.s_sup_cd in (select a_s_cd from s_supplier where a_s_nm = '" + paramValue + "') ";
                break;

            case "byBudgetHead":
                q = "SELECT distinct s_recid, s_mst.s_title, s_mst.s_issn, s_mst.s_sub_cd, s_supplier_1.a_s_nm AS Publisher, s_supplier.a_s_nm AS Publisher, m_fcltydept.Fclty_dept_dscr, \n" +
                        "s_frequency.s_f_nm, s_mst.s_freq_cd, s_mode.s_mode_desc, b_heads.short_desc, s_mst.s_price, s_mst.s_status, s_mst.s_inv_no, s_mst.s_order_no \n" +
                        "FROM s_mst, s_supplier s_supplier_1, s_supplier, m_fcltydept, s_frequency, s_mode, b_heads, bk_subject, LibMaterials, LanguageMaster,a_budget \n" +
                        "WHERE s_mst.s_pub_cd = s_supplier_1.a_s_cd AND s_mst.s_sup_cd = s_supplier.a_s_cd AND s_mst.s_dept_cd = m_fcltydept.Fclty_dept_cd \n" +
                        "AND s_mst.s_freq_cd = s_frequency.s_f_cd AND s_mst.s_mode = s_mode.s_mode_cd AND s_mst.s_buget_cd =  a_budget.a_b_budget_code \n" +
                        "and budget_code=budget_head   and s_mst.s_buget_cd in (select (a_b_budget_code as varchar) from b_heads,a_budget where budget_code = budget_head and short_desc = '" + paramValue + "') ";
                break;
                
            case "byFrequency":                                                             
                q = "SELECT distinct s_recid, s_mst.s_title, s_mst.s_issn, s_mst.s_sub_cd, s_supplier_1.a_s_nm AS Publisher, s_supplier.a_s_nm AS Publisher,\n" +
                        " m_fcltydept.Fclty_dept_dscr, s_frequency.s_f_nm, s_mst.s_freq_cd, s_mode.s_mode_desc, b_heads.short_desc, s_mst.s_price, s_mst.s_status, \n" +
                        " s_mst.s_inv_no, s_mst.s_order_no FROM s_mst, s_supplier s_supplier_1, s_supplier, m_fcltydept, s_frequency, s_mode, b_heads, bk_subject, \n" +
                        " LibMaterials, LanguageMaster,a_budget WHERE s_mst.s_pub_cd = s_supplier_1.a_s_cd AND s_mst.s_sup_cd = s_supplier.a_s_cd AND \n" +
                        " s_mst.s_dept_cd = m_fcltydept.Fclty_dept_cd AND s_mst.s_freq_cd = s_frequency.s_f_cd AND s_mst.s_mode = s_mode.s_mode_cd \n" +
                        " AND s_mst.s_buget_cd =  a_budget.a_b_budget_code and budget_code=budget_head   and s_mst.s_freq_cd in (select s_f_cd from s_frequency where s_f_nm = '" + paramValue + "') ";
                break;

            case "byDeliveryMode":
                q = "SELECT distinct s_recid, s_mst.s_title, s_mst.s_issn, s_mst.s_sub_cd, s_supplier_1.a_s_nm AS Publisher, s_supplier.a_s_nm AS Publisher, m_fcltydept.Fclty_dept_dscr, \n" +
                        "s_frequency.s_f_nm, s_mst.s_freq_cd, s_mode.s_mode_desc, b_heads.short_desc, s_mst.s_price, s_mst.s_status, s_mst.s_inv_no, s_mst.s_order_no \n" +
                        "FROM s_mst, s_supplier s_supplier_1, s_supplier, m_fcltydept, s_frequency, s_mode, b_heads, bk_subject, LibMaterials, LanguageMaster,a_budget \n" +
                        "WHERE s_mst.s_pub_cd = s_supplier_1.a_s_cd AND s_mst.s_sup_cd = s_supplier.a_s_cd AND s_mst.s_dept_cd = m_fcltydept.Fclty_dept_cd AND \n" +
                        "s_mst.s_freq_cd = s_frequency.s_f_cd AND s_mst.s_mode = s_mode.s_mode_cd AND s_mst.s_buget_cd =  a_budget.a_b_budget_code and \n" +
                        "budget_code=budget_head   and s_mst.s_mode in (select s_mode_cd from s_mode where s_mode_desc = '" + paramValue + "')  ";
                break;
                
            case "byClassificationNo":
                q = "SELECT distinct s_recid, s_mst.s_title, s_mst.s_issn, s_mst.s_sub_cd, s_supplier_1.a_s_nm AS Publisher, s_supplier.a_s_nm AS Publisher,\n" +
                        " m_fcltydept.Fclty_dept_dscr, s_frequency.s_f_nm, s_mst.s_freq_cd, s_mode.s_mode_desc, b_heads.short_desc, s_mst.s_price, s_mst.s_status,\n" +
                        " s_mst.s_inv_no, s_mst.s_order_no FROM s_mst, s_supplier s_supplier_1, s_supplier, m_fcltydept, s_frequency, s_mode, b_heads, bk_subject, \n" +
                        " LibMaterials, LanguageMaster,a_budget WHERE s_mst.s_pub_cd = s_supplier_1.a_s_cd AND s_mst.s_sup_cd = s_supplier.a_s_cd \n" +
                        " AND s_mst.s_dept_cd = m_fcltydept.Fclty_dept_cd AND s_mst.s_freq_cd = s_frequency.s_f_cd AND s_mst.s_mode = s_mode.s_mode_cd \n" +
                        " AND s_mst.s_buget_cd =  a_budget.a_b_budget_code and budget_code=budget_head   \n" +
                        " and s_mst.s_class_cd in (select bk_classno from bk_subject where bk_subject = '" + paramValue + "') ";
                break;
                
            case "byLanguage":
                q = "SELECT distinct s_recid, s_mst.s_title, s_mst.s_issn, s_mst.s_sub_cd, s_supplier_1.a_s_nm AS Publisher, s_supplier.a_s_nm AS Publisher, \n" +
                        "m_fcltydept.Fclty_dept_dscr, s_frequency.s_f_nm, s_mst.s_freq_cd, s_mode.s_mode_desc, b_heads.short_desc, s_mst.s_price, s_mst.s_status, \n" +
                        "s_mst.s_inv_no, s_mst.s_order_no FROM s_mst, s_supplier s_supplier_1, s_supplier, m_fcltydept, s_frequency, s_mode, b_heads, bk_subject, \n" +
                        "LibMaterials, LanguageMaster,a_budget WHERE s_mst.s_pub_cd = s_supplier_1.a_s_cd AND s_mst.s_sup_cd = s_supplier.a_s_cd AND \n" +
                        "s_mst.s_dept_cd = m_fcltydept.Fclty_dept_cd AND s_mst.s_freq_cd = s_frequency.s_f_cd AND s_mst.s_mode = s_mode.s_mode_cd AND \n" +
                        "s_mst.s_buget_cd =  a_budget.a_b_budget_code and budget_code=budget_head   and s_mst.s_lan_cd in (select code from LanguageMaster where Biblanguage='" + paramValue + "') ";
                break;
                                                                    
            case "bySubject":
                q = "SELECT distinct s_recid, s_mst.s_title, s_mst.s_issn, s_mst.s_sub_cd, s_supplier_1.a_s_nm AS Publisher, s_supplier.a_s_nm AS Publisher, \n" +
                        "m_fcltydept.Fclty_dept_dscr, s_frequency.s_f_nm, s_mst.s_freq_cd, s_mode.s_mode_desc, b_heads.short_desc, s_mst.s_price, s_mst.s_status, \n" +
                        "s_mst.s_inv_no, s_mst.s_order_no FROM s_mst, s_supplier s_supplier_1, s_supplier, m_fcltydept, s_frequency, s_mode, b_heads, bk_subject, \n" +
                        "LibMaterials, LanguageMaster,a_budget WHERE s_mst.s_pub_cd = s_supplier_1.a_s_cd AND s_mst.s_sup_cd = s_supplier.a_s_cd AND \n" +
                        "s_mst.s_dept_cd = m_fcltydept.Fclty_dept_cd AND s_mst.s_freq_cd = s_frequency.s_f_cd AND s_mst.s_mode = s_mode.s_mode_cd AND \n" +
                        "s_mst.s_buget_cd =  a_budget.a_b_budget_code and budget_code=budget_head   and s_mst.s_sub_cd like '" + paramValue + "' ";
                break;

            case "expiryDateBetween":
                q = "SELECT distinct s_recid, s_mst.s_title, s_mst.s_issn, s_mst.s_sub_cd, s_supplier_1.a_s_nm AS Publisher, s_supplier.a_s_nm AS \n" +
                        "Publisher, m_fcltydept.Fclty_dept_dscr, s_frequency.s_f_nm, s_mst.s_freq_cd, s_mode.s_mode_desc, b_heads.short_desc, s_mst.s_price, \n" +
                        "s_mst.s_status, s_mst.s_inv_no, s_mst.s_order_no FROM s_mst, s_supplier s_supplier_1, s_supplier, m_fcltydept, s_frequency, s_mode, \n" +
                        "b_heads, bk_subject, LibMaterials, LanguageMaster,a_budget WHERE s_mst.s_pub_cd = s_supplier_1.a_s_cd AND \n" +
                        "s_mst.s_sup_cd = s_supplier.a_s_cd AND s_mst.s_dept_cd = m_fcltydept.Fclty_dept_cd AND s_mst.s_freq_cd = s_frequency.s_f_cd AND \n" +
                        "s_mst.s_mode = s_mode.s_mode_cd AND s_mst.s_buget_cd =  a_budget.a_b_budget_code and budget_code=budget_head and \n" +
                        "(s_mst.s_en_dt>'" + valueString[0] + "' and s_mst.s_en_dt<'" + valueString[1] + "') ";
                break;    
        }
        

        //List<Object> result;
        query = getEntityManager().createNativeQuery(q,SMst.class);
        result = query.getResultList();
        return result;
    }
    
    @GET
    @Path("titleHoldingReport/{paramName}/{paramValue}")
    @Produces({"application/xml", "application/json"})
    public List<String> titleHoldingReport(@PathParam("paramName") String paramName,@PathParam("paramValue") String paramValue) throws ParseException {
        String q = "";
        String[] valueString = paramValue.split(",");
        List<String> result = new ArrayList<>();
        Query query;
        switch (paramName) {
            case "byLocation":
                q = "select a_s_nm as s_hasdetail,s_lackdetail,s_title + '////' + s_remark as s_remark,s_missing,s_available \n" +
                        "from s_hist_hold,s_mst,s_supplier \n" +
                        "where s_mst.s_recid=s_hist_hold.s_recid AND s_mst.s_pub_cd = s_supplier.a_s_cd \n" +
                        "and s_location = '" + paramValue + "' ";
                break;

            case "byDepartment":
                q = "select a_s_nm as s_hasdetail,s_lackdetail,s_title + '////' + s_remark as s_remark,s_missing,s_available \n" +
                        "from s_hist_hold,s_mst,s_supplier \n" +
                        "where s_mst.s_recid = s_hist_hold.s_recid AND s_mst.s_pub_cd = s_supplier.a_s_cd \n" +
                        "and s_dept_cd in (select Fclty_dept_cd from m_fcltydept where fclty_dept_dscr = '" + paramValue + "') ";
                break;

            case "bySubscription":
                q = "select a_s_nm as s_hasdetail,s_lackdetail,s_title + '////' + s_remark as s_remark,s_missing,s_available\n" +
                        "from s_hist_hold,s_mst,s_supplier\n" +
                        "where s_mst.s_recid=s_hist_hold.s_recid AND s_mst.s_pub_cd = s_supplier.a_s_cd \n" +
                        "and s_order_no = '" + paramValue + "' ";
                break;

            case "bySupplier":
                q = "select a_s_nm as s_hasdetail,s_lackdetail,s_title + '////' + s_remark as s_remark,s_missing,s_available \n" +
                        "from s_hist_hold,s_mst,s_supplier \n" +
                        "where s_mst.s_recid=s_hist_hold.s_recid AND s_mst.s_pub_cd = s_supplier.a_s_cd  \n" +
                        "and s_mst.s_sup_cd in (select a_s_cd from s_supplier where a_s_nm = '" + paramValue + "' ";
                break;
                
            case "byPublisher":
                q = "select a_s_nm as s_hasdetail,s_lackdetail,s_title + '////' + s_remark as s_remark,s_missing,s_available \n" +
                        "from s_hist_hold,s_mst,s_supplier \n" +
                        "where s_mst.s_recid=s_hist_hold.s_recid AND s_mst.s_pub_cd = s_supplier.a_s_cd  \n" +
                        "and s_mst.s_pub_cd in (select a_s_cd from s_supplier where a_s_nm = '" + paramValue + "')";
                break;

            case "byTitle":
                q = "select a_s_nm as s_hasdetail,s_lackdetail,s_title + '////' + s_remark as s_remark,s_missing,s_available \n" +
                        "from s_hist_hold,s_mst,s_supplier \n" +
                        "where s_mst.s_recid=s_hist_hold.s_recid AND s_mst.s_pub_cd = s_supplier.a_s_cd and \n" +
                        "s_mst.s_title like '" + paramValue + "'% ";
                break;
                
            case "byFrequency":                                                             
                q = "select a_s_nm as s_hasdetail,s_lackdetail,s_title + '////' + s_remark as s_remark,s_missing,s_available \n" +
                    "from s_hist_hold,s_mst,s_supplier \n" +
                    "where s_mst.s_recid=s_hist_hold.s_recid AND s_mst.s_pub_cd = s_supplier.a_s_cd and \n" +
                    "s_mst.s_freq_cd in (select s_f_cd from s_frequency where s_f_nm = '" + paramValue + "')";
                break;

            case "byDeliveryMode":
                q = "select a_s_nm as s_hasdetail,s_lackdetail,s_title + '////' + s_remark as s_remark,s_missing,s_available \n" +
                        "from s_hist_hold,s_mst,s_supplier \n" +
                        "where s_mst.s_recid=s_hist_hold.s_recid AND s_mst.s_pub_cd = s_supplier.a_s_cd \n" +
                        "and s_mst.s_mode in (select s_mode_cd from s_mode where s_mode_desc = '" + paramValue + "') ";
                break;
                
            case "byClassificationNo":
                q = "select a_s_nm as s_hasdetail,s_lackdetail,s_title + '////' + s_remark as s_remark,s_missing,s_available \n" +
                        "from s_hist_hold,s_mst,s_supplier \n" +
                        "where s_mst.s_recid=s_hist_hold.s_recid AND s_mst.s_pub_cd = s_supplier.a_s_cd and \n" +
                        "s_mst.s_class_cd in (select bk_classno from bk_subject where bk_subject = '" + paramValue + "') ";
                break;
                
            case "byLanguage":
                q = "select a_s_nm as s_hasdetail,s_lackdetail,s_title + '////' + s_remark as s_remark,s_missing,s_available \n" +
                        "from s_hist_hold,s_mst,s_supplier where s_mst.s_recid=s_hist_hold.s_recid AND \n" +
                        "s_mst.s_pub_cd = s_supplier.a_s_cd and \n" +
                        "s_mst.s_lan_cd in (select code from LanguageMaster where Biblanguage= '" + paramValue + "') ";
                break;
                                                                    
            case "bySubject":
                q = "select a_s_nm as s_hasdetail,s_lackdetail,s_title + '////' + s_remark as s_remark,s_missing,s_available \n" +
                        "from s_hist_hold,s_mst,s_supplier \n" +
                        "where s_mst.s_recid=s_hist_hold.s_recid AND s_mst.s_pub_cd = s_supplier.a_s_cd \n" +
                        "and s_mst.s_sub_cd like '" + paramValue + "' ";
                break;
        }
        

        //List<Object> result;
        query = getEntityManager().createNativeQuery(q,SMst.class);
        result = query.getResultList();
        return result;
    }
}