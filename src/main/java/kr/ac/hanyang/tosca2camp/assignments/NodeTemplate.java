package kr.ac.hanyang.tosca2camp.assignments;

import java.util.*;

import kr.ac.hanyang.tosca2camp.definitiontypes.*;

public class NodeTemplate<V> {
	private String name;
	private String type;
	private String description;
	private List<String> directives;
	private Map<String, PropertyAs<V>> properties;
	private Map<String, AttributeAs<V>> attributes;
	private List<RequirementAs> requirements;
	private Map<String, CapabilityAs<V>> capabilities;
	private Map<String, InterfaceDef> interfaces;
	private Map<String, ArtifactDef> artifacts;
	//TODO Nodefilter and copy should be implemented
	
	public static class Builder <V>{
		private String name;
		private String type;
		private String description;
		private List<String> directives;
		private Map<String, PropertyAs<V>> properties = new LinkedHashMap<String, PropertyAs<V>>();
		private Map<String, AttributeAs<V>> attributes = new LinkedHashMap<String, AttributeAs<V>>();
		private List<RequirementAs> requirements = new ArrayList<RequirementAs>();
		private Map<String, CapabilityAs<V>> capabilities = new LinkedHashMap<String, CapabilityAs<V>>();
		private Map<String, InterfaceDef> interfaces = new LinkedHashMap<String, InterfaceDef>();
		private Map<String, ArtifactDef> artifacts = new LinkedHashMap<String, ArtifactDef>();
		
		public Builder( String name, String type){
			this.name = name;
			this.type = type;
		}
		
		@SuppressWarnings("unchecked")
		public Builder description(String description){
			this.description = description;
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder directives(List<String> directives){
			this.directives = directives;
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addProperty(PropertyAs<V> property){
			this.properties.put(property.getName(), property);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addAttribute(AttributeAs<V> attribute){
			this.attributes.put(attribute.getName(), attribute);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addRequirement(RequirementAs requirement){
			this.requirements.add(requirement);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addCapability(CapabilityAs<V> capability){
			this.capabilities.put(capability.getType(), capability);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addInterface(InterfaceDef inface){
			this.interfaces.put(inface.getName(), inface);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addArtifact(ArtifactDef artifact){
			this.artifacts.put(artifact.getName(), artifact);
			return this;
		}
		
		public NodeTemplate<V> build(){
			return new NodeTemplate<V>(this);
		}
		
		
	}
	
	protected NodeTemplate(Builder builder){
		this.name = builder.name;
		this.type = builder.type;
		this.description = builder.description;
		this.directives = builder.directives;
		this.properties = builder.properties;
		this.attributes = builder.attributes;
		this.requirements = builder.requirements;
		this.capabilities = builder.capabilities;
		this.interfaces = builder.interfaces;
		this.artifacts = builder.artifacts;
	}
	public String getName(){return name;}
	
	public String getType() {return type;}

	public String getDescription() {return description;}
	
	public List<String> getDirectives() {return directives;}

	public Map<String, PropertyAs<V>> getProperties() {return properties;}

	public Map<String, AttributeAs<V>> getAttributes() {return attributes;}

	public List<RequirementAs> getRequirements() {return requirements;}

	public Map<String, CapabilityAs<V>> getCapabilities() {return capabilities;}

	public Map<String, InterfaceDef> getInterfaces() {return interfaces;}

	public Map<String, ArtifactDef> getArtifacts() {return artifacts;}
	
	public V getAttributeAs(String key){
		if (attributes.containsKey(key))
			return (V) attributes.get(key);
		return null;
	}
	
	public V getPropertyAs(String key){
		if (properties.containsKey(key))
			return (V) properties.get(key);
		return null;
	}
	
	public CapabilityAs getCapabilityAs(String key){
		if (capabilities.containsKey(key))
			return capabilities.get(key);
		return null;
	}
	
	public RequirementAs getRequirementAs(String key) {
		for(RequirementAs reqItem: requirements){
			if(reqItem.getName().equals(key))
				return reqItem;
		}
		return null;
	}
	
	//TODO fix the toString
	public String toString(){
		return "  type: "+type+"\n";	   
	}
	
	
}
