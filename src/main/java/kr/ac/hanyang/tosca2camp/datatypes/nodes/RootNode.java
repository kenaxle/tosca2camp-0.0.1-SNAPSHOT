package kr.ac.hanyang.tosca2camp.datatypes.nodes;


import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;
import kr.ac.hanyang.tosca2camp.assignments.NodeTemplate;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.NodeCapability;
import kr.ac.hanyang.tosca2camp.datatypes.relationships.DependsOnRelationship;

@SuppressWarnings("rawtypes")
public class RootNode extends NodeTemplate {
	
	public static class Builder<V> extends NodeTemplate.Builder<V,Builder>{
		
		@SuppressWarnings({ "unchecked" })
		public Builder(String type, String id, String toscaName, String status){		
			super(type);
			super.description("The TOSCA Root Node type")
			.addAttribute(new AttributeAs.Builder("tosca_id",id).build())
			.addAttribute(new AttributeAs.Builder("tosca_name",toscaName).build())
			.addAttribute(new AttributeAs.Builder("state",status).build())
			.addCapability(new NodeCapability.Builder("tosca.capabilities.Node").build())
			.addRequirement(new RequirementAs.Builder("dependency").capability(new NodeCapability.Builder("tosca.capabilities.Node").build())
																	.node(new RootNode.Builder<V>("tosca.nodes.Root", "NodeID", "NodeName", "NodeStatus").build())
																	.relationship(new DependsOnRelationship.Builder("dependsOn").build())
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
	
	public String toString(){
		String attr = "";
		String props = "";
		String caps = "";
		String reqs = "";
//		for (AttributeAs attrAs:super.getAttributes()){
//			attr += attrAs.toString()+"\n";
//		}
//		for (PropertyAs propAs:super.getProperties()){
//			props += propAs.toString()+"\n";
//		}
//		for (CapabilityAs capAs:super.getCapabilities()){
//			caps += capAs.toString()+"\n";
//		}
//		for (RequirementAs reqsAs:super.getRequirements()){
//			reqs += reqsAs.toString()+"\n";
//		}
		return super.getType()+"\n"+
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
