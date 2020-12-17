package br.com.vhb.designpattern.creational.builder;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

class Field {
	String type;
	String name;

	@Override
	public String toString() {
		return "public " + type + " " + name + ";";
	}
}

class Class {
	String name;
	List<Field> fields = new ArrayList<>();

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String nl = System.lineSeparator();
		sb.append("public class " + name).append(nl).append("{").append(nl);
		for (Field f : fields)
			sb.append("  " + f).append(nl);
		sb.append("}").append(nl);
		return sb.toString();
	}
}

class CodeBuilder {
	protected Class clazz;

	public CodeBuilder(String className) {
		clazz = new Class();
		clazz.name = className;
	}

	public CodeBuilder addField(String name, String type) {
		Field e = new Field();
		e.name = name;
		e.type = type;
		clazz.fields.add(e);
		return this;
	}

	@Override
	public String toString() {
		return clazz.toString();
	}
}

public class ExercicioBuilder {
	private String preprocess(String text) {
		return text.replace("\r\n", "\n").trim();
	}

	@Test
	public void emptyTest() {
		CodeBuilder cb = new CodeBuilder("Foo");
		assertEquals("public class Foo\n{\n}", preprocess(cb.toString()));
	}

	@Test
	public void personTest() {
		CodeBuilder cb = new CodeBuilder("Foo").addField("name", "String").addField("age", "int");
		assertEquals("public class Foo\n{\n" + "  public String name;\n" + "  public int age;\n}",
				preprocess(cb.toString()));
	}
}
