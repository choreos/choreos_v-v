package eu.choreos.vv.aggregations;

import java.util.List;

import eu.choreos.vv.chart.Labeled;


/**
 * Interface to aggregate a list of numbers in a single value.
 * Classes which implement Aggregator should be used in order to give statistical treatment to a battery of tests. 
 *
 */
public interface Aggregator extends Labeled {
	/**
	 * aggregate must be implemented to calculate some aggregation on a list
	 * @param series	a list of numbers to be aggregated
	 * @return			a single value aggregating the list
	 */
	public Double aggregate(List<Number> series);
}
