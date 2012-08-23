package eu.choreos.vv.aggregations;

import java.util.List;

import eu.choreos.vv.chart.Labeled;

public interface Aggregator extends Labeled {
	public Double aggregate(List<Number> series);
}
