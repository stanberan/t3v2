package uk.ac.abdn.t3.t3v2;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;










import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

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
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */





@POST
@Path("{deviceid}/prov")

    public String uploadProv(@PathParam("deviceid") String device_id,String body ) throws JsonParseException, JsonMappingException, IOException {
      System.out.println(body);

	ObjectMapper mapper=new ObjectMapper();
    	mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
   RDFData dat=mapper.readValue(body,RDFData.class);
   String inputdata=dat.body;
   InputStream stream = new ByteArrayInputStream(inputdata.getBytes(StandardCharsets.UTF_8));
   
   Model temp=ModelFactory.createDefaultModel();
   temp.read(stream,null,"TTL");
   System.out.println("From model object:");
   temp.write(System.out, "TTL");
        Model m=TDBInit.getNamedGraph(graphNS+device_id);
        
        
        
        
        return "Cool";
        
        
    }
}
