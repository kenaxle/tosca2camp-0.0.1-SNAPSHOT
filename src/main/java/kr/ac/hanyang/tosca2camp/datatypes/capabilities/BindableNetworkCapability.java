package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

public class BindableNetworkCapability extends NodeCapability{
	
	public static class Builder extends NodeCapability.Builder{
		
		public Builder(String desc){
			super("bindable");
		}
		
		public BindableNetworkCapability build(){
			return new BindableNetworkCapability(this);
		}
	}
	
	protected BindableNetworkCapability(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}
}
