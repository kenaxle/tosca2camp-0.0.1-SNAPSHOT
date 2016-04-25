package kr.ac.hanyang.tosca2camp.assignments;

import kr.ac.hanyang.tosca2camp.definitiontypes.CapabilityDef;

public class RequirementAs {
	
	private String name;
	private CapabilityAs capability; //TODO capability def or type
	private NodeTemplate node; //TODO this is the nodetemplate or node type
	private RelationshipTemplate relationship; //TODO
	
	private RequirementAs(Builder builder){	
		this.name = builder.name;
		this.capability =  builder.capability;
		this.node =  builder.node;
		this.relationship =  builder.relationship;
	}
	
	public static class Builder{
		private String name;
		private CapabilityAs capability; //TODO capability def or type
		private NodeTemplate node; //TODO this is the nodetemplate or node type
		private RelationshipTemplate relationship; //TODO
		
		public Builder(String name){this.name = name;}
		
		public Builder capability(CapabilityAs capability){
			this.capability = capability;
			return this;
		}
		
		public Builder node(NodeTemplate node){
			this.node = node;
			return this;
		}
		
		public Builder relationship(RelationshipTemplate relationship){
			this.relationship = relationship;
			return this;
		}
		
		public RequirementAs build(){
			return new RequirementAs(this);
		}
		
	}

	public String getName(){ return name;}
	
	public CapabilityAs getCapability(){ return capability;}
	public NodeTemplate getNode(){return node;}
	public RelationshipTemplate retRelationship(){return relationship;}
	
//	public String toString(){	
//		String retString="  - "+name+"\n";
//		if (node != null) 
//			retString+= "      node: "+(String)((NodeTemplate) node).getAttributeValue("tosca_name") +"\n";
//		if (capability != null){
//			String capType="";
//			switch (capability.getClass().getSimpleName()){
//				case "CapabilityAs":
//					CapabilityAs<?> capAs = (CapabilityAs<?>) capability;
//					capType = capAs.getType();
//					break;
//				case "CapabilityDef":
//					CapabilityDef capDef = (CapabilityDef) capability;
//					capType = capDef.getType();
//					break;
//				default:break; 
//			}
//			retString+="      Capability: "+capType+"\n";
//		}
//		if (relationship != null){
//			RelationshipTemplate relTemp = (RelationshipTemplate) relationship;
//			retString+="      relationship: "+relTemp.getType()+"\n";
//		}
//		return  retString;
//	}
	
}
