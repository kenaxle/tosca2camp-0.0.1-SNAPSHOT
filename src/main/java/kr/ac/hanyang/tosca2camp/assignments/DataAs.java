package kr.ac.hanyang.tosca2camp.assignments;

import java.util.LinkedHashMap;
import java.util.Map;

import kr.ac.hanyang.tosca2camp.definitiontypes.*;

public class DataAs {
	private String type;
	private Map<String, PropertyAs> properties;
	
	public static class Builder {
		private String type;
		private Map<String, PropertyAs> properties = new LinkedHashMap<String, PropertyAs>();
		
		public Builder(String type){
			this.type = type;
		}

		public Builder addProperty(PropertyAs property){
			this.properties.put(property.getName(), property);
			return this;
		}
		
		public DataAs build(){
			return new DataAs(this);
		}
		
		
	}
	

	protected DataAs(Builder builder){
		this.type = builder.type;
		this.properties = builder.properties;
	}
	
	public static DataAs clone(DataAs orig2Copy){
		DataAs.Builder copyBuilder = new DataAs.Builder(orig2Copy.getType());
		for(Object propName:orig2Copy.properties.keySet()){
			PropertyAs prop = (PropertyAs) orig2Copy.properties.get(propName);
			copyBuilder.addProperty(PropertyAs.clone(prop)); //make sure pDef can create a copy
		}
		return copyBuilder.build();		   
	}

	// this method will build a type using the type's definition. 
	public static Builder getDefinitionBuilder(String type, DataTypeDef definition){
		Builder builder = new Builder(definition.getTypeName());
		if (! type.equals(definition.getTypeName())) return null; //type mismatch. trying to build from the incorrect type
		for(String propName:definition.getProperties().keySet()){
			PropertyDef propDef = definition.getProperties().get(propName);
			if (propDef.isRequired()){
				builder.addProperty(PropertyAs.getDefinitionBuilder(propDef).build());
			}
		}		
		return builder;
	}
	
	public String getType() {return type;}

	public Map<String, PropertyAs> getProperties() {return properties;}
	
	public Object getPropertyAs(String key){
		if (properties.containsKey(key))
			return properties.get(key);
		return null;
	}
		
	//TODO fix the toString
	public String toString(){
		return "  type: "+type+"\n";	   
	}
	
	
}
