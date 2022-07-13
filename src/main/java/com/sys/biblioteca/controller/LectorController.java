package com.sys.biblioteca.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public Lector getById(@PathVariable("id") int id) {
		return lectorService.get(id);
	}

	@RequestMapping(value = "/guardarLector", method = RequestMethod.POST)
	public ModelAndView guardarLector(@ModelAttribute Lector lector) {
		lector.setActivo(true);
		lectorService.save(lector);
		return new ModelAndView("redirect:/lector/home");
	}

	@GetMapping("/home")
	public ModelAndView mostrarLectores() {
		ModelAndView mav = new ModelAndView("Lectores");
		List<Lector> listas = lectorService.lectoresActivos();
		mav.addObject("lector", listas);
		return mav;
	}

	@RequestMapping(value = "/agregar", method = RequestMethod.GET)
	public ModelAndView agregar() {
		ModelAndView mav = new ModelAndView("crearLectorFrm");
		Lector lector = new Lector();
		mav.addObject("lector", lector);
		return mav;
	}

	@PutMapping(path = "/actualizar")
	public ModelAndView update(@ModelAttribute Lector lector) {
		lectorService.save(lector);
		return new ModelAndView("redirect:/lector/home");
	}

	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.GET)
	public ModelAndView update(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("actualizarLectorForm");
		Lector lector = lectorService.get(id);
		mav.addObject("lector", lector);
		return mav;
	}

	@GetMapping(path = "borrar/{id}")
	public ModelAndView delete(@PathVariable("id") int id) {
		lectorService.bajaLogica(id);
		return new ModelAndView("redirect:/lector/home");
	}

}
