package eu.choreos.vv.aggregations;

import java.util.List;

public class Percentile implements Aggregator {

	private int percentile;
	final private String LABEL = "percentile";

	public Percentile(int value) {
		percentile = value;
	}

	@Override
	public String getLabel() {
		return percentile + "-" + LABEL;
	}

	@Override
	public Double aggregate(List<Number> series) {
		return DescriptiveStatisticsFactory.create(series).getPercentile(
				percentile);
	}

}
