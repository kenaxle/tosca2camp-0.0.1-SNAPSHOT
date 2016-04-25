package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.ac.hanyang.tosca2camp.toscaTypes.ListEntry;

public class PropertyDef {

	private String name;
	private String type;
	private String description; // description are treated as their own type but for now they will be string
	private boolean required;
	private String defaultVal; //if the property value is not specified then use this default value
	private String status;
	private List<ListEntry> constraints; //TODO this type will have to be defined
	private String entry_schema; 
	
	private String deployPath;
	
	public static class Builder <T extends Builder>{
		private String name;
		private String type;
		private String description; // description are treated as their own type but for now they will be string
		private boolean required;
		private String defaultVal; //TODO this will have to suite the type
		private String status;
		private List<ListEntry> constraints = new ArrayList<ListEntry>(); //TODO this type will have to be defined
		private String entry_schema; 
		
		public Builder(String name, String type){
			this.name = name;
			this.type = type;
		}

		public T description(String description){
			this.description = description;
			return (T) this;
		}
		
		public T required(boolean required){
			this.required = required;
			return (T) this;
		}
		
		public T defaultVal(String defaultVal){
			this.defaultVal = defaultVal;
			return (T) this;
		}
		
		public T status(String status){
			this.status = status;
			return (T) this;
		}
		
		public T addConstraint(ListEntry constraint){
			this.constraints.add(constraint);
			return (T) this;
		}
		
		public T entry_schema(String entry_schema){
			this.entry_schema = entry_schema;
			return (T) this;
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
		PropertyDef.Builder<Builder> copyBuilder = new PropertyDef.Builder<Builder>(origProp.name,origProp.type);
		copyBuilder.description(origProp.description)
				   .required(origProp.required)
				   .defaultVal(origProp.defaultVal)
				   .status(origProp.status);		   
		for( ListEntry constraint:origProp.constraints){
			copyBuilder.addConstraint(new ListEntry(constraint)); //make sure to create a copy
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
	public String getType(){return type;}
	public String getDescription(){return description;}
	public boolean isRequired(){return required;}
	public String getDefaultVal(){return defaultVal;}
		
	public String toString(){
		return "name: "+name+"\n"+
			   "type: "+type+"\n";
	}
	
}
