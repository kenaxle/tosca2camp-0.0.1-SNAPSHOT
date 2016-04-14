package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;

public class ContainerCapability extends RootCapability {
	
	public static class Builder extends RootCapability.Builder{
		
		public Builder(String desc){
			super("host",desc);
		}
		
		public Builder addNumCpu(int numCpu){
			super.addProperty(new PropertyAs.Builder("num_cpus",numCpu).build());
			return this;
		}
		
		public Builder addCpu_frequencyCpu(int cpuFrequency){
			super.addProperty(new PropertyAs.Builder("cpu_frequency",cpuFrequency).build());
			return this;
		}
		
		public Builder addDisk_size(int diskSize){
			super.addProperty(new PropertyAs.Builder("disk_size",diskSize).build());
			return this;
		}
		
		public Builder addMem_size(int memSize){
			super.addProperty(new PropertyAs.Builder("mem_size",memSize).build());
			return this;
		}
		
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
