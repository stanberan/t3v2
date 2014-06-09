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
import uk.ac.abdn.t3.t3v2.services.AnalyseTimer;
@Path("user")
public class UserResource {
	
	static Repository TDB=Repository.getSingleton();
	static DB db=DB.getDB();
	
	
	
	@GET
	@Path("/register/{userid}/{gcmid}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response registerUserWithGCM(@PathParam ("userid")String userid,@PathParam("gcmid")String gcmid){
		User u=new User(userid,gcmid);
		
	   boolean updated=db.registerUpdateUser(u);
	  
	   if(updated){
		   return Response.ok().entity(u.toJson()).build();
	   }
	   return Response.ok(new CustomError("registerUser","User not updated").toJson()).build();
		
	}
	
	
	

	@GET
	@Path("/register/device/{userid}/{deviceid}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response registerDev(@PathParam ("userid")String userid,@PathParam("deviceid")String gcmid){
		
		
	   boolean updated=db.associateDevAndUser(gcmid,userid );
	  
	   if(updated){
		   return Response.ok().entity("Associated").build();
	   }
	   return Response.ok(new CustomError("registerUser","User not updated").toJson()).build();
		
	}
}

		
		
	
	
	
	

