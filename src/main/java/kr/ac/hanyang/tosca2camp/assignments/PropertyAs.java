package kr.ac.hanyang.tosca2camp.assignments;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import kr.ac.hanyang.tosca2camp.definitiontypes.PropertyDef;

// use Generic for late binding the value object
public class PropertyAs {

	private String name;
	private Object value;
	
	public static class Builder{
		private String name;
		private Object value;
		
		public Builder(String name){
			this.name = name;
			this.value =  null; 
		}
		
		public Builder value(Object value){
			this.value = value;
			return this;
		}
		
		public PropertyAs build(){
			return new PropertyAs(this);
		}
	}
	
	private PropertyAs(Builder builder){
		this.name = builder.name;
		this.value = builder.value;
	}
	
	public Builder getBuilder(String name){
		Builder builder = new Builder(name);
		builder.value = this.value;
		return builder;
	}
	
	public static PropertyAs clone(PropertyAs origProp){
		PropertyAs.Builder copyBuilder = new PropertyAs.Builder(origProp.name);
		copyBuilder.value(origProp.getValue());
		return copyBuilder.build();
	}
	
	public static PropertyAs.Builder getDefinitionBuilder(PropertyDef propDef){
		
		Constructor<?> constructor;
		try {
			constructor = Class.forName(propDef.getType()).getConstructor(String.class);
			Object value;
			value = constructor.newInstance(propDef.getDefaultVal());
			return new PropertyAs.Builder(propDef.getName()).value(value);
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
		PropertyAs other = (PropertyAs) obj;
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
