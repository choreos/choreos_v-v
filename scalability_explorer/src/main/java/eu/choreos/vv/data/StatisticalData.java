package eu.choreos.vv.data;

import java.util.HashMap;

public class StatisticalData extends HashMap<Double, Statistics> implements PlotData<Double, Statistics> {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
