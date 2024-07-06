/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.json.JsonObjectBuilder;
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
import javax.ws.rs.core.PathSegment;
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.catalogue.Location;
import soul.catalogue.service.LocationFacadeREST;
import soul.circulation.CtgryMediaIssueReserve;
import soul.circulation.MMember;
import soul.circulation.Memissueinfo;
import soul.circulation.TIssue;
//import soul.circulation.TMemdue;
import soul.circulation.TMemfine;
import soul.circulation.TMemrcpt;
import soul.circulation.TReceive;
import soul.circulation.TReserve;
import soul.circulation.TReservePK;
import soul.circulation.TrevsDetails;
import soul.circulation_master.MCtgry;
import soul.circulation_master.MCtgryMedia;
import soul.circulation_master.MCtgryMediaPK;
import soul.circulation_master.service.MCtgryFacadeREST;
import soul.circulation_master.service.MCtgryMediaFacadeREST;
import soul.response.StringprocessData;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.treserve")
public class TReserveFacadeREST extends AbstractFacade<TReserve> {

    @EJB
    private TIssueFacadeREST tIssueFacadeREST;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private MMemberFacadeREST mMemberFacadeREST;
    @EJB
    private MCtgryFacadeREST mCtgryFacadeREST;
    @EJB
    private MCtgryMediaFacadeREST mCtgryMediaFacadeREST;

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    Date Todaydate = new Date();
//    MMember member;
//    Location location;
//    TIssue issue;
//    TReceive receive;
// //   TMemdue due;
//    TMemfine fine;
//    TMemrcpt receipt;
//    TReserve reserve;
//    List<Location> locationList;
//    List<TReserve> reserveList;

    private TReservePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;memCd=memCdValue;recordNo=recordNoValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        soul.circulation.TReservePK key = new soul.circulation.TReservePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> memCd = map.get("memCd");
        if (memCd != null && !memCd.isEmpty()) {
            key.setMemCd(memCd.get(0));
        }
        java.util.List<String> recordNo = map.get("recordNo");
        if (recordNo != null && !recordNo.isEmpty()) {
            key.setRecordNo(new java.lang.Integer(recordNo.get(0)));
        }
        return key;
    }

    public TReserveFacadeREST() {
        super(TReserve.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TReserve entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TReserve entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        soul.circulation.TReservePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @DELETE
    @Path("{memcd}/{recid}")
    public void removebyMemcdNRecid(@PathParam("memcd") String memcd, @PathParam("recid") int recid) {
        TReservePK tk = new TReservePK();
        tk.setMemCd(memcd);
        tk.setRecordNo(recid);
        super.remove(super.find(tk));
    }

    @POST
    @Path("deletereservation")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData removebyrevdata(String deletedata) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String recid = "";
        String memcd = "";
        String datatype = deletedata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(deletedata);
            memcd = jsonobj.getString("memcd");
            recid = jsonobj.getString("recid");

        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(deletedata);
                memcd = stringintoxml.getdatafromxmltag(doc, "memcd");
                recid = stringintoxml.getdatafromxmltag(doc, "recid");

            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }
        String memcds[] = memcd.split(",");
        MMember m;
        List<TReserve> litreserv = null;
        litreserv = findBy("findByRecordNo", recid);
        List<String> limember = null;
        if (litreserv.size() > 0) {
            int srno = 1;
            limember = new ArrayList<>();
            // check all member belog to the recid;+

            for (int j = 0; j < memcds.length; j++) {
                m = null;
                int process = 0;
                m = mMemberFacadeREST.find(memcds[j]);
                if (m != null) {
                    for (int i = 0; i < litreserv.size(); i++) {
                        if (litreserv.get(i).getTReservePK().getMemCd().equals(m.getMemCd())) {
                            limember.add(m.getMemCd());
                            process = 1;
                        }
                    }
                    if (process == 0) {
                        notprocess += memcds[j] + " member not found,";
                    }

                } else {
                    notprocess += memcds[j] + " member not found,";
                }
            }

            for (int i = 0; i < limember.size(); i++) {
                removebyMemcdNRecid(limember.get(i), Integer.valueOf(recid));
            }

            List<TReserve> litreservnew = null;
            String sql = "SELECT * FROM t_reserve where record_no='" + recid + "'" + "ORDER BY t_reserve.sr_no ASC";
            Query q = em.createNativeQuery(sql, TReserve.class);
            litreservnew = q.getResultList();

            if (litreservnew.size() > 0) {
                int newsrno = 1;
                for (int k = 0; k < litreservnew.size(); k++) {
                    String sql2 = "update t_reserve set sr_no='" + newsrno + "' where record_no='" + recid + "' and mem_cd='" + litreservnew.get(k).getTReservePK().getMemCd() + "'";
                    Query update2 = em.createNativeQuery(sql2);
                    update2.executeUpdate();
                    newsrno++;
                    output += litreservnew.get(k).getTReservePK().getMemCd() + ",";
                }
            }else{
                output="All data deleted..";
            }

        } else {
            notprocess = "No data found for this book..";
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
    @Path("removebyRecid/{recid}")
    public void removebyRecid(@PathParam("recid") int recid) {
        Query query;
        query = em.createNativeQuery("delete from t_reserve where record_no='" + recid + "'");
        query.executeUpdate();
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public TReserve find(@PathParam("id") PathSegment id) {
        soul.circulation.TReservePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TReserve> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TReserve> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("count/by/{namedQuery}/{values}")
    @Produces("text/plain")
    public String countBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();

        switch (query) {
            default:
                valueList.add(valueString[0]);
            //used for CountByMemCd
        }
        return String.valueOf(super.countBy("TReserve." + query, valueList));
    }

    @GET
    @Path("memreservinfobyaccno/{accno}")
    public List<Memissueinfo> getreservdetailsbyaccno(@PathParam("accno") String accno) {
        TReserve t;
        List<TReserve> litreserv;
        List<Memissueinfo> limemissueinfo = null;
        List<Location> liloc = locationFacadeREST.getByAcc(accno);
        if (liloc.size() > 0) {
            litreserv = findBy("findByRecordNo", String.valueOf(liloc.get(0).getLocationPK().getRecID()));
        } else {
            litreserv = null;
        }

        if (litreserv.size() > 0) {
            t = litreserv.get(0);
        } else {
            t = null;
        }

        if (t != null) {
            Query query;
            String q = "select m_member.mem_cd as memcd,m_member.mem_id as memid,concat(m_member.mem_firstnm,' ',m_member.mem_lstnm) as memname,m_member.mem_status as Status,f1.Fclty_dept_dscr as dept,f2.Fclty_dept_dscr as inst,m_ctgry.ctgry_desc as crgty,m_branchmaster.Branch_name as branch,t_reserve.resv_dt as reservdt,t_reserve.hold_dt as holddt,t_reserve.sr_no as srno from m_member,m_fcltydept f1,m_fcltydept f2,m_ctgry,m_branchmaster,t_reserve,location where m_member.mem_dept=f1.Fclty_dept_cd and f1.Fld_tag='D' and m_member.mem_ctgry=m_ctgry.ctgry_cd and m_member.mem_inst=f2.Fclty_dept_cd and f2.Fld_tag='I' and m_branchmaster.branch_cd=m_member.mem_degree and m_member.mem_cd=t_reserve.mem_cd and t_reserve.record_no=location.RecID and location.p852='" + accno + "'";
            query = getEntityManager().createNativeQuery(q, Memissueinfo.class);
            limemissueinfo = query.getResultList();

        } else {
            limemissueinfo = new ArrayList<>();
        }
        return limemissueinfo;
    }

    //Added Manually
    @GET
    @Path("by/{namedQuery}/{attrValue}")
    @Produces({"application/xml", "application/json"})
    public List<TReserve> findBy(@PathParam("namedQuery") String query, @PathParam("attrValue") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();

        System.out.println("namedQuery--" + query.toString() + "---" + values);

        switch (query) {
            case "findByRecNoAndMaxSrNo":
                valueList.add(Integer.parseInt(valueString[0]));
                break;
            case "findByRecNoAndMinSrNo":
                valueList.add(Integer.parseInt(valueString[0]));// used in issue in transaction
                break;
            case "findByResvDtBtwn":
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    valueList.add(dateFormat1.parse(valueString[0]));
                    valueList.add(dateFormat1.parse(valueString[1]));

                } catch (ParseException ex) {
                    Logger.getLogger(MMemberFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "findByRecordNo":
                valueList.add(Integer.parseInt(valueString[0]));// used in issue in transaction
                break;
            case "findByMemCd":
                valueList.add(valueString[0]);// used in issue in transaction
                break;

            default:
                valueList.add(valueString[0]);
            //
        }
        // System.out.println("TReserve---"+super.findBy("TReserve."+query, valueList));
        return super.findBy("TReserve." + query, valueList);
    }

//   @DELETE
//   @Path("removeByMemcdAndRecordNo")
    public void removeByMemcdAndRecordNo(String memCd, int recordNo) {
        //String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();

        valueList.add(memCd);
        valueList.add(recordNo);

        super.removeBy("TReserve.removeByMemcdAndRecordNo", valueList);
    }
    //String output = "";

    @POST
    @Path("reserve")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData reserve(String reservdata) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String accno = "";
        String memcd = "";
        String datatype = reservdata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(reservdata);
            memcd = jsonobj.getString("memcd");
            accno = jsonobj.getString("accno");

        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(reservdata);
                memcd = stringintoxml.getdatafromxmltag(doc, "memcd");
                accno = stringintoxml.getdatafromxmltag(doc, "accno");

            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }
        String accnos[] = accno.split(",");
        MMember m = null;
        m = mMemberFacadeREST.find(memcd);
        int skip = 0;
        List<MCtgryMedia> limemctmedia = null;
        List<Integer> memrecid = new ArrayList<>();
        Map<String, Integer> catmediamapwithcodeNmaxallowed = new HashMap<>();
        Map<String, Integer> reservemeadiacodeNcount = new HashMap<>();
        Map<String, Integer> materialcodenduration = new HashMap<>();
        if (m != null) {
            //check  max material book allow.
            limemctmedia = mCtgryMediaFacadeREST.findBy("findByCtgryCd", m.getMemCtgry().getCtgryCd());

            if (limemctmedia.size() > 0) {
                for (int i = 0; i < limemctmedia.size(); i++) {
                    // store individual material code with no of books allowed
                    catmediamapwithcodeNmaxallowed.put(limemctmedia.get(i).getMCtgryMediaPK().getMediaCd(), limemctmedia.get(i).getMaxReserve());
                    // store individual material code with allowed periods
                    materialcodenduration.put(limemctmedia.get(i).getMCtgryMediaPK().getMediaCd(), limemctmedia.get(i).getResPeriod());
                }
                System.out.println("catmediamapwithcodeNmaxallowed :" + catmediamapwithcodeNmaxallowed);
                System.out.println("materialcodenduration :" + materialcodenduration);
            } else {
                skip = 1;
            }

            if (skip == 0) {
                List<TReserve> lireves = null;
                lireves = findBy("findByMemCd", m.getMemCd());
                if (lireves.size() > 0) {
                    for (int i = 0; i < lireves.size(); i++) {
                        // store recid in list who reserved by member
                        memrecid.add(lireves.get(i).getTReservePK().getRecordNo());

                        // find number of materials reserved by member
                        List<Location> liloc = null;
                        liloc = locationFacadeREST.findBy("findByRecID", String.valueOf(lireves.get(i).getTReservePK().getRecordNo()));
                        if (liloc.size() > 0) {
                            if (reservemeadiacodeNcount.isEmpty()) {
                                reservemeadiacodeNcount.put(liloc.get(0).getMaterial().getCode(), 1);
                            } else {
                                if (reservemeadiacodeNcount.containsKey(liloc.get(0).getMaterial().getCode())) {
                                    int previouscount = reservemeadiacodeNcount.get(liloc.get(0).getMaterial().getCode());
                                    previouscount++;
                                    reservemeadiacodeNcount.replace(liloc.get(0).getMaterial().getCode(), previouscount);
                                } else {
                                    reservemeadiacodeNcount.put(liloc.get(0).getMaterial().getCode(), 1);
                                }
                            }
                        }
                    }
                }
            }

            if (skip == 0) {
                for (int i = 0; i < accnos.length; i++) {
                    List<Location> liloc = null;
                    liloc = locationFacadeREST.getByAcc(accnos[i]);
                    if (liloc.size() > 0) {
                        Location ln = null;
                        ln = liloc.get(0);
                        if (ln.getStatus().equals("IS") || ln.getStatus().equals("AR") || ln.getStatus().equals("IR")) {

                            int reserveprocess = 0;

                            //check book is already reserved by member or not
                            if (memrecid.size() > 0) {
                                boolean bs = memrecid.contains(ln.getLocationPK().getRecID());
                                if (bs) {
                                    reserveprocess = 1;
                                }
                            }

                            //check member already issue this book or not
                            List<TIssue> litissue = null;
                            litissue = tIssueFacadeREST.findBy("findByAccNo", accnos[i]);
                            if (litissue.size() > 0) {
                                for (int j = 0; j < litissue.size(); j++) {
                                    if (litissue.get(j).getTIssuePK().getMemCd().equals(m.getMemCd())) {
                                        reserveprocess = 2;
                                    }
                                }
                            }

                            if (reserveprocess == 0) {
                                int processtoreserve = 0;
                                String accnonumber = "";
                                // check multiple book is available or not by status 'AV'
                                List<Location> limultibook = null;
                                limultibook = locationFacadeREST.findBy("findByRecID", String.valueOf(ln.getLocationPK().getRecID()));
                                if (limultibook.size() > 0) {
                                    for (int k = 0; k < limultibook.size(); k++) {
                                        if (limultibook.get(k).getStatus().equals("AV")) {
                                            accnonumber = limultibook.get(k).getLocationPK().getP852();
                                            k = limultibook.size();
                                            processtoreserve = 1;
                                        }
                                    }
                                }

                                if (ln.getB852().isEmpty()) {
                                    processtoreserve = 2;
                                }

                                if (processtoreserve == 0) {
                                    int maxbookreser = 0;
                                    if (catmediamapwithcodeNmaxallowed.containsKey(ln.getMaterial().getCode())) {
                                        maxbookreser = catmediamapwithcodeNmaxallowed.get(ln.getMaterial().getCode());
                                        int currentreserve = 0;
                                        if (reservemeadiacodeNcount.containsKey(ln.getMaterial().getCode())) {
                                            currentreserve = reservemeadiacodeNcount.get(ln.getMaterial().getCode());
                                        }

                                        if (currentreserve < maxbookreser) {
                                            List<TReserve> lirevesbyrecid = null;
                                            lirevesbyrecid = findBy("findByRecordNo", String.valueOf(ln.getLocationPK().getRecID()));
                                            int srno = 1;
                                            if (lirevesbyrecid.size() > 0) {
                                                List<TReserve> litrmaxsrno = findBy("findByRecNoAndMaxSrNo", String.valueOf(ln.getLocationPK().getRecID()));
                                                srno = litrmaxsrno.get(0).getSrNo();
                                                srno++;
                                            }
                                            System.err.println("old date : " + Todaydate);
                                            Calendar cl = Calendar.getInstance();
                                            cl.add(Calendar.DATE, materialcodenduration.get(ln.getMaterial().getCode()));
                                            System.out.println("onhold dt : " + cl.getTime());
                                            TReservePK trpk = new TReservePK();
                                            trpk.setMemCd(m.getMemCd());
                                            trpk.setRecordNo(ln.getLocationPK().getRecID());
                                            TReserve tr = new TReserve();
                                            tr.setTReservePK(trpk);
                                            tr.setResvDt(Todaydate);
                                            tr.setHoldDt(cl.getTime());
                                            tr.setSrNo(srno);
                                            tr.setUserCd("superuser");
                                            tr.setMMember(m);

                                            create(tr);

                                            if (ln.getStatus().equals("IS") || ln.getStatus().equals("IR")) {
                                                ln.setStatus("IR");
                                            } else {
                                                ln.setStatus("AR");
                                            }

                                            locationFacadeREST.edit(ln);

                                            output += accnos[i] + " Reserved successfully,";

                                        } else {
                                            notprocess += accnos[i] + " Member has exccesed privilege,";
                                        }
                                    } else {
                                        notprocess += accnos[i] + " Member has not enough privilege to reserve this book,";
                                    }

                                } else {
                                    if (processtoreserve == 1) {
                                        notprocess += " Book copy already availabe accesion no : " + accnonumber + ",";
                                    } else if (processtoreserve == 2) {
                                        notprocess += accnos[i] + " Book not have collection type please enter it,";
                                    }
                                }

                            } else {
                                if (reserveprocess == 1) {
                                    notprocess += accnos[i] + " Book is already reserved by member " + m.getMemCd() + ",";
                                } else if (reserveprocess == 2) {
                                    notprocess += accnos[i] + " Book is already issued by member " + m.getMemCd() + ",";
                                }
                            }

                        } else {
                            if (ln.getStatus().equals("AV")) {
                                notprocess += accnos[i] + " Book availabe to issue,";
                            } else {
                                notprocess += accnos[i] + " Book status in not Issue,Reserve or ON-hold,";
                            }

                        }
                    } else {
                        notprocess += accnos[i] + " Book not found,";
                    }
                }
            } else {
                notprocess = "Member not have privileges..";
            }

        } else {
            notprocess = "Member not available..";
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
    @Path("setreservationorder")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData setnewsrno(String setneworder) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String recid = "";
        String memcd = "";
        String datatype = setneworder.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(setneworder);
            memcd = jsonobj.getString("memcd");
            recid = jsonobj.getString("recid");

        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(setneworder);
                memcd = stringintoxml.getdatafromxmltag(doc, "memcd");
                recid = stringintoxml.getdatafromxmltag(doc, "recid");

            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }
        String memcds[] = memcd.split(",");
        MMember m;
        List<TReserve> litreserv = null;
        litreserv = findBy("findByRecordNo", recid);
        List<String> limember = null;
        if (litreserv.size() > 0) {
            int srno = 1;
            limember = new ArrayList<>();
            // check all member belog to the recid;+

            for (int j = 0; j < memcds.length; j++) {
                m = null;
                int process = 0;
                m = mMemberFacadeREST.find(memcds[j]);
                if (m != null) {
                    for (int i = 0; i < litreserv.size(); i++) {
                        if (litreserv.get(i).getTReservePK().getMemCd().equals(m.getMemCd())) {
                            limember.add(m.getMemCd());
                            process = 1;
                        }
                    }
                    if (process == 0) {
                        notprocess += memcds[j] + " member not found,";
                    }

                } else {
                    notprocess += memcds[j] + " member not found,";
                }
            }

            for (int i = 0; i < limember.size(); i++) {
                String sql = "update t_reserve set sr_no='" + srno + "' where record_no='" + recid + "' and mem_cd='" + limember.get(i) + "'";
                Query update = em.createNativeQuery(sql);
                update.executeUpdate();
                srno++;
                output += limember.get(i) + ",";
            }

        } else {
            notprocess = "No data found for this book..";
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

    public Boolean checkPrivilege(Location location, MMember member, String operation) {
        Boolean allowed = false;
        CtgryMediaIssueReserve privilege;
        // GenericType<List<CtgryMediaIssueReserve>> privilegeGenericType = new GenericType<List<CtgryMediaIssueReserve>>(){};

//       vijay chnage
//       switch(operation)
//        {
//            case "Reserve":     privilege = ctgryMediaIssueReserveFacadeREST.findBy("findByCtgryCdAndMediaCd", member.getMemCtgry().getCtgryCd()+","+location.getMaterial().getCode()).get(0);  
//                                if(privilege.getResCount() == null || privilege.getResCount().intValue() < privilege.getMaxReserve())
//                                    allowed = true;
//                                break;
//        }
        switch (operation) {
            case "Reserve":
                MCtgry mCtgry = mCtgryFacadeREST.find(member.getMemCtgry().getCtgryCd());
                String isscount = countBy("countByMemCd", member.getMemCd());
                if (isscount == null || (mCtgry.getMaxBookAllow() - Integer.parseInt(isscount) > 0)) {
                    allowed = true;
                }
                break;
        }

        return allowed;
    }

    @GET
    @Path("getReservedFor/{accNos}")
    //  @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    @Produces("text/plain")
    // public String getReservedFor(@QueryParam("accNos") String acc_Nos)
    public String getReservedFor(@PathParam("accNos") String acc_Nos) {
        String output="";
        Location location;
        TReserve reserve;
        //Gets Member Code for given accNo with min SrNo(Max Prority) book is reserved for
        String[] accNos = acc_Nos.split(",");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        for (int i = 0; i < accNos.length; i++) {
            location = locationFacadeREST.findBy("findByP852", accNos[i]).get(0);
            reserve = findBy("findByRecNoAndMinSrNo", Integer.toString(location.getLocationPK().getRecID())).get(0);
            objectBuilder.add(location.getLocationPK().getP852(), reserve.getMMember().getMemCd());
        }
        output = objectBuilder.build().toString();
        return output;
    }

    @GET
    @Path("memrevsinfo/{memcd}")
    public List<TrevsDetails> getreservmeminfo(@PathParam("memcd") String memcd) {
        MMember m;

        List<TrevsDetails> libbmeminfo = null;
        m = mMemberFacadeREST.find(memcd);
        if (m != null) {
            Query query;
            String q = "select DISTINCT b1.FValue as Bookname,b2.FValue as author,t.mem_cd as memcode,t.resv_dt as resvdt,t.hold_dt as holddt,t.user_cd as usercd,t.sr_no as srno,bk.status_dscr as status,t.record_no as recodid from biblidetails b left JOIN biblidetails b1 on b.RecID=b1.RecID and b1.Tag='245' and b1.SbFld='a' left join  biblidetails b2 on b1.RecID=b2.RecID and (b2.Tag='100' or b2.Tag='700') and b2.SbFld='a'  INNER join t_reserve t on b.RecID=t.record_no  INNER join location l on l.RecID=t.record_no and l.Status='IR' INNER JOIN m_bkstatus bk on l.Status=bk.bk_issue_stat where t.mem_cd='" + m.getMemCd() + "'";
            query = getEntityManager().createNativeQuery(q, TrevsDetails.class);
            libbmeminfo = query.getResultList();

        } else {
            libbmeminfo = new ArrayList<>();
        }
        return libbmeminfo;
    }

    //Item Reserved Over A Period
    //This method will return the list of items which are reserved over a period
    @GET
    @Path("ItemReservedOverAPeriod/{Paramname}/{Paramvalue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> ItemReservedOverAPeriod(@PathParam("Paramname") String Paramname, @PathParam("Paramvalue") String Paramvalue) throws ParseException {
        String q = "";
        String[] valueString = Paramvalue.split(",");
        List<Object> result = new ArrayList<>();
        Query query;
        switch (Paramname) {
            case "byReservedDateBetween":
                q = "select m_member.mem_cd, m_member.mem_tag, m_member.mem_firstnm, m_member.mem_midnm, m_member.mem_lstnm, Location.RecID, \n"
                        + "	Location.p852, Biblidetails.FValue, t_reserve.resv_dt, t_reserve.user_cd, t_reserve.sr_no, t_reserve.hold_dt  \n"
                        + "    from t_reserve join m_member on m_member.mem_cd = t_reserve.mem_cd\n"
                        + "	join Location on t_reserve.record_no = Location.RecID\n"
                        + "	join Biblidetails on Location.RecID = Biblidetails.RecID\n"
                        + "	where (t_reserve.mem_cd = m_member.mem_cd) and (t_reserve.record_no = Location.RecID) and (t_reserve.resv_dt BETWEEN '" + valueString[0] + "' AND '" + valueString[1] + "')\n"
                        + "	and (Location.p852 IS NOT NULL) AND (Biblidetails.Tag = '245') AND (Biblidetails.SbFld = 'a');";
                break;
        }
        //List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();
        return result;
    }
}
