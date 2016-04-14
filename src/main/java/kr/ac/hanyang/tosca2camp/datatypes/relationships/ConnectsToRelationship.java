package kr.ac.hanyang.tosca2camp.datatypes.relationships;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;

public class ConnectsToRelationship extends RootRelationship{
	
public static class Builder extends RootRelationship.Builder{
		
		public Builder(String name, String type){
			super(name, type);
			super.description("Tosca ConnectsTo Relationship Type");
		}
		
		public Builder(String name){
			super(name, "tosca.relationships.ConnectsTo");
			super.description("Tosca ConnectsTo Relationship Type");
		}
		
		//TODO change this to credential type
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
