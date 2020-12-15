package br.com.vhb.designpattern.strategy;

import java.util.Arrays;
import java.util.List;

enum Outputformat {

	MARKDOWN {
		@Override
		ListStrategy getStrategy() {
			return new MarkdownListStrategy();
		}
	},
	HTML {
		@Override
		ListStrategy getStrategy() {
			return new HtmlListStrategy();
		}
	};
	abstract ListStrategy getStrategy();

}

interface ListStrategy {
	default void start(StringBuilder sb) {
	}

	void addListItem(StringBuilder stringBuilder, String item);

	default void end(StringBuilder sb) {
	}
}

class MarkdownListStrategy implements ListStrategy {
	@Override
	public void addListItem(StringBuilder stringBuilder, String item) {
		stringBuilder.append(" * ").append(item).append(System.lineSeparator());
	}
}

class HtmlListStrategy implements ListStrategy {
	@Override
	public void start(StringBuilder sb) {
		sb.append("<ul>").append(System.lineSeparator());
	}

	@Override
	public void addListItem(StringBuilder stringBuilder, String item) {
		stringBuilder.append("  <li>").append(item).append("</li>").append(System.lineSeparator());
	}

	@Override
	public void end(StringBuilder sb) {
		sb.append("</ul>").append(System.lineSeparator());
	}
}

class TextProcessor {
	private StringBuilder sb = new StringBuilder();

	private ListStrategy listStrategy;

	public TextProcessor(Outputformat format) {
		setListStrategy(format);
	}

	private void setListStrategy(Outputformat format) {
		listStrategy = format.getStrategy();
	}

	// the skeleton algorithm is here
	public void appendList(List<String> items) {
		listStrategy.start(sb);
		for (String item : items)
			listStrategy.addListItem(sb, item);
		listStrategy.end(sb);
	}

	public void clear() {
		sb.setLength(0);
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}

public class StrategyDinamic {
	public static void main(String[] args) {
		TextProcessor tp = new TextProcessor(Outputformat.MARKDOWN);
		tp.appendList(Arrays.asList("liberte", "egalite", "fraternite"));
		System.out.println(tp);

		TextProcessor tp2 = new TextProcessor(Outputformat.HTML);
		tp2.appendList(Arrays.asList("inheritance", "encapsulation", "polymorphism"));
		System.out.println(tp2);
	}
}
