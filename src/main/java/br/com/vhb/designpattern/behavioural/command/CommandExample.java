package br.com.vhb.designpattern.behavioural.command;

import java.util.ArrayList;
import java.util.List;

interface Command1 {
	void execute();
}

class DomesticEngineer implements Command1 {
	public void execute() {
		System.out.println("take out the trash");
	}
}

class Politician implements Command1 {
	public void execute() {
		System.out.println("take money from the rich, take votes from the poor");
	}
}

class Programmer implements Command1 {
	public void execute() {
		System.out.println("sell the bugs, charge extra for the fixes");
	}
}

public class CommandExample {
	
	public static List<Command1> produceRequests() {
		List<Command1> queue = new ArrayList<>();
		queue.add(new DomesticEngineer());
		queue.add(new Politician());
		queue.add(new Programmer());
		return queue;
	}

	public static void workOffRequests(List<?> queue) {
		for (Object command : queue) {
			((Command1) command).execute();
		}
	}

	public static void main(String[] args) {
		List<?> queue = produceRequests();
		workOffRequests(queue);
	}
}
