package kr.ac.hanyang.tosca2camp.toscaTypes;

public class ScalarFrequency extends ScalarType<Object> {

	public ScalarFrequency(Double scalarVal, String unit) {		
		super(scalarVal.doubleValue()*getMultiple(unit), unit);
	}
	
	public Integer convertTo(String newUnit){
		return (((Integer)super.getScalar()).intValue()/getMultiple(newUnit));
	}
	
	public Integer getScalarSize(){
		return convertTo(getUnit());
	}
	
	// unable to handle TB
	private static int getMultiple(String unit){
		switch(unit){
		case "Hz":   return 1;
		case "kHz":  return 1000;
		case "MHz":  return 1000000;
		case "GHz":  return 1000000000;
		default: return 1;
		}
	}
	
}
