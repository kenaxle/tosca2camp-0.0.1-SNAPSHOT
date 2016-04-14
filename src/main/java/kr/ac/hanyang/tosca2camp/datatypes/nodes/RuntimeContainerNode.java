package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.ContainerCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.ScalableCapability;

public class RuntimeContainerNode extends SoftwareComponentNode {

public static class Builder extends SoftwareComponentNode.Builder{
		
		public Builder(String id, String toscaName, String status){		
			super("tosca.nodes.Container.Runtime",id,toscaName,status);
			super.description("The TOSCA Runtime Container Node type")
				 .build();
//			super.addCapability(new NodeCapability.Builder("host","tosca.capabilities.Container","").build())
//				 .addCapability(new NodeCapability.Builder("scalable","tosca.capabilities.Scalable","").build())
//				 .build();
		}
		
		public Builder(String type, String id, String toscaName, String status){		
			super(type,id,toscaName,status);
			super.description("The TOSCA Runtime Container Node type")
				 .build();
//			super.addCapability(new NodeCapability.Builder("host","tosca.capabilities.Container","").build())
//				 .addCapability(new NodeCapability.Builder("scalable","tosca.capabilities.Scalable","").build())
//			     .build();
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder port(int port){
			super.addProperty(new PropertyAs.Builder("port",port).build());
			return this;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder addHostCapability(ContainerCapability hostCap){
			super.addCapability(hostCap);
			return this;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder addScalableCapability(ScalableCapability scCap){
			super.addCapability(scCap);
			return this;
		}
		
		public RuntimeContainerNode build(){
			return new RuntimeContainerNode(this);
		}
	}
	
	protected RuntimeContainerNode(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}


}
