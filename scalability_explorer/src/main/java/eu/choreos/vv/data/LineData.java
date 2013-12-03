package eu.choreos.vv.data;

import java.util.HashMap;

public class LineData extends HashMap<Double, Double>  implements PlotData<Double, Double> {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
