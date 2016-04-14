package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;

public class OperatingSystemCapability<V> extends RootCapability<V> {
	
	public static class Builder extends RootCapability.Builder{
		
		public Builder(String type, String desc){
			super(type,desc);
		}
		public Builder(String desc){
			super("os",desc);
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder addArchitecture(String architecture){
			super.addProperty((PropertyAs) new PropertyAs.Builder("architecture",architecture).build());
			return this;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder addType(String type){
			super.addProperty((PropertyAs) new PropertyAs.Builder("type",type).build());
			return this;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder addDistribution(String distribution){
			super.addProperty((PropertyAs) new PropertyAs.Builder("distribution",distribution).build());
			return this;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder addVersion(String version){ //TODO may have to fix this as it should be version type
			super.addProperty((PropertyAs) new PropertyAs.Builder("version",version).build());
			return this;
		}
		
		@SuppressWarnings("rawtypes")
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
