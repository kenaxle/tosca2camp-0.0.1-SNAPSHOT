package kr.ac.hanyang.tosca2camp.datatypes.relationships;

import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;

public class RoutesToRelationship extends ConnectsToRelationship{
	
public static class Builder extends ConnectsToRelationship.Builder{
		
		public Builder(String name, String type, String id, String toscaName){
			super(name, type, id, toscaName);
			super.description("Tosca RoutesTo Relationship Type");
		}
		
		public Builder(String name, String id, String toscaName){
			super(name, "RoutesTo",id, toscaName);
			super.description("Tosca RoutesTo Relationship Type");
		}
		
		//TODO change this to credential type
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder credential(String credential){
			super.addProperties(new PropertyAs.Builder("credential", credential).build());
			return this;
		}

		public RoutesToRelationship build(){
			return new RoutesToRelationship(this);
		}
	}
	
	protected RoutesToRelationship(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}

}
