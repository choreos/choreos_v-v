package eu.choreos.vv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class ScalabilityReport extends HashMap<Number, List<Number>> {

	private static final long serialVersionUID = -2993273004741518768L;
	private String methodName;

	public ScalabilityReport() {
	}

	public ScalabilityReport(String methodName) {
		this.methodName = methodName;
	}

	public Comparable<String> getName() {
		return methodName;
	}

	public void setName(String name) {
		methodName = name;
	}

}
