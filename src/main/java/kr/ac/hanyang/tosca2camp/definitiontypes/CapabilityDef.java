package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.*;

import kr.ac.hanyang.tosca2camp.assignments.CapabilityAs;
import kr.ac.hanyang.tosca2camp.definitiontypes.PropertyDef.Builder;

public class CapabilityDef {
	private String name;
	private String type;
	private CapabilityDef derived_from; //URI string
	private String description; // description are treated as their own type but for now they will be string
	private Map<String, PropertyDef> properties; 
	private Map<String, AttributeDef> attributes;
	private List<String> valid_source_types;
	
	public static class Builder {
		private String name;
		private String type;
		private CapabilityDef derived_from; //URI string
		private String description; // description are treated as their own type but for now they will be string
		private Map<String, PropertyDef> properties = new LinkedHashMap<String, PropertyDef>(); 
		private Map<String, AttributeDef> attributes = new LinkedHashMap<String, AttributeDef>();
		private List<String> valid_source_types = new ArrayList<String>();
		
		public Builder(String name, String type){
			this.name = name;
			this.type = type;
		}
		
		public Builder derived_from(CapabilityDef derived_from){
			this.derived_from = derived_from;
			return this;
		}
		
		public Builder description(String description){
			this.description = description;
			return this;
		}
		public Builder addProperty(PropertyDef property){
			this.properties.put(property.getName(),property);
			return this;
		}
		
		public Builder addAttribute(AttributeDef attribute){
			this.attributes.put(attribute.getName(),attribute);
			return this;
		}
		
		public Builder addValid_source_types(String valid_source_type){
			this.valid_source_types.add(valid_source_type);
			return this;
		}
		
		public CapabilityDef build(){
			return new CapabilityDef(this);
		}
	}
		
	public static CapabilityDef clone(CapabilityDef origCap){
		CapabilityDef.Builder copyBuilder = new CapabilityDef.Builder(origCap.name,origCap.type);
		copyBuilder.description(origCap.description)
				   .derived_from(origCap.derived_from);
		for(String pDefName:origCap.properties.keySet()){
			PropertyDef pDef = origCap.properties.get(pDefName);
			copyBuilder.addProperty(PropertyDef.clone(pDef)); //make sure pDef can create a copy
		}
		for(String aDefName:origCap.attributes.keySet()){
			AttributeDef aDef = origCap.attributes.get(aDefName);
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
		Builder builder = new Builder(name,type);
		builder.derived_from = this.derived_from;
		builder.description = this.description;
		builder.properties = this.properties;
		builder.attributes = this.attributes;
		builder.valid_source_types = this.valid_source_types;
		return builder;
		
	}
	

	public String getName() {return name;}

	public String getType() {return type;}

	public CapabilityDef getDerived_from() {return derived_from;}

	public String getDescription() {return description;}

	public Map<String, PropertyDef> getProperties() {return properties;}

	public Map<String, AttributeDef> getAttributes() {return attributes;}

	public List<String> getValid_source_types() {return valid_source_types;}
	
	public String toString(){
		return "name: "+name+"\n"+
			   "type: "+type+"\n";
	}

	public boolean validate(CapabilityAs capAs){
		return false;
	}
	
}
