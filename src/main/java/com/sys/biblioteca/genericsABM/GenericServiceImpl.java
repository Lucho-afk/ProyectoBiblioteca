package com.sys.biblioteca.genericsABM;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class GenericServiceImpl<T, ID extends Serializable> implements GenericService<T, ID> {

	@Override
	public T save(T entity) {
		return getRepository().save(entity);
	}

	@Override
	public void delete(ID id) {
		getRepository().deleteById(id);

	}

	@Override
	public T get(ID id) {
		Optional<T> obj = getRepository().findById(id);
		T aux = null;
		if (obj.isPresent()) {
			aux = obj.get();
		} else {
			throw new RuntimeException("id inexistente: " + id);
		}
		return aux;

	}

	@Override
	public List<T> getall() {
		List<T> lista = new ArrayList<>();
		getRepository().findAll().forEach(i -> lista.add(i));
		return lista;
	}

	public abstract JpaRepository<T, ID> getRepository();

}
