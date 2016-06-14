package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;
import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.assignments.RelationshipTemplate;
import kr.ac.hanyang.tosca2camp.definitiontypes.RelationshipDef.Builder;

public class RelationshipDef implements Cloneable{
	private String name;
	private String type;
	private RelationshipDef derived_from; //URI string
	private String description; // description are treated as their own type but for now they will be string
	private Map<String, PropertyDef> properties; 
	private Map<String, AttributeDef> attributes;
	private Map<String, InterfaceDef> interfaces;
	private List<String> valid_target_types;
	
	public static class Builder {
		private String name;
		private String type;
		private RelationshipDef derived_from; //URI string
		private String description; // description are treated as their own type but for now they will be string
		private Map<String, PropertyDef> properties = new LinkedHashMap(); 
		private Map<String, AttributeDef> attributes = new LinkedHashMap();
		private Map<String, InterfaceDef> interfaces = new LinkedHashMap();
		private List<String> valid_target_types = new ArrayList();
		
		public Builder(String type){
			this.type = type;
		}
		
		public Builder(String name, String type) {
			this.name = name;
			this.type = type;
		}

		public Builder derived_from(RelationshipDef derived_from){
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
		
		public Builder addInterface(InterfaceDef iFace){
			this.interfaces.put(iFace.getName(),iFace);
			return this;
		}
		
		public Builder addValid_target_types(String valid_target_types){
			this.valid_target_types.add(valid_target_types);
			return this;
		}
		
		public RelationshipDef build(){
			return new RelationshipDef(this);
		}
	}
	
	
	
	private RelationshipDef(Builder builder){
		this.name = builder.name;
		this.type = builder.type;
		this.derived_from = builder.derived_from;
		this.description = builder.description;
		this.properties = builder.properties;
		this.attributes = builder.attributes;
		this.interfaces = builder.interfaces;
		this.valid_target_types = builder.valid_target_types;
	}
	
	
	public Object clone(){
		try{
			RelationshipDef toReturn = (RelationshipDef) super.clone();
			if(derived_from != null) toReturn.derived_from = (RelationshipDef)derived_from.clone();
			if(properties != null)toReturn.properties = new LinkedHashMap<String, PropertyDef>();
			for(String pDefName:properties.keySet()){
				PropertyDef pDef = properties.get(pDefName);
				toReturn.properties.put(pDefName,  (PropertyDef)pDef.clone()); //make sure pDef can create a copy
			}
			toReturn.attributes = new LinkedHashMap<String, AttributeDef>();
			for(String aDefName:attributes.keySet()){
				AttributeDef aDef = attributes.get(aDefName);
				toReturn.attributes.put(aDefName,  (AttributeDef)aDef.clone()); //make sure aDef can create a copy
			}
			toReturn.interfaces = new LinkedHashMap<String, InterfaceDef>();
			for(String iDefName:interfaces.keySet()){
				InterfaceDef iDef = interfaces.get(iDefName);
				toReturn.interfaces.put(iDefName,  (InterfaceDef)iDef.clone()); //make sure aDef can create a copy
			}
			toReturn.valid_target_types = new ArrayList<String>();
			for(String vSource: valid_target_types)
				toReturn.valid_target_types.add(vSource);
			return toReturn;
		}catch(CloneNotSupportedException e){
			return null;
		}		   
	}
	
	
	public Builder getBuilder(String type){
		Builder builder = new Builder(type);
		builder.derived_from = this.derived_from;
		builder.description = this.description;
		builder.properties = this.properties;
		builder.attributes = this.attributes;
		builder.interfaces = this.interfaces;
		builder.valid_target_types = this.valid_target_types;
		return builder;
	}

//	public boolean validate(RelationshipTemplate rTemp){
//		// (type.equals(rTemp.getType()));
//		for (String propItemName:properties.keySet()){
//			PropertyDef propItem = properties.get(propItemName);
//			if (!propItem.validate((PropertyAs)rTemp.getPropertyAs(propItem.getType())))
//				return false;
//		}
//		for (String attrItemName:attributes.keySet()){
//			AttributeDef attrItem = attributes.get(attrItemName);
//			if (!attrItem.validate((AttributeAs)rTemp.getPropertyAs(attrItem.getType())))
//				return false;
//		}
//		return true;
//	}
	
	public String toString(){
		return "type: "+type+"\n";
	}
}

