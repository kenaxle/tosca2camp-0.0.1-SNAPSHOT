package kr.ac.hanyang.tosca2camp.definitiontypes;

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
		
		public Builder(){}
		
		public T addInput(String input){
			this.inputs.add(input);
			return (T) this;
		}
		
		public InterfaceDef build(){
			return new InterfaceDef(this);
		}
	}
	
	
	public InterfaceDef(InterfaceDef origIDef){
		InterfaceDef.Builder copyBuilder = new InterfaceDef.Builder(origIDef.name);
		for(String input:inputs){
			copyBuilder.addInput(input); 
		}
	}
	
	private InterfaceDef(Builder builder){
		this.name = builder.name;
		this.inputs = builder.inputs;
	}
	
	public Builder getBuilder(){return new Builder();}
	
	public String getName(){return name;}
	
}
