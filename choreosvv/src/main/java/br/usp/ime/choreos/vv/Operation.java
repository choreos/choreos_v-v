package br.usp.ime.choreos.vv;

public class Operation {

	private String name;
	private int numberOfParameters;

	public Operation() {
	}

	public Operation(String name, int numberOfParameters) {
		this.name = name;
		this.numberOfParameters = numberOfParameters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfParameters() {
		return numberOfParameters;
	}

	public void setNumberOfParameters(int numberOfParameters) {
		this.numberOfParameters = numberOfParameters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + numberOfParameters;
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
		Operation other = (Operation) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (numberOfParameters != other.numberOfParameters)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Operation [name=" + name + ", numberOfParameters="
				+ numberOfParameters + "]";
	}
	
	
}
