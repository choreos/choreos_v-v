package eu.choreos.vv.increasefunctions;

/**
 * Exponentialy increases the return value each time increaseParams() is called,
 * if the same fixedParam is used and actualParam is replaced by the last return
 * value.
 * 
 */
public class ExponentialIncrease implements ScalabilityFunction {

	private Number fixedParam;

	public ExponentialIncrease(Number fixedParam) {
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
	public Number increaseParams(Number currentParam) {
		return currentParam.doubleValue() * fixedParam.doubleValue();
	}

}
