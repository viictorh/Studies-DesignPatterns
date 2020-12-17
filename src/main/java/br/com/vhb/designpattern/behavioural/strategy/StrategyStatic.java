package br.com.vhb.designpattern.behavioural.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

interface ListStrategyStatic {
	default void start(StringBuilder sb) {
	}

	void addListItem(StringBuilder stringBuilder, String item);

	default void end(StringBuilder sb) {
	}
}

class MarkdownListStrategyStatic implements ListStrategyStatic {
	@Override
	public void addListItem(StringBuilder stringBuilder, String item) {
		stringBuilder.append(" * ").append(item).append(System.lineSeparator());
	}
}

class HtmlListStrategyStatic implements ListStrategyStatic {
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

class TextProcessorStatic<LS extends ListStrategyStatic> {
	private StringBuilder sb = new StringBuilder();
	// cannot do this
	// private LS listStrategy = new LS();
	private LS listStrategy;

	public TextProcessorStatic(Supplier<? extends LS> ctor) {
		listStrategy = ctor.get();
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

public class StrategyStatic {
	public static void main(String[] args) {
		TextProcessorStatic<MarkdownListStrategyStatic> tp = new TextProcessorStatic<>(MarkdownListStrategyStatic::new);
		tp.appendList(Arrays.asList("liberte", "egalite", "fraternite"));
		System.out.println(tp);

		TextProcessorStatic<HtmlListStrategyStatic> tp2 = new TextProcessorStatic<>(HtmlListStrategyStatic::new);
		tp2.appendList(Arrays.asList("inheritance", "encapsulation", "polymorphism"));
		System.out.println(tp2);
	}
}
