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
	
	public static class Builder <T extends Builder>{
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
		
		public T implementation(String implementation){
			this.implementation = implementation;
			return (T) this;
		}
		
		public T repository(String repository){
			this.repository = repository;
			return (T) this;
		}
		
		public T description(String description){
			this.description = description;
			return (T) this;
		}
		
		public T deployPath(String deployPath){
			this.deployPath = deployPath;
			return (T) this;
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
	
	public String getName(){return name;}
	
	
}
