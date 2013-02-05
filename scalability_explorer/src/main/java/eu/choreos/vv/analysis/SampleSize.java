package eu.choreos.vv.analysis;

import java.util.List;

import org.apache.commons.math.distribution.TDistribution;
import org.apache.commons.math.distribution.TDistributionImpl;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import eu.choreos.vv.aggregations.DescriptiveStatisticsFactory;
import eu.choreos.vv.data.ScalabilityReport;

public class SampleSize extends Analyser {

	private double d;

	/**
	 * Calculates the appropriate sample size for the experiment.
	 * 
	 * @param precision
	 *            a degree of precision given in the same unit as the
	 *            measurements
	 */
	public SampleSize(double precision) {
		d = precision;
	}

	@Override
	public void analyse(ScalabilityReport report) throws Exception {
		for (Number key : report.keySet()) {
			List<Number> sample = report.get(key).getMeasurements();
			int n = sample.size();
			DescriptiveStatistics ds = DescriptiveStatisticsFactory
					.create(sample);
			double s = ds.getStandardDeviation();
			TDistribution dist = new TDistributionImpl(n - 1);
			double t = dist.cumulativeProbability(0.05);
			long sampleSize = Math.round(Math.ceil(Math.pow((t * s) / d, 2)));
			System.out.println("sample size must be " + sampleSize);
		}

	}

}
