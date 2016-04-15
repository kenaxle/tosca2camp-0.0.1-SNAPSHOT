package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.EndpointCapability;

public class WebApplicationNode extends RootNode {

@SuppressWarnings("rawtypes")
public static class Builder extends RootNode.Builder{
		
		public Builder(String id, String toscaName, String status){		
			super("tosca.nodes.WebApplication",id,toscaName,status);
			super.description("The TOSCA Web Application Node type").build();
//			super.addCapability(new NodeCapability.Builder("app_endpoint","tosca.capabilities.Endpoint","").build())
//			     .addRequirement(new RequirementAs.Builder("host").capability(new CapabilityAs.Builder("attachment","tosca.capabilities.Container").build())
//																	.node(new NodeTemplate.Builder<Builder>("tosca.nodes.WebServer").build())
//																	.relationship(new HostedOnRelationship.Builder("HostedOn").build())
//																	.build());
		}
		
		public Builder(String type, String id, String toscaName, String status){		
			super(type,id,toscaName,status);
			super.description("The TOSCA Web Application Node type").build();
//			super.addCapability(new NodeCapability.Builder("app_endpoint","tosca.capabilities.Endpoint","").build())
//			     .addRequirement(new RequirementAs.Builder("host").capability(new CapabilityAs.Builder("attachment","tosca.capabilities.Container").build())
//																	.node(new NodeTemplate.Builder<Builder>("tosca.nodes.WebServer").build())
//																	.relationship(new HostedOnRelationship.Builder("HostedOn").build())
//																	.build());
		}
		
		@SuppressWarnings({ "unchecked" })
		public Builder contextRoot(String contextRoot){
			super.addProperty(new PropertyAs.Builder("context_root",contextRoot).build());
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addAppEndpointCapability(EndpointCapability endCap){
			super.addCapability(endCap);
			return this;
		}
		
		@SuppressWarnings({ "unchecked" })
		public Builder addRequirement(RequirementAs requirement){
			super.addRequirement(requirement);
			return this;
		}
		
		public WebApplicationNode build(){
			return new WebApplicationNode(this);
		}
		
	}
	
	protected WebApplicationNode(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}


}
