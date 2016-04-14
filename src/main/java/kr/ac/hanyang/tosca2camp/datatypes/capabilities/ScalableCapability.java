package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;

public class ScalableCapability<V> extends RootCapability<V> {
	
	public static class Builder extends RootCapability.Builder{
		
		public Builder(String type, String desc){
			super(type, desc);
		}
		
		public Builder(String desc){
			super("scalable",desc);
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder addMinInstances(int min){
			super.addProperty((PropertyAs) new PropertyAs.Builder("min_instances",min).build());
			return this;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder addMaxInstances(int max){
			super.addProperty((PropertyAs) new PropertyAs.Builder("max_instances",max).build());
			return this;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder addDefaultInstances(int defInstances){
			super.addProperty((PropertyAs) new PropertyAs.Builder("default_instances",defInstances).build());
			return this;
		}
		
		@SuppressWarnings("rawtypes")
		public ScalableCapability build(){
			return new ScalableCapability(this);
		}
	}
	
	protected ScalableCapability(Builder builder){
		super(builder);
	}
	
	//TODO may have to fix
	public String toString(){
		return super.toString();
	}

}
