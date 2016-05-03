package kr.ac.hanyang.tosca2camp.assignments;

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
	
	public static RequirementAs clone(RequirementAs orig){
		Builder copyBuilder = new Builder(orig.name);
		return copyBuilder.capability(CapabilityAs.clone(orig.capability))
				          .node(NodeTemplate.clone(orig.node))
				          .relationship(RelationshipTemplate.clone(orig.relationship))
				          .build();
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
	public RelationshipTemplate getRelationship(){return relationship;}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass())
			return false;
		RequirementAs other = (RequirementAs) obj;
		if (capability == null) {
			if (other.capability != null)
				return false;
		} else if (!capability.equals(other.capability))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
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
