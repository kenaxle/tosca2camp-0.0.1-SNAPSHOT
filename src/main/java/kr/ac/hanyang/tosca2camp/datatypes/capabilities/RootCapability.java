package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

import kr.ac.hanyang.tosca2camp.assignments.CapabilityAs;

public class RootCapability<V> extends CapabilityAs<V>{
	
	@SuppressWarnings("rawtypes")
	public static class Builder extends CapabilityAs.Builder{
		
		public Builder(String type, String desc){
			super(type);
			super.description(desc)
				 .build();
		}
		
		public RootCapability build(){
			return new RootCapability(this);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected RootCapability(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}
	
}
