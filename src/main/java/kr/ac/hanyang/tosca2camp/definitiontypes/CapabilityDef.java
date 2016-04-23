package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.*;

import kr.ac.hanyang.tosca2camp.definitiontypes.PropertyDef.Builder;

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
		private List<PropertyDef> properties = new ArrayList(); 
		private List<AttributeDef> attributes = new ArrayList();
		private List<String> valid_source_types = new ArrayList();
		
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
		public T addProperty(PropertyDef property){
			this.properties.add(property);
			return (T) this;
		}
		
		public T addAttribute(AttributeDef attribute){
			this.attributes.add(attribute);
			return (T) this;
		}
		
		public T addValid_source_types(String valid_source_type){
			this.valid_source_types.add(valid_source_type);
			return (T) this;
		}
		
		public CapabilityDef build(){
			return new CapabilityDef(this);
		}
	}
		
	public static CapabilityDef clone(CapabilityDef origCap){
		CapabilityDef.Builder<Builder> copyBuilder = new CapabilityDef.Builder<Builder>(origCap.name,origCap.type);
		copyBuilder.description(origCap.description)
				   .derived_from(origCap.derived_from);
		for(PropertyDef pDef:origCap.properties){
			copyBuilder.addProperty(PropertyDef.clone(pDef)); //make sure pDef can create a copy
		}
		for(AttributeDef aDef:origCap.attributes){
			copyBuilder.addAttribute(AttributeDef.clone(aDef)); //make sure pDef can create a copy
		}
		return copyBuilder.build();
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
	
	public Builder getBuilder(String name, String type){
		Builder builder = new Builder<Builder>(name,type);
		builder.derived_from = this.derived_from;
		builder.description = this.description;
		builder.properties = this.properties;
		builder.attributes = this.attributes;
		builder.valid_source_types = this.valid_source_types;
		return builder;
		
	}
	

	public String getName() {return name;}

	public String getType() {return type;}

	public String getDerived_from() {return derived_from;}

	public String getDescription() {return description;}

	public List<PropertyDef> getProperties() {return properties;}

	public List<AttributeDef> getAttributes() {return attributes;}

	public List<String> getValid_source_types() {return valid_source_types;}
	
	public String toString(){
		return "name: "+name+"\n"+
			   "type: "+type+"\n";
	}

}
