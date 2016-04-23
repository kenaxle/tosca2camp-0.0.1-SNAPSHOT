package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.List;

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
	
	public static class Builder <T extends Builder>{
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
		
		public Builder(String name, String type){
			this.name = name;
			this.type = type;
		}
		
		public Builder() {}

		public T derived_from(String derived_from){
			this.derived_from = derived_from;
			return (T) this;
		}
		
		public T description(String description){
			this.description = description;
			return (T) this;
		}
		public T addProperty(PropertyDef property){
			this.properties.add(property);
			return (T) this;
		}
		
		public T addAttribute(AttributeDef attribute){
			this.attributes.add(attribute);
			return (T) this;
		}
		
		public T addRequirement(RequirementDef requirement){
			this.requirements.add(requirement);
			return (T) this;
		}
		
		public T addCapabilitiy(CapabilityDef capability){
			this.capabilities.add(capability);
			return (T) this;
		}
		
		public T addInterface(InterfaceDef iface){
			this.interfaces.add(iface);
			return (T) this;
		}
		
		public T addArtifact(ArtifactDef artifact){
			this.artifacts.add(artifact);
			return (T) this;
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

	public NodeDef(NodeDef orig2Copy){
		NodeDef.Builder<Builder> copyBuilder = new NodeDef.Builder<>(orig2Copy.getName(), orig2Copy.getType());
		copyBuilder.derived_from(orig2Copy.getDerived_from())
				   .description(orig2Copy.getDescription());
		for(PropertyDef pDef:properties){
			copyBuilder.addProperty(new PropertyDef(pDef)); //make sure pDef can create a copy
		}
		for(AttributeDef aDef:attributes){
			copyBuilder.addAttribute(new AttributeDef(aDef)); //make sure pDef can create a copy
		}
		for(RequirementDef rDef:requirements){
			copyBuilder.addRequirement(new RequirementDef(rDef)); //make sure pDef can create a copy
		}
		for(CapabilityDef cDef:capabilities){
			copyBuilder.addCapabilitiy(new CapabilityDef(cDef)); //make sure pDef can create a copy
		}
		copyBuilder.build();		   
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

	public Builder getBuilder(){return new Builder();}
	
	public String toString(){
		return "Name "+name+"\n"+
			   "Type "+type+"\n"+
			   "Description "+description+"\n";
	}

}
