package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.List;

import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;
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
	
	private RequirementDef(Builder builder){
		this.name = builder.name;
		this.capability = builder.capability;
		this.nodeType = builder.nodeType;
		this.relationshipType = builder.relationshipType;
		this.occurence = builder.occurence;
	}
	
	public static RequirementDef clone(RequirementDef origReq){
		RequirementDef.Builder<Builder> copyBuilder = new RequirementDef.Builder<Builder>(origReq.name,origReq.capability);
		return copyBuilder.node(origReq.nodeType)
				   		  .relationship(origReq.relationshipType)
				   		  .occurence(origReq.occurence)
				   		  .build();
	}
	
	public Builder getBuilder(String name, String capability){
		Builder builder = new Builder(name, capability);
		builder.nodeType = this.nodeType;
		builder.relationshipType = this.relationshipType;
		builder.occurence = this.occurence;
		return builder;	
	}
	
	public String getCapDefName(){return capability;}
	public String getNodeDefName(){return nodeType;}
	public String getRelDefName(){return relationshipType;}
	
	
	public boolean validate(RequirementAs rTemp){
		return (capability.equals(rTemp.getCapability().getType())&&
				nodeType.equals(rTemp.getNode().getType())&&
				relationshipType.equals(rTemp.getRelationship().getType()));	
	}
	
	public String toString(){
		return "name: "+name+"\n"+
			   "capability: "+capability+"\n";
	}

}

