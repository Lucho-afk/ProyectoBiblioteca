package com.sys.biblioteca.utils;

public class EstadoUtil {

	private String disponible;
	private String prestado;
	private String retrasado;
	private String reparacion;
	public EstadoUtil(String disponible, String prestado, String retrasado, String reparacion) {
		this.disponible = disponible;
		this.prestado = prestado;
		this.retrasado = retrasado;
		this.reparacion = reparacion;
	}
	public String getDisponible() {
		return disponible;
	}
	public void setDisponible(String disponible) {
		this.disponible = disponible;
	}
	public String getPrestado() {
		return prestado;
	}
	public void setPrestado(String prestado) {
		this.prestado = prestado;
	}
	public String getRetrasado() {
		return retrasado;
	}
	public void setRetrasado(String retrasado) {
		this.retrasado = retrasado;
	}
	public String getReparacion() {
		return reparacion;
	}
	public void setReparacion(String reparacion) {
		this.reparacion = reparacion;
	}
}
