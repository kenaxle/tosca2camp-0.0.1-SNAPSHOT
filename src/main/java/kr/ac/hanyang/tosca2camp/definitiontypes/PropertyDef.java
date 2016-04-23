package kr.ac.hanyang.tosca2camp.definitiontypes;

public class PropertyDef {

	private String name;
	private String type;
	private String description; // description are treated as their own type but for now they will be string
	private boolean required;
	private String defaultVal; //if the property value is not specified then use this default value
	private String status;
	private String constraints; //TODO this type will have to be defined
	private String entry_schema; 
	
	private String deployPath;
	
	public static class Builder <T extends Builder>{
		private String name;
		private String type;
		private String description; // description are treated as their own type but for now they will be string
		private boolean required;
		private String defaultVal; //TODO don't know what this default value means as yet
		private String status;
		private String constraints; //TODO this type will have to be defined
		private String entry_schema; 
		
		public Builder(String name, String type){
			this.name = name;
			this.type = type;
		}
			
		public Builder() {}

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
		
		public T constraints(String constraints){
			this.constraints = constraints;
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
	
	
	public PropertyDef(PropertyDef origProp){
		PropertyDef.Builder<Builder> copyBuilder = new PropertyDef.Builder<Builder>(origProp.name,origProp.type);
		copyBuilder.description(origProp.description)
				   .required(origProp.required)
				   .defaultVal(origProp.defaultVal)
				   .status(origProp.status)
				   .constraints(origProp.constraints)
				   .entry_schema(origProp.entry_schema)
				   .build();
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
	
	public Builder getBuilder(){ return new Builder();}
	
}
