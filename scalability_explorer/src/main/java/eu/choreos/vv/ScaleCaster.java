package eu.choreos.vv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.data.ScalabilityReport;
import eu.choreos.vv.increasefunctions.LinearIncrease;
import eu.choreos.vv.increasefunctions.ScalabilityFunction;

/**
 * ScalabilityTest is the class in charge of executing a test multiple times
 * increasing the scalable parameters.
 * 
 */
public class ScaleCaster {

	Scalable item;
	String name;
	Integer timesToExecute;
	Double measurementLimit;
	ScalabilityFunction[] functions;
	Number[] currentParameterValues;

	public ScaleCaster(Scalable item, String name) {
		this(item, name, Integer.MAX_VALUE, Double.MAX_VALUE,
				new LinearIncrease(1));
	}

	public ScaleCaster(Scalable item, String name,
			Integer timesToExecute, Double measurementLimit,
			ScalabilityFunction... function) {
		super();
		this.item = item;
		this.name = name;
		this.timesToExecute = timesToExecute;
		this.measurementLimit = measurementLimit;
		this.functions = function;
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

	public ScalabilityFunction[] getFunctions() {
		return functions;
	}
	
	public ScalabilityFunction getFunction(int index) {
		if (index < functions.length)
			return functions[index];
		else
			return functions[functions.length-1];
	}

	public void setFunctions(ScalabilityFunction... function) {
		this.functions = function;
	}

	public void setInitialParametersValues(Number... values) {
		currentParameterValues = values;
	}

	public Number[] getCurrentParametersValues() {
		return currentParameterValues;
	}

	private void increaseParamentersValues() {
		for (int i = 0; i < currentParameterValues.length; i++)
			currentParameterValues[i] = getFunction(i).increaseParams(
					currentParameterValues[i]);
	}

	private List<Number> execute() throws Exception {
		return item.execute(currentParameterValues);
	}

	/**
	 * Executes the test, increasing the scalability parameters, up to the
	 * specified number of times ou measurement limit
	 * 
	 * @return a ScalabiltyReport of the execution
	 * @throws Exception
	 */
	public ScalabilityReport executeIncreasingParams() {
		List<Number> values;
		ScalabilityReport report = new ScalabilityReport(this.getName());
		try {
			for (int i = 0; i < this.getTimesToExecute()
					/*&& value <= this.getMeasurementLimit()*/; i++) { //TODO: consider limit again
				values = this.execute();
				List<Number> params = new ArrayList<Number>();
				Collections.addAll(params, currentParameterValues);
				ReportData data = new ReportData(params, values); //TODO: include labels
				report.put((double) i + 1, data);
				this.increaseParamentersValues();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return report;
	}

}
