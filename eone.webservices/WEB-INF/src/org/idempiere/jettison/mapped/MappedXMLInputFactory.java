
package org.idempiere.jettison.mapped;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.codehaus.jettison.AbstractXMLInputFactory;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONTokener;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;

/**
 * @author hengsin
 *
 */
public class MappedXMLInputFactory extends AbstractXMLInputFactory {


    private MappedNamespaceConvention convention;

    /**
     * 
     * @param convention
     */
    public MappedXMLInputFactory(MappedNamespaceConvention convention) {
        this.convention = convention;
    }
    
    /**
     * @param tokener
     * @return {@link XMLStreamReader}
     */
    public XMLStreamReader createXMLStreamReader(JSONTokener tokener) throws XMLStreamException {
        try {
            JSONObject root = createJSONObject(tokener);
            return new MappedXMLStreamReader(root, convention);
        } catch (JSONException e) {
            throw new XMLStreamException(e);
        }
    }
    
    /**
     * 
     * @param tokener
     * @return {@link JSONObject}
     * @throws JSONException
     */
    protected JSONObject createJSONObject(JSONTokener tokener) throws JSONException {
    	return new JSONObject(tokener);
    }
}
