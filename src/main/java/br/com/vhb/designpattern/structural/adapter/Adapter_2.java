package br.com.vhb.designpattern.structural.adapter;

class TomadaDeDoisPinos {
	public void ligarNaTomadaDeDoisPinos() {
		System.out.println("Ligado na Tomada de Dois Pinos");
	}
}

class TomadaDeTresPinos {
	public void ligarNaTomadaDeTresPinos() {
		System.out.println("Ligado na Tomada de Tres Pinos");
	}
}

class AdapterTomada extends TomadaDeDoisPinos {
	private TomadaDeTresPinos tomadaDeTresPinos;

	public AdapterTomada(TomadaDeTresPinos tomadaDeTresPinos) {
		this.tomadaDeTresPinos = tomadaDeTresPinos;
	}

	public void ligarNaTomadaDeDoisPinos() {
		tomadaDeTresPinos.ligarNaTomadaDeTresPinos();
	}
}

public class Adapter_2 {
	public static void main(String args[]) {
		TomadaDeTresPinos t3 = new TomadaDeTresPinos();

		AdapterTomada a = new AdapterTomada(t3);
		a.ligarNaTomadaDeDoisPinos();
	}
}
