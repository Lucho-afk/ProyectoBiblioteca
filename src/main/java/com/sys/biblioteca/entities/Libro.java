package com.sys.biblioteca.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "libros")
public class Libro {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String titulo;

	@Column
	private String genero;

	@Column
	private String editorial;

	@Column
	private int anio;

	@Column(nullable = false)
	private String autor;

	@Column(name = "nacionalidad_autor")
	private String nacionalidadAutor;

	@Column(name = "f_nacimiento_autor")
	private LocalDate fechaNaciomientoAutor;

	@OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
	private Set<Copia> lstCopias = new HashSet<Copia>();

	@Column
	@JsonIgnore
	private boolean activo;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getNacionalidadAutor() {
		return nacionalidadAutor;
	}

	public void setNacionalidadAutor(String nacionalidadAutor) {
		this.nacionalidadAutor = nacionalidadAutor;
	}

	public LocalDate getFechaNaciomientoAutor() {
		return fechaNaciomientoAutor;
	}

	public void setFechaNaciomientoAutor(LocalDate fechaNaciomientoAutor) {
		this.fechaNaciomientoAutor = fechaNaciomientoAutor;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}


	public Set<Copia> getLstCopias() {
		return lstCopias;
	}

	public void setLstCopias(Set<Copia> lstCopias) {
		this.lstCopias = lstCopias;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	
}
