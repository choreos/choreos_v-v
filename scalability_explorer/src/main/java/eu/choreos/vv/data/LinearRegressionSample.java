package eu.choreos.vv.data;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.stat.descriptive.MultivariateSummaryStatistics;

import eu.choreos.vv.aggregations.AggregationFunction;

public class LinearRegressionSample {
	
	private double[][] x;
	private double[] y;
	private double[][] omega;
	
	private AggregationFunction aggregator;
	
	public LinearRegressionSample(AggregationFunction function) {
		aggregator = function;
	}
	
	public void setSample(ExperimentReport report) {
		setY(report);
		setX(report);
		updateOmega();
	}

	public void setX(ExperimentReport report) {
		x = new double[report.size()][];
		for(int i = 0; i < x.length; i++) {
			List<Number> parameters = report.get(i).getParameters();
			x[i] = new double[parameters.size()];
			for(int j = 0; j < x[i].length; j++) {
				x[i][j] = parameters.get(j).doubleValue();
			}
		}
	}

	public void setY(ExperimentReport report) {
		y = new double[report.size()];
		for(int i = 0; i < y.length; i++) {
			List<Number> measurements = report.get(i).getMeasurements("latency");
			double value = aggregator.aggregate(measurements);
			y[i] = value;
		}
	}
	
	public double[][] updateOmega() {
		MultivariateSummaryStatistics summary = new MultivariateSummaryStatistics(x[0].length, true);
		RealMatrix matrix = summary.getCovariance();
		matrix.copySubMatrix(0, matrix.getRowDimension(), 0, matrix.getColumnDimension(), omega);
		return omega;
	}
	
	public double[][] getX() {
		return x;
	}
	public void setX(double[][] x) {
		this.x = x;
	}
	public double[] getY() {
		return y;
	}
	public void setY(double[] y) {
		this.y = y;
	}
	public double[][] getOmega() {
		return omega;
	}
	public void setOmega(double[][] omega) {
		this.omega = omega;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(omega);
		result = prime * result + Arrays.hashCode(x);
		result = prime * result + Arrays.hashCode(y);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinearRegressionSample other = (LinearRegressionSample) obj;
		if (!Arrays.equals(omega, other.omega))
			return false;
		if (!Arrays.equals(x, other.x))
			return false;
		if (!Arrays.equals(y, other.y))
			return false;
		return true;
	}
	
	

}
