package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;

public class ApplicationContainerNode extends RootNode {

@SuppressWarnings("rawtypes")
public static class Builder extends RootNode.Builder{
		
		public Builder(String id, String toscaName, String status){		
			super("tosca.nodes.Container.Application",id,toscaName,status);
			super.description("The TOSCA Application Container Node type")
				 .build();
//			super.addRequirement(new RequirementAs.Builder("host").capability(new CapabilityAs.Builder("container","tosca.capabilities.Container").build())
//																	.node(new NodeTemplate.Builder<Builder>("tosca.nodes.Container").build())
//																	.relationship(new HostedOnRelationship.Builder("HostedOn").build())
//																	.build());
		}
		
		public Builder(String type, String id, String toscaName, String status){		
			super(type,id,toscaName,status);
			super.description("The TOSCA Application Container Node type")
				 .build();
//			super.addRequirement(new RequirementAs.Builder("host").capability(new CapabilityAs.Builder("container","tosca.capabilities.Container").build())
//																	.node(new NodeTemplate.Builder<Builder>("tosca.nodes.Container").build())
//																	.relationship(new HostedOnRelationship.Builder("HostedOn").build())
//																	.build());
		}
		
		@SuppressWarnings("unchecked")
		public Builder addRequirement(RequirementAs requirement){
			super.addRequirement(requirement);
			return this;
		}
		
		public ApplicationContainerNode build(){
			return new ApplicationContainerNode(this);
		}
	}
	
	protected ApplicationContainerNode(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}


}
