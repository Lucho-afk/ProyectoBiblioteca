package com.sys.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sys.biblioteca.entities.Lector;
import com.sys.biblioteca.service.LectorService;

@RestController
@RequestMapping("/lector")
public class LectorController {

	@Autowired
	LectorService lectorService;

	@GetMapping
	public List<Lector> getAll() {
		return lectorService.getall();
	}
	
	@GetMapping(path = "/{id}")
	public Lector getById(@PathVariable("id")int id) {
		return lectorService.get(id);
	}

	@PostMapping
	public int add(@RequestBody Lector lector) {
		return lectorService.save(lector).getId();
	}

	@DeleteMapping(path = "/{id}")
	public void delete(@PathVariable("id") int id) {
		lectorService.delete(id);
	}
	
	@PutMapping(path="/{id}")
	public int update(@RequestBody Lector lector, @PathVariable("id") int id) {
		return this.lectorService.update(lector, id).getId();
	}
	
	@PostMapping(path="/{id}/{dias}")//controller temporal
	public void multar(@PathVariable("id") int id, @PathVariable("dias") int dias) {
		this.lectorService.multar(id, dias);
	}
	
	@PostMapping(path="/multa/{id}")//controller temporal
	public boolean desmultar(@PathVariable("id") int id) {
		return this.lectorService.verificarMulta(id);
	}
	
	@PostMapping(path = "/prestar/{idLector}/{idLibro}")
	public void prestar(@PathVariable("idLector") int idLector, @PathVariable("idLibro") int idLibro){
		this.lectorService.prestar(idLector, idLibro);
	}
	
	@DeleteMapping(path = "/borrar/{idLector}/{idPrestamo}")
	public void borrar(@PathVariable("idLector") int idLector, @PathVariable("idPrestamo") int idPrestamo) {
		this.lectorService.devolver(idLector, idPrestamo);
	}
}
