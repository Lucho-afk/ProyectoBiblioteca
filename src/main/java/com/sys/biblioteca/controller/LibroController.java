package com.sys.biblioteca.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.sys.biblioteca.entities.Libro;
import com.sys.biblioteca.service.CopiaService;
import com.sys.biblioteca.service.LibroService;
import com.sys.biblioteca.utils.EstadosUtil;

@RestController
@RequestMapping("/libros")
public class LibroController {

	@Autowired
	LibroService libroService;

	@Autowired
	CopiaService copiaService;

	@GetMapping
	public List<Libro> getAll() {
		return libroService.getall();
	}

	@GetMapping(path = "/{id}")
	public Libro getById(@PathVariable("id") int id) {
		return libroService.get(id);
	}

	@PostMapping("/save")
	public String add(@ModelAttribute("libro") Libro l) {
		libroService.save(l);
		return "redirect:/libros";
	}

	@GetMapping("/home")
	public ModelAndView mostrarLibros() {
		ModelAndView mav = new ModelAndView("Libros");
		List<Libro> listas = libroService.librosActivos();
		for (EstadosUtil e : copiaService.verEstatus(listas)) {
			System.err.println(e.toString());
		}
		mav.addObject("libro", listas);
		mav.addObject("copias", copiaService.verEstatus(listas));
		return mav;
	}

	@RequestMapping(value = "/agregar", method = RequestMethod.GET) // libro/agregar
	public ModelAndView agregar() {
		ModelAndView mav = new ModelAndView("crearLibroFrm");
		Libro libro = new Libro();
		mav.addObject("libro", libro);
		return mav;
	}

	@RequestMapping(value = "/guardarLibro", method = RequestMethod.POST)
	public ModelAndView guardarLibro(@ModelAttribute Libro libro) {
		libro.setActivo(true);
		libroService.save(libro);
		return new ModelAndView("redirect:/libros/home");
	}

	@GetMapping(path = "borrar/{id}")
	public ModelAndView delete(@PathVariable("id") int id) {
		libroService.bajaLogica(id);
		return new ModelAndView("redirect:/libros/home");
	}

	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.GET)
	public ModelAndView update(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("actualizarLibroForm");
		Libro libro = libroService.get(id);
		mav.addObject("libro", libro);
		return mav;
	}

	@GetMapping(path = "/estadoCopia")
	public List<String> estadoCopia(@ModelAttribute Libro l) {
		return copiaService.statusCopia(l.getId());
	}
}
