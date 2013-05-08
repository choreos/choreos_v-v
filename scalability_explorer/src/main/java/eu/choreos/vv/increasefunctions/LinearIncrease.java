package eu.choreos.vv.increasefunctions;

/**
 * Linearly increases the return value each time increaseParams() is called, if the same fixedParam is used and actualParam is replaced by the last return value.
 *
 */
public class LinearIncrease implements ScalabilityFunction {
	
	private Number fixedParam;
	
	public LinearIncrease(Number fixedParam) {
		this.fixedParam = fixedParam;
	}
	/**
	 * Returns currentParam + fixedParam.
	 * @param currentParam last value used
	 * @return next value to use
	 */
	@Override
	public Number increaseParams(Number currentParam) {
		return currentParam.doubleValue() + fixedParam.doubleValue();
	}

}
