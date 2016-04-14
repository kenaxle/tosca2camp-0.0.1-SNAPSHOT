package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.NodeCapability;

public class BlockStorageNode extends RootNode {

public static class Builder extends RootNode.Builder{
		
	    //TODO size should be scalar unit type 
		public Builder(String id, String toscaName, String status,String size){		
			super("tosca.nodes.Compute",id,toscaName,status);
			super.description("The TOSCA Compute Node type");
			super.addProperty((new PropertyAs.Builder("size",size).build()))
			 .addCapability(new NodeCapability.Builder("attachment","tosca.capabilities.Attachment","").build()).build();
		}
		
		public Builder(String type, String id, String toscaName, String status,String size){		
			super(type,id,toscaName,status);
			super.description("The TOSCA Compute Node type");
			super.addProperty((new PropertyAs.Builder("size",size).build()))
				 .addCapability(new NodeCapability.Builder("attachment","tosca.capabilities.Attachment","").build()).build();
		}
		
		public Builder volumeId(String volumeId){ //TODO this should be scalar unit
			super.addProperty(new PropertyAs.Builder("volume_id",volumeId).build());
			return this;
		}
		
		public Builder snapshotId(String snapshotId){//TODO this should be scalar unit
			super.addProperty(new PropertyAs.Builder("snpshot_id",snapshotId).build());
			return this;
		}
		
		public BlockStorageNode build(){
			return new BlockStorageNode(this);
		}
	}
	
	protected BlockStorageNode(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}


}
