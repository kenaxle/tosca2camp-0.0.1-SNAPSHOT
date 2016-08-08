package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PolicyDef implements Cloneable{
	private String name; //Node Template name
	private String typeName;
	private PolicyDef derived_from;
	private String description; // description are treated as their own type but for now they will be string
	private Map<String, PropertyDef> properties; 
	private List<String> targets;
	
	
	public static class Builder {
		private String name;
		private String typeName;
		private PolicyDef derived_from;
		private String description; // description are treated as their own type but for now they will be string
		private Map<String, PropertyDef> properties = new LinkedHashMap<String, PropertyDef>(); 
		private List<String> targets = new ArrayList<String>();
		
		//used to builde a type Definition
		public Builder(String typeName){
			this.typeName = typeName;
		}
		
		//usede to builde a NodeTemplate
		public Builder(String name, String typeName) {   
			this.name = name;
			this.typeName = typeName;
		}
		
		public Builder name(String name){
			this.name = name;
			return  this;
		}

		public Builder derived_from(PolicyDef derived_from){
			this.derived_from = derived_from;
			return  this;
		}
		
		public Builder description(String description){
			this.description = description;
			return  this;
		}
		
		
		public Builder addProperty(PropertyDef property){
			this.properties.put(property.getName(),property);
			return  this;
		}
		
		public Builder addTargets(String target){
			this.targets.add(target);
			return  this;
		}
		
		public PolicyDef build(){
			return new PolicyDef(this);
		}
	}
	
	
	
	protected PolicyDef(Builder builder){
		this.name = builder.name;
		this.typeName = builder.typeName;
		this.derived_from = builder.derived_from;
		this.description = builder.description;
		this.properties = builder.properties;
		this.targets = builder.targets;
	}

	
	//using a static clone method because I can make use of the builder to build a 
	//properly constructed clone
	@Override
	public Object clone(){
		try{
			PolicyDef toReturn = (PolicyDef) super.clone();
			toReturn.properties = new LinkedHashMap<String, PropertyDef>();
			for(String pDefName:properties.keySet()){
				PropertyDef pDef = properties.get(pDefName);
				toReturn.properties.put(pDefName,  (PropertyDef)pDef.clone()); //make sure pDef can create a copy
			}
			toReturn.targets = new ArrayList<String>();
//			for(String nDef:targets){
//				toReturn.targets.add((NodeDef)nDef.clone()); //make sure pDef can create a copy
//			}
			return toReturn;
		}catch(CloneNotSupportedException e){
			return null;
		}		   
	}
	
	public Builder getBuilder(String typeName){
		Builder builder = new Builder(typeName);
		builder.name = this.name;
		builder.description = this.description;
		builder.properties = this.properties;
		builder.targets = this.targets;
		return builder;
	}	
	

	public String getTypeName() {return typeName;}

    public String getDescription() {return description;}

	public Map<String, PropertyDef> getProperties() {return properties;}
	
	//public PropertyDef getProperty(String name){return properties.get(name);}

	//public AttributeDef getAttribute(String name){return attributes.get(name);}

	public List<String> getTargets() {return targets;}
	
	//might be a custom requirement then I cater for the default not being found
//	public String getTarget(String tName){
//		String toFind = new NodeDef.Builder(tName).build();
//		int index = targets.indexOf(toFind);
//		if (index > 0)
//			return targets.get(index);
//		return null;
//	}
	
	public void setPropertyValue(String name, Object value){
		PropertyDef toSet = properties.get(name);
		toSet.setPropertyValue(value);
	}
	
	
	public String toString(){
		String props ="";
		String attrs ="";
		String caps ="";
		String nodes ="";
		for(String propName:properties.keySet()){
			PropertyDef prop = properties.get(propName);
			props+=prop;
		}
		for(String nDef:targets)
			nodes+=nDef;
		return  "name: "+name+"\n"+
				"type: "+typeName+"\n"+
			    "properties: \n"+props+"\n"+
			    "requirements: \n"+nodes+"\n";
			   
	}
	
}
