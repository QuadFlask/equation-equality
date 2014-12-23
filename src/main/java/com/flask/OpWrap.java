package com.flask;

public class OpWrap {
	private String value;

	public OpWrap() {
	}

	public OpWrap(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			if (obj.getClass() == OpWrapSet.class) {
				OpWrapSet other = (OpWrapSet) obj;
				return equals(other.getOperend());
			} else
				return false;
		OpWrap other = (OpWrap) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return value;
	}
}