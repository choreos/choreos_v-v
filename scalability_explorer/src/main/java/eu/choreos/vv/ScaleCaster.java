package eu.choreos.vv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;

import eu.choreos.vv.data.ExperimentReport;
import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.increasefunctions.ScalabilityFunction;
import eu.choreos.vv.stop.StopCriterion;

/**
 * ScalabilityTest is the class in charge of executing a test multiple times
 * increasing the scalable parameters.
 * 
 */
public class ScaleCaster {

	RandomData keyGenerator;
	Scalable item;
	String name;
	StopCriterion criteria;
	Map<String, ValueAndFunction> currentParameterValues;
	List<ValueAndFunction> values;


	public ScaleCaster(Scalable item, String name, StopCriterion criteria) {
		super();
		this.item = item;
		this.name = name;
		this.criteria = criteria;
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

	public String addInitialParameterValue(Integer value, ScalabilityFunction function) {
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
			Integer value = pair.value;
			ScalabilityFunction function = pair.function;
			pair.value = function.increaseParams(value);
		}
	}

	private ReportData executeItem() throws Exception {
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
		int i = 1;
		ReportData data;
		ExperimentReport report = new ExperimentReport(this.getName());
		try {
			while(!criteria.stop(report)) {
				data = this.executeItem();
				List<Number> params = new ArrayList<Number>();
				for(ValueAndFunction pair: this.values) {
					params.add(pair.value);
				}
				data.setParameters(params);
				report.put( i++, data);
				this.increaseParamentersValues();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return report;
	}

}

class ValueAndFunction {
	Integer value;
	ScalabilityFunction function;
	
	public ValueAndFunction(Integer value, ScalabilityFunction function) {
		this.value = value;
		this.function = function;
	}
	
}