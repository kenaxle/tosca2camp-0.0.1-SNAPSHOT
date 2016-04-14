package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

public class DatabaseEndpointCapability<V> extends EndpointCapability<V>{
	
	public static class Builder extends EndpointCapability.Builder{
		
		public Builder(String desc, String protocol, String ipAddress){
			super("database_endpoint",desc,protocol,ipAddress);
		}
		
		public Builder(String type, String desc, String protocol, String ipAddress){
			super(type,desc,protocol,ipAddress);
		}
		
		@SuppressWarnings("rawtypes")
		public DatabaseEndpointCapability build(){
			return new DatabaseEndpointCapability(this);
		}
	}
	
	protected DatabaseEndpointCapability(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}
}
