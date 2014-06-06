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
	
	
	@GET
	@Path("load/export")
	public String load(){
	
		Model m=ModelFactory.createDefaultModel();
		m.read("http://t3.abdn.ac.uk/ontologies/export.rdf");
	TDB.addToGraph(m, QueryHandler.busgraph);
		
		TDB.getIndependentModel(QueryHandler.busgraph).write(System.out,"TURTLE");
		
		return "Done";
	}
	
	
	@GET
	  @Path("/accepted/{device}/{iotdevice}")
	@Produces({MediaType.APPLICATION_JSON})
	  public Response accepted(@PathParam("device") String device, @PathParam("iotdevice") String iotDevice, @QueryParam("busurl") String busURL){
		
		try {
			//for stats!!
		//	conn.registerScan(device,iotDevice,new Date());
			//
			if(busURL!=null && busURL.contains("deps.at")){
			QueryHandler.registerBustStopTag(iotDevice, busURL);
			
			
			String time=Models.conn.accepted(device, iotDevice);
			if(time!=null){
				
				String accepted="{'accepted':'"+time +"'}";
				
				
				return Response.ok(accepted).build();
			}
			else if(busURL!=null && busURL.contains(Models.graphNS)){
				
				return Response.status(Response.Status.NOT_FOUND).build();
				
				
			}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
		    
	  }	
	
	
	
	
}
