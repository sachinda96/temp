/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import soul.general_master.MBranchmaster;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.mbranchmaster")
public class MBranchmasterFacadeREST extends AbstractFacade<MBranchmaster> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    MBranchmaster branchMaster = new MBranchmaster();
    int count;
    String output;
    
    public MBranchmasterFacadeREST() {
        super(MBranchmaster.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(MBranchmaster entity) {
        super.create(entity);
    }
    
    //Added Manually
    @POST
    @Override
    @Path("createAndGet")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public MBranchmaster createAndGet(MBranchmaster entity) {
        return super.createAndGet(entity);
    }


    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MBranchmaster entity) {
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
    public MBranchmaster find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MBranchmaster> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MBranchmaster> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @GET
    @Path("retrieveAllBranch/{accept}/{form}")
    @Produces({"application/xml", "application/json"})
   // public List<MBranchmaster> retrieveAllBranch(@QueryParam("accept") String accept,@QueryParam("form") String form)
    public List<MBranchmaster> retrieveAllBranch(@PathParam("accept") String accept,@PathParam("form") String form)        
    {
        
        List<MBranchmaster> branchMasterList = new ArrayList<>();
        branchMasterList= findAll();
        String names = "";
        String codes = "";
        for(MBranchmaster b: branchMasterList)
            {
                codes = codes+","+b.getBranchCd();
                names = names+","+b.getBranchname();
            }
        if(codes.length()>0)
        {
               String branches = codes.subSequence(1, codes.length())+"|"+names.subSequence(1, names.length());
        }
      return branchMasterList;
    }
            
    
    @GET
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<MBranchmaster> retrieveAll()
    {
        //to send list of branchMasters under different departments of institiute
        List<MBranchmaster> getAll = findAll();
        return getAll;
    }
  
    @DELETE
    @Path("deleteBranchMaster/{code}")
    @Produces("text/plain")
    public String deleteBranchMaster(@PathParam("code") String code)
    {
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
    
    @POST
    @Path("createBranch")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String createBranch(@FormParam("branchName") String branchName,@FormParam("branchCd") String branchCd)
    {
        //Add Line
        branchMaster.setBranchCd(branchCd);
        branchMaster.setBranchname(branchName);
        create(branchMaster);
        count = super.count();
        if (count == Integer.parseInt(countREST())) {
            output = "Branch Inserted!!!";
        } else {
            output = "Something went wrong, branch is not created.";
        }
        return output;
        //response.sendRedirect("./branchMaster.jsp");
    }
    
    @PUT
    @Path("updateBranch")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    public String updateBranch(@FormParam("branchCode") String branchCode, @FormParam("branchName") String branchName)
    {
        MBranchmaster BM;
        try{
            BM = find(Integer.parseInt(branchCode));
        }catch(NullPointerException e){
            return "Invalid branch!!!";
        }  
        BM.setBranchname(branchName);
        edit(BM);
        output = "Branch record updated!!!";
        return output;
        //response.sendRedirect("./branchMaster.jsp");
    }
    
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<MBranchmaster> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();


        switch (query) {
            case "findByBranchname":
                valueList.add(valueString[0]);
                break;

        }
        return super.findBy("MBranchmaster." + query, valueList);
    }
}
