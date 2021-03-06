package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NodeDef implements Cloneable{
	private String name; //Node Template name
	private String typeName;
	private NodeDef derived_from; //URI string
	private String description; // description are treated as their own type but for now they will be string
	private List<String> directives; //Node template item
	private Map<String, PropertyDef> properties; 
	private Map<String, AttributeDef> attributes;
	private List<RequirementDef> requirements;
	private Map<String, CapabilityDef> capabilities;
	private Map<String, InterfaceDef> interfaces;
	private Map<String, ArtifactDef> artifacts;
	
	public static class Builder {
		private String name;
		private String typeName;
		private NodeDef derived_from; //URI string
		private String description; // description are treated as their own type but for now they will be string
		private List<String> directives;
		private Map<String, PropertyDef> properties = new LinkedHashMap<String, PropertyDef>(); 
		private Map<String, AttributeDef> attributes = new LinkedHashMap<String, AttributeDef>() ;
		private List<RequirementDef> requirements = new ArrayList<RequirementDef>();
		private Map<String, CapabilityDef> capabilities = new LinkedHashMap<String, CapabilityDef>();
		private Map<String, InterfaceDef> interfaces = new LinkedHashMap<String, InterfaceDef>();
		private Map<String, ArtifactDef> artifacts = new LinkedHashMap<String, ArtifactDef>();
		
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

		public Builder derived_from(NodeDef derived_from){
			this.derived_from = derived_from;
			return  this;
		}
		
		public Builder description(String description){
			this.description = description;
			return  this;
		}
		
		public Builder directives(List<String> directives){
			this.directives = directives;
			return this;
		}
		
		public Builder addProperty(PropertyDef property){
			this.properties.put(property.getName(),property);
			return  this;
		}
		
		public Builder addAttribute(AttributeDef attribute){
			this.attributes.put(attribute.getName(),attribute);
			return  this;
		}
		
		public Builder addRequirement(RequirementDef requirement){
			this.requirements.add(requirement);
			return  this;
		}
		
		public Builder addCapabilitiy(CapabilityDef capability){
			this.capabilities.put(capability.getName(),capability);
			return  this;
		}
		
		public Builder addInterface(InterfaceDef iface){
			this.interfaces.put(iface.getName(),iface);
			return  this;
		}
		
		public Builder addArtifact(ArtifactDef artifact){
			this.artifacts.put(artifact.getName(),artifact);
			return  this;
		}
		
		public NodeDef build(){
			return new NodeDef(this);
		}
	}
	
	
	
	protected NodeDef(Builder builder){
		this.name = builder.name;
		this.typeName = builder.typeName;
		this.derived_from = builder.derived_from;
		this.description = builder.description;
		this.directives = builder.directives;
		this.properties = builder.properties;
		this.attributes = builder.attributes;
		this.requirements = builder.requirements;
		this.capabilities = builder.capabilities;
		this.interfaces = builder.interfaces;
		this.artifacts = builder.artifacts;
	}

	
	//using a static clone method because I can make use of the builder to build a 
	//properly constructed clone
	@Override
	public Object clone(){
		try{
			NodeDef toReturn = (NodeDef) super.clone();
			toReturn.properties = new LinkedHashMap<String, PropertyDef>();
			for(String pDefName:properties.keySet()){
				PropertyDef pDef = properties.get(pDefName);
				toReturn.properties.put(pDefName,  (PropertyDef)pDef.clone()); //make sure pDef can create a copy
			}
			toReturn.attributes = new LinkedHashMap<String, AttributeDef>();
			for(String aDefName:attributes.keySet()){
				AttributeDef aDef = attributes.get(aDefName);
				toReturn.attributes.put(aDefName,  (AttributeDef)aDef.clone()); //make sure aDef can create a copy
			}
			toReturn.requirements = new ArrayList<RequirementDef>();
			for(RequirementDef rDef:requirements){
				toReturn.requirements.add((RequirementDef)rDef.clone()); //make sure pDef can create a copy
			}
			toReturn.capabilities = new LinkedHashMap<String, CapabilityDef>();
			for(String cDefName:capabilities.keySet()){
				CapabilityDef cDef = capabilities.get(cDefName);
				toReturn.capabilities.put(cDefName, (CapabilityDef)cDef.clone()); //make sure pDef can create a copy
			}
			return toReturn;
		}catch(CloneNotSupportedException e){
			return null;
		}		   
	}
	
	public Builder getBuilder(String typeName){
		Builder builder = new Builder(typeName);
		builder.name = this.name;
		builder.derived_from = this.derived_from;
		builder.description = this.description;
		builder.properties = this.properties;
		builder.attributes = this.attributes;
		builder.requirements = this.requirements;
		builder.capabilities = this.capabilities;
		builder.interfaces = this.interfaces;
		builder.artifacts = this.artifacts;
		return builder;
	}	
	

	public String getTypeName() {return typeName;}

	public NodeDef getDerived_from() {return derived_from;}

    public String getDescription() {return description;}

	public Map<String, PropertyDef> getProperties() {return properties;}
	
	//public PropertyDef getProperty(String name){return properties.get(name);}

	public Map<String, AttributeDef> getAttributes() {return attributes;}
	
	//public AttributeDef getAttribute(String name){return attributes.get(name);}

	public List<RequirementDef> getRequirements() {return requirements;}
	
	//might be a custom requirement then I cater for the default not being found
	public RequirementDef getRequirement(String reqName){
		RequirementDef toFind = new RequirementDef.Builder(reqName).build();
		int index = requirements.indexOf(toFind);
		if (index > 0)
			return requirements.get(index);
		return null;
	}
	
	public Map<String, CapabilityDef> getCapabilities() {return capabilities;}
	
	public CapabilityDef getCapability(String name){
		return capabilities.get(name);
	}

	public Map<String, InterfaceDef> getInterfaces() {return interfaces;}

	public Map<String, ArtifactDef> getArtifacts() {return artifacts;}
	
	
	public void setPropertyValue(String name, Object value){
		PropertyDef toSet = properties.get(name);
		toSet.setPropertyValue(value);
	}
	
	
	public String toString(){
		String props ="";
		String attrs ="";
		String caps ="";
		String reqs ="";
		for(String propName:properties.keySet()){
			PropertyDef prop = properties.get(propName);
			props+=prop;
		}
		for(String attrName:attributes.keySet()){
			AttributeDef attr = attributes.get(attrName);
			attrs+=attr;
		}
		for(String capName:capabilities.keySet()){
			CapabilityDef cap = capabilities.get(capName);
			caps+=cap;
		}
		for(RequirementDef req:requirements)
			reqs+=req;
		return  "name: "+name+"\n"+
				"type: "+typeName+"\n"+
			   //"Description: "+description+"\n"+
			   "attributes: \n"+attrs+"\n"+
			   "properties: \n"+props+"\n"+
			   "capabilities: \n"+caps+"\n"+
			   "requirements: \n"+reqs+"\n";
			   
	}
	
}
