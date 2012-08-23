package eu.choreos.vv.aggregations;

import java.util.List;

public class Mean implements Aggregator {

	final String LABEL = "mean";

	@Override
	public Double aggregate(List<Number> series) {
		return DescriptiveStatisticsFactory.create(series).getMean();
	}

	@Override
	public String getLabel() {
		return LABEL;
	}

}
