package uk.ac.abdn.t3.t3v2.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import uk.ac.abdn.t3.t3v2.DB;
import uk.ac.abdn.t3.t3v2.Repository;
import uk.ac.abdn.t3.t3v2.pojo.CustomError;
import uk.ac.abdn.t3.t3v2.pojo.Device;
import uk.ac.abdn.t3.t3v2.pojo.User;
import uk.ac.abdn.t3.t3v2.services.InferenceService;
import uk.ac.abdn.t3.t3v2.services.QueryService;
@Path("user")
public class UserResource {
	
	static Repository TDB=Repository.getSingleton();
	static DB db=DB.getDB();
	static InferenceService inferenceService=InferenceService.getService();
	
	
	
	@GET
	@Path("/register/{userid}/{gcmid}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response registerUserWithGCM(@PathParam ("userid")String userid,@PathParam("gcmid")String gcmid){
		User u=new User(userid,gcmid);
		
	   boolean updated=db.registerUpdateUser(u);
	  
	   if(updated){
		   return Response.ok().entity(u.toJson()).build();
	   }
	   return Response.noContent().entity(new CustomError("registerUser","Users gcm not updated").toJson()).build();
		
	}
	
/*
@Path("compare/{type}")
@Produces(MediaType.TEXT_PLAIN)
public String get(@PathParam("type") String type) {
InferenceService infService=InferenceService.getService();

//infService.compareCapabilities("simbbox001", "user");
return "Done";

}*/
	
	
	@GET
	@Path("/accept/capabilities/{userid}/{devid}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response acceptCap(@PathParam ("userid")String userid,@PathParam("devid")String devid){
		
		
	   boolean exist=db.associateDevAndUser(devid,userid );
	   inferenceService.changeAcceptedCapabilities(userid, devid);
	     
	   if(exist){
		   
		   return Response.ok().entity("Associated").build();
	   }
	   return Response.noContent().entity(new CustomError("registerUser","User not updated").toJson()).build();
		
	}

	

	@GET
	@Path("/register/device/{userid}/{deviceid}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response registerDev(@PathParam ("userid")String userid,@PathParam("deviceid")String devid){
		
		
	   boolean exist=db.associateDevAndUser(devid,userid );
	
	     
	   if(exist){
		   return Response.ok().entity("Associated").build();
	   }
	   return Response.noContent().entity(new CustomError("registerUser","User not updated").toJson()).build();
		
	}
}

		
		
	
	
	
	

