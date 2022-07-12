package com.sys.biblioteca.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sys.biblioteca.entities.Copia;
import com.sys.biblioteca.entities.Libro;
import com.sys.biblioteca.repository.CopiaRepository;
import com.sys.biblioteca.service.CopiaService;


@RestController
@RequestMapping("/copias")
public class CopiaController {

	@Autowired
	CopiaService copiaService;
	
	@Autowired
	CopiaRepository copiaRepository;

	
	@GetMapping
	public List<Copia> findAll(){
		return copiaService.getall();
	}
	
	@GetMapping(path = "/agregar/{id}")//hay que pasarle el id del libro al que se le agrega una copia
	public ModelAndView add(@PathVariable ("id")int id) {
		copiaService.add(id);
		return new ModelAndView("redirect:/libros/home");
	}
	
	@GetMapping(path = "/estadoCopia")
	public List<String> estadoCopia(@ModelAttribute Libro l){
		return copiaService.statusCopia(l.getId());
	}
	
	/*-------------------------------------------------------------------*//*
	// method="GET"
	@GetMapping(path = "/libro/{id}")//esta funcion va a traer las copias de un libro sin importar su estado
	public List<Copia> findCopysByBook(@PathVariable("id")int id){
		return copiaService.findCopys(id);
		
	}
	@PostMapping(path = "/{id}")//hay que pasarle el id del libro al que se le agrega una copia
	public int add(@PathVariable ("id")int id) {
		return copiaService.add(id);
	}
	
	@DeleteMapping(path="/{id}")
	public void delete(@PathVariable("id") int id) {
		copiaService.delete(id);
	}
	
	@PutMapping(path="/{id}/{idEstado}")//hay que pasarle el id de la copia que queremos actualizar, y el estado que le queremos editar
	public void update(@PathVariable("id") int id, @PathVariable("idEstado") int idEstado) {
		copiaService.update(id, idEstado);
	}
	
	@GetMapping(path="/mostrar/{id}")
	public List<String> mostrar(@PathVariable("id") int id){
		return copiaService.mostarPorPantalla(id);
	}
*/

}

