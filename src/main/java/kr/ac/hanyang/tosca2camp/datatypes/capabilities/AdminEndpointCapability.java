package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

public class AdminEndpointCapability<V> extends EndpointCapability<V>{
	
	public static class Builder extends EndpointCapability.Builder{
		
		public Builder(String desc, String protocol, String ipAddress){
			super("admin_endpoint",desc,protocol,ipAddress);
		}
		
		public Builder(String type, String desc, String protocol, String ipAddress){
			super(type,desc,protocol,ipAddress);
		}
		
		@SuppressWarnings("rawtypes")
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
