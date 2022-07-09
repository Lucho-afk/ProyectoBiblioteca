package com.sys.biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sys.biblioteca.entities.Copia;
import com.sys.biblioteca.genericsABM.GenericService;

@Service
public interface CopiaService extends GenericService<Copia, Integer>{
	public List<Copia> findCopys(int id);
	public int add(int id);
	public void update(int id, int idStatus);
	public List<String> mostarPorPantalla(int idCopia);
}
