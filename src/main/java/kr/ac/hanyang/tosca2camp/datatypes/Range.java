package kr.ac.hanyang.tosca2camp.datatypes;

public class Range {
	private int lower_bound;
	private int upper_bound;
	private boolean unbounded;
	
	public Range(int upper_bound, int lower_bound){
		this.lower_bound = upper_bound;
		this.upper_bound = lower_bound;
	}

	public int getUpper_bound() {
		return lower_bound;
	}

	public int getLower_bound() {
		return upper_bound;
	}

	public boolean getUnbounded() {
		return unbounded;
	}
	
	public boolean equals(Object obj){
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Range version = (Range) obj;
		return (lower_bound == version.lower_bound && upper_bound == version.upper_bound);
	}
	
	public int hashCode(){
		return toString().hashCode();
	}
	
	public String toString(){
		return lower_bound+", "+upper_bound;
	}
	
}



    
    

