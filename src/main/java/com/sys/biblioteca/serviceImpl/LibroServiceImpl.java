package com.sys.biblioteca.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.sys.biblioteca.entities.Copia;
import com.sys.biblioteca.entities.Libro;
import com.sys.biblioteca.genericsABM.GenericServiceImpl;
import com.sys.biblioteca.repository.LibroRepository;
import com.sys.biblioteca.service.LibroService;

@Service
public class LibroServiceImpl extends GenericServiceImpl<Libro, Integer> implements LibroService {

	@Autowired
	LibroRepository libroRepository;

	@Override
	public JpaRepository<Libro, Integer> getRepository() {
		return libroRepository;
	}

	@Override
	public boolean verificarCopiasDisponibles(int id) {

		Libro libro = libroRepository.findById(id).get();
		List<Copia> lista = libro.getLstCopias().stream().filter(o -> o.getStatus().getEstado().equals("disponible"))
				.collect(Collectors.toList());
		return lista.size() > 0;
	}

	@Override
	public void bajaLogica(int id) {
		Libro libro = libroRepository.findById(id).get();
		libro.setActivo(false);
		libroRepository.save(libro);

	}

	@Override
	public List<Libro> librosActivos() {
		List<Libro> libros = libroRepository.findAll().stream().filter(l -> l.isActivo()).collect(Collectors.toList());
		return libros;
	}

}
