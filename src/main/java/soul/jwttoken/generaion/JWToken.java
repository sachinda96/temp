package soul.jwttoken.generaion;



import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import soul.auth.filter.OauthRequestFilter;
import soul.encryptdecrypt.service.SoulSecurity;
import soul.jwttoken.commonfunction.JWTCommon;


public class JWToken {
    
    private static Map<String,String> refreshidmap = new HashMap<String,String>(); // map of uname and refid
    private static Map<String,String> mapidwithtoken = new HashMap<String,String>(); // map refid and jwt Token
    private static String generateid = "SOUL-Web-token.000";
    private static int incrementali=0;
    private static String oldusername;
    SoulSecurity security = new SoulSecurity();
    
  
    public  String buildJWT(String uname,String privilleges) {
        String jwt = "null";
        int flg;
         System.out.println("refreshidmap is empty : "+refreshidmap.isEmpty());
        if(refreshidmap.isEmpty()){
            flg=0;
        }else{
            System.out.println("refreshidmap is contains username : "+refreshidmap.containsKey(uname));
            boolean s = refreshidmap.containsKey(uname);
            if(s){
                flg=1;
                 System.out.println("User name already exist");
            }else{
                flg=0;
            }
        }
        
        if(flg==0){
            RsaJsonWebKey rsaJsonWebKey = JwsKey.produce();
       // Key JsonWebKey = JwsKey.keys();
        System.out.println("RSA hash code... " + rsaJsonWebKey.hashCode());
        
        //set issue time of token by getting current time in millis
         long millies = System.currentTimeMillis();
       // Date dateofissue = new Date(millies);
        
       long expmillies ;
      long tokenmillies = Gettime();
//         if(tokenmillies >= 0){
            expmillies = millies + tokenmillies;
//          //   exp = new Date(expmillies); 
//        }
        System.out.println("subject en and de : " + uname +"  : "+security.encrypt(uname,security.Securitykey));
        System.out.println("privileges en and de : " + privilleges +"  : "+security.encrypt(privilleges,SoulSecurity.Securitykey));
        JwtClaims claims = new JwtClaims();
        claims.setSubject(security.encrypt(uname,security.Securitykey)); // the subject/principal is whom the token is about
        claims.setClaim("Privilleges", security.encrypt(privilleges,security.Securitykey));
        claims.setIssuedAt(NumericDate.fromMilliseconds(millies));
        claims.setExpirationTime(NumericDate.fromMilliseconds(expmillies));
        

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(rsaJsonWebKey.getPrivateKey());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        
        System.err.println("key :" +rsaJsonWebKey.getKey());
        System.err.println("Private key :" +rsaJsonWebKey.getKey());

        
        try {
            jwt = jws.getCompactSerialization();
        } catch (JoseException ex) {
            Logger.getLogger(OauthRequestFilter.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Claim:\n" + claims);
        System.out.println("JWS:\n" + jws);
        System.out.println("JWT:\n" + jwt);
        
        incrementali++;
        
        System.out.println("The Refresh id is :"+ generateid + incrementali );
        refreshidmap.put(uname, generateid + incrementali);
        mapidwithtoken.put(generateid + incrementali, jwt);
        return jwt;
        
        }else{
            return jwt;
        }
        
       
    }
    
     private static long Gettime(){
        // live 1 hr
       // return 3600000;
       
       // live 10 sec 
       return 600000;// 10 min
    }
     
    public  String getrefreshtokenid(String uname){
        if (!uname.isEmpty()){
            String refid = refreshidmap.get(uname);
            return refid;
        }
        else{
            return "null";
        }                
    } 
    
    public  Boolean checkJWTTokeninMap(String JWT){
    Boolean rs = false;
        boolean s = mapidwithtoken.containsValue(JWT);
        if(s){
            rs = true;
        }
    return rs;
    }
    
   public  String getTokenfromrefid(String Refid){
       String jwt = mapidwithtoken.get(Refid);
       return jwt;
   }
   
   public  Boolean resetuserlogin(String uname){
       Boolean rs = false;
       String Refid = refreshidmap.get(uname);
       boolean refid = refreshidmap.remove(uname,Refid);
      String jwtold = mapidwithtoken.get(Refid);
      boolean mapid = mapidwithtoken.remove(Refid, jwtold);
      
      if (refid == true && mapid==true){
          rs=true;
          System.out.println(" delete from ref : "+refid);
          System.out.println("delete from map : "+mapid);
          System.out.println("new refreshidmap......... : "+ refreshidmap);
          System.out.println("new mapidwithtoken......... : "+ mapidwithtoken);
      }
       return rs;
   }
   
   public String getuserid(String Refid){
        String keyuname = "N";
        for (Map.Entry<String, String> e : refreshidmap.entrySet()) {
            System.out.println(e.getKey() + " "
                               + e.getValue()); 
             if (Refid.equals(e.getValue())){
                 keyuname = e.getKey();
             }
        }
        
        return keyuname;
   }
     
   public  Boolean DeleteMapOfUnameAndRefidAndJWT(String Refid){
       Boolean rs = false;
       String keyuname = "";
       System.out.println("Delete method call with ref id ...." + Refid);
     for (Map.Entry<String, String> e : refreshidmap.entrySet()) {
            System.out.println(e.getKey() + " "
                               + e.getValue()); 
             if (Refid.equals(e.getValue())){
                 keyuname = e.getKey();
             }
    } 
             
      boolean refid = refreshidmap.remove(keyuname,Refid);
      String jwtold = mapidwithtoken.get(Refid);
      boolean mapid = mapidwithtoken.remove(Refid, jwtold);
      
      if (refid == true && mapid==true){
          rs=true;
          System.out.println("JWT-Token delete from ref : "+refid);
          System.out.println("JWT-Token delete from map : "+mapid);
          System.out.println("refreshidmap......... : "+ refreshidmap);
          System.out.println("mapidwithtoken......... : "+ mapidwithtoken);
      }
       return rs;
   }
   
   public  String verifytoken(String reftoken){
       String rs ="null";
       String jwt ="";
       if(mapidwithtoken.containsKey(reftoken)){
           jwt = mapidwithtoken.get(reftoken);
           
           try {
               //process jwt token
               JWTCommon.getJwtConsumer().processToClaims(jwt);
               rs="S";
           } catch (InvalidJwtException ex) {
                return rs;
           }
       }
       return rs;
   }
   
    public  String jwtrefreshidwithtoken(String jwttoken,String refid){
        
        System.out.println("JWT Refreshid with token is called......");
         System.out.println("JWT-Token : "+jwttoken);
         String fetchtoken = jwttoken.substring(7);
          System.out.println("Ref-ID : "+ refid);
        String jwtrefreshid="null";
        
        if(mapidwithtoken.containsKey(refid)){
            
            System.out.println("Ref-ID is Verify : ");
            String oldjwt = mapidwithtoken.get(refid);
             
//            RsaJsonWebKey rsaJsonWebKey = JwsKey.produce();
//            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
//                .setRequireSubject() // the JWT must have a subject claim
//                .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
//                .build(); // create the JwtConsumer instance

            try {
                // check token values with stored token values
              //  JwtClaims jwtClaims = jwtConsumer.processToClaims(jwttoken);
                JwtClaims jwtClaims = JWTCommon.getJwtConsumer().processToClaims(fetchtoken);
                String subject = (String) jwtClaims.getClaimValue("sub");
                String rolesofuser = (String) jwtClaims.getClaimValue("Privilleges");
                System.out.println("ref subject en and de : " + subject +"  : "+security.decrypt(subject,SoulSecurity.Securitykey));
                System.out.println("ref privileges en and de : " + rolesofuser +"  : "+security.decrypt(rolesofuser,SoulSecurity.Securitykey));
                String freshsubject = security.decrypt(subject, SoulSecurity.Securitykey);
                String freashrolesofuser = security.decrypt(rolesofuser, SoulSecurity.Securitykey);
                System.out.println("freshsubject : "+ freshsubject);
                 System.out.println("freashrolesofuser : "+ freashrolesofuser);
//             String jwtClaims =JWTCommon.DecodePayload(fetchtoken);
//             String subject =JWTCommon.GetPayloadString(jwtClaims,JWTPayloadFields.Subject);
//                System.out.println("N-Subject : "+subject);
//             String rolesofuser =  JWTCommon.GetPayloadString(jwtClaims,JWTPayloadFields.Privilleges);
//                 System.out.println("N-rolesofuser : "+rolesofuser);
                
                System.out.println("Client token is Verify : ");
                
                // check previous token store in map 
             //   JwtClaims oldjwtClaims = jwtConsumer.processToClaims(oldjwt);
              JwtClaims oldjwtClaims = JWTCommon.getJwtConsumer().processToClaims(oldjwt);
                String oldsubject = (String) oldjwtClaims.getClaimValue("sub");
                String oldrolesofuser = (String) oldjwtClaims.getClaimValue("Privilleges");
                long oldissuertime = (long) oldjwtClaims.getClaimValue("iat");
                
                String osubject = security.decrypt(oldsubject, SoulSecurity.Securitykey);
                String orolesofuser = security.decrypt(oldrolesofuser,SoulSecurity.Securitykey);
                
                //check previous token store in map with onther way
//                String oldpayload = JWTCommon.DecodePayload(oldjwt);
//                String oldsubject = JWTCommon.GetPayloadString(oldpayload,JWTPayloadFields.Subject);
//                 System.out.println("N-oldsubject : "+oldsubject);
//                String oldrolesofuser = JWTCommon.GetPayloadString(oldpayload,JWTPayloadFields.Privilleges); 
//                 System.out.println("N-oldrolesofuser : "+oldrolesofuser);
//                String s = JWTCommon.GetPayloadString(oldpayload,JWTPayloadFields.Issuetime);
//                System.out.println("string long :" + s);
//                long oldissuertime = Long.parseLong(s) ;
                
                System.out.println("Client store token is Verify : ");
                
                if (osubject.equals(freshsubject) && orolesofuser.equals(freashrolesofuser)){
                    incrementali++;
                    System.out.println("Process of generate new token-------------------- ");
               
                    RsaJsonWebKey rsaJsonWebKey1 = JwsKey.produce();
       
                    long millies = System.currentTimeMillis();
       
        
                    long expmillies ;
                    long tokenmillies = Gettime();

                    expmillies = millies + tokenmillies;

                    JwtClaims claims = new JwtClaims();
                    claims.setSubject(security.encrypt(osubject,SoulSecurity.Securitykey)); // the subject/principal is whom the token is about
                    claims.setClaim("Privilleges", security.encrypt(orolesofuser,SoulSecurity.Securitykey));
                    claims.setIssuedAt(NumericDate.fromMilliseconds(oldissuertime));
                    claims.setExpirationTime(NumericDate.fromMilliseconds(expmillies));
        

                    JsonWebSignature jws = new JsonWebSignature();
                    jws.setPayload(claims.toJson());
                    jws.setKey(rsaJsonWebKey1.getPrivateKey());
                    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        
     

                    String jwt = null;
                    try {
                        jwt = jws.getCompactSerialization();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                 
                  System.out.println("mapidwithtoken : "+mapidwithtoken);  
                   System.out.println("OLD REF ID : "+refid);  
                    if (mapidwithtoken.containsKey(refid) && mapidwithtoken.containsValue(fetchtoken)){
                       Boolean rs = mapidwithtoken.remove(refid, fetchtoken);
                       System.out.println("mapidwithtoken After Delete: "+mapidwithtoken);  
                        if (rs){
                    refreshidmap.replace(osubject, generateid + incrementali);
                    mapidwithtoken.put(generateid + incrementali, jwt);
                   // oldusername = oldsubject;
                   jwtrefreshid = generateid + incrementali;
                    System.out.println("new subject ------ :"+osubject);
                    System.out.println("new token ------ :"+jwt);
                    System.out.println("new Ref-token ------ :"+generateid + incrementali);
                    }
                         System.out.println("Fields are remove from map id and token :" + rs);
                    }
                }
         
            } catch (Exception e) {
                 e.printStackTrace();
           // throw e;
            }
      
        
        return jwtrefreshid;
        }else{
             return jwtrefreshid;
        }
                
       
    } 
    
}
