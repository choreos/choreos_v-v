package eu.choreos.vv.aggregations;

import java.util.List;


public class Percentile implements Aggregator{

	private float percentile;
	
	public Percentile(float value) {
		percentile = value;
	}
	
	@Override
	public Double aggregate(List<Double> series) {
		return DescriptiveStatisticsFactory.create(series).getPercentile(percentile);
		
//		int size = series.size();
//		Vector<Double> vector = new Vector<Double>(series); 
//		Collections.sort(vector);
//		int index = (int) Math.round( Math.floor(size * percentile) );
//		if (index >= size)
//			index = size - 1;
//		Double result = vector.get(index);
//		return result;
	}

	
	
}
