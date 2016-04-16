package kr.ac.hanyang.tosca2camp.datatypes.relationships;

import kr.ac.hanyang.tosca2camp.assignments.RelationshipTemplate;

public class DependsOnRelationship extends RootRelationship{
	
public static class Builder<V, T  extends RelationshipTemplate.Builder<V, T>> extends RootRelationship.Builder<V,T>{
		
		public Builder(String name, String type, String id, String toscaName){
			super(name, type, id, toscaName);
			super.description("Tosca DependsOn Relationship Type");
		}
		
		public Builder(String name, String id, String toscaName){
			super(name, "DependsOn", id, toscaName);
			super.description("Tosca DependsOn Relationship Type");
		}
		

		public DependsOnRelationship build(){
			return new DependsOnRelationship(this);
		}
	}
	
	protected DependsOnRelationship(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}

}
