/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.util.function;

import org.json.JSONObject;

/**
 *
 * @author admin
 */
public class ConvertStringIntoJson {
    private JSONObject jb;
    public JSONObject convertTOJson(String data){
        jb = new JSONObject(data);
        return jb;
    }
    
}
