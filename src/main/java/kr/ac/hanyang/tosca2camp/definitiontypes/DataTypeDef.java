package kr.ac.hanyang.tosca2camp.definitiontypes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.ac.hanyang.tosca2camp.definitiontypes.NodeDef.Builder;

/**
 * @author Kena Alexander
 * @since 
 */

public class DataTypeDef {

	private String typeName;
	private DataTypeDef derived_from;
	private String description; // description are treated as their own type but for now they will be string
	private List<Map<String, Object>> constraints; 
	private Map<String, PropertyDef> properties;
	
	public static class Builder{
		private String typeName;
		private DataTypeDef derived_from;
		private String description; // description are treated as their own type but for now they will be string
		private List<Map<String, Object>> constraints = new ArrayList<Map<String, Object>>();
		private Map<String, PropertyDef> properties = new LinkedHashMap<String, PropertyDef>(); 
		
		public Builder(String name){
			this.typeName = name;
		}
		
		public Builder derived_from(DataTypeDef derived_from){
			this.derived_from = derived_from;
			return this;
		}
		
		public Builder description(String description){
			this.description = description;
			return this;
		}
		
		public Builder addConstraint(Map<String, Object> constraint){
			this.constraints.add(constraint);
			return this;
		}
		
		public Builder addProperty(PropertyDef property){
			this.properties.put(property.getName(),property);
			return  this;
		}
		
		public DataTypeDef build(){
			return new DataTypeDef(this);
		}
	}
	
	private DataTypeDef(Builder builder){
		this.typeName = builder.typeName;
		this.derived_from = builder.derived_from;
		this.description = builder.description;
		this.constraints = builder.constraints;
		this.properties = builder.properties;
	}
	
	public Builder getBuilder(String typeName){ 
		Builder builder = new Builder(typeName);
		builder.derived_from = this.derived_from;
		builder.description = this.description;
		builder.constraints = this.constraints;
		builder.properties = this.properties;
		return builder;
	}
	
	public static DataTypeDef clone(DataTypeDef orig2Copy){
		DataTypeDef.Builder copyBuilder = new DataTypeDef.Builder(orig2Copy.getTypeName());
		copyBuilder.derived_from(orig2Copy.getDerived_from())
				   .description(orig2Copy.getDescription());
		for(String pDefName:orig2Copy.properties.keySet()){
			PropertyDef pDef = orig2Copy.properties.get(pDefName);
			copyBuilder.addProperty(PropertyDef.clone(pDef)); //make sure pDef can create a copy
		}
		//need to be able to clone the constraints
//		for(Map<String, Object> constraint:orig2Copy.constraints){
//			copyBuilder.addConstraint(RequirementDef.clone(rDef)); //make sure pDef can create a copy
//		}
		return copyBuilder.build();		   
	}
		
	
	public String getTypeName(){return typeName;}
	public DataTypeDef getDerived_from(){return derived_from;}
	public String getDescription(){return description;}
	public List<Map<String, Object>> getConstraints(){return constraints;}
	public Map<String, PropertyDef>  getProperties(){return properties;}
	
}
