package kr.ac.hanyang.tosca2camp.datatypes.relationships;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;

public class ConnectsToRelationship extends RootRelationship{
	
public static class Builder extends RootRelationship.Builder{
		
		public Builder(String name, String type, String id, String toscaName){
			super(name, type, id, toscaName);
			super.description("Tosca ConnectsTo Relationship Type");
		}
		
		public Builder(String name, String id, String toscaName){
			super(name, "ConnectsTo", id, toscaName);
			super.description("Tosca ConnectsTo Relationship Type");
		}
		
		//TODO change this to credential type
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder credential(String credential){
			super.addProperties(new PropertyAs.Builder("credential", credential).build());
			return this;
		}

		public ConnectsToRelationship build(){
			return new ConnectsToRelationship(this);
		}
	}
	
	protected ConnectsToRelationship(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}

}
