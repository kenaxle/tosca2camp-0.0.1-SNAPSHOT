package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;
import kr.ac.hanyang.tosca2camp.assignments.CapabilityAs;
import kr.ac.hanyang.tosca2camp.assignments.NodeTemplate;
import kr.ac.hanyang.tosca2camp.assignments.RelationshipTemplate;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.AttachmentCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.BindableNetworkCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.ContainerCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.EndpointCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.NodeCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.OperatingSystemCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.ScalableCapability;
import kr.ac.hanyang.tosca2camp.datatypes.relationships.DependsOnRelationship;

public class ComputeNode extends RootNode {

public static class Builder extends RootNode.Builder{
		
		@SuppressWarnings("unchecked")
		public Builder(String id, String toscaName, String status){		
			super("tosca.nodes.Compute",id,toscaName,status);
			super.description("The TOSCA Compute Node type");
//			super.addCapability(new ContainerCapability.Builder("tosca.capabilities.Container").build())
//			     .addCapability(new EndpointCapability.Builder("tosca.capabilities.Endpoint.Admin","","","").build())
//			     .addCapability(new OperatingSystemCapability.Builder("tosca.capabilities.OperatingSystem","").build())
//			     .addCapability(new ScalableCapability.Builder("tosca.capabilities.Scalable","").build())
//			     .addCapability(new BindableNetworkCapability.Builder("tosca.capabilities.network.Bindable").build())
//			     .addRequirement(new RequirementAs.Builder("local_storage").capability(new AttachmentCapability.Builder("tosca.capabilities.Attachement").build())
//																	.node(new BlockStorageNode.Builder("tosca.nodes.BlockStorage","","","").build())
//																	.relationship(new DependsOnRelationship.Builder("dependsOn").build())
//																	.build());
		}
		
//		public Builder(String type, String id, String toscaName, String status){		
//			super(type,id,toscaName,status);
//			super.description("The TOSCA Compute Node type");
//			super.addCapability(new NodeCapability.Builder("host","tosca.capabilities.Container","").build())
//			     .addCapability(new NodeCapability.Builder("endpoint","tosca.capabilities.Endpoint.Admin","").build())
//			     .addCapability(new NodeCapability.Builder("os","tosca.capabilities.OperatingSystem","").build())
//			     .addCapability(new NodeCapability.Builder("scalable","tosca.capabilities.Scalable","").build())
//			     .addCapability(new NodeCapability.Builder("binding","tosca.capabilities.network.Bindable","").build())
//			     .addRequirement(new RequirementAs.Builder("local_storage").capability(new CapabilityAs.Builder("attachment","tosca.capabilities.Attachement").build())
//																	.node(new NodeTemplate.Builder<Builder>("tosca.nodes.BlockStorage").build())
//																	.relationship(new DependsOnRelationship.Builder("dependsOn").build())
//																	.build());
//		}
		
		public Builder privateAddress(String privateAddress){
			super.addAttribute(new AttributeAs.Builder("private_address",privateAddress).build());
			return this;
		}
		
		public Builder publicAddress(String publicAddress){
			super.addAttribute(new AttributeAs.Builder("public_address",publicAddress).build());
			return this;
		}
		
		//TODO use a string for now but this should be a map type
		public Builder networks(String networks){
			super.addAttribute(new AttributeAs.Builder("networks",networks).build());
			return this;
		}
		
		//TODO use a string for now but this should be a map type
		public Builder ports(String ports){
			super.addAttribute(new AttributeAs.Builder("ports",ports).build());
			return this;
		}

		public Builder addContainerCapability(ContainerCapability conCap){
			super.addCapability(conCap).build();
			return this;
		}
		
		public Builder addEndpointCapability(EndpointCapability conCap){
			super.addCapability(conCap).build();
			return this;
		}
		
		public Builder addOSCapability(OperatingSystemCapability conCap){
			super.addCapability(conCap).build();
			return this;
		}
		
		public Builder addScalableCapability(ScalableCapability conCap){
			super.addCapability(conCap).build();
			return this;
		}
		
		public Builder addBindableCapability(BindableNetworkCapability conCap){
			super.addCapability(conCap).build();
			return this;
		}
		
		public Builder addRequirement(RequirementAs requirement){
			super.addRequirement(requirement).build();
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
