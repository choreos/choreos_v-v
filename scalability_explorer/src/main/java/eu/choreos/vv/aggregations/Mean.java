package eu.choreos.vv.aggregations;

import java.util.List;

/**
 * Implements mean calculation 
 *
 */
public class Mean implements Aggregator {

	final String LABEL = "mean";

	/**
	 * calculates the mean of a list
	 * @param series	a list of numbers
	 * @return			the mean of the numbers in the list
	 */
	@Override
	public Double aggregate(List<Number> series) {
		return DescriptiveStatisticsFactory.create(series).getMean();
	}

	@Override
	public String getLabel() {
		return LABEL;
	}

}
