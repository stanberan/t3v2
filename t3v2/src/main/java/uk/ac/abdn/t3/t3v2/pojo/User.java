package uk.ac.abdn.t3.t3v2.pojo;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class User {

	String userid;
	String gcmid;
	
	public User(String userid,String gcmid){
		this.userid=userid;
		this.gcmid=gcmid;
		
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getGcmid() {
		return gcmid;
	}
	public void setGcmid(String gcmid) {
		this.gcmid = gcmid;
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", gcmid=" + gcmid + "]";
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
