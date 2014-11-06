package uk.ac.abdn.t3.t3v2.rest;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import uk.ac.abdn.t3.t3v2.CapabilityMatchingService;
import uk.ac.abdn.t3.t3v2.DB;
import uk.ac.abdn.t3.t3v2.Repository;
import uk.ac.abdn.t3.t3v2.pojo.CustomError;
import uk.ac.abdn.t3.t3v2.pojo.Device;
import uk.ac.abdn.t3.t3v2.pojo.User;
import uk.ac.abdn.t3.t3v2.services.InferenceService;
import uk.ac.abdn.t3.t3v2.services.NotificationService;
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
	@GET
	@Path("/get/devices/{userid}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getListDevices(@PathParam ("userid") String userid){
	
		
	
	  
	   
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
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response acceptCap(@PathParam ("userid")String userid,@PathParam("devid")String devid){
		
	
		//	boolean exist=db.associateDevAndUser(devid,userid,"x12"); //ignore
		
	   try {
		inferenceService.changeAcceptedCapabilities(userid, devid);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	     JSONObject o=new JSONObject();
	     o.put("accepted","The capabilities were accepted. Thank you.");
	     
		   
		   return Response.accepted().entity(o).build();
	   
	}
@GET
@Path("/notification/test")
@Produces (MediaType.APPLICATION_JSON)
public Response notification(){
	
String user="351710054464382";
JSONObject jsondata=CapabilityMatchingService.capabilityMatch("simbbox001", user);
if(jsondata.has("currentHeaders")){
String message="Some capabilities for the "+"simbbox001"+"has changed.\nClick on this notification to retrieve them.";			
JSONObject ob=new JSONObject();
ob.put("headers",jsondata.getJSONArray("currentHeaders"));
ob.put("message", message);
ob.put("devid", "simbbox001");
ob.put("time", new Date().getTime());

	NotificationService.notifyUser("APA91bHSGYQgnQ2fwM0plAkDvHfDvMwWBtrZqiCu9OT3xAVZpDtr7_Ps2fasXE2EnF5kYdZ_CQ2fGh7U1wmhD8M4i4mrM3501wY2p4qyET46ZXfInicXD7mxrU_CQQXsh7dv7uEPFdTEqVeTBUWSM-g-bFFswx5Ljw", ob);
	return Response.ok().entity(ob.toString()).build();
}
return Response.noContent().entity(new CustomError("error","No new capabilities detected.").toJson()).build();
}


@GET
@Path("/exist/device/{userid}/{deviceid}")
@Produces(MediaType.TEXT_PLAIN)
public Response exist(@PathParam ("userid")String userid,@PathParam("deviceid")String devid){

	boolean e=db.accepted(devid, userid);
	JSONObject j=new JSONObject();
	j.put("exist", e);
	
   return Response.noContent().entity(j.toString()).build();
	
}

	@GET
	@Path("/register/device/{userid}/{deviceid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerDev(@PathParam ("userid")String userid,@PathParam("deviceid")String devid){
		
		boolean exist=false;
		
		JSONObject j=new JSONObject();
	  exist=db.associateDevAndUser(devid,userid,"My Blackbox");
	   if(exist){
		   j.put("registered", "Device registered as"+j.getString("nickname"));
		   return Response.ok().entity(j).build();
	   }
	   return Response.noContent().entity(new CustomError("registerUser","User not updated").toJson()).build();
		
	}
}
	

		
		
	
	
	
	

