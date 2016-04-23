package kr.ac.hanyang.tosca2camp.definitiontypes;

/**
 * @author Kena
 *
 */
public class RepositoryDef {

	private String name;
	private String url;
	private String description; // description are treated as their own type but for now they will be string
	private String credential;  //TODO the credential type must be defined
	
	public static class Builder <T extends Builder>{
		private String name;
		private String url;
		private String description; // description are treated as their own type but for now they will be string
		private String credential;
		
		public Builder(String name, String url){
			this.name = name;
			this.url = url;
		}
		
		public T description(String description){
			this.description = description;
			return (T) this;
		}
		
		public T credential(String credential){
			this.credential = credential;
			return (T) this;
		}
		
		public RepositoryDef build(){
			return new RepositoryDef(this);
		}
	}
	
	private RepositoryDef(Builder builder){
		this.name = builder.name;
		this.url = builder.url;
		this.description = builder.description;
		this.credential = builder.credential;
	}
	
}

