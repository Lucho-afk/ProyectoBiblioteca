package com.sys.biblioteca.service;

import org.springframework.stereotype.Service;

import com.sys.biblioteca.entities.Prestamo;
import com.sys.biblioteca.genericsABM.GenericService;

@Service
public interface PrestamoService extends GenericService<Prestamo, Integer>{
	
}
