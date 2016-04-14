package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

public class DatabaseEndpointCapability extends EndpointCapability{
	
	public static class Builder extends EndpointCapability.Builder{
		
		public Builder(String type, String desc, String protocol, String ipAddress){
			super("database_endpoint",desc,protocol,ipAddress);
		}
		
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
