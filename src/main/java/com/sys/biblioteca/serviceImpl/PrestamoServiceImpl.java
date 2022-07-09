package com.sys.biblioteca.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.sys.biblioteca.entities.Copia;
import com.sys.biblioteca.entities.Lector;
import com.sys.biblioteca.entities.Libro;
import com.sys.biblioteca.entities.Prestamo;
import com.sys.biblioteca.genericsABM.GenericServiceImpl;
import com.sys.biblioteca.repository.PrestamoRepository;
import com.sys.biblioteca.service.CopiaService;
import com.sys.biblioteca.service.LectorService;
import com.sys.biblioteca.service.LibroService;
import com.sys.biblioteca.service.PrestamoService;

@Service
public class PrestamoServiceImpl extends GenericServiceImpl<Prestamo, Integer> implements PrestamoService {

	@Autowired
	PrestamoRepository prestamoRepository;

	@Override
	public JpaRepository<Prestamo, Integer> getRepository() {
		return prestamoRepository;
	}

	

	
	
}
