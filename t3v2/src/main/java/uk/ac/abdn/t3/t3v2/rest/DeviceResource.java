package uk.ac.abdn.t3.t3v2.rest;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.security.acl.Owner;
import java.util.Date;
import java.util.HashMap;

import javax.swing.text.html.parser.Entity;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import uk.ac.abdn.t3.t3v2.Models;
import uk.ac.abdn.t3.t3v2.RDFData;
import uk.ac.abdn.t3.t3v2.Repository;
import uk.ac.abdn.t3.t3v2.models.ModelController;
import uk.ac.abdn.t3.t3v2.pojo.CustomError;
import uk.ac.abdn.t3.t3v2.pojo.DeviceDescription;
import uk.ac.abdn.t3.t3v2.services.InferenceService;
import uk.ac.abdn.t3.t3v2.services.QueryService;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.update.UpdateAction;
import com.hp.hpl.jena.update.UpdateRequest;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("device")
public class DeviceResource {
static Repository TDB=Repository.getSingleton();
static QueryService queryService=QueryService.getSingleton();
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */



static{

///	prefixes.put("dev", Models.graphNS);
	
}

@POST
@Path("/upload/{deviceid}/prov")
    public Response uploadProv(@PathParam("deviceid") String device_id,String body ) {
      System.out.println(body);
try{
	ObjectMapper mapper=new ObjectMapper();
    	mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
   RDFData dat=mapper.readValue(body,RDFData.class);
   String inputdata=dat.body;
   InputStream stream = new ByteArrayInputStream(inputdata.getBytes(StandardCharsets.UTF_8));
   
   Model temp=ModelFactory.createDefaultModel();
   temp.read(stream,null,"TTL");
   System.out.println("From model object:");
   temp.write(System.out, "TTL");
        TDB.addToGraph(temp, Models.graphNS+device_id+"/prov");
}
catch(Exception e){
	return Response.noContent().entity(e.getMessage()).build();
}
        return Response.accepted().entity("Done").build();
        
        
    }
@GET
@Path("/remove/{devid}/{type}")
public Response removeDev(@PathParam("type") String type,@PathParam("devid")String id){
	String graph="";
	if(type.equals("all")){
		graph=ModelController.TTT_GRAPH+id;
	}
	else{
		graph=ModelController.TTT_GRAPH+id+"/"+type;
	}
	System.out.println("Trying to remove ..."+graph);
	boolean removed=TDB.removeNamedGraph(graph);
	if(removed){
	return Response.status(Response.Status.OK).entity("Removed"+graph).build();
	}
	else{
		return Response.status(Response.Status.NO_CONTENT).entity("Not Removed or doesn't exist:"+graph).build();
	}
}



@GET
@Path("{deviceid}/{type}")
@Produces(MediaType.TEXT_PLAIN)
public Response getGraph(@PathParam("deviceid") String id,@PathParam("type") String type) {
	String graph=Models.graphNS+id+"/"+type;
	final Model m=TDB.getIndependentModel(graph);
	//OntModel ontModel=ModelFactory.createOntologyModel();
	//ontModel.addSubModel(ModelController.ALL_OM);
//	ontModel.add(m);
	
	if(m==null){
		return Response.ok().entity("Graph Does not exist:"+graph).build();
	}
	
	
  StreamingOutput stream = new StreamingOutput() {
    @Override
    public void write(OutputStream os) throws IOException,
    WebApplicationException {
      Writer writer = new BufferedWriter(new OutputStreamWriter(os));
      m.write(writer,"N3");
      writer.close();
    }
  };
  return Response.ok(stream).build();
}

@GET
@Path("infer/{type}")
@Produces(MediaType.TEXT_PLAIN)
public String infer(@PathParam("type") String type) {
InferenceService infService=InferenceService.getService();

//infService.inferCapabilities(type);
return "Done";

}
@GET
@Path("compare/{type}")
@Produces(MediaType.TEXT_PLAIN)
public String get(@PathParam("type") String type) {
InferenceService infService=InferenceService.getService();

//infService.compareCapabilities("simbbox001", "user");
return "Done";

}

@GET
@Path("{devid}/description/")
@Produces(MediaType.TEXT_PLAIN)
public Response getDescription(@Context UriInfo uriInfo,@PathParam("devid") String devid) {
    
	DeviceDescription desc=queryService.getDeviceDescription(devid);
	if(desc!=null){
		return Response.ok().entity(desc.toJson()).build();
	}

return Response.noContent().entity(new CustomError(uriInfo.getPath(),"Request for description failed").toJson()).build();

}



@POST
@Path("/register")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registerDevice(
    		@FormParam("dev_id") String id,
    		@FormParam("dev_name") String name,
    		@FormParam("dev_img") String image,
    		@FormParam("dev_desc") String description,
    		@FormParam("dev_sec_desc") String sec_description,
    		@FormParam("dev_type") String type,
    		@FormParam("dev_own_name") String own_name,
    		@FormParam("dev_own_address") String own_address,
    		@FormParam("dev_own_web") String own_web,
    		@FormParam("dev_own_logo") String own_logo,
    		@FormParam("dev_own_tel") String own_tel,
    		@FormParam("dev_man_name") String man_name,
    		@FormParam("dev_man_address") String man_address,
    		@FormParam("dev_man_tel") String man_tel,
    		@FormParam("dev_man_logo") String man_logo,
    		@FormParam("dev_man_web") String man_web,
    		@FormParam("dev_man_email") String man_email,
    		@FormParam("dev_own_email") String own_email
    		) throws JsonParseException, JsonMappingException, IOException {
System.out.println("Manweb:"+man_web);
	//TODO : Addresss ....
if(TDB.getIndependentModel(ModelController.TTT_GRAPH+id+"/data")!=null){
	
	return Response.status(Response.Status.FOUND).entity("This id is already taken!Try again").build();
}
	ParameterizedSparqlString query=new ParameterizedSparqlString();
	
	Model temp=ModelFactory.createDefaultModel();
	
	
	query.setCommandText("INSERT DATA { "
		

	+"	 ?device foaf:name ?name ."
	+"	 ?device ttt:deviceDescription ?description ."
	+"	?device ttt:securityDescription ?secdescription ."
	+"   ?device a prov:Entity ."
//	+"   ?device a iota:Device ."
	+" ?device a ttt:TelematicsDevice ."
	+"   ?device ttt:pictureURL ?image ."
	+"   ?device ttt:typeDescription ?typeDesc."
	+"  ?device ttt:manufacturer ?manufacturer ."
	+"    ?device ttt:owner ?owner ."
	+"    ?manufacturer  a foaf:Organization . "
	+"    ?manufacturer foaf:homepage ?manpage ."
	+"    ?manufacturer foaf:logo ?manlogo ."
	+"    ?manufacturer foaf:phone ?mantel ."
	+"   ?manufacturer foaf:name ?manname ."
	+"    ?manufacturer foaf:mbox ?manemail ."		 
	+"   ?owner  a foaf:Organization . "
	+"    ?owner foaf:homepage ?ownpage ."
	+"     ?owner foaf:logo ?ownlogo ."
	+"    ?owner foaf:phone ?owntel ."
	+"    	?owner foaf:name ?ownname ."
	 +"   	?owner foaf:mbox ?ownemail .  "
		  
+"	    ?owner ns:hasAddress ?ownaddress .	"		
+"	    ?manufacturer ns:hasAddress ?manaddress 	"		

+"		 }");
	
	query.setNsPrefixes(ModelController.prefixes);
	query.setIri("device",ModelController.TTT_GRAPH+id);
	query.setLiteral("name", name);
	query.setLiteral("description", description);
	query.setLiteral("secdescription", sec_description);
	query.setIri("image",image);
	query.setLiteral("typeDesc", type);
	query.setIri("manufacturer", Models.graphNS+id+"/Manufacturer");
	query.setIri("owner",Models.graphNS+id+"/Owner");
	query.setIri("manpage",man_web);
	query.setIri("manlogo",man_logo);
	query.setIri("mantel","tel:"+man_tel);
	query.setLiteral("manname", man_name);
	query.setIri("manemail", "mailto:"+man_email);
	//USER ??
	query.setIri("ownpage",own_web);
	query.setIri("ownlogo",own_logo);
	query.setIri("owntel","tel:"+own_tel);
	query.setLiteral("ownname", own_name);
	query.setIri("ownemail", "mailto:"+own_email);
	
	query.setLiteral("ownaddress", own_address);
	query.setLiteral("manaddress",man_address);
	
	
	UpdateRequest parsedQuery=query.asUpdate();
	System.out.println(parsedQuery.toString());

	UpdateAction.execute(parsedQuery, temp);
	
	
	temp.write(System.out,"N3");
	
	//registerDevice
	TDB.registerDeviceData(ModelController.TTT_GRAPH+id+"/data",temp);
	
	
	
	

	return Response.status(Response.Status.OK).entity("Device registered. Please note down this url.To access your device visit "+Models.graphNS+id).build();

}
}
