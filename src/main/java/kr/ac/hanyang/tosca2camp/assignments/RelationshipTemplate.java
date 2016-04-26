package kr.ac.hanyang.tosca2camp.assignments;

import java.util.*;

import kr.ac.hanyang.tosca2camp.definitiontypes.*;
import kr.ac.hanyang.tosca2camp.definitiontypes.RelationshipDef.Builder;

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
	public static class Builder {
		private String name;
		private String type;
		private String alias;
		private String description;
		private Map<String, PropertyAs> properties = new LinkedHashMap<String, PropertyAs>();
		private Map<String, AttributeAs> attributes= new LinkedHashMap<String, AttributeAs>();
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
		this.interfaces = builder.interfaces;
	}
	
	public static RelationshipTemplate clone(RelationshipTemplate origRel){
		RelationshipTemplate.Builder copyBuilder = new RelationshipTemplate.Builder(origRel.name, origRel.type);
		copyBuilder.description(origRel.description);
		for(Object propName:origRel.properties.keySet()){
			PropertyAs pDef = (PropertyAs) origRel.properties.get(propName);
			copyBuilder.addProperties(PropertyAs.clone(pDef)); //make sure pDef can create a copy
		}
		for(Object aDefName:origRel.attributes.keySet()){
			AttributeAs aDef = (AttributeAs) origRel.attributes.get(aDefName);
			copyBuilder.addAttributes(AttributeAs.clone(aDef)); //make sure pDef can create a copy
		}
		for(Object iDefName:origRel.interfaces.keySet()){
			InterfaceDef iDef = (InterfaceDef) origRel.interfaces.get(iDefName);
			copyBuilder.addInterfaces(InterfaceDef.clone(iDef)); //make sure pDef can create a copy
		}
		return copyBuilder.build();		   
	}
	
		
	public String getName() {return name;}
	
	public String getType() {return type;}

	public String getAlias() {return alias;}

	public String getDescription() {return description;}

	public Map<String, PropertyAs> getProperties() {return properties;}

	public Map<String, AttributeAs> getAttributes() {return attributes;}

	public Map<String, InterfaceDef> getInterfaces() {return interfaces;}
	
	@SuppressWarnings("unchecked")
	public Object getAttributeAs(String key){
		if (attributes.containsKey(key))
			return attributes.get(key);
		return null;
	}
	@SuppressWarnings("unchecked")
	public Object getPropertyAs(String key){
		if (properties.containsKey(key))
			return properties.get(key);
		return null;
	}

	//TODO fix toString
	public String toString(){
		return "relationship: "+type+"\n";
	}
	
}
