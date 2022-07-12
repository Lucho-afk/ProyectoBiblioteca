package com.sys.biblioteca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sys.biblioteca.entities.Multa;
import com.sys.biblioteca.genericsABM.GenericServiceImpl;
import com.sys.biblioteca.repository.MultaRepository;
import com.sys.biblioteca.service.MultaService;

@Service
public class MultaServiceImpl extends GenericServiceImpl<Multa, Integer> implements MultaService{

	@Autowired
	MultaRepository multaRepository;
	
	@Override
	public JpaRepository<Multa, Integer> getRepository() {
		
		return this.multaRepository;
	}

}
