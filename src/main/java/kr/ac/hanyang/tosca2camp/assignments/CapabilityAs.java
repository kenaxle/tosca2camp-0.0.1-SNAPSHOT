package kr.ac.hanyang.tosca2camp.assignments;

import java.util.*;

import kr.ac.hanyang.tosca2camp.toscaTypes.MapEntry;

public class CapabilityAs<V> {
	private String type;
	private String derived_from; //URI string
	private String description; // description are treated as their own type but for now they will be string
	private Map<String, PropertyAs<V>> properties;
	private Map<String, AttributeAs<V>> attributes;
	
	public static class Builder <V, T extends Builder<V,T>>{
		private String type;
		private String derived_from; //URI string
		private String description; // description are treated as their own type but for now they will be string
		private Map<String, PropertyAs<V>> properties; //= new ArrayList<PropertyAs>();
		private Map<String, AttributeAs<V>> attributes; //= new ArrayList<AttributeAs>();
	
		public Builder(){}
		
		public Builder(String type){
			this.type = type;
			properties = new LinkedHashMap<String, PropertyAs<V>>();
			attributes = new LinkedHashMap<String, AttributeAs<V>>();
		}
		
		public T derivedFrom(String derived_from){
			this.derived_from = derived_from;
			return (T) this;
		}
		
		public T description(String description){
			this.description = description;
			return (T) this;
		}
		
		public T addProperty(MapEntry mapEntry){
			properties.put(mapEntry.getName(), (PropertyAs<V>) mapEntry);
			return (T) this;
		}
		
		public T addAttribute(MapEntry mapEntry){
			attributes.put(mapEntry.getName(), (AttributeAs<V>) mapEntry);
			return (T) this;
		}
		
		public CapabilityAs build(){
			return new CapabilityAs(this);
		}
	
	}
	
	public Builder getBuilder(){
		return new Builder();
	}
	
	protected CapabilityAs(Builder<V, ?> builder){
		this.type = builder.type;
		this.derived_from = builder.derived_from;
		this.description = builder.description;
		this.properties = builder.properties;
		this.attributes = builder.attributes;
	}
	

	public String getType() {return type;}

	public String getDerived_from() {return derived_from;}

	public String getDescription() {return description;}

	public Map<String, PropertyAs<V>> getProperties() {return properties;}

	public Map<String, AttributeAs<V>> getAttributes() {return attributes;}

	public String toString(){
		
		String propStr = "properties: \n";
		String attrStr = "attributes: \n";
//		for(PropertyAs propAs:this.properties){
//			propStr += propAs.toString()+"\n";
//		}
//		for(PropertyAs attrAs:this.properties){
//			attrStr += attrAs.toString()+"\n";
//		}
		return  "   type: "+type+"\n";//+
		       //propStr+attrStr;
	}
}
