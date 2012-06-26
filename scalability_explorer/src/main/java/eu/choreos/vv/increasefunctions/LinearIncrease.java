package eu.choreos.vv.increasefunctions;


public class LinearIncrease extends ScalabilityFunction {

	@Override
	public Object increaseParams(int actualParam, int fixedParam) {
		return actualParam + fixedParam;
	}

}
