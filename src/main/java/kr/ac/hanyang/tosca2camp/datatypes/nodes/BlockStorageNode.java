package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.AttachmentCapability;

public class BlockStorageNode extends RootNode {

@SuppressWarnings("rawtypes")
public static class Builder extends RootNode.Builder{
		
	    //TODO size should be scalar unit type 
		@SuppressWarnings("unchecked")
		public Builder(String id, String toscaName, String status,String size){		
			super("tosca.nodes.BlockStorage",id,toscaName,status);
			super.description("The TOSCA BlockStorage Node type");
			super.addProperty((new PropertyAs.Builder("size",size).build()))
			.build();
//			 .addCapability(new NodeCapability.Builder("attachment","tosca.capabilities.Attachment","").build()).build();
		}
		
		@SuppressWarnings("unchecked")
		public Builder(String type, String id, String toscaName, String status,String size){		
			super(type,id,toscaName,status);
			super.description("The TOSCA BlockStorage Node type");
			super.addProperty((new PropertyAs.Builder("size",size).build()))
			.build();
//				 .addCapability(new NodeCapability.Builder("attachment","tosca.capabilities.Attachment","").build()).build();
		}
		
		@SuppressWarnings("unchecked")
		public Builder volumeId(String volumeId){ //TODO this should be scalar unit
			super.addProperty(new PropertyAs.Builder("volume_id",volumeId).build());
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder snapshotId(String snapshotId){//TODO this should be scalar unit
			super.addProperty(new PropertyAs.Builder("snpshot_id",snapshotId).build());
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addAttachmentCapability(AttachmentCapability endCap){
			super.addCapability(endCap);
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
