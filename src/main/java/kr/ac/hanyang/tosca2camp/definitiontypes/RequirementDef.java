package kr.ac.hanyang.tosca2camp.definitiontypes;

import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;

public class RequirementDef {
	private String name;
	private CapabilityDef capability;
	private NodeDef nodeType; 
	private RelationshipDef relationshipType;
	private String occurence; // must fix
	
	
	public static class Builder {
		private String name;
		private CapabilityDef capability;
		private NodeDef nodeType; 
		private RelationshipDef relationshipType;
		private String occurence; // must fix
		
		public Builder(String name, CapabilityDef capability){
			this.name = name;
			this.capability = capability;
		}
		

		public Builder node(NodeDef node){
			this.nodeType = node;
			return this;
		}
		
		public Builder relationship(RelationshipDef relationship){
			this.relationshipType = relationship;
			return this;
		}
		
		public Builder occurence(String occurence){
			this.occurence = occurence;
			return this;
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
	
	public RequirementDef clone(){
		try{
			RequirementDef toReturn = (RequirementDef) super.clone();
			toReturn.capability = capability.clone(); //TODO this should clone
			toReturn.nodeType = nodeType.clone();
			toReturn.relationshipType = relationshipType.clone();
			return toReturn;
		}catch(CloneNotSupportedException e){
			return null;
		}	
	}
	
	public Builder getBuilder(){
		Builder builder = new Builder(name, capability);
		builder.nodeType = this.nodeType;
		builder.relationshipType = this.relationshipType;
		builder.occurence = this.occurence;
		return builder;	
	}
	
	public CapabilityDef getCapDefName(){return capability;}
	public NodeDef getNodeDefName(){return nodeType;}
	public RelationshipDef getRelDefName(){return relationshipType;}
	
	
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

