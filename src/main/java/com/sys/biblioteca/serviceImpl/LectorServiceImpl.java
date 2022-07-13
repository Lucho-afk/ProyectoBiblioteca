package com.sys.biblioteca.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.sys.biblioteca.entities.Copia;
import com.sys.biblioteca.entities.Lector;
import com.sys.biblioteca.entities.Libro;
import com.sys.biblioteca.entities.Multa;
import com.sys.biblioteca.entities.Prestamo;
import com.sys.biblioteca.genericsABM.GenericServiceImpl;
import com.sys.biblioteca.repository.LectorRepository;
import com.sys.biblioteca.repository.MultaRepository;
import com.sys.biblioteca.service.CopiaService;
import com.sys.biblioteca.service.LectorService;
import com.sys.biblioteca.service.LibroService;
import com.sys.biblioteca.service.MultaService;
import com.sys.biblioteca.service.PrestamoService;

@Service
public class LectorServiceImpl extends GenericServiceImpl<Lector, Integer> implements LectorService {

	@Autowired
	LectorRepository lectorRepository;

	@Autowired
	MultaRepository multaRepository;

	@Autowired
	CopiaService copiaService;

	@Autowired
	MultaService multaService;

	@Autowired
	LibroService libroService;

	@Autowired
	PrestamoService prestamoService;

	@Override
	public JpaRepository<Lector, Integer> getRepository() {
		return this.lectorRepository;
	}

	@Override
	public Lector update(Lector entity, int id) {
		Optional<Lector> aux = lectorRepository.findById(id);
		if (aux.isPresent()) {
			Lector lector = aux.get();
			lector.setDireccion(entity.getDireccion());
			lector.setNombre(entity.getNombre());
			lector.setTelefono(entity.getTelefono());
			return lectorRepository.save(lector);
		} else {
			throw new RuntimeException("ID NO ENCONTRADO:" + id);
		}
	}

	@Override
	public void multar(int id, int dias) {
		Optional<Lector> lector = this.lectorRepository.findById(id);
		if (lector.isPresent()) {
			Lector aux = lector.get();
			if (aux.getMulta() == null) {
				Multa multa = new Multa();
				multa.setFechaInicio(LocalDate.now());
				multa.setFechaFinal(multa.getFechaInicio().plusDays(dias * 2));
				this.multaRepository.save(multa);
				aux.setMulta(multa);
				update(aux, id);
			} else {
				if (aux.getMulta().getFechaFinal().isBefore(LocalDate.now().plusDays(dias * 2))) {
					aux.getMulta().setFechaFinal(LocalDate.now().plusDays(dias * 2));
				}
			}
		} else {
			throw new RuntimeException("id invalido");
		}
	}

	@Override
	public void prestar(int idLector, int idLibro) {
		Optional<Lector> optionalAux = lectorRepository.findById(idLector);
		if (optionalAux.isPresent()) {
			Lector lector = optionalAux.get();
			if (verificarMulta(lector.getId())) {
				if (libroService.verificarCopiasDisponibles(idLibro)) {
					add(idLector, idLibro);
				} else {
					throw new RuntimeException("El libro no tiene copias disponibles");
				}
			} else {
				throw new RuntimeException("Este lector esta multado");
			}
		} else {
			throw new RuntimeException("id invalido");
		}
	}

	@Override
	public boolean verificarMulta(int id) {
		Lector aux = lectorRepository.findById(id).get();
		if (aux.getMulta() != null) {
			if (aux.getMulta().getFechaFinal().isBefore(LocalDate.now())) {
				aux.setMulta(null);
				lectorRepository.save(aux);
			}
		}
		return (aux.getMulta() == null);
	}

	@Override
	public Prestamo add(int idLector, int idLibro) {
		Lector lector = lectorRepository.findById(idLector).get();
		Libro libro = libroService.get(idLibro);
		Prestamo prestamoNuevo = new Prestamo();
		prestamoNuevo.setActivo(true);
		prestamoNuevo.setInicio(LocalDate.now());
		prestamoNuevo.setFin(LocalDate.now().plusDays(30));
		List<Prestamo> lstActivaDePrestamos = lector.getLstPrestamos().stream().filter(p -> p.isActivo())
				.collect(Collectors.toList());
		if (lstActivaDePrestamos.size() < 3) {
			Copia copia = libro.getLstCopias().stream().filter(obj -> obj.getStatus().getEstado().equals("disponible"))
					.collect(Collectors.toList()).get(0);
			copiaService.update(copia.getId(), 2);
			prestamoNuevo.setCopia(copiaService.get(copia.getId()));
			List<Prestamo> listaPrestamoAUX = lector.getLstPrestamos();
			prestamoNuevo.setLector(lector);
			Prestamo aux = prestamoService.save(prestamoNuevo);
			listaPrestamoAUX.add(aux);
			lector.setLstPrestamos(listaPrestamoAUX);
			lectorRepository.save(lector);
		} else {
			throw new RuntimeException("El lector tiene mas de 3 prestamos");
		}
		return null;
	}

	@Override
	public int devolver(String idLector, String idPrestamo) {
		int idLec = Integer.parseInt(idLector);
		int idPre = Integer.parseInt(idPrestamo);
		Lector lector = lectorRepository.findById(idLec).get();
		if (!lector.getLstPrestamos().isEmpty()) {
			Prestamo prestamo = prestamoService.get(idPre);
			if (LocalDate.now().isAfter(prestamo.getFin())) {
				int dias = prestamo.getFin().datesUntil(LocalDate.now()).collect(Collectors.toList()).size();
				multar(idLec, dias);
			}
			List<Prestamo> lstAux = lector.getLstPrestamos();
			lstAux.remove(prestamo);
			lector.setLstPrestamos(lstAux);
			Copia copia = prestamo.getCopia();
			copiaService.update(copia.getId(), 1);
			lectorRepository.save(lector);
			prestamoService.bajaLogica(idPre);
		} else {
			throw new RuntimeException("no hay prestamos asociados a este lector");
		}
		return 0;
	}

	@Override
	public void bajaLogica(int id) {
		Lector lector = lectorRepository.findById(id).get();
		lector.setActivo(false);
		lectorRepository.save(lector);
	}

	@Override
	public List<Lector> lectoresActivos() {
		List<Lector> lectores = lectorRepository.findAll().stream().filter(l -> l.isActivo())
				.collect(Collectors.toList());
		return lectores;
	}

	public List<Lector> lectoresConPrestamos() {
		List<Prestamo> prestamos = new ArrayList<>();
		List<Lector> aux = new ArrayList<>();
		List<Lector> lectores = lectorRepository.findAll().stream()
				.filter(l -> l.getLstPrestamos().size() > 0 && l.isActivo()).collect(Collectors.toList());
		for (Lector l : lectores) {
			Lector auxLector = new Lector();
			for (Prestamo p : l.getLstPrestamos()) {
				if (p.isActivo()) {
					prestamos.add(p);
				}
			}
			auxLector = l;
			auxLector.setLstPrestamos(prestamos);
			aux.add(auxLector);
			prestamos = new ArrayList<>();
		}
		return aux.stream().filter(l -> l.getLstPrestamos().size() > 0 && l.isActivo()).collect(Collectors.toList());
	}
}
