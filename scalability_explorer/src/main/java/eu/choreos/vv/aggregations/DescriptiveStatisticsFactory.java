package eu.choreos.vv.aggregations;

import java.util.List;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

public class DescriptiveStatisticsFactory {
	
	public static DescriptiveStatistics create(List<Double> series) {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for(Double d:series) {
			stats.addValue(d);
		}
		return stats;
	}

}
