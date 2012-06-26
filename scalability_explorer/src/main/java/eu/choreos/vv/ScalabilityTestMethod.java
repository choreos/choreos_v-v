package eu.choreos.vv;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import eu.choreos.vv.annotations.ScalabilityTest;
import eu.choreos.vv.increasefunctions.ScalabilityFunction;

public class ScalabilityTestMethod {

	private Method method;
	private Object scalabilityTestObject;

	public ScalabilityTestMethod(Object scalabilityTests, String methodName) throws NoSuchMethodException {
		method = searchScalabilityTestMethod(methodName, scalabilityTests);
		scalabilityTestObject = scalabilityTests;
	}
	
	public Annotation[][] getParameterAnnotations() {
		return method.getParameterAnnotations();
	}

	public Number invoke(Object[] actualParams) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		return (Number) method.invoke(scalabilityTestObject, actualParams);
	}

	public ScalabilityFunction getScalabilityFunctionObject() throws InstantiationException,
			IllegalAccessException {
		ScalabilityTest scalabilityTest = method.getAnnotation(ScalabilityTest.class);
		Class<? extends ScalabilityFunction> functionClass = scalabilityTest.scalabilityFunction();
		ScalabilityFunction function = functionClass.newInstance();
		return function;
	}
	
	public int getNumberOfTimesToExecute() {
		ScalabilityTest scalabilityTest = method.getAnnotation(ScalabilityTest.class);
		return scalabilityTest.maxIncreaseTimes();
	}
	
	public double getMaxMeasurementPermitted() {
		ScalabilityTest scalabilityTest = method.getAnnotation(ScalabilityTest.class);
		return scalabilityTest.maxMeasurement();
	}

	private Method searchScalabilityTestMethod(String methodName, Object scalabilityTests) throws NoSuchMethodException {
		Class<? extends Object> scalabilityTestingClass = scalabilityTests.getClass();
		Method scalabilityTestingMethod = getMethodWithMethodName(methodName, scalabilityTestingClass);
		verifyIfMethodExistsAndHasAnnotation(methodName, scalabilityTestingMethod);
		return scalabilityTestingMethod;
	}

	private Method getMethodWithMethodName(String methodName, Class<? extends Object> scalabilityTestingClass) {
		for (Method method : scalabilityTestingClass.getDeclaredMethods()) {
			if (methodIsEqualToMethodName(method, methodName))
				return method;
		}
		return null;
	}

	private void verifyIfMethodExistsAndHasAnnotation(String methodName, Method scalabilityTestingMethod)
			throws NoSuchMethodException {
		verifyIfMethodExists(methodName, scalabilityTestingMethod);
		verifyIfMethodHasScalabilityTestAnnotation(methodName, scalabilityTestingMethod);
	}

	private Boolean methodIsEqualToMethodName(Method method, String methodName) {
		return method.getName().equals(methodName);
	}

	private void verifyIfMethodExists(String methodName, Method scalabilityTestingMethod) throws NoSuchMethodException {
		if (scalabilityTestingMethod == null)
			throw new NoSuchMethodException("Method " + methodName + " does not exist");
	}

	private void verifyIfMethodHasScalabilityTestAnnotation(String methodName, Method scalabilityTestingMethod)
			throws NoSuchMethodException {
		if (!scalabilityTestingMethod.isAnnotationPresent(ScalabilityTest.class))
			throw new NoSuchMethodException("Method " + methodName + "without annotation ScalabilityTest");
	}
}
