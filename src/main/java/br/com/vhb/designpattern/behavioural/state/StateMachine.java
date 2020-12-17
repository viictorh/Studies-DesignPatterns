package br.com.vhb.designpattern.behavioural.state;

import static java.util.Arrays.asList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;

/**
 * Este exemplo é igual ao StateMachineSpring, porém sem o framework Spring.
 * 
 * @author victor
 *
 */

enum State {
	OFF_HOOK, // starting
	ON_HOOK, // terminal
	CONNECTING, CONNECTED, ON_HOLD
}

enum Trigger {
	CALL_DIALED, HUNG_UP, CALL_CONNECTED, PLACED_ON_HOLD, TAKEN_OFF_HOLD, LEFT_MESSAGE, STOP_USING_PHONE
}

public class StateMachine {

	private static Map<State, List<Pair<Trigger, State>>> rules = new HashMap<>();
	private static State currentState = State.OFF_HOOK;
	private static State exiteState = State.ON_HOOK;
	static {
		rules.put(State.OFF_HOOK, asList(new Pair<>(Trigger.CALL_DIALED, State.CONNECTING),
				new Pair<>(Trigger.STOP_USING_PHONE, State.ON_HOOK)));
		rules.put(State.CONNECTING, asList(new Pair<>(Trigger.HUNG_UP, State.OFF_HOOK),
				new Pair<>(Trigger.CALL_CONNECTED, State.CONNECTED)));
		rules.put(State.CONNECTED, asList(new Pair<>(Trigger.LEFT_MESSAGE, State.OFF_HOOK),
				new Pair<>(Trigger.HUNG_UP, State.OFF_HOOK), new Pair<>(Trigger.PLACED_ON_HOLD, State.ON_HOLD)));
		rules.put(State.ON_HOLD, asList(new Pair<>(Trigger.TAKEN_OFF_HOLD, State.CONNECTED),
				new Pair<>(Trigger.HUNG_UP, State.OFF_HOOK)));
	}

	public static void main(String[] args) {
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.println("The phone is currently: " + currentState);
			System.out.println("Selecte a trigger:");
			for (int i = 0; i < rules.get(currentState).size(); i++) {
				Trigger trigger = rules.get(currentState).get(i).getValue0();
				System.out.println("" + i + ". " + trigger);
			}
			boolean parseOk;
			int choice = 0;
			do {
				try {
					System.out.println("Please enter your choise:");
					choice = Integer.parseInt(console.readLine());
					parseOk = choice >= 0 && choice < rules.get(currentState).size();
				} catch (Exception e) {
					parseOk = false;
				}
			} while (!parseOk);

			currentState = rules.get(currentState).get(choice).getValue1();
			if (currentState == exiteState)
				break;
		}

		System.out.println("And we're done");
	}
}
