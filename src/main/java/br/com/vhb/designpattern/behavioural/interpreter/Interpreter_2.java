package br.com.vhb.designpattern.behavioural.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Context {

	private static Map<String, List<Row>> tables = new HashMap<>();

	static {
		List<Row> list = new ArrayList<>();
		list.add(new Row("John", "Doe"));
		list.add(new Row("Jan", "Kowalski"));
		list.add(new Row("Dominic", "Doom"));

		tables.put("people", list);
	}

	private String table;
	private String column;

	/**
	 * Index of column to be shown in result. Calculated in
	 * {@link #setColumnMapper()}
	 */
	private int colIndex = -1;

	/**
	 * Default setup, used for clearing the context for next queries. See
	 * {@link Context#clear()}
	 */
	private static final Predicate<String> matchAnyString = s -> s.length() > 0;
	private static final Function<String, Stream<? extends String>> matchAllColumns = Stream::of;
	/**
	 * Varies based on setup in subclasses of {@link Expression}
	 */
	private Predicate<String> whereFilter = matchAnyString;
	private Function<String, Stream<? extends String>> columnMapper = matchAllColumns;

	void setColumn(String column) {
		this.column = column;
		setColumnMapper();
	}

	void setTable(String table) {
		this.table = table;
	}

	void setFilter(Predicate<String> filter) {
		whereFilter = filter;
	}

	/**
	 * Clears the context to defaults. No filters, match all columns.
	 */
	void clear() {
		column = "";
		columnMapper = matchAllColumns;
		whereFilter = matchAnyString;
	}

	List<String> search() {

		List<String> result = tables.entrySet().stream().filter(entry -> entry.getKey().equalsIgnoreCase(table))
				.flatMap(entry -> Stream.of(entry.getValue())).flatMap(Collection::stream).map(Row::toString)
				.flatMap(columnMapper).filter(whereFilter).collect(Collectors.toList());

		clear();

		return result;
	}

	/**
	 * Sets column mapper based on {@link #column} attribute. Note: If column is
	 * unknown, will remain to look for all columns.
	 */
	private void setColumnMapper() {
		switch (column) {
		case "*":
			colIndex = -1;
			break;
		case "name":
			colIndex = 0;
			break;
		case "surname":
			colIndex = 1;
			break;
		}
		if (colIndex != -1) {
			columnMapper = s -> Stream.of(s.split(" ")[colIndex]);
		}
	}
}

class Row {

	private String name;
	private String surname;

	Row(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}

	@Override
	public String toString() {
		return name + " " + surname;
	}
}

interface Expression {
	List<String> interpret(Context ctx);
}

class Select implements Expression {

	private String column;
	private From from;

	Select(String column, From from) {
		this.column = column;
		this.from = from;
	}

	@Override
	public List<String> interpret(Context ctx) {
		ctx.setColumn(column);
		return from.interpret(ctx);
	}
}

class From implements Expression {

	private String table;
	private Where where;

	From(String table) {
		this.table = table;
	}

	From(String table, Where where) {
		this.table = table;
		this.where = where;
	}

	@Override
	public List<String> interpret(Context ctx) {
		ctx.setTable(table);
		if (where == null) {
			return ctx.search();
		}
		return where.interpret(ctx);
	}
}

class Where implements Expression {

	private Predicate<String> filter;

	Where(Predicate<String> filter) {
		this.filter = filter;
	}

	@Override
	public List<String> interpret(Context ctx) {
		ctx.setFilter(filter);
		return ctx.search();
	}
}

public class Interpreter_2 {
	public static void main(String[] args) {

		Expression query = new Select("name", new From("people"));
		Context ctx = new Context();
		List<String> result = query.interpret(ctx);
		System.out.println(result);

		Expression query2 = new Select("*", new From("people"));
		List<String> result2 = query2.interpret(ctx);
		System.out.println(result2);

		Expression query3 = new Select("name",
				new From("people", new Where(name -> name.toLowerCase().startsWith("d"))));
		List<String> result3 = query3.interpret(ctx);
		System.out.println(result3);
	}
}
