package eu.choreos.vv;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.vv.aggregations.AggregationFunction;
import eu.choreos.vv.aggregations.Mean;
import eu.choreos.vv.aggregations.Percentile;

public class AggregatorTest {

	List<Number> aList;
	
	@Before
	public void setUp() {
		aList = new ArrayList<Number>();
		Collections.addAll(aList, 11406.0, 19502.0, 25386.0, 31496.0, 2574.0, 8098.0, 24085.0, 21763.0, 29396.0, 19302.0, 10239.0, 31871.0, 3773.0, 21630.0, 15977.0, 6545.0, 8615.0, 31188.0, 1153.0, 24453.0, 9693.0, 31254.0, 21880.0, 22513.0, 16157.0, 7159.0, 31585.0, 21693.0, 31748.0, 10700.0);
	}
	
	@Test
	public void sholdGet50Percentile() {
		AggregationFunction aggr = new Percentile(50);
		double value = aggr.aggregate(aList);
		assertEquals(20566.0 ,value, 0);
//		assertEquals(21630.0 ,value, 0);
	}

	@Test
	public void sholdGet95Percentile() {
		AggregationFunction aggr = new Percentile(95);
		double value = aggr.aggregate(aList);
		assertEquals(31803.35 ,value, 0);
//		assertEquals(31748.0 ,value, 0);
	}
	
	@Test
	public void sholdGetMean() {
		AggregationFunction aggr = new Mean();
		double value = aggr.aggregate(aList);
		assertEquals(18427.8, value, 0);
	}
}
