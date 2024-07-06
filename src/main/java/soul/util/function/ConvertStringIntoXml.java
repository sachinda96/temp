/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.util.function;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author admin
 */
public class ConvertStringIntoXml {
  //  private Document doc;
   // private NodeList nList;
   // private Node nNode;
   // private Element eElement;
    
    public Document getxmldata(String xml) throws IOException, SAXException{
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         Document doc=null; 
        try {
          //  System.err.println("EE! : "+xml);
            String fxml ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"+xml;
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(new InputSource(new StringReader(fxml)));
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException ex) {
        }
        
        return doc;
    }
    
    public String getdatafromxmltag(Document document,String element){
        String result;
        result=document.getElementsByTagName(element).item(0).getTextContent();
        return result;
    }
    
}
