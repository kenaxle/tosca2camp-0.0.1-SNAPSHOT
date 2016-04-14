package kr.ac.hanyang.tosca2camp.assignments;

import kr.ac.hanyang.tosca2camp.toscaTypes.MapEntry;

// use Generic for late binding the value object
public class PropertyAs<V> extends MapEntry<V> {

	public PropertyAs(String key, V value) {
		super(key, value);
	}
//	private String name;
//	private V value;
//	
//	public static class Builder<V>{
//		private String name;
//		private V value;
//		
//		public Builder(String name, V value){
//			this.name = name;
//			this.value = (V) value; 
//		}
//		
//		public PropertyAs build(){
//			return new PropertyAs(this);
//		}
//	}
//	
//	private PropertyAs(Builder builder){
//		this.name = builder.name;
//		this.value = (V) builder.value;
//	}
//	
//	public String getName(){return name;}
//	
//	public V getValue(){return value;}
//	
//	public String toString(){
//		return "   "+name+": "+value;
//	}
}
