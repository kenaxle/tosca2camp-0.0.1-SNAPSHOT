package kr.ac.hanyang.tosca2camp.assignments;

import java.util.*;

import kr.ac.hanyang.tosca2camp.definitiontypes.*;


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
		

		public Builder alias(String alias){
			this.alias = alias;
			return  this;
		}
		

		public Builder description(String description){
			this.description = description;
			return  this;
		}
		

		public Builder addProperty(PropertyAs property){
			this.properties.put(property.getName(),property);
			return  this;
		}
		

		public Builder addAttribute(AttributeAs attribute){
			this.attributes.put(attribute.getName(),attribute);
			return  this;
		}
		

		public Builder addInterfaces(InterfaceDef iterface){
			this.interfaces.put(iterface.getName(),iterface);
			return  this;
		}
		
		public RelationshipTemplate build(){
			return new RelationshipTemplate(this);
		}
	}
	

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
			copyBuilder.addProperty(PropertyAs.clone(pDef)); //make sure pDef can create a copy
		}
		for(Object aDefName:origRel.attributes.keySet()){
			AttributeAs aDef = (AttributeAs) origRel.attributes.get(aDefName);
			copyBuilder.addAttribute(AttributeAs.clone(aDef)); //make sure pDef can create a copy
		}
		for(Object iDefName:origRel.interfaces.keySet()){
			InterfaceDef iDef = (InterfaceDef) origRel.interfaces.get(iDefName);
			copyBuilder.addInterfaces(InterfaceDef.clone(iDef)); //make sure pDef can create a copy
		}
		return copyBuilder.build();		   
	}
	
	
	public static Builder getDefinitionBuilder(String name, String type, NodeDef definition){
		Builder builder = new Builder(name,type);
		if (! type.equals(definition.getTypeName())) return null; //type mismatch. trying to build from the incorrect type
		if (definition.getDerived_from() != null) 
			builder = getDefinitionBuilder(name,definition.getDerived_from().getTypeName(),definition.getDerived_from()); // recursively build the parent
		for(String propName:definition.getProperties().keySet()){
			PropertyDef propDef = definition.getProperties().get(propName);
			if (propDef.isRequired()){
				builder.addProperty(PropertyAs.getDefinitionBuilder(propDef).build());
			}
		}
		
		for(String attrName:definition.getAttributes().keySet()){
			AttributeDef attrDef = definition.getAttributes().get(attrName);
			builder.addAttribute(AttributeAs.getDefinitionBuilder(attrDef).build());
		}
		
		return builder;
	}
		
	public String getName() {return name;}
	
	public String getType() {return type;}

	public String getAlias() {return alias;}

	public String getDescription() {return description;}

	public Map<String, PropertyAs> getProperties() {return properties;}

	public Map<String, AttributeAs> getAttributes() {return attributes;}

	public Map<String, InterfaceDef> getInterfaces() {return interfaces;}
	

	public Object getAttributeAs(String key){
		if (attributes.containsKey(key))
			return attributes.get(key);
		return null;
	}

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
