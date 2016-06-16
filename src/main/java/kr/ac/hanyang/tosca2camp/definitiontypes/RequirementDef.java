package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.Map;

public class RequirementDef<T> implements Cloneable{
	private String name;
	private T capability;
	private T nodeType; 
	private T relationshipType;
	private String occurence; // must fix
	
	
	public static class Builder {
		private String name;
		private String capability;
		private String nodeType; 
		private String relationshipType;
		private String occurence; // must fix
		
		public Builder(String name){
			this.name = name;
			//this.capability = capability;
		}
		
		public Builder node(String node){
			this.nodeType = node;
			return this;
		}
		
		public Builder relationship(String relationship){
			this.relationshipType = relationship;
			return this;
		}
		
		public Builder capability (String capability){
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
		this.capability = (T) builder.capability;
		this.nodeType = (T) builder.nodeType;
		this.relationshipType = (T) builder.relationshipType;
		this.occurence = builder.occurence;
	}
	
	public Object clone(){
		try{
			RequirementDef toReturn = (RequirementDef) super.clone();
			if (capability != null && (capability instanceof CapabilityDef)) toReturn.capability = ((CapabilityDef)capability).clone(); //TODO this should clone
			if (nodeType != null && (nodeType instanceof NodeDef)) toReturn.nodeType = ((NodeDef)nodeType).clone();
			if (relationshipType != null && (relationshipType instanceof RelationshipDef)) toReturn.relationshipType = ((RelationshipDef)relationshipType).clone();
			return toReturn;
		}catch(CloneNotSupportedException e){
			return null;
		}	
	}
	
	public Builder getBuilder(){
		Builder builder = new Builder(name);
		builder.nodeType = (String) this.nodeType;
		builder.capability = (String) this.capability;
		builder.relationshipType = (String) this.relationshipType;
		builder.occurence = this.occurence;
		return builder;	
	}
	
	public CapabilityDef getCapDefName(){return (CapabilityDef) capability;}
	public NodeDef getNodeDefName(){return (NodeDef) nodeType;}
	public RelationshipDef getRelDefName(){return (RelationshipDef) relationshipType;}
	
	public RelationshipDef parseRelationshipDef(Object toParse){
		if(toParse instanceof Map){ 
			Map<String, Object> relMap = (Map<String, Object>) toParse;
			if (relationshipType != null && (relationshipType instanceof RelationshipDef))
				return ((RelationshipDef) relationshipType).parseRelationshipTemplate(relMap);
		}
		return null;
	}
	
	public boolean equals(Object obj){
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		RequirementDef rDef = (RequirementDef) obj;
		return name.equals(rDef.name);
	}
	
	public String toString(){
		String nodeString = "";
		String capString = "";
		String relString = "";
		if(nodeType != null && (nodeType instanceof NodeDef)) 
			nodeString = ((NodeDef) nodeType).getTypeName();
		else 
			nodeString = (String) nodeType;
		if(capability != null && (capability instanceof CapabilityDef))
			capString = ((CapabilityDef) capability).getName();
		else 
			capString = (String) capability;
		if(relationshipType != null && (relationshipType instanceof RelationshipDef))
			relString = ((RelationshipDef) relationshipType).getName();
		else 
			relString = (String) capability;
		return "name: "+name+"\n"+
			   "node: "+nodeString+"\n"+
			   "capability: "+capString+"\n"+
			   "relationship: "+relString+"\n";
	}

}

