package com.proyect.main.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyect.main.models.entity.Factura;
import com.proyect.main.models.entity.Item;
import com.proyect.main.models.entity.Mesa;

public interface IMesaDao extends CrudRepository<Mesa, Long>{

	
	@Query("SELECT m FROM Mesa m LEFT JOIN m.facturas f  where f.estado = 0 or f.estado is null order by f.mesa.id")
	public List<Mesa> listadoFacturas();
	
}
