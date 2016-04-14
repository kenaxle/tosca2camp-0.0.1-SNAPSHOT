package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;

public class OperatingSystemCapability extends RootCapability {
	
	public static class Builder extends RootCapability.Builder{
		
		public Builder(String type, String desc){
			super("os",desc);
		}
		
		public Builder addArchitectureCpu(String architecture){
			super.addProperty(new PropertyAs.Builder("architecture",architecture).build());
			return this;
		}
		
		public Builder addType(String type){
			super.addProperty(new PropertyAs.Builder("type",type).build());
			return this;
		}
		
		public Builder addDistribution(String distribution){
			super.addProperty(new PropertyAs.Builder("distribution",distribution).build());
			return this;
		}
		
		public Builder addVersion(String version){ //TODO may have to fix this as it should be version type
			super.addProperty(new PropertyAs.Builder("version",version).build());
			return this;
		}
		
		public OperatingSystemCapability build(){
			return new OperatingSystemCapability(this);
		}
		
	}
	
	protected OperatingSystemCapability(Builder builder){
		super(builder);
	}
	
	//TODO may have to fix
	public String toString(){
		return super.toString();
	}

}
