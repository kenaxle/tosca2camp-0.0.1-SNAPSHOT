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

public class ConstraintTypeDef {

	private String operator;
	private Object value; 
	
	
	public static class Builder{
		private String operator;
		private Object value;
		
		public Builder(String name){
			this.operator = name;
			this.value = null;
		}
		
		public Builder value(Object value){
			this.value = value;
			return this;
		}
		
		public ConstraintTypeDef build(){
			return new ConstraintTypeDef(this);
		}
	}
	
	private ConstraintTypeDef(Builder builder){
		this.operator = builder.operator;
		this.value = builder.value;
	}
	
	public Builder getBuilder(String operator){ 
		Builder builder = new Builder(operator);
		builder.value = this.value;
		return builder;
	}
	
	public static ConstraintTypeDef clone(ConstraintTypeDef orig2Copy){
		ConstraintTypeDef.Builder copyBuilder = new ConstraintTypeDef.Builder(orig2Copy.getOperator());
		copyBuilder.value(orig2Copy.getValue());
		return copyBuilder.build();		   
	}
		
	
	public String getOperator(){return operator;}
	public Object getValue(){return value;}
		
}
