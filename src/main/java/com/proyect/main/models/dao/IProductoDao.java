package com.proyect.main.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.proyect.main.models.entity.Producto;

public interface IProductoDao extends PagingAndSortingRepository<Producto, Long>{

	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String term);
	
	
	
	
}
