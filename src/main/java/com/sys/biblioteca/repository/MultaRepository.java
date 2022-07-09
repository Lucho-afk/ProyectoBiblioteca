package com.sys.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sys.biblioteca.entities.Multa;

public interface MultaRepository extends JpaRepository<Multa, Integer>{
	
}
