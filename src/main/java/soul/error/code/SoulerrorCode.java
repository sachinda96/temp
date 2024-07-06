/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.error.code;

import com.itextpdf.awt.geom.IllegalPathStateException;
import com.sun.jersey.api.ConflictException;
import io.jsonwebtoken.InvalidClaimException;
import java.io.CharConversionException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.NotActiveException;
import java.io.NotSerializableException;
import java.io.ObjectStreamException;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.io.SyncFailedException;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.io.WriteAbortedException;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.ProviderNotFoundException;
import java.security.ProviderException;
import java.text.ParseException;
import java.util.ConcurrentModificationException;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import javax.lang.model.UnknownEntityException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.transaction.RollbackException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.RedirectionException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.PropertyException;
import javax.xml.bind.TypeConstraintException;
import javax.xml.bind.UnmarshalException;
import javax.xml.parsers.ParserConfigurationException;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import soul.errorresponse.service.ValidationException;


/**
 *
 * @author admin
 */
public class SoulerrorCode {
    
   
   private static Map<Object,String> exceptionlistofsoul = new HashMap<Object,String>();
 
   private void listofex(){
       exceptionlistofsoul.clear();
       exceptionlistofsoul.put(ArithmeticException.class, "1XX");
       exceptionlistofsoul.put(ArrayIndexOutOfBoundsException.class, "2XX");
       exceptionlistofsoul.put(StringIndexOutOfBoundsException.class, "3XX");
       exceptionlistofsoul.put(ArrayStoreException.class, "4XX");
       exceptionlistofsoul.put(ClassCastException.class, "5XX");
       exceptionlistofsoul.put(EnumConstantNotPresentException.class, "6XX");
       exceptionlistofsoul.put(IllegalThreadStateException.class, "7XX");
       exceptionlistofsoul.put(IndexOutOfBoundsException.class, "8XX");
       exceptionlistofsoul.put(NumberFormatException.class, "9XX");
       exceptionlistofsoul.put(IllegalArgumentException.class, "10X");
       exceptionlistofsoul.put(IllegalMonitorStateException.class, "11X");
       exceptionlistofsoul.put(IllegalStateException.class, "12X");
       exceptionlistofsoul.put(NegativeArraySizeException.class, "13X");
       exceptionlistofsoul.put(NullPointerException.class, "14X");
       exceptionlistofsoul.put(SecurityException.class, "15X");
       exceptionlistofsoul.put(TypeNotPresentException.class, "16X");
       exceptionlistofsoul.put(UnsupportedOperationException.class, "17X");
       exceptionlistofsoul.put(MissingResourceException.class, "18X");
       exceptionlistofsoul.put(InputMismatchException.class, "19X");
       exceptionlistofsoul.put(NoSuchElementException.class, "20X");
       exceptionlistofsoul.put(EmptyStackException.class, "21X");
       exceptionlistofsoul.put(ConcurrentModificationException.class, "22X");
       exceptionlistofsoul.put(ClassNotFoundException.class, "23X");
       exceptionlistofsoul.put(InstantiationException.class, "24X");
       exceptionlistofsoul.put(IllegalAccessException.class, "25X");
       exceptionlistofsoul.put(InvocationTargetException.class, "26X");
       exceptionlistofsoul.put(NoSuchFieldException.class, "27X");
       exceptionlistofsoul.put(NoSuchMethodException.class, "28X");
       exceptionlistofsoul.put(CloneNotSupportedException.class, "29X");
       exceptionlistofsoul.put(ReflectiveOperationException.class, "30X");
       exceptionlistofsoul.put(InterruptedException.class, "31X");
       exceptionlistofsoul.put(IOException.class, "32X");
       exceptionlistofsoul.put(EOFException.class, "33X");
       exceptionlistofsoul.put(FileNotFoundException.class, "34X");
       exceptionlistofsoul.put(InterruptedIOException.class, "35X");
       exceptionlistofsoul.put(UnsupportedEncodingException.class, "36X");
       exceptionlistofsoul.put(UTFDataFormatException.class, "37X");
       exceptionlistofsoul.put(ObjectStreamException.class, "38X");
       exceptionlistofsoul.put(InvalidClassException.class, "39X");
       exceptionlistofsoul.put(InvalidObjectException.class, "40X");
       exceptionlistofsoul.put(NotSerializableException.class, "41X");
       exceptionlistofsoul.put(StreamCorruptedException.class, "42X");
       exceptionlistofsoul.put(WriteAbortedException.class, "43X");
       exceptionlistofsoul.put(JoseException.class, "44X");
       exceptionlistofsoul.put(InvalidClaimException.class, "45X");
       exceptionlistofsoul.put(InvalidJwtException.class, "46X");
      // exceptionlistofsoul.put(JWTDecodeException.class, "47X");
      // exceptionlistofsoul.put(JWTVerificationException.class, "48X");
       //exceptionlistofsoul.put(SignatureGenerationException.class, "49X");
      // exceptionlistofsoul.put(SignatureVerificationException.class, "50X");
      // exceptionlistofsoul.put(TokenExpiredException.class, "51X");
       exceptionlistofsoul.put(BadRequestException.class, "52X");
       exceptionlistofsoul.put(UnauthorizedException.class, "53X");
       exceptionlistofsoul.put(ForbiddenException.class, "54X");
       exceptionlistofsoul.put(NotFoundException.class, "55X");
       exceptionlistofsoul.put(ConflictException.class, "56X");
       exceptionlistofsoul.put(InternalServerErrorException.class, "57X");
       exceptionlistofsoul.put(ServiceUnavailableException.class, "58X");
       exceptionlistofsoul.put(PersistenceException.class, "59X");
       exceptionlistofsoul.put(EntityExistsException.class, "60X");
       exceptionlistofsoul.put(EntityNotFoundException.class, "61X");
       exceptionlistofsoul.put(LockTimeoutException.class, "62X");
       exceptionlistofsoul.put(NonUniqueResultException.class, "63X");
       exceptionlistofsoul.put(NoResultException.class, "64X");
       exceptionlistofsoul.put(OptimisticLockException.class, "65X");
       exceptionlistofsoul.put(PessimisticLockException.class, "66X");
       exceptionlistofsoul.put(QueryTimeoutException.class, "67X");
       exceptionlistofsoul.put(RollbackException.class, "68X");
       exceptionlistofsoul.put(TransactionRequiredException.class, "69X");
       exceptionlistofsoul.put(ParserConfigurationException.class, "70X");
       exceptionlistofsoul.put(SAXNotRecognizedException.class, "71X");
       exceptionlistofsoul.put(SAXNotSupportedException.class, "72X");
       exceptionlistofsoul.put(SAXParseException.class, "73X");
       exceptionlistofsoul.put(CharConversionException.class, "74X");
       exceptionlistofsoul.put(NotActiveException.class, "75X");
       exceptionlistofsoul.put(FileNotFoundException.class, "76X");
       exceptionlistofsoul.put(OptionalDataException.class, "77X");
       exceptionlistofsoul.put(SyncFailedException.class, "78X");
       exceptionlistofsoul.put(NotAcceptableException.class, "79X");
       exceptionlistofsoul.put(NotAllowedException.class, "80X");
       exceptionlistofsoul.put(NotAuthorizedException.class, "81X");
       exceptionlistofsoul.put(NotSupportedException.class, "82X");
       exceptionlistofsoul.put(ProcessingException.class, "83X");
       exceptionlistofsoul.put(RedirectionException.class, "84X");
       exceptionlistofsoul.put(ServerErrorException.class, "85X");
       exceptionlistofsoul.put(WebApplicationException.class, "86X");
       exceptionlistofsoul.put(AnnotationTypeMismatchException.class, "87X");
       exceptionlistofsoul.put(DataBindingException.class, "88X");
       exceptionlistofsoul.put(DOMException.class, "89X");
       exceptionlistofsoul.put(ProviderException.class, "90X");
       exceptionlistofsoul.put(ProviderNotFoundException.class, "91X");
       exceptionlistofsoul.put(TypeConstraintException.class, "92X");
       exceptionlistofsoul.put(UnknownEntityException.class, "93X");
       exceptionlistofsoul.put(WrongMethodTypeException.class, "94X");
       exceptionlistofsoul.put(IllegalPathStateException.class, "95X");
       exceptionlistofsoul.put(JAXBException.class, "96X");
       exceptionlistofsoul.put(ValidationException.class, "97X");
       exceptionlistofsoul.put(MarshalException.class, "98X");
       exceptionlistofsoul.put(PropertyException.class, "99X");
       exceptionlistofsoul.put(UnmarshalException.class, "100");
       exceptionlistofsoul.put(ParseException.class, "101");
       exceptionlistofsoul.put(RuntimeException.class, "102");
   }
   
   public String getexceptioncode(Object exception){
       String code="N";
       listofex();
       System.err.println("Size of list : "+exceptionlistofsoul.size());
       for(Map.Entry<Object,String> e : exceptionlistofsoul.entrySet()){
         //  System.err.println("E key :" + e.getKey());
           if(exception.getClass().toString().equals(e.getKey().toString())){
             // System.err.println("SSSSSSS--------------------------------------------");
              code=e.getValue();
          }
       }
      // code= code ;
       return code;
   }
}
