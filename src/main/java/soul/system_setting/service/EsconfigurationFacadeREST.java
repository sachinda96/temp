/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.system_setting.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;
import org.w3c.dom.Document;
import soul.response.StringprocessData;
import soul.system_setting.Esconfiguration;
import soul.util.function.ConvertStringIntoJson;
import soul.util.function.ConvertStringIntoXml;

/**
 *
 * @author admin
 */
@Stateless
@Path("soul.system_setting.esconfiguration")
public class EsconfigurationFacadeREST extends AbstractFacade<Esconfiguration> {

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public EsconfigurationFacadeREST() {
        super(Esconfiguration.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Esconfiguration entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Esconfiguration entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Esconfiguration find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Esconfiguration> findAll() {
        return super.findAll();
    }

    //Added mannually
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<Esconfiguration> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();

        switch (query) {
            default:
                valueList.add(valueString[0]);
            //used for findByCollectionType
        }
        return super.findBy("Esconfiguration." + query, valueList);
    }

    @POST
    @Path("addemailconfig")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData createemaildata(String emaildata) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String domainname = "";
        String port = "";
        String Emailid = "";
        String username = "";
        String smtp = "";
        String password = "";
        String ssl = "";

        String datatype = emaildata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(emaildata);
            domainname = jsonobj.getString("domainname");
            port = jsonobj.getString("port");
            Emailid = jsonobj.getString("Emailid");
            username = jsonobj.getString("username");
            smtp = jsonobj.getString("smtp");
            password = jsonobj.getString("password");
            ssl = jsonobj.getString("ssl");

        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(emaildata);
                domainname = stringintoxml.getdatafromxmltag(doc, "domainname");
                port = stringintoxml.getdatafromxmltag(doc, "port");
                Emailid = stringintoxml.getdatafromxmltag(doc, "Emailid");
                username = stringintoxml.getdatafromxmltag(doc, "username");
                smtp = stringintoxml.getdatafromxmltag(doc, "smtp");
                password = stringintoxml.getdatafromxmltag(doc, "password");
                ssl = stringintoxml.getdatafromxmltag(doc, "ssl");

            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }

        Map<String, String> emailekrvaluemap = new HashMap<>();
        emailekrvaluemap.put("DomainName", domainname);
        emailekrvaluemap.put("SMTP", smtp);
        emailekrvaluemap.put("Port", port);
        emailekrvaluemap.put("EmailId", Emailid);
        emailekrvaluemap.put("Username", username);
        emailekrvaluemap.put("Password", password);
        emailekrvaluemap.put("SSL_Enable", ssl);
        try {
            for (Map.Entry<String, String> e : emailekrvaluemap.entrySet()) {
                Esconfiguration es = new Esconfiguration();
                es.setIdentity("Email");
                es.setDescription(e.getKey());
                es.setVdata(e.getValue());

                create(es);
                output = "Configuration set successfuly..";
            }
        } catch (Exception e) {
            output = "";
            notprocess = "Somting wrong Configuration not set successfuly..";
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
    @Path("editemailconfig")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData editemaildata(String editemaildata) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String domainname = "";
        String port = "";
        String Emailid = "";
        String username = "";
        String smtp = "";
        String password = "";
        String ssl = "";

        String datatype = editemaildata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(editemaildata);
            domainname = jsonobj.getString("domainname");
            port = jsonobj.getString("port");
            Emailid = jsonobj.getString("Emailid");
            username = jsonobj.getString("username");
            smtp = jsonobj.getString("smtp");
            password = jsonobj.getString("password");
            ssl = jsonobj.getString("ssl");

        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(editemaildata);
                domainname = stringintoxml.getdatafromxmltag(doc, "domainname");
                port = stringintoxml.getdatafromxmltag(doc, "port");
                Emailid = stringintoxml.getdatafromxmltag(doc, "Emailid");
                username = stringintoxml.getdatafromxmltag(doc, "username");
                smtp = stringintoxml.getdatafromxmltag(doc, "smtp");
                password = stringintoxml.getdatafromxmltag(doc, "password");
                ssl = stringintoxml.getdatafromxmltag(doc, "ssl");

            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }

        Map<String, String> emailekrvaluemap = new HashMap<>();
        emailekrvaluemap.put("DomainName", domainname);
        emailekrvaluemap.put("SMTP", smtp);
        emailekrvaluemap.put("Port", port);
        emailekrvaluemap.put("EmailId", Emailid);
        emailekrvaluemap.put("Username", username);
        emailekrvaluemap.put("Password", password);
        emailekrvaluemap.put("SSL_Enable", ssl);
        
        List<Esconfiguration> liesconfig = findBy("findByIdentity","Email");
       
        try {
            for (Map.Entry<String, String> e : emailekrvaluemap.entrySet()) {
                
                for(int i=0;i<liesconfig.size();i++){
                    if(liesconfig.get(i).getDescription().equals(e.getKey())){
                        Esconfiguration es = null;
                        es = liesconfig.get(i);
                        es.setVdata(e.getValue());
                        
                        edit(es.getId(),es);
                    }
                }
             
                output = "Configuration set successfuly..";
            }
        } catch (Exception e) {
            output = "";
            notprocess = "Somting wrong Configuration not set successfuly..";
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
    @Path("editsmsconfig")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData editsmsdata(String editsmsdata) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
 String url="";
        String username="";
        String password="";
        String failr="";
        String successr="";
        String pendingr="";
        String msgendoder="";
        String api="";
        String datatype = editsmsdata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(editsmsdata);
            url = jsonobj.getString("url");
            username = jsonobj.getString("username");
            password = jsonobj.getString("password");
            failr = jsonobj.getString("failr");
            successr = jsonobj.getString("successr");
            pendingr = jsonobj.getString("pendingr");
            msgendoder = jsonobj.getString("msgendoder");
            api = jsonobj.getString("api");
            

        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(editsmsdata);
                url = stringintoxml.getdatafromxmltag(doc, "url");
                username = stringintoxml.getdatafromxmltag(doc, "username");
                password = stringintoxml.getdatafromxmltag(doc, "password");
                failr = stringintoxml.getdatafromxmltag(doc, "failr");
                successr = stringintoxml.getdatafromxmltag(doc, "successr");
                pendingr = stringintoxml.getdatafromxmltag(doc, "pendingr");
                msgendoder = stringintoxml.getdatafromxmltag(doc, "msgendoder");
                api = stringintoxml.getdatafromxmltag(doc, "api");

            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }
        
        Map<String, String> emailekrvaluemap = new HashMap<>();
        emailekrvaluemap.put("Url", url);
        emailekrvaluemap.put("UserName", username);
        emailekrvaluemap.put("Password", password);
        emailekrvaluemap.put("Fail_R", failr);
        emailekrvaluemap.put("Success_R", successr);
        emailekrvaluemap.put("Pending_R", pendingr);
        emailekrvaluemap.put("MsgEncoder", msgendoder);
        emailekrvaluemap.put("API", api);
        
         List<Esconfiguration> liesconfig = findBy("findByIdentity","SMS");
       
        try {
            for (Map.Entry<String, String> e : emailekrvaluemap.entrySet()) {
                
                for(int i=0;i<liesconfig.size();i++){
                    if(liesconfig.get(i).getDescription().equals(e.getKey())){
                        Esconfiguration es = null;
                        es = liesconfig.get(i);
                        es.setVdata(e.getValue());
                        
                        edit(es.getId(),es);
                    }
                }
             
                output = "Configuration set successfuly..";
            }
        } catch (Exception e) {
            output = "";
            notprocess = "Somting wrong Configuration not set successfuly..";
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
    @Path("addsmsconfig")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public StringprocessData createsmsdata(String smsdata) {
        StringprocessData spd = new StringprocessData();
        String output = "";
        String notprocess = "";
        String url="";
        String username="";
        String password="";
        String failr="";
        String successr="";
        String pendingr="";
        String msgendoder="";
        String api="";
        String datatype = smsdata.substring(0, 1);

        if (datatype.equals("{")) {
            ConvertStringIntoJson stringintojson = new ConvertStringIntoJson();
            JSONObject jsonobj = stringintojson.convertTOJson(smsdata);
            url = jsonobj.getString("url");
            username = jsonobj.getString("username");
            password = jsonobj.getString("password");
            failr = jsonobj.getString("failr");
            successr = jsonobj.getString("successr");
            pendingr = jsonobj.getString("pendingr");
            msgendoder = jsonobj.getString("msgendoder");
            api = jsonobj.getString("api");
            

        } else if (datatype.equals("<")) {
            try {
                ConvertStringIntoXml stringintoxml = new ConvertStringIntoXml();
                Document doc = stringintoxml.getxmldata(smsdata);
                url = stringintoxml.getdatafromxmltag(doc, "url");
                username = stringintoxml.getdatafromxmltag(doc, "username");
                password = stringintoxml.getdatafromxmltag(doc, "password");
                failr = stringintoxml.getdatafromxmltag(doc, "failr");
                successr = stringintoxml.getdatafromxmltag(doc, "successr");
                pendingr = stringintoxml.getdatafromxmltag(doc, "pendingr");
                msgendoder = stringintoxml.getdatafromxmltag(doc, "msgendoder");
                api = stringintoxml.getdatafromxmltag(doc, "api");

            } catch (Exception ex) {
                System.err.println("ex :" + ex.getMessage());
            }
        }
        
        Map<String, String> emailekrvaluemap = new HashMap<>();
        emailekrvaluemap.put("Url", url);
        emailekrvaluemap.put("UserName", username);
        emailekrvaluemap.put("Password", password);
        emailekrvaluemap.put("Fail_R", failr);
        emailekrvaluemap.put("Success_R", successr);
        emailekrvaluemap.put("Pending_R", pendingr);
        emailekrvaluemap.put("MsgEncoder", msgendoder);
        emailekrvaluemap.put("API", api);
        try {
            for (Map.Entry<String, String> e : emailekrvaluemap.entrySet()) {
                Esconfiguration es = new Esconfiguration();
                es.setIdentity("SMS");
                es.setDescription(e.getKey());
                es.setVdata(e.getValue());

                create(es);
                output = "Configuration set successfuly..";
            }
        } catch (Exception e) {
            output = "";
            notprocess = "Somting wrong Configuration not set successfuly..";
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
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Esconfiguration> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

}
