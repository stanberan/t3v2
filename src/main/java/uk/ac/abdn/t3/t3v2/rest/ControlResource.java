package uk.ac.abdn.t3.t3v2.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import uk.ac.abdn.t3.t3v2.Repository;
import uk.ac.abdn.t3.t3v2.models.ModelController;
import uk.ac.abdn.t3.t3v2.services.AnalyseTimer;
@Path("control")
public class ControlResource {
	static AnalyseTimer notificationServiceTimer=null;
	static Repository TDB=Repository.getSingleton();
	
	
	
	
	@GET
	@Path("/loadmodels")
	@Produces(MediaType.TEXT_PLAIN)
	public String loadModels(){
	TDB.preloadData();
	return"Simboxx init data loaded...";
	}
	
	
	

	@GET
	@Path("/repository/remove/device/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response repoclear(@PathParam("id")String id){
		String graph=ModelController.TTT_GRAPH+id+"/data";
		String graph1=ModelController.TTT_GRAPH+id+"/prov";
		boolean removed=TDB.removeNamedGraph(graph);
		boolean removedprov=TDB.removeNamedGraph(graph1);
		if(removed){
		return Response.status(Response.Status.OK).entity("Removed"+graph+"and"+graph1).build();
		}
		else{
			return Response.status(Response.Status.NO_CONTENT).entity("Not Removed or doesn't exist:"+graph).build();
		}
	}
	@GET
	@Path("/repository/remove/capability/{id}/{uid}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response clearCap(@PathParam("id")String id,@PathParam("uid") String uid){
		String graph=ModelController.TTT_GRAPH+id+uid+"/accepted";
		String graph1=ModelController.TTT_GRAPH+id+uid+"/temp";
		boolean removed=TDB.removeNamedGraph(graph);
		boolean removedprov=TDB.removeNamedGraph(graph1);
		if(removed){
		return Response.status(Response.Status.OK).entity("Removed"+graph+"and"+graph1).build();
		}
		else{
			return Response.status(Response.Status.NO_CONTENT).entity("Not Removed or doesn't exist:"+graph).build();
		}
	}
	@GET
	@Path("/repository/restart")
	@Produces(MediaType.TEXT_PLAIN)
	public String reporestart(){
	TDB.restart();
	return"TDB Restarted...";
	}

	
	@GET
	@Path("/notification/start/{minute}")
	@Produces(MediaType.TEXT_PLAIN)
	public String notificationStart(@PathParam("minute") int minutes){
       if(notificationServiceTimer==null){
    	   notificationServiceTimer = new AnalyseTimer(minutes);
       }
       notificationServiceTimer.start();
		
		
	return"Notification Service Started";
	}
	
	@GET
	@Path("/notification/stop")
	@Produces(MediaType.TEXT_PLAIN)
	public String notificationStop(){
	notificationServiceTimer.stopTimer();
	notificationServiceTimer=null;
	return"Notification Service Stopped";
	}

	
	
}
