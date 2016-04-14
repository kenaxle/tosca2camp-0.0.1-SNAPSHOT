package kr.ac.hanyang.tosca2camp.datatypes.relationships;

public class DependsOnRelationship extends RootRelationship{
	
public static class Builder extends RootRelationship.Builder{
		
		public Builder(String name, String type){
			super(name, type);
			super.description("Tosca DependsOn Relationship Type");
		}
		
		public Builder(String name){
			super(name, "tosca.relationships.DependsOn");
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
