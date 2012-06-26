package eu.choreos.vv.increasefunctions;


public class LinearIncrease implements ScalabilityFunction {

	@Override
	public Object increaseParams(int actualParam, int fixedParam) {
		return actualParam + fixedParam;
	}

}
