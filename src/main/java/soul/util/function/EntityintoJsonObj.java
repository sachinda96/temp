/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.util.function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class EntityintoJsonObj {
    
    public String convertintoJson(Object obj){
        String jsondata;
        
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json="Null";
        try {
            json = ow.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(EntityintoJsonObj.class.getName()).log(Level.SEVERE, null, ex);
        }
        jsondata = json;
        return jsondata;
    }
    
}
