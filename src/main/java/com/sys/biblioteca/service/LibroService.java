package com.sys.biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;


import com.sys.biblioteca.entities.Libro;
import com.sys.biblioteca.genericsABM.GenericService;
import com.sys.biblioteca.utils.LibroModeloDeMuestreo;

@Service
public interface LibroService extends GenericService<Libro, Integer>{
	public Libro update (int id, Libro entity);
	public int add(Libro entity, int id);
	public boolean verificarCopiasDisponibles(int id);
	public List<LibroModeloDeMuestreo> mostarLibros();
}
