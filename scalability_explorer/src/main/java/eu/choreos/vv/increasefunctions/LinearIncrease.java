package eu.choreos.vv.increasefunctions;

/**
 * Linearly increases the return value each time increaseParams() is called, if the same fixedParam is used and actualParam is replaced by the last return value.
 *
 */
public class LinearIncrease implements ScalabilityFunction {
	
	/**
	 * Returns actualParam + fixedParam.
	 * @param actualParam last value used
	 * @param fixedParam initial value
	 * @return next value to use
	 */
	@Override
	public Number increaseParams(Number actualParam, Number fixedParam) {
		return actualParam.doubleValue() + fixedParam.doubleValue();
	}

}
