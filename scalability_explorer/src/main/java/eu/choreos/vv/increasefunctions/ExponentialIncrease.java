package eu.choreos.vv.increasefunctions;

/**
 * Exponentialy increases the return value each time increaseParams() is called,
 * if the same fixedParam is used and actualParam is replaced by the last return
 * value.
 * 
 */
public class ExponentialIncrease implements ScalabilityFunction {

	private Integer fixedParam;

	public ExponentialIncrease(Integer fixedParam) {
		this.fixedParam = fixedParam;
	}

	/**
	 * Returns actualParam * fixedParam.
	 * 
	 * @param actualParam
	 *            last value used
	 * @return next value to use
	 */
	@Override
	public Integer increaseParams(Integer currentParam) {
		return currentParam * fixedParam;
	}

}
