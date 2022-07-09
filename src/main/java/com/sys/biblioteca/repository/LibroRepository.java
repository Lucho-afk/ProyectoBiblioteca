package com.sys.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sys.biblioteca.entities.Libro;


public interface LibroRepository extends JpaRepository<Libro, Integer> {

}
