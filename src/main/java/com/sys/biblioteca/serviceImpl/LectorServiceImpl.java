package com.sys.biblioteca.serviceImpl;

import java.time.LocalDate;
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

//
//agrega = dias * 2, por cada dia de retraso en la entrega pactada 
//en caso de tener multa previa agrega los dias a la multa que tengas(se usaria en caso de devolver 1 de los 3 libros permitidos)
//

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

//	
//	  En la siguiente metodo se implementaron 2 modulos 
//	  --verificarMulta referente a LectorService 
//	  --verificarCopiasDisponibles referente a LibrosServiceImpl
//
	@Override
	public void prestar(int idLector, int idLibro) {

		Optional<Lector> optionalAux = lectorRepository.findById(idLector);
		if (optionalAux.isPresent()) {
			Lector lector = optionalAux.get();
			if (verificarMulta(lector.getId())) {
				if (libroService.verificarCopiasDisponibles(idLibro)) {
					add(idLector, idLibro);
				} else {
					throw new RuntimeException("el libro no tiene copias disponibles");// lo voy a tirar en alerts en el
																						// front
				}
			} else {
				throw new RuntimeException("este lector esta multado");
			}
		} else {
			throw new RuntimeException("id invalido");
		}
	}

//
//	devuelve FALSE si la multa esta activa
//	devuelve TRUE si la multa ya expiro
//
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

// necesito el id del lector, del libro V
// necesito validar que el lector no tenga mas de 3 prestamos V
// necesito traer la cantidad disponible de copias V
// necesito agarrar una de esas copias y hacerle un update que pase a prestado V
// necesito obtener el prestamo asociado al lector // en caso de que no tenga necesito crear uno V
// necesito persistir ese prestamo v
// necesito a esa lista de prestamo agregarle el prestamo v
// necesito setear al lector en prestamo v
// necesito agregar ese prestamo al lector v
// necesito persistir al lector v
// lector -> prestamo v
// en el momento que hice este codigo solo dios y yo sabiamos que hacia ahora solo dios lo sabe... suerte.
	@Override
	public Prestamo add(int idLector, int idLibro) {
		Lector lector = lectorRepository.findById(idLector).get();
		Libro libro = libroService.get(idLibro);
		Prestamo prestamoNuevo = new Prestamo();
		prestamoNuevo.setInicio(LocalDate.now());
		prestamoNuevo.setFin(LocalDate.now().plusDays(30));
		if (lector.getLstPrestamos().size() < 3) {
			Copia copia = libro.getLstCopias().stream().filter(obj -> obj.getStatus().getEstado().equals("disponible"))
					.collect(Collectors.toList()).get(0);
			copiaService.update(copia.getId(), 2);
			prestamoNuevo.setCopia(copiaService.get(copia.getId()));
			List<Prestamo> listaPrestamoAUX = lector.getLstPrestamos();
			prestamoNuevo.setLector(lector);
			Prestamo aux = prestamoService.save(prestamoNuevo);
			listaPrestamoAUX.add(aux);
			lector.setLstPrestamos(listaPrestamoAUX);
			System.err.println(lector.getLstPrestamos().toString());
			lectorRepository.save(lector);
		} else {
			throw new RuntimeException("el lector tiene mas de 3 prestamos");
		}
		return null;
	}

	@Override
	public int devolver(int idLector, int idPrestamo) {
		Lector lector = lectorRepository.findById(idLector).get();
		if (!lector.getLstPrestamos().isEmpty()) {
			Prestamo prestamo = prestamoService.get(idPrestamo);
			if(LocalDate.now().isAfter(prestamo.getFin())) {
				int dias =prestamo.getFin().datesUntil(LocalDate.now()).collect(Collectors.toList()).size();
				multar(idLector, dias);
			}
			lector.getLstPrestamos().remove(prestamo);
			Copia copia=prestamo.getCopia();
			copiaService.update(copia.getId(), 1);
			lectorRepository.save(lector);
			prestamoService.delete(idPrestamo);
		} else {
			throw new RuntimeException("no hay prestamos asociados a este lector");
		}
		return 0;
	}

}
