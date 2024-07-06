/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.jwttoken.commonfunction;


import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import soul.jwttoken.generaion.JwsKey;

/**
 *
 * @author admin
 */
public class JWTCommon {
    
//    public String getusernamefromtoken(String token){
//        String uname="N";
//        RsaJsonWebKey rsaJsonWebKey = JwsKey.produce();
//        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
//                .setRequireSubject() // the JWT must have a subject claim
//                .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
//                .build(); // create the JwtConsumer instance
//        
//        JwtClaims jwtClaims;
//        try {
//            jwtClaims = jwtConsumer.processToClaims(token);
//            String encrpsubject = (String) jwtClaims.getClaimValue("sub");
//            uname=encrpsubject;
//        } catch (InvalidJwtException ex) {
//            System.err.println("ex : "+ ex.getMessage());
//        }
//            
//        return uname;
//    }
//    
    
    private static final RsaJsonWebKey rsaJsonWebKey = JwsKey.produce();
    private static final JwtConsumer jwtConsumer = new JwtConsumerBuilder()
               .setRequireSubject() // the JWT must have a subject claim
                .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
                .build(); // create the JwtConsumer instance
    
    public static JwtConsumer getJwtConsumer(){
        return jwtConsumer;
    }
    
}
