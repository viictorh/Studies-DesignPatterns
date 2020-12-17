package br.com.vhb.designpattern.structural.composite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.junit.internal.runners.JUnit38ClassRunner;
import static org.junit.Assert.assertEquals;

interface ValueContainer extends Iterable<Integer> {

	default int count() {
		Iterable<Integer> iterable = () -> this.iterator();
		Stream<Integer> stream = StreamSupport.stream(iterable.spliterator(), false);
		return stream.mapToInt(c -> c).sum();
	}
}

class SingleValue implements ValueContainer {
	public int value;

	// please leave this constructor as-is
	public SingleValue(int value) {
		this.value = value;
	}

	@Override
	public Iterator<Integer> iterator() {
		return Collections.singleton(this.value).iterator();
	}

	@Override
	public Spliterator<Integer> spliterator() {
		return Collections.singleton(this.value).spliterator();
	}
}

class ManyValues extends ArrayList<Integer> implements ValueContainer {
}

class MyList extends ArrayList<ValueContainer> {
	// please leave this constructor as-is
	public MyList(Collection<? extends ValueContainer> c) {
		super(c);
	}

	public int sum() {
		int total = 0;
		Iterator<ValueContainer> it = this.iterator();
		while (it.hasNext()) {
			ValueContainer next = it.next();
			total += next.count();
		}
		return total;
	}

}

public class Composite_exercicio {
	@Test
	public void test() {
		SingleValue singleValue = new SingleValue(11);
		ManyValues otherValues = new ManyValues();
		otherValues.add(22);
		otherValues.add(33);
		assertEquals(66, new MyList(Arrays.asList(singleValue, otherValues)).sum());
	}
}