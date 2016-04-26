package kr.ac.hanyang.tosca2camp.assignments;

// use Generic for late binding the value object
public class PropertyAs<V> {

	private String name;
	private V value;
	
	public static class Builder<V>{
		private String name;
		private V value;
		
		public Builder(String name, V value){
			this.name = name;
			this.value = (V) value; 
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public PropertyAs build(){
			return new PropertyAs(this);
		}
	}
	
	private PropertyAs(Builder<V> builder){
		this.name = builder.name;
		this.value = (V) builder.value;
	}
	
	public String getName(){return name;}	
	public V getValue(){return value;}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyAs<V> other = (PropertyAs<V>) obj;
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
