package kr.ac.hanyang.tosca2camp.datatypes.relationships;

public class HostedOnRelationship extends RootRelationship{
	
public static class Builder extends RootRelationship.Builder{
		
		public Builder(String name, String type, String id, String toscaName){
			super(name, type, id, toscaName);
			super.description("Tosca Root Relationship Type");
		}
		
		public Builder(String name, String id, String toscaName){
			super(name, "HostedOn", id, toscaName);
			super.description("Tosca Root Relationship Type");
		}
		

		public HostedOnRelationship build(){
			return new HostedOnRelationship(this);
		}
	}
	
	protected HostedOnRelationship(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}

}
