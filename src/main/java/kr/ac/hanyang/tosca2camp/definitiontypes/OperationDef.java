package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.ArrayList;
import java.util.List;

public class OperationDef {

	private String name;
	private String description; // description are treated as their own type but for now they will be string
	private String implementation; 
	private List<String> inputs; //TODO define the property definitions or property assignments
	

	public static class Builder <T extends Builder>{
		private String name;
		private String description; // description are treated as their own type but for now they will be string
		private String implementation; 
		private List<String> inputs = new ArrayList(); //TODO define the property definitions or property assignments
		
		public Builder(String name){
			this.name = name;
		}
		
		public T description(String description){
			this.description = description;
			return (T) this;
		}
		
		public T implementation(String implementation){
			this.implementation = implementation;
			return (T) this;
		}
		
		public T inputs(List<String> inputs){
			this.inputs = inputs;
			return (T) this;
		}
		
		public OperationDef build(){
			return new OperationDef(this);
		}
	}
	
	private OperationDef(Builder builder){
		this.name = builder.name;
		this.description = builder.description;
		this.implementation = builder.implementation;
		this.inputs = builder.inputs;
	}
	
}
