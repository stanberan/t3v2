package uk.ac.abdn.t3.t3v2.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import uk.ac.abdn.t3.t3v2.Repository;
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
	@Path("/repository/clear")
	@Produces(MediaType.TEXT_PLAIN)
	public String repoclear(){
	TDB.preloadData();
	return"TDB Cleared...";
	}
	@GET
	@Path("/repository/restart")
	@Produces(MediaType.TEXT_PLAIN)
	public String reporestart(){
	TDB.preloadData();
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
	return"Notification Service Stopped";
	}

	
	
}
