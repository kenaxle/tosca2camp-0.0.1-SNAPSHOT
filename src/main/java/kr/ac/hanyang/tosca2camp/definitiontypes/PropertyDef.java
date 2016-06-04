package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;


public class PropertyDef {

	private String name;
	private String type;
	private String description; // description are treated as their own type but for now they will be string
	private boolean required;
	private String defaultVal; //if the property value is not specified then use this default value
	private String status;
	private List<ConstraintTypeDef> constraints; //TODO this type will have to be defined
	private String entry_schema; 
	
	private String deployPath;
	private Object valid;
	
	public static class Builder{
		private String name;
		private String type;
		private String description; // description are treated as their own type but for now they will be string
		private boolean required = true;
		private String defaultVal = ""; //TODO this will have to suite the type
		private String status;
		private List<ConstraintTypeDef> constraints = new ArrayList<ConstraintTypeDef>(); //TODO this type will have to be defined
		private String entry_schema; 
		
		public Builder(String name, String type){
			this.name = name;
			this.type = type;
			this.defaultVal = "";
		}

		public Builder description(String description){
			this.description = description;
			return this;
		}
		
		public Builder required(boolean required){
			this.required = required;
			return this;
		}
		
		public Builder defaultVal(String defaultVal){
			this.defaultVal = defaultVal;
			return this;
		}
		
		public Builder status(String status){
			this.status = status;
			return this;
		}
		
		public Builder addConstraint(ConstraintTypeDef constraint){
			this.constraints.add(constraint);
			return this;
		}
		
		public Builder entry_schema(String entry_schema){
			this.entry_schema = entry_schema;
			return this;
		}
		
		public PropertyDef build(){
			return new PropertyDef(this);
		}
	}
	
	
	private PropertyDef(Builder builder){
		this.name = builder.name;
		this.type = builder.type;
		this.description = builder.description;
		this.required = builder.required;
		this.defaultVal = builder.defaultVal;
		this.status = builder.status;
		this.constraints = builder.constraints;
		this.entry_schema = builder.entry_schema;
	}
	
	public static PropertyDef clone(PropertyDef origProp){
		PropertyDef.Builder copyBuilder = new PropertyDef.Builder(origProp.name,origProp.type);
		copyBuilder.description(origProp.description)
				   .required(origProp.required)
				   .defaultVal(origProp.defaultVal)
				   .status(origProp.status);		   
		for( ConstraintTypeDef constraint:origProp.constraints){
			copyBuilder.addConstraint(ConstraintTypeDef.clone(constraint)); //make sure to create a copy
		}
		return copyBuilder.entry_schema(origProp.entry_schema)
				   .build();
	}
	
	public Builder getBuilder(String name, String type){ 
		Builder builder = new Builder(name,type);
		builder.description = this.description;
		builder.required = this.required;
		builder.defaultVal = this.defaultVal;
		builder.status = this.status;
		builder.constraints = this.constraints;
		builder.entry_schema = this.entry_schema;
		return builder;
	}
	
	
	
	public String getName(){return name;}
	
	public String getType(){
		switch(type){
		case "string": return "java.lang.String";
		case "integer": return "java.lang.Integer";
		case "float": return "java.lang.Double";
		case "boolean": return "java.lang.Boolean";
		case "list": return "java.util.ArrayList";
		case "map": return "java.util.LinkedHashMap";
		case "scalar-unit.size": return "kr.ac.hanyang.tosca2camp.toscaTypes.ScalarSize";
		case "scalar-unit.time": return "kr.ac.hanyang.tosca2camp.toscaTypes.ScalarTime";
		case "scalar-unit.frequency": return "kr.ac.hanyang.tosca2camp.toscaTypes.ScalarFrequency";
		default: return type; 		
		}	
	}
	
	public String getDescription(){return description;}
	public boolean isRequired(){return required;}
	public String getDefaultVal(){return defaultVal;}
	
	@SuppressWarnings("rawtypes")
	public boolean validate(PropertyAs property){
		boolean valid = false;
		if (property != null){
			if (name.equals(property.getName())){
				//the type is valid
				if (type.equals(property.getValue().getClass().getName())){
					valid = true;
				}
			}
		}
		return valid;
	}
	
	public String toString(){
		return "name: "+name+"\n"+
			   "type: "+type+"\n";
	}
	
}
