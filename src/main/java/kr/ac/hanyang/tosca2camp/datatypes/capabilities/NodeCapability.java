package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

public class NodeCapability<V> extends RootCapability<V> {
	
	public static class Builder extends RootCapability.Builder{
		
		public Builder( String desc){
			super("node",desc);
		}
		
		public Builder(String type, String desc){
			super(type, desc);
		}
		
		@SuppressWarnings("rawtypes")
		public NodeCapability build(){
			return new NodeCapability(this);
		}
		
	}
	
	protected NodeCapability(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}
}
