package com.sys.biblioteca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sys.biblioteca.entities.Genero;
import com.sys.biblioteca.genericsABM.GenericServiceImpl;
import com.sys.biblioteca.repository.GeneroRepository;
import com.sys.biblioteca.service.GeneroService;

@Service
public class GeneroServiceImpl  extends GenericServiceImpl<Genero, Integer> implements GeneroService{

	@Autowired
	GeneroRepository generoRepository;
	
	@Override
	public JpaRepository<Genero, Integer> getRepository() {
		return generoRepository;
	}

}
