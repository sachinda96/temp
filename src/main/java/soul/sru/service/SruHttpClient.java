/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.sru.service;

import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author admin
 */
@Path("Sruclient")
public class SruHttpClient {
    
    @GET
    @Path("Sruserach")
    public String httpclientsru(@QueryParam("qu") String S) throws IOException, ParserConfigurationException, SAXException{
     //   System.out.println("soul-1 : "+S);
     //   System.out.println("soul-2 : "+S.replace("@","%"));
       //ss System.out.println("SRUXML Get data call..");
        String sruurl = S.replace(";","&");
      //  System.err.println("Sru-1 & "+ sruurl);
        String sruurll = sruurl.replace("@","%");
        System.err.println("Sru url : "+ sruurll);
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(sruurll);
        HttpResponse response = client.execute(request);
        String data = EntityUtils.toString(response.getEntity());
      //  System.out.println("DATA : " + data);
      
      
//    InputStream inputStream = response.getEntity().getContent();
//    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//    Document doc = dBuilder.parse(inputStream);
//    doc.getDocumentElement().normalize();
    
    
        return data;
    }
    
}
