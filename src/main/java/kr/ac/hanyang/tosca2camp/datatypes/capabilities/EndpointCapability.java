package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;
import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;

public class EndpointCapability extends RootCapability {

public static class Builder extends RootCapability.Builder{
		
		@SuppressWarnings("unchecked")
		public Builder(String type, String desc, String protocol, String ipAddress){
			super("endpoint",desc);
			super.addProperty(new PropertyAs.Builder("protocol",protocol).build());	//TODO default should be tcp
			super.addAttribute(new AttributeAs.Builder("ip_address",ipAddress).build()); 
		}
		
		public Builder addPort(int port){
			super.addProperty(new PropertyAs.Builder("port",port).build());
			return this;
		}
		
		public Builder addSecure(int secure){
			super.addProperty(new PropertyAs.Builder("secure",secure).build());
			return this;
		}
		
		public Builder addUrlPath_size(int urlPath){
			super.addProperty(new PropertyAs.Builder("url_path",urlPath).build());
			return this;
		}
		
		public Builder addPortname(int portname){
			super.addProperty(new PropertyAs.Builder("port_name",portname).build());
			return this;
		}
		
		public Builder addNetworkName(int networkName){
			super.addProperty(new PropertyAs.Builder("network_name",networkName).build());
			return this;
		}
		
		public Builder addInitiator(int initiator){
			super.addProperty(new PropertyAs.Builder("initiator",initiator).build());
			return this;
		}
		
		public Builder addPorts(int ports){
			super.addProperty(new PropertyAs.Builder("ports",ports).build());
			return this;
		}
		
		public EndpointCapability build(){
			return new EndpointCapability(this);
		}
		
	}
	
	protected EndpointCapability(Builder builder){
		super(builder);
	}
	
	//TODO may have to fix
	public String toString(){
		return super.toString();
	}
	
}
