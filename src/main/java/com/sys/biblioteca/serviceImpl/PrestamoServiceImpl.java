package com.sys.biblioteca.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sys.biblioteca.entities.Lector;
import com.sys.biblioteca.entities.Prestamo;
import com.sys.biblioteca.genericsABM.GenericServiceImpl;
import com.sys.biblioteca.repository.PrestamoRepository;
import com.sys.biblioteca.service.PrestamoService;

@Service
public class PrestamoServiceImpl extends GenericServiceImpl<Prestamo, Integer> implements PrestamoService {

	@Autowired
	PrestamoRepository prestamoRepository;

	@Override
	public JpaRepository<Prestamo, Integer> getRepository() {
		return prestamoRepository;
	}

	@Override
	public void bajaLogica(int id) {
		Prestamo prestamo= prestamoRepository.findById(id).get();
		prestamo.setActivo(false);
		prestamoRepository.save(prestamo);
	}

	@Override
	public List<Prestamo> prestamosActivos() {
		List<Prestamo> prestamo=prestamoRepository.findAll().stream().filter(l -> l.isActivo()).collect(Collectors.toList());
		return prestamo;
	}
}
