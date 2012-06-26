package eu.choreos.vv.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import eu.choreos.vv.increasefunctions.LinearIncrease;
import eu.choreos.vv.increasefunctions.ScalabilityFunction;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScalabilityTest {
	Class<? extends ScalabilityFunction> scalabilityFunction() default LinearIncrease.class;
	int maxIncreaseTimes() default Integer.MAX_VALUE;
	double maxMeasurement() default Double.MAX_VALUE;
}
