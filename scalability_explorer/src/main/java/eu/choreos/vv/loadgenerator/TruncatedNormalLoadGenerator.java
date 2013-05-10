package eu.choreos.vv.loadgenerator;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistribution;
import org.apache.commons.math.distribution.NormalDistributionImpl;

public class TruncatedNormalLoadGenerator extends FastestLoadGenerator {

	private double upperBound;
	private double lowerBound;
	private double sd;
	private NormalDistribution normal;

	public TruncatedNormalLoadGenerator(int poolSize, int timeout, double mean,
			double standardDeviation) {
		super(poolSize, timeout);
		init(mean, standardDeviation, 0, Double.POSITIVE_INFINITY);
	}

	public TruncatedNormalLoadGenerator(double mean, double standardDeviation) {
		super();
		init(mean, standardDeviation, 0, Double.POSITIVE_INFINITY);
	}

	public TruncatedNormalLoadGenerator() {
		super();
		init(0, 1, 0, Double.POSITIVE_INFINITY);
	}

	public TruncatedNormalLoadGenerator(double mean, double standardDeviation,
			double lowerBound, double upperBound) {
		super();
		init(mean, standardDeviation, lowerBound, upperBound);
	}

	private void init(double mean, double standardDeviation, double lowerBound,
			double upperBound) {
		this.upperBound = upperBound;
		this.lowerBound = lowerBound;
		this.sd = standardDeviation;
		normal = new NormalDistributionImpl(mean, standardDeviation);
	}

	@Override
	public void setDelay(long delay) {
		normal = new NormalDistributionImpl(delay, delay);
	}

	@Override
	protected void performRequest(final ExecutorService executor,
			final List<Future<Double>> futureResults) throws Exception {
		long delay = Math.round(nextTruncatedGaussian());
		long start = System.currentTimeMillis();
		super.performRequest(executor, futureResults);
		long end = System.currentTimeMillis();
		sleep(delay - end + start);
	}

	private double nextTruncatedGaussian() throws MathException {
		double cdfMin, cdfMax, random, truncatedGaussian;
		cdfMin = normal.cumulativeProbability(lowerBound);
		cdfMax = normal.cumulativeProbability(upperBound);
		random = Math.random() * (cdfMax - cdfMin);
		truncatedGaussian = normal
				.inverseCumulativeProbability(cdfMin + random);
		return truncatedGaussian;

	}

}
