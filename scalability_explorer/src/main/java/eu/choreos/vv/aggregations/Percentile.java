package eu.choreos.vv.aggregations;

import java.util.List;

/**
 * Implements percentile computation, i. e., a value for which a given percentile of a list is bellow it. 
 *
 */
public class Percentile implements Aggregator {

	private int percentile;
	final private String LABEL = "percentile";
	
	/**
	 * Creates an instance of Percentile for a specified value.
	 * @param value the percentile this object will work on
	 */
	public Percentile(int value) {
		percentile = value;
	}

	@Override
	public String getLabel() {
		return percentile + "-" + LABEL;
	}

	/**
	 * calculates the percentile for a given list
	 * @param series a list of numbers
	 * @return the percentile for the given list
	 */
	@Override
	public Double aggregate(List<Number> series) {
		return DescriptiveStatisticsFactory.create(series).getPercentile(
				percentile);
	}

}
