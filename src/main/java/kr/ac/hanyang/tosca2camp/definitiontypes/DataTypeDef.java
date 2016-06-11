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
	private List<ConstraintTypeDef> constraints; 
	private Map<String, PropertyDef> properties;
	
	public static class Builder{
		private String typeName;
		private DataTypeDef derived_from;
		private String description; // description are treated as their own type but for now they will be string
		private List<ConstraintTypeDef> constraints = new ArrayList<ConstraintTypeDef>();
		private Map<String, PropertyDef> properties = new LinkedHashMap<String, PropertyDef>(); 
		
		public Builder(String typeName){
			this.typeName = typeName;
		}
		
		public Builder derived_from(DataTypeDef derived_from){
			this.derived_from = derived_from;
			return this;
		}
		
		public Builder description(String description){
			this.description = description;
			return this;
		}
		
		public Builder addConstraint(ConstraintTypeDef constraint){
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
	
	public Builder getBuilder(){ 
		Builder builder = new Builder(typeName);
		builder.derived_from = this.derived_from;
		builder.description = this.description;
		builder.constraints = this.constraints;
		builder.properties = this.properties;
		return builder;
	}
	
	public DataTypeDef clone(){
		try{
			DataTypeDef toReturn = (DataTypeDef) super.clone();
			toReturn.derived_from = (DataTypeDef) derived_from.clone();
			toReturn.constraints = new ArrayList<ConstraintTypeDef>();
			for( ConstraintTypeDef constraint:constraints){
				toReturn.constraints.add((ConstraintTypeDef) constraint.clone()); //make sure to create a copy
			}
			toReturn.properties = new LinkedHashMap<String, PropertyDef>();
			for(String pDefName:properties.keySet()){
				PropertyDef pDef = properties.get(pDefName);
				toReturn.properties.put(pDefName,  pDef.clone()); //make sure pDef can create a copy
			}
			return toReturn;
		}catch(CloneNotSupportedException e){
			return null;
		}	
	}
	
	
//	public static DataTypeDef clone(DataTypeDef orig2Copy){
//		DataTypeDef.Builder copyBuilder = new DataTypeDef.Builder(orig2Copy.getTypeName());
//		copyBuilder.derived_from(orig2Copy.getDerived_from())
//				   .description(orig2Copy.getDescription());
//		for(String pDefName:orig2Copy.properties.keySet()){
//			PropertyDef pDef = orig2Copy.properties.get(pDefName);
//			copyBuilder.addProperty(PropertyDef.clone(pDef)); //make sure pDef can create a copy
//		}
//		//need to be able to clone the constraintstosca.constraints.min_length.yml
////		for(Map<String, Object> constraint:orig2Copy.constraints){
////			copyBuilder.addConstraint(RequirementDef.clone(rDef)); //make sure pDef can create a copy
////		}
//		return copyBuilder.build();		   
//	}
//		
	
	public String getTypeName(){return typeName;}
	public DataTypeDef getDerived_from(){return derived_from;}
	public String getDescription(){return description;}
	public List<ConstraintTypeDef> getConstraints(){return constraints;}
	public Map<String, PropertyDef>  getProperties(){return properties;}
	
	public String toString(){
		String props = "";
		String consts = "";
		for(String prop: properties.keySet())
			props+=properties.get(prop);
		for(ConstraintTypeDef constr: constraints)
			consts+=constr;	
		return "Name: "+typeName+"\n"+
				"properties: \n"+props+"\n"+
				"constraints: \n"+consts+"\n";			
	}
	
}
