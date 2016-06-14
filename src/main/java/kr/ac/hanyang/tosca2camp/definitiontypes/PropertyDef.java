package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;


public class PropertyDef implements Cloneable{

	private String name;
	private DataTypeDef type;
	private String description; // description are treated as their own type but for now they will be string
	private boolean required;
	private Object propertyValue;
	private Object defaultVal; //if the property value is not specified then use this default value
	private String status;
	private List<ConstraintTypeDef> constraints; //TODO this type will have to be defined
	private EntrySchemaDef entry_schema; 
	
	private String deployPath;
	private Object valid;
	
	public static class Builder{
		private String name;
		private DataTypeDef type;
		private String description; // description are treated as their own type but for now they will be string
		private boolean required = true;
		private Object propertyValue;
		private Object defaultVal; //TODO this will have to suite the type
		private String status;
		private List<ConstraintTypeDef> constraints = new ArrayList<ConstraintTypeDef>(); //TODO this type will have to be defined
		private EntrySchemaDef entry_schema; 
		
		public Builder(String name){
			this.name = name;
			//this.type = type;
			//this.defaultVal = "";
		}

		public Builder type(DataTypeDef type){
			this.type = type;
			return this;
		}
		
		public Builder description(String description){
			this.description = description;
			return this;
		}
		
		public Builder required(boolean required){
			this.required = required;
			return this;
		}
		
		public Builder defaultVal(Object defaultVal){
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
		
		public Builder entry_schema(EntrySchemaDef entry_schema){
			this.entry_schema = entry_schema;
			return this;
		}
		
		public PropertyDef build(){
			// if the default value is not set then set it here.
			return new PropertyDef(this);
		}
	}
	
	
	private PropertyDef(Builder builder){
		this.name = builder.name;
		this.type = builder.type;
		this.description = builder.description;
		this.required = builder.required;
		this.propertyValue = builder.propertyValue;
		this.defaultVal = builder.defaultVal;
		this.status = builder.status;
		this.constraints = builder.constraints;
		this.entry_schema = builder.entry_schema;
	}
	
	public Object clone(){
		try{
			PropertyDef toReturn = (PropertyDef) super.clone();
			//toReturn.propertyValue = (Object) propertyValue.clone();
			//toReturn.defaultVal = (DataTypeDef) defaultVal.clone();
			toReturn.constraints = new ArrayList<ConstraintTypeDef>();
			for( ConstraintTypeDef constraint:constraints){
				toReturn.constraints.add((ConstraintTypeDef) constraint.clone()); //make sure to create a copy
			}
			if(entry_schema != null) toReturn.entry_schema = (EntrySchemaDef) entry_schema.clone();
			return toReturn;
		}catch(CloneNotSupportedException e){
			return null;
		}	
	}
	
	public Builder getBuilder(){ 
		Builder builder = new Builder(name);
		builder.type = this.type;
		builder.description = this.description;
		builder.required = this.required;
		builder.defaultVal = this.defaultVal;
		builder.propertyValue = this.propertyValue;
		builder.status = this.status;
		builder.constraints = this.constraints;
		builder.entry_schema = this.entry_schema;
		return builder;
	}
	
	
	
	public String getName(){return name;}	
	public DataTypeDef getType(){return type;}	
	public String getDescription(){return description;}
	public boolean isRequired(){return required;}
	public Object getDefaultVal(){return defaultVal;}
	
	public void setPropertyValue(Object value){
		propertyValue = value;
	}
	
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
			   "type: "+type.getTypeName()+"\n"+
			   /*"required: "+required+"\n"+*/
			   "property value: "+propertyValue+"\n"
			   /*"default value: "+name+"\n"+
			   "status: "+type+"\n"*/;
	}
	
}
