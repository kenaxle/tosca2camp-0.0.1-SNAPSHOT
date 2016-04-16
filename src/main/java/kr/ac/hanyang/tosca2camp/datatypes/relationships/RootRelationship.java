package kr.ac.hanyang.tosca2camp.datatypes.relationships;

import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;
import kr.ac.hanyang.tosca2camp.assignments.RelationshipTemplate;

public class RootRelationship extends RelationshipTemplate{
	
public static class Builder<V, T extends RelationshipTemplate.Builder<V, T>> extends RelationshipTemplate.Builder<V,T>{
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder(String name, String type, String id, String toscaName){
			super(name, type);
			super.description("Tosca Root Relationship Type")
				 .addAttributes(new AttributeAs.Builder("tosca_id", id).build())
				 .addAttributes(new AttributeAs.Builder("tosca_name", toscaName).build())
				 //TODO add the configure interface
				 .build();
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder(String name, String id, String toscaName){
			super(name, "tosca.relationships.Root");
			super.description("Tosca Root Relationship Type")
			 	 .addAttributes(new AttributeAs.Builder("tosca_id", id).build())
			 	 .addAttributes(new AttributeAs.Builder("tosca_name", toscaName).build())
			 	 //TODO add the configure interface
			 	 .build();
		}
		
		
		
		
		public RootRelationship build(){
			return new RootRelationship(this);
		}
	}
	
	@SuppressWarnings("rawtypes")
	protected RootRelationship(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}

}
