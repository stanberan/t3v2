package uk.ac.abdn.t3.t3v2.pojo;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class Device {

	String devid;
	ArrayList<String> users;
	ArrayList<String> googleId;
	
	public String getDevid() {
		return devid;
	}
	public void setDevid(String devid) {
		this.devid = devid;
	}
	public ArrayList<String> getUsers() {
		return users;
	}
	public void setUsers(ArrayList<String> users) {
		this.users = users;
	}
	public ArrayList<String> getGoogleId() {
		return googleId;
	}
	public void setGoogleId(ArrayList<String> googleId) {
		this.googleId = googleId;
	}
	@Override
	public String toString() {
		return "Device [devid=" + devid + ", users=" + users + ", googleId="
				+ googleId + "]";
	}
	public String toJson(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
		try {
			return mapper.writeValueAsString(this);
		} catch (Exception e) {
		}
		return "{\"error\":\"couldnotgenerate json"+this.toString()+"\"}";
	
		
	}
	
	
}
