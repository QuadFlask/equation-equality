package com.flask;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class EquationTest {
	@Test
	public void different_position_sholud_equal() {
		OpWrapSet opWrapSet = new OpWrapSet("+", "a", "b");
		OpWrapSet opWrapSet1 = new OpWrapSet("+", "b", "a");

		assertEquals(opWrapSet, opWrapSet1);
	}

	@Test
	public void different_operend_should_not_equal() {
		OpWrapSet opWrapSet = new OpWrapSet("+", "a", "b");
		OpWrapSet opWrapSet1 = new OpWrapSet("+", "b", "a", "c");

		assertNotEquals(opWrapSet, opWrapSet1);
	}

	@Test
	public void different_operator_should_not_equal() {
		OpWrapSet opWrapSet = new OpWrapSet("+", "a", "b");
		OpWrapSet opWrapSet1 = new OpWrapSet("*", "a", "b");

		assertNotEquals(opWrapSet, opWrapSet1);
	}

	@Test
	public void opwrapset_should_take_other_operend() {
		OpWrapSet opWrapSet = new OpWrapSet("+", "a", "b").put("c");
		OpWrapSet opWrapSet1 = new OpWrapSet("+", "a", "b", "c");

		assertEquals(opWrapSet, opWrapSet1);
	}

	@Test
	public void opwrapset_should_init_with_other_opwrap() {
		OpWrapSet opWrapSet = new OpWrapSet("+", new OpWrap("a"), new OpWrap("b"));
		OpWrapSet opWrapSet1 = new OpWrapSet("+", "a", "b");

		assertEquals(opWrapSet, opWrapSet1);
	}

	@Test
	public void opwrapset_should_merge_if_same_operator_opwaprset() {
		OpWrapSet opWrapSet = new OpWrapSet("+", "a", "b");
		OpWrapSet opWrapSet1 = new OpWrapSet("+", "c", "d");
		opWrapSet.merge(opWrapSet1);

		OpWrapSet opWrapSet2 = new OpWrapSet("+", "a", "b", "c", "d");

		assertEquals(opWrapSet, opWrapSet2);
	}

	@Test
	public void opwrapset_should_take_other_opwrapset() {
		OpWrapSet opWrapSet = new OpWrapSet("+", "a", "b");
		opWrapSet.put(new OpWrapSet("*", "c", "d"));

		OpWrapSet opWrapSet1 = new OpWrapSet("+", "a", "b");
		opWrapSet1.put(new OpWrapSet("*", "c", "d"));

		assertEquals(opWrapSet, opWrapSet1);

		opWrapSet1.put(new OpWrapSet("*", "e", "f"));

		assertNotEquals(opWrapSet, opWrapSet1);

		System.out.println(opWrapSet1.toString());
	}

	// @Test
	// public void opwrapset_build_test() {
	// String equation = "a+b";
	// OpWrapSet opWrapSet = OpWrapSetBuilder.build(equation);
	//
	// OpWrapSet opWrapSet1 = new OpWrapSet("+", "a", "b");
	// assertEquals(opWrapSet, opWrapSet1);
	// }

	@Test
	public void buildTest_plus() {
		OpWrapSet opWrapSet = build("a+b+c");
		OpWrapSet opWrapSet1 = new OpWrapSet("+", "a", "c", "b");
		assertEquals(opWrapSet, opWrapSet1);
	}

	@Test
	public void buildTest_multiply() {
		OpWrapSet opWrapSet = build("a*b*c");
		OpWrapSet opWrapSet1 = new OpWrapSet("*", "a", "c", "b");
		assertEquals(opWrapSet, opWrapSet1);
	}

	@Test
	public void buildTest_complicated() {
		OpWrapSet opWrapSet = build("a+b*c");
		OpWrapSet opWrapSet1 = new OpWrapSet("+");
		opWrapSet1.put("a");
		opWrapSet1.put(new OpWrapSet("*", "c", "b"));
		assertEquals(opWrapSet1, opWrapSet);
	}

	private OpWrapSet build(String e) {
		Character operator = null;
		Set<OpWrap> operends = Sets.newConcurrentHashSet();

		for (int i = 0; i < e.length(); i++) {
			Character c = e.charAt(i);
			if (operators.contains(c)) {
				if (operator != null && operator != c) {
					
				}
				operator = c;
			} else {
				operends.add(new OpWrap(c.toString()));
			}
		}

		return new OpWrapSet(operator.toString(), operends);
	}

	private static final Set<Character> operators = Sets.newConcurrentHashSet();
	static {
		operators.add('+');
		operators.add('-');
		operators.add('*');
		operators.add('/');
	}
}

class OpWrapSetBuilder {
	private static final Set<Character> operators = Sets.newConcurrentHashSet();
	static {
		operators.add('+');
		operators.add('-');
		operators.add('*');
		operators.add('/');
	}

	public static OpWrapSet build(String equation) {
		List<Character> operends = Lists.newArrayList();
		char operator = '?';
		for (int i = 0; i < equation.length(); i++) {
			char c = equation.charAt(i);
			if (isOperator(c)) {
				operator = c;
			} else {
				operends.add(c);
				if (operator != '?') {

				}
			}
		}
		return null;
	}

	private static boolean isOperator(char c) {
		return operators.contains(c);
	}

}