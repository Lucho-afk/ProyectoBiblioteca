package com.sys.biblioteca.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	public List<Copia> findAll() {
		return copiaService.getall();
	}

	@GetMapping(path = "/agregar/{id}")
	public ModelAndView add(@PathVariable("id") int id) {
		copiaService.add(id);
		return new ModelAndView("redirect:/libros/home");
	}

	@GetMapping(path = "/estadoCopia")
	public List<String> estadoCopia(@ModelAttribute Libro l) {
		return copiaService.statusCopia(l.getId());
	}

}
