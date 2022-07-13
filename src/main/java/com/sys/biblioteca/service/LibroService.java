package com.sys.biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.sys.biblioteca.entities.Libro;
import com.sys.biblioteca.genericsABM.GenericService;

@Service
public interface LibroService extends GenericService<Libro, Integer>{
	public boolean verificarCopiasDisponibles(int id);
	public void bajaLogica(int id);
	public List<Libro> librosActivos();
}
