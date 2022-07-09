package com.sys.biblioteca.serviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sys.biblioteca.entities.Status;
import com.sys.biblioteca.genericsABM.GenericServiceImpl;
import com.sys.biblioteca.repository.StatusRepository;
import com.sys.biblioteca.service.StatusService;

@Service
public class StatusServiceImpl extends GenericServiceImpl<Status, Integer> implements StatusService{

	@Autowired
	StatusRepository repository;
	
	@Override
	public JpaRepository<Status, Integer> getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}

}
