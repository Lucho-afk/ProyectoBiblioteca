
package com.sys.biblioteca.entities;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "prestamos")
public class Prestamo {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private LocalDate inicio;

	@Column
	private LocalDate fin;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "lector_id", referencedColumnName = "id", columnDefinition = "int", nullable = true)
	@JsonIgnore
	Lector lector;

	@OneToOne()
	@JoinColumn(name = "copia_id", nullable = true)
	Copia copia;

	@Column
	@JsonIgnore
	private boolean activo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getInicio() {
		return inicio;
	}

	public void setInicio(LocalDate inicio) {
		this.inicio = inicio;
	}

	public LocalDate getFin() {
		return fin;
	}

	public void setFin(LocalDate fin) {
		this.fin = fin;
	}

	public Lector getLector() {
		return lector;
	}

	public void setLector(Lector lector) {
		this.lector = lector;
	}

	public Copia getCopia() {
		return copia;
	}

	public void setCopia(Copia copia) {
		this.copia = copia;
	}

	@Override
	public String toString() {
		return "Prestamo [id=" + id + ", inicio=" + inicio + ", fin=" + fin + ", lector=" + lector + ", copia=" + copia
				+ "]";
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
