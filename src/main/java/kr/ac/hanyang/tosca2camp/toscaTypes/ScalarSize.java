package kr.ac.hanyang.tosca2camp.toscaTypes;

//the value should be stored natively in bytes
public class ScalarSize extends ScalarType<Object> {

	public ScalarSize(Integer scalarVal, String unit) {		
		super(scalarVal.intValue()*getMultiple(unit), unit);
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
		case "B":   return 1;
		case "kB":  return 1000;
		case "KiB": return 1024;
		case "MB":  return 1000000;
		case "MiB": return 1048576;
		case "GB":  return 1000000000;
		case "GiB": return 1073741824;
		default: return 1;
		//case "TB":  return 1000000000000;
		//case "TiB": return 1099511627776;
		}
	}
	
	

}
