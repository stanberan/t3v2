package uk.ac.abdn.t3.t3v2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import uk.ac.abdn.t3.t3v2.models.ModelController;
import uk.ac.abdn.t3.t3v2.pojo.Device;
import uk.ac.abdn.t3.t3v2.pojo.User;


	
	public class DB {
		static{
			System.setProperty("http.proxyHost", "proxy.abdn.ac.uk");
			  System.setProperty("http.proxyPort", "8080");
		}
		static DB singleton=null;
		static Connection conn=null;
		static int id=12;
		 public static DB getDB(){
	   	  if (singleton!=null){
	   		  return singleton;
	   	  }
	   	  else{
	   		singleton=new DB();  
	   	  
	   	  try{
	     Class.forName(Configuration.driver).newInstance();
	     conn = DriverManager.getConnection(Configuration.url+Configuration.dbName,Configuration.userName,Configuration.password);
	     return singleton;
	   	  }
	   	  catch(Exception e){
	   		 
	   		  e.printStackTrace();
	   		  return null;
	   	  }
	   	  }
	     }
		 
	
	
		 
	
	
public boolean userExist(User u){
	
	try{
		if(conn.isClosed()){
			conn=DriverManager.getConnection(Configuration.url+Configuration.dbName,Configuration.userName,Configuration.password);
		}
		
		PreparedStatement pStatement=conn.prepareStatement("SELECT * FROM users WHERE userid=?");
		pStatement.setString(1, u.getUserid());
		if(pStatement.executeQuery().isBeforeFirst()){
			return true; 
		}
		else{
			return false;
		}
	}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
}


public boolean deviceExist(Device u){
	
	try{
		if(conn.isClosed()){
			conn=DriverManager.getConnection(Configuration.url+Configuration.dbName,Configuration.userName,Configuration.password);
		}
		
		PreparedStatement pStatement=conn.prepareStatement("SELECT * FROM devices WHERE devid=?");
		pStatement.setString(1, u.getDevid());
		if(pStatement.executeQuery().isBeforeFirst()){
			return true; 
		}
		else{
			return false;
		}
	}
		catch(Exception e){
			e.printStackTrace();
			//new
			return false;
		}
}


public ArrayList<Dev> getList(String user){
		
		ArrayList<Dev> devices=new ArrayList<Dev>();
		try{
			if(conn.isClosed()){
				conn=DriverManager.getConnection(Configuration.url+Configuration.dbName,Configuration.userName,Configuration.password);
			}
			PreparedStatement pStatement=conn.prepareStatement("SELECT * FROM accepted WHERE userid=?");
			pStatement.setString(1,user);	
			ResultSet rs= pStatement.executeQuery();
			while(rs.next()){
				System.out.println("HAS NEXT DEVICE UID");
				Dev d=new Dev();
				d.setId(rs.getString("iotuid"));
				d.setNickname(rs.getString("nickname"));
					devices.add(d);	
			}
			return devices;
		}
			catch(Exception e){
				e.printStackTrace();
				return devices;
			}
		
		
	}


public boolean accepted(String devid,String userid){
	try{
		if(conn.isClosed()){
			conn=DriverManager.getConnection(Configuration.url+Configuration.dbName,Configuration.userName,Configuration.password);
		}
		
		PreparedStatement pStatement=conn.prepareStatement("SELECT * FROM accepted WHERE devid=? AND userid=?");
		pStatement.setString(1, devid);
		pStatement.setString(2, userid);
		if(pStatement.executeQuery().isBeforeFirst()){
			return true; 
		}
		else{
			return false;
		}
	}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
}
	


public boolean associateDevAndUser(String devid,String userid){

	try{
		if(conn.isClosed()){
			conn=DriverManager.getConnection(Configuration.url+Configuration.dbName,Configuration.userName,Configuration.password);
		}
		if(!accepted(devid,userid)){
		PreparedStatement pStatement=conn.prepareStatement("INSERT into accepted values(?,?)");
		pStatement.setString(1, devid);
		pStatement.setString(2, userid);
		
	int i=pStatement.executeUpdate();
	System.out.println("IoT Device:"+devid +"was associated with user with new cap"+userid);
	return true;  //return true even if no association was done. 
		}
	}
	catch(Exception e){
		System.out.println("Error when associating device and user"+e.getMessage());
		e.printStackTrace();
		return false;
	
	}
	System.out.println("No association.");
	return false;
}
	
public boolean registerDevice(Device d){
	
	
	try{
		if(conn.isClosed()){
			conn=DriverManager.getConnection(Configuration.url+Configuration.dbName,Configuration.userName,Configuration.password);
		}
		if(!deviceExist(d)){
		PreparedStatement pStatement=conn.prepareStatement("INSERT into devices(devid) values(?)");
		pStatement.setString(1, d.getDevid());
		
	int i=pStatement.executeUpdate();
	System.out.println("Dev Registered:"+d.toString());
	return true;
		}
	}
	catch(Exception e){
		System.out.println("Error when registering ...sestatck trac"+e.getMessage());
		e.printStackTrace();
		return false;
	
	}
	System.out.println("Device not registered.");
	return false;
		
}
public boolean registerUpdateUser(User u){
	
	try{
		if(conn.isClosed()){
			conn=DriverManager.getConnection(Configuration.url+Configuration.dbName,Configuration.userName,Configuration.password);
		}
		if(!userExist(u)){
		PreparedStatement pStatement=conn.prepareStatement("INSERT into users values(?,?)");
		pStatement.setString(1, u.getUserid());
		pStatement.setString(2, u.getGcmid());
		
	int i=pStatement.executeUpdate();
	System.out.println("USER Created"+u.toString());
	return true;

		}
		else{
			PreparedStatement pStatement=conn.prepareStatement("UPDATE users SET gcmid=? WHERE userid=? ");
			pStatement.setString(2, u.getUserid());
			pStatement.setString(1, u.getGcmid());
			int i=pStatement.executeUpdate();
			System.out.println("USER UPDATED"+u.toString());
			return true;
			
		}}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	
	
	
}
	
	
	
public ArrayList<Device> getAllDevices(){
		
		ArrayList<Device> devices=new ArrayList<Device>();
	
	
		try{
			if(conn.isClosed()){
				conn=DriverManager.getConnection(Configuration.url+Configuration.dbName,Configuration.userName,Configuration.password);
			}
			PreparedStatement pStatement=conn.prepareStatement("SELECT devid FROM devices");
	
			ResultSet rs= pStatement.executeQuery();
			while(rs.next()){
				ArrayList<String> users=new ArrayList<String>();
				ArrayList<String> gcms=new ArrayList<String>();
				Device d=new Device();
				d.setDevid(rs.getString("devid"));
					
					
				

					PreparedStatement pStatement2 =conn.prepareStatement("SELECT p.userid AS userid ,p.gcmid , d.devid ,a.devid,a.userid FROM users p, accepted a, devices d WHERE a.devid=? AND a.userid=p.userid AND d.devid=a.devid");
				  pStatement2.setString(1,d.getDevid());
					ResultSet rs2=pStatement2.executeQuery();
					
					while(rs2.next()){
						users.add(rs2.getString("userid"));
						gcms.add(rs2.getString("gcmid"));
						
						
					}
					pStatement2.close();
					rs2.close();
					d.setUsers(users);
					d.setGoogleId(gcms);
					devices.add(d);		
			
			}
			
		
			return devices;
		}
			catch(Exception e){
				e.printStackTrace();
				return devices;
			}
		
		
	}
public static void main(String args[]){
  ArrayList<Device> d=DB.getDB().getAllDevices();
  for (Device dev : d){
	  System.out.println(dev.toString());
  }
  DB.getDB().registerUpdateUser(new User("54321","newGCM289374r2jhj5th5j5hj4j5"));
  
  ArrayList<Device> d1=DB.getDB().getAllDevices();
  for (Device dev : d1){
	  System.out.println(dev.toString());
  }
}
	
}
