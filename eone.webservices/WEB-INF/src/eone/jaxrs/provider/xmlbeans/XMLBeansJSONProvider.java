
package eone.jaxrs.provider.xmlbeans;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.cxf.jaxrs.provider.xmlbeans.XMLBeanStreamSerializer;
import org.apache.cxf.jaxrs.provider.xmlbeans.XMLBeansElementProvider;
import org.apache.xmlbeans.XmlObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedXMLOutputFactory;

import eone.jettison.mapped.CustomMappedNamespaceConvention;
import eone.jettison.mapped.MappedXMLInputFactory;

/**
 * JSON provider for XMLBeans data objects.
 */
@Produces("application/json")
@Consumes("application/json")
@Provider
public class XMLBeansJSONProvider extends XMLBeansElementProvider {

    private static final String AD_INTERFACE_1_0_NAMESPACE = "http://dssvn.com";

	/** {@inheritDoc} */
    @Override
    public XmlObject readFrom(Class<XmlObject> type, Type genericType, 
                              Annotation[] annotations, MediaType m, 
        MultivaluedMap<String, String> headers, InputStream is)
        throws IOException {
        XmlObject result = null;

        try {

        	Map<String, String> nstojns = new HashMap<String, String>();
            nstojns.put(AD_INTERFACE_1_0_NAMESPACE, "");

            Configuration conf = new Configuration(nstojns);
            conf.setIgnoreNamespaces(false);
            CustomMappedNamespaceConvention convention = new CustomMappedNamespaceConvention(conf);
            convention.addNamespacePrefix("i", AD_INTERFACE_1_0_NAMESPACE);
            MappedXMLInputFactory factory = new MappedXMLInputFactory(convention);            
            XMLStreamReader xsr = factory.createXMLStreamReader(is);
            result = parseXmlBean(type, xsr);

            xsr.close();
        } catch (XMLStreamException e) {
            throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
        }

        return result;
    }

    /**
     * Create an XMLBean data object using a stream <code>Reader</code>
     * 
     * @param type declared type of the desired XMLBean data object
     * @param reader
     * @return an instance of the required object, otherwise null
     */
    protected XmlObject parseXmlBean(Class<?> type, XMLStreamReader xsr) {
        XmlObject result = null;

        Class<?> factory = getFactory(type);

        try {

            // get factory method parse(InputStream)
            Method m = factory.getMethod("parse", XMLStreamReader.class);
            Object[] args = {xsr};
            Object obj = m.invoke(type, args);

            if (obj instanceof XmlObject) {            	            	
                result = (XmlObject)obj;                
            }

        } catch (NoSuchMethodException nsme) {
            nsme.printStackTrace();
        } catch (InvocationTargetException ite) {
            ite.printStackTrace();
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }

        return result;
    }
    
    /**
     * Locate the XMLBean <code>Factory</code> inner class.
     * 
     * @param type
     * @return the Factory class if present, otherwise null.
     */
    private Class<?> getFactory(Class<?> type) {
        Class<?> result = null;

        Class<?>[] declared = type.getDeclaredClasses();
        for (Class<?> c : declared) {

            if (c.getSimpleName().equals("Factory")) {
                result = c;
            }
        }
        
        if (result == null) {
	        Class<?>[] interfaces = type.getInterfaces();
	
	        // look for XMLBeans inner class Factory
	        for (Class<?> inter : interfaces) {
	
	            declared = inter.getDeclaredClasses();
	
	            for (Class<?> c : declared) {
	
	                if (c.getSimpleName().equals("Factory")) {
	                    result = c;
	                }
	            }
	        }
        }

        return result;
    }
    
    /** {@inheritDoc} */
    @Override
    public void writeTo(XmlObject obj, Class<?> cls, Type genericType, Annotation[] annotations,  
                        MediaType m, MultivaluedMap<String, Object> headers, OutputStream os) {

        try {

            // Set up the JSON StAX implementation
            Map<String, String> nstojns = new HashMap<String, String>();
            Configuration conf = new Configuration(nstojns);
            conf.setIgnoreNamespaces(true);
            XMLOutputFactory factory = new MappedXMLOutputFactory(conf);
            XMLStreamWriter xsw = factory.createXMLStreamWriter(os);
            xsw.writeStartDocument();
            if (obj instanceof XmlObject) {

                XmlObject xObj = obj;
                XMLBeanStreamSerializer ser = new XMLBeanStreamSerializer();
                ser.serialize(xObj, xsw);
            }

            xsw.flush();
            xsw.close();

        } catch (XMLStreamException e) {
            throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
        } catch (IOException ioe) {
            throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }    
}
