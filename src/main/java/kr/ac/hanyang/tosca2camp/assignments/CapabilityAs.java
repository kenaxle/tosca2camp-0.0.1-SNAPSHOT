 package kr.ac.hanyang.tosca2camp.assignments;

import java.util.*;

import kr.ac.hanyang.tosca2camp.definitiontypes.AttributeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.CapabilityDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.PropertyDef;

public class CapabilityAs {
	//public static Builder Builder;
	private String type;
	//private String derived_from; //URI string
	private String description; // description are treated as their own type but for now they will be string
	private Map<String, PropertyAs> properties;
	private Map<String, AttributeAs> attributes;
	
	public static class Builder{
		private String type;
		//private String derived_from; //URI string
		private String description; // description are treated as their own type but for now they will be string
		private Map<String, PropertyAs> properties; //= new ArrayList<PropertyAs>();
		private Map<String, AttributeAs> attributes; //= new ArrayList<AttributeAs>();
	
		public Builder(){}
		
		public Builder(String type){
			this.type = type;
			properties = new LinkedHashMap<String, PropertyAs>();
			attributes = new LinkedHashMap<String, AttributeAs>();
		}
		

//		public Builder derivedFrom(String derived_from){
//			this.derived_from = derived_from;
//			return this;
//		}
		

		public Builder description(String description){
			this.description = description;
			return this;
		}
		

		public Builder addProperty(PropertyAs property){
			properties.put(property.getName(), property);
			return this;
		}
		

		public Builder addAttribute(AttributeAs attribute){
			attributes.put(attribute.getName(), attribute);
			return this;
		}
		
		public CapabilityAs build(){
			return new CapabilityAs(this);
		}
	
	}
	
	protected CapabilityAs(Builder builder){
		this.type = builder.type;
		//this.derived_from = builder.derived_from;
		this.description = builder.description;
		this.properties = builder.properties;
		this.attributes = builder.attributes;
	}
	
	public static CapabilityAs clone(CapabilityAs origCap){
		CapabilityAs.Builder copyBuilder = new CapabilityAs.Builder(origCap.type);
		copyBuilder.description(origCap.description);
				
		for(Object propName:origCap.properties.keySet()){
			PropertyAs pDef = (PropertyAs) origCap.properties.get(propName);
			copyBuilder.addProperty(PropertyAs.clone(pDef)); //make sure pDef can create a copy
		}
		for(Object attrName:origCap.attributes.keySet()){
			AttributeAs aDef = (AttributeAs) origCap.attributes.get(attrName);
			copyBuilder.addAttribute(AttributeAs.clone(aDef)); //make sure pDef can create a copy
		}
		return copyBuilder.build();
	}
	
	public static Builder getDefinitionBuilder(String type, CapabilityDef definition){
		Builder builder = new Builder(type);
		if (! type.equals(definition.getType())) return null; //type mismatch. trying to build from the incorrect type
		if (definition.getDerived_from() != null) 
			builder = getDefinitionBuilder(definition.getDerived_from().getType(),definition.getDerived_from());
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
		
		builder/*.derivedFrom(definition.getDerived_from())*/
			   .description(definition.getDescription());
		
		return builder;	
	}
	

	public String getType() {return type;}

	//public String getDerived_from() {return derived_from;}

	public String getDescription() {return description;}

	public Map<String, PropertyAs> getProperties() {return properties;}

	public Map<String, AttributeAs> getAttributes() {return attributes;}
	
	public Object getAttributeValue(String key){
		if (attributes.containsKey(key))
			return attributes.get(key).getValue();
		return null;
	}
	public Object getPropertyValue(String key){
		if (properties.containsKey(key))
			return properties.get(key).getValue();
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CapabilityAs other = (CapabilityAs) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
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
