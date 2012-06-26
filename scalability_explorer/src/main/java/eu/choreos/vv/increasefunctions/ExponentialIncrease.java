package eu.choreos.vv.increasefunctions;

public class ExponentialIncrease implements ScalabilityFunction {

	@Override
	public Object increaseParams(int actualParam, int fixedParam) {
		return actualParam*2;
	}

}
