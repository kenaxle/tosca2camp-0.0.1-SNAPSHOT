package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.List;

import kr.ac.hanyang.tosca2camp.definitiontypes.RequirementDef.Builder;

public class RequirementDef {
	private String name;
	private String capability;
	private String nodeType; 
	private String relationshipType;
	private String occurence; // must fix
	
	
	public static class Builder <T extends Builder>{
		private String name;
		private String capability;
		private String nodeType; 
		private String relationshipType;
		private String occurence; // must fix
		
		public Builder(String name, String capability){
			this.name = name;
			this.capability = capability;
		}
		
		public Builder() {}

		public T node(String node){
			this.nodeType = node;
			return (T) this;
		}
		
		public T relationship(String relationship){
			this.relationshipType = relationship;
			return (T) this;
		}
		
		public T occurence(String occurence){
			this.occurence = occurence;
			return (T) this;
		}
		
		public RequirementDef build(){
			return new RequirementDef(this);
		}
	}
	
	public RequirementDef(RequirementDef origReq){
		RequirementDef.Builder<Builder> copyBuilder = new RequirementDef.Builder<Builder>(origReq.name,origReq.capability);
		copyBuilder.node(origReq.nodeType)
				   .relationship(origReq.relationshipType)
				   .occurence(origReq.occurence)
				   .build();
	}
	
	private RequirementDef(Builder builder){
		this.name = builder.name;
		this.capability = builder.capability;
		this.nodeType = builder.nodeType;
		this.relationshipType = builder.relationshipType;
		this.occurence = builder.occurence;
	}
	
	public Builder getBuilder(){return new Builder();}

}

