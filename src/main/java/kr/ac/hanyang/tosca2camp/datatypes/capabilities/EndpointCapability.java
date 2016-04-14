package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;
import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;

public class EndpointCapability<V> extends RootCapability<V> {

public static class Builder extends RootCapability.Builder{
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder(String desc, String protocol, String ipAddress){
			super("endpoint",desc);
			super.addProperty((PropertyAs) new PropertyAs.Builder("protocol",protocol).build());	//TODO default should be tcp
			super.addAttribute((AttributeAs) new AttributeAs.Builder("ip_address",ipAddress).build()); 
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder(String type, String desc, String protocol, String ipAddress){
			super(type,desc);
			super.addProperty((PropertyAs) new PropertyAs.Builder("protocol",protocol).build());	//TODO default should be tcp
			super.addAttribute((AttributeAs) new AttributeAs.Builder("ip_address",ipAddress).build()); 
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder addPort(int port){
			super.addProperty((PropertyAs) new PropertyAs.Builder("port",port).build());
			return this;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder addSecure(boolean secure){
			super.addProperty((PropertyAs) new PropertyAs.Builder("secure",secure).build());
			return this;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder addUrlPath_size(String urlPath){
			super.addProperty((PropertyAs) new PropertyAs.Builder("url_path",urlPath).build());
			return this;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder addPortname(String portname){
			super.addProperty((PropertyAs) new PropertyAs.Builder("port_name",portname).build());
			return this;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder addNetworkName(String networkName){
			super.addProperty((PropertyAs) new PropertyAs.Builder("network_name",networkName).build());
			return this;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder addInitiator(String initiator){
			super.addProperty((PropertyAs) new PropertyAs.Builder("initiator",initiator).build());
			return this;
		}
		
		//TODO have to change this to a map type
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder addPorts(String ports){
			super.addProperty((PropertyAs) new PropertyAs.Builder("ports",ports).build());
			return this;
		}
		
		@SuppressWarnings("rawtypes")
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
