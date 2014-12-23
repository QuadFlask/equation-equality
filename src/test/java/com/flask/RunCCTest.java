package com.flask;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import fri.patterns.interpreter.parsergenerator.Parser;
import fri.patterns.interpreter.parsergenerator.Token;
import fri.patterns.interpreter.parsergenerator.builder.SerializedParser;
import fri.patterns.interpreter.parsergenerator.examples.Calculator;
import fri.patterns.interpreter.parsergenerator.semantics.ReflectSemantic;

public class RunCCTest extends ReflectSemantic {
	private static String[][] rules = {
		    { "EXPRESSION",   "TERM" },
	        { "EXPRESSION",   "EXPRESSION", "'+'", "TERM" },
	        { "EXPRESSION",   "EXPRESSION", "'-'", "TERM" },
	        { "TERM",   "FACTOR", },
	        { "TERM",   "TERM", "'*'", "FACTOR" },
	        { "TERM",   "TERM", "'/'", "FACTOR" },
	        { "FACTOR",   "`number`", },
	        { "FACTOR",   "'-'", "FACTOR" },
	        { "FACTOR",   "'('", "EXPRESSION", "')'" },
	        { Token.IGNORED,   "`whitespaces`" },
		
	};

	public Object EXPRESSION(Object TERM) {
		return TERM;
	}

	public Object EXPRESSION(Object EXPRESSION, Object operator, Object TERM) {
		if (operator.equals("+"))
			return new Double(((Double) EXPRESSION).doubleValue() + ((Double) TERM).doubleValue());
		return new Double(((Double) EXPRESSION).doubleValue() - ((Double) TERM).doubleValue());
	}

	public Object TERM(Object FACTOR) {
		return FACTOR;
	}

	public Object TERM(Object TERM, Object operator, Object FACTOR) {
		if (operator.equals("*"))
			return new Double(((Double) TERM).doubleValue() * ((Double) FACTOR).doubleValue());
		return new Double(((Double) TERM).doubleValue() / ((Double) FACTOR).doubleValue());
	}

	public Object FACTOR(Object number) {
		return Double.valueOf((String) number);
	}

	public Object FACTOR(Object minus, Object FACTOR) {
		return new Double(-((Double) FACTOR).doubleValue());
	}

	public Object FACTOR(Object leftParenthesis, Object EXPRESSION, Object rightParenthesis) {
		return EXPRESSION;
	}

	@Test
	public void testName() throws Exception {
		String input = "(4+2.3) *(2 - -6) + 3*2"; // define some arithmetic expressions on
								// commandline
		Parser parser = new SerializedParser().get(rules, "Calculator"); // allocates
																			// a
																			// default
																			// lexer
		boolean ok = parser.parse(input, new Calculator());
		System.out.println(parser.getResult());
	}

}
