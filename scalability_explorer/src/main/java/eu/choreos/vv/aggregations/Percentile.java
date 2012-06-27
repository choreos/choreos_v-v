package eu.choreos.vv.aggregations;

import java.util.Collections;
import java.util.List;
import java.util.Vector;


public class Percentile implements Aggregator{

	private float percentile;
	
	public Percentile(float value) {
		percentile = value/100;
	}
	
	@Override
	public Double aggregate(List<Double> series) {
		int size = series.size();
		Vector<Double> vector = new Vector<Double>(series); 
		Collections.sort(vector);
		int index = (int) Math.round( Math.floor(size * percentile) + 1 );
		Double result = vector.get(index);
		return result;
	}

	
	
}
