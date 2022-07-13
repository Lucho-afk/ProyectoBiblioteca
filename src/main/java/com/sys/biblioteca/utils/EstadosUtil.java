package com.sys.biblioteca.utils;

public class EstadosUtil {

	private int disponible;
	private int prestado;
	private int retrasado;
	private int reparacion;

	public EstadosUtil(int disponible, int prestado, int retrasado, int reparacion) {
		this.disponible = disponible;
		this.prestado = prestado;
		this.retrasado = retrasado;
		this.reparacion = reparacion;
	}

	public int getDisponible() {
		return disponible;
	}

	public void setDisponible(int disponible) {
		this.disponible = disponible;
	}

	public int getPrestado() {
		return prestado;
	}

	public void setPrestado(int prestado) {
		this.prestado = prestado;
	}

	public int getRetrasado() {
		return retrasado;
	}

	public void setRetrasado(int retrasado) {
		this.retrasado = retrasado;
	}

	public int getReparacion() {
		return reparacion;
	}

	public void setReparacion(int reparacion) {
		this.reparacion = reparacion;
	}

	@Override
	public String toString() {
		return "EstadosUtil [disponible=" + disponible + ", prestado=" + prestado + ", retrasado=" + retrasado
				+ ", reparacion=" + reparacion + "]";
	}
}
