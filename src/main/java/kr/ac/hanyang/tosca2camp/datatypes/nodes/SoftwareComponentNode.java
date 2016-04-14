package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;

public class SoftwareComponentNode extends RootNode {

@SuppressWarnings("rawtypes")
public static class Builder extends RootNode.Builder{
		
		public Builder(String id, String toscaName, String status){		
			super("tosca.nodes.SoftwareComponent",id,toscaName,status);
			super.description("The TOSCA Software Component Node type").build();
//			super.addRequirement(new RequirementAs.Builder("host").capability(new CapabilityAs.Builder("container","tosca.capabilities.Container").build())
//																	.node(new NodeTemplate.Builder<Builder>("tosca.nodes.Compute").build())
//																	.relationship(new HostedOnRelationship.Builder("HostedOn").build())
//																	.build());
		}
		
		public Builder(String type, String id, String toscaName, String status){		
			super(type,id,toscaName,status);
			super.description("The TOSCA Software Component Node type").build();
//			super.addRequirement(new RequirementAs.Builder("host").capability(new CapabilityAs.Builder("container","tosca.capabilities.Container").build())
//																	.node(new NodeTemplate.Builder<Builder>("tosca.nodes.Compute").build())
//																	.relationship(new HostedOnRelationship.Builder("HostedOn").build())
//																	.build());
		}
		
		//TODO using a string but should change to a version type
		@SuppressWarnings("unchecked")
		public Builder componentVersion(String version){
			super.addProperty(new PropertyAs.Builder("version",version).build());
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder adminCreds(String adminCreds){
			super.addProperty(new PropertyAs.Builder("admin_credentials",adminCreds).build());
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addRequirement(RequirementAs requirement){
			super.addRequirement(requirement);
			return this;
		}
		
		public SoftwareComponentNode build(){
			return new SoftwareComponentNode(this);
		}
			
	}
	
	protected SoftwareComponentNode(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}


}
