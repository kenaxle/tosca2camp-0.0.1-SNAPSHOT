package kr.ac.hanyang.tosca2camp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.ac.hanyang.tosca2camp.definitiontypes.GroupDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.NodeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.ParameterDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.PolicyDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.RelationshipDef;

public class TopologyTemplate implements Cloneable{
	private String name; //Node Template name
	private String typeName;
	private TopologyTemplate derived_from; //URI string
	private String description; // description are treated as their own type but for now they will be string
	private List<String> directives; //Node template item
	private List<ParameterDef> inputs;
	private List<NodeDef> nodeTemplates; 
	private List<RelationshipDef> relationshipTemplates;
	private List<GroupDef> groups;
	private List<PolicyDef> policies;
	private List<ParameterDef> outputs;
	
	
	public static class Builder {
		private String name;
		private String typeName;
		private TopologyTemplate derived_from; //URI string
		private String description; // description are treated as their own type but for now they will be string
		private List<String> directives;
		private List<ParameterDef> inputs = new ArrayList<ParameterDef>();
		private List<NodeDef> nodeTemplates = new ArrayList<NodeDef>();
		private List<RelationshipDef> relationshipTemplates = new ArrayList<RelationshipDef>();
		private List<GroupDef> groups = new ArrayList<GroupDef>();
		private List<PolicyDef> policies = new ArrayList<PolicyDef>();
		private List<ParameterDef> outputs = new ArrayList<ParameterDef>();
		
		
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

		public Builder derived_from(TopologyTemplate derived_from){
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
		
		public Builder addInput(ParameterDef input){
			this.inputs.add(input);
			return  this;
		}
		
		public Builder addNode(NodeDef node){
			this.nodeTemplates.add(node);
			return  this;
		}
		
		public Builder addRelationship(RelationshipDef relationship){
			this.relationshipTemplates.add(relationship);
			return  this;
		}
		
		public Builder addGroup(GroupDef group){
			this.groups.add(group);
			return  this;
		}
		
		public Builder addPolicy(PolicyDef policy){
			this.policies.add(policy);
			return  this;
		}
		
		public Builder addOutput(ParameterDef output){
			this.outputs.add(output);
			return  this;
		}
		
		public TopologyTemplate build(){
			return new TopologyTemplate(this);
		}
	}
	
	
	
	protected TopologyTemplate(Builder builder){
		this.name = builder.name;
		this.typeName = builder.typeName;
		this.derived_from = builder.derived_from;
		this.description = builder.description;
		this.directives = builder.directives;
		this.inputs = builder.inputs;
		this.nodeTemplates = builder.nodeTemplates;
		this.relationshipTemplates = builder.relationshipTemplates;
		this.groups = builder.groups;
		this.policies = builder.policies;
		this.outputs = builder.outputs;
	}

	
	//using a static clone method because I can make use of the builder to build a 
	//properly constructed clone
	//@Override
//	public Object clone(){
//		try{
//			TopologyTemplateDef toReturn = (TopologyTemplateDef) super.clone();
//			toReturn.properties = new LinkedHashMap<String, PropertyDef>();
//			for(String pDefName:properties.keySet()){
//				PropertyDef pDef = properties.get(pDefName);
//				toReturn.properties.put(pDefName,  (PropertyDef)pDef.clone()); //make sure pDef can create a copy
//			}
//			toReturn.attributes = new LinkedHashMap<String, AttributeDef>();
//			for(String aDefName:attributes.keySet()){
//				AttributeDef aDef = attributes.get(aDefName);
//				toReturn.attributes.put(aDefName,  (AttributeDef)aDef.clone()); //make sure aDef can create a copy
//			}
//			toReturn.requirements = new ArrayList<RequirementDef>();
//			for(RequirementDef rDef:requirements){
//				toReturn.requirements.add((RequirementDef)rDef.clone()); //make sure pDef can create a copy
//			}
//			toReturn.capabilities = new LinkedHashMap<String, CapabilityDef>();
//			for(String cDefName:capabilities.keySet()){
//				CapabilityDef cDef = capabilities.get(cDefName);
//				toReturn.capabilities.put(cDefName, (CapabilityDef)cDef.clone()); //make sure pDef can create a copy
//			}
//			return toReturn;
//		}catch(CloneNotSupportedException e){
//			return null;
//		}		   
//	}
	
	public Builder getBuilder(String typeName){
		Builder builder = new Builder(typeName);
		builder.name = this.name;
		builder.derived_from = this.derived_from;
		builder.description = this.description;
		builder.directives = this.directives;
		builder.inputs = this.inputs;
		builder.nodeTemplates = this.nodeTemplates;
		builder.relationshipTemplates = this.relationshipTemplates;
		builder.groups = this.groups;
		builder.policies = this.policies;
		builder.outputs = this.outputs;
		return builder;
	}	
	

	public String getTypeName() {return typeName;}

	public TopologyTemplate getDerived_from() {return derived_from;}

    public String getDescription() {return description;}

	public List<ParameterDef> getInputs() {return inputs;}
	
	public List<NodeDef> getNodes() {return nodeTemplates;}
	
	public List<RelationshipDef> getRelationships() {return relationshipTemplates;}
	
	public List<GroupDef> getGroups() {return groups;}
	
	public List<PolicyDef> getPolicies() {return policies;}
	
	public List<ParameterDef> getOutputs() {return outputs;}
	
	
//	public String toString(){
//		String props ="";
//		String attrs ="";
//		String caps ="";
//		String reqs ="";
//		for(String propName:properties.keySet()){
//			PropertyDef prop = properties.get(propName);
//			props+=prop;
//		}
//		for(String attrName:attributes.keySet()){
//			AttributeDef attr = attributes.get(attrName);
//			attrs+=attr;
//		}
//		for(String capName:capabilities.keySet()){
//			CapabilityDef cap = capabilities.get(capName);
//			caps+=cap;
//		}
//		for(RequirementDef req:requirements)
//			reqs+=req;
//		return  "name: "+name+"\n"+
//				"type: "+typeName+"\n"+
//			   //"Description: "+description+"\n"+
//			   "attributes: \n"+attrs+"\n"+
//			   "properties: \n"+props+"\n"+
//			   "capabilities: \n"+caps+"\n"+
//			   "requirements: \n"+reqs+"\n";
//			   
//	}
	
}
