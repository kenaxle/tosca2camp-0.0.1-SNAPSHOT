package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.BindableNetworkCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.ContainerCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.EndpointCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.OperatingSystemCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.ScalableCapability;

public class ComputeNode extends RootNode {

@SuppressWarnings("rawtypes")
public static class Builder extends RootNode.Builder{
		
		public Builder(String id, String toscaName, String status){		
			super("tosca.nodes.Compute",id,toscaName,status);
			super.description("The TOSCA Compute Node type");
			super.build();
		}
		
		public Builder(String type, String id, String toscaName, String status){		
			super(type,id,toscaName,status);
			super.description("The TOSCA Compute Node type");
			super.build();
		}

		@SuppressWarnings({ "unchecked" })
		public Builder privateAddress(String privateAddress){
			super.addAttribute(new AttributeAs.Builder("private_address",privateAddress).build());
			return this;
		}
		
		@SuppressWarnings({ "unchecked" })
		public Builder publicAddress(String publicAddress){
			super.addAttribute(new AttributeAs.Builder("public_address",publicAddress).build());
			return this;
		}
		
		//TODO use a string for now but this should be a map type
		@SuppressWarnings({ "unchecked" })
		public Builder networks(String networks){
			super.addAttribute(new AttributeAs.Builder("networks",networks).build());
			return this;
		}
		
		//TODO use a string for now but this should be a map type
		@SuppressWarnings({ "unchecked" })
		public Builder ports(String ports){
			super.addAttribute(new AttributeAs.Builder("ports",ports).build());
			return this;
		}

		@SuppressWarnings("unchecked")
		public Builder addContainerCapability(ContainerCapability conCap){
			super.addCapability(conCap);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addEndpointCapability(EndpointCapability conCap){
			super.addCapability(conCap);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addOSCapability(OperatingSystemCapability conCap){
			super.addCapability(conCap);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addScalableCapability(ScalableCapability conCap){
			super.addCapability(conCap);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addBindableCapability(BindableNetworkCapability conCap){
			super.addCapability(conCap);
			return this;
		}
		
		@SuppressWarnings({ "unchecked" })
		public Builder addRequirement(RequirementAs requirement){
			super.addRequirement(requirement);
			return this;
		}
		
		public ComputeNode build(){
			return new ComputeNode(this);
		}
	}
	
	protected ComputeNode(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}


}
