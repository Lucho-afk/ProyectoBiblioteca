package com.sys.biblioteca.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sys.biblioteca.entities.Copia;
import com.sys.biblioteca.entities.Lector;
import com.sys.biblioteca.entities.Libro;
import com.sys.biblioteca.service.CopiaService;
import com.sys.biblioteca.service.LibroService;
import com.sys.biblioteca.utils.LibroModeloDeMuestreo;


@RestController
@RequestMapping("/libros")
public class LibroController{

	@Autowired
	LibroService libroService;
	
	@Autowired
	CopiaService copiaService;
	
	@GetMapping
	public List<Libro> getAll(){
		return libroService.getall();
	}
	
	@GetMapping(path="/{id}")
	public Libro getById(@PathVariable("id") int id) {
		return libroService.get(id);
	}
	
	@PostMapping("/save")
	public String add(@ModelAttribute("libro") Libro l) {
		libroService.save(l);
		return "redirect:/libros";
	}
	
	
	@GetMapping("/home")//  libros/home
	public ModelAndView mostrarLibros() {
		ModelAndView mav = new ModelAndView("Libros");
		List<Libro> listas = libroService.librosActivos();
		mav.addObject("libro", listas);
		//copiaService.statusCopia(0);
		return mav;
	}
	
	
	@RequestMapping(value = "/agregar", method = RequestMethod.GET)// libro/agregar
	public ModelAndView agregar() {
		ModelAndView mav = new ModelAndView("crearLibroFrm");
		Libro libro = new Libro();
		mav.addObject("libro", libro);
		return mav;
	}
	
	@RequestMapping(value = "/guardarLibro", method = RequestMethod.POST)//libro/guardarLector
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
	
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.GET)//libro/modificar 
	public ModelAndView update(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("actualizarLibroForm");
		Libro libro = libroService.get(id);
		mav.addObject("libro", libro);
		return mav;
	}
	
	@GetMapping(path = "/estadoCopia")
	public List<String> estadoCopia(@ModelAttribute Libro l){
		return copiaService.statusCopia(l.getId());
	}
	
	
	/*----------------------------------------------------------*/	
/*	
	
	
//	@PostMapping("/save")
//	public String saveCourse(@ModelAttribute("course") Course course) {
//		// save Course to database
//		courseService.saveCourse(course);
//		return "redirect:/";
//	}
	
	@PutMapping("/actualizar")
	public int update(@RequestBody Libro l) {
		return libroService.save(l).getId();
	}
	
	@GetMapping(path = "/filtro/{id}")
	public boolean filtro(@PathVariable("id")int id) {
		return libroService.verificarCopiasDisponibles(id);
	}
	
	@GetMapping(path = "/mostrarLibros")
	public List<LibroModeloDeMuestreo>mostrarLibros(){
		return libroService.mostarLibros();
	}
	
	@GetMapping(path="/muestra")
	public ModelAndView muestreoDeLibros() {
		ModelAndView mav = new ModelAndView("libro");
		List<LibroModeloDeMuestreo> lista= new ArrayList<>();
		lista= libroService.mostarLibros();
		mav.addObject("libros", lista);
		System.err.println(lista.toString());
		return mav;
	}
	
//	@GetMapping("/addLibro")
//	public ModelAndView addLibro() {
//		ModelAndView mav= new ModelAndView("agregar-libro");
//		
//	}
	
	@GetMapping("/create")
	public String crear() {
		
		return "frmCrear";
	}
	
*/	
}
