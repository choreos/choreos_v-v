package eu.choreos.vv.aggregations;

import java.util.List;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

/**
 * Factory of DescriptiveStatistics. To be used by aggregators that use apache.commons.math.
 * @author paulo
 *
 */
public class DescriptiveStatisticsFactory {
	
	/**
	 * Creates a new DescriptiveStatistics for a given series
	 * @param series a list of numbers
	 * @return a new DescriptiveStatistics
	 */
	public static DescriptiveStatistics create(List<Number> series) {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for(Number d:series) {
			stats.addValue(d.doubleValue());
		}
		return stats;
	}

}
