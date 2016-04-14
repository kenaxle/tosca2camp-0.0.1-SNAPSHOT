package kr.ac.hanyang.tosca2camp.datatypes.nodes;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.ContainerCapability;

public class WebServerNode extends SoftwareComponentNode {

public static class Builder extends SoftwareComponentNode.Builder{
		
		public Builder(String id, String toscaName, String status){		
			super("tosca.nodes.WebServer",id,toscaName,status);
			super.description("The TOSCA Compute Node type").build();
//			super.addCapability(new NodeCapability.Builder("host","tosca.capabilities.Container","").build())
//				 .build();
		}
		
		public Builder(String type, String id, String toscaName, String status){		
			super(type,id,toscaName,status);
			super.description("The TOSCA Compute Node type").build();
//			super.addCapability(new NodeCapability.Builder("host","tosca.capabilities.Container","").build())
//			     .build();
		}
		
		//TODO using a string but should change to a version type
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder componentVersion(String version){
			super.addProperty(new PropertyAs.Builder("version",version).build());
			return this;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder adminCreds(String adminCreds){
			super.addProperty(new PropertyAs.Builder("admin_credentials",adminCreds).build());
			return this;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder addHostCapability(ContainerCapability noCap){
			super.addCapability(noCap);
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
