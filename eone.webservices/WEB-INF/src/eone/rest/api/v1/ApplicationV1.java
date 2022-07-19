
package eone.rest.api.v1;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.jackson.JacksonFeature;

import eone.rest.api.v1.auth.filter.RequestFilter;
import eone.rest.api.v1.auth.filter.ResponseFilter;
import eone.rest.api.v1.auth.impl.AuthServiceImpl;
import eone.rest.api.v1.resource.impl.CacheResourceImpl;
import eone.rest.api.v1.resource.impl.FileResourceImpl;
import eone.rest.api.v1.resource.impl.FormResourceImpl;
import eone.rest.api.v1.resource.impl.InfoResourceImpl;
import eone.rest.api.v1.resource.impl.ModelResourceImpl;
import eone.rest.api.v1.resource.impl.NodeResourceImpl;
import eone.rest.api.v1.resource.impl.ProcessResourceImpl;
import eone.rest.api.v1.resource.impl.ReferenceResourceImpl;
import eone.rest.api.v1.resource.impl.ServerResourceImpl;
import eone.rest.api.v1.resource.impl.WindowResourceImpl;

/**
 * @author Client
 *
 */
public class ApplicationV1 extends Application {

	/**
	 * 
	 */
	public ApplicationV1() {
	}

	@Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        
        classes.add(AuthServiceImpl.class);
        classes.add(RequestFilter.class);
        classes.add(ResponseFilter.class);
        classes.add(JacksonFeature.class);
        classes.add(ModelResourceImpl.class);
        classes.add(WindowResourceImpl.class);
        classes.add(FormResourceImpl.class);
        classes.add(ProcessResourceImpl.class);
        classes.add(ReferenceResourceImpl.class);
        classes.add(FileResourceImpl.class);
        classes.add(CacheResourceImpl.class);
        classes.add(NodeResourceImpl.class);
        classes.add(ServerResourceImpl.class);
        classes.add(InfoResourceImpl.class);
        
        return classes;
    }	
}
