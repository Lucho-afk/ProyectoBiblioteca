package com.sys.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sys.biblioteca.entities.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer>{
	
}
