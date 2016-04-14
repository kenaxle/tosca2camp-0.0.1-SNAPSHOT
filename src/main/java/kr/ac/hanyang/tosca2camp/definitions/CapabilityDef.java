package kr.ac.hanyang.tosca2camp.definitions;

import java.util.*;

public class CapabilityDef {
	private String name;
	private String type;
	private String derived_from; //URI string
	private String description; // description are treated as their own type but for now they will be string
	private List<PropertyDef> properties; 
	private List<AttributeDef> attributes;
	private List<String> valid_source_types;
	
	public static class Builder <T extends Builder>{
		private String name;
		private String type;
		private String derived_from; //URI string
		private String description; // description are treated as their own type but for now they will be string
		private List<PropertyDef> properties; 
		private List<AttributeDef> attributes;
		private List<String> valid_source_types;
		
		public Builder(String name, String type){
			this.name = name;
			this.type = type;
		}
		
		public T derived_from(String derived_from){
			this.derived_from = derived_from;
			return (T) this;
		}
		
		public T description(String description){
			this.description = description;
			return (T) this;
		}
		public T properties(List<PropertyDef> properties){
			this.properties = properties;
			return (T) this;
		}
		
		public T attributes(List<AttributeDef> attributes){
			this.attributes = attributes;
			return (T) this;
		}
		
		public T valid_source_types(List<String> valid_source_types){
			this.valid_source_types = valid_source_types;
			return (T) this;
		}
		
		public CapabilityDef build(){
			return new CapabilityDef(this);
		}
	}
	
	private CapabilityDef(Builder builder){
		this.name = builder.name;
		this.type = builder.type;
		this.derived_from = builder.derived_from;
		this.description = builder.description;
		this.properties = builder.properties;
		this.attributes = builder.attributes;
		this.valid_source_types = builder.valid_source_types;
	}

	public String getName() {return name;}

	public String getType() {return type;}

	public String getDerived_from() {return derived_from;}

	public String getDescription() {return description;}

	public List<PropertyDef> getProperties() {return properties;}

	public List<AttributeDef> getAttributes() {return attributes;}

	public List<String> getValid_source_types() {return valid_source_types;}
	
	

}
