package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.ArrayList;
import java.util.List;

import kr.ac.hanyang.tosca2camp.definitiontypes.RelationshipDef.Builder;

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
		private List<PropertyDef> properties = new ArrayList(); 
		private List<AttributeDef> attributes = new ArrayList();
		private List<InterfaceDef> interfaces = new ArrayList();
		private List<String> valid_target_types = new ArrayList();
		
		public Builder(String type){
			this.type = type;
		}
		
		public Builder() {}

		public T derived_from(String derived_from){
			this.derived_from = derived_from;
			return (T) this;
		}
		
		public T description(String description){
			this.description = description;
			return (T) this;
		}
		
		public T addProperty(PropertyDef property){
			this.properties.add(property);
			return (T) this;
		}
		
		public T addAttribute(AttributeDef attribute){
			this.attributes.add(attribute);
			return (T) this;
		}
		
		public T addInterface(InterfaceDef iFace){
			this.interfaces.add(iFace);
			return (T) this;
		}
		
		public T addValid_target_types(String valid_target_types){
			this.valid_target_types.add(valid_target_types);
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
	
	public static RelationshipDef clone(RelationshipDef origRel){
		RelationshipDef.Builder<Builder> copyBuilder = new RelationshipDef.Builder<>(origRel.type);
		copyBuilder.derived_from(origRel.derived_from)
				   .description(origRel.description);
		for(PropertyDef pDef:origRel.properties){
			copyBuilder.addProperty(PropertyDef.clone(pDef)); //make sure pDef can create a copy
		}
		for(AttributeDef aDef:origRel.attributes){
			copyBuilder.addAttribute(AttributeDef.clone(aDef)); //make sure pDef can create a copy
		}
		for(InterfaceDef rDef:origRel.interfaces){
			copyBuilder.addInterface(InterfaceDef.clone(rDef)); //make sure pDef can create a copy
		}
		for(String cDef:origRel.valid_target_types){
			copyBuilder.addValid_target_types(cDef); //make sure pDef can create a copy
		}
		return copyBuilder.build();		   
	}
	
	public Builder getBuilder(String name){
		Builder builder = new Builder(type);
		builder.derived_from = this.derived_from;
		builder.description = this.description;
		builder.properties = this.properties;
		builder.attributes = this.attributes;
		builder.interfaces = this.interfaces;
		builder.valid_target_types = this.valid_target_types;
		return builder;
	}

	public String toString(){
		return "type: "+type+"\n";
	}
}

