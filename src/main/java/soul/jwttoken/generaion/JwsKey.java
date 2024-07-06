/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.jwttoken.generaion;

import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.lang.JoseException;

/**
 *
 * @author admin
 */
public class JwsKey {
    
    public JwsKey(){
        
    }
    
    
   // private static RsaJsonWebKey theOne;
  
    
  //  theOne = RsaJwkGenerator.generateJwk(2084,"SOULWebEditionTokenbyJWT",SecureRandom.getInstanceStrong());
    
     // Secart key of token store at server side and verify by server
//    private static String Getsecart(){
//        return "SOULWebEditionTokenbyJWT";
//    }
//    
//    public static Key keys(){
//     SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.NONE;
//     byte[] keyfortoken = DatatypeConverter.parseBase64Binary(Getsecart());
//     Key signaturekey = new SecretKeySpec(keyfortoken, signatureAlgorithm.getJcaName());
//        return signaturekey;
//    }
    private static RsaJsonWebKey theOne;
    
    /**
     * 
     * not an ideal implementation since does not implement double-lock synchronization check
     */
    public static RsaJsonWebKey produce(){
        if(theOne == null){
            try {
                theOne = RsaJwkGenerator.generateJwk(2084);
            } catch (JoseException ex) {
                Logger.getLogger(JwsKey.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
        System.out.println("RSA Key setup... "+ theOne.hashCode());
        return theOne;
    }
}
