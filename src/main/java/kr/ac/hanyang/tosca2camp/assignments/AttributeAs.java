package kr.ac.hanyang.tosca2camp.assignments;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import kr.ac.hanyang.tosca2camp.definitiontypes.AttributeDef;

//use Generic for late binding the value object
public class AttributeAs {


	private String name;
	private Object value;
	
	public static class Builder{
		private String name;
		private Object value;
		
		public Builder(String name){
			this.name = name;
			this.value = null; 
		}
		
		public Builder value(Object value){
			this.value = value;
			return this;
		}
		
		public AttributeAs build(){
			return new AttributeAs(this);
		}
	}
	
	private AttributeAs(Builder builder){
		this.name = builder.name;
		this.value = builder.value;
	}
	
	public Builder getBuilder(String name){
		Builder builder = new Builder(name);
		builder.value = this.value;
		return builder;
	}
	
	public static AttributeAs clone(AttributeAs origAttr){
		AttributeAs.Builder copyBuilder = new AttributeAs.Builder(origAttr.name);
		copyBuilder.value(origAttr.getValue());
		return copyBuilder.build();
	}
	
	public static AttributeAs.Builder getDefinitionBuilder(AttributeDef attrDef){
		Constructor<?> constructor;
		try {
			constructor = Class.forName(attrDef.getType()).getConstructor(String.class);
			Object value;
			value = constructor.newInstance(attrDef.getDefaultVal());
			return new AttributeAs.Builder(attrDef.getName()).value(value);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException| InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getName(){return name;}
	
	public Object getValue(){return value;}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeAs other = (AttributeAs) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public String toString(){
		return "   "+name+": "+value;
	}
}