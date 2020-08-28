package com.proyect.main.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.proyect.main.models.entity.Cliente;


public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>{

	@Query("select c from Cliente c where c.nombre like %?1%")
	public List<Cliente> findByNombreCliente(String term);
}