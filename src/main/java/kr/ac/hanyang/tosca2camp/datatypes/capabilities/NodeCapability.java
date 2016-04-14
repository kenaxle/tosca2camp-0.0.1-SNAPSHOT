package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

public class NodeCapability extends RootCapability {
	
	public static class Builder extends RootCapability.Builder{
		
		public Builder( String desc){
			super("node",desc);
		}
		
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
