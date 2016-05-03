package kr.ac.hanyang.tosca2camp.definitiontypes;

import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;

public class AttributeDef {

	private String name;
	private String type;
	private String description; // description are treated as their own type but for now they will be string
	private String defaultVal;  //TODO
	private String status; 
	private String entry_schema;
	
	public static class Builder{
		private String name;
		private String type;
		private String description; // description are treated as their own type but for now they will be string
		private String defaultVal;  //TODO
		private String status; 
		private String entry_schema;
		
		public Builder(String name, String type){
			this.name = name;
			this.type = type;
			this.defaultVal = "";
		}
		
		public Builder() {}

		public Builder description(String description){
			this.description = description;
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
		
		public Builder entry_schema(String entry_schema){
			this.entry_schema = entry_schema;
			return this;
		}
		
		public AttributeDef build(){
			return new AttributeDef(this);
		}
	}
	
	public static AttributeDef clone(AttributeDef origAttr){
		AttributeDef.Builder copyBuilder = new AttributeDef.Builder(origAttr.name, origAttr.type);
		return copyBuilder.description(origAttr.description)
				   		  .defaultVal(origAttr.defaultVal)
				   		  .status(origAttr.status)
				   		  .entry_schema(origAttr.entry_schema)
				   		  .build();
	}
	
	private AttributeDef(Builder builder){
		this.name = builder.name;
		this.type = builder.type;
		this.description = builder.description;
		this.defaultVal = builder.defaultVal;
		this.status = builder.status;
		this.entry_schema = builder.entry_schema;
	}
	
	public Builder getBuilder(String name, String type){ 
		Builder builder = new Builder(name, type);
		builder.description = this.description;
		builder.defaultVal = this.defaultVal;
		builder.status = this.status;
		builder.entry_schema = this.entry_schema;
		return builder;
	}
	
	public String getName(){return name;}
	// need to convert to java types 
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
	public String getDefaultVal(){return defaultVal;}
	

	public boolean validate(AttributeAs attribute){
		boolean valid = false;
		if (attribute != null){
			if (name.equals(attribute.getName())){
				//the type is valid
				if (type.equals(attribute.getValue().getClass().getName())){
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

