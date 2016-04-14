package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;
import kr.ac.hanyang.tosca2camp.assignments.CapabilityAs;
import kr.ac.hanyang.tosca2camp.assignments.NodeTemplate;
import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.assignments.RelationshipTemplate;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.NodeCapability;

public class WebServerNode extends SoftwareComponentNode {

public static class Builder extends SoftwareComponentNode.Builder{
		
		public Builder(String id, String toscaName, String status){		
			super("tosca.nodes.WebServer",id,toscaName,status);
			super.description("The TOSCA Compute Node type");
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
		public Builder componentVersion(String version){
			super.addProperty(new PropertyAs.Builder("version",version).build());
			return this;
		}
		
		public Builder adminCreds(String adminCreds){
			super.addProperty(new PropertyAs.Builder("admin_credentials",adminCreds).build());
			return this;
		}
		
		public WebServerNode build(){
			return new WebServerNode(this);
		}
	}
	
	protected WebServerNode(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}


}
