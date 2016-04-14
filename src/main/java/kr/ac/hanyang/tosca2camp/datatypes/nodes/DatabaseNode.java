package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.DatabaseEndpointCapability;

public class DatabaseNode extends RootNode {

@SuppressWarnings("rawtypes")
public static class Builder extends RootNode.Builder{
		
		@SuppressWarnings("unchecked")
		public Builder(String id, String toscaName, String status,String dbName){		
			super("tosca.nodes.Database",id,toscaName,status);
			super.description("The TOSCA Database Node type")
				 .addProperty(new PropertyAs.Builder("name",dbName).build())
				 .build();
//			     .addCapability(new NodeCapability.Builder("database_endpoint","tosca.capabilities.Endpoint.Database","").build())
//			     .addRequirement(new RequirementAs.Builder("host").capability(new CapabilityAs.Builder("container","tosca.capabilities.Container").build())
//																	.node(new NodeTemplate.Builder<Builder>("tosca.nodes.DBMS").build())
//																	.relationship(new HostedOnRelationship.Builder("HostedOn").build())
//																	.build());
		}
		
		@SuppressWarnings("unchecked")
		public Builder(String type, String id, String toscaName, String status,String dbName){		
			super(type,id,toscaName,status);
			super.description("The TOSCA Database Node type")
			 .addProperty(new PropertyAs.Builder("name",dbName).build())
			 .build();
//		     .addCapability(new NodeCapability.Builder("database_endpoint","tosca.capabilities.Endpoint.Database","").build())
//		     .addRequirement(new RequirementAs.Builder("host").capability(new CapabilityAs.Builder("container","tosca.capabilities.Container").build())
//																.node(new NodeTemplate.Builder<Builder>("tosca.nodes.DBMS").build())
//																.relationship(new HostedOnRelationship.Builder("HostedOn").build())
//																.build());
		}
		
		@SuppressWarnings("unchecked")
		public Builder port(int port){
			super.addProperty(new PropertyAs.Builder("port",port).build());
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder user(String user){
			super.addProperty(new PropertyAs.Builder("user",user).build());
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder password(String password){
			super.addProperty(new PropertyAs.Builder("password",password).build());
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder addDatabaseEndpointCapability(DatabaseEndpointCapability endCap){
			super.addCapability(endCap);
			return this;
		}
		
		@SuppressWarnings({ "unchecked" })
		public Builder addRequirement(RequirementAs requirement){
			super.addRequirement(requirement);
			return this;
		}
		
		public DatabaseNode build(){
			return new DatabaseNode(this);
		}
	}
	
	protected DatabaseNode(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}


}
