package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.ArrayList;
import java.util.List;


public class EntrySchemaDef implements Cloneable{

	private DataTypeDef type;
	private String description; // description are treated as their own type but for now they will be string
	private List<ConstraintTypeDef> constraints; //TODO this type will have to be defined
	
	public static class Builder{
		private DataTypeDef type;
		private String description; // description are treated as their own type but for now they will be string
		private List<ConstraintTypeDef> constraints = new ArrayList<ConstraintTypeDef>(); //TODO this type will have to be defined
		
		public Builder(DataTypeDef type){
			this.type = type;
		}

		public Builder description(String description){
			this.description = description;
			return this;
		}
		
		public Builder addConstraint(ConstraintTypeDef constraint){
			this.constraints.add(constraint);
			return this;
		}
		
		public EntrySchemaDef build(){
			return new EntrySchemaDef(this);
		}
		
	}
	
	
	private EntrySchemaDef(Builder builder){
		this.type = builder.type;
		this.description = builder.description;
		this.constraints = builder.constraints;
	}
	
	public Object clone(){
		try{
			EntrySchemaDef toReturn = (EntrySchemaDef) super.clone();
			toReturn.type = (DataTypeDef)type.clone();
			toReturn.constraints = new ArrayList<ConstraintTypeDef>();
			for( ConstraintTypeDef constraint:constraints){
				toReturn.constraints.add((ConstraintTypeDef) constraint.clone()); //make sure to create a copy
			}
			return toReturn;
		}catch(CloneNotSupportedException e){
			return null;
		}		  
	}
	
	public Builder getBuilder(){ 
		Builder builder = new Builder(type);
		builder.description = this.description;
		builder.constraints = this.constraints;
		return builder;
	}
	
	public String getDescription(){return description;}
	
	public String toString(){
		return "type: "+type+"\n";
	}
	
}
