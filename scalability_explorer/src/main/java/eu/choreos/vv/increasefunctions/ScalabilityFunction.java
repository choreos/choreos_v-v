package eu.choreos.vv.increasefunctions;


/**
 * Interface to define the increasing patterns to scale the parameters of a ScalabilityTestItem 
 *
 */
public interface ScalabilityFunction {
	/**
	 * Calculates the next value for a parameter
	 * @param actualParam last value used
	 * @param fixedParam initial value
	 * @return next value to use
	 */
	public Number increaseParams(Number actualParam, Number fixedParam);
}
