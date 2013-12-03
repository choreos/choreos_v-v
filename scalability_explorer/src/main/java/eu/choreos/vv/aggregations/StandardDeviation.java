package eu.choreos.vv.aggregations;

import java.util.List;

public class StandardDeviation implements AggregationFunction {

	private final String LABEL = "standard deviation";
	
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return LABEL;
	}

	@Override
	public Double aggregate(List<Number> series) {
		// TODO Auto-generated method stub
		return DescriptiveStatisticsFactory.create(series).getStandardDeviation();
	}

}
