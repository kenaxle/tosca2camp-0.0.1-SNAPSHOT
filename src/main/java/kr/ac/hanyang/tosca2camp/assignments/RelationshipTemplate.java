package kr.ac.hanyang.tosca2camp.assignments;

import java.util.*;

import kr.ac.hanyang.tosca2camp.definitiontypes.*;

@SuppressWarnings("rawtypes")
public class RelationshipTemplate<V> {
	private String name;
	private String type;
	private String alias;
	private String description;
	private Map<String, PropertyAs> properties;
	private Map<String, AttributeAs> attributes;
	private Map<String, InterfaceDef> interfaces;

	//TODO copy should be implemented
	public static class Builder <V>{
		private String name;
		private String type;
		private String alias;
		private String description;
		private Map<String, PropertyAs<V>> properties = new LinkedHashMap<String, PropertyAs<V>>();
		private Map<String, AttributeAs<V>> attributes= new LinkedHashMap<String, AttributeAs<V>>();
		private Map<String, InterfaceDef> interfaces= new LinkedHashMap<String, InterfaceDef>();
		
		public Builder(String name,String type){
			this.name = name;
			this.type = type;
		}
		
		@SuppressWarnings("unchecked")
		public Builder alias(String alias){
			this.alias = alias;
			return  this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder description(String description){
			this.description = description;
			return  this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addProperties(PropertyAs property){
			this.properties.put(property.getName(),property);
			return  this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addAttributes(AttributeAs attribute){
			this.attributes.put(attribute.getName(),attribute);
			return  this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addInterfaces(InterfaceDef iterface){
			this.interfaces.put(iterface.getName(),iterface);
			return  this;
		}
		
		public RelationshipTemplate build(){
			return new RelationshipTemplate(this);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected RelationshipTemplate(Builder builder){
		this.name = builder.name;
		this.type = builder.type;
		this.alias = builder.alias;
		this.description = builder.description;		
		this.properties = builder.properties;
		this.attributes = builder.attributes;

	}
		
	public String getName() {return name;}
	
	public String getType() {return type;}

	public String getAlias() {return alias;}

	public String getDescription() {return description;}

	public Map<String, PropertyAs> getProperties() {return properties;}

	public Map<String, AttributeAs> getAttributes() {return attributes;}

	public Map<String, InterfaceDef> getInterfaces() {return interfaces;}
	
	@SuppressWarnings("unchecked")
	public V getAttributeAs(String key){
		if (attributes.containsKey(key))
			return (V) attributes.get(key);
		return null;
	}
	@SuppressWarnings("unchecked")
	public V getPropertyAs(String key){
		if (properties.containsKey(key))
			return (V) properties.get(key);
		return null;
	}

	//TODO fix toString
	public String toString(){
		return "relationship: "+type+"\n";
	}
	
}
