package eu.choreos.vv;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import eu.choreos.vv.annotations.ScalabilityTest;
import eu.choreos.vv.annotations.Scale;
import eu.choreos.vv.increasefunctions.ScalabilityFunction;

public class ScalabilityTestMethod {

	private Method method;
	private String methodName;
	private Object scalabilityTestObject;
	private ScalabilityFunction function;
	private int timesToRun;
	private double returnLimit;
	
	public ScalabilityTestMethod(ScalabilityFunction scalabilityFunction, int numberOfTimes, double returnLimit, Object scalabilityTests, String methodName) throws NoSuchMethodException {
		init(scalabilityTests, methodName);
		this.function = scalabilityFunction;
		this.timesToRun = numberOfTimes;
		this.returnLimit = returnLimit;
	}
	
	public ScalabilityTestMethod(Object scalabilityTests, String methodName) throws NoSuchMethodException, InstantiationException, IllegalAccessException {
		init(scalabilityTests, methodName);
		verifyIfMethodHasScalabilityTestAnnotation();
		this.function = getScalabilityFunctionFromMethod();
		this.timesToRun = getNumberOfTimesToExecuteFromMethod();
		this.returnLimit = getMaxMeasurementPermitteddFromMethod();
	}
	
	private void init(Object scalabilityTests, String methodName) throws NoSuchMethodException {
		this.methodName = methodName; 
		this.scalabilityTestObject = scalabilityTests;
		this.method = searchScalabilityTestMethod();
	}
	
	private Annotation[] getParameterAnnotations(int index) {
		return method.getParameterAnnotations()[index];
	}

	public Number invoke(Object[] actualParams) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		return (Number) method.invoke(scalabilityTestObject, actualParams);
	}

	public ScalabilityFunction getScalabilityFunction() {
		return function;
	}
	
	private ScalabilityFunction getScalabilityFunctionFromMethod() throws InstantiationException,
			IllegalAccessException {
		ScalabilityTest scalabilityTest = method.getAnnotation(ScalabilityTest.class);
		Class<? extends ScalabilityFunction> functionClass = scalabilityTest.scalabilityFunction();
		ScalabilityFunction function = functionClass.newInstance();
		return function;
	}
	
	public int getNumberOfTimesToExecute() {
		return timesToRun;
	}
	
	private int getNumberOfTimesToExecuteFromMethod() {
		ScalabilityTest scalabilityTest = method.getAnnotation(ScalabilityTest.class);
		return scalabilityTest.maxIncreaseTimes();
	}
	
	public double getMaxMeasurementPermitted() {
		return returnLimit;
	}
	
	private double getMaxMeasurementPermitteddFromMethod() {
		ScalabilityTest scalabilityTest = method.getAnnotation(ScalabilityTest.class);
		return scalabilityTest.maxMeasurement();
	}

	private Method searchScalabilityTestMethod() throws NoSuchMethodException {
		Class<? extends Object> scalabilityTestingClass = scalabilityTestObject.getClass();
		Method scalabilityTestingMethod = getMethodWithMethodName(scalabilityTestingClass);
		return scalabilityTestingMethod;
	}

	private Method getMethodWithMethodName(Class<? extends Object> scalabilityTestingClass) throws NoSuchMethodException {
		for (Method method : scalabilityTestingClass.getDeclaredMethods()) {
			if (methodIsEqualToMethodName(method))
				return method;
		}
		throw new NoSuchMethodException("Method " + methodName + " does not exist");
	}

	private Boolean methodIsEqualToMethodName(Method method) {
		return method.getName().equals(methodName);
	}

	private void verifyIfMethodHasScalabilityTestAnnotation()
			throws NoSuchMethodException {
		if (!methodHasScalabilityTestAnnotation())
			throw new NoSuchMethodException("Method " + methodName + " without annotation ScalabilityTest");
	}
	
	private boolean methodHasScalabilityTestAnnotation() {
		return this.method.isAnnotationPresent(ScalabilityTest.class);
	}
	
	public int getChartDomainParamIndex() {
		Annotation[][] annotations = method.getParameterAnnotations();
		for (int i = 0; i < annotations.length; i++) {
			Annotation[] parameterAnnotations = annotations[i];
			for (Annotation annotation: parameterAnnotations)
				if ( (annotation instanceof Scale) && ((Scale)annotation).chartDomain() )
					return i;
		}
		return -1;
	}
	
	public String getName() {
		return methodName;
	}

	public void increaseParametersValue(Object[] initialParameters, Object[] currentParameters) {
		for (int i = 0; i < initialParameters.length; i++)
			if (parameterHasScaleAnnotation(getParameterAnnotations(i)))
				currentParameters[i] = function.increaseParams((Integer)currentParameters[i], (Integer)initialParameters[i]);
	}
	
	private boolean parameterHasScaleAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation instanceof Scale)
				return true;
		}
		return false;
	}
	
}
