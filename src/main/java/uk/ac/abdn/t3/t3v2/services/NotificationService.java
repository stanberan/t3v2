package uk.ac.abdn.t3.t3v2.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;



public class NotificationService {

	
	
	
	
	public static  boolean notifyUser(String gcm,JSONObject message){
	//	System.getProperties().put("proxySet","true");
		//   System.getProperties().put("proxyHost","proxy.abdn.ac.uk");
	//	   System.getProperties().put("https.proxyHost","proxy.abdn.ac.uk");
		//   System.getProperties().put("https.proxyPort",8080);
		//   System.setProperty("https.proxyHost", "proxy.abdn.ac.uk");
		//   System.setProperty("http.proxyHost", "proxy.abdn.ac.uk");
		//   System.setProperty("https.proxyPort","8080");
		//   System.setProperty("https.proxyPort","8080");
		String key="AIzaSyBXB-j11raRKF6coCDoT39P4roqHjSETWc";
		 DefaultHttpClient httpclient = new DefaultHttpClient();
		 HttpHost proxy=new HttpHost("proxy.abdn.ac.uk",8080);
		 httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	JSONObject notificationJson=new JSONObject();
	JSONArray ids=new JSONArray();
	ids.put(gcm);
		 notificationJson.put("registration_ids", ids);
		 notificationJson.put("data", message);
		 
	      try{
	    	  System.err.println("Executing REQUEST");
	    	  HttpPost request = new HttpPost("https://android.googleapis.com/gcm/send");
	          request.addHeader("content-type", "application/json");
	          request.setHeader("Authorization","key="+key);
	          StringEntity params = new StringEntity(notificationJson.toString(),"UTF-8");
	          request.setEntity(params);
	          HttpResponse response = httpclient.execute(request);
	          System.err.println("Resp:"+response.getStatusLine().getStatusCode());
	       
	    	
	return true;
	    
	      }
	      catch(Exception e){
	    	  e.printStackTrace();
	    	  return false;
	      }
	    }
	public static void main (String args[]){
		NotificationService.notifyUser("APA91bHSGYQgnQ2fwM0plAkDvHfDvMwWBtrZqiCu9OT3xAVZpDtr7_Ps2fasXE2EnF5kYdZ_CQ2fGh7U1wmhD8M4i4mrM3501wY2p4qyET46ZXfInicXD7mxrU_CQQXsh7dv7uEPFdTEqVeTBUWSM-g-bFFswx5Ljw", new JSONObject("{\"cap\":\"new capabilities\"}"));
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