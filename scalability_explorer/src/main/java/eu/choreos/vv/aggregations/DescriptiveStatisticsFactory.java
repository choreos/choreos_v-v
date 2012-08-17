package eu.choreos.vv.aggregations;

import java.util.List;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

public class DescriptiveStatisticsFactory {
	
	public static DescriptiveStatistics create(List<Number> series) {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for(Number d:series) {
			stats.addValue(d.doubleValue());
		}
		return stats;
	}

}
