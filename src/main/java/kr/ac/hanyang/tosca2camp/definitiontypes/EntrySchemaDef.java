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
	
	public DataTypeDef getType(){
//		switch(type){
//		case "string": return "java.lang.String";
//		case "integer": return "java.lang.Integer";
//		case "float": return "java.lang.Double";
//		case "boolean": return "java.lang.Boolean";
//		case "list": return "java.util.ArrayList";
//		case "map": return "java.util.LinkedHashMap";
//		case "scalar-unit.size": return "kr.ac.hanyang.tosca2camp.toscaTypes.ScalarSize";
//		case "scalar-unit.time": return "kr.ac.hanyang.tosca2camp.toscaTypes.ScalarTime";
//		case "scalar-unit.frequency": return "kr.ac.hanyang.tosca2camp.toscaTypes.ScalarFrequency";
//		default: return type; 		
//		}	
		return this.type;
	}
	
	public String getDescription(){return description;}

	
//	public boolean validate(PropertyAs property){
//		boolean valid = false;
//		if (property != null){
//			if (name.equals(property.getName())){
//				//the type is valid
//				if (type.equals(property.getValue().getClass().getName())){
//					valid = true;
//				}
//			}
//		}
//		return valid;
//	}
	
	public String toString(){
		return "type: "+type+"\n";
	}
	
}
