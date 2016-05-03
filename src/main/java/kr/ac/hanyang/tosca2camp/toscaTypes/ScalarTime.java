package kr.ac.hanyang.tosca2camp.toscaTypes;

public class ScalarTime extends ScalarType<Object> {
	
	public ScalarTime(Double scalarVal, String unit) {		
		super(scalarVal.doubleValue()*getMultiple(unit), unit);
	}
	
	public Double convertTo(String newUnit){
		return (((Integer)super.getScalar()).doubleValue()/getMultiple(newUnit));
	}
	
	public Double getScalarSize(){
		return convertTo(getUnit());
	}
	
	// unable to handle TB
	private static double getMultiple(String unit){
		switch(unit){
		case "ns": return 0.000000001;
		case "us": return 0.000001;
		case "ms": return 0.001;
		case "s":  return 1;
		case "m":  return 60;
		case "h":  return 3600;
		case "d":  return 86400;
		default:   return 1;
		//case "TB":  return 1000000000000;
		//case "TiB": return 1099511627776;
		}
	}
	
	
}
