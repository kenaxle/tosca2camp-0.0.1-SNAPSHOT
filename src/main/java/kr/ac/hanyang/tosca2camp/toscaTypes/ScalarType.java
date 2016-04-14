package kr.ac.hanyang.tosca2camp.toscaTypes;

// use Generics to provide late binding since the scalar value may be of any type
public abstract class ScalarType<T> {
	
	private T scalarVal;
	private String unit;
	
	public ScalarType(T scalarVal, String unit){
		this.scalarVal = scalarVal;
		this.unit = unit;
	}
		
}
