package eu.choreos.vv.loadgenerator.strategy;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistribution;
import org.apache.commons.math.distribution.NormalDistributionImpl;

public class TruncatedNormalLoad extends LoadGenerationStrategy {
	
	private NormalDistribution normal;
	private double lowerBound;
	private double upperBound;
	private double standardDeviation;
	private long nextNormal;
	
	@Override
	public void setup() {
		normal = new NormalDistributionImpl(delay, standardDeviation);
	}
	
	@Override
	public void beforeRequest() throws Exception {
		nextNormal = Math.round(nextTruncatedGaussian());
		super.beforeRequest();
	}
	
	@Override
	public void afterRequest() throws Exception {
		super.afterRequest();
		sleep(nextNormal - end + start);
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

	public double getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}

	public double getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}

	public double getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
	
	

}
