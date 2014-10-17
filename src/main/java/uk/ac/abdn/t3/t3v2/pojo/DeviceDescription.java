package uk.ac.abdn.t3.t3v2.pojo;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import uk.ac.abdn.t3.t3v2.Models;
import uk.ac.abdn.t3.t3v2.services.InferenceService;

public class DeviceDescription {

	String dev_uri;
	String name;
	String logo;
	String manufacturer;
	String owner;
	String manufacturerLogo;
	String ownerLogo;
	String man_name;
	String own_name;
	String securityDescription;
	String typeDescription;
	String deviceDescription;
	public String getManufacturerLogo() {
		return manufacturerLogo;
	}
	public void setManufacturerLogo(String manufacturerLogo) {
		this.manufacturerLogo = manufacturerLogo;
	}
	public String getOwnerLogo() {
		return ownerLogo;
	}
	public void setOwnerLogo(String ownerLogo) {
		this.ownerLogo = ownerLogo;
	}
	public String getMan_name() {
		return man_name;
	}
	public void setMan_name(String man_name) {
		this.man_name = man_name;
	}
	public String getOwn_name() {
		return own_name;
	}
	public void setOwn_name(String own_name) {
		this.own_name = own_name;
	}
	public String getDev_uri() {
		return dev_uri;
	}
	public void setDev_uri(String dev_uri) {
		this.dev_uri = dev_uri;
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
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getSecurityDescription() {
		return securityDescription;
	}
	public void setSecurityDescription(String securityDescription) {
		this.securityDescription = securityDescription;
	}
	public String getTypeDescription() {
		return typeDescription;
	}
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}
	public String getDeviceDescription() {
		return deviceDescription;
	}
	public void setDeviceDescription(String deviceDescription) {
		this.deviceDescription = deviceDescription;
	}
	
	public String toJson(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{\"error\":\"couldnotgenerate json"+this.toString()+"\"}";
	
		
	}
	public static void main(String args[]){
		
	InferenceService i=	InferenceService.getService();
	//i.getDeviceGeneralData(i.getDeviceOntModel("simbbox001"), Models.graphNS+"simbbox001");	
		
		
	}
	
}
