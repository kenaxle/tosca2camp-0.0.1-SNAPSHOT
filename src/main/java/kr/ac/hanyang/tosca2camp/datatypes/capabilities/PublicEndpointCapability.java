package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

public class PublicEndpointCapability extends EndpointCapability{
	
	public static class Builder extends EndpointCapability.Builder{
		
		public Builder(String type, String desc, String protocol, String ipAddress){
			super("public_endpoint",desc,protocol,ipAddress);
		}
		
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
