/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.errorresponce;


/**
 *
 * @author admin
 */
public class CommonErrorProvider {
      private String message;
      private String code;
      
     public CommonErrorProvider(){} 
      
    public CommonErrorProvider(String message, String code) {
        this.message = message;
        this.code = code;
    }
      
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
      

}
