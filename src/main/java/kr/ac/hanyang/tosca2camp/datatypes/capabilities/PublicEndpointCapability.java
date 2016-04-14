package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

public class PublicEndpointCapability<V> extends EndpointCapability<V>{
	
	public static class Builder extends EndpointCapability.Builder{
		
		public Builder(String desc, String protocol, String ipAddress){
			super("public_endpoint",desc,protocol,ipAddress);
		}
		
		public Builder(String type, String desc, String protocol, String ipAddress){
			super(type,desc,protocol,ipAddress);
		}
		
		@SuppressWarnings("rawtypes")
		public PublicEndpointCapability build(){
			return new PublicEndpointCapability(this);
		}
	}
	
	protected PublicEndpointCapability(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}
}
