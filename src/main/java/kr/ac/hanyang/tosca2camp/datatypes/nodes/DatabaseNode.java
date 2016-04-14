package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.CapabilityAs;
import kr.ac.hanyang.tosca2camp.assignments.NodeTemplate;
import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.assignments.RelationshipTemplate;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.NodeCapability;
import kr.ac.hanyang.tosca2camp.datatypes.relationships.HostedOnRelationship;

public class DatabaseNode extends RootNode {

public static class Builder extends RootNode.Builder{
		
		public Builder(String id, String toscaName, String status,String dbName){		
			super("tosca.nodes.Database",id,toscaName,status);
			super.description("The TOSCA Database Node type")
				 .addProperty(new PropertyAs.Builder("name",dbName).build())
			     .addCapability(new NodeCapability.Builder("database_endpoint","tosca.capabilities.Endpoint.Database","").build())
			     .addRequirement(new RequirementAs.Builder("host").capability(new CapabilityAs.Builder("container","tosca.capabilities.Container").build())
																	.node(new NodeTemplate.Builder<Builder>("tosca.nodes.DBMS").build())
																	.relationship(new HostedOnRelationship.Builder("HostedOn").build())
																	.build());
		}
		
		public Builder(String type, String id, String toscaName, String status,String dbName){		
			super(type,id,toscaName,status);
			super.description("The TOSCA Database Node type")
			 .addProperty(new PropertyAs.Builder("name",dbName).build())
		     .addCapability(new NodeCapability.Builder("database_endpoint","tosca.capabilities.Endpoint.Database","").build())
		     .addRequirement(new RequirementAs.Builder("host").capability(new CapabilityAs.Builder("container","tosca.capabilities.Container").build())
																.node(new NodeTemplate.Builder<Builder>("tosca.nodes.DBMS").build())
																.relationship(new HostedOnRelationship.Builder("HostedOn").build())
																.build());
		}
		
		public Builder port(int port){
			super.addProperty(new PropertyAs.Builder("port",port).build());
			return this;
		}
		
		public Builder user(String user){
			super.addProperty(new PropertyAs.Builder("user",user).build());
			return this;
		}
		
		public Builder password(String password){
			super.addProperty(new PropertyAs.Builder("password",password).build());
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
