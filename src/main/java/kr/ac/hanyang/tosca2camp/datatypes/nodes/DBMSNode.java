package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.NodeCapability;

public class DBMSNode extends SoftwareComponentNode {

public static class Builder extends SoftwareComponentNode.Builder{
		
		public Builder(String id, String toscaName, String status){		
			super("tosca.nodes.DBMS",id,toscaName,status);
			super.description("The TOSCA DBMS Node type");
			super.addCapability(new NodeCapability.Builder("host","tosca.capabilities.Container","").build())
				 .build();
		}
		
		public Builder(String type, String id, String toscaName, String status){		
			super(type,id,toscaName,status);
			super.description("The TOSCA Compute Node type");
			super.addCapability(new NodeCapability.Builder("host","tosca.capabilities.Container","").build())
			     .build();
		}
		
		//TODO using a string but should change to a version type
		public Builder rootPassword(String rootPass){
			super.addProperty(new PropertyAs.Builder("root_password",rootPass).build());
			return this;
		}
		
		public Builder port(int port){
			super.addProperty(new PropertyAs.Builder("port",port).build());
			return this;
		}
		
		public DBMSNode build(){
			return new DBMSNode(this);
		}
	}
	
	protected DBMSNode(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}


}
