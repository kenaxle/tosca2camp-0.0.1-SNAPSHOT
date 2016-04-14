package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;
import kr.ac.hanyang.tosca2camp.assignments.CapabilityAs;
import kr.ac.hanyang.tosca2camp.assignments.NodeTemplate;
import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.assignments.RelationshipTemplate;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.NodeCapability;

public class ObjectStorageNode extends RootNode {

public static class Builder extends RootNode.Builder{
		
		public Builder(String id, String toscaName, String status,String name){		
			super("tosca.nodes.Compute",id,toscaName,status);
			super.description("The TOSCA Compute Node type");
			super.addProperty((new PropertyAs.Builder("name",name).build()))
			 .addCapability(new NodeCapability.Builder("storage_endpoint","tosca.capabilities.Endpoint","").build()).build();
		}
		
		public Builder(String type, String id, String toscaName, String status,String name){		
			super(type,id,toscaName,status);
			super.description("The TOSCA Compute Node type");
			super.addProperty((new PropertyAs.Builder("name",name).build()))
				 .addCapability(new NodeCapability.Builder("storage_endpoint","tosca.capabilities.Endpoint","").build()).build();
		}
		
		public Builder size(String size){ //TODO this should be scalar unit
			super.addProperty(new PropertyAs.Builder("size",size).build());
			return this;
		}
		
		public Builder maxSize(String maxSize){//TODO this should be scalar unit
			super.addProperty(new PropertyAs.Builder("maxsize",maxSize).build());
			return this;
		}
		
		public ObjectStorageNode build(){
			return new ObjectStorageNode(this);
		}
	}
	
	protected ObjectStorageNode(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}


}
