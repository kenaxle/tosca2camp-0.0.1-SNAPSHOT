package kr.ac.hanyang.tosca2camp.datatypes.nodes;


import java.util.Map;

import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;
import kr.ac.hanyang.tosca2camp.assignments.CapabilityAs;
import kr.ac.hanyang.tosca2camp.assignments.NodeTemplate;
import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.NodeCapability;
import kr.ac.hanyang.tosca2camp.datatypes.relationships.DependsOnRelationship;

@SuppressWarnings("rawtypes")
public class RootNode<V> extends NodeTemplate {
	
	public static class Builder<V> extends NodeTemplate.Builder<V,Builder>{
		
		
		@SuppressWarnings({ "unchecked" })
		public Builder( String id, String toscaName, String status){		
			super("Root");
			super.description("The TOSCA Root Node type")
			.addAttribute(new AttributeAs.Builder("tosca_id",id).build())
			.addAttribute(new AttributeAs.Builder("tosca_name",toscaName).build())
			.addAttribute(new AttributeAs.Builder("state",status).build())
			.addCapability(new NodeCapability.Builder("tosca.capabilities.Node").build())
			.addRequirement(new RequirementAs.Builder("dependency").capability(new NodeCapability.Builder("tosca.capabilities.Node").build())
																	.node(new NodeTemplate.Builder("Root").build())
																	.relationship(new DependsOnRelationship.Builder("baseDependency","DependsOn","ToscaID","DependsOn").build())
																	.build());	
		}
		
		
		
		@SuppressWarnings({ "unchecked" })
		public Builder(String type, String id, String toscaName, String status){		
			super(type);
			super.description("The TOSCA Root Node type")
			.addAttribute(new AttributeAs.Builder("tosca_id",id).build())
			.addAttribute(new AttributeAs.Builder("tosca_name",toscaName).build())
			.addAttribute(new AttributeAs.Builder("state",status).build())
			.addCapability(new NodeCapability.Builder("tosca.capabilities.Node").build())
			.addRequirement(new RequirementAs.Builder("dependency").capability(new NodeCapability.Builder("tosca.capabilities.Node").build())
																	.node(new NodeTemplate.Builder("Root").build())
																	.relationship(new DependsOnRelationship.Builder("baseDependency","DependsOn","ToscaID","DependsOn").build())
																	.build());	
		}
		

		public RootNode build(){
			return new RootNode(this);
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	protected RootNode(Builder builder){
		super(builder);
	}
	
	@SuppressWarnings("unchecked")
	public String toString(){
		String attr = "";
		String props = "";
		String caps = "";
		String reqs = "";
		for (String attrAs:((Map<String, AttributeAs<V>>)super.getAttributes()).keySet()){
			attr +=  ((AttributeAs<V>)((Map<String, AttributeAs<V>>)super.getAttributes()).get(attrAs)).toString()+"\n";
		}
		for (String propAs:((Map<String, PropertyAs<V>>)super.getProperties()).keySet()){
			props +=  ((PropertyAs<V>)((Map<String, PropertyAs<V>>)super.getProperties()).get(propAs)).toString()+"\n";
		}
		for (String capAs:((Map<String, CapabilityAs<V>>)super.getCapabilities()).keySet()){
			caps +=  ((CapabilityAs<V>)((Map<String, CapabilityAs<V>>)super.getCapabilities()).get(capAs)).toString()+"\n";
		}

		for(Object reqAs:super.getRequirements()){
			reqs += reqAs.toString()+"\n";
		}
		//for (RequirementAs<?,?,?> reqsAs:super.getRequirements()){
			
		//}
		return ((AttributeAs<V>)((Map<String, AttributeAs<V>>)super.getAttributes()).get("tosca_name")).getValue()+"\n"+
			   "type: "+ this.getType()+"\n"+
			   "Attributes: \n"+
			   attr+"\n"+
			   "Properties: \n"+
			   props+"\n"+
			   "Capabilities: \n"+
			   caps+"\n"+
			   "Requirements: \n"+
			   reqs;
	}

}
