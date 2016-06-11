package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.ArrayList;
import java.util.List;

public class InterfaceDef {

	private String name;
	private List<String> inputs; //TODO define the property definitions or property assignments
	

	public static class Builder{
		private String name;
		private List<String> inputs = new ArrayList(); //TODO define the property definitions or property assignments
		
		public Builder(String name){
			this.name = name;
		}
		
		public Builder(){}
		
		public Builder addInput(String input){
			this.inputs.add(input);
			return this;
		}
		
		public InterfaceDef build(){
			return new InterfaceDef(this);
		}
	}
	
	
	public InterfaceDef clone(){
		try{
			InterfaceDef toReturn = (InterfaceDef) super.clone();
			//toReturn.inputs = new ArrayList<String>();
			return toReturn;
		}catch(CloneNotSupportedException e){
			return null;
		}		  
	}
	
	private InterfaceDef(Builder builder){
		this.name = builder.name;
		this.inputs = builder.inputs;
	}
	
	public Builder getBuilder(){
		Builder builder = new Builder(name);
		builder.name = this.name;
		return builder;
	}
	
	public String getName(){return name;}
	
}
