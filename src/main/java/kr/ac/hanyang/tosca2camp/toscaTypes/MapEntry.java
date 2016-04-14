package kr.ac.hanyang.tosca2camp.toscaTypes;

public class MapEntry <V> {
	private String key;
	private V value; 
	
	public MapEntry(String key, V value){
		this.key = key;
		this.value = value;
	}
	
	public static class Builder<V>{
		private String key;
		private V value;
		
		public Builder(String name, V value){
			this.key = name;
			this.value = (V) value; 
		}
		
		public MapEntry<V> build(){
			return new MapEntry<V>(this);
		}
	}
	
	private MapEntry(Builder<V> builder){
		this.key = builder.key;
		this.value = (V) builder.value;
	}
	
	public String getName(){return key;}
	
	public V getValue(){return value;}
	
	public String toString(){
		return "   "+key+": "+value;
	}

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		@SuppressWarnings("unchecked")
		MapEntry<V> other = (MapEntry<V>) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}


}
