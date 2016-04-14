package kr.ac.hanyang.tosca2camp.datatypes.relationships;

import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;
import kr.ac.hanyang.tosca2camp.assignments.RelationshipTemplate;

public class RootRelationship extends RelationshipTemplate{
	
public static class Builder extends RelationshipTemplate.Builder<Builder>{
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder(String name, String type){
			super(name, type);
			super.description("Tosca Root Relationship Type")
				 .addAttributes(new AttributeAs.Builder("tosca_id", "").build())
				 .addAttributes(new AttributeAs.Builder("tosca_name", "").build())
				 //TODO add the configure interface
				 .build();
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder(String name){
			super(name, "tosca.relationships.Root");
			super.description("Tosca Root Relationship Type")
			 	 .addAttributes(new AttributeAs.Builder("tosca_id", "").build())
			 	 .addAttributes(new AttributeAs.Builder("tosca_name", "").build())
			 	 //TODO add the configure interface
			 	 .build();
		}
		

		public RootRelationship build(){
			return new RootRelationship(this);
		}
	}
	
	protected RootRelationship(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}

}
