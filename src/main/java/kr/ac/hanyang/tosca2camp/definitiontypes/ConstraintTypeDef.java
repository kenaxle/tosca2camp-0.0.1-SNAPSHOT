package kr.ac.hanyang.tosca2camp.definitiontypes;

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
	
	public Builder getBuilder(){ 
		Builder builder = new Builder(operator);
		builder.value = this.value;
		return builder;
	}
	
	public ConstraintTypeDef clone(){
		try{
			ConstraintTypeDef toReturn = (ConstraintTypeDef) super.clone();
			toReturn.value = value.clone(); //TODO this should clone
			return toReturn;
		}catch(CloneNotSupportedException e){
			return null;
		}	
	}
		
	
	public String getOperator(){return operator;}
	public Object getValue(){return value;}
		
}
