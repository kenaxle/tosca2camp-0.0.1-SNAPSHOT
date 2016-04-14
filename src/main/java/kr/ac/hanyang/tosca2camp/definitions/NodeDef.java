package kr.ac.hanyang.tosca2camp.definitions;

import java.util.List;

public class NodeDef {
	private String name;
	private String type;
	private String derived_from; //URI string
	private String description; // description are treated as their own type but for now they will be string
	private List<PropertyDef> properties; 
	private List<AttributeDef> attributes;
	private List<String> requirements;
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
		private List<String> requirements;
		private List<CapabilityDef> capabilities;
		private List<InterfaceDef> interfaces;
		private List<ArtifactDef> artifacts;
		
		public Builder(String name, String type){
			this.name = name;
			this.type = type;
		}
		
		public T derived_from(String derived_from){
			this.derived_from = derived_from;
			return (T) this;
		}
		
		public T description(String description){
			this.description = description;
			return (T) this;
		}
		public T properties(List<PropertyDef> properties){
			this.properties = properties;
			return (T) this;
		}
		
		public T attributes(List<AttributeDef> attributes){
			this.attributes = attributes;
			return (T) this;
		}
		
		public T requirements(List<String> requirements){
			this.requirements = requirements;
			return (T) this;
		}
		
		public T capabilities(List<CapabilityDef> capabilities){
			this.capabilities = capabilities;
			return (T) this;
		}
		
		public T interfaces(List<InterfaceDef> interfaces){
			this.interfaces = interfaces;
			return (T) this;
		}
		
		public T artifacts(List<ArtifactDef> artifacts){
			this.artifacts = artifacts;
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

		
	public String getName() {return name;}

	public String getType() {return type;}

	public String getDerived_from() {return derived_from;}

    public String getDescription() {return description;}

	public List<PropertyDef> getProperties() {return properties;}

	public List<AttributeDef> getAttributes() {return attributes;}

	public List<String> getRequirements() {return requirements;}

	public List<CapabilityDef> getCapabilities() {return capabilities;}

	public List<InterfaceDef> getInterfaces() {return interfaces;}

	public List<ArtifactDef> getArtifacts() {return artifacts;}

	public String toString(){
		return "Name "+name+"\n"+
			   "Type "+type+"\n"+
			   "Description "+description+"\n";
	}

}
