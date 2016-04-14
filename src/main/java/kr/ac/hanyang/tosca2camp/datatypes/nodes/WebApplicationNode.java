package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;
import kr.ac.hanyang.tosca2camp.assignments.CapabilityAs;
import kr.ac.hanyang.tosca2camp.assignments.NodeTemplate;
import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.assignments.RelationshipTemplate;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.NodeCapability;
import kr.ac.hanyang.tosca2camp.datatypes.relationships.HostedOnRelationship;

public class WebApplicationNode extends RootNode {

public static class Builder extends RootNode.Builder{
		
		public Builder(String id, String toscaName, String status){		
			super("tosca.nodes.WebApplication",id,toscaName,status);
			super.description("The TOSCA Web Application Node type");
			super.addCapability(new NodeCapability.Builder("app_endpoint","tosca.capabilities.Endpoint","").build())
			     .addRequirement(new RequirementAs.Builder("host").capability(new CapabilityAs.Builder("attachment","tosca.capabilities.Container").build())
																	.node(new NodeTemplate.Builder<Builder>("tosca.nodes.WebServer").build())
																	.relationship(new HostedOnRelationship.Builder("HostedOn").build())
																	.build());
		}
		
		public Builder(String type, String id, String toscaName, String status){		
			super(type,id,toscaName,status);
			super.description("The TOSCA Web Application Node type");
			super.addCapability(new NodeCapability.Builder("app_endpoint","tosca.capabilities.Endpoint","").build())
			     .addRequirement(new RequirementAs.Builder("host").capability(new CapabilityAs.Builder("attachment","tosca.capabilities.Container").build())
																	.node(new NodeTemplate.Builder<Builder>("tosca.nodes.WebServer").build())
																	.relationship(new HostedOnRelationship.Builder("HostedOn").build())
																	.build());
		}
		
		public Builder privateAddress(String contextRoot){
			super.addProperty(new PropertyAs.Builder("context_root",contextRoot).build());
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
