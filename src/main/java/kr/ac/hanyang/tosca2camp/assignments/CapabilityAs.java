package kr.ac.hanyang.tosca2camp.assignments;

import java.util.*;

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
		
		@SuppressWarnings("unchecked")
		public T derivedFrom(String derived_from){
			this.derived_from = derived_from;
			return (T) this;
		}
		
		@SuppressWarnings("unchecked")
		public T description(String description){
			this.description = description;
			return (T) this;
		}
		
		@SuppressWarnings("unchecked")
		public T addProperty(PropertyAs<V> property){
			properties.put(property.getName(), property);
			return (T) this;
		}
		
		@SuppressWarnings("unchecked")
		public T addAttribute(AttributeAs<V> attribute){
			attributes.put(attribute.getName(), attribute);
			return (T) this;
		}
		
		public CapabilityAs<V> build(){
			return new CapabilityAs<V>(this);
		}
	
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
	
	public V getAttributeValue(String key){
		if (attributes.containsKey(key))
			return (V) attributes.get(key).getValue();
		return null;
	}
	public V getPropertyValue(String key){
		if (properties.containsKey(key))
			return (V) properties.get(key).getValue();
		return null;
	}

	public String toString(){
		
		String propStr = "      properties: \n";
		String attrStr = "      attributes: \n";
		for(String propAs:this.properties.keySet()){
			propStr += "      "+properties.get(propAs)+"\n";
		}
		for(String attrAs:this.attributes.keySet()){
			attrStr += "      "+attributes.get(attrAs)+"\n";
		}
		return  "   "+type+":"+"\n"+
		       propStr+attrStr;
	}
}
