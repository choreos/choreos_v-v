package eu.choreos.vv;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import eu.choreos.vv.increasefunctions.ScalabilityFunction;

public class ScalabilityTesting {

	public static ScalabilityReport run(ScalabilityFunction scalabilityfunction, int numberOfTimes, double measurementLimit, Object scalabilityTests, String methodName, Object... params)
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		ScalabilityTestMethod method = new ScalabilityTestMethod(scalabilityfunction, numberOfTimes, measurementLimit, scalabilityTests, methodName);
		return executeIncreasingParams(method, params);
	}
	
	public static ScalabilityReport run(ScalabilityFunction scalabilityfunction, int numberOfTimes, Object scalabilityTests, String methodName, Object... params)
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		return run(scalabilityfunction, numberOfTimes, Double.MAX_VALUE, scalabilityTests, methodName, params);
	}
	
	public static ScalabilityReport run(ScalabilityFunction scalabilityfunction, double measurementLimit, Object scalabilityTests, String methodName, Object... params)
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		return run(scalabilityfunction, Integer.MAX_VALUE, measurementLimit, scalabilityTests, methodName, params);
	}
	
	public static ScalabilityReport run(Object scalabilityTests, String methodName, Object... params)
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		ScalabilityTestMethod method = new ScalabilityTestMethod(scalabilityTests, methodName);
		return executeIncreasingParams(method, params);
	}
	
	public static ScalabilityReport executeIncreasingParams(ScalabilityTestMethod method, Object... params)
			throws IllegalAccessException, InvocationTargetException, InstantiationException {
		Object[] currentParams = Arrays.copyOf(params, params.length);
		double value = 0.0;
		double key = 0.0;
		ScalabilityReport report = new ScalabilityReport(method.getName());
		int chartDomainParameterIndex = method.getChartDomainParamIndex();
		for (int i = 0; i < method.getNumberOfTimesToExecute() && value <= method.getMaxMeasurementPermitted(); i++) {
			key = getChartKey(currentParams, chartDomainParameterIndex, i);
			value = invokeMethod(method, currentParams);
			report.put(key, value);
			method.increaseParametersValue(params, currentParams);
			
		}
		return report;
	}

	private static double getChartKey(Object[] actualParams,
			int chartDomainParamIndex, int noParam) {
		if (chartDomainParamIndex == -1)
			return noParam;
		else
			return ( (Number) actualParams[chartDomainParamIndex]).doubleValue();
	}

	private static Double invokeMethod(ScalabilityTestMethod method, Object[] actualParams) throws IllegalAccessException, InvocationTargetException {
		Number value = (Number) method.invoke(actualParams);
		return value.doubleValue();
	}

}
