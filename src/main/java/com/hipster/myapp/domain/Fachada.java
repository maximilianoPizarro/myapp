package com.hipster.myapp.domain;

import java.io.Serializable;

public class Fachada implements Serializable{
	
	private Prueba prueba;

	public Prueba getPrueba() {
		return prueba;
	}

	public void setPrueba(Prueba prueba) {
		this.prueba = prueba;
	}
	
	

}
