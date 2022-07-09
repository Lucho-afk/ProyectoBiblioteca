package com.sys.biblioteca.service;

import org.springframework.stereotype.Service;
import com.sys.biblioteca.entities.Lector;
import com.sys.biblioteca.entities.Prestamo;
import com.sys.biblioteca.genericsABM.GenericService;

@Service
public interface LectorService  extends GenericService<Lector, Integer> {
	public Lector update(Lector lector, int id);
	public void multar(int id,int dias);
	public void prestar(int idLector, int idLibro);
	public boolean verificarMulta(int id);
	public Prestamo add(int idLector, int idLibro);
	public int devolver(int idLector, int idPrestamo);
	
}
