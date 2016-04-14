package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

public class AdminEndpointCapability extends EndpointCapability{
	
	public static class Builder extends EndpointCapability.Builder{
		
		public Builder(String type, String desc, String protocol, String ipAddress){
			super("admin_endpoint",desc,protocol,ipAddress);
		}
		
		public AdminEndpointCapability build(){
			return new AdminEndpointCapability(this);
		}
		
	}
	
	protected AdminEndpointCapability(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}
}
