package com.sys.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sys.biblioteca.entities.Copia;
import com.sys.biblioteca.entities.Lector;
import com.sys.biblioteca.entities.Libro;
import com.sys.biblioteca.entities.Prestamo;
import com.sys.biblioteca.service.LectorService;
import com.sys.biblioteca.service.LibroService;
import com.sys.biblioteca.service.PrestamoService;
import com.sys.biblioteca.utils.DuoUtil;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {

	@Autowired
	PrestamoService prestamoService;
	
	@Autowired
	LectorService lectorService;
	
	@Autowired
	LibroService libroService;

	@GetMapping
	public List<Prestamo> getAll() {
		return prestamoService.getall();
	}

	@GetMapping(path = "/{id}")
	public Prestamo getById(@PathVariable("id") int id) {
		return prestamoService.get(id);
	}
	
	@PostMapping(path = "/{idLector}/{idLibro}")//prueba agregar prestamos
	public int add(@PathVariable("idLector") int idLector, @PathVariable("idLibro") int idLibro){
		return prestamoService.save(null).getId();
	}
	
	@GetMapping(path="/agregarPrestamo")
	public ModelAndView prestar() {
		ModelAndView mav = new ModelAndView("PrestarFrm");
		List<Lector> listaLectores= lectorService.lectoresActivos();
		List<Libro> listaLibros= libroService.librosActivos();
		mav.addObject("nombre_generico_para_lectores", listaLectores);
		mav.addObject("libros", listaLibros);
		mav.addObject("duoutil", new DuoUtil());
		return mav;
	}
	
	@GetMapping(path="/home")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("Prestamos");
		List<Lector> lista = lectorService.lectoresConPrestamos();
		mav.addObject("lectores", lista);
		return mav;
	}
	
	@PostMapping(path="/prestar")
	public ModelAndView crearPrestamo(@ModelAttribute DuoUtil duo) {
		lectorService.prestar(duo.getLector().getId(), duo.getLibro().getId());
		return new ModelAndView("redirect:/prestamo/home");
	}
	
	@GetMapping(path = "/devolver/{idL}/{idP}")
	public ModelAndView devolver(@PathVariable ("idL") String idLector, @PathVariable("idP") String idPrestamo) {
		lectorService.devolver(idLector, idPrestamo);
		return new ModelAndView("redirect:/prestamo/home");
	}
	
}
