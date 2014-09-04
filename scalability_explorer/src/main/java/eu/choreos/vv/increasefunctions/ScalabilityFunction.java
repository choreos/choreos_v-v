package eu.choreos.vv.increasefunctions;


/**
 * Interface to define the increasing patterns to scale the parameters of a ScalabilityTestItem 
 *
 */
public interface ScalabilityFunction {
	/**
	 * Calculates the next value for a parameter
	 * @param currentValue last value used
	 * @return next value to use
	 */
	public Integer increaseParams(Integer currentValue);
}
