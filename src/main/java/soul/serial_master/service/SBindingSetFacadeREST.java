/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.serial_master.service;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import soul.serialControl.SSchedule;
import soul.serialControl.service.SScheduleFacadeREST;
import soul.serialControl.SSchedule;
import soul.serialControl.SSchedulePK;
import soul.serial_master.SBindingSet;
import soul.serial_master.SBindingSetPK;
import soul.serial_master.SBinding;
import soul.serial_master.SBindingPK;
import soul.serial_master.service.SBindingFacadeREST;

/**
 *
 * @author admin
 */
@Stateless
@Path("soul.serial_master.sbindingset")
public class SBindingSetFacadeREST extends AbstractFacade<SBindingSet> {

    @EJB
    private SScheduleFacadeREST sScheduleFacadeREST;
    @EJB
    private SBindingFacadeREST sBindingFacadeREST;

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    private String[] recIds;
    private String[] srNos;
    private String[] issues;
    private String[] volumes;
    private String[] rem;
    private String[] sta;
    SSchedule sSchedule;

    private SBindingSetPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;sBSetno=sBSetnoValue;sBVolno=sBVolnoValue;sBIssueno=sBIssuenoValue;sBRecid=sBRecidValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.serial_master.SBindingSetPK key = new soul.serial_master.SBindingSetPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> sBSetno = map.get("sBSetno");
        if (sBSetno != null && !sBSetno.isEmpty()) {
            key.setSBSetno(sBSetno.get(0));
        }
        java.util.List<String> sBVolno = map.get("sBVolno");
        if (sBVolno != null && !sBVolno.isEmpty()) {
            key.setSBVolno(sBVolno.get(0));
        }
        java.util.List<String> sBIssueno = map.get("sBIssueno");
        if (sBIssueno != null && !sBIssueno.isEmpty()) {
            key.setSBIssueno(sBIssueno.get(0));
        }
        java.util.List<String> sBRecid = map.get("sBRecid");
        if (sBRecid != null && !sBRecid.isEmpty()) {
            key.setSBRecid(sBRecid.get(0));
        }
        return key;
    }

    public SBindingSetFacadeREST() {
        super(SBindingSet.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(SBindingSet entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, SBindingSet entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.serial_master.SBindingSetPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public SBindingSet find(@PathParam("id") PathSegment id) {
        soul.serial_master.SBindingSetPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SBindingSet> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SBindingSet> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<SBindingSet> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        List<String> smstStatus = new ArrayList<>();

        switch (query) {
            case "findBySBSetno":
                valueList.add(valueString[0]);
                break;
        }
        return super.findBy("SBindingSet." + query, valueList);
    }

    //preparition of binding set
    //service to update reminder counter
    @POST
    @Path("prepareOrUpdateBindingSet")
    @Produces("text/plain")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String prepareBindingSet(@FormParam("recordIds") String recordIds, @FormParam("issue") String issue,
            @FormParam("volume") String volume, 
            @FormParam("srNo") String srNo,
            @Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{setNo.pattern}") @FormParam("setNo") String setNo, 
            @FormParam("remark") String remark,
            @FormParam("bindingType") String bindingType, @FormParam("index") String index,
            @FormParam("bindingColor") String bindingColor, @FormParam("bRemark") String bRemark,
            @FormParam("embossingType") String emboisingType, 
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("firstPage") String firstPage,
            @FormParam("embossingText") String emboisingText, 
            @Pattern(regexp = "^[0-9]*$", message = "{number.pattern}") @FormParam("lastPage") String lastPage,
            @FormParam("operation") String operation,@FormParam("binderCode") String binderCode,
            @FormParam("formName") String formName, @FormParam("status") String status , @FormParam("bSrNo") Long bSrNo_update ) {

        //2. Insert into s_binding_set -> multiple insert
        //1. Update into s_schedule -> multiple updates
        //3. Insert into s_binding 
        String output = null;
        recIds = recordIds.split(",");
        srNos = srNo.split(",");
        volumes = volume.split(",");
        issues = issue.split(",");
        rem = remark.split(",",-1);

        SBinding sBinding = new SBinding();
        SBindingPK sBindingPK = new SBindingPK();

        if (operation.contentEquals("new")) {
            for (int i = 0; i < recIds.length; i++) {
                SSchedule ss = new SSchedule();
                SSchedulePK ssPK = new SSchedulePK();
                SBindingSet sb = new SBindingSet();
                SBindingSetPK sbpk = new SBindingSetPK();

                //Schedule update
                ssPK.setSSRecid(recIds[i]);
                ssPK.setSSSrno(Integer.parseInt(srNos[i]));
                ssPK.setSSVol(volumes[i]);
                ssPK.setSSIss(issues[i]);
                ss = sScheduleFacadeREST.find(ssPK);
                ss.setSSchedulePK(ssPK);
                if(!rem[i].contentEquals(""))
                {
                    ss.setSSRemark(rem[i]);
                }

                ss.setSSBindNo(setNo);
                ss.setSSStatus("Binding");

                //binding entry
                sbpk.setSBIssueno(issues[i]);
                sbpk.setSBRecid(recIds[i]);
                sbpk.setSBSetno(setNo);
                sbpk.setSBVolno(volumes[i]);
                sb.setSBindingSetPK(sbpk);

                sScheduleFacadeREST.edit(ss);
                create(sb);
            }
            sBindingPK.setSBNo(setNo);
            Long bSrNo = new Long("1");
            sBindingPK.setSBSrNo(bSrNo);
            sBinding.setSBindingPK(sBindingPK);
            sBinding.setSBBindtype(bindingType);
            if(formName.contentEquals("commercial_set"))
                sBinding.setSBBinderCd(binderCode);
            else if(formName.contentEquals("inhouse_set"))
                sBinding.setSBBinderCd("INHOUSE");
            else
                output = "Binder code is mandatory";
            sBinding.setSBBindcol(bindingColor);
            sBinding.setSBEmbosetype(emboisingType);
            sBinding.setSBEmbosetext(emboisingText);
            sBinding.setSBEnpg(Integer.parseInt(lastPage));
            sBinding.setSBStpg(Integer.parseInt(firstPage));
            sBinding.setSBRemark(bRemark);
            sBinding.setSBIndex(index);
            sBindingFacadeREST.create(sBinding);
            output = "Set no: " + setNo + " is created";
        } else if (operation.contentEquals("update")) {
            for (int i = 0; i < recIds.length; i++) {
                SSchedule ss = new SSchedule();
                SSchedulePK ssPK = new SSchedulePK();
                SBindingSet sb = new SBindingSet();
                SBindingSetPK sbpk = new SBindingSetPK();
               
                //Schedule update
                ssPK.setSSRecid(recIds[i]);
                ssPK.setSSSrno(Integer.parseInt(srNos[i]));
                ssPK.setSSVol(volumes[i]);
                ssPK.setSSIss(issues[i]);
                ss = sScheduleFacadeREST.find(ssPK);
                ss.setSSchedulePK(ssPK);
                ss.setSSRemark(remark);
                ss.setSSBindNo(setNo);
                ss.setSSStatus("Binding");

                //binding entry
                sbpk.setSBIssueno(issues[i]);
                sbpk.setSBRecid(recIds[i]);
                sbpk.setSBSetno(setNo);
                sbpk.setSBVolno(volumes[i]);
                sb.setSBindingSetPK(sbpk);

                sScheduleFacadeREST.edit(ss);
                create(sb);

            }
            sBindingPK.setSBNo(setNo);
            //Long bSrNo = new Long("1");
            sBindingPK.setSBSrNo(bSrNo_update);
            sBinding.setSBindingPK(sBindingPK);
            sBinding.setSBBindtype(bindingType);
            if(formName.contentEquals("commercial_set"))
                sBinding.setSBBinderCd(binderCode);
            else if(formName.contentEquals("inhouse_set"))
                sBinding.setSBBinderCd("INHOUSE");
            else
                output = "Binder code is mandatory";
//            sBinding.setSBBindcol(bindingColor);
//            sBinding.setSBEmbosetype(emboisingType);
            sBinding.setSBEmbosetext(emboisingText);
//            sBinding.setSBEnpg(Integer.parseInt(lastPage));
//            sBinding.setSBStpg(Integer.parseInt(firstPage));
//            sBinding.setSBRemark(bRemark);
//            sBinding.setSBIndex(index);
            sBindingFacadeREST.edit(sBinding);
            output = "Set no: " + setNo + " updated.";
        } else {
            output = "Invalid operation...";
        }

        return output;
    }

    //delete preparede binding set
    @DELETE
    @Path("deleteBindingSet/{setNo}")
    @Produces("text/plain")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String deleteBindingSet(@Pattern(regexp = "^[A-Za-z0-9 -_]*$", message = "{setNo.pattern}") @PathParam("setNo") String setNo) {

        String output = null;
        SSchedule sSchedule;
        SSchedulePK sSchedulePk = new SSchedulePK();

        //remove from s_binding
        Long bSrNo = new Long("1");
        sBindingFacadeREST.removeBy("deleteSet", setNo + "," + bSrNo);

        //remove from s_binding_set
        removeBy("deleteSet", setNo);

        List<SSchedule> sSchedules = null;
        sSchedules = sScheduleFacadeREST.findBy("findBySSBindNo", setNo);
        System.out.println("sSchedules:" + sSchedules);

        for (int i = 0; i < sSchedules.size(); i++) {
            sSchedules.get(i).setSSBindNo(null);
            sSchedules.get(i).setSSStatus("Received");
            sScheduleFacadeREST.edit(sSchedules.get(i));
        }
        //update s_Schedule
        output = "Setno: " + setNo + " deleted.";
        return output;
    }

    @DELETE
    @Path("removeBy/{namedQuery}/{values}")
    public void removeBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch (query) {
            case "deleteSet":
                valueList.add(valueString[0]);
                break;
            default:
                valueList.add(valueString[0]);
                break;
        }
        super.removeBy("SBindingSet." + query, valueList);
    }
    
    
}
