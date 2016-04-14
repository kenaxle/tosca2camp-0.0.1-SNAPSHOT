package kr.ac.hanyang.tosca2camp.assignments;

//use Generic for late binding the value object
public class AttributeAs<V> {


	private String name;
	private V value;
	
	public static class Builder<V>{
		private String name;
		private V value;
		
		public Builder(String name, V value){
			this.name = name;
			this.value = (V) value; 
		}
		
		public AttributeAs<V> build(){
			return new AttributeAs<V>(this);
		}
	}
	
	private AttributeAs(Builder<V> builder){
		this.name = builder.name;
		this.value = (V) builder.value;
	}
	
	public String getName(){return name;}
	
	public V getValue(){return value;}
	
	public String toString(){
		return "   "+name+": "+value;
	}
}