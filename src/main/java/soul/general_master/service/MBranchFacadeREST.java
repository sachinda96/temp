/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master.service;

import java.util.ArrayList;
import java.util.List;
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
import javax.ws.rs.core.Form;
import javax.ws.rs.core.PathSegment;
import soul.circulation.MMember;
import soul.general_master.MBranch;
import soul.general_master.MBranchPK;
import soul.general_master.MBranchmaster;
import soul.general_master.MFcltydept;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.mbranch")
public class MBranchFacadeREST extends AbstractFacade<MBranch> {
    @EJB
    private MBranchmasterFacadeREST  mBranchmasterFacadeREST;
    @EJB
    private MFcltydeptFacadeREST mFcltydeptFacadeREST;
    @PersistenceContext(unitName = "SoulRestAppPU")
    
    private EntityManager em;
    MBranch branch = new MBranch();
    MBranchmaster branchMaster = new MBranchmaster();
    int count;
    String output;
    

    private MBranchPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;branchcd=branchcdValue;fcltycd=fcltycdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.general_master.MBranchPK key = new soul.general_master.MBranchPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> branchcd = map.get("branchcd");
        if (branchcd != null && !branchcd.isEmpty()) {
            key.setBranchcd(new java.lang.String(branchcd.get(0)));
        }
        java.util.List<String> fcltycd = map.get("fcltycd");
        if (fcltycd != null && !fcltycd.isEmpty()) {
            key.setFcltycd(new java.lang.String(fcltycd.get(0)));
        }
        return key;
    }
    @PUT
    @Path("viewers/view/issue/data")
    public void countAll() {
        MMember s = new MMember();
        Query query = getEntityManager().createNativeQuery(s.getIssues());
        int result = query.executeUpdate();
    }

    public MBranchFacadeREST() {
        super(MBranch.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(MBranch entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MBranch entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.general_master.MBranchPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public MBranch find(@PathParam("id") PathSegment id) {
        soul.general_master.MBranchPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MBranch> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MBranch> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
    //Added Manually
    @GET
    @Path("by/{attribute}/{value}")
    @Produces({"application/xml", "application/json"})
    public List<MBranch> findBy(@PathParam("attribute") String attribute, @PathParam("value") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();

        switch (attribute) {
            case "findByFcltycd":
                valueList.add(String.valueOf(valueString[0])); //used to loadCourse
                break;

            case "reportByInstitute":
                valueList.add(Integer.parseInt(valueString[0])); //used to loadCourse
                break;
            case "reportByDepartment":
                valueList.add(Integer.parseInt(valueString[0])); //used to loadCourse
                break;
            case "reportByBranch":
                valueList.add(Integer.parseInt(valueString[0])); //used to loadCourse
                break;
            default:
                valueList.add(values);
                break;
        }
        return super.findBy("MBranch." + attribute, valueList);
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
 
    @GET
    @Path("retrieveAll/{accept}/{form}")
    @Produces({"application/xml", "application/json"})
   // public List<MBranch> retrieveAll(@QueryParam("accept") String accept,@QueryParam("form") String form)
    public List<MBranch> retrieveAll(@PathParam("accept") String accept,@PathParam("form") String form)        
    {
        List<MBranch> getAll = null;
        if(accept.equals("XML"))
         {
             //to send list of branches under different departments of institiute
            getAll = findAll();
         }
         return getAll;
    }
    
    @DELETE
    @Path("deleteBranch/{code}")
    @Produces("text/plain")
    public String deleteBranch(@PathParam("code") PathSegment codes)
    {
        System.out.println("codes "+codes);
        count = Integer.parseInt(countREST());
        branch = find(codes);
        remove(codes);
         if(count == Integer.parseInt(countREST()))
        {
            output = "Someting went wrong record is not deleted!";
        }
        else
        {
            output = "OK";      
        }
        return output;
//          System.out.println("code "+code);
//        count = Integer.parseInt(countREST());
//        branch = find(code);
//        String id = code;
//        System.out.println("id "+id);
//        String pk[] = id.split(";");
//        String branchcd = pk[1].split("=")[1];
//        String fcltycd = pk[2].split("=")[1];
//        deleteByBranchCdAndFacultyCd("deleteByBranchCdAndFacultyCd",branchcd+","+fcltycd);
//         if(count == Integer.parseInt(countREST()))
//        {
//            output = "Someting went wrong record is not deleted!";
//        }
//        else
//        {
//            output = "OK";      
//        }
//        return output;
    }
 
    @POST
    @Path("createOrSave")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrSave(Form form, @FormParam("branchOperation") String operation) {
        if (operation.equals("Add") || operation.equals("Update")) {
            System.out.println("operation : " + operation);
            branchMaster = mBranchmasterFacadeREST.find(Integer.parseInt(form.asMap().getFirst("branch")));

            branch.setBranchdescr(branchMaster.getBranchname());
            branch.setBranchdescr(branchMaster.getBranchCd());
            branch.setMBranchPK(new MBranchPK(branchMaster.getBranchCd(), String.valueOf(form.asMap().getFirst("deptCode"))));
            branch.setMBranchmaster(branchMaster);
            branch.setMFcltydept(mFcltydeptFacadeREST.find(String.valueOf(form.asMap().getFirst("deptCode"))));

            count = Integer.parseInt(countREST());
            create(branch);

            if (Integer.parseInt(countREST()) == count) {
                output = "Something went wrong, branch is not Added!";
            } else {
                output = "Branch Added to " + branch.getMFcltydept().getFcltydeptdscr() + " Department.";
            }

            if (operation.equals("Update")) {
                System.out.println("operation : " + operation);
                output = output.replace("Added", "Updated");
            }
        }
        if (operation.equals("Save")) {
            System.out.println("operation : " + operation);
            String id = form.asMap().getFirst("code");
            String pk[] = id.split(";");
            String branchcd = pk[1].split("=")[1];
            String fcltycd = pk[2].split("=")[1];
            deleteByBranchCdAndFacultyCd("deleteByBranchCdAndFacultyCd", branchcd + "," + fcltycd);
            createOrSave(form, "Update");
        }
        return output;
    }
    
    @DELETE
    @Path("deleteByBranchCdAndFacultyCd/{FacaltyCode}/{BranchCode}")
    @Produces("text/plain")
    public String deleteByBranchCdAndFacultyCd(@PathParam("FacaltyCode") String facaltyCode,@PathParam("BranchCode") String branchCode)
    {
         count = Integer.parseInt(countREST());
         MBranchPK key = new MBranchPK();
         key.setFcltycd(String.valueOf(facaltyCode));
         key.setBranchcd(String.valueOf(branchCode));
        
        System.out.println("branchCode----"+branchCode+"---facaltyCode---"+facaltyCode);
        try{
            super.remove(super.find(key));
        }catch(IllegalArgumentException e){
            return "Soemthing went wrong, department branch is not deleted.";
        }
        if(count == Integer.parseInt(countREST()))
        {
            output = "Soemthing went wrong, department is not deleted.";
        }
        else
        {
            output = "ok";
        }
        return output;
    }
    
    @PUT
    @Path("updateBranch/{FacaltyCode}/{BranchCode}")
     @Produces("text/plain")
    public String updateBranch(@PathParam("FacaltyCode") String facaltyCode,@PathParam("BranchCode") String branchCode) {
        
            String pk[] = branchCode.split(",");
            String del_branchcd = pk[0];
            String in_branchcd = pk[1];
            String fcltycd = facaltyCode;
            deleteByBranchCdAndFacultyCd(fcltycd,del_branchcd);
            
           MBranchPK mb_pk=new MBranchPK();
           mb_pk.setBranchcd(String.valueOf(in_branchcd));
           mb_pk.setFcltycd(String.valueOf(fcltycd));
           
            MFcltydept mFcltydept = new MFcltydept();
            mFcltydept.setFcltydeptcd(String.valueOf(fcltycd));
            
           branchMaster = mBranchmasterFacadeREST.find(Integer.parseInt(in_branchcd));
           branch.setBranchdescr(branchMaster.getBranchname());
           branch.setMBranchPK(mb_pk);
           branch.setMFcltydept(mFcltydept);
           branch=createAndGet(branch);
        
        return "suceessfully updated";
    }
    
    @GET
    @Path("instituteReport/{paramName}/{paramValue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> instituteReport(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;
        switch (paramName) {

            case "reportByInstitute":
                q = "SELECT m_fcltydept.Fclty_dept_cd As InstCode,m_fcltydept.Fclty_dept_dscr As InstName,  m_fcltydept.Fclty_dept_add1 As InstAdd1, \n" +
                        " m_fcltydept.Fclty_dept_add2 As InstAdd2,  m_fcltydept.Fclty_dept_city As InstCity, m_fcltydept.Fclty_dept_pin As InstPin,  \n" +
                        " m_fcltydept.Fclty_dept_phone As InstPhone, m_fcltydept.Fclty_dept_fax As InstFax,  m_fcltydept.Fclty_dept_email As InstEmail, \n" +
                        " m_fcltydept.Inst_cd,  m_fcltydept_1.Fclty_dept_cd AS DeptCode,m_fcltydept_1.Fclty_dept_dscr AS DeptName,  \n" +
                        " m_fcltydept_1.Fclty_dept_add1 AS DeptAdd1,m_fcltydept_1.Fclty_dept_add2 AS DeptAdd2,  m_fcltydept_1.Fclty_dept_city AS DeptCity,\n" +
                        " m_fcltydept_1.Fclty_dept_pin AS DeptPin,  m_fcltydept_1.Fclty_dept_phone AS DeptPhone,m_fcltydept_1.Fclty_dept_fax AS DeptFax,  \n" +
                        " m_fcltydept_1.Fclty_dept_email AS DeptEmail, m_fcltydept_1.Inst_cd As Dept_cd,  m_branch.Branch_cd, m_branch.Branch_descr  \n" +
                        " FROM m_fcltydept, m_fcltydept AS m_fcltydept_1, m_branch, m_branchmaster  \n" +
                        " Where m_fcltydept.Fclty_dept_cd = m_fcltydept_1.Inst_cd and  m_branch.Branch_cd = m_branchmaster.branch_cd and  \n" +
                        " m_fcltydept_1.Fclty_dept_cd = m_branch.Fclty_cd and  (m_fcltydept.Fld_tag = 'I') and (m_fcltydept_1.Fld_tag = 'D')  and m_fcltydept.Fclty_dept_cd = '" + paramValue + "' ";
                break;

            case "reportByDepartment":
                q = "SELECT m_fcltydept.Fclty_dept_cd As InstCode,m_fcltydept.Fclty_dept_dscr As InstName,  m_fcltydept.Fclty_dept_add1 As InstAdd1, \n" +
                        " m_fcltydept.Fclty_dept_add2 As InstAdd2,  m_fcltydept.Fclty_dept_city As InstCity, m_fcltydept.Fclty_dept_pin As InstPin,  \n" +
                        " m_fcltydept.Fclty_dept_phone As InstPhone, m_fcltydept.Fclty_dept_fax As InstFax,  m_fcltydept.Fclty_dept_email As InstEmail,\n" +
                        " m_fcltydept.Inst_cd,  m_fcltydept_1.Fclty_dept_cd AS DeptCode,m_fcltydept_1.Fclty_dept_dscr AS DeptName,  \n" +
                        " m_fcltydept_1.Fclty_dept_add1 AS DeptAdd1,m_fcltydept_1.Fclty_dept_add2 AS DeptAdd2,  m_fcltydept_1.Fclty_dept_city AS DeptCity, \n" +
                        " m_fcltydept_1.Fclty_dept_pin AS DeptPin,  m_fcltydept_1.Fclty_dept_phone AS DeptPhone,m_fcltydept_1.Fclty_dept_fax AS DeptFax,  \n" +
                        " m_fcltydept_1.Fclty_dept_email AS DeptEmail, m_fcltydept_1.Inst_cd As Dept_cd,  m_branch.Branch_cd, m_branch.Branch_descr  \n" +
                        " FROM m_fcltydept, m_fcltydept AS m_fcltydept_1, m_branch, m_branchmaster  \n" +
                        " Where m_fcltydept.Fclty_dept_cd = m_fcltydept_1.Inst_cd and  m_branch.Branch_cd = m_branchmaster.branch_cd and  \n" +
                        " m_fcltydept_1.Fclty_dept_cd = m_branch.Fclty_cd and  (m_fcltydept.Fld_tag = 'I') and (m_fcltydept_1.Fld_tag = 'D')  and m_fcltydept_1.Fclty_dept_cd = '" + paramValue + "' ";
                break;
            
            case "reportByBranch":
                q = "SELECT m_fcltydept.Fclty_dept_cd As InstCode,m_fcltydept.Fclty_dept_dscr As InstName,  m_fcltydept.Fclty_dept_add1 As InstAdd1, \n" +
                        " m_fcltydept.Fclty_dept_add2 As InstAdd2,  m_fcltydept.Fclty_dept_city As InstCity, m_fcltydept.Fclty_dept_pin As InstPin,  \n" +
                        " m_fcltydept.Fclty_dept_phone As InstPhone, m_fcltydept.Fclty_dept_fax As InstFax,  m_fcltydept.Fclty_dept_email As InstEmail, \n" +
                        " m_fcltydept.Inst_cd,  m_fcltydept_1.Fclty_dept_cd AS DeptCode,m_fcltydept_1.Fclty_dept_dscr AS DeptName,  \n" +
                        " m_fcltydept_1.Fclty_dept_add1 AS DeptAdd1,m_fcltydept_1.Fclty_dept_add2 AS DeptAdd2,  m_fcltydept_1.Fclty_dept_city AS DeptCity, \n" +
                        " m_fcltydept_1.Fclty_dept_pin AS DeptPin,  m_fcltydept_1.Fclty_dept_phone AS DeptPhone,m_fcltydept_1.Fclty_dept_fax AS DeptFax,  \n" +
                        " m_fcltydept_1.Fclty_dept_email AS DeptEmail, m_fcltydept_1.Inst_cd As Dept_cd,  m_branch.Branch_cd, m_branch.Branch_descr  \n" +
                        " FROM m_fcltydept, m_fcltydept AS m_fcltydept_1, m_branch, m_branchmaster  \n" +
                        " Where m_fcltydept.Fclty_dept_cd = m_fcltydept_1.Inst_cd and  m_branch.Branch_cd = m_branchmaster.branch_cd and  \n" +
                        " m_fcltydept_1.Fclty_dept_cd = m_branch.Fclty_cd and  (m_fcltydept.Fld_tag = 'I') and (m_fcltydept_1.Fld_tag = 'D')  and m_branch.Branch_cd = '" + paramValue + "' ";
                break;

        }
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
        
}