package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;

public class ContainerCapability<V> extends RootCapability<V> {
	
	public static class Builder extends RootCapability.Builder{
		
		public Builder(String desc){
			super("host",desc);
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder addNumCpu(int numCpu){
			super.addProperty((PropertyAs) new PropertyAs.Builder("num_cpus",numCpu).build());
			return this;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder addCpu_frequency(int cpuFrequency){
			super.addProperty((PropertyAs) new PropertyAs.Builder("cpu_frequency",cpuFrequency).build());
			return this;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder addDisk_size(int diskSize){
			super.addProperty((PropertyAs) new PropertyAs.Builder("disk_size",diskSize).build());
			return this;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder addMem_size(int memSize){
			super.addProperty((PropertyAs) new PropertyAs.Builder("mem_size",memSize).build());
			return this;
		}
		
		@SuppressWarnings("rawtypes")
		public ContainerCapability build(){
			return new ContainerCapability(this);
		}
		
	}
	
	protected ContainerCapability(Builder builder){
		super(builder);
	}
	
	//TODO may have to fix
	public String toString(){
		return super.toString();
	}

}
