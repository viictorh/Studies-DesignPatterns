package br.com.vhb.designpattern.behavioural.state;

class LightState {
	void on(LightSwitch ls) {
		System.out.println("Light is already turned on");
	}

	void off(LightSwitch ls) {
		System.out.println("Light is already turned off");
	}
}

class LightSwitch {
	private LightState state;

	public LightSwitch() {
		this.state = new OffState();
	}

	public void setState(LightState state) {
		this.state = state;
	}

	public void off() {
		this.state.off(this);
	}

	public void on() {
		this.state.on(this);
	}
}

class OnState extends LightState {
	public OnState() {
		System.out.println("Light turned on...");
	}

	@Override
	void off(LightSwitch ls) {
		System.out.println("Turning light off...");
		ls.setState(new OffState());
	}

}

class OffState extends LightState {
	public OffState() {
		System.out.println("Light turned off...");
	}

	@Override
	void on(LightSwitch ls) {
		System.out.println("Turning light on...");
		ls.setState(new OnState());
	}

}

public class State_1 {
	public static void main(String[] args) {
		LightSwitch lightSwitch = new LightSwitch();
		lightSwitch.on();
		lightSwitch.off();
		lightSwitch.off();
	}
}
