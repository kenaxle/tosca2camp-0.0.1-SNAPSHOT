package kr.ac.hanyang.tosca2camp.definitiontypes;

import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;

public class RequirementDef implements Cloneable{
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
		
		public Builder(String name){
			this.name = name;
			//this.capability = capability;
		}
		
		public Builder node(NodeDef node){
			this.nodeType = node;
			return this;
		}
		
		public Builder relationship(RelationshipDef relationship){
			this.relationshipType = relationship;
			return this;
		}
		
		public Builder capability (CapabilityDef capability){
			this.capability = capability;
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
	
	public Object clone(){
		try{
			RequirementDef toReturn = (RequirementDef) super.clone();
			if (capability != null) toReturn.capability = (CapabilityDef)capability.clone(); //TODO this should clone
			if (nodeType != null) toReturn.nodeType = (NodeDef)nodeType.clone();
			if (relationshipType != null) toReturn.relationshipType = (RelationshipDef)relationshipType.clone();
			return toReturn;
		}catch(CloneNotSupportedException e){
			return null;
		}	
	}
	
	public Builder getBuilder(){
		Builder builder = new Builder(name);
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

