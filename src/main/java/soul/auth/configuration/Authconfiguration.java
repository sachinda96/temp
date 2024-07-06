package soul.auth.configuration;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import soul.cross.filter.CrossFilter;
import soul.loginNout.service.loginservice;


@ApplicationPath("auth")
public class Authconfiguration extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> clazzes = new HashSet();
        clazzes.add(loginservice.class);
       clazzes.add(CrossFilter.class);
        return clazzes;  
    } 
}
