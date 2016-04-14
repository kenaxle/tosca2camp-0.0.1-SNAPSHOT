package kr.ac.hanyang.tosca2camp.definitions;

import java.util.List;

public class InterfaceDef {

	private String name;
	private List<String> inputs; //TODO define the property definitions or property assignments
	

	public static class Builder <T extends Builder>{
		private String name;
		private List<String> inputs; //TODO define the property definitions or property assignments
		
		public Builder(String name){
			this.name = name;
		}
		
		public T inputs(List<String> inputs){
			this.inputs = inputs;
			return (T) this;
		}
		
		public InterfaceDef build(){
			return new InterfaceDef(this);
		}
	}
	
	private InterfaceDef(Builder builder){
		this.name = builder.name;
		this.inputs = builder.inputs;
	}
	
	public String getName(){return name;}
	
}
