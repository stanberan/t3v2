package uk.ac.abdn.t3.t3v2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import uk.ac.abdn.t3.model.BboxData;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("upload")
public class MyResource {
private static final String graphNS="http://t3.abdn.ac.uk/devices/";
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	@Path("{deviceid}/prov")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String uploadProv(@PathParam("deviceid") String device_id,String body ) {
        
		ObjectMapper mapper=new ObjectMapper();
    	mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	RDFData dat=mapper.readValue(body,RDFData.class);
	
        Model m=TDBInit.getNamedGraph(graphNS+device_id);
        
        
        
        
        return "";
        
        
    }
}
