package com.sys.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sys.biblioteca.entities.Prestamo;
import com.sys.biblioteca.service.PrestamoService;

@RestController
@RequestMapping(name = "/prestamo")
public class PrestamoController {

	@Autowired
	PrestamoService prestamoService;

	@GetMapping
	public List<Prestamo> getAll() {
		return prestamoService.getall();
	}

	@GetMapping(path = "/{id}")
	public Prestamo getById(@PathVariable("id") int id) {
		return prestamoService.get(id);
	}
	
	@PostMapping(path = "/{idLector}/{idLibro}")
	public int add(@PathVariable("idLector") int idLector, @PathVariable("idLibro") int idLibro){
		return prestamoService.save(null).getId();
	}
}
