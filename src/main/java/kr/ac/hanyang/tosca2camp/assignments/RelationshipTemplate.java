package kr.ac.hanyang.tosca2camp.assignments;

import java.util.*;
import kr.ac.hanyang.tosca2camp.definitions.*;
import kr.ac.hanyang.tosca2camp.toscaTypes.MapEntry;

@SuppressWarnings("rawtypes")
public class RelationshipTemplate {
	private String name;
	private String type;
	private String alias;
	private String description;
	private Map<String, PropertyAs> properties;
	private Map<String, AttributeAs> attributes;
	private Map<String, InterfaceDef> interfaces;

	//TODO copy should be implemented
	public static class Builder <T extends Builder<T>>{
		private String name;
		private String type;
		private String alias;
		private String description;
		private Map<String, PropertyAs> properties;
		private Map<String, AttributeAs> attributes;
		private Map<String, InterfaceDef> interfaces;
		
		public Builder(String name,String type){
			this.name = name;
			this.type = type;
		}
		
		@SuppressWarnings("unchecked")
		public T alias(String alias){
			this.alias = alias;
			return (T) this;
		}
		
		@SuppressWarnings("unchecked")
		public T description(String description){
			this.description = description;
			return (T) this;
		}
		
		@SuppressWarnings("unchecked")
		public T addProperties(PropertyAs property){
			this.properties.put(property.getName(),property);
			return (T) this;
		}
		
		@SuppressWarnings("unchecked")
		public T addAttributes(AttributeAs attribute){
			this.attributes.put(attribute.getName(),attribute);
			return (T) this;
		}
		
		@SuppressWarnings("unchecked")
		public T addInterfaces(InterfaceDef iterface){
			this.interfaces.put(iterface.getName(),iterface);
			return (T) this;
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

	//TODO fix toString
	public String toString(){
		return "relationship: "+type+"\n";
	}
	
}
