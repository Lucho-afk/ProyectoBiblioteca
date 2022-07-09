package com.sys.biblioteca.utils;

public class LibroModeloDeMuestreo {

	private String titulo;
	private String autor;
	private String genero;
	private String unidades;
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getUnidades() {
		return unidades;
	}
	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}
	public LibroModeloDeMuestreo(String titulo, String autor, String genero, String unidades) {
		this.titulo = titulo;
		this.autor = autor;
		this.genero = genero;
		this.unidades = unidades;
	}
	
}
