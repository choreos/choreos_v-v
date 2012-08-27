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

	public QuadraticIncrease() {
		actualIncreasingTime=2;
	}
	/**
	 * Returns fixedParam*(actualIncreasingTime^2) and increments actualIncreasingTime.
	 * @param actualParam last value used
	 * @param fixedParam initial value
	 * @return next value to use
	 */
	@Override
	public Number increaseParams(Number actualParam, Number fixedParam) {
		Number value = fixedParam.doubleValue()*(actualIncreasingTime*actualIncreasingTime);
		actualIncreasingTime++;
		return value;
	}
}
