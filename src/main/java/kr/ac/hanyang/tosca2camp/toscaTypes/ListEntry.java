package kr.ac.hanyang.tosca2camp.toscaTypes;

public class ListEntry<V>  {

	private String key;
	private V value; 
	
	public ListEntry(String key, V value){
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
		
		public ListEntry<V> build(){
			return new ListEntry<V>(this);
		}
	}
	
	private ListEntry(Builder<V> builder){
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
		ListEntry<V> other = (ListEntry<V>) obj;
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
// need to fix this class