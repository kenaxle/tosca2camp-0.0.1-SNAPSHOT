package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.*;

import kr.ac.hanyang.tosca2camp.assignments.CapabilityAs;
import kr.ac.hanyang.tosca2camp.definitiontypes.PropertyDef.Builder;

public class CapabilityDef {
	private String name; // for normative this should be the short name
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
		
		public Builder(String type){
			this.name = "";
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
	
	
	public CapabilityDef clone(){
		try{
			CapabilityDef toReturn = (CapabilityDef) super.clone();
			toReturn.derived_from = derived_from.clone();
			toReturn.properties = new LinkedHashMap<String, PropertyDef>();
			for(String pDefName:properties.keySet()){
				PropertyDef pDef = properties.get(pDefName);
				toReturn.properties.put(pDefName,  pDef.clone()); //make sure pDef can create a copy
			}
			toReturn.attributes = new LinkedHashMap<String, AttributeDef>();
			for(String aDefName:attributes.keySet()){
				AttributeDef aDef = attributes.get(aDefName);
				toReturn.attributes.put(aDefName,  aDef.clone()); //make sure aDef can create a copy
			}
			toReturn.valid_source_types = new ArrayList<String>();
			for(String vSource: valid_source_types)
				toReturn.valid_source_types.add(vSource);
			return toReturn;
		}catch(CloneNotSupportedException e){
			return null;
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
	
	public Builder getBuilder(){
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

//	public boolean validate(CapabilityAs capAs){
//		return false;
//	}
	
}
