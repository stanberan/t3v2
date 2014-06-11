package uk.ac.abdn.t3.t3v2.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;


public class NotificationService {

	
	
	
	
	public static  boolean notifyUser(String gcm,String message){
		
		String key="AIzaSyBXB-j11raRKF6coCDoT39P4roqHjSETWc";
		 DefaultHttpClient httpclient = new DefaultHttpClient();
	      try{
	    	  HttpPost request = new HttpPost("https://android.googleapis.com/gcm/send");
	    	   List<NameValuePair> postParameters;
	    	  postParameters = new ArrayList<NameValuePair>();
	    	    postParameters.add(new BasicNameValuePair("registration_id", gcm));
	    	    postParameters.add(new BasicNameValuePair("data", message));
	          request.addHeader("content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	          request.setHeader("Authorization","key="+key);
	          request.setEntity(new UrlEncodedFormEntity(postParameters));
	          HttpResponse response = httpclient.execute(request);
	       
	    	
	return true;
	    
	      }
	      catch(Exception e){
	    	  e.printStackTrace();
	    	  return false;
	      }
	    }
	public static void main (String args[]){
		NotificationService.notifyUser("APA91bE2d7yn0nGsiHssdKjOqkXy8rFp28xWerD0VPvm8q9xKj9DZm__SmSKGohZDLv2YfhdpCB_JmNKlehbJ4skCd4Kyi0RDIfofzCK7iQX1lLFHCOs6z3Er7v2oWN0fvYxi2gRogtGzxuyTFBwnatrcifkCSqNyQ", "TEST");
	}
		
	}

	
	
	


//for each device
	//for each user that is registered for device
		//get acc cap graph   if any
		//calculate current cap for device
		//compare them. if new cap or changed -send notification to registered user
		




//save cap
//get temp cap from dataset and remove them, assign permanent address. for user


//model android!