package com.proyect.main.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyect.main.models.dao.IFacturaDao;
import com.proyect.main.models.entity.Factura;

@Service
public class FacturaServiceImpl implements IFacturaService{

	
	@Autowired
	private IFacturaDao facturaDao;
	

}
