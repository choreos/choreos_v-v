package eu.choreos.vv.increasefunctions;

/**
 * Quadratically increases the return value each time increaseParams() is called, if the same fixedParam is used and actualParam is replaced by the last return value.
 *
 */
public class QuadraticIncrease implements ScalabilityFunction {
	
	/**
	 * factor of increase. initial value is 2.
	 */
	private double actualIncreasingTime;
	private Number fixedParam;

	public QuadraticIncrease(Number fixedParam) {
		actualIncreasingTime=2;
		this.fixedParam = fixedParam;
	}
	/**
	 * Returns fixedParam*(actualIncreasingTime^2) and increments actualIncreasingTime.
	 * currentParam is not used. 
	 * @param currentParam last value used
	 * @return next value to use
	 */
	@Override
	public Number increaseParams(Number currentParam) {
		Number value = fixedParam.doubleValue()*(actualIncreasingTime*actualIncreasingTime);
		actualIncreasingTime++;
		return value;
	}
}
