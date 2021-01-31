package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

public enum Curso {
	
	PRIMERO("1º ESO"), 
	SEGUNDO("2º ESO"), 
	TERCERO("3º ESO"), 
	CUARTO("4º ESO");

	private String aMostrar;
	
	private Curso(String aMostrar) {
		this.aMostrar = aMostrar;
	}

	public String toString() {
		return aMostrar;
	}
	
}
