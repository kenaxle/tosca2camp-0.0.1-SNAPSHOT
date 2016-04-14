package kr.ac.hanyang.tosca2camp.definitions;

public class AttributeDef {

	private String name;
	private String type;
	private String description; // description are treated as their own type but for now they will be string
	private String defaultVal;  //TODO
	private String status; 
	private String entry_schema;
	
	public static class Builder <T extends Builder>{
		private String name;
		private String type;
		private String description; // description are treated as their own type but for now they will be string
		private String defaultVal;  //TODO
		private String status; 
		private String entry_schema;
		
		public Builder(String name, String type){
			this.name = name;
			this.type = type;
		}
		
		public T description(String description){
			this.description = description;
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
		
		public T entry_schema(String entry_schema){
			this.entry_schema = entry_schema;
			return (T) this;
		}
		
		public AttributeDef build(){
			return new AttributeDef(this);
		}
	}
	
	private AttributeDef(Builder builder){
		this.name = builder.name;
		this.type = builder.type;
		this.description = builder.description;
		this.defaultVal = builder.defaultVal;
		this.status = builder.status;
		this.entry_schema = builder.entry_schema;
	}
	
}

