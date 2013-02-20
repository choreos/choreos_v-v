package eu.choreos.vv.analysis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.stat.inference.TestUtils;

import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.data.ScalabilityReport;

public class ANOVATest extends Analyzer {

	@Override
	public void analyse(ScalabilityReport report) throws Exception {
		List<double[]> classes = new ArrayList<double[]>();
		for (ReportData data : report.values()) {
			double[] sample = doubleArray(data.getMeasurements());
			classes.add(sample);
		}
		String test = TestUtils.oneWayAnovaTest(classes, 0.01) ? "not equivalent"
				: "equivalent";
		System.out.println("samples in " + report.getName() + " are " + test);
	}

	private double[] doubleArray(List<Number> list) {
		double[] result = new double[list.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = list.get(i).doubleValue();
		}
		return result;
	}

}
