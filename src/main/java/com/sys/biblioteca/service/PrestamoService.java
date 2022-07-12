package com.sys.biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sys.biblioteca.entities.Prestamo;
import com.sys.biblioteca.genericsABM.GenericService;

@Service
public interface PrestamoService extends GenericService<Prestamo, Integer>{
	public void bajaLogica(int id);
	public List<Prestamo> prestamosActivos();
}
