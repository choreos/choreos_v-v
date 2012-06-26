package eu.choreos.vv.increasefunctions;

public class QuadraticIncrease implements ScalabilityFunction {
	

	private int actualIncreasingTime;

	public QuadraticIncrease() {
		actualIncreasingTime=2;
	}

	@Override
	public Object increaseParams(int actualParam, int fixedParam) {
		int value = fixedParam*(actualIncreasingTime*actualIncreasingTime);
		actualIncreasingTime++;
		return value;
	}
}
