package kr.ac.hanyang.tosca2camp.toscaTypes;

public class RangeType {
	private int upperBound;
	private int lowerBound;
	private boolean isUnbounded;
	
	public RangeType(int upperBound, int lowerBound) {
		this.upperBound = upperBound;
		this.lowerBound = lowerBound;
		this.isUnbounded = false;
	}
	
	//may have to set a very large number as the uperbounds
	public RangeType(int lowerBound) {
		this.lowerBound = lowerBound;
		this.isUnbounded = true;
	}

	public boolean isUnbounded(){
		return this.isUnbounded;
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
