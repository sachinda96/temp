/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master.service;

//import ExceptionService.DataException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import soul.general_master.MFcltydept;
/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.mfcltydept")
public class MFcltydeptFacadeREST extends AbstractFacade<MFcltydept> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output;
    String count;
    MFcltydept institute = new MFcltydept();
    List<MFcltydept> insDepList = new ArrayList<>();
    
    public MFcltydeptFacadeREST() {
        super(MFcltydept.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(MFcltydept entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MFcltydept entity) {
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
    public MFcltydept find(@PathParam("id") String id) {
        return super.find(id);
    }
    

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MFcltydept> findAll() {
        return super.findAll();
    }


    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MFcltydept> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    

    @GET
    @Path("by/{attribute}/{value}")
    @Produces({"application/xml", "application/json"})
    public List<MFcltydept> findBy(@PathParam("attribute") String attribute, @PathParam("value") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        
        switch(attribute)
        {           
            case "findByFldtag":   valueList.add(values.charAt(0));  //used to load institute  
                                    break;
                                    
//            case "findByInst":     valueList.add(Integer.parseInt(valueString[0]));
//                                    break;
           
            case "findByInstcd":   valueList.add(String.valueOf(valueString[0]));
                                    break;
            
            case "findByInstituteName":      valueList.add(valueString[0]);
                                             break;
            
            case "findByDepartmentName":      valueList.add(valueString[0]);
                                             break;
                                    
            default:                valueList.add(String.valueOf(valueString[0]));
                                    break;
        }
        return super.findBy("MFcltydept."+attribute, valueList);
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
    
    // add manually
    //Get list of all departments
    @GET
    @Path("allDepartments")
    @Produces({"application/xml", "application/json","text/plain"})
    public Response allDepartments() 
    {  
        String output = null;
        Response.ResponseBuilder responseBuilder = Response.status(200);
        List<MFcltydept> insDepList ;
        insDepList = findBy("findByFldtag", "D");
        System.out.println("insDepList  "+insDepList.size());
        String names = "";
        String codes = "";
        for(MFcltydept insDep: insDepList)
            {  System.out.println("xxxxxxx   "+insDep.getFcltydeptcd());
                codes = codes+","+insDep.getFcltydeptcd();
                names = names+","+insDep.getFcltydeptdscr();
            }
       if(codes.length()>0)
       {
            output =  codes.subSequence(1, codes.length())+"|"+names.subSequence(1, names.length());
           System.out.println("output "+output);
            responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
       }
              
               GenericEntity<List<MFcltydept>> list = new GenericEntity<List<MFcltydept>>(insDepList) {};
                responseBuilder.type(MediaType.APPLICATION_XML).entity(list);
             return responseBuilder.build();
    }
   
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<MFcltydept> retrieveAll()
    {
        List<MFcltydept> getAll = null;
        getAll = findAll();
        return getAll;
    }
    
    @DELETE
    @Path("deleteInstitute/{code}")
    @Produces("text/plain")
    public String deleteInstitute(@PathParam("code") String code)
    {  
        count = String.valueOf(countREST());
        institute = find(String.valueOf(code));
        remove(String.valueOf(code));
        if(count == String.valueOf(countREST()))
        {
            if(institute.getFldtag() == 'I')
                output = "There are some departments under this institute, you can not delete this Insitute.";
            else
                output = "There are some branches under this department, you can not delete this Department.";
        }
        else
        {
            output = "OK";
        }
        return output;
    }
    
    @POST
    @Path("createOrSave")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createOrSave(Form form,@FormParam("operation") String operation)
    {
        if(operation.equals("Create"))
        {
            institute = getInstitute(form);
            count = String.valueOf(countREST());
            create(institute);
            if(String.valueOf(countREST()) == count)
            {
                output = "Something went wrong, record for "+institute.getFcltydeptdscr()+" not created!";
            }
            else
                output = institute.getFcltydeptdscr() + " record created.";
            //response.sendRedirect("./institute.jsp");
            //output = institute.getFldtag() == 'I' ? "Institute" : "Department";
            //output = output+" Created.";
            //out.println(output);
        }
           if(operation.equals("Save"))
        {                  
            institute = find(Integer.parseInt(form.asMap().getFirst("code")));
            institute = getInstitute(form);
            edit(institute);
            //response.sendRedirect("./institute.jsp");
            output = institute.getFldtag() == 'I' ? "Institute" : "Department";
            output = output+" Updated.";
        }
            return output;
    }
    
     public  MFcltydept getInstitute(Form form)
    {
        MFcltydept institute = null ;
        if(!form.asMap().getFirst("code").isEmpty())
        {
         institute = find(String.valueOf(form.asMap().getFirst("code")));
        }
        if(institute == null)
            institute = new MFcltydept();
        
        institute.setFcltydeptdscr(form.asMap().getFirst("name"));
        institute.setFldtag(form.asMap().getFirst("type").charAt(0));
        institute.setFcltydeptadd1(form.asMap().getFirst("address"));
        institute.setFcltydeptadd2(form.asMap().getFirst("address2"));
        institute.setFcltydeptcity(form.asMap().getFirst("city"));
        institute.setFcltydeptpin(form.asMap().getFirst("pin"));
        institute.setFcltydeptphone(form.asMap().getFirst("phone"));
        if(institute.getFldtag() == 'I')
            institute.setInstcd(null);
        else
            {
                institute.setInstcd(find(String.valueOf(form.asMap().getFirst("institute"))));
            }
        institute.setFcltydeptemail(form.asMap().getFirst("email"));
        institute.setFcltydeptfax(form.asMap().getFirst("fax"));

        return institute;
    }
     
    @GET
    @Path("getLibraries")
    @Produces({"application/xml", "application/json"})
    public List<Object> remtoLib() throws ParseException {
        String q = "";
        List<Object> result = new ArrayList<>();
        Query query;
        q = "SELECT Fclty_dept_cd, Fclty_dept_dscr FROM m_fcltydept WHERE fld_tag = 'I'";
        query = getEntityManager().createNativeQuery(q, MFcltydept.class);
        result = (List<Object>) query.getResultList();
        return result;
}
     
    //Get all institutes list
    @GET
    @Path("instituteList")
    @Produces({"application/xml", "application/json"})
    public Response instituteList() 
    {  
        String output = null;
        Response.ResponseBuilder responseBuilder = Response.status(200);
        List<MFcltydept> insDepList ;
        insDepList = findBy("findByFldtag", "I");
        String names = "";
        String codes = "";
        for(MFcltydept insDep: insDepList)
            {
                codes = codes+","+insDep.getFcltydeptcd();
                names = names+","+insDep.getFcltydeptdscr();
            }
       if(codes.length()>0)
       {
            output =  codes.subSequence(1, codes.length())+"|"+names.subSequence(1, names.length());
            responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
       }
            GenericEntity<List<MFcltydept>> list = new GenericEntity<List<MFcltydept>>(insDepList) {};
            
            //changes
            responseBuilder.type(MediaType.APPLICATION_XML).entity(list);
            return responseBuilder.build();
    }
    
    
    //GEt institute by name
    @GET
    @Path("instituteByName/{instituteName}")
    @Produces({"application/xml", "application/json","text/plain"})
    public Response instituteByName(@PathParam("instituteName") String instituteName) 
    {  
        String output = null;
        Response.ResponseBuilder responseBuilder = Response.status(200);
        List<MFcltydept> insDepList ;
        insDepList = findBy("findByInstituteName", instituteName);
        String names = "";
        String codes = "";
        for(MFcltydept insDep: insDepList)
            {
                codes = codes+","+insDep.getFcltydeptcd();
                names = names+","+insDep.getFcltydeptdscr();
            }
       if(codes.length()>0)
       {
            output =  codes.subSequence(1, codes.length())+"|"+names.subSequence(1, names.length());
            responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
       }
            GenericEntity<List<MFcltydept>> list = new GenericEntity<List<MFcltydept>>(insDepList) {};
            responseBuilder.type(MediaType.APPLICATION_XML).entity(list);
            return responseBuilder.build();
    }
    
    //GEt institute by name
    @GET
    @Path("departmentByName/{departmentName}")
    @Produces({"application/xml", "application/json","text/plain"})
    public Response departmentByName(@PathParam("departmentName") String departmentName) 
    {  
        String output = null;
        Response.ResponseBuilder responseBuilder = Response.status(200);
        List<MFcltydept> insDepList ;
        insDepList = findBy("findByDepartmentName", departmentName);
        String names = "";
        String codes = "";
        for(MFcltydept insDep: insDepList)
            {
                codes = codes+","+insDep.getFcltydeptcd();
                names = names+","+insDep.getFcltydeptdscr();
            }
       if(codes.length()>0)
       {
            output =  codes.subSequence(1, codes.length())+"|"+names.subSequence(1, names.length());
            responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
       }
            GenericEntity<List<MFcltydept>> list = new GenericEntity<List<MFcltydept>>(insDepList) {};
            responseBuilder.type(MediaType.APPLICATION_XML).entity(list);
            return responseBuilder.build();
    }
    
    @GET
    @Path("GET/{findBy}/{searchParameter}")
    @Produces({"application/xml", "application/json"})
    public List<Object> findDepartmentAndInstitute(@PathParam("findBy") String Paramname, @PathParam("searchParameter") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();

        Query query;
        switch (Paramname) {
            case "GetDeptByInstitute":
                MFcltydept inst = find(String.valueOf(Paramvalue));
                if (inst == null || inst.getFldtag() == 'D') {
                   // throw new DataException("No institute found with instCd : " + Paramvalue);
                }
                q = "select distinct fclty_dept_dscr,fclty_dept_cd from m_fcltydept where fld_tag = 'D' and inst_cd = '" + Paramvalue + "'";
                break;

            case "GetBranchByDepartment":
                MFcltydept dept = find(String.valueOf(Paramvalue));
                if (dept == null || dept.getFldtag() == 'I') {
                   // throw new DataException("No department found with deptCd : " + Paramvalue);
                }
                q = "select distinct branch_descr,branch_cd  from m_branch b,m_fcltydept fd where fd.fclty_dept_cd = b.fclty_cd and fclty_dept_cd = '" + Paramvalue + "'";
                break;
                
            case "InstituteList":
                q = " SELECT m_fcltydept.Fclty_dept_cd AS InstCode,m_fcltydept.Fclty_dept_dscr AS InstName, \n "+
                    " m_fcltydept.Fclty_dept_add1 AS InstAdd1, m_fcltydept.Fclty_dept_add2 AS InstAdd2,  \n" +
                    " m_fcltydept.Fclty_dept_city AS InstCity, m_fcltydept.Fclty_dept_pin AS InstPin,  m_fcltydept.Fclty_dept_phone AS InstPhone, \n" +
                    " m_fcltydept.Fclty_dept_fax AS InstFax,  \n" +
                    " m_fcltydept.Fclty_dept_email AS InstEmail, m_fcltydept.Inst_cd,  m_fcltydept_1.Fclty_dept_cd AS DeptCode, \n" +
                    " m_fcltydept_1.Fclty_dept_dscr AS DeptName,  \n" +
                    " m_fcltydept_1.Fclty_dept_add1 AS DeptAdd1,m_fcltydept_1.Fclty_dept_add2 AS DeptAdd2, \n" +
                    " m_fcltydept_1.Fclty_dept_city AS DeptCity, m_fcltydept_1.Fclty_dept_pin AS DeptPin,  \n" +
                    " m_fcltydept_1.Fclty_dept_phone AS DeptPhone,m_fcltydept_1.Fclty_dept_fax AS DeptFax,  \n" +
                    " m_fcltydept_1.Fclty_dept_email AS DeptEmail, m_fcltydept_1.Inst_cd AS Dept_cd,  \n" +
                    " m_branch.Branch_cd, m_branch.Branch_descr  \n" +
                    " FROM m_fcltydept, m_fcltydept AS m_fcltydept_1, m_branch, m_branchmaster  \n" +
                    " WHERE m_fcltydept.Fclty_dept_cd = m_fcltydept_1.Inst_cd AND  m_branch.Branch_cd = m_branchmaster.branch_cd AND  \n" +
                    " m_fcltydept_1.Fclty_dept_cd = m_branch.Fclty_cd \n" +
                    " AND  (m_fcltydept.Fld_tag = 'I') AND (m_fcltydept_1.Fld_tag = 'D')  AND m_fcltydept_1.Fclty_dept_cd = '" + Paramvalue + "' ";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
}
