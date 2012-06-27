package eu.choreos.vv.aggregations;

import java.util.List;

public interface Aggregator {
	public Double aggregate(List<Double> series);
}
