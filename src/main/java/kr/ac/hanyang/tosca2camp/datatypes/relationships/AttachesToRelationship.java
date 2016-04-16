package kr.ac.hanyang.tosca2camp.datatypes.relationships;

import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;
import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;

public class AttachesToRelationship extends RootRelationship{
	
public static class Builder extends RootRelationship.Builder{
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder(String name, String type, String id, String toscaName, String location){
			super(name, type, id, toscaName);
			super.description("Tosca AttachesTo Relationship Type")
				 .addProperties(new PropertyAs.Builder("location", location).build());
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder(String name, String id, String toscaName, String location){
			super(name, "AttachesTo", id, toscaName);
			super.description("Tosca AttachesTo Relationship Type")
			     .addProperties(new PropertyAs.Builder("location", location).build());
		}
		
		//TODO property and attribute are set at the same time
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder device(String device){
			super.addProperties(new PropertyAs.Builder("device", device).build())
				 .addAttributes(new AttributeAs.Builder("device", "").build());
			return this;
		}

		public AttachesToRelationship build(){
			return new AttachesToRelationship(this);
		}
	}
	
	protected AttachesToRelationship(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}

}
