package eu.choreos.vv;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.vv.aggregations.Aggregator;
import eu.choreos.vv.aggregations.Percentile;

public class AggregatorTest {

	List<Double> aList;
	
	@Before
	public void setUp() {
		aList = new ArrayList<Double>();
		Collections.addAll(aList, 11406.0, 19502.0, 25386.0, 31496.0, 2574.0, 8098.0, 24085.0, 21763.0, 29396.0, 19302.0, 10239.0, 31871.0, 3773.0, 21630.0, 15977.0, 6545.0, 8615.0, 31188.0, 1153.0, 24453.0, 9693.0, 31254.0, 21880.0, 22513.0, 16157.0, 7159.0, 31585.0, 21693.0, 31748.0, 10700.0);
	}
	
	@Test
	public void sholdGet50Percentile() {
		Aggregator aggr = new Percentile(50);
		double value = aggr.aggregate(aList);
		assertEquals(21693.0 ,value, 0);
	}

	@Test
	public void sholdGet95Percentile() {
		Aggregator aggr = new Percentile(95);
		double value = aggr.aggregate(aList);
		assertEquals(31871.0 ,value, 0);
	}
}
