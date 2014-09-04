package eu.choreos.vv.increasefunctions;

/**
 * Linearly increases the return value each time increaseParams() is called, if the same fixedParam is used and actualParam is replaced by the last return value.
 *
 */
public class LinearIncrease implements ScalabilityFunction {
	
	private Integer fixedParam;
	
	public LinearIncrease(Integer fixedParam) {
		this.fixedParam = fixedParam;
	}
	/**
	 * Returns currentParam + fixedParam.
	 * @param currentParam last value used
	 * @return next value to use
	 */
	@Override
	public Integer increaseParams(Integer currentParam) {
		return currentParam + fixedParam;
	}

}
