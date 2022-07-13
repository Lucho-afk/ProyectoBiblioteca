package com.sys.biblioteca.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "lectores")
public class Lector {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String nombre;

	@Column
	private String telefono;

	@Column
	private String direccion;

	@OneToOne
	@JoinColumn(name = "multa_id", nullable = true, unique = false)
	private Multa multa;

	@Column
	@JsonIgnore
	private boolean activo;

	@OneToMany(mappedBy = "lector", cascade = CascadeType.ALL)
	List<Prestamo> lstPrestamos = new ArrayList<>();

	public List<Prestamo> getLstPrestamos() {
		return lstPrestamos;
	}

	public void setLstPrestamos(List<Prestamo> lstPrestamos) {
		this.lstPrestamos = lstPrestamos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Multa getMulta() {
		return multa;
	}

	public void setMulta(Multa multa) {
		this.multa = multa;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
