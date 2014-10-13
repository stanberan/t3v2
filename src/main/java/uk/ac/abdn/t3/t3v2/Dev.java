package uk.ac.abdn.t3.t3v2;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;


public class Dev {

	String id;
	String nickname;
	 String url;
	  public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	static java.util.ArrayList<Dev> devices=new java.util.ArrayList<Dev>();

	public static java.util.ArrayList<Dev> getDevices() {
		return devices;
	}
	public static void setDevices(java.util.ArrayList<Dev> devices) {
		Dev.devices = devices;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
