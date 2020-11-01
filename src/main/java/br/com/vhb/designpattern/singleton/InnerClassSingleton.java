package br.com.vhb.designpattern.singleton;

public class InnerClassSingleton {

	private InnerClassSingleton() {
	}

	public InnerClassSingleton getInstance() {
		return Implementation.INSTANCE;
	}

	/**
	 * Classe só será criada quando houver a solicitação do método getInstance()
	 */
	private static class Implementation {
		private static final InnerClassSingleton INSTANCE = new InnerClassSingleton();
	}

}
