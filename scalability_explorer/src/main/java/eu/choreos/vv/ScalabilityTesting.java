package eu.choreos.vv;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import eu.choreos.vv.annotations.Scale;
import eu.choreos.vv.increasefunctions.ScalabilityFunction;

public class ScalabilityTesting {

	private static ScalabilityFunction function;
	private static int times;
	private static ScalabilityTestMethod method;
	private static ScalabilityReport report;

	public static ScalabilityReport run(Object scalabilityTests, String methodName, Object... params)
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		method = new ScalabilityTestMethod(scalabilityTests, methodName);
		function = method.getScalabilityFunctionObject();
		times = method.getNumberOfTimesToExecute();
		report = new ScalabilityReport(methodName);
		executeIncreasingParams(scalabilityTests, params);
		return report;
	}

	public static void executeIncreasingParams(Object scalabilityTestingObject, Object... params)
			throws IllegalAccessException, InvocationTargetException, InstantiationException {
		Object[] actualParams = Arrays.copyOf(params, params.length);
		Annotation[][] parametersAnnotation = method.getParameterAnnotations();
		double value = 0.0;
		for (int i = 0; i < times; i++) {
			value = invokeMethodWithParamsUpdated(scalabilityTestingObject, actualParams);
			report.add(value);
			increaseEachParamOfParamsArray(actualParams, parametersAnnotation, params);
		}
	}

	private static Double invokeMethodWithParamsUpdated(Object scalabilityTestingObject, Object[] actualParams) throws IllegalAccessException, InvocationTargetException {
		Number value = (Number) method.invoke(actualParams);
		return value.doubleValue();
	}

	private static void increaseEachParamOfParamsArray(Object[] actualParams,
			Annotation[][] parametersAnnotation, Object... params) {
		for (int j = 0; j < params.length; j++) {
			if (parameterHasScaleAnnotation(parametersAnnotation[j]))
				actualParams[j] = function.increaseParams((Integer) actualParams[j], (Integer) params[j]);
		}
	}

	private static boolean parameterHasScaleAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation instanceof Scale)
				return true;
		}
		return false;
	}

}
