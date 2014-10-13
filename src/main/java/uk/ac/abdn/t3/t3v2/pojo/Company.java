package uk.ac.abdn.t3.t3v2.pojo;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class Company {
String uri;
String name;
String logo;
String tel;
String address;
String email;
String web;
public String getWeb() {
	return web;
}
public void setWeb(String web) {
	this.web = web;
}
public String getUri() {
	return uri;
}
public void setUri(String uri) {
	this.uri = uri;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getLogo() {
	return logo;
}
public void setLogo(String logo) {
	this.logo = logo;
}
public String getTel() {
	return tel;
}
public void setTel(String tel) {
	this.tel = tel;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
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
