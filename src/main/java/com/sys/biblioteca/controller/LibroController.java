package com.sys.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sys.biblioteca.entities.Libro;
import com.sys.biblioteca.service.LibroService;
import com.sys.biblioteca.utils.LibroModeloDeMuestreo;


@RestController
@RequestMapping(value="/libros")
public class LibroController{

	@Autowired
	LibroService libroService;
	
	@GetMapping
	public List<Libro> getAll(){
		return libroService.getall();
	}
	
	@GetMapping(path="/{id}")
	public Libro getById(@PathVariable("id") int id) {
		return libroService.get(id);
	}
	
	@PostMapping(path="/{idGenero}")
	public int add(@RequestBody Libro l, @PathVariable("idGenero") int idGenero) {
		return libroService.add(l, idGenero);
	}
	
	@PutMapping(path="/{id}")
	public int update(@PathVariable("id")int id, @RequestBody Libro l) {
		return libroService.update(id, l).getId();
	}
	
	@GetMapping(path = "/filtro/{id}")
	public boolean filtro(@PathVariable("id")int id) {
		return libroService.verificarCopiasDisponibles(id);
	}
	
	@GetMapping(path = "/mostrarLibros")
	public List<LibroModeloDeMuestreo>mostrarLibros(){
		return libroService.mostarLibros();
	}
	
}
