package eu.choreos.vv;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;

import eu.choreos.vv.data.ExperimentReport;
import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.increasefunctions.LinearIncrease;
import eu.choreos.vv.increasefunctions.ScalabilityFunction;

/**
 * ScalabilityTest is the class in charge of executing a test multiple times
 * increasing the scalable parameters.
 * 
 */
public class ScaleCaster {

	RandomData keyGenerator;
	Scalable item;
	String name;
	Integer timesToExecute;
	Double measurementLimit;
	Map<String, ValueAndFunction> currentParameterValues;
	List<ValueAndFunction> values;


	public ScaleCaster(Scalable item, String name,
			Integer timesToExecute, Double measurementLimit) {
		super();
		this.item = item;
		this.name = name;
		this.timesToExecute = timesToExecute;
		this.measurementLimit = measurementLimit;
		this.keyGenerator = new RandomDataImpl();
		this.currentParameterValues = new HashMap<String, ValueAndFunction>();
		this.values = new ArrayList<ValueAndFunction>();
	}

	public Scalable getItem() {
		return item;
	}

	public void setItem(Scalable item) {
		this.item = item;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTimesToExecute() {
		return timesToExecute;
	}

	public void setTimesToExecute(Integer timesToExecute) {
		this.timesToExecute = timesToExecute;
	}

	public Double getMeasurementLimit() {
		return measurementLimit;
	}

	public void setMeasurementLimit(double measurementLimit) {
		this.measurementLimit = measurementLimit;
	}

	public String addInitialParameterValue(Number value, ScalabilityFunction function) {
		String key = nextKey();
		ValueAndFunction pair = new ValueAndFunction(value, function);
		currentParameterValues.put(key, pair);
		values.add(pair);
		return key;
	}

	public Number getCurrentParameterValue(String key) {
		return currentParameterValues.get(key).value;
	}
	
	private synchronized String nextKey() {
		return keyGenerator.nextHexString(8);
	}

	private void increaseParamentersValues() {
		for(ValueAndFunction pair: currentParameterValues.values()) {
			Number value = pair.value;
			ScalabilityFunction function = pair.function;
			pair.value = function.increaseParams(value);
		}
	}

	private List<Number> executeItem() throws Exception {
		return item.execute(this);
	}

	/**
	 * Executes the test, increasing the scalability parameters, up to the
	 * specified number of times ou measurement limit
	 * 
	 * @return a ScalabiltyReport of the execution
	 * @throws Exception
	 */
	public ExperimentReport execute() {
		List<Number> values;
		ExperimentReport report = new ExperimentReport(this.getName());
		try {
			for (int i = 0; i < this.getTimesToExecute()
					/*&& value <= this.getMeasurementLimit()*/; i++) { //TODO: consider limit again
				values = this.executeItem();
				List<Number> params = new ArrayList<Number>();
				for(ValueAndFunction pair: this.values) {
					params.add(pair.value);
				}
				ReportData data = new ReportData(params, values); //TODO: include labels
				report.put( i + 1, data);
				this.increaseParamentersValues();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return report;
	}

}

class ValueAndFunction {
	Number value;
	ScalabilityFunction function;
	
	public ValueAndFunction(Number value, ScalabilityFunction function) {
		this.value = value;
		this.function = function;
	}
	
}