package com.flask;

import java.util.Arrays;
import java.util.Set;

import com.google.common.collect.Sets;

public class OpWrapSet extends OpWrap {
	private final String operator;
	private Set<OpWrap> operendSet = Sets.newConcurrentHashSet();

	public OpWrapSet(String operend, OpWrap... operends) {
		this.operator = "+";
		operendSet.addAll(Arrays.asList(operends));
	}

	public void merge(OpWrapSet opWrapSet) {
		if (opWrapSet.operator.equals(operator))
			operendSet.addAll(opWrapSet.operendSet);
	}

	public OpWrap getOperend() {
		return operendSet.iterator().next();
	}

	public OpWrapSet(String operator, String... operends) {
		this.operator = operator;
		for (int i = 0; i < operends.length; i++)
			operendSet.add(new OpWrap(operends[i]));
	}

	public OpWrapSet(String operator, OpWrapSet... opWrapSets) {
		this.operator = operator;
		operendSet.addAll(Arrays.asList(opWrapSets));
	}

	public OpWrapSet put(OpWrapSet opWrapSet) {
		operendSet.add(opWrapSet);
		return this;
	}

	public OpWrapSet put(String operend) {
		operendSet.add(new OpWrap(operend));
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((operendSet == null) ? 0 : operendSet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			if (obj.getClass() == OpWrap.class) {
				OpWrap other = (OpWrap) obj;
				return operendSet.size() == 1 && operendSet.iterator().next().equals(other);
			} else
				return false;
		OpWrapSet other = (OpWrapSet) obj;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (operendSet == null) {
			if (other.operendSet != null)
				return false;
		} else if (!operendSet.equals(other.operendSet))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return operator + operendSet;
	}

}