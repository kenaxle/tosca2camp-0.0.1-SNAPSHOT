package kr.ac.hanyang.tosca2camp.definitions;

import java.util.List;

import kr.ac.hanyang.tosca2camp.definitions.RelationshipDef.Builder;

public class RelationshipDef {
	private String type;
	private String derived_from; //URI string
	private String description; // description are treated as their own type but for now they will be string
	private List<PropertyDef> properties; 
	private List<AttributeDef> attributes;
	private List<InterfaceDef> interfaces;
	private List<String> valid_target_types;
	
	public static class Builder <T extends Builder>{
		private String type;
		private String derived_from; //URI string
		private String description; // description are treated as their own type but for now they will be string
		private List<PropertyDef> properties; 
		private List<AttributeDef> attributes;
		private List<InterfaceDef> interfaces;
		private List<String> valid_target_types;
		
		public Builder(String type){
			this.type = type;
		}
		
		public T derived_from(String derived_from){
			this.derived_from = derived_from;
			return (T) this;
		}
		
		public T description(String description){
			this.description = description;
			return (T) this;
		}
		public T properties(List<PropertyDef> properties){
			this.properties = properties;
			return (T) this;
		}
		
		public T attributes(List<AttributeDef> attributes){
			this.attributes = attributes;
			return (T) this;
		}
		
		public T interfaces(List<InterfaceDef> interfaces){
			this.interfaces = interfaces;
			return (T) this;
		}
		
		public T valid_target_types(List<String> valid_target_types){
			this.valid_target_types = valid_target_types;
			return (T) this;
		}
		
		public RelationshipDef build(){
			return new RelationshipDef(this);
		}
	}
	
	private RelationshipDef(Builder builder){
		this.type = builder.type;
		this.derived_from = builder.derived_from;
		this.description = builder.description;
		this.properties = builder.properties;
		this.attributes = builder.attributes;
		this.interfaces = builder.interfaces;
		this.valid_target_types = builder.valid_target_types;
	}

}

