/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.circulation.ILLitemreceive;
import soul.circulation.IllReqprocdmp;
import soul.circulation.IllRqstmst;
import soul.circulation.MMember;
import soul.circulation.Requestloaddata;
//import soul.circulation.client.IllBorrowing;
import soul.general_master.service.IllLibmstFacadeREST;
import soul.general_master.service.LibmaterialsFacadeREST;
import soul.response.StringprocessData;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;
import soul.util.function.DateNTimeChange;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.illrqstmst")
public class IllRqstmstFacadeREST extends AbstractFacade<IllRqstmst> {

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    @EJB
    private LibmaterialsFacadeREST libmaterialsFacadeREST;
    @EJB
    private IllLibmstFacadeREST illLibmstFacadeREST;
    @EJB
    private MMemberFacadeREST mMemberFacadeREST;
    @EJB
    private IllReqprocdmpFacadeREST illReqprocdmpFacadeREST;

    DateNTimeChange dt = new DateNTimeChange();
    Date Todaydate = new Date();
//    String output;
//    String requestIds[];
//    IllRqstmst illRequest;
//    IllReqprocdmp reqprocdmp;
//    char status;
//    String member;
//    String list;

    public IllRqstmstFacadeREST() {
        super(IllRqstmst.class);
    }

    @POST
    @Path("Request")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData create(String illrequestdata) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        IllRqstmst illRequest = null;
        String memcd = "";
        String mediatypecd = "";
        String title = "";
        String auhtor1 = "";
        String author2 = "";
        String publisher = "";
        String pubyear = "";
        String requestDate = "";
        String libcode = "";
        String datatype = illrequestdata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(illrequestdata);
            memcd = jsonobj.getString("memcd");
            mediatypecd = jsonobj.getString("mediatypecd");
            title = jsonobj.getString("title");
            auhtor1 = jsonobj.getString("author1");
            author2 = jsonobj.getString("author2");
            publisher = jsonobj.getString("publisher");
            pubyear = jsonobj.getString("pubyear");
            requestDate = jsonobj.getString("requestDate");
            libcode = jsonobj.getString("libcode");

        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(illrequestdata);
                memcd = stringintoxml.getdatafromxmltag(doc, "memcd");
                mediatypecd = stringintoxml.getdatafromxmltag(doc, "mediatypecd");
                title = stringintoxml.getdatafromxmltag(doc, "title");
                auhtor1 = stringintoxml.getdatafromxmltag(doc, "author1");
                author2 = stringintoxml.getdatafromxmltag(doc, "author2");
                publisher = stringintoxml.getdatafromxmltag(doc, "publisher");
                pubyear = stringintoxml.getdatafromxmltag(doc, "pubyear");
                requestDate = stringintoxml.getdatafromxmltag(doc, "requestDate");
                libcode = stringintoxml.getdatafromxmltag(doc, "libcode");

            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }

        MMember member = mMemberFacadeREST.find(memcd);

        if (member != null) {
            if (member.getMemStatus().equals("A")) {
                illRequest = new IllRqstmst();
                illRequest.setMemCd(member.getMemCd());
                illRequest.setMedia(libmaterialsFacadeREST.find(mediatypecd));
                illRequest.setTitle(title);
                illRequest.setAuth1(auhtor1);
                illRequest.setAuth2(author2);
                illRequest.setPubYear(pubyear);
                illRequest.setRqstDt(dt.getDateNTimechange(requestDate));
                illRequest.setPublisher(publisher);
                illRequest.setRqstStatus("P");
                illRequest.setLibCd(illLibmstFacadeREST.find(libcode));
                illRequest = createAndGet(illRequest);

                output = "Request Submitted successfully..";
            } else {
                notprocess = "Member is Inactive..";
            }
        } else {
            notprocess = "Member Code Invalid..";
        }

        illRequest = createAndGet(illRequest);

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

    @POST
    @Path("UpdateRequest")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData updaterequest(String illupdaterequestdata) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        IllRqstmst illRequest = null;
        String memcd = "";
        String mediatypecd = "";
        String title = "";
        String auhtor1 = "";
        String author2 = "";
        String publisher = "";
        String pubyear = "";
        String requestDate = "";
        String libcode = "";
        String requestid = "";
        String datatype = illupdaterequestdata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(illupdaterequestdata);
            requestid = jsonobj.getString("requestid");
            memcd = jsonobj.getString("memcd");
            mediatypecd = jsonobj.getString("mediatypecd");
            title = jsonobj.getString("title");
            auhtor1 = jsonobj.getString("author1");
            author2 = jsonobj.getString("author2");
            publisher = jsonobj.getString("publisher");
            pubyear = jsonobj.getString("pubyear");
            requestDate = jsonobj.getString("requestDate");
            libcode = jsonobj.getString("libcode");

        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(illupdaterequestdata);
                requestid = stringintoxml.getdatafromxmltag(doc, "requestid");
                memcd = stringintoxml.getdatafromxmltag(doc, "memcd");
                mediatypecd = stringintoxml.getdatafromxmltag(doc, "mediatypecd");
                title = stringintoxml.getdatafromxmltag(doc, "title");
                auhtor1 = stringintoxml.getdatafromxmltag(doc, "author1");
                author2 = stringintoxml.getdatafromxmltag(doc, "author2");
                publisher = stringintoxml.getdatafromxmltag(doc, "publisher");
                pubyear = stringintoxml.getdatafromxmltag(doc, "pubyear");
                requestDate = stringintoxml.getdatafromxmltag(doc, "requestDate");
                libcode = stringintoxml.getdatafromxmltag(doc, "libcode");

            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }
        List<IllRqstmst> lirequest = findBy("findByIllRequestNo", requestid);
        MMember member = mMemberFacadeREST.find(memcd);
        int skip = 0;
        if (!(lirequest.size() > 0)) {
            skip = 1;
        }

        if (member != null && skip == 0) {
            if (member.getMemStatus().equals("A")) {
                if (lirequest.get(0).getMemCd().equals(member.getMemCd()) && lirequest.get(0).getMedia().getCode().equals(mediatypecd) && lirequest.get(0).getTitle().equals(title) && lirequest.get(0).getRqstStatus().equals("P")) {
                    illRequest = lirequest.get(0);
                    illRequest.setAuth1(auhtor1);
                    illRequest.setAuth2(author2);
                    illRequest.setPubYear(pubyear);
                    illRequest.setRqstDt(dt.getDateNTimechange(requestDate));
                    illRequest.setPublisher(publisher);
                    illRequest.setLibCd(illLibmstFacadeREST.find(libcode));
                    edit(illRequest);

                    output = illRequest.getTitle() + " Request Updated successfully..";
                } else {
                    notprocess = " Requst is not match..";
                }

            } else {
                notprocess = "Member is Inactive..";
            }
        } else {
            notprocess = "Member Code Invalid..";
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

    @DELETE
    @Path("Deletebyrequest/{id}")
    @Produces({"application/xml", "application/json"})
    public StringprocessData remove(@PathParam("id") String id) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String ids[] = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            int count = Integer.parseInt(countREST());
            super.remove(super.find(Integer.valueOf(ids[i])));
            if (Integer.parseInt(countREST()) == count) {
                notprocess += ids[i] + " Request is not deleted,";
            } else {
                output += ids[i] + "Deleted Successfully,";
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

    @POST
    @Path("Sendback")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData Sendbacktolibrary(String sendbackdata) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String requestid = "";
        String datatype = sendbackdata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(sendbackdata);
            requestid = jsonobj.getString("requestid");
        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(sendbackdata);
                requestid = stringintoxml.getdatafromxmltag(doc, "requestid");
            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }

        String ids[] = requestid.split(",");
        
        for(int i = 0; i < ids.length; i++) {
            IllRqstmst illRequest = null;
            illRequest = find(Integer.parseInt(ids[i]));

            if (illRequest != null) {

                if (illRequest.getRqstStatus().equals("R")) {
                    IllReqprocdmp reqprocdmp = new IllReqprocdmp();
                    reqprocdmp.setArrDt(illRequest.getArrivalDt());
                    reqprocdmp.setIssDt(illRequest.getIssueDt());
                    reqprocdmp.setIssUCd(illRequest.getIssueUserCode());
                    reqprocdmp.setLibCd(illRequest.getLibCd());
                    reqprocdmp.setMemCd(mMemberFacadeREST.find(illRequest.getMemCd()));
                    reqprocdmp.setRecvUCd(illRequest.getRecieveUserCode());
                    reqprocdmp.setRetDt(illRequest.getRecieveDt());
                    reqprocdmp.setRetUCd(illRequest.getReturnUserCode());
                    reqprocdmp.setSendDt(Todaydate);
                    reqprocdmp.setSendUCd("super user");    //Need to be changed to dynamic
                    reqprocdmp.setTitle(illRequest.getTitle());

                    illReqprocdmpFacadeREST.create(reqprocdmp);

                    removebyrequestno(Integer.parseInt(ids[i]));

                    output += ids[i] + " Items Sent back,";
                } else {
                     notprocess += ids[i] + " Item status is not Return,";
                }

            } else {
                notprocess += ids[i] + " No data found,";
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

    @POST
    @Path("createAndGet")
    @Override
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public IllRqstmst createAndGet(IllRqstmst entity) {
        return super.createAndGet(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(IllRqstmst entity) {
        super.edit(entity);
    }

    @PUT
    @Path("Received")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData editStatusOfReceived(String receivedata) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String requestid = "";
        String reqdt = "";
        String datatype = receivedata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(receivedata);
            requestid = jsonobj.getString("requestid");
            reqdt = jsonobj.getString("receivedt");
        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(receivedata);
                requestid = stringintoxml.getdatafromxmltag(doc, "requestid");
                reqdt = stringintoxml.getdatafromxmltag(doc, "receivedt");
            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }
        String ids[] = requestid.split(",");
        for (int i = 0; i < ids.length; i++) {
            List<IllRqstmst> lirequest = null;
            lirequest = findBy("findByIllRequestNo", ids[i]);

            if (lirequest.size() > 0) {
                IllRqstmst illreq = null;
                illreq = lirequest.get(0);
                illreq.setArrivalDt(dt.getDateNTimechange(reqdt));
                illreq.setRqstStatus("A");
                illreq.setRecieveUserCode("SuperUser");
                edit(illreq);

                output += ids[i] + " Item received.,";

            } else {
                notprocess += ids[i] + " no data found.,";
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
    @Path("issuedata/{memcd}")
    @Produces({"application/xml", "application/json"})
    public List<Requestloaddata> getissuedata(@PathParam("memcd") String memcd) {
        List<Requestloaddata> lirequestdata = null;
        String sql = "select lib_nm as 'libname',title as 'title',arrival_dt as 'arrivaldt',ir.lib_cd as 'libcode',ir.ill_request_no as 'requestid' from ill_libmst im,ill_rqstmst ir where im.lib_cd=ir.lib_cd and rqst_status='A' and mem_cd='" + memcd + "'";
        Query q = em.createNativeQuery(sql, Requestloaddata.class);
        lirequestdata = q.getResultList();
        if (!(lirequestdata.size() > 0)) {
            lirequestdata = new ArrayList<>();
        }
        return lirequestdata;
    }
    
    @GET
    @Path("senddata/{libcd}")
    @Produces({"application/xml", "application/json"})
    public List<Requestloaddata> getsenddata(@PathParam("libcd") String libcd) {
        List<Requestloaddata> lirequestdata = null;
        String sql = "Select ir.mem_cd as 'memcd',mem_firstnm as 'memname',ir.title as 'title',ir.arrival_dt as 'arrivaldt',ir.issue_dt as 'issuedt',ir.recieve_dt as 'receivedt',ir.ill_request_no as 'requestid',ill.lib_nm as 'libname',ill.lib_cd as 'libcode' from m_member mm,ill_rqstmst ir,ill_libmst ill where mm.mem_cd = ir.mem_cd and  ir.rqst_status='R' and ill.lib_cd = ir.lib_cd and ir.lib_cd = '"+libcd+"'";
        Query q = em.createNativeQuery(sql, Requestloaddata.class);
        lirequestdata = q.getResultList();
        if (!(lirequestdata.size() > 0)) {
            lirequestdata = new ArrayList<>();
        }
        return lirequestdata;
    }
    
    
    
    @GET
    @Path("returndata/{memcd}")
    @Produces({"application/xml", "application/json"})
    public List<Requestloaddata> getreturndata(@PathParam("memcd") String memcd) {
        List<Requestloaddata> lirequestdata = null;
        String sql = "select lib_nm as 'libname',title as 'title',arrival_dt as 'arrivaldt',issue_dt as 'issuedt',ir.lib_cd as 'libcode',ir.ill_request_no as 'requestid' from ill_libmst im,ill_rqstmst ir where im.lib_cd=ir.lib_cd and rqst_status='I' and mem_cd='" + memcd + "'";
        Query q = em.createNativeQuery(sql, Requestloaddata.class);
        lirequestdata = q.getResultList();
        if (!(lirequestdata.size() > 0)) {
            lirequestdata = new ArrayList<>();
        }
        return lirequestdata;
    }

    @POST
    @Path("Issued")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData editStatusOfIssued(String issuemembook) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String requestid="";
        String memcode="";
        String datatype = issuemembook.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(issuemembook);
            requestid = jsonobj.getString("requestid");
            memcode= jsonobj.getString("memcd");
        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(issuemembook);
                requestid = stringintoxml.getdatafromxmltag(doc, "requestid");
               memcode=stringintoxml.getdatafromxmltag(doc, "memcd");
            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }
        String ids[]=requestid.split(",");
        MMember member = mMemberFacadeREST.find(memcode);
        
        if(member!=null){
            for(int i = 0; i < ids.length; i++) {
                List<IllRqstmst> liillrequest =null;
                liillrequest = findBy("findByIllRequestNo", ids[i]);
                if(liillrequest.size()>0){
                    IllRqstmst illRequest =null;
                    illRequest =liillrequest.get(0);
                    if(illRequest.getMemCd().equals(member.getMemCd())){
                        illRequest.setIssueDt(Todaydate);
                        illRequest.setRqstStatus("I");
                        illRequest.setIssueUserCode("Superuser");
                        edit(illRequest);
                        output+=ids[i]+" item issue to member,";
                    }else{
                        notprocess+=ids[i]+" Membercode not match with request,";
                    }
                }else{
                    notprocess+=ids[i]+" No data found,";
                }
            }
        }else{
            notprocess="No member data found..";
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
    @Path("Returned")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData editStatusOfReturned(String requestedData) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String requestid="";
        String memcode="";
        String datatype = requestedData.substring(0, 1);
        
         if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(requestedData);
            requestid = jsonobj.getString("requestid");
            memcode= jsonobj.getString("memcd");
        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(requestedData);
                requestid = stringintoxml.getdatafromxmltag(doc, "requestid");
               memcode=stringintoxml.getdatafromxmltag(doc, "memcd");
            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }
        
          String ids[]=requestid.split(",");
        MMember member = mMemberFacadeREST.find(memcode);
        
        if(member!=null){
            for(int i = 0; i < ids.length; i++) {
                List<IllRqstmst> liillrequest =null;
                liillrequest = findBy("findByIllRequestNo", ids[i]);
                if(liillrequest.size()>0){
                    IllRqstmst illRequest =null;
                    illRequest =liillrequest.get(0);
                    if(illRequest.getMemCd().equals(member.getMemCd())){
                        illRequest.setRecieveDt(Todaydate);
                        illRequest.setRqstStatus("R");
                        illRequest.setReturnUserCode("Superuser");
                        edit(illRequest);
                        output+=ids[i]+" item return successfully,";
                    }else{
                        notprocess+=ids[i]+" Membercode not match with request,";
                    }
                }else{
                    notprocess+=ids[i]+" No data found,";
                }
            }
        }else{
            notprocess="No member data found..";
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
    @Path("Requestdata")
    public List<Requestloaddata> getrequestdata() {
        List<Requestloaddata> lirequestdata = null;
        String sql = "select req.mem_cd as 'memcode',Description as 'description',title as 'title',auth1 as 'author1',auth2 as 'author2',publisher as 'publisher',pub_year as 'publisherYear',rqst_dt as 'requestDt',lib_nm as 'libname',req.lib_cd as 'libcode',req.ill_request_no as 'requestid',req.media as 'mediatype' from ill_rqstmst req,LibMaterials mm,m_member mem,ill_libmst ill where ill.lib_cd = req.lib_cd and mm.code = req.media and mem.mem_cd = req.mem_cd and rqst_status='P'";
        Query q = em.createNativeQuery(sql, Requestloaddata.class);
        lirequestdata = q.getResultList();
        if (!(lirequestdata.size() > 0)) {
            lirequestdata = new ArrayList<>();
        }
        return lirequestdata;
    }

    @GET
    @Path("recivedata/{libcode}")
    public List<ILLitemreceive> recivedatadata(@PathParam("libcode") String libcode) {
        List<ILLitemreceive> lirequestdata = null;
        String sql = "Select ir.mem_cd as 'memcd',mem_firstnm as 'mem_name',ir.title as 'title',ir.recieve_dt as 'recievedt',ir.ill_request_no as 'requestid' from m_member mm,ill_rqstmst ir where mm.mem_cd = ir.mem_cd and lib_cd='" + libcode + "' and ir.rqst_status='P'";
        Query q = em.createNativeQuery(sql, ILLitemreceive.class);
        lirequestdata = q.getResultList();
        if (!(lirequestdata.size() > 0)) {
            lirequestdata = new ArrayList<>();
        }
        return lirequestdata;
    }

    @DELETE
    @Path("{id}")
    public void removebyrequestno(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public IllRqstmst find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<IllRqstmst> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<IllRqstmst> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<IllRqstmst> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {

        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        System.out.println(valueString);
        switch (query) {

            case "findByRqstStatus":
                valueList.add(valueString[0].charAt(0));
                break;
            case "findByLibCd":
                valueList.add(valueString[0].charAt(0));
                break;
            case "findByMemCdAndStatus":
                valueList.add(valueString[0]);// used in borrowing to get list of all received(A)/issued(I) request
                valueList.add(valueString[1].charAt(0));
                break;
            case "findByLibCdAndStatus":
                valueList.add(valueString[0]);// used in borrowing to get list of all received(A)/issued(I) request
                valueList.add(valueString[1].charAt(0));
                break;
            case "findByIllRequestNo":
                valueList.add(Integer.valueOf(valueString[0]));
                break;
            default:
                valueList.add(valueString[0]);
            //findByMemCd
        }
        return super.findBy("IllRqstmst." + query, valueList);
    }

    //For converting json data to xml using javascript 
    //http://goessner.net/download/prj/jsonxml/
    //http://www.xml.com/pub/a/2006/05/31/converting-between-xml-and-json.html
    //https://stackoverflow.com/questions/1773550/convert-xml-to-json-and-back-using-javascript?noredirect=1&lq=1
    //Reminder for non supply of books
    //THis method will get the details of the members and issued item by the member to send the reminder to suppy books
    @GET
    @Path("ReminderLetter/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> ReminderLetterforNonSupplyofBooks(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
            case "byMemberCode":
                q = "SELECT m_member.mem_cd as lib_cd , mem_firstnm +  mem_lstnm as lib_nm, mem_prmntadd1 as lib_add1, mem_prmntadd2 as lib_add2, "
                        + " mem_prmntcity as lib_city, mem_prmntpin as lib_pin, mem_prmntphone as lib_phone , \n"
                        + " mem_email as lib_email ,  ill_rqstmst.title, ill_rqstmst.auth1, ill_rqstmst.auth2, ill_rqstmst.publisher, "
                        + " ill_rqstmst.pub_year,LibMaterials.Description,letter_formats.letter_format, letter_formats.subject, \n"
                        + " letter_formats.letter_fullname   \n"
                        + " FROM LibMaterials, ill_rqstmst, ill_libmst, letter_formats ,m_member \n"
                        + " WHERE  LibMaterials.Code = ill_rqstmst.media \n"
                        + " AND ill_rqstmst.lib_cd = ill_libmst.lib_cd  and  letter_formats.letter_name = 'remin_lett' \n"
                        + " and ill_rqstmst.rqst_status='I' and ill_rqstmst.mem_cd='" + Paramvalue + "' and m_member.mem_cd = ill_rqstmst.mem_cd";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }

    //Details for Return on Inter library Loan
    @GET
    @Path("ReturnBorrowingBook/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> ReturnBorrowingBook(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
            case "byMemberCode":
                q = "select lib_nm,title,arrival_dt,issue_dt,ir.lib_cd as lib_cd from ill_libmst im,ill_rqstmst ir "
                        + "where im.lib_cd = ir.lib_cd and rqst_status = 'I' and mem_cd = '" + Paramvalue + "'";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }

    //Details for Return on Inter library Loan
    @GET
    @Path("ListIllItems/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> ListIllItems(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
            case "byIssueDateBetween":
                //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                q = "SELECT  ill_reqprocdmp.mem_cd, m_member.mem_tag, m_member.mem_firstnm, m_member.mem_midnm, m_member.mem_lstnm, ill_reqprocdmp.lib_cd,  \n"
                        + " ill_libmst.lib_nm, ill_reqprocdmp.title, ill_reqprocdmp.arr_dt, ill_reqprocdmp.iss_dt  FROM m_member, ill_reqprocdmp, ill_libmst \n"
                        + " WHERE m_member.mem_cd = ill_reqprocdmp.mem_cd AND ill_reqprocdmp.lib_cd = ill_libmst.lib_cd and \n"
                        + "iss_dt BETWEEN '" + valueString[0] + "' AND '" + valueString[1] + "'";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }

    //Inter library loan request letter
    //this method will return the details of library items that are issued under IIL and details will be used for generating letter to Library
    @GET
    @Path("RequestToLibrary/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> IILRequestLetter(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
            case "byLibraryCode":
                q = "SELECT ill_libmst.lib_nm, ill_libmst.lib_cd, ill_libmst.lib_add1, ill_libmst.lib_add2, ill_libmst.lib_city, "
                        + "ill_libmst.lib_pin, ill_libmst.lib_phone, ill_libmst.lib_email,  ill_rqstmst.title, ill_rqstmst.auth1, "
                        + "ill_rqstmst.auth2, ill_rqstmst.publisher, ill_rqstmst.pub_year,LibMaterials.Description,letter_formats.letter_format, "
                        + "letter_formats.subject, letter_formats.letter_fullname   "
                        + "FROM LibMaterials, ill_rqstmst, ill_libmst, letter_formats "
                        + "WHERE(LibMaterials.Code = ill_rqstmst.media) AND ill_rqstmst.lib_cd = ill_libmst.lib_cd  and  "
                        + "letter_formats.letter_name ='ILL_Request' and ill_rqstmst.rqst_status='P' and ill_libmst.lib_cd = '" + Paramvalue + "'";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }

    //Arrival Report to library
    //this method will return the details of library items that are requested under IIL and received and details will be used for generating arrival report
    @GET
    @Path("ArrivalReport/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> IILArrivalReport(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
            case "byLibraryCode":
                q = "SELECT     m_member.mem_cd as lib_cd , mem_firstnm +' '+  mem_lstnm as lib_nm, mem_prmntadd1 as lib_add1, "
                        + "mem_prmntadd2 as lib_add2, mem_prmntcity as lib_city, mem_prmntpin as lib_pin, mem_prmntphone as lib_phone , "
                        + "mem_email as lib_email ,  ill_rqstmst.title, ill_rqstmst.auth1, ill_rqstmst.auth2, ill_rqstmst.publisher, "
                        + "ill_rqstmst.pub_year,LibMaterials.Description,letter_formats.letter_format, letter_formats.subject, "
                        + "letter_formats.letter_fullname   "
                        + "FROM LibMaterials, ill_rqstmst, ill_libmst, letter_formats ,m_member "
                        + "WHERE  LibMaterials.Code = ill_rqstmst.media AND ill_rqstmst.lib_cd = ill_libmst.lib_cd  and  "
                        + "letter_formats.letter_name = 'ILL_Arrival' and ill_rqstmst.rqst_status='A' and ill_rqstmst.lib_cd='" + Paramvalue + "' "
                        + "and m_member.mem_cd = ill_rqstmst.mem_cd";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }

}
