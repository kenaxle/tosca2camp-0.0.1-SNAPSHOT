package kr.ac.hanyang.tosca2camp.toscaTypes;

public class RangeType {
	private int upperBound;
	private int lowerBound;
	
	public RangeType(int upperBound, int lowerBound) {
		this.upperBound = upperBound;
		this.lowerBound = lowerBound;
	}
	
	//may have to set a very large number as the uperbounds
	public RangeType(int upperBound) {
		this.upperBound = upperBound;
	}


	public int getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}
	
	
	
}
