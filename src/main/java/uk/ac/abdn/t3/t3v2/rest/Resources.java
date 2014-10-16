package uk.ac.abdn.t3.t3v2.rest;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import uk.ac.abdn.t3.t3v2.Models;
import uk.ac.abdn.t3.t3v2.QueryHandler;
import uk.ac.abdn.t3.t3v2.Repository;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;


@Path("thing")
public class Resources {
static Repository TDB=Repository.getSingleton();
	
	
	@GET
	  @Path("{id}/{user}/information")
	  @Produces({MediaType.APPLICATION_JSON })
	  public String getThing(@PathParam("id") String id, @PathParam("user") String user, @QueryParam("dev") String dev,@QueryParam("busstop") String busstop, @QueryParam("busurl") String busUrl) {
		  // if lookup is successful then get from hashtable
		  // else perform the op and put to hashtable
		  String iotid=id;
		  if(busstop!=null && busstop.equals("1")){
			//  iotid="MD5Hash";
			 QueryHandler.registerBustStopTag(id,busUrl);
			 System.out.println("Registered bus stop");
			 
			 if(!QueryHandler.busExists(QueryHandler.busgraph,iotid)){
			      throw new RuntimeException("Get: Not found with " + id +  " not found");
			  }
			 
		  }
		  
		  else{
		  if(!QueryHandler.exists(Models.graphNS+dev,iotid)){
		      throw new RuntimeException("Get: Not found with " + id +  " not found");
		  }
		  
		// ThingInformation thing=Queries.getDeviceInformation(iotid,user);
		  
		  }
		  //log user
		  //get info
		  //get cap
		  //construct Thing Information
		 return "It was found";
	  }
	

	
	

	
	
	
}
