/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;


import ar.com.fdvs.dj.core.DJServletHelper;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.util.SortUtils;


//import ExceptionService.DataException;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
//import ar.com.fdvs.dj.test.ReportExporter;
//import ar.com.fdvs.dj.test.TestRepositoryProducts;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.FilenameUtils;
import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import soul.catalogue.Location;
import soul.catalogue.service.LocationFacadeREST;
import soul.circulation.CtgryMediaIssueReserve;
import soul.circulation.IllRqstmst;
import soul.circulation.MMember;
import soul.circulation.TIssue;
import soul.circulation_master.MCalender;
import soul.circulation_master.MCtgry;
import soul.circulation_master.MWeekoffday;
import soul.circulation_master.service.MCalenderFacadeREST;
import soul.circulation_master.service.MCtgryFacadeREST;
import soul.circulation_master.service.MTypeFacadeREST;
import soul.circulation_master.service.MWeekoffdayFacadeREST;
import soul.general_master.service.MBranchmasterFacadeREST;
import soul.general_master.service.MFcltydeptFacadeREST;

import com.thoughtworks.xstream.XStream;
import java.io.ByteArrayInputStream;


import soul.serialControl.service.SRequestFacadeREST;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringJoiner;
import javax.json.JsonArrayBuilder;
import javax.validation.constraints.Pattern;
import javax.ws.rs.InternalServerErrorException;
import net.sf.jasperreports.engine.JRDataSource;
import org.json.JSONException;
import soul.general_master.MFcltydept;
import soul.circulation.TMemdue;
import soul.general_master.MBranchmaster;
import soul.circulation.TReserve;
import soul.circulation.TOtherissue;
import soul.circulation.TReceive;
import soul.circulation.TBookbankissuereturn;
import soul.circulation.MGroup;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.glassfish.jersey.media.multipart.FormDataParam;
import java.io.File;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.ResponseBuilder;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.circulation.Memdetails;
import soul.response.StringData;
import soul.response.StringprocessData;
import soul.serialControl.SRequest;
import soul.response.postdata;
//import sun.text.resources.CollationData;

import soul.user_setting.service.UserdetailFacadeREST;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;
import soul.util.function.DateNTimeChange;



/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.mmember")
public class MMemberFacadeREST extends AbstractFacade<MMember> {

    @EJB
    private UserdetailFacadeREST UserdetailFacadeREST;
    @EJB
    private MCtgryFacadeREST mCtgryFacadeREST;
    @EJB
    private MFcltydeptFacadeREST mFcltydeptFacadeREST;
    @EJB
    private MBranchmasterFacadeREST mBranchmasterFacadeREST;
    @EJB
    private MTypeFacadeREST mTypeFacadeREST;
    @EJB
    private TIssueFacadeREST tIssueFacadeREST;
    @EJB
    private TMemdueFacadeREST tMemdueFacadeREST;
    @EJB
    private IllRqstmstFacadeREST illRqstmstFacadeREST;
    @EJB
    private CtgryMediaIssueReserveFacadeREST ctgryMediaIssueReserveFacadeREST;
    @EJB
    private MWeekoffdayFacadeREST mWeekoffdayFacadeREST;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private MCalenderFacadeREST mCalenderFacadeREST;
    @EJB
    private TReserveFacadeREST tReserveFacadeREST;
    @EJB
    private TOtherissueFacadeREST tOtherissueFacadeREST;
    @EJB
    private TBookbankissuereturnFacadeREST tBookbankissuereturnFacadeREST;
    @EJB
    private MGroupFacadeREST mGroupFacadeREST;
    @EJB
    private SRequestFacadeREST sRequestFacadeREST;
    @EJB
    private MemPhotoFacadeREST memPhotoFacadeREST;

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    DateNTimeChange dt  = new DateNTimeChange();
    Date Todaydate = new Date();
    protected Map params = new HashMap();
  //  postdata pd;
  //  StringData sd;
    int count;
    String output;
    String memberCode1;
    public MMemberFacadeREST() {
        super(MMember.class);
    }
    
  

//    @POST
//    // @Override
//    @Path("addMember")
//    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
//    public String addMember(@FormParam("category") String category, @FormParam("department") String department,
//            @FormParam("institute") String institute, @FormParam("course") String course,
//            @Pattern(regexp = "^[a-zA-Z]*$", message = "{surName.pattern}") @FormParam("memberSurname") String memberSurname,
//            @FormParam("status") String status,
//            @Pattern(regexp = "^[a-zA-Z]*$", message = "{foreName.pattern}") @FormParam("memberForename") String memberForename,
//            @FormParam("gender") String gender, @FormParam("birthDate") String birthDate,
//            @FormParam("memberId") String memberId, @FormParam("joiningYear") String joiningYear,
//            @FormParam("memberType") String memberType,
//            @FormParam("permanentAddress") String permanentAddress, @FormParam("permanentCity") String permanentCity,
//            @Pattern(regexp = "^[1-9][0-9]{5}$", message = "{pinCode.pattern}") @FormParam("permanentPin") String permanentPin,
//            @FormParam("permanentPhone") String permanentPhone,
//            @FormParam("presentAdd") String presentAdd,
//            @FormParam("presentCity") String presentCity,
//            @Pattern(regexp = "^[1-9][0-9]{5}$", message = "{pinCode.pattern}") @FormParam("presentPin") String presentPin,
//            @FormParam("presentPhone") String presentPhone,
//            @Pattern(regexp = "[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}", message = "{email.pattern}") @FormParam("memberEmail") String memberEmail,
//            @FormParam("libMember") String libMember, @FormParam("guarantorId") String guarantorId,
//            @FormParam("formNo") String formNo, @FormParam("gCity") String gCity,
//            @FormParam("gPerAddress") String gPerAddress,
//            @Pattern(regexp = "^[a-zA-Z]*$", message = "{surName.pattern}") @FormParam("gSurname") String gSurname,
//            @FormParam("gPhone") String gPhone,
//            @Pattern(regexp = "^[1-9][0-9]{5}$", message = "{pinCode.pattern}") @FormParam("gPin") String gPin,
//            @Pattern(regexp = "^[a-zA-Z]*$", message = "{foreName.pattern}") @FormParam("gForename") String gForename,
//            @Pattern(regexp = "[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}", message = "{email.pattern}") @FormParam("gEmail") String gEmail,
//            @FormParam("depositeAmt") String depositeAmt,
//            @FormParam("depositeRecNo") String depositeRecNo, @FormParam("depositeReceiptDate") String depositeRecDate,
//            @FormParam("membershipAmt") String membershipAmt, @FormParam("membershipRecNo") String membershipRecNo,
//            @FormParam("membershipRecDt") String membershipRecDt, @FormParam("maxDueAmt") String maxDue,
//            @FormParam("effectiveDate") String effectiveDate, @FormParam("cardExpDate") String cardExpDate,
//            @FormParam("cardIssue") String cardIssue_String, @FormParam("password") String password,
//            @FormParam("noDue") String noDue_String, @FormParam("confirmPassword") String confirmPassword,
//            @FormParam("remark") String remark) throws ParseException {
//        int count;
//        String output = null;
//        char cardIssue, noDue;
//        MMember member = null;
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        member = new MMember();
//
//        boolean deptCode = false;
//        boolean instCode = false;
//        boolean courseCode = false;
//        if (department.isEmpty()) {
//            deptCode = true;
//        } else {
//            MFcltydept dept = mFcltydeptFacadeREST.find(Integer.parseInt(department));
//            if (dept == null || dept.getFldtag() == 'I') {
//                throw new DataException("No department found with deptCd : " + department);
//            }
//            member.setMemDept(dept);
//        }
//        if (institute.isEmpty()) {
//            instCode = true;
//        } else {
//            MFcltydept inst = mFcltydeptFacadeREST.find(Integer.parseInt(institute));
//            if (inst == null || inst.getFldtag() == 'D') {
//                throw new DataException("No institute found with instCd : " + institute);
//            }
//            member.setMemInst(inst);
//        }
//        if (course.isEmpty()) {
//            courseCode = true;
//        } else {
//            MBranchmaster branch = mBranchmasterFacadeREST.find(Integer.parseInt(course));
//            if (branch == null) {
//                throw new DataException("No branch found with courseCd : " + course);
//            }
//            member.setMemDegree(branch);
//        }
//        member.setMemCtgry(mCtgryFacadeREST.find(category));
////        member.setMemInst(mFcltydeptFacadeREST.find(Integer.parseInt(institute)));
////        member.setMemDept(mFcltydeptFacadeREST.find(Integer.parseInt(department)));
////        member.setMemDegree(mBranchmasterFacadeREST.find(Integer.parseInt(course)));
//
//        //All this is for Getting the Correct MemCd
//        //AACCDDYYSSSS  ie  AA: CategoryCode CC: Department Code DD: Cource/Branch Code
//        //YY: Currenct Year (14 for 2014) SSSS: Searial No Starting from 0001
//        String memCode = category + (institute.length() == 1 ? "0" : "") + institute + (department.length() == 1 ? "0" : "") + department;
//        memCode = memCode + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2);
//        List<MMember> memberList = findBy("findByMaxMemCd", memCode);
//        if (memberList.size() > 0) {
//            memCode = memCode + String.format("%04d", Integer.parseInt(memberList.get(0).getMemCd().substring(8)) + 1);
//        } else {
//            memCode = memCode + String.format("%04d", 1);
//        }
//        //Got MemCd
//
//        member.setMemCd(memCode);
//
//        //Personal Info Tab
//        member.setMemFirstnm(memberForename);
//        member.setMemLstnm(memberSurname);
//        member.setDateOfBirth(dateFormat.parse(birthDate));
//        member.setMemYear(dateFormat.parse(joiningYear));
//        member.setMemStatus(status);
//        member.setMemGender(gender);
//        member.setMemType(mTypeFacadeREST.find(memberType));
//        member.setMemId(memberId);
//
//        //Contact Info Tab
//        member.setMemPrmntadd1(permanentAddress);
//        member.setMemPrmntcity(permanentCity);
//        member.setMemPrmntpin(permanentPin);
//        member.setMemPrmntphone(permanentPhone);
//
//        member.setMemTmpadd1(presentAdd);
//        member.setMemTmpcity(presentCity);
//        member.setMemTmppin(presentPin);
//        member.setMemTmpphone(presentPhone);
//
//        member.setMemEmail(memberEmail);
//
//        //Guarantor Info
//        member.setGrentrFirstnm(gForename);
//        member.setGrentrLastnm(gSurname);
//        member.setGrentrAdd1(gPerAddress);
//        member.setGrentrCity(gCity);
//        member.setGrentrPin(gPin);
//        member.setGrentrPhone(gPhone);
//        member.setGrentrEmail(gEmail);
//        member.setGrentrFormNo(formNo);
//        if (libMember != null) {
//            member.setGrentrMemId(guarantorId);
//        }
//
//        //Payment Info
//        if (depositeAmt != null && !"".equals(depositeAmt)) {
//            member.setMemDeposit(new BigDecimal(depositeAmt));
//        }
//        member.setRcptNo1(depositeRecNo);
//        if ( != null && !"".equals(depositeRecDate)) {
//            member.setRcptNo1Dt(dateFormat.parse(depositeRecDate));
//        }
//
//        if (membershipAmt != null && !"".equals(membershipAmt)) {
//            member.setMembershipAmt(new BigDecimal(membershipAmt));
//        }
//        member.setRcptNo2(membershipRecNo);
//        if (membershipRecDt != null && !"".equals(membershipRecDt)) {
//            member.setRcptNo2Dt(dateFormat.parse(membershipRecDt));
//        }
//
//        if (maxDue != null && !"".equals(maxDue)) {
//            member.setMemMaxdueAmt(new BigDecimal(maxDue));
//        }
//        Date MemEfctvDtDate = dateFormat.parse(effectiveDate);
//        if (effectiveDate != null && !"".equals(effectiveDate)) {
//            member.setMemEfctvDt(new java.sql.Date(MemEfctvDtDate.getTime()));
//        }
//        Date MemEffctuptoDate = dateFormat.parse(cardExpDate);
//        if (cardExpDate != null && !"".equals(cardExpDate)) {
//            member.setMemEffctupto(new java.sql.Date(MemEffctuptoDate.getTime()));
//        }
//
//        //Others Tab
//        if (cardIssue_String == null) {
//            cardIssue = 'N';
//        } else {
//            cardIssue = 'Y';
//        }
//
//        if (noDue_String == null) {
//            noDue = 'N';
//        } else {
//            noDue = 'Y';
//        }
//
//        member.setCardissude(cardIssue);
//        //member.setNoDueTag(Integer.valueOf(noDue));a
//        member.setNoDueTag(noDue);
//        member.setMemPassword(password);
//        member.setRemarks(remark);
//
//        //Don't belong to form
//        Date date = new Date();
//        member.setMemEntryDt(new java.sql.Date(date.getTime()));
//        member.setMemFineAmt(new BigDecimal(0));
//        member.setUserCd("super user");
//
//        count = super.count();
//        if (category.isEmpty() || memberType.isEmpty() || deptCode == true || instCode == true || courseCode == true) {
//            output = "Category, Member Type, Department, Course fields are mandatory. Please fill in data.";
//        } else {
//            super.create(member);
//            if (count == super.count()) {
//                output = "Someting went wrong, Member is not saved.";
//            } else {
//                output = "Member Inserted with Member Code: " + member.getMemCd() + ".";
//            }
//        }
//        return output;
//    }
    
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(MMember entity) {
        String memCode = ""+entity.getMemCtgry().getCtgryCd()+""+entity.getMemDept().getFcltydeptcd()+""+entity.getMemDegree().getBranchCd();
        System.out.println("memCode---------------"+memCode);
        entity.setMemCd(memCode);
        List<MMember> memberList = findBy("findByMaxMemCd", memCode);
//        System.err.println("memberList size :" + memberList.size() +" old : "+memberList.get(0).getMemCd());
//        if (memberList.size() > 0) {
//            memCode = memCode + String.format("%04d", Todaydate.getYear() + 1 + Todaydate.getMinutes()+Todaydate.getSeconds()+Todaydate.getHours());
//        } else {
//            memCode = memCode + String.format("%04d", 1);
//        }   
        memCode = memCode + String.format("%04d", Todaydate.getYear() + 1 + Todaydate.getMinutes()+Todaydate.getSeconds()+Todaydate.getHours());
        this.memberCode1 = memCode;
        entity.setMemCd(memCode);
        System.out.println("member-------"+entity.getMemCd());
       
    super.create(entity);
    }
    
    // this api to check input is mem code / ID and if it found it return memcode. 
    @GET
    @Path("membercheckidorcode/{memCd}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public postdata checkmemidorcode(@PathParam("memCd") String memcd){
        postdata pd;
       // System.out.println("called....");
        String memcode;
        Query query;
        List<Object> memdata;
        String sql = "select m.mem_cd from m_member m where m.mem_cd='"+memcd+"' or m.mem_id='"+memcd+"'"; 
       // System.out.println("sql : " + sql);
        query = em.createNativeQuery(sql);
        memdata = query.getResultList();
        
        if (memdata.size() > 0){
            memcode = memdata.get(0).toString();
         //   System.out.println("memcode : " + memcode);
        }else{
            memcode="N";
        }
        
        pd = new postdata("member",memcode);
                
        return pd;
    }
    
    @GET
    @Path("memdata/{memcd}")
     @Produces({"application/xml", "application/json"})
    public List<Memdetails> getmemdata(@PathParam("memcd") String memcd){
         MMember m;
     
        List<Memdetails> limeminfo=null;
        m = find(memcd);
        if(m!=null){
            Query query;
            String q="select m_member.mem_cd as memcd,m_member.mem_id as memid,concat(m_member.mem_firstnm,' ',m_member.mem_lstnm) as memname,m_member.mem_status as Status,f1.Fclty_dept_dscr as dept,f2.Fclty_dept_dscr as inst,m_ctgry.ctgry_desc as crgty,m_branchmaster.Branch_name as branch from m_member,m_fcltydept f1,m_fcltydept f2,m_ctgry,m_branchmaster where m_member.mem_dept=f1.Fclty_dept_cd and f1.Fld_tag='D' and m_member.mem_ctgry=m_ctgry.ctgry_cd and m_member.mem_inst=f2.Fclty_dept_cd and f2.Fld_tag='I' and m_branchmaster.branch_cd=m_member.mem_degree and m_member.mem_cd='"+m.getMemCd()+"'";
            query = getEntityManager().createNativeQuery(q,Memdetails.class);
            limeminfo = query.getResultList();
            
        }else{
            limeminfo = new ArrayList<>();
        }
        return limeminfo;
    }
    
    @POST
    // @Override
    @Path("addMember") // copied from 166
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded","multipart/form-data"})
    public String addMember(@FormParam("category") String category, @FormParam("department") String department,
            @FormParam("institute") String institute, @FormParam("course") String course,
            @Pattern(regexp = "^[a-zA-Z]*$", message = "{surName.pattern}") @FormParam("memberSurname") String memberSurname,
            @FormParam("status") String status,
            @Pattern(regexp = "^[a-zA-Z]*$", message = "{foreName.pattern}") @FormParam("memberForename") String memberForename,
            @FormParam("gender") String gender, @FormParam("birthDate") String birthDate,
            @FormParam("memberId") String memberId, @FormParam("joiningYear") String joiningYear,
            @FormParam("memberType") String memberType,
            @FormParam("permanentAddress") String permanentAddress, @FormParam("permanentCity") String permanentCity,
            @Pattern(regexp = "^[1-9][0-9]{5}$", message = "{pinCode.pattern}") @FormParam("permanentPin") String permanentPin,
            @FormParam("permanentPhone") String permanentPhone,
            @FormParam("presentAdd") String presentAdd,
            @FormParam("presentCity") String presentCity,
            @Pattern(regexp = "^[1-9][0-9]{5}$", message = "{pinCode.pattern}") @FormParam("presentPin") String presentPin,
            @FormParam("presentPhone") String presentPhone,
            @Pattern(regexp = "[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}", message = "{email.pattern}") @FormParam("memberEmail") String memberEmail,
            @FormParam("libMember") String libMember, @FormParam("guarantorId") String guarantorId,
            @FormParam("formNo") String formNo, @FormParam("gCity") String gCity,
            @FormParam("gPerAddress") String gPerAddress,
            @Pattern(regexp = "^[a-zA-Z]*$", message = "{surName.pattern}") @FormParam("gSurname") String gSurname,
            @FormParam("gPhone") String gPhone,
            @Pattern(regexp = "^[1-9][0-9]{5}$", message = "{pinCode.pattern}") @FormParam("gPin") String gPin,
            @Pattern(regexp = "^[a-zA-Z]*$", message = "{foreName.pattern}") @FormParam("gForename") String gForename,
            @Pattern(regexp = "[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}", message = "{email.pattern}") @FormParam("gEmail") String gEmail,
            @FormParam("depositeAmt") String depositeAmt,
            @FormParam("depositeRecNo") String depositeRecNo, @FormParam("depositeReceiptDate") String depositeRecDate,
            @FormParam("membershipAmt") String membershipAmt, @FormParam("membershipRecNo") String membershipRecNo,
            @FormParam("membershipRecDt") String membershipRecDt, @FormParam("maxDueAmt") String maxDue,
            @FormParam("effectiveDate") String effectiveDate, @FormParam("cardExpDate") String cardExpDate, @FormParam("entryDate") String entryDate,
            @FormParam("cardIssue") String cardIssue_String, @FormParam("password") String password,
            @FormParam("noDue") String noDue_String, @FormParam("confirmPassword") String confirmPassword,
            @FormParam("remark") String remark) throws ParseException {
        System.out.println("callllllllllllllllllllllllllllllllllllllllllllllllll");
        int count;
        String output = null;
        String cardIssue, noDue;
        MMember member = null;

        System.out.println("d:" + effectiveDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        member = new MMember();

        boolean deptCode = false;
        boolean instCode = false;
        boolean courseCode = false;
        if (department.isEmpty()) {
            deptCode = true;
        } else {
            MFcltydept dept = mFcltydeptFacadeREST.find(Integer.parseInt(department));
            if (dept == null || dept.getFldtag() == 'I') {
               // throw new DataException("No department found with deptCd : " + department);
            }
            member.setMemDept(dept);
        }
        if (institute.isEmpty()) {
            instCode = true;
        } else {
            MFcltydept inst = mFcltydeptFacadeREST.find(Integer.parseInt(institute));
            if (inst == null || inst.getFldtag() == 'D') {
               // throw new DataException("No institute found with instCd : " + institute);
            }
            member.setMemInst(inst);
        }
        if (course.isEmpty()) {
            courseCode = true;
        } else {
            MBranchmaster branch = mBranchmasterFacadeREST.find(Integer.parseInt(course));
            if (branch == null) {
              //  throw new DataException("No branch found with courseCd : " + course);
            }
            member.setMemDegree(branch);
        }
        member.setMemCtgry(mCtgryFacadeREST.find(category));
//        member.setMemInst(mFcltydeptFacadeREST.find(Integer.parseInt(institute)));
//        member.setMemDept(mFcltydeptFacadeREST.find(Integer.parseInt(department)));
//        member.setMemDegree(mBranchmasterFacadeREST.find(Integer.parseInt(course)));

        //All this is for Getting the Correct MemCd
        //AACCDDYYSSSS  ie  AA: CategoryCode CC: Department Code DD: Cource/Branch Code
        //YY: Currenct Year (14 for 2014) SSSS: Searial No Starting from 0001
        String memCode = category + (institute.length() == 1 ? "0" : "") + institute + (department.length() == 1 ? "0" : "") + department;
        memCode = memCode + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2);
        List<MMember> memberList = findBy("findByMaxMemCd", memCode);
        if (memberList.size() > 0) {
            memCode = memCode + String.format("%04d", String.valueOf(memberList.get(0).getMemCd().substring(8)) + 1);
        } else {
            memCode = memCode + String.format("%04d", 1);
        }
        //Got MemCd

        member.setMemCd(memCode);

        //Personal Info Tab
        member.setMemFirstnm(memberForename);
        member.setMemLstnm(memberSurname);
        System.out.println("date" + birthDate);
        if (birthDate.length() == 0) {
            member.setDateOfBirth(null);
        } else {
            member.setDateOfBirth(dateFormat.parse(birthDate));
        }
        System.out.println("jd" + joiningYear);
        member.setMemYear(dateFormat.parse(joiningYear));
        member.setMemStatus(status);
        member.setMemGender(gender);
        member.setMemType(mTypeFacadeREST.find(memberType));
        member.setMemId(memberId);

        //Contact Info Tab
        member.setMemPrmntadd1(permanentAddress);
        member.setMemPrmntcity(permanentCity);
        member.setMemPrmntpin(permanentPin);
        member.setMemPrmntphone(permanentPhone);

        member.setMemTmpadd1(presentAdd);
        member.setMemTmpcity(presentCity);
        member.setMemTmppin(presentPin);
        member.setMemTmpphone(presentPhone);

        member.setMemEmail(memberEmail);

        //Guarantor Info
        member.setGrentrFirstnm(gForename);
        member.setGrentrLastnm(gSurname);
        member.setGrentrAdd1(gPerAddress);
        member.setGrentrCity(gCity);
        member.setGrentrPin(gPin);
        member.setGrentrPhone(gPhone);
        member.setGrentrEmail(gEmail);
        member.setGrentrFormNo(formNo);
        if (libMember != null) {
            member.setGrentrMemId(guarantorId);
        }

        //Payment Info
        if (depositeAmt != null && !"".equals(depositeAmt)) {
            member.setMemDeposit(new BigDecimal(depositeAmt));
        }
        member.setRcptNo1(depositeRecNo);
        if (depositeRecDate != null && !"".equals(depositeRecDate)) {
            member.setRcptNo1Dt(dateFormat.parse(depositeRecDate));
        }

        if (membershipAmt != null && !"".equals(membershipAmt)) {
            member.setMembershipAmt(new BigDecimal(membershipAmt));
        }
        member.setRcptNo2(membershipRecNo);
        if (membershipRecDt != null && !"".equals(membershipRecDt)) {
            member.setRcptNo2Dt(dateFormat.parse(membershipRecDt));
        }

        if (maxDue != null && !"".equals(maxDue)) {
            member.setMemMaxdueAmt(new BigDecimal(maxDue));
        }
        Date MemEfctvDtDate = dateFormat.parse(effectiveDate);
        if (effectiveDate != null && !"".equals(effectiveDate)) {
            member.setMemEfctvDt(new java.sql.Date(MemEfctvDtDate.getTime()));
        }
        Date MemEffctuptoDate = dateFormat.parse(cardExpDate);
        if (cardExpDate != null && !"".equals(cardExpDate)) {
            member.setMemEffctupto(new java.sql.Date(MemEffctuptoDate.getTime()));
        }

        //Others Tab
        if (cardIssue_String == null) {
            cardIssue = "N";
        } else {
            cardIssue = "Y";
        }

        if (noDue_String == null) {
            noDue = "N";
        } else {
            noDue = "Y";
        }

        member.setCardissude(cardIssue);
        //member.setNoDueTag(Integer.valueOf(noDue));a
        member.setNoDueTag(noDue);
        member.setMemPassword(password);
        member.setRemarks(remark);

        //Don't belong to form
        Date date = new Date();
    //p    if (entryDate.length() == 0) {
            member.setMemEntryDt(new java.sql.Date(date.getTime()));
//     //p     } else {
//     //p         member.setMemEntryDt(dateFormat.parse(entryDate));
//     //p     }
        member.setMemFineAmt(new BigDecimal(0));
        member.setUserCd("super user");

        count = super.count();
        System.out.println(category.isEmpty() + "1D\n" + memberType.isEmpty() + "2D\n" + deptCode + "3D\n" + instCode + "4D\n" + courseCode);
        if (category.isEmpty() || memberType.isEmpty() || deptCode == true || instCode == true || courseCode == true) {
            output = "Category, Member Type, Department, Course fields are mandatory. Please fill in data.";
        } else {
            super.create(member);
            if (count == super.count()) {
              //  output = "Someting went wrong, Member is not saved.";
              output = "error";
            } else {
               // output = "Member Inserted with Member Code: " + member.getMemCd();
               output = member.getMemCd();
            }
        }
        return output;
    }
    
    @POST
    @Path("addmem")
    @Consumes({"application/xml", "application/json"})
    @Produces("text/plain")
    public String createq(MMember entity) {
        String rs;
        try{
            super.create(entity);
            rs = "Member Code : "+entity.getMemCd() +" and Member Name "+entity.getMemFirstnm()+" "+entity.getMemLstnm();
        }catch(Exception ex){
            rs= ex.getMessage();
        }
        return rs;
    }

    @POST
    // @Override
    @Path("copyMember")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String copyMember(@FormParam("category") String category, @FormParam("department") String department,
            @FormParam("institute") String institute, @FormParam("course") String course,
            @Pattern(regexp = "^[a-zA-Z]*$", message = "{surName.pattern}") @FormParam("memberSurname") String memberSurname,
            @FormParam("status") String status,
            @Pattern(regexp = "^[a-zA-Z]*$", message = "{foreName.pattern}") @FormParam("memberForename") String memberForename,
            @FormParam("gender") String gender, @FormParam("birthDate") String birthDate,
            @FormParam("joiningYear") String joiningYear, @FormParam("memberType") String memberType,
            @Pattern(regexp = "^([a-zA-Z0-9]+([ ][a-zA-Z0-9])*)+{12}+$", message = "{memCd.pattern}") @FormParam("memberCode") String memCode,
            @FormParam("memberId") String memberId,
            @FormParam("permanentAddress") String permanentAddress, @FormParam("permanentCity") String permanentCity,
            @Pattern(regexp = "^[1-9][0-9]{5}$", message = "{pinCode.pattern}") @FormParam("permanentPin") String permanentPin,
            @FormParam("permanentPhone") String permanentPhone,
            @FormParam("presentAdd") String presentAdd,
            @FormParam("presentCity") String presentCity,
            @Pattern(regexp = "^[1-9][0-9]{5}$", message = "{pinCode.pattern}") @FormParam("presentPin") String presentPin,
            @FormParam("presentPhone") String presentPhone,
            @Pattern(regexp = "[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}", message = "{email.pattern}") @FormParam("memberEmail") String memberEmail,
            @FormParam("libMember") String libMember, @FormParam("guarantorId") String guarantorId,
            @FormParam("formNo") String formNo, @FormParam("gCity") String gCity,
            @FormParam("gPerAddress") String gPerAddress,
            @Pattern(regexp = "^[a-zA-Z]*$", message = "{surName.pattern}") @FormParam("gSurname") String gSurname,
            @FormParam("gPhone") String gPhone,
            @Pattern(regexp = "^[1-9][0-9]{5}$", message = "{pinCode.pattern}") @FormParam("gPin") String gPin,
            @Pattern(regexp = "^[a-zA-Z]*$", message = "{foreName.pattern}") @FormParam("gForename") String gForename,
            @Pattern(regexp = "[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}", message = "{email.pattern}") @FormParam("gEmail") String gEmail,
            @FormParam("depositeAmt") String depositeAmt,
            @FormParam("depositeRecNo") String depositeRecNo, @FormParam("depositeReceiptDate") String depositeRecDate,
            @FormParam("membershipAmt") String membershipAmt, @FormParam("membershipRecNo") String membershipRecNo,
            @FormParam("membershipRecDt") String membershipRecDt, @FormParam("maxDueAmt") String maxDue,
            @FormParam("effectiveDate") String effectiveDate, @FormParam("cardExpDate") String cardExpDate,
            @FormParam("cardIssue") String cardIssue_String, @FormParam("password") String password,
            @FormParam("noDue") String noDue_String, @FormParam("confirmPassword") String confirmPassword,
            @FormParam("remark") String remark) throws ParseException {

        int count;
        String output = null;
        String cardIssue, noDue;
        MMember member = null;
        MMember updateMember = find(memCode);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Member: " + memCode);

        if (CheckMemberAvalibility(memCode)) {
            if (checkIfCopyMemberIsAllowed(memCode).contentEquals("Allowed")) {
                member = new MMember();
                updateMember.setMemStatus("S");
                boolean deptCode = false;
                boolean instCode = false;
                boolean courseCode = false;
                if (department.isEmpty()) {
                    deptCode = true;
                } else {
                    MFcltydept dept = mFcltydeptFacadeREST.find(Integer.parseInt(department));
                    if (dept == null || dept.getFldtag() == 'I') {
                    //    throw new DataException("No department found with deptCd : " + department);
                    }
                    member.setMemDept(dept);
                }
                if (institute.isEmpty()) {
                    instCode = true;
                } else {
                    MFcltydept inst = mFcltydeptFacadeREST.find(Integer.parseInt(institute));
                    if (inst == null || inst.getFldtag() == 'D') {
                     //   throw new DataException("No institute found with instCd : " + institute);
                    }
                    member.setMemInst(inst);
                }
                if (course.isEmpty()) {
                    courseCode = true;
                } else {
                    MBranchmaster branch = mBranchmasterFacadeREST.find(Integer.parseInt(course));
                    if (branch == null) {
                     //   throw new DataException("No branch found with courseCd : " + course);
                    }
                    member.setMemDegree(branch);
                }
                member.setMemCtgry(mCtgryFacadeREST.find(category));
//        member.setMemInst(mFcltydeptFacadeREST.find(Integer.parseInt(institute)));
//        member.setMemDept(mFcltydeptFacadeREST.find(Integer.parseInt(department)));
//        member.setMemDegree(mBranchmasterFacadeREST.find(Integer.parseInt(course)));

                //All this is for Getting the Correct MemCd
                //AACCDDYYSSSS  ie  AA: CategoryCode CC: Department Code DD: Cource/Branch Code
                //YY: Currenct Year (14 for 2014) SSSS: Searial No Starting from 0001
                memCode = category + (institute.length() == 1 ? "0" : "") + institute + (department.length() == 1 ? "0" : "") + department;
                memCode = memCode + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2);
                List<MMember> memberList = findBy("findByMaxMemCd", memCode);
                if (memberList.size() > 0) {
                    memCode = memCode + String.format("%04d", Integer.parseInt(memberList.get(0).getMemCd().substring(8)) + 1);
                } else {
                    memCode = memCode + String.format("%04d", 1);
                }
                //Got MemCd

                member.setMemCd(memCode);
                System.out.println("Member: " + memCode);
                //Personal Info Tab
                member.setMemFirstnm(memberForename);
                member.setMemLstnm(memberSurname);
                member.setDateOfBirth(dateFormat.parse(birthDate));
                member.setMemYear(dateFormat.parse(joiningYear));
                member.setMemStatus(status);
                member.setMemGender(gender);
                member.setMemType(mTypeFacadeREST.find(memberType));
                member.setMemId(memberId);

                //Contact Info Tab
                member.setMemPrmntadd1(permanentAddress);
                member.setMemPrmntcity(permanentCity);
                member.setMemPrmntpin(permanentPin);
                member.setMemPrmntphone(permanentPhone);

                member.setMemTmpadd1(presentAdd);
                member.setMemTmpcity(presentCity);
                member.setMemTmppin(presentPin);
                member.setMemTmpphone(presentPhone);

                member.setMemEmail(memberEmail);

                //Guarantor Info
                member.setGrentrFirstnm(gForename);
                member.setGrentrLastnm(gSurname);
                member.setGrentrAdd1(gPerAddress);
                member.setGrentrCity(gCity);
                member.setGrentrPin(gPin);
                member.setGrentrPhone(gPhone);
                member.setGrentrEmail(gEmail);
                member.setGrentrFormNo(formNo);
                if (libMember != null) {
                    member.setGrentrMemId(guarantorId);
                }

                //Payment Info
                if (depositeAmt != null && !"".equals(depositeAmt)) {
                    member.setMemDeposit(new BigDecimal(depositeAmt));
                }
                member.setRcptNo1(depositeRecNo);
                if (depositeRecDate != null && !"".equals(depositeRecDate)) {
                    member.setRcptNo1Dt(dateFormat.parse(depositeRecDate));
                }

                if (membershipAmt != null && !"".equals(membershipAmt)) {
                    member.setMembershipAmt(new BigDecimal(membershipAmt));
                }
                member.setRcptNo2(membershipRecNo);
                if (membershipRecDt != null && !"".equals(membershipRecDt)) {
                    member.setRcptNo2Dt(dateFormat.parse(membershipRecDt));
                }

                if (maxDue != null && !"".equals(maxDue)) {
                    member.setMemMaxdueAmt(new BigDecimal(maxDue));
                }
                Date MemEfctvDtDate = dateFormat.parse(effectiveDate);
                if (effectiveDate != null && !"".equals(effectiveDate)) {
                    member.setMemEfctvDt(new java.sql.Date(MemEfctvDtDate.getTime()));
                }
                Date MemEffctuptoDate = dateFormat.parse(cardExpDate);
                if (cardExpDate != null && !"".equals(cardExpDate)) {
                    member.setMemEffctupto(new java.sql.Date(MemEffctuptoDate.getTime()));
                }

                //Others Tab
                if (cardIssue_String == null) {
                    cardIssue = "N";
                } else {
                    cardIssue = "Y";
                }

                if (noDue_String == null) {
                    noDue = "N";
                } else {
                    noDue = "Y";
                }

                member.setCardissude(cardIssue);
                //member.setNoDueTag(Integer.valueOf(noDue));a
                member.setNoDueTag(noDue);
                member.setMemPassword(password);
                member.setRemarks(remark);

                //Don't belong to form
                Date date = new Date();
                member.setMemEntryDt(new java.sql.Date(date.getTime()));
                member.setMemFineAmt(new BigDecimal(0));
                member.setUserCd("super user");

                count = super.count();
                if (category.isEmpty() || memberType.isEmpty() || deptCode == true || instCode == true || courseCode == true) {
                    //Could be executed when primary key columns will be passed empty
                    output = "Too many empty columns. Please fill in data.";
                } else {
                    System.out.println("Member: " + memCode);
                    super.edit(updateMember);
                    super.create(member);
                    if (count == super.count()) {
                        //Could be executed whrn Not null columns will be passed empty
                        output = "Someting went wrong, Member is not copied.";
                    } else {
                        //Success
                        output = "Member Copied with Member Code: " + member.getMemCd() + ".";
                    }
                }
            } else {
                //will check if the due amount is > 0 for member to copy
                output = checkIfCopyMemberIsAllowed(memCode);
            }
        } else {
            //could be executed when member code is null or invalid
            output = "Invalid member code.";
        }
        return output;
    }

    @POST
    // @Override
    @Path("updateMemberDetails")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String updateMemberDetails(@Pattern(regexp = "^[a-zA-Z]*$", message = "{surName.pattern}") @FormParam("memberSurname") String memberSurname,
            @FormParam("status") String status,
            @Pattern(regexp = "^[a-zA-Z]*$", message = "{foreName.pattern}") @FormParam("memberForename") String memberForename,
            @FormParam("gender") String gender, @FormParam("birthDate") String birthDate,
            @Pattern(regexp = "^([a-zA-Z0-9]+([ ][a-zA-Z0-9])*)+{12}+$", message = "{memCd.pattern}") @FormParam("memberCode") String memberCode,
            @FormParam("memberType") String memberType,
            @FormParam("permanentAddress") String permanentAddress, @FormParam("permanentCity") String permanentCity,
            @Pattern(regexp = "^[1-9][0-9]{5}$", message = "{pinCode.pattern}") @FormParam("permanentPin") String permanentPin,
            @FormParam("permanentPhone") String permanentPhone,
            @FormParam("presentAdd") String presentAdd,
            @FormParam("presentCity") String presentCity,
            @Pattern(regexp = "^[1-9][0-9]{5}$", message = "{pinCode.pattern}") @FormParam("presentPin") String presentPin,
            @FormParam("presentPhone") String presentPhone,
            @Pattern(regexp = "[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}", message = "{email.pattern}") @FormParam("memberEmail") String memberEmail,
            @FormParam("libMember") String libMember, @FormParam("guarantorId") String guarantorId,
            @FormParam("formNo") String formNo, @FormParam("gCity") String gCity,
            @FormParam("gPerAddress") String gPerAddress,
            @Pattern(regexp = "^[a-zA-Z]*$", message = "{surName.pattern}") @FormParam("gSurname") String gSurname,
            @FormParam("gPhone") String gPhone,
            @Pattern(regexp = "^[a-zA-Z]*$", message = "{foreName.pattern}") @FormParam("gForename") String gForename,
            @Pattern(regexp = "^[1-9][0-9]{5}$", message = "{pinCode.pattern}") @FormParam("gPin") String gPin,
            @Pattern(regexp = "[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}", message = "{email.pattern}") @FormParam("gEmail") String gEmail,
            @FormParam("depositeAmt") String depositeAmt,
            @FormParam("depositeRecNo") String depositeRecNo, @FormParam("depositeReceiptDate") String depositeRecDate,
            @FormParam("membershipAmt") String membershipAmt, @FormParam("membershipRecNo") String membershipRecNo,
            @FormParam("membershipRecDt") String membershipRecDt, @FormParam("maxDueAmt") String maxDue,
            @FormParam("effectiveDate") String effectiveDate, @FormParam("cardExpDate") String cardExpDate,
            @FormParam("cardIssue") String cardIssue_String, @FormParam("password") String password,
            @FormParam("noDue") String noDue_String, @FormParam("confirmPassword") String confirmPassword,
            @FormParam("remark") String remark) throws ParseException {
        int count;
        String output = null;
        String cardIssue, noDue;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        MMember member = find(memberCode);

        if (CheckMemberAvalibility(memberCode)) {
            //Personal Info Tab
            member.setMemFirstnm(memberForename);
            member.setMemLstnm(memberSurname);
            member.setDateOfBirth(dateFormat.parse(birthDate));
            member.setMemStatus(status);
            member.setMemGender(gender);
            member.setMemType(mTypeFacadeREST.find(memberType));

            //Contact Info Tab
            member.setMemPrmntadd1(permanentAddress);
            member.setMemPrmntcity(permanentCity);
            member.setMemPrmntpin(permanentPin);
            member.setMemPrmntphone(permanentPhone);

            member.setMemTmpadd1(presentAdd);
            member.setMemTmpcity(presentCity);
            member.setMemTmppin(presentPin);
            member.setMemTmpphone(presentPhone);

            member.setMemEmail(memberEmail);

            //Guarantor Info
            member.setGrentrFirstnm(gForename);
            member.setGrentrLastnm(gSurname);
            member.setGrentrAdd1(gPerAddress);
            member.setGrentrCity(gCity);
            member.setGrentrPin(gPin);
            member.setGrentrPhone(gPhone);
            member.setGrentrEmail(gEmail);
            member.setGrentrFormNo(formNo);
            if (libMember != null) {
                member.setGrentrMemId(guarantorId);
            }

            //Payment Info
            if (depositeAmt != null && !"".equals(depositeAmt)) {
                member.setMemDeposit(new BigDecimal(depositeAmt));
            }
            member.setRcptNo1(depositeRecNo);
            if (depositeRecDate != null && !"".equals(depositeRecDate)) {
                member.setRcptNo1Dt(dateFormat.parse(depositeRecDate));
            }

            if (membershipAmt != null && !"".equals(membershipAmt)) {
                member.setMembershipAmt(new BigDecimal(membershipAmt));
            }
            member.setRcptNo2(membershipRecNo);
            if (membershipRecDt != null && !"".equals(membershipRecDt)) {
                member.setRcptNo2Dt(dateFormat.parse(membershipRecDt));
            }

            if (maxDue != null && !"".equals(maxDue)) {
                member.setMemMaxdueAmt(new BigDecimal(maxDue));
            }
            Date MemEfctvDtDate = dateFormat.parse(effectiveDate);
            if (effectiveDate != null && !"".equals(effectiveDate)) {
                member.setMemEfctvDt(new java.sql.Date(MemEfctvDtDate.getTime()));
            }
            Date MemEffctuptoDate = dateFormat.parse(cardExpDate);
            if (cardExpDate != null && !"".equals(cardExpDate)) {
                member.setMemEffctupto(new java.sql.Date(MemEffctuptoDate.getTime()));
            }

            //Others Tab
            if (cardIssue_String == null) {
                cardIssue = "N";
            } else {
                cardIssue = "Y";
            }

            if (noDue_String == null) {
                noDue = "N";
            } else {
                noDue = "Y";
            }

            member.setCardissude(cardIssue);
            //member.setNoDueTag(Integer.valueOf(noDue));a
            member.setNoDueTag(noDue);
            member.setMemPassword(password);
            member.setRemarks(remark);

            //Don't belong to form
            Date date = new Date();
            member.setMemEntryDt(new java.sql.Date(date.getTime()));
            member.setMemFineAmt(new BigDecimal(0));
            member.setUserCd("super user");

            count = super.count();
            if (memberType.isEmpty()) {
                output = "Member Type is mandatory. Please fill in data.";
            } else {
                super.edit(member);
                output = "Details of member with Member Code: " + member.getMemCd() + " is now updated.";
            }
        } else {
            //could be executed when member code is null or invalid
            output = "Invalid member code.";
        }

        return output;
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MMember entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }
    
    @POST
    @Path("copyMemberNew")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData copyMembernew(MMember mentity) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        int count;
       String memCode="";
        String cardIssue, noDue;
        MMember member = null;
        MMember member1 = find(mentity.getMemCd());
        MMember updateMember = find(mentity.getMemCd()); // suspend old 
       // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       // System.out.println("Member: " + memCode);

        if (member1!=null) {
            if(updateMember.getMemStatus().equals("A")){
            
            List<TIssue> litissue =null;
             litissue = tIssueFacadeREST.findBy("findByMemCd", mentity.getMemCd());
             if(litissue.isEmpty()){
                List<IllRqstmst> rqstMst = null;
                rqstMst = illRqstmstFacadeREST.findBy("findByMemCd",mentity.getMemCd());
                if(rqstMst.isEmpty()){
                
                member = new MMember();
                updateMember.setMemStatus("S");
                boolean deptCode = false;
                boolean instCode = false;
                boolean courseCode = false;
                if (mentity.getMemDept().getFcltydeptcd().toString().equals("")) {
                    deptCode = true;
                } else {
                    MFcltydept dept = mFcltydeptFacadeREST.find(mentity.getMemDept().getFcltydeptcd());
                    if (dept == null || dept.getFldtag() == 'I') {
                        deptCode = true;
                    }
                    member.setMemDept(dept);
                }
                if (mentity.getMemInst().getFcltydeptcd().toString().equals("")) {
                    instCode = true;
                } else {
                    MFcltydept inst = mFcltydeptFacadeREST.find(mentity.getMemInst().getFcltydeptcd());
                    if (inst == null || inst.getFldtag() == 'D') {
                     instCode = true;
                    }
                    member.setMemInst(inst);
                }
                if (mentity.getMemDegree().getBranchCd().toString().equals("")) {
                    courseCode = true;
                } else {
                    MBranchmaster branch = mBranchmasterFacadeREST.find(mentity.getMemDegree().getBranchCd());
                    if (branch == null) {
                     courseCode = true;
                    }
                    member.setMemDegree(branch);
                }
                member.setMemCtgry(mCtgryFacadeREST.find(mentity.getMemCtgry().getCtgryCd()));
//        member.setMemInst(mFcltydeptFacadeREST.find(Integer.parseInt(institute)));
//        member.setMemDept(mFcltydeptFacadeREST.find(Integer.parseInt(department)));
//        member.setMemDegree(mBranchmasterFacadeREST.find(Integer.parseInt(course)));

                //All this is for Getting the Correct MemCd
                //AACCDDYYSSSS  ie  AA: CategoryCode CC: Department Code DD: Cource/Branch Code
                //YY: Currenct Year (14 for 2014) SSSS: Searial No Starting from 0001
                memCode = mentity.getMemCtgry().getCtgryCd() + (mentity.getMemInst().getFcltydeptcd().toString().length() == 1 ? "0" : "") + mentity.getMemInst().getFcltydeptcd().toString() + (mentity.getMemDept().getFcltydeptcd().toString().length() == 1 ? "0" : "") + mentity.getMemDept().getFcltydeptcd().toString();
                memCode += Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2);
                List<MMember> memberList = findBy("findByMaxMemCd", memCode);
                if (memberList.size() > 0) {
                    memCode = memCode + String.format("%04d", Integer.parseInt(memberList.get(0).getMemCd().substring(8)) + 1);
                } else {
                    memCode = memCode + String.format("%04d", 1);
                }
                //Got MemCd

                member.setMemCd(memCode);
                System.out.println("Member: " + memCode);
                //Personal Info Tab
                member.setMemFirstnm(mentity.getMemFirstnm());
                member.setMemLstnm(mentity.getMemLstnm());
                member.setDateOfBirth(dt.getDateNTimechange(mentity.getDateOfBirth().toString()));
                member.setMemYear(dt.getDateNTimechange(mentity.getMemYear().toString()));
                member.setMemStatus(mentity.getMemStatus());
                member.setMemGender(mentity.getMemGender());
                member.setMemType(mTypeFacadeREST.find(mentity.getMemType().getMemberType()));
                if(mentity.getMemId().equals(updateMember.getMemId())){
                     member.setMemId(mentity.getMemId());
                     updateMember.setMemId(updateMember.getMemCd());
                }else if(mentity.getMemId().equals("")){
                    member.setMemId("");
                     updateMember.setMemId(updateMember.getMemCd());
                }else{
                     member.setMemId(mentity.getMemId());
                     updateMember.setMemId(updateMember.getMemCd());
                }
               

                //Contact Info Tab
                member.setMemPrmntadd1(mentity.getMemPrmntadd1());
                member.setMemPrmntadd2(mentity.getMemPrmntadd2());
                member.setMemPrmntcity(mentity.getMemPrmntcity());
                member.setMemPrmntpin(mentity.getMemPrmntpin());
                member.setMemPrmntphone(mentity.getMemPrmntphone());

                member.setMemTmpadd1(mentity.getMemTmpadd1());
                member.setMemTmpadd2(mentity.getMemTmpadd2());
                member.setMemTmpcity(mentity.getMemTmpcity());
                member.setMemTmppin(mentity.getMemTmppin());
                member.setMemTmpphone(mentity.getMemTmpphone());

                member.setMemEmail(mentity.getMemEmail());

                //Guarantor Info
                member.setGrentrFirstnm(mentity.getGrentrFirstnm());
                member.setGrentrLastnm(mentity.getGrentrLastnm());
                member.setGrentrAdd1(mentity.getGrentrAdd1());
                member.setGrentrCity(mentity.getGrentrCity());
                member.setGrentrPin(mentity.getGrentrPin());
                member.setGrentrPhone(mentity.getGrentrPhone());
                member.setGrentrEmail(mentity.getGrentrEmail());
                member.setGrentrFormNo(mentity.getGrentrFormNo());
                if (!(mentity.getGrentrMemId().equals(""))) {
                    member.setGrentrMemId(mentity.getGrentrMemId());
                }

                //Payment Info
                if (mentity.getMemDeposit().compareTo(BigDecimal.valueOf(Double.valueOf(""))) != -1 ) {
                    member.setMemDeposit(mentity.getMemDeposit());
                }
                member.setRcptNo1(mentity.getRcptNo1());
                if (!(mentity.getRcptNo1Dt().equals(""))) {
                    member.setRcptNo1Dt(dt.getDateNTimechange(mentity.getRcptDt().toString()));
                }else{
                
                }

                if (mentity.getMembershipAmt().compareTo(BigDecimal.valueOf(Double.valueOf(""))) != -1) {
                    member.setMembershipAmt(mentity.getMembershipAmt());
                }
                member.setRcptNo2(mentity.getRcptNo2());
                if (!(mentity.getRcptNo2Dt().equals(""))) {
                    member.setRcptNo2Dt(dt.getDateNTimechange(mentity.getRcptDt().toString()));
                }

                if (mentity.getMemMaxdueAmt().compareTo(BigDecimal.valueOf(Double.valueOf(""))) != -1) {
                    member.setMemMaxdueAmt(mentity.getMemMaxdueAmt());
                }
                //Date MemEfctvDtDate = dateFormat.parse(effectiveDate);
                if (!(mentity.getMemEfctvDt().equals(""))) {
                    member.setMemEfctvDt(dt.getDateNTimechange(mentity.getMemEfctvDt().toString()));
                }
              //  Date MemEffctuptoDate = dateFormat.parse(cardExpDate);
                if (!(mentity.getMemEffctupto().equals(""))) {
                    member.setMemEffctupto(dt.getDateNTimechange(mentity.getMemEffctupto().toString()));
                }

                //Others Tab
//                if (cardIssue_String == null) {
//                    cardIssue = "N";
//                } else {
//                    cardIssue = "Y";
//                }

//                if (noDue_String == null) {
//                    noDue = "N";
//                } else {
//                    noDue = "Y";
//                }

                member.setCardissude(mentity.getCardissude());
                //member.setNoDueTag(Integer.valueOf(noDue));a
                member.setNoDueTag(mentity.getNoDueTag());
                member.setMemPassword(mentity.getMemPassword());
                member.setRemarks(mentity.getRemarks());

                //Don't belong to form
               // Date date = new Date();
                member.setMemEntryDt(Todaydate);
                member.setMemFineAmt(new BigDecimal(0));
                member.setUserCd("super user");

                count = super.count();
                if (mentity.getMemCtgry().getCtgryCd().equals("") || mentity.getMemType().getMemberType().equals("") || deptCode == true || instCode == true || courseCode == true) {
                    //Could be executed when primary key columns will be passed empty
                    notprocess = "Too many empty columns. Please fill in data.";
                } else {
                    System.out.println("Member: " + memCode);
                    super.edit(updateMember);
                    super.create(member);
                     output = "Member Copied with Member Code: " + member.getMemCd() + ".";
//                    if (count == super.count()) {
//                        //Could be executed whrn Not null columns will be passed empty
//                        output = "Someting went wrong, Member is not copied.";
//                    } else {
//                        //Success
//                        output = "Member Copied with Member Code: " + member.getMemCd() + ".";
//                    }
                }
                }else{
                    notprocess = "member has ill request..";
                }
            }else{
             notprocess = "member has book issue..";
             }
            }else{
                notprocess = "member is suspended..";
            } 
        } else {
            //could be executed when member code is null or invalid
            notprocess = "Invalid member code.";
        }
         String op;
        String nonop;
        if (output.length() > 1) {
            op = output.substring(0, output.length() - 1);
        } else {
            op = output;
        }
        if (notprocess.length() > 1) {
            nonop = notprocess.substring(0, notprocess.length() - 1);
        } else {
            nonop = notprocess;
        }

        spd.setProcessdata(op);
        spd.setNonprocessdata(nonop);
        return spd;
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public MMember find(@PathParam("id") String id) {
       postdata pds =  checkmemidorcode(id);
       String memcd;
       if (!pds.getMessage().equals("N")){
           memcd = pds.getMessage();
           return super.find(memcd);
       }else{
           return super.find("Null");
       }
        
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MMember> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MMember> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @PUT
    @Path("viewers/view/issues/data")
    public void countAll() {
        MMember s = new MMember();
        Query query = getEntityManager().createNativeQuery(s.getIssues());
        int result = query.executeUpdate();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("findIssuedItemAndDueByMemCode/{memCd}")
    @Produces({"text/plain"})
    // public String findIssuedItemAndDueByMemCode(@Pattern(regexp = "^([a-zA-Z0-9]+([ ][a-zA-Z0-9])*)+{12}+$", message = "{memCd.pattern}") @QueryParam("memCd") String memCd) {    //used in copy member to get due amt
    public String findIssuedItemAndDueByMemCode(@PathParam("memCd") String memCd) {
        String output = "";
        MMember m = find(memCd);
        String count = "0";
        String dueTotal = "0.00";
        if (m != null) {
            count = tIssueFacadeREST.countBy("countByMemCd", m.getMemCd());
            dueTotal = tMemdueFacadeREST.sumBy("findDueSumByMemCd", m.getMemCd());

            if (dueTotal.equals("null")) {
                dueTotal = "0.00";
            }
            output = count + "," + dueTotal;
            System.out.println("output " + output);
        } else {
            output = count + "," + dueTotal;
        }

        return output;
    }

    @GET
    @Path("CheckCopyMember/{memCd}")
    @Produces({"text/plain"})
   // public String checkIfCopyMemberIsAllowed(@Pattern(regexp = "^([a-zA-Z0-9]+([ ][a-zA-Z0-9])*)+{12}+$", message = "{memCd.pattern}") @QueryParam("memCd") String memCd) {    //used in copy member to check whether it is allowed to copy the member or not
    public String checkIfCopyMemberIsAllowed(@PathParam("memCd") String memCd) {
       String output = "Allowed";
       List<IllRqstmst> illRqstList = new ArrayList<>();
        String dueTotalString = tMemdueFacadeREST.sumBy("findDueSumByMemCd", memCd);
        float dueTotal = (float) (dueTotalString.equals("null") ? 0.00 : Float.parseFloat(dueTotalString));

        if (!(dueTotal > 0)) {
            int issueCount = Integer.parseInt(tIssueFacadeREST.countBy("countByMemCd", memCd));
            if (issueCount > 0) {
                output = "Item(s) are issued to member, you can not copy this member.";
            } else {
                illRqstList = illRqstmstFacadeREST.findBy("findByMemCd", memCd);
                if (!illRqstList.isEmpty()) {
                    output = "ILL Item(s) are issued to member, you can not copy this member.";
                }
            }
        } else {
            output = "Due Amount is remaining for member, you can not copy this member.";
        }

        return output;
    }

    //Added Manually
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<MMember> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        //List<String> inString = new ArrayList<>();
        List<String> inString = new ArrayList<>();

        switch (query) {
            case "findByMemberCodes":
                inString.addAll(Arrays.asList(values.split(",")));
                valueList.add(inString);
                break;

            case "findByMemEntryDtBetween":
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    valueList.add(dateFormat.parse(valueString[0]));
                    valueList.add(dateFormat.parse(valueString[1]));

                } catch (ParseException ex) {
                    Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "findByMemFirstnmLike":
                valueList.add(valueString[0] + "%");
                break;
            // used in save new member to generate new member code
            case "findByMaxMemCd":
                valueList.add(valueString[0] + "%");
                break;
            case "findByMemDeptCd":
                valueList.add(Integer.parseInt(valueString[0]));
                break;
            case "findByDepartmentCode":
                valueList.add(Integer.parseInt(valueString[0]));
                break;
            case "findByInstituteCode":
                valueList.add(Integer.parseInt(valueString[0]));
                break;
            case "findByMemEfctvDt":
                SimpleDateFormat startDate = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    valueList.add(startDate.parse(valueString[0]));
                    System.out.println("Executed");
                } catch (ParseException ex) {
                    Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "findByMemEffctupto":
                SimpleDateFormat endDate = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    valueList.add(endDate.parse(valueString[0]));
                } catch (ParseException ex) {
                    Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                valueList.add(valueString[0]);
                break;

        }
        System.out.println("MMember." + query + valueList);
        return super.findBy("MMember." + query, valueList);
    }

    //Sample code by Divyakant 
    //This is for getting category wise member's list.
    @GET
    @Path("MemberCategory/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<MMember> MemberListByCategory(@PathParam("searchParameter") String param) {
        List<MMember> member = findBy("findByMemCtgryDesc", param);
        return member;
    }

    //This service returns all the members by department name
    @GET
    @Path("MemberDepartment/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<MMember> MemberListByDepartment(@PathParam("searchParameter") String param) {
        List<MMember> member = findBy("findByMemDeptDscr", param);
        return member;
    }

    //Return member information by member code
    @GET
    @Path("MemberCode/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<MMember> SingleMember(@PathParam("searchParameter") String param) {
        List<MMember> member = findBy("findByMemCd", param);
        return member;
    }

    //Return member information by member codes, seperate member codes by ","
    @GET
    @Path("MemberCodes/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<MMember> MultipleMembers(@PathParam("searchParameter") String param) {
        List<MMember> member = findBy("findByMemberCodes", param);
        return member;
    }

    //Return member information by member id
    @GET
    @Path("MemberID/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<MMember> MemberListByMemberID(@PathParam("searchParameter") String param) {
        List<MMember> member = findBy("findByMemId", param);
        return member;
    }

    //Return all members information by member designation
    @GET
    @Path("MemberDesignation/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<MMember> MemberListByDesignation(@PathParam("searchParameter") String param) {
        List<MMember> member = findBy("findByMemDegreeName", param);
        return member;
    }

    //Return all members information by respective institute
    @GET
    @Path("MemberInstitute/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<MMember> MemberListByInstitute(@PathParam("searchParameter") String param) {
        List<MMember> member = findBy("findByInstitute", param);
        return member;
    }

    //Return members information by username
    @GET
    @Path("Username/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<MMember> MemberListByUsername(@PathParam("searchParameter") String param) {
        List<MMember> member = findBy("findByUserCd", param);
        return member;
    }

    //Return all members information by member status. (Active:A, Suspended:S)
    @GET
    @Path("MemberStatus/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<MMember> MemberListByStatus(@PathParam("searchParameter") String param) {
        List<MMember> member = findBy("findByMemStatus", param);
        return member;
    }

    //Return member information by members firstname
    @GET
    @Path("MemberFirstName/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<MMember> MemberListByFirstName(@PathParam("searchParameter") String param) {
        List<MMember> member = findBy("findByMemFirstnm", param);
        return member;
    }

    //Return all members information by member entry date, membership starting date and mebership ending date
    @GET
    @Path("MemberShipDate/{findBy}/{searchparameter}")
    @Produces({"application/xml", "application/json"})
    public List<MMember> MemberListByDate(@PathParam("findBy") String code, @PathParam("searchparameter") String param) {
        switch (code) {

            case "MemberEntryDateBetween":
                code = "findByMemEntryDtBetween";
                break;

            case "MemberShipStartingdate":
                code = "findByMemEfctvDt";
                break;

            case "MemberShipEndingdate":
                code = "findByMemEffctupto";
                break;
        }
        List<MMember> member = findBy(code, param);
        return member;
    }

    //All the members registered into the library 
    @GET
    @Path("AllMembers")
    @Produces({"application/xml", "application/json"})
    public List<MMember> AllMembers() {
        List<MMember> member = findAll();
        return member;
    }

    //Get member details by specifying code of institute, department, designation and category
//    @GET
//    @Path("findMemberByParameters/inst/{Institute}/dept/{Department}/desgn/{Designation}/ctgry/{Category}")
//    @Produces({"application/xml", "application/json"})
//    public List<Object> findMemberByParameters(@PathParam("Institute") String Institute,
//            @PathParam("Department") String Department,
//            @PathParam("Designation") String Designation,
//            @PathParam("Category") String Category) throws ParseException {
//        String q = "";
//        List<Object> result = new ArrayList<>();
//        Query query;
//        boolean deptCode = false;
//        boolean instCode = false;
//        boolean courseCode = false;
//        boolean cateoryCode = false;
//        if (Department.isEmpty()) {
//            deptCode = true;
//        } else {
//            MFcltydept dept = mFcltydeptFacadeREST.find(Integer.parseInt(Department));
//            if (dept == null || dept.getFldtag() == 'I') {
//             //   throw new DataException("No department found with deptCd : " + Department);
//            }
//        }
//        if (Institute.isEmpty()) {
//            instCode = true;
//        } else {
//            MFcltydept inst = mFcltydeptFacadeREST.find(Integer.parseInt(Institute));
//            if (inst == null || inst.getFldtag() == 'D') {
//              //  throw new DataException("No institute found with instCd : " + Institute);
//            }
//        }
//        if (Designation.isEmpty()) {
//            courseCode = true;
//        } else {
//            MBranchmaster branch = mBranchmasterFacadeREST.find(Integer.parseInt(Designation));
//            if (branch == null) {
//              //  throw new DataException("No designation found with designationCd : " + Designation);
//            }
//        }
//        if (Category.isEmpty()) {
//            cateoryCode = true;
//        } else {
//            MCtgry ctgry = mCtgryFacadeREST.find(Category);
//            if (ctgry == null) {
//              //  throw new DataException("No category found with categoryCd : " + Category);
//            }
//        }
//        q = "select * from m_member where "
//                + " m_member.mem_inst = '" + Institute + "' "
//                + " and m_member.mem_dept = '" + Department + "' "
//                + " and m_member.mem_degree = '" + Designation + "' "
//                + " and m_member.mem_ctgry = '" + Category + "' ";
//
//        query = getEntityManager().createNativeQuery(q);
//        result = (List<Object>) query.getResultList();
//
//        return result;
//    }
//   
    
    @GET
    @Path("findMember/{Institute}/{Department}/{Designation}/{Category}")
    @Produces({"application/xml", "application/json"})
    public List<Object> findMember(@PathParam("Institute") String Institute,
            @PathParam("Department") String Department,
             @PathParam("Designation") String Designation,
            @PathParam("Category") String Category) throws ParseException {
        String q = "";  
        List<Object> result = new ArrayList<>();
        Query query;
        boolean deptCode = false;
        boolean instCode = false;
        boolean cateoryCode = false;
        boolean courseCode = false;
        
        System.out.println("this is institute code"+Institute);
        System.out.println("this is Category code"+Category);
       
        if (Department.isEmpty()) {
            System.out.println("i am in if");
            deptCode = true;
            
        } else {
            System.out.println("i am in else");
            MFcltydept dept = mFcltydeptFacadeREST.find(String.valueOf(Department));
            System.out.println("i am in else"+dept);
            if (dept == null || dept.getFldtag() == 'I') {
                deptCode = true;
             //   throw new DataException("No department found with deptCd : " + Department);
            }
        }
        if (Institute.isEmpty()) {
            instCode = true;
        } else {
            MFcltydept inst = mFcltydeptFacadeREST.find(String.valueOf(Institute));
            if (inst == null || inst.getFldtag() == 'D') {
                instCode = true;
              //  throw new DataException("No institute found with instCd : " + Institute);
            }
        }
        if (Designation.isEmpty()) {
            courseCode = true;
        } else {
            MBranchmaster branch = mBranchmasterFacadeREST.find(String.valueOf(Designation));
            if (branch == null) {
                courseCode = true;
              //  throw new DataException("No designation found with designationCd : " + Designation);
            }
        }
        if (Category.isEmpty()) {
            cateoryCode = true;
        } else {
            MCtgry ctgry = mCtgryFacadeREST.find(Category);
            if (ctgry == null) {
                cateoryCode = true;
              //  throw new DataException("No category found with categoryCd : " + Category);
            }
        }
        System.out.println("this is department code"+Department);
        if(courseCode == true && deptCode == true && instCode == true){
              q = "select * from m_member where"
                + " m_member.mem_ctgry = '" + Category + "' ";
        }
        else if(courseCode == true && deptCode == true){
              q = "select * from m_member where"
                + "  m_member.mem_inst = '" + Institute + "' "
                + " and m_member.mem_ctgry = '" + Category + "' ";
        }
        else if(courseCode == true){
            q = "select * from m_member where"
                + "  m_member.mem_inst = '" + Institute + "' "
                + " and m_member.mem_ctgry = '" + Category + "' "
                + " and m_member.mem_dept = '" + Department + "' ";
        }
        else{
            q = "select * from m_member where"
                + "  m_member.mem_inst = '" + Institute + "' "
                + " and m_member.mem_dept = '" + Department + "' "
                + " and m_member.mem_degree = '" + Designation + "' "
                + " and m_member.mem_ctgry = '" + Category + "' ";
        }
         System.out.println("q------"+q);

        query = getEntityManager().createNativeQuery(q,MMember.class);
        result = (List<Object>) query.getResultList();

        return result;
    }
    
    
    @GET
    @Path("findMemberByParameters/{Institute}/{Department}/{Designation}/{Category}/{entryDates}")
    @Produces({"application/xml", "application/json"})
    public List<Object> findMemberByParameters(@PathParam("Institute") String Institute,
            @PathParam("Department") String Department,
            @PathParam("Designation") String Designation,
            @PathParam("Category") String Category,@PathParam("entryDates") String entryDates ) throws ParseException {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;
        boolean deptCode = false;
        boolean instCode = false;
        boolean courseCode = false;
        boolean cateoryCode = false;
        String[] entrydate = entryDates.split(",");
        
        String entrydtfrm = entrydate[0];
        String entrydtto = entrydate[1];
        
       
        if (Department.isEmpty()) {
            deptCode = true;
        } else {
            MFcltydept dept = mFcltydeptFacadeREST.find(String.valueOf(Department));
            if (dept == null || dept.getFldtag() == 'I') {
             //   throw new DataException("No department found with deptCd : " + Department);
            }
        }
        if (Institute.isEmpty()) {
            instCode = true;
        } else {
            MFcltydept inst = mFcltydeptFacadeREST.find(String.valueOf(Institute));
            if (inst == null || inst.getFldtag() == 'D') {
              //  throw new DataException("No institute found with instCd : " + Institute);
            }
        }
        if (Designation.isEmpty()) {
            courseCode = true;
        } else {
            MBranchmaster branch = mBranchmasterFacadeREST.find(String.valueOf(Designation));
            if (branch == null) {
              //  throw new DataException("No designation found with designationCd : " + Designation);
            }
        }
        if (Category.isEmpty()) {
            cateoryCode = true;
        } else {
            MCtgry ctgry = mCtgryFacadeREST.find(Category);
            if (ctgry == null) {
              //  throw new DataException("No category found with categoryCd : " + Category);
            }
        }
        q = "select * from m_member where"
                + "(m_member.mem_entry_dt BETWEEN '" + entrydtfrm + "' AND '" + entrydtto + "')"
                + " and m_member.mem_inst = '" + Institute + "' "
                + " and m_member.mem_dept = '" + Department + "' "
                + " and m_member.mem_degree = '" + Designation + "' "
                + " and m_member.mem_ctgry = '" + Category + "' ";
        
         System.out.println("q------"+q);

        query = getEntityManager().createNativeQuery(q,MMember.class);
        result = (List<Object>) query.getResultList();

        return result;
    }
    
    @GET
    @Path("searchMember/{searchParameter}/{param}/{param1}/{param2}")
    @Produces({"application/xml", "application/json"})
   // public List<MMember> searchMember(@QueryParam("searchParameter") String code, @QueryParam("param") String param,
    //        @QueryParam("param1") String param1, @QueryParam("param2") String param2) {
    public List<MMember> searchMember(@PathParam("searchParameter") String code, @PathParam("param") String param,
            @PathParam("param1") String param1, @PathParam("param2") String param2) {    
        switch (code) {
            case "code":
                code = "findByMemCd";
                break;
            case "firstName":
                code = "findByMemFirstnmLike";
                break;
            case "department":
                code = "findByMemDeptDscr";
                break;
            case "designation":
                code = "findByMemDegreeName";
                break;
            case "category":
                code = "findByMemCtgryDesc";
                break;
            case "date":
                code = "findByMemEntryDtBetween";
                param = param1 + "," + param2;
                break;
        }
        List<MMember> member = findBy(code, param);
        return member;
    }

    @POST
    @Path("deleteMembers")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData deleteMembers(String memberIdsList) {
        
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String memCd="";
        
         String datatype = memberIdsList.substring(0, 1);
        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(memberIdsList);
            memCd = jsonobj.getString("memcd");

        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(memberIdsList);
                memCd = stringintoxml.getdatafromxmltag(doc, "memcd");
            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }
        
        
        String[] memberIds = memCd.split(",");
        
        int newCount;
       
        List<TIssue> issue = new ArrayList<>();
        List<TReserve> reserve = new ArrayList<>();
        List<TOtherissue> otherIssue = new ArrayList<>();
        List<IllRqstmst> rqstMst = new ArrayList<>();
        List<TBookbankissuereturn> TBookbankissuereturn = new ArrayList<>();
        List<TMemdue> memberDue = new ArrayList<>();
        List<SRequest> request = new ArrayList<>();
        List<MGroup> group = new ArrayList<>();
        for (String memberId : memberIds) {
            int oldCount = Integer.parseInt(countREST());
            System.out.println("Old Count: " + oldCount);
            MMember m=null;
            StringprocessData spdoutput=null;
            m= find(memberId);
            if (m!=null) {
                issue = tIssueFacadeREST.findBy("findByMemCd", m.getMemCd());
                reserve = tReserveFacadeREST.findBy("findByMemCd", m.getMemCd());
                otherIssue = tOtherissueFacadeREST.findBy("findByMemCd", m.getMemCd());
                TBookbankissuereturn = tBookbankissuereturnFacadeREST.findBy("findByMemCd", m.getMemCd());
                memberDue = tMemdueFacadeREST.findBy("findByMemCd", m.getMemCd());
                rqstMst = illRqstmstFacadeREST.findBy("findByMemCd", m.getMemCd());
                request = sRequestFacadeREST.findBy("findByMemCd", m.getMemCd());
                group = mGroupFacadeREST.findBy("findByMemCd", m.getMemCd());
                System.out.println("Issue Size: " + issue.size());
                System.out.println("Reserve Size: " + reserve.size());
                System.out.println("Other issue Size: " + otherIssue.size());
                System.out.println("TBookbankissuereturn Size: " + TBookbankissuereturn.size());
                System.out.println("memberDue Size: " + memberDue.size());
                System.out.println("RqstMst Size: " + rqstMst.size());
                System.out.println("Request Size: " + request.size());
                System.out.println("group Size: " + group.size());
                System.out.println("group Size: " + group.toString());
                if (issue.size() == 0 && reserve.size() == 0 && otherIssue.size() == 0
                        && rqstMst.size() == 0 && TBookbankissuereturn.size() == 0 && memberDue.size() == 0 && request.size() == 0) {
                    try {
                        if(group.size()==0){
                            super.remove(super.find(m.getMemCd()));
                            output+=m.getMemCd()+" Member deleted,";
                        }else{
                           // String groupdata="{groupname:"+group.get(0).getGroupName()+",memcd:"+m.getMemCd()+"}";
                           spdoutput = mGroupFacadeREST.deleteMemberingroup("{groupname:"+group.get(0).getGroupName()+",memcd:"+m.getMemCd()+"}");
                           //mGroupFacadeREST.remove(group.get(0));.
                           String s[]=spdoutput.getProcessdata().split(",");
                           if(s[0].contains(m.getMemCd()+" member delete")){
                                super.remove(super.find(m.getMemCd()));
                                output+=m.getMemCd()+" Member deleted,";
                           }else{
                             notprocess+=m.getMemCd()+" Member not deleted,";
                           }
                          
                        }
                       
                    } catch (Exception e) {
                        notprocess+= "Something went wrong,"+m.getMemCd()+" member not deleted Pending transactions or requests,";
                    }

//                    newCount = Integer.parseInt(countREST());
//                    System.out.println("Old Count: " + oldCount + ", New Count: " + newCount);
//                    if (oldCount == newCount) {
//                        //notDeletedId = String.join(",", memberId);
//                        notDeletedId = notDeletedId + memberId + ",";
//                    } else {
//                        //DeletedId = String.join(",", memberId);
//                        DeletedId = DeletedId + memberId + ",";
//                    }
                   // System.out.println("DeletedId: " + DeletedId);
                   // System.out.println("Not DeletedId: " + notDeletedId);
                } else {
                   // notDeletedId = notDeletedId + memberId + ",";
                    notprocess+= "Member with ID: " + memberId + " not deleted Pending transactions or requests,";
                }
            } else {
                notprocess+= "Invalid Member Code or member does not exist with member code: " + memberId+",";
            }
           // System.out.println(".........  " + notDeletedId + "        " + DeletedId);
           // output += "DeletedIds: " + DeletedId + "\nNot DeletedIds: " + notDeletedId;
        }
        
         String op;
        String nonop;
        if (output.length() > 1) {
            op = output.substring(0, output.length() - 1);
        } else {
            op = output;
        }
        if (notprocess.length() > 1) {
            nonop = notprocess.substring(0, notprocess.length() - 1);
        } else {
            nonop = notprocess;
        }

        spd.setProcessdata(op);
        spd.setNonprocessdata(nonop);
        return spd;
    }

    @PUT
    @Path("renewMembership")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData renewMembership(String renewdatails) {
        
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String memCd="";
        String validupto="";
         String datatype = renewdatails.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(renewdatails);
            memCd = jsonobj.getString("memcd");
            validupto = jsonobj.getString("validupto");

        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(renewdatails);
                memCd = stringintoxml.getdatafromxmltag(doc, "memcd");
                validupto = stringintoxml.getdatafromxmltag(doc, "validupto");

            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }           
        
        MMember member=null;
        String[] memlist = memCd.split(",");
        for (int i = 0; i < memlist.length; i++) {
            member = find(memlist[i]);
            if(member!=null){
                member.setMemEffctupto(dt.getDateNTimechange(validupto));
                edit(member);
                output+=memlist[i]+" Member updated,"; 
            }else{
                notprocess +=memlist[i]+" Member not found,"; 
            }
        
        }

        String op;
        String nonop;
        if (output.length() > 1) {
            op = output.substring(0, output.length() - 1);
        } else {
            op = output;
        }
        if (notprocess.length() > 1) {
            nonop = notprocess.substring(0, notprocess.length() - 1);
        } else {
            nonop = notprocess;
        }

        spd.setProcessdata(op);
        spd.setNonprocessdata(nonop);
        return spd;
    }

    @GET
    @Path("CheckMemberEligibility/{searchParameter}")
    @Produces({"text/plain"})
    public String MemberEligibility(@PathParam("searchParameter") String memberCode) {
        MMember member=null;
        String status="";
        try {
            member = find(memberCode);
            status = member.getMemStatus();
        } catch (ArrayIndexOutOfBoundsException | NullPointerException | InternalServerErrorException d) {
            return "Invalid member code";
        }

        String output;

        if (!"A".equals(status)) {
            output = "Suspended Member.";
        } else {
            output = "Active Member.";
        }
        return output;
    }

    @GET
    @Path("memberPrevilege/{memCd}")
    @Produces({"text/plain"})
    public String memberPrevilege(@PathParam("memCd") String memCd) {    //used in copy member to get due amt
        MMember member = find(memCd);
        String output="";
        if (member == null) {
            return "Invalid member code";
        } else {
            int issCount = Integer.parseInt(tIssueFacadeREST.countBy("countByMemCd", memCd));
            int resCount = Integer.parseInt(tReserveFacadeREST.countBy("countByMemCd", memCd));
            System.out.println("Issued count:" + issCount + ", Reserve count: " + resCount);
            String memCtgry = memCd.substring(0, 2);
            System.out.println("Member category: " + memCtgry);
            List<CtgryMediaIssueReserve> ctgryDetails = ctgryMediaIssueReserveFacadeREST.findBy("findByCtgryCd", memCtgry);
            int maxIssAllowed = ctgryDetails.get(0).getMaxAllowed();
            int maxResAllowed = ctgryDetails.get(0).getMaxReserve();
            System.out.println("Max Allowed:" + maxIssAllowed + ", Max Reserve: " + maxResAllowed);

            if (issCount >= maxIssAllowed) {
                output = "Maximum issue limit reached for " + memCd;
            } else if (resCount >= maxResAllowed) {
                output = "Maximum reserve limit reached for " + memCd;
            } else {
                output = "Member allowed to Issue or Reserve";
            }
        }
        return output;
    }

    @GET
    @Path("checkforMemberStatus/{searchParameter}")
    @Produces("text/plain")
    public String checkforMemberStatus(@PathParam("searchParameter") String memberCode) {
        MMember member=null;
        String status="";
        try {
            member = find(memberCode);
            status = member.getMemStatus();
        } catch (ArrayIndexOutOfBoundsException | NullPointerException | InternalServerErrorException d) {
            return "Invalid member code";
        }

        String output;

        if (!"A".equals(status)) {
            output = "Suspended Member.";
        } else {
            output = "Active Member.";
        }
        return output;
    }

    @GET
    @Path("CheckMemberAvalibility/{memCd}")
    @Produces({"text/plain", "application/xml", "application/json"})
    public boolean CheckMemberAvalibility(@PathParam("memCd") String memCd) {
       MMember member = find(memCd);

        if (member == null) {
            return false;
        } else {
            return true;
        }
    }

//   @GET
//   @Path("checkMemberValidity/{memCd}")
//   @Produces({"text/plain","application/xml", "application/json"})
//   public Response MemberValidity(@PathParam("memCd") String memCd)
//   {    
//       Response.ResponseBuilder responseBuilder = Response.status(200);
//        member = find(memCd);
//        
//        if(member == null)
//        {
//            output = "Invalid member code.";
//            responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
//        }
//      
//        else
//        {
//            responseBuilder.type(MediaType.APPLICATION_XML).entity(member);
//            //Also used by CopyMember page
//        }
//        return responseBuilder.build();
//    }
    @GET
    @Path("checkMemberValidity/{memCd}")
    @Produces({"text/plain"})
    public String checkMemberValidity(@PathParam("memCd") String memCd) {
        String output="";
        MMember member = find(memCd);
        if (member == null) {
            output = "invalid";
        } else {
            output = "valid";
        }
        return output;
    }

    @GET
    @Path("IssuedItemAndDueByMemCd/{memCd}")
    @Produces({"text/plain"})
    public String getIssuedItemAndDueByMemCd(@PathParam("memCd") String memCd) {
        String output="";     
        MMember member = find(memCd);

        if (member == null) {
            return output = "Invalid member code.";
        }
        String count = tIssueFacadeREST.countBy("countByMemCd", memCd);
        String dueTotal = tMemdueFacadeREST.sumBy("findDueSumByMemCd", memCd);
        if (dueTotal.equals("null")) {
            dueTotal = "0.00";
        }
        output = count + "," + dueTotal;
        System.out.println("output " + output);
        //Also used by CopyMember page
        return output;
    }

    @GET
    @Path("memberDetail/{memCd}")
    @Produces({"text/plain", "application/xml", "application/json"})
    public Response memberDetail(@PathParam("memCd") String memCd) {
        Response.ResponseBuilder responseBuilder = Response.status(200);
        String output="";
       MMember member = find(memCd);

        if (member == null) {
            output = "Invalid member code.";
            responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
        } else {
            responseBuilder.type(MediaType.APPLICATION_XML).entity(member);
            //Also used by CopyMember page
        }
        return responseBuilder.build();
    }

//   @GET
//   @Path("privilegeDetail")
//   @Produces({"text/plain","application/xml", "application/json"})
//   public Response privilegeDetail(@QueryParam("memCd") String memCd)
//   {   Response.ResponseBuilder responseBuilder = Response.status(200);
//       if(!memCd.equals(""))
//       { List<CtgryMediaIssueReserve> list = ctgryMediaIssueReserveFacadeREST.findBy("findByMemCd", memCd);
//         if(!list.isEmpty())
//         {
//             System.out.println("list size "+list.size()+"   nn "+list.get(0));
//            
//           responseBuilder.type(MediaType.APPLICATION_XML).entity(ctgryMediaIssueReserveFacadeREST.findBy("findByMemCd", memCd));
//           
//         }
//       }
//       else
//       {
//           output = "Empty MemberCode, Please enter valid Member Code.";
//           responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
//       }
//       return responseBuilder.build();
//   } 
    //used in transaction js to get max. due date of member
    @GET
    @Path("getDueDate/{accNos}/{memCd}")
    @Produces({"text/plain", "application/xml", "application/json"})
   // public String getDueDate(@QueryParam("accNos") String accNos, @QueryParam("memCd") String memCd) {
    public String getDueDate(@PathParam("accNos") String accNos, @PathParam("memCd") String memCd) {    
        String output="";
        if (accNos.length() > 0) {
            output = getDueDate(accNos.split(","), memCd).toString();
        }
        return output;
    }

    public JsonObject getDueDate(String[] accNos, String memCd) {
        MMember member = find(memCd);
        Location location;
        CtgryMediaIssueReserve privilege;
        JsonObjectBuilder object = Json.createObjectBuilder();
        Calendar dueDate;
        String libMatCode;
        Map<String, Integer> matTypeIssDays = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        List<MWeekoffday> mweekOffList = new ArrayList<>();
        List<String> weekOffList = new ArrayList<>();

        mweekOffList = mWeekoffdayFacadeREST.findAll();
        for (MWeekoffday w : mweekOffList) {
            weekOffList.add(w.getWeekOffDay());
        }

        for (int i = 0; i < accNos.length; i++) {
            location = locationFacadeREST.findBy("findByP852", accNos[i]).get(0);
            libMatCode = location.getMaterial().getCode();

            if (!matTypeIssDays.containsKey(libMatCode)) {
                privilege = ctgryMediaIssueReserveFacadeREST.findBy("findByCtgryCdAndMediaCd", member.getMemCtgry().getCtgryCd() + "," + location.getMaterial().getCode()).get(0);
                matTypeIssDays.put(libMatCode, privilege.getIssPeriod());
            }

            dueDate = Calendar.getInstance();
            dueDate.setTime(new Date());
            dueDate.add(Calendar.DATE, matTypeIssDays.get(libMatCode));

            dueDate = getWorkingDayDate(dueDate, weekOffList);        //Check Week of Day and gets next working day date

            object.add(accNos[i], dateFormat.format(dueDate.getTime()).toString());
        }

        return object.build();
    }

    public Calendar getWorkingDayDate(Calendar dueDate, List weekOffList) {
        dueDate = setTimeZero(dueDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Boolean workingDay = false;
        List<MCalender> holidayList;
        //Calender.SUNDAY = 1, MONDAY=2, TUESDAY=3, WEDNESDAY=4, THURSDAY=5, FRIDAY=6, SATURDAY=7
        while (!workingDay) {
            workingDay = true;
            if (weekOffList.contains(Integer.toString(dueDate.get(Calendar.DAY_OF_WEEK))))//Check for WeekOffday
            {
                dueDate.add(Calendar.DATE, 1);
                workingDay = false;
            }
            if (workingDay) //Check of Holiday
            {
                holidayList = mCalenderFacadeREST.findBy("findByFestivalDt", dateFormat.format(dueDate.getTime()));
                if (!holidayList.isEmpty()) {
                    dueDate.add(Calendar.DATE, 1);
                    workingDay = false;
                }
            }
        }
        return dueDate;
    }

    public Calendar setTimeZero(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        return date;
    }

    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    //public List<MMember> retrieveAll(@QueryParam("accept") String accept) {
    public List<MMember> retrieveAll() {    
        List<MMember> getAll = null;
        getAll = findAll();
        return getAll;
    }

    /*@GET
     @Path("criteria")
     @Produces({"application/xml", "application/json"})
     public void criteria(@QueryParam("accept") String accept)
     {
     //criteria_test();
     criteria_test_join();
     }*/
    //For letter generation to send reminder mail to member 
    @GET
    @Path("ReminderToMember/{findBy}/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<Object> ReminderToMember(@PathParam("findBy") String Paramname,
            @PathParam("searchParameter") String accNo) throws ParseException {

        String q = "";
        //System.out.println("d:"+accNo);

        if (accNo.length() == 2) {
            System.out.println("v:" + accNo);
         //   throw new DataException("Accession no should not be null");
        }
        String[] valueString = accNo.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
            case "LetterGenerationByAccessionNo":
                q = "SELECT  m_member.mem_cd, m_member.mem_firstnm, m_member.mem_lstnm, m_member.mem_tag, m_member.mem_ctgry, m_member.mem_status, m_member.mem_inst, m_member.mem_dept, m_member.mem_degree, \n"
                        + " m_member.mem_year, m_member.mem_prmntadd1, m_member.mem_prmntadd2,m_member.mem_prmntcity, m_member.mem_prmntpin, m_member.mem_prmntphone,m_member.mem_email,\n"
                        + " t_issue.mem_cd AS Expr1,letter_formats.letter_format, letter_formats.subject,Biblidetails.Fvalue, t_issue.iss_dt,t_issue.due_dt,t_issue.acc_no,letter_formats.letter_fullname \n"
                        + " FROM m_member, t_issue, letter_formats,Biblidetails,Location WHERE m_member.mem_cd = t_issue.mem_cd \n"
                        + " and Biblidetails.Recid=Location.recid and Location.p852=t_issue.acc_no and Biblidetails.Tag='245' and Biblidetails.sbfld='a' \n"
                        + " AND letter_formats.letter_name = 'reminder_cir' \n"
                        + " AND t_issue.acc_no = '" + accNo + "'";
                break;

        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }

    //Reminder Mail
    //This method will be used to send mail to member, will perform action on the combination on all the parameters
    @GET
    @Path("ReminderMail/inst/{memberInstitute}/dept/{memberDepartment}/degree/{memberDegree}")
    @Produces({"application/xml", "application/json"})
    public List<Object> GetIssuedItemDetailsforReminderToMember(@PathParam("memberInstitute") String memberInstitute,
            @PathParam("memberDepartment") String memberDepartment,
            @PathParam("memberDegree") String memberDegree) throws ParseException {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;
        boolean deptCode = false;
        boolean instCode = false;
        boolean courseCode = false;
        if (memberDepartment.isEmpty()) {
            deptCode = true;
        } else {
            MFcltydept dept = mFcltydeptFacadeREST.find(Integer.parseInt(memberDepartment));
            if (dept == null || dept.getFldtag() == 'I') {
              //  throw new DataException("No department found with deptCd : " + memberDepartment);
            }
        }
        if (memberInstitute.isEmpty()) {
            instCode = true;
        } else {
            MFcltydept inst = mFcltydeptFacadeREST.find(Integer.parseInt(memberInstitute));
            if (inst == null || inst.getFldtag() == 'D') {
               // throw new DataException("No institute found with instCd : " + memberInstitute);
            }
        }
        if (memberDegree.isEmpty()) {
            courseCode = true;
        } else {
            MBranchmaster branch = mBranchmasterFacadeREST.find(Integer.parseInt(memberDegree));
            if (branch == null) {
              //  throw new DataException("No designation found with degreeCd : " + memberDegree);
            }
        }

        q = "select * from m_member,t_issue where m_member.mem_cd = t_issue.mem_cd "
                + " and m_member.mem_inst = '" + memberInstitute + "' "
                + " and m_member.mem_dept = '" + memberDepartment + "' "
                + " and m_member.mem_degree = '" + memberDegree + "' ";

        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }

    /* public void criteria_test() 
     {
     CriteriaBuilder cb = em.getCriteriaBuilder();                                                                                       
     CriteriaQuery<MMember> query = cb.createQuery(MMember.class);
     Root<MMember> c = query.from(MMember.class);
     query.select(c);
     TypedQuery<MMember> qry = em.createQuery(query);
     List<MMember> results = qry.getResultList();

     for(MMember mem: results) {
     System.out.println(mem.getMemCd());
     System.out.println(mem.getMemFirstnm());
     System.out.println(mem.getMemLstnm());
     System.out.println(mem.getDateOfBirth());
     System.out.println(mem.getMemStatus());
     System.out.println(mem.getMemType().getMemberType());
     }
     } 
    
     public void criteria_test_join() 
     {
     CriteriaBuilder cb = em.getCriteriaBuilder();                                                                                       
     CriteriaQuery<MMember> query = cb.createQuery(MMember.class);
            
     Root<MMember> mem = query.from(MMember.class);
     Join<MMember, TIssue> issue = mem.join("memCd");
     List<Predicate> conditions = new ArrayList();
     conditions.add(cb.equal(mem.get("memCd"), issue.get("memCd")));
            
            
     TypedQuery<MMember> typedQuery = em.createQuery(query
     .select(mem)
     .where(conditions.toArray(new Predicate[] {}))
     .orderBy(cb.asc(mem.get("memCd")))
     .distinct(true));
     List<MMember> results = typedQuery.getResultList();
            
     for(MMember result: results) {
     System.out.println(result.getMemFirstnm());
     }
     }*/
//        CriteriaBuilder cb = em.getCriteriaBuilder();                                                                                       
//        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
//        Root<MMember> root = query.from(MMember.class);
//        Join<MMember, TIssue> rootPart = root.join("memCd");
//      
//        query.multiselect(root.get("memCd"), root.get("memFirstnm"), rootPart.get("userCd"));
//        //query.multiselect(root, rootPart);
//        Query qry = em.createQuery(query);
//        List<Object[]> results = qry.getResultList();
//
//        for(Object[] result: results) {
//            System.out.println(result[0]);
//            System.out.println(result[1]);
//            System.out.println(result[2]);
//        }
    public void jsontoxml() throws JAXBException {
        JAXBContext contextObj = JAXBContext.newInstance(MMember.class);

        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        Result writer = null;
        marshallerObj.marshal(em, writer);
    }

    public final class XStreamTranslator {

        private XStream xstream = null;

        private XStreamTranslator() {
            xstream = new XStream();
            xstream.ignoreUnknownElements();
        }

        /**
         * Convert a any given Object to a XML String
         *
         * @param object
         * @return
         */
        public String toXMLString(Object object) {
            return xstream.toXML(object);
        }
    }
    @DELETE
    @Path("delete/{code}")
    @Produces("text/plain")
    public String deleteBranchMaster(@PathParam("code") String code)
    {   
        System.out.print("i am in delete");
        count = Integer.valueOf(countREST());
        remove(String.valueOf(code));
        if(count == Integer.parseInt(countREST()))
        {
           output = "This branch is used under some department, you can not delete this branch.";
        }
        else
        {
            output = "Branch Deleted!!!";
        }
       return output;   
    }

    @DELETE
    @Path("resetTransactions/{memberCode}")
    @Produces({"text/plain","application/xml", "application/json"})
    public StringData resetTransactions(@PathParam("memberCode") String memberCode) {
        StringData sd;
        List<TMemdue> memberDue = null;
        MMember member = null;
        List<TIssue> issue = null;
        String issueCount = null;
        String dueAmount = "null";
        List<TReserve> reserveDetails = null;
        List<Location> res_acc_no = new ArrayList<>();
        Location location = null;
        List<Integer> res_record_id = new ArrayList<>();
        List<TOtherissue> otherissue = null;
        List<IllRqstmst> illRqstmst = null;
        List<TMemdue> litmemdue =null;
        String memcode="";
        String output="";
        
        
        try {
            member = find(memberCode);
        } catch (NullPointerException e) {
            output = "Invalid member code.";
        }
        
        if (member != null){
            memcode = member.getMemCd();
            // find all data
            issue = tIssueFacadeREST.findBy("findByMemCd", memcode); // check t_issue 
            memberDue = tMemdueFacadeREST.findBy("findByMemCd", memcode); // check memdue
            reserveDetails=tReserveFacadeREST.findBy("findByMemCd", memcode); // check t_reserve
            otherissue = tOtherissueFacadeREST.findBy("findByMemCd", memcode); // check t_otherissue
            illRqstmst = illRqstmstFacadeREST.findBy("findByMemCd", memcode); //check ill_request
            
            // remover from each table.
            if(issue.size()>0){
                   for(int i=0;i<issue.size();i++){
                      String accno=issue.get(i).getTIssuePK().getAccNo();
                      location = locationFacadeREST.getByAcc(accno).get(0);
                      location.setStatus("AV");
                      tIssueFacadeREST.removebymemcodeNaccno(memcode, accno);
                      locationFacadeREST.edit(location);
                   } 
            }
            if(memberDue.size() >0){
                for(int i=0;i<memberDue.size();i++){
                    int slipno = memberDue.get(i).getSlipNo();
                    tMemdueFacadeREST.remove(slipno); 
                }
            }
            if(reserveDetails.size()>0){
                for(int i=0;i<reserveDetails.size();i++){
                    int recid = reserveDetails.get(i).getTReservePK().getRecordNo();
                    tReserveFacadeREST.removebyMemcdNRecid(memcode,recid);
                }
            }
            if(otherissue.size()>0){
                for(int i=0;i<otherissue.size();i++){
                    String accno = otherissue.get(i).getTOtherissuePK().getAccNo();
                    tOtherissueFacadeREST.removebyMemcdNAccno(memcode,accno);
                }
            }
            if(illRqstmst.size()>0){
                for(int i=0;i<illRqstmst.size();i++){
                    int requestno = illRqstmst.get(i).getIllRequestNo();
                    illRqstmstFacadeREST.removebyrequestno(requestno);
                }
            }
            output="Member : "+memcode+" Reset Successfuly";
        }else{
            output="Member not exist";
        }
        sd = new StringData();
        sd.setMessage(output);
        return sd;
    }

    @GET
    @Path("GetCalculatedChargeForLeavyCharges/{accNo}")
    @Produces("text/plain")
    public String GetCalculatedChargeForLeavyCharges(@PathParam("accNo") String accNo) {
        boolean fineFlag = false;
        String output="";
        MMember member=null; 
        Location location;  
        TIssue issue;
         CtgryMediaIssueReserve privilege;
        BigDecimal dueCharge = BigDecimal.ZERO;
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            issue = tIssueFacadeREST.findBy("findByAccNo", accNo).get(0);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            output = "Item with " + accNo + " is not issued.";
            return output;
        }

        dueCharge = BigDecimal.ZERO;
        issue = tIssueFacadeREST.findBy("findByAccNo", accNo).get(0);
        member = issue.getMMember();

        // if(issue.getDueDt().before(new Date()))
        System.out.println("issue.getDueDt()  " + issue.getDueDt());
        if (issue.getDueDt().before(new Date())) {
            fineFlag = true;
            location = locationFacadeREST.findBy("findByP852", accNo).get(0);
            privilege = ctgryMediaIssueReserveFacadeREST.findBy("findByCtgryCdAndMediaCd", member.getMemCtgry().getCtgryCd() + "," + location.getMaterial().getCode()).get(0);

            //3. Calulation of Due charge
            long dateDiff = new Date().getTime() - issue.getDueDt().getTime();
            long days = dateDiff / (24 * 60 * 60 * 1000);

            long phase1period = privilege.getFinePhase1Prd();
            long phase2period = privilege.getFinePhase2Prd();
            BigDecimal phase1charge = privilege.getFinePhase1Charge();
            BigDecimal phase2charge = privilege.getFinePhase2Charge();

            BigDecimal defaultCharge = privilege.getFineCharges();

            System.out.println("P1P: " + phase1period + "   P2P: " + phase2period);
            System.out.println("P1C: " + phase1charge + "   P2C: " + phase2charge);
            System.out.println("DC: " + defaultCharge);
            dueCharge = phase1charge.multiply(new BigDecimal(Math.min(phase1period, Math.max(0, days))));
            dueCharge = dueCharge.add(phase2charge.multiply(new BigDecimal(Math.min(phase2period, Math.max(0, days - phase1period)))));
            dueCharge = dueCharge.add(defaultCharge.multiply(new BigDecimal(Math.max(0, days - phase1period - phase2period))));
        }
        jsonArrayBuilder.add(Json.createObjectBuilder()
                .add("accNo", accNo)
                .add("issuedDate", dateFormat.format(issue.getIssDt()).toString())
                .add("dueDate", dateFormat.format(issue.getDueDt()).toString())
                .add("fineAmount", dueCharge));

        System.out.println("fineAmount: " + dueCharge);
        return dueCharge.toString();
    }

    @GET
    @Path("noDueCertificateTag/{memCds}")
    @Produces({"application/xml", "application/json"})
    public List<Object> noDueCertificateTag(@PathParam("memCds") String memCds) {
        String[] memberIds = memCds.split(",");
        List<TMemdue> memberDue = null;
        String output="";
        MMember member = null;
        List<TIssue> issue = null;
        String issueCount = null;
        String dueAmount = "0";
        List<IllRqstmst> illRqstmst = null;
        List<TBookbankissuereturn> tBookbankissuereturn = null;
        List<MGroup> mGroup = null;
        List<Object> result = new ArrayList<>();

        StringJoiner success = new StringJoiner(",");
        StringJoiner failed = new StringJoiner(",");
        StringJoiner invalid = new StringJoiner(",");

        for (String memberId : memberIds) {
            int oldCount = Integer.parseInt(countREST());
            System.out.println("Old Count: " + oldCount);
            if (CheckMemberAvalibility(memberId)) {
                try {
                    member = find(memberId);
                } catch (NullPointerException e) {
                    invalid.add(memberId);
                    output = "Invalid member code.";
                }

                try {
                    memberDue = tMemdueFacadeREST.findBy("findByMemCd", memberId);
                    for (int i = 0; i < memberDue.size(); i++) {
                        if (memberDue.get(i).getDueAmt().toString().contentEquals("null")) {
                            continue;
                        } else {
                            dueAmount = dueAmount + memberDue.get(i).getDueAmt().toString();
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException | NullPointerException d) {
                    output = "Invalid member code " + memberId;
                }

                try {
                    issue = tIssueFacadeREST.findBy("findByMemCd", memberId);
                    issueCount = tIssueFacadeREST.countBy("countByMemCd", memberId);
                } catch (NullPointerException e) {
                    output = "Invalid member code.";
                }

                try {
                    mGroup = mGroupFacadeREST.findBy("findByMemCd", memberId);
                } catch (NullPointerException e) {
                    output = "Invalid member code.";
                }

                try {
                    tBookbankissuereturn = tBookbankissuereturnFacadeREST.findBy("findByMemCd", memberId);
                } catch (NullPointerException e) {
                    output = "Invalid member code.";
                }

                try {
                    illRqstmst = illRqstmstFacadeREST.findBy("findByMemCd", memberId);
                } catch (NullPointerException e) {
                    output = "Invalid member code";
                }

                if (issue.size() == 0 && illRqstmst.size() == 0 && tBookbankissuereturn.size() == 0
                        && memberDue.size() == 0 && mGroup.size() == 0) {
                    member.setNoDueTag("Y");
                    edit(member);
                    success.add(memberId);

                    result.add(noDueCertificate(memberId));

                    try {
                        //openReport();
                    } catch (Exception ex) {
                        Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
//                    System.out.println("MMember entry: " + member);
//                    System.out.println("Member Due: " + memberDue);
//                    System.out.println("Due Amount: " + dueAmount);
//                    System.out.println("Issue: " + issue);
//                    System.out.println("Issue count: " + issueCount);
//                    System.out.println("Rqstmst record: " + illRqstmst);
//                    System.out.println("Bookbankissuereturn count: " + tBookbankissuereturn);
//                    System.out.println("MGroup record: " + mGroup);
//                    System.out.println("\nNo due certificate generated for members: " + success
//                            + "\nNo due certificate fail to generate for members: " + failed
//                            + "\nInvalid Ids: " + invalid
//                            + "\nPossible Reasons: \n\tPending issues: " + issue.size() + "\n\tPending ILL issues: " + illRqstmst.size()
//                            + "\n\tPending Group issues: " + mGroup.size() + "\n\tPending Bookbank issues:" + tBookbankissuereturn.size() + "\n\tPending Due: " + dueAmount);
                    failed.add(memberId);
                }
            } else {
                invalid.add(memberId);
            }
        }
//        output = "\nNo due certificate generated for members: " + success
//                + "\nNo due certificate fail to generate for members: " + failed
//                + "\nInvalid Ids: " + invalid
//                + "\n\nPossible Reasons: \n\tPending issues: " + issue.size() + "\n\tPending ILL issues:" + illRqstmst.size()
//                + "\n\tPending Group issues:" + mGroup.size() + "\n\tPending boolbank issues:" + tBookbankissuereturn.size() + "\n\tPending Due:" + dueAmount;
        output = "\nNo due certificate generated for members: " + success
                + "\nNo due certificate fail to generate for members: " + failed
                + "\nInvalid Ids: " + invalid;
//        return output;
        System.out.println("\nOutput: " + output);
        return result;
    }

    @GET
    @Path("noDueCertificate/{memCd}")
    @Produces({"application/xml", "application/json"})
    public List<Object> noDueCertificate(@PathParam("memCd") String memCd) {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;
        q = " SELECT     m_member.mem_cd, m_member.mem_tag, m_member.mem_firstnm, m_member.mem_midnm, "
                + "m_member.mem_lstnm, m_member.mem_prmntadd1,\n"
                + " m_member.mem_prmntadd2, m_member.mem_prmntcity, m_member.mem_prmntpin, m_member.mem_prmntphone, "
                + "m_member.mem_email,letter_formats.letter_format, \n"
                + " letter_formats.subject, letter_formats.letter_fullname  \n"
                + " FROM letter_formats, m_member \n"
                + " where letter_formats.letter_name = 'NoDue' and m_member.mem_cd = '" + memCd + "'";

        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }

//    public void openReport() throws Exception {
//        try {
//            System.out.println("DA");
//            Connection con = null;
//            Class.forName("com.mysql.jdbc.Driver");
//            System.out.println("DA3");
////            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
////            boolean headless_check = ge.isHeadless();
////            System.out.println("headless:"+headless_check);
//            Map<String, Object> params = new HashMap<>();
//            con = DriverManager.getConnection("jdbc:mysql://172.16.16.66:3306/soul?zeroDateTimeBehavior=convertToNull", "root", "");
//            String report = "F:\\SRA29012018\\SoulRestApp\\web\\Reports\\testing4.jrxml";
//            
//            JasperReport jr = JasperCompileManager.compileReport(report);
//            JasperPrint jp = JasperFillManager.fillReport(jr, params, con);
//            JasperExportManager.exportReportToPdfFile(jp,"test");
//            //JasperPrintManager.printReport(jp,false); 
//            //JasperViewer.viewReport(jp);
//        } catch (JRException j) {
//            System.out.println("D:"+j);
//            System.out.println("V:"+j.getMessage());
//        }
//    }    
    @POST
    @Path("displayMembersFromExcelFile")
    @Consumes({"multipart/form-data"})
    @Produces({"application/xml", "application/json"})
    public JsonObject displayMembersFromExcelFile(
            @FormDataParam("fileLocation") InputStream fileLocation) throws FileNotFoundException, IOException {

        ArrayList<String> list_mem_ctgry = new ArrayList<>();
        ArrayList<String> list_mem_degree = new ArrayList<>();
        ArrayList<String> list_mem_inst = new ArrayList<>();
        ArrayList<String> list_mem_dept = new ArrayList<>();
        ArrayList<String> list_mem_firstnm = new ArrayList<>();
        ArrayList<String> list_mem_lastnm = new ArrayList<>();
        ArrayList<String> list_mem_status = new ArrayList<>();
        ArrayList<String> list_mem_year = new ArrayList<>();
        ArrayList<String> list_mem_prmntadd1 = new ArrayList<>();
        ArrayList<String> list_mem_prmntcity = new ArrayList<>();
        ArrayList<String> list_mem_prmntpin = new ArrayList<>();
        ArrayList<String> list_mem_prmntphone = new ArrayList<>();
        ArrayList<String> list_mem_tmpadd1 = new ArrayList<>();
        ArrayList<String> list_mem_tmpcity = new ArrayList<>();
        ArrayList<String> list_mem_tmppin = new ArrayList<>();
        ArrayList<String> list_mem_tmpphone = new ArrayList<>();
        ArrayList<String> list_mem_email = new ArrayList<>();
        ArrayList<String> list_mem_efctv_dt = new ArrayList<>();
        ArrayList<String> list_mem_effctupto = new ArrayList<>();
        ArrayList<String> list_user_cd = new ArrayList<>();
        ArrayList<String> list_mem_id = new ArrayList<>();
        ArrayList<String> list_remarks = new ArrayList<>();
        ArrayList<String> list_date_of_birth = new ArrayList<>();
        ArrayList<String> list_no_due_tag = new ArrayList<>();
        ArrayList<String> list_card_issude = new ArrayList<>();
        ArrayList<String> list_grentr_firstnm = new ArrayList<>();
        ArrayList<String> list_grentr_lastnm = new ArrayList<>();
        ArrayList<String> list_grentr_add1 = new ArrayList<>();
        ArrayList<String> list_confirm_password = new ArrayList<>();
        ArrayList<String> list_password = new ArrayList<>();
        ArrayList<String> list_grentr_city = new ArrayList<>();
        ArrayList<String> list_library_member = new ArrayList<>();
        ArrayList<String> list_mem_type = new ArrayList<>();
        ArrayList<String> list_grentr_pin = new ArrayList<>();
        ArrayList<String> list_mem_gender = new ArrayList<>();
        ArrayList<String> list_grentr_form_no = new ArrayList<>();
        ArrayList<String> list_grentr_phone = new ArrayList<>();
        ArrayList<String> list_grentr_mem_id = new ArrayList<>();
        ArrayList<String> list_grentr_email = new ArrayList<>();
        ArrayList<String> list_mem_entrydt = new ArrayList<>();
        ArrayList<String> list_mem_code = new ArrayList<>();
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        JsonObjectBuilder jsonBuilderFinal = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        System.out.println("loac ..  " + fileLocation);
        try {
            Workbook wb = WorkbookFactory.create(fileLocation);
            Sheet sheet = wb.getSheetAt(0);

            Row row;
            System.out.println("1" + sheet.getRow(1).getCell(0));
            System.out.println("2" + sheet.getRow(1).getCell(1));
            System.out.println("3" + sheet.getRow(1).getCell(2));
            System.out.println("3" + sheet.getRow(1).getCell(3));
            System.out.println("14" + sheet.getRow(1).getCell(4));
            System.out.println("25" + sheet.getRow(1).getCell(5));
            System.out.println("36" + sheet.getRow(1).getCell(6));
            System.out.println("37" + sheet.getRow(1).getCell(7));
            System.out.println("18" + sheet.getRow(1).getCell(8));
            System.out.println("29" + sheet.getRow(1).getCell(9));
            System.out.println("30" + sheet.getRow(1).getCell(10));
            System.out.println("31" + sheet.getRow(1).getCell(11));
            System.out.println("12" + sheet.getRow(1).getCell(12));
            System.out.println("23" + sheet.getRow(1).getCell(13));
            System.out.println("34" + sheet.getRow(1).getCell(14));
            System.out.println("35" + sheet.getRow(1).getCell(15));
            System.out.println("29" + sheet.getRow(1).getCell(17));
            System.out.println("30" + sheet.getRow(1).getCell(18));
            System.out.println("31" + sheet.getRow(1).getCell(19));
            System.out.println("12" + sheet.getRow(1).getCell(20));
            System.out.println("23" + sheet.getRow(1).getCell(21));


            System.out.println("3" + sheet.getLastRowNum());
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                String mem_ctgry;
                row = sheet.getRow(i);
                System.out.println("i  " + i + "  " + sheet.getLastRowNum());
                if (row.getCell(0) == null || row.getCell(0).toString().length() == 0 || row.getCell(0).toString().contentEquals("null")) {
                    System.out.println("iffffffffff");
                    // return "Member category should not be empty.";
                    list_mem_ctgry.add("");
                    jsonBuilder.add("category", "");
                } else {
                    System.out.println("elseeeeeeeeee");
                    // mem_ctgry = getMappingValue(categories_list, categories_mapping_list, row.getCell(0).toString());
                    // List<MCtgry> ctgry = mCtgryFacadeREST.findBy("findByCtgryDesc", mem_ctgry);
                    list_mem_ctgry.add(row.getCell(0).toString());
                    jsonBuilder.add("category", row.getCell(0).toString());
                }
                String mem_degree;
                if (row.getCell(1) == null || row.getCell(1).toString().length() == 0 || row.getCell(1).toString().contentEquals("null")) {
                    list_mem_degree.add("");
                    jsonBuilder.add("courseDesignation", "");
                    // return "Member degree should not be empty.";
                } else {
                    list_mem_degree.add(row.getCell(1).toString());
                    jsonBuilder.add("courseDesignation", row.getCell(1).toString());
                }
                String mem_inst;
                if (row.getCell(2) == null || row.getCell(2).toString().length() == 0 || row.getCell(2).toString().contentEquals("null")) {
                    list_mem_inst.add("");
                    jsonBuilder.add("institute", "");
                    //     return "Member institute should not be empty.";
                } else {
                    list_mem_inst.add(row.getCell(2).toString());
                    jsonBuilder.add("institute", row.getCell(2).toString());
                }
                String mem_dept;
                if (row.getCell(3) == null || row.getCell(3).toString().length() == 0 || row.getCell(3).toString().contentEquals("null")) {
                    list_mem_dept.add("");
                    jsonBuilder.add("department", "");
                    //  return "Member department should not be empty.";
                } else {
                    list_mem_dept.add(row.getCell(3).toString());
                    jsonBuilder.add("department", row.getCell(3).toString());
                }
                String mem_firstnm;
                if (row.getCell(4) == null || row.getCell(4).toString().length() == 0 || row.getCell(4).toString().contentEquals("null")) {
                    // return "Member firstname is mandaroty.";
                    list_mem_firstnm.add("");
                    jsonBuilder.add("memFirstName", "");
                } else {
                    mem_firstnm = row.getCell(4).toString();
                    list_mem_firstnm.add(mem_firstnm);
                    jsonBuilder.add("memFirstName", mem_firstnm);
                }
                String mem_lastnm;
                if (row.getCell(5) == null || row.getCell(5).toString().length() == 0 || row.getCell(5).toString().contentEquals("null")) {
                    list_mem_lastnm.add("");
                    jsonBuilder.add("memLastName", "");
                } else {
                    mem_lastnm = row.getCell(5).toString();
                    list_mem_lastnm.add(mem_lastnm);
                    jsonBuilder.add("memLastName", mem_lastnm);
                }
                String mem_prmntadd1;
                if (row.getCell(6) == null || row.getCell(6).toString().length() == 0 || row.getCell(6).toString().contentEquals("null")) {
                    list_mem_prmntadd1.add("");
                    jsonBuilder.add("permanentAddress", "");
                } else {
                    mem_prmntadd1 = row.getCell(6).toString();
                    jsonBuilder.add("permanentAddress", mem_prmntadd1);

                    list_mem_prmntadd1.add(mem_prmntadd1);
                }
                String mem_prmntcity;
                if (row.getCell(7) == null || row.getCell(7).toString().length() == 0 || row.getCell(7).toString().contentEquals("null")) {
                    list_mem_prmntcity.add("");
                    jsonBuilder.add("permanentCity", "");
                } else {
                    mem_prmntcity = row.getCell(7).toString();
                    list_mem_prmntcity.add(mem_prmntcity);
                    jsonBuilder.add("permanentCity", mem_prmntcity);
                }

                String mem_prmntpin;
                if (row.getCell(8) == null || row.getCell(8).toString().length() == 0 || row.getCell(8).toString().contentEquals("null")) {
                    list_mem_prmntpin.add("");
                    jsonBuilder.add("permanentPincode", "");
                } else {
                    mem_prmntpin = row.getCell(8).toString();
                    list_mem_prmntpin.add(mem_prmntpin);
                    jsonBuilder.add("permanentPincode", mem_prmntpin);
                }
                String mem_tmpadd1;
                if (row.getCell(9) == null || row.getCell(9).toString().length() == 0 || row.getCell(9).toString().contentEquals("null")) {
                    list_mem_tmpadd1.add("");
                    jsonBuilder.add("tempAddress", "");
                } else {
                    mem_tmpadd1 = row.getCell(9).toString();

                    list_mem_tmpadd1.add(mem_tmpadd1);
                    jsonBuilder.add("tempAddress", mem_tmpadd1);
                }
                String mem_tmpcity;
                if (row.getCell(10) == null || row.getCell(10).toString().length() == 0 || row.getCell(10).toString().contentEquals("null")) {
                    list_mem_tmpcity.add("");
                    jsonBuilder.add("tempCity", "");
                } else {
                    mem_tmpcity = row.getCell(10).toString();
                    list_mem_tmpcity.add(mem_tmpcity);
                    jsonBuilder.add("tempCity", mem_tmpcity);
                }

                String mem_tmppin;
                if (row.getCell(11) == null || row.getCell(11).toString().length() == 0 || row.getCell(11).toString().contentEquals("null")) {
                    list_mem_tmppin.add("");
                    jsonBuilder.add("tempPincode", "");
                } else {
                    mem_tmppin = row.getCell(11).toString();
                    list_mem_tmppin.add(mem_tmppin);
                    jsonBuilder.add("tempPincode", mem_tmppin);
                }

                String mem_prmntphone;
                if (row.getCell(12) == null || row.getCell(12).toString().length() == 0 || row.getCell(12).toString().contentEquals("null")) {
                    list_mem_prmntphone.add("");
                    jsonBuilder.add("phone", "");
                } else {
                    mem_prmntphone = row.getCell(12).toString();
                    list_mem_prmntphone.add(mem_prmntphone);
                    jsonBuilder.add("phone", mem_prmntphone);
                }
                String mem_email;
                if (row.getCell(13) == null || row.getCell(13).toString().length() == 0 || row.getCell(13).toString().contentEquals("null")) {
                    list_mem_email.add("");
                    jsonBuilder.add("email", "");
                } else {
                    mem_email = row.getCell(13).toString();
                    list_mem_email.add(mem_email);
                    jsonBuilder.add("email", mem_email);
                }

                String mem_type;
                if (row.getCell(14) == null || row.getCell(14).toString().length() == 0 || row.getCell(14).toString().contentEquals("null")) {
                    list_mem_type.add("");
                    jsonBuilder.add("memberType", "");
                } else {
                    mem_type = row.getCell(14).toString();
                    list_mem_type.add(mem_type);
                    jsonBuilder.add("memberType", mem_type);
                }
                String remarks;
                if (row.getCell(15) == null || row.getCell(15).toString().length() == 0 || row.getCell(15).toString().contentEquals("null")) {
                    list_remarks.add("");
                    jsonBuilder.add("remark", "");
                } else {
                    System.out.println("...row.getCell(15)  .. " + row.getCell(15));
                    remarks = row.getCell(15).toString();
                    list_remarks.add(remarks);
                    jsonBuilder.add("remark", remarks);
                }

                String mem_id;
                if (row.getCell(16) == null || row.getCell(16).toString().length() == 0 || row.getCell(16).toString().contentEquals("null")) {
                    list_mem_id.add("");
                    jsonBuilder.add("memId", "");
                } else {
                    System.out.println("...row.getCell(16)  .. " + row.getCell(16));
                    mem_id = row.getCell(16).toString();
                    mem_id = mem_id.substring(0, (mem_id.length() - 2));
                    System.out.println("mem_id  " + mem_id);
                    list_mem_id.add(mem_id);
                    jsonBuilder.add("memId", mem_id);
                }
                String mem_efctv_dt;
                if (row.getCell(17) == null || row.getCell(17).toString().length() == 0 || row.getCell(17).toString().contentEquals("null")) {
                    list_mem_efctv_dt.add("");
                    jsonBuilder.add("validFrom", "");
                } else {
                    mem_efctv_dt = row.getCell(17).toString();
                    list_mem_efctv_dt.add(mem_efctv_dt);
                    jsonBuilder.add("validFrom", mem_efctv_dt);
                }
                String mem_effctupto;
                if (row.getCell(18) == null || row.getCell(18).toString().length() == 0 || row.getCell(18).toString().contentEquals("null")) {
                    list_mem_effctupto.add("");
                    jsonBuilder.add("validTo", "");
                } else {
                    mem_effctupto = row.getCell(18).toString();
                    list_mem_effctupto.add(mem_effctupto);
                    jsonBuilder.add("validTo", mem_effctupto);
                }
                String mem_entry_dt;
                if (row.getCell(19) == null || row.getCell(19).toString().length() == 0 || row.getCell(19).toString().contentEquals("null")) {
                    jsonBuilder.add("entryDate", "");
                } else {
                    mem_entry_dt = row.getCell(19).toString();
                    list_mem_entrydt.add(mem_entry_dt);
                    jsonBuilder.add("entryDate", mem_entry_dt);
                }
                String mem_gender;
                if (row.getCell(20) == null || row.getCell(20).toString().length() == 0 || row.getCell(20).toString().contentEquals("null")) {
                    list_mem_gender.add("");
                    jsonBuilder.add("gender", "");
                } else {
                    mem_gender = row.getCell(20).toString();
                    list_mem_gender.add(mem_gender);
                    jsonBuilder.add("gender", mem_gender);
                }
                String mem_year;
                if (row.getCell(21) == null || row.getCell(21).toString().length() == 0 || row.getCell(21).toString().contentEquals("null")) {
                    list_mem_year.add("");
                    jsonBuilder.add("memYear", "");
                } else {
                    mem_year = row.getCell(21).toString();
                    list_mem_year.add(mem_year);
                    jsonBuilder.add("memYear", mem_year);
                }
                String dob;
                if (row.getCell(22) == null || row.getCell(22).toString().length() == 0 || row.getCell(22).toString().contentEquals("null")) {
                    list_date_of_birth.add("");
                    jsonBuilder.add("dob", "");
                } else {
                    dob = row.getCell(22).toString();
                    list_date_of_birth.add(dob);
                    jsonBuilder.add("dob", dob);
                }

                String mem_code;
                if (row.getCell(23) == null || row.getCell(23).toString().length() == 0 || row.getCell(23).toString().contentEquals("null")) {
                    list_mem_code.add("");
                    jsonBuilder.add("memCd", "");
                } else {
                    mem_code = row.getCell(23).toString();
                    list_mem_code.add(mem_code);
                    jsonBuilder.add("memCd", mem_code);
                }
                arrayBuilder.add(jsonBuilder.build());
                //  jsonBuilder.build();
                //  System.out.println("jsonBuilder  "+jsonBuilder);
//arrayBuilder.add(jsonBuilder);
// System.out.println("arrayBuilder  "+arrayBuilder);
            }
            jsonBuilderFinal.add("member", arrayBuilder.build());
            //    jsonBuilderFinal.add("member",jsonBuilder);
            // jsonBuilderFinal.build();
        } catch (Exception e) {
        }
        return jsonBuilderFinal.build();

// return   ""+StringUtils.join(list_mem_ctgry, ',')+"~"+StringUtils.join(list_mem_degree, ',') +"~"+StringUtils.join(list_mem_inst, ',')+"~"+
//                            StringUtils.join(list_mem_dept, ',')+"~"+StringUtils.join(list_mem_firstnm, ',')+"~"+
//                            StringUtils.join(list_mem_lastnm, ',')+"~"+ StringUtils.join(list_mem_prmntadd1,',')+"~"+StringUtils.join(list_mem_prmntcity, ',')+"~"+
//                            StringUtils.join(list_mem_prmntpin, ',')+"~"+
//                            StringUtils.join(list_mem_tmpadd1, ',')+"~"+StringUtils.join(list_mem_tmpcity, ',')+"~"+
//                            StringUtils.join(list_mem_tmppin, ',')+"~"+StringUtils.join(list_mem_prmntphone, ',')+"~"+
//							StringUtils.join(list_mem_email, ',')+"~"+StringUtils.join(list_mem_type, ',')+"~"+
//							StringUtils.join(list_remarks, ',')+"~"+StringUtils.join(list_mem_id, ',')+"~"+
//							StringUtils.join(list_mem_efctv_dt, ',')+"~"+StringUtils.join(list_mem_effctupto, ',')+"~"+
//							StringUtils.join(list_mem_entrydt, ',')+"~"+StringUtils.join(list_mem_gender, ',')+"~"+
//							StringUtils.join(list_mem_year, ',')+"~"+StringUtils.join(list_date_of_birth, ',')+"~"+
//                            StringUtils.join(list_mem_code, ',');       

    }
    private static final String FILE_UPLOAD_PATH = "D:\\Uploadedst";

    @POST
    @Path("importMembers")
    //@Produces({"application/xml", "application/json", "text/plain"})
    @Consumes({"multipart/form-data"})
    public String importMembers(
            @FormDataParam("categories") String categories, @FormDataParam("category_mapping") String category_mapping,
            @FormDataParam("institutes") String institutes, @FormDataParam("institute_mapping") String institute_mapping,
            @FormDataParam("departments") String departments, @FormDataParam("department_mapping") String department_mapping,
            @FormDataParam("degrees") String degrees, @FormDataParam("degree_mapping") String degree_mapping,
            @FormDataParam("fileLocation") InputStream fileLocation,
            @FormDataParam("imageFile") List<FormDataBodyPart> bodyParts) throws FileNotFoundException, IOException {

        // memPhotoFacadeREST.uploadFolder( imageFile);
        String newMember = null;
         String output="";   
        ArrayList<String> list_mem_ctgry = new ArrayList<>();
        ArrayList<String> list_mem_degree = new ArrayList<>();
        ArrayList<String> list_mem_inst = new ArrayList<>();
        ArrayList<String> list_mem_dept = new ArrayList<>();
        ArrayList<String> list_mem_firstnm = new ArrayList<>();
        ArrayList<String> list_mem_lastnm = new ArrayList<>();
        ArrayList<String> list_mem_status = new ArrayList<>();
        ArrayList<String> list_mem_year = new ArrayList<>();
        ArrayList<String> list_mem_prmntadd1 = new ArrayList<>();
        ArrayList<String> list_mem_prmntcity = new ArrayList<>();
        ArrayList<String> list_mem_prmntpin = new ArrayList<>();
        ArrayList<String> list_mem_prmntphone = new ArrayList<>();
        ArrayList<String> list_mem_tmpadd1 = new ArrayList<>();
        ArrayList<String> list_mem_tmpcity = new ArrayList<>();
        ArrayList<String> list_mem_tmppin = new ArrayList<>();
        ArrayList<String> list_mem_tmpphone = new ArrayList<>();
        ArrayList<String> list_mem_email = new ArrayList<>();
        ArrayList<String> list_mem_efctv_dt = new ArrayList<>();
        ArrayList<String> list_mem_effctupto = new ArrayList<>();
        ArrayList<String> list_user_cd = new ArrayList<>();
        ArrayList<String> list_mem_id = new ArrayList<>();
        ArrayList<String> list_remarks = new ArrayList<>();
        ArrayList<String> list_date_of_birth = new ArrayList<>();
        ArrayList<String> list_no_due_tag = new ArrayList<>();
        ArrayList<String> list_card_issude = new ArrayList<>();
        ArrayList<String> list_grentr_firstnm = new ArrayList<>();
        ArrayList<String> list_grentr_lastnm = new ArrayList<>();
        ArrayList<String> list_grentr_add1 = new ArrayList<>();
        ArrayList<String> list_confirm_password = new ArrayList<>();
        ArrayList<String> list_password = new ArrayList<>();
        ArrayList<String> list_grentr_city = new ArrayList<>();
        ArrayList<String> list_library_member = new ArrayList<>();
        ArrayList<String> list_mem_type = new ArrayList<>();
        ArrayList<String> list_grentr_pin = new ArrayList<>();
        ArrayList<String> list_mem_gender = new ArrayList<>();
        ArrayList<String> list_grentr_form_no = new ArrayList<>();
        ArrayList<String> list_grentr_phone = new ArrayList<>();
        ArrayList<String> list_grentr_mem_id = new ArrayList<>();
        ArrayList<String> list_grentr_email = new ArrayList<>();
        ArrayList<String> list_mem_entrydt = new ArrayList<>();
        HashMap<String, InputStream> memPhotoMap = new HashMap<>();
        HashMap<String, String> memPhotoNameMap = new HashMap<>();
        try {
            // to get member photo from folder
            for (int i = 0; i < bodyParts.size(); i++) {
                BodyPartEntity bodyPartEntity = (BodyPartEntity) bodyParts.get(i).getEntity();
                String fileName = bodyParts.get(i).getContentDisposition().getFileName();

                String basename = FilenameUtils.getBaseName(fileName);
                String extension = FilenameUtils.getExtension(fileName);
                System.out.println(basename); // file
                System.out.println(extension); // txt (NOT ".txt" !)
                //memPhotoFacadeREST.saveUpdatedFileToDb(bodyPartEntity.getInputStream(), fileName);
                if (bodyParts.get(i).getMediaType().toString().startsWith("image/")) {
                    memPhotoMap.put(basename, bodyPartEntity.getInputStream());
                    memPhotoNameMap.put(basename, fileName);
                }
                //fileDetails.append(" File saved at /Volumes/Drive2/temp/file/" + fileName);
            }


            Workbook wb = WorkbookFactory.create(fileLocation);
            String[] categories_array = categories.split(",");
            String[] categories_mapping_array = category_mapping.split(",");
            String[] institute_array = institutes.split(",");
            String[] institute_mapping_array = institute_mapping.split(",");
            String[] department_array = departments.split(",");
            String[] department_mapping_array = department_mapping.split(",");
            String[] degree_array = degrees.split(",");
            String[] degree_mapping_array = degree_mapping.split(",");

            ArrayList<String> categories_list = new ArrayList<String>();
            ArrayList<String> categories_mapping_list = new ArrayList<String>();
            ArrayList<String> institute_list = new ArrayList<String>();
            ArrayList<String> institute_mapping_list = new ArrayList<String>();
            ArrayList<String> department_list = new ArrayList<String>();
            ArrayList<String> department_mapping_list = new ArrayList<String>();
            ArrayList<String> degree_list = new ArrayList<String>();
            ArrayList<String> degree_mapping_list = new ArrayList<String>();

            for (int i = 0; i < categories_array.length; i++) {
                categories_list.add(categories_array[i].trim());
                categories_mapping_list.add(categories_mapping_array[i].trim());
            }
            for (int i = 0; i < institute_array.length; i++) {
                institute_list.add(institute_array[i].trim());
                institute_mapping_list.add(institute_mapping_array[i].trim());
            }
            for (int i = 0; i < department_array.length; i++) {
                department_list.add(department_array[i].trim());
                department_mapping_list.add(department_mapping_array[i].trim());
            }
            for (int i = 0; i < degree_array.length; i++) {
                degree_list.add(degree_array[i].trim());
                degree_mapping_list.add(degree_mapping_array[i].trim());
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Sheet sheet = wb.getSheetAt(0);

            Row row;
            System.out.println("1" + sheet.getRow(1).getCell(0));
            System.out.println("2" + sheet.getRow(1).getCell(1));
            System.out.println("3" + sheet.getRow(1).getCell(2));
            System.out.println("3" + sheet.getRow(1).getCell(3));
            int count = 0;
            int flag = 0;
            //System.out.println("3" + sheet.getRow(0));
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                String mem_ctgry;
                count++;
                if (flag == 1) {
                    return "members successfully imported.";
                } else {
                }
                try {
                    if (row.getCell(0) == null || row.getCell(0).toString().length() == 0 || row.getCell(0).toString().contentEquals("null")) {
                        flag = 1;
                        return "Member category should not be empty.";
                    } else {
                        mem_ctgry = getMappingValue(categories_list, categories_mapping_list, row.getCell(0).toString());
                        List<MCtgry> ctgry = mCtgryFacadeREST.findBy("findByCtgryDesc", mem_ctgry);
                        list_mem_ctgry.add(ctgry.get(0).getCtgryCd());

                    }
                    String mem_degree;
                    if (row.getCell(1) == null || row.getCell(1).toString().length() == 0 || row.getCell(1).toString().contentEquals("null")) {
                        return "Member degree should not be empty.";
                    } else {
                        mem_degree = getMappingValue(degree_list, degree_mapping_list, row.getCell(1).toString());
                        List<MBranchmaster> deg = mBranchmasterFacadeREST.findBy("findByBranchname", mem_degree);
                        list_mem_degree.add(deg.get(0).getBranchCd().toString());
                    }
                    String mem_inst;
                    if (row.getCell(2) == null || row.getCell(2).toString().length() == 0 || row.getCell(2).toString().contentEquals("null")) {
                        return "Member institute should not be empty.";
                    } else {
                        mem_inst = getMappingValue(institute_list, institute_mapping_list, row.getCell(2).toString());
                        List<MFcltydept> ins = mFcltydeptFacadeREST.findBy("findByInstituteName", mem_inst);
                        list_mem_inst.add(ins.get(0).getFcltydeptcd().toString());
                    }
                    String mem_dept;
                    if (row.getCell(3) == null || row.getCell(3).toString().length() == 0 || row.getCell(3).toString().contentEquals("null")) {
                        return "Member department should not be empty.";
                    } else {
                        mem_dept = getMappingValue(department_list, department_mapping_list, row.getCell(3).toString());
                        List<MFcltydept> depts = mFcltydeptFacadeREST.findBy("findByDepartmentName", mem_dept);
                        list_mem_dept.add(depts.get(0).getFcltydeptcd().toString());

                    }
                    String mem_firstnm;
                    if (row.getCell(4) == null || row.getCell(4).toString().length() == 0 || row.getCell(4).toString().contentEquals("null")) {
                        return "Member firstname is mandaroty.";
                    } else {
                        mem_firstnm = row.getCell(4).toString();
                        list_mem_firstnm.add(mem_firstnm);
                    }
                    String mem_lastnm;
                    if (row.getCell(5) == null || row.getCell(5).toString().length() == 0 || row.getCell(5).toString().contentEquals("null")) {
                        return "Member lastname is mandaroty.";
                    } else {
                        mem_lastnm = row.getCell(5).toString();
                        list_mem_lastnm.add(mem_lastnm);
                    }
                    String mem_prmntadd1;
                    if (row.getCell(6) == null || row.getCell(6).toString().length() == 0 || row.getCell(6).toString().contentEquals("null")) {
                        mem_prmntadd1 = null;
                    } else {
                        mem_prmntadd1 = row.getCell(6).toString();
                    }
                    list_mem_prmntadd1.add(mem_prmntadd1);
                    String mem_prmntcity;
                    if (row.getCell(7) == null || row.getCell(7).toString().length() == 0 || row.getCell(7).toString().contentEquals("null")) {
                        mem_prmntcity = null;
                    } else {
                        mem_prmntcity = row.getCell(7).toString();
                    }
                    list_mem_prmntcity.add(mem_prmntcity);
                    String mem_prmntpin;
                    if (row.getCell(8) == null || row.getCell(8).toString().length() == 0 || row.getCell(8).toString().contentEquals("null")) {
                        mem_prmntpin = null;
                    } else {
                        mem_prmntpin = row.getCell(8).toString();
                    }
                    list_mem_prmntpin.add(mem_prmntpin);
                    String mem_tmpadd1;
                    if (row.getCell(9) == null || row.getCell(9).toString().length() == 0 || row.getCell(9).toString().contentEquals("null")) {
                        mem_tmpadd1 = null;
                    } else {
                        mem_tmpadd1 = row.getCell(9).toString();
                    }
                    list_mem_tmpadd1.add(mem_tmpadd1);
                    String mem_tmpcity;
                    if (row.getCell(10) == null || row.getCell(10).toString().length() == 0 || row.getCell(10).toString().contentEquals("null")) {
                        mem_tmpcity = null;
                    } else {
                        mem_tmpcity = row.getCell(10).toString();
                    }
                    list_mem_tmpcity.add(mem_tmpcity);
                    String mem_tmppin;
                    if (row.getCell(11) == null || row.getCell(11).toString().length() == 0 || row.getCell(11).toString().contentEquals("null")) {
                        mem_tmppin = null;
                    } else {
                        mem_tmppin = row.getCell(11).toString();
                    }
                    list_mem_tmppin.add(mem_tmppin);
                    String mem_prmntphone;
                    if (row.getCell(12) == null || row.getCell(12).toString().length() == 0 || row.getCell(12).toString().contentEquals("null")) {
                        return "Member phone no should not be empty.";
                    } else {
                        mem_prmntphone = row.getCell(12).toString();
                        list_mem_prmntphone.add(mem_prmntphone);
                    }
                    String mem_email;
                    if (row.getCell(13) == null || row.getCell(13).toString().length() == 0 || row.getCell(13).toString().contentEquals("null")) {
                        return "MembSystem.out.println(\"if 1111111111111111\");er emil should not be empty.";
                    } else {
                        mem_email = row.getCell(13).toString();
                    }
                    list_mem_email.add(mem_email);
                    String mem_type;
                    if (row.getCell(14) == null || row.getCell(14).toString().length() == 0 || row.getCell(14).toString().contentEquals("null")) {
                        return "Member type should not be empty.";
                    } else {
                        mem_type = row.getCell(14).toString();
                        list_mem_type.add(mem_type);
                    }
                    String remarks;
                    if (row.getCell(15) == null || row.getCell(15).toString().length() == 0 || row.getCell(15).toString().contentEquals("null")) {
                        remarks = null;
                    } else {
                        remarks = row.getCell(15).toString();
                    }
                    list_remarks.add(remarks);
                    String mem_id;
                    if (row.getCell(16) == null || row.getCell(16).toString().length() == 0 || row.getCell(16).toString().contentEquals("null")) {
                        return "Member id should not be empty.";
                    } else {
                        mem_id = row.getCell(16).toString();
                        mem_id = mem_id.substring(0, (mem_id.length() - 2));
                        list_mem_id.add(mem_id);
                    }
                    String mem_efctv_dt;
                    if (row.getCell(17) == null || row.getCell(17).toString().length() == 0 || row.getCell(17).toString().contentEquals("null")) {
                        return "Membership start date should not be empty.";
                    } else {
                        mem_efctv_dt = row.getCell(17).toString();
                        list_mem_efctv_dt.add(mem_efctv_dt);
                    }
                    String mem_effctupto;
                    if (row.getCell(18) == null || row.getCell(18).toString().length() == 0 || row.getCell(18).toString().contentEquals("null")) {
                        return "Membership end date should not be empty.";
                    } else {
                        mem_effctupto = row.getCell(18).toString();
                        list_mem_effctupto.add(mem_effctupto);
                    }
                    String mem_entry_dt;
                    if (row.getCell(19) == null || row.getCell(19).toString().length() == 0 || row.getCell(19).toString().contentEquals("null")) {
                        return "Membership end date should not be empty.";
                    } else {
                        mem_entry_dt = row.getCell(19).toString();
                        list_mem_entrydt.add(mem_entry_dt);
                    }
                    String mem_gender;
                    if (row.getCell(20) == null || row.getCell(20).toString().length() == 0 || row.getCell(20).toString().contentEquals("null")) {
                        return "Gender should not be empty.";
                    } else {
                        mem_gender = row.getCell(20).toString();
                        list_mem_gender.add(mem_gender);
                    }
                    String mem_year;
                    if (row.getCell(21) == null || row.getCell(21).toString().length() == 0 || row.getCell(21).toString().contentEquals("null")) {
                        return "Member year should not be empty.";
                    } else {
                        mem_year = row.getCell(21).toString();
                        list_mem_year.add(mem_year);
                    }

                    String date_of_birth;
                    if (row.getCell(22) == null || row.getCell(22).toString().length() == 0 || row.getCell(22).toString().contentEquals("null")) {
                        return "Member DOB should not be empty.";
                    } else {
                        date_of_birth = row.getCell(22).toString();
                        list_date_of_birth.add(date_of_birth);
                    }

                    System.out.println(list_mem_ctgry.get(i - 1));
                    System.out.println(list_mem_dept.get(i - 1));
                    System.out.println(list_mem_inst.get(i - 1));
                    System.out.println(list_mem_degree.get(i - 1));
                    System.out.println(list_mem_firstnm.get(i - 1));
                    System.out.println(list_mem_lastnm.get(i - 1));
                    System.out.println(list_mem_gender.get(i - 1));
                    System.out.println(list_date_of_birth.get(i - 1));
                    System.out.println(list_mem_type.get(i - 1));
                    System.out.println(list_mem_prmntadd1.get(i - 1));
                    System.out.println(list_mem_prmntcity.get(i - 1));
                    System.out.println(list_mem_prmntpin.get(i - 1));
                    System.out.println(list_mem_prmntphone.get(i - 1));
                    System.out.println(list_mem_tmpadd1.get(i - 1));
                    System.out.println(list_mem_tmpcity.get(i - 1));
                    System.out.println(list_mem_tmppin.get(i - 1));
                    System.out.println(list_mem_email.get(i - 1));
                    System.out.println(list_mem_efctv_dt.get(i - 1));
                    System.out.println(list_mem_effctupto.get(i - 1));
                    System.out.println(list_mem_entrydt.get(i - 1));
                    System.out.println(list_remarks.get(i - 1));


                    newMember = addMember(list_mem_ctgry.get(i - 1), list_mem_dept.get(i - 1), list_mem_inst.get(i - 1), list_mem_degree.get(i - 1), list_mem_firstnm.get(i - 1),
                            null, list_mem_lastnm.get(i - 1), list_mem_gender.get(i - 1), list_date_of_birth.get(i - 1), list_mem_id.get(i - 1),
                            list_mem_year.get(i - 1), list_mem_type.get(i - 1), list_mem_prmntadd1.get(i - 1), list_mem_prmntcity.get(i - 1), list_mem_prmntpin.get(i - 1),
                            list_mem_prmntphone.get(i - 1), list_mem_tmpadd1.get(i - 1), list_mem_tmpcity.get(i - 1), list_mem_tmppin.get(i - 1), null,
                            list_mem_email.get(i - 1), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                            list_mem_efctv_dt.get(i - 1), list_mem_effctupto.get(i - 1), list_mem_entrydt.get(i - 1),
                            null, null, null, null, list_remarks.get(i - 1));

                    // to get memcd from newly created member
                    newMember = newMember.split(":")[1].trim();
                    memPhotoFacadeREST.saveUpdatedFileToDb(memPhotoMap.get(list_mem_id.get(i - 1)), newMember, memPhotoNameMap.get(list_mem_id.get(i - 1)));

                } catch (NullPointerException e) {
                    System.out.println("errrrrrrrrrr  " + e);
                    break;
                } catch (ArrayIndexOutOfBoundsException d) {
                    System.out.println("errrrrrrrrrrr   " + d);
                    break;
                }
            }
            output = "Members imported successfully";
        } catch (IOException ioe) {
            System.out.println(ioe);
            output = "error";
        } catch (Exception ex) {
            output = "error";
        }
        //System.out.println(list_mem_type);
        return output;
    }

//    @POST
//    @Path("importMembers")
//    @Produces({"application/xml", "application/json", "text/plain"})
//    @Consumes({"application/xml", "application/json","multipart/form-data"})
//    public String importMembers(@FormDataParam("fileLocation") InputStream fileLocation, 
//     @FormDataParam("fileLocation") FormDataContentDisposition contentDispositionHeader,
//            @FormDataParam("categories") String categories, @FormDataParam("category_mapping") String category_mapping,
//            @FormDataParam("institutes") String institutes, @FormDataParam("institute_mapping") String institute_mapping ,
//            @FormDataParam("departments") String departments, @FormDataParam("department_mapping") String department_mapping ,
//            @FormDataParam("degrees") String degrees , @FormDataParam("degree_mapping") String degree_mapping) throws FileNotFoundException, IOException{
//        
//            
//        URL u = getClass().getProtectionDomain().getCodeSource().getLocation();
//        System.out.println("uuuu  ..  " + u.getPath());
//
//        File f = null;
//        try {
//            f = new File(u.toURI());
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println(f.getParentFile());
//
//      //  String fileLocation = f.getParentFile() + "\\2.txt";
//
//        ArrayList<String> list_mem_ctgry = null;
//        ArrayList<String> list_mem_degree = null;
//        ArrayList<String> list_mem_inst = null;
//        ArrayList<String> list_mem_dept = null;
//        ArrayList<String> list_mem_firstnm = null;
//        ArrayList<String> list_mem_lastnm = null;
//        ArrayList<String> list_mem_status = null;
//        ArrayList<String> list_mem_year = null;
//        ArrayList<String> list_mem_prmntadd1 = null;
//        ArrayList<String> list_mem_prmntcity = null;
//        ArrayList<String> list_mem_prmntpin = null;
//        ArrayList<String> list_mem_prmntphone = null;
//        ArrayList<String> list_mem_tmpadd1 = null;
//        ArrayList<String> list_mem_tmpcity = null;
//        ArrayList<String> list_mem_tmppin = null;
//        ArrayList<String> list_mem_tmpphone = null;
//        ArrayList<String> list_mem_email = null;
//        ArrayList<String> list_mem_efctv_dt = null;
//        ArrayList<String> list_mem_effctupto = null;
//        ArrayList<String> list_user_cd = null;
//        ArrayList<String> list_mem_id = null;
//        ArrayList<String> list_remarks = null;
//        ArrayList<String> list_date_of_birth = null;
//        ArrayList<String> list_no_due_tag = null;
//        ArrayList<String> list_card_issude = null;
//        ArrayList<String> list_grentr_firstnm = null;
//        ArrayList<String> list_grentr_lastnm = null;
//        ArrayList<String> list_grentr_add1 = null;
//        ArrayList<String> list_grentr_city = null;
//        ArrayList<String> list_grentr_pin = null;
//        ArrayList<String> list_grentr_phone = null;
//        ArrayList<String> list_grentr_email = null;
//        ArrayList<String> list_grentr_mem_id = null;
//        ArrayList<String> list_grentr_form_no = null;
//        ArrayList<String> list_mem_gender = null;
//        ArrayList<String> list_mem_type = null;
//        ArrayList<String> list_library_member = null;
//        ArrayList<String> list_password = null;
//        ArrayList<String> list_confirm_password = null;
//        try {
//           // InputStream excelFile = new FileInputStream("C:\\Users\\admin\\Desktop\\MDATA.xlsx");
//           // System.out.println("path:"+fileLocation.getPath());
//            System.out.println("fileLocation  "+fileLocation);
//            XSSFWorkbook  wb;
//            wb = new XSSFWorkbook(fileLocation);
//            XSSFSheet sheet = wb.getSheetAt(0);
//            XSSFRow row;
//            String[] categories_array = categories.split(",");
//            String[] categories_mapping_array = category_mapping.split(",");
//            String[] institute_array = categories.split(",");
//            String[] institute_mapping_array = institute_mapping.split(",");
//            String[] department_array = categories.split(",");
//            String[] department_mapping_array = department_mapping.split(",");
//            String[] degree_array = categories.split(",");
//            String[] degree_mapping_array = degree_mapping.split(",");
//            ArrayList<String> categories_list = new ArrayList<String>();
//            ArrayList<String> categories_mapping_list = new ArrayList<String>();
//            ArrayList<String> institute_list = new ArrayList<String>();
//            ArrayList<String> institute_mapping_list = new ArrayList<String>();
//            ArrayList<String> department_list = new ArrayList<String>();
//            ArrayList<String> department_mapping_list = new ArrayList<String>();
//            ArrayList<String> degree_list = new ArrayList<String>();
//            ArrayList<String> degree_mapping_list = new ArrayList<String>();
//            for(int i=0; i<categories_array.length; i++){  
//                categories_list.add(categories_array[i]);
//                categories_mapping_list.add(categories_mapping_array[i]);
//                
//            }
//            for(int i=0; i<institute_array.length; i++){  
//                institute_list.add(institute_array[i]);
//                System.out.println("institute_array[i]  "+institute_array[i]);
//                institute_mapping_list.add(institute_mapping_array[i]);
//            }
//            for(int i=0; i<department_array.length; i++){  
//                department_list.add(department_array[i]);
//                department_mapping_list.add(department_mapping_array[i]);
//            }
//            for(int i=0; i<degree_array.length; i++){  
//                degree_list.add(degree_array[i]);
//                degree_mapping_list.add(degree_mapping_array[i]);
//            }
//
//
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//                row = sheet.getRow(i);
//
//                String mem_ctgry;
//                if (row.getCell(0) == null || row.getCell(0).toString().length() == 0 || row.getCell(0).toString().contentEquals("null")) {
//                    return "Member category should not be empty.";
//                } else {
//                    mem_ctgry = getMappingValue(categories_list,categories_mapping_list,row.getCell(0).toString());
//                    list_mem_ctgry.add(mem_ctgry);
//                    System.out.println("mem_ctgry  "+mem_ctgry);
//                }
//                String mem_degree;
//                if (row.getCell(1) == null || row.getCell(1).toString().length() == 0 || row.getCell(1).toString().contentEquals("null")) {
//                    return "Member degree should not be empty.";
//                } else {
//                    mem_degree = getMappingValue(categories_list,categories_mapping_list,row.getCell(1).toString());;
//                    list_mem_degree.add(mem_degree);
//                }
//                String mem_inst;
//                if (row.getCell(2) == null || row.getCell(2).toString().length() == 0 || row.getCell(2).toString().contentEquals("null")) {
//                    return "Member institute should not be empty.";
//                } else {
//                    mem_inst = getMappingValue(categories_list,categories_mapping_list,row.getCell(2).toString());
//                    list_mem_inst.add(mem_inst);
//                }
//                String mem_dept;
//                if (row.getCell(3) == null || row.getCell(3).toString().length() == 0 || row.getCell(3).toString().contentEquals("null")) {
//                    return "Member department should not be empty.";
//                } else {
//                    mem_dept = getMappingValue(categories_list,categories_mapping_list,row.getCell(3).toString());
//                    list_mem_dept.add(mem_dept);
//                }
//                String mem_firstnm;
//                if (row.getCell(4) == null || row.getCell(4).toString().length() == 0 || row.getCell(4).toString().contentEquals("null")) {
//                    return "Member firstname is mandaroty.";
//                } else {
//                    mem_firstnm = row.getCell(4).toString();
//                    list_mem_firstnm.add(mem_firstnm);
//                }
//                String mem_lastnm;
//                if (row.getCell(6) == null || row.getCell(6).toString().length() == 0 || row.getCell(6).toString().contentEquals("null")) {
//                    return "Member lastname is mandaroty.";
//                } else {
//                    mem_lastnm = row.getCell(6).toString();
//                    list_mem_lastnm.add(mem_lastnm);
//                }
//                String mem_status;
//                if (row.getCell(7) == null || row.getCell(7).toString().length() == 0 || row.getCell(7).toString().contentEquals("null")) {
//                    mem_status = null;
//                } else {
//                    mem_status = row.getCell(7).toString();
//                }
//                list_mem_status.add(mem_status);
//                String mem_year;
//                if (row.getCell(8) == null || row.getCell(8).toString().length() == 0 || row.getCell(8).toString().contentEquals("null")) {
//                    return "Member mem_year should not be empty.";
//                } else {
//                    mem_year = row.getCell(8).toString();
//                    list_mem_year.add(mem_year);
//                }
//                String mem_prmntadd1;
//                if (row.getCell(9) == null || row.getCell(9).toString().length() == 0 || row.getCell(9).toString().contentEquals("null")) {
//                    return "Member address should not be empty.";
//                } else {
//                    mem_prmntadd1 = row.getCell(9).toString();
//                    list_mem_prmntadd1.add(mem_prmntadd1);
//                }
//                String mem_prmntcity;
//                if (row.getCell(11) == null || row.getCell(11).toString().length() == 0 || row.getCell(11).toString().contentEquals("null")) {
//                    return "Member city should not be empty.";
//                } else {
//                    mem_prmntcity = row.getCell(11).toString();
//                    list_mem_prmntcity.add(mem_prmntcity);
//                }
//                String mem_prmntpin;
//                if (row.getCell(12) == null || row.getCell(12).toString().length() == 0 || row.getCell(12).toString().contentEquals("null")) {
//                    return "Member pin should not be empty.";
//                } else {
//                    mem_prmntpin = row.getCell(12).toString();
//                    list_mem_prmntpin.add(mem_prmntpin);
//                }
//                String mem_prmntphone;
//                if (row.getCell(13) == null || row.getCell(13).toString().length() == 0 || row.getCell(13).toString().contentEquals("null")) {
//                    return "Member phone no should not be empty.";
//                } else {
//                    mem_prmntphone = row.getCell(13).toString();
//                    list_mem_prmntphone.add(mem_prmntphone);
//                }
//                String mem_tmpadd1;
//                if (row.getCell(14) == null || row.getCell(14).toString().length() == 0 || row.getCell(14).toString().contentEquals("null")) {
//                    mem_tmpadd1 = null;
//                } else {
//                    mem_tmpadd1 = row.getCell(14).toString();
//                }
//                list_mem_tmpadd1.add(mem_tmpadd1);
//                String mem_tmpcity;
//                if (row.getCell(16) == null || row.getCell(16).toString().length() == 0 || row.getCell(16).toString().contentEquals("null")) {
//                    mem_tmpcity = null;
//                } else {
//                    mem_tmpcity = row.getCell(16).toString();
//                }
//                list_mem_tmpcity.add(mem_tmpcity);
//                String mem_tmppin;
//                if (row.getCell(17) == null || row.getCell(17).toString().length() == 0 || row.getCell(17).toString().contentEquals("null")) {
//                    mem_tmppin = null;
//                } else {
//                    mem_tmppin = row.getCell(17).toString();
//                }
//                list_mem_tmppin.add(mem_tmppin);
//                String mem_tmpphone;
//                if (row.getCell(18) == null || row.getCell(18).toString().length() == 0 || row.getCell(18).toString().contentEquals("null")) {
//                    mem_tmpphone = null;
//                } else {
//                    mem_tmpphone = row.getCell(18).toString();
//                }
//                list_mem_tmpphone.add(mem_tmpphone);
//                String mem_email;
//                if (row.getCell(19) == null || row.getCell(19).toString().length() == 0 || row.getCell(19).toString().contentEquals("null")) {
//                    return "Member emil should not be empty.";
//                } else {
//                    mem_email = row.getCell(19).toString();
//                }
//                list_mem_email.add(mem_email);
//                String mem_efctv_dt;
//                if (row.getCell(20) == null || row.getCell(20).toString().length() == 0 || row.getCell(20).toString().contentEquals("null")) {
//                    return "Membership start date should not be empty.";
//                } else {
//                    mem_efctv_dt = row.getCell(20).toString();
//                    list_mem_efctv_dt.add(mem_efctv_dt);
//                }
//                String mem_effctupto;
//                if (row.getCell(22) == null || row.getCell(22).toString().length() == 0 || row.getCell(22).toString().contentEquals("null")) {
//                    return "Membership end date should not be empty.";
//                } else {
//                    mem_effctupto = row.getCell(22).toString();
//                    list_mem_effctupto.add(mem_effctupto);
//                }
//                String user_cd;
//                if (row.getCell(23) == null || row.getCell(23).toString().length() == 0 || row.getCell(23).toString().contentEquals("null")) {
//                    user_cd = null;
//                } else {
//                    user_cd = row.getCell(23).toString();
//                }
//                list_user_cd.add(user_cd);
//                String mem_id;
//                if (row.getCell(24) == null || row.getCell(24).toString().length() == 0 || row.getCell(24).toString().contentEquals("null")) {
//                    return "Member id should not be empty.";
//                } else {
//                    mem_id = row.getCell(24).toString();
//                    list_mem_id.add(mem_id);
//                }
//                String remarks;
//                if (row.getCell(25) == null || row.getCell(25).toString().length() == 0 || row.getCell(25).toString().contentEquals("null")) {
//                    remarks = null;
//                } else {
//                    remarks = row.getCell(25).toString();
//                }
//                list_remarks.add(remarks);
//                String date_of_birth;
//                if (row.getCell(26) == null || row.getCell(26).toString().length() == 0 || row.getCell(26).toString().contentEquals("null")) {
//                    return "Member dob should not be empty.";
//                } else {
//                    date_of_birth = row.getCell(26).toString();
//                    list_date_of_birth.add(date_of_birth);
//                }
//                String no_due_tag;
//                if (row.getCell(27) == null || row.getCell(27).toString().length() == 0 || row.getCell(27).toString().contentEquals("null")) {
//                    no_due_tag = null;
//                } else {
//                    no_due_tag = row.getCell(27).toString();
//                }
//                list_no_due_tag.add(no_due_tag);
//                String Card_issude;
//                if (row.getCell(28) == null || row.getCell(28).toString().length() == 0 || row.getCell(28).toString().contentEquals("null")) {
//                    Card_issude = null;
//                } else {
//                    Card_issude = row.getCell(28).toString();
//                }
//                list_card_issude.add(Card_issude);
//                String grentr_firstnm;
//                if (row.getCell(29) == null || row.getCell(29).toString().length() == 0 || row.getCell(29).toString().contentEquals("null")) {
//                    grentr_firstnm = null;
//                } else {
//                    grentr_firstnm = row.getCell(29).toString();
//                }
//                list_grentr_firstnm.add(grentr_firstnm);
//                String grentr_lastnm;
//                if (row.getCell(30) == null || row.getCell(30).toString().length() == 0 || row.getCell(30).toString().contentEquals("null")) {
//                    grentr_lastnm = null;
//                } else {
//                    grentr_lastnm = row.getCell(30).toString();
//                }
//                list_grentr_lastnm.add(grentr_lastnm);
//                String grentr_add1;
//                if (row.getCell(31) == null || row.getCell(31).toString().length() == 0 || row.getCell(31).toString().contentEquals("null")) {
//                    grentr_add1 = null;
//                } else {
//                    grentr_add1 = row.getCell(31).toString();
//                }
//                list_grentr_add1.add(grentr_add1);
//                String grentr_city;
//                if (row.getCell(32) == null || row.getCell(32).toString().length() == 0 || row.getCell(32).toString().contentEquals("null")) {
//                    grentr_city = null;
//                } else {
//                    grentr_city = row.getCell(32).toString();
//                }
//                list_grentr_city.add(grentr_city);
//                String grentr_pin;
//                if (row.getCell(33) == null || row.getCell(33).toString().length() == 0 || row.getCell(33).toString().contentEquals("null")) {
//                    grentr_pin = null;
//                } else {
//                    grentr_pin = row.getCell(33).toString();
//                }
//                list_grentr_pin.add(grentr_pin);
//                String grentr_phone;
//                if (row.getCell(34) == null || row.getCell(34).toString().length() == 0 || row.getCell(34).toString().contentEquals("null")) {
//                    grentr_phone = null;
//                } else {
//                    grentr_phone = row.getCell(34).toString();
//                }
//                list_grentr_phone.add(grentr_phone);
//                String grentr_email;
//                if (row.getCell(35) == null || row.getCell(35).toString().length() == 0 || row.getCell(35).toString().contentEquals("null")) {
//                    grentr_email = null;
//                } else {
//                    grentr_email = row.getCell(35).toString();
//                }
//                list_grentr_email.add(grentr_email);
//                String grentr_mem_id;
//                if (row.getCell(36) == null || row.getCell(36).toString().length() == 0 || row.getCell(36).toString().contentEquals("null")) {
//                    grentr_mem_id = null;
//                } else {
//                    grentr_mem_id = row.getCell(36).toString();
//                }
//                list_grentr_mem_id.add(grentr_mem_id);
//                String grentr_form_no;
//                if (row.getCell(37) == null || row.getCell(37).toString().length() == 0 || row.getCell(37).toString().contentEquals("null")) {
//                    grentr_form_no = null;
//                } else {
//                    grentr_form_no = row.getCell(37).toString();
//                }
//                list_grentr_form_no.add(grentr_form_no);
//                String mem_gender;
//                if (row.getCell(38) == null || row.getCell(38).toString().length() == 0 || row.getCell(38).toString().contentEquals("null")) {
//                    return "Gender should not be empty.";
//                } else {
//                    mem_gender = row.getCell(38).toString();
//                    list_mem_gender.add(mem_gender);
//                }
//                String mem_type;
//                if (row.getCell(39) == null || row.getCell(39).toString().length() == 0 || row.getCell(39).toString().contentEquals("null")) {
//                    return "Member type should not be empty.";
//                } else {
//                    mem_type = row.getCell(39).toString();
//                    list_mem_type.add(mem_type);
//                }
//                String library_member;
//                if (row.getCell(40) == null || row.getCell(40).toString().length() == 0 || row.getCell(40).toString().contentEquals("null")) {
//                    library_member = null; 
//                } else {
//                    library_member = row.getCell(40).toString();  
//                }
//                list_library_member.add(mem_type);
//                String password;
//                if (row.getCell(41) == null || row.getCell(41).toString().length() == 0 || row.getCell(41).toString().contentEquals("null")) {
//                    password = null; 
//                } else {
//                    password = row.getCell(41).toString();  
//                }
//                list_password.add(password);
//                String confirm_password;
//                if (row.getCell(42) == null || row.getCell(42).toString().length() == 0 || row.getCell(42).toString().contentEquals("null")) {
//                    confirm_password = null; 
//                } else {
//                    confirm_password = row.getCell(42).toString();  
//                }
//                list_confirm_password.add(confirm_password);
//                addMember(list_mem_ctgry.get(i),list_mem_dept.get(i),list_mem_dept.get(i),list_mem_degree.get(i),list_mem_firstnm.get(i),
//                        list_mem_status.get(i),list_mem_lastnm.get(i),list_mem_gender.get(i),list_date_of_birth.get(i),list_mem_id.get(i),
//                        list_mem_year.get(i),list_mem_type.get(i),list_mem_prmntadd1.get(i),list_mem_prmntcity.get(i),list_mem_prmntpin.get(i),
//                        list_mem_prmntphone.get(i),list_mem_tmpadd1.get(i),list_mem_tmpcity.get(i),list_mem_tmppin.get(i),list_mem_tmppin.get(i),
//                        list_mem_email.get(i),list_library_member.get(i),list_grentr_mem_id.get(i),list_grentr_form_no.get(i),list_grentr_city.get(i),
//                        list_grentr_add1.get(i),list_grentr_firstnm.get(i),list_grentr_phone.get(i),list_grentr_pin.get(i),list_grentr_lastnm.get(i),
//                        list_grentr_email.get(i),null,null,null,null,null,null,null,list_mem_efctv_dt.get(i),list_mem_effctupto.get(i),
//                        list_card_issude.get(i),list_password.get(i),list_no_due_tag.get(i),list_confirm_password.get(i),list_remarks.get(i));
//            }
//        } catch (IOException ioe) {
//            System.out.println(ioe);
//        } catch (Exception ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //System.out.println(list_mem_type);
//        return "Members imported successfully!!!";
//    }
//    
    public String getMappingValue(ArrayList<String> excel_list, ArrayList<String> mapping_list, String checkValue) {
        String result = null;
        for (int i = 0; i < excel_list.size(); i++) {
            if (excel_list.get(i).contentEquals(checkValue)) {
                result = mapping_list.get(i);
            }
        }
        return result;
    }

//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public Response uploadFile(
//			@FormDataParam("file") InputStream uploadedInputStream) {
//                String UPLOAD_FOLDER = "?C:\\Users\\admin\\Desktop\\MemberData";
//		// check if all form parameters are provided
//		if (uploadedInputStream == null)
//			return Response.status(400).entity("Invalid form data").build();
//		// create our destination folder, if it not exists
//		try {
//			createFolderIfNotExists(UPLOAD_FOLDER);
//		} catch (SecurityException se) {
//			return Response.status(500)
//					.entity("Can not create destination folder on server")
//					.build();
//		}
//		String uploadedFileLocation = UPLOAD_FOLDER + "MemberData.xlsx";
//		try {
//			saveToFile(uploadedInputStream, uploadedFileLocation);
//		} catch (IOException e) {
//			return Response.status(500).entity("Can not save file").build();
//		}
//		return Response.status(200)
//				.entity("File saved to " + uploadedFileLocation).build();
//	}
    /**
     * Utility method to save InputStream data to target location/file
     *
     * @param inStream - InputStream to be saved
     * @param target - full path to destination file
     */
    private void saveToFile(InputStream inStream, String target)
            throws IOException {
        OutputStream out = null;
        int read = 0;
        byte[] bytes = new byte[1024];
        out = new FileOutputStream(new File(target));
        while ((read = inStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
    }

    /**
     * Creates a folder to desired location if it not already exists
     *
     * @param dirName - full path to the folder
     * @throws SecurityException - in case you don't have permission to create
     * the folder
     */
    private void createFolderIfNotExists(String dirName)
            throws SecurityException {
        File theDir = new File(dirName);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
    }
    static final String excelLoc = "C:/Users/soullab/Desktop/Copy of MDATA.xlsx";

    @POST
    @Path("getExcel")
    @Consumes({"multipart/form-data"})
    public void getExcel(@FormDataParam("fileLocation") File fileLocation,
            @FormDataParam("name") String name) {
        InputStream inputStream = null;
        try {
            System.out.println("name  .. " + name);
            inputStream = new FileInputStream(fileLocation);
            Workbook wb = WorkbookFactory.create(inputStream);
            System.out.println("inputStream  " + inputStream);
            int numberOfSheet = wb.getNumberOfSheets();

            for (int j = 0; j < numberOfSheet; j++) {
                System.out.println("calledddd");
                Sheet sheet = wb.getSheetAt(j);


                Row row;




                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    System.out.println("row.getCell(0).toString()  " + row.getCell(i).toString());

                }
            }
        } catch (Exception e) {
            System.out.println("errname  .. " + name);
            System.out.println("errrrrorrrrrrrrrrrrrrr");
        }
    }
 
    @GET
    @Path("generatereports")
    public void generatereports() throws FileNotFoundException
    {
//  List<Object> list  = new ArrayList<>();
//  String q="";
//        Query query;
//        q = " SELECT    * from  m_member";
//
//        //List<Object> result;
//        query = getEntityManager().createNativeQuery(q);
//        list = (List<Object>) query.getResultList();
//           
//
//DynamicReport dynamicReport = new ReflectiveReportBuilder (list).build();
//dynamicReport.setTitle("hello");
// 
//JasperPrint jasperPrint = null;
//        try {
//            jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, new ClassicLayoutManager(), list);
//       JasperExportManager.exportReportToPdf(jasperPrint);
//        } catch (JRException ex) {
//            Logger.getLogger (MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
// JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
//jasperViewer.setDefaultCloseOperation(JasperViewer.DISPOSE_ON_CLOSE);
//        jasperViewer.setTitle("Etiquetas");
//        jasperViewer.setZoomRatio((float) 1.25);
//        jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
//        jasperViewer.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
//        jasperViewer.requestFocus();
//        jasperViewer.setVisible(true);
// JasperViewer.viewReport(jasperPrint);	
//JasperExportManager.exportReportToPdfStream(jasperPrint, resp.getOutputStream());
        HttpServletRequest request = null;
   //     System.out.println("bbbbbb.....  "+request.getServletContext().getContextPath());
FastReportBuilder drb = new FastReportBuilder();
DynamicReport dr = null;
        try {
            dr = drb.addColumn("State", "state", String.class.getName(),30)
.addColumn("Branch", "branch", String.class.getName(),30)
.addColumn("Product Line", "productLine", String.class.getName(),50)
.addColumn("Item", "item", String.class.getName(),50)
.addColumn("Item Code", "id", Long.class.getName(),30,true)
.addColumn("Quantity", "quantity", Long.class.getName(),60,true)
.addColumn("Amount", "amount", Float.class.getName(),70,true)
.addGroups(2)
 .setPrintColumnNames(true)
        .setIgnorePagination(false) //for Excel, we may dont want pagination, just a plain list
      .setMargins(0, 0, 0, 0)
        .setTitle("November 2006 sales report")
        .setSubtitle("This report was generated at " + new Date())
        .setUseFullPageWidth(true)
                   // .setAllowDetailSplit(true)
//.setPrintBackgroundOnOddRows(true)
//.setUseFullPageWidth(true)
.build();
        } catch (ColumnBuilderException ex) {
            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }

//JRDataSource ds = new JRBeanCollectionDataSource(TestRepositoryProducts.getDummyCollection());
//JasperPrint jp = null;
        //try {
       //     jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
       // } catch (JRException ex) {
      //      Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
      //  }
        //try {
           // ReportExporter.exportReportPlainXls(jp, System.getProperty("user.home") +"/Desktop/mem.xls");
//ReportExporter.exportReportHtml(jp, System.getProperty("user.home") +"/Desktop/mem.html");
           // JasperExportManager.exportReportToPdfFile(jp, System.getProperty("user.home") +"/Desktop/mem.pdf");
     //   } catch (JRException ex) {
          //  Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
      //  }

}
    
    
        @GET
    @Path("generatereports")
       @Produces("text/html")
    public Response generatereports_() throws JRException, ParseException, JSONException, FileNotFoundException
    {
//  List<Object> list  = new ArrayList<>();
//  String q="";
//        Query query;
//        q = " SELECT    * from  m_member";
//
//        //List<Object> result;
//        query = getEntityManager().createNativeQuery(q);
//        list = (List<Object>) query.getResultList();
//           
//
//DynamicReport dynamicReport = new ReflectiveReportBuilder (list).build();
//dynamicReport.setTitle("hello");
// 
//JasperPrint jasperPrint = null;
//        try {
//            jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, new ClassicLayoutManager(), list);
//       JasperExportManager.exportReportToPdf(jasperPrint);
//        } catch (JRException ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
// JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
//jasperViewer.setDefaultCloseOperation(JasperViewer.DISPOSE_ON_CLOSE);
//        jasperViewer.setTitle("Etiquetas");
//        jasperViewer.setZoomRatio((float) 1.25);
//        jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
//        jasperViewer.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
//        jasperViewer.requestFocus();
//        jasperViewer.setVisible(true);
// JasperViewer.viewReport(jasperPrint);	
//JasperExportManager.exportReportToPdfStream(jasperPrint, resp.getOutputStream());
 
FastReportBuilder drb = new FastReportBuilder();
DynamicReport dr = null;
//JsonObject jobj = biblidetailsLocationFacadeREST.getBibliRecordsall("department","3","18","''","''");
// List<String> titleList = new ArrayList<>();
//     for (String key: jobj.keySet()) {
//          titleList.add(key);
//     }      
try {
          
            
         dr = drb.addColumn("accNo", "accessionNo", String.class.getName(),30)
.addColumn("author", "author", String.class.getName(),50)
                 .addColumn("title", "title", String.class.getName(),20)
                 .addColumn("gender", "gender", String.class.getName(),30)
                 .addColumn("rating", "rating", String.class.getName(),20)
                  .addColumn("col", "col", String.class.getName(),30)
                
//            dr = drb.addColumn("State", "state", String.class.getName(),30)
//.addColumn("Branch", "branch", String.class.getName(),30)
//.addColumn("Product Line", "productLine", String.class.getName(),50)
//.addColumn("Item", "item", String.class.getName(),50)
//.addColumn("Item Code", "id", Long.class.getName(),30,true)
//.addColumn("Quantity", "quantity", Long.class.getName(),60,true)
//.addColumn("Amount", "amount", Float.class.getName(),70,true)
.addGroups(2)
.setTitle("November 2006 sales report")
.setSubtitle("This report was generated at " + new Date())
.setPrintBackgroundOnOddRows(true)
.setUseFullPageWidth(true)
.build();
        } catch (ColumnBuilderException ex) {
            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
//JRDataSource ds = new JRBeanCollectionDataSource(biblidetailsLocationFacadeREST.getBibliRecordsall(););
 //jobj =  biblidetailsLocationFacadeREST.getBibliRecordsall("department","3","18","''","''");
 //JSONObject obj = new JSONObject(jobj.toString());
 
String s = "["+
  "{id:1, name:'Oli Bob', progress:12, gender:'male', rating:1, col:'red', dob:'', car:1, lucky_no:5, cheese:'Cheader'},"+
  "{id:2, name:'Mary May', progress:1, gender:'female', rating:2, col:'blue', dob:'14/05/1982', car:true, lucky_no:10, cheese:'Gouda'},"+
  "{id:3, name:'Christine Lobowski', progress:42, gender:'female', rating:0, col:'green', dob:'22/05/1982', car:'true', lucky_no:12, cheese:'Manchego'},"+
  "{id:4, name:'Brendon Philips', progress:100, gender:'male', rating:1, col:'orange', dob:'01/08/1980', lucky_no:18, cheese:'Brie'},"+
  "{id:5, name:'Margret Marmajuke', progress:16, gender:'female', rating:5, col:'yellow', dob:'31/01/1999', lucky_no:33, cheese:'Cheader'}"+
"]";

//System.out.println(obj.getString("name")); //John
 
// System.out.println("jobj  .1. "+jobj.toString());     
//  System.out.println();
// System.out.println("jobj  .2. "+jobj.getString("accessionNo"));
//          System.out.println("jobj  .3. "+jobj.getJsonArray("2"));
 ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(s.getBytes());

JsonDataSource ds= new JsonDataSource(jsonDataStream);
JasperPrint jp = null;
        try {
            jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
        } catch (JRException ex) {
            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
 String xmlString = null;
        try {
            xmlString = JasperExportManager.exportReportToXml(jp);
        } catch (JRException ex) {
            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        //JasperExportManager.exportReportToHtmlFile(jp, xmlString);
//        JasperExportManager.exportReportToPdfFile(jp, System.getProperty("user.home") +"/Desktop/mem112.pdf");
//
//        String ExcelFileName = "D:/BasicReport.xls";
//       
//                        JRXlsExporter exporter = new JRXlsExporter();
//                      
//                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
//                       // exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
//                      //  response.setHeader("Content-Disposition", "inline;filename=" + "basicReport.xls");
//                        exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
//                        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//
//                        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
//                        exporter.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
//                        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, ExcelFileName);
//
//                     //   response.setContentType("application/vnd.ms-excel");
//  ResponseBuilder response = Response.ok((Object) exporter);
//                        exporter.exportReport();
    	  HtmlExporter exporter = new HtmlExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                     //   exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, sout);
                      //  exporter.exportReport();
  ResponseBuilder response = Response.ok((Object) exporter).type(MediaType.TEXT_HTML_TYPE);
                        exporter.exportReport();
		//response.header("Content-Disposition",
		//		"attachment; filename=data123.jsp");
		return response.build();
    
}
   
    
//          @GET
//    @Path("generateDynamicColreports")
//    public String generateDynamicColreports() throws JRException, ParseException, JSONException, FileNotFoundException
//    {
// DynamicReportBuilder drb = new DynamicReportBuilder();
// Style detailStyle = new Style();
// detailStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
// 
// 
// Style headerStyle = new Style();
//
//headerStyle.setBackgroundColor(new Color(50,111,235));
//headerStyle.setBorderBottom(Border.THIN());
//headerStyle.setTextColor(new Color(255,255,255));
//headerStyle.setBorderColor(Color.black);
//headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
//headerStyle.setTransparency(Transparency.OPAQUE);
//
//
///**
// * "titleStyle" exists in the template .jrxml file
// * The title should be seen in a big font size, violet foreground and light green background
// */
//Style titleStyle = new Style();
//
///**
// * "subtitleStyleParent" is meant to be used as a parent style, while
// * "subtitleStyle" is the child.
// */
//Style subtitleStyleParent = Style.createBlankStyle("subtitleParent");
//subtitleStyleParent.setBackgroundColor(Color.CYAN);
//subtitleStyleParent.setTransparency(Transparency.OPAQUE);
//
//Style subtitleStyle = Style.createBlankStyle("subtitleStyle");
//subtitleStyle.setFont(Font.GEORGIA_SMALL_BOLD);
//
//Style amountStyle = new Style(); amountStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
// 
//drb.setTitle("November 2006 sales report")		//defines the title of the report
//        .setSubtitle("The items in this report correspond "
//                        +"to the main products: DVDs, Books, Foods and Magazines")
//        .setDetailHeight(15)		//defines the height for each record of the report
//        .setMargins(30, 20, 30, 15)		//define the margin space for each side (top, bottom, left and right)
//       .setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
//        .setColumnsPerPage(1);	
//
//	
//
//
////FastReportBuilder drb = new FastReportBuilder();
//DynamicReport dr = null;
//JsonObject jobj = biblidetailsLocationFacadeREST.getBibliRecordsall("department","3","18","''","''");
// List<String> titleList = new ArrayList<>();
//     for (String key: jobj.keySet()) {
//          titleList.add(key);
//          
//     }  
//     
//     AbstractColumn[] s = new AbstractColumn[titleList.size()];
//     
//     for(int i=0;i<titleList.size();i++)
//     {
//         s[i] = ColumnBuilder.getNew()		//creates a new instance of a ColumnBuilder
//        .setColumnProperty(titleList.get(i), String.class.getName())		//defines the field of the data source that this column will show, also its type
//        .setTitle(titleList.get(i))		//the title for the column
//        .setWidth(85)		//the width of the column
//        .build();
//     }
//try {
//   for(int ij=0;ij<titleList.size();ij++)
//     {        
//      drb.addColumn(s[ij]);
//     }
//
///**
// * add some more options to the report (through the builder)
// */
//drb.setUseFullPageWidth(true);  //we tell the report to use the full width of the page. this rezises
//                                //the columns width proportionally to meat the page width.
//
// dr = drb.build(); //Finally build the report!      
//        
//        } catch (ColumnBuilderException ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        } 
//
// ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(biblidetailsLocationFacadeREST.getReportJsonData().toString().getBytes());
//
//JsonDataSource ds= new JsonDataSource(jsonDataStream);
//JasperPrint jp = null;
//        try {
//            jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
//        } catch (JRException ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
// String xmlString = null;
//        try {
//            xmlString = JasperExportManager.exportReportToXml(jp);
//        } catch (JRException ex) {
//            Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //JasperExportManager.exportReportToHtmlFile(jp, xmlString);
//        JasperExportManager.exportReportToPdfFile(jp, System.getProperty("user.home") +"/Desktop/mem13.pdf");
//                JasperExportManager.exportReportToHtmlFile(jp, System.getProperty("user.home") +"/Desktop/mem14.html");
// ReportExporter.exportReportPlainXls(jp, System.getProperty("user.home") +"/Desktop/mem15.xls");
//return xmlString;
//           // JasperViewer.viewReport(jp);    //finally display the report report
//
//    
//    
//}
//    
    
    private DynamicReport createDynamicReport(String realPath)
                        throws ColumnBuilderException, ClassNotFoundException {
                FastReportBuilder drb = new FastReportBuilder();
                drb.addColumn("State", "state", String.class.getName(),30)
                        .addColumn("Branch", "branch", String.class.getName(),30)
                        .addColumn("Product Line", "productLine", String.class.getName(),50)
                        .addColumn("Item", "item", String.class.getName(),50)
                        .addColumn("Item Code", "id", Long.class.getName(),30,true)
                        .addColumn("Quantity", "quantity", Long.class.getName(),60,true)
                        .addColumn("Amount", "amount", Float.class.getName(),70,true)
                        .addGroups(2)
                        .addFirstPageImageBanner(realPath + "/images/logo_fdv_solutions_60.jpg", new Integer(197), new Integer(60), ImageBanner.ALIGN_LEFT)
                        .setTitle("November 2008 sales report")
                        .setSubtitle("This report was generated at " + new Date())
                        .setPrintBackgroundOnOddRows(true)
                        .setIgnorePagination(true)
                        .setUseFullPageWidth(true);

                DynamicReport dr = drb.build();
                return dr;
        }

      //  public Collection getDummyCollectionSorted(List columnlist) {
          //      Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
         //       return SortUtils.sortCollection(dummyCollection,columnlist);

      //  }
    
        @GET
        @Path("getReport")    
   public void  getReport() throws ColumnBuilderException,
                        ClassNotFoundException, JRException, IOException {
       HttpServletRequest request = null;
       HttpServletResponse response = null;
               // String realPath = request.getSession().getServletContext().getRealPath("/");

                DynamicReport dr = createDynamicReport(System.getProperty("user.home") +"/Desktop/mem.html");

                String imageServletUrl = "reports/image";
                LayoutManager layoutManager = new ClassicLayoutManager();
               // JRDataSource ds = new JRBeanCollectionDataSource(getDummyCollectionSorted(dr.getColumns()));
                Map parameters = new HashMap();
                Map exporterParams = new HashMap();

               // DJServletHelper.exportToHtml(request, response, imageServletUrl, dr, layoutManager, ds, parameters, exporterParams);

        }

    
    
    
}
