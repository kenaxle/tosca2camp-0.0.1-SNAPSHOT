package kr.ac.hanyang.tosca2camp.assignments;

import java.util.*;
import kr.ac.hanyang.tosca2camp.definitions.*;
import kr.ac.hanyang.tosca2camp.toscaTypes.MapEntry;

public class NodeTemplate<V> {

	private String type;
	private String description;
	private List<String> directives;
	private Map<String, PropertyAs<V>> properties;
	private Map<String, AttributeAs<V>> attributes;
	private Map<String, RequirementAs<?, ?, ?>> requirements;
	private Map<String, CapabilityAs<V>> capabilities;
	private Map<String, InterfaceDef> interfaces;
	private Map<String, ArtifactDef> artifacts;
	//TODO Nodefilter and copy should be implemented
	
	public static class Builder <V, T extends Builder<?, ?>>{

		private String type;
		private String description;
		private List<String> directives;
		private Map<String, PropertyAs<V>> properties = new LinkedHashMap<String, PropertyAs<V>>();
		private Map<String, AttributeAs<V>> attributes = new LinkedHashMap<String, AttributeAs<V>>();
		private Map<String, RequirementAs<?,?,?>> requirements = new LinkedHashMap<String, RequirementAs<?,?,?>>();
		private Map<String, CapabilityAs<V>> capabilities = new LinkedHashMap<String, CapabilityAs<V>>();
		private Map<String, InterfaceDef> interfaces = new LinkedHashMap<String, InterfaceDef>();
		private Map<String, ArtifactDef> artifacts = new LinkedHashMap<String, ArtifactDef>();
		
		public Builder( String type){

			this.type = type;
		}
		
		@SuppressWarnings("unchecked")
		public T description(String description){
			this.description = description;
			return (T) this;
		}
		
		@SuppressWarnings("unchecked")
		public T directives(List<String> directives){
			this.directives = directives;
			return (T) this;
		}
		
		@SuppressWarnings("unchecked")
		public T addProperty(PropertyAs<V> property){
			this.properties.put(property.getName(), property);
			return (T) this;
		}
		
		@SuppressWarnings("unchecked")
		public T addAttribute(AttributeAs<V> attribute){
			this.attributes.put(attribute.getName(), attribute);
			return (T) this;
		}
		
		@SuppressWarnings("unchecked")
		public T addRequirement(RequirementAs<?,?,?> requirement){
			this.requirements.put(requirement.getName(), requirement);
			return (T) this;
		}
		
		@SuppressWarnings("unchecked")
		public T addCapability(CapabilityAs<V> capability){
			this.capabilities.put(capability.getType(), capability);
			return (T) this;
		}
		
		@SuppressWarnings("unchecked")
		public T addInterface(InterfaceDef inface){
			this.interfaces.put(inface.getName(), inface);
			return (T) this;
		}
		
		@SuppressWarnings("unchecked")
		public T addArtifact(ArtifactDef artifact){
			this.artifacts.put(artifact.getName(), artifact);
			return (T) this;
		}
		
		public NodeTemplate<V> build(){
			return new NodeTemplate<V>(this);
		}
		
		
	}
	
	protected NodeTemplate(Builder<V,?> builder){

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
	
	public String getType() {return type;}

	public String getDescription() {return description;}
	
	public List<String> getDirectives() {return directives;}

	public Map<String, PropertyAs<V>> getProperties() {return properties;}

	public Map<String, AttributeAs<V>> getAttributes() {return attributes;}

	public Map<String, RequirementAs<?,?,?>> getRequirements() {return requirements;}

	public Map<String, CapabilityAs<V>> getCapabilities() {return capabilities;}

	public Map<String, InterfaceDef> getInterfaces() {return interfaces;}

	public Map<String, ArtifactDef> getArtifacts() {return artifacts;}

	//TODO fix the toString
	public String toString(){
		return "  type: "+type+"\n";	   
	}
	
}
