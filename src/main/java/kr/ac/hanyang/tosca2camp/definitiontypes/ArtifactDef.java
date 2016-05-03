package kr.ac.hanyang.tosca2camp.definitiontypes;

/**
 * @author Kena Alexander
 * @since 
 */

public class ArtifactDef {

	private String name;
	private String type;
	private String implementation; //URI string
	private String repository; 
	private String description; // description are treated as their own type but for now they will be string
	private String deployPath;
	
	public static class Builder{
		private String name;
		private String type;
		private String implementation; //URI string
		private String repository; 
		private String description; // description are treated as their own type but for now they will be string
		private String deployPath;
		
		public Builder(String name, String type){
			this.name = name;
			this.type = type;
		}
		
		public Builder implementation(String implementation){
			this.implementation = implementation;
			return this;
		}
		
		public Builder repository(String repository){
			this.repository = repository;
			return this;
		}
		
		public Builder description(String description){
			this.description = description;
			return this;
		}
		
		public Builder deployPath(String deployPath){
			this.deployPath = deployPath;
			return this;
		}
		
		public ArtifactDef build(){
			return new ArtifactDef(this);
		}
	}
	
	private ArtifactDef(Builder builder){
		this.type = builder.type;
		this.implementation = builder.implementation;
		this.repository = builder.repository;
		this.description = builder.description;
		this.deployPath = builder.deployPath;
	}
	
	public Builder getBuilder(String name, String type){ 
		Builder builder = new Builder(name,type);
		builder.description = this.description;
		builder.implementation = this.implementation;
		builder.repository = this.repository;
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
	public String getImplementation(){return implementation;}
	public String getRepository(){return repository;}
	
}
