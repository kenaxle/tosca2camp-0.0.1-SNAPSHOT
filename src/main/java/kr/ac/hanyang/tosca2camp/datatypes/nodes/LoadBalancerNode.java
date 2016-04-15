package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.PublicEndpointCapability;

public class LoadBalancerNode extends RootNode {

@SuppressWarnings("rawtypes")
public static class Builder extends RootNode.Builder{
		
		public Builder(String id, String toscaName, String status){		
			super("tosca.nodes.Compute",id,toscaName,status);
			super.description("The TOSCA Compute Node type")
				 .build();
//			super.addCapability(new NodeCapability.Builder("client","tosca.capabilities.Endpoint.Public","").build())
//			     .addRequirement(new RequirementAs.Builder("application").capability(new CapabilityAs.Builder("endpoint","tosca.capabilities.Endpoint").build())
//																	.relationship(new RoutesToRelationship.Builder("RoutesTo").build())
//																	.build());
		}
		
		public Builder(String type, String id, String toscaName, String status){		
			super(type,id,toscaName,status);
			super.description("The TOSCA Compute Node type")
				 .build();
//			super.addCapability(new NodeCapability.Builder("client","tosca.capabilities.Endpoint.Public","").build())
//		     .addRequirement(new RequirementAs.Builder("application").capability(new CapabilityAs.Builder("endpoint","tosca.capabilities.Endpoint").build())
//																.relationship(new RoutesToRelationship.Builder("RoutesTo").build())
//																.build());
		}
		
		@SuppressWarnings("unchecked")
		public Builder algorithm(String algorithm){
			super.addProperty(new PropertyAs.Builder("algorithm",algorithm).build());
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addClientCapability(PublicEndpointCapability conCap){
			super.addCapability(conCap);
			return this;
		}
		
		@SuppressWarnings({ "unchecked" })
		public Builder addRequirement(RequirementAs requirement){
			super.addRequirement(requirement);
			return this;
		}
		
		public LoadBalancerNode build(){
			return new LoadBalancerNode(this);
		}
	}
	
	protected LoadBalancerNode(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}


}
