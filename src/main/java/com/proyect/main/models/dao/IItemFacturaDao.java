package com.proyect.main.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyect.main.models.entity.Item;
import com.proyect.main.models.entity.Producto;

public interface IItemFacturaDao extends CrudRepository<Item, Long>{

	
	
}
