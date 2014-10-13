package uk.ac.abdn.t3.t3v2.services;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class ProvToJson {

	
	String uri;
	String type;
	String name;
	
	String wasAssociatedWith;
	String wasGeneratedBy;
	String actedOnBehalfOf;
	String used;
	String Usage;
	String entity;
	String qualifiedusage;

	

	
	
	
	public String getUri() {
		return uri;
	}






	public void setUri(String uri) {
		this.uri = uri;
	}






	public String getType() {
		return type;
	}






	public void setType(String type) {
		this.type = type;
	}






	public String getName() {
		return name;
	}






	public void setName(String name) {
		this.name = name;
	}






	public String getWasAssociatedWith() {
		return wasAssociatedWith;
	}






	public void setWasAssociatedWith(String wasAssociatedWith) {
		this.wasAssociatedWith = wasAssociatedWith;
	}






	public String getWasGeneratedBy() {
		return wasGeneratedBy;
	}






	public void setWasGeneratedBy(String wasGeneratedBy) {
		this.wasGeneratedBy = wasGeneratedBy;
	}






	public String getActedOnBehalfOf() {
		return actedOnBehalfOf;
	}






	public void setActedOnBehalfOf(String actedOnBehalfOf) {
		this.actedOnBehalfOf = actedOnBehalfOf;
	}






	public String getUsed() {
		return used;
	}






	public void setUsed(String used) {
		this.used = used;
	}






	public String getUsage() {
		return Usage;
	}






	public void setUsage(String usage) {
		Usage = usage;
	}






	public String getEntity() {
		return entity;
	}






	public void setEntity(String entity) {
		this.entity = entity;
	}






	public String getQualifiedusage() {
		return qualifiedusage;
	}






	public void setQualifiedusage(String qualifiedusage) {
		this.qualifiedusage = qualifiedusage;
	}






	public static void main(String args[]) throws JsonGenerationException, JsonMappingException, IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		
		ProvToJson activity= new ProvToJson();

		activity.setUri("http://t3.abdn.ac./Activity1");
		activity.setType("Activity");
		activity.setWasAssociatedWith("AgentURI");
		activity.setUsed("EntityURI");
		
		ProvToJson entity= new ProvToJson();
		entity.setUri("http://t3.abdn.ac./Activity23jh3");
		entity.setType("Entity");
		entity.setWasGeneratedBy("http://t3.abdn.ac./Activity1");
		
		ProvToJson[] provdata={entity,activity};
		
		 mapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
		String json=mapper.writeValueAsString(provdata);
		System.out.println(json);
		
		
		
	}
}
