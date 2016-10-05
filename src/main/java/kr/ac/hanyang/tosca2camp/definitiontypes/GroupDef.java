package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kr.ac.hanyang.tosca2camp.definitiontypes.GroupDef.Builder;

public class GroupDef implements Cloneable{
	private String name;
	private String type;
	private GroupDef derived_from; //URI string
	private String description; // description are treated as their own type but for now they will be string
	private Map<String, PropertyDef> properties; 
	private Map<String, NodeDef> members;
	private Map<String, InterfaceDef> interfaces;
	
	
	public static class Builder {
		private String name;
		private String type;
		private GroupDef derived_from; //URI string
		private String description; // description are treated as their own type but for now they will be string
		private Map<String, PropertyDef> properties = new LinkedHashMap(); 
		private Map<String, NodeDef> members = new LinkedHashMap();
		private Map<String, InterfaceDef> interfaces = new LinkedHashMap();
	
		
		public Builder(String type){
			this.type = type;
		}
		
		public Builder(String name, String type) {
			this.name = name;
			this.type = type;
		}
		
		public Builder name(String name){
			this.name = name;
			return this;
		}

		public Builder derived_from(GroupDef derived_from){
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
		
		public Builder addMember(NodeDef node){
			this.members.put(node.getTypeName(),node);
			return this;
		}
		
		public Builder addInterface(InterfaceDef iFace){
			this.interfaces.put(iFace.getName(),iFace);
			return this;
		}
		
		public GroupDef build(){
			return new GroupDef(this);
		}
	}
	

	private GroupDef(Builder builder){
		this.name = builder.name;
		this.type = builder.type;
		this.derived_from = builder.derived_from;
		this.description = builder.description;
		this.properties = builder.properties;
		this.members = builder.members;
		this.interfaces = builder.interfaces;
	}
	
	
	public Object clone(){
		try{
			GroupDef toReturn = (GroupDef) super.clone();
			if(derived_from != null) toReturn.derived_from = (GroupDef)derived_from.clone();
			if(properties != null)toReturn.properties = new LinkedHashMap<String, PropertyDef>();
			for(String pDefName:properties.keySet()){
				PropertyDef pDef = properties.get(pDefName);
				toReturn.properties.put(pDefName,  (PropertyDef)pDef.clone()); //make sure pDef can create a copy
			}
			toReturn.members = new LinkedHashMap<String, NodeDef>();
			for(String aDefName:members.keySet()){
				NodeDef aDef = members.get(aDefName);
				toReturn.members.put(aDefName,  (NodeDef)aDef.clone()); //make sure aDef can create a copy
			}
			toReturn.interfaces = new LinkedHashMap<String, InterfaceDef>();
			for(String iDefName:interfaces.keySet()){
				InterfaceDef iDef = interfaces.get(iDefName);
				toReturn.interfaces.put(iDefName,  (InterfaceDef)iDef.clone()); //make sure aDef can create a copy
			}
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
		builder.members = this.members;
		builder.interfaces = this.interfaces;
		return builder;
	}
	
	public String getName(){return name;}
	public String getType(){return type;}

	public void setPropertyValue(String name, Object value){
		PropertyDef toSet = properties.get(name);
		toSet.setPropertyValue(value);
	}
	
	public String toString(){
		String props ="";
		
		for(String propName:properties.keySet()){
			PropertyDef prop = properties.get(propName);
			props+=prop;
		}
		return  "name: "+name+"\n"+
				"type: "+type+"\n"+
				"properties: \n"+props+"\n";
	}
}

