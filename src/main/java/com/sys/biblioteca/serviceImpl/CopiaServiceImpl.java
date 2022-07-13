package com.sys.biblioteca.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.sys.biblioteca.entities.Copia;
import com.sys.biblioteca.entities.Libro;
import com.sys.biblioteca.entities.Status;
import com.sys.biblioteca.genericsABM.GenericServiceImpl;
import com.sys.biblioteca.repository.CopiaRepository;
import com.sys.biblioteca.service.CopiaService;
import com.sys.biblioteca.service.LibroService;
import com.sys.biblioteca.service.StatusService;
import com.sys.biblioteca.utils.EstadosUtil;

@Service
public class CopiaServiceImpl extends GenericServiceImpl<Copia, Integer> implements CopiaService {

	@Autowired
	CopiaRepository copiaRepository;

	@Autowired
	LibroService libroService;

	@Autowired
	StatusService statusService;
	
	@Override
	public List<Copia> findCopys(int id) {
		List<Copia> lista = new ArrayList<Copia>();
		for (Copia c : libroService.get(id).getLstCopias()) {
			lista.add(c);
		}
		return lista;
	}

	@Override
	public JpaRepository<Copia, Integer> getRepository() {
		return copiaRepository;
	}

	@Override
	public int add(int id) {
		Libro libro = libroService.get(id);
		Copia copia= new Copia();
		copia.setActivo(true);
		Status status= statusService.get(1);
		copia.setLibro(libro);
		copia.setStatus(status);
		Copia copiaAux = copiaRepository.save(copia);
		Set<Copia> aux = libro.getLstCopias();
		aux.add(copiaAux);
		libro.setLstCopias(aux);
		libroService.save(libro);
		return copiaAux.getId();
	}

	@Override
	public void update(int id, int idStatus) {
		Optional<Copia> aux = copiaRepository.findById(id);
		if (aux.isPresent()) {
			Copia persisCopia = aux.get();
			Status status= statusService.get(idStatus);
			persisCopia.setStatus(status);
			copiaRepository.save(persisCopia);
		} else {
			throw new RuntimeException("id no encontrado: " + id);
		}

	}

	@Override
	public List<String> mostarPorPantalla(int idCopia) {
		Optional<Copia> aux = copiaRepository.findById(idCopia);
		Copia copia = aux.get();
		List<String> rta= new ArrayList<>();
		rta.add(copia.getId()+"");
		rta.add(copia.getLibro().getTitulo());
		rta.add(copia.getStatus().getEstado());
		return rta;
	}

	@Override
	public List<String> statusCopia(int idLibro) {
		Libro libro= libroService.get(idLibro);
		List<String> rta= new ArrayList<>();
		rta.add("Disponibles :" + libro.getLstCopias().stream().filter(i -> i.getStatus().getId()==1).collect(Collectors.toList()).size());
		rta.add("Prestado :" + libro.getLstCopias().stream().filter(i -> i.getStatus().getId()==2).collect(Collectors.toList()).size());
		rta.add("Retraso :" + libro.getLstCopias().stream().filter(i -> i.getStatus().getId()==3).collect(Collectors.toList()).size());
		rta.add("Reparacion :" + libro.getLstCopias().stream().filter(i -> i.getStatus().getId()==4).collect(Collectors.toList()).size());
		return rta;
	}

	@Override
	public List<EstadosUtil> verEstatus(List<Libro> lbs) {
		List<EstadosUtil> aux= new ArrayList<>();
		for(Libro l: lbs) {
			int disponible=0;
			int prestado=0;
			int retrasado=0;
			int reparacion=0;
			disponible= l.getLstCopias().stream().filter(c -> c.getStatus().getId()==1).collect(Collectors.toList()).size();
			prestado= l.getLstCopias().stream().filter(c -> c.getStatus().getId()==2).collect(Collectors.toList()).size();
			retrasado= l.getLstCopias().stream().filter(c -> c.getStatus().getId()==3).collect(Collectors.toList()).size();
			reparacion= l.getLstCopias().stream().filter(c -> c.getStatus().getId()==4).collect(Collectors.toList()).size();
			aux.add(new EstadosUtil(disponible,prestado,retrasado,reparacion));
		}
		return aux;
	}	
}
