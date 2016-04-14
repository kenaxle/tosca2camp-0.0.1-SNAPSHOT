package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

public class BindableNetworkCapability<V> extends NodeCapability<V>{
	
	public static class Builder extends NodeCapability.Builder{
		
		public Builder(String desc){
			super("bindable",desc);
		}
		
		public Builder(String type, String desc){
			super(type, desc);
		}
		
		@SuppressWarnings("rawtypes")
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
