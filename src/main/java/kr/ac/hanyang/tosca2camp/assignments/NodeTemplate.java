package kr.ac.hanyang.tosca2camp.assignments;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.ac.hanyang.tosca2camp.definitiontypes.*;
import kr.ac.hanyang.tosca2camp.definitiontypes.NodeDef.Builder;

public class NodeTemplate {
	private String name;
	private String type;
	private String description;
	private List<String> directives;
	private Map<String, PropertyAs> properties;
	private Map<String, AttributeAs> attributes;
	private List<RequirementAs> requirements;
	private Map<String, CapabilityAs> capabilities;
	private Map<String, InterfaceDef> interfaces;
	private Map<String, ArtifactDef> artifacts;
	//TODO Nodefilter and copy should be implemented
	
	public static class Builder {
		private String name;
		private String type;
		private String description;
		private List<String> directives;
		private Map<String, PropertyAs> properties = new LinkedHashMap<String, PropertyAs>();
		private Map<String, AttributeAs> attributes = new LinkedHashMap<String, AttributeAs>();
		private List<RequirementAs> requirements = new ArrayList<RequirementAs>();
		private Map<String, CapabilityAs> capabilities = new LinkedHashMap<String, CapabilityAs>();
		private Map<String, InterfaceDef> interfaces = new LinkedHashMap<String, InterfaceDef>();
		private Map<String, ArtifactDef> artifacts = new LinkedHashMap<String, ArtifactDef>();
		
		public Builder( String name, String type){
			this.name = name;
			this.type = type;
		}
		
		public Builder(NodeDef definition){
			
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
		public Builder addProperty(PropertyAs property){
			this.properties.put(property.getName(), property);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addAttribute(AttributeAs attribute){
			this.attributes.put(attribute.getName(), attribute);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addRequirement(RequirementAs requirement){
			this.requirements.add(requirement);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addCapability(CapabilityAs capability){
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
		
		public NodeTemplate build(){
			return new NodeTemplate(this);
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
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
	
	public static NodeTemplate clone(NodeTemplate orig2Copy){
		NodeTemplate.Builder copyBuilder = new NodeTemplate.Builder(orig2Copy.getName(), orig2Copy.getType());
		copyBuilder.description(orig2Copy.getDescription());
		for(Object propName:orig2Copy.properties.keySet()){
			PropertyAs prop = (PropertyAs) orig2Copy.properties.get(propName);
			copyBuilder.addProperty(PropertyAs.clone(prop)); //make sure pDef can create a copy
		}
		for(Object attrName:orig2Copy.attributes.keySet()){
			AttributeAs attr = (AttributeAs) orig2Copy.attributes.get(attrName);
			copyBuilder.addAttribute(AttributeAs.clone(attr)); //make sure pDef can create a copy
		}
		for(RequirementAs rDef:orig2Copy.requirements){
			copyBuilder.addRequirement(RequirementAs.clone(rDef)); //make sure pDef can create a copy
		}
		for(Object capName:orig2Copy.capabilities.keySet()){
			CapabilityAs cap = (CapabilityAs) orig2Copy.capabilities.get(capName);
			copyBuilder.addCapability(CapabilityAs.clone(cap)); //make sure pDef can create a copy
		}
		return copyBuilder.build();		   
	}

	public Builder getDefinitionBuilder(String name, String type, NodeDef definition){
		Builder builder = new Builder(name,type);
		if (! type.equals(definition.getType())) return null; //type mismatch
		
		for(String propName:definition.getProperties().keySet()){
			PropertyDef propDef = definition.getProperties().get(propName);
			if (propDef.isRequired()){
				builder.addProperty(PropertyAs.getDefinitionBuilder(propDef).build());
			}
		}
		
		for(String attrName:definition.getAttributes().keySet()){
			AttributeDef attrDef = definition.getAttributes().get(attrName);
			builder.addAttribute(AttributeAs.getDefinitionBuilder(attrDef).build());
		}
		
		for(String capName:definition.getCapabilities().keySet()){
			CapabilityDef attrDef = definition.getAttributes().get(attrName);
			Constructor constructor;
			try {
				constructor = Class.forName(definition.getType()).getConstructor(String.class);
				Object value;
				value = constructor.newInstance(attrDef.getDefaultVal());
				builder.addProperty(new PropertyAs.Builder(attrName,value).build());
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException| InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public String getName(){return name;}
	
	public String getType() {return type;}

	public String getDescription() {return description;}
	
	public List<String> getDirectives() {return directives;}

	public Map<String, PropertyAs> getProperties() {return properties;}

	public Map<String, AttributeAs> getAttributes() {return attributes;}

	public List<RequirementAs> getRequirements() {return requirements;}

	public Map<String, CapabilityAs> getCapabilities() {return capabilities;}

	public Map<String, InterfaceDef> getInterfaces() {return interfaces;}

	public Map<String, ArtifactDef> getArtifacts() {return artifacts;}
	
	public Object getAttributeAs(String key){
		if (attributes.containsKey(key))
			return attributes.get(key);
		return null;
	}
	
	public Object getPropertyAs(String key){
		if (properties.containsKey(key))
			return properties.get(key);
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
