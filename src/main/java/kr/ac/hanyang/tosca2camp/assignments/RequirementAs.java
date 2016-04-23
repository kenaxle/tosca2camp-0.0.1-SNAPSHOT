package kr.ac.hanyang.tosca2camp.assignments;

import kr.ac.hanyang.tosca2camp.definitiontypes.CapabilityDef;

public class RequirementAs<T , U, V> {
	
	private String name;
	private T capability; //TODO capability def or type
	private U node; //TODO this is the nodetemplate or node type
	private V relationship; //TODO
	
	private RequirementAs(Builder<T, U, V> builder){	
		this.name = builder.name;
		this.capability = (T) builder.capability;
		this.node = (U) builder.node;
		this.relationship = (V) builder.relationship;
	}
	
	public static class Builder<T,U,V>{
		private String name;
		private T capability; //TODO capability def or type
		private U node; //TODO this is the nodetemplate or node type
		private V relationship; //TODO
		
		public Builder(String name){this.name = name;}
		
		public Builder<T, U, V> capability(T capability){
			this.capability = capability;
			return this;
		}
		
		public Builder<T, U, V> node(U node){
			this.node = node;
			return this;
		}
		
		public Builder<T, U, V> relationship(V relationship){
			this.relationship = relationship;
			return this;
		}
		
		public RequirementAs<T, U, V> build(){
			return new RequirementAs<T,U,V>(this);
		}
		
	}
	
	public String getName(){ return name;}
	
	@SuppressWarnings({ "rawtypes" })
	public String toString(){	
		String retString="  - "+name+"\n";
		if (node != null) 
			retString+= "      node: "+(String)((NodeTemplate) node).getAttributeValue("tosca_name") +"\n";
		if (capability != null){
			String capType="";
			switch (capability.getClass().getSimpleName()){
				case "CapabilityAs":
					CapabilityAs<?> capAs = (CapabilityAs<?>) capability;
					capType = capAs.getType();
					break;
				case "CapabilityDef":
					CapabilityDef capDef = (CapabilityDef) capability;
					capType = capDef.getType();
					break;
				default:break; 
			}
			retString+="      Capability: "+capType+"\n";
		}
		if (relationship != null){
			RelationshipTemplate relTemp = (RelationshipTemplate) relationship;
			retString+="      relationship: "+relTemp.getType()+"\n";
		}
		return  retString;
	}
	
}
