package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;

public class ScalableCapability extends RootCapability {
	
	public static class Builder extends RootCapability.Builder{
		
		public Builder(String type, String desc){
			super("scalable",desc);
		}
		
		public Builder addMinInstances(int min){
			super.addProperty(new PropertyAs.Builder("min_instances",min).build());
			return this;
		}
		
		public Builder addMaxInstancesCpu(int max){
			super.addProperty(new PropertyAs.Builder("max_instances",max).build());
			return this;
		}
		
		public Builder addDefaultInstances(int defInstances){
			super.addProperty(new PropertyAs.Builder("default_instances",defInstances).build());
			return this;
		}
		
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
