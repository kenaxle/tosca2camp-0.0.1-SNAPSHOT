package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.ArrayList;
import java.util.List;

import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;
import kr.ac.hanyang.tosca2camp.assignments.CapabilityAs;
import kr.ac.hanyang.tosca2camp.assignments.NodeTemplate;
import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;

public class NodeDef {
	private String name;
	private String type;
	private String derived_from; //URI string
	private String description; // description are treated as their own type but for now they will be string
	private List<PropertyDef> properties; 
	private List<AttributeDef> attributes;
	private List<RequirementDef> requirements;
	private List<CapabilityDef> capabilities;
	private List<InterfaceDef> interfaces;
	private List<ArtifactDef> artifacts;
	
	public static class Builder {
		private String name;
		private String type;
		private String derived_from; //URI string
		private String description; // description are treated as their own type but for now they will be string
		private List<PropertyDef> properties = new ArrayList(); 
		private List<AttributeDef> attributes = new ArrayList() ;
		private List<RequirementDef> requirements = new ArrayList();
		private List<CapabilityDef> capabilities = new ArrayList();
		private List<InterfaceDef> interfaces = new ArrayList();
		private List<ArtifactDef> artifacts = new ArrayList();
		
		public Builder(String name, String type){
			this.name = name;
			this.type = type;
		}
		
		public Builder() {}

		public Builder derived_from(String derived_from){
			this.derived_from = derived_from;
			return  this;
		}
		
		public Builder description(String description){
			this.description = description;
			return  this;
		}
		public Builder addProperty(PropertyDef property){
			this.properties.add(property);
			return  this;
		}
		
		public Builder addAttribute(AttributeDef attribute){
			this.attributes.add(attribute);
			return  this;
		}
		
		public Builder addRequirement(RequirementDef requirement){
			this.requirements.add(requirement);
			return  this;
		}
		
		public Builder addCapabilitiy(CapabilityDef capability){
			this.capabilities.add(capability);
			return  this;
		}
		
		public Builder addInterface(InterfaceDef iface){
			this.interfaces.add(iface);
			return  this;
		}
		
		public Builder addArtifact(ArtifactDef artifact){
			this.artifacts.add(artifact);
			return  this;
		}
		
		public NodeDef build(){
			return new NodeDef(this);
		}
	}
	
	
	
	protected NodeDef(Builder builder){
		this.name = builder.name;
		this.type = builder.type;
		this.derived_from = builder.derived_from;
		this.description = builder.description;
		this.properties = builder.properties;
		this.attributes = builder.attributes;
		this.requirements = builder.requirements;
		this.capabilities = builder.capabilities;
		this.interfaces = builder.interfaces;
		this.artifacts = builder.artifacts;
	}
	
	public static NodeDef clone(NodeDef orig2Copy){
		NodeDef.Builder copyBuilder = new NodeDef.Builder(orig2Copy.getName(), orig2Copy.getType());
		copyBuilder.derived_from(orig2Copy.getDerived_from())
				   .description(orig2Copy.getDescription());
		for(PropertyDef pDef:orig2Copy.properties){
			copyBuilder.addProperty(PropertyDef.clone(pDef)); //make sure pDef can create a copy
		}
		for(AttributeDef aDef:orig2Copy.attributes){
			copyBuilder.addAttribute(AttributeDef.clone(aDef)); //make sure pDef can create a copy
		}
		for(RequirementDef rDef:orig2Copy.requirements){
			copyBuilder.addRequirement(RequirementDef.clone(rDef)); //make sure pDef can create a copy
		}
		for(CapabilityDef cDef:orig2Copy.capabilities){
			copyBuilder.addCapabilitiy(CapabilityDef.clone(cDef)); //make sure pDef can create a copy
		}
		return copyBuilder.build();		   
	}
	
	public Builder getBuilder(String name, String type){
		Builder builder = new Builder(name,type);
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
	
	public String getName() {return name;}

	public String getType() {return type;}

	public String getDerived_from() {return derived_from;}

    public String getDescription() {return description;}

	public List<PropertyDef> getProperties() {return properties;}

	public List<AttributeDef> getAttributes() {return attributes;}

	public List<RequirementDef> getRequirements() {return requirements;}

	public List<CapabilityDef> getCapabilities() {return capabilities;}

	public List<InterfaceDef> getInterfaces() {return interfaces;}

	public List<ArtifactDef> getArtifacts() {return artifacts;}
	
	public String toString(){
		String props ="";
		String attrs ="";
		String caps ="";
		String reqs ="";
		for(PropertyDef prop:properties)
			props+=prop;
		for(AttributeDef attr:attributes)
			attrs+=attr;
		for(CapabilityDef cap:capabilities)
			caps+=cap;
		for(RequirementDef req:requirements)
			reqs+=req;
		return "Name: "+name+"\n"+
			   "Type: "+type+"\n"+
			   "Description: "+description+"\n"+
			   "properties: \n"+props+"\n"+
			   "attributes: \n"+attrs+"\n"+
			   "capabilities: \n"+caps+"\n"+
			   "requirements: \n"+reqs+"\n";
			   
	}

	public boolean validate(NodeTemplate node){
		boolean valid = true;
		node.getType();
		for(PropertyDef pDef: properties){
			if(!pDef.validate((PropertyAs)node.getPropertyAs(pDef.getName())))
				valid = false;
		}
		for(AttributeDef aDef: attributes){
			if(!aDef.validate((AttributeAs)node.getAttributeAs(aDef.getName())))
				valid = false;
		}
		for(CapabilityDef cDef: capabilities){
			CapabilityAs capAs = node.getCapabilityAs(cDef.getName());
			if (capAs != null){
				valid = cDef.validate(capAs);
			}else
				valid = false;
		}
		for(RequirementDef rDef: requirements){
			RequirementAs reqAs = node.getRequirementAs(rDef.getCapDefName());
			if (reqAs != null){
				valid = rDef.validate(reqAs);
			}else
				valid = false;
		}
		return valid;
	}
	
}
